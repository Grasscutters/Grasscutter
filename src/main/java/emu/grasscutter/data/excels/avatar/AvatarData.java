package emu.grasscutter.data.excels.avatar;

import emu.grasscutter.data.*;
import emu.grasscutter.data.ResourceType.LoadPriority;
import emu.grasscutter.data.common.PropGrowCurve;
import emu.grasscutter.game.props.*;
import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.*;
import lombok.Getter;

import java.util.*;

@ResourceType(name = "AvatarExcelConfigData.json", loadPriority = LoadPriority.LOW)
public class AvatarData extends GameResource {

    private String iconName;
    @Getter private String bodyType;
    @Getter private String qualityType;
    @Getter private int chargeEfficiency;
    @Getter private int initialWeapon;
    @Getter private WeaponType weaponType;
    @Getter private String imageName;
    @Getter private int avatarPromoteId;
    @Getter private String cutsceneShow;
    @Getter private int skillDepotId;
    @Getter private int staminaRecoverSpeed;
    @Getter private List<Integer> candSkillDepotIds;
    @Getter private String avatarIdentityType;
    @Getter private List<Integer> avatarPromoteRewardLevelList;
    @Getter private List<Integer> avatarPromoteRewardIdList;

    @Getter
    private long nameTextMapHash;

    private float hpBase;
    private float attackBase;
    private float defenseBase;
    private float critical;
    private float criticalHurt;

    private List<PropGrowCurve> propGrowCurves;
    @Getter(onMethod_ = @Override)
    private int id;

    // Transient
    @Getter
    private String name;

    private Int2ObjectMap<String> growthCurveMap;
    private float[] hpGrowthCurve;
    private float[] attackGrowthCurve;
    private float[] defenseGrowthCurve;
    @Getter
    private AvatarSkillDepotData skillDepot;
    @Getter
    private IntList abilities;
    @Getter
    private List<String> abilitieNames = new ArrayList<>();

    @Getter
    private List<Integer> fetters;
    @Getter
    private int nameCardRewardId;
    @Getter
    private int nameCardId;

    public float getBaseHp(int level) {
        try {
            return this.hpBase * this.hpGrowthCurve[level - 1];
        } catch (Exception e) {
            return this.hpBase;
        }
    }

    public float getBaseAttack(int level) {
        try {
            return this.attackBase * this.attackGrowthCurve[level - 1];
        } catch (Exception e) {
            return this.attackBase;
        }
    }

    public float getBaseDefense(int level) {
        try {
            return this.defenseBase * this.defenseGrowthCurve[level - 1];
        } catch (Exception e) {
            return this.defenseBase;
        }
    }

    public float getBaseCritical() {
        return this.critical;
    }

    public float getBaseCriticalHurt() {
        return this.criticalHurt;
    }

    public float getGrowthCurveById(int level, FightProperty prop) {
        String growCurve = this.growthCurveMap.get(prop.getId());
        if (growCurve == null) {
            return 1f;
        }
        AvatarCurveData curveData = GameData.getAvatarCurveDataMap().get(level);
        if (curveData == null) {
            return 1f;
        }
        return curveData.getCurveInfos().getOrDefault(growCurve, 1f);
    }

    @Override
    public void onLoad() {
        this.skillDepot = GameData.getAvatarSkillDepotDataMap().get(this.skillDepotId);

        // Get fetters from GameData
        this.fetters = GameData.getFetterDataEntries().get(this.id);

        if (GameData.getFetterCharacterCardDataMap().get(this.id) != null) {
            this.nameCardRewardId = GameData.getFetterCharacterCardDataMap().get(this.id).getRewardId();
        }

        if (GameData.getRewardDataMap().get(this.nameCardRewardId) != null) {
            this.nameCardId = GameData.getRewardDataMap().get(this.nameCardRewardId).getRewardItemList().get(0).getItemId();
        }

        int size = GameData.getAvatarCurveDataMap().size();
        this.hpGrowthCurve = new float[size];
        this.attackGrowthCurve = new float[size];
        this.defenseGrowthCurve = new float[size];
        for (AvatarCurveData curveData : GameData.getAvatarCurveDataMap().values()) {
            int level = curveData.getLevel() - 1;
            for (PropGrowCurve growCurve : this.propGrowCurves) {
                FightProperty prop = FightProperty.getPropByName(growCurve.getType());
                switch (prop) {
                    case FIGHT_PROP_BASE_HP:
                        this.hpGrowthCurve[level] = curveData.getCurveInfos().get(growCurve.getGrowCurve());
                        break;
                    case FIGHT_PROP_BASE_ATTACK:
                        this.attackGrowthCurve[level] = curveData.getCurveInfos().get(growCurve.getGrowCurve());
                        break;
                    case FIGHT_PROP_BASE_DEFENSE:
                        this.defenseGrowthCurve[level] = curveData.getCurveInfos().get(growCurve.getGrowCurve());
                        break;
                    default:
                        break;
                }
            }
        }

        /*
        for (PropGrowCurve growCurve : this.PropGrowCurves) {
            FightProperty prop = FightProperty.getPropByName(growCurve.getType());
            this.growthCurveMap.put(prop.getId(), growCurve.getGrowCurve());
        }
        */

        // Cache abilities
        this.buildEmbryo();
    }

    /**
     * Create ability embryos.
     */
    public void buildEmbryo() {
        var split = this.iconName.split("_");
        if (split.length > 0) {
            this.name = split[split.length - 1];

            var info = GameData.getAbilityEmbryoInfo().get(this.name);
            if (info != null) {
                this.abilities = new IntArrayList(info.getAbilities().length);
                for (var ability : info.getAbilities()) {
                    this.abilities.add(Utils.abilityHash(ability));
                    this.abilitieNames.add(ability);
                }
            }
        }
    }
}
