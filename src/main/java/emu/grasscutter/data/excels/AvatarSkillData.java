package emu.grasscutter.data.excels;

import java.util.List;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.ResourceType.LoadPriority;
import emu.grasscutter.game.props.ElementType;

@ResourceType(name = "AvatarSkillExcelConfigData.json", loadPriority = LoadPriority.HIGHEST)
public class AvatarSkillData extends GameResource {
	private int id;
    private	float cdTime;
    private int costElemVal;
    private int maxChargeNum;
    private int triggerID;
    private boolean isAttackCameraLock;
    private int proudSkillGroupId;
    private ElementType costElemType;
    private long nameTextMapHash;
    private String abilityName;

    @Override
	public int getId(){
        return this.id;
    }

	public float getCdTime() {
		return cdTime;
	}

	public int getCostElemVal() {
		return costElemVal;
	}

	public int getMaxChargeNum() {
		return maxChargeNum;
	}

	public int getTriggerID() {
		return triggerID;
	}

	public boolean isIsAttackCameraLock() {
		return isAttackCameraLock;
	}

	public int getProudSkillGroupId() {
		return proudSkillGroupId;
	}

	public ElementType getCostElemType() {
		return costElemType;
	}

	public long getNameTextMapHash() {
		return nameTextMapHash;
	}

	public String getAbilityName() {
		return abilityName;
	}
	
	@Override
	public void onLoad() {
		
	}
}
