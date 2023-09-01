package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.PlatformChangeRouteNotifyOuterClass.PlatformChangeRouteNotify;
import lombok.val;

public class PacketPlatformChangeRouteNotify extends BasePacket {

    public PacketPlatformChangeRouteNotify(EntityGadget gadgetEntity) {
        super(PacketOpcodes.PlatformChangeRouteNotify);

        val proto =
                PlatformChangeRouteNotify.newBuilder()
                        .setEntityId(gadgetEntity.getId())
                        .setSceneTime(gadgetEntity.getScene().getSceneTime())
                        .setPlatform(gadgetEntity.getPlatformInfo())
                        .build();

        this.setData(proto);
    }
}
