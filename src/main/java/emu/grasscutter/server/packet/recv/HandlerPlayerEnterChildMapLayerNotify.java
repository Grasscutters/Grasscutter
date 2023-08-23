package emu.grasscutter.server.packet.recv;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.BMODMHEPOFFOuterClass;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.BMODMHEPOFF)
public class HandlerPlayerEnterChildMapLayerNotify extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var fromClient = BMODMHEPOFFOuterClass.BMODMHEPOFF.parseFrom(payload);
        // probably
        Grasscutter.getLogger().info("player entered child map layer: id {}", fromClient.getHJMMAOMEHOL());
    }
}
