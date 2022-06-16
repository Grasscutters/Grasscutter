package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.player.Player;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "unban", usage = "unban <uid>", permission = "server.unban", description = "commands.unban.description", targetRequirement = Command.TargetRequirement.NONE)
public class UnBanCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size() < 1) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.unban.command_usage"));
            return;
        }

        String uid = args.get(0);
        try {
            if (!DatabaseHelper.getAccountById(uid).isBanned())
                CommandHandler.sendMessage(sender, translate(sender, "commands.unban.no_ban"));
            else {
                try {
                    Account account = DatabaseHelper.getAccountById(uid);
                    //delete at first due to it is fucking retard
                    DatabaseHelper.deleteAccount(DatabaseHelper.getAccountById(uid));
                    account.setBan(false);
                    account.save();

                    CommandHandler.sendMessage(sender, translate(sender, "commands.unban.success"));
                } catch (Exception e) {
                    CommandHandler.sendMessage(sender, translate(sender, "commands.unban.error"));
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            CommandHandler.sendMessage(sender, translate(sender, "commands.unban.invalid_player"));
        }

    }
}
