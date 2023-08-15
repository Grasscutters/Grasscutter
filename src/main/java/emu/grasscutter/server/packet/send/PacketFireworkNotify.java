package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.FireworksLaunchDataNotifyOuterClass.FireworksLaunchDataNotify;
import emu.grasscutter.net.proto.FireworksLaunchSchemeDataOuterClass.FireworksLaunchSchemeData;

public class PacketFireworkNotify extends BasePacket {
    public PacketFireworkNotify(FireworksLaunchSchemeData data) {
        super(PacketOpcodes.FireworkNotify);

        this.setData(FireworksLaunchDataNotify.newBuilder().addSchemeDataList(data));
    }
}
