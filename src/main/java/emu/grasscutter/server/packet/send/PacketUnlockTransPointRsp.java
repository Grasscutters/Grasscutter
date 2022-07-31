package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;
import emu.grasscutter.net.proto.UnlockTransPointRspOuterClass.UnlockTransPointRsp;

public class PacketUnlockTransPointRsp extends BasePacket {
	public PacketUnlockTransPointRsp(Retcode retcode) {
		super(PacketOpcodes.UnlockTransPointRsp);
		
		UnlockTransPointRsp proto = UnlockTransPointRsp.newBuilder()
				.setRetcode(retcode.getNumber())
				.build();
		
		this.setData(proto);
	}
}
