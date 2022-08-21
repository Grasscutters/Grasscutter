package emu.grasscutter.data.excels;

import java.util.List;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import lombok.Getter;

@ResourceType(name = "BlossomRefreshExcelConfigData.json")
public class BlossomRefreshExcelConfigData extends GameResource {
    private int id;
    // Map details
    @Getter private long nameTextMapHash;
    @Getter private long descTextMapHash;
    @Getter private String icon;
    @Getter private String clientShowType;  // BLOSSOM_SHOWTYPE_CHALLENGE, BLOSSOM_SHOWTYPE_NPCTALK

    // Refresh details
    @Getter private String refreshType;  // Leyline blossoms, magical ore outcrops
    @Getter private int refreshCount;  // Number of entries to spawn at refresh (1 for each leyline type for each city, 4 for magical ore for each city)
    @Getter private String refreshTime;  // Server time-of-day to refresh at
    @Getter private RefreshCond[] refreshCondVec;  // AR requirements etc.

    @Getter private int cityId;
    @Getter private int blossomChestId;  // 1 for mora, 2 for exp
    @Getter private Drop[] dropVec;

    // Unknown details
    // @Getter private int reviseLevel;
    // @Getter private int campUpdateNeedCount;  // Always 1 if specified

    @Override
    public int getId() {
        return id;
    }

    public static class Drop {
        @Getter int dropId;
        @Getter int previewReward;
    }

    public static class RefreshCond {
        @Getter String type;
        @Getter List<Integer> param;
    }
}
