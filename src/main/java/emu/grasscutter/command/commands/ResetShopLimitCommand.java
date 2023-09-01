package emu.grasscutter.command.commands;

import emu.grasscutter.command.*;
import emu.grasscutter.game.player.Player;

import java.util.List;

import static emu.grasscutter.utils.lang.Language.translate;

@Command(
        label = "resetShopLimit",
        aliases = {"resetshop"},
        permission = "server.resetshop",
        permissionTargeted = "server.resetshop.others")
public final class ResetShopLimitCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        targetPlayer.getShopLimit().forEach(x -> x.setNextRefreshTime(0));
        targetPlayer.save();
        CommandHandler.sendMessage(sender, translate(sender, "commands.resetShopLimit.success"));
    }
}
