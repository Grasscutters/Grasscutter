package emu.grasscutter.game.player;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Transient;
import emu.grasscutter.GameConstants;
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
import emu.grasscutter.server.packet.send.*;
import it.unimi.dsi.fastutil.ints.*;

import java.util.*;

import static emu.grasscutter.Configuration.GAME_OPTIONS;

@Entity
public class TeamManager {
    @Transient
    private Player player;

    private Map<Integer, TeamInfo> teams;
    private int currentTeamIndex;
    private int currentCharacterIndex;

    @Transient
    private TeamInfo mpTeam;
    @Transient
    private int entityId;
    @Transient
    private final List<EntityAvatar> avatars;
    @Transient
    private final Set<EntityBaseGadget> gadgets;
    @Transient
    private final IntSet teamResonances;
    @Transient
    private final IntSet teamResonancesConfig;

    @Transient
    private int useTemporarilyTeamIndex = -1;
    /**
     * Temporary Team for tower
     */
    @Transient
    private List<TeamInfo> temporaryTeam;

    public TeamManager() {
        this.mpTeam = new TeamInfo();
        this.avatars = new ArrayList<>();
        this.gadgets = new HashSet<>();
        this.teamResonances = new IntOpenHashSet();
        this.teamResonancesConfig = new IntOpenHashSet();
    }

    public TeamManager(Player player) {
        this();
        this.player = player;

        this.teams = new HashMap<>();
        this.currentTeamIndex = 1;
        for (int i = 1; i <= GameConstants.MAX_TEAMS; i++) {
            this.teams.put(i, new TeamInfo());
        }
    }

    public Player getPlayer() {
        return this.player;
    }

    public World getWorld() {
        return this.player.getWorld();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Map<Integer, TeamInfo> getTeams() {
        return this.teams;
    }

    public TeamInfo getMpTeam() {
        return this.mpTeam;
    }

    public void setMpTeam(TeamInfo mpTeam) {
        this.mpTeam = mpTeam;
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
        return this.currentTeamIndex;
    }

    private void setCurrentTeamId(int currentTeamIndex) {
        this.currentTeamIndex = currentTeamIndex;
    }

    public int getCurrentCharacterIndex() {
        return this.currentCharacterIndex;
    }

    public void setCurrentCharacterIndex(int currentCharacterIndex) {
        this.currentCharacterIndex = currentCharacterIndex;
    }

    public long getCurrentCharacterGuid() {
        return this.getCurrentAvatarEntity().getAvatar().getGuid();
    }

    public TeamInfo getCurrentTeamInfo() {
        if (this.useTemporarilyTeamIndex >= 0 &&
            this.useTemporarilyTeamIndex < this.temporaryTeam.size()) {
            return this.temporaryTeam.get(this.useTemporarilyTeamIndex);
        }
        if (this.getPlayer().isInMultiplayer()) {
            return this.getMpTeam();
        }
        return this.getTeams().get(this.currentTeamIndex);
    }

    public TeamInfo getCurrentSinglePlayerTeamInfo() {
        return this.getTeams().get(this.currentTeamIndex);
    }

    public int getEntityId() {
        return this.entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public Set<EntityBaseGadget> getGadgets() {
        return this.gadgets;
    }

    public IntSet getTeamResonances() {
        return this.teamResonances;
    }

    public IntSet getTeamResonancesConfig() {
        return this.teamResonancesConfig;
    }

    public List<EntityAvatar> getActiveTeam() {
        return this.avatars;
    }

    public EntityAvatar getCurrentAvatarEntity() {
        return this.getActiveTeam().get(this.currentCharacterIndex);
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
        Int2IntOpenHashMap map = new Int2IntOpenHashMap();

        this.getTeamResonances().clear();
        this.getTeamResonancesConfig().clear();

        for (EntityAvatar entity : this.getActiveTeam()) {
            AvatarSkillDepotData skillData = entity.getAvatar().getAvatarData().getSkillDepot();
            if (skillData != null) {
                map.addTo(skillData.getElementType().getValue(), 1);
            }
        }

        for (Int2IntMap.Entry e : map.int2IntEntrySet()) {
            if (e.getIntValue() >= 2) {
                ElementType element = ElementType.getTypeByValue(e.getIntKey());
                if (element.getTeamResonanceId() != 0) {
                    this.getTeamResonances().add(element.getTeamResonanceId());
                    this.getTeamResonancesConfig().add(element.getConfigHash());
                }
            }
        }

        // No resonances
        if (this.getTeamResonances().size() == 0) {
            this.getTeamResonances().add(ElementType.Default.getTeamResonanceId());
            this.getTeamResonancesConfig().add(ElementType.Default.getTeamResonanceId());
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
        for (EntityAvatar entity : this.getActiveTeam()) {
            if (entity.getAvatar().getSkillExtraChargeMap().size() > 0) {
                this.getPlayer().sendPacket(new PacketAvatarSkillInfoNotify(entity.getAvatar()));
            }
        }

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
        if (this.useTemporarilyTeamIndex < 0) {
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

                entity.setFightProperty(
                    FightProperty.FIGHT_PROP_CUR_HP,
                    entity.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP) * .1f
                );
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
        this.player.getStaminaManager().stopSustainedStaminaHandler(); // prevent drowning immediately after respawn

        // Revive all team members
        for (EntityAvatar entity : this.getActiveTeam()) {
            entity.setFightProperty(
                FightProperty.FIGHT_PROP_CUR_HP,
                entity.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP) * .4f
            );
            this.getPlayer().sendPacket(new PacketAvatarFightPropUpdateNotify(entity.getAvatar(), FightProperty.FIGHT_PROP_CUR_HP));
            this.getPlayer().sendPacket(new PacketAvatarLifeStateChangeNotify(entity.getAvatar()));
        }

        // Teleport player
        this.getPlayer().sendPacket(new PacketPlayerEnterSceneNotify(this.getPlayer(), EnterType.ENTER_TYPE_SELF, EnterReason.Revival, 3, GameConstants.START_POSITION));

        // Set player position
        this.player.setSceneId(3);
        this.player.getPos().set(GameConstants.START_POSITION);

        // Packets
        this.getPlayer().sendPacket(new BasePacket(PacketOpcodes.WorldPlayerReviveRsp));
    }

    public void saveAvatars() {
        // Save all avatars from active team
        for (EntityAvatar entity : this.getActiveTeam()) {
            entity.getAvatar().save();
        }
    }
}
