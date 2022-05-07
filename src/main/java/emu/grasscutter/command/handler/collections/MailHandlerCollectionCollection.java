package emu.grasscutter.command.handler.collections;

import emu.grasscutter.command.handler.*;
import emu.grasscutter.command.handler.annotation.HandlerCollection;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.mail.Mail;
import emu.grasscutter.game.player.Player;
import lombok.SneakyThrows;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static emu.grasscutter.command.handler.ContextNaming.*;

@HandlerCollection(collectionCode = HandlerCollectionCode.Mail, collectionName = "mail")
public class MailHandlerCollectionCollection extends BaseHandlerCollection {
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    @SneakyThrows
    public void sendMail(HandlerEvent event) {
        // if you should handle this event
        if (notValid(event, Send)) {
            return;
        }

        // read arguments from the context
        HandlerContext context = event.getContext();
        Mail mail = context.getRequired("mail", Mail.class);
        int target = context.getOptional(TargetUid, -1); // -1 means to all

        // do send
        if (target != -1) {
            Player player = DatabaseHelper.getPlayerById(target);
            if (player != null) {
                player.sendMail(mail);
            } else {
                throw new RuntimeException("recipient not found");
            }
        } else {
            DatabaseHelper.getAllPlayers().forEach(player -> player.sendMail(mail));
        }
        context.returnWith(null);
    }

    public static final int Send = 0;
}
