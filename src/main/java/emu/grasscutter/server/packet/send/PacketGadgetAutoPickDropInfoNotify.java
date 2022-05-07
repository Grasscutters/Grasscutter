package emu.grasscutter.server.packet.send;

import java.util.Collection;

import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GadgetAutoPickDropInfoNotifyOuterClass.GadgetAutoPickDropInfoNotify;
import emu.grasscutter.net.proto.GadgetAutoPickDropInfoNotifyOuterClass.GadgetAutoPickDropInfoNotify.Builder;

public class PacketGadgetAutoPickDropInfoNotify extends BasePacket {
	
	public PacketGadgetAutoPickDropInfoNotify(Collection<GameItem> items) {
		super(PacketOpcodes.GadgetAutoPickDropInfoNotify);
		
		GadgetAutoPickDropInfoNotify.Builder proto = GadgetAutoPickDropInfoNotify.newBuilder();
		
		items.forEach(item -> proto.addItemList(item.toProto()));
		
		this.setData(proto);
	}
}
