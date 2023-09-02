package emu.grasscutter.data.excels.monster;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.data.*;
import emu.grasscutter.data.ResourceType.LoadPriority;
import emu.grasscutter.data.common.PropGrowCurve;
import emu.grasscutter.data.excels.GadgetData;
import emu.grasscutter.game.props.*;
import java.util.*;
import lombok.Getter;

@ResourceType(name = "MonsterExcelConfigData.json", loadPriority = LoadPriority.LOW)
@Getter
public class MonsterData extends GameResource {
    public static Set<FightProperty> definedFightProperties =
            Set.of(
                    FightProperty.FIGHT_PROP_BASE_HP,
                    FightProperty.FIGHT_PROP_BASE_ATTACK,
                    FightProperty.FIGHT_PROP_BASE_DEFENSE,
                    FightProperty.FIGHT_PROP_PHYSICAL_SUB_HURT,
                    FightProperty.FIGHT_PROP_FIRE_SUB_HURT,
                    FightProperty.FIGHT_PROP_ELEC_SUB_HURT,
                    FightProperty.FIGHT_PROP_WATER_SUB_HURT,
                    FightProperty.FIGHT_PROP_GRASS_SUB_HURT,
                    FightProperty.FIGHT_PROP_WIND_SUB_HURT,
                    FightProperty.FIGHT_PROP_ROCK_SUB_HURT,
                    FightProperty.FIGHT_PROP_ICE_SUB_HURT);

    @Getter(onMethod_ = @Override)
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

    @SerializedName("hpBase")
    private float baseHp;

    @SerializedName("attackBase")
    private float baseAttack;

    @SerializedName("defenseBase")
    private float baseDefense;

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

    private int specialNameId; // will only be set if describe data is available

    @Override
    public void onLoad() {
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

        this.describeData = GameData.getMonsterDescribeDataMap().get(this.getDescribeId());

        if (this.describeData == null) {
            return;
        }
        for (var entry : GameData.getMonsterSpecialNameDataMap().int2ObjectEntrySet()) {
            if (entry.getValue().getSpecialNameLabId() == this.getDescribeData().getSpecialNameLabId()) {
                this.specialNameId = entry.getIntKey();
                break;
            }
        }
    }

    public float getFightProperty(FightProperty prop) {
        return switch (prop) {
            case FIGHT_PROP_BASE_HP -> this.baseHp;
            case FIGHT_PROP_BASE_ATTACK -> this.baseAttack;
            case FIGHT_PROP_BASE_DEFENSE -> this.baseDefense;
            case FIGHT_PROP_PHYSICAL_SUB_HURT -> this.physicalSubHurt;
            case FIGHT_PROP_FIRE_SUB_HURT -> this.fireSubHurt;
            case FIGHT_PROP_ELEC_SUB_HURT -> this.elecSubHurt;
            case FIGHT_PROP_WATER_SUB_HURT -> this.waterSubHurt;
            case FIGHT_PROP_GRASS_SUB_HURT -> this.grassSubHurt;
            case FIGHT_PROP_WIND_SUB_HURT -> this.windSubHurt;
            case FIGHT_PROP_ROCK_SUB_HURT -> this.rockSubHurt;
            case FIGHT_PROP_ICE_SUB_HURT -> this.iceSubHurt;
            default -> 0f;
        };
    }

    @Getter
    public class HpDrops {
        private int DropId;
        private int HpPercent;
    }
}
