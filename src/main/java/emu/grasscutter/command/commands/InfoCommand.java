package emu.grasscutter.command.commands;

import emu.grasscutter.BuildConfig;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.tools.Tools;

import java.util.List;

@Command(label = "info", aliases = {"info", "helpme"},
    usage = "/info", targetRequirement = Command.TargetRequirement.NONE)
public final class InfoCommand implements CommandHandler {
    // This command can use the 'grasscutter.command.troubleshoot' permission to show sensitive information.

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        // Collect server information.
        var build = "%s (%s)".formatted(
            BuildConfig.VERSION, BuildConfig.GIT_HASH);
        var playerCount = Grasscutter.getGameServer()
            .getPlayers().size();
        var resourceInfo = Tools.resourcesInfo();

        // Collect configuration information.
        var config = Grasscutter.getConfig();
        var gameOptions = config.server.game;
        var questingEnabled = gameOptions.gameOptions.questing.enabled;
        var scriptsEnabled = gameOptions.enableScriptInBigWorld;

        // TODO: Send to remote server (Grasscutter API) and send dump link.
        CommandHandler.sendMessage(sender, """
            Credits
            - Slushy Team (akio, azzu, Areha11Fz, tamilp)
            - Yuki (resource minifying & packaging)
            - Dimbreath (dumping most resources)

            Server Information
            Revision: %s
            Player Count: %d
            Questing Enabled: %s
            Scripts Enabled: %s
            Operating System: %s
            Resource Information: %s"""
            .formatted(
                build, playerCount, questingEnabled, scriptsEnabled,
                System.getProperty("os.name"), resourceInfo.toString()
            )
        );
    }
}
