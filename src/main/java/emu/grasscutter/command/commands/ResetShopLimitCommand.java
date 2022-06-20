package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "resetshop", usage = "resetshop", permission = "server.resetshop", permissionTargeted = "server.resetshop.others", description = "commands.resetShopLimit.description")
public final class ResetShopLimitCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.isEmpty()) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.resetShopLimit.usage"));
            return;
        }

        targetPlayer.getShopLimit().forEach(x -> x.setNextRefreshTime(0));
        targetPlayer.save();
        CommandHandler.sendMessage(sender, translate(sender, "commands.resetShopLimit.success"));
    }
}
