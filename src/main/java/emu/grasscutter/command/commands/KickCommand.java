package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
import io.jpower.kcp.netty.Kcp;
import io.jpower.kcp.netty.Ukcp;
import io.jpower.kcp.netty.UkcpServerChildChannel;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

@Command(label = "kick", usage = "kick <player>",
        description = "Kicks the specified player from the server (TEST)", permission = "server.kick")
public final class KickCommand implements CommandHandler {

    @Override
    public void execute(Player sender, List<String> args) {
        if (args.size() < 1) {
            CommandHandler.sendMessage(sender, "Usage: kick <player>");
            return;
        }

        int target = Integer.parseInt(args.get(0));

        Player targetPlayer = Grasscutter.getGameServer().getPlayerByUid(target);
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, "Player not found.");
            return;
        }

        if (sender != null) {
            CommandHandler.sendMessage(sender, String.format("Player [%s:%s] has kicked player [%s:%s]", sender.getAccount().getPlayerUid(), sender.getAccount().getUsername(), target, targetPlayer.getAccount().getUsername()));
        }

        CommandHandler.sendMessage(sender, String.format("Kicking player [%s:%s]", target, targetPlayer.getAccount().getUsername()));

        int conv1 = (int) 590414080L;
        int conv2 = (int) 3448548035L;

        ByteBuf packet = Unpooled.buffer(20);

        packet.writeInt(404);
        packet.writeIntLE(conv1);
        packet.writeIntLE(conv2);
        packet.writeInt(14);
        packet.writeInt(0x19419494);

        /***
         * Disconnect Type
         *
         * 2 - Blank Dialog, back to MainMenu
         * 3 - (Re-Login)
         * 4 - Account has logged in on another device
         * 5 - Server connection lost
         * 6 - Server restarting...
         * 7 - No server connection
         * 8 - (Re-Login)
         * 9 - Data communication frequency too high
         * 10 - (Re-Login)
         * 11 - (Re-Login)
         * 12 - Genshin Impact encountered a problem loading game data, please log in again ( Game Close )
         * 13 - (Blank Dialog)
         * 14 - Login expired
         * 15 - Data error, please log in again. Error code: 15-4001
         * 16 - Data error, please log in again. Error code: 16-4001
         * 17 - SDK Authentication failed
         *
         * Others - Unknown Error
         */

        /**
         * Temporary solution
         */
        try {
            UkcpServerChildChannel ukcpChannel = (UkcpServerChildChannel) targetPlayer.getSession().getChannel();

            Method ukcpMethod = ukcpChannel.getClass().getDeclaredMethod("ukcp");
            ukcpMethod.setAccessible(true);
            Ukcp ukcp = (Ukcp) ukcpMethod.invoke(ukcpChannel);

            Field kcpField = ukcp.getClass().getDeclaredField("kcp");
            kcpField.setAccessible(true);
            Kcp kcp = (Kcp) kcpField.get(ukcp);

            Method outputMethod = kcp.getClass().getDeclaredMethod("output", ByteBuf.class, Kcp.class);
            outputMethod.setAccessible(true);
            outputMethod.invoke(null, packet, kcp);

            outputMethod.setAccessible(false);
            kcpField.setAccessible(false);
            ukcpMethod.setAccessible(false);
        } catch (Exception e) {

        }
    }
}
