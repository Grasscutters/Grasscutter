package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.DropHintNotifyOuterClass.DropHintNotify;
import emu.grasscutter.net.proto.VectorOuterClass.Vector;

public class PacketDropHintNotify extends BasePacket {
    public PacketDropHintNotify(int itemId, Vector position){
        super(PacketOpcodes.DropHintNotify);
        var proto= DropHintNotify.newBuilder()
            .addItemIdList(itemId)
            .setPosition(position);
        setData(proto.build());
    }
    public PacketDropHintNotify(Iterable<Integer> items, Vector position){
        super(PacketOpcodes.DropHintNotify);
        var proto= DropHintNotify.newBuilder()
            .addAllItemIdList(items)
            .setPosition(position);
        setData(proto.build());
    }
}
