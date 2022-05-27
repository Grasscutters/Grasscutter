package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "enterdungeon", usage = "enterdungeon <dungeon id>",
        description = "Enter a dungeon", aliases = {"dungeon"}, permission = "player.enterdungeon")
public final class EnterDungeonCommand implements CommandHandler {
    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (targetPlayer == null) {
            CommandHandler.sendMessage(null, translate("commands.execution.need_target"));
            return;
        }

        if (args.size() < 1) {
            CommandHandler.sendMessage(sender, translate("commands.enter_dungeon.usage"));
            return;
        }

        try {
            int dungeonId = Integer.parseInt(args.get(0));
            if (dungeonId == targetPlayer.getSceneId()) {
            	CommandHandler.sendMessage(sender, translate("commands.enter_dungeon.in_dungeon_error"));
            	return;
            }
            
            boolean result = targetPlayer.getServer().getDungeonManager().enterDungeon(targetPlayer.getSession().getPlayer(), 0, dungeonId);
            CommandHandler.sendMessage(sender, translate("commands.enter_dungeon.changed", dungeonId));

            if (!result) {
                CommandHandler.sendMessage(sender, translate("commands.enter_dungeon.not_found_error"));
            }
        } catch (Exception e) {
            CommandHandler.sendMessage(sender, translate("commands.enter_dungeon.usage"));
        }
    }
}
