package emu.grasscutter.command.commands;

import static emu.grasscutter.utils.lang.Language.translate;

import emu.grasscutter.command.*;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.player.Player;
import java.util.*;

@Command(
        label = "help",
        usage = {"[<command>]"},
        targetRequirement = Command.TargetRequirement.NONE)
public final class HelpCommand implements CommandHandler {
    private final boolean SHOW_COMMANDS_WITHOUT_PERMISSIONS =
            false; // TODO: Make this into a server config key

    private String createCommand(Player player, CommandHandler command, List<String> args) {
        StringBuilder builder =
                new StringBuilder(command.getLabel())
                        .append(" - ")
                        .append(command.getDescriptionString(player))
                        .append("\n\t")
                        .append(command.getUsageString(player, args.toArray(new String[0])));

        Command annotation = command.getClass().getAnnotation(Command.class);
        if (annotation.aliases().length > 0) {
            builder.append("\n\t").append(translate(player, "commands.help.aliases"));
            for (String alias : annotation.aliases()) {
                builder.append(alias).append(" ");
            }
        }

        builder.append("\n\t").append(translate(player, "commands.help.tip_need_permission"));
        if (!annotation.permission().isEmpty()) {
            builder.append(annotation.permission());
        } else {
            builder.append(translate(player, "commands.help.tip_need_no_permission"));
        }

        if (!annotation.permissionTargeted().isEmpty()) {
            String permissionTargeted = annotation.permissionTargeted();
            builder
                    .append(" ")
                    .append(translate(player, "commands.help.tip_permission_targeted", permissionTargeted));
        }
        return builder.toString();
    }

    @Override
    public void execute(Player player, Player targetPlayer, List<String> args) {
        Account account = (player == null) ? null : player.getAccount();
        var commandMap = CommandMap.getInstance();
        List<String> commands = new ArrayList<>();
        List<String> commands_no_permission = new ArrayList<>();
        if (args.isEmpty()) {
            commandMap
                    .getHandlers()
                    .forEach(
                            (key, command) -> {
                                Command annotation = command.getClass().getAnnotation(Command.class);
                                if (player == null || account.hasPermission(annotation.permission())) {
                                    commands.add(createCommand(player, command, args));
                                } else if (SHOW_COMMANDS_WITHOUT_PERMISSIONS) {
                                    commands_no_permission.add(createCommand(player, command, args));
                                }
                            });
            CommandHandler.sendTranslatedMessage(player, "commands.help.available_commands");
        } else {
            String command_str = args.remove(0).toLowerCase();
            CommandHandler command = commandMap.getHandler(command_str);
            if (command == null) {
                CommandHandler.sendTranslatedMessage(player, "commands.generic.command_exist_error");
                CommandHandler.sendMessage(player, "Command: " + command_str);
                return;
            } else {
                Command annotation = command.getClass().getAnnotation(Command.class);
                if (player == null || account.hasPermission(annotation.permission())) {
                    commands.add(createCommand(player, command, args));
                } else {
                    commands_no_permission.add(createCommand(player, command, args));
                }
            }
        }
        final String suf = "\n\t" + translate(player, "commands.help.warn_player_has_no_permission");
        commands.forEach(s -> CommandHandler.sendMessage(player, s));
        commands_no_permission.forEach(s -> CommandHandler.sendMessage(player, s + suf));
    }
}
