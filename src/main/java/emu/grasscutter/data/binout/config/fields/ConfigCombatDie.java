package emu.grasscutter.data.binout.config.fields;

import com.google.gson.annotations.SerializedName;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfigCombatDie {
    @SerializedName(
            value = "dieEndTime",
            alternate = {"HGGPMFGGBNC"})
    double dieEndTime;

    double dieForceDisappearTime;
    boolean hasAnimatorDie;
}
