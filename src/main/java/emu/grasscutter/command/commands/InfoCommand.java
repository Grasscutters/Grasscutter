package emu.grasscutter.command.commands;

import emu.grasscutter.*;
import emu.grasscutter.command.*;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.tools.Tools;

import java.util.List;

@Command(label = "info", aliases = {"troubleshoot", "helpme"},
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
        var fastRequire = config.server.fastRequire;

        CommandHandler.sendMessage(sender, """
                Created by Meledy
                - currently maintained by KingRainbow44
                - formerly maintained by Birdulon

                Other Credits
                 - Slushy Team (akio, azzu, Areha11Fz, tamil; protocol)
                 - Yuki (resource minifying & packaging)
                 - Dimbreath (dumping most resources)""");
        // TODO: Send to remote server (Grasscutter API) and send dump link.
        if (
                sender == null
                        || sender.getAccount()
                        .hasPermission("grasscutter.command.troubleshoot")
                        || playerCount == 1
        ) {
            CommandHandler.sendMessage(sender, """
            Server Information
            Revision: %s
            Player Count: %d
            Questing Enabled: %s
            Scripts Enabled: %s
            Using Fast Require: %s
            Operating System: %s
            Resource Information: %s

            discord.gg/T5vZU6UyeG"""
                    .formatted(
                            build, playerCount, questingEnabled, scriptsEnabled, fastRequire,
                            System.getProperty("os.name"), resourceInfo.toString()
                    )
            );
        } else {
            CommandHandler.sendMessage(sender, """
                    Grasscutter Discord: discord.gg/T5vZU6UyeG""");
        }
    }
}
