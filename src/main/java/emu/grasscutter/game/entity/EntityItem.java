package emu.grasscutter.game.entity;

import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.EntityIdType;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.proto.AbilitySyncStateInfoOuterClass.AbilitySyncStateInfo;
import emu.grasscutter.net.proto.AnimatorParameterValueInfoPairOuterClass.AnimatorParameterValueInfoPair;
import emu.grasscutter.net.proto.EntityAuthorityInfoOuterClass.EntityAuthorityInfo;
import emu.grasscutter.net.proto.EntityClientDataOuterClass.EntityClientData;
import emu.grasscutter.net.proto.EntityRendererChangedInfoOuterClass.EntityRendererChangedInfo;
import emu.grasscutter.net.proto.GadgetBornTypeOuterClass.GadgetBornType;
import emu.grasscutter.net.proto.MotionInfoOuterClass.MotionInfo;
import emu.grasscutter.net.proto.PropPairOuterClass.PropPair;
import emu.grasscutter.net.proto.ProtEntityTypeOuterClass.ProtEntityType;
import emu.grasscutter.net.proto.SceneEntityAiInfoOuterClass.SceneEntityAiInfo;
import emu.grasscutter.net.proto.SceneEntityInfoOuterClass.SceneEntityInfo;
import emu.grasscutter.net.proto.SceneGadgetInfoOuterClass.SceneGadgetInfo;
import emu.grasscutter.net.proto.VectorOuterClass.Vector;
import emu.grasscutter.utils.Position;
import emu.grasscutter.utils.ProtoHelper;
import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;

public class EntityItem extends EntityBaseGadget {
    private final Position pos;
    private final Position rot;

    private final GameItem item;
    private final long guid;

    private final boolean share;

    public EntityItem(Scene scene, Player player, ItemData itemData, Position pos, int count) {
        super(scene);
        this.id = this.getScene().getWorld().getNextEntityId(EntityIdType.GADGET);
        this.pos = new Position(pos);
        this.rot = new Position();
        this.guid = player == null ? scene.getWorld().getHost().getNextGameGuid() : player.getNextGameGuid();
        this.item = new GameItem(itemData, count);
        this.share = true;
    }

    // In official game, some drop items are shared to all players, and some other items are independent to all players
    // For example, if you killed a monster in MP mode, all players could get drops but rarity and number of them are different
    // but if you broke regional mine, when someone picked up the drop then it disappeared
    public EntityItem(Scene scene, Player player, ItemData itemData, Position pos, int count, boolean share) {
        super(scene);
        this.id = this.getScene().getWorld().getNextEntityId(EntityIdType.GADGET);
        this.pos = new Position(pos);
        this.rot = new Position();
        this.guid = player == null ? scene.getWorld().getHost().getNextGameGuid() : player.getNextGameGuid();
        this.item = new GameItem(itemData, count);
        this.share = share;
    }

    @Override
    public int getId() {
        return this.id;
    }

    private GameItem getItem() {
        return this.item;
    }

    public ItemData getItemData() {
        return this.getItem().getItemData();
    }

    public long getGuid() {
        return this.guid;
    }

    public int getCount() {
        return this.getItem().getCount();
    }

    @Override
    public int getGadgetId() {
        return this.getItemData().getGadgetId();
    }

    @Override
    public Position getPosition() {
        return this.pos;
    }

    @Override
    public Position getRotation() {
        return this.rot;
    }

    @Override
    public Int2FloatOpenHashMap getFightProperties() {
        return null;
    }

    public boolean isShare() {
        return this.share;
    }

    @Override
    public SceneEntityInfo toProto() {
        EntityAuthorityInfo authority = EntityAuthorityInfo.newBuilder()
            .setAbilityInfo(AbilitySyncStateInfo.newBuilder())
            .setRendererChangedInfo(EntityRendererChangedInfo.newBuilder())
            .setAiInfo(SceneEntityAiInfo.newBuilder().setIsAiOpen(true).setBornPos(Vector.newBuilder()))
            .setBornPos(Vector.newBuilder())
            .build();

        SceneEntityInfo.Builder entityInfo = SceneEntityInfo.newBuilder()
            .setEntityId(this.getId())
            .setEntityType(ProtEntityType.PROT_ENTITY_TYPE_GADGET)
            .setMotionInfo(MotionInfo.newBuilder().setPos(this.getPosition().toProto()).setRot(this.getRotation().toProto()).setSpeed(Vector.newBuilder()))
            .addAnimatorParaList(AnimatorParameterValueInfoPair.newBuilder())
            .setEntityClientData(EntityClientData.newBuilder())
            .setEntityAuthorityInfo(authority)
            .setLifeState(1);

        PropPair pair = PropPair.newBuilder()
            .setType(PlayerProperty.PROP_LEVEL.getId())
            .setPropValue(ProtoHelper.newPropValue(PlayerProperty.PROP_LEVEL, 1))
            .build();
        entityInfo.addPropList(pair);

        SceneGadgetInfo.Builder gadgetInfo = SceneGadgetInfo.newBuilder()
            .setGadgetId(this.getItemData().getGadgetId())
            .setTrifleItem(this.getItem().toProto())
            .setBornType(GadgetBornType.GADGET_BORN_TYPE_IN_AIR)
            .setAuthorityPeerId(this.getWorld().getHostPeerId())
            .setIsEnableInteract(true);

        entityInfo.setGadget(gadgetInfo);

        return entityInfo.build();
    }
}
