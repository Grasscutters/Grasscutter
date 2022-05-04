package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

@Command(label = "coop", usage = "coop [host UID]",
        description = "Forces someone to join the world of others", permission = "server.coop")
public final class CoopCommand implements CommandHandler {
    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
		if (targetPlayer == null) {
			CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Target_needed);
			return;
		}

        Player host = sender;
        switch (args.size()) {
            case 0:  // Summon target to self
                break;
            case 1:  // Summon target to argument
                try {
                    int hostId = Integer.parseInt(args.get(1));
                    host = sender.getServer().getPlayerByUid(hostId);
                    if (host == null) {
                        CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Player_is_offline);
                        return;
                    }
                    break;
                } catch (Exception e) {
                    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Invalid_playerId);
                    return;
                }
            default:
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Coop_usage);
                return;
        }
        
        
        if (targetPlayer.isInMultiplayer()) {
            sender.getServer().getMultiplayerManager().leaveCoop(targetPlayer);
        }
        sender.getServer().getMultiplayerManager().applyEnterMp(targetPlayer, host.getUid());
        sender.getServer().getMultiplayerManager().applyEnterMpReply(host, targetPlayer.getUid(), true);
        CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Coop_success.replace("{host}", host.getNickname()).replace("{target}", targetPlayer.getNickname()));
    }
}
