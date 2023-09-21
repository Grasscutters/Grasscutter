package emu.grasscutter.command;

import emu.grasscutter.game.player.Player;

import java.util.List;

public interface BrigadierCommand extends CommandHandler {
    @Override
    default void execute(Player sender, Player targetPlayer, List<String> args) {
        throw new IllegalStateException("You may forget to add brigadier property! try @Command(brigadier = true)");
    }
}
