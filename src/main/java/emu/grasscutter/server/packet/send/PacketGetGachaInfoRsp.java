package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.gacha.GachaManager;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;

public class PacketGetGachaInfoRsp extends BasePacket {
	
	@Deprecated
	public PacketGetGachaInfoRsp(GachaManager manager) {
		super(PacketOpcodes.GetGachaInfoRsp);
		
		this.setData(manager.toProto());
	}

	public PacketGetGachaInfoRsp(GachaManager manager, String sessionKey) {
		super(PacketOpcodes.GetGachaInfoRsp);
		
		this.setData(manager.toProto(sessionKey));
	}

}
