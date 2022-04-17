package emu.grasscutter.server.packet.send;

import java.util.List;

import emu.grasscutter.game.inventory.GenshinItem;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ItemAddHintNotifyOuterClass.ItemAddHintNotify;

public class PacketItemAddHintNotify extends GenshinPacket {
	
	public PacketItemAddHintNotify(GenshinItem item, ActionReason reason) {
		super(PacketOpcodes.ItemAddHintNotify);
		
		ItemAddHintNotify proto = ItemAddHintNotify.newBuilder()
				.addItemList(item.toItemHintProto())
				.setReason(reason.getValue())
				.build();
		
		this.setData(proto);
	}

	public PacketItemAddHintNotify(List<GenshinItem> items, ActionReason reason) {
		super(PacketOpcodes.ItemAddHintNotify);
		
		ItemAddHintNotify.Builder proto = ItemAddHintNotify.newBuilder()
				.setReason(reason.getValue());
		
		for (GenshinItem item : items) {
			proto.addItemList(item.toItemHintProto());
		}
		
		this.setData(proto);
	}
}
