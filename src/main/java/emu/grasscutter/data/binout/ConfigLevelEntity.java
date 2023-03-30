
package emu.grasscutter.data.binout;

import java.util.List;
import lombok.Getter;

public class ConfigLevelEntity {

    @Getter private List<EntityAbilities> abilities; //monster abilities
    @Getter private List<EntityAbilities> avatarAbilities;

    public class EntityAbilities {
        @Getter private String abilityName;
    }
}
