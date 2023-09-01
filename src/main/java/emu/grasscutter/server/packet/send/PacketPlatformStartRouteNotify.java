package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.PlatformStartRouteNotifyOuterClass.PlatformStartRouteNotify;
import lombok.val;

public class PacketPlatformStartRouteNotify extends BasePacket {
    public PacketPlatformStartRouteNotify(EntityGadget gadgetEntity) {
        super(PacketOpcodes.PlatformStartRouteNotify);

        val notify =
                PlatformStartRouteNotify.newBuilder()
                        .setEntityId(gadgetEntity.getId())
                        .setSceneTime(gadgetEntity.getScene().getSceneTime())
                        .setPlatform(gadgetEntity.getPlatformInfo());

        this.setData(notify);
    }
}
