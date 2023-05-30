package emu.grasscutter.server.packet.send;

import java.util.List;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ClientAbilitiesInitFinishCombineNotifyOuterClass.ClientAbilitiesInitFinishCombineNotify;
import emu.grasscutter.net.proto.EntityAbilityInvokeEntryOuterClass.EntityAbilityInvokeEntry;

public class PacketClientAbilitiesInitFinishCombineNotify extends BasePacket {

	public PacketClientAbilitiesInitFinishCombineNotify(List<EntityAbilityInvokeEntry> entries) {
		super(PacketOpcodes.ClientAbilitiesInitFinishCombineNotify, true);

		ClientAbilitiesInitFinishCombineNotify proto = ClientAbilitiesInitFinishCombineNotify.newBuilder()
				.addAllEntityInvokeList(entries)
				.build();

		this.setData(proto);
	}
}
