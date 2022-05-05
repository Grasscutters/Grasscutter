package emu.grasscutter.command;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.player.Player;

import java.util.List;

public interface CommandHandler {
    /**
     * Send a message to the target.
     *
     * @param player  The player to send the message to, or null for the server console.
     * @param message The message to send.
     */
    static void sendMessage(Player player, String message) {
        if (player == null) {
            Grasscutter.getLogger().info(message);
        } else {
            player.dropMessage(message);
        }
    }

    /**
     * Called when a player/console invokes a command.
     * @param sender The player/console that invoked the command.
     * @param args The arguments to the command.
     */
    default void execute(Player sender, Player targetPlayer, List<String> args) {
    }
}
