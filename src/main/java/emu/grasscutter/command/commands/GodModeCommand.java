package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "godmode", usage = "godmode [on|off|toggle]",
        description = "Prevents you from taking damage. Defaults to toggle.", permission = "player.godmode")
public final class GodModeCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, translate("commands.execution.need_target"));
            return;
        }

        boolean enabled = !targetPlayer.inGodmode();
        if (args.size() == 1) {
            switch (args.get(0).toLowerCase()) {
                case "on":
                    enabled = true;
                    break;
                case "off":
                    enabled = false;
                    break;
                case "toggle":
                    break;  // Already toggled
                default:
                    break;
            }
        }

        targetPlayer.setGodmode(enabled);
        CommandHandler.sendMessage(sender, translate("commands.godmode.success", (enabled ? translate("commands.status.enabled") : translate("commands.status.disabled")), targetPlayer.getNickname()));
    }
}
