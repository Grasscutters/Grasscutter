package emu.grasscutter.game.managers.StaminaManager;

import ch.qos.logback.classic.Logger;
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
    public static final HashSet<Integer> TalentMovements = new HashSet<>(List.of(
            10013, // Kamisato Ayaka
            10413 // Mona
    ));

    // TODO: Get from somewhere else, instead of hard-coded here?
    public static final HashSet<Integer> ClaymoreSkills = new HashSet<>(List.of(
            10160, // Diluc, /=2
            10201, // Razor
            10241, // Beidou
            10341, // Noelle
            10401, // Chongyun
            10441, // Xinyan
            10511, // Eula
            10531, // Sayu
            10571 // Arataki Itto, = 0
    ));
    public static final HashSet<Integer> CatalystSkills = new HashSet<>(List.of(
            10060, // Lisa
            10070, // Barbara
            10271, // Ningguang
            10291, // Klee
            10411, // Mona
            10431, // Sucrose
            10481, // Yanfei
            10541, // Sangonomoiya Kokomi
            10581 // Yae Miko
    ));
    public static final HashSet<Integer> PolearmSkills = new HashSet<>(List.of(
            10231, // Xiangling
            10261, // Xiao
            10301, // Zhongli
            10451, // Rosaria
            10461, // Hu Tao
            10501, // Thoma
            10521, // Raiden Shogun
            10631, // Shenhe
            10641 // Yunjin
    ));
    public static final HashSet<Integer> SwordSkills = new HashSet<>(List.of(
            10024, // Kamisato Ayaka
            10031, // Jean
            10073, // Kaeya
            10321, // Bennett
            10337, // Tartaglia, melee stance (10332 switch to melee, 10336 switch to ranged stance)
            10351, // Qiqi
            10381, // Xingqiu
            10386, // Albedo
            10421, // Keqing, =-2500
            10471, // Kaedehara Kazuha
            10661, // Kamisato Ayato
            100553, // Lumine
            100540 // Aether
    ));
    public static final HashSet<Integer> BowSkills = new HashSet<>(List.of(
            10041, 10043, // Amber
            10221, 10223,// Venti
            10311, 10315, // Fischl
            10331, 10335, // Tartaglia, ranged stance
            10371, // Ganyu
            10391, 10394, // Diona
            10491, // Yoimiya
            10551, 10554, // Gorou
            10561, 10564, // Kojou Sara
            10621, // Aloy
            99998, 99999 // Yelan // TODO: get real values
    ));


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

    public int updateStaminaRelative(GameSession session, Consumption consumption) {
        int currentStamina = player.getProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA);
        if (consumption.amount == 0) {
            return currentStamina;
        }
        // notify will update
        for (Map.Entry<String, BeforeUpdateStaminaListener> listener : beforeUpdateStaminaListeners.entrySet()) {
            Consumption overriddenConsumption = listener.getValue().onBeforeUpdateStamina(consumption.type.toString(), consumption);
            if ((overriddenConsumption.type != consumption.type) && (overriddenConsumption.amount != consumption.amount)) {
                logger.debug("[StaminaManager] Stamina update relative(" +
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
        return setStamina(session, consumption.type.toString(), newStamina);
    }

    public int updateStaminaAbsolute(GameSession session, String reason, int newStamina) {
        int currentStamina = player.getProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA);
        // notify will update
        for (Map.Entry<String, BeforeUpdateStaminaListener> listener : beforeUpdateStaminaListeners.entrySet()) {
            int overriddenNewStamina = listener.getValue().onBeforeUpdateStamina(reason, newStamina);
            if (overriddenNewStamina != newStamina) {
                logger.debug("[StaminaManager] Stamina update absolute(" +
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
        // Ignore if skill not cast by not current active
        if (casterId != player.getTeamManager().getCurrentAvatarEntity().getId()) {
            return;
        }
        setSkillCast(skillId, casterId);
        // Handle immediate stamina cost
        if (ClaymoreSkills.contains(skillId)) {
            // Exclude claymore as their stamina cost starts when MixinStaminaCost gets in
            return;
        }
        // TODO: Differentiate normal attacks from charged attacks and exclude
        // TODO: Temporary: Exclude non-claymore attacks for now
        if (BowSkills.contains(skillId)
                || SwordSkills.contains(skillId)
                || PolearmSkills.contains(skillId)
                || CatalystSkills.contains(skillId)
        ) {
            return;
        }
        handleImmediateStamina(session, skillId);
    }

    public void handleMixinCostStamina(boolean isSwim) {
        // Talent moving and claymore avatar charged attack duration
        // logger.trace("abilityMixinCostStamina: isSwim: " + isSwim);
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
        Consumption consumption = getFightConsumption(skillId);
        updateStaminaRelative(session, consumption);
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
                            logger.trace("[StaminaManager] Delaying recovery: " + staminaRecoverDelay);
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
        if (BowSkills.contains(skillCasting)) {
            return getBowSustainedCost(skillCasting);
        }
        // Claymore avatar charged attack
        if (ClaymoreSkills.contains(skillCasting)) {
            return getClaymoreSustainedCost(skillCasting);
        }
        // Catalyst avatar charged attack
        if (CatalystSkills.contains(skillCasting)) {
            return getCatalystSustainedCost(skillCasting);
        }
        // Polearm avatar charged attack
        if (PolearmSkills.contains(skillCasting)) {
            return getPolearmSustainedCost(skillCasting);
        }
        // Sword avatar charged attack
        if (SwordSkills.contains(skillCasting)) {
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
        // No known reduction for skiffing.
        return new Consumption(ConsumptionType.SKIFF);
    }

    private Consumption getOtherConsumptions() {
        if (currentState == MotionState.MOTION_NOTIFY) {
            if (BowSkills.contains(lastSkillId)) {
                return new Consumption(ConsumptionType.FIGHT, 500);
            }
        }
        // TODO: Add other logic
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
                consumption.amount = 0;
                break;
            case 10160: // Diluc, with talent "Relentless" stamina cost is decreased by 50%
                // TODO: How to get talent status?
                consumption.amount /= 2;
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
