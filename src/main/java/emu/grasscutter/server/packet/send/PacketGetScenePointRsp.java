package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetScenePointRspOuterClass.GetScenePointRsp;

public class PacketGetScenePointRsp extends GenshinPacket {
	
	public PacketGetScenePointRsp(int sceneId) {
		super(PacketOpcodes.GetScenePointRsp);

		GetScenePointRsp.Builder p = GetScenePointRsp.newBuilder()
				.setSceneId(sceneId);
		
		for (int i = 1; i < 1000; i++) {
			p.addUnlockedPointList(i);
		}
		
		for (int i = 1; i < 9; i++) {
			p.addUnlockAreaList(i);
		}

		this.setData(p);
	}
}
