package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

@Command(label = "setbp", usage = "", aliases = "bp",permission = "player.setbp", description = "")
public final class SetBPLevelCommand implements CommandHandler {
    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size() < 1) {
            CommandHandler.sendMessage(sender , "Need a arg");
            return;
        }

        int level = Integer.parseInt(args.get(0));

        sender.getBattlePassManager().addPoints(level);
    }
}
