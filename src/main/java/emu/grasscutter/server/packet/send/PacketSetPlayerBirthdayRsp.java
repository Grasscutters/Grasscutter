package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SetPlayerBirthdayRspOuterClass.SetPlayerBirthdayRsp;
import emu.grasscutter.net.proto.SetPlayerBornDataReqOuterClass;

public class PacketSetPlayerBirthdayRsp extends GenshinPacket {

	public PacketSetPlayerBirthdayRsp(int retCode) {
		super(PacketOpcodes.SetPlayerBirthdayRsp);

		SetPlayerBirthdayRsp proto = SetPlayerBirthdayRsp.newBuilder()
				.setRetcode(retCode)
				.build();

		this.setData(proto);
	}

	public PacketSetPlayerBirthdayRsp(GenshinPlayer player) {
		super(PacketOpcodes.SetPlayerBirthdayRsp);

		SetPlayerBirthdayRsp proto = SetPlayerBirthdayRsp.newBuilder()
				.setBirthday(player.getBirthday().toProto())
				.build();

		this.setData(proto);
	}
}
