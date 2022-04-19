package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.entity.GenshinEntity;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.CombatInvocationsNotifyOuterClass.CombatInvocationsNotify;
import emu.grasscutter.net.proto.CombatInvokeEntryOuterClass.CombatInvokeEntry;
import emu.grasscutter.net.proto.EntityMoveInfoOuterClass.EntityMoveInfo;
import emu.grasscutter.net.proto.EvtBeingHitInfoOuterClass.EvtBeingHitInfo;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.CombatInvocationsNotify)
public class HandlerCombatInvocationsNotify extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		CombatInvocationsNotify notif = CombatInvocationsNotify.parseFrom(payload);
		
		for (CombatInvokeEntry entry : notif.getInvokeListList()) {
			switch (entry.getArgumentType()) {
				case CombatEvtBeingHit:
					// Handle damage
					EvtBeingHitInfo hitInfo = EvtBeingHitInfo.parseFrom(entry.getCombatData());
					session.getPlayer().getScene().handleAttack(hitInfo.getAttackResult());
					break;
				case EntityMove:
					// Handle movement
					EntityMoveInfo moveInfo = EntityMoveInfo.parseFrom(entry.getCombatData());
					GenshinEntity entity = session.getPlayer().getScene().getEntityById(moveInfo.getEntityId());
					if (entity != null) {
						entity.getPosition().set(moveInfo.getMotionInfo().getPos());
						entity.getRotation().set(moveInfo.getMotionInfo().getRot());
						entity.setLastMoveSceneTimeMs(moveInfo.getSceneTime());
						entity.setLastMoveReliableSeq(moveInfo.getReliableSeq());
						entity.setMotionState(moveInfo.getMotionInfo().getState());
					}
					break;
				default:
					break;
			}
			
			session.getPlayer().getCombatInvokeHandler().addEntry(entry.getForwardType(), entry);
		}
		
		if (notif.getInvokeListList().size() > 0) {
			session.getPlayer().getCombatInvokeHandler().update(session.getPlayer());
		}
	}

}
