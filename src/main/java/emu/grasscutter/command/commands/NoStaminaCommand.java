package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;


@Command(label = "nostamina", usage = "nostamina [on|off]", permission = "player.nostamina", permissionTargeted = "player.nostamina.others", description = "commands.nostamina.description")
public final class NoStaminaCommand implements CommandHandler {
    public static boolean StaminaState = false;
    //Temp Value
    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size() == 1) {
            switch (args.get(0).toLowerCase()) {
                case "on":
                    StaminaState = true;
                    break;
                case "off":
                    StaminaState = false;
                    break;
                default:
                    break;
            }
        }
        targetPlayer.setStamina(StaminaState);//Set

        CommandHandler.sendMessage(sender, translate(sender, "commands.nostamina.success", (StaminaState ? translate(sender, "commands.status.enabled") : translate(sender, "commands.status.disabled")), targetPlayer.getNickname()));
    }
}
