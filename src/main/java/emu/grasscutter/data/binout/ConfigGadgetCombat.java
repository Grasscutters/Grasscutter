package emu.grasscutter.data.binout;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfigGadgetCombat {
    // There are more values that can be added that might be useful in the json
    ConfigGadgetCombatProperty property;
}
