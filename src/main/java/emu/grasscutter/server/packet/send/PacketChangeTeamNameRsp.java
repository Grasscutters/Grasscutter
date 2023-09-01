package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ChangeTeamNameRspOuterClass.ChangeTeamNameRsp;

public class PacketChangeTeamNameRsp extends BasePacket {

    public PacketChangeTeamNameRsp(int teamId, String teamName) {
        super(PacketOpcodes.ChangeTeamNameRsp);

        ChangeTeamNameRsp proto =
                ChangeTeamNameRsp.newBuilder().setTeamId(teamId).setTeamName(teamName).build();

        this.setData(proto);
    }
}
