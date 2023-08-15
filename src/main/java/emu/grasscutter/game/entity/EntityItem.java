package emu.grasscutter.game.entity;

import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.*;
import emu.grasscutter.game.world.*;
import emu.grasscutter.net.proto.AbilitySyncStateInfoOuterClass.AbilitySyncStateInfo;
import emu.grasscutter.net.proto.AnimatorParameterValueInfoPairOuterClass.AnimatorParameterValueInfoPair;
import emu.grasscutter.net.proto.EntityAuthorityInfoOuterClass.EntityAuthorityInfo;
import emu.grasscutter.net.proto.EntityClientDataOuterClass.EntityClientData;
import emu.grasscutter.net.proto.EntityRendererChangedInfoOuterClass.EntityRendererChangedInfo;
import emu.grasscutter.net.proto.GadgetBornTypeOuterClass.GadgetBornType;
import emu.grasscutter.net.proto.GadgetInteractReqOuterClass.GadgetInteractReq;
import emu.grasscutter.net.proto.InteractTypeOuterClass.InteractType;
import emu.grasscutter.net.proto.MotionInfoOuterClass.MotionInfo;
import emu.grasscutter.net.proto.PropPairOuterClass.PropPair;
import emu.grasscutter.net.proto.ProtEntityTypeOuterClass.ProtEntityType;
import emu.grasscutter.net.proto.SceneEntityAiInfoOuterClass.SceneEntityAiInfo;
import emu.grasscutter.net.proto.SceneEntityInfoOuterClass.SceneEntityInfo;
import emu.grasscutter.net.proto.SceneGadgetInfoOuterClass.SceneGadgetInfo;
import emu.grasscutter.net.proto.TrifleGadgetOuterClass.TrifleGadget;
import emu.grasscutter.net.proto.VectorOuterClass.Vector;
import emu.grasscutter.server.packet.send.PacketGadgetInteractRsp;
import emu.grasscutter.utils.helpers.ProtoHelper;
import it.unimi.dsi.fastutil.ints.Int2FloatMap;
import lombok.Getter;

public class EntityItem extends EntityBaseGadget {
    @Getter private final GameItem item;
    @Getter private final long guid;
    @Getter private final boolean share;

    public EntityItem(Scene scene, Player player, ItemData itemData, Position pos, int count) {
        this(scene, player, itemData, pos, count, true);
    }

    public EntityItem(
            Scene scene, Player player, ItemData itemData, Position pos, Position rotation, int count) {
        this(scene, player, itemData, pos, rotation, count, true);
    }

    public EntityItem(
            Scene scene, Player player, ItemData itemData, Position pos, int count, boolean share) {
        this(scene, player, itemData, pos, null, count, share);
    }

    // In official game, some drop items are shared to all players, and some other items are
    // independent to all players
    // For example, if you killed a monster in MP mode, all players could get drops but rarity and
    // number of them are different
    // but if you broke regional mine, when someone picked up the drop then it disappeared
    public EntityItem(
            Scene scene,
            Player player,
            ItemData itemData,
            Position pos,
            Position rotation,
            int count,
            boolean share) {
        super(scene, pos, rotation);
        this.id = getScene().getWorld().getNextEntityId(EntityIdType.GADGET);
        this.guid =
                player == null ? scene.getWorld().getHost().getNextGameGuid() : player.getNextGameGuid();
        this.item = new GameItem(itemData, count);
        this.share = share;
    }

    public ItemData getItemData() {
        return this.getItem().getItemData();
    }

    public int getCount() {
        return this.getItem().getCount();
    }

    @Override
    public int getGadgetId() {
        return this.getItemData().getGadgetId();
    }

    @Override
    public Int2FloatMap getFightProperties() {
        return null;
    }

    @Override
    public void onInteract(Player player, GadgetInteractReq interactReq) {
        // check drop owner to avoid someone picked up item in others' world
        if (!this.isShare()) {
            int dropOwner = (int) (this.getGuid() >> 32);
            if (dropOwner != player.getUid()) {
                return;
            }
        }

        this.getScene().removeEntity(this);
        GameItem item = new GameItem(this.getItemData(), this.getCount());

        // Add to inventory
        boolean success = player.getInventory().addItem(item, ActionReason.SubfieldDrop);
        if (success) {
            if (!this.isShare()) { // not shared drop
                player.sendPacket(new PacketGadgetInteractRsp(this, InteractType.INTERACT_TYPE_PICK_ITEM));
            } else {
                this.getScene()
                        .broadcastPacket(
                                new PacketGadgetInteractRsp(this, InteractType.INTERACT_TYPE_PICK_ITEM));
            }
        }
    }

    @Override
    public SceneEntityInfo toProto() {
        EntityAuthorityInfo authority =
                EntityAuthorityInfo.newBuilder()
                        .setAbilityInfo(AbilitySyncStateInfo.newBuilder())
                        .setRendererChangedInfo(EntityRendererChangedInfo.newBuilder())
                        .setAiInfo(
                                SceneEntityAiInfo.newBuilder().setIsAiOpen(true).setBornPos(Vector.newBuilder()))
                        .setBornPos(Vector.newBuilder())
                        .build();

        SceneEntityInfo.Builder entityInfo =
                SceneEntityInfo.newBuilder()
                        .setEntityId(getId())
                        .setEntityType(ProtEntityType.PROT_ENTITY_TYPE_GADGET)
                        .setMotionInfo(
                                MotionInfo.newBuilder()
                                        .setPos(getPosition().toProto())
                                        .setRot(getRotation().toProto())
                                        .setSpeed(Vector.newBuilder()))
                        .addAnimatorParaList(AnimatorParameterValueInfoPair.newBuilder())
                        .setEntityClientData(EntityClientData.newBuilder())
                        .setEntityAuthorityInfo(authority)
                        .setLifeState(1);

        PropPair pair =
                PropPair.newBuilder()
                        .setType(PlayerProperty.PROP_LEVEL.getId())
                        .setPropValue(ProtoHelper.newPropValue(PlayerProperty.PROP_LEVEL, 1))
                        .build();
        entityInfo.addPropList(pair);

        SceneGadgetInfo.Builder gadgetInfo =
                SceneGadgetInfo.newBuilder()
                        .setGadgetId(this.getItemData().getGadgetId())
                        .setTrifleGadget(TrifleGadget.newBuilder().setItem(this.getItem().toProto()))
                        .setBornType(GadgetBornType.GADGET_BORN_TYPE_IN_AIR)
                        .setAuthorityPeerId(this.getWorld().getHostPeerId())
                        .setIsEnableInteract(true);

        entityInfo.setGadget(gadgetInfo);

        return entityInfo.build();
    }

    @Override
    public void initAbilities() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initAbilities'");
    }
}
