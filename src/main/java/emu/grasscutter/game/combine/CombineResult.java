package emu.grasscutter.game.combine;

import emu.grasscutter.data.common.ItemParamData;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CombineResult {
    private List<ItemParamData> material;
    private List<ItemParamData> result;
    private List<ItemParamData> extra;
    private List<ItemParamData> back;
}
