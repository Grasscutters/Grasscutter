package emu.grasscutter.server.packet.send;


import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.*;

import java.util.List;

public class PacketMailChangeNotify extends GenshinPacket {

    public PacketMailChangeNotify(GenshinPlayer player) {
        super(PacketOpcodes.MailChangeNotify);

        MailChangeNotifyOuterClass.MailChangeNotify.Builder proto = MailChangeNotifyOuterClass.MailChangeNotify.newBuilder();

        // Dummy data.
        MailTextContentOuterClass.MailTextContent.Builder mailTextContent = MailTextContentOuterClass.MailTextContent.newBuilder();
        mailTextContent.setTitle("System Message");
        mailTextContent.setContent("I'm going to kill you...");
        mailTextContent.setSender("YOU");

        MailItemOuterClass.MailItem.Builder mailItem = MailItemOuterClass.MailItem.newBuilder();
        ItemParamOuterClass.ItemParam.Builder itemParam = ItemParamOuterClass.ItemParam.newBuilder();

        itemParam.setItemId(1062);
        itemParam.setCount(1);
        mailItem.setItemParam(itemParam.build());

        MailDataOuterClass.MailData.Builder mailData = MailDataOuterClass.MailData.newBuilder();
        mailData.setMailId(100);
        mailData.setMailTextContent(mailTextContent.build());
        mailData.addItemList(mailItem.build());
        mailData.setSendTime(1634100481);
        mailData.setExpireTime(1664498747);
        mailData.setImportance(1);
        mailData.setIsRead(false);
        mailData.setIsAttachmentGot(false);
        mailData.setStateValue(1);

        proto.addMailList(mailData.build());

        this.setData(proto.build());
    }
}