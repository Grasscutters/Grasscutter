package emu.grasscutter.data.binout.config.fields;

import java.util.List;
import lombok.*;

@Data
public class ConfigCombatSummon {
    List<SummonTag> summonTags;

    @Getter
    public final class SummonTag {
        int summonTag;
    }
}
