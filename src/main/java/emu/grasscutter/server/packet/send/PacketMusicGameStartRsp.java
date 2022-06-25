package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.MusicGameStartRspOuterClass;

public class PacketMusicGameStartRsp extends BasePacket {

	public PacketMusicGameStartRsp(int musicBasicId) {
		super(PacketOpcodes.MusicGameStartRsp);

		var proto = MusicGameStartRspOuterClass.MusicGameStartRsp.newBuilder();

		proto.setMusicBasicId(musicBasicId);

		this.setData(proto);
	}
}
