package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.CancelCoopTaskRspOuterClass;

public class PacketCancelCoopTaskRsp extends BasePacket {

    public PacketCancelCoopTaskRsp(int chapterId) {
        super(PacketOpcodes.SetCoopChapterViewedRsp);

        CancelCoopTaskRspOuterClass.CancelCoopTaskRsp proto =
                CancelCoopTaskRspOuterClass.CancelCoopTaskRsp.newBuilder().setChapterId(chapterId).build();

        this.setData(proto);
    }
}
