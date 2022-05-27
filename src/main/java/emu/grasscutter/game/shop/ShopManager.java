package emu.grasscutter.game.shop;

import com.google.gson.reflect.TypeToken;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.DataLoader;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.data.def.ShopGoodsData;
import emu.grasscutter.server.game.GameServer;
import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static emu.grasscutter.Configuration.*;

public class ShopManager {
	private final GameServer server;

	public Int2ObjectMap<List<ShopInfo>> getShopData() {
		return shopData;
	}

	public List<ShopChestTable> getShopChestData() {
		return shopChestData;
	}

	public List<ShopChestBatchUseTable> getShopChestBatchUseData() {
		return shopChestBatchUseData;
	}

	private final Int2ObjectMap<List<ShopInfo>> shopData;
	private final List<ShopChestTable> shopChestData;
	private final List<ShopChestBatchUseTable> shopChestBatchUseData;

	public ShopManager(GameServer server) {
		this.server = server;
		this.shopData = new Int2ObjectOpenHashMap<>();
		this.shopChestData = new ArrayList<>();
		this.shopChestBatchUseData = new ArrayList<>();
		this.load();
	}

	private static final int REFRESH_HOUR = 4; // In GMT+8 server
	private static final String TIME_ZONE = "Asia/Shanghai"; // GMT+8 Timezone

	public static int getShopNextRefreshTime(ShopInfo shopInfo) {
		return switch (shopInfo.getShopRefreshType()) {
			case SHOP_REFRESH_DAILY -> Utils.getNextTimestampOfThisHour(REFRESH_HOUR, TIME_ZONE, shopInfo.getShopRefreshParam());
			case SHOP_REFRESH_WEEKLY ->  Utils.getNextTimestampOfThisHourInNextWeek(REFRESH_HOUR, TIME_ZONE, shopInfo.getShopRefreshParam());
			case SHOP_REFRESH_MONTHLY -> Utils.getNextTimestampOfThisHourInNextMonth(REFRESH_HOUR, TIME_ZONE, shopInfo.getShopRefreshParam());
			default -> 0;
		};
	}

	private void loadShop() {
		try (Reader fileReader = new InputStreamReader(DataLoader.load("Shop.json"))) {
			getShopData().clear();
			List<ShopTable> banners = Grasscutter.getGsonFactory().fromJson(fileReader, TypeToken.getParameterized(Collection.class, ShopTable.class).getType());
			if(banners.size() > 0) {
				for (ShopTable shopTable : banners) {
					for (ShopInfo cost : shopTable.getItems()) {
						if (cost.getCostItemList() != null) {
							Iterator<ItemParamData> iterator = cost.getCostItemList().iterator();
							while (iterator.hasNext()) {
								ItemParamData ipd = iterator.next();
								if (ipd.getId() == 201) {
									cost.setHcoin(cost.getHcoin() + ipd.getCount());
									iterator.remove();
								}
								if (ipd.getId() == 203) {
									cost.setMcoin(cost.getMcoin() + ipd.getCount());
									iterator.remove();
								}
							}
						}
					}
					getShopData().put(shopTable.getShopId(), shopTable.getItems());
				}
				Grasscutter.getLogger().info("Shop data successfully loaded.");
			} else {
				Grasscutter.getLogger().error("Unable to load shop data. Shop data size is 0.");
			}

			if (GAME_OPTIONS.enableShopItems) {
				GameData.getShopGoodsDataEntries().forEach((k, v) -> {
					if (!getShopData().containsKey(k.intValue()))
						getShopData().put(k.intValue(), new ArrayList<>());
					for (ShopGoodsData sgd : v) {
						var shopInfo = new ShopInfo(sgd);
						getShopData().get(k.intValue()).add(shopInfo);
					}
				});
			}
		} catch (Exception e) {
			Grasscutter.getLogger().error("Unable to load shop data.", e);
		}
	}

	private void loadShopChest() {
		try (Reader fileReader = new InputStreamReader(DataLoader.load("ShopChest.json"))) {
			getShopChestData().clear();
			List<ShopChestTable> shopChestTableList = Grasscutter.getGsonFactory().fromJson(fileReader, TypeToken.getParameterized(Collection.class, ShopChestTable.class).getType());
			if (shopChestTableList.size() > 0) {
				getShopChestData().addAll(shopChestTableList);
				Grasscutter.getLogger().info("ShopChest data successfully loaded.");
			} else {
				Grasscutter.getLogger().error("Unable to load ShopChest data. ShopChest data size is 0.");
			}
		} catch (Exception e) {
			Grasscutter.getLogger().error("Unable to load ShopChest data.", e);
		}
	}

	private void loadShopChestBatchUse() {
		try (Reader fileReader = new InputStreamReader(DataLoader.load("ShopChestBatchUse.json"))) {
			getShopChestBatchUseData().clear();
			List<ShopChestBatchUseTable> shopChestBatchUseTableList = Grasscutter.getGsonFactory().fromJson(fileReader, TypeToken.getParameterized(Collection.class, ShopChestBatchUseTable.class).getType());
			if (shopChestBatchUseTableList.size() > 0) {
				getShopChestBatchUseData().addAll(shopChestBatchUseTableList);
				Grasscutter.getLogger().info("ShopChestBatchUse data successfully loaded.");
			} else {
				Grasscutter.getLogger().error("Unable to load ShopChestBatchUse data. ShopChestBatchUse data size is 0.");
			}
		} catch (Exception e) {
			Grasscutter.getLogger().error("Unable to load ShopChestBatchUse data.", e);
		}
	}

	public synchronized void load() {
		loadShop();
		loadShopChest();
		loadShopChestBatchUse();
	}

	public GameServer getServer() {
		return server;
	}
}
