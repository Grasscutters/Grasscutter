package emu.grasscutter.data.def;

import java.util.List;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.ResourceType.LoadPriority;

@ResourceType(name = "AvatarSkillExcelConfigData.json", loadPriority = LoadPriority.HIGHEST)
public class AvatarSkillData extends GameResource {
	private int Id;
    private	float CdTime;
    private int CostElemVal;
    private int MaxChargeNum;
    private int TriggerID;
    private boolean IsAttackCameraLock;
    private int ProudSkillGroupId;
    private String CostElemType;
    private List<Float> LockWeightParams;
    
    private long NameTextMapHash;
    
    private String AbilityName;
    private String LockShape;
    private String GlobalValueKey;

    @Override
	public int getId(){
        return this.Id;
    }

	public float getCdTime() {
		return CdTime;
	}

	public int getCostElemVal() {
		return CostElemVal;
	}

	public int getMaxChargeNum() {
		return MaxChargeNum;
	}

	public int getTriggerID() {
		return TriggerID;
	}

	public boolean isIsAttackCameraLock() {
		return IsAttackCameraLock;
	}

	public int getProudSkillGroupId() {
		return ProudSkillGroupId;
	}

	public String getCostElemType() {
		return CostElemType;
	}

	public List<Float> getLockWeightParams() {
		return LockWeightParams;
	}

	public long getNameTextMapHash() {
		return NameTextMapHash;
	}

	public String getAbilityName() {
		return AbilityName;
	}

	public String getLockShape() {
		return LockShape;
	}

	public String getGlobalValueKey() {
		return GlobalValueKey;
	}
	
	@Override
	public void onLoad() {
		
	}
}
