package emu.grasscutter.command;

import emu.grasscutter.game.player.Player;

import java.util.List;

public interface BrigadierCommand extends CommandHandler {
    @Override
    default void execute(Player sender, Player targetPlayer, List<String> args) {
        throw new IllegalStateException("DO NOT invoke this method if the command uses brigadier!");
    }
}
