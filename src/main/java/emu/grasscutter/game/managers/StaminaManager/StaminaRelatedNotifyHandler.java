package emu.grasscutter.game.managers.StaminaManager;

import java.io.FileReader;
import java.util.*;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import emu.grasscutter.utils.Position;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import emu.grasscutter.Configuration;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.AvatarData;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.managers.StaminaManager.StaminaManager.DataUpdateInvoke;
import emu.grasscutter.net.proto.EntityMoveInfoOuterClass.EntityMoveInfo;
import emu.grasscutter.net.proto.MotionStateOuterClass.MotionState;
import emu.grasscutter.net.proto.VectorOuterClass.Vector;
import emu.grasscutter.net.proto.VehicleInteractTypeOuterClass.VehicleInteractType;
import emu.grasscutter.server.game.GameSession;

public class StaminaRelatedNotifyHandler {
    private static final HashMap<String, MotionStateData> MotionStateMap = new HashMap<String, MotionStateData>();
    private static final Int2ObjectMap<String> WeaponTypeMap = new Int2ObjectOpenHashMap<String>();
    private static final HashSet<Integer> BashLocalIdMap = new HashSet<Integer>(List.of(
        32771, 32779, 32795
    ));
    private static final String SERIAL_SKILL = "SKILL_SERIAL_";
    private static final String DISCRETE_SKILL = "SKILL_DISCRETE_";
    private static final String PRE_SKILL = "PRE_SKILL_";

    public static Consumption defultConsumption;
    private Consumption consumption;

    public static final int UpdateDelay = 31;
    private static final int MaxStateNotSwitchTime = 420;
    private int deltaTime;
    public int turn2RecoverOffset;
    private long lastTime;
    private Timer timer;

    private Queue<Consumption> immediateConsumptionList;
    private int maxQueueSize = 24;

    private boolean moveInfoReady = false;
    private boolean skillEvtInfoReady = false;
    private boolean serialSkillInfoReady = false;
    private boolean discreteSkillInfoReady = false;

    private int skillId;
    private int skillCasterId;
    private int notifyEntityId;
    private int vehicleId;

    private GameSession session;
    private GameEntity entity;
    private MotionStateData doSkillState;
    private MotionStateData movingMotionState;
    private MotionStateData serialSkillMotionState;
    private MotionStateData discreteSkillMotionState;

    private Position position;
    private double speed;
    private boolean atDashing = false;

    private DataUpdateInvoke dataUpdateInvoke;;

    public StaminaRelatedNotifyHandler(DataUpdateInvoke dataListener, GameSession session) {
        this.lastTime = System.nanoTime();
        this.consumption = defultConsumption;
        this.position = new Position();
        this.immediateConsumptionList = new LinkedList<Consumption>();
        this.dataUpdateInvoke = dataListener;
        this.session = session;
    }

    public static synchronized void initialize() {
        // Initialize skill categories
        WeaponTypeMap.put(10337, "WEAPON_SWORD_ONE_HAND");
        Int2ObjectMap<AvatarData> avatarDataMap = GameData.getAvatarDataMap();
        for (AvatarData data : avatarDataMap.values()) {
            int normalAttackId = data.getSkillDepot().getSkills().get(0);
            if (normalAttackId == 0) {
                continue;
            }
            String weaponType = data.getWeaponType();

            // Traveller skill
            if (normalAttackId > 100000) {
                normalAttackId /= 10;
            }
            WeaponTypeMap.put(normalAttackId, weaponType);
        }
        // Initialize motion status datas
        try (FileReader fileReader = new FileReader(Configuration.DATA("MotionStateDatas.json"))) {
            MotionStateMap.clear();
            List<MotionStateData> MotionStatusDatas = Grasscutter.getGsonFactory().fromJson(fileReader,
                    TypeToken.getParameterized(Collection.class, MotionStateData.class).getType());
            if (MotionStatusDatas.size() > 0) {
                for (MotionStateData data : MotionStatusDatas) {
                    MotionStateMap.put(data.state, data);
                }
                defultConsumption = new Consumption(MotionStateMap.get("MOTION_STANDBY"));
                Grasscutter.getLogger().info("MotionStatusDatas successfully loaded.");
            } else {
                Grasscutter.getLogger().error("Unable to load MotionStatusDatas. MotionStatusDatas size is 0.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO: Initialize foods etc.
    }

    private class NotifyHandleTask extends TimerTask {
        public void run() {
            MotionStateData stateData = consumption;
            if (serialSkillInfoReady) {
                stateData = serialSkillMotionState;
                if (deltaTime > 0 && consumption.state.equals(stateData.state)) {
                    deltaTime -= UpdateDelay;
                } else {
                    turn2RecoverOffset = UpdateDelay - deltaTime;
                    serialSkillInfoReady = false;
                }
            } else if (discreteSkillInfoReady) {
                stateData = discreteSkillMotionState;
                discreteSkillInfoReady = false;
            } else if (skillEvtInfoReady) {
                stateData = doSkillState;
                skillEvtInfoReady = false;
            } else if (moveInfoReady) {
                stateData = movingMotionState;

                // Determine the state of the continuous jump
                String dashState = MotionState.MOTION_DASH.toString();
                if (stateData.state.equals(dashState)) {
                    atDashing = true;
                } else if (atDashing && stateData.stateType == MotionStateType.OVERRIDABLE) {
                    stateData = MotionStateMap.get(dashState);
                } else {
                    atDashing = false;
                }
                moveInfoReady = false;
            }
            consumption = new Consumption(stateData);
            dataUpdateInvoke.run(UpdateDelay);
        }
    }

    public void start() {
        if (this.timer == null) {
            this.timer = new Timer();
            this.timer.schedule(new NotifyHandleTask(), 0, UpdateDelay);
        }
    }

    public void stop() {
        if (this.timer != null) {
            this.timer.cancel();
            this.timer = null;
        }
    }

    private double calculateSpeed(Position newPosition, Position oldPosition, Double deltaTime) {
        Position deltaPosition = (new Position(newPosition)).subtract(oldPosition);
        return Math.sqrt(
                Math.pow(deltaPosition.getX(), 2) +
                        Math.pow(deltaPosition.getY(), 2) +
                        Math.pow(deltaPosition.getZ(), 2))
                / deltaTime;
    }

    private boolean isCorrectEntityId(int entityId) {
        int currentAvatarEntityId = this.session.getPlayer().getTeamManager().getCurrentAvatarEntity().getId();
        if (entityId != currentAvatarEntityId && entityId != this.vehicleId) {
             return false;
        }
        return true;
    }


    public void handleSerialSkill(int entityId, int eventId, int localId) {
        if (this.session == null){
            return;
        }
        if (!isCorrectEntityId(entityId)){
            return;
        }
        
        String key;
        if (WeaponTypeMap.containsKey(skillId)) {
            key = WeaponTypeMap.get(skillId);
        } else {
            key = SERIAL_SKILL + Integer.toString(skillId);
        }
        if (MotionStateMap.containsKey(key)) {
            this.serialSkillMotionState = MotionStateMap.get(key);
            this.deltaTime = MaxStateNotSwitchTime;
            this.serialSkillInfoReady = true;
        }
    }

    public void handleDiscreteSkill(int entityId, int eventId, int localId){
        if (this.session == null){
            return;
        }
        if (!isCorrectEntityId(entityId)){
            return;
        }
        
        String key;
        if (WeaponTypeMap.containsKey(skillId)) {
            key = WeaponTypeMap.get(skillId);
            if (!BashLocalIdMap.contains(localId)) {
                key += Integer.toString(entityId);
            }
        } else {
            key = DISCRETE_SKILL + Integer.toString(skillId);
        }
        if (MotionStateMap.containsKey(key)) {
            this.discreteSkillMotionState = MotionStateMap.get(key);
            if (immediateConsumptionList.size() < this.maxQueueSize) {
                immediateConsumptionList.add(new Consumption(this.discreteSkillMotionState));
            } else {
                return;
            }
            
            this.discreteSkillInfoReady = true;
        }
    }

    public void handleEvtDoSkillSuccNotify(@NotNull GameSession session, int skillId, int casterId) {
        this.session = session;
        this.skillId = skillId;
        this.skillCasterId = casterId;

        if (skillId > 100000) {
            skillId /= 10;
        }
        String key = PRE_SKILL + Integer.toString(skillId);
        
        if (MotionStateMap.containsKey(key)) {
            this.doSkillState = MotionStateMap.get(key);
            if (!this.doSkillState.persistent) {
                if (this.immediateConsumptionList.size() < this.maxQueueSize) {
                    this.immediateConsumptionList.add(new Consumption(this.doSkillState));
                } else {
                    return;
                }
            }
            this.skillEvtInfoReady = false;
            this.doSkillState = defultConsumption;
        }
    }

    public void handleBaseMotionStateNotify(@NotNull GameSession session, @NotNull EntityMoveInfo moveInfo,
            @NotNull GameEntity entity) {
        
        this.session = session;
        this.entity = entity;
        if (!isCorrectEntityId(entity.getId())){
            return;
        }

        // Calculate the speed for climb stamina consumption in the climbing state
        long time = System.nanoTime();
        double deltaTime = 1E-9f * (double) (time - this.lastTime);
        Vector posVector = moveInfo.getMotionInfo().getPos();
        if (Math.abs(posVector.getX()) > Float.MIN_VALUE &&
                Math.abs(posVector.getY()) > Float.MIN_VALUE &&
                Math.abs(posVector.getZ()) > Float.MIN_VALUE) {

            Position newPosition = new Position(
                    posVector.getX(),
                    posVector.getY(),
                    posVector.getZ());

            this.speed = calculateSpeed(newPosition, this.position, deltaTime);
            this.position = newPosition;
            this.lastTime = time;
        }

        String key = moveInfo.getMotionInfo().getState().toString();
        if (MotionStateMap.containsKey(key)) {
            this.movingMotionState = MotionStateMap.get(key);
            if (!this.movingMotionState.persistent) {
                if (immediateConsumptionList.size() < this.maxQueueSize) {
                    immediateConsumptionList.add(new Consumption(this.movingMotionState));
                } else {
                    return;
                }
            }
            this.moveInfoReady = true;
        } else {
            this.movingMotionState = defultConsumption;
        }
    }

    public void handleVehicleInteractReq(@NotNull GameSession session, int vehicleId, VehicleInteractType vehicleInteractType) {
        if (vehicleInteractType == VehicleInteractType.VEHICLE_INTERACT_IN) {
            this.vehicleId = vehicleId;
        } else {
            this.vehicleId = -1;
        }
    }

    public Consumption getConsumption() {
        return this.consumption;
    }

    public double getSpeed() {
        return this.speed;
    }

    public int getSkillCasterId() {
        return this.skillCasterId;
    }

    public GameSession getSession() {
        return this.session;
    }

    public GameEntity getEntity() {
        return this.entity;
    }

    public int getNotifyEntityId() {
        return this.notifyEntityId;
    }

    public Queue<Consumption> getImmediateConsumptionList() {
        return this.immediateConsumptionList;
    }

    public int getVehicleId() {
        return this.vehicleId;
    }

}
