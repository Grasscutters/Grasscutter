package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

@Command(label = "godmode", usage = "godmode [on|off|toggle]",
        description = "Prevents you from taking damage. Defaults to toggle.", permission = "player.godmode")
public final class GodModeCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Target_needed);
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
                    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Godmode_status);
            }
        }

        targetPlayer.setGodmode(enabled);
        CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Godmode_status.replace("{status}", (enabled ? Grasscutter.getLanguage().Enabled : Grasscutter.getLanguage().Disabled)).replace("{name}", targetPlayer.getNickname()));
    }
}
