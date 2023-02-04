package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.managers.mapmark.MapMark;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.MarkMapRspOuterClass.MarkMapRsp;
import emu.grasscutter.net.proto.MapMarkPointOuterClass.MapMarkPoint;

import java.util.*;

public class PacketMarkMapRsp extends BasePacket {

    public PacketMarkMapRsp(Map<String, MapMark> mapMarks) {
        super(PacketOpcodes.MarkMapRsp);

        var proto = MarkMapRsp.newBuilder();
        proto.setRetcode(0);

        if (mapMarks != null) {
            for (MapMark mapMark: mapMarks.values()) {
                var markPoint = MapMarkPoint.newBuilder();
                markPoint.setSceneId(mapMark.getSceneId());
                markPoint.setName(mapMark.getName());
                markPoint.setPos(mapMark.getPosition().toProto());
                markPoint.setPointType(mapMark.getMapMarkPointType());
                markPoint.setFromType(mapMark.getMapMarkFromType());
                markPoint.setMonsterId(mapMark.getMonsterId());
                markPoint.setQuestId(mapMark.getQuestId());

                proto.addMarkList(markPoint.build());
            }
        }
        this.setData(proto.build());
    }
}
