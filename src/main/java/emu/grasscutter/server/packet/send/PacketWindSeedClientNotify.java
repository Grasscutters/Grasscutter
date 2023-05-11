package emu.grasscutter.server.packet.send;

import com.google.protobuf.ByteString;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.WindSeedClientNotifyOuterClass.WindSeedClientNotify;
import emu.grasscutter.net.proto.WindSeedClientNotifyOuterClass.WindSeedClientNotify.AreaNotify;

public final class PacketWindSeedClientNotify extends BasePacket {
    public PacketWindSeedClientNotify(byte[] compiledLua) {
        super(PacketOpcodes.WindSeedClientNotify);

        this.setData(
                WindSeedClientNotify.newBuilder()
                        .setAreaNotify(
                                AreaNotify.newBuilder()
                                        .setAreaId(1)
                                        .setAreaType(1)
                                        .setAreaCode(ByteString.copyFrom(compiledLua))));
    }
}
