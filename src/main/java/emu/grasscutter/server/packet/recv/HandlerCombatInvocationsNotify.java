package emu.grasscutter.server.packet.recv;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AttackResultOuterClass;
import emu.grasscutter.net.proto.AttackResultOuterClass.AttackResult;
import emu.grasscutter.net.proto.CombatInvocationsNotifyOuterClass.CombatInvocationsNotify;
import emu.grasscutter.net.proto.CombatInvokeEntryOuterClass.CombatInvokeEntry;
import emu.grasscutter.net.proto.EntityMoveInfoOuterClass.EntityMoveInfo;
import emu.grasscutter.net.proto.EvtAnimatorParameterInfoOuterClass.EvtAnimatorParameterInfo;
import emu.grasscutter.net.proto.EvtBeingHitInfoOuterClass.EvtBeingHitInfo;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.proto.MotionInfoOuterClass.MotionInfo;
import emu.grasscutter.net.proto.MotionStateOuterClass.MotionState;
import emu.grasscutter.net.proto.PlayerDieTypeOuterClass;
import emu.grasscutter.server.event.entity.EntityMoveEvent;
import emu.grasscutter.server.event.game.ReceiveCommandFeedbackEvent;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketEntityFightPropUpdateNotify;
import emu.grasscutter.utils.Position;

@Opcodes(PacketOpcodes.CombatInvocationsNotify)
public class HandlerCombatInvocationsNotify extends PacketHandler {

	private float cachedLandingSpeed = 0;
	private long cachedLandingTimeMillisecond = 0;
	private boolean monitorLandingEvent = false;

	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		CombatInvocationsNotify notif = CombatInvocationsNotify.parseFrom(payload);
		for (CombatInvokeEntry entry : notif.getInvokeListList()) {
		    // Handle combat invoke
			switch (entry.getArgumentType()) {
				case COMBAT_TYPE_ARGUMENT_EVT_BEING_HIT:
					EvtBeingHitInfo hitInfo = EvtBeingHitInfo.parseFrom(entry.getCombatData());
					AttackResult attackResult = hitInfo.getAttackResult();
					Player player = session.getPlayer();

					// Handle damage
					player.getAttackResults().add(attackResult);
					player.getEnergyManager().handleAttackHit(hitInfo);
					break;
				case COMBAT_TYPE_ARGUMENT_ENTITY_MOVE:
					// Handle movement
					EntityMoveInfo moveInfo = EntityMoveInfo.parseFrom(entry.getCombatData());
					GameEntity entity = session.getPlayer().getScene().getEntityById(moveInfo.getEntityId());
					if (entity != null) {
						// Move player
						MotionInfo motionInfo = moveInfo.getMotionInfo();
                        MotionState motionState = motionInfo.getState();

                        // Call entity move event.
                        EntityMoveEvent event = new EntityMoveEvent(
                            entity, new Position(motionInfo.getPos()),
                            new Position(motionInfo.getRot()), motionState);
                        event.call();

						entity.getPosition().set(motionInfo.getPos());
						entity.getRotation().set(motionInfo.getRot());
						entity.setLastMoveSceneTimeMs(moveInfo.getSceneTime());
						entity.setLastMoveReliableSeq(moveInfo.getReliableSeq());
						entity.setMotionState(motionState);

						session.getPlayer().getStaminaManager().handleCombatInvocationsNotify(session, moveInfo, entity);

						// TODO: handle MOTION_FIGHT landing which has a different damage factor
						// 		Also, for plunge attacks, LAND_SPEED is always -30 and is not useful.
						//  	May need the height when starting plunge attack.

						// MOTION_LAND_SPEED and MOTION_FALL_ON_GROUND arrive in different packets.
						// Cache land speed for later use.
						if (motionState == MotionState.MOTION_STATE_LAND_SPEED) {
							cachedLandingSpeed = motionInfo.getSpeed().getY();
							cachedLandingTimeMillisecond = System.currentTimeMillis();
							monitorLandingEvent = true;
						}
						if (monitorLandingEvent) {
 							if (motionState == MotionState.MOTION_STATE_FALL_ON_GROUND) {
								monitorLandingEvent = false;
								handleFallOnGround(session, entity, motionState);
							}
						}
						
						// MOTION_STATE_NOTIFY = Dont send to other players
						if (motionState == MotionState.MOTION_STATE_NOTIFY) {
						    continue;
						}
					}
					break;
				case COMBAT_TYPE_ARGUMENT_ANIMATOR_PARAMETER_CHANGED:
				    EvtAnimatorParameterInfo paramInfo = EvtAnimatorParameterInfo.parseFrom(entry.getCombatData());
				    
				    if (paramInfo.getIsServerCache()) {
				        paramInfo = paramInfo.toBuilder().setIsServerCache(false).build();
	                    entry = entry.toBuilder().setCombatData(paramInfo.toByteString()).build();
				    }
				    break;
				default:
					break;
			}

			session.getPlayer().getCombatInvokeHandler().addEntry(entry.getForwardType(), entry);
		}
	}

	private void handleFallOnGround(GameSession session, GameEntity entity, MotionState motionState) {
		if (session.getPlayer().inGodmode()) {
			return;
		}
		// People have reported that after plunge attack (client sends a FIGHT instead of FALL_ON_GROUND) they will die
		// 		if they talk to an NPC (this is when the client sends a FALL_ON_GROUND) without jumping again.
		// A dirty patch: if not received immediately after MOTION_LAND_SPEED, discard this packet.
		// 200ms seems to be a reasonable delay.
		int maxDelay = 200;
		long actualDelay = System.currentTimeMillis() - cachedLandingTimeMillisecond;
		Grasscutter.getLogger().trace("MOTION_FALL_ON_GROUND received after " + actualDelay + "/" + maxDelay + "ms." + (actualDelay > maxDelay ? " Discard" : ""));
		if (actualDelay > maxDelay) {
			return;
		}
		float currentHP = entity.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP);
		float maxHP = entity.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP);
		float damageFactor = 0;
		if (cachedLandingSpeed < -23.5) {
			damageFactor = 0.33f;
		}
		if (cachedLandingSpeed < -25) {
			damageFactor = 0.5f;
		}
		if (cachedLandingSpeed < -26.5) {
			damageFactor = 0.66f;
		}
		if (cachedLandingSpeed < -28) {
			damageFactor = 1f;
		}
		float damage = maxHP * damageFactor;
		float newHP = currentHP - damage;
		if (newHP < 0) {
			newHP = 0;
		}
		if (damageFactor > 0) {
			Grasscutter.getLogger().debug(currentHP + "/" + maxHP + "\tLandingSpeed: " + cachedLandingSpeed +
					"\tDamageFactor: " + damageFactor + "\tDamage: " + damage + "\tNewHP: " + newHP);
		} else {
			Grasscutter.getLogger().trace(currentHP + "/" + maxHP + "\tLandingSpeed: 0\tNo damage");
		}
		entity.setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, newHP);
		entity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_CUR_HP));
		if (newHP == 0) {
			session.getPlayer().getStaminaManager().killAvatar(session, entity, PlayerDieTypeOuterClass.PlayerDieType.PLAYER_DIE_TYPE_FALL);
		}
		cachedLandingSpeed = 0;
	}
}
