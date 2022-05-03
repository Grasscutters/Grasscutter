package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
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
            CommandHandler.sendMessage(null, Grasscutter.getLanguage().Run_this_command_in_game);
            return;
        }

        if (args.size() < 1) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().EnterDungeon_usage);
            return;
        }

        try {
            int dungeonId = Integer.parseInt(args.get(0));
            
            if (dungeonId == sender.getSceneId()) {
            	CommandHandler.sendMessage(sender, Grasscutter.getLanguage().EnterDungeon_you_in_that_dungeon);
            	return;
            }
            
            boolean result = sender.getServer().getDungeonManager().enterDungeon(sender.getSession().getPlayer(), 0, dungeonId);
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().EnterDungeon_changed_to_dungeon + dungeonId);

            if (!result) {
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().EnterDungeon_dungeon_not_found);
            }
        } catch (Exception e) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().EnterDungeon_usage);
        }
    }
}
