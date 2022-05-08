package emu.grasscutter.game.managers.StaminaManager;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.props.LifeState;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.net.proto.EntityMoveInfoOuterClass.EntityMoveInfo;
import emu.grasscutter.net.proto.EvtDoSkillSuccNotifyOuterClass.EvtDoSkillSuccNotify;
import emu.grasscutter.net.proto.MotionInfoOuterClass.MotionInfo;
import emu.grasscutter.net.proto.MotionStateOuterClass.MotionState;
import emu.grasscutter.net.proto.PlayerDieTypeOuterClass.PlayerDieType;
import emu.grasscutter.net.proto.VectorOuterClass.Vector;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.*;
import emu.grasscutter.utils.Position;

import java.lang.Math;
import java.util.*;

public class StaminaManager {
    private final Player player;
    private HashMap<String, HashSet<MotionState>> MotionStatesCategorized = new HashMap<>();

    public final static int GlobalMaximumStamina = 24000;
    private Position currentCoordinates = new Position(0, 0, 0);
    private Position previousCoordinates = new Position(0, 0, 0);
    private MotionState currentState = MotionState.MOTION_STANDBY;
    private MotionState previousState = MotionState.MOTION_STANDBY;
    private Timer sustainedStaminaHandlerTimer;
    private GameSession cachedSession = null;
    private GameEntity cachedEntity = null;
    private int staminaRecoverDelay = 0;
    private boolean isInSkillMove = false;




    public boolean getIsInSkillMove() {
        return isInSkillMove;
    }

    public void setIsInSkillMove(boolean b) {
        isInSkillMove = b;
    }

    public StaminaManager(Player player) {
        this.player = player;

        MotionStatesCategorized.put("SWIM", new HashSet<>(Arrays.asList(
            MotionState.MOTION_SWIM_MOVE,
            MotionState.MOTION_SWIM_IDLE,
            MotionState.MOTION_SWIM_DASH,
            MotionState.MOTION_SWIM_JUMP
        )));

        MotionStatesCategorized.put("STANDBY", new HashSet<>(Arrays.asList(
            MotionState.MOTION_STANDBY,
            MotionState.MOTION_STANDBY_MOVE,
            MotionState.MOTION_DANGER_STANDBY,
            MotionState.MOTION_DANGER_STANDBY_MOVE,
            MotionState.MOTION_LADDER_TO_STANDBY,
            MotionState.MOTION_JUMP_UP_WALL_FOR_STANDBY
        )));

        MotionStatesCategorized.put("CLIMB", new HashSet<>(Arrays.asList(
            MotionState.MOTION_CLIMB,
            MotionState.MOTION_CLIMB_JUMP,
            MotionState.MOTION_STANDBY_TO_CLIMB,
            MotionState.MOTION_LADDER_IDLE,
            MotionState.MOTION_LADDER_MOVE,
            MotionState.MOTION_LADDER_SLIP,
            MotionState.MOTION_STANDBY_TO_LADDER
        )));

        MotionStatesCategorized.put("FLY", new HashSet<>(Arrays.asList(
            MotionState.MOTION_FLY,
            MotionState.MOTION_FLY_IDLE,
            MotionState.MOTION_FLY_SLOW,
            MotionState.MOTION_FLY_FAST,
            MotionState.MOTION_POWERED_FLY
        )));

        MotionStatesCategorized.put("RUN", new HashSet<>(Arrays.asList(
            MotionState.MOTION_DASH,
            MotionState.MOTION_DANGER_DASH,
            MotionState.MOTION_DASH_BEFORE_SHAKE,
            MotionState.MOTION_RUN,
            MotionState.MOTION_DANGER_RUN,
            MotionState.MOTION_WALK,
            MotionState.MOTION_DANGER_WALK
        )));

        MotionStatesCategorized.put("FIGHT", new HashSet<>(Arrays.asList(
            MotionState.MOTION_FIGHT
        )));
    }

    private boolean isPlayerMoving() {
        float diffX = currentCoordinates.getX() - previousCoordinates.getX();
        float diffY = currentCoordinates.getY() - previousCoordinates.getY();
        float diffZ = currentCoordinates.getZ() - previousCoordinates.getZ();
        Grasscutter.getLogger().debug("isPlayerMoving: " + previousCoordinates + ", " + currentCoordinates +
                ", " + diffX + ", " + diffY + ", " + diffZ);
        return Math.abs(diffX) > 0.3 || Math.abs(diffY) > 0.2 || Math.abs(diffZ) > 0.3;
    }

    // Returns new stamina and sends PlayerPropNotify
    public int updateStamina(GameSession session, Consumption consumption) {
        int currentStamina = player.getProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA);
        if (consumption.amount == 0) {
            return currentStamina;
        }
        int playerMaxStamina = player.getProperty(PlayerProperty.PROP_MAX_STAMINA);
        Grasscutter.getLogger().debug(currentStamina + "/" + playerMaxStamina + "\t" + currentState + "\t" +
                (isPlayerMoving() ? "moving" : "      ") + "\t(" + consumption.consumptionType + "," +
                consumption.amount + ")");
        int newStamina = currentStamina + consumption.amount;
        if (newStamina < 0) {
            newStamina = 0;
        }
        if (newStamina > playerMaxStamina) {
            newStamina = playerMaxStamina;
        }
        player.setProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA, newStamina);
        session.send(new PacketPlayerPropNotify(player, PlayerProperty.PROP_CUR_PERSIST_STAMINA));
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
        if (!player.isPaused()) {
            if (sustainedStaminaHandlerTimer == null) {
                sustainedStaminaHandlerTimer = new Timer();
                sustainedStaminaHandlerTimer.scheduleAtFixedRate(new SustainedStaminaHandler(), 0, 200);
                Grasscutter.getLogger().debug("[MovementManager] SustainedStaminaHandlerTimer started");
            }
        }

    }

    public void stopSustainedStaminaHandler() {
        Grasscutter.getLogger().debug("[MovementManager] SustainedStaminaHandlerTimer stopped");
        sustainedStaminaHandlerTimer.cancel();
        sustainedStaminaHandlerTimer = null;
    }

    // Handlers

    // External trigger handler

    public void handleEvtDoSkillSuccNotify(GameSession session, EvtDoSkillSuccNotify notify) {
        handleImmediateStamina(session, notify);
    }

    public void handleCombatInvocationsNotify(GameSession session, EntityMoveInfo moveInfo, GameEntity entity) {
        // cache info for later use in SustainedStaminaHandler tick
        cachedSession = session;
        cachedEntity = entity;
        MotionInfo motionInfo = moveInfo.getMotionInfo();
        MotionState motionState = motionInfo.getState();
        boolean isReliable = moveInfo.getIsReliable();
        Grasscutter.getLogger().trace("" + motionState + "\t" + (isReliable ? "reliable" : ""));
        if (isReliable) {
            currentState = motionState;
            Vector posVector = motionInfo.getPos();
            Position newPos = new Position(posVector.getX(), posVector.getY(), posVector.getZ());
            if (newPos.getX() != 0 && newPos.getY() != 0 && newPos.getZ() != 0) {
                currentCoordinates = newPos;
            }
        }
        startSustainedStaminaHandler();
        handleImmediateStamina(session, motionInfo, motionState, entity);
    }

    // Internal handler

    private void handleImmediateStamina(GameSession session, MotionInfo motionInfo, MotionState motionState,
                                        GameEntity entity) {
        switch (motionState) {
            case MOTION_DASH_BEFORE_SHAKE:
                if (previousState != MotionState.MOTION_DASH_BEFORE_SHAKE) {
                    updateStamina(session, new Consumption(ConsumptionType.SPRINT));
                }
                break;
            case MOTION_CLIMB_JUMP:
                if (previousState != MotionState.MOTION_CLIMB_JUMP) {
                    updateStamina(session, new Consumption(ConsumptionType.CLIMB_JUMP));
                }
                break;
            case MOTION_SWIM_DASH:
                if (previousState != MotionState.MOTION_SWIM_DASH) {
                    updateStamina(session, new Consumption(ConsumptionType.SWIM_DASH_START));
                }
                break;
        }
    }

    private void handleImmediateStamina(GameSession session, EvtDoSkillSuccNotify notify) {
        Consumption consumption = getFightConsumption(notify.getSkillId());
        updateStamina(session, consumption);
    }

    private class SustainedStaminaHandler extends TimerTask {
        public void run() {
            if (Grasscutter.getConfig().OpenStamina) {
                boolean moving = isPlayerMoving();
                int currentStamina = player.getProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA);
                int maxStamina = player.getProperty(PlayerProperty.PROP_MAX_STAMINA);
                if (moving || (currentStamina < maxStamina)) {
                    Grasscutter.getLogger().debug("Player moving: " + moving + ", stamina full: " +
                            (currentStamina >= maxStamina) + ", recalculate stamina");
                    Consumption consumption = new Consumption(ConsumptionType.None);
                    if (!isInSkillMove) {
                        if (MotionStatesCategorized.get("CLIMB").contains(currentState)) {
                            consumption = getClimbSustainedConsumption();
                        } else if (MotionStatesCategorized.get("SWIM").contains((currentState))) {
                            consumption = getSwimSustainedConsumptions();
                        } else if (MotionStatesCategorized.get("RUN").contains(currentState)) {
                            consumption = getRunWalkDashSustainedConsumption();
                        } else if (MotionStatesCategorized.get("FLY").contains(currentState)) {
                            consumption = getFlySustainedConsumption();
                        } else if (MotionStatesCategorized.get("STANDBY").contains(currentState)) {
                            consumption = getStandSustainedConsumption();
                        }
                    }
                    if (cachedSession != null) {
                        if (consumption.amount < 0) {
                            staminaRecoverDelay = 0;
                        }
                        if (consumption.amount > 0 && consumption.consumptionType != ConsumptionType.POWERED_FLY) {
                            // For POWERED_FLY recover immediately - things like Amber's gliding exam may require this.
                            if (staminaRecoverDelay < 10) {
                                // For others recover after 2 seconds (10 ticks) - as official server does.
                                staminaRecoverDelay++;
                                consumption = new Consumption(ConsumptionType.None);
                            }
                        }
                        updateStamina(cachedSession, consumption);
                    }
                    handleDrowning();
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
            boolean isSwimming = MotionStatesCategorized.get("SWIM").contains(currentState);
            Grasscutter.getLogger().debug(player.getProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA) + "/" +
                    player.getProperty(PlayerProperty.PROP_MAX_STAMINA) + "\t" + currentState + "\t" + isSwimming);
            if (isSwimming && currentState != MotionState.MOTION_SWIM_IDLE) {
                killAvatar(cachedSession, cachedEntity, PlayerDieType.PLAYER_DIE_DRAWN);
            }
        }
    }

    // Consumption Calculators

    private Consumption getFightConsumption(int skillCasting) {
        Consumption consumption = new Consumption(ConsumptionType.None);
        HashMap<Integer, Integer> fightingCost = new HashMap<>() {{
            put(10013, -1000); // Kamisato Ayaka
            put(10413, -1000); // Mona
        }};
        if (fightingCost.containsKey(skillCasting)) {
            consumption = new Consumption(ConsumptionType.FIGHT, fightingCost.get(skillCasting));
        }
        return consumption;
    }

    private Consumption getClimbSustainedConsumption() {
        Consumption consumption = new Consumption(ConsumptionType.None);
        if (currentState == MotionState.MOTION_CLIMB && isPlayerMoving()) {
            consumption = new Consumption(ConsumptionType.CLIMBING);
            if (previousState != MotionState.MOTION_CLIMB && previousState != MotionState.MOTION_CLIMB_JUMP) {
                consumption = new Consumption(ConsumptionType.CLIMB_START);
            }
        }
        return consumption;
    }

    private Consumption getSwimSustainedConsumptions() {
        Consumption consumption = new Consumption(ConsumptionType.None);
        if (currentState == MotionState.MOTION_SWIM_MOVE) {
            consumption = new Consumption(ConsumptionType.SWIMMING);
        }
        if (currentState == MotionState.MOTION_SWIM_DASH) {
            consumption = new Consumption(ConsumptionType.SWIM_DASH);
        }
        return consumption;
    }

    private Consumption getRunWalkDashSustainedConsumption() {
        Consumption consumption = new Consumption(ConsumptionType.None);
        if (currentState == MotionState.MOTION_DASH) {
            consumption = new Consumption(ConsumptionType.DASH);
        }
        if (currentState == MotionState.MOTION_RUN) {
            consumption = new Consumption(ConsumptionType.RUN);
        }
        if (currentState == MotionState.MOTION_WALK) {
            consumption = new Consumption(ConsumptionType.WALK);
        }
        return consumption;
    }

    private Consumption getFlySustainedConsumption() {
        Consumption consumption = new Consumption(ConsumptionType.FLY);
        HashMap<Integer, Float> glidingCostReduction = new HashMap<>() {{
            put(212301, 0.8f); // Amber
            put(222301, 0.8f); // Venti
        }};
        float reduction = 1;
        for (EntityAvatar entity : cachedSession.getPlayer().getTeamManager().getActiveTeam()) {
            for (int skillId : entity.getAvatar().getProudSkillList()) {
                if (glidingCostReduction.containsKey(skillId)) {
                    reduction = glidingCostReduction.get(skillId);
                }
            }
        }
        consumption.amount *= reduction;
        // POWERED_FLY, e.g. wind tunnel
        if (currentState == MotionState.MOTION_POWERED_FLY) {
            consumption = new Consumption(ConsumptionType.POWERED_FLY);
        }
        return consumption;
    }

    private Consumption getStandSustainedConsumption() {
        Consumption consumption = new Consumption(ConsumptionType.None);
        if (currentState == MotionState.MOTION_STANDBY) {
            consumption = new Consumption(ConsumptionType.STANDBY);
        }
        if (currentState == MotionState.MOTION_STANDBY_MOVE) {
            consumption = new Consumption(ConsumptionType.STANDBY_MOVE);
        }
        return consumption;
    }
}
