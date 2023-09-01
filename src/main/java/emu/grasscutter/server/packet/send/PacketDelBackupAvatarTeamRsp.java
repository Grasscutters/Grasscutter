package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.DelBackupAvatarTeamRspOuterClass.DelBackupAvatarTeamRsp;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;

public class PacketDelBackupAvatarTeamRsp extends BasePacket {
    public PacketDelBackupAvatarTeamRsp(Retcode retcode, int id) {
        super(PacketOpcodes.DelBackupAvatarTeamRsp);

        DelBackupAvatarTeamRsp proto =
                DelBackupAvatarTeamRsp.newBuilder()
                        .setRetcode(retcode.getNumber())
                        .setBackupAvatarTeamId(id)
                        .build();

        this.setData(proto);
    }

    public PacketDelBackupAvatarTeamRsp(int id) {
        this(Retcode.RET_SUCC, id);
    }
}
