package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.ResourceType.LoadPriority;
import emu.grasscutter.data.binout.AbilityEmbryoEntry;
import emu.grasscutter.data.common.PropGrowCurve;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.props.WeaponType;
import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

import java.util.List;

@ResourceType(name = "AvatarExcelConfigData.json", loadPriority = LoadPriority.LOW)
public class AvatarData extends GameResource {

    private String iconName;
    private String bodyType;
    private String qualityType;
    private int chargeEfficiency;
    private int initialWeapon;
    private WeaponType weaponType;
    private String imageName;
    private int avatarPromoteId;
    private String cutsceneShow;
    private int skillDepotId;
    private int staminaRecoverSpeed;
    private List<String> candSkillDepotIds;
    private String avatarIdentityType;
    private List<Integer> avatarPromoteRewardLevelList;
    private List<Integer> avatarPromoteRewardIdList;

    private long nameTextMapHash;

    private float hpBase;
    private float attackBase;
    private float defenseBase;
    private float critical;
    private float criticalHurt;

    private List<PropGrowCurve> propGrowCurves;
    private int id;

    // Transient
    private String name;

    private Int2ObjectMap<String> growthCurveMap;
    private float[] hpGrowthCurve;
    private float[] attackGrowthCurve;
    private float[] defenseGrowthCurve;
    private AvatarSkillDepotData skillDepot;
    private IntList abilities;

    private List<Integer> fetters;
    private int nameCardRewardId;
    private int nameCardId;

    @Override
    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getBodyType() {
        return this.bodyType;
    }

    public String getQualityType() {
        return this.qualityType;
    }

    public int getChargeEfficiency() {
        return this.chargeEfficiency;
    }

    public int getInitialWeapon() {
        return this.initialWeapon;
    }

    public WeaponType getWeaponType() {
        return this.weaponType;
    }

    public String getImageName() {
        return this.imageName;
    }

    public int getAvatarPromoteId() {
        return this.avatarPromoteId;
    }

    public String getCutsceneShow() {
        return this.cutsceneShow;
    }

    public int getSkillDepotId() {
        return this.skillDepotId;
    }

    public int getStaminaRecoverSpeed() {
        return this.staminaRecoverSpeed;
    }

    public List<String> getCandSkillDepotIds() {
        return this.candSkillDepotIds;
    }

    public String getAvatarIdentityType() {
        return this.avatarIdentityType;
    }

    public List<Integer> getAvatarPromoteRewardLevelList() {
        return this.avatarPromoteRewardLevelList;
    }

    public List<Integer> getAvatarPromoteRewardIdList() {
        return this.avatarPromoteRewardIdList;
    }

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

    public long getNameTextMapHash() {
        return this.nameTextMapHash;
    }

    public AvatarSkillDepotData getSkillDepot() {
        return this.skillDepot;
    }

    public IntList getAbilities() {
        return this.abilities;
    }

    public List<Integer> getFetters() {
        return this.fetters;
    }

    public int getNameCardRewardId() {
        return this.nameCardRewardId;
    }

    public int getNameCardId() {
        return this.nameCardId;
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
        String[] split = this.iconName.split("_");
        if (split.length > 0) {
            this.name = split[split.length - 1];

            AbilityEmbryoEntry info = GameData.getAbilityEmbryoInfo().get(this.name);
            if (info != null) {
                this.abilities = new IntArrayList(info.getAbilities().length);
                for (String ability : info.getAbilities()) {
                    this.abilities.add(Utils.abilityHash(ability));
                }
            }
        }
    }
}

