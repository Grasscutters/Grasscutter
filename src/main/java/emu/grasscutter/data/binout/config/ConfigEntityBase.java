package emu.grasscutter.data.binout.config;

import emu.grasscutter.data.binout.config.fields.*;
import java.util.Collection;
import javax.annotation.Nullable;
import lombok.Data;

@Data
public class ConfigEntityBase {
    @Nullable ConfigCommon configCommon;
    @Nullable ConfigCombat combat;
    Collection<ConfigAbilityData> abilities;
    ConfigGlobalValue globalValue; // used for SGV in monsters and Gadgets
}
