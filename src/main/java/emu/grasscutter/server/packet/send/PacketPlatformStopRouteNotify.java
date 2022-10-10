package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.entity.platform.EntityPlatform;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.MathQuaternionOuterClass;
import emu.grasscutter.net.proto.PlatformInfoOuterClass;
import emu.grasscutter.net.proto.PlatformStopRouteNotifyOuterClass;

public class PacketPlatformStopRouteNotify extends BasePacket {
    public PacketPlatformStopRouteNotify(int clientSequence, EntityPlatform entity, Scene scene) {
        super(PacketOpcodes.PlatformStopRouteNotify, clientSequence);

        var notify = PlatformStopRouteNotifyOuterClass.PlatformStopRouteNotify.newBuilder()
            .setPlatform(PlatformInfoOuterClass.PlatformInfo.newBuilder()
                .setStartSceneTime(scene.getTime())
                .setStartRot(MathQuaternionOuterClass.MathQuaternion.newBuilder()
                    .setW(0.653F)
                    .setY(0.757F)
                    .build())
                .setStopSceneTime(scene.getTime())
                .setPosOffset(entity.getPosition().toProto())
                .setRotOffset(MathQuaternionOuterClass.MathQuaternion.newBuilder()
                    .setW(1.0F)
                    .build())
                .setMovingPlatformType(entity.getMovingPlatformType())
                .build())
            .setSceneTime(scene.getTime())
            .setEntityId(entity.getId())
            .build();

        entity.setStarted(false);
        entity.setActive(false);

        this.setData(notify);
    }
}
