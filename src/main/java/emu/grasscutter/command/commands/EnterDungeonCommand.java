package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

@Command(label = "enterdungeon", usage = "enterdungeon <dungeon id>",
        description = "Enter a dungeon", aliases = {"dungeon"}, permission = "player.enterdungeon")
public final class EnterDungeonCommand implements CommandHandler {
    @Override
    public void execute(Player sender, List<String> args) {
        if (sender == null) {
            CommandHandler.sendMessage(null, "Run this command in-game.");
            return;
        }

        if (args.size() < 1) {
            CommandHandler.sendMessage(sender, "Usage: enterdungeon <dungeon id>");
            return;
        }

        try {
            int dungeonId = Integer.parseInt(args.get(0));
            
            if (dungeonId == sender.getSceneId()) {
            	CommandHandler.sendMessage(sender, "You are already in that dungeon");
            	return;
            }
            
            boolean result = sender.getServer().getDungeonManager().enterDungeon(sender.getSession().getPlayer(), 0, dungeonId);
            CommandHandler.sendMessage(sender, "Changed to dungeon " + dungeonId);

            if (!result) {
                CommandHandler.sendMessage(sender, "Dungeon does not exist");
            }
        } catch (Exception e) {
            CommandHandler.sendMessage(sender, "Usage: enterdungeon <dungeon id>");
        }
    }
}
