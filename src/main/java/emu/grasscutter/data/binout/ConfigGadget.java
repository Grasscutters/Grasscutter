package emu.grasscutter.data.binout;

import javax.annotation.Nullable;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfigGadget {
    // There are more values that can be added that might be useful in the json
    @Nullable ConfigGadgetCombat combat;
}
