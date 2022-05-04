package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

@Command(label = "kick", usage = "kick",
        description = "Kicks the specified player from the server (WIP)", permission = "server.kick")
public final class KickCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Target_needed);
            return;
        }

        if (sender != null) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Kick_player_kick_player.replace("{sendUid}", Integer.toString(sender.getAccount().getPlayerUid())).replace("{sendName}", sender.getAccount().getUsername()).replace("kickUid", Integer.toString(targetPlayer.getUid())).replace("{kickName}", targetPlayer.getAccount().getUsername()));
        } else {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Kick_server_player.replace("{kickUid}", Integer.toString(targetPlayer.getUid())).replace("{kickName}", targetPlayer.getAccount().getUsername()));
        }

        targetPlayer.getSession().close();
    }
}
