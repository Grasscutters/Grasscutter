package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;

public class PacketHomeAvatarSummonAllEventNotify extends BasePacket {
    public PacketHomeAvatarSummonAllEventNotify(Player homeOwner) {
        super(PacketOpcodes.HomeAvatarSummonAllEventNotify);
        this.setData(homeOwner.getCurHomeWorld().getModuleManager().toSummonEventProto());
    }
}
