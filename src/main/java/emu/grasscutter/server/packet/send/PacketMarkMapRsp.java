package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.managers.MapMarkManager.MapMark;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.*;

import java.util.*;

public class PacketMarkMapRsp extends BasePacket {

    public PacketMarkMapRsp(Player player, HashMap<String, MapMark> mapMarks) {
        super(PacketOpcodes.MarkMapRsp);

        MarkMapRspOuterClass.MarkMapRsp.Builder proto = MarkMapRspOuterClass.MarkMapRsp.newBuilder();
        proto.setRetcode(0);

        if (mapMarks != null) {
            for (MapMark mapMark: mapMarks.values()) {
                MapMarkPointOuterClass.MapMarkPoint.Builder markPoint = MapMarkPointOuterClass.MapMarkPoint.newBuilder();
                markPoint.setSceneId(mapMark.getSceneId());
                markPoint.setName(mapMark.getName());

                VectorOuterClass.Vector.Builder positionVector = VectorOuterClass.Vector.newBuilder();
                positionVector.setX(mapMark.getPosition().getX());
                positionVector.setY(mapMark.getPosition().getY());
                positionVector.setZ(mapMark.getPosition().getZ());
                markPoint.setPos(positionVector.build());

                markPoint.setPointType(mapMark.getMapMarkPointType());
                markPoint.setFromType(mapMark.getMapMarkFromType());
                markPoint.setMonsterId(mapMark.getMonsterId());
                markPoint.setQuestId(mapMark.getQuestId());

                proto.addMarkList(markPoint.build());
            }
        }

        MarkMapRspOuterClass.MarkMapRsp data = proto.build();
        this.setData(data);
    }
}