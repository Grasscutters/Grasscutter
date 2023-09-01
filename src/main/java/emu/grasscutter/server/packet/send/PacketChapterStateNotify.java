package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.*;

public class PacketChapterStateNotify extends BasePacket {

    public PacketChapterStateNotify(int id, ChapterStateOuterClass.ChapterState state) {
        super(PacketOpcodes.ChapterStateNotify);

        var proto = ChapterStateNotifyOuterClass.ChapterStateNotify.newBuilder();

        proto.setChapterId(id).setChapterState(state);

        this.setData(proto);
    }
}
