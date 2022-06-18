package emu.grasscutter.game.entity;

import emu.grasscutter.GameConstants;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.AvatarData;
import emu.grasscutter.data.excels.AvatarSkillDepotData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.inventory.EquipType;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.EntityIdType;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.proto.AbilityControlBlockOuterClass.AbilityControlBlock;
import emu.grasscutter.net.proto.AbilityEmbryoOuterClass.AbilityEmbryo;
import emu.grasscutter.net.proto.AbilitySyncStateInfoOuterClass.AbilitySyncStateInfo;
import emu.grasscutter.net.proto.AnimatorParameterValueInfoPairOuterClass.AnimatorParameterValueInfoPair;
import emu.grasscutter.net.proto.ChangeEnergyReasonOuterClass.ChangeEnergyReason;
import emu.grasscutter.net.proto.ChangeHpReasonOuterClass.ChangeHpReason;
import emu.grasscutter.net.proto.EntityAuthorityInfoOuterClass.EntityAuthorityInfo;
import emu.grasscutter.net.proto.EntityClientDataOuterClass.EntityClientData;
import emu.grasscutter.net.proto.EntityRendererChangedInfoOuterClass.EntityRendererChangedInfo;
import emu.grasscutter.net.proto.FightPropPairOuterClass.FightPropPair;
import emu.grasscutter.net.proto.PlayerDieTypeOuterClass.PlayerDieType;
import emu.grasscutter.net.proto.PropChangeReasonOuterClass.PropChangeReason;
import emu.grasscutter.net.proto.PropPairOuterClass.PropPair;
import emu.grasscutter.net.proto.ProtEntityTypeOuterClass.ProtEntityType;
import emu.grasscutter.net.proto.SceneAvatarInfoOuterClass.SceneAvatarInfo;
import emu.grasscutter.net.proto.SceneEntityAiInfoOuterClass.SceneEntityAiInfo;
import emu.grasscutter.net.proto.SceneEntityInfoOuterClass.SceneEntityInfo;
import emu.grasscutter.net.proto.VectorOuterClass.Vector;
import emu.grasscutter.server.packet.send.PacketAvatarFightPropUpdateNotify;
import emu.grasscutter.server.packet.send.PacketEntityFightPropChangeReasonNotify;
import emu.grasscutter.server.packet.send.PacketEntityFightPropUpdateNotify;
import emu.grasscutter.utils.Position;
import emu.grasscutter.utils.ProtoHelper;
import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.Int2FloatMap;
import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;

public class EntityAvatar extends GameEntity {
    private final Avatar avatar;

    private PlayerDieType killedType;
    private int killedBy;

    public EntityAvatar(Scene scene, Avatar avatar) {
        super(scene);
        this.avatar = avatar;
        this.avatar.setCurrentEnergy();
        this.id = this.getScene().getWorld().getNextEntityId(EntityIdType.AVATAR);

        GameItem weapon = this.getAvatar().getWeapon();
        if (weapon != null) {
            weapon.setWeaponEntityId(this.getScene().getWorld().getNextEntityId(EntityIdType.WEAPON));
        }
    }

    public EntityAvatar(Avatar avatar) {
        super(null);
        this.avatar = avatar;
        this.avatar.setCurrentEnergy();
    }

    public Player getPlayer() {
        return this.avatar.getPlayer();
    }

    @Override
    public Position getPosition() {
        return this.getPlayer().getPos();
    }

    @Override
    public Position getRotation() {
        return this.getPlayer().getRotation();
    }

    public Avatar getAvatar() {
        return this.avatar;
    }

    public int getKilledBy() {
        return this.killedBy;
    }

    public PlayerDieType getKilledType() {
        return this.killedType;
    }

    @Override
    public boolean isAlive() {
        return this.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP) > 0f;
    }

    @Override
    public Int2FloatOpenHashMap getFightProperties() {
        return this.getAvatar().getFightProperties();
    }

    public int getWeaponEntityId() {
        if (this.getAvatar().getWeapon() != null) {
            return this.getAvatar().getWeapon().getWeaponEntityId();
        }
        return 0;
    }

    @Override
    public void onDeath(int killerId) {
        this.killedType = PlayerDieType.PLAYER_DIE_TYPE_KILL_BY_MONSTER;
        this.killedBy = killerId;
        this.clearEnergy(ChangeEnergyReason.CHANGE_ENERGY_REASON_NONE);
    }

    public void onDeath(PlayerDieType dieType, int killerId) {
        this.killedType = dieType;
        this.killedBy = killerId;
        this.clearEnergy(ChangeEnergyReason.CHANGE_ENERGY_REASON_NONE);
    }

    @Override
    public float heal(float amount) {
        float healed = super.heal(amount);

        if (healed > 0f) {
            this.getScene().broadcastPacket(
                new PacketEntityFightPropChangeReasonNotify(this, FightProperty.FIGHT_PROP_CUR_HP, healed, PropChangeReason.PROP_CHANGE_REASON_ABILITY, ChangeHpReason.CHANGE_HP_REASON_CHANGE_HP_ADD_ABILITY)
            );
        }

        return healed;
    }

    public void clearEnergy(ChangeEnergyReason reason) {
        // Fight props.
        FightProperty curEnergyProp = this.getAvatar().getSkillDepot().getElementType().getCurEnergyProp();
        FightProperty maxEnergyProp = this.getAvatar().getSkillDepot().getElementType().getMaxEnergyProp();

        // Get max energy.
        float maxEnergy = this.avatar.getFightProperty(maxEnergyProp);

        // Set energy to zero.
        this.avatar.setCurrentEnergy(curEnergyProp, 0);

        // Send packets.
        this.getScene().broadcastPacket(new PacketEntityFightPropUpdateNotify(this, curEnergyProp));

        if (reason == ChangeEnergyReason.CHANGE_ENERGY_REASON_SKILL_START) {
            this.getScene().broadcastPacket(new PacketEntityFightPropChangeReasonNotify(this, curEnergyProp, -maxEnergy, reason));
        }
    }

    public void addEnergy(float amount, PropChangeReason reason) {
        this.addEnergy(amount, reason, false);
    }

    public void addEnergy(float amount, PropChangeReason reason, boolean isFlat) {
        // Get current and maximum energy for this avatar.
        FightProperty curEnergyProp = this.getAvatar().getSkillDepot().getElementType().getCurEnergyProp();
        FightProperty maxEnergyProp = this.getAvatar().getSkillDepot().getElementType().getMaxEnergyProp();

        float curEnergy = this.getFightProperty(curEnergyProp);
        float maxEnergy = this.getFightProperty(maxEnergyProp);

        // Get energy recharge.
        float energyRecharge = this.getFightProperty(FightProperty.FIGHT_PROP_CHARGE_EFFICIENCY);

        // Scale amount by energy recharge, if the amount is not flat.
        if (!isFlat) {
            amount *= energyRecharge;
        }

        // Determine the new energy value.
        float newEnergy = Math.min(curEnergy + amount, maxEnergy);

        // Set energy and notify.
        if (newEnergy != curEnergy) {
            this.avatar.setCurrentEnergy(curEnergyProp, newEnergy);

            this.getScene().broadcastPacket(new PacketAvatarFightPropUpdateNotify(this.getAvatar(), curEnergyProp));
            this.getScene().broadcastPacket(new PacketEntityFightPropChangeReasonNotify(this, curEnergyProp, newEnergy, reason));
        }
    }

    public SceneAvatarInfo getSceneAvatarInfo() {
        SceneAvatarInfo.Builder avatarInfo = SceneAvatarInfo.newBuilder()
            .setUid(this.getPlayer().getUid())
            .setAvatarId(this.getAvatar().getAvatarId())
            .setGuid(this.getAvatar().getGuid())
            .setPeerId(this.getPlayer().getPeerId())
            .addAllTalentIdList(this.getAvatar().getTalentIdList())
            .setCoreProudSkillLevel(this.getAvatar().getCoreProudSkillLevel())
            .putAllSkillLevelMap(this.getAvatar().getSkillLevelMap())
            .setSkillDepotId(this.getAvatar().getSkillDepotId())
            .addAllInherentProudSkillList(this.getAvatar().getProudSkillList())
            .putAllProudSkillExtraLevelMap(this.getAvatar().getProudSkillBonusMap())
            .addAllTeamResonanceList(this.getAvatar().getPlayer().getTeamManager().getTeamResonances())
            .setWearingFlycloakId(this.getAvatar().getFlyCloak())
            .setCostumeId(this.getAvatar().getCostume())
            .setBornTime(this.getAvatar().getBornTime());

        for (GameItem item : this.avatar.getEquips().values()) {
            if (item.getItemData().getEquipType() == EquipType.EQUIP_WEAPON) {
                avatarInfo.setWeapon(item.createSceneWeaponInfo());
            } else {
                avatarInfo.addReliquaryList(item.createSceneReliquaryInfo());
            }
            avatarInfo.addEquipIdList(item.getItemId());
        }

        return avatarInfo.build();
    }

    @Override
    public SceneEntityInfo toProto() {
        EntityAuthorityInfo authority = EntityAuthorityInfo.newBuilder()
            .setAbilityInfo(AbilitySyncStateInfo.newBuilder())
            .setRendererChangedInfo(EntityRendererChangedInfo.newBuilder())
            .setAiInfo(SceneEntityAiInfo.newBuilder().setIsAiOpen(true).setBornPos(Vector.newBuilder()))
            .setBornPos(Vector.newBuilder())
            .build();

        SceneEntityInfo.Builder entityInfo = SceneEntityInfo.newBuilder()
            .setEntityId(this.getId())
            .setEntityType(ProtEntityType.PROT_ENTITY_TYPE_AVATAR)
            .addAnimatorParaList(AnimatorParameterValueInfoPair.newBuilder())
            .setEntityClientData(EntityClientData.newBuilder())
            .setEntityAuthorityInfo(authority)
            .setLastMoveSceneTimeMs(this.getLastMoveSceneTimeMs())
            .setLastMoveReliableSeq(this.getLastMoveReliableSeq())
            .setLifeState(this.getLifeState().getValue());

        if (this.getScene() != null) {
            entityInfo.setMotionInfo(this.getMotionInfo());
        }

        for (Int2FloatMap.Entry entry : this.getFightProperties().int2FloatEntrySet()) {
            if (entry.getIntKey() == 0) {
                continue;
            }
            FightPropPair fightProp = FightPropPair.newBuilder().setPropType(entry.getIntKey()).setPropValue(entry.getFloatValue()).build();
            entityInfo.addFightPropList(fightProp);
        }

        PropPair pair = PropPair.newBuilder()
            .setType(PlayerProperty.PROP_LEVEL.getId())
            .setPropValue(ProtoHelper.newPropValue(PlayerProperty.PROP_LEVEL, this.getAvatar().getLevel()))
            .build();
        entityInfo.addPropList(pair);

        entityInfo.setAvatar(this.getSceneAvatarInfo());

        return entityInfo.build();
    }

    public AbilityControlBlock getAbilityControlBlock() {
        AvatarData data = this.getAvatar().getAvatarData();
        AbilityControlBlock.Builder abilityControlBlock = AbilityControlBlock.newBuilder();
        int embryoId = 0;

        // Add avatar abilities
        if (data.getAbilities() != null) {
            for (int id : data.getAbilities()) {
                AbilityEmbryo emb = AbilityEmbryo.newBuilder()
                    .setAbilityId(++embryoId)
                    .setAbilityNameHash(id)
                    .setAbilityOverrideNameHash(GameConstants.DEFAULT_ABILITY_NAME)
                    .build();
                abilityControlBlock.addAbilityEmbryoList(emb);
            }
        }
        // Add default abilities
        for (int id : GameConstants.DEFAULT_ABILITY_HASHES) {
            AbilityEmbryo emb = AbilityEmbryo.newBuilder()
                .setAbilityId(++embryoId)
                .setAbilityNameHash(id)
                .setAbilityOverrideNameHash(GameConstants.DEFAULT_ABILITY_NAME)
                .build();
            abilityControlBlock.addAbilityEmbryoList(emb);
        }
        // Add team resonances
        for (int id : this.getPlayer().getTeamManager().getTeamResonancesConfig()) {
            AbilityEmbryo emb = AbilityEmbryo.newBuilder()
                .setAbilityId(++embryoId)
                .setAbilityNameHash(id)
                .setAbilityOverrideNameHash(GameConstants.DEFAULT_ABILITY_NAME)
                .build();
            abilityControlBlock.addAbilityEmbryoList(emb);
        }
        // Add skill depot abilities
        AvatarSkillDepotData skillDepot = GameData.getAvatarSkillDepotDataMap().get(this.getAvatar().getSkillDepotId());
        if (skillDepot != null && skillDepot.getAbilities() != null) {
            for (int id : skillDepot.getAbilities()) {
                AbilityEmbryo emb = AbilityEmbryo.newBuilder()
                    .setAbilityId(++embryoId)
                    .setAbilityNameHash(id)
                    .setAbilityOverrideNameHash(GameConstants.DEFAULT_ABILITY_NAME)
                    .build();
                abilityControlBlock.addAbilityEmbryoList(emb);
            }
        }
        // Add equip abilities
        if (this.getAvatar().getExtraAbilityEmbryos().size() > 0) {
            for (String skill : this.getAvatar().getExtraAbilityEmbryos()) {
                AbilityEmbryo emb = AbilityEmbryo.newBuilder()
                    .setAbilityId(++embryoId)
                    .setAbilityNameHash(Utils.abilityHash(skill))
                    .setAbilityOverrideNameHash(GameConstants.DEFAULT_ABILITY_NAME)
                    .build();
                abilityControlBlock.addAbilityEmbryoList(emb);
            }
        }

        //
        return abilityControlBlock.build();
    }
}
