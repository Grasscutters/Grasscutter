package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ChapterStateNotifyOuterClass;
import emu.grasscutter.net.proto.ChapterStateOuterClass;

public class PacketChapterStateNotify extends BasePacket {

	public PacketChapterStateNotify(int id, ChapterStateOuterClass.ChapterState state) {
		super(PacketOpcodes.ChapterStateNotify);

        var proto = ChapterStateNotifyOuterClass.ChapterStateNotify.newBuilder();

        proto.setChapterId(id)
            .setChapterState(state);

        this.setData(proto);
	}
}
