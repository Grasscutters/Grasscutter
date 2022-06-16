package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;


@Command(label = "nostamina", usage = "nostamina [on|off|toggle]", aliases = {"ns"}, permission = "player.nostamina", permissionTargeted = "player.nostamina.others", description = "commands.nostamina.description")
public final class NoStaminaCommand implements CommandHandler {
    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        boolean stamina = !targetPlayer.getStamina();
        if (args.size() == 1) {
            switch (args.get(0).toLowerCase()) {
                case "on":
                    stamina = true;
                    break;
                case "off":
                    stamina = false;
                    break;
                default:
                    // toggled
                    break;
            }
        }
        targetPlayer.setStamina(stamina); //Set

        CommandHandler.sendMessage(sender, translate(sender, "commands.nostamina.success", (stamina ? translate(sender, "commands.status.enabled") : translate(sender, "commands.status.disabled")), targetPlayer.getNickname()));
    }
}
