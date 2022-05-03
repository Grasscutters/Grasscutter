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
    public void execute(Player sender, List<String> args) {
        if (sender == null) {
            CommandHandler.sendMessage(null, Grasscutter.getLanguage().Run_this_command_in_game);
            return;
        }
        
        if (!sender.getWorld().isMultiplayer()) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().TeleportAll_message);
            return;
        }
        
        for (Player player : sender.getWorld().getPlayers()) {
            if (player.equals(sender))
                continue;
            Position pos = sender.getPos();

            player.getWorld().transferPlayerToScene(player, sender.getSceneId(), pos);
        }
    }
}
