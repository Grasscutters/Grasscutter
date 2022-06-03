package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "unlimitenergy", usage = "unlimitenergy [on|off|toggle]", aliases = {"ule"}, permission = "player.unlimitenergy", permissionTargeted = "player.unlimitenergy.others", description = "commands.unlimitenergy.description")
public final class UnlimitEnergyCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        Boolean status = targetPlayer.getEnergyManager().getEnergyUsage();
        if (args.size() == 1) {
            switch (args.get(0).toLowerCase()) {
                case "on":
                    status = true;
                    break;
                case "off":
                    status = false;
                    break;
                default:
                    status = !status;

                    break;
            }
        }
        targetPlayer.getEnergyManager().setEnergyUsage(!status);
//        targetPlayer.setStamina(StaminaState);//Set

        CommandHandler.sendMessage(sender, translate(sender, "commands.unlimitenergy.success", (status ? translate(sender, "commands.status.enabled") : translate(sender, "commands.status.disabled")), targetPlayer.getNickname()));
    }
}
