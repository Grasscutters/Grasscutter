package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.entity.platform.EntityPlatform;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PlatformStartRouteNotifyOuterClass;

public class PacketPlatformStartRouteNotify extends BasePacket {
    public PacketPlatformStartRouteNotify(EntityPlatform entity, Scene scene) {
        super(PacketOpcodes.PlatformStartRouteNotify);

        var notify = PlatformStartRouteNotifyOuterClass.PlatformStartRouteNotify.newBuilder()
            .setEntityId(entity.getId())
            .setSceneTime(scene.getSceneTime())
            .setPlatform(entity.onStartRoute())
            .build();

        this.setData(notify);
    }
}
