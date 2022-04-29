package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.WorktopOptionNotifyOuterClass.WorktopOptionNotify;

public class PacketWorktopOptionNotify extends BasePacket {
	
	public PacketWorktopOptionNotify(EntityGadget gadget) {
		super(PacketOpcodes.WorktopOptionNotify);
		
		WorktopOptionNotify.Builder proto = WorktopOptionNotify.newBuilder()
				.setGadgetEntityId(gadget.getId());
		
		if (gadget.getWorktopOptions() != null) {
			proto.addAllOptionList(gadget.getWorktopOptions());
		}
		
		this.setData(proto);
	}
}
