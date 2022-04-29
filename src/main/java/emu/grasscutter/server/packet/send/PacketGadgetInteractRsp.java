package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.entity.EntityBaseGadget;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GadgetInteractRspOuterClass.GadgetInteractRsp;
import emu.grasscutter.net.proto.InteractTypeOuterClass.InteractType;
import emu.grasscutter.net.proto.RetcodeOuterClass;

public class PacketGadgetInteractRsp extends BasePacket {
	public PacketGadgetInteractRsp(EntityBaseGadget gadget, InteractType interact) {
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
				.setRetcode(RetcodeOuterClass.Retcode.RET_SVR_ERROR_VALUE)
				.build();
		
		this.setData(proto);
	}
}
