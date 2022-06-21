package emu.grasscutter.game.entity;

import emu.grasscutter.game.props.EntityIdType;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.proto.*;
import emu.grasscutter.scripts.data.SceneNPC;
import emu.grasscutter.utils.Position;
import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;

public class EntityNPC extends GameEntity {

    private final Position position;
    private final Position rotation;
    private final SceneNPC metaNpc;
    private final int suiteId;

    public EntityNPC(Scene scene, SceneNPC metaNPC, int blockId, int suiteId) {
        super(scene);
        this.id = this.getScene().getWorld().getNextEntityId(EntityIdType.NPC);
        this.setConfigId(metaNPC.config_id);
        this.setGroupId(metaNPC.group.id);
        this.setBlockId(blockId);
        this.suiteId = suiteId;
        this.position = metaNPC.pos.clone();
        this.rotation = metaNPC.rot.clone();
        this.metaNpc = metaNPC;

    }

    @Override
    public Int2FloatOpenHashMap getFightProperties() {
        return null;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public Position getRotation() {
        return this.rotation;
    }

    public int getSuiteId() {
        return this.suiteId;
    }

    @Override
    public SceneEntityInfoOuterClass.SceneEntityInfo toProto() {

        EntityAuthorityInfoOuterClass.EntityAuthorityInfo authority = EntityAuthorityInfoOuterClass.EntityAuthorityInfo.newBuilder()
            .setAbilityInfo(AbilitySyncStateInfoOuterClass.AbilitySyncStateInfo.newBuilder())
            .setRendererChangedInfo(EntityRendererChangedInfoOuterClass.EntityRendererChangedInfo.newBuilder())
            .setAiInfo(SceneEntityAiInfoOuterClass.SceneEntityAiInfo.newBuilder()
                .setIsAiOpen(true)
                .setBornPos(this.getPosition().toProto()))
            .setBornPos(this.getPosition().toProto())
            .build();

        SceneEntityInfoOuterClass.SceneEntityInfo.Builder entityInfo = SceneEntityInfoOuterClass.SceneEntityInfo.newBuilder()
            .setEntityId(this.getId())
            .setEntityType(ProtEntityTypeOuterClass.ProtEntityType.PROT_ENTITY_TYPE_NPC)
            .setMotionInfo(MotionInfoOuterClass.MotionInfo.newBuilder()
                .setPos(this.getPosition().toProto())
                .setRot(this.getRotation().toProto())
                .setSpeed(VectorOuterClass.Vector.newBuilder()))
            .addAnimatorParaList(AnimatorParameterValueInfoPairOuterClass.AnimatorParameterValueInfoPair.newBuilder())
            .setEntityClientData(EntityClientDataOuterClass.EntityClientData.newBuilder())
            .setEntityAuthorityInfo(authority)
            .setLifeState(1);


        entityInfo.setNpc(SceneNpcInfoOuterClass.SceneNpcInfo.newBuilder()
            .setNpcId(this.metaNpc.npc_id)
            .setBlockId(this.getBlockId())
            .build());

        return entityInfo.build();
    }
}
