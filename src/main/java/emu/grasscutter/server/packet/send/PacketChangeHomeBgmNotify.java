package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeChangeBgmNotifyOuterClass;

public class PacketChangeHomeBgmNotify extends BasePacket {
    public PacketChangeHomeBgmNotify(int homeBgmId) {
        super(PacketOpcodes.HomeChangeBgmNotify);

        var notify = HomeChangeBgmNotifyOuterClass.HomeChangeBgmNotify.newBuilder()
            .setBgmId(homeBgmId)
            .build();

        this.setData(notify);
    }
}
