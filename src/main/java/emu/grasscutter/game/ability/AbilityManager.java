package emu.grasscutter.game.ability;

import com.google.protobuf.InvalidProtocolBufferException;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import emu.grasscutter.data.binout.AbilityModifierEntry;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.AbilityInvokeEntryHeadOuterClass.AbilityInvokeEntryHead;
import emu.grasscutter.net.proto.AbilityInvokeEntryOuterClass.AbilityInvokeEntry;
import emu.grasscutter.net.proto.AbilityMetaModifierChangeOuterClass.AbilityMetaModifierChange;
import emu.grasscutter.net.proto.AbilityMetaReInitOverrideMapOuterClass.AbilityMetaReInitOverrideMap;
import emu.grasscutter.net.proto.AbilityMixinCostStaminaOuterClass.AbilityMixinCostStamina;
import emu.grasscutter.net.proto.AbilityScalarValueEntryOuterClass.AbilityScalarValueEntry;
import emu.grasscutter.net.proto.ModifierActionOuterClass.ModifierAction;

public class AbilityManager {
    private final Player player;
    HealAbilityManager healAbilityManager;

    public AbilityManager(Player player) {
        this.player = player;
        this.healAbilityManager = new HealAbilityManager(player);
    }

    public Player getPlayer() {
        return this.player;
    }

    public void onAbilityInvoke(AbilityInvokeEntry invoke) throws Exception {
        this.healAbilityManager.healHandler(invoke);

        //Grasscutter.getLogger().info(invoke.getArgumentType() + " (" + invoke.getArgumentTypeValue() + "): " + Utils.bytesToHex(invoke.toByteArray()));
        switch (invoke.getArgumentType()) {
            case ABILITY_INVOKE_ARGUMENT_META_OVERRIDE_PARAM:
                this.handleOverrideParam(invoke);
                break;
            case ABILITY_INVOKE_ARGUMENT_META_REINIT_OVERRIDEMAP:
                this.handleReinitOverrideMap(invoke);
                break;
            case ABILITY_INVOKE_ARGUMENT_META_MODIFIER_CHANGE:
                this.handleModifierChange(invoke);
                break;
            case ABILITY_INVOKE_ARGUMENT_MIXIN_COST_STAMINA:
                this.handleMixinCostStamina(invoke);
                break;
            case ABILITY_INVOKE_ARGUMENT_ACTION_GENERATE_ELEM_BALL:
                this.handleGenerateElemBall(invoke);
                break;
            default:
                break;
        }

    }

    private void handleOverrideParam(AbilityInvokeEntry invoke) throws Exception {
        GameEntity entity = this.player.getScene().getEntityById(invoke.getEntityId());

        if (entity == null) {
            return;
        }

        AbilityScalarValueEntry entry = AbilityScalarValueEntry.parseFrom(invoke.getAbilityData());

        entity.getMetaOverrideMap().put(entry.getKey().getStr(), entry.getFloatValue());
    }

    private void handleReinitOverrideMap(AbilityInvokeEntry invoke) throws Exception {
        GameEntity entity = this.player.getScene().getEntityById(invoke.getEntityId());

        if (entity == null) {
            return;
        }

        AbilityMetaReInitOverrideMap map = AbilityMetaReInitOverrideMap.parseFrom(invoke.getAbilityData());

        for (AbilityScalarValueEntry entry : map.getOverrideMapList()) {
            entity.getMetaOverrideMap().put(entry.getKey().getStr(), entry.getFloatValue());
        }
    }

    private void handleModifierChange(AbilityInvokeEntry invoke) throws Exception {
        GameEntity target = this.player.getScene().getEntityById(invoke.getEntityId());
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

        GameEntity sourceEntity = this.player.getScene().getEntityById(data.getApplyEntityId());
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
                    this.invokeAction(action, target, sourceEntity);
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
                        this.invokeAction(action, target, sourceEntity);
                    }
                }

                // Remove from meta modifiers
                target.getMetaModifiers().remove(head.getInstancedModifierId());
            }
        }
    }

    private void handleMixinCostStamina(AbilityInvokeEntry invoke) throws InvalidProtocolBufferException {
        AbilityMixinCostStamina costStamina = AbilityMixinCostStamina.parseFrom((invoke.getAbilityData()));
        this.getPlayer().getStaminaManager().handleMixinCostStamina(costStamina.getIsSwim());
    }

    private void handleGenerateElemBall(AbilityInvokeEntry invoke) throws InvalidProtocolBufferException {
        this.player.getEnergyManager().handleGenerateElemBall(invoke);
    }

    private void invokeAction(AbilityModifierAction action, GameEntity target, GameEntity sourceEntity) {
        switch (action.type) {
            case HealHP -> {
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

