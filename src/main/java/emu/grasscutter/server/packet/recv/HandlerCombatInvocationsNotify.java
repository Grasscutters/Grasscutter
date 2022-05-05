package emu.grasscutter.server.packet.recv;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.CombatInvocationsNotifyOuterClass.CombatInvocationsNotify;
import emu.grasscutter.net.proto.CombatInvokeEntryOuterClass.CombatInvokeEntry;
import emu.grasscutter.net.proto.EntityMoveInfoOuterClass.EntityMoveInfo;
import emu.grasscutter.net.proto.EvtBeingHitInfoOuterClass.EvtBeingHitInfo;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.proto.MotionStateOuterClass.MotionState;
import emu.grasscutter.net.proto.VectorOuterClass.Vector;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.*;

import java.util.Arrays;
import java.util.Collection;

@Opcodes(PacketOpcodes.CombatInvocationsNotify)
public class HandlerCombatInvocationsNotify extends PacketHandler {

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
					MotionState state = moveInfo.getMotionInfo().getState();
					if (entity != null) {
						//move
						entity.getPosition().set(moveInfo.getMotionInfo().getPos());
						entity.getRotation().set(moveInfo.getMotionInfo().getRot());
						entity.setLastMoveSceneTimeMs(moveInfo.getSceneTime());
						entity.setLastMoveReliableSeq(moveInfo.getReliableSeq());
						entity.setMotionState(moveInfo.getMotionInfo().getState());

						if(Grasscutter.getConfig().OpenStamina){
							//consume stamina
							int curStamina = session.getPlayer().getProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA);
							int maxStamina = session.getPlayer().getProperty(PlayerProperty.PROP_MAX_STAMINA);
							if (CONSUME_STAMINA_LIST.contains(state)) {

								//In the water exhausted stamina

								//Climbing the wall stays in place

								//Sprint in the water
								if (state == MotionState.MOTION_SWIM_DASH) {
									curStamina -= 700;
								}
								//wall jump
								else if (state == MotionState.MOTION_CLIMB_JUMP) {
									curStamina -= 2000;
								}
								//climb the wall slowly
								else if (state == MotionState.MOTION_CLIMB) {
									curStamina -= 800;
								}
								else if (state == MotionState.MOTION_DASH_BEFORE_SHAKE) {
									curStamina -= 2500;
								}
								else {
									curStamina -= 500;
								}

								session.getPlayer().setProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA, curStamina);
								session.send(new PacketPlayerPropNotify(session.getPlayer(), PlayerProperty.PROP_CUR_PERSIST_STAMINA));
								break;
							}
							//restore stamina
							if (RESTORE_STAMINA_LIST.contains(state)) {
								if(state == MotionState.MOTION_STANDBY) {
									Vector speed = moveInfo.getMotionInfo().getSpeed();
									if(speed.getX() != 0 && speed.getZ() != 0 && speed.getY() != 0) {
										break;
									}
								}
								curStamina += 1000;
								if (curStamina >= maxStamina) {
									curStamina = maxStamina;
								}
								session.getPlayer().setProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA, curStamina);
								session.send(new PacketPlayerPropNotify(session.getPlayer(), PlayerProperty.PROP_CUR_PERSIST_STAMINA));
							}
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

	private static MotionState[] consumeStaminaTypes = new MotionState[]{
			MotionState.MOTION_CLIMB, MotionState.MOTION_CLIMB_JUMP, MotionState.MOTION_SWIM_DASH,
			MotionState.MOTION_SWIM_MOVE, MotionState.MOTION_FLY, MotionState.MOTION_DASH,
			MotionState.MOTION_DASH_BEFORE_SHAKE, MotionState.MOTION_FIGHT, MotionState.MOTION_JUMP_UP_WALL_FOR_STANDBY,
			MotionState.MOTION_FLY_SLOW
	};
	private static MotionState[] restoreStaminaTypes = new MotionState[]{
			MotionState.MOTION_STANDBY, MotionState.MOTION_RUN, MotionState.MOTION_WALK,
			MotionState.MOTION_STANDBY_MOVE
	};

	private static final Collection<MotionState> CONSUME_STAMINA_LIST = Arrays.asList(consumeStaminaTypes);
	private static final Collection<MotionState> RESTORE_STAMINA_LIST = Arrays.asList(restoreStaminaTypes);
}
