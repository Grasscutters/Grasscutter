package emu.grasscutter.game.shop;

import static emu.grasscutter.config.Configuration.GAME_OPTIONS;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.*;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.data.excels.ShopGoodsData;
import emu.grasscutter.server.game.*;
import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.*;
import java.util.*;

public class ShopSystem extends BaseGameSystem {
    private static final int REFRESH_HOUR = 4; // In GMT+8 server
    private static final String TIME_ZONE = "Asia/Shanghai"; // GMT+8 Timezone
    private final Int2ObjectMap<List<ShopInfo>> shopData;
    private final Int2ObjectMap<List<ItemParamData>> shopChestData;

    public ShopSystem(GameServer server) {
        super(server);
        this.shopData = new Int2ObjectOpenHashMap<>();
        this.shopChestData = new Int2ObjectOpenHashMap<>();
        this.load();
    }

    public static int getShopNextRefreshTime(ShopInfo shopInfo) {
        return switch (shopInfo.getShopRefreshType()) {
            case SHOP_REFRESH_DAILY -> Utils.getNextTimestampOfThisHour(
                    REFRESH_HOUR, TIME_ZONE, shopInfo.getShopRefreshParam());
            case SHOP_REFRESH_WEEKLY -> Utils.getNextTimestampOfThisHourInNextWeek(
                    REFRESH_HOUR, TIME_ZONE, shopInfo.getShopRefreshParam());
            case SHOP_REFRESH_MONTHLY -> Utils.getNextTimestampOfThisHourInNextMonth(
                    REFRESH_HOUR, TIME_ZONE, shopInfo.getShopRefreshParam());
            default -> 0;
        };
    }

    public Int2ObjectMap<List<ShopInfo>> getShopData() {
        return shopData;
    }

    public List<ItemParamData> getShopChestData(int chestId) {
        return this.shopChestData.get(chestId);
    }

    private void loadShop() {
        getShopData().clear();
        try {
            List<ShopTable> banners = DataLoader.loadList("Shop.json", ShopTable.class);
            if (banners.size() > 0) {
                for (ShopTable shopTable : banners) {
                    shopTable.getItems().forEach(ShopInfo::removeVirtualCosts);
                    getShopData().put(shopTable.getShopId(), shopTable.getItems());
                }
                Grasscutter.getLogger().debug("Shop data successfully loaded.");
            } else {
                Grasscutter.getLogger().error("Unable to load shop data. Shop data size is 0.");
            }

            if (GAME_OPTIONS.enableShopItems) {
                GameData.getShopGoodsDataEntries()
                        .forEach(
                                (k, v) -> {
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
        shopChestData.clear();
        try {
            Map<Integer, String> chestMap =
                    DataLoader.loadMap("ShopChest.v2.json", Integer.class, String.class);
            chestMap.forEach(
                    (chestId, itemStr) -> {
                        if (itemStr.isEmpty()) return;
                        var entries = itemStr.split(",");
                        var list = new ArrayList<ItemParamData>(entries.length);
                        for (var entry : entries) {
                            var idAndCount = entry.split(":");
                            int id = Integer.parseInt(idAndCount[0]);
                            int count = Integer.parseInt(idAndCount[1]);
                            list.add(new ItemParamData(id, count));
                        }
                        this.shopChestData.put((int) chestId, list);
                    });
            Grasscutter.getLogger().debug("Loaded " + chestMap.size() + " ShopChest entries.");
        } catch (Exception e) {
            Grasscutter.getLogger().error("Unable to load ShopChest data.", e);
        }
    }

    public synchronized void load() {
        loadShop();
        loadShopChest();
    }

    public GameServer getServer() {
        return server;
    }
}
