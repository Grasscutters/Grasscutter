package emu.grasscutter.game.combine;

import emu.grasscutter.data.common.ItemParamData;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class CombineResult {
    @Getter @Setter private List<ItemParamData> material;
    @Getter @Setter private List<ItemParamData> result;
    @Getter @Setter private List<ItemParamData> extra;
    @Getter @Setter private List<ItemParamData> back;
    @Getter @Setter private List<ItemParamData> random;
}
