package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.command.CommandMap;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.utils.Language;

import java.awt.*;
import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "commandpermission",targetRequirement = Command.TargetRequirement.NONE, usage = "commandpermission <command>", description = "commands.cmdperm.description", aliases = {"cmdperm"})
public class CommandPermissionCommand implements CommandHandler {
    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        var annotations = CommandMap.getInstance().getAnnotations();
        if(args.size() < 1) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.cmdperm.usage"));
            return;
        }

        String command = args.get(0);
        for(var pair : annotations.entrySet()) {
            if(command.equals(pair.getValue().label())) {
                printPermission(sender, pair.getValue());
                return;
            }

            for(var alias : pair.getValue().aliases()) {
                if(!alias.equals(command)) {
                    continue;
                }

                printPermission(sender, pair.getValue());
                return;
            }
        }

        CommandHandler.sendMessage(sender, translate(sender, "commands.cmdperm.no_such_cmd"));
    }

    void printPermission(Player sender, Command command) {
        String permission = command.permission();
        String label = command.label();

        if(permission.isEmpty() || permission.isBlank()) {
            String transTipNeedNoPerm = Language.translate(sender, "commands.cmdperm.tip_need_no_permission", label);
            CommandHandler.sendMessage(sender, transTipNeedNoPerm);
            return;
        }
        String transTipNeedPerm = Language.translate(sender, "commands.cmdperm.tip_need_permission", label, permission);
        CommandHandler.sendMessage(sender, transTipNeedPerm);
    }
}
