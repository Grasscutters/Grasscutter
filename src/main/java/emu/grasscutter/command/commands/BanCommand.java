package emu.grasscutter.command.commands;

import emu.grasscutter.command.*;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.game.GameSession;
import java.util.List;

@Command(
        label = "ban",
        usage = {"[<time> [<reason>]]"},
        permission = "server.ban",
        targetRequirement = Command.TargetRequirement.PLAYER)
public final class BanCommand implements CommandHandler {

    private boolean banAccount(Player targetPlayer, int time, String reason) {
        Account account = targetPlayer.getAccount();

        if (account == null) {
            return false;
        }

        account.setBanReason(reason);
        account.setBanEndTime(time);
        account.setBanStartTime((int) System.currentTimeMillis() / 1000);
        account.setBanned(true);
        account.save();

        GameSession session = targetPlayer.getSession();
        if (session != null) {
            session.close();
        }
        return true;
    }

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        int time = 2051190000;
        String reason = "Reason not specified.";

        switch (args.size()) {
            case 2:
                reason = args.get(1); // Fall-through
            case 1:
                try {
                    time = Integer.parseInt(args.get(0));
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendTranslatedMessage(sender, "commands.ban.invalid_time");
                    return;
                } // Fall-through, unimportant
            default:
                break;
        }

        if (banAccount(targetPlayer, time, reason)) {
            CommandHandler.sendTranslatedMessage(sender, "commands.ban.success");
        } else {
            CommandHandler.sendTranslatedMessage(sender, "commands.ban.failure");
        }
    }
}
