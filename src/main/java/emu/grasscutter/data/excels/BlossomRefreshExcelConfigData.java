package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = "BlossomRefreshExcelConfigData.json")
public class BlossomRefreshExcelConfigData extends GameResource {
    private int id; //freshId
    private int cityId;

    private String refreshType;

    private DropVec[] dropVec;

    public String getRefreshType() {
        return refreshType;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getCityId() {
        return cityId;
    }

    public DropVec[] getDropVec() {
        return dropVec;
    }

    public static class DropVec{
        int dropId;
        int previewReward;

        public int getDropId() {
            return dropId;
        }

        public int getPreviewReward() {
            return previewReward;
        }
    }
}
