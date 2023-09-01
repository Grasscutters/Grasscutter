package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.SetPlayerSignatureRspOuterClass.SetPlayerSignatureRsp;

public class PacketSetPlayerSignatureRsp extends BasePacket {

    public PacketSetPlayerSignatureRsp(Player player) {
        super(PacketOpcodes.SetPlayerSignatureRsp);

        SetPlayerSignatureRsp proto =
                SetPlayerSignatureRsp.newBuilder().setSignature(player.getSignature()).build();

        this.setData(proto);
    }
}
