package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.player.Player;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(
    label = "ban",
    usage = "ban <player> [time] [reason]",
    description = "commands.ban.description",
    targetRequirement = Command.TargetRequirement.NONE
)
public final class BanCommand implements CommandHandler {

    private boolean banAccount(int uid, int time, String reason) {
        Player player = Grasscutter.getGameServer().getPlayerByUid(uid, true);

        if (player == null) {
            return false;
        }

        Account account = player.getAccount();
        if (account == null) {
            account = DatabaseHelper.getAccountByPlayerId(uid);
            if (account == null) {
                return false;
            }
        }

        account.setBanReason(reason);
        account.setBanEndTime(time);
        account.setBanStartTime((int) System.currentTimeMillis() / 1000);
        account.setBanned(true);
        account.save();

        Player banUser = Grasscutter.getGameServer().getPlayerByUid(uid);

        if (banUser != null) {
            banUser.getSession().close();
        }
        return true;
    }

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size() < 1) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.ban.command_usage"));
            return;
        }

        int uid = 0;
        int time = 2051190000;
        String reason = "Reason not specified.";

        if (args.size() >= 1) {
            try {
                uid = Integer.parseInt(args.get(0));
            } catch (NumberFormatException ignored) {
                CommandHandler.sendMessage(sender, translate(sender, "commands.ban.invalid_player_id"));
                return;
            }
        }

        if (args.size() >= 2) {
            try {
                time = Integer.parseInt(args.get(1));
            } catch (NumberFormatException ignored) {
                CommandHandler.sendMessage(sender, translate(sender, "commands.ban.invalid_time"));
                return;
            }
        }

        if (args.size() >= 3) {
            reason = args.get(2);
        }

        if (this.banAccount(uid, time, reason)) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.ban.success"));
        } else {
            CommandHandler.sendMessage(sender, translate(sender, "commands.ban.failure"));
        }
    }
}