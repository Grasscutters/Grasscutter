package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.command.Command.TargetRequirement;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.player.Player;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "permission", usage = "(add|remove) <permission>", permission = "permission", targetRequirement = TargetRequirement.PLAYER)
public final class PermissionCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size() != 2) {
            sendUsageMessage(sender);
            return;
        }

        if(!Grasscutter.getPermissionHandler().EnablePermissionCommand()) {
            CommandHandler.sendTranslatedMessage(sender, "commands.generic.permission_error");
            return;
        }

        String action = args.get(0);
        String permission = args.get(1);

        Account account = targetPlayer.getAccount();
        if (account == null) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.permission.account_error"));
            return;
        }

        switch (action) {
            default:
                sendUsageMessage(sender);
                break;
            case "add":
                if (account.addPermission(permission)) {
                    CommandHandler.sendMessage(sender, translate(sender, "commands.permission.add"));
                } else CommandHandler.sendMessage(sender, translate(sender, "commands.permission.has_error"));
                break;
            case "remove":
                if (account.removePermission(permission)) {
                    CommandHandler.sendMessage(sender, translate(sender, "commands.permission.remove"));
                } else CommandHandler.sendMessage(sender, translate(sender, "commands.permission.not_have_error"));
                break;
        }

        account.save();
    }
}
