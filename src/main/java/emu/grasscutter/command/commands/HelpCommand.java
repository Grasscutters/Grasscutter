package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.command.CommandMap;
import emu.grasscutter.game.player.Player;

import java.util.*;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "help", usage = "help [command]", description = "commands.help.description", targetRequirement = Command.TargetRequirement.NONE)
public final class HelpCommand implements CommandHandler {

    @Override
    public void execute(Player player, Player targetPlayer, List<String> args) {
        if (args.size() < 1) {
            HashMap<String, CommandHandler> handlers = CommandMap.getInstance().getHandlers();
            List<Command> annotations = new ArrayList<>();
            for (String key : handlers.keySet()) {
                Command annotation = handlers.get(key).getClass().getAnnotation(Command.class);

                if (!Arrays.asList(annotation.aliases()).contains(key)) {
                    if (player != null && !Objects.equals(annotation.permission(), "") && !player.getAccount().hasPermission(annotation.permission()))
                        continue;
                    annotations.add(annotation);
                }
            }

            SendAllHelpMessage(player, annotations);
        } else {
            String command = args.get(0);
            CommandHandler handler = CommandMap.getInstance().getHandler(command);
            StringBuilder builder = new StringBuilder(player == null ? "\n" + translate(player, "commands.status.help") + " - " : translate(player, "commands.status.help") + " - ").append(command).append(": \n");
            if (handler == null) {
                builder.append(translate(player, "commands.generic.command_exist_error"));
            } else {
                Command annotation = handler.getClass().getAnnotation(Command.class);

                builder.append("   ").append(translate(player, annotation.description())).append("\n");
                builder.append(translate(player, "commands.help.usage")).append(annotation.usage());
                if (annotation.aliases().length >= 1) {
                    builder.append("\n").append(translate(player, "commands.help.aliases"));
                    for (String alias : annotation.aliases()) {
                        builder.append(alias).append(" ");
                    }
                }

                builder.append("\n").append(translate(player, "commands.help.tip_need_permission"));
                if(annotation.permission().isEmpty() || annotation.permission().isBlank()) {
                    builder.append(translate(player, "commands.help.tip_need_no_permission"));
                }
                else {
                    builder.append(annotation.permission());
                }
                builder.append(" ");

                if(!annotation.permissionTargeted().isEmpty() && !annotation.permissionTargeted().isBlank()) {
                    String permissionTargeted = annotation.permissionTargeted();
                    builder.append(translate(player, "commands.help.tip_permission_targeted", permissionTargeted));
                }

                if (player != null && !Objects.equals(annotation.permission(), "") && !player.getAccount().hasPermission(annotation.permission())) {
                    builder.append("\n ");
                    builder.append(translate(player, "commands.help.warn_player_has_no_permission"));
                }
            }

            CommandHandler.sendMessage(player, builder.toString());
        }
    }

    void SendAllHelpMessage(Player player, List<Command> annotations) {
        if (player == null) {
            StringBuilder builder = new StringBuilder("\n" + translate(player, "commands.help.available_commands") + "\n");
            annotations.forEach(annotation -> {
                builder.append(annotation.label()).append("\n");
                builder.append("   ").append(translate(player, annotation.description())).append("\n");
                builder.append(translate(player, "commands.help.usage")).append(annotation.usage());
                if (annotation.aliases().length >= 1) {
                    builder.append("\n").append(translate(player, "commands.help.aliases"));
                    for (String alias : annotation.aliases()) {
                        builder.append(alias).append(" ");
                    }
                }
                builder.append("\n").append(translate(player, "commands.help.tip_need_permission"));
                if(annotation.permission().isEmpty() || annotation.permission().isBlank()) {
                    builder.append(translate(player, "commands.help.tip_need_no_permission"));
                }
                else {
                    builder.append(annotation.permission());
                }

                builder.append(" ");

                if(!annotation.permissionTargeted().isEmpty() && !annotation.permissionTargeted().isBlank()) {
                    String permissionTargeted = annotation.permissionTargeted();
                    builder.append(translate(player, "commands.help.tip_permission_targeted", permissionTargeted));
                }

                builder.append("\n");
            });

            CommandHandler.sendMessage(null, builder.toString());
        } else {
            CommandHandler.sendMessage(player, translate(player, "commands.help.available_commands"));
            annotations.forEach(annotation -> {
                StringBuilder builder = new StringBuilder(annotation.label()).append("\n");
                builder.append("   ").append(translate(player, annotation.description())).append("\n");
                builder.append(translate(player, "commands.help.usage")).append(annotation.usage());
                if (annotation.aliases().length >= 1) {
                    builder.append("\n").append(translate(player, "commands.help.aliases"));
                    for (String alias : annotation.aliases()) {
                        builder.append(alias).append(" ");
                    }
                }
                builder.append("\n").append(translate(player, "commands.help.tip_need_permission"));
                if(annotation.permission().isEmpty() || annotation.permission().isBlank()) {
                    builder.append(translate(player, "commands.help.tip_need_no_permission"));
                }
                else {
                    builder.append(annotation.permission());
                }

                builder.append(" ");

                if(!annotation.permissionTargeted().isEmpty() && !annotation.permissionTargeted().isBlank()) {
                    String permissionTargeted = annotation.permissionTargeted();
                    builder.append(translate(player, "commands.help.tip_permission_targeted", permissionTargeted));
                }


                CommandHandler.sendMessage(player, builder.toString());
            });
        }
    }
}
