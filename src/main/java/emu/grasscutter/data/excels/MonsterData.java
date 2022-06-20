package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.ResourceType.LoadPriority;
import emu.grasscutter.data.common.PropGrowCurve;
import emu.grasscutter.game.props.MonsterType;

import java.util.List;

@ResourceType(name = "MonsterExcelConfigData.json", loadPriority = LoadPriority.LOW)
public class MonsterData extends GameResource {
    private int id;

    private String monsterName;
    private MonsterType type;
    private String serverScript;
    private List<Integer> affix;
    private String ai;
    private int[] equips;
    private List<HpDrops> hpDrops;
    private int killDropId;
    private String excludeWeathers;
    private int featureTagGroupID;
    private int mpPropID;
    private String skin;
    private int describeId;
    private int combatBGMLevel;
    private int entityBudgetLevel;
    private float hpBase;
    private float attackBase;
    private float defenseBase;
    private float fireSubHurt;
    private float elecSubHurt;
    private float grassSubHurt;
    private float waterSubHurt;
    private float windSubHurt;
    private float rockSubHurt;
    private float iceSubHurt;
    private float physicalSubHurt;
    private List<PropGrowCurve> propGrowCurves;
    private long nameTextMapHash;
    private int campID;

    // Transient
    private int weaponId;
    private MonsterDescribeData describeData;

    @Override
    public int getId() {
        return this.id;
    }

    public String getMonsterName() {
        return this.monsterName;
    }

    public MonsterType getType() {
        return this.type;
    }

    public String getServerScript() {
        return this.serverScript;
    }

    public List<Integer> getAffix() {
        return this.affix;
    }

    public String getAi() {
        return this.ai;
    }

    public int[] getEquips() {
        return this.equips;
    }

    public List<HpDrops> getHpDrops() {
        return this.hpDrops;
    }

    public int getKillDropId() {
        return this.killDropId;
    }

    public String getExcludeWeathers() {
        return this.excludeWeathers;
    }

    public int getFeatureTagGroupID() {
        return this.featureTagGroupID;
    }

    public int getMpPropID() {
        return this.mpPropID;
    }

    public String getSkin() {
        return this.skin;
    }

    public int getDescribeId() {
        return this.describeId;
    }

    public int getCombatBGMLevel() {
        return this.combatBGMLevel;
    }

    public int getEntityBudgetLevel() {
        return this.entityBudgetLevel;
    }

    public float getBaseHp() {
        return this.hpBase;
    }

    public float getBaseAttack() {
        return this.attackBase;
    }

    public float getBaseDefense() {
        return this.defenseBase;
    }

    public float getElecSubHurt() {
        return this.elecSubHurt;
    }

    public float getGrassSubHurt() {
        return this.grassSubHurt;
    }

    public float getWaterSubHurt() {
        return this.waterSubHurt;
    }

    public float getWindSubHurt() {
        return this.windSubHurt;
    }

    public float getIceSubHurt() {
        return this.iceSubHurt;
    }

    public float getPhysicalSubHurt() {
        return this.physicalSubHurt;
    }

    public List<PropGrowCurve> getPropGrowCurves() {
        return this.propGrowCurves;
    }

    public long getNameTextMapHash() {
        return this.nameTextMapHash;
    }

    public int getCampID() {
        return this.campID;
    }

    public MonsterDescribeData getDescribeData() {
        return this.describeData;
    }

    public int getWeaponId() {
        return this.weaponId;
    }

    @Override
    public void onLoad() {
        this.describeData = GameData.getMonsterDescribeDataMap().get(this.getDescribeId());

        for (int id : this.equips) {
            if (id == 0) {
                continue;
            }
            GadgetData gadget = GameData.getGadgetDataMap().get(id);
            if (gadget == null) {
                continue;
            }
            if (gadget.getItemJsonName().equals("Default_MonsterWeapon")) {
                this.weaponId = id;
            }
        }
    }

    public class HpDrops {
        private int DropId;
        private int HpPercent;

        public int getDropId() {
            return this.DropId;
        }

        public int getHpPercent() {
            return this.HpPercent;
        }
    }
}
