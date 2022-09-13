package emu.grasscutter.game.expedition;

import lombok.Getter;

import java.util.List;

public class ExpeditionRewardInfo {
    @Getter private int expId;
    @Getter private List<ExpeditionRewardDataList> expeditionRewardDataList;
}
