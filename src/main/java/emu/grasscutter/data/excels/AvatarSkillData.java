package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.ResourceType.LoadPriority;
import emu.grasscutter.game.props.ElementType;
import lombok.Getter;

@ResourceType(name = "AvatarSkillExcelConfigData.json", loadPriority = LoadPriority.HIGHEST)
public class AvatarSkillData extends GameResource {
    private int id;
    @Getter private float cdTime;
    @Getter private int costElemVal;
    @Getter private int maxChargeNum;
    @Getter private int triggerID;
    @Getter private boolean isAttackCameraLock;
    @Getter private int proudSkillGroupId;
    @Getter private ElementType costElemType;
    @Getter private long nameTextMapHash;
    @Getter private long descTextMapHash;
    @Getter private String abilityName;

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void onLoad() {

    }
}
