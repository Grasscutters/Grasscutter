package emu.grasscutter.game.managers.stamina;

import ch.qos.logback.classic.Logger;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.player.BasePlayerManager;
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

import static emu.grasscutter.config.Configuration.GAME_OPTIONS;

import java.util.*;

public class StaminaManager extends BasePlayerManager {

    // TODO: Skiff state detection?
    private static final HashMap<String, HashSet<MotionState>> MotionStatesCategorized = new HashMap<>() {{
        put("CLIMB", new HashSet<>(List.of(
                MotionState.MOTION_STATE_CLIMB, // sustained, when not moving no cost no recover
                MotionState.MOTION_STATE_STANDBY_TO_CLIMB // NOT OBSERVED, see MOTION_JUMP_UP_WALL_FOR_STANDBY
        )));
        put("DASH", new HashSet<>(List.of(
                MotionState.MOTION_STATE_DANGER_DASH, // sustained
                MotionState.MOTION_STATE_DASH // sustained
        )));
        put("FLY", new HashSet<>(List.of(
                MotionState.MOTION_STATE_FLY, // sustained
                MotionState.MOTION_STATE_FLY_FAST, // sustained
                MotionState.MOTION_STATE_FLY_SLOW, // sustained
                MotionState.MOTION_STATE_POWERED_FLY // sustained, recover
        )));
        put("RUN", new HashSet<>(List.of(
                MotionState.MOTION_STATE_DANGER_RUN, // sustained, recover
                MotionState.MOTION_STATE_RUN // sustained, recover
        )));
        put("SKIFF", new HashSet<>(List.of(
                MotionState.MOTION_STATE_SKIFF_BOARDING, // NOT OBSERVED even when boarding
                MotionState.MOTION_STATE_SKIFF_DASH, // sustained, observed with waverider entity ID.
                MotionState.MOTION_STATE_SKIFF_NORMAL, // sustained, OBSERVED when both normal and dashing
                MotionState.MOTION_STATE_SKIFF_POWERED_DASH // sustained, recover
        )));
        put("STANDBY", new HashSet<>(List.of(
                MotionState.MOTION_STATE_DANGER_STANDBY_MOVE, // sustained, recover
                MotionState.MOTION_STATE_DANGER_STANDBY, // sustained, recover
                MotionState.MOTION_STATE_LADDER_TO_STANDBY, // NOT OBSERVED
                MotionState.MOTION_STATE_STANDBY_MOVE, // sustained, recover
                MotionState.MOTION_STATE_STANDBY // sustained, recover
        )));
        put("SWIM", new HashSet<>(List.of(
                MotionState.MOTION_STATE_SWIM_IDLE, // sustained
                MotionState.MOTION_STATE_SWIM_DASH, // immediate and sustained
                MotionState.MOTION_STATE_SWIM_JUMP, // NOT OBSERVED
                MotionState.MOTION_STATE_SWIM_MOVE // sustained
        )));
        put("WALK", new HashSet<>(List.of(
                MotionState.MOTION_STATE_DANGER_WALK, // sustained, recover
                MotionState.MOTION_STATE_WALK // sustained, recover
        )));
        put("OTHER", new HashSet<>(List.of(
                MotionState.MOTION_STATE_CLIMB_JUMP, // cost only once if repeated without switching state
                MotionState.MOTION_STATE_DASH_BEFORE_SHAKE, // immediate one time sprint charge.
                MotionState.MOTION_STATE_FIGHT, // immediate, if sustained then subsequent will be MOTION_NOTIFY
                MotionState.MOTION_STATE_JUMP_UP_WALL_FOR_STANDBY, // immediate, observed when RUN/WALK->CLIMB
                MotionState.MOTION_STATE_NOTIFY, // can be either cost or recover - check previous state and check skill casting
                MotionState.MOTION_STATE_SIT_IDLE, // sustained, recover
                MotionState.MOTION_STATE_JUMP // recover
        )));
        put("NOCOST_NORECOVER", new HashSet<>(List.of(
                MotionState.MOTION_STATE_LADDER_SLIP, // NOT OBSERVED
                MotionState.MOTION_STATE_SLIP, // sustained, no cost no recover
                MotionState.MOTION_STATE_FLY_IDLE // NOT OBSERVED
        )));
        put("IGNORE", new HashSet<>(List.of(
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
    public final static int GlobalCharacterMaximumStamina = PlayerProperty.PROP_MAX_STAMINA.getMax();
    public final static int GlobalVehicleMaxStamina = PlayerProperty.PROP_MAX_STAMINA.getMax();
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
        put(0, 0.8f); // Sample food
    }};
    private static final HashMap<Integer, Float> DashFoodReductionMap = new HashMap<>() {{
        // TODO: get real food id
        put(0, 0.8f); // Sample food
    }};
    private static final HashMap<Integer, Float> FlyFoodReductionMap = new HashMap<>() {{
        // TODO: get real food id
        put(0, 0.8f); // Sample food
    }};
    private static final HashMap<Integer, Float> SwimFoodReductionMap = new HashMap<>() {{
        // TODO: get real food id
        put(0, 0.8f); // Sample food
    }};
    private static final HashMap<Integer, Float> ClimbTalentReductionMap = new HashMap<>() {{
        put(262301, 0.8f);
    }};
    private static final HashMap<Integer, Float> FlyTalentReductionMap = new HashMap<>() {{
        put(212301, 0.8f);
        put(222301, 0.8f);
    }};
    private static final HashMap<Integer, Float> SwimTalentReductionMap = new HashMap<>() {{
        put(242301, 0.8f);
        put(542301, 0.8f);
    }};

    public static void initialize() {
        // TODO: Initialize foods etc.
    }

    public StaminaManager(Player player) {
        super(player);
    }

    // Accessors

    public void setSkillCast(int skillId, int skillCasterId) {
        lastSkillFirstTick = true;
        lastSkillId = skillId;
        lastSkillCasterId = skillCasterId;
    }

    public int getMaxCharacterStamina() {
        return player.getProperty(PlayerProperty.PROP_MAX_STAMINA);
    }

    public int getCurrentCharacterStamina() {
        return player.getProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA);
    }

    public int getMaxVehicleStamina() {
        return GlobalVehicleMaxStamina;
    }

    public int getCurrentVehicleStamina() {
        return vehicleStamina;
    }

    public boolean registerBeforeUpdateStaminaListener(String listenerName, BeforeUpdateStaminaListener listener) {
        if (beforeUpdateStaminaListeners.containsKey(listenerName)) {
            return false;
        }
        beforeUpdateStaminaListeners.put(listenerName, listener);
        return true;
    }

    public boolean unregisterBeforeUpdateStaminaListener(String listenerName) {
        if (!beforeUpdateStaminaListeners.containsKey(listenerName)) {
            return false;
        }
        beforeUpdateStaminaListeners.remove(listenerName);
        return true;
    }

    public boolean registerAfterUpdateStaminaListener(String listenerName, AfterUpdateStaminaListener listener) {
        if (afterUpdateStaminaListeners.containsKey(listenerName)) {
            return false;
        }
        afterUpdateStaminaListeners.put(listenerName, listener);
        return true;
    }

    public boolean unregisterAfterUpdateStaminaListener(String listenerName) {
        if (!afterUpdateStaminaListeners.containsKey(listenerName)) {
            return false;
        }
        afterUpdateStaminaListeners.remove(listenerName);
        return true;
    }

    private boolean isPlayerMoving() {
        float diffX = currentCoordinates.getX() - previousCoordinates.getX();
        float diffY = currentCoordinates.getY() - previousCoordinates.getY();
        float diffZ = currentCoordinates.getZ() - previousCoordinates.getZ();
        logger.trace("isPlayerMoving: " + previousCoordinates + ", " + currentCoordinates +
                ", " + diffX + ", " + diffY + ", " + diffZ);
        return Math.abs(diffX) > 0.3 || Math.abs(diffY) > 0.2 || Math.abs(diffZ) > 0.3;
    }

    public int updateStaminaRelative(GameSession session, Consumption consumption, boolean isCharacterStamina) {
        int currentStamina = isCharacterStamina ? getCurrentCharacterStamina() : getCurrentVehicleStamina();
        if (consumption.amount == 0) {
            return currentStamina;
        }
        // notify will update
        for (Map.Entry<String, BeforeUpdateStaminaListener> listener : beforeUpdateStaminaListeners.entrySet()) {
            Consumption overriddenConsumption = listener.getValue().onBeforeUpdateStamina(consumption.type.toString(), consumption, isCharacterStamina);
            if ((overriddenConsumption.type != consumption.type) && (overriddenConsumption.amount != consumption.amount)) {
                logger.debug("Stamina update relative(" +
                        consumption.type.toString() + ", " + consumption.amount + ") overridden to relative(" +
                        consumption.type.toString() + ", " + consumption.amount + ") by: " + listener.getKey());
                return currentStamina;
            }
        }
        int maxStamina = isCharacterStamina ? getMaxCharacterStamina() : getMaxVehicleStamina();
        logger.trace((isCharacterStamina ? "C " : "V ") + currentStamina + "/" + maxStamina + "\t" + currentState + "\t" +
                (isPlayerMoving() ? "moving" : "      ") + "\t(" + consumption.type + "," +
                consumption.amount + ")");
        int newStamina = currentStamina + consumption.amount;
        if (newStamina < 0) {
            newStamina = 0;
        } else if (newStamina > maxStamina) {
            newStamina = maxStamina;
        }
        return setStamina(session, consumption.type.toString(), newStamina, isCharacterStamina);
    }

    public int updateStaminaAbsolute(GameSession session, String reason, int newStamina, boolean isCharacterStamina) {
        int currentStamina = isCharacterStamina ? getCurrentCharacterStamina() : getCurrentVehicleStamina();
        // notify will update
        for (Map.Entry<String, BeforeUpdateStaminaListener> listener : beforeUpdateStaminaListeners.entrySet()) {
            int overriddenNewStamina = listener.getValue().onBeforeUpdateStamina(reason, newStamina, isCharacterStamina);
            if (overriddenNewStamina != newStamina) {
                logger.debug("Stamina update absolute(" +
                        reason + ", " + newStamina + ") overridden to absolute(" +
                        reason + ", " + newStamina + ") by: " + listener.getKey());
                return currentStamina;
            }
        }
        int maxStamina = isCharacterStamina ? getMaxCharacterStamina() : getMaxVehicleStamina();
        if (newStamina < 0) {
            newStamina = 0;
        } else if (newStamina > maxStamina) {
            newStamina = maxStamina;
        }
        return setStamina(session, reason, newStamina, isCharacterStamina);
    }

    // Returns new stamina and sends PlayerPropNotify or VehicleStaminaNotify
    public int setStamina(GameSession session, String reason, int newStamina, boolean isCharacterStamina) {
        // Target Player
        if (!GAME_OPTIONS.staminaUsage || session.getPlayer().getUnlimitedStamina()) {
            newStamina = getMaxCharacterStamina();
        }

        // set stamina if is character stamina
        if (isCharacterStamina) {
            player.setProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA, newStamina);
        } else {
            vehicleStamina = newStamina;
            session.send(new PacketVehicleStaminaNotify(vehicleId, ((float) newStamina) / 100));
        }
        // notify updated
        for (Map.Entry<String, AfterUpdateStaminaListener> listener : afterUpdateStaminaListeners.entrySet()) {
            listener.getValue().onAfterUpdateStamina(reason, newStamina, isCharacterStamina);
        }
        return newStamina;
    }

    // Kills avatar, removes entity and sends notification.
    // TODO: Probably move this to Avatar class? since other components may also need to kill avatar.
    public void killAvatar(GameSession session, GameEntity entity, PlayerDieType dieType) {
        session.send(new PacketAvatarLifeStateChangeNotify(player.getTeamManager().getCurrentAvatarEntity().getAvatar(),
                LifeState.LIFE_DEAD, dieType));
        session.send(new PacketLifeStateChangeNotify(entity, LifeState.LIFE_DEAD, dieType));
        entity.setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, 0);
        entity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_CUR_HP));
        entity.getWorld().broadcastPacket(new PacketLifeStateChangeNotify(0, entity, LifeState.LIFE_DEAD));
        player.getScene().removeEntity(entity);
        ((EntityAvatar) entity).onDeath(dieType, 0);
    }

    public void startSustainedStaminaHandler() {
        if (!player.isPaused() && sustainedStaminaHandlerTimer == null) {
            sustainedStaminaHandlerTimer = new Timer();
            sustainedStaminaHandlerTimer.scheduleAtFixedRate(new SustainedStaminaHandler(), 0, 200);
            logger.debug("[MovementManager] SustainedStaminaHandlerTimer started");
        }
    }

    public void stopSustainedStaminaHandler() {
        if (sustainedStaminaHandlerTimer != null) {
            sustainedStaminaHandlerTimer.cancel();
            sustainedStaminaHandlerTimer = null;
            logger.debug("[MovementManager] SustainedStaminaHandlerTimer stopped");
        }
    }

    // Handlers

    // External trigger handler

    public void handleEvtDoSkillSuccNotify(GameSession session, int skillId, int casterId) {
        // Ignore if skill not cast by not current active avatar
        if (casterId != player.getTeamManager().getCurrentAvatarEntity().getId()) {
            return;
        }
        setSkillCast(skillId, casterId);
        // Handle immediate stamina cost
        Avatar currentAvatar = player.getTeamManager().getCurrentAvatarEntity().getAvatar();
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
        if (lastSkillCasterId == player.getTeamManager().getCurrentAvatarEntity().getId()) {
            handleImmediateStamina(cachedSession, lastSkillId);
        }
    }

    public void handleCombatInvocationsNotify(@NotNull GameSession session, @NotNull EntityMoveInfo moveInfo, @NotNull GameEntity entity) {
        // cache info for later use in SustainedStaminaHandler tick
        cachedSession = session;
        cachedEntity = entity;
        MotionInfo motionInfo = moveInfo.getMotionInfo();
        MotionState motionState = motionInfo.getState();
        int notifyEntityId = entity.getId();
        int currentAvatarEntityId = session.getPlayer().getTeamManager().getCurrentAvatarEntity().getId();
        if (notifyEntityId != currentAvatarEntityId && notifyEntityId != vehicleId) {
            return;
        }
        currentState = motionState;
        // logger.trace(currentState + "\t" + (notifyEntityId == currentAvatarEntityId ? "character" : "vehicle"));
        Vector posVector = motionInfo.getPos();
        Position newPos = new Position(posVector.getX(), posVector.getY(), posVector.getZ());
        if (newPos.getX() != 0 && newPos.getY() != 0 && newPos.getZ() != 0) {
            currentCoordinates = newPos;
        }
        startSustainedStaminaHandler();
        handleImmediateStamina(session, motionState);
    }

    public void handleVehicleInteractReq(GameSession session, int vehicleId, VehicleInteractType vehicleInteractType) {
        if (vehicleInteractType == VehicleInteractType.VEHICLE_INTERACT_TYPE_IN) {
            this.vehicleId = vehicleId;
            // Reset character stamina here to prevent falling into water immediately on ejection if char stamina is
            //      close to empty when boarding.
            updateStaminaAbsolute(session, "board vehicle", getMaxCharacterStamina(), true);
            updateStaminaAbsolute(session, "board vehicle", getMaxVehicleStamina(), false);
        } else {
            this.vehicleId = -1;
        }
    }

    // Internal handler

    private void handleImmediateStamina(GameSession session, @NotNull MotionState motionState) {
        switch (motionState) {
            case MOTION_STATE_CLIMB:
                if (currentState != MotionState.MOTION_STATE_CLIMB) {
                    updateStaminaRelative(session, new Consumption(ConsumptionType.CLIMB_START), true);
                }
                break;
            case MOTION_STATE_DASH_BEFORE_SHAKE:
                if (previousState != MotionState.MOTION_STATE_DASH_BEFORE_SHAKE) {
                    updateStaminaRelative(session, new Consumption(ConsumptionType.SPRINT), true);
                }
                break;
            case MOTION_STATE_CLIMB_JUMP:
                if (previousState != MotionState.MOTION_STATE_CLIMB_JUMP) {
                    updateStaminaRelative(session, new Consumption(ConsumptionType.CLIMB_JUMP), true);
                }
                break;
            case MOTION_STATE_SWIM_DASH:
                if (previousState != MotionState.MOTION_STATE_SWIM_DASH) {
                    updateStaminaRelative(session, new Consumption(ConsumptionType.SWIM_DASH_START), true);
                }
                break;
        }
    }

    private void handleImmediateStamina(GameSession session, int skillId) {
        Consumption consumption = getFightConsumption(skillId);
        updateStaminaRelative(session, consumption, true);
    }

    private class SustainedStaminaHandler extends TimerTask {
        public void run() {
            boolean moving = isPlayerMoving();
            int currentCharacterStamina = getCurrentCharacterStamina();
            int maxCharacterStamina = getMaxCharacterStamina();
            int currentVehicleStamina = getCurrentVehicleStamina();
            int maxVehicleStamina = getMaxVehicleStamina();
            if (moving || (currentCharacterStamina < maxCharacterStamina) || (currentVehicleStamina < maxVehicleStamina)) {
                logger.trace("Player moving: " + moving + ", stamina full: " +
                        (currentCharacterStamina >= maxCharacterStamina) + ", recalculate stamina");
                boolean isCharacterStamina = true;
                Consumption consumption;
                if (MotionStatesCategorized.get("CLIMB").contains(currentState)) {
                    consumption = getClimbConsumption();
                } else if (MotionStatesCategorized.get("DASH").contains(currentState)) {
                    consumption = getDashConsumption();
                } else if (MotionStatesCategorized.get("FLY").contains(currentState)) {
                    consumption = getFlyConsumption();
                } else if (MotionStatesCategorized.get("RUN").contains(currentState)) {
                    consumption = new Consumption(ConsumptionType.RUN);
                } else if (MotionStatesCategorized.get("SKIFF").contains(currentState)) {
                    consumption = getSkiffConsumption();
                    isCharacterStamina = false;
                } else if (MotionStatesCategorized.get("STANDBY").contains(currentState)) {
                    consumption = new Consumption(ConsumptionType.STANDBY);
                } else if (MotionStatesCategorized.get("SWIM").contains(currentState)) {
                    consumption = getSwimConsumptions();
                } else if (MotionStatesCategorized.get("WALK").contains(currentState)) {
                    consumption = new Consumption(ConsumptionType.WALK);
                } else if (MotionStatesCategorized.get("NOCOST_NORECOVER").contains(currentState)) {
                    consumption = new Consumption();
                } else if (MotionStatesCategorized.get("OTHER").contains(currentState)) {
                    consumption = getOtherConsumptions();
                } else { // ignore
                    return;
                }

                if (consumption.amount < 0 && isCharacterStamina) {
                    // Do not apply reduction factor when recovering stamina
                    if (player.getTeamManager().getTeamResonances().contains(10301)) {
                        consumption.amount *= 0.85f;
                    }
                }
                // Delay 1 seconds before starts recovering stamina
                if (consumption.amount != 0 && cachedSession != null) {
                    if (consumption.amount < 0) {
                        staminaRecoverDelay = 0;
                    }
                    if (consumption.amount > 0
                            && consumption.type != ConsumptionType.POWERED_FLY
                            && consumption.type != ConsumptionType.POWERED_SKIFF) {
                        // For POWERED_* recover immediately - things like Amber's gliding exam and skiff challenges may require this.
                        if (staminaRecoverDelay < 5) {
                            // For others recover after 1 seconds (5 ticks) - as official server does.
                            staminaRecoverDelay++;
                            consumption.amount = 0;
                            logger.trace("Delaying recovery: " + staminaRecoverDelay);
                        }
                    }
                    updateStaminaRelative(cachedSession, consumption, isCharacterStamina);
                }
            }
            previousState = currentState;
            previousCoordinates = new Position(
                    currentCoordinates.getX(),
                    currentCoordinates.getY(),
                    currentCoordinates.getZ()
            );
        }
    }

    private void handleDrowning() {
        // TODO: fix drowning waverider entity
        int stamina = getCurrentCharacterStamina();
        if (stamina < 10) {
            logger.trace(getCurrentCharacterStamina() + "/" +
                    getMaxCharacterStamina() + "\t" + currentState);
            if (currentState != MotionState.MOTION_STATE_SWIM_IDLE) {
                killAvatar(cachedSession, cachedEntity, PlayerDieType.PLAYER_DIE_TYPE_DRAWN);
            }
        }
    }

    // Consumption Calculators

    // Stamina Consumption Reduction: https://genshin-impact.fandom.com/wiki/Stamina

    private Consumption getFightConsumption(int skillCasting) {
        // Talent moving
        if (TalentMovements.contains(skillCasting)) {
            // TODO: recover 1000 if kamisato hits an enemy at the end of dashing
            return getTalentMovingSustainedCost(skillCasting);
        }
        // Bow avatar charged attack
        Avatar currentAvatar = player.getTeamManager().getCurrentAvatarEntity().getAvatar();

        switch (currentAvatar.getAvatarData().getWeaponType()) {
            case WEAPON_BOW:
                return getBowSustainedCost(skillCasting);
            case WEAPON_CLAYMORE:
                return getClaymoreSustainedCost(skillCasting);
            case WEAPON_CATALYST:
                return getCatalystCost(skillCasting);
            case WEAPON_POLE:
                return getPolearmCost(skillCasting);
            case WEAPON_SWORD_ONE_HAND:
                return getSwordCost(skillCasting);
        }

        return new Consumption();
    }

    private Consumption getClimbConsumption() {
        Consumption consumption = new Consumption();
        if (currentState == MotionState.MOTION_STATE_CLIMB && isPlayerMoving()) {
            consumption.type = ConsumptionType.CLIMBING;
            consumption.amount = ConsumptionType.CLIMBING.amount;
        }
        // Climbing specific reductions
        consumption.amount *= getFoodCostReductionFactor(ClimbFoodReductionMap);
        consumption.amount *= getTalentCostReductionFactor(ClimbTalentReductionMap);
        return consumption;
    }

    private Consumption getSwimConsumptions() {
        handleDrowning();
        Consumption consumption = new Consumption();
        if (currentState == MotionState.MOTION_STATE_SWIM_MOVE) {
            consumption.type = ConsumptionType.SWIMMING;
            consumption.amount = ConsumptionType.SWIMMING.amount;
        }
        if (currentState == MotionState.MOTION_STATE_SWIM_DASH) {
            consumption.type = ConsumptionType.SWIM_DASH;
            consumption.amount = ConsumptionType.SWIM_DASH.amount;
        }
        // Swimming specific reductions
        consumption.amount *= getFoodCostReductionFactor(SwimFoodReductionMap);
        consumption.amount *= getTalentCostReductionFactor(SwimTalentReductionMap);
        return consumption;
    }

    private Consumption getDashConsumption() {
        Consumption consumption = new Consumption();
        if (currentState == MotionState.MOTION_STATE_DASH) {
            consumption.type = ConsumptionType.DASH;
            consumption.amount = ConsumptionType.DASH.amount;
            // Dashing specific reductions
            consumption.amount *= getFoodCostReductionFactor(DashFoodReductionMap);
        }
        return consumption;
    }

    private Consumption getFlyConsumption() {
        // POWERED_FLY, e.g. wind tunnel
        if (currentState == MotionState.MOTION_STATE_POWERED_FLY) {
            return new Consumption(ConsumptionType.POWERED_FLY);
        }
        Consumption consumption = new Consumption(ConsumptionType.FLY);
        // Flying specific reductions
        consumption.amount *= getFoodCostReductionFactor(FlyFoodReductionMap);
        consumption.amount *= getTalentCostReductionFactor(FlyTalentReductionMap);
        return consumption;
    }

    private Consumption getSkiffConsumption() {
        // No known reduction for skiffing.
        return switch (currentState) {
            case MOTION_STATE_SKIFF_DASH -> new Consumption(ConsumptionType.SKIFF_DASH);
            case MOTION_STATE_SKIFF_POWERED_DASH -> new Consumption(ConsumptionType.POWERED_SKIFF);
            case MOTION_STATE_SKIFF_NORMAL -> new Consumption(ConsumptionType.SKIFF);
            default -> new Consumption();
        };
    }

    private Consumption getOtherConsumptions() {
        switch (currentState) {
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
        for (EntityAvatar entity : cachedSession.getPlayer().getTeamManager().getActiveTeam()) {
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
        if (lastSkillFirstTick) {
            lastSkillFirstTick = false;
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
                if (player.getTeamManager().getCurrentAvatarEntity().getAvatar().getProudSkillList().contains(162101)) {
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
