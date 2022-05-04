package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

@Command(label = "kick", usage = "kick <player>",
        description = "Kicks the specified player from the server (WIP)", permission = "server.kick")
public final class KickCommand implements CommandHandler {

    @Override
    public void execute(Player sender, List<String> args) {
        int target = Integer.parseInt(args.get(0));

        Player targetPlayer = Grasscutter.getGameServer().getPlayerByUid(target);
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Player_not_found);
            return;
        }

        if (sender != null) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Kick_player_kick_player.replace("{sendUid}", Integer.toString(sender.getAccount().getPlayerUid())).replace("{sendName}", sender.getAccount().getUsername()).replace("kickUid", Integer.toString(target)).replace("{kickName}", targetPlayer.getAccount().getUsername()));
        } else {
            CommandHandler.sendMessage(null, Grasscutter.getLanguage().Kick_server_player.replace("{kickUid}", Integer.toString(target)).replace("{kickName}", targetPlayer.getAccount().getUsername()));
        }

        targetPlayer.getSession().close();
    }
}
