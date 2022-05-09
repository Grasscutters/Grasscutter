package emu.grasscutter.command.handler.collection;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.handler.*;
import emu.grasscutter.command.handler.annotation.Handler;
import emu.grasscutter.command.handler.annotation.HandlerCollection;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.mail.Mail;
import emu.grasscutter.game.player.Player;
import lombok.SneakyThrows;

import static emu.grasscutter.command.handler.ContextNaming.*;
import static emu.grasscutter.utils.Language.translate;
@HandlerCollection
public class MailHandlerCollection {
    @Handler(SEND_MAIL)
    @SneakyThrows
    public void sendMail(HandlerContext context) {
        // read arguments from the context
        Mail mail = context.getRequired("mail", Mail.class);
        int target = context.getOptional(TargetUid, -1); // -1 means to all

        // do send
        if (target != -1) {
            Player player = DatabaseHelper.getPlayerById(target);
            if (player != null) {
                player.sendMail(mail);
            } else {
                throw new RuntimeException(translate("commands.execution.player_exist_error"));
            }
        } else {
            DatabaseHelper.getAllPlayers().forEach(
                    player -> Grasscutter.getGameServer().getPlayerByUid(player.getUid(), true).sendMail(mail)
            );
        }
        context.returnWith(null);
    }

    public static final String SEND_MAIL = "mail.send";
}
