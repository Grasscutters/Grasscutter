package emu.grasscutter.command;

import emu.grasscutter.game.player.Player;

import javax.annotation.Nullable;

public interface PermissionHandler {
    boolean EnablePermissionCommand();

    boolean checkPermission(
            Player player, Player targetPlayer, String permissionNode, String permissionNodeTargeted);

    boolean hasPermission(@Nullable Player player, String permissionName);
}
