package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.expedition.ExpeditionInfo;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarExpeditionDataNotifyOuterClass.AvatarExpeditionDataNotify;

import java.util.*;
import java.util.stream.Collectors;

public class PacketAvatarExpeditionDataNotify extends BasePacket {
    public PacketAvatarExpeditionDataNotify(Map<Long, ExpeditionInfo> expeditionInfo) {
        super(PacketOpcodes.AvatarExpeditionDataNotify);

        this.setData(AvatarExpeditionDataNotify.newBuilder()
            .putAllExpeditionInfoMap(
                expeditionInfo.entrySet().stream()
                    .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> e.getValue().toProto())))
            .build()
        );
    }
}
