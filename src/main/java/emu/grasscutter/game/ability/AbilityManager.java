package emu.grasscutter.game.ability;

import java.util.Optional;

import com.google.protobuf.InvalidProtocolBufferException;

import emu.grasscutter.Grasscutter;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.custom.AbilityModifier.AbilityModifierAction;
import emu.grasscutter.data.def.AvatarSkillDepotData;
import emu.grasscutter.data.def.ItemData;
import emu.grasscutter.data.custom.AbilityModifierEntry;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.entity.EntityClientGadget;
import emu.grasscutter.game.entity.EntityItem;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ElementType;
import emu.grasscutter.net.proto.AbilityActionGenerateElemBallOuterClass.AbilityActionGenerateElemBall;
import emu.grasscutter.net.proto.AbilityInvokeEntryHeadOuterClass.AbilityInvokeEntryHead;
import emu.grasscutter.net.proto.AbilityInvokeEntryOuterClass.AbilityInvokeEntry;
import emu.grasscutter.net.proto.AbilityMetaModifierChangeOuterClass.AbilityMetaModifierChange;
import emu.grasscutter.net.proto.AbilityMetaReInitOverrideMapOuterClass.AbilityMetaReInitOverrideMap;
import emu.grasscutter.net.proto.AbilityMixinCostStaminaOuterClass.AbilityMixinCostStamina;
import emu.grasscutter.net.proto.AbilityScalarValueEntryOuterClass.AbilityScalarValueEntry;
import emu.grasscutter.net.proto.ModifierActionOuterClass.ModifierAction;
import emu.grasscutter.utils.Position;
import emu.grasscutter.utils.Utils;

public class AbilityManager {
	private Player player;
	
	public AbilityManager(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return this.player;
	}

	public void onAbilityInvoke(AbilityInvokeEntry invoke) throws Exception {
		// Grasscutter.getLogger().info(invoke.getArgumentType() + " (" + invoke.getArgumentTypeValue() + "): " + Utils.bytesToHex(invoke.toByteArray()));
		switch (invoke.getArgumentType()) {
			case ABILITY_META_OVERRIDE_PARAM:
				handleOverrideParam(invoke);
				break;
			case ABILITY_META_REINIT_OVERRIDEMAP:
				handleReinitOverrideMap(invoke);
				break;
			case ABILITY_META_MODIFIER_CHANGE:
				handleModifierChange(invoke);
				break;
			case ABILITY_MIXIN_COST_STAMINA:
				handleMixinCostStamina(invoke);
				break;
			case ABILITY_ACTION_GENERATE_ELEM_BALL:
				handleGenerateElemBall(invoke);
				break;
			default:
				break;
		}
	}

	private void handleOverrideParam(AbilityInvokeEntry invoke) throws Exception {
		GameEntity entity = player.getScene().getEntityById(invoke.getEntityId());
		
		if (entity == null) {
			return;
		}
		
		AbilityScalarValueEntry entry = AbilityScalarValueEntry.parseFrom(invoke.getAbilityData());
		
		entity.getMetaOverrideMap().put(entry.getKey().getStr(), entry.getFloatValue());
	}

	private void handleReinitOverrideMap(AbilityInvokeEntry invoke) throws Exception {
		GameEntity entity = player.getScene().getEntityById(invoke.getEntityId());
		
		if (entity == null) {
			return;
		}
		
		AbilityMetaReInitOverrideMap map = AbilityMetaReInitOverrideMap.parseFrom(invoke.getAbilityData());
		
		for (AbilityScalarValueEntry entry : map.getOverrideMapList()) {
			entity.getMetaOverrideMap().put(entry.getKey().getStr(), entry.getFloatValue());
		}
	}
	
	private void handleModifierChange(AbilityInvokeEntry invoke) throws Exception {
		GameEntity target = player.getScene().getEntityById(invoke.getEntityId());
		if (target == null) {
			return;
		}
		
		AbilityInvokeEntryHead head = invoke.getHead();
		if (head == null) {
			return;
		}
		
		AbilityMetaModifierChange data = AbilityMetaModifierChange.parseFrom(invoke.getAbilityData());
		if (data == null) {
			return;
		}
		
		GameEntity sourceEntity = player.getScene().getEntityById(data.getApplyEntityId());
		if (sourceEntity == null) {
			return;
		}
		
		// This is not how it works but we will keep it for now since healing abilities dont work properly anyways
		if (data.getAction() == ModifierAction.ADDED && data.getParentAbilityName() != null) {
			// Handle add modifier here
			String modifierString = data.getParentAbilityName().getStr();
			AbilityModifierEntry modifier = GameData.getAbilityModifiers().get(modifierString);
			
			if (modifier != null && modifier.getOnAdded().size() > 0) {
				for (AbilityModifierAction action : modifier.getOnAdded()) {
					invokeAction(action, target, sourceEntity);
				}
			}
			
			// Add to meta modifier list
			target.getMetaModifiers().put(head.getInstancedModifierId(), modifierString);
		} else if (data.getAction() == ModifierAction.REMOVED) {
			String modifierString = target.getMetaModifiers().get(head.getInstancedModifierId());
			
			if (modifierString != null) {
				// Get modifier and call on remove event
				AbilityModifierEntry modifier = GameData.getAbilityModifiers().get(modifierString);
				
				if (modifier != null && modifier.getOnRemoved().size() > 0) {
					for (AbilityModifierAction action : modifier.getOnRemoved()) {
						invokeAction(action, target, sourceEntity);
					}
				}
				
				// Remove from meta modifiers
				target.getMetaModifiers().remove(head.getInstancedModifierId());
			}
		}
	}
	
	private void handleMixinCostStamina(AbilityInvokeEntry invoke) throws InvalidProtocolBufferException {
		AbilityMixinCostStamina costStamina = AbilityMixinCostStamina.parseFrom((invoke.getAbilityData()));
		getPlayer().getStaminaManager().handleMixinCostStamina(costStamina.getIsSwim());
	}
	
	private int getCastingAvatarIdForElemBall(int invokeEntityId) {
		// To determine the avatar that has cast the skill that caused the energy particle to be generated,
		// we have to look at the entity that has invoked the ability. This can either be that avatar directly, 
		// or it can be an `EntityClientGadget`, owned (some way up the owner hierarchy) by the avatar 
		// that cast the skill.
		int res = 0;

		// Try to get the invoking entity from the scene.
		GameEntity entity = player.getScene().getEntityById(invokeEntityId);
		
		// If this entity is null, or not an `EntityClientGadget`, we assume that we are directly 
		// looking at the casting avatar.
		if (!(entity instanceof EntityClientGadget)) {
			res = invokeEntityId;
		}
		// If the entity is a `EntityClientGadget`, we need to "walk up" the owner hierarchy,
		// until the owner is no longer a gadget. This should then be the ID of the casting avatar.
		else {	
			while (entity instanceof EntityClientGadget gadget) {
				res = gadget.getOwnerEntityId();
				entity = player.getScene().getEntityById(gadget.getOwnerEntityId());
			}
		}
		
		return res;
	}

	private void handleGenerateElemBall(AbilityInvokeEntry invoke) throws InvalidProtocolBufferException {
		// Get action info.
		AbilityActionGenerateElemBall action = AbilityActionGenerateElemBall.parseFrom(invoke.getAbilityData());
		if (action == null) {
			return;
		}
		
		// Determine the element of the energy particle that we have to generate.
		// In case we can't, we default to an elementless particle.
		// The element is the element of the avatar that has cast the ability.
		// We can get that from the avatar's skill depot.
		int itemId = 2024;
		
		// Try to fetch the avatar from the player's party and determine their element.
		// ToDo: Does this work in co-op?
		int avatarId = getCastingAvatarIdForElemBall(invoke.getEntityId());	
		Optional<EntityAvatar> avatarEntity = player.getTeamManager().getActiveTeam()
													.stream()
													.filter(character -> character.getId() == avatarId)
													.findFirst();

		if (avatarEntity.isPresent()) {
			Avatar avatar = avatarEntity.get().getAvatar();

			if (avatar != null) {
				AvatarSkillDepotData skillDepotData = avatar.getSkillDepot();

				if (skillDepotData != null) {
					ElementType element = skillDepotData.getElementType();

					// If we found the element, we use it to deterine the ID of the
					// energy particle that we have to generate.
		 			if (element != null) {
		 				itemId = switch (element) {
							case Fire -> 2017;
							case Water -> 2018; 
							case Grass -> 2019;
							case Electric -> 2020;
							case Wind -> 2021;
							case Ice -> 2022;
							case Rock -> 2023;
							default -> 2024;
						};
		 			}
		 		}
		 	}
		}

		// Get the item data for an energy particle of the correct element.
		ItemData itemData = GameData.getItemDataMap().get(itemId);
		if (itemData == null) {
			return; // Should never happen
		}
		
		// Generate entity.
		EntityItem energyBall = new EntityItem(getPlayer().getScene(), getPlayer(), itemData, new Position(action.getPos()), 1);
		energyBall.getRotation().set(action.getRot());
		
		getPlayer().getScene().addEntity(energyBall);
	}
	
	private void invokeAction(AbilityModifierAction action, GameEntity target, GameEntity sourceEntity) {
		switch (action.type) {
			case HealHP -> {
				if (action.amount == null) {
					return;
				}
				
				float healAmount = 0;
				
				if (action.amount.isDynamic && action.amount.dynamicKey != null) {
					healAmount = sourceEntity.getMetaOverrideMap().getOrDefault(action.amount.dynamicKey, 0f);
				}
				
				if (healAmount > 0) {
					target.heal(healAmount);
				}
			}
			case LoseHP -> {
				if (action.amountByTargetCurrentHPRatio == null) {
					return;
				}
				
				float damageAmount = 0;
				
				if (action.amount.isDynamic && action.amount.dynamicKey != null) {
					damageAmount = sourceEntity.getMetaOverrideMap().getOrDefault(action.amount.dynamicKey, 0f);
				}
				
				if (damageAmount > 0) {
					target.damage(damageAmount);
				}
			}
		}
	}
}

