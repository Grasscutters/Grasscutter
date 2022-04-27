package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.RetcodeOuterClass;
import emu.grasscutter.net.proto.SceneKickPlayerRspOuterClass.SceneKickPlayerRsp;

public class PacketSceneKickPlayerRsp extends BasePacket {
	
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
				.setRetcode(RetcodeOuterClass.Retcode.RET_SVR_ERROR_VALUE)
				.build();
		
		this.setData(proto);
	}
}
