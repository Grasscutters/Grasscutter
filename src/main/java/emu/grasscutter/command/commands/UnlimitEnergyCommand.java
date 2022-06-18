package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.managers.energy.EnergyManager;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.PropChangeReasonOuterClass;

import java.util.List;

import static emu.grasscutter.Configuration.GAME_OPTIONS;
import static emu.grasscutter.utils.Language.translate;

@Command(label = "unlimitenergy", usage = "unlimitenergy [on|off|toggle]", aliases = {"ule"}, permission = "player.unlimitenergy", permissionTargeted = "player.unlimitenergy.others", description = "commands.unlimitenergy.description")
public final class UnlimitEnergyCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (!GAME_OPTIONS.energyUsage) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.unlimitenergy.config_error"));
            return;
        }
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
        EnergyManager energyManager = targetPlayer.getEnergyManager();
        energyManager.setEnergyUsage(!status);
        // if unlimitEnergy is enable , make currentActiveTeam's Avatar full-energy
        if (status) {
            for (EntityAvatar entityAvatar : targetPlayer.getTeamManager().getActiveTeam()) {
                entityAvatar.addEnergy(1000,
                    PropChangeReasonOuterClass.PropChangeReason.PROP_CHANGE_REASON_GM, true);
            }
        }

        CommandHandler.sendMessage(sender, translate(sender, "commands.unlimitenergy.success", (status ? translate(sender, "commands.status.enabled") : translate(sender, "commands.status.disabled")), targetPlayer.getNickname()));
    }
}
