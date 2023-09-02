package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.AbilityInvokeEntryOuterClass.AbilityInvokeEntry;
import emu.grasscutter.net.proto.ClientAbilityInitFinishNotifyOuterClass.ClientAbilityInitFinishNotify;
import java.util.List;

public class PacketClientAbilityInitFinishNotify extends BasePacket {

    public PacketClientAbilityInitFinishNotify(List<AbilityInvokeEntry> entries) {
        super(PacketOpcodes.ClientAbilityInitFinishNotify, true);

        int entityId = 0;

        if (entries.size() > 0) {
            AbilityInvokeEntry entry = entries.get(0);
            entityId = entry.getEntityId();
        }

        ClientAbilityInitFinishNotify proto =
                ClientAbilityInitFinishNotify.newBuilder()
                        .setEntityId(entityId)
                        .addAllInvokes(entries)
                        .build();

        this.setData(proto);
    }
}
