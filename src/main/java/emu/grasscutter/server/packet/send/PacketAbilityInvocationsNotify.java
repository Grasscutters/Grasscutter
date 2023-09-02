package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.AbilityInvocationsNotifyOuterClass.AbilityInvocationsNotify;
import emu.grasscutter.net.proto.AbilityInvokeEntryOuterClass.AbilityInvokeEntry;
import java.util.List;

public class PacketAbilityInvocationsNotify extends BasePacket {

    public PacketAbilityInvocationsNotify(AbilityInvokeEntry entry) {
        super(PacketOpcodes.AbilityInvocationsNotify, true);

        AbilityInvocationsNotify proto =
                AbilityInvocationsNotify.newBuilder().addInvokes(entry).build();

        this.setData(proto);
    }

    public PacketAbilityInvocationsNotify(List<AbilityInvokeEntry> entries) {
        super(PacketOpcodes.AbilityInvocationsNotify, true);

        AbilityInvocationsNotify proto =
                AbilityInvocationsNotify.newBuilder().addAllInvokes(entries).build();

        this.setData(proto);
    }
}
