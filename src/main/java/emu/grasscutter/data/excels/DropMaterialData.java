package emu.grasscutter.data.excels;

import emu.grasscutter.data.*;
import lombok.Getter;

@ResourceType(name = "DropMaterialExcelConfigData.json")
@Getter
public class DropMaterialData extends GameResource {
    @Getter(onMethod_ = @Override)
    private int id;

    private boolean useOnGain;
    private boolean disableFirstGainHint;
    private boolean autoPick;
    private boolean dropSeparately;
    private int groupId;
    private boolean forceGainHint;
}
