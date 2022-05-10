package emu.grasscutter.game.managers.StaminaManager;

import emu.grasscutter.Grasscutter;
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

import java.lang.Math;
import java.util.*;

public class StaminaManager {

    // TODO: Skiff state detection?
    private final Player player;
    private final HashMap<String, HashSet<MotionState>> MotionStatesCategorized = new HashMap<>() {{
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
        Grasscutter.getLogger().trace("isPlayerMoving: " + previousCoordinates + ", " + currentCoordinates +
                ", " + diffX + ", " + diffY + ", " + diffZ);
        return Math.abs(diffX) > 0.3 || Math.abs(diffY) > 0.2 || Math.abs(diffZ) > 0.3;
    }

    public int updateStaminaRelative(GameSession session, Consumption consumption) {
        int currentStamina = player.getProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA);
        if (consumption.amount == 0) {
            return currentStamina;
        }
        // notify will update
        for (Map.Entry<String, BeforeUpdateStaminaListener> listener : beforeUpdateStaminaListeners.entrySet()) {
            Consumption overriddenConsumption = listener.getValue().onBeforeUpdateStamina(consumption.type.toString(), consumption);
            if ((overriddenConsumption.type != consumption.type) && (overriddenConsumption.amount != consumption.amount)) {
                Grasscutter.getLogger().debug("[StaminaManager] Stamina update relative(" +
                        consumption.type.toString() + ", " + consumption.amount + ") overridden to relative(" +
                        consumption.type.toString() + ", " + consumption.amount + ") by: " + listener.getKey());
                return currentStamina;
            }
        }
        int playerMaxStamina = player.getProperty(PlayerProperty.PROP_MAX_STAMINA);
        Grasscutter.getLogger().trace(currentStamina + "/" + playerMaxStamina + "\t" + currentState + "\t" +
                (isPlayerMoving() ? "moving" : "      ") + "\t(" + consumption.type + "," +
                consumption.amount + ")");
        int newStamina = currentStamina + consumption.amount;
        if (newStamina < 0) {
            newStamina = 0;
        } else if (newStamina > playerMaxStamina) {
            newStamina = playerMaxStamina;
        }
        return setStamina(session, consumption.type.toString(), newStamina);
    }

    public int updateStaminaAbsolute(GameSession session, String reason, int newStamina) {
        int currentStamina = player.getProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA);
        // notify will update
        for (Map.Entry<String, BeforeUpdateStaminaListener> listener : beforeUpdateStaminaListeners.entrySet()) {
            int overriddenNewStamina = listener.getValue().onBeforeUpdateStamina(reason, newStamina);
            if (overriddenNewStamina != newStamina) {
                Grasscutter.getLogger().debug("[StaminaManager] Stamina update absolute(" +
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
        return setStamina(session, reason, newStamina);
    }

    // Returns new stamina and sends PlayerPropNotify
    public int setStamina(GameSession session, String reason, int newStamina) {
        if (!Grasscutter.getConfig().OpenStamina) {
            newStamina = player.getProperty(PlayerProperty.PROP_MAX_STAMINA);
        }
        // set stamina
        player.setProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA, newStamina);
        session.send(new PacketPlayerPropNotify(player, PlayerProperty.PROP_CUR_PERSIST_STAMINA));
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
            Grasscutter.getLogger().debug("[MovementManager] SustainedStaminaHandlerTimer started");
        }
    }

    public void stopSustainedStaminaHandler() {
        if (sustainedStaminaHandlerTimer != null) {
            sustainedStaminaHandlerTimer.cancel();
            sustainedStaminaHandlerTimer = null;
            Grasscutter.getLogger().debug("[MovementManager] SustainedStaminaHandlerTimer stopped");
        }
    }

    // Handlers

    // External trigger handler

    public void handleEvtDoSkillSuccNotify(GameSession session, int skillId, int casterId) {
        // Ignore if skill not cast by not current active
        if (casterId != player.getTeamManager().getCurrentAvatarEntity().getId()) {
            return;
        }
        setSkillCast(skillId, casterId);
        handleImmediateStamina(session, skillId);
    }

    public void handleMixinCostStamina(boolean isSwim) {
        // Talent moving and claymore avatar charged attack duration
        // Grasscutter.getLogger().trace("abilityMixinCostStamina: isSwim: " + isSwim);
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
        // Grasscutter.getLogger().trace("" + currentState);
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
                    updateStaminaRelative(session, new Consumption(ConsumptionType.CLIMB_START));
                }
                break;
            case MOTION_DASH_BEFORE_SHAKE:
                if (previousState != MotionState.MOTION_DASH_BEFORE_SHAKE) {
                    updateStaminaRelative(session, new Consumption(ConsumptionType.SPRINT));
                }
                break;
            case MOTION_CLIMB_JUMP:
                if (previousState != MotionState.MOTION_CLIMB_JUMP) {
                    updateStaminaRelative(session, new Consumption(ConsumptionType.CLIMB_JUMP));
                }
                break;
            case MOTION_SWIM_DASH:
                if (previousState != MotionState.MOTION_SWIM_DASH) {
                    updateStaminaRelative(session, new Consumption(ConsumptionType.SWIM_DASH_START));
                }
                break;
        }
    }

    private void handleImmediateStamina(GameSession session, int skillId) {
        // Non-claymore avatar attacks
        // TODO: differentiate charged vs normal attack
        Consumption consumption = getFightConsumption(skillId);
        updateStaminaRelative(session, consumption);
    }

    private class SustainedStaminaHandler extends TimerTask {
        public void run() {
            boolean moving = isPlayerMoving();
            int currentStamina = player.getProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA);
            int maxStamina = player.getProperty(PlayerProperty.PROP_MAX_STAMINA);
            if (moving || (currentStamina < maxStamina)) {
                Grasscutter.getLogger().trace("Player moving: " + moving + ", stamina full: " +
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
                } else if (MotionStatesCategorized.get("SWIM").contains((currentState))) {
                    consumption = getSwimConsumptions();
                } else if (MotionStatesCategorized.get("WALK").contains((currentState))) {
                    consumption = new Consumption(ConsumptionType.WALK);
                } else if (MotionStatesCategorized.get("OTHER").contains((currentState))) {
                    consumption = getOtherConsumptions();
                } else {
                    // ignore
                    return;
                }
                if (consumption.amount < 0) {
                    /* Do not apply reduction factor when recovering stamina
                    TODO: Reductions that apply to all motion types:
                        Elemental Resonance
                            Wind: -15%
                        Skills
                            Diona E: -10% while shield lasts - applies to SP+MP
                            Barbara E: -12% while lasts - applies to SP+MP
                    */
                }
                // Delay 2 seconds before starts recovering stamina
                if (cachedSession != null) {
                    if (consumption.amount < 0) {
                        staminaRecoverDelay = 0;
                    }
                    if (consumption.amount > 0 && consumption.type != ConsumptionType.POWERED_FLY) {
                        // For POWERED_FLY recover immediately - things like Amber's gliding exam may require this.
                        if (staminaRecoverDelay < 10) {
                            // For others recover after 2 seconds (10 ticks) - as official server does.
                            staminaRecoverDelay++;
                            consumption.amount = 0;
                            Grasscutter.getLogger().trace("[StaminaManager] Delaying recovery: " + staminaRecoverDelay);
                        }
                    }
                    updateStaminaRelative(cachedSession, consumption);
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
        int stamina = player.getProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA);
        if (stamina < 10) {
            Grasscutter.getLogger().trace(player.getProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA) + "/" +
                    player.getProperty(PlayerProperty.PROP_MAX_STAMINA) + "\t" + currentState);
            if (currentState != MotionState.MOTION_SWIM_IDLE) {
                killAvatar(cachedSession, cachedEntity, PlayerDieType.PLAYER_DIE_DRAWN);
            }
        }
    }

    // Consumption Calculators

    // Stamina Consumption Reduction: https://genshin-impact.fandom.com/wiki/Stamina

    private Consumption getFightConsumption(int skillCasting) {
        /* TODO:
            Instead of handling here, consider call StaminaManager.updateStamina****() with a Consumption object with
                type=FIGHT and a modified amount when handling attacks for more accurate attack start/end time and
                other info. Handling it here could be very complicated.
            Charged attack
                Default:
                    Polearm: (-2500)
                    Claymore: (-4000 per second, -800 each tick)
                    Catalyst: (-5000)
                Talent:
                    Ningguang: When Ningguang is in possession of Star Jades, her Charged Attack does not consume Stamina. (Catalyst * 0)
                    Klee: When Jumpy Dumpty and Normal Attacks deal DMG, Klee has a 50% chance to obtain an Explosive Spark.
                            This Explosive Spark is consumed by the next Charged Attack, which costs no Stamina. (Catalyst * 0)
                Constellations:
                    Hu Tao: While in a Paramita Papilio state activated by Guide to Afterlife, Hu Tao's Charge Attacks do not consume Stamina. (Polearm * 0)
                Character Specific:
                    Keqing: (-2500)
                    Diluc: (Claymore * 0.5)
            Talent Moving: (Those are skills too)
                Ayaka: (-1000 initial) (-1500 per second) When the Cryo application at the end of Kamisato Art: Senho hits an opponent (+1000)
                Mona: (-1000 initial) (-1500 per second)
         */

        // TODO: Currently only handling Ayaka and Mona's talent moving initial costs.
        Consumption consumption = new Consumption();

        // Talent moving
        HashMap<Integer, List<Consumption>> talentMovementConsumptions = new HashMap<>() {{
            // List[0] = initial cost, [1] = sustained cost. Sustained costs are divided by 3 per second as MixinStaminaCost is triggered at 3Hz.
            put(10013, List.of(new Consumption(ConsumptionType.TALENT_DASH_START, -1000), new Consumption(ConsumptionType.TALENT_DASH, -500))); // Kamisato Ayaka
            put(10413, List.of(new Consumption(ConsumptionType.TALENT_DASH_START, -1000), new Consumption(ConsumptionType.TALENT_DASH, -500))); // Mona
        }};
        if (talentMovementConsumptions.containsKey(skillCasting)) {
            if (lastSkillFirstTick) {
                consumption = talentMovementConsumptions.get(skillCasting).get(0);
            } else {
                lastSkillFirstTick = false;
                consumption = talentMovementConsumptions.get(skillCasting).get(1);
            }
        }
        // TODO: Claymore avatar charged attack
        // HashMap<Integer, Integer> fightConsumptions = new HashMap<>();

        // TODO: Non-claymore avatar charged attack

        return consumption;
    }

    private Consumption getClimbConsumption() {
        Consumption consumption = new Consumption();
        if (currentState == MotionState.MOTION_CLIMB && isPlayerMoving()) {
            consumption.type = ConsumptionType.CLIMBING;
            consumption.amount = ConsumptionType.CLIMBING.amount;
        }
        // Climbing specific reductions
        // TODO: create a food cost reduction map
        HashMap<Integer, Float> foodReductionMap = new HashMap<>() {{
            // TODO: get real talent id
            put(0, 0.8f); // Sample food
        }};
        consumption.amount *= getFoodCostReductionFactor(foodReductionMap);

        HashMap<Integer, Float> talentReductionMap = new HashMap<>() {{
            // TODO: get real talent id
            put(0, 0.8f); // Xiao
        }};
        consumption.amount *= getTalentCostReductionFactor(talentReductionMap);
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
        // Reductions
        HashMap<Integer, Float> talentReductionMap = new HashMap<>() {{
            // TODO: get real talent id
            put(0, 0.8f); // Beidou
            put(1, 0.8f); // Sangonomiya Kokomi
        }};
        consumption.amount *= getTalentCostReductionFactor(talentReductionMap);
        return consumption;
    }

    private Consumption getDashConsumption() {
        Consumption consumption = new Consumption();
        if (currentState == MotionState.MOTION_DASH) {
            consumption.type = ConsumptionType.DASH;
            consumption.amount = ConsumptionType.DASH.amount;
            // TODO: Dashing specific reductions
            //     Foods:
        }
        return consumption;
    }

    private Consumption getFlyConsumption() {
        // POWERED_FLY, e.g. wind tunnel
        if (currentState == MotionState.MOTION_POWERED_FLY) {
            return new Consumption(ConsumptionType.POWERED_FLY);
        }
        Consumption consumption = new Consumption(ConsumptionType.FLY);
        // Passive Talents
        HashMap<Integer, Float> talentReductionMap = new HashMap<>() {{
            put(212301, 0.8f); // Amber
            put(222301, 0.8f); // Venti
        }};
        consumption.amount *= getTalentCostReductionFactor(talentReductionMap);
        // TODO: Foods
        return consumption;
    }

    private Consumption getSkiffConsumption() {
        // POWERED_SKIFF, e.g. wind tunnel
        if (currentState == MotionState.MOTION_SKIFF_POWERED_DASH) {
            return new Consumption(ConsumptionType.POWERED_SKIFF);
        }
        Consumption consumption = new Consumption(ConsumptionType.SKIFF);
        // No known reduction for skiffing.
        return consumption;
    }

    private Consumption getOtherConsumptions() {
        // TODO: Add logic
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
}
