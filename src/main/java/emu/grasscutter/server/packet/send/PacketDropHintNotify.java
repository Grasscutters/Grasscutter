package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.DropHintNotifyOuterClass.DropHintNotify;
import emu.grasscutter.net.proto.VectorOuterClass.Vector;

public class PacketDropHintNotify extends BasePacket {
    public PacketDropHintNotify(int itemId, Vector position) {
        super(PacketOpcodes.DropHintNotify);

        var proto = DropHintNotify.newBuilder().addItemIdList(itemId).setPosition(position);
        this.setData(proto.build());
    }

    public PacketDropHintNotify(Iterable<GameItem> items, Vector position) {
        super(PacketOpcodes.DropHintNotify);

        var proto = DropHintNotify.newBuilder();
        items.forEach(i -> proto.addItemIdList(i.getItemId()));
        proto.setPosition(position);
        this.setData(proto.build());
    }
}
