package emu.grasscutter.server.packet.recv;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ACDCLDJFDFKOuterClass;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.ACDCLDJFDFK)
public class HandlerPlayerEnterMapLayerNotify extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var fromClient = ACDCLDJFDFKOuterClass.ACDCLDJFDFK.parseFrom(payload);
        Grasscutter.getLogger().info("[DEBUG] Player found new map layer: id {}", fromClient.getPPLHKCKDHEC());

    }
}
