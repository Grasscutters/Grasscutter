package emu.grasscutter.command;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.event.game.CommandResponseEvent;
import emu.grasscutter.server.event.types.ServerEvent;
import static emu.grasscutter.utils.Language.translate;

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
        CommandResponseEvent event = new CommandResponseEvent(ServerEvent.Type.GAME,player, message);
        event.call();
    }
    
    static void sendTranslatedMessage(Player player, String messageKey, Object... args) {
        sendMessage(player, translate(player, messageKey, args));
    }

    /**
     * Called when a player/console invokes a command.
     * @param sender The player/console that invoked the command.
     * @param args The arguments to the command.
     */
    default void execute(Player sender, Player targetPlayer, List<String> args) {
    }
}
