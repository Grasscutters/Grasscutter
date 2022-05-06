package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.managers.SotSManager.SotSManager;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.ExitTransPointRegionNotify)
public class HandlerExitTransPointRegionNotify extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception{
        Player player = session.getPlayer();
        SotSManager sotsManager = player.getSotSManager();
        sotsManager.cancelAutoRecover();
    }
}
