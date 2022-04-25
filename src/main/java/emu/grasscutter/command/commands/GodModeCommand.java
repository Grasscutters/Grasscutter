package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.GenshinPlayer;

import java.util.List;

@Command(label = "godmode", usage = "godmode [playerId]",
        description = "Prevents you from taking damage", permission = "player.godmode")
public final class GodModeCommand implements CommandHandler {

    @Override
    public void execute(GenshinPlayer sender, List<String> args) {
        if (sender == null) {
            CommandHandler.sendMessage(null, "Run this command in-game.");
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
                CommandHandler.sendMessage(sender, "Invalid player id.");
                return;
            }
        } else {
            target = sender.getUid();
        }
        GenshinPlayer targetPlayer = Grasscutter.getGameServer().getPlayerByUid(target);
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, "Player not found.");
            return;
        }

        targetPlayer.setGodmode(!targetPlayer.inGodmode());
        sender.dropMessage("Godmode is now " + (targetPlayer.inGodmode() ? "enabled" : "disabled") +
                "for " + targetPlayer.getNickname() + " .");
    }
}
