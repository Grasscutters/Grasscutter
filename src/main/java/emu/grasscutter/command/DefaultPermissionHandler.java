package emu.grasscutter.command;

import emu.grasscutter.game.Account;
import emu.grasscutter.game.player.Player;

public class DefaultPermissionHandler implements PermissionHandler {
    @Override
    public boolean EnablePermissionCommand() {
        return true;
    }

    @Override
    public boolean checkPermission(Player player, Player targetPlayer, String permissionNode, String permissionNodeTargeted) {
        if (player == null) {
            return true;
        }

        Account account = player.getAccount();
        if (player != targetPlayer) {  // Additional permission required for targeting another player
            if (!permissionNodeTargeted.isEmpty() && !account.hasPermission(permissionNodeTargeted)) {
                CommandHandler.sendTranslatedMessage(player, "commands.generic.permission_error");
                return false;
            }
        }
        if (!permissionNode.isEmpty() && !account.hasPermission(permissionNode)) {
            CommandHandler.sendTranslatedMessage(player, "commands.generic.permission_error");
            return false;
        }

        return true;
    }
}
