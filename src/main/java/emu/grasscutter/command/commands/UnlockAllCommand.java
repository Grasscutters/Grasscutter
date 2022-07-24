package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.GameData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.player.PlayerOpenStateManager;
import emu.grasscutter.server.packet.send.PacketOpenStateChangeNotify;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "unlockall", usage = {""}, permission = "player.unlockall", permissionTargeted = "player.unlockall.others")
public final class UnlockAllCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        Map<Integer, Integer> changed = new HashMap<>();

        for (var state : GameData.getOpenStateList()) {
            // Don't unlock blacklisted open states.
            if (PlayerOpenStateManager.BLACKLIST_OPEN_STATES.contains(state.getId())) {
                continue;
            }

            if (targetPlayer.getOpenStateManager().getOpenState(state.getId()) == 0) {
                targetPlayer.getOpenStateManager().getOpenStateMap().put(state.getId(), 1); 
                changed.put(state.getId(), 1);
            }
        }
        
        targetPlayer.sendPacket(new PacketOpenStateChangeNotify(changed));
        
        CommandHandler.sendMessage(sender, translate(sender, "commands.unlockall.success", targetPlayer.getNickname()));
    }
}
