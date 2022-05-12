package emu.grasscutter.server.packet.recv;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.TowerEnterLevelReqOuterClass.TowerEnterLevelReq;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.TowerEnterLevelReq)
public class HandlerTowerEnterLevelReq extends PacketHandler {

	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
    if(session == null && payload == null){
      return;
    }

    if (Grasscutter.getConfig().server.game.gameOptions.AbyssMT) {
			CommandHandler.sendMessage(session.getPlayer(), "Sorry Abyss has been temporarily turned off");
      return;
		}

    TowerEnterLevelReq req = TowerEnterLevelReq.parseFrom(payload);
		session.getPlayer().getTowerManager().enterLevel(req.getEnterPointId());

	}
}
