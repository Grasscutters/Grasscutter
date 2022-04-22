package emu.grasscutter.server.packet.send;

import com.google.gson.Gson;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.DateTimeDeleteOuterClass;
import emu.grasscutter.net.proto.GetAllMailRspOuterClass.GetAllMailRsp;
import emu.grasscutter.net.proto.ItemParamOuterClass;
import emu.grasscutter.net.proto.MailDataOuterClass.MailData;
import emu.grasscutter.net.proto.MailItemOuterClass;
import emu.grasscutter.net.proto.MailTextContentOuterClass.MailTextContent;
import emu.grasscutter.net.proto.MaterialDeleteInfoOuterClass;

public class PacketGetAllMailRsp extends GenshinPacket {

    public PacketGetAllMailRsp(GenshinPlayer player, boolean isGiftMail) {
        super(PacketOpcodes.GetAllMailRsp);
        Grasscutter.getLogger().info(String.valueOf(isGiftMail));

        GetAllMailRsp.Builder proto = GetAllMailRsp.newBuilder();

        // Dummy data.
        MailTextContent.Builder mailTextContent = MailTextContent.newBuilder();
        mailTextContent.setTitle("Hello Traveller..");
        mailTextContent.setContent("You've called me emergency food for the last time. \n Get ready to die!");
        mailTextContent.setSender("P·A·I·M·O·N");

        MailItemOuterClass.MailItem.Builder mailItem = MailItemOuterClass.MailItem.newBuilder();
        ItemParamOuterClass.ItemParam.Builder itemParam = ItemParamOuterClass.ItemParam.newBuilder();

        itemParam.setItemId(1062);
        itemParam.setCount(1);
        mailItem.setItemParam(itemParam.build());

        MailData.Builder mailData = MailData.newBuilder();
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
        proto.addMailList(mailData.setMailId(101).build());
        proto.setIsTruncated(true);

        Grasscutter.getLogger().info(Grasscutter.getDispatchServer().getGsonFactory().toJson(proto.build()));

        this.setData(proto.build());
    }
}
