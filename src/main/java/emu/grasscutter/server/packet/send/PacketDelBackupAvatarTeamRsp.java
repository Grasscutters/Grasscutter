package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;
import emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp;

public class PacketDelBackupAvatarTeamRsp extends BasePacket {
    public PacketDelBackupAvatarTeamRsp(Retcode retcode, int id) {
          super(PacketOpcodes.DelBackupAvatarTeamRsp);

        DelBackupAvatarTeamRsp proto = DelBackupAvatarTeamRsp.newBuilder()
            .setRetcode(retcode.getNumber())
            .setBackupAvatarTeamId(id)
            .build();

        this.setData(proto);
    }

    public PacketDelBackupAvatarTeamRsp(int id) {
        this(Retcode.RET_SUCC, id);
    }
}
