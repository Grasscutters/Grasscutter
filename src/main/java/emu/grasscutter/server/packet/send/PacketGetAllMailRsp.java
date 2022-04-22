package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetAllMailRspOuterClass.GetAllMailRsp;
import emu.grasscutter.net.proto.MailDataOuterClass.MailData;
import emu.grasscutter.net.proto.MailTextContentOuterClass.MailTextContent;

import java.time.Instant;

public class PacketGetAllMailRsp extends GenshinPacket {

    public PacketGetAllMailRsp(GenshinPlayer player, boolean isGiftMail) {
        super(PacketOpcodes.GetAllMailRsp);

        GetAllMailRsp.Builder proto = GetAllMailRsp.newBuilder();

        MailTextContent.Builder mailTextContent = MailTextContent.newBuilder();
        mailTextContent.setTitle("System Message");
        mailTextContent.setContent("I'm going to kill you...");
        mailTextContent.setSender("YOU");

        MailData.Builder mailData = MailData.newBuilder();
        mailData.setMailId(0);
        mailData.setMailTextContent(mailTextContent.build());
        mailData.setSendTime((int) Instant.now().getEpochSecond());
        mailData.setExpireTime(999999999);
        mailData.setImportance(1);
        mailData.setIsRead(false);
        mailData.setIsAttachmentGot(false);
        maildata.

        proto.addMailList(mailData.build());
        proto.setIsTruncated(false);

        this.setData(proto);
    }
}
