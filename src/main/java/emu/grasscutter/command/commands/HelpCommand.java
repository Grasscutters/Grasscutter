package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.command.CommandMap;
import emu.grasscutter.game.player.Player;

import java.util.*;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "help", usage = "help [command]")
public final class HelpCommand implements CommandHandler {

    @Override
    public String description() {
        return translate("commands.help.description");
    }

    @Override
    public void execute(Player player, Player targetPlayer, List<String> args) {
        if (args.size() < 1) {
            HashMap<String, CommandHandler> handlers = CommandMap.getInstance().getHandlers();
            HashMap<Command, CommandHandler> annotations = new HashMap<>();
            for (String key : handlers.keySet()) {
                Command annotation = handlers.get(key).getClass().getAnnotation(Command.class);

                if (!Arrays.asList(annotation.aliases()).contains(key)) {
                    if (player != null && !Objects.equals(annotation.permission(), "") && !player.getAccount().hasPermission(annotation.permission()))
                        continue;
                    annotations.put(annotation, handlers.get(key));
                }
            }

            SendAllHelpMessage(player, annotations);
        } else {
            String command = args.get(0);
            CommandHandler handler = CommandMap.getInstance().getHandler(command);
            StringBuilder builder = new StringBuilder(player == null ? "\n" + translate("commands.status.help") + " - " : translate("commands.status.help") + " - ").append(command).append(": \n");
            if (handler == null) {
                builder.append(translate("commands.generic.command_exist_error"));
            } else {
                Command annotation = handler.getClass().getAnnotation(Command.class);

                builder.append("   ").append(handler.description() == null ? annotation.description(): handler.description()).append("\n");
                builder.append(translate("commands.help.usage")).append(annotation.usage());
                if (annotation.aliases().length >= 1) {
                    builder.append("\n").append(translate("commands.help.aliases"));
                    for (String alias : annotation.aliases()) {
                        builder.append(alias).append(" ");
                    }
                }
                if (player != null && !Objects.equals(annotation.permission(), "") && !player.getAccount().hasPermission(annotation.permission())) {
                    builder.append("\n Warning: You do not have permission to run this command.");
                }
            }

            CommandHandler.sendMessage(player, builder.toString());
        }
    }

    void SendAllHelpMessage(Player player, HashMap<Command, CommandHandler> annotations) {
        if (player == null) {
            StringBuilder builder = new StringBuilder("\n" + translate("commands.help.available_commands") + "\n");
            annotations.forEach((annotation, handler) -> {
                builder.append(annotation.label()).append("\n");
                builder.append("   ").append(handler.description() == null ? annotation.description() : handler.description()).append("\n");
                builder.append(translate("commands.help.usage")).append(annotation.usage());
                if (annotation.aliases().length >= 1) {
                    builder.append("\n").append(translate("commands.help.aliases"));
                    for (String alias : annotation.aliases()) {
                        builder.append(alias).append(" ");
                    }
                }

                builder.append("\n");
            });

            CommandHandler.sendMessage(null, builder.toString());
        } else {
            CommandHandler.sendMessage(player, translate("commands.help.available_commands"));
            annotations.forEach((annotation, handler) -> {
                StringBuilder builder = new StringBuilder(annotation.label()).append("\n");
                builder.append("   ").append(handler.description() == null ? annotation.description() : handler.description()).append("\n");
                builder.append(translate("commands.help.usage")).append(annotation.usage());
                if (annotation.aliases().length >= 1) {
                    builder.append("\n").append(translate("commands.help.aliases"));
                    for (String alias : annotation.aliases()) {
                        builder.append(alias).append(" ");
                    }
                }

                CommandHandler.sendMessage(player, builder.toString());
            });
        }
    }
}
