package emu.grasscutter.data.excels;

import emu.grasscutter.data.*;
import java.util.List;
import lombok.Getter;

@ResourceType(name = "BlossomRefreshExcelConfigData.json")
@Getter
public class BlossomRefreshExcelConfigData extends GameResource {
    @Getter(onMethod_ = @Override)
    private int id;
    // Map details
    private long nameTextMapHash;
    private long descTextMapHash;
    private String icon;
    private String clientShowType; // BLOSSOM_SHOWTYPE_CHALLENGE, BLOSSOM_SHOWTYPE_NPCTALK

    // Refresh details
    private String refreshType; // Leyline blossoms, magical ore outcrops
    private int
            refreshCount; // Number of entries to spawn at refresh (1 for each leyline type for each city,
    // 4 for magical ore for each city)
    private String refreshTime; // Server time-of-day to refresh at
    private RefreshCond[] refreshCondVec; // AR requirements etc.

    private int cityId;
    private int blossomChestId; // 1 for mora, 2 for exp
    private Drop[] dropVec;

    // Unknown details
    // @Getter private int reviseLevel;
    // @Getter private int campUpdateNeedCount;  // Always 1 if specified

    @Getter
    public static class Drop {
        int dropId;
        int previewReward;
    }

    @Getter
    public static class RefreshCond {
        String type;
        List<Integer> param;
    }
}
