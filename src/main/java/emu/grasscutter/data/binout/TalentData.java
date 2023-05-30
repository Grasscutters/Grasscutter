package emu.grasscutter.data.binout;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.data.common.DynamicFloat;
import java.io.Serializable;

public class TalentData implements Serializable {
    public enum Type {
        AddAbility,
        ModifySkillCD,
        UnlockTalentParam,
        AddTalentExtraLevel,
        ModifyAbility;
    }

    @SerializedName("$type")
    public Type type;

    public String abilityName;
    public String talentParam;
    public int talentIndex;
    public int extraLevel;

    public String paramSpecial;
    public DynamicFloat paramDelta;
    public DynamicFloat paramRatio = new DynamicFloat(1.0f);
}
