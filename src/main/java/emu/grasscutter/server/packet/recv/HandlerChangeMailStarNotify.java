package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.mail.Mail;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ChangeMailStarNotifyOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketMailChangeNotify;
import java.util.*;

@Opcodes(PacketOpcodes.ChangeMailStarNotify)
public class HandlerChangeMailStarNotify extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        ChangeMailStarNotifyOuterClass.ChangeMailStarNotify req =
                ChangeMailStarNotifyOuterClass.ChangeMailStarNotify.parseFrom(payload);

        List<Mail> updatedMail = new ArrayList<>();

        for (int mailId : req.getMailIdListList()) {
            Mail message = session.getPlayer().getMail(mailId);

            message.importance = req.getIsStar() ? 1 : 0;

            session.getPlayer().replaceMailByIndex(mailId, message);
            updatedMail.add(message);
        }

        session.send(new PacketMailChangeNotify(session.getPlayer(), updatedMail));
    }
}
