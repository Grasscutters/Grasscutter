package emu.grasscutter.game.entity;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.props.LifeState;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.game.world.SpawnDataEntry;
import emu.grasscutter.game.world.World;
import emu.grasscutter.net.proto.FightPropPairOuterClass.FightPropPair;
import emu.grasscutter.net.proto.GadgetInteractReqOuterClass.GadgetInteractReq;
import emu.grasscutter.net.proto.MotionInfoOuterClass.MotionInfo;
import emu.grasscutter.net.proto.MotionStateOuterClass.MotionState;
import emu.grasscutter.net.proto.SceneEntityInfoOuterClass.SceneEntityInfo;
import emu.grasscutter.net.proto.VectorOuterClass.Vector;
import emu.grasscutter.server.event.entity.EntityDamageEvent;
import emu.grasscutter.server.event.entity.EntityDeathEvent;
import emu.grasscutter.server.packet.send.PacketEntityFightPropUpdateNotify;
import emu.grasscutter.utils.Position;
import it.unimi.dsi.fastutil.ints.Int2FloatMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import lombok.Getter;
import lombok.Setter;

public abstract class GameEntity {
    @Getter protected int id;
    @Getter private final Scene scene;
    @Getter @Setter private SpawnDataEntry spawnEntry;

    @Getter @Setter private int blockId;
    @Getter @Setter private int configId;
    @Getter @Setter private int groupId;

    @Getter @Setter private MotionState motionState;
    @Getter @Setter private int lastMoveSceneTimeMs;
    @Getter @Setter private int lastMoveReliableSeq;

    @Getter @Setter private boolean lockHP;

    // Abilities
    private Object2FloatMap<String> metaOverrideMap;
    private Int2ObjectMap<String> metaModifiers;

    public GameEntity(Scene scene) {
        this.scene = scene;
        this.motionState = MotionState.MOTION_STATE_NONE;
    }

    public int getEntityType() {
        return this.getId() >> 24;
    }

    public World getWorld() {
        return this.getScene().getWorld();
    }

    public boolean isAlive() {
        return true;
    }

    public LifeState getLifeState() {
        return this.isAlive() ? LifeState.LIFE_ALIVE : LifeState.LIFE_DEAD;
    }

    public Object2FloatMap<String> getMetaOverrideMap() {
        if (this.metaOverrideMap == null) {
            this.metaOverrideMap = new Object2FloatOpenHashMap<>();
        }
        return this.metaOverrideMap;
    }

    public Int2ObjectMap<String> getMetaModifiers() {
        if (this.metaModifiers == null) {
            this.metaModifiers = new Int2ObjectOpenHashMap<>();
        }
        return this.metaModifiers;
    }

    public abstract Int2FloatMap getFightProperties();

    public abstract Position getPosition();

    public abstract Position getRotation();

    public void setFightProperty(FightProperty prop, float value) {
        this.getFightProperties().put(prop.getId(), value);
    }

    public void setFightProperty(int id, float value) {
        this.getFightProperties().put(id, value);
    }

    public void addFightProperty(FightProperty prop, float value) {
        this.getFightProperties().put(prop.getId(), this.getFightProperty(prop) + value);
    }

    public float getFightProperty(FightProperty prop) {
        return this.getFightProperties().getOrDefault(prop.getId(), 0f);
    }

    public boolean hasFightProperty(FightProperty prop) {
        return this.getFightProperties().containsKey(prop.getId());
    }

    public void addAllFightPropsToEntityInfo(SceneEntityInfo.Builder entityInfo) {
        this.getFightProperties().forEach((key, value) -> {
            if (key == 0) return;
            entityInfo.addFightPropList(FightPropPair.newBuilder().setPropType(key).setPropValue(value).build());
        });
    }

    protected MotionInfo getMotionInfo() {
        MotionInfo proto = MotionInfo.newBuilder()
                .setPos(this.getPosition().toProto())
                .setRot(this.getRotation().toProto())
                .setSpeed(Vector.newBuilder())
                .setState(this.getMotionState())
                .build();

        return proto;
    }

    public float heal(float amount) {
        if (this.getFightProperties() == null) {
            return 0f;
        }

        float curHp = this.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP);
        float maxHp = this.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP);

        if (curHp >= maxHp) {
            return 0f;
        }

        float healed = Math.min(maxHp - curHp, amount);
        this.addFightProperty(FightProperty.FIGHT_PROP_CUR_HP, healed);

        this.getScene().broadcastPacket(new PacketEntityFightPropUpdateNotify(this, FightProperty.FIGHT_PROP_CUR_HP));

        return healed;
    }

    public void damage(float amount) {
        this.damage(amount, 0);
    }

    public void damage(float amount, int killerId) {
        // Check if the entity has properties.
        if (this.getFightProperties() == null || !hasFightProperty(FightProperty.FIGHT_PROP_CUR_HP)) {
            return;
        }

        // Invoke entity damage event.
        EntityDamageEvent event = new EntityDamageEvent(this, amount, this.getScene().getEntityById(killerId));
        event.call();
        if (event.isCanceled()) {
            return; // If the event is canceled, do not damage the entity.
        }

        float curHp = getFightProperty(FightProperty.FIGHT_PROP_CUR_HP);
        if (curHp != Float.POSITIVE_INFINITY && !lockHP || lockHP && curHp <= event.getDamage()) {
            // Add negative HP to the current HP property.
            this.addFightProperty(FightProperty.FIGHT_PROP_CUR_HP, -(event.getDamage()));
        }

        // Check if dead
        boolean isDead = false;
        if (this.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP) <= 0f) {
            this.setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, 0f);
            isDead = true;
        }

        // Packets
        this.getScene().broadcastPacket(new PacketEntityFightPropUpdateNotify(this, FightProperty.FIGHT_PROP_CUR_HP));

        // Check if dead.
        if (isDead) {
            this.getScene().killEntity(this, killerId);
        }
    }

    /**
     * Move this entity to a new position.
     * @param position The new position.
     * @param rotation The new rotation.
     */
    public void move(Position position, Position rotation) {
        // Set the position and rotation.
        this.getPosition().set(position);
        this.getRotation().set(rotation);
    }

    /**
     * Called when a player interacts with this entity
     * @param player Player that is interacting with this entity
     * @param interactReq Interact request protobuf data
     */
    public void onInteract(Player player, GadgetInteractReq interactReq) {

    }

    /**
     * Called when this entity is added to the world
     */
    public void onCreate() {

    }

    public void onRemoved() {

    }

    /**
     * Called when this entity dies
     * @param killerId Entity id of the entity that killed this entity
     */
    public void onDeath(int killerId) {
        // Invoke entity death event.
        EntityDeathEvent event = new EntityDeathEvent(this, killerId);
        event.call();
    }

    public abstract SceneEntityInfo toProto();
}
