package emu.grasscutter.command.commands;

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
        sender.setGodmode(!sender.inGodmode());
        sender.dropMessage("Godmode is now " + (sender.inGodmode() ? "enabled" : "disabled") + ".");
    }
}
