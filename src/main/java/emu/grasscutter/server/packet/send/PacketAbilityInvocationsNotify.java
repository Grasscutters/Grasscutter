package emu.grasscutter.server.packet.send;

import java.util.List;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AbilityInvocationsNotifyOuterClass.AbilityInvocationsNotify;
import emu.grasscutter.net.proto.AbilityInvokeEntryOuterClass.AbilityInvokeEntry;

public class PacketAbilityInvocationsNotify extends GenshinPacket {
	
	public PacketAbilityInvocationsNotify(AbilityInvokeEntry entry) {
		super(PacketOpcodes.AbilityInvocationsNotify, true);
		
		AbilityInvocationsNotify proto = AbilityInvocationsNotify.newBuilder()
				.addInvokes(entry)
				.build();

		this.setData(proto);
	}
	
	public PacketAbilityInvocationsNotify(List<AbilityInvokeEntry> entries) {
		super(PacketOpcodes.AbilityInvocationsNotify, true);
		
		AbilityInvocationsNotify proto = AbilityInvocationsNotify.newBuilder()
				.addAllInvokes(entries)
				.build();
		
		this.setData(proto);
	}
	
}
