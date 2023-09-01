package emu.grasscutter.server.packet.send;

import com.google.protobuf.ByteString;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.WindSeedType1NotifyOuterClass.WindSeedType1Notify;

public final class PacketWindSeedClientNotify extends BasePacket {
    /**
     * Constructor for the generic WindSeedClientNotify packet.
     *
     * @param compiledLua The compiled Lua to send to the client.
     */
    public PacketWindSeedClientNotify(byte[] compiledLua) {
        super(PacketOpcodes.WindSeedType1Notify);

        var packet =
                WindSeedType1Notify.newBuilder().setPayload(ByteString.copyFrom(compiledLua)).build();

        this.setData(packet);
    }
}
