package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

@Command(label = "godmode", usage = "godmode [playerId]",
        description = "Prevents you from taking damage", permission = "player.godmode")
public final class GodModeCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (sender == null) {
            CommandHandler.sendMessage(null, Grasscutter.getLanguage().Run_this_command_in_game);
            return; // TODO: toggle player's godmode statue from console or other players
        }

        int target;
        if (args.size() == 1) {
            try {
                target = Integer.parseInt(args.get(0));
                if (Grasscutter.getGameServer().getPlayerByUid(target) == null) {
                    target = sender.getUid();
                }
            } catch (NumberFormatException e) {
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Invalid_playerId);
                return;
            }
        } else {
            target = sender.getUid();
        }
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Player_not_found);
            return;
        }

        targetPlayer.setGodmode(!targetPlayer.inGodmode());
        sender.dropMessage(Grasscutter.getLanguage().Godmode_status.replace("{status}", (targetPlayer.inGodmode() ? Grasscutter.getLanguage().Enabled : Grasscutter.getLanguage().Disabled)).replace("{name}", targetPlayer.getNickname()));
    }
}
