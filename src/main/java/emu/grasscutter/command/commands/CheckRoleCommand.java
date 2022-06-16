package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.permission.PermissionGroup;

import java.util.List;
import java.util.Objects;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "checkrole", usage = "checkrole <uid>", aliases = {"crole"}, permission = "player.roles", description = "commands.checkrole.description", permissionLevel = 0, targetRequirement = Command.TargetRequirement.NONE)

public class CheckRoleCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size() < 1) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.checkrole.command_usage"));
            return;
        }

        String uid = args.get(0);

        try {
            Player player = Grasscutter.getGameServer().getPlayerByUid(Integer.parseInt(uid), true);
            Account account = Objects.requireNonNull(player).getAccount();
            account.save();
            CommandHandler.sendMessage(sender, translate(sender, "commands.checkrole.role_found") + ": " +
                    Objects.requireNonNull(PermissionGroup.getGroupByNumber(account.getPermission())).name());
        } catch (Exception e) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.checkrole.role_not_found"));
        }
    }

}
