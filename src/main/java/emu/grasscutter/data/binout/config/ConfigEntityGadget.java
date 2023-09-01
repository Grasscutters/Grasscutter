package emu.grasscutter.data.binout.config;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfigEntityGadget extends ConfigEntityBase {
    // There are more values that can be added that might be useful in the json
}
