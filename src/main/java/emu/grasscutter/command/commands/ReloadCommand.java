package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "reload", permission = "server.reload", targetRequirement = Command.TargetRequirement.NONE)
public final class ReloadCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        CommandHandler.sendMessage(sender, translate(sender, "commands.reload.reload_start"));

        Grasscutter.loadConfig();
        Grasscutter.loadLanguage();
        Grasscutter.getGameServer().getGachaSystem().load();
        Grasscutter.getGameServer().getDropSystem().load();
        Grasscutter.getGameServer().getShopSystem().load();

        CommandHandler.sendMessage(sender, translate(sender, "commands.reload.reload_done"));
    }
}
