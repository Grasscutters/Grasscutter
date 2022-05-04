package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.utils.Position;

import java.util.List;

@Command(label = "tpall", usage = "tpall",
        description = "Teleports all players in your world to your position", permission = "player.tpall")
public final class TeleportAllCommand implements CommandHandler {
    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Target_needed);
            return;
        }
        
        if (!targetPlayer.getWorld().isMultiplayer()) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().TeleportAll_message);
            return;
        }
        
        for (Player player : targetPlayer.getWorld().getPlayers()) {
            if (player.equals(targetPlayer))
                continue;
            Position pos = targetPlayer.getPos();

            player.getWorld().transferPlayerToScene(player, targetPlayer.getSceneId(), pos);
        }
    }
}
