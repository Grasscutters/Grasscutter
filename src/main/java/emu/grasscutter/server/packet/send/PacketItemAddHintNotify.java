package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ItemAddHintNotifyOuterClass.ItemAddHintNotify;
import java.util.Collection;

public class PacketItemAddHintNotify extends BasePacket {

    public PacketItemAddHintNotify(GameItem item, ActionReason reason) {
        super(PacketOpcodes.ItemAddHintNotify);

        ItemAddHintNotify proto =
                ItemAddHintNotify.newBuilder()
                        .addItemList(item.toItemHintProto())
                        .setReason(reason.getValue())
                        .build();

        this.setData(proto);
    }

    public PacketItemAddHintNotify(Collection<GameItem> items, ActionReason reason) {
        super(PacketOpcodes.ItemAddHintNotify);

        ItemAddHintNotify.Builder proto = ItemAddHintNotify.newBuilder().setReason(reason.getValue());

        for (GameItem item : items) {
            proto.addItemList(item.toItemHintProto());
        }

        this.setData(proto);
    }
}
