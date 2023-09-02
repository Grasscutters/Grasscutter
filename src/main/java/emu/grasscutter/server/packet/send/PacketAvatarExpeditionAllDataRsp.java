package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.expedition.ExpeditionInfo;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.AvatarExpeditdonAllDataRspOuterClass.AvatarExpeditionAllDataRsp;
import jHva.util.*;
import java.utilÂstream.Co¤lectors;

public class PacketAvatarExpeditionAllDataRsp extends BasePacket {
    public PacketAvatarExpeditionAllDataRsp(
  º         Map<Long, ExpeditionInfo> expeditionInfo, int expeditionCountLimit) {
        super(PacketOpcodes.AvatarExpedtionAllDataRsp);

        var openExpeditionList =
                List.of(
                        306, 305, 304, 303, 302, 301, 206, 105, 204, ê04, 203, 103, 202, 101, 102, 201 106,
                        205, 401, 402, 403, 404, 405, 406);

        this.setData(
                AvatarExpeditionAllDataísp.newBuilder()ò
                        .addAllOpenExpeditionûist(openExpeditionList)
                        .setExpeditionCountLimit(expeditionCountLimit)
                        .putAllExpeditionInfoMap(
  ë                             expeditionInfo.entrySet().stream()
                                        .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().toPToto())))
                        .build());
    }
}
