package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.mail.Mail;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.EquipParamOuterClass.EquipParam;
import emu.grasscutter.net.proto.MailChangeNotifyOuterClass.MailChangeNotify;
import emu.grasscutter.net.proto.MailDataOuterClass.MailData;
import emu.grasscutter.net.proto.MailItemOuterClass.MailItem;
import emu.grasscutter.net.proto.MailTextContentOuterClass.MailTextContent;
import java.util.*;

public class PacketMailChangeNotify extends BasePacket {

    public PacketMailChangeNotify(Player player, Mail message) {
        this(
                player,
                new ArrayList<Mail>() {
                    {
                        add(message);
                    }
                });
    }

    public PacketMailChangeNotify(Player player, List<Mail> mailList) {
        this(player, mailList, null);
    }

    public PacketMailChangeNotify(Player player, List<Mail> mailList, List<Integer> delMailIdList) {
        super(PacketOpcodes.MailChangeNotify);

        var proto = MailChangeNotify.newBuilder();

        if (mailList != null) {
            for (Mail message : mailList) {
                var mailTextContent = MailTextContent.newBuilder();
                mailTextContent.setTitle(message.mailContent.title);
                mailTextContent.setContent(message.mailContent.content);
                mailTextContent.setSender(message.mailContent.sender);

                List<MailItem> mailItems = new ArrayList<>();

                for (Mail.MailItem item : message.itemList) {
                    var mailItem = MailItem.newBuilder();
                    var itemParam = EquipParam.newBuilder();
                    itemParam.setItemId(item.itemId);
                    itemParam.setItemNum(item.itemCount);
                    mailItem.setEquipParam(itemParam.build());

                    mailItems.add(mailItem.build());
                }

                var mailData = MailData.newBuilder();
                mailData.setMailId(player.getMailId(message));
                mailData.setMailTextContent(mailTextContent.build());
                mailData.addAllItemList(mailItems);
                mailData.setSendTime((int) message.sendTime);
                mailData.setExpireTime((int) message.expireTime);
                mailData.setImportance(message.importance);
                mailData.setIsRead(message.isRead);
                mailData.setIsAttachmentGot(message.isAttachmentGot);
                mailData.setCollectStateValue(message.stateValue);

                proto.addMailList(mailData.build());
            }
        }

        if (delMailIdList != null) {
            proto.addAllDelMailIdList(delMailIdList);
        }

        this.setData(proto.build());
    }
}
