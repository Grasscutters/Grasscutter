package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SceneKickPlayerRspOuterClass.SceneKickPlayerRsp;

public class PacketSceneKickPlayerRsp extends GenshinPacket {
	
	public PacketSceneKickPlayerRsp(int targetUid) {
		super(PacketOpcodes.SceneKickPlayerRsp);

		SceneKickPlayerRsp proto = SceneKickPlayerRsp.newBuilder()
				.setTargetUid(targetUid)
				.build();
		
		this.setData(proto);
	}
	
	public PacketSceneKickPlayerRsp() {
		super(PacketOpcodes.SceneKickPlayerRsp);

		SceneKickPlayerRsp proto = SceneKickPlayerRsp.newBuilder()
				.setRetcode(1)
				.build();
		
		this.setData(proto);
	}
}
