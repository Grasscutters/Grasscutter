package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "resetshop", usage = "resetshop", permission = "server.resetshop", permissionTargeted = "server.resetshop.others", description = "commands.status.description")
public final class ResetShopLimitCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, translate("commands.execution.need_target"));
            return;
        }

        targetPlayer.getShopLimit().forEach(x -> x.setNextRefreshTime(0));
        targetPlayer.save();
        CommandHandler.sendMessage(sender, translate("commands.status.success"));
    }
}
