package emu.grasscutter.data.binout;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfigGadgetCombatProperty {
    float HP;
    boolean isLockHP;
    boolean isInvincible;
    boolean isGhostToAllied;
    float attack;
    float defence;
    float weight;
    boolean useCreatorProperty;
}
