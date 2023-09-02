package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.DelTeamEntityNotifyOuterClass.DelTeamEntityNotify;
import java.util.List;

public class PacketDelTeamEntityNotify extends BasePacket {

    public PacketDelTeamEntityNotify(int sceneId, int teamEntityId) {
        super(PacketOpcodes.DelTeamEntityNotify);

        DelTeamEntityNotify proto =
                DelTeamEntityNotify.newBuilder()
                        .setSceneId(sceneId)
                        .addDelEntityIdList(teamEntityId)
                        .build();

        this.setData(proto);
    }

    public PacketDelTeamEntityNotify(int sceneId, List<Integer> list) {
        super(PacketOpcodes.DelTeamEntityNotify);

        DelTeamEntityNotify proto =
                DelTeamEntityNotify.newBuilder().setSceneId(sceneId).addAllDelEntityIdList(list).build();

        this.setData(proto);
    }
}
