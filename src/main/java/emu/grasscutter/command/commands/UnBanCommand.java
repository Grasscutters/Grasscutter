package emu.grasscutter.command.commands;

import emu.grasscutter.command.*;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.player.Player;
import java.util.List;

@Command(
        label = "unban",
        permission = "server.ban",
        targetRequirement = Command.TargetRequirement.PLAYER)
public final class UnBanCommand implements CommandHandler {

    private boolean unBanAccount(Player targetPlayer) {
        Account account = targetPlayer.getAccount();

        if (account == null) {
            return false;
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
        if (unBanAccount(targetPlayer)) {
            CommandHandler.sendTranslatedMessage(sender, "commands.unban.success");
        } else {
            CommandHandler.sendTranslatedMessage(sender, "commands.unban.failure");
        }
    }
}
