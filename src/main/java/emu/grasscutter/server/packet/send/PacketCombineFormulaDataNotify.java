package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.CombineFormulaDataNotifyOuterClass.CombineFormulaDataNotify;

public class PacketCombineFormulaDataNotify extends BasePacket {

    public PacketCombineFormulaDataNotify(int combineId) {
        super(PacketOpcodes.CombineFormulaDataNotify);

        CombineFormulaDataNotify proto = CombineFormulaDataNotify.newBuilder()
            .setCombineId(combineId)
            .setIsLocked(false)
            .build();

        this.setData(proto);
    }
}
