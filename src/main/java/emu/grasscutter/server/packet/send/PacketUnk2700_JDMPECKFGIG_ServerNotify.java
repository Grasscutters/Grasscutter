package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeUnknown1NotifyOuterClass;
import emu.grasscutter.net.proto.Unk2700JDMPECKFGIGServerNotify;

public class PacketUnk2700_JDMPECKFGIG_ServerNotify extends BasePacket {

	public PacketUnk2700_JDMPECKFGIG_ServerNotify(boolean isEnterEditMode) {
		super(PacketOpcodes.Unk2700_JDMPECKFGIG_ServerNotify);

		var proto = Unk2700JDMPECKFGIGServerNotify.Unk2700_JDMPECKFGIG_ServerNotify.newBuilder();

		proto.setIsEnterEditMode(isEnterEditMode);

		this.setData(proto);
	}
}
