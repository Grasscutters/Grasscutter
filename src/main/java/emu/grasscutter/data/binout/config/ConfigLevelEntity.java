package emu.grasscutter.data.binout.config;

import java.util.List;

import emu.grasscutter.data.binout.config.fields.ConfigAbilityData;
import lombok.Getter;

public class ConfigLevelEntity {

    @Getter private List<ConfigAbilityData> abilities; //monster abilities
    @Getter private List<ConfigAbilityData> avatarAbilities;
    @Getter private List<ConfigAbilityData> teamAbilities;
    @Getter private List<Integer> preloadMonsterEntityIDs;
}
