package emu.grasscutter.data.binout;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.annotation.Nullable;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfigGadget {
    // There are more values that can be added that might be useful in the json
    @Nullable
    ConfigGadgetCombat combat;
}
