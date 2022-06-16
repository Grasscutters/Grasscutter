package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;
import java.util.Objects;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "ban", usage = "ban <uid>", aliases = {"sus"}, permission = "server.ban", description = "commands.ban.description", permissionLevel = 2, targetRequirement = Command.TargetRequirement.NONE)
public class BanCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size() < 1) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.ban.command_usage"));
            return;
        }

        String uid = args.get(0);
        try {
            Player entity = Grasscutter.getGameServer().getPlayerByUid
                    (Integer.parseInt(uid), true);
            if (!Objects.requireNonNull(entity).getAccount().isBanned()) {
                for (Player p : Grasscutter.getGameServer().getPlayers().values())
                    CommandHandler.sendMessage(p, translate(p, "commands.ban.notify" , entity.getAccount().getUsername()));

                Objects.requireNonNull(entity).getAccount().setBanned();
                CommandHandler.sendMessage(sender, translate(sender, "commands.ban.banned"));
            }

            Objects.requireNonNull(entity).getAccount().save();
        } catch (Exception e) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.ban.error"));
        }

    }
}
