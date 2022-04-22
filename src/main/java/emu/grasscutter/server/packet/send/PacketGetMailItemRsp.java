package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetMailItemRspOuterClass.GetMailItemRsp;
import emu.grasscutter.net.proto.ItemParamOuterClass;
import emu.grasscutter.net.proto.MailItemOuterClass;

import java.util.List;

public class PacketGetMailItemRsp  extends GenshinPacket {

    public PacketGetMailItemRsp(GenshinPlayer player, List<Integer> mailList) {
        super(PacketOpcodes.GetMailItemRsp);

        //I'm assuming that this is to receive the attachments on the message.
        // TODO: This.

        //GetMailItemRsp.Builder proto = GetMailItemRsp.newBuilder();

        //MailItemOuterClass.MailItem.Builder mailItem = MailItemOuterClass.MailItem.newBuilder();

        //ItemParamOuterClass.ItemParam.Builder itemParam = ItemParamOuterClass.ItemParam.newBuilder();

        //mailItem.setItemParam(itemParam);

        //proto.addAllMailIdList(mailList);
        //proto.addItemList();
    }
}
