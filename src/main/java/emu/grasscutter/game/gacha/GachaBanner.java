package emu.grasscutter.game.gacha;

import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.GachaInfoOuterClass.GachaInfo;
import emu.grasscutter.net.proto.GachaUpInfoOuterClass.GachaUpInfo;
import emu.grasscutter.utils.Utils;

import static emu.grasscutter.Configuration.*;

public class GachaBanner {
    private int gachaType;
    private int scheduleId;
    private String prefabPath;
    private String previewPrefabPath;
    private String titlePath;
    private int costItemId = 0;
    private int costItemAmount = 1;
    private int costItemId10 = 0;
    private int costItemAmount10 = 10;
    private int beginTime;
    private int endTime;
    private int sortId;
    private int[] rateUpItems4 = {};
    private int[] rateUpItems5 = {};
    private int[] fallbackItems3 = {11301, 11302, 11306, 12301, 12302, 12305, 13303, 14301, 14302, 14304, 15301, 15302, 15304};
    private int[] fallbackItems4Pool1 = {1014, 1020, 1023, 1024, 1025, 1027, 1031, 1032, 1034, 1036, 1039, 1043, 1044, 1045, 1048, 1053, 1055, 1056, 1064};
    private int[] fallbackItems4Pool2 = {11401, 11402, 11403, 11405, 12401, 12402, 12403, 12405, 13401, 13407, 14401, 14402, 14403, 14409, 15401, 15402, 15403, 15405};
    private int[] fallbackItems5Pool1 = {1003, 1016, 1042, 1035, 1041};
    private int[] fallbackItems5Pool2 = {11501, 11502, 12501, 12502, 13502, 13505, 14501, 14502, 15501, 15502};
    private boolean removeC6FromPool = false;
    private boolean autoStripRateUpFromFallback = true;
    private int[][] weights4 = {{1, 510}, {8, 510}, {10, 10000}};
    private int[][] weights5 = {{1, 75}, {73, 150}, {90, 10000}};
    private int[][] poolBalanceWeights4 = {{1, 255}, {17, 255}, {21, 10455}};
    private int[][] poolBalanceWeights5 = {{1, 30}, {147, 150}, {181, 10230}};
    private int eventChance4 = 50; // Chance to win a featured event item
    private int eventChance5 = 50; // Chance to win a featured event item
    private BannerType bannerType = BannerType.STANDARD;

    // Kinda wanna deprecate these but they're in people's configs
    private int[] rateUpItems1 = {};
    private int[] rateUpItems2 = {};
    private int eventChance = -1;
    private int costItem = 0;
    private int wishMaxProgress = 2;

    public int getGachaType() {
        return this.gachaType;
    }

    public BannerType getBannerType() {
        return this.bannerType;
    }

    public int getScheduleId() {
        return this.scheduleId;
    }

    public String getPrefabPath() {
        return this.prefabPath;
    }

    public String getPreviewPrefabPath() {
        return this.previewPrefabPath;
    }

    public String getTitlePath() {
        return this.titlePath;
    }

    public ItemParamData getCost(int numRolls) {
        return switch (numRolls) {
            case 10 -> new ItemParamData((this.costItemId10 > 0) ? this.costItemId10 : this.getCostItem(), this.costItemAmount10);
            default -> new ItemParamData(this.getCostItem(), this.costItemAmount * numRolls);
        };
    }

    public int getCostItem() {
        return (this.costItem > 0) ? this.costItem : this.costItemId;
    }

    public int getBeginTime() {
        return this.beginTime;
    }

    public int getEndTime() {
        return this.endTime;
    }

    public int getSortId() {
        return this.sortId;
    }

    public int[] getRateUpItems4() {
        return (this.rateUpItems2.length > 0) ? this.rateUpItems2 : this.rateUpItems4;
    }

    public int[] getRateUpItems5() {
        return (this.rateUpItems1.length > 0) ? this.rateUpItems1 : this.rateUpItems5;
    }

    public int[] getFallbackItems3() {
        return this.fallbackItems3;
    }

    public int[] getFallbackItems4Pool1() {
        return this.fallbackItems4Pool1;
    }

    public int[] getFallbackItems4Pool2() {
        return this.fallbackItems4Pool2;
    }

    public int[] getFallbackItems5Pool1() {
        return this.fallbackItems5Pool1;
    }

    public int[] getFallbackItems5Pool2() {
        return this.fallbackItems5Pool2;
    }

    public boolean getRemoveC6FromPool() {
        return this.removeC6FromPool;
    }

    public boolean getAutoStripRateUpFromFallback() {
        return this.autoStripRateUpFromFallback;
    }

    public int getWishMaxProgress() {
        return this.wishMaxProgress;
    }

    public boolean hasEpitomized() {
        return this.bannerType.equals(BannerType.WEAPON);
    }

    public int getWeight(int rarity, int pity) {
        return switch (rarity) {
            case 4 -> Utils.lerp(pity, this.weights4);
            default -> Utils.lerp(pity, this.weights5);
        };
    }

    public int getPoolBalanceWeight(int rarity, int pity) {
        return switch (rarity) {
            case 4 -> Utils.lerp(pity, this.poolBalanceWeights4);
            default -> Utils.lerp(pity, this.poolBalanceWeights5);
        };
    }

    public int getEventChance(int rarity) {
        return switch (rarity) {
            case 4 -> this.eventChance4;
            default -> (this.eventChance > -1) ? this.eventChance : this.eventChance5;
        };
    }

    public GachaInfo toProto(Player player) {
        // TODO: use other Nonce/key insteadof session key to ensure the overall security for the player
        String sessionKey = player.getAccount().getSessionKey();

        String record = "http" + (HTTP_ENCRYPTION.useInRouting ? "s" : "") + "://"
            + lr(HTTP_INFO.accessAddress, HTTP_INFO.bindAddress) + ":"
            + lr(HTTP_INFO.accessPort, HTTP_INFO.bindPort)
            + "/gacha?s=" + sessionKey + "&gachaType=" + this.gachaType;
        String details = "http" + (HTTP_ENCRYPTION.useInRouting ? "s" : "") + "://"
            + lr(HTTP_INFO.accessAddress, HTTP_INFO.bindAddress) + ":"
            + lr(HTTP_INFO.accessPort, HTTP_INFO.bindPort)
            + "/gacha/details?s=" + sessionKey + "&scheduleId=" + this.scheduleId;

        // Grasscutter.getLogger().info("record = " + record);
        ItemParamData costItem1 = this.getCost(1);
        ItemParamData costItem10 = this.getCost(10);
        GachaInfo.Builder info = GachaInfo.newBuilder()
            .setGachaType(this.getGachaType())
            .setScheduleId(this.getScheduleId())
            .setBeginTime(this.getBeginTime())
            .setEndTime(this.getEndTime())
            .setCostItemId(costItem1.getId())
            .setCostItemNum(costItem1.getCount())
            .setTenCostItemId(costItem10.getId())
            .setTenCostItemNum(costItem10.getCount())
            .setGachaPrefabPath(this.getPrefabPath())
            .setGachaPreviewPrefabPath(this.getPreviewPrefabPath())
            .setGachaProbUrl(details)
            .setGachaProbUrlOversea(details)
            .setGachaRecordUrl(record)
            .setGachaRecordUrlOversea(record)
            .setLeftGachaTimes(Integer.MAX_VALUE)
            .setGachaTimesLimit(Integer.MAX_VALUE)
            .setGachaSortId(this.getSortId());

        if (this.hasEpitomized()) {
            PlayerGachaBannerInfo gachaInfo = player.getGachaInfo().getBannerInfo(this);

            info.setWishItemId(gachaInfo.getWishItemId())
                .setWishProgress(gachaInfo.getFailedChosenItemPulls())
                .setWishMaxProgress(this.getWishMaxProgress());
        }

        if (this.getTitlePath() != null) {
            info.setTitleTextmap(this.getTitlePath());
        }

        if (this.getRateUpItems5().length > 0) {
            GachaUpInfo.Builder upInfo = GachaUpInfo.newBuilder().setItemParentType(1);

            for (int id : this.getRateUpItems5()) {
                upInfo.addItemIdList(id);
                info.addDisplayUp5ItemList(id);
            }

            info.addGachaUpInfoList(upInfo);
        }

        if (this.getRateUpItems4().length > 0) {
            GachaUpInfo.Builder upInfo = GachaUpInfo.newBuilder().setItemParentType(2);

            for (int id : this.getRateUpItems4()) {
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
        STANDARD, EVENT, WEAPON
    }
}
