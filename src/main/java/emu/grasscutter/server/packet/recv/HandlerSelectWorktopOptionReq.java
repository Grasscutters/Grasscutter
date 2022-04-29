package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SelectWorktopOptionReqOuterClass.SelectWorktopOptionReq;
import emu.grasscutter.scripts.constants.EventType;
import emu.grasscutter.scripts.data.ScriptArgs;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketSelectWorktopOptionRsp;

@Opcodes(PacketOpcodes.SelectWorktopOptionReq)
public class HandlerSelectWorktopOptionReq extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		SelectWorktopOptionReq req = SelectWorktopOptionReq.parseFrom(payload);
		
		try {
			GameEntity entity = session.getPlayer().getScene().getEntityById(req.getGadgetEntityId());
			
			if (entity == null || !(entity instanceof EntityGadget)) {
				return;
			}

			session.getPlayer().getScene().getScriptManager().callEvent(
					EventType.EVENT_SELECT_OPTION, 
					new ScriptArgs(entity.getConfigId(), req.getOptionId())
			);
		} finally {
			// Always send packet
			session.send(new PacketSelectWorktopOptionRsp(req.getGadgetEntityId(), req.getOptionId()));
		}
	}

}
