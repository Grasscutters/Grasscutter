package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.RetcodeOuterClass;
import emu.grasscutter.net.proto.TryEnterHomeRspOuterClass;

public class PacketTryEnterHomeRsp extends BasePacket {

    public PacketTryEnterHomeRsp() {
        super(PacketOpcodes.TryEnterHomeRsp);

        TryEnterHomeRspOuterClass.TryEnterHomeRsp proto =
                TryEnterHomeRspOuterClass.TryEnterHomeRsp.newBuilder()
                        .setRetcode(RetcodeOuterClass.Retcode.RET_HOME_APPLY_ENTER_OTHER_HOME_FAIL_VALUE)
                        .build();

        this.setData(proto);
    }

    public PacketTryEnterHomeRsp(int uid) {
        super(PacketOpcodes.TryEnterHomeRsp);

        TryEnterHomeRspOuterClass.TryEnterHomeRsp proto =
                TryEnterHomeRspOuterClass.TryEnterHomeRsp.newBuilder()
                        .setTargetUid(uid)
                        .build();

        this.setData(proto);
    }

    public PacketTryEnterHomeRsp(int retCode, int uid) {
        super(PacketOpcodes.TryEnterHomeRsp);

        TryEnterHomeRspOuterClass.TryEnterHomeRsp proto =
                TryEnterHomeRspOuterClass.TryEnterHomeRsp.newBuilder()
                        .setRetcode(retCode)
                        .setTargetUid(uid)
                        .build();

        this.setData(proto);
    }
}
