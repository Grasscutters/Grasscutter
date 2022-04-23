package emu.grasscutter.server.packet.send;

import com.google.gson.Gson;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.Mail;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.*;
import emu.grasscutter.net.proto.GetAllMailRspOuterClass.GetAllMailRsp;
import emu.grasscutter.net.proto.MailDataOuterClass.MailData;
import emu.grasscutter.net.proto.MailTextContentOuterClass.MailTextContent;

import java.util.ArrayList;
import java.util.List;

public class PacketGetAllMailRsp extends GenshinPacket {

    public PacketGetAllMailRsp(GenshinPlayer player, boolean isGiftMail) {
        super(PacketOpcodes.GetAllMailRsp);
        Grasscutter.getLogger().info(String.valueOf(isGiftMail));

        GetAllMailRsp.Builder proto = GetAllMailRsp.newBuilder();

        List<MailData> mailDataList = new ArrayList<MailData>();

        // Dummy data.
        for(Mail message : player.getMail()) {
            MailTextContent.Builder mailTextContent = MailTextContent.newBuilder();
            mailTextContent.setTitle(message.mailContent.title);
            mailTextContent.setContent(message.mailContent.content);
            mailTextContent.setSender(message.mailContent.sender);

            List<MailItemOuterClass.MailItem> mailItems = new ArrayList<MailItemOuterClass.MailItem>();

            for(Mail.MailItem item : message.itemList) {
                MailItemOuterClass.MailItem.Builder mailItem = MailItemOuterClass.MailItem.newBuilder();
                ItemParamOuterClass.ItemParam.Builder itemParam = ItemParamOuterClass.ItemParam.newBuilder();
                itemParam.setItemId(item.itemId);
                itemParam.setCount(item.itemCount);
                mailItem.setItemParam(itemParam.build());

                mailItems.add(mailItem.build());
            }

            MailDataOuterClass.MailData.Builder mailData = MailDataOuterClass.MailData.newBuilder();
            mailData.setMailId(message._id);
            mailData.setMailTextContent(mailTextContent.build());
            mailData.addAllItemList(mailItems);
            mailData.setSendTime((int)message.sendTime);
            mailData.setExpireTime((int)message.expireTime);
            mailData.setImportance(message.importance);
            mailData.setIsRead(message.isRead);
            mailData.setIsAttachmentGot(message.isAttachmentGot);
            mailData.setStateValue(message.stateValue);

            mailDataList.add(mailData.build());
        }

        proto.addAllMailList(mailDataList);
        //proto.setIsTruncated(true);

        Grasscutter.getLogger().info(Grasscutter.getDispatchServer().getGsonFactory().toJson(proto.build()));

        this.setData(proto.build());
    }
}
