package emu.grasscutter.game.entity;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.config.ConfigEntityMonster;
import emu.grasscutter.data.common.PropGrowCurve;
import emu.grasscutter.data.excels.EnvAnimalGatherConfigData;
import emu.grasscutter.data.excels.monster.*;
import emu.grasscutter.game.dungeons.enums.DungeonPassConditionType;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.*;
import emu.grasscutter.game.quest.enums.QuestContent;
import emu.grasscutter.game.world.*;
import emu.grasscutter.net.proto.AbilitySyncStateInfoOuterClass.AbilitySyncStateInfo;
import emu.grasscutter.net.proto.AnimatorParameterValueInfoPairOuterClass.AnimatorParameterValueInfoPair;
import emu.grasscutter.net.proto.EntityAuthorityInfoOuterClass.EntityAuthorityInfo;
import emu.grasscutter.net.proto.EntityClientDataOuterClass.EntityClientData;
import emu.grasscutter.net.proto.EntityRendererChangedInfoOuterClass.EntityRendererChangedInfo;
import emu.grasscutter.net.proto.GadgetInteractReqOuterClass.GadgetInteractReq;
import emu.grasscutter.net.proto.MonsterBornTypeOuterClass.MonsterBornType;
import emu.grasscutter.net.proto.PropPairOuterClass.PropPair;
import emu.grasscutter.net.proto.ProtEntityTypeOuterClass.ProtEntityType;
import emu.grasscutter.net.proto.SceneEntityAiInfoOuterClass.SceneEntityAiInfo;
import emu.grasscutter.net.proto.SceneEntityInfoOuterClass.SceneEntityInfo;
import emu.grasscutter.net.proto.SceneMonsterInfoOuterClass.SceneMonsterInfo;
import emu.grasscutter.net.proto.SceneWeaponInfoOuterClass.SceneWeaponInfo;
import emu.grasscutter.scripts.constants.EventType;
import emu.grasscutter.scripts.data.*;
import emu.grasscutter.server.event.entity.EntityDamageEvent;
import emu.grasscutter.utils.helpers.ProtoHelper;
import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;
import lombok.*;

import javax.annotation.Nullable;
import java.util.*;

import static emu.grasscutter.scripts.constants.EventType.EVENT_SPECIFIC_MONSTER_HP_CHANGE;

public class EntityMonster extends GameEntity {
    @Getter(onMethod_ = @Override)
    private final Int2FloatOpenHashMap fightProperties;

    @Getter(onMethod_ = @Override)
    private final Position position;
    @Getter(onMethod_ = @Override)
    private final Position rotation;
    @Getter private final MonsterData monsterData;
    @Getter private final ConfigEntityMonster configEntityMonster;
    @Getter private final Position bornPos;
    @Getter private final int level;
    @Getter private EntityWeapon weaponEntity;
    @Getter @Setter private int poseId;
    @Getter @Setter private int aiId = -1;

    @Getter private List<Player> playerOnBattle;
    @Nullable @Getter @Setter private SceneMonster metaMonster;

    public EntityMonster(Scene scene, MonsterData monsterData, Position pos, Position rot, int level) {
        super(scene);

        this.id = this.getWorld().getNextEntityId(EntityIdType.MONSTER);
        this.monsterData = monsterData;
        this.fightProperties = new Int2FloatOpenHashMap();
        this.position = new Position(pos);
        this.rotation = new Position(rot);
        this.bornPos = this.getPosition().clone();
        this.level = level;
        this.playerOnBattle = new ArrayList<>();

        if (GameData.getMonsterMappingMap().containsKey(this.getMonsterId())) {
            this.configEntityMonster = GameData.getMonsterConfigData().get(
                    GameData.getMonsterMappingMap().get(this.getMonsterId()).getMonsterJson());
        } else {
            this.configEntityMonster = null;
        }

        // Monster weapon
        if (getMonsterWeaponId() > 0) {
            this.weaponEntity = new EntityWeapon(scene, getMonsterWeaponId());
            scene.getWeaponEntities().put(this.weaponEntity.getId(), this.weaponEntity);
            //this.weaponEntityId = getWorld().getNextEntityId(EntityIdType.WEAPON);
        }

        this.recalcStats();
        this.initAbilities();
    }

    private void addConfigAbility(String name) {
        var data = GameData.getAbilityData(name);
        if (data != null) {
            this.getWorld().getHost()
                    .getAbilityManager()
                    .addAbilityToEntity(this, data);
        }
    }

    @Override
    public void initAbilities() {
        // Affix abilities
        var optionalGroup = this.getScene().getLoadedGroups().stream()
                .filter(g -> g.id == this.getGroupId())
                .findAny();
        List<Integer> affixes = null;
        if (optionalGroup.isPresent()) {
            var group = optionalGroup.get();

            var monster = group.monsters.get(getConfigId());
            if (monster != null) affixes = monster.affix;
        }

        if (monsterData != null) {
            // TODO: Research if group affixes goes first
            if (affixes == null) affixes = monsterData.getAffix();
            else affixes.addAll(monsterData.getAffix());
        }

        if (affixes != null) {
            for (var affixId : affixes) {
                var affix = GameData.getMonsterAffixDataMap().get(affixId.intValue());
                if (!affix.isPreAdd()) continue;

                //Add the ability
                for (var name : affix.getAbilityName()) {
                    this.addConfigAbility(name);
                }
            }
        }

        // TODO: Research if any monster is non humanoid
        for(var ability : GameData.getConfigGlobalCombat()
                .getDefaultAbilities()
                .getNonHumanoidMoveAbilities()) {
            this.addConfigAbility(ability);
        }

        if (this.configEntityMonster != null &&
                this.configEntityMonster.getAbilities() != null) {
            for (var configAbilityData : this.configEntityMonster.getAbilities()) {
                this.addConfigAbility(configAbilityData.abilityName);
            }
        }

        if (optionalGroup.isPresent()) {
            var group = optionalGroup.get();
            var monster = group.monsters.get(getConfigId());
            if (monster != null && monster.isElite) {
                this.addConfigAbility(GameData.getConfigGlobalCombat()
                        .getDefaultAbilities()
                        .getMonterEliteAbilityName());
            }
        }

        if (affixes != null) {
            for (var affixId : affixes) {
                var affix = GameData.getMonsterAffixDataMap().get(affixId.intValue());
                if (affix.isPreAdd()) continue;

                //Add the ability
                for(var name : affix.getAbilityName()) {
                    this.addConfigAbility(name);
                }
            }
        }

        var levelEntityConfig = getScene().getSceneData().getLevelEntityConfig();
        var config = GameData.getConfigLevelEntityDataMap().get(levelEntityConfig);
        if (config == null){
            return;
        }

        if (config.getMonsterAbilities() != null) {
            for (var monsterAbility : config.getMonsterAbilities()) {
                addConfigAbility(monsterAbility.abilityName);
            }
        }
    }

    @Override
    public int getEntityTypeId() {
        return getMonsterId();
    }

    public int getMonsterWeaponId() {
        return this.getMonsterData().getWeaponId();
    }

    private int getMonsterId() {
        return this.getMonsterData().getId();
    }

    @Override
    public boolean isAlive() {
        return this.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP) > 0f;
    }

    @Override
    public void onInteract(Player player, GadgetInteractReq interactReq) {
        EnvAnimalGatherConfigData gatherData = GameData.getEnvAnimalGatherConfigDataMap().get(this.getMonsterData().getId());

        if (gatherData == null) {
            return;
        }

        player.getInventory().addItem(gatherData.getGatherItem(), ActionReason.SubfieldDrop);

        this.getScene().killEntity(this);
    }

    @Override
    public void onCreate() {
        // Lua event
        getScene().getScriptManager().callEvent(new ScriptArgs(this.getGroupId(), EventType.EVENT_ANY_MONSTER_LIVE, this.getConfigId()));
    }

    @Override
    public void damage(float amount, int killerId, ElementType attackType) {
        // Get HP before damage.
        float hpBeforeDamage = this.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP);

        // Apply damage.
        super.damage(amount, killerId, attackType);

        // Get HP after damage.
        float hpAfterDamage = this.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP);

        // Invoke energy drop logic.
        for (Player player : this.getScene().getPlayers()) {
            player.getEnergyManager().handleMonsterEnergyDrop(this, hpBeforeDamage, hpAfterDamage);
        }
    }

    @Override
    public void runLuaCallbacks(EntityDamageEvent event) {
        super.runLuaCallbacks(event);
        getScene().getScriptManager().callEvent(new ScriptArgs(this.getGroupId(), EVENT_SPECIFIC_MONSTER_HP_CHANGE, getConfigId(), monsterData.getId())
            .setSourceEntityId(getId())
            .setParam3((int) this.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP))
            .setEventSource(getConfigId()));
    }

    @Override
    public void onDeath(int killerId) {
        super.onDeath(killerId); // Invoke super class's onDeath() method.
        var scene = this.getScene();
        var challenge = Optional.ofNullable(scene.getChallenge());
        var scriptManager = scene.getScriptManager();

        Optional.ofNullable(this.getSpawnEntry()).ifPresent(scene.getDeadSpawnedEntities()::add);

        // first set the challenge data
        challenge.ifPresent(c -> c.onMonsterDeath(this));

        if (scriptManager.isInit() && this.getGroupId() > 0) {
            Optional.ofNullable(scriptManager.getScriptMonsterSpawnService()).ifPresent(s -> s.onMonsterDead(this));

            // prevent spawn monster after success
            /*if (challenge.map(c -> c.inProgress()).orElse(true)) {
                scriptManager.callEvent(new ScriptArgs(EventType.EVENT_ANY_MONSTER_DIE, this.getConfigId()).setGroupId(this.getGroupId()));
            } else if (getScene().getChallenge() == null) {
            }*/
            scriptManager.callEvent(new ScriptArgs(this.getGroupId(), EventType.EVENT_ANY_MONSTER_DIE, this.getConfigId()));
        }
        // Battle Pass trigger
        scene.getPlayers().forEach(p -> p.getBattlePassManager().triggerMission(WatcherTriggerType.TRIGGER_MONSTER_DIE, this.getMonsterId(), 1));

        scene.getPlayers().forEach(p -> p.getQuestManager().queueEvent(QuestContent.QUEST_CONTENT_MONSTER_DIE, this.getMonsterId()));
        scene.getPlayers().forEach(p -> p.getQuestManager().queueEvent(QuestContent.QUEST_CONTENT_KILL_MONSTER, this.getMonsterId()));
        scene.getPlayers().forEach(p -> p.getQuestManager().queueEvent(QuestContent.QUEST_CONTENT_CLEAR_GROUP_MONSTER, this.getGroupId()));

        SceneGroupInstance groupInstance = scene.getScriptManager().getGroupInstanceById(this.getGroupId());
        if(groupInstance != null && metaMonster != null)
            groupInstance.getDeadEntities().add(metaMonster.config_id);

        scene.triggerDungeonEvent(DungeonPassConditionType.DUNGEON_COND_KILL_GROUP_MONSTER, this.getGroupId());
        scene.triggerDungeonEvent(DungeonPassConditionType.DUNGEON_COND_KILL_TYPE_MONSTER, this.getMonsterData().getType().getValue());
        scene.triggerDungeonEvent(DungeonPassConditionType.DUNGEON_COND_KILL_MONSTER, this.getMonsterId());
    }

    public void recalcStats() {
        // Monster data
        MonsterData data = this.getMonsterData();

        // Get hp percent, set to 100% if none
        float hpPercent = this.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP) <= 0 ? 1f : this.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP) / this.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP);

        // Clear properties
        this.getFightProperties().clear();

        // Base stats
        MonsterData.definedFightProperties.forEach(prop -> this.setFightProperty(prop, data.getFightProperty(prop)));

        // Level curve
        MonsterCurveData curve = GameData.getMonsterCurveDataMap().get(this.getLevel());
        if (curve != null) {
            for (PropGrowCurve growCurve : data.getPropGrowCurves()) {
                FightProperty prop = FightProperty.getPropByName(growCurve.getType());
                this.setFightProperty(prop, this.getFightProperty(prop) * curve.getMultByProp(growCurve.getGrowCurve()));
            }
        }

        // Set % stats
        FightProperty.forEachCompoundProperty(c -> this.setFightProperty(c.getResult(),
            this.getFightProperty(c.getFlat()) + (this.getFightProperty(c.getBase()) * (1f + this.getFightProperty(c.getPercent())))));

        // Set current hp
        this.setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, this.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP) * hpPercent);
    }

    @Override
    public SceneEntityInfo toProto() {
        var data = this.getMonsterData();

        var authority = EntityAuthorityInfo.newBuilder()
            .setAbilityInfo(AbilitySyncStateInfo.newBuilder())
            .setRendererChangedInfo(EntityRendererChangedInfo.newBuilder())
            .setAiInfo(SceneEntityAiInfo.newBuilder()
                .setIsAiOpen(true)
                .setBornPos(this.getBornPos().toProto()))
            .setBornPos(this.getBornPos().toProto())
            .build();

        var entityInfo = SceneEntityInfo.newBuilder()
            .setEntityId(this.getId())
            .setEntityType(ProtEntityType.PROT_ENTITY_TYPE_MONSTER)
            .setMotionInfo(this.getMotionInfo())
            .addAnimatorParaList(AnimatorParameterValueInfoPair.newBuilder())
            .setEntityClientData(EntityClientData.newBuilder())
            .setEntityAuthorityInfo(authority)
            .setLifeState(this.getLifeState().getValue());

        this.addAllFightPropsToEntityInfo(entityInfo);

        entityInfo.addPropList(PropPair.newBuilder()
            .setType(PlayerProperty.PROP_LEVEL.getId())
            .setPropValue(ProtoHelper.newPropValue(PlayerProperty.PROP_LEVEL, this.getLevel()))
            .build());

        var monsterInfo = SceneMonsterInfo.newBuilder()
            .setMonsterId(getMonsterId())
            .setGroupId(this.getGroupId())
            .setConfigId(this.getConfigId())
            .addAllAffixList(data.getAffix())
            .setAuthorityPeerId(this.getWorld().getHostPeerId())
            .setPoseId(this.getPoseId())
            .setBlockId(this.getScene().getId())
            .setBornType(MonsterBornType.MONSTER_BORN_TYPE_DEFAULT);

        if (this.metaMonster != null) {
            if (this.metaMonster.special_name_id != 0) {
                monsterInfo.setTitleId(this.metaMonster.title_id)
                    .setSpecialNameId(this.metaMonster.special_name_id);
            } else if (data.getDescribeData() != null) {
                monsterInfo.setTitleId(data.getDescribeData().getTitleId())
                    .setSpecialNameId(data.getSpecialNameId());
            }
        }

        if (this.getMonsterWeaponId() > 0) {
            SceneWeaponInfo weaponInfo = SceneWeaponInfo.newBuilder()
                .setEntityId(this.getWeaponEntity() != null ? this.getWeaponEntity().getId() : 0)
                .setGadgetId(this.getMonsterWeaponId())
                .setAbilityInfo(AbilitySyncStateInfo.newBuilder())
                .build();

            monsterInfo.addWeaponList(weaponInfo);
        }
        if (this.aiId != -1) {
            monsterInfo.setAiConfigId(aiId);
        }

        entityInfo.setMonster(monsterInfo);

        return entityInfo.build();
    }
}
