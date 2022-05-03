package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

@Command(label = "coop", usage = "coop",
        description = "Forces someone to join the world of others", permission = "server.coop")
public final class CoopCommand implements CommandHandler {
    @Override
    public void execute(Player sender, List<String> args) {
        if (args.size() < 2) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Coop_usage);
            return;
        }
        
        try {
            int tid = Integer.parseInt(args.get(0));
            int hostId = Integer.parseInt(args.get(1));
            Player host = sender.getServer().getPlayerByUid(hostId);
            Player want = sender.getServer().getPlayerByUid(tid);
            if (host == null || want == null) {
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Player_is_offline);
                return;
            }
            if (want.isInMultiplayer()) {
                sender.getServer().getMultiplayerManager().leaveCoop(want);
            }
            sender.getServer().getMultiplayerManager().applyEnterMp(want, hostId);
            sender.getServer().getMultiplayerManager().applyEnterMpReply(host, tid, true);
        } catch (Exception e) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Invalid_playerId);
        }
    }
}
