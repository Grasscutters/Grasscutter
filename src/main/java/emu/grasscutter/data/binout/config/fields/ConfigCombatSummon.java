package emu.grasscutter.data.binout.config.fields;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;

@Data
public class ConfigCombatSummon {
    List<SummonTag> summonTags;

    @Getter
    public final class SummonTag {
        int summonTag;
    }
}
