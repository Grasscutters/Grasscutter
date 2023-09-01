package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ForgeFormulaDataNotifyOuterClass.ForgeFormulaDataNotify;

public class PacketForgeFormulaDataNotify extends BasePacket {

    public PacketForgeFormulaDataNotify(int itemId) {
        super(PacketOpcodes.ForgeFormulaDataNotify);

        ForgeFormulaDataNotify proto =
                ForgeFormulaDataNotify.newBuilder().setForgeId(itemId).setIsLocked(false).build();

        this.setData(proto);
    }
}
