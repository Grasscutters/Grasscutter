package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ChooseCurAvatarTeamRspOuterClass.ChooseCurAvatarTeamRsp;

public class PacketChooseCurAvatarTeamRsp extends BasePacket {

    public PacketChooseCurAvatarTeamRsp(int teamId) {
        super(PacketOpcodes.ChooseCurAvatarTeamRsp);

        ChooseCurAvatarTeamRsp proto = ChooseCurAvatarTeamRsp.newBuilder()
            .setCurTeamId(teamId)
            .build();

        this.setData(proto);
    }
}
