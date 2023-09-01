package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ItemParamOuterClass.ItemParam;
import emu.grasscutter.net.proto.TakeCompoundOutputRspOuterClass.TakeCompoundOutputRsp;

public class PackageTakeCompoundOutputRsp extends BasePacket {

    public PackageTakeCompoundOutputRsp(Iterable<ItemParam> itemList, int retcode) {
        super(PacketOpcodes.TakeCompoundOutputRsp);
        var builder = TakeCompoundOutputRsp.newBuilder().addAllItemList(itemList).setRetcode(retcode);
        setData(builder.build());
    }
}
