package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.gacha.GachaManager;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;

public class PacketGetGachaInfoRsp extends BasePacket {

    public PacketGetGachaInfoRsp(GachaManager manager, Player player) {
        super(PacketOpcodes.GetGachaInfoRsp);

        this.setData(manager.toProto(player));
    }

}
