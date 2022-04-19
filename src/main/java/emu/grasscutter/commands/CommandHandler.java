package emu.grasscutter.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.GenshinPlayer;

import java.util.List;

public interface CommandHandler {
    /* Invoked on player execution. */
    default void execute(GenshinPlayer player, List<String> args) { }
    /* Invoked on server execution. */
    default void execute(List<String> args) { }
    
    /*
     * Utilities.
     */

    /**
     * Send a message to the target.
     * @param player The player to send the message to, or null for the server console.
     * @param message The message to send.
     */
    static void sendMessage(GenshinPlayer player, String message) {
        if(player == null) {
            Grasscutter.getLogger().info(message);
        } else player.dropMessage(message);
    }
}
