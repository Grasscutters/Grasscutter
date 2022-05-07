package emu.grasscutter.game.managers.MovementManager;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.props.LifeState;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.net.proto.EntityMoveInfoOuterClass;
import emu.grasscutter.net.proto.MotionInfoOuterClass.MotionInfo;
import emu.grasscutter.net.proto.MotionStateOuterClass.MotionState;
import emu.grasscutter.net.proto.PlayerDieTypeOuterClass.PlayerDieType;
import emu.grasscutter.net.proto.VectorOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.*;
import emu.grasscutter.utils.Position;
import org.jetbrains.annotations.NotNull;

import java.lang.Math;
import java.util.*;

public class MovementManager {

    public HashMap<String, HashSet<MotionState>> MotionStatesCategorized = new HashMap<>();

    private enum ConsumptionType {
        None(0),

        // consume
        CLIMB_START(-500),
        CLIMBING(-150),
        CLIMB_JUMP(-2500),
        DASH(-1800),
        SPRINT(-360),
        FLY(-60),
        SWIM_DASH_START(-200),
        SWIM_DASH(-200),
        SWIMMING(-80),
        FIGHT(0),

        // restore
        STANDBY(500),
        RUN(500),
        WALK(500),
        STANDBY_MOVE(500),
        POWERED_FLY(500);

        public final int amount;
        ConsumptionType(int amount) {
            this.amount = amount;
        }
    }

    private class Consumption {
        public ConsumptionType consumptionType;
        public int amount;
        public Consumption(ConsumptionType ct, int a) {
            consumptionType = ct;
            amount = a;
        }
        public Consumption(ConsumptionType ct) {
            this(ct, ct.amount);
        }
    }

    private MotionState previousState = MotionState.MOTION_STANDBY;
    private MotionState currentState = MotionState.MOTION_STANDBY;
    private Position previousCoordinates = new Position(0, 0, 0);
    private Position currentCoordinates = new Position(0, 0, 0);

    private final Player player;

    private float landSpeed = 0;
    private long landTimeMillisecond = 0;
    private Timer movementManagerTickTimer;
    private GameSession cachedSession = null;
    private GameEntity cachedEntity = null;
    private int staminaRecoverDelay = 0;
    private int skillCaster = 0;
    private int skillCasting = 0;

    public MovementManager(Player player) {
        previousCoordinates.add(new Position(0,0,0));
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

    public void handle(GameSession session, EntityMoveInfoOuterClass.EntityMoveInfo moveInfo, GameEntity entity) {
        if (movementManagerTickTimer == null) {
            movementManagerTickTimer = new Timer();
            movementManagerTickTimer.scheduleAtFixedRate(new MotionManagerTick(), 0, 200);
        }
        // cache info for later use in tick
        cachedSession = session;
        cachedEntity = entity;

        MotionInfo motionInfo = moveInfo.getMotionInfo();
        moveEntity(entity, moveInfo);
        VectorOuterClass.Vector posVector = motionInfo.getPos();
        Position newPos = new Position(posVector.getX(),
                posVector.getY(), posVector.getZ());;
        if (newPos.getX() != 0 && newPos.getY() != 0 && newPos.getZ() != 0) {
            currentCoordinates = newPos;
        }
        currentState = motionInfo.getState();
        Grasscutter.getLogger().debug("" + currentState + "\t" + (moveInfo.getIsReliable() ? "reliable" : ""));
        handleFallOnGround(motionInfo);
    }

    public void resetTimer() {
        Grasscutter.getLogger().debug("MovementManager ticker stopped");
        movementManagerTickTimer.cancel();
        movementManagerTickTimer = null;
    }

    private void moveEntity(GameEntity entity, EntityMoveInfoOuterClass.EntityMoveInfo moveInfo) {
        entity.getPosition().set(moveInfo.getMotionInfo().getPos());
        entity.getRotation().set(moveInfo.getMotionInfo().getRot());
        entity.setLastMoveSceneTimeMs(moveInfo.getSceneTime());
        entity.setLastMoveReliableSeq(moveInfo.getReliableSeq());
        entity.setMotionState(moveInfo.getMotionInfo().getState());
    }

    private boolean isPlayerMoving() {
        float diffX = currentCoordinates.getX() - previousCoordinates.getX();
        float diffY = currentCoordinates.getY() - previousCoordinates.getY();
        float diffZ = currentCoordinates.getZ() - previousCoordinates.getZ();
        // Grasscutter.getLogger().debug("isPlayerMoving: " + previousCoordinates + ", " + currentCoordinates + ", " + diffX + ", " + diffY + ", " + diffZ);
        return Math.abs(diffX) > 0.2  || Math.abs(diffY) > 0.1 || Math.abs(diffZ) > 0.2;
    }

    private int getCurrentStamina() {
        return player.getProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA);
    }

    private int getMaximumStamina() {
        return player.getProperty(PlayerProperty.PROP_MAX_STAMINA);
    }

    // Returns new stamina
    public int updateStamina(GameSession session, int amount) {
        int currentStamina = session.getPlayer().getProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA);
        if (amount == 0) {
            return currentStamina;
        }
        int playerMaxStamina = session.getPlayer().getProperty(PlayerProperty.PROP_MAX_STAMINA);
        int newStamina = currentStamina + amount;
        if (newStamina < 0) {
            newStamina = 0;
        }
        if (newStamina > playerMaxStamina) {
            newStamina = playerMaxStamina;
        }
        session.getPlayer().setProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA, newStamina);
        session.send(new PacketPlayerPropNotify(player, PlayerProperty.PROP_CUR_PERSIST_STAMINA));
        return newStamina;
    }

    private void handleFallOnGround(@NotNull MotionInfo motionInfo) {
        MotionState state = motionInfo.getState();
        // land speed and fall on ground event arrive in different packets
        // cache land speed
        if (state == MotionState.MOTION_LAND_SPEED) {
            landSpeed = motionInfo.getSpeed().getY();
            landTimeMillisecond = System.currentTimeMillis();
        }
        if (state == MotionState.MOTION_FALL_ON_GROUND) {
            // if not received immediately after MOTION_LAND_SPEED, discard this packet.
            // TODO: Test in high latency.
            int maxDelay = 200;
            if ((System.currentTimeMillis() - landTimeMillisecond) > maxDelay) {
                Grasscutter.getLogger().debug("MOTION_FALL_ON_GROUND received after " + maxDelay + "ms, discard.");
                return;
            }
            float currentHP = cachedEntity.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP);
            float maxHP = cachedEntity.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP);
            float damage = 0;
            Grasscutter.getLogger().debug("LandSpeed: " + landSpeed);
            if (landSpeed < -23.5) {
                damage = (float)(maxHP * 0.33);
            }
            if (landSpeed < -25) {
                damage = (float)(maxHP * 0.5);
            }
            if (landSpeed < -26.5) {
                damage = (float)(maxHP * 0.66);
            }
            if (landSpeed < -28) {
                damage = (maxHP * 1);
            }
            float newHP = currentHP - damage;
            if (newHP < 0) {
                newHP = 0;
            }
            Grasscutter.getLogger().debug("Max: " + maxHP + "\tCurr: " + currentHP + "\tDamage: " + damage + "\tnewHP: " + newHP);
            cachedEntity.setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, newHP);
            cachedEntity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(cachedEntity, FightProperty.FIGHT_PROP_CUR_HP));
            if (newHP == 0) {
                killAvatar(cachedSession, cachedEntity, PlayerDieType.PLAYER_DIE_FALL);
            }
            landSpeed = 0;
        }
    }

    private void handleDrowning() {
        int stamina = getCurrentStamina();
        if (stamina < 10) {
            boolean isSwimming = MotionStatesCategorized.get("SWIM").contains(currentState);
            Grasscutter.getLogger().debug(player.getProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA) + "/" + player.getProperty(PlayerProperty.PROP_MAX_STAMINA) + "\t" + currentState + "\t" + isSwimming);
            if (isSwimming && currentState != MotionState.MOTION_SWIM_IDLE) {
                killAvatar(cachedSession, cachedEntity, PlayerDieType.PLAYER_DIE_DRAWN);
            }
        }
    }

    public void killAvatar(GameSession session, GameEntity entity, PlayerDieType dieType) {
        cachedSession.send(new PacketAvatarLifeStateChangeNotify(
                cachedSession.getPlayer().getTeamManager().getCurrentAvatarEntity().getAvatar(),
                LifeState.LIFE_DEAD,
                dieType
        ));
        cachedSession.send(new PacketLifeStateChangeNotify(
                cachedEntity,
                LifeState.LIFE_DEAD,
                dieType
        ));
        cachedEntity.setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, 0);
        cachedEntity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(cachedEntity, FightProperty.FIGHT_PROP_CUR_HP));
        entity.getWorld().broadcastPacket(new PacketLifeStateChangeNotify(0, entity, LifeState.LIFE_DEAD));
        session.getPlayer().getScene().removeEntity(entity);
        ((EntityAvatar)entity).onDeath(dieType, 0);
    }

    private class MotionManagerTick extends TimerTask
    {
        public void run() {
            if (Grasscutter.getConfig().OpenStamina) {
                boolean moving = isPlayerMoving();
                if (moving || (getCurrentStamina() < getMaximumStamina())) {
                    // Grasscutter.getLogger().debug("Player moving: " + moving + ", stamina full: " + (getCurrentStamina() >= getMaximumStamina()) + ", recalculate stamina");
                    Consumption consumption = new Consumption(ConsumptionType.None);

                    // TODO: refactor these conditions.
                    if (MotionStatesCategorized.get("CLIMB").contains(currentState)) {
                        consumption = getClimbConsumption();
                    } else if (MotionStatesCategorized.get("SWIM").contains((currentState))) {
                        consumption = getSwimConsumptions();
                    } else if (MotionStatesCategorized.get("RUN").contains(currentState)) {
                        consumption = getRunWalkDashConsumption();
                    } else if (MotionStatesCategorized.get("FLY").contains(currentState)) {
                        consumption = getFlyConsumption();
                    } else if (MotionStatesCategorized.get("STANDBY").contains(currentState)) {
                        consumption = getStandConsumption();
                    } else if (MotionStatesCategorized.get("FIGHT").contains(currentState)) {
                        consumption = getFightConsumption();
                    }

                    // delay 2 seconds before start recovering - as official server does.
                    if (cachedSession != null) {
                        if (consumption.amount < 0) {
                            staminaRecoverDelay = 0;
                        }
                        if (consumption.amount > 0 && consumption.consumptionType != ConsumptionType.POWERED_FLY) {
                            if (staminaRecoverDelay < 10) {
                                staminaRecoverDelay++;
                                consumption = new Consumption(ConsumptionType.None);
                            }
                        }
                        // Grasscutter.getLogger().debug(getCurrentStamina() + "/" + getMaximumStamina() + "\t" + currentState + "\t" + "isMoving: " + isPlayerMoving() + "\t(" + consumption.consumptionType + "," + consumption.amount + ")");
                        updateStamina(cachedSession, consumption.amount);
                    }

                    // tick triggered
                    handleDrowning();
                }
            }

            previousState = currentState;
            previousCoordinates = new Position(currentCoordinates.getX(),
                    currentCoordinates.getY(), currentCoordinates.getZ());;
        }
    }

    private Consumption getClimbConsumption() {
        Consumption consumption = new Consumption(ConsumptionType.None);
        if (currentState == MotionState.MOTION_CLIMB) {
            consumption = new Consumption(ConsumptionType.CLIMBING);
            if (previousState != MotionState.MOTION_CLIMB && previousState != MotionState.MOTION_CLIMB_JUMP) {
                consumption = new Consumption(ConsumptionType.CLIMB_START);
            }
            if (!isPlayerMoving()) {
                consumption = new Consumption(ConsumptionType.None);
            }
        }
        if (currentState == MotionState.MOTION_CLIMB_JUMP) {
            if (previousState != MotionState.MOTION_CLIMB_JUMP) {
                consumption = new Consumption(ConsumptionType.CLIMB_JUMP);
            }
        }
        return consumption;
    }

    private Consumption getSwimConsumptions() {
        Consumption consumption = new Consumption(ConsumptionType.None);
        if (currentState == MotionState.MOTION_SWIM_MOVE) {
            consumption = new Consumption(ConsumptionType.SWIMMING);
        }
        if (currentState == MotionState.MOTION_SWIM_DASH) {
            consumption = new Consumption(ConsumptionType.SWIM_DASH_START);
            if (previousState == MotionState.MOTION_SWIM_DASH) {
                consumption = new Consumption(ConsumptionType.SWIM_DASH);
            }
        }
        return consumption;
    }

    private Consumption getRunWalkDashConsumption() {
        Consumption consumption = new Consumption(ConsumptionType.None);
        if (currentState == MotionState.MOTION_DASH_BEFORE_SHAKE) {
            consumption = new Consumption(ConsumptionType.DASH);
            if (previousState == MotionState.MOTION_DASH_BEFORE_SHAKE) {
                // only charge once
                consumption = new Consumption(ConsumptionType.SPRINT);
            }
        }
        if (currentState == MotionState.MOTION_DASH) {
            consumption = new Consumption(ConsumptionType.SPRINT);
        }
        if (currentState == MotionState.MOTION_RUN) {
            consumption = new Consumption(ConsumptionType.RUN);
        }
        if (currentState == MotionState.MOTION_WALK) {
            consumption = new Consumption(ConsumptionType.WALK);
        }
        return consumption;
    }

    private Consumption getFlyConsumption() {
        Consumption consumption = new Consumption(ConsumptionType.FLY);
        HashMap<Integer, Float> glidingCostReduction = new HashMap<>() {{
            put(212301, 0.8f); // Amber
            put(222301, 0.8f); // Venti
        }};
        float reduction = 1;
        for (EntityAvatar entity: cachedSession.getPlayer().getTeamManager().getActiveTeam()) {
            for (int skillId: entity.getAvatar().getProudSkillList()) {
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

    private Consumption getStandConsumption() {
        Consumption consumption = new Consumption(ConsumptionType.None);
        if (currentState == MotionState.MOTION_STANDBY) {
            consumption = new Consumption(ConsumptionType.STANDBY);
        }
        if (currentState == MotionState.MOTION_STANDBY_MOVE) {
            consumption = new Consumption(ConsumptionType.STANDBY_MOVE);
        }
        return consumption;
    }

    private Consumption getFightConsumption() {
        Consumption consumption = new Consumption(ConsumptionType.None);
        HashMap<Integer, Integer> fightingCost = new HashMap<>() {{
            put(10013, -1000); // Kamisato Ayaka
            put(10413, -1000); // Mona
        }};
        if (fightingCost.containsKey(skillCasting)) {
            consumption = new Consumption(ConsumptionType.FIGHT, fightingCost.get(skillCasting));
            // only handle once, so reset.
            skillCasting = 0;
            skillCaster = 0;
        }
        return consumption;
    }

    public void notifySkill(int caster, int skillId) {
        skillCaster = caster;
        skillCasting = skillId;
    }
}

