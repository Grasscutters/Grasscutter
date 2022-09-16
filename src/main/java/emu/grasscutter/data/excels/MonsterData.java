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
        return monsterName;
    }

    public MonsterType getType() {
        return type;
    }

    public String getServerScript() {
        return serverScript;
    }

    public List<Integer> getAffix() {
        return affix;
    }

    public String getAi() {
        return ai;
    }

    public void stopAi(){  // Temporarily useless
        ai = "sentry02";
    }

    public int[] getEquips() {
        return equips;
    }

    public List<emu.grasscutter.data.excels.MonsterData.HpDrops> getHpDrops() {
        return hpDrops;
    }

    public int getKillDropId() {
        return killDropId;
    }

    public void setKillDropId(int value){
        killDropId = value;
    }

    public String getExcludeWeathers() {
        return excludeWeathers;
    }

    public void getExcludeWeathers(String value){
        excludeWeathers = value;
    }

    public int getFeatureTagGroupID() {
        return featureTagGroupID;
    }

    public int getMpPropID() {
        return mpPropID;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String  value){
        skin = value;
    }

    public int getDescribeId() {
        return describeId;
    }

    public void setDescribeId(int value){
        describeId = value;
    }

    public int getCombatBGMLevel() {
        return combatBGMLevel;
    }

    public int getEntityBudgetLevel() {
        return entityBudgetLevel;
    }

    public float getBaseHp() {
        return hpBase;
    }

    public void setBaseHp(float value){
        hpBase = value;
    }

    public float getBaseAttack() {
        return attackBase;
    }

    public void setBaseAttack(float value){
        attackBase = value;
    }

    public float getBaseDefense() {
        return defenseBase;
    }

    public void setBaseDefense(float value){
        defenseBase = value;
    }

    public float getElecSubHurt() {
        return elecSubHurt;
    }

    public void setElecSubHurt(float value){
        elecSubHurt = value;
    }

    public float getGrassSubHurt() {
        return grassSubHurt;
    }

    public void setGrassSubHurt(float value){
        grassSubHurt = value;
    }

    public float getWaterSubHurt() {
        return waterSubHurt;
    }

    public void setWaterSubHurt(float value){
        waterSubHurt = value;
    }

    public float getWindSubHurt() {
        return windSubHurt;
    }

    public void setWindSubHurt(float value){
        windSubHurt = value;
    }

    public float getIceSubHurt() {
        return iceSubHurt;
    }

    public void setIceSubHurt(float value){
        iceSubHurt = value;
    }

    public float getPhysicalSubHurt() {
        return physicalSubHurt;
    }

    public void setPhysicalSubHurt(float value){
        physicalSubHurt = value;
    }

    public List<PropGrowCurve> getPropGrowCurves() {
        return propGrowCurves;
    }

    public long getNameTextMapHash() {
        return nameTextMapHash;
    }

    public int getCampID() {
        return campID;
    }

    public void setCampID(int value){
        campID = value;
    }

    public MonsterDescribeData getDescribeData() {
        return describeData;
    }

    public int getWeaponId() {
        return weaponId;
    }

    public void setWeaponId(int value){
        weaponId = value;
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

        public int getDropId(){
            return this.DropId;
        }
        public int getHpPercent(){
            return this.HpPercent;
        }
    }
}
