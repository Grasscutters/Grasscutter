package emu.grasscutter.command.commands;

import emu.grasscutter.BuildConfig;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.tools.Tools;

import java.util.List;

@Command(label = "troubleshoot", aliases = {"helpme"},
    usage = "/troubleshoot", permission = "grasscutter.command.troubleshoot",
    targetRequirement = Command.TargetRequirement.NONE)
public final class TroubleshootCommand implements CommandHandler {
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
            Troubleshooting/Debug Information
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
