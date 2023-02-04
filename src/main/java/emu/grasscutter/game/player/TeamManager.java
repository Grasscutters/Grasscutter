package emu.grasscutter.game.player;

import static emu.grasscutter.config.Configuration.*;

import java.util.*;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Transient;
import emu.grasscutter.GameConstants;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.AvatarSkillDepotData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.entity.EntityBaseGadget;
import emu.grasscutter.game.props.ElementType;
import emu.grasscutter.game.props.EnterReason;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.world.World;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.EnterTypeOuterClass.EnterType;
import emu.grasscutter.net.proto.MotionStateOuterClass.MotionState;
import emu.grasscutter.net.proto.PlayerDieTypeOuterClass.PlayerDieType;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;
import emu.grasscutter.server.event.player.PlayerTeamDeathEvent;
import emu.grasscutter.server.packet.send.PacketAddBackupAvatarTeamRsp;
import emu.grasscutter.server.packet.send.PacketAvatarDieAnimationEndRsp;
import emu.grasscutter.server.packet.send.PacketAvatarFightPropUpdateNotify;
import emu.grasscutter.server.packet.send.PacketAvatarLifeStateChangeNotify;
import emu.grasscutter.server.packet.send.PacketAvatarTeamAllDataNotify;
import emu.grasscutter.server.packet.send.PacketAvatarTeamUpdateNotify;
import emu.grasscutter.server.packet.send.PacketChangeAvatarRsp;
import emu.grasscutter.server.packet.send.PacketChangeMpTeamAvatarRsp;
import emu.grasscutter.server.packet.send.PacketChangeTeamNameRsp;
import emu.grasscutter.server.packet.send.PacketChooseCurAvatarTeamRsp;
import emu.grasscutter.server.packet.send.PacketDelBackupAvatarTeamRsp;
import emu.grasscutter.server.packet.send.PacketPlayerEnterSceneNotify;
import emu.grasscutter.server.packet.send.PacketSceneTeamUpdateNotify;
import emu.grasscutter.server.packet.send.PacketSetUpAvatarTeamRsp;
import emu.grasscutter.server.packet.send.PacketWorldPlayerDieNotify;
import emu.grasscutter.utils.Position;
import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import lombok.Getter;
import lombok.Setter;

@Entity
public class TeamManager extends BasePlayerDataManager {
    // This needs to be a LinkedHashMap to guarantee insertion order.
    @Getter private LinkedHashMap<Integer, TeamInfo> teams;
    private int currentTeamIndex;
    @Getter @Setter private int currentCharacterIndex;

    @Transient @Getter @Setter private TeamInfo mpTeam;
    @Transient @Getter @Setter private int entityId;
    @Transient private final List<EntityAvatar> avatars;
    @Transient @Getter private final Set<EntityBaseGadget> gadgets;
    @Transient @Getter private final IntSet teamResonances;
    @Transient @Getter private final IntSet teamResonancesConfig;

    @Transient private int useTemporarilyTeamIndex = -1;
    @Transient private List<TeamInfo> temporaryTeam; // Temporary Team for tower

    public TeamManager() {
        this.mpTeam = new TeamInfo();
        this.avatars = new ArrayList<>();
        this.gadgets = new HashSet<>();
        this.teamResonances = new IntOpenHashSet();
        this.teamResonancesConfig = new IntOpenHashSet();
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

    public World getWorld() {
        return this.getPlayer().getWorld();
    }

    /**
     * Search through all teams and if the team matches, return that index.
     * Otherwise, return -1.
     * No match could mean that the team does not currently belong to the player.
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
        if (useTemporarilyTeamIndex >= 0 &&
            useTemporarilyTeamIndex < temporaryTeam.size()) {
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

    public EntityAvatar getCurrentAvatarEntity() {
        return this.getActiveTeam().get(currentCharacterIndex);
    }

    public boolean isSpawned() {
        return this.getPlayer().getScene() != null && this.getPlayer().getScene().getEntities().containsKey(this.getCurrentAvatarEntity().getId());
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

    /**
     * Returns true if there is space to add the number of avatars to the team.
     */
    public boolean canAddAvatarsToTeam(TeamInfo team, int avatars) {
        return team.size() + avatars <= this.getMaxTeamSize();
    }

    /**
     * Returns true if there is space to add to the team.
     */
    public boolean canAddAvatarToTeam(TeamInfo team) {
        return this.canAddAvatarsToTeam(team, 1);
    }

    /**
     * Returns true if there is space to add the number of avatars to the current team.
     * If the current team is temporary, returns false.
     */
    public boolean canAddAvatarsToCurrentTeam(int avatars) {
        if (this.useTemporarilyTeamIndex != -1) {
            return false;
        }
        return this.canAddAvatarsToTeam(this.getCurrentTeamInfo(), avatars);
    }

    /**
     * Returns true if there is space to add to the current team.
     * If the current team is temporary, returns false.
     */
    public boolean canAddAvatarToCurrentTeam() {
        return this.canAddAvatarsToCurrentTeam(1);
    }

    /**
     * Try to add the collection of avatars to the team.
     * Returns true if all were successfully added.
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

    /**
     * Try to add an avatar to a team.
     * Returns true if successful.
     */
    public boolean addAvatarToTeam(TeamInfo team, Avatar avatar) {
        return this.addAvatarsToTeam(team, Collections.singleton(avatar));
    }

    /**
     * Try to add the collection of avatars to the current team.
     * Will not modify a temporary team.
     * Returns true if all were successfully added.
     * If some can not be added, returns false and does not add any.
     */
    public boolean addAvatarsToCurrentTeam(Collection<Avatar> avatars) {
        if (this.useTemporarilyTeamIndex != -1) {
            return false;
        }
        return this.addAvatarsToTeam(this.getCurrentTeamInfo(), avatars);
    }

    /**
     * Try to add an avatar to the current team.
     * Will not modify a temporary team.
     * Returns true if successful.
     */
    public boolean addAvatarToCurrentTeam(Avatar avatar) {
        return this.addAvatarsToCurrentTeam(Collections.singleton(avatar));
    }

    private void updateTeamResonances() {
        this.getTeamResonances().clear();
        this.getTeamResonancesConfig().clear();
        // Official resonances require a full party
        if (this.avatars.size() < 4) return;

        // TODO: make this actually read from TeamResonanceExcelConfigData.json for the real resonances and conditions
        // Currently we just hardcode these conditions, but this won't work for modded resources or future changes
        var elementCounts = new Object2IntOpenHashMap<ElementType>();
        this.getActiveTeam().stream()
            .map(EntityAvatar::getAvatar).filter(Objects::nonNull)
            .map(Avatar::getSkillDepot).filter(Objects::nonNull)
            .map(AvatarSkillDepotData::getElementType).filter(Objects::nonNull)
            .forEach(elementType -> elementCounts.addTo(elementType, 1));

        // Dual element resonances
        elementCounts.object2IntEntrySet().stream()
            .filter(e -> e.getIntValue() >= 2)
            .map(e -> e.getKey())
            .filter(elementType -> elementType.getTeamResonanceId() != 0)
            .forEach(elementType -> {
                this.teamResonances.add(elementType.getTeamResonanceId());
                this.teamResonancesConfig.add(elementType.getConfigHash());
            });

        // Four element resonance
        if (elementCounts.size() >= 4) {
            this.teamResonances.add(ElementType.Default.getTeamResonanceId());
            this.teamResonancesConfig.add(ElementType.Default.getConfigHash());
        }
    }

    public void updateTeamEntities(BasePacket responsePacket) {
        // Sanity check - Should never happen
        if (this.getCurrentTeamInfo().getAvatars().size() <= 0) {
            return;
        }

        // If current team has changed
        EntityAvatar currentEntity = this.getCurrentAvatarEntity();
        Int2ObjectMap<EntityAvatar> existingAvatars = new Int2ObjectOpenHashMap<>();
        int prevSelectedAvatarIndex = -1;

        for (EntityAvatar entity : this.getActiveTeam()) {
            existingAvatars.put(entity.getAvatar().getAvatarId(), entity);
        }

        // Clear active team entity list
        this.getActiveTeam().clear();

        // Add back entities into team
        for (int i = 0; i < this.getCurrentTeamInfo().getAvatars().size(); i++) {
            int avatarId = this.getCurrentTeamInfo().getAvatars().get(i);
            EntityAvatar entity;

            if (existingAvatars.containsKey(avatarId)) {
                entity = existingAvatars.get(avatarId);
                existingAvatars.remove(avatarId);
                if (entity == currentEntity) {
                    prevSelectedAvatarIndex = i;
                }
            } else {
                entity = new EntityAvatar(this.getPlayer().getScene(), this.getPlayer().getAvatars().getAvatarById(avatarId));
            }

            this.getActiveTeam().add(entity);
        }

        // Unload removed entities
        for (EntityAvatar entity : existingAvatars.values()) {
            this.getPlayer().getScene().removeEntity(entity);
            entity.getAvatar().save();
        }

        // Set new selected character index
        if (prevSelectedAvatarIndex == -1) {
            // Previous selected avatar is not in the same spot, we will select the current one in the prev slot
            prevSelectedAvatarIndex = Math.min(this.currentCharacterIndex, this.getActiveTeam().size() - 1);
        }
        this.currentCharacterIndex = prevSelectedAvatarIndex;

        // Update team resonances
        this.updateTeamResonances();

        // Packets
        this.getPlayer().getWorld().broadcastPacket(new PacketSceneTeamUpdateNotify(this.getPlayer()));

        // Skill charges packet - Yes, this is official server behavior as of 2.6.0
        this.getActiveTeam().stream().map(EntityAvatar::getAvatar).forEach(Avatar::sendSkillExtraChargeMap);

        // Run callback
        if (responsePacket != null) {
            this.getPlayer().sendPacket(responsePacket);
        }

        // Check if character changed
        if (currentEntity != this.getCurrentAvatarEntity()) {
            // Remove and Add
            this.getPlayer().getScene().replaceEntity(currentEntity, this.getCurrentAvatarEntity());
        }
    }

    public synchronized void setupAvatarTeam(int teamId, List<Long> list) {
        // Sanity checks
        if (list.size() == 0 || list.size() > this.getMaxTeamSize() || this.getPlayer().isInMultiplayer()) {
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
        if (list.size() == 0 || list.size() > this.getMaxTeamSize() || !this.getPlayer().isInMultiplayer()) {
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

    public void setupTemporaryTeam(List<List<Long>> guidList) {
        this.temporaryTeam = guidList.stream().map(list -> {
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
                return newTeam.stream()
                    .map(Avatar::getAvatarId)
                    .toList();
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

    public synchronized void changeAvatar(long guid) {
        EntityAvatar oldEntity = this.getCurrentAvatarEntity();

        if (guid == oldEntity.getAvatar().getGuid()) {
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

        // Set index
        this.setCurrentCharacterIndex(index);

        // Old entity motion state
        oldEntity.setMotionState(MotionState.MOTION_STATE_STANDBY);

        // Remove and Add
        this.getPlayer().getScene().replaceEntity(oldEntity, newEntity);
        this.getPlayer().sendPacket(new PacketChangeAvatarRsp(guid));
    }

    public void onAvatarDie(long dieGuid) {
        EntityAvatar deadAvatar = this.getCurrentAvatarEntity();

        if (deadAvatar.isAlive() || deadAvatar.getId() != dieGuid) {
            return;
        }

        PlayerDieType dieType = deadAvatar.getKilledType();
        int killedBy = deadAvatar.getKilledBy();

        if (dieType == PlayerDieType.PLAYER_DIE_TYPE_DRAWN) {
            // Died in water. Do not replace
            // The official server has skipped this notify and will just respawn the team immediately after the animation.
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
                PlayerTeamDeathEvent event = new PlayerTeamDeathEvent(this.getPlayer(),
                    this.getActiveTeam().get(this.getCurrentCharacterIndex()));
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
                this.getPlayer().sendPacket(new PacketAvatarFightPropUpdateNotify(entity.getAvatar(), FightProperty.FIGHT_PROP_CUR_HP));
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
                    (float) Math.min(
                        (entity.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP) +
                            entity.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP) * (float) healRate / 100.0 +
                            (float) healAmount / 100.0),
                        entity.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP)
                    )
                );
                this.getPlayer().sendPacket(new PacketAvatarFightPropUpdateNotify(entity.getAvatar(), FightProperty.FIGHT_PROP_CUR_HP));
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
        player.getStaminaManager().stopSustainedStaminaHandler(); // prevent drowning immediately after respawn

        // Revive all team members
        for (EntityAvatar entity : this.getActiveTeam()) {
            entity.setFightProperty(
                FightProperty.FIGHT_PROP_CUR_HP,
                entity.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP) * .4f
            );
            this.getPlayer().sendPacket(new PacketAvatarFightPropUpdateNotify(entity.getAvatar(), FightProperty.FIGHT_PROP_CUR_HP));
            this.getPlayer().sendPacket(new PacketAvatarLifeStateChangeNotify(entity.getAvatar()));
        }

        // Teleport player and set player position
        try {
            this.getPlayer().sendPacket(new PacketPlayerEnterSceneNotify(this.getPlayer(), EnterType.ENTER_TYPE_SELF, EnterReason.Revival, player.getSceneId(), getRespawnPosition()));
            player.getPosition().set(getRespawnPosition());
        }catch (Exception e) {
            this.getPlayer().sendPacket(new PacketPlayerEnterSceneNotify(this.getPlayer(), EnterType.ENTER_TYPE_SELF, EnterReason.Revival, 3, GameConstants.START_POSITION));
            player.getPosition().set(GameConstants.START_POSITION);  // If something goes wrong, the resurrection is here
        }

        // Packets
        this.getPlayer().sendPacket(new BasePacket(PacketOpcodes.WorldPlayerReviveRsp));
    }
    public Position getRespawnPosition() {
        var deathPos = this.getPlayer().getPosition();
        int sceneId = this.getPlayer().getSceneId();

        // Get the closest trans point to where the player died.
        var respawnPoint = this.getPlayer().getUnlockedScenePoints(sceneId).stream()
            .map(pointId -> GameData.getScenePointEntryById(sceneId, pointId))
            .filter(point -> point.getPointData().getType().equals("SceneTransPoint"))
            .min((Comparator.comparingDouble(pos -> Utils.getDist(pos.getPointData().getTranPos(), deathPos))));

        return respawnPoint.get().getPointData().getTranPos();
    }
    public void saveAvatars() {
        // Save all avatars from active team
        for (EntityAvatar entity : this.getActiveTeam()) {
            entity.getAvatar().save();
        }
    }

    public void onPlayerLogin() {  // Hack for now to fix resonances on login
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
            if (!this.teams.keySet().contains(i)) {
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
}
