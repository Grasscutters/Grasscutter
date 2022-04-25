package emu.grasscutter.server.packet.send;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GenshinData;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.Mail;
import emu.grasscutter.game.inventory.GenshinItem;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.EquipParamOuterClass;
import emu.grasscutter.net.proto.GetMailItemRspOuterClass.GetMailItemRsp;
import emu.grasscutter.net.proto.ItemParamOuterClass;
import emu.grasscutter.net.proto.MailItemOuterClass;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PacketGetMailItemRsp  extends GenshinPacket {

    public PacketGetMailItemRsp(GenshinPlayer player, List<Integer> mailList) {
        super(PacketOpcodes.GetMailItemRsp);

        //I'm assuming that this is to receive the attachments on the message.
        // TODO: This.

        List<Mail> claimedMessages = new ArrayList<>();
        List<EquipParamOuterClass.EquipParam> claimedItems = new ArrayList<>();

        GetMailItemRsp.Builder proto = GetMailItemRsp.newBuilder();

        for (int mailId : mailList) {
            Mail message = player.getMailById(mailId);
            int messageIndex = player.getMailIndex(message);

            message.isAttachmentGot = true;
            claimedMessages.add(message);

            player.replaceMailByIndex(messageIndex, message);

            for(Mail.MailItem mailItem : message.itemList) {
                //TODO: Actually give the item
                EquipParamOuterClass.EquipParam.Builder item = EquipParamOuterClass.EquipParam.newBuilder();
                item.setItemId(mailItem.itemId);
                item.setItemNum(mailItem.itemCount);
                claimedItems.add(item.build());

                GenshinItem genshinItem = new GenshinItem(GenshinData.getItemDataMap().get(mailItem.itemId));
                genshinItem.setCount(mailItem.itemCount);
                player.getInventory().addItem(genshinItem);
                player.sendPacket(new PacketItemAddHintNotify(genshinItem, ActionReason.MailAttachment));
            }
        }

        proto.addAllMailIdList(claimedMessages.stream().map(Mail::getId).collect(Collectors.toList()));
        proto.addAllItemList(claimedItems);
        player.save();

        Grasscutter.getLogger().info(Grasscutter.getDispatchServer().getGsonFactory().toJson(proto.build()));
        this.setData(proto.build());
        player.getSession().send(new PacketMailChangeNotify(player, claimedMessages));
    }
}
