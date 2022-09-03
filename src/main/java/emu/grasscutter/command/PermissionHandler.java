package emu.grasscutter.command;

import emu.grasscutter.game.player.Player;

public interface PermissionHandler {
    public boolean EnablePermissionCommand();
    public boolean checkPermission(Player player, Player targetPlayer, String permissionNode, String permissionNodeTargeted);
}
