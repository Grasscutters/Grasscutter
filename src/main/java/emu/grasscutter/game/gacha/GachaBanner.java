package emu.grasscutter.game.gacha;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.net.proto.GachaInfoOuterClass.GachaInfo;
import emu.grasscutter.net.proto.GachaUpInfoOuterClass.GachaUpInfo;

public class GachaBanner {
	private int gachaType;
	private int scheduleId;
	private String prefabPath;
	private String previewPrefabPath;
	private String titlePath;
	private int costItem;
	private int tenWishCost = 10;
	private int beginTime;
	private int endTime;
	private int sortId;
	private int[] yellowAvatarList;
	private int[] yellowWeaponList;
	private int[] purpleAvatarList;
	private int[] purpleWeaponList;
	private int[] blueWeaponList;
	private int[] rateUpItems1;
	private int[] rateUpItems2;
	private int baseYellowWeight = 60; // Max 10000
	private int basePurpleWeight = 510; // Max 10000
	private int eventChance = 50; // Chance to win a featured event item
	private int softPity = 75;
	private int hardPity = 90;
	private BannerType bannerType = BannerType.STANDARD;
	
	public int getGachaType() {
		return gachaType;
	}

	public BannerType getBannerType() {
		return bannerType;
	}

	public int getScheduleId() {
		return scheduleId;
	}

	public String getPrefabPath() {
		return prefabPath;
	}

	public String getPreviewPrefabPath() {
		return previewPrefabPath;
	}

	public String getTitlePath() {
		return titlePath;
	}

	public int getCostItem() {
		return costItem;
	}
	
	public int getTenWishCost() {
		return tenWishCost;
	}

	public int getBeginTime() {
		return beginTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public int getSortId() {
		return sortId;
	}

	public int getBaseYellowWeight() {
		return baseYellowWeight;
	}

	public int getBasePurpleWeight() {
		return basePurpleWeight;
	}
	
	public int[] getYellowAvatarList() {
		return yellowAvatarList;
	}
	
	public int[] getYellowWeaponList() {
		return yellowWeaponList;
	}
	
	public int[] getPurpleAvatarList() {
		return purpleAvatarList;
	}
	
	public int[] getPurpleWeaponList() {
		return purpleWeaponList;
	}
	
	public int[] getBlueWeaponList() {
		return blueWeaponList;
	}

	public int[] getRateUpItems1() {
		return rateUpItems1;
	}

	public int[] getRateUpItems2() {
		return rateUpItems2;
	}
	
	public int getSoftPity() {
		return softPity - 1;
	}

	public int getHardPity() {
		return hardPity - 1;
	}

	public int getEventChance() {
		return eventChance;
	}

	@Deprecated
	public GachaInfo toProto() {
		return toProto("");
	}
	public GachaInfo toProto(String sessionKey) {
		String record = "http" + (Grasscutter.getConfig().getDispatchOptions().FrontHTTPS ? "s" : "") + "://"
						+ (Grasscutter.getConfig().getDispatchOptions().PublicIp.isEmpty() ? 
							Grasscutter.getConfig().getDispatchOptions().Ip : 
							Grasscutter.getConfig().getDispatchOptions().PublicIp)
						+ ":"
						+ Integer.toString(Grasscutter.getConfig().getDispatchOptions().PublicPort == 0 ?
							Grasscutter.getConfig().getDispatchOptions().Port : 
							Grasscutter.getConfig().getDispatchOptions().PublicPort)
						+ "/gacha?s=" + sessionKey + "&gachaType=" + gachaType;
		// Grasscutter.getLogger().info("record = " + record);
		GachaInfo.Builder info = GachaInfo.newBuilder()
				.setGachaType(this.getGachaType())
				.setScheduleId(this.getScheduleId())
				.setBeginTime(this.getBeginTime())
				.setEndTime(this.getEndTime())
				.setCostItemId(this.getCostItem())
	            .setCostItemNum(1)
	            .setGachaPrefabPath(this.getPrefabPath())
	            .setGachaPreviewPrefabPath(this.getPreviewPrefabPath())
	            .setGachaProbUrl(record)
	            .setGachaProbUrlOversea(record)
	            .setGachaRecordUrl(record)
	            .setGachaRecordUrlOversea(record)
	            .setTenCostItemId(this.getCostItem())
	            .setTenCostItemNum(this.getTenWishCost()) // Allows for custom 10-wish costs for discounted banners
	            .setLeftGachaTimes(Integer.MAX_VALUE)
	            .setGachaTimesLimit(Integer.MAX_VALUE)
	            .setGachaSortId(this.getSortId());
		
		if (this.getTitlePath() != null) {
			info.setGachaTitlePath(this.getTitlePath());
		}
	
		if (this.getYellowAvatarList().length > 0) {
			GachaUpInfo.Builder upInfo = GachaUpInfo.newBuilder().setItemParentType(1);
			
			for (int id : getYellowAvatarList()) {
				upInfo.addItemIdList(id);
				info.addMainNameId(id);
			}
			
			info.addGachaUpInfoList(upInfo);
		}
		
		if (this.getYellowWeaponList().length > 0) {
			GachaUpInfo.Builder upInfo = GachaUpInfo.newBuilder().setItemParentType(1);
			
			for (int id : getYellowWeaponList()) {
				upInfo.addItemIdList(id);
				info.addMainNameId(id);
			}
			
			info.addGachaUpInfoList(upInfo);
		}
		
		if (this.getPurpleAvatarList().length > 0) {
			GachaUpInfo.Builder upInfo = GachaUpInfo.newBuilder().setItemParentType(1);
			
			for (int id : getPurpleAvatarList()) {
				upInfo.addItemIdList(id);
				info.addMainNameId(id);
			}
			
			info.addGachaUpInfoList(upInfo);
		}
		
		if (this.getPurpleWeaponList().length > 0) {
			GachaUpInfo.Builder upInfo = GachaUpInfo.newBuilder().setItemParentType(1);
			
			for (int id : getPurpleWeaponList()) {
				upInfo.addItemIdList(id);
				info.addMainNameId(id);
			}
			
			info.addGachaUpInfoList(upInfo);
		}
		
		if (this.getBlueWeaponList().length > 0) {
			GachaUpInfo.Builder upInfo = GachaUpInfo.newBuilder().setItemParentType(1);
			
			for (int id : getBlueWeaponList()) {
				upInfo.addItemIdList(id);
				info.addMainNameId(id);
			}
			
			info.addGachaUpInfoList(upInfo);
		}
		
		if (this.getRateUpItems1().length > 0) {
			GachaUpInfo.Builder upInfo = GachaUpInfo.newBuilder().setItemParentType(1);
			
			for (int id : getRateUpItems1()) {
				upInfo.addItemIdList(id);
				info.addMainNameId(id);
			}
			
			info.addGachaUpInfoList(upInfo);
		}
		
		if (this.getRateUpItems2().length > 0) {
			GachaUpInfo.Builder upInfo = GachaUpInfo.newBuilder().setItemParentType(2);
			
			for (int id : getRateUpItems2()) {
				upInfo.addItemIdList(id);
				if (info.getSubNameIdCount() == 0) {
					info.addSubNameId(id);
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
