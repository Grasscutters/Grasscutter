
package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ExecuteGadgetLuaRspOuterClass.ExecuteGadgetLuaRsp;

public class PacketExecuteGadgetLuaRsp extends BasePacket {

    public PacketExecuteGadgetLuaRsp(int result) {
        super(PacketOpcodes.ExecuteGadgetLuaRsp, true);

        ExecuteGadgetLuaRsp proto = ExecuteGadgetLuaRsp.newBuilder()
            .setRetcode(result)
            .build();

        this.setData(proto);
    }
}
