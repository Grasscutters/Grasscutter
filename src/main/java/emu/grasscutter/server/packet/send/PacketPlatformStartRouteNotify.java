package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.entity.platform.EntityPlatform;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.MathQuaternionOuterClass;
import emu.grasscutter.net.proto.PlatformInfoOuterClass;
import emu.grasscutter.net.proto.PlatformStartRouteNotifyOuterClass;

public class PacketPlatformStartRouteNotify extends BasePacket {
    public PacketPlatformStartRouteNotify(int clientSequence, EntityPlatform entity, Scene scene) {
        super(PacketOpcodes.PlatformStartRouteNotify, clientSequence);

        var notify = PlatformStartRouteNotifyOuterClass.PlatformStartRouteNotify.newBuilder()
            .setEntityId(entity.getId())
            .setSceneTime(scene.getTime())
            .setPlatform(PlatformInfoOuterClass.PlatformInfo.newBuilder()
                .setStartSceneTime(scene.getTime())
                .setIsStarted(true)
                .setStartRot(MathQuaternionOuterClass.MathQuaternion.newBuilder()
                    .setW(0.653F)
                    .setY(0.757F)
                    .build())
                .setPosOffset(entity.getPosition().toProto())
                .setRotOffset(MathQuaternionOuterClass.MathQuaternion.newBuilder()
                    .setW(1.0F)
                    .build())
                .setMovingPlatformType(entity.getMovingPlatformType())
                .setIsActive(true)
                .build())
            .build();

        entity.setStarted(true);
        entity.setActive(true);

        this.setData(notify);
    }
}
