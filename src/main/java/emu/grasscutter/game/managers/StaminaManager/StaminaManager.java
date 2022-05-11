package emu.grasscutter.game.managers.StaminaManager;

import ch.qos.logback.classic.Logger;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.props.LifeState;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.net.proto.EntityMoveInfoOuterClass.EntityMoveInfo;
import emu.grasscutter.net.proto.MotionInfoOuterClass.MotionInfo;
import emu.grasscutter.net.proto.MotionStateOuterClass.MotionState;
import emu.grasscutter.net.proto.PlayerDieTypeOuterClass.PlayerDieType;
import emu.grasscutter.net.proto.VectorOuterClass.Vector;
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
        put("CLIMB", new HashSet<>(List.of(
                MotionState.MOTION_CLIMB, // sustained, when not moving no cost no recover
                MotionState.MOTION_STANDBY_TO_CLIMB // NOT OBSERVED, see MOTION_JUMP_UP_WALL_FOR_STANDBY
        )));
        put("DASH", new HashSet<>(List.of(
                MotionState.MOTION_DANGER_DASH, // sustained
                MotionState.MOTION_DASH // sustained
        )));
        put("FLY", new HashSet<>(List.of(
                MotionState.MOTION_FLY, // sustained
                MotionState.MOTION_FLY_FAST, // sustained
                MotionState.MOTION_FLY_SLOW, // sustained
                MotionState.MOTION_POWERED_FLY // sustained, recover
        )));
        put("RUN", new HashSet<>(List.of(
                MotionState.MOTION_DANGER_RUN, // sustained, recover
                MotionState.MOTION_RUN // sustained, recover
        )));
        put("SKIFF", new HashSet<>(List.of(
                MotionState.MOTION_SKIFF_BOARDING, // NOT OBSERVED even when boarding
                MotionState.MOTION_SKIFF_DASH, // NOT OBSERVED even when dashing
                MotionState.MOTION_SKIFF_NORMAL, // sustained, OBSERVED when both normal and dashing
                MotionState.MOTION_SKIFF_POWERED_DASH // sustained, recover
        )));
        put("STANDBY", new HashSet<>(List.of(
                MotionState.MOTION_DANGER_STANDBY_MOVE, // sustained, recover
                MotionState.MOTION_DANGER_STANDBY, // sustained, recover
                MotionState.MOTION_LADDER_TO_STANDBY, // NOT OBSERVED
                MotionState.MOTION_STANDBY_MOVE, // sustained, recover
                MotionState.MOTION_STANDBY // sustained, recover
        )));
        put("SWIM", new HashSet<>(List.of(
                MotionState.MOTION_SWIM_IDLE, // sustained
                MotionState.MOTION_SWIM_DASH, // immediate and sustained
                MotionState.MOTION_SWIM_JUMP, // NOT OBSERVED
                MotionState.MOTION_SWIM_MOVE // sustained
        )));
        put("WALK", new HashSet<>(List.of(
                MotionState.MOTION_DANGER_WALK, // sustained, recover
                MotionState.MOTION_WALK // sustained, recover
        )));
        put("OTHER", new HashSet<>(List.of(
                MotionState.MOTION_CLIMB_JUMP, // cost only once if repeated without switching state
                MotionState.MOTION_DASH_BEFORE_SHAKE, // immediate one time sprint charge.
                MotionState.MOTION_FIGHT, // immediate, if sustained then subsequent will be MOTION_NOTIFY
                MotionState.MOTION_JUMP_UP_WALL_FOR_STANDBY, // immediate, observed when RUN/WALK->CLIMB
                MotionState.MOTION_NOTIFY, // can be either cost or recover - check previous state and check skill casting
                MotionState.MOTION_SIT_IDLE, // sustained, recover
                MotionState.MOTION_JUMP // recover
        )));
        put("NOCOST_NORECOVER", new HashSet<>(List.of(
                MotionState.MOTION_LADDER_SLIP, // NOT OBSERVED
                MotionState.MOTION_SLIP, // sustained, no cost no recover
                MotionState.MOTION_FLY_IDLE // NOT OBSERVED
        )));
        put("IGNORE", new HashSet<>(List.of(
                // these states have no impact on stamina
                MotionState.MOTION_CROUCH_IDLE,
                MotionState.MOTION_CROUCH_MOVE,
                MotionState.MOTION_CROUCH_ROLL,
                MotionState.MOTION_DESTROY_VEHICLE,
                MotionState.MOTION_FALL_ON_GROUND,
                MotionState.MOTION_FOLLOW_ROUTE,
                MotionState.MOTION_FORCE_SET_POS,
                MotionState.MOTION_GO_UPSTAIRS,
                MotionState.MOTION_JUMP_OFF_WALL,
                MotionState.MOTION_LADDER_IDLE,
                MotionState.MOTION_LADDER_MOVE,
                MotionState.MOTION_LAND_SPEED,
                MotionState.MOTION_MOVE_FAIL_ACK,
                MotionState.MOTION_NONE,
                MotionState.MOTION_NUM,
                MotionState.MOTION_QUEST_FORCE_DRAG,
                MotionState.MOTION_RESET,
                MotionState.MOTION_STANDBY_TO_LADDER,
                MotionState.MOTION_WATERFALL
        )));
    }};

    private final Logger logger = Grasscutter.getLogger();
    public final static int GlobalMaximumStamina = 24000;
    private Position currentCoordinates = new Position(0, 0, 0);
    private Position previousCoordinates = new Position(0, 0, 0);
    private MotionState currentState = MotionState.MOTION_STANDBY;
    private MotionState previousState = MotionState.MOTION_STANDBY;
    private Timer sustainedStaminaHandlerTimer;
    private GameSession cachedSession = null;
    private GameEntity cachedEntity = null;
    private int staminaRecoverDelay = 0;
    private final HashMap<String, BeforeUpdateStaminaListener> beforeUpdateStaminaListeners = new HashMap<>();
    private final HashMap<String, AfterUpdateStaminaListener> afterUpdateStaminaListeners = new HashMap<>();
    private int lastSkillId = 0;
    private int lastSkillCasterId = 0;
    private boolean lastSkillFirstTick = true;
    private static final HashSet<Integer> TalentMovements = new HashSet<>(List.of(
            10013, // Kamisato Ayaka
            10413 // Mona
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
        put(262301, 0.8f); // Xiao
    }};
    private static final HashMap<Integer, Float> FlyTalentReductionMap = new HashMap<>() {{
        put(212301, 0.8f); // Amber
        put(222301, 0.8f); // Venti
    }};
    private static final HashMap<Integer, Float> SwimTalentReductionMap = new HashMap<>() {{
        put(242301, 0.8f); // Beidou
        put(542301, 0.8f); // Sangonomiya Kokomi
    }};

    public static final HashSet<Integer> BowAvatars = new HashSet<>();
    public static final HashSet<Integer> CatalystAvatars = new HashSet<>();
    public static final HashSet<Integer> ClaymoreAvatars = new HashSet<>();
    public static final HashSet<Integer> PolearmAvatars = new HashSet<>();
    public static final HashSet<Integer> SwordAvatars = new HashSet<>();

    public static void initialize() {
        // Initialize skill categories
        GameData.getAvatarDataMap().forEach((avatarId, avatarData) -> {
            switch(avatarData.getWeaponType()) {
                case "WEAPON_BOW":
                    BowAvatars.add(avatarId);
                    break;
                case "WEAPON_CLAYMORE":
                    ClaymoreAvatars.add(avatarId);
                    break;
                case "WEAPON_CATALYST":
                    CatalystAvatars.add(avatarId);
                    break;
                case "WEAPON_POLE":
                    PolearmAvatars.add(avatarId);
                    break;
                case "WEAPON_SWORD_ONE_HAND":
                    SwordAvatars.add(avatarId);
                    break;
            }
            // TODO: Initialize foods etc.
        });
    }

    public StaminaManager(Player player) {
        this.player = player;
    }

    // Accessors

    public void setSkillCast(int skillId, int skillCasterId) {
        lastSkillFirstTick = true;
        lastSkillId = skillId;
        lastSkillCasterId = skillCasterId;
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

    public int updateStaminaRelative(GameSession session, Consumption consumption, PlayerProperty staminaType) {
        int currentStamina = player.getProperty(staminaType);
        if (consumption.amount == 0) {
            return currentStamina;
        }
        // notify will update
        for (Map.Entry<String, BeforeUpdateStaminaListener> listener : beforeUpdateStaminaListeners.entrySet()) {
            Consumption overriddenConsumption = listener.getValue().onBeforeUpdateStamina(consumption.type.toString(), consumption);
            if ((overriddenConsumption.type != consumption.type) && (overriddenConsumption.amount != consumption.amount)) {
                logger.debug("Stamina update relative(" +
                        consumption.type.toString() + ", " + consumption.amount + ") overridden to relative(" +
                        consumption.type.toString() + ", " + consumption.amount + ") by: " + listener.getKey());
                return currentStamina;
            }
        }
        int playerMaxStamina = player.getProperty(PlayerProperty.PROP_MAX_STAMINA);
        logger.trace(currentStamina + "/" + playerMaxStamina + "\t" + currentState + "\t" +
                (isPlayerMoving() ? "moving" : "      ") + "\t(" + consumption.type + "," +
                consumption.amount + ")");
        int newStamina = currentStamina + consumption.amount;
        if (newStamina < 0) {
            newStamina = 0;
        } else if (newStamina > playerMaxStamina) {
            newStamina = playerMaxStamina;
        }
        return setStamina(session, consumption.type.toString(), newStamina, staminaType);
    }

    public int updateStaminaAbsolute(GameSession session, String reason, int newStamina, PlayerProperty staminaType) {
        int currentStamina = player.getProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA);
        // notify will update
        for (Map.Entry<String, BeforeUpdateStaminaListener> listener : beforeUpdateStaminaListeners.entrySet()) {
            int overriddenNewStamina = listener.getValue().onBeforeUpdateStamina(reason, newStamina);
            if (overriddenNewStamina != newStamina) {
                logger.debug("Stamina update absolute(" +
                        reason + ", " + newStamina + ") overridden to absolute(" +
                        reason + ", " + newStamina + ") by: " + listener.getKey());
                return currentStamina;
            }
        }
        int playerMaxStamina = player.getProperty(PlayerProperty.PROP_MAX_STAMINA);
        if (newStamina < 0) {
            newStamina = 0;
        } else if (newStamina > playerMaxStamina) {
            newStamina = playerMaxStamina;
        }
        return setStamina(session, reason, newStamina, staminaType);
    }

    // Returns new stamina and sends PlayerPropNotify
    public int setStamina(GameSession session, String reason, int newStamina, PlayerProperty staminaType) {
        if (!GAME_OPTIONS.staminaUsage) {
            newStamina = player.getProperty(PlayerProperty.PROP_MAX_STAMINA);
        }
        // set stamina
        player.setProperty(staminaType, newStamina);
        if (staminaType == PlayerProperty.PROP_CUR_TEMPORARY_STAMINA) {
            // TODO: Implement
            // session.send(new PacketVehicleStaminaNotify(vehicleEntity, newStamina));
        } else {
            session.send(new PacketPlayerPropNotify(player, PlayerProperty.PROP_CUR_PERSIST_STAMINA));
        }
        // notify updated
        for (Map.Entry<String, AfterUpdateStaminaListener> listener : afterUpdateStaminaListeners.entrySet()) {
            listener.getValue().onAfterUpdateStamina(reason, newStamina);
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
        int currentAvatarId = player.getTeamManager().getCurrentAvatarEntity().getAvatar().getAvatarId();
        if (ClaymoreAvatars.contains(currentAvatarId)) {
            // Exclude claymore as their stamina cost starts when MixinStaminaCost gets in
            return;
        }
        // TODO: Differentiate normal attacks from charged attacks and exclude
        // TODO: Temporary: Exclude non-claymore attacks for now
        if (BowAvatars.contains(currentAvatarId)
                || SwordAvatars.contains(currentAvatarId)
                || PolearmAvatars.contains(currentAvatarId)
                || CatalystAvatars.contains(currentAvatarId)
        ) {
            return;
        }
        handleImmediateStamina(session, skillId);
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
        if (notifyEntityId != currentAvatarEntityId) {
            return;
        }
        currentState = motionState;
        // logger.trace("" + currentState);
        Vector posVector = motionInfo.getPos();
        Position newPos = new Position(posVector.getX(), posVector.getY(), posVector.getZ());
        if (newPos.getX() != 0 && newPos.getY() != 0 && newPos.getZ() != 0) {
            currentCoordinates = newPos;
        }
        startSustainedStaminaHandler();
        handleImmediateStamina(session, motionState);
    }

    // Internal handler

    private void handleImmediateStamina(GameSession session, @NotNull MotionState motionState) {
        switch (motionState) {
            case MOTION_CLIMB:
                if (currentState != MotionState.MOTION_CLIMB) {
                    updateStaminaRelative(session, new Consumption(ConsumptionType.CLIMB_START), PlayerProperty.PROP_CUR_PERSIST_STAMINA);
                }
                break;
            case MOTION_DASH_BEFORE_SHAKE:
                if (previousState != MotionState.MOTION_DASH_BEFORE_SHAKE) {
                    updateStaminaRelative(session, new Consumption(ConsumptionType.SPRINT), PlayerProperty.PROP_CUR_PERSIST_STAMINA);
                }
                break;
            case MOTION_CLIMB_JUMP:
                if (previousState != MotionState.MOTION_CLIMB_JUMP) {
                    updateStaminaRelative(session, new Consumption(ConsumptionType.CLIMB_JUMP), PlayerProperty.PROP_CUR_PERSIST_STAMINA);
                }
                break;
            case MOTION_SWIM_DASH:
                if (previousState != MotionState.MOTION_SWIM_DASH) {
                    updateStaminaRelative(session, new Consumption(ConsumptionType.SWIM_DASH_START), PlayerProperty.PROP_CUR_PERSIST_STAMINA);
                }
                break;
        }
    }

    private void handleImmediateStamina(GameSession session, int skillId) {
        Consumption consumption = getFightConsumption(skillId);
        updateStaminaRelative(session, consumption, PlayerProperty.PROP_CUR_PERSIST_STAMINA);
    }

    private class SustainedStaminaHandler extends TimerTask {
        public void run() {
            boolean moving = isPlayerMoving();
            int currentStamina = player.getProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA);
            int maxStamina = player.getProperty(PlayerProperty.PROP_MAX_STAMINA);
            if (moving || (currentStamina < maxStamina)) {
                logger.trace("Player moving: " + moving + ", stamina full: " +
                        (currentStamina >= maxStamina) + ", recalculate stamina");

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

                if (consumption.amount < 0) {
                    /* Do not apply reduction factor when recovering stamina
                    TODO: Reductions that apply to all motion types:
                        Skills
                            Diona E: -10% while shield lasts - applies to SP+MP
                            Barbara E: -12% while lasts - applies to SP+MP
                    */
                    // Elemental Resonance - Winds -15%
                    if (player.getTeamManager().getTeamResonances().contains(10301)) {
                         consumption.amount *= 0.85f;
                    }
                }
                // Delay 1 seconds before starts recovering stamina
                if (consumption.amount != 0 && cachedSession != null) {
                    if (consumption.amount < 0) {
                        staminaRecoverDelay = 0;
                    }
                    if (consumption.amount > 0 && consumption.type != ConsumptionType.POWERED_FLY) {
                        // For POWERED_FLY recover immediately - things like Amber's gliding exam may require this.
                        if (staminaRecoverDelay < 5) {
                            // For others recover after 1 seconds (5 ticks) - as official server does.
                            staminaRecoverDelay++;
                            consumption.amount = 0;
                            logger.trace("Delaying recovery: " + staminaRecoverDelay);
                        }
                    }
                    updateStaminaRelative(cachedSession, consumption, PlayerProperty.PROP_CUR_PERSIST_STAMINA);
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
        int stamina = player.getProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA);
        if (stamina < 10) {
            logger.trace(player.getProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA) + "/" +
                    player.getProperty(PlayerProperty.PROP_MAX_STAMINA) + "\t" + currentState);
            if (currentState != MotionState.MOTION_SWIM_IDLE) {
                killAvatar(cachedSession, cachedEntity, PlayerDieType.PLAYER_DIE_DRAWN);
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
        int currentAvatarId = player.getTeamManager().getCurrentAvatarEntity().getAvatar().getAvatarId();
        if (BowAvatars.contains(currentAvatarId)) {
            return getBowSustainedCost(skillCasting);
        }
        // Claymore avatar charged attack
        if (ClaymoreAvatars.contains(currentAvatarId)) {
            return getClaymoreSustainedCost(skillCasting);
        }
        // Catalyst avatar charged attack
        if (CatalystAvatars.contains(currentAvatarId)) {
            return getCatalystSustainedCost(skillCasting);
        }
        // Polearm avatar charged attack
        if (PolearmAvatars.contains(currentAvatarId)) {
            return getPolearmSustainedCost(skillCasting);
        }
        // Sword avatar charged attack
        if (SwordAvatars.contains(skillCasting)) {
            return getSwordSustainedCost(skillCasting);
        }
        return new Consumption();
    }

    private Consumption getClimbConsumption() {
        Consumption consumption = new Consumption();
        if (currentState == MotionState.MOTION_CLIMB && isPlayerMoving()) {
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
        if (currentState == MotionState.MOTION_SWIM_MOVE) {
            consumption.type = ConsumptionType.SWIMMING;
            consumption.amount = ConsumptionType.SWIMMING.amount;
        }
        if (currentState == MotionState.MOTION_SWIM_DASH) {
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
        if (currentState == MotionState.MOTION_DASH) {
            consumption.type = ConsumptionType.DASH;
            consumption.amount = ConsumptionType.DASH.amount;
            // Dashing specific reductions
            consumption.amount *= getFoodCostReductionFactor(DashFoodReductionMap);
        }
        return consumption;
    }

    private Consumption getFlyConsumption() {
        // POWERED_FLY, e.g. wind tunnel
        if (currentState == MotionState.MOTION_POWERED_FLY) {
            return new Consumption(ConsumptionType.POWERED_FLY);
        }
        Consumption consumption = new Consumption(ConsumptionType.FLY);
        // Flying specific reductions
        consumption.amount *= getFoodCostReductionFactor(FlyFoodReductionMap);
        consumption.amount *= getTalentCostReductionFactor(FlyTalentReductionMap);
        return consumption;
    }

    private Consumption getSkiffConsumption() {
        // POWERED_SKIFF, e.g. wind tunnel
        if (currentState == MotionState.MOTION_SKIFF_POWERED_DASH) {
            return new Consumption(ConsumptionType.POWERED_SKIFF);
        }
        // No known reduction for skiffing.
        return new Consumption(ConsumptionType.SKIFF);
    }

    private Consumption getOtherConsumptions() {
        switch (currentState) {
            case MOTION_NOTIFY:
//                if (BowSkills.contains(lastSkillId)) {
//                    return new Consumption(ConsumptionType.FIGHT, 500);
//                }
                break;
            case MOTION_FIGHT:
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

    private Consumption getCatalystSustainedCost(int skillId) {
        Consumption consumption = new Consumption(ConsumptionType.FIGHT, -5000);
        // Character specific handling
        switch (skillId) {
            // TODO: Yanfei
        }
        return consumption;
    }

    private Consumption getClaymoreSustainedCost(int skillId) {
        Consumption consumption = new Consumption(ConsumptionType.FIGHT, -1333); // 4000 / 3 = 1333
        // Character specific handling
        switch (skillId) {
            case 10571: // Arataki Itto, does not consume stamina at all.
            case 10532: // Sayu, windwheel does not consume stamina.
                consumption.amount = 0;
                break;
            case 10160: // Diluc, with talent "Relentless" stamina cost is decreased by 50%
                if (player.getTeamManager().getCurrentAvatarEntity().getAvatar().getProudSkillList().contains(162101)) {
                    consumption.amount /= 2;
                }
                break;
        }
        return consumption;
    }

    private Consumption getPolearmSustainedCost(int skillId) {
        Consumption consumption = new Consumption(ConsumptionType.FIGHT, -2500);
        // Character specific handling
        switch (skillId) {
            // TODO:
        }
        return consumption;
    }

    private Consumption getSwordSustainedCost(int skillId) {
        Consumption consumption = new Consumption(ConsumptionType.FIGHT, -2000);
        // Character specific handling
        switch (skillId) {
            case 10421: // Keqing, -2500
                consumption.amount = -2500;
                break;
        }
        return consumption;
    }
}
