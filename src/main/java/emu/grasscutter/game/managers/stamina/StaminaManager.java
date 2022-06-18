package emu.grasscutter.game.managers.stamina;

import ch.qos.logback.classic.Logger;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.props.LifeState;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.game.props.WeaponType;
import emu.grasscutter.net.proto.EntityMoveInfoOuterClass.EntityMoveInfo;
import emu.grasscutter.net.proto.MotionInfoOuterClass.MotionInfo;
import emu.grasscutter.net.proto.MotionStateOuterClass.MotionState;
import emu.grasscutter.net.proto.PlayerDieTypeOuterClass.PlayerDieType;
import emu.grasscutter.net.proto.VectorOuterClass.Vector;
import emu.grasscutter.net.proto.VehicleInteractTypeOuterClass.VehicleInteractType;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.*;
import emu.grasscutter.utils.Position;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static emu.grasscutter.Configuration.GAME_OPTIONS;

public class StaminaManager {

    // TODO: Skiff state detection?
    private final Player player;
    private static final HashMap<String, HashSet<MotionState>> MotionStatesCategorized = new HashMap<>() {{
        this.put("CLIMB", new HashSet<>(List.of(
            MotionState.MOTION_STATE_CLIMB, // sustained, when not moving no cost no recover
            MotionState.MOTION_STATE_STANDBY_TO_CLIMB // NOT OBSERVED, see MOTION_JUMP_UP_WALL_FOR_STANDBY
        )));
        this.put("DASH", new HashSet<>(List.of(
            MotionState.MOTION_STATE_DANGER_DASH, // sustained
            MotionState.MOTION_STATE_DASH // sustained
        )));
        this.put("FLY", new HashSet<>(List.of(
            MotionState.MOTION_STATE_FLY, // sustained
            MotionState.MOTION_STATE_FLY_FAST, // sustained
            MotionState.MOTION_STATE_FLY_SLOW, // sustained
            MotionState.MOTION_STATE_POWERED_FLY // sustained, recover
        )));
        this.put("RUN", new HashSet<>(List.of(
            MotionState.MOTION_STATE_DANGER_RUN, // sustained, recover
            MotionState.MOTION_STATE_RUN // sustained, recover
        )));
        this.put("SKIFF", new HashSet<>(List.of(
            MotionState.MOTION_STATE_SKIFF_BOARDING, // NOT OBSERVED even when boarding
            MotionState.MOTION_STATE_SKIFF_DASH, // sustained, observed with waverider entity ID.
            MotionState.MOTION_STATE_SKIFF_NORMAL, // sustained, OBSERVED when both normal and dashing
            MotionState.MOTION_STATE_SKIFF_POWERED_DASH // sustained, recover
        )));
        this.put("STANDBY", new HashSet<>(List.of(
            MotionState.MOTION_STATE_DANGER_STANDBY_MOVE, // sustained, recover
            MotionState.MOTION_STATE_DANGER_STANDBY, // sustained, recover
            MotionState.MOTION_STATE_LADDER_TO_STANDBY, // NOT OBSERVED
            MotionState.MOTION_STATE_STANDBY_MOVE, // sustained, recover
            MotionState.MOTION_STATE_STANDBY // sustained, recover
        )));
        this.put("SWIM", new HashSet<>(List.of(
            MotionState.MOTION_STATE_SWIM_IDLE, // sustained
            MotionState.MOTION_STATE_SWIM_DASH, // immediate and sustained
            MotionState.MOTION_STATE_SWIM_JUMP, // NOT OBSERVED
            MotionState.MOTION_STATE_SWIM_MOVE // sustained
        )));
        this.put("WALK", new HashSet<>(List.of(
            MotionState.MOTION_STATE_DANGER_WALK, // sustained, recover
            MotionState.MOTION_STATE_WALK // sustained, recover
        )));
        this.put("OTHER", new HashSet<>(List.of(
            MotionState.MOTION_STATE_CLIMB_JUMP, // cost only once if repeated without switching state
            MotionState.MOTION_STATE_DASH_BEFORE_SHAKE, // immediate one time sprint charge.
            MotionState.MOTION_STATE_FIGHT, // immediate, if sustained then subsequent will be MOTION_NOTIFY
            MotionState.MOTION_STATE_JUMP_UP_WALL_FOR_STANDBY, // immediate, observed when RUN/WALK->CLIMB
            MotionState.MOTION_STATE_NOTIFY, // can be either cost or recover - check previous state and check skill casting
            MotionState.MOTION_STATE_SIT_IDLE, // sustained, recover
            MotionState.MOTION_STATE_JUMP // recover
        )));
        this.put("NOCOST_NORECOVER", new HashSet<>(List.of(
            MotionState.MOTION_STATE_LADDER_SLIP, // NOT OBSERVED
            MotionState.MOTION_STATE_SLIP, // sustained, no cost no recover
            MotionState.MOTION_STATE_FLY_IDLE // NOT OBSERVED
        )));
        this.put("IGNORE", new HashSet<>(List.of(
            // these states have no impact on stamina
            MotionState.MOTION_STATE_CROUCH_IDLE,
            MotionState.MOTION_STATE_CROUCH_MOVE,
            MotionState.MOTION_STATE_CROUCH_ROLL,
            MotionState.MOTION_STATE_DESTROY_VEHICLE,
            MotionState.MOTION_STATE_FALL_ON_GROUND,
            MotionState.MOTION_STATE_FOLLOW_ROUTE,
            MotionState.MOTION_STATE_FORCE_SET_POS,
            MotionState.MOTION_STATE_GO_UPSTAIRS,
            MotionState.MOTION_STATE_JUMP_OFF_WALL,
            MotionState.MOTION_STATE_LADDER_IDLE,
            MotionState.MOTION_STATE_LADDER_MOVE,
            MotionState.MOTION_STATE_LAND_SPEED,
            MotionState.MOTION_STATE_MOVE_FAIL_ACK,
            MotionState.MOTION_STATE_NONE,
            MotionState.MOTION_STATE_NUM,
            MotionState.MOTION_STATE_QUEST_FORCE_DRAG,
            MotionState.MOTION_STATE_RESET,
            MotionState.MOTION_STATE_STANDBY_TO_LADDER,
            MotionState.MOTION_STATE_WATERFALL
        )));
    }};

    private final Logger logger = Grasscutter.getLogger();
    public final static int GlobalCharacterMaximumStamina = 24000;
    public final static int GlobalVehicleMaxStamina = 24000;
    private Position currentCoordinates = new Position(0, 0, 0);
    private Position previousCoordinates = new Position(0, 0, 0);
    private MotionState currentState = MotionState.MOTION_STATE_STANDBY;
    private MotionState previousState = MotionState.MOTION_STATE_STANDBY;
    private Timer sustainedStaminaHandlerTimer;
    private GameSession cachedSession = null;
    private GameEntity cachedEntity = null;
    private int staminaRecoverDelay = 0;
    private final HashMap<String, BeforeUpdateStaminaListener> beforeUpdateStaminaListeners = new HashMap<>();
    private final HashMap<String, AfterUpdateStaminaListener> afterUpdateStaminaListeners = new HashMap<>();
    private int lastSkillId = 0;
    private int lastSkillCasterId = 0;
    private boolean lastSkillFirstTick = true;
    private int vehicleId = -1;
    private int vehicleStamina = GlobalVehicleMaxStamina;
    private static final HashSet<Integer> TalentMovements = new HashSet<>(List.of(
        10013, 10413
    ));
    private static final HashMap<Integer, Float> ClimbFoodReductionMap = new HashMap<>() {{
        // TODO: get real food id
        this.put(0, 0.8f); // Sample food
    }};
    private static final HashMap<Integer, Float> DashFoodReductionMap = new HashMap<>() {{
        // TODO: get real food id
        this.put(0, 0.8f); // Sample food
    }};
    private static final HashMap<Integer, Float> FlyFoodReductionMap = new HashMap<>() {{
        // TODO: get real food id
        this.put(0, 0.8f); // Sample food
    }};
    private static final HashMap<Integer, Float> SwimFoodReductionMap = new HashMap<>() {{
        // TODO: get real food id
        this.put(0, 0.8f); // Sample food
    }};
    private static final HashMap<Integer, Float> ClimbTalentReductionMap = new HashMap<>() {{
        this.put(262301, 0.8f);
    }};
    private static final HashMap<Integer, Float> FlyTalentReductionMap = new HashMap<>() {{
        this.put(212301, 0.8f);
        this.put(222301, 0.8f);
    }};
    private static final HashMap<Integer, Float> SwimTalentReductionMap = new HashMap<>() {{
        this.put(242301, 0.8f);
        this.put(542301, 0.8f);
    }};

    public static void initialize() {
        // TODO: Initialize foods etc.
    }

    public StaminaManager(Player player) {
        this.player = player;
    }

    // Accessors

    public void setSkillCast(int skillId, int skillCasterId) {
        this.lastSkillFirstTick = true;
        this.lastSkillId = skillId;
        this.lastSkillCasterId = skillCasterId;
    }

    public int getMaxCharacterStamina() {
        return this.player.getProperty(PlayerProperty.PROP_MAX_STAMINA);
    }

    public int getCurrentCharacterStamina() {
        return this.player.getProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA);
    }

    public int getMaxVehicleStamina() {
        return GlobalVehicleMaxStamina;
    }

    public int getCurrentVehicleStamina() {
        return this.vehicleStamina;
    }

    public boolean registerBeforeUpdateStaminaListener(String listenerName, BeforeUpdateStaminaListener listener) {
        if (this.beforeUpdateStaminaListeners.containsKey(listenerName)) {
            return false;
        }
        this.beforeUpdateStaminaListeners.put(listenerName, listener);
        return true;
    }

    public boolean unregisterBeforeUpdateStaminaListener(String listenerName) {
        if (!this.beforeUpdateStaminaListeners.containsKey(listenerName)) {
            return false;
        }
        this.beforeUpdateStaminaListeners.remove(listenerName);
        return true;
    }

    public boolean registerAfterUpdateStaminaListener(String listenerName, AfterUpdateStaminaListener listener) {
        if (this.afterUpdateStaminaListeners.containsKey(listenerName)) {
            return false;
        }
        this.afterUpdateStaminaListeners.put(listenerName, listener);
        return true;
    }

    public boolean unregisterAfterUpdateStaminaListener(String listenerName) {
        if (!this.afterUpdateStaminaListeners.containsKey(listenerName)) {
            return false;
        }
        this.afterUpdateStaminaListeners.remove(listenerName);
        return true;
    }

    private boolean isPlayerMoving() {
        float diffX = this.currentCoordinates.getX() - this.previousCoordinates.getX();
        float diffY = this.currentCoordinates.getY() - this.previousCoordinates.getY();
        float diffZ = this.currentCoordinates.getZ() - this.previousCoordinates.getZ();
        this.logger.trace("isPlayerMoving: " + this.previousCoordinates + ", " + this.currentCoordinates +
            ", " + diffX + ", " + diffY + ", " + diffZ);
        return Math.abs(diffX) > 0.3 || Math.abs(diffY) > 0.2 || Math.abs(diffZ) > 0.3;
    }

    public int updateStaminaRelative(GameSession session, Consumption consumption, boolean isCharacterStamina) {
        int currentStamina = isCharacterStamina ? this.getCurrentCharacterStamina() : this.getCurrentVehicleStamina();
        if (consumption.amount == 0) {
            return currentStamina;
        }
        // notify will update
        for (Map.Entry<String, BeforeUpdateStaminaListener> listener : this.beforeUpdateStaminaListeners.entrySet()) {
            Consumption overriddenConsumption = listener.getValue().onBeforeUpdateStamina(consumption.type.toString(), consumption, isCharacterStamina);
            if ((overriddenConsumption.type != consumption.type) && (overriddenConsumption.amount != consumption.amount)) {
                this.logger.debug("Stamina update relative(" +
                    consumption.type.toString() + ", " + consumption.amount + ") overridden to relative(" +
                    consumption.type.toString() + ", " + consumption.amount + ") by: " + listener.getKey());
                return currentStamina;
            }
        }
        int maxStamina = isCharacterStamina ? this.getMaxCharacterStamina() : this.getMaxVehicleStamina();
        this.logger.trace((isCharacterStamina ? "C " : "V ") + currentStamina + "/" + maxStamina + "\t" + this.currentState + "\t" +
            (this.isPlayerMoving() ? "moving" : "      ") + "\t(" + consumption.type + "," +
            consumption.amount + ")");
        int newStamina = currentStamina + consumption.amount;
        if (newStamina < 0) {
            newStamina = 0;
        } else if (newStamina > maxStamina) {
            newStamina = maxStamina;
        }
        return this.setStamina(session, consumption.type.toString(), newStamina, isCharacterStamina);
    }

    public int updateStaminaAbsolute(GameSession session, String reason, int newStamina, boolean isCharacterStamina) {
        int currentStamina = isCharacterStamina ? this.getCurrentCharacterStamina() : this.getCurrentVehicleStamina();
        // notify will update
        for (Map.Entry<String, BeforeUpdateStaminaListener> listener : this.beforeUpdateStaminaListeners.entrySet()) {
            int overriddenNewStamina = listener.getValue().onBeforeUpdateStamina(reason, newStamina, isCharacterStamina);
            if (overriddenNewStamina != newStamina) {
                this.logger.debug("Stamina update absolute(" +
                    reason + ", " + newStamina + ") overridden to absolute(" +
                    reason + ", " + newStamina + ") by: " + listener.getKey());
                return currentStamina;
            }
        }
        int maxStamina = isCharacterStamina ? this.getMaxCharacterStamina() : this.getMaxVehicleStamina();
        if (newStamina < 0) {
            newStamina = 0;
        } else if (newStamina > maxStamina) {
            newStamina = maxStamina;
        }
        return this.setStamina(session, reason, newStamina, isCharacterStamina);
    }

    // Returns new stamina and sends PlayerPropNotify or VehicleStaminaNotify
    public int setStamina(GameSession session, String reason, int newStamina, boolean isCharacterStamina) {
        // Target Player
        if (!GAME_OPTIONS.staminaUsage || session.getPlayer().getStamina()) {
            newStamina = this.getMaxCharacterStamina();
        }

        // set stamina if is character stamina
        if (isCharacterStamina) {
            this.player.setProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA, newStamina);
            session.send(new PacketPlayerPropNotify(this.player, PlayerProperty.PROP_CUR_PERSIST_STAMINA));
        } else {
            this.vehicleStamina = newStamina;
            session.send(new PacketVehicleStaminaNotify(this.vehicleId, ((float) newStamina) / 100));
        }
        // notify updated
        for (Map.Entry<String, AfterUpdateStaminaListener> listener : this.afterUpdateStaminaListeners.entrySet()) {
            listener.getValue().onAfterUpdateStamina(reason, newStamina, isCharacterStamina);
        }
        return newStamina;
    }

    // Kills avatar, removes entity and sends notification.
    // TODO: Probably move this to Avatar class? since other components may also need to kill avatar.
    public void killAvatar(GameSession session, GameEntity entity, PlayerDieType dieType) {
        session.send(new PacketAvatarLifeStateChangeNotify(this.player.getTeamManager().getCurrentAvatarEntity().getAvatar(),
            LifeState.LIFE_DEAD, dieType));
        session.send(new PacketLifeStateChangeNotify(entity, LifeState.LIFE_DEAD, dieType));
        entity.setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, 0);
        entity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_CUR_HP));
        entity.getWorld().broadcastPacket(new PacketLifeStateChangeNotify(0, entity, LifeState.LIFE_DEAD));
        this.player.getScene().removeEntity(entity);
        ((EntityAvatar) entity).onDeath(dieType, 0);
    }

    public void startSustainedStaminaHandler() {
        if (!this.player.isPaused() && this.sustainedStaminaHandlerTimer == null) {
            this.sustainedStaminaHandlerTimer = new Timer();
            this.sustainedStaminaHandlerTimer.scheduleAtFixedRate(new SustainedStaminaHandler(), 0, 200);
            this.logger.debug("[MovementManager] SustainedStaminaHandlerTimer started");
        }
    }

    public void stopSustainedStaminaHandler() {
        if (this.sustainedStaminaHandlerTimer != null) {
            this.sustainedStaminaHandlerTimer.cancel();
            this.sustainedStaminaHandlerTimer = null;
            this.logger.debug("[MovementManager] SustainedStaminaHandlerTimer stopped");
        }
    }

    // Handlers

    // External trigger handler

    public void handleEvtDoSkillSuccNotify(GameSession session, int skillId, int casterId) {
        // Ignore if skill not cast by not current active avatar
        if (casterId != this.player.getTeamManager().getCurrentAvatarEntity().getId()) {
            return;
        }
        this.setSkillCast(skillId, casterId);
        // Handle immediate stamina cost
        Avatar currentAvatar = this.player.getTeamManager().getCurrentAvatarEntity().getAvatar();
        if (currentAvatar.getAvatarData().getWeaponType() == WeaponType.WEAPON_CLAYMORE) {
            // Exclude claymore as their stamina cost starts when MixinStaminaCost gets in
            return;
        }
        // TODO: Differentiate normal attacks from charged attacks and exclude
        // TODO: Temporary: Exclude non-claymore attacks for now
        /*
        if (BowAvatars.contains(currentAvatarId)
                || SwordAvatars.contains(currentAvatarId)
                || PolearmAvatars.contains(currentAvatarId)
                || CatalystAvatars.contains(currentAvatarId)
        ) {
            return;
        }
        */
        //handleImmediateStamina(session, skillId);
    }

    public void handleMixinCostStamina(boolean isSwim) {
        // Talent moving and claymore avatar charged attack duration
        // logger.trace("abilityMixinCostStamina: isSwim: " + isSwim + "\tlastSkill: " + lastSkillId);
        if (this.lastSkillCasterId == this.player.getTeamManager().getCurrentAvatarEntity().getId()) {
            this.handleImmediateStamina(this.cachedSession, this.lastSkillId);
        }
    }

    public void handleCombatInvocationsNotify(@NotNull GameSession session, @NotNull EntityMoveInfo moveInfo, @NotNull GameEntity entity) {
        // cache info for later use in SustainedStaminaHandler tick
        this.cachedSession = session;
        this.cachedEntity = entity;
        MotionInfo motionInfo = moveInfo.getMotionInfo();
        MotionState motionState = motionInfo.getState();
        int notifyEntityId = entity.getId();
        int currentAvatarEntityId = session.getPlayer().getTeamManager().getCurrentAvatarEntity().getId();
        if (notifyEntityId != currentAvatarEntityId && notifyEntityId != this.vehicleId) {
            return;
        }
        this.currentState = motionState;
        // logger.trace(currentState + "\t" + (notifyEntityId == currentAvatarEntityId ? "character" : "vehicle"));
        Vector posVector = motionInfo.getPos();
        Position newPos = new Position(posVector.getX(), posVector.getY(), posVector.getZ());
        if (newPos.getX() != 0 && newPos.getY() != 0 && newPos.getZ() != 0) {
            this.currentCoordinates = newPos;
        }
        this.startSustainedStaminaHandler();
        this.handleImmediateStamina(session, motionState);
    }

    public void handleVehicleInteractReq(GameSession session, int vehicleId, VehicleInteractType vehicleInteractType) {
        if (vehicleInteractType == VehicleInteractType.VEHICLE_INTERACT_TYPE_IN) {
            this.vehicleId = vehicleId;
            // Reset character stamina here to prevent falling into water immediately on ejection if char stamina is
            //      close to empty when boarding.
            this.updateStaminaAbsolute(session, "board vehicle", this.getMaxCharacterStamina(), true);
            this.updateStaminaAbsolute(session, "board vehicle", this.getMaxVehicleStamina(), false);
        } else {
            this.vehicleId = -1;
        }
    }

    // Internal handler

    private void handleImmediateStamina(GameSession session, @NotNull MotionState motionState) {
        switch (motionState) {
            case MOTION_STATE_CLIMB:
                if (this.currentState != MotionState.MOTION_STATE_CLIMB) {
                    this.updateStaminaRelative(session, new Consumption(ConsumptionType.CLIMB_START), true);
                }
                break;
            case MOTION_STATE_DASH_BEFORE_SHAKE:
                if (this.previousState != MotionState.MOTION_STATE_DASH_BEFORE_SHAKE) {
                    this.updateStaminaRelative(session, new Consumption(ConsumptionType.SPRINT), true);
                }
                break;
            case MOTION_STATE_CLIMB_JUMP:
                if (this.previousState != MotionState.MOTION_STATE_CLIMB_JUMP) {
                    this.updateStaminaRelative(session, new Consumption(ConsumptionType.CLIMB_JUMP), true);
                }
                break;
            case MOTION_STATE_SWIM_DASH:
                if (this.previousState != MotionState.MOTION_STATE_SWIM_DASH) {
                    this.updateStaminaRelative(session, new Consumption(ConsumptionType.SWIM_DASH_START), true);
                }
                break;
        }
    }

    private void handleImmediateStamina(GameSession session, int skillId) {
        Consumption consumption = this.getFightConsumption(skillId);
        this.updateStaminaRelative(session, consumption, true);
    }

    private class SustainedStaminaHandler extends TimerTask {
        public void run() {
            boolean moving = StaminaManager.this.isPlayerMoving();
            int currentCharacterStamina = StaminaManager.this.getCurrentCharacterStamina();
            int maxCharacterStamina = StaminaManager.this.getMaxCharacterStamina();
            int currentVehicleStamina = StaminaManager.this.getCurrentVehicleStamina();
            int maxVehicleStamina = StaminaManager.this.getMaxVehicleStamina();
            if (moving || (currentCharacterStamina < maxCharacterStamina) || (currentVehicleStamina < maxVehicleStamina)) {
                StaminaManager.this.logger.trace("Player moving: " + moving + ", stamina full: " +
                    (currentCharacterStamina >= maxCharacterStamina) + ", recalculate stamina");
                boolean isCharacterStamina = true;
                Consumption consumption;
                if (MotionStatesCategorized.get("CLIMB").contains(StaminaManager.this.currentState)) {
                    consumption = StaminaManager.this.getClimbConsumption();
                } else if (MotionStatesCategorized.get("DASH").contains(StaminaManager.this.currentState)) {
                    consumption = StaminaManager.this.getDashConsumption();
                } else if (MotionStatesCategorized.get("FLY").contains(StaminaManager.this.currentState)) {
                    consumption = StaminaManager.this.getFlyConsumption();
                } else if (MotionStatesCategorized.get("RUN").contains(StaminaManager.this.currentState)) {
                    consumption = new Consumption(ConsumptionType.RUN);
                } else if (MotionStatesCategorized.get("SKIFF").contains(StaminaManager.this.currentState)) {
                    consumption = StaminaManager.this.getSkiffConsumption();
                    isCharacterStamina = false;
                } else if (MotionStatesCategorized.get("STANDBY").contains(StaminaManager.this.currentState)) {
                    consumption = new Consumption(ConsumptionType.STANDBY);
                } else if (MotionStatesCategorized.get("SWIM").contains(StaminaManager.this.currentState)) {
                    consumption = StaminaManager.this.getSwimConsumptions();
                } else if (MotionStatesCategorized.get("WALK").contains(StaminaManager.this.currentState)) {
                    consumption = new Consumption(ConsumptionType.WALK);
                } else if (MotionStatesCategorized.get("NOCOST_NORECOVER").contains(StaminaManager.this.currentState)) {
                    consumption = new Consumption();
                } else if (MotionStatesCategorized.get("OTHER").contains(StaminaManager.this.currentState)) {
                    consumption = StaminaManager.this.getOtherConsumptions();
                } else { // ignore
                    return;
                }

                if (consumption.amount < 0 && isCharacterStamina) {
                    // Do not apply reduction factor when recovering stamina
                    if (StaminaManager.this.player.getTeamManager().getTeamResonances().contains(10301)) {
                        consumption.amount *= 0.85f;
                    }
                }
                // Delay 1 seconds before starts recovering stamina
                if (consumption.amount != 0 && StaminaManager.this.cachedSession != null) {
                    if (consumption.amount < 0) {
                        StaminaManager.this.staminaRecoverDelay = 0;
                    }
                    if (consumption.amount > 0
                        && consumption.type != ConsumptionType.POWERED_FLY
                        && consumption.type != ConsumptionType.POWERED_SKIFF) {
                        // For POWERED_* recover immediately - things like Amber's gliding exam and skiff challenges may require this.
                        if (StaminaManager.this.staminaRecoverDelay < 5) {
                            // For others recover after 1 seconds (5 ticks) - as official server does.
                            StaminaManager.this.staminaRecoverDelay++;
                            consumption.amount = 0;
                            StaminaManager.this.logger.trace("Delaying recovery: " + StaminaManager.this.staminaRecoverDelay);
                        }
                    }
                    StaminaManager.this.updateStaminaRelative(StaminaManager.this.cachedSession, consumption, isCharacterStamina);
                }
            }
            StaminaManager.this.previousState = StaminaManager.this.currentState;
            StaminaManager.this.previousCoordinates = new Position(
                StaminaManager.this.currentCoordinates.getX(),
                StaminaManager.this.currentCoordinates.getY(),
                StaminaManager.this.currentCoordinates.getZ()
            );
        }
    }

    private void handleDrowning() {
        // TODO: fix drowning waverider entity
        int stamina = this.getCurrentCharacterStamina();
        if (stamina < 10) {
            this.logger.trace(this.getCurrentCharacterStamina() + "/" +
                this.getMaxCharacterStamina() + "\t" + this.currentState);
            if (this.currentState != MotionState.MOTION_STATE_SWIM_IDLE) {
                this.killAvatar(this.cachedSession, this.cachedEntity, PlayerDieType.PLAYER_DIE_TYPE_DRAWN);
            }
        }
    }

    // Consumption Calculators

    // Stamina Consumption Reduction: https://genshin-impact.fandom.com/wiki/Stamina

    private Consumption getFightConsumption(int skillCasting) {
        // Talent moving
        if (TalentMovements.contains(skillCasting)) {
            // TODO: recover 1000 if kamisato hits an enemy at the end of dashing
            return this.getTalentMovingSustainedCost(skillCasting);
        }
        // Bow avatar charged attack
        Avatar currentAvatar = this.player.getTeamManager().getCurrentAvatarEntity().getAvatar();

        switch (currentAvatar.getAvatarData().getWeaponType()) {
            case WEAPON_BOW:
                return this.getBowSustainedCost(skillCasting);
            case WEAPON_CLAYMORE:
                return this.getClaymoreSustainedCost(skillCasting);
            case WEAPON_CATALYST:
                return this.getCatalystCost(skillCasting);
            case WEAPON_POLE:
                return this.getPolearmCost(skillCasting);
            case WEAPON_SWORD_ONE_HAND:
                return this.getSwordCost(skillCasting);
        }

        return new Consumption();
    }

    private Consumption getClimbConsumption() {
        Consumption consumption = new Consumption();
        if (this.currentState == MotionState.MOTION_STATE_CLIMB && this.isPlayerMoving()) {
            consumption.type = ConsumptionType.CLIMBING;
            consumption.amount = ConsumptionType.CLIMBING.amount;
        }
        // Climbing specific reductions
        consumption.amount *= this.getFoodCostReductionFactor(ClimbFoodReductionMap);
        consumption.amount *= this.getTalentCostReductionFactor(ClimbTalentReductionMap);
        return consumption;
    }

    private Consumption getSwimConsumptions() {
        this.handleDrowning();
        Consumption consumption = new Consumption();
        if (this.currentState == MotionState.MOTION_STATE_SWIM_MOVE) {
            consumption.type = ConsumptionType.SWIMMING;
            consumption.amount = ConsumptionType.SWIMMING.amount;
        }
        if (this.currentState == MotionState.MOTION_STATE_SWIM_DASH) {
            consumption.type = ConsumptionType.SWIM_DASH;
            consumption.amount = ConsumptionType.SWIM_DASH.amount;
        }
        // Swimming specific reductions
        consumption.amount *= this.getFoodCostReductionFactor(SwimFoodReductionMap);
        consumption.amount *= this.getTalentCostReductionFactor(SwimTalentReductionMap);
        return consumption;
    }

    private Consumption getDashConsumption() {
        Consumption consumption = new Consumption();
        if (this.currentState == MotionState.MOTION_STATE_DASH) {
            consumption.type = ConsumptionType.DASH;
            consumption.amount = ConsumptionType.DASH.amount;
            // Dashing specific reductions
            consumption.amount *= this.getFoodCostReductionFactor(DashFoodReductionMap);
        }
        return consumption;
    }

    private Consumption getFlyConsumption() {
        // POWERED_FLY, e.g. wind tunnel
        if (this.currentState == MotionState.MOTION_STATE_POWERED_FLY) {
            return new Consumption(ConsumptionType.POWERED_FLY);
        }
        Consumption consumption = new Consumption(ConsumptionType.FLY);
        // Flying specific reductions
        consumption.amount *= this.getFoodCostReductionFactor(FlyFoodReductionMap);
        consumption.amount *= this.getTalentCostReductionFactor(FlyTalentReductionMap);
        return consumption;
    }

    private Consumption getSkiffConsumption() {
        // No known reduction for skiffing.
        return switch (this.currentState) {
            case MOTION_STATE_SKIFF_DASH -> new Consumption(ConsumptionType.SKIFF_DASH);
            case MOTION_STATE_SKIFF_POWERED_DASH -> new Consumption(ConsumptionType.POWERED_SKIFF);
            case MOTION_STATE_SKIFF_NORMAL -> new Consumption(ConsumptionType.SKIFF);
            default -> new Consumption();
        };
    }

    private Consumption getOtherConsumptions() {
        switch (this.currentState) {
            case MOTION_STATE_NOTIFY:
//                if (BowSkills.contains(lastSkillId)) {
//                    return new Consumption(ConsumptionType.FIGHT, 500);
//                }
                break;
            case MOTION_STATE_FIGHT:
                // TODO: what if charged attack
                return new Consumption(ConsumptionType.FIGHT, 500);
        }

        return new Consumption();
    }

    // Reduction getter

    private float getTalentCostReductionFactor(HashMap<Integer, Float> talentReductionMap) {
        // All known talents reductions are not stackable
        float reduction = 1;
        for (EntityAvatar entity : this.cachedSession.getPlayer().getTeamManager().getActiveTeam()) {
            for (int skillId : entity.getAvatar().getProudSkillList()) {
                if (talentReductionMap.containsKey(skillId)) {
                    float potentialLowerReduction = talentReductionMap.get(skillId);
                    if (potentialLowerReduction < reduction) {
                        reduction = potentialLowerReduction;
                    }
                }
            }
        }
        return reduction;
    }

    private float getFoodCostReductionFactor(HashMap<Integer, Float> foodReductionMap) {
        // All known food reductions are not stackable
        // TODO: Check consumed food (buff?) and return proper factor
        float reduction = 1;
        return reduction;
    }

    private Consumption getTalentMovingSustainedCost(int skillId) {
        if (this.lastSkillFirstTick) {
            this.lastSkillFirstTick = false;
            return new Consumption(ConsumptionType.TALENT_DASH, -1000);
        } else {
            return new Consumption(ConsumptionType.TALENT_DASH, -500);
        }
    }

    private Consumption getBowSustainedCost(int skillId) {
        // Note that bow skills actually recovers stamina
        // Character specific handling
        // switch (skillId) {
        //     // No known bow skills cost stamina
        // }
        return new Consumption(ConsumptionType.FIGHT, +500);
    }

    private Consumption getCatalystCost(int skillId) {
        Consumption consumption = new Consumption(ConsumptionType.FIGHT, -5000);
        // Character specific handling
        switch (skillId) {
            // TODO:
        }
        return consumption;
    }

    private Consumption getClaymoreSustainedCost(int skillId) {
        Consumption consumption = new Consumption(ConsumptionType.FIGHT, -1333); // 4000 / 3 = 1333
        // Character specific handling
        switch (skillId) {
            case 10571:
            case 10532:
                consumption.amount = 0;
                break;
            case 10160:
                if (this.player.getTeamManager().getCurrentAvatarEntity().getAvatar().getProudSkillList().contains(162101)) {
                    consumption.amount /= 2;
                }
                break;
        }
        return consumption;
    }

    private Consumption getPolearmCost(int skillId) {
        Consumption consumption = new Consumption(ConsumptionType.FIGHT, -2500);
        // Character specific handling
        switch (skillId) {
            // TODO:
        }
        return consumption;
    }

    private Consumption getSwordCost(int skillId) {
        Consumption consumption = new Consumption(ConsumptionType.FIGHT, -2000);
        // Character specific handling
        switch (skillId) {
            case 10421:
                consumption.amount = -2500;
                break;
        }
        return consumption;
    }
}
