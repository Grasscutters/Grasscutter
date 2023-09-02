package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ForgeQueueManipulateRspOuterClass.ForgeQueueManipulateRsp;
import emu.grasscutter.net.proto.ForgeQueueManipulateTypeOuterClass.ForgeQueueManipulateType;
import emu.grasscutter.net.proto.ItemParamOuterClass.ItemParam;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;
import java.util.List;

public class PacketForgeQueueManipulateRsp extends BasePacket {

    public PacketForgeQueueManipulateRsp(
            Retcode retcode,
            ForgeQueueManipulateType type,
            List<GameItem> output,
            List<GameItem> refund,
            List<GameItem> extra) {
        super(PacketOpcodes.ForgeQueueManipulateRsp);

        ForgeQueueManipulateRsp.Builder builder =
                ForgeQueueManipulateRsp.newBuilder()
                        .setRetcode(retcode.getNumber())
                        .setManipulateType(type);

        for (GameItem item : output) {
            ItemParam toAdd =
                    ItemParam.newBuilder().setItemId(item.getItemId()).setCount(item.getCount()).build();

            builder.addExtraOutputItemList(toAdd);
        }

        for (GameItem item : refund) {
            ItemParam toAdd =
                    ItemParam.newBuilder().setItemId(item.getItemId()).setCount(item.getCount()).build();

            builder.addReturnItemList(toAdd);
        }

        // ToDo: Add extra items when once we have handling for it.

        this.setData(builder.build());
    }
}
