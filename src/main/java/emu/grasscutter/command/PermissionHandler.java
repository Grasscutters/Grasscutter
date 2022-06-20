package emu.grasscutter.command;

import emu.grasscutter.game.player.Player;

public interface PermissionHandler {
    boolean EnablePermissionCommand();

    boolean checkPermission(Player player, Player targetPlayer, String permissionNode, String permissionNodeTargeted);
}
