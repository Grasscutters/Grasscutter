package emu.grasscutter.data.excels.trial;

import emu.grasscutter.data.*;
import emu.grasscutter.data.common.BaseTrialActivityData;
import lombok.*;

import java.util.List;

@ResourceType(name = "TrialAvatarActivityExcelConfigData.json")
@EqualsAndHashCode(callSuper = false)
@Data
public class TrialAvatarActivityData extends GameResource implements BaseTrialActivityData {
    private int ScheduleId;
    private List<Integer> AvatarIndexIdList;
    private List<Integer> RewardIdList;

    @Override
    public int getId() {
        return ScheduleId;
    }
}
