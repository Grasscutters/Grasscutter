package emu.grasscutter.data.custom;

import emu.grasscutter.data.common.BaseTrialActivityData;
import java.util.List;
import lombok.Data;

@Data
public class TrialAvatarActivityCustomData implements BaseTrialActivityData {
    private int ScheduleId;
    private List<Integer> AvatarIndexIdList;
    private List<Integer> RewardIdList;

    public void onLoad() {
        this.AvatarIndexIdList = AvatarIndexIdList.stream().filter(x -> x > 0).toList();
        this.RewardIdList = RewardIdList.stream().filter(x -> x > 0).toList();
    }
}
