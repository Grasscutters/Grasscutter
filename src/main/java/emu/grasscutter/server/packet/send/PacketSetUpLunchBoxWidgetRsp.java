package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.lunchbox.LunchBoxData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.LunchBoxWidgetItemOuterClass;
import emu.grasscutter.net.proto.SetUpLunchBoxWidgetReqOuterClass;
import emu.grasscutter.net.proto.SetUpLunchBoxWidgetRspOuterClass;

import java.util.ArrayList;

public class PacketSetUpLunchBoxWidgetRsp extends BasePacket {
    public PacketSetUpLunchBoxWidgetRsp(Player player, SetUpLunchBoxWidgetReqOuterClass.SetUpLunchBoxWidgetReq req) {
        super(PacketOpcodes.SetUpLunchBoxWidgetRsp);

        // TODO: item check required
        ArrayList<LunchBoxData> newLunchBoxData = new ArrayList<>();
        for (int i = 0; i < req.getReq().getItemsCount(); i++)
        {
            LunchBoxWidgetItemOuterClass.LunchBoxWidgetItem widget = req.getReq().getItems(i);
            newLunchBoxData.add(new LunchBoxData(widget.getWidgetSlot(), widget.getSlotItemId()));
        }
        if (newLunchBoxData.size() != 0)
        {
            player.setLunchBoxData(newLunchBoxData);
            player.save();
        }

        this.setData(SetUpLunchBoxWidgetRspOuterClass.SetUpLunchBoxWidgetRsp.newBuilder().setRsp(req.getReq()));
    }
}
