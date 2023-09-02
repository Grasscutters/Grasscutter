package emu.grasscutter.game.ability;

import com.google.protobuf.*;
import emu.grasscutter.*;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.*;
import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import emu.grasscutter.game.ability.actions.*;
import emu.grasscutter.game.ability.mixins.*;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.player.*;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.net.proto.AbilityInvokeEntryOuterClass.AbilityInvokeEntry;
import emu.grasscutter.net.proto.AbilityMetaAddAbilityOuterClass.AbilityMetaAddAbility;
import emu.grasscutter.net.proto.AbilityMetaModifierChangeOuterClass.AbilityMetaModifierChange;
import emu.grasscutter.net.proto.AbilityMetaReInitOverrideMapOuterClass.AbilityMetaReInitOverrideMap;
import emu.grasscutter.net.proto.AbilityMetaSetKilledStateOuterClass.AbilityMetaSetKilledState;
import emu.grasscutter.net.proto.AbilityScalarTypeOuterClass.AbilityScalarType;
import emu.grasscutter.net.proto.AbilityScalarValueEntryOuterClass.AbilityScalarValueEntry;
import emu.grasscutter.net.proto.ModifierActionOuterClass.ModifierAction;
import emu.grasscutter.server.event.player.PlayerUseSkillEvent;
import io.netty.util.concurrent.FastThreadLocalThread;
import java.util.HashMap;
import java.util.concurrent.*;
import lombok.Getter;

public final class AbilityManager extends BasePlayerManager {
    private static final HashMap<AbilityModifierAction.Type, AbilityActionHandler> actionHandlers =
            new HashMap<>();
    private static final HashMap<AbilityMixinData.Type, AbilityMixinHandler> mixinHandlers =
            new HashMap<>();

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

        registerHandlers();
    }

    @Getter private boolean abilityInvulnerable = false;

    public AbilityManager(Player player) {
        super(player);
    }

    public static void registerHandlers() {
        var handlerClassesAction = Grasscutter.reflector.getSubTypesOf(AbilityActionHandler.class);

        for (var obj : handlerClassesAction) {
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

        var handlerClassesMixin = Grasscutter.reflector.getSubTypesOf(AbilityMixinHandler.class);
        for (var obj : handlerClassesMixin) {
            try {
                if (obj.isAnnotationPresent(AbilityAction.class)) {
                    AbilityMixinData.Type abilityMixin = obj.getAnnotation(AbilityMixin.class).value();
                    mixinHandlers.put(abilityMixin, obj.getDeclaredConstructor().newInstance());
                } else {
                    return;
                }
            } catch (Exception e) {
                Grasscutter.getLogger().error("Unable to register handler.", e);
            }
        }
    }

    public void executeAction(
            Ability ability, AbilityModifierAction action, ByteString abilityData, GameEntity target) {
        var handler = actionHandlers.get(action.type);
        if (handler == null || ability == null) {
            if (DebugConstants.LOG_MISSING_ABILITY_HANDLERS) {
                Grasscutter.getLogger()
                        .debug("Missing ability action handler for {} (invoker: {}).", action.type, ability);
            }

            return;
        }

        eventExecutor.submit(
                () -> {
                    if (!handler.execute(ability, action, abilityData, target)) {
                        Grasscutter.getLogger()
                                .debug("Ability execute action failed for {} at {}.", action.type, ability);
                    }
                });
    }

    public void executeMixin(Ability ability, AbilityMixinData mixinData, ByteString abilityData) {
        var handler = mixinHandlers.get(mixinData.type);
        if (handler == null || ability == null) {
            Grasscutter.getLogger()
                    .trace("Could not execute ability mixin {} at {}", mixinData.type, ability);
            return;
        }

        eventExecutor.submit(
                () -> {
                    if (!handler.execute(ability, mixinData, abilityData)) {
                        Grasscutter.getLogger()
                                .error("Ability execute action failed for {} at {}.", mixinData.type, ability);
                    }
                });
    }

    public void onAbilityInvoke(AbilityInvokeEntry invoke) throws Exception {
        Grasscutter.getLogger()
                .trace(
                        "Ability invoke: "
                                + invoke
                                + " "
                                + invoke.getArgumentType()
                                + " ("
                                + invoke.getArgumentTypeValue()
                                + "): "
                                + this.player.getScene().getEntityById(invoke.getEntityId()));
        var entity = this.player.getScene().getEntityById(invoke.getEntityId());
        if (entity != null) {
            Grasscutter.getLogger()
                    .trace(
                            "Entity {} has a group of {} and a config of {}.",
                            invoke.getEntityId(),
                            entity.getGroupId(),
                            entity.getConfigId());

            Grasscutter.getLogger()
                    .trace(
                            "Invoke type of {} ({}) has entity {}.",
                            invoke.getArgumentType(),
                            invoke.getArgumentTypeValue(),
                            entity.getId());
        } else if (DebugConstants.LOG_ABILITIES) {
            Grasscutter.getLogger()
                    .debug(
                            "Invoke type of {} ({}) has no entity. (referring to {})",
                            invoke.getArgumentType(),
                            invoke.getArgumentTypeValue(),
                            invoke.getEntityId());
        }

        if (invoke.getHead().getTargetId() != 0) {
            Grasscutter.getLogger()
                    .trace("Target: " + this.player.getScene().getEntityById(invoke.getHead().getTargetId()));
        }
        if (invoke.getHead().getLocalId() != 0) {
            this.handleServerInvoke(invoke);
            return;
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
            case ABILITY_INVOKE_ARGUMENT_META_SET_KILLED_SETATE -> this.handleKillState(invoke);
            default -> {
                if (DebugConstants.LOG_MISSING_ABILITIES) {
                    Grasscutter.getLogger()
                            .trace("Missing invoke handler for ability {}.", invoke.getArgumentType().name());
                }
            }
        }
    }

    public void handleServerInvoke(AbilityInvokeEntry invoke) {
        var head = invoke.getHead();

        var entity = this.player.getScene().getEntityById(invoke.getEntityId());
        if (entity == null) {
            Grasscutter.getLogger().trace("Entity not found: {}", invoke.getEntityId());
            return;
        }

        var target = this.player.getScene().getEntityById(head.getTargetId());
        if (target == null) target = entity;

        Ability ability = null;

        // Seems that target is used, but need to be sure, TODO: Research

        // Find ability or modifier's ability
        if (head.getInstancedModifierId() != 0
                && entity.getInstancedModifiers().containsKey(head.getInstancedModifierId())) {
            ability = entity.getInstancedModifiers().get(head.getInstancedModifierId()).getAbility();
        }

        if (ability == null
                && head.getInstancedAbilityId() != 0
                && (head.getInstancedAbilityId() - 1) < entity.getInstancedAbilities().size()) {
            ability = entity.getInstancedAbilities().get(head.getInstancedAbilityId() - 1);
        }

        if (ability == null) {
            Grasscutter.getLogger()
                    .trace(
                            "Ability not found: ability {} modifier {}",
                            head.getInstancedAbilityId(),
                            head.getInstancedModifierId());
            return;
        }

        // Time to reach the handlers
        var action = ability.getData().localIdToAction.get(head.getLocalId());
        if (action != null) {
            // Executing action
            this.executeAction(ability, action, invoke.getAbilityData(), target);
            return;
        } else {
            var mixin = ability.getData().localIdToMixin.get(head.getLocalId());

            if (mixin != null) {
                executeMixin(ability, mixin, invoke.getAbilityData());

                return;
            }
        }

        Grasscutter.getLogger()
                .trace(
                        "Action or mixin not found: local_id {} ability {} actions to ids {}",
                        head.getLocalId(),
                        ability.getData().abilityName,
                        ability.getData().localIdToAction.toString());
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
        var currentAvatar = player.getTeamManager().getCurrentAvatarEntity();
        if (currentAvatar == null || currentAvatar.getId() != casterId) {
            return;
        }

        var skillData = GameData.getAvatarSkillDataMap().get(skillId);
        if (skillData == null) {
            return;
        }

        // Invoke PlayerUseSkillEvent.
        var event = new PlayerUseSkillEvent(player, skillData, currentAvatar.getAvatar());
        if (!event.call()) return;

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

    private void setAbilityOverrideValue(Ability ability, AbilityScalarValueEntry valueChange) {
        if (valueChange.getValueType() != AbilityScalarType.ABILITY_SCALAR_TYPE_FLOAT) {
            Grasscutter.getLogger().trace("Scalar type not supported: {}", valueChange.getValueType());

            return;
        }

        if (!valueChange.getKey().hasStr()) {
            Grasscutter.getLogger().trace("TODO: Calculate all the ability value hashes");

            return;
        }

        ability.getAbilitySpecials().put(valueChange.getKey().getStr(), valueChange.getFloatValue());
        Grasscutter.getLogger()
                .trace(
                        "Ability {} changed {} to {}",
                        ability.getData().abilityName,
                        valueChange.getKey().getStr(),
                        valueChange.getFloatValue());
    }

    private void handleOverrideParam(AbilityInvokeEntry invoke) throws Exception {
        var entity = this.player.getScene().getEntityById(invoke.getEntityId());
        var head = invoke.getHead();

        if (entity == null) {
            Grasscutter.getLogger().trace("Entity not found: {}", invoke.getEntityId());
            return;
        }

        var instancedAbilityIndex = head.getInstancedAbilityId() - 1;
        if (instancedAbilityIndex >= entity.getInstancedAbilities().size()) {
            Grasscutter.getLogger().trace("Ability not found {}", head.getInstancedAbilityId());
            return;
        }

        var valueChange = AbilityScalarValueEntry.parseFrom(invoke.getAbilityData());

        var ability = entity.getInstancedAbilities().get(instancedAbilityIndex);
        setAbilityOverrideValue(ability, valueChange);
    }

    private void handleReinitOverrideMap(AbilityInvokeEntry invoke) throws Exception {
        var entity = this.player.getScene().getEntityById(invoke.getEntityId());
        var head = invoke.getHead();

        if (entity == null) {
            Grasscutter.getLogger().trace("Entity not found: {}", invoke.getEntityId());
            return;
        }

        var instancedAbilityIndex = head.getInstancedAbilityId() - 1;
        if (instancedAbilityIndex >= entity.getInstancedAbilities().size()) {
            Grasscutter.getLogger().trace("Ability not found {}", head.getInstancedAbilityId());
            return;
        }

        var ability = entity.getInstancedAbilities().get(instancedAbilityIndex);
        var valueChanges = AbilityMetaReInitOverrideMap.parseFrom(invoke.getAbilityData());
        for (var variableChange : valueChanges.getOverrideMapList()) {
            setAbilityOverrideValue(ability, variableChange);
        }
    }

    private void handleModifierChange(AbilityInvokeEntry invoke) throws Exception {
        // TODO:
        var modChange = AbilityMetaModifierChange.parseFrom(invoke.getAbilityData());
        var head = invoke.getHead();

        if (head.getInstancedAbilityId() == 0 || head.getInstancedModifierId() > 2000)
            return; // Error: TODO: display error

        if (head.getIsServerbuffModifier()) {
            // TODO
            Grasscutter.getLogger().trace("TODO: Handle serverbuff modifier");
            return;
        }

        var entity = this.player.getScene().getEntityById(invoke.getEntityId());
        if (entity == null) {
            if (DebugConstants.LOG_ABILITIES) {
                Grasscutter.getLogger().debug("Entity not found: {}", invoke.getEntityId());
            }

            return;
        }

        if (modChange.getAction() == ModifierAction.MODIFIER_ACTION_ADDED) {
            AbilityData instancedAbilityData = null;
            Ability instancedAbility = null;

            if (head.getTargetId() != 0) {
                // Get ability from target entity
                var targetEntity = this.player.getScene().getEntityById(head.getTargetId());
                if (targetEntity != null) {
                    if ((head.getInstancedAbilityId() - 1) < targetEntity.getInstancedAbilities().size()) {
                        instancedAbility =
                                targetEntity.getInstancedAbilities().get(head.getInstancedAbilityId() - 1);
                        if (instancedAbility != null) instancedAbilityData = instancedAbility.getData();
                    }
                }
            }

            if (instancedAbilityData == null) {
                // search on entity base id
                if ((head.getInstancedAbilityId() - 1) < entity.getInstancedAbilities().size()) {
                    instancedAbility = entity.getInstancedAbilities().get(head.getInstancedAbilityId() - 1);
                    if (instancedAbility != null) instancedAbilityData = instancedAbility.getData();
                }
            }

            if (instancedAbilityData == null) {
                // Search for the parent ability

                // TODO: Research about hash
                instancedAbilityData = GameData.getAbilityData(modChange.getParentAbilityName().getStr());
            }

            if (instancedAbilityData == null) {
                Grasscutter.getLogger().trace("No ability found");
                return; // TODO: Display error message
            }

            var modifierArray = instancedAbilityData.modifiers.values().toArray();
            if (modChange.getModifierLocalId() >= modifierArray.length) {
                Grasscutter.getLogger()
                        .trace("Modifier local id {} not found", modChange.getModifierLocalId());
                return;
            }

            var modifierData = (AbilityModifier) modifierArray[modChange.getModifierLocalId()];
            if (entity.getInstancedModifiers().containsKey(head.getInstancedModifierId())) {
                Grasscutter.getLogger()
                        .trace(
                                "Replacing entity {} modifier id {} with ability {} modifier {}",
                                invoke.getEntityId(),
                                head.getInstancedModifierId(),
                                instancedAbilityData.abilityName,
                                modifierData);
            } else {
                Grasscutter.getLogger()
                        .trace(
                                "Adding entity {} modifier id {} with ability {} modifier {}",
                                invoke.getEntityId(),
                                head.getInstancedModifierId(),
                                instancedAbilityData.abilityName,
                                modifierData);
            }

            AbilityModifierController modifier =
                    new AbilityModifierController(instancedAbility, instancedAbilityData, modifierData);

            entity.getInstancedModifiers().put(head.getInstancedModifierId(), modifier);

            // TODO: Add all the ability modifier property change
        } else if (modChange.getAction() == ModifierAction.MODIFIER_ACTION_REMOVED) {
            Grasscutter.getLogger()
                    .trace(
                            "Removed on entity {} modifier id {}: {}",
                            invoke.getEntityId(),
                            head.getInstancedModifierId(),
                            entity.getInstancedModifiers().get(head.getInstancedModifierId()));

            // TODO: Add debug log

            entity.getInstancedModifiers().remove(head.getInstancedModifierId());
        } else {
            // TODO: Display error message
            Grasscutter.getLogger().debug("Unknown action");
        }
    }

    private void handleMixinCostStamina(AbilityInvokeEntry invoke)
            throws InvalidProtocolBufferException {}

    private void handleGenerateElemBall(AbilityInvokeEntry invoke)
            throws InvalidProtocolBufferException {}

    private void handleGlobalFloatValue(AbilityInvokeEntry invoke)
            throws InvalidProtocolBufferException {
        var entity = this.player.getScene().getEntityById(invoke.getEntityId());
        if (entity == null) return;

        var entry = AbilityScalarValueEntry.parseFrom(invoke.getAbilityData());
        if (entry == null || !entry.hasFloatValue()) return;

        String key = null;
        if (entry.getKey().hasStr()) key = entry.getKey().getStr();
        else if (entry.getKey().hasHash())
            key = GameData.getAbilityHashes().get(entry.getKey().getHash());

        if (key == null) return;

        if (key.startsWith("SGV_")) return; // Server does not allow to change this variables I think
        switch (entry.getValueType().getNumber()) {
            case AbilityScalarType.ABILITY_SCALAR_TYPE_FLOAT_VALUE -> {
                if (!Float.isNaN(entry.getFloatValue()))
                    entity.getGlobalAbilityValues().put(key, entry.getFloatValue());
            }
            case AbilityScalarType.ABILITY_SCALAR_TYPE_UINT_VALUE -> entity
                    .getGlobalAbilityValues()
                    .put(key, (float) entry.getUintValue());
            default -> {
                return;
            }
        }

        entity.onAbilityValueUpdate();
    }

    private void invokeAction(
            AbilityModifierAction action, GameEntity target, GameEntity sourceEntity) {}

    private void handleModifierDurabilityChange(AbilityInvokeEntry invoke)
            throws InvalidProtocolBufferException {}

    private void handleAddNewAbility(AbilityInvokeEntry invoke)
            throws InvalidProtocolBufferException {
        var entity = this.player.getScene().getEntityById(invoke.getEntityId());

        if (entity == null) {
            if (DebugConstants.LOG_ABILITIES)
                Grasscutter.getLogger().debug("Entity not found: {}", invoke.getEntityId());
            return;
        }

        var addAbility = AbilityMetaAddAbility.parseFrom(invoke.getAbilityData());
        var abilityName = Ability.getAbilityName(addAbility.getAbility().getAbilityName());
        var ability = GameData.getAbilityData(abilityName);
        if (ability == null) {
            if (DebugConstants.LOG_MISSING_ABILITIES)
                Grasscutter.getLogger().debug("Ability not found: {}", abilityName);
            return;
        }

        entity.getInstancedAbilities().add(new Ability(ability, entity, player));
        if (DebugConstants.LOG_ABILITIES) {
            Grasscutter.getLogger()
                    .debug(
                            "Ability added to entity {} at index {}.",
                            entity.getId(),
                            entity.getInstancedAbilities().size());
        }
    }

    private void handleKillState(AbilityInvokeEntry invoke) throws InvalidProtocolBufferException {
        var scene = this.getPlayer().getScene();
        var entity = scene.getEntityById(invoke.getEntityId());
        if (entity == null) {
            Grasscutter.getLogger()
                    .trace("Entity of ID {} was not found in the scene.", invoke.getEntityId());
            return;
        }

        var killState = AbilityMetaSetKilledState.parseFrom(invoke.getAbilityData());
        if (killState.getKilled()) {
            scene.killEntity(entity);
        } else if (!entity.isAlive()) {
            entity.setFightProperty(
                    FightProperty.FIGHT_PROP_CUR_HP,
                    entity.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP));
        }
    }

    public void addAbilityToEntity(GameEntity entity, String name) {
        AbilityData data = GameData.getAbilityData(name);
        if (data != null) addAbilityToEntity(entity, data);
    }

    public void addAbilityToEntity(GameEntity entity, AbilityData abilityData) {
        var ability = new Ability(abilityData, entity, this.player);
        entity.getInstancedAbilities().add(ability); // This is in order
    }
}
