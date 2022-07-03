package emu.grasscutter.game.gacha;

import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.GachaInfoOuterClass.GachaInfo;
import emu.grasscutter.net.proto.GachaUpInfoOuterClass.GachaUpInfo;
import emu.grasscutter.utils.Utils;
import lombok.Getter;

import static emu.grasscutter.Configuration.*;

public class GachaBanner {
	@Getter private int gachaType;
	@Getter private int scheduleId;
	@Getter private String prefabPath;
	@Getter private String previewPrefabPath;
	@Getter private String titlePath;
	private int costItemId = 0;
	private int costItemAmount = 1;
	private int costItemId10 = 0;
	private int costItemAmount10 = 10;
	@Getter private int beginTime;
	@Getter private int endTime;
	@Getter private int sortId;
	@Getter private int gachaTimesLimit = Integer.MAX_VALUE;
	private int[] rateUpItems4 = {};
	private int[] rateUpItems5 = {};
	@Getter private int[] fallbackItems3 = {11301, 11302, 11306, 12301, 12302, 12305, 13303, 14301, 14302, 14304, 15301, 15302, 15304};
	@Getter private int[] fallbackItems4Pool1 = {1014, 1020, 1023, 1024, 1025, 1027, 1031, 1032, 1034, 1036, 1039, 1043, 1044, 1045, 1048, 1053, 1055, 1056, 1064};
	@Getter private int[] fallbackItems4Pool2 = {11401, 11402, 11403, 11405, 12401, 12402, 12403, 12405, 13401, 13407, 14401, 14402, 14403, 14409, 15401, 15402, 15403, 15405};
	@Getter private int[] fallbackItems5Pool1 = {1003, 1016, 1042, 1035, 1041};
	@Getter private int[] fallbackItems5Pool2 = {11501, 11502, 12501, 12502, 13502, 13505, 14501, 14502, 15501, 15502};
	@Getter private boolean removeC6FromPool = false;
	@Getter private boolean autoStripRateUpFromFallback = true;
	private int[][] weights4 = {{1,510}, {8,510}, {10,10000}};
	private int[][] weights5 = {{1,75}, {73,150}, {90,10000}};
	private int[][] poolBalanceWeights4 = {{1,255}, {17,255}, {21,10455}};
	private int[][] poolBalanceWeights5 = {{1,30}, {147,150}, {181,10230}};
	private int eventChance4 = 50; // Chance to win a featured event item
	private int eventChance5 = 50; // Chance to win a featured event item
	@Getter private BannerType bannerType = BannerType.STANDARD;

	// Kinda wanna deprecate these but they're in people's configs
	private int[] rateUpItems1 = {};
	private int[] rateUpItems2 = {};
	private int eventChance = -1;
	private int costItem = 0;
	@Getter private int wishMaxProgress = 2;

	public ItemParamData getCost(int numRolls) {
		return switch (numRolls) {
			case 10 -> new ItemParamData((costItemId10 > 0) ? costItemId10 : getCostItem(), costItemAmount10);
			default -> new ItemParamData(getCostItem(), costItemAmount * numRolls);
		};
	}

	public int getCostItem() {
		return (costItem > 0) ? costItem : costItemId;
	}

	public int[] getRateUpItems4() {
		return (rateUpItems2.length > 0) ? rateUpItems2 : rateUpItems4;
	}
	public int[] getRateUpItems5() {
		return (rateUpItems1.length > 0) ? rateUpItems1 : rateUpItems5;
	}

	public boolean hasEpitomized() {
		return bannerType.equals(BannerType.WEAPON);
	}

	public int getWeight(int rarity, int pity) {
		return switch(rarity) {
			case 4 -> Utils.lerp(pity, weights4);
			default -> Utils.lerp(pity, weights5);
		};
	}

	public int getPoolBalanceWeight(int rarity, int pity) {
		return switch(rarity) {
			case 4 -> Utils.lerp(pity, poolBalanceWeights4);
			default -> Utils.lerp(pity, poolBalanceWeights5);
		};
	}

	public int getEventChance(int rarity) {
		return switch(rarity) {
			case 4 -> eventChance4;
			default -> (eventChance > -1) ? eventChance : eventChance5;
		};
	}
	
	public GachaInfo toProto(Player player) {
		// TODO: use other Nonce/key insteadof session key to ensure the overall security for the player
		String sessionKey = player.getAccount().getSessionKey();
		
		String record = "http" + (HTTP_ENCRYPTION.useInRouting ? "s" : "") + "://"
						+ lr(HTTP_INFO.accessAddress, HTTP_INFO.bindAddress) + ":"
						+ lr(HTTP_INFO.accessPort, HTTP_INFO.bindPort)
						+ "/gacha?s=" + sessionKey + "&gachaType=" + gachaType;
		String details = "http" + (HTTP_ENCRYPTION.useInRouting ? "s" : "") + "://"
						+ lr(HTTP_INFO.accessAddress, HTTP_INFO.bindAddress) + ":"
						+ lr(HTTP_INFO.accessPort, HTTP_INFO.bindPort)
						+ "/gacha/details?s=" + sessionKey + "&scheduleId=" + scheduleId;

		// Grasscutter.getLogger().info("record = " + record);
		ItemParamData costItem1 = this.getCost(1);
		ItemParamData costItem10 = this.getCost(10);
		PlayerGachaBannerInfo gachaInfo = player.getGachaInfo().getBannerInfo(this);
		int leftGachaTimes = switch(gachaTimesLimit) {
			case Integer.MAX_VALUE -> Integer.MAX_VALUE;
			default -> Math.max(gachaTimesLimit - gachaInfo.getTotalPulls(), 0);
		};
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
	            .setLeftGachaTimes(leftGachaTimes)
	            .setGachaTimesLimit(gachaTimesLimit)
	            .setGachaSortId(this.getSortId());

		if(hasEpitomized()) {
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
		STANDARD, EVENT, WEAPON;
	}
}
