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
    label = "unban",
    usage = "unban <player>",
    description = "commands.unban.description",
    targetRequirement = Command.TargetRequirement.NONE
)
public final class UnBanCommand implements CommandHandler {

    private boolean unBanAccount(int uid) {
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

        account.setBanReason(null);
        account.setBanEndTime(0);
        account.setBanStartTime(0);
        account.setBanned(false);
        account.save();

        return true;
    }

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size() < 1) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.unban.command_usage"));
            return;
        }

        int uid = 0;

        try {
            uid = Integer.parseInt(args.get(0));
        } catch (NumberFormatException ignored) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.unban.invalid_player_id"));
            return;
        }

        if (this.unBanAccount(uid)) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.unban.success"));
        } else {
            CommandHandler.sendMessage(sender, translate(sender, "commands.unban.failure"));
        }
    }
}