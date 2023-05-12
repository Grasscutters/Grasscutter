package emu.grasscutter.game.ability;

import com.google.protobuf.InvalidProtocolBufferException;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.AbilityData;
import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import emu.grasscutter.data.binout.AbilityModifierEntry;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.entity.gadget.GadgetGatherObject;
import emu.grasscutter.game.player.BasePlayerManager;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.enums.QuestContent;
import emu.grasscutter.net.proto.AbilityInvokeEntryOuterClass.AbilityInvokeEntry;
import emu.grasscutter.net.proto.AbilityMetaAddAbilityOuterClass.AbilityMetaAddAbility;
import emu.grasscutter.net.proto.AbilityMetaModifierChangeOuterClass.AbilityMetaModifierChange;
import emu.grasscutter.net.proto.AbilityMetaModifierDurabilityChangeOuterClass.AbilityMetaModifierDurabilityChange;
import emu.grasscutter.net.proto.AbilityMetaReInitOverrideMapOuterClass.AbilityMetaReInitOverrideMap;
import emu.grasscutter.net.proto.AbilityMixinCostStaminaOuterClass.AbilityMixinCostStamina;
import emu.grasscutter.net.proto.AbilityScalarValueEntryOuterClass.AbilityScalarValueEntry;
import emu.grasscutter.net.proto.ModifierActionOuterClass.ModifierAction;
import io.netty.util.concurrent.FastThreadLocalThread;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import org.reflections.Reflections;

public final class AbilityManager extends BasePlayerManager {
    public static final ExecutorService eventExecutor;

    static {
        eventExecutor =
                new ThreadPoolExecutor(
                        4,
                        4,
                        60,
                        TimeUnit.SECONDS,
                        new LinkedBlockingDeque<>(1000),
                        FastThreadLocalThread::new,
                        new ThreadPoolExecutor.AbortPolicy());
    }

    private final HealAbilityManager healAbilityManager;
    private final Map<AbilityModifierAction.Type, AbilityActionHandler> actionHandlers;

    @Getter private boolean abilityInvulnerable = false;

    public AbilityManager(Player player) {
        super(player);

        this.healAbilityManager = new HealAbilityManager(player);
        this.actionHandlers = new HashMap<>();

        this.registerHandlers();
    }

    /** Registers all present ability handlers. */
    private void registerHandlers() {
        var reflections = new Reflections("emu.grasscutter.game.ability.actions");
        var handlerClasses = reflections.getSubTypesOf(AbilityActionHandler.class);

        for (var obj : handlerClasses) {
            try {
                if (obj.isAnnotationPresent(AbilityAction.class)) {
                    AbilityModifierAction.Type abilityAction = obj.getAnnotation(AbilityAction.class).value();
                    actionHandlers.put(abilityAction, obj.getDeclaredConstructor().newInstance());
                } else {
                    return;
                }
            } catch (Exception e) {
                Grasscutter.getLogger().error("Unable to register handler.", e);
            }
        }
    }

    public void executeAction(Ability ability, AbilityModifierAction action) {
        var handler = actionHandlers.get(action.type);

        if (handler == null || ability == null) {
            Grasscutter.getLogger()
                    .trace("Could not execute ability action {} at {}", action.type, ability);
            return;
        }

        eventExecutor.submit(
                () -> {
                    if (!handler.execute(ability, action)) {
                        Grasscutter.getLogger()
                                .debug("exec ability action failed {} at {}", action.type, ability);
                    }
                });
    }

    public void onAbilityInvoke(AbilityInvokeEntry invoke) throws Exception {
        this.healAbilityManager.healHandler(invoke);

        if (invoke.getEntityId() == 67109298) {
            Grasscutter.getLogger()
                    .info(
                            invoke.getArgumentType()
                                    + " ("
                                    + invoke.getArgumentTypeValue()
                                    + "): "
                                    + invoke.getEntityId());
        }

        switch (invoke.getArgumentType()) {
            case ABILITY_INVOKE_ARGUMENT_META_OVERRIDE_PARAM -> this.handleOverrideParam(invoke);
            case ABILITY_INVOKE_ARGUMENT_META_REINIT_OVERRIDEMAP -> this.handleReinitOverrideMap(invoke);
            case ABILITY_INVOKE_ARGUMENT_META_MODIFIER_CHANGE -> this.handleModifierChange(invoke);
            case ABILITY_INVOKE_ARGUMENT_MIXIN_COST_STAMINA -> this.handleMixinCostStamina(invoke);
            case ABILITY_INVOKE_ARGUMENT_ACTION_GENERATE_ELEM_BALL -> this.handleGenerateElemBall(invoke);
            case ABILITY_INVOKE_ARGUMENT_META_GLOBAL_FLOAT_VALUE -> this.handleGlobalFloatValue(invoke);
            case ABILITY_INVOKE_ARGUMENT_META_MODIFIER_DURABILITY_CHANGE -> this
                    .handleModifierDurabilityChange(invoke);
            case ABILITY_INVOKE_ARGUMENT_META_ADD_NEW_ABILITY -> this.handleAddNewAbility(invoke);
            case ABILITY_INVOKE_ARGUMENT_NONE -> this.handleInvoke(invoke);
            default -> {}
        }
    }

    /**
     * Invoked when a player starts a skill.
     *
     * @param player The player who started the skill.
     * @param skillId The skill ID.
     * @param casterId The caster ID.
     */
    public void onSkillStart(Player player, int skillId, int casterId) {
        // Check if the player matches this player.
        if (player.getUid() != this.player.getUid()) {
            return;
        }

        // Check if the caster matches the player.
        if (player.getTeamManager().getCurrentAvatarEntity().getId() != casterId) {
            return;
        }

        var skillData = GameData.getAvatarSkillDataMap().get(skillId);
        if (skillData == null) {
            return;
        }

        // Check if the skill is an elemental burst.
        if (skillData.getCostElemVal() <= 0) {
            return;
        }

        // Set the player as invulnerable.
        this.abilityInvulnerable = true;
    }

    /**
     * Invoked when a player ends a skill.
     *
     * @param player The player who started the skill.
     */
    public void onSkillEnd(Player player) {
        // Check if the player matches this player.
        if (player.getUid() != this.player.getUid()) {
            return;
        }

        // Check if the player is invulnerable.
        if (!this.abilityInvulnerable) {
            return;
        }

        // Set the player as not invulnerable.
        this.abilityInvulnerable = false;
    }

    /**
     * Handles an ability invoke.
     *
     * @param invoke The invocation.
     */
    private void handleInvoke(AbilityInvokeEntry invoke) {
        var entity = this.player.getScene().getEntityById(invoke.getEntityId());
        if (entity == null) {
            return;
        }

        var head = invoke.getHead();
        Grasscutter.getLogger()
                .trace(
                        "{} {} {}",
                        head.getInstancedAbilityId(),
                        entity.getInstanceToHash(),
                        head.getLocalId());

        var hash = entity.getInstanceToHash().get(head.getInstancedAbilityId());
        if (hash == null) {
            var abilities = entity.getAbilities().values().toArray(new Ability[0]);

            if (head.getInstancedAbilityId() <= abilities.length) {
                var ability = abilities[head.getInstancedAbilityId() - 1];
                Grasscutter.getLogger().trace("-> {}", ability.getData().localIdToAction);
                var action = ability.getData().localIdToAction.get(head.getLocalId());
                if (action != null) ability.getManager().executeAction(ability, action);
            }

            return;
        }

        var stream =
                entity.getAbilities().values().stream()
                        .filter(
                                a ->
                                        a.getHash() == hash
                                                || a.getData().abilityName
                                                        == entity.getInstanceToName().get(head.getInstancedAbilityId()));
        stream.forEach(
                ability -> {
                    var action = ability.getData().localIdToAction.get(head.getLocalId());
                    if (action != null) ability.getManager().executeAction(ability, action);
                });
    }

    private void handleOverrideParam(AbilityInvokeEntry invoke) throws Exception {
        var entity = this.player.getScene().getEntityById(invoke.getEntityId());

        if (entity == null) {
            return;
        }

        var entry = AbilityScalarValueEntry.parseFrom(invoke.getAbilityData());
        entity.getMetaOverrideMap().put(entry.getKey().getStr(), entry.getFloatValue());
    }

    private void handleReinitOverrideMap(AbilityInvokeEntry invoke) throws Exception {
        var entity = this.player.getScene().getEntityById(invoke.getEntityId());

        if (entity == null) {
            return;
        }

        var map = AbilityMetaReInitOverrideMap.parseFrom(invoke.getAbilityData());
        for (var entry : map.getOverrideMapList()) {
            entity.getMetaOverrideMap().put(entry.getKey().getStr(), entry.getFloatValue());
        }
    }

    private void handleModifierChange(AbilityInvokeEntry invoke) throws Exception {
        // Sanity checks
        var target = this.player.getScene().getEntityById(invoke.getEntityId());
        if (target == null) {
            return;
        }

        var data = AbilityMetaModifierChange.parseFrom(invoke.getAbilityData());
        if (data == null) {
            return;
        }

        // Destroying rocks
        if (target instanceof EntityGadget targetGadget
                && targetGadget.getContent() instanceof GadgetGatherObject gatherObject) {
            if (data.getAction() == ModifierAction.MODIFIER_ACTION_REMOVED) {
                gatherObject.dropItems(this.getPlayer());
                return;
            }
        }

        // Sanity checks
        var head = invoke.getHead();

        if (data.getAction() == ModifierAction.MODIFIER_ACTION_REMOVED) {
            var ability = target.getAbilities().get(data.getParentAbilityName().getStr());
            if (ability != null) {
                var modifier = ability.getModifiers().get(head.getInstancedModifierId());
                if (modifier != null) {
                    modifier.onRemoved();
                    ability.getModifiers().remove(modifier);
                }
            }
        }

        if (data.getAction() == ModifierAction.MODIFIER_ACTION_ADDED) {
            var modifierString = data.getParentAbilityName().getStr();
            var hash = target.getInstanceToHash().get(head.getInstancedAbilityId());
            if (hash == null) return;

            target.getAbilities().values().stream()
                    .filter(
                            a ->
                                    a.getHash() == hash
                                            || a.getData().abilityName
                                                    == target.getInstanceToName().get(head.getInstancedAbilityId()))
                    .forEach(
                            a -> {
                                a.getModifiers().keySet().stream()
                                        .filter(key -> key.compareTo(modifierString) == 0)
                                        .forEach(
                                                key -> {
                                                    a.getModifiers().get(key).setLocalId(head.getInstancedModifierId());
                                                });
                            });
        }

        var sourceEntity = this.player.getScene().getEntityById(data.getApplyEntityId());
        if (sourceEntity == null) {
            return;
        }

        // This is not how it works but we will keep it for now since healing abilities dont work
        // properly anyways
        if (data.getAction() == ModifierAction.MODIFIER_ACTION_ADDED
                && data.getParentAbilityName() != null) {
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
        } else if (data.getAction() == ModifierAction.MODIFIER_ACTION_REMOVED) {
            // Handle remove modifier
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

    private void handleMixinCostStamina(AbilityInvokeEntry invoke)
            throws InvalidProtocolBufferException {
        AbilityMixinCostStamina costStamina =
                AbilityMixinCostStamina.parseFrom((invoke.getAbilityData()));
        this.getPlayer().getStaminaManager().handleMixinCostStamina(costStamina.getIsSwim());
    }

    private void handleGenerateElemBall(AbilityInvokeEntry invoke)
            throws InvalidProtocolBufferException {
        this.player.getEnergyManager().handleGenerateElemBall(invoke);
    }

    /**
     * Handles a float value ability entry.
     *
     * @param invoke The ability invoke entry.
     */
    private void handleGlobalFloatValue(AbilityInvokeEntry invoke)
            throws InvalidProtocolBufferException {
        var entry = AbilityScalarValueEntry.parseFrom(invoke.getAbilityData());
        if (entry.getKey().hasStr()
                && entry.hasFloatValue()
                && entry.getFloatValue() == 2.0f
                && entry.getKey().getStr().equals("_ABILITY_UziExplode_Count")) {
            player.getQuestManager().queueEvent(QuestContent.QUEST_CONTENT_SKILL, 10006);
        }
    }

    private void invokeAction(
            AbilityModifierAction action, GameEntity target, GameEntity sourceEntity) {
        switch (action.type) {
            case HealHP -> {}
            case LoseHP -> {
                if (action.amountByTargetCurrentHPRatio == null) {
                    return;
                }

                float damageAmount = action.amount.get();

                // if (action.amount.isDynamic && action.amount.dynamicKey != null) {
                //     damageAmount =
                // sourceEntity.getMetaOverrideMap().getOrDefault(action.amount.dynamicKey, 0f);
                // }

                if (damageAmount > 0) {
                    target.damage(damageAmount);
                }
            }
            default -> {}
        }
    }

    private void handleModifierDurabilityChange(AbilityInvokeEntry invoke)
            throws InvalidProtocolBufferException {
        var target = this.player.getScene().getEntityById(invoke.getEntityId());
        if (target == null) {
            return;
        }

        var data = AbilityMetaModifierDurabilityChange.parseFrom(invoke.getAbilityData());
        if (data == null) {
            return;
        }

        var head = invoke.getHead();
        var hash = target.getInstanceToHash().get(head.getInstancedAbilityId());
        if (hash == null) return;
        target.getAbilities().values().stream()
                .filter(
                        a ->
                                a.getHash() == hash
                                        || a.getData().abilityName
                                                == target.getInstanceToName().get(head.getInstancedAbilityId()))
                .forEach(
                        a -> {
                            a.getModifiers().values().stream()
                                    .filter(m -> m.getLocalId() == head.getInstancedModifierId())
                                    .forEach(
                                            modifier -> {
                                                modifier.setElementDurability(data.getRemainDurability());
                                            });
                        });
    }

    private void handleAddNewAbility(AbilityInvokeEntry invoke)
            throws InvalidProtocolBufferException {
        var data = AbilityMetaAddAbility.parseFrom(invoke.getAbilityData());
        if (data == null) {
            return;
        }

        var ability = data.getAbility();
        var abilityName = ability.getAbilityName();
        if (abilityName.getHash() != 0)
            Grasscutter.getLogger()
                    .trace("Instancing {} in to {}", abilityName.getHash(), ability.getInstancedAbilityId());
        else
            Grasscutter.getLogger()
                    .trace("Instancing {} in to {}", abilityName.getStr(), ability.getInstancedAbilityId());

        var target = this.player.getScene().getEntityById(invoke.getEntityId());
        if (target == null) {
            return;
        }

        target.getInstanceToHash().put(ability.getInstancedAbilityId(), abilityName.getHash());
        target.getInstanceToName().put(ability.getInstancedAbilityId(), abilityName.getStr());
    }

    public void addAbilityToEntity(GameEntity entity, String name) {
        var data = GameData.getAbilityData(name);
        if (data != null) addAbilityToEntity(entity, data, name);
    }

    public void addAbilityToEntity(GameEntity entity, AbilityData abilityData, String id) {
        var ability = new Ability(abilityData, entity);
        entity.getAbilities().put(id, ability);

        ability.onAdded();
    }
}
