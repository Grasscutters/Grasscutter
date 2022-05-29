package emu.grasscutter.game.managers.StaminaManager;

import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import org.jetbrains.annotations.NotNull;

import emu.grasscutter.net.proto.EntityMoveInfoOuterClass.EntityMoveInfo;
import emu.grasscutter.net.proto.MotionStateOuterClass.MotionState;
import emu.grasscutter.net.proto.PlayerDieTypeOuterClass.PlayerDieType;
import emu.grasscutter.net.proto.VehicleInteractTypeOuterClass.VehicleInteractType;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketAvatarLifeStateChangeNotify;
import emu.grasscutter.server.packet.send.PacketEntityFightPropUpdateNotify;
import emu.grasscutter.server.packet.send.PacketLifeStateChangeNotify;
import emu.grasscutter.server.packet.send.PacketPlayerPropNotify;
import emu.grasscutter.server.packet.send.PacketVehicleStaminaNotify;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.props.LifeState;
import emu.grasscutter.game.props.PlayerProperty;
import static emu.grasscutter.Configuration.GAME_OPTIONS;

public class StaminaManager {
    private final Player player;
    private final PlayerProperty StaminaProp;
    public final static int GlobalCharacterMaximumStamina = 24000;
    public final static int GlobalVehicleMaxStamina = 24000;

    private int stamina;
    private int vehicleStamina;

    private StaminaRelatedNotifyHandler notifyHandler;
    private ModifierList modifierList;
    private Consumption consumption = new Consumption();
    private Queue<Consumption> immediateConsumptionList;

    private int calDelay = 163;
    private int recoveryDelay = 1400;
    private Timer timer;
    private boolean staminaUpdated = true;
    private int time2Recover;

    private double speed;

    private boolean atSkiff = false;
    private boolean atRecovering = false;

    private int vehicleId = -1;
    private int notifyEntityId;
    private int skillCasterId;

    private GameSession session;
    private GameEntity entity;
    private DataUpdateInvoke dataUpdateInvoke;

    public static synchronized void initialize() {
        StaminaRelatedNotifyHandler.initialize();
    }

    public StaminaManager(Player player, PlayerProperty staminaProp, PlayerProperty maxStaminaProp) {
        this.player = player;
        this.StaminaProp = staminaProp;
        this.dataUpdateInvoke = new DataUpdateInvoke();
        this.stamina = GlobalCharacterMaximumStamina;
        this.vehicleStamina = GlobalVehicleMaxStamina;
        this.modifierList = new ModifierList();
        this.notifyHandler = new StaminaRelatedNotifyHandler(this.dataUpdateInvoke, player.getSession());
    }

    protected class DataUpdateInvoke {
        int skipStep = 0;
        public void run(long deltaTime) {
            handleDataUpdate();
            if (!immediateConsumptionList.isEmpty()) {
                immediateConsumptionHandle();
                skipStep = 3;
            }
            if (time2Recover > 0 && atRecovering || skipStep > 0) {
                skipStep -= 1;
                time2Recover -= deltaTime;
                return;
            }
            continuousStaminaConsumptionHandle(deltaTime);
        }
    }

    private class SendStaminaTask extends TimerTask {
        public void run() {
            if (staminaUpdated) {
                sendNewStamina(atSkiff);
            }
        }
    }

    public void startStaminaHandler() {
        if (!player.isPaused() && this.timer == null) {
            this.notifyHandler.start();
            this.timer = new Timer();
            this.timer.scheduleAtFixedRate(new SendStaminaTask(), 0, calDelay);
        }
    }

    public void stopStaminaHandler() {
        if (this.timer != null) {
            this.timer.cancel();
            this.notifyHandler.stop();
            this.timer = null;
        }
    }

    public void handleSerialSkill(int entityId, int eventId, int localId) {
        notifyHandler.handleSerialSkill(entityId, eventId, localId);
    }

    public void handleDiscreteSkill(int entityId, int eventId, int localId){
        notifyHandler.handleDiscreteSkill(entityId, eventId, localId);
    }

    public void handleEvtDoSkillSuccNotify(@NotNull GameSession session, int skillId, int casterId) {
        notifyHandler.handleEvtDoSkillSuccNotify(session, skillId, casterId);
    }

    public void handleBaseMotionStateNotify(@NotNull GameSession session, @NotNull EntityMoveInfo moveInfo,
            @NotNull GameEntity entity) {
        notifyHandler.handleBaseMotionStateNotify(session, moveInfo, entity);
        startStaminaHandler();
    }

    public void handleVehicleInteractReq(@NotNull GameSession session, int vehicleId, VehicleInteractType vehicleInteractType) {
        notifyHandler.handleVehicleInteractReq(session, vehicleId, vehicleInteractType);
    }

    private void handleDataUpdate() {
        Consumption newConsumption = new Consumption(this.notifyHandler.getConsumption());
        if (newConsumption.getValue() > 0) {
            this.time2Recover = this.recoveryDelay - this.notifyHandler.turn2RecoverOffset;
            this.notifyHandler.turn2RecoverOffset = 0;
        }
        this.atRecovering = newConsumption.getValue() < 0;
        this.consumption = newConsumption;
        this.atSkiff = this.consumption.stateType == MotionStateType.SKIFF;
        this.vehicleId = this.notifyHandler.getVehicleId();
        this.entity = this.notifyHandler.getEntity();
        this.notifyEntityId = this.notifyHandler.getNotifyEntityId();
        this.immediateConsumptionList = this.notifyHandler.getImmediateConsumptionList();
        this.speed = this.notifyHandler.getSpeed();
        this.session = this.notifyHandler.getSession();
        this.skillCasterId = this.notifyHandler.getSkillCasterId();
    }

    private void continuousStaminaConsumptionHandle(long deltaTime) {
        int currentAvatarEntityId = player.getTeamManager().getCurrentAvatarEntity().getId();
        if (this.notifyEntityId != currentAvatarEntityId &&
                this.notifyEntityId != vehicleId ||
                !this.consumption.persistent) {
            return;
        }
        Consumption consumption = new Consumption(this.consumption);
        consumption.value = this.modifierList.calculateInt(consumption);
        if (consumption.value == 0 || (consumption.value < 0 &&
                this.stamina == GlobalCharacterMaximumStamina)) {
            return;
        }

        if (consumption.state.equals(MotionState.MOTION_CLIMB.toString())) {
            consumption.value *= (Math.min(2, Math.sqrt(this.speed) + 1));
        }

        int newCons = (int) ((((float) deltaTime) * 1E-3) * consumption.getValue());
        consumption.value = newCons;

        if (this.atSkiff) {
            this.vehicleStamina -= consumption.getValue();
            this.vehicleStamina = Math.max(0, Math.min(GlobalVehicleMaxStamina, this.vehicleStamina));
        } else {
            this.stamina = Math.max(0,
                    Math.min(GlobalCharacterMaximumStamina, this.stamina - consumption.getValue()));
        }
        this.staminaUpdated = true;
    }

    private void immediateConsumptionHandle() {
        this.atRecovering = true;
        while (!this.immediateConsumptionList.isEmpty()) {
            Consumption consumption = this.immediateConsumptionList.poll();
            consumption.value = modifierList.calculateInt(consumption);
            if (consumption.value == 0 || (consumption.value < 0 &&
                    this.stamina == GlobalCharacterMaximumStamina)) {
                break;
            }
            if (this.atSkiff) {
                this.vehicleStamina -= ((float) consumption.getValue()) / 100;
                this.vehicleStamina = Math.max(0, Math.min(GlobalVehicleMaxStamina, this.vehicleStamina));
            } else {
                this.stamina = Math.max(0,
                        Math.min(GlobalCharacterMaximumStamina, this.stamina - consumption.getValue()));
            }
            this.staminaUpdated = true;
            if (consumption.value > 0) {
                this.time2Recover = this.recoveryDelay;
                this.atRecovering = false;
            }
        }
    }

    private void sendNewStamina(boolean atSkill) {
        if (!GAME_OPTIONS.staminaUsage) {
            this.vehicleStamina = GlobalVehicleMaxStamina;
            this.stamina = GlobalCharacterMaximumStamina;
        }
        if (atSkill) {
            this.session.send(new PacketVehicleStaminaNotify(this.vehicleId, ((float) this.vehicleStamina) / 100));
        } else {
            player.setProperty(StaminaProp, this.stamina);
            this.session.send(new PacketPlayerPropNotify(player, StaminaProp));
        }
        handleDrowning();
    }

    private void handleDrowning() {
        // TODO: fix drowning waverider entity
        if (this.stamina < 10 && this.consumption.stateType == MotionStateType.SWIM) {
            killAvatar(this.session, this.entity, PlayerDieType.PLAYER_DIE_DRAWN);
        }
    }

    public void killAvatar(GameSession session, GameEntity entity, PlayerDieType dieType) {
        session.send(new PacketAvatarLifeStateChangeNotify(player.getTeamManager().getCurrentAvatarEntity().getAvatar(),
                LifeState.LIFE_DEAD, dieType));
        session.send(new PacketLifeStateChangeNotify(entity, LifeState.LIFE_DEAD, dieType));
        entity.setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, 0);
        entity.getWorld()
                .broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_CUR_HP));
        entity.getWorld().broadcastPacket(new PacketLifeStateChangeNotify(0, entity, LifeState.LIFE_DEAD));
        player.getScene().removeEntity(entity);
        ((EntityAvatar) entity).onDeath(dieType, 0);
    }

}
