package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "coop", usage = "coop [host UID]",
        description = "Forces someone to join the world of others", permission = "server.coop")
public final class CoopCommand implements CommandHandler {
    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
		if (targetPlayer == null) {
			CommandHandler.sendMessage(sender, translate("commands.execution.need_target"));
			return;
		}

        Player host = sender;
        switch (args.size()) {
            case 0:  // Summon target to self
                CommandHandler.sendMessage(sender, translate("commands.coop.usage"));
                if (sender == null) // Console doesn't have a self to summon to
                    return;
                break;
            case 1:  // Summon target to argument
                try {
                    int hostId = Integer.parseInt(args.get(0));
                    host = Grasscutter.getGameServer().getPlayerByUid(hostId);
                    if (host == null) {
                        CommandHandler.sendMessage(sender, translate("commands.execution.player_offline_error"));
                        return;
                    }
                    break;
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, translate("commands.execution.uid_error"));
                    return;
                }
            default:
                CommandHandler.sendMessage(sender, translate("commands.coop.usage"));
                return;
        }
        
        // There's no target==host check but this just places them in multiplayer in their own world which seems fine.
        if (targetPlayer.isInMultiplayer()) {
            targetPlayer.getServer().getMultiplayerManager().leaveCoop(targetPlayer);
        }
        host.getServer().getMultiplayerManager().applyEnterMp(targetPlayer, host.getUid());
        targetPlayer.getServer().getMultiplayerManager().applyEnterMpReply(host, targetPlayer.getUid(), true);
        CommandHandler.sendMessage(sender, translate("commands.coop.success", targetPlayer.getNickname(), host.getNickname()));
    }
}
