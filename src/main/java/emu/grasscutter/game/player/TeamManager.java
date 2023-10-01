package emu.grasscutter.game.player;

import static emu.grasscutter.config.Configuration.GAME_OPTIONS;

import dev.morphia.annotations.*;
import emu.grasscutter.*;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.avatar.AvatarSkillDepotData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.entity.*;
import emu.grasscutter.game.props.*;
import emu.grasscutter.game.world.*;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.*;
import emu.grasscutter.net.proto.EnterTypeOuterClass.EnterType;
import emu.grasscutter.net.proto.MotionStateOuterClass.MotionState;
import emu.grasscutter.net.proto.PlayerDieTypeOuterClass.PlayerDieType;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;
import emu.grasscutter.net.proto.TrialAvatarGrantRecordOuterClass.TrialAvatarGrantRecord.GrantReason;
import emu.grasscutter.server.event.entity.EntityCreationEvent;
import emu.grasscutter.server.event.player.*;
import emu.grasscutter.server.packet.send.*;
import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.*;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.*;
import java.util.stream.Stream;
import lombok.*;

@Entity
public final class TeamManager extends BasePlayerDataManager {
    @Transient private final List<EntityAvatar> avatars;
    @Transient @Getter private final Set<EntityBaseGadget> gadgets;
    @Transient @Getter private final IntSet teamResonances;
    @Transient @Getter private final IntSet teamResonancesConfig;
    @Transient @Getter @Setter private Set<String> teamAbilityEmbryos;
    // This needs to be a LinkedHashMap to guarantee insertion order.
    @Getter private LinkedHashMap<Integer, TeamInfo> teams;
    private int currentTeamIndex;
    @Getter @Setter private int currentCharacterIndex;
    @Transient @Getter @Setter private TeamInfo mpTeam;
    @Transient @Getter @Setter private EntityTeam entity;

    @Transient private int useTemporarilyTeamIndex = -1;
    @Transient private List<TeamInfo> temporaryTeam; // Temporary Team for tower
    @Transient @Getter @Setter private boolean usingTrialTeam;
    @Transient @Getter @Setter private TeamInfo trialAvatarTeam;
    // hold trial avatars for later use in rebuilding active team
    @Transient @Getter @Setter private Map<Integer, Avatar> trialAvatars;

    @Transient @Getter @Setter
    private int previousIndex = -1; // index of character selection in team before adding trial avatar

    public TeamManager() {
        this.mpTeam = new TeamInfo();
        this.avatars = Collections.synchronizedList(new ArrayList<>());
        this.gadgets = new HashSet<>();
        this.teamResonances = new IntOpenHashSet();
        this.teamResonancesConfig = new IntOpenHashSet();
        this.teamAbilityEmbryos = new HashSet<>();
        this.trialAvatars = new HashMap<>();
        this.trialAvatarTeam = new TeamInfo();
    }

    public TeamManager(Player player) {
        this();
        this.setPlayer(player);

        this.teams = new LinkedHashMap<>();
        this.currentTeamIndex = 1;
        for (int i = 1; i <= GameConstants.DEFAULT_TEAMS; i++) {
            this.teams.put(i, new TeamInfo());
        }
    }

    // Add team ability embryos, NOT to be confused with avatarAbilties.
    // These should include the ones in LevelEntity (according to levelEntityConfig field in sceneId)
    // rn only apply to big world defaults, but will fix scaramouch domain circles
    // (BinOutput/LevelEntity/Level_Monster_Nada_setting)
    public AbilityControlBlockOuterClass.AbilityControlBlock getAbilityControlBlock() {
        AbilityControlBlockOuterClass.AbilityControlBlock.Builder abilityControlBlock =
                AbilityControlBlockOuterClass.AbilityControlBlock.newBuilder();
        int embryoId = 0;

        // add from default
        if (Arrays.stream(GameConstants.DEFAULT_TEAM_ABILITY_STRINGS).count() > 0) {
            List<String> teamAbilties =
                    Arrays.stream(GameConstants.DEFAULT_TEAM_ABILITY_STRINGS).toList();
            for (String skill : teamAbilties) {
                AbilityEmbryoOuterClass.AbilityEmbryo emb =
                        AbilityEmbryoOuterClass.AbilityEmbryo.newBuilder()
                                .setAbilityId(++embryoId)
                                .setAbilityNameHash(Utils.abilityHash(skill))
                                .setAbilityOverrideNameHash(GameConstants.DEFAULT_ABILITY_NAME)
                                .build();
                abilityControlBlock.addAbilityEmbryoList(emb);
            }
        }

        // same as avatar ability hash (add frm levelEntityConfig data)
        if (this.getTeamAbilityEmbryos().size() > 0) {
            for (String skill : this.getTeamAbilityEmbryos()) {
                AbilityEmbryoOuterClass.AbilityEmbryo emb =
                        AbilityEmbryoOuterClass.AbilityEmbryo.newBuilder()
                                .setAbilityId(++embryoId)
                                .setAbilityNameHash(Utils.abilityHash(skill))
                                .setAbilityOverrideNameHash(GameConstants.DEFAULT_ABILITY_NAME)
                                .build();
                abilityControlBlock.addAbilityEmbryoList(emb);
            }
        }

        // return block to add
        return abilityControlBlock.build();
    }

    public World getWorld() {
        return this.getPlayer().getWorld();
    }

    /**
     * Search through all teams and if the team matches, return that index. Otherwise, return -1. No
     * match could mean that the team does not currently belong to the player.
     */
    public int getTeamId(TeamInfo team) {
        for (int i = 1; i <= this.teams.size(); i++) {
            if (this.teams.get(i).equals(team)) {
                return i;
            }
        }
        return -1;
    }

    public int getCurrentTeamId() {
        // Starts from 1
        return currentTeamIndex;
    }

    private void setCurrentTeamId(int currentTeamIndex) {
        this.currentTeamIndex = currentTeamIndex;
    }

    public long getCurrentCharacterGuid() {
        return this.getCurrentAvatarEntity().getAvatar().getGuid();
    }

    public TeamInfo getCurrentTeamInfo() {
        if (useTemporarilyTeamIndex >= 0 && useTemporarilyTeamIndex < temporaryTeam.size()) {
            return temporaryTeam.get(useTemporarilyTeamIndex);
        }
        if (this.getPlayer().isInMultiplayer()) {
            return this.getMpTeam();
        }
        return this.getTeams().get(this.currentTeamIndex);
    }

    public TeamInfo getCurrentSinglePlayerTeamInfo() {
        return this.getTeams().get(this.currentTeamIndex);
    }

    public List<EntityAvatar> getActiveTeam() {
        return avatars;
    }

    /**
     * Returns the active team. If there are errors with the team, they can be fixed.
     *
     * @param fix If true, the team will be fixed.
     * @return The active team.
     */
    public List<EntityAvatar> getActiveTeam(boolean fix) {
        if (!fix) return this.getActiveTeam();

        // Remove duplicate avatars.
        var avatars = this.getActiveTeam();
        var avatarIds = new HashSet<Long>();
        for (var entityAvatar : new ArrayList<>(avatars)) {
            if (avatarIds.contains(entityAvatar.getAvatar().getGuid())) {
                avatars.remove(entityAvatar);
            } else {
                avatarIds.add(entityAvatar.getAvatar().getGuid());
            }
        }

        return avatars; // Return the fixed team.
    }

    public EntityAvatar getCurrentAvatarEntity() {
        // Check if any avatars are equipped.
        if (this.getActiveTeam().isEmpty()) return null;

        if (this.currentCharacterIndex >= this.getActiveTeam().size()) {
            this.currentCharacterIndex = 0; // Reset to the first character.
        }

        return this.getActiveTeam().get(this.currentCharacterIndex);
    }

    public boolean isSpawned() {
        return this.getPlayer().getScene() != null
                && this.getPlayer()
                        .getScene()
                        .getEntities()
                        .containsKey(this.getCurrentAvatarEntity().getId());
    }

    public int getMaxTeamSize() {
        if (this.getPlayer().isInMultiplayer()) {
            int max = GAME_OPTIONS.avatarLimits.multiplayerTeam;
            if (this.getPlayer().getWorld().getHost() == this.getPlayer()) {
                return Math.max(1, (int) Math.ceil(max / (double) this.getWorld().getPlayerCount()));
            }
            return Math.max(1, (int) Math.floor(max / (double) this.getWorld().getPlayerCount()));
        }

        return GAME_OPTIONS.avatarLimits.singlePlayerTeam;
    }

    // Methods

    /** Returns true if there is space to add the number of avatars to the team. */
    public boolean canAddAvatarsToTeam(TeamInfo team, int avatars) {
        return team.size() + avatars <= this.getMaxTeamSize();
    }

    /** Returns true if there is space to add to the team. */
    public boolean canAddAvatarToTeam(TeamInfo team) {
        return this.canAddAvatarsToTeam(team, 1);
    }

    /**
     * Returns true if there is space to add the number of avatars to the current team. If the current
     * team is temporary, returns false.
     */
    public boolean canAddAvatarsToCurrentTeam(int avatars) {
        if (this.useTemporarilyTeamIndex != -1) {
            return false;
        }
        return this.canAddAvatarsToTeam(this.getCurrentTeamInfo(), avatars);
    }

    /**
     * Returns true if there is space to add to the current team. If the current team is temporary,
     * returns false.
     */
    public boolean canAddAvatarToCurrentTeam() {
        return this.canAddAvatarsToCurrentTeam(1);
    }

    /**
     * Try to add the collection of avatars to the team. Returns true if all were successfully added.
     * If some can not be added, returns false and does not add any.
     */
    public boolean addAvatarsToTeam(TeamInfo team, Collection<Avatar> avatars) {
        if (!this.canAddAvatarsToTeam(team, avatars.size())) {
            return false;
        }

        // Convert avatars into a collection of avatar IDs, then add
        team.getAvatars().addAll(avatars.stream().map(a -> a.getAvatarId()).toList());

        // Update team
        if (this.getPlayer().isInMultiplayer()) {
            if (team.equals(this.getMpTeam())) {
                // MP team Packet
                this.updateTeamEntities(new PacketChangeMpTeamAvatarRsp(this.getPlayer(), team));
            }
        } else {
            // SP team update packet
            this.getPlayer().sendPacket(new PacketAvatarTeamUpdateNotify(this.getPlayer()));

            int teamId = this.getTeamId(team);
            if (teamId != -1) {
                // This is one of the player's teams
                // Update entites
                if (teamId == this.getCurrentTeamId()) {
                    this.updateTeamEntities(new PacketSetUpAvatarTeamRsp(this.getPlayer(), teamId, team));
                } else {
                    this.getPlayer().sendPacket(new PacketSetUpAvatarTeamRsp(this.getPlayer(), teamId, team));
                }
            }
        }

        return true;
    }

    /** Try to add an avatar to a team. Returns true if successful. */
    public boolean addAvatarToTeam(TeamInfo team, Avatar avatar) {
        return this.addAvatarsToTeam(team, Collections.singleton(avatar));
    }

    /**
     * Try to add the collection of avatars to the current team. Will not modify a temporary team.
     * Returns true if all were successfully added. If some can not be added, returns false and does
     * not add any.
     */
    public boolean addAvatarsToCurrentTeam(Collection<Avatar> avatars) {
        if (this.useTemporarilyTeamIndex != -1) {
            return false;
        }
        return this.addAvatarsToTeam(this.getCurrentTeamInfo(), avatars);
    }

    /**
     * Try to add an avatar to the current team. Will not modify a temporary team. Returns true if
     * successful.
     */
    public boolean addAvatarToCurrentTeam(Avatar avatar) {
        return this.addAvatarsToCurrentTeam(Collections.singleton(avatar));
    }

    private void updateTeamResonances() {
        this.getTeamResonances().clear();
        this.getTeamResonancesConfig().clear();
        // Official resonances require a full party
        if (this.avatars.size() < 4) return;

        // TODO: make this actually read from TeamResonanceExcelConfigData.json for the real resonances
        // and conditions
        // Currently we just hardcode these conditions, but this won't work for modded resources or
        // future changes
        var elementCounts = new Object2IntOpenHashMap<ElementType>();
        this.getActiveTeam().stream()
                .map(EntityAvatar::getAvatar)
                .filter(Objects::nonNull)
                .map(Avatar::getSkillDepot)
                .filter(Objects::nonNull)
                .map(AvatarSkillDepotData::getElementType)
                .filter(Objects::nonNull)
                .forEach(elementType -> elementCounts.addTo(elementType, 1));

        // Dual element resonances
        elementCounts.object2IntEntrySet().stream()
                .filter(e -> e.getIntValue() >= 2)
                .map(e -> e.getKey())
                .filter(elementType -> elementType.getTeamResonanceId() != 0)
                .forEach(
                        elementType -> {
                            this.teamResonances.add(elementType.getTeamResonanceId());
                            this.teamResonancesConfig.add(elementType.getConfigHash());
                        });

        // Four element resonance
        if (elementCounts.size() >= 4) {
            this.teamResonances.add(ElementType.Default.getTeamResonanceId());
            this.teamResonancesConfig.add(ElementType.Default.getConfigHash());
        }
    }

    /** Updates all properties of the active team. */
    public void updateTeamProperties() {
        this.updateTeamResonances(); // Update team resonances.
        this.getWorld()
                .broadcastPacket(
                        new PacketSceneTeamUpdateNotify(
                                this.getPlayer())); // Notify the all players in the world.

        // Skill charges packet - Yes, this is official server behavior as of 2.6.0
        this.getActiveTeam().stream()
                .map(EntityAvatar::getAvatar)
                .forEach(Avatar::sendSkillExtraChargeMap);
    }

    public void updateTeamEntities(BasePacket responsePacket) {
        // Sanity check - Should never happen
        if (this.getCurrentTeamInfo().getAvatars().size() <= 0) {
            return;
        }

        // If current team has changed
        var currentEntity = this.getCurrentAvatarEntity();
        var existingAvatars = new Int2ObjectOpenHashMap<EntityAvatar>();
        var prevSelectedAvatarIndex = -1;

        for (EntityAvatar entity : this.getActiveTeam()) {
            existingAvatars.put(entity.getAvatar().getAvatarId(), entity);
        }

        // Clear active team entity list
        this.getActiveTeam().clear();

        // Add back entities into team
        for (int i = 0; i < this.getCurrentTeamInfo().getAvatars().size(); i++) {
            var avatarId = (int) this.getCurrentTeamInfo().getAvatars().get(i);
            EntityAvatar entity;
            if (existingAvatars.containsKey(avatarId)) {
                entity = existingAvatars.get(avatarId);
                existingAvatars.remove(avatarId);
                if (entity == currentEntity) {
                    prevSelectedAvatarIndex = i;
                }
            } else {
                var player = this.getPlayer();
                entity =
                        EntityCreationEvent.call(
                                EntityAvatar.class,
                                new Class<?>[] {Scene.class, Avatar.class},
                                new Object[] {player.getScene(), player.getAvatars().getAvatarById(avatarId)});
            }

            this.getActiveTeam().add(entity);
        }

        // Unload removed entities
        for (var entity : existingAvatars.values()) {
            this.getPlayer().getScene().removeEntity(entity);
            entity.getAvatar().save();
        }

        // Set new selected character index
        if (prevSelectedAvatarIndex == -1) {
            // Previous selected avatar is not in the same spot, we will select the current one in the
            // prev slot
            prevSelectedAvatarIndex =
                    Math.min(this.currentCharacterIndex, this.getActiveTeam().size() - 1);
        }
        this.currentCharacterIndex = prevSelectedAvatarIndex;

        // Update properties.
        // Notify player.
        this.updateTeamProperties();

        // Send response packet.
        if (responsePacket != null) {
            this.getPlayer().sendPacket(responsePacket);
        }

        // Check if character changed
        var newAvatarEntity = this.getCurrentAvatarEntity();
        if (currentEntity != null && newAvatarEntity != null && currentEntity != newAvatarEntity) {
            // Call PlayerSwitchAvatarEvent.
            var event =
                    new PlayerSwitchAvatarEvent(
                            this.getPlayer(), currentEntity.getAvatar(), newAvatarEntity.getAvatar());
            if (!event.call()) return;

            // Remove and Add
            this.getPlayer().getScene().replaceEntity(currentEntity, newAvatarEntity);
        }
    }

    public synchronized void setupAvatarTeam(int teamId, List<Long> list) {
        // Sanity checks
        if (list.isEmpty()
                || list.size() > this.getMaxTeamSize()
                || this.getPlayer().isInMultiplayer()) {
            return;
        }

        // Get team
        TeamInfo teamInfo = this.getTeams().get(teamId);
        if (teamInfo == null) {
            return;
        }

        // Set team data
        LinkedHashSet<Avatar> newTeam = new LinkedHashSet<>();
        for (Long aLong : list) {
            Avatar avatar = this.getPlayer().getAvatars().getAvatarByGuid(aLong);
            if (avatar == null || newTeam.contains(avatar)) {
                // Should never happen
                return;
            }
            newTeam.add(avatar);
        }

        // Clear current team info and add avatars from our new team
        teamInfo.getAvatars().clear();
        this.addAvatarsToTeam(teamInfo, newTeam);
    }

    public void setupMpTeam(List<Long> list) {
        // Sanity checks
        if (list.size() == 0
                || list.size() > this.getMaxTeamSize()
                || !this.getPlayer().isInMultiplayer()) {
            return;
        }

        TeamInfo teamInfo = this.getMpTeam();

        // Set team data
        LinkedHashSet<Avatar> newTeam = new LinkedHashSet<>();
        for (Long aLong : list) {
            Avatar avatar = this.getPlayer().getAvatars().getAvatarByGuid(aLong);
            if (avatar == null || newTeam.contains(avatar)) {
                // Should never happen
                return;
            }
            newTeam.add(avatar);
        }

        // Clear current team info and add avatars from our new team
        teamInfo.getAvatars().clear();
        this.addAvatarsToTeam(teamInfo, newTeam);
    }

    /**
     * Setup avatars for a trial avatar team.
     *
     * @param save Should the original team be saved?
     */
    public void setupTrialAvatars(boolean save) {
        this.setPreviousIndex(this.getCurrentCharacterIndex());

        if (save) {
            var originalTeam = this.getCurrentTeamInfo();
            this.getTrialAvatarTeam().copyFrom(originalTeam);
        } else this.getActiveTeam().clear();

        this.usingTrialTeam = true;
    }

    /**
     * Displays the trial avatars.
     *
     * @param newCharacterIndex The avatar to equip.
     */
    public void trialAvatarTeamPostUpdate(int newCharacterIndex) {
        this.setCurrentCharacterIndex(Math.min(newCharacterIndex, this.getActiveTeam().size() - 1));

        this.updateTeamProperties();
        if (this.getPlayer().getScene() != null)
            this.getPlayer().getScene().addEntity(this.getCurrentAvatarEntity());
    }

    /**
     * Adds an avatar to the trial team.
     *
     * @param trialAvatar The avatar to add.
     */
    public void addAvatarToTrialTeam(Avatar trialAvatar) {
        // Remove the existing team's avatars.
        this.getActiveTeam()
                .forEach(
                        x ->
                                this.getPlayer()
                                        .getScene()
                                        .removeEntity(x, VisionTypeOuterClass.VisionType.VISION_TYPE_REMOVE));
        // Remove the existing avatar from the teams if it exists.
        this.getActiveTeam().removeIf(x -> x.getAvatar().getAvatarId() == trialAvatar.getAvatarId());
        this.getCurrentTeamInfo().getAvatars().removeIf(x -> x == trialAvatar.getAvatarId());
        // Add the avatar to the teams.
        this.getActiveTeam()
                .add(
                        EntityCreationEvent.call(
                                EntityAvatar.class,
                                new Class<?>[] {Scene.class, Avatar.class},
                                new Object[] {player.getScene(), trialAvatar}));
        this.getCurrentTeamInfo().addAvatar(trialAvatar);
        this.getTrialAvatars().put(trialAvatar.getAvatarId(), trialAvatar);
    }

    /**
     * Get the GUID of a trial avatar.
     *
     * @param trialAvatarId The avatar ID.
     * @return The GUID of the avatar.
     */
    public long getTrialAvatarGuid(int trialAvatarId) {
        return this.getTrialAvatars().values().stream()
                .filter(avatar -> avatar.getTrialAvatarId() == trialAvatarId)
                .map(Avatar::getGuid)
                .findFirst()
                .orElse(0L);
    }

    /** Rollback changes from using a trial avatar team. */
    public void unsetTrialAvatarTeam() {
        // Get the previous index.
        var index = this.getPreviousIndex();
        if (index < 0) index = 0;

        // Remove the trial avatars.
        this.trialAvatarTeamPostUpdate(index);
        // Reset the index.
        this.setPreviousIndex(-1);
    }

    /** Removes all avatars from the trial avatar team. */
    public void removeTrialAvatarTeam() {
        this.removeTrialAvatarTeam(
                this.getActiveTeam().stream().map(avatar -> avatar.getAvatar().getAvatarId()).toList());
    }

    /**
     * Removes one avatar from the trial avatar team.
     *
     * @param avatarId The avatar ID to remove.
     */
    public void removeTrialAvatarTeam(int avatarId) {
        this.removeTrialAvatarTeam(List.of(avatarId));
    }

    /**
     * Removes a collection of avatars from the trial avatar team.
     *
     * @param trialAvatarIds The avatar IDs to remove.
     */
    public void removeTrialAvatarTeam(List<Integer> trialAvatarIds) {
        var isTeam = trialAvatarIds.size() == this.getActiveTeam().size();

        var player = this.getPlayer();
        var scene = player.getScene();

        // Disable the trial team.
        this.usingTrialTeam = false;
        this.trialAvatarTeam = new TeamInfo();

        // Remove the avatars from the team.
        this.getActiveTeam()
                .forEach(
                        avatarEntity ->
                                scene.removeEntity(
                                        avatarEntity, VisionTypeOuterClass.VisionType.VISION_TYPE_REMOVE));

        if (isTeam) {
            this.getActiveTeam().clear();
            this.getTrialAvatars().clear();
        } else {
            trialAvatarIds.forEach(
                    trialAvatarId -> {
                        this.getActiveTeam().removeIf(x -> x.getAvatar().getTrialAvatarId() == trialAvatarId);
                        this.getTrialAvatars().values().removeIf(x -> x.getTrialAvatarId() == trialAvatarId);
                    });
        }

        // Re-add the avatars to the team.
        if (isTeam) {
            // Restores all avatars from the player's avatar storage.
            this.getCurrentTeamInfo()
                    .getAvatars()
                    .forEach(
                            avatarId ->
                                    this.getActiveTeam()
                                            .add(
                                                    EntityCreationEvent.call(
                                                            EntityAvatar.class,
                                                            new Class<?>[] {Scene.class, Avatar.class},
                                                            new Object[] {scene, player.getAvatars().getAvatarById(avatarId)})));
        } else {
            // Restores all avatars from the player's avatar storage.
            // If the avatar is already in the team, it will not be added.
            var avatars = this.getCurrentTeamInfo().getAvatars();
            for (var index = 0; index < avatars.size() - 1; index++) {
                var avatar = avatars.get(index);
                if (this.getActiveTeam().stream()
                        .map(entity -> entity.getAvatar().getAvatarId())
                        .toList()
                        .contains(avatar)) continue;

                // Check if the player owns the avatar.
                var avatarData = player.getAvatars().getAvatarById(avatar);
                if (avatarData == null) continue;

                this.getActiveTeam()
                        .add(
                                index,
                                EntityCreationEvent.call(
                                        EntityAvatar.class,
                                        new Class<?>[] {Scene.class, Avatar.class},
                                        new Object[] {scene, avatarData}));
            }
        }

        this.unsetTrialAvatarTeam();
    }

    public void setupTemporaryTeam(List<List<Long>> guidList) {
        this.temporaryTeam =
                guidList.stream()
                        .map(
                                list -> {
                                    // Sanity checks
                                    if (list.size() == 0 || list.size() > this.getMaxTeamSize()) {
                                        return null;
                                    }

                                    // Set team data
                                    LinkedHashSet<Avatar> newTeam = new LinkedHashSet<>();
                                    for (Long aLong : list) {
                                        Avatar avatar = this.getPlayer().getAvatars().getAvatarByGuid(aLong);
                                        if (avatar == null || newTeam.contains(avatar)) {
                                            // Should never happen
                                            return null;
                                        }
                                        newTeam.add(avatar);
                                    }

                                    // convert to avatar ids
                                    return newTeam.stream().map(Avatar::getAvatarId).toList();
                                })
                        .filter(Objects::nonNull)
                        .map(TeamInfo::new)
                        .toList();
    }

    public void useTemporaryTeam(int index) {
        this.useTemporarilyTeamIndex = index;
        this.updateTeamEntities(null);
    }

    public void cleanTemporaryTeam() {
        // check if using temporary team
        if (useTemporarilyTeamIndex < 0) {
            return;
        }

        this.useTemporarilyTeamIndex = -1;
        this.temporaryTeam = null;
        this.updateTeamEntities(null);
    }

    public synchronized void setCurrentTeam(int teamId) {
        //
        if (this.getPlayer().isInMultiplayer()) {
            return;
        }

        // Get team
        TeamInfo teamInfo = this.getTeams().get(teamId);
        if (teamInfo == null || teamInfo.getAvatars().size() == 0) {
            return;
        }

        // Set
        this.setCurrentTeamId(teamId);
        this.updateTeamEntities(new PacketChooseCurAvatarTeamRsp(teamId));
    }

    public synchronized void setTeamName(int teamId, String teamName) {
        // Get team
        TeamInfo teamInfo = this.getTeams().get(teamId);
        if (teamInfo == null) {
            return;
        }

        teamInfo.setName(teamName);

        // Packet
        this.getPlayer().sendPacket(new PacketChangeTeamNameRsp(teamId, teamName));
    }

    /**
     * Swaps the current avatar in the scene.
     *
     * @param guid The GUID of the avatar to swap to.
     */
    public synchronized void changeAvatar(long guid) {
        EntityAvatar oldEntity = this.getCurrentAvatarEntity();
        if (oldEntity == null || guid == oldEntity.getAvatar().getGuid()) {
            return;
        }

        EntityAvatar newEntity = null;
        int index = -1;
        for (int i = 0; i < this.getActiveTeam().size(); i++) {
            if (guid == this.getActiveTeam().get(i).getAvatar().getGuid()) {
                index = i;
                newEntity = this.getActiveTeam().get(i);
            }
        }

        if (index < 0 || newEntity == oldEntity) {
            return;
        }

        // Call PlayerSwitchAvatarEvent.
        var event =
                new PlayerSwitchAvatarEvent(this.getPlayer(), oldEntity.getAvatar(), newEntity.getAvatar());
        if (!event.call()) return;

        newEntity = event.getNewAvatarEntity();

        // Set index
        this.setCurrentCharacterIndex(index);

        // Old entity motion state
        oldEntity.setMotionState(MotionState.MOTION_STATE_STANDBY);

        // Remove and Add
        this.getPlayer().getScene().replaceEntity(oldEntity, newEntity);
        this.getPlayer().sendPacket(new PacketChangeAvatarRsp(guid));
    }

    /**
     * Applies 10% of the avatar's max HP as damage. This occurs when the avatar is killed by the
     * void.
     */
    public void applyVoidDamage() {
        this.getActiveTeam()
                .forEach(
                        entity -> {
                            entity.damage(entity.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP) * .1f);
                            player.sendPacket(new PacketAvatarLifeStateChangeNotify(entity.getAvatar()));
                        });
    }

    public void onAvatarDie(long dieGuid) {
        EntityAvatar deadAvatar = this.getCurrentAvatarEntity();
        if (deadAvatar == null || deadAvatar.getId() != dieGuid) return;

        PlayerDieType dieType = deadAvatar.getKilledType();
        int killedBy = deadAvatar.getKilledBy();

        if (dieType == PlayerDieType.PLAYER_DIE_TYPE_DRAWN) {
            // Died in water. Do not replace
            // The official server has skipped this notify and will just respawn the team immediately
            // after the animation.
            // TODO: Perhaps find a way to get vanilla experience?
            this.getPlayer().sendPacket(new PacketWorldPlayerDieNotify(dieType, killedBy));
        } else {
            // Replacement avatar
            EntityAvatar replacement = null;
            int replaceIndex = -1;

            for (int i = 0; i < this.getActiveTeam().size(); i++) {
                EntityAvatar entity = this.getActiveTeam().get(i);
                if (entity.isAlive()) {
                    replaceIndex = i;
                    replacement = entity;
                    break;
                }
            }

            if (replacement == null) {
                // No more living team members...
                this.getPlayer().sendPacket(new PacketWorldPlayerDieNotify(dieType, killedBy));
                // Invoke player team death event.
                PlayerTeamDeathEvent event =
                        new PlayerTeamDeathEvent(
                                this.getPlayer(), this.getActiveTeam().get(this.getCurrentCharacterIndex()));
                event.call();
            } else {
                // Set index and spawn replacement member
                this.setCurrentCharacterIndex(replaceIndex);
                this.getPlayer().getScene().addEntity(replacement);
            }
        }

        // Response packet
        this.getPlayer().sendPacket(new PacketAvatarDieAnimationEndRsp(deadAvatar.getId(), 0));
    }

    public boolean reviveAvatar(Avatar avatar) {
        for (EntityAvatar entity : this.getActiveTeam()) {
            if (entity.getAvatar() == avatar) {
                if (entity.isAlive()) {
                    return false;
                }

                entity.setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, 1f);
                // Satiation is reset when reviving an avatar
                player.getSatiationManager().removeSatiationDirectly(entity.getAvatar(), 15000);
                this.getPlayer()
                        .sendPacket(
                                new PacketAvatarFightPropUpdateNotify(
                                        entity.getAvatar(), FightProperty.FIGHT_PROP_CUR_HP));
                this.getPlayer().sendPacket(new PacketAvatarLifeStateChangeNotify(entity.getAvatar()));
                return true;
            }
        }

        return false;
    }

    public boolean healAvatar(Avatar avatar, int healRate, int healAmount) {
        for (EntityAvatar entity : this.getActiveTeam()) {
            if (entity.getAvatar() == avatar) {
                if (!entity.isAlive()) {
                    return false;
                }

                entity.setFightProperty(
                        FightProperty.FIGHT_PROP_CUR_HP,
                        (float)
                                Math.min(
                                        (entity.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP)
                                                + entity.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP)
                                                        * (float) healRate
                                                        / 100.0
                                                + (float) healAmount / 100.0),
                                        entity.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP)));
                this.getPlayer()
                        .sendPacket(
                                new PacketAvatarFightPropUpdateNotify(
                                        entity.getAvatar(), FightProperty.FIGHT_PROP_CUR_HP));
                this.getPlayer().sendPacket(new PacketAvatarLifeStateChangeNotify(entity.getAvatar()));
                return true;
            }
        }
        return false;
    }

    public void respawnTeam() {
        // Make sure all team members are dead
        // Drowning needs revive when there may be other team members still alive.
        //  for (EntityAvatar entity : getActiveTeam()) {
        //      if (entity.isAlive()) {
        //		     return;
        //		}
        //	}
        this.getPlayer()
                .getStaminaManager()
                .stopSustainedStaminaHandler(); // prevent drowning immediately after respawn

        // Revive all team members
        for (EntityAvatar entity : this.getActiveTeam()) {
            entity.setFightProperty(
                    FightProperty.FIGHT_PROP_CUR_HP,
                    entity.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP) * .4f);
            this.getPlayer().getSatiationManager().removeSatiationDirectly(entity.getAvatar(), 15000);
            this.getPlayer()
                    .sendPacket(
                            new PacketAvatarFightPropUpdateNotify(
                                    entity.getAvatar(), FightProperty.FIGHT_PROP_CUR_HP));
            this.getPlayer().sendPacket(new PacketAvatarLifeStateChangeNotify(entity.getAvatar()));
        }

        // Teleport player and set player position
        try {
            this.getPlayer()
                    .sendPacket(
                            new PacketPlayerEnterSceneNotify(
                                    this.getPlayer(),
                                    EnterType.ENTER_TYPE_SELF,
                                    EnterReason.Revival,
                                    this.getPlayer().getSceneId(),
                                    this.getRespawnPosition()));
            this.getPlayer().getPosition().set(this.getRespawnPosition());
        } catch (Exception ignored) {
            this.getPlayer()
                    .sendPacket(
                            new PacketPlayerEnterSceneNotify(
                                    this.getPlayer(),
                                    EnterType.ENTER_TYPE_SELF,
                                    EnterReason.Revival,
                                    3,
                                    GameConstants.START_POSITION));
            this.getPlayer()
                    .getPosition()
                    .set(GameConstants.START_POSITION); // If something goes wrong, the resurrection is here
        }

        // Packets
        this.getPlayer().sendPacket(new BasePacket(PacketOpcodes.WorldPlayerReviveRsp));
    }

    public Position getRespawnPosition() {
        var deathPos = this.getPlayer().getPosition();
        int sceneId = this.getPlayer().getSceneId();

        // Get the closest trans point to where the player died.
        var respawnPoint =
                this.getPlayer().getUnlockedScenePoints(sceneId).stream()
                        .map(pointId -> GameData.getScenePointEntryById(sceneId, pointId))
                        .filter(point -> point.getPointData().getType().equals("SceneTransPoint"))
                        .min(
                                (Comparator.comparingDouble(
                                        pos -> Utils.getDist(pos.getPointData().getTranPos(), deathPos))));

        return respawnPoint.get().getPointData().getTranPos();
    }

    public void saveAvatars() {
        // Save all avatars from active team
        for (EntityAvatar entity : this.getActiveTeam()) {
            entity.getAvatar().save();
        }
    }

    public void onPlayerLogin() { // Hack for now to fix resonances on login
        this.updateTeamResonances();
    }

    public synchronized void addNewCustomTeam() {
        // Sanity check - max number of teams.
        if (this.teams.size() == GameConstants.MAX_TEAMS) {
            player.sendPacket(new PacketAddBackupAvatarTeamRsp(Retcode.RET_FAIL));
            return;
        }

        // The id of the new custom team is the lowest id in [5,MAX_TEAMS] that is not yet taken.
        int id = -1;
        for (int i = 5; i <= GameConstants.MAX_TEAMS; i++) {
            if (!this.teams.containsKey(i)) {
                id = i;
                break;
            }
        }

        // Create the new team.
        this.teams.put(id, new TeamInfo());

        // Send packets.
        player.sendPacket(new PacketAvatarTeamAllDataNotify(player));
        player.sendPacket(new PacketAddBackupAvatarTeamRsp());
    }

    public synchronized void removeCustomTeam(int id) {
        // Check if the target id exists.
        if (!this.teams.containsKey(id)) {
            player.sendPacket(new PacketDelBackupAvatarTeamRsp(Retcode.RET_FAIL, id));
        }

        // Remove team.
        this.teams.remove(id);

        // Send packets.
        player.sendPacket(new PacketAvatarTeamAllDataNotify(player));
        player.sendPacket(new PacketDelBackupAvatarTeamRsp(id));
    }

    /**
     * Applies abilities for the currently selected team. These abilities are sourced from the scene.
     *
     * @param scene The scene with the abilities to apply.
     */
    public void applyAbilities(Scene scene) {
        try {
            var levelEntityConfig = scene.getSceneData().getLevelEntityConfig();
            var config = GameData.getConfigLevelEntityDataMap().get(levelEntityConfig);
            if (config == null) return;

            var avatars = this.getPlayer().getAvatars();
            var avatarIds = scene.getSceneData().getSpecifiedAvatarList();
            var specifiedAvatarList = this.getActiveTeam();

            if (avatarIds != null && avatarIds.size() > 0) {
                // certain scene could limit specific avatars' entry
                specifiedAvatarList.clear();
                for (int id : avatarIds) {
                    var avatar = avatars.getAvatarById(id);
                    if (avatar == null) continue;

                    specifiedAvatarList.add(
                            EntityCreationEvent.call(
                                    EntityAvatar.class,
                                    new Class<?>[] {Scene.class, Avatar.class},
                                    new Object[] {scene, avatar}));
                }
            }

            for (var entityAvatar : specifiedAvatarList) {
                var avatarData = entityAvatar.getAvatar().getAvatarData();
                if (avatarData == null) {
                    continue;
                }

                avatarData.buildEmbryo(); // Create avatar abilities.
                if (config.getAvatarAbilities() == null) {
                    continue; // continue and not break because has to rebuild ability for the next avatar if
                    // any
                }

                for (var abilities : config.getAvatarAbilities()) {
                    avatarData.getAbilities().add(Utils.abilityHash(abilities.getAbilityName()));
                }
            }
        } catch (Exception e) {
            Grasscutter.getLogger()
                    .error(
                            "Error applying level entity config for scene {}", scene.getSceneData().getId(), e);
        }
    }

    public List<Integer> getTrialAvatarParam(int trialAvatarId) {
        if (GameData.getTrialAvatarCustomData()
                .isEmpty()) { // use default data if custom data not available
            if (GameData.getTrialAvatarDataMap().get(trialAvatarId) == null) return List.of();

            return GameData.getTrialAvatarDataMap().get(trialAvatarId).getTrialAvatarParamList();
        }
        // use custom data
        if (GameData.getTrialAvatarCustomData().get(trialAvatarId) == null) return List.of();

        val trialCustomParams =
                GameData.getTrialAvatarCustomData().get(trialAvatarId).getTrialAvatarParamList();
        return trialCustomParams.isEmpty()
                ? List.of()
                : Stream.of(trialCustomParams.get(0).split(";")).map(Integer::parseInt).toList();
    }

    /**
     * Adds a trial avatar to the player's team.
     *
     * @param avatarId The ID of the avatar.
     * @param questMainId The quest ID associated with the quest.
     * @param reason The reason for granting the avatar.
     * @return True if the avatar was added, false otherwise.
     */
    public boolean addTrialAvatar(int avatarId, int questMainId, GrantReason reason) {
        List<Integer> trialAvatarBasicParam = getTrialAvatarParam(avatarId);
        if (trialAvatarBasicParam.isEmpty()) return false;

        var avatar = new Avatar(trialAvatarBasicParam.get(0));
        if (avatar.getAvatarData() == null || !this.getPlayer().hasSentLoginPackets()) return false;

        avatar.setOwner(this.getPlayer());
        // Add trial weapons and relics.
        avatar.setTrialAvatarInfo(trialAvatarBasicParam.get(1), avatarId, reason, questMainId);
        avatar.equipTrialItems();
        // Re-calculate stats
        avatar.recalcStats();

        // Packet, mimic official server behaviour, add to player's bag but not saving to database.
        this.getPlayer().sendPacket(new PacketAvatarAddNotify(avatar, false));
        // Add to avatar to the temporary trial team.
        this.addAvatarToTrialTeam(avatar);
        return true;
    }

    /**
     * Adds a trial avatar to the player's team.
     *
     * @param avatarId The ID of the avatar.
     * @param questMainId The quest ID associated with the quest.
     */
    public void addTrialAvatar(int avatarId, int questMainId) {
        this.addTrialAvatars(List.of(avatarId), questMainId, true);

        // Packet, mimic official server behaviour, necessary to stop player from modifying team.
        this.getPlayer().sendPacket(new PacketAvatarTeamUpdateNotify(this.getPlayer()));
    }

    /**
     * Adds a collection of trial avatars to the player's team.
     *
     * @param avatarIds List of trial avatar IDs.
     */
    public void addTrialAvatars(List<Integer> avatarIds) {
        this.addTrialAvatars(avatarIds, 0, false);
    }

    /**
     * Adds a collection of trial avatars to the player's team.
     *
     * @param avatarIds List of trial avatar IDs.
     * @param save Whether to retain the currently equipped avatars.
     */
    public void addTrialAvatars(List<Integer> avatarIds, boolean save) {
        this.addTrialAvatars(avatarIds, 0, save);
    }

    /**
     * Adds a list of trial avatars to the player's team.
     *
     * @param trialAvatarIds List of trial avatar IDs.
     * @param questId The ID of the quest this trial team is associated with.
     * @param save Whether to retain the currently equipped avatars.
     */
    public void addTrialAvatars(List<Integer> trialAvatarIds, int questId, boolean save) {
        this.setupTrialAvatars(save); // Perform initial setup.

        // Add the avatars to the team.
        trialAvatarIds.forEach(
                trialAvatarId -> {
                    var result =
                            this.addTrialAvatar(
                                    trialAvatarId,
                                    questId,
                                    questId != 0
                                            ? GrantReason.GRANT_REASON_BY_QUEST
                                            : GrantReason.GRANT_REASON_BY_TRIAL_AVATAR_ACTIVITY);

                    if (!result) throw new RuntimeException("Unable to add trial avatar to team.");
                });

        // Update the team.
        this.trialAvatarTeamPostUpdate(questId != 0 ? this.getActiveTeam().size() - 1 : 0);
    }

    /** Removes all trial avatars from the player's team. */
    public void removeTrialAvatar() {
        this.removeTrialAvatar(
                this.getActiveTeam().stream()
                        .map(EntityAvatar::getAvatar)
                        .map(Avatar::getTrialAvatarId)
                        .toList());
    }

    /**
     * Removes a trial avatar from the player's team. Additionally, unlocks the ability to change the
     * team configuration.
     *
     * @param trialAvatarId The ID of the avatar.
     */
    public void removeTrialAvatar(int trialAvatarId) {
        this.removeTrialAvatar(List.of(trialAvatarId));
    }

    /**
     * Removes a collection of trial avatars from the player's team.
     *
     * @param trialAvatarIds List of trial avatar IDs.
     */
    public void removeTrialAvatar(List<Integer> trialAvatarIds) {
        // Check if the player is using a trial team.
        if (!this.isUsingTrialTeam()) return;

        this.getPlayer()
                .sendPacket(
                        new PacketAvatarDelNotify(
                                trialAvatarIds.stream().map(this::getTrialAvatarGuid).toList()));
        this.removeTrialAvatarTeam(trialAvatarIds);

        // Update the team.
        if (trialAvatarIds.size() == 1) this.getPlayer().sendPacket(new PacketAvatarTeamUpdateNotify());
    }
}
