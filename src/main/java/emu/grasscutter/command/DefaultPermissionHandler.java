package emu.grasscutter.command;

import emu.grasscutter.game.player.Player;

import javax.annotation.Nullable;

public class DefaultPermissionHandler implements PermissionHandler {
    @Override
    public boolean EnablePermissionCommand() {
        return true;
    }

    @Override
    public boolean checkPermission(
        Player player, Player targetPlayer, String permissionNode, String permissionNodeTargeted) {
        if (player != targetPlayer) { // Additional permission required for targeting another player
            if (!permissionNodeTargeted.isEmpty() && !this.hasPermission(player, permissionNodeTargeted)) {
                CommandHandler.sendTranslatedMessage(player, "commands.generic.permission_error");
                return false;
            }
        }
        if (!permissionNode.isEmpty() && !this.hasPermission(player, permissionNode)) {
            CommandHandler.sendTranslatedMessage(player, "commands.generic.permission_error");
            return false;
        }

        return true;
    }

    @Override
    public boolean hasPermission(@Nullable Player player, String permissionName) {
        if (player == null) {
            return true;
        }

        return permissionName.isEmpty() || player.getAccount().hasPermission(permissionName);
    }
}
