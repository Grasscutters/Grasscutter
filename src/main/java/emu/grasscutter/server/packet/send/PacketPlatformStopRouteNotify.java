package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.PlatformStopRouteNotifyOuterClass;

public class PacketPlatformStopRouteNotify extends BasePacket {
    public PacketPlatformStopRouteNotify(EntityGadget gadgetEntity) {
        super(PacketOpcodes.PlatformStopRouteNotify);

        var notify =
                PlatformStopRouteNotifyOuterClass.PlatformStopRouteNotify.newBuilder()
                        .setPlatform(gadgetEntity.getPlatformInfo())
                        .setSceneTime(gadgetEntity.getScene().getSceneTime())
                        .setEntityId(gadgetEntity.getId())
                        .build();

        this.setData(notify);
    }
}
