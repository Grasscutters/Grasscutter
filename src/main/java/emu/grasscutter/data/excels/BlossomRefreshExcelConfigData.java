package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import lombok.Getter;

@ResourceType(name = "BlossomRefreshExcelConfigData.json")
public class BlossomRefreshExcelConfigData extends GameResource {
    private int id; //freshId
    @Getter private int cityId;
    @Getter private String refreshType;
    @Getter private DropVec[] dropVec;

    @Override
    public int getId() {
        return id;
    }

    public static class DropVec {
        @Getter int dropId;
        @Getter int previewReward;
    }
}
