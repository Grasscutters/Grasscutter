package emu.grasscutter.data.binout.config;

import emu.grasscutter.data.binout.config.fields.ConfigAbilityData;
import java.util.List;
import lombok.Getter;

@Getter
public class ConfigLevelEntity {

    private List<ConfigAbilityData> abilities;
    private List<ConfigAbilityData> monsterAbilities;
    private List<ConfigAbilityData> avatarAbilities;
    private List<ConfigAbilityData> teamAbilities;
    private List<Integer> preloadMonsterEntityIDs;

    private String dropElemControlType;
}
