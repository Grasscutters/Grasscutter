package emu.grasscutter.game.managers.MotionManager;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.props.LifeState;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.net.proto.EntityMoveInfoOuterClass;
import emu.grasscutter.net.proto.MotionStateOuterClass.MotionState;
import emu.grasscutter.net.proto.VectorOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketEntityFightPropUpdateNotify;
import emu.grasscutter.server.packet.send.PacketLifeStateChangeNotify;
import emu.grasscutter.server.packet.send.PacketPlayerPropNotify;
import emu.grasscutter.utils.Position;

import java.util.ArrayList;
import java.lang.Math;

public class MotionManager {

    private enum Consumption {
        None(0),

        // consumers
        CLIMB_START(-500),
        CLIMBING(-150),
        CLIMB_JUMP(-2500),
        DASH(-1800),
        SPRINT(-360),
        FLY(-60),
        SWIM_DASH_START(-200),
        SWIM_DASH(-200),
        SWIMMING(-80),

        // restorers
        STANDBY(500),
        RUN(500),
        WALK(500),
        STANDBY_MOVE(500);

        public final int amount;
        Consumption(int amount) {
            this.amount = amount;
        }
    }

    private EntityMoveInfoOuterClass.EntityMoveInfo moveInfo;

    private MotionState previousState = MotionState.MOTION_STANDBY;
    private ArrayList<Position> previousCoordinates = new ArrayList<>();
    private final Player player;

    private float landSpeed = 0;

    public MotionManager(Player player) {
        previousCoordinates.add(new Position(0,0,0));
        this.player = player;
    }

    public void handle(GameSession session, GameEntity entity, EntityMoveInfoOuterClass.EntityMoveInfo moveInfo) {
        MotionState state = moveInfo.getMotionInfo().getState();
        setMoveInfo(moveInfo);
        if (state == MotionState.MOTION_LAND_SPEED) {
            setLandSpeed(moveInfo.getMotionInfo().getSpeed().getY());
        }
        if (state == MotionState.MOTION_FALL_ON_GROUND) {
            handleFallOnGround(session, entity);
        }
    }

    public void tick() {
        if(Grasscutter.getConfig().OpenStamina){
            EntityMoveInfoOuterClass.EntityMoveInfo mInfo = moveInfo;
            if (mInfo == null) {
                return;
            }

            MotionState state = moveInfo.getMotionInfo().getState();
            Consumption consumption = Consumption.None;

            boolean isMoving = false;
            VectorOuterClass.Vector posVector = moveInfo.getMotionInfo().getPos();
            Position currentCoordinates = new Position(posVector.getX(), posVector.getY(), posVector.getZ());

            float diffX = currentCoordinates.getX() - previousCoordinates.get(0).getX();
            float diffY = currentCoordinates.getY() - previousCoordinates.get(0).getY();
            float diffZ = currentCoordinates.getZ() - previousCoordinates.get(0).getZ();

            if (Math.abs(diffX) > 0.3  || Math.abs(diffY) > 0.3 || Math.abs(diffZ) > 0.3) {
                isMoving = true;
            }

            if (isMoving) {
                // TODO: refactor these conditions.
                // CLIMB
                if (state == MotionState.MOTION_CLIMB) {
                    if (previousState != MotionState.MOTION_CLIMB && previousState != MotionState.MOTION_CLIMB_JUMP) {
                        consumption = Consumption.CLIMB_START;
                    } else {
                        consumption = Consumption.CLIMBING;
                    }
                }
                // JUMP
                if (state == MotionState.MOTION_CLIMB_JUMP) {
                    if (previousState != MotionState.MOTION_CLIMB_JUMP) {
                        consumption = Consumption.CLIMB_JUMP;
                    }
                }
                if (state == MotionState.MOTION_JUMP) {
                    if (previousState == MotionState.MOTION_CLIMB) {
                        consumption = Consumption.CLIMB_JUMP;
                    }
                }
                // SWIM
                if (state == MotionState.MOTION_SWIM_MOVE) {
                    consumption = Consumption.SWIMMING;
                }
                if (state == MotionState.MOTION_SWIM_DASH) {
                    if (previousState != MotionState.MOTION_SWIM_DASH) {
                        consumption = Consumption.SWIM_DASH_START;
                    } else {
                        consumption = Consumption.SWIM_DASH;
                    }
                }
                // DASH
                if (state == MotionState.MOTION_DASH) {
                    if (previousState == MotionState.MOTION_DASH) {
                        consumption = Consumption.SPRINT;
                    } else {
                        consumption = Consumption.DASH;
                    }
                }
                // RUN and WALK
                if (state == MotionState.MOTION_RUN) {
                    consumption = Consumption.RUN;
                }
                if (state == MotionState.MOTION_WALK) {
                    consumption = Consumption.WALK;
                }
                // FLY
                if (state == MotionState.MOTION_FLY) {
                    consumption = Consumption.FLY;
                }
            }
            // STAND
            if (state == MotionState.MOTION_STANDBY) {
                consumption = Consumption.STANDBY;
            }
            if (state == MotionState.MOTION_STANDBY_MOVE) {
                consumption = Consumption.STANDBY_MOVE;
            }

            GameSession session = player.getSession();
            updateStamina(session, consumption.amount);
            session.send(new PacketPlayerPropNotify(session.getPlayer(), PlayerProperty.PROP_CUR_PERSIST_STAMINA));

            Grasscutter.getLogger().debug(session.getPlayer().getProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA) + " " + state + " " + isMoving + " " + consumption + " " + consumption.amount);

            previousState = state;
            previousCoordinates.add(currentCoordinates);
            if (previousCoordinates.size() > 3) {
                previousCoordinates.remove(0);
            }
        }
    }

    public void updateStamina(GameSession session, int amount) {
        if (amount == 0) {
            return;
        }
        int currentStamina = session.getPlayer().getProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA);
        int playerMaxStamina = session.getPlayer().getProperty(PlayerProperty.PROP_MAX_STAMINA);
        int newStamina = currentStamina + amount;
        if (newStamina < 0) {
            newStamina = 0;
        }
        if (newStamina > playerMaxStamina) {
            newStamina = playerMaxStamina;
        }
        session.getPlayer().setProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA, newStamina);
    }

    public void setMoveInfo(EntityMoveInfoOuterClass.EntityMoveInfo moveInfo) {
        this.moveInfo = moveInfo;
    }

    public EntityMoveInfoOuterClass.EntityMoveInfo getMoveInfo() {
        return moveInfo;
    }

    public void handleFallOnGround(GameSession session, GameEntity entity) {
        float currentHP = entity.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP);
        float maxHP = entity.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP);
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
        entity.setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, newHP);
        entity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_CUR_HP));
        if (newHP == 0) {
            entity.getWorld().broadcastPacket(new PacketLifeStateChangeNotify(0, entity, LifeState.LIFE_DEAD));
            session.getPlayer().getScene().removeEntity(entity);
            entity.onDeath(0);
        }
    }

    public void setLandSpeed(float landSpeed) {
        this.landSpeed = landSpeed;
    }
}
