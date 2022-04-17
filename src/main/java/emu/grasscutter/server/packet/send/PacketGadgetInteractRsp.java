package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GadgetInteractRspOuterClass.GadgetInteractRsp;
import emu.grasscutter.net.proto.InteractTypeOuterClass.InteractType;

public class PacketGadgetInteractRsp extends GenshinPacket {
	public PacketGadgetInteractRsp(EntityGadget gadget, InteractType interact) {
		super(PacketOpcodes.GadgetInteractRsp);

		GadgetInteractRsp proto = GadgetInteractRsp.newBuilder()
				.setGadgetEntityId(gadget.getId())
				.setInteractType(interact)
				.setGadgetId(gadget.getGadgetId())
				.build();
		
		this.setData(proto);
	}
	
	public PacketGadgetInteractRsp() {
		super(PacketOpcodes.GadgetInteractRsp);

		GadgetInteractRsp proto = GadgetInteractRsp.newBuilder()
				.setRetcode(1)
				.build();
		
		this.setData(proto);
	}
}
