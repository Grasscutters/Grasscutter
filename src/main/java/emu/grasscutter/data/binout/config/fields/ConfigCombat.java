package emu.grasscutter.data.binout.config.fields;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfigCombat {
    // There are more values that can be added that might be useful in the json
    ConfigCombatProperty property;
}
