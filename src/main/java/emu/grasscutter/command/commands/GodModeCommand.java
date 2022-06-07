package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "godmode", usage = "godmode [on|off|toggle]", permission = "player.godmode", permissionTargeted = "player.godmode.others", description = "commands.godmode.description")
public final class GodModeCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
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
        CommandHandler.sendMessage(sender, translate(sender, "commands.godmode.success", (enabled ? translate(sender, "commands.status.enabled") : translate(sender, "commands.status.disabled")), targetPlayer.getNickname()));
    }
}
