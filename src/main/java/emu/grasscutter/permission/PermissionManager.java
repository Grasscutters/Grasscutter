package emu.grasscutter.permission;


import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.game.GameServer;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.LinkedHashMap;

import static emu.grasscutter.utils.Language.translate;

@Getter
@Setter
public class PermissionManager {

    private HashMap<Player, PermissionGroup> groups = new LinkedHashMap<>();
    private final GameServer gameServer;

    public PermissionManager(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    public boolean canAccess(Player player, PermissionGroup group, String permission) {
        if (!Grasscutter.getConfig().account.newPermissionManager
                || player.getAccount().getPermission() >= group.getNumber())
            return true;

        player.dropMessage(translate(player, "action.forbidden", permission)
                + " " + translate(player, "action.perm", group.name()));
        return false;
    }
}