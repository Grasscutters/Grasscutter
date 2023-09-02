package emu.grasscutter.command.commands;

import static emu.grasscutter.utils.lang.Language.translate;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.*;
import emu.grasscutter.game.player.Player;
import java.util.List;

@Command(
        label = "reload",
        permission = "server.reload",
        targetRequirement = Command.TargetRequirement.NONE)
public final class ReloadCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        CommandHandler.sendMessage(sender, translate(sender, "commands.reload.reload_start"));

        Grasscutter.loadConfig();
        Grasscutter.loadLanguage();
        Grasscutter.getGameServer().getGachaSystem().load();
        Grasscutter.getGameServer().getShopSystem().load();

        CommandHandler.sendMessage(sender, translate(sender, "commands.reload.reload_done"));
    }
}
