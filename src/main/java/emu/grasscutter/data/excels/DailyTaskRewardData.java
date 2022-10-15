package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ResourceType(name = {"DailyTaskRewardExcelConfigData.json"})
public class DailyTaskRewardData extends GameResource {
    int id;
    List<DropVec> dropVec;

    @Override
    public int getId() {
        return id;
    }

    @Getter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class DropVec {
        int dropId;
        int previewRewardId;
    }
}
