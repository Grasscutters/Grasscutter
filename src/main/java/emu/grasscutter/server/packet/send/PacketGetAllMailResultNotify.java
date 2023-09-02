package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.GetAllMailResultNotifyOuterClass.GetAllMailResultNotify;
import emu.grasscutter.utils.Utils;
import java.time.Instant;
import java.util.List;

public final class PacketGetAllMailResultNotify extends BasePacket {
    /**
     * @param player The player to fetch the mail for.
     * @param gifts Is the mail for gifts?
     */
    public PacketGetAllMailResultNotify(Player player, boolean gifts) {
        super(PacketOpcodes.GetAllMailResultNotify);

        var packet =
                GetAllMailResultNotify.newBuilder()
                        .setTransaction(player.getUid() + "-" + Utils.getCurrentSeconds() + "-" + 0)
                        .setIsCollected(gifts)
                        .setPacketBeSentNum(1)
                        .setPacketNum(1);

        var inbox = player.getAllMail();
        if (!gifts && inbox.size() > 0) {
            packet.addAllMailList(
                    inbox.stream()
                            .filter(mail -> mail.stateValue == 1)
                            .filter(mail -> mail.expireTime > Instant.now().getEpochSecond())
                            .map(mail -> mail.toProto(player))
                            .toList());
        } else {
            // Empty mailbox.
            // TODO: Implement the gift mailbox.
            packet.addAllMailList(List.of());
        }

        this.setData(packet.build());
    }
}
