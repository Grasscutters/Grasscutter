package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// later add for messages: import static emu.grasscutter.utils.Language.translate;

@Command(
    label = "alias",
    aliases = "al",
    usage = {
        "set <alias> [<command input> %<args>;<command input> %<args>;...]",
        "(info|clear) <alias>",
        "(list|clearall)"
    },
    permission = "player.alias",
    permissionTargeted = "player.alias.others",
    // Just for testing, delete when done
    targetRequirement = Command.TargetRequirement.PLAYER,
    threading = true)
public final class AliasCommand implements CommandHandler {
    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size() < 1) {
            sendUsageMessage(sender);
            return;
        }
        switch (args.get(0)) {
            case "set" -> {
                if (args.size() < 2) {
                    sendUsageMessage(sender);
                    return;
                }
                String commandInputString = args.subList(2, args.size()).toString().replaceAll(",", "");
                // Temporary if, to prevent stacking the same alias, would still return if alias is the same with arguments though
                Pattern pattern = Pattern.compile("\\b" + args.get(1) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(args.subList(2, args.size()).toString());
                boolean matchFound = matcher.find();
                if (matchFound)
                    CommandHandler.sendMessage(sender, "Set parameters cannot contain the same word as the alias.");
                else setAlias(sender, targetPlayer, args.get(1), commandInputString);
            }
            case "info" -> {
                if (args.size() < 2) {
                    sendUsageMessage(sender);
                    return;
                }
                infoAlias(sender, targetPlayer, args.get(1));
            }
            case "clear" -> {
                if (args.size() < 2) {
                    sendUsageMessage(sender);
                    return;
                }
                clearAlias(sender, targetPlayer, args.get(1));
            }
            case "list" -> listAlias(sender, targetPlayer);
            case "clearall" -> clearAllAlias(sender, targetPlayer);
            default -> sendUsageMessage(sender);
        }
    }
    private void setAlias(Player sender, Player player, String alias, String commandInputString) {
        if (player.setAlias(alias, commandInputString)) CommandHandler.sendMessage(sender, "Alias set.");
        else CommandHandler.sendMessage(sender, "Failed to set alias.");
    }

    private void infoAlias(Player sender, Player player, String alias) {
        if (!player.getAliasDescription(player, alias)) CommandHandler.sendMessage(sender, "No alias found.");
    }
    private void clearAlias(Player sender, Player player, String alias) {
        if (!player.clearAlias(alias)) CommandHandler.sendMessage(sender, "No alias found.");
        else CommandHandler.sendMessage(sender, "Alias cleared.");
    }
    private void listAlias(Player sender, Player player) {
        if (!player.listAlias(player)) CommandHandler.sendMessage(sender,"No alias found.");
    }
    private void clearAllAlias(Player sender, Player player) {
        player.clearAllAlias();
        CommandHandler.sendMessage(sender, "All existing aliases has been cleared.");
    }
}
