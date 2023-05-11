package emu.grasscutter.game.gacha;

import static emu.grasscutter.config.Configuration.*;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.GachaInfoOuterClass.GachaInfo;
import emu.grasscutter.net.proto.GachaUpInfoOuterClass.GachaUpInfo;
import emu.grasscutter.utils.Utils;
import lombok.Getter;

public class GachaBanner {
    // Constants used by the BannerType enum
    static final int[][] DEFAULT_WEIGHTS_4 = {{1, 510}, {8, 510}, {10, 10000}};
    static final int[][] DEFAULT_WEIGHTS_4_WEAPON = {{1, 600}, {7, 600}, {8, 6600}, {10, 12600}};
    static final int[][] DEFAULT_WEIGHTS_5 = {{1, 75}, {73, 150}, {90, 10000}};
    static final int[][] DEFAULT_WEIGHTS_5_CHARACTER = {{1, 80}, {73, 80}, {90, 10000}};
    static final int[][] DEFAULT_WEIGHTS_5_WEAPON = {{1, 100}, {62, 100}, {73, 7800}, {80, 10000}};
    static final int[] DEFAULT_FALLBACK_ITEMS_4_POOL_1 = {
        1014, 1020, 1023, 1024, 1025, 1027, 1031, 1032, 1034, 1036, 1039, 1043, 1044, 1045, 1048, 1053,
        1055, 1056, 1059, 1064, 1065, 1067, 1068, 1072
    }; // Default avatars
    static final int[] DEFAULT_FALLBACK_ITEMS_4_POOL_2 = {
        11401, 11402, 11403, 11405, 12401, 12402, 12403, 12405, 13401, 13407, 14401, 14402, 14403,
        14409, 15401, 15402, 15403, 15405
    }; // Default weapons
    static final int[] DEFAULT_FALLBACK_ITEMS_5_POOL_1 = {
        1003, 1016, 1042, 1035, 1041, 1069
    }; // Default avatars
    static final int[] DEFAULT_FALLBACK_ITEMS_5_POOL_2 = {
        11501, 11502, 12501, 12502, 13502, 13505, 14501, 14502, 15501, 15502
    }; // Default weapons
    static final int[] EMPTY_POOL = {}; // Used to remove a type of fallback
    @Getter int scheduleId = -1;
    @Getter int sortId = -1;
    @Getter private int gachaType = -1;
    @Getter private String prefabPath;
    @Getter private String previewPrefabPath;
    @Getter private String titlePath;
    private int costItemId = 0;
    private int costItemAmount = 1;
    private int costItemId10 = 0;
    private int costItemAmount10 = 10;
    @Getter private int beginTime = 0;
    @Getter private int endTime = 1924992000;
    @Getter private int gachaTimesLimit = Integer.MAX_VALUE;
    @Getter private int[] rateUpItems4 = {};
    @Getter private int[] rateUpItems5 = {};
    // This now handles default values for the fields below
    @Getter private BannerType bannerType = BannerType.STANDARD;
    // These don't change between banner types (apart from Standard having three extra 4star avatars)
    @Getter
    private int[] fallbackItems3 = {
        11301, 11302, 11306, 12301, 12302, 12305, 13303, 14301, 14302, 14304, 15301, 15302, 15304
    };

    @Getter private int[] fallbackItems4Pool1 = DEFAULT_FALLBACK_ITEMS_4_POOL_1;
    @Getter private int[] fallbackItems4Pool2 = DEFAULT_FALLBACK_ITEMS_4_POOL_2;
    // Different banner types have different defaults, see above for default values and the enum for
    // which are used where.
    @Getter private int[] fallbackItems5Pool1;
    @Getter private int[] fallbackItems5Pool2;
    private int[][] weights4;
    private int[][] weights5;
    private int eventChance4 = -1; // Chance to win a featured event item
    private int eventChance5 = -1; // Chance to win a featured event item
    //
    @Getter private boolean removeC6FromPool = false;

    @Getter
    private boolean autoStripRateUpFromFallback =
            true; // Ensures that featured items won't "double dip" into the losing pool

    private int[][] poolBalanceWeights4 = {
        {1, 255}, {17, 255}, {21, 10455}
    }; // Used to ensure that players won't go too many rolls without getting something from pool 1
    // (avatar) or pool 2 (weapon)
    private int[][] poolBalanceWeights5 = {{1, 30}, {147, 150}, {181, 10230}};
    @Getter private int wishMaxProgress = 2;

    // Deprecated fields that were tolerated in early May 2022 but have apparently still being
    // circulating in new custom configs
    // For now, throw up big scary errors on load telling people that they will be banned outright in
    // a future version
    @Deprecated private int[] rateUpItems1 = {};
    @Deprecated private int[] rateUpItems2 = {};
    @Deprecated private int eventChance = -1;
    @Deprecated private int costItem = 0;
    @Deprecated private int softPity = -1;
    @Deprecated private int hardPity = -1;
    @Deprecated private int minItemType = -1;
    @Deprecated private int maxItemType = -1;
    @Getter private boolean deprecated = false;
    @Getter private boolean disabled = false;

    private void warnDeprecated(String name, String replacement) {
        Grasscutter.getLogger()
                .error(
                        "Deprecated field found in Banners config: "
                                + name
                                + " was replaced back in early May 2022, use "
                                + replacement
                                + " instead. You MUST remove this field from your config.");
        this.deprecated = true;
    }

    public void onLoad() {
        // Handle deprecated configs
        if (eventChance != -1) warnDeprecated("eventChance", "eventChance4 & eventChance5");
        if (costItem != 0) warnDeprecated("costItem", "costItemId");
        if (softPity != -1) warnDeprecated("softPity", "weights5");
        if (hardPity != -1) warnDeprecated("hardPity", "weights5");
        if (minItemType != -1) warnDeprecated("minItemType", "fallbackItems[4,5]Pool[1,2]");
        if (maxItemType != -1) warnDeprecated("maxItemType", "fallbackItems[4,5]Pool[1,2]");
        if (rateUpItems1.length > 0) warnDeprecated("rateUpItems1", "rateUpItems5");
        if (rateUpItems2.length > 0) warnDeprecated("rateUpItems2", "rateUpItems4");

        // Handle default values
        if (this.previewPrefabPath != null
                && this.previewPrefabPath.equals("UI_Tab_" + this.prefabPath))
            Grasscutter.getLogger()
                    .error(
                            "Redundant field found in Banners config: previewPrefabPath does not need to be specified if it is identical to prefabPath prefixed with \"UI_Tab_\".");
        if (this.previewPrefabPath == null || this.previewPrefabPath.isEmpty())
            this.previewPrefabPath = "UI_Tab_" + this.prefabPath;
        if (this.gachaType < 0) this.gachaType = this.bannerType.gachaType;
        if (this.costItemId == 0) this.costItemId = this.bannerType.costItemId;
        if (this.costItemId10 == 0) this.costItemId10 = this.costItemId;
        if (this.weights4 == null) this.weights4 = this.bannerType.weights4;
        if (this.weights5 == null) this.weights5 = this.bannerType.weights5;
        if (this.eventChance4 < 0) this.eventChance4 = this.bannerType.eventChance4;
        if (this.eventChance5 < 0) this.eventChance5 = this.bannerType.eventChance5;
        if (this.fallbackItems5Pool1 == null)
            this.fallbackItems5Pool1 = this.bannerType.fallbackItems5Pool1;
        if (this.fallbackItems5Pool2 == null)
            this.fallbackItems5Pool2 = this.bannerType.fallbackItems5Pool2;
    }

    public ItemParamData getCost(int numRolls) {
        return switch (numRolls) {
            case 10 -> new ItemParamData(costItemId10, costItemAmount10);
            default -> new ItemParamData(costItemId, costItemAmount * numRolls);
        };
    }

    @Deprecated
    public int getCostItem() {
        return costItemId;
    }

    public boolean hasEpitomized() {
        return bannerType.equals(BannerType.WEAPON);
    }

    public int getWeight(int rarity, int pity) {
        return switch (rarity) {
            case 4 -> Utils.lerp(pity, weights4);
            default -> Utils.lerp(pity, weights5);
        };
    }

    public int getPoolBalanceWeight(int rarity, int pity) {
        return switch (rarity) {
            case 4 -> Utils.lerp(pity, poolBalanceWeights4);
            default -> Utils.lerp(pity, poolBalanceWeights5);
        };
    }

    public int getEventChance(int rarity) {
        return switch (rarity) {
            case 4 -> eventChance4;
            default -> eventChance5;
        };
    }

    public GachaInfo toProto(Player player) {
        // TODO: use other Nonce/key insteadof session key to ensure the overall security for the player
        String sessionKey = player.getAccount().getSessionKey();

        String record =
                "http"
                        + (HTTP_ENCRYPTION.useInRouting ? "s" : "")
                        + "://"
                        + lr(HTTP_INFO.accessAddress, HTTP_INFO.bindAddress)
                        + ":"
                        + lr(HTTP_INFO.accessPort, HTTP_INFO.bindPort)
                        + "/gacha?s="
                        + sessionKey
                        + "&gachaType="
                        + gachaType;
        String details =
                "http"
                        + (HTTP_ENCRYPTION.useInRouting ? "s" : "")
                        + "://"
                        + lr(HTTP_INFO.accessAddress, HTTP_INFO.bindAddress)
                        + ":"
                        + lr(HTTP_INFO.accessPort, HTTP_INFO.bindPort)
                        + "/gacha/details?s="
                        + sessionKey
                        + "&scheduleId="
                        + scheduleId;

        // Grasscutter.getLogger().info("record = " + record);
        PlayerGachaBannerInfo gachaInfo = player.getGachaInfo().getBannerInfo(this);
        int leftGachaTimes =
                switch (gachaTimesLimit) {
                    case Integer.MAX_VALUE -> Integer.MAX_VALUE;
                    default -> Math.max(gachaTimesLimit - gachaInfo.getTotalPulls(), 0);
                };
        GachaInfo.Builder info =
                GachaInfo.newBuilder()
                        .setGachaType(this.getGachaType())
                        .setScheduleId(this.getScheduleId())
                        .setBeginTime(this.getBeginTime())
                        .setEndTime(this.getEndTime())
                        .setCostItemId(this.costItemId)
                        .setCostItemNum(this.costItemAmount)
                        .setTenCostItemId(this.costItemId10)
                        .setTenCostItemNum(this.costItemAmount10)
                        .setGachaPrefabPath(this.getPrefabPath())
                        .setGachaPreviewPrefabPath(this.getPreviewPrefabPath())
                        .setGachaProbUrl(details)
                        .setGachaProbUrlOversea(details)
                        .setGachaRecordUrl(record)
                        .setGachaRecordUrlOversea(record)
                        .setLeftGachaTimes(leftGachaTimes)
                        .setGachaTimesLimit(gachaTimesLimit)
                        .setGachaSortId(this.getSortId());

        if (hasEpitomized()) {
            info.setWishItemId(gachaInfo.getWishItemId())
                    .setWishProgress(gachaInfo.getFailedChosenItemPulls())
                    .setWishMaxProgress(this.getWishMaxProgress());
        }

        if (this.getTitlePath() != null) {
            info.setTitleTextmap(this.getTitlePath());
        }

        if (this.getRateUpItems5().length > 0) {
            GachaUpInfo.Builder upInfo = GachaUpInfo.newBuilder().setItemParentType(1);

            for (int id : getRateUpItems5()) {
                upInfo.addItemIdList(id);
                info.addDisplayUp5ItemList(id);
            }

            info.addGachaUpInfoList(upInfo);
        }

        if (this.getRateUpItems4().length > 0) {
            GachaUpInfo.Builder upInfo = GachaUpInfo.newBuilder().setItemParentType(2);

            for (int id : getRateUpItems4()) {
                upInfo.addItemIdList(id);
                if (info.getDisplayUp4ItemListCount() == 0) {
                    info.addDisplayUp4ItemList(id);
                }
            }

            info.addGachaUpInfoList(upInfo);
        }

        return info.build();
    }

    public enum BannerType {
        STANDARD(
                200,
                224,
                DEFAULT_WEIGHTS_4,
                DEFAULT_WEIGHTS_5,
                50,
                50,
                DEFAULT_FALLBACK_ITEMS_5_POOL_1,
                DEFAULT_FALLBACK_ITEMS_5_POOL_2),
        BEGINNER(
                100,
                224,
                DEFAULT_WEIGHTS_4,
                DEFAULT_WEIGHTS_5,
                50,
                50,
                DEFAULT_FALLBACK_ITEMS_5_POOL_1,
                DEFAULT_FALLBACK_ITEMS_5_POOL_2),
        EVENT(
                301,
                223,
                DEFAULT_WEIGHTS_4,
                DEFAULT_WEIGHTS_5_CHARACTER,
                50,
                50,
                DEFAULT_FALLBACK_ITEMS_5_POOL_1,
                DEFAULT_FALLBACK_ITEMS_5_POOL_2), // Legacy value for CHARACTER
        CHARACTER(
                301,
                223,
                DEFAULT_WEIGHTS_4,
                DEFAULT_WEIGHTS_5_CHARACTER,
                50,
                50,
                DEFAULT_FALLBACK_ITEMS_5_POOL_1,
                EMPTY_POOL),
        CHARACTER2(
                400,
                223,
                DEFAULT_WEIGHTS_4,
                DEFAULT_WEIGHTS_5_CHARACTER,
                50,
                50,
                DEFAULT_FALLBACK_ITEMS_5_POOL_1,
                EMPTY_POOL),
        WEAPON(
                302,
                223,
                DEFAULT_WEIGHTS_4_WEAPON,
                DEFAULT_WEIGHTS_5_WEAPON,
                75,
                75,
                EMPTY_POOL,
                DEFAULT_FALLBACK_ITEMS_5_POOL_2);

        public final int gachaType;
        public final int costItemId;
        public final int[][] weights4;
        public final int[][] weights5;
        public final int eventChance4;
        public final int eventChance5;
        public final int[] fallbackItems5Pool1;
        public final int[] fallbackItems5Pool2;

        BannerType(
                int gachaType,
                int costItemId,
                int[][] weights4,
                int[][] weights5,
                int eventChance4,
                int eventChance5,
                int[] fallbackItems5Pool1,
                int[] fallbackItems5Pool2) {
            this.gachaType = gachaType;
            this.costItemId = costItemId;
            this.weights4 = weights4;
            this.weights5 = weights5;
            this.eventChance4 = eventChance4;
            this.eventChance5 = eventChance5;
            this.fallbackItems5Pool1 = fallbackItems5Pool1;
            this.fallbackItems5Pool2 = fallbackItems5Pool2;
        }
    }
}
