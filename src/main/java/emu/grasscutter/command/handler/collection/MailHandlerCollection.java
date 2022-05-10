package emu.grasscutter.command.handler.collection;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.handler.*;
import emu.grasscutter.command.handler.annotation.Handler;
import emu.grasscutter.command.handler.annotation.HandlerCollection;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.mail.Mail;
import emu.grasscutter.game.player.Player;
import lombok.SneakyThrows;

import static emu.grasscutter.command.handler.ContextFields.*;
import static emu.grasscutter.utils.Language.translate;

@HandlerCollection
public class MailHandlerCollection {
    @Handler(MAIL_SEND)
    @SneakyThrows
    public void sendMail(HandlerContext context) {
        // read arguments from the context
        Mail mail = context.getRequired(Fields.MAIL, Mail.class);
        int target = context.getOptional(TARGET_UID, -1); // -1 means to all

        if (target != -1) {
            Player player = DatabaseHelper.getPlayerById(target);
            if (player != null) {
                player.sendMail(mail);
            } else {
                throw new RuntimeException(translate("commands.execution.player_exist_error"));
            }
        } else {
            DatabaseHelper.getAllPlayers().forEach(
                    player -> Grasscutter.getGameServer().getPlayerByUid(
                            player.getUid(), true
                    ).sendMail(mail)
            );
        }
    }

    public static final String MAIL_SEND = "mail.send";
    public static final class Fields {
        public static final String MAIL = "mail.mail";
    }
}
