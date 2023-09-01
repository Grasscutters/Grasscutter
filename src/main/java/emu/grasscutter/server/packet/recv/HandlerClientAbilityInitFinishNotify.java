package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.AbilityInvokeEntryOuterClass.AbilityInvokeEntry;
import emu.grasscutter.net.proto.ClientAbilityInitFinishNotifyOuterClass.ClientAbilityInitFinishNotify;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.ClientAbilityInitFinishNotify)
public class HandlerClientAbilityInitFinishNotify extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        ClientAbilityInitFinishNotify notif = ClientAbilityInitFinishNotify.parseFrom(payload);

        Player player = session.getPlayer();

        // Call skill end in the player's ability manager.
        player.getAbilityManager().onSkillEnd(player);

        for (AbilityInvokeEntry entry : notif.getInvokesList()) {
            player.getAbilityManager().onAbilityInvoke(entry);
            player.getClientAbilityInitFinishHandler().addEntry(entry.getForwardType(), entry);
        }

        if (notif.getInvokesList().size() > 0) {
            session.getPlayer().getClientAbilityInitFinishHandler().update(session.getPlayer());
        }
    }
}
