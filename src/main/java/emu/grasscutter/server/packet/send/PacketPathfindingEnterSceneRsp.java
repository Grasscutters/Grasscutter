package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;

public class PacketPathfindingEnterSceneRsp extends BasePacket {

    public PacketPathfindingEnterSceneRsp(int clientSequence) {
        super(PacketOpcodes.PathfindingEnterSceneRsp);
    }
}
