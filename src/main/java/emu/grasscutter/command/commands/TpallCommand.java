package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.utils.Position;

import java.util.List;

@Command(label = "tpall", usage = "tpall",
        description = "Teleports all players in your world to your position", permission = "player.tpall")
public class TpallCommand implements CommandHandler {
    @Override
    public void execute(Player sender, List<String> args) {
        if (sender == null) {
            CommandHandler.sendMessage(null, "Run this command in-game.");
            return;
        }
        if (!sender.getWorld().isMultiplayer()) {
            CommandHandler.sendMessage(sender, "You only can use this command in MP mode.");
            return;
        }
        for (Player gp : sender.getWorld().getPlayers()) {
            if (gp.equals(sender))
                continue;
            Position pos = sender.getPos();

            gp.getWorld().transferPlayerToScene(gp, sender.getSceneId(), pos);
        }
    }
}
