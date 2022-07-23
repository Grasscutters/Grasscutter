package emu.grasscutter.game.player;

import static emu.grasscutter.config.Configuration.*;

import java.util.*;

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
import emu.grasscutter.server.packet.send.PacketAvatarDieAnimationEndRsp;
import emu.grasscutter.server.packet.send.PacketAvatarFightPropUpdateNotify;
import emu.grasscutter.server.packet.send.PacketAvatarLifeStateChangeNotify;
import emu.grasscutter.server.packet.send.PacketAvatarSkillInfoNotify;
import emu.grasscutter.server.packet.send.PacketAvatarTeamUpdateNotify;
import emu.grasscutter.server.packet.send.PacketChangeAvatarRsp;
import emu.grasscutter.server.packet.send.PacketChangeMpTeamAvatarRsp;
import emu.grasscutter.server.packet.send.PacketChangeTeamNameRsp;
import emu.grasscutter.server.packet.send.PacketChooseCurAvatarTeamRsp;
import emu.grasscutter.server.packet.send.PacketPlayerEnterSceneNotify;
import emu.grasscutter.server.packet.send.PacketSceneTeamUpdateNotify;
import emu.grasscutter.server.packet.send.PacketSetUpAvatarTeamRsp;
import emu.grasscutter.server.packet.send.PacketWorldPlayerDieNotify;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;

@Entity
public class TeamManager extends BasePlayerDataManager {
    private Map<Integer, TeamInfo> teams;
    private int currentTeamIndex;
    private int currentCharacterIndex;

    @Transient private TeamInfo mpTeam;
    @Transient private int entityId;
    @Transient private final List<EntityAvatar> avatars;
    @Transient private final Set<EntityBaseGadget> gadgets;
    @Transient private final IntSet teamResonances;
    @Transient private final IntSet teamResonancesConfig;

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

        this.teams = new HashMap<>();
        this.currentTeamIndex = 1;
        for (int i = 1; i <= GameConstants.MAX_TEAMS; i++) {
            this.teams.put(i, new TeamInfo());
        }
    }

    public World getWorld() {
        return getPlayer().getWorld();
    }

    public Map<Integer, TeamInfo> getTeams() {
        return this.teams;
    }

    public TeamInfo getMpTeam() {
        return mpTeam;
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
        return currentTeamIndex;
    }

    private void setCurrentTeamId(int currentTeamIndex) {
        this.currentTeamIndex = currentTeamIndex;
    }

    public int getCurrentCharacterIndex() {
        return currentCharacterIndex;
    }

    public void setCurrentCharacterIndex(int currentCharacterIndex) {
        this.currentCharacterIndex = currentCharacterIndex;
    }

    public long getCurrentCharacterGuid() {
        return getCurrentAvatarEntity().getAvatar().getGuid();
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

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public Set<EntityBaseGadget> getGadgets() {
        return gadgets;
    }

    public IntSet getTeamResonances() {
        return teamResonances;
    }

    public IntSet getTeamResonancesConfig() {
        return teamResonancesConfig;
    }

    public List<EntityAvatar> getActiveTeam() {
        return avatars;
    }

    public EntityAvatar getCurrentAvatarEntity() {
        return getActiveTeam().get(currentCharacterIndex);
    }

    public boolean isSpawned() {
        return getPlayer().getScene() != null && getPlayer().getScene().getEntities().containsKey(getCurrentAvatarEntity().getId());
    }

    public int getMaxTeamSize() {
        if (getPlayer().isInMultiplayer()) {
            int max = GAME_OPTIONS.avatarLimits.multiplayerTeam;
            if (getPlayer().getWorld().getHost() == this.getPlayer()) {
                return Math.max(1, (int) Math.ceil(max / (double) getWorld().getPlayerCount()));
            }
            return Math.max(1, (int) Math.floor(max / (double) getWorld().getPlayerCount()));
        }

        return GAME_OPTIONS.avatarLimits.singlePlayerTeam;
    }

    // Methods

    /**
     * Returns true if there is space to add the number of avatars to the team.
     */
    public boolean canAddAvatarsToTeam(TeamInfo team, int avatars) {
        return team.size() + avatars <= getMaxTeamSize();
    }

    /**
     * Returns true if there is space to add to the team.
     */
    public boolean canAddAvatarToTeam(TeamInfo team) {
        return canAddAvatarsToTeam(team, 1);
    }

    /**
     * Returns true if there is space to add the number of avatars to the current team.
     * If the current team is temporary, returns false.
     */
    public boolean canAddAvatarsToCurrentTeam(int avatars) {
        if (this.useTemporarilyTeamIndex != -1) {
            return false;
        }
        return canAddAvatarsToTeam(this.getCurrentTeamInfo(), avatars);
    }

    /**
     * Returns true if there is space to add to the current team.
     * If the current team is temporary, returns false.
     */
    public boolean canAddAvatarToCurrentTeam() {
        return canAddAvatarsToCurrentTeam(1);
    }

    /**
     * Try to add the collection of avatars to the team.
     * Returns true if all were successfully added.
     * If some can not be added, returns false and does not add any.
     */
    public boolean addAvatarsToTeam(TeamInfo team, Collection<Avatar> avatars) {
        if (!canAddAvatarsToTeam(team, avatars.size())) {
            return false;
        }

        // Convert avatars into a collection of avatar IDs, then add
        team.getAvatars().addAll(avatars.stream().map(a -> a.getAvatarId()).toList());

        // Update team
        if (this.getPlayer().isInMultiplayer()) {
            if (team.equals(this.getMpTeam())) {
                // MP team Packet
                this.updateTeamEntities(new PacketChangeMpTeamAvatarRsp(getPlayer(), team));
            }
        } else {
            // SP team update packet
            getPlayer().sendPacket(new PacketAvatarTeamUpdateNotify(getPlayer()));

            int teamId = this.getTeamId(team);
            if (teamId != -1) {
                // This is one of the player's teams
                // Update entites
                if (teamId == this.getCurrentTeamId()) {
                    this.updateTeamEntities(new PacketSetUpAvatarTeamRsp(getPlayer(), teamId, team));
                } else {
                    getPlayer().sendPacket(new PacketSetUpAvatarTeamRsp(getPlayer(), teamId, team));
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
        return addAvatarsToTeam(team, Collections.singleton(avatar));
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
        return addAvatarsToTeam(this.getCurrentTeamInfo(), avatars);
    }

    /**
     * Try to add an avatar to the current team.
     * Will not modify a temporary team.
     * Returns true if successful.
     */
    public boolean addAvatarToCurrentTeam(Avatar avatar) {
        return addAvatarsToCurrentTeam(Collections.singleton(avatar));
    }

    private void updateTeamResonances() {
        Int2IntOpenHashMap map = new Int2IntOpenHashMap();

        this.getTeamResonances().clear();
        this.getTeamResonancesConfig().clear();

        for (EntityAvatar entity : getActiveTeam()) {
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

        for (EntityAvatar entity : getActiveTeam()) {
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
                entity = new EntityAvatar(getPlayer().getScene(), getPlayer().getAvatars().getAvatarById(avatarId));
            }

            this.getActiveTeam().add(entity);
        }

        // Unload removed entities
        for (EntityAvatar entity : existingAvatars.values()) {
            getPlayer().getScene().removeEntity(entity);
            entity.getAvatar().save();
        }

        // Set new selected character index
        if (prevSelectedAvatarIndex == -1) {
            // Previous selected avatar is not in the same spot, we will select the current one in the prev slot
            prevSelectedAvatarIndex = Math.min(this.currentCharacterIndex, this.getActiveTeam().size() - 1);
        }
        this.currentCharacterIndex = prevSelectedAvatarIndex;

        // Update team resonances
        updateTeamResonances();

        // Packets
        getPlayer().getWorld().broadcastPacket(new PacketSceneTeamUpdateNotify(getPlayer()));

        // Skill charges packet - Yes, this is official server behavior as of 2.6.0
        for (EntityAvatar entity : getActiveTeam()) {
            if (entity.getAvatar().getSkillExtraChargeMap().size() > 0) {
                getPlayer().sendPacket(new PacketAvatarSkillInfoNotify(entity.getAvatar()));
            }
        }

        // Run callback
        if (responsePacket != null) {
            getPlayer().sendPacket(responsePacket);
        }

        // Check if character changed
        if (currentEntity != getCurrentAvatarEntity()) {
            // Remove and Add
            getPlayer().getScene().replaceEntity(currentEntity, getCurrentAvatarEntity());
        }
    }

    public synchronized void setupAvatarTeam(int teamId, List<Long> list) {
        // Sanity checks
        if (list.size() == 0 || list.size() > getMaxTeamSize() || getPlayer().isInMultiplayer()) {
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
            Avatar avatar = getPlayer().getAvatars().getAvatarByGuid(aLong);
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
        if (list.size() == 0 || list.size() > getMaxTeamSize() || !getPlayer().isInMultiplayer()) {
            return;
        }

        TeamInfo teamInfo = this.getMpTeam();

        // Set team data
        LinkedHashSet<Avatar> newTeam = new LinkedHashSet<>();
        for (Long aLong : list) {
            Avatar avatar = getPlayer().getAvatars().getAvatarByGuid(aLong);
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
                    if (list.size() == 0 || list.size() > getMaxTeamSize()) {
                        return null;
                    }

                    // Set team data
                    LinkedHashSet<Avatar> newTeam = new LinkedHashSet<>();
                    for (Long aLong : list) {
                        Avatar avatar = getPlayer().getAvatars().getAvatarByGuid(aLong);
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
        updateTeamEntities(null);
    }

    public void cleanTemporaryTeam() {
        // check if using temporary team
        if (useTemporarilyTeamIndex < 0) {
            return;
        }

        this.useTemporarilyTeamIndex = -1;
        this.temporaryTeam = null;
        updateTeamEntities(null);
    }
    public synchronized void setCurrentTeam(int teamId) {
        //
        if (getPlayer().isInMultiplayer()) {
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
        getPlayer().sendPacket(new PacketChangeTeamNameRsp(teamId, teamName));
    }

    public synchronized void changeAvatar(long guid) {
        EntityAvatar oldEntity = this.getCurrentAvatarEntity();

        if (guid == oldEntity.getAvatar().getGuid()) {
            return;
        }

        EntityAvatar newEntity = null;
        int index = -1;
        for (int i = 0; i < getActiveTeam().size(); i++) {
            if (guid == getActiveTeam().get(i).getAvatar().getGuid()) {
                index = i;
                newEntity = getActiveTeam().get(i);
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
        getPlayer().getScene().replaceEntity(oldEntity, newEntity);
        getPlayer().sendPacket(new PacketChangeAvatarRsp(guid));
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
            getPlayer().sendPacket(new PacketWorldPlayerDieNotify(dieType, killedBy));
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
                getPlayer().sendPacket(new PacketWorldPlayerDieNotify(dieType, killedBy));
            } else {
                // Set index and spawn replacement member
                this.setCurrentCharacterIndex(replaceIndex);
                getPlayer().getScene().addEntity(replacement);
            }
        }

        // Response packet
        getPlayer().sendPacket(new PacketAvatarDieAnimationEndRsp(deadAvatar.getId(), 0));
    }

    public boolean reviveAvatar(Avatar avatar) {
        for (EntityAvatar entity : getActiveTeam()) {
            if (entity.getAvatar() == avatar) {
                if (entity.isAlive()) {
                    return false;
                }

                entity.setFightProperty(
                        FightProperty.FIGHT_PROP_CUR_HP,
                        entity.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP) * .1f
                );
                getPlayer().sendPacket(new PacketAvatarFightPropUpdateNotify(entity.getAvatar(), FightProperty.FIGHT_PROP_CUR_HP));
                getPlayer().sendPacket(new PacketAvatarLifeStateChangeNotify(entity.getAvatar()));
                return true;
            }
        }

        return false;
    }

    public boolean healAvatar(Avatar avatar, int healRate, int healAmount) {
        for (EntityAvatar entity : getActiveTeam()) {
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
                getPlayer().sendPacket(new PacketAvatarFightPropUpdateNotify(entity.getAvatar(), FightProperty.FIGHT_PROP_CUR_HP));
                getPlayer().sendPacket(new PacketAvatarLifeStateChangeNotify(entity.getAvatar()));
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
        for (EntityAvatar entity : getActiveTeam()) {
            entity.setFightProperty(
                    FightProperty.FIGHT_PROP_CUR_HP,
                    entity.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP) * .4f
            );
            getPlayer().sendPacket(new PacketAvatarFightPropUpdateNotify(entity.getAvatar(), FightProperty.FIGHT_PROP_CUR_HP));
            getPlayer().sendPacket(new PacketAvatarLifeStateChangeNotify(entity.getAvatar()));
        }

        // Teleport player
        getPlayer().sendPacket(new PacketPlayerEnterSceneNotify(getPlayer(), EnterType.ENTER_TYPE_SELF, EnterReason.Revival, 3, GameConstants.START_POSITION));

        // Set player position
        player.setSceneId(3);
        player.getPosition().set(GameConstants.START_POSITION);

        // Packets
        getPlayer().sendPacket(new BasePacket(PacketOpcodes.WorldPlayerReviveRsp));
    }

    public void saveAvatars() {
        // Save all avatars from active team
        for (EntityAvatar entity : getActiveTeam()) {
            entity.getAvatar().save();
        }
    }
}
