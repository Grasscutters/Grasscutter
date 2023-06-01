package emu.grasscutter.data.binout.config;

import java.util.List;
import lombok.Data;

@Data
public class ConfigGlobalCombat {
    private DefaultAbilities defaultAbilities;
    // TODO: Add more indices

    @Data
    public class DefaultAbilities {
        private String monterEliteAbilityName;
        private List<String> nonHumanoidMoveAbilities;
        private List<String> levelDefaultAbilities;
        private List<String> levelElementAbilities;
        private List<String> levelItemAbilities;
        private List<String> levelSBuffAbilities;
        private List<String> defaultMPLevelAbilities;
        private List<String> defaultAvatarAbilities;
        private List<String> defaultTeamAbilities;
    }
}
