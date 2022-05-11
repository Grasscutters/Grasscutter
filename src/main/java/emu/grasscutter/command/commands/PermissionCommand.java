package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.player.Player;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "permission", usage = "permission <add|remove> <permission>", permission = "*", description = "commands.permission.description")
public final class PermissionCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.execution.need_target"));
            return;
        }
        
        if (args.size() != 2) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.permission.usage"));
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
                CommandHandler.sendMessage(sender, translate(sender, "commands.permission.usage"));
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
