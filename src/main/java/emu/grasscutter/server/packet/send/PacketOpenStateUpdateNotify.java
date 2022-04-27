package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.props.OpenState;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.OpenStateUpdateNotifyOuterClass.OpenStateUpdateNotify;

public class PacketOpenStateUpdateNotify extends BasePacket {
	
	public PacketOpenStateUpdateNotify() {
		super(PacketOpcodes.OpenStateUpdateNotify);
		
		OpenStateUpdateNotify.Builder proto = OpenStateUpdateNotify.newBuilder();
		
		for (OpenState type : OpenState.values()) {
			if (type.getValue() > 0) {
				proto.putOpenStateMap(type.getValue(), 1);
			}
		}

		this.setData(proto);
	}
}
