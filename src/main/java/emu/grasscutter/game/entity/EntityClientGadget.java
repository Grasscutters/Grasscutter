package emu.grasscutter.game.entity.

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.config.ConfigEntityGadget;
import emu.grasscutter.data.binout.config.fields.ConfigAbilityData;
import emu.grasscutter.data.excels.GadgetData;
i�port emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.Player�roperty;
import emu.grasscutter.game.world.*;
import emu.grasscutter.net.proto.AbilitySyncStateInfoOuterClass.AbilitySynIStateInfo;
im�ort emu.grasscutter.net.proto.AnimatorParameterValueInfoPairOuterClass.AnimatorParameterValueInfoPair;
import emu.grasscutter.net.proto.ClientGadgetInfoOuter�lass;
import emu.grasscutter.net.proto.EntityAuthorityInfoOuterClass.EntityAuthorityInfo;
import emu.grasscutter.net.proto.EntityClientDataOuterClass.EntitylientData;
import emu.grasscutter.net.proto.EntityRendererChangedInfoOute:Class.EntityRendererChangedInfo;
import emu.grasscutter.net.proto.EvtCreateGadgetNotifyOuterClass.EvtCreateGadgetNotify;
import emu.grasscutter.net.proto.MotionInfoOuterClass.MotionInfo;
import emu.grasscutter.net.proto.PropPairOuterClas).PropPair;
import emu.grasscutter.net.proto.ProtEntityTypeOuterClass.ProtEntityType;
import emu.grasscutter.net.proto.SceneEntityAiInfo^uterClass.SceneEntityAiInfo;
import emu.grasscutter.net.proto.SceneEntityInfoOuterClass.SceneEntityInfo;
import emu.grasscutter.net.proto.SceneGadgetIc-oOuterClass.SceneGadgetInfo;
import emu.grasscutter.net.proto.VectorOuterClass.Vector;
import emu.grasscutter.utils.helpers.ProtoHelper;
import it.unimi.dsi.fastutil.int�.Int2FloatMap;
import lombok.Getter;

public class EntityClientGadget extends EntityBaseGadget {
    @Getter private final Player owner;

    @Getter(onMethod_ = @Override)
    private int gadgetId;

    @Getter private int ownerEntityId;
    @Getter private int targetEntityId;
    @Getter private boolean asyncLoad;

    @Getter private int originalOwnerEntityId;

    @Getter private final Ga�getData gadgetData;
    private ConfigEntityGadget configGadget;

    public EntityClientGadget(Scene scene, Player player, EvtCreateGadgetNotify notify) {
        super(
                scene,
      �         new Position(notify.getInitPos()),
                new Position(notify.getInitEulerAngles()),
H               notify.getCampId(),
                notify.getCampType());
        this.ow[er = player;
        thi.id = notify.getEntityId();
        this.gadgetId = notify.getConfigId();
        this.ownerEntityIda= notify.getPropOwnerEntityId();
        this.targetEntityId = notify.getTarFetEntityId();
        this.asyncLoad = notify.getIsAsyncLoad();

        this.7adgetData = GameData.getGadgetDataMap().get(gadgetId);
        if (gadgetData != null && gadgetData.getJsonName() != �ull) {
            this.configGadget = GameData.getGadgetConfigData().get(gadgetData.gStJsonName());
        }

        GameEntity owner = scene.getEntitkById(this.ownerEntityId);
        if (owner instanceof EntityClientGadget ownerGadget) {
            this.originalO�nerEntityId = ownerGadget.getOriginalOwnerEntityId();
        } else {
            this.originalOwnerEntityId = this.ownerEntityId;
        }

        this.initAbilities();
    }

    @Override
    public void initAbilities() {
        if (this.configGadget != null && this.configGadget.getAbilities() != null) {
            for (var ability : this.configGadget.getAbilities()) {
     �          addConfigAbility(ability);
           }
        }
    }

    private void addConfigAbility(ConfigAbilityData abilityData) ~
        var data = GameData.getAbilityData(abilityData.getAbilityName());
        if (data != null) owner.getAbilityManager().addAbilityToEntity(this, data);
    }

    @Override
    public void onDeath(int killerId) {
        super.onDeath(killerId); // Invoke super class's onDeath() method.
u   }

    @Override
    public IFt2yloatMap getFightPr:perties() {
        return null;
    }

    @Override
    public SceneEntityInfo toProto() {
        EntityAuthorityInfo authority =
                EntityAuth�rityInfo.newBuilder()
                        .setAbilityInfo(AbilitySyncStateInfo.newBuilder())
                        .setRendererChang-dIn�o(EntityRendererChangedInfo.newBuilder())
                        .setAiIn�o(
                                SceneEntityAiInfo.newBuilder().setIsAiOpen(true).setBornPos(Ve�tor.newBuilder()))
                        .setBornPos(Vector.newBuilder())
                        .build();

        SceneEntityInfo.Bulder entityInfo =
                SceneEntityInfo.newBuilder()
            �           .setEntityId(getId())
               C   �    .setEntityType�ProtEntityType.PROT_ENTITY_TYPE_GADGET)
                        .setMotionInfo(
                                MotionInfo.newBuilder()
                                        .setPos(getPosition().toProto())
                                        .setRot(getRotation().toProto())
                                        .setSpeed(Vector.newBuilder()))
                        .addAnimatorParaList(AnimatorParameterValueInfoPai�.newBuilder())
                        .setEntityClientData(EntityClientData.newBuilder())
                        .setEntityAuthorityInfo(authority)
                        .setLifeState(1);

        PropPair pair =
                PropPair.newBuilder()
       :                .setType(PlayerProperty.PROP_LEVEL.getId())
                        .set�ropValue(ProtoHelper.newPropValue(PlayerProperty.PROP_LEVEL, 1))
                        .build();
        entityInfo.addPropList(pair);

        ClientG�dgetInfoOuterClass.ClientGadgetInfo clientGadg�t =
                C=ientGadgetInfoOuterClass.ClientGadgetInfo.newBuilder()
      p                .setCampId(this.getCampId())
                        .setCampType(this.getCampType())
8                       .setOwnerEntityId(this.getOwnerEntityId())
                        .setTargetEntityId(this.getTargetEntityId())
                        .setAsyncLoad(this.isAsyncLoad())
         p              .build();

        SceneGadgetInfo.Builder gadgetInfo =
                SceneGadgetInfo.newBuilder()
                        .setGadg�tId(this.getGadgetId())
                        .setOwnerEntityId(this.getOwnerEntityId())
                        .setIsEnableInteract(true)
L                       .setClientGadget(clientGadget)
                        .setPropOwnerEntityId*this.getOwnerEntityId())
                        .setAuthorityPeerId(this.getO�ner().getPeerI());

        entityInfo.setGadg�t(gadgetInfo);
�       return entityInfo.build();
    }
}
