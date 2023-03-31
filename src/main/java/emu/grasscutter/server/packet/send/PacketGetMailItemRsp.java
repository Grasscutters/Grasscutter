package emu.grasscutter.server.packet.send;

import emu.grasscutter.data.GameData;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.mail.Mail;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.EquipParamOuterClass;
import emu.grasscutter.net.proto.GetMailItemRspOuterClass.GetMailItemRsp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PacketGetMailItemRsp extends BasePacket {

    public PacketGetMailItemRsp(Player player, List<Integer> mailList) {
        super(PacketOpcodes.GetMailItemRsp);
        List<Mail> claimedMessages = new ArrayList<>();
        List<EquipParamOuterClass.EquipParam> claimedItems = new ArrayList<>();

        GetMailItemRsp.Builder proto = GetMailItemRsp.newBuilder();

        synchronized (player) {
            boolean modified = false;
            for (int mailId : mailList) {
                Mail message = player.getMail(mailId);
                if (!message.isAttachmentGot) {//No duplicated item
                    for (Mail.MailItem mailItem : message.itemList) {
                        EquipParamOuterClass.EquipParam.Builder item = EquipParamOuterClass.EquipParam.newBuilder();
                        int promoteLevel = GameItem.getMinPromoteLevel(mailItem.itemLevel);

                        item.setItemId(mailItem.itemId);
                        item.setItemNum(mailItem.itemCount);
                        item.setItemLevel(mailItem.itemLevel);
                        item.setPromoteLevel(promoteLevel);
                        claimedItems.add(item.build());

                        GameItem gameItem = new GameItem(GameData.getItemDataMap().get(mailItem.itemId));
                        gameItem.setCount(mailItem.itemCount);
                        gameItem.setLevel(mailItem.itemLevel);
                        gameItem.setPromoteLevel(promoteLevel);
                        player.getInventory().addItem(gameItem, ActionReason.MailAttachment);
                    }

                    message.isAttachmentGot = true;
                    claimedMessages.add(message);

                    player.replaceMailByIndex(mailId, message);
                    modified = true;
                }
            }
            if (modified) {
                player.save();
            }
        }

        proto.addAllMailIdList(claimedMessages.stream().map(player::getMailId).collect(Collectors.toList()));
        proto.addAllItemList(claimedItems);

        this.setData(proto.build());
        player.getSession().send(new PacketMailChangeNotify(player, claimedMessages)); // For some reason you have to also send the MailChangeNotify packet
    }
}
