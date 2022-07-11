package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.command.CommandMap;
import emu.grasscutter.game.player.Player;

import java.util.*;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "help", usage = "help [command]", description = "commands.help.description", targetRequirement = Command.TargetRequirement.NONE)
public final class HelpCommand implements CommandHandler {

    private void createCommand(StringBuilder builder, Player player, Command annotation) {
        builder.append("\n").append(annotation.label()).append(" - ").append(translate(player, annotation.description()));
        builder.append("\n\t").append(translate(player, "commands.help.usage"));
        if (annotation.aliases().length >= 1) {
            builder.append("\n\t").append(translate(player, "commands.help.aliases"));
            for (String alias : annotation.aliases()) {
                builder.append(alias).append(" ");
            }
        }
        builder.append("\n\t").append(translate(player, "commands.help.tip_need_permission"));
        if(annotation.permission().isEmpty() || annotation.permission().isBlank()) {
            builder.append(translate(player, "commands.help.tip_need_no_permission"));
        } else {
            builder.append(annotation.permission());
        }

        if(!annotation.permissionTargeted().isEmpty() && !annotation.permissionTargeted().isBlank()) {
            String permissionTargeted = annotation.permissionTargeted();
            builder.append(" ").append(translate(player, "commands.help.tip_permission_targeted", permissionTargeted));
        }
    }

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
            StringBuilder builder = new StringBuilder("");
            if (handler == null) {
                builder.append(translate(player, "commands.generic.command_exist_error"));
            } else {
                Command annotation = handler.getClass().getAnnotation(Command.class);

                this.createCommand(builder, player, annotation);

                if (player != null && !Objects.equals(annotation.permission(), "") && !player.getAccount().hasPermission(annotation.permission())) {
                    builder.append("\n\t").append(translate(player, "commands.help.warn_player_has_no_permission"));
                }
            }

            CommandHandler.sendMessage(player, builder.toString());
        }
    }

    void SendAllHelpMessage(Player player, List<Command> annotations) {
        StringBuilder builder = new StringBuilder(translate(player, "commands.help.available_commands"));
        annotations.forEach(annotation -> {
            this.createCommand(builder, player, annotation);
            builder.append("\n");
        });

        CommandHandler.sendMessage(player, builder.toString());
    }
}
