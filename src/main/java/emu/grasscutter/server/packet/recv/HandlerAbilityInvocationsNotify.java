package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AbilityInvocationsNotifyOuterClass.AbilityInvocationsNotify;
import emu.grasscutter.net.proto.AbilityInvokeEntryOuterClass.AbilityInvokeEntry;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.AbilityInvocationsNotify)
public class HandlerAbilityInvocationsNotify extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        AbilityInvocationsNotify notif = AbilityInvocationsNotify.parseFrom(payload);

        for (AbilityInvokeEntry entry : notif.getInvokesList()) {
            session.getPlayer().getAbilityManager().onAbilityInvoke(entry);
            session.getPlayer().getAbilityInvokeHandler().addEntry(entry.getForwardType(), entry);
        }
    }

}
