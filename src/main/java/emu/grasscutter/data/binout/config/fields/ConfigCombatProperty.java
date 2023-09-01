package emu.grasscutter.data.binout.config.fields;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfigCombatProperty {
    float HP;
    boolean isLockHP;
    boolean isInvincible;
    boolean isGhostToAllied;
    float attack;
    float defence;
    float weight;
    boolean useCreatorProperty;
}
