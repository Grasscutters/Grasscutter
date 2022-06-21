package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.ResourceType.LoadPriority;
import emu.grasscutter.game.props.ElementType;

import java.util.List;

@ResourceType(name = "AvatarSkillExcelConfigData.json", loadPriority = LoadPriority.HIGHEST)
public class AvatarSkillData extends GameResource {
    private int id;
    private float cdTime;
    private int costElemVal;
    private int maxChargeNum;
    private int triggerID;
    private boolean isAttackCameraLock;
    private int proudSkillGroupId;
    private ElementType costElemType;
    private List<Float> lockWeightParams;

    private long nameTextMapHash;

    private String abilityName;
    private String lockShape;
    private String globalValueKey;

    @Override
    public int getId() {
        return this.id;
    }

    public float getCdTime() {
        return this.cdTime;
    }

    public int getCostElemVal() {
        return this.costElemVal;
    }

    public int getMaxChargeNum() {
        return this.maxChargeNum;
    }

    public int getTriggerID() {
        return this.triggerID;
    }

    public boolean isIsAttackCameraLock() {
        return this.isAttackCameraLock;
    }

    public int getProudSkillGroupId() {
        return this.proudSkillGroupId;
    }

    public ElementType getCostElemType() {
        return this.costElemType;
    }

    public List<Float> getLockWeightParams() {
        return this.lockWeightParams;
    }

    public long getNameTextMapHash() {
        return this.nameTextMapHash;
    }

    public String getAbilityName() {
        return this.abilityName;
    }

    public String getLockShape() {
        return this.lockShape;
    }

    public String getGlobalValueKey() {
        return this.globalValueKey;
    }

    @Override
    public void onLoad() {

    }
}
