package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.UnlockPersonalLineRspOuterClass;

public class PacketUnlockPersonalLineRsp extends BasePacket {

	public PacketUnlockPersonalLineRsp(int id, int level, int chapterId) {
		super(PacketOpcodes.UnlockPersonalLineRsp);

        var proto = UnlockPersonalLineRspOuterClass.UnlockPersonalLineRsp.newBuilder();

        proto.setPersonalLineId(id)
            .setLevel(level)
            .setChapterId(chapterId);

        this.setData(proto);
	}
}
