package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PlayerQuitFromHomeNotifyOuterClass;

public class PacketPlayerQuitFromHomeNotify extends BasePacket {
    public PacketPlayerQuitFromHomeNotify(
            PlayerQuitFromHomeNotifyOuterClass.PlayerQuitFromHomeNotify.QuitReason reason) {
        super(PacketOpcodes.PlayerQuitFromHomeNotify);

        this.setData(
                PlayerQuitFromHomeNotifyOuterClass.PlayerQuitFromHomeNotify.newBuilder().setReason(reason));
    }
}
