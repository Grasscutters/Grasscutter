package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetAllMailNotifyOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketGetAllMailResultNotify;
import emu.grasscutter.utils.Utils;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Opcodes(PacketOpcodes.GetAllMailNotify)
public class HandlerGetAllMailNotify extends PacketHandler {
    private static final int MAX_MAIL_DATA_NUM_PER_PACKET = 40;

    private static void subdivide(Player player) {
        var notGiftedMails = player.getAllMail().stream().filter(mail -> mail.stateValue == 1).toList();
        var packetsBeSent = notGiftedMails.size() / MAX_MAIL_DATA_NUM_PER_PACKET + 1;
        var curPacketNum = new AtomicInteger(1);
        for (int i = 0; i < packetsBeSent; i++) {
            player.sendPacket(new PacketGetAllMailResultNotify(packetsBeSent, curPacketNum.get(), Utils.make(() -> {
                var index = (curPacketNum.get() - 1) * MAX_MAIL_DATA_NUM_PER_PACKET;
                return notGiftedMails.subList(index, curPacketNum.get() == packetsBeSent ? notGiftedMails.size() - 1 : index + MAX_MAIL_DATA_NUM_PER_PACKET - 1);
            }).stream().map(mail -> mail.toProto(player)).toList(), createTransaction(player), false));
            curPacketNum.incrementAndGet();
        }
    }

    private static String createTransaction(Player player) {
        return player.getUid() + "-" + Utils.getCurrentSeconds() + "-" + 0;//mock
    }

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = GetAllMailNotifyOuterClass.GetAllMailNotify.parseFrom(payload);
        var gift = req.getIsCollected();
        if (gift) {
            session.send(new PacketGetAllMailResultNotify(1, 1, List.of(), "", true));
            return;
        }

        subdivide(session.getPlayer());
    }
}
