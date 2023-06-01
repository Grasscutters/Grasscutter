package emu.grasscutter.game.expedition;

import java.util.List;
import lombok.Getter;

public class ExpeditionRewardInfo {
    @Getter private int expId;
    @Getter private List<ExpeditionRewardDataList> expeditionRewardDataList;
}
