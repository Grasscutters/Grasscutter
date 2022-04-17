package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.gacha.GachaManager;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;

public class PacketGetGachaInfoRsp extends GenshinPacket {
	
	public PacketGetGachaInfoRsp(GachaManager manager) {
		super(PacketOpcodes.GetGachaInfoRsp);
		
		this.setData(manager.toProto());
	}
}
