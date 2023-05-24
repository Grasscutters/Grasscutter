package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AbilityInvokeEntryOuterClass.AbilityInvokeEntry;
import emu.grasscutter.net.proto.ClientAbilitiesInitFinishCombineNotifyOuterClass.ClientAbilitiesInitFinishCombineNotify;
import emu.grasscutter.net.proto.EntityAbilityInvokeEntryOuterClass.EntityAbilityInvokeEntry;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.utils.Utils;

@Opcodes(PacketOpcodes.ClientAbilitiesInitFinishCombineNotify)
public class HandlerClientAbilitiesInitFinishCombineNotify extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        ClientAbilitiesInitFinishCombineNotify notif = ClientAbilitiesInitFinishCombineNotify.parseFrom(payload);

        Player player = session.getPlayer();

        // Call skill end in the player's ability manager.
        player.getAbilityManager().onSkillEnd(player);

        for(EntityAbilityInvokeEntry entry : notif.getEntityInvokeListList()) {
            for (AbilityInvokeEntry ability : entry.getInvokesList()) {
                player.getAbilityManager().onAbilityInvoke(ability);
                player.getClientAbilityInitFinishHandler().addEntry(ability.getForwardType(), ability);
            }

            if (entry.getInvokesList().size() > 0) {
                session.getPlayer().getClientAbilityInitFinishHandler().update(session.getPlayer());
            }
        }
    }
}
