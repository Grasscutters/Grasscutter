package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.EvtEntityRenderersChangedNotifyOuterClass;

public class PacketEvtEntityRenderersChangedNotify extends BasePacket {

	public PacketEvtEntityRenderersChangedNotify(EvtEntityRenderersChangedNotifyOuterClass.EvtEntityRenderersChangedNotify req) {
		super(PacketOpcodes.EvtEntityRenderersChangedNotify, true);

        this.setData(req);
	}
}
