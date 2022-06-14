package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.home.GameHome;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeMarkPointNotifyOuterClass;

public class PacketHomeMarkPointNotify extends BasePacket {

	public PacketHomeMarkPointNotify(Player player, GameHome home) {
		super(PacketOpcodes.HomeMarkPointNotify);

		var proto = HomeMarkPointNotifyOuterClass.HomeMarkPointNotify.newBuilder();


		this.setData(proto);
	}
}
