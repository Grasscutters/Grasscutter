package emu.grasscutter.game.ability;

import java.util.*;
import java.util.Optional;

import com.google.protobuf.InvalidProtocolBufferException;

import emu.grasscutter.Grasscutter;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.AbilityModifierEntry;
import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import emu.grasscutter.data.excels.AvatarSkillDepotData;
import emu.grasscutter.data.excels.ItemData;
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
import emu.grasscutter.game.props.FightProperty;

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
			case ABILITY_INVOKE_ARGUMENT_META_OVERRIDE_PARAM:
				handleOverrideParam(invoke);
				break;
			case ABILITY_INVOKE_ARGUMENT_META_REINIT_OVERRIDEMAP:
				handleReinitOverrideMap(invoke);
				break;
			case ABILITY_INVOKE_ARGUMENT_META_MODIFIER_CHANGE:
				handleModifierChange(invoke);
				break;
			case ABILITY_INVOKE_ARGUMENT_MIXIN_COST_STAMINA:
				handleMixinCostStamina(invoke);
				break;
			case ABILITY_INVOKE_ARGUMENT_ACTION_GENERATE_ELEM_BALL:
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
					invokeAction(action, target, sourceEntity, modifierString);
				}
			}

			if (modifier != null && modifier.getOnThinkInterval().size() > 0) {
				for (AbilityModifierAction action : modifier.getOnThinkInterval()) {
					invokeAction(action, target, sourceEntity, modifierString);
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
						invokeAction(action, target, sourceEntity, modifierString);
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

	private void handleGenerateElemBall(AbilityInvokeEntry invoke) throws InvalidProtocolBufferException {
		this.player.getEnergyManager().handleGenerateElemBall(invoke);
	}
	
    private class HealData {
        public String avatar = "";
        public int type= 0;
        public float factor = 0;
        public float base = 0;

        public HealData(String _avatar, int _type, float _factor, float _base) {
            avatar = _avatar;
            type = _type;
            factor = _factor;
            base = _base;
        }
    }

	private void invokeAction(AbilityModifierAction action, GameEntity target, GameEntity sourceEntity, String modifierString) {
		switch (action.type) {
			case HealHP -> {
                ArrayList<HealData> healDataList = new ArrayList();
                healDataList.add(new HealData("Kokomi", 0, 0.094f, 1165f));
                healDataList.add(new HealData("Qin", 1, 5.34f, 4236f)); 
                healDataList.add(new HealData("Noel", 2, 0.452f, 282f));
                healDataList.add(new HealData("Bennett", 0, 0.1275f, 1588f));
                healDataList.add(new HealData("Diona", 0, 0.1134f, 1411f));
                healDataList.add(new HealData("Sayu", 1, 1.958f, 1588f));
                healDataList.add(new HealData("Barbara", 0, 0.374f, 4660f));
                healDataList.add(new HealData("Hutao", 0, 0.1166f, 0f));
                healDataList.add(new HealData("Shinobu", 0, 0.064f, 795f));
                healDataList.add(new HealData("Qiqi", 1, 1.91f, 1588f));

                int type = 0;
                float factor = 0;
                float base = 0;
                for(int i = 0 ; i < healDataList.size() ; i ++) {
                    HealData healData = healDataList.get(i);
                    if(modifierString.contains(healData.avatar)) {
                        type = healData.type;
                        factor = healData.factor;
                        base = healData.base;
                        break;
                    }
                }

                float maxHP = target.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP);
                float curAttack = target.getFightProperty(FightProperty.FIGHT_PROP_CUR_ATTACK);
                float curDefense = target.getFightProperty(FightProperty.FIGHT_PROP_CUR_DEFENSE);

				float healAmount = 0;
                switch(type) {
                    case 0:
                        healAmount = factor * maxHP + base;
                        break;
                    case 1:
                        healAmount = factor * curAttack + base;
                        break;
                    case 2:
                        healAmount = factor * curDefense + base;
                        break;
                }
				target.heal(healAmount);
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

