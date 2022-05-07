package emu.grasscutter.server.packet.recv;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.CombatInvocationsNotifyOuterClass.CombatInvocationsNotify;
import emu.grasscutter.net.proto.CombatInvokeEntryOuterClass.CombatInvokeEntry;
import emu.grasscutter.net.proto.EntityMoveInfoOuterClass.EntityMoveInfo;
import emu.grasscutter.net.proto.EvtBeingHitInfoOuterClass.EvtBeingHitInfo;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.proto.MotionInfoOuterClass.MotionInfo;
import emu.grasscutter.net.proto.MotionStateOuterClass.MotionState;
import emu.grasscutter.net.proto.PlayerDieTypeOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketEntityFightPropUpdateNotify;

@Opcodes(PacketOpcodes.CombatInvocationsNotify)
public class HandlerCombatInvocationsNotify extends PacketHandler {

	private float cachedLandingSpeed = 0;
	private long cachedLandingTimeMillisecond = 0;
	private boolean monitorLandingEvent = false;

	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		CombatInvocationsNotify notif = CombatInvocationsNotify.parseFrom(payload);
		for (CombatInvokeEntry entry : notif.getInvokeListList()) {
			switch (entry.getArgumentType()) {
				case COMBAT_EVT_BEING_HIT:
					// Handle damage
					EvtBeingHitInfo hitInfo = EvtBeingHitInfo.parseFrom(entry.getCombatData());
					session.getPlayer().getAttackResults().add(hitInfo.getAttackResult());
					break;
				case ENTITY_MOVE:
					// Handle movement
					EntityMoveInfo moveInfo = EntityMoveInfo.parseFrom(entry.getCombatData());
					GameEntity entity = session.getPlayer().getScene().getEntityById(moveInfo.getEntityId());
					if (entity != null) {
						// Move player
						MotionInfo motionInfo = moveInfo.getMotionInfo();
						entity.getPosition().set(motionInfo.getPos());
						entity.getRotation().set(motionInfo.getRot());
						entity.setLastMoveSceneTimeMs(moveInfo.getSceneTime());
						entity.setLastMoveReliableSeq(moveInfo.getReliableSeq());
						MotionState motionState = motionInfo.getState();
						entity.setMotionState(motionState);

						session.getPlayer().getStaminaManager().handleCombatInvocationsNotify(session, moveInfo, entity);

						// TODO: handle MOTION_FIGHT landing
						//  For plunge attacks, LAND_SPEED is always -30 and is not useful.
						//  May need the height when starting plunge attack.

						if (monitorLandingEvent) {
 							if (motionState == MotionState.MOTION_FALL_ON_GROUND) {
								monitorLandingEvent = false;
								handleFallOnGround(session, entity, motionState);
							}
						}
						if (motionState == MotionState.MOTION_LAND_SPEED) {
							// MOTION_LAND_SPEED and MOTION_FALL_ON_GROUND arrive in different packet. Cache land speed for later use.
							cachedLandingSpeed = motionInfo.getSpeed().getY();
							cachedLandingTimeMillisecond = System.currentTimeMillis();
							monitorLandingEvent = true;
						}
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
        // Handle attack results last
		while (!session.getPlayer().getAttackResults().isEmpty()) {
			session.getPlayer().getScene().handleAttack(session.getPlayer().getAttackResults().poll());
		}
	}

	private void handleFallOnGround(GameSession session, GameEntity entity, MotionState motionState) {
		// If not received immediately after MOTION_LAND_SPEED, discard this packet.
		int maxDelay = 200;
		long actualDelay = System.currentTimeMillis() - cachedLandingTimeMillisecond;
		Grasscutter.getLogger().debug("MOTION_FALL_ON_GROUND received after " + actualDelay + "/" + maxDelay + "ms." + (actualDelay > maxDelay ? " Discard" : ""));
		if (actualDelay > maxDelay) {
			return;
		}
		float currentHP = entity.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP);
		float maxHP = entity.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP);
		float damage = 0;
		if (cachedLandingSpeed < -23.5) {
			damage = (float) (maxHP * 0.33);
		}
		if (cachedLandingSpeed < -25) {
			damage = (float) (maxHP * 0.5);
		}
		if (cachedLandingSpeed < -26.5) {
			damage = (float) (maxHP * 0.66);
		}
		if (cachedLandingSpeed < -28) {
			damage = (maxHP * 1);
		}
		float newHP = currentHP - damage;
		if (newHP < 0) {
			newHP = 0;
		}
		Grasscutter.getLogger().debug(currentHP + "/" + maxHP + "\t" + "\tDamage: " + damage + "\tnewHP: " + newHP);
		entity.setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, newHP);
		entity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_CUR_HP));
		if (newHP == 0) {
			session.getPlayer().getStaminaManager().killAvatar(session, entity, PlayerDieTypeOuterClass.PlayerDieType.PLAYER_DIE_FALL);
		}
		cachedLandingSpeed = 0;
	}
}
