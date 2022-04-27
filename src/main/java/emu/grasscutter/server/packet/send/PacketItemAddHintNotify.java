package emu.grasscutter.server.packet.send;

import java.util.Collection;
import java.util.List;

import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ItemAddHintNotifyOuterClass.ItemAddHintNotify;

public class PacketItemAddHintNotify extends BasePacket {
	
	public PacketItemAddHintNotify(GameItem item, ActionReason reason) {
		super(PacketOpcodes.ItemAddHintNotify);
		
		ItemAddHintNotify proto = ItemAddHintNotify.newBuilder()
				.addItemList(item.toItemHintProto())
				.setReason(reason.getValue())
				.build();
		
		this.setData(proto);
	}

	public PacketItemAddHintNotify(Collection<GameItem> items, ActionReason reason) {
		super(PacketOpcodes.ItemAddHintNotify);
		
		ItemAddHintNotify.Builder proto = ItemAddHintNotify.newBuilder()
				.setReason(reason.getValue());
		
		for (GameItem item : items) {
			proto.addItemList(item.toItemHintProto());
		}
		
		this.setData(proto);
	}
}
