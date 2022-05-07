package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

@Command(label = "restart", usage = "restart - Restarts the current session")
public final class RestartCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
		if (sender == null) {
			return;
		}
        sender.getSession().close();
    }
}
