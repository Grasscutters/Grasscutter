package emu.grasscutter.game.shop;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.DataLoader;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.ShopGoodsData;
import emu.grasscutter.server.game.BaseGameSystem;
import emu.grasscutter.server.game.GameServer;
import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import static emu.grasscutter.config.Configuration.*;

import java.util.ArrayList;
import java.util.List;

public class ShopSystem extends BaseGameSystem {
    private final Int2ObjectMap<List<ShopInfo>> shopData;
    private final List<ShopChestTable> shopChestData;
    private final List<ShopChestBatchUseTable> shopChestBatchUseData;

    private static final int REFRESH_HOUR = 4; // In GMT+8 server
    private static final String TIME_ZONE = "Asia/Shanghai"; // GMT+8 Timezone

    public ShopSystem(GameServer server) {
        super(server);
        this.shopData = new Int2ObjectOpenHashMap<>();
        this.shopChestData = new ArrayList<>();
        this.shopChestBatchUseData = new ArrayList<>();
        this.load();
    }

    public Int2ObjectMap<List<ShopInfo>> getShopData() {
        return shopData;
    }

    public List<ShopChestTable> getShopChestData() {
        return shopChestData;
    }

    public List<ShopChestBatchUseTable> getShopChestBatchUseData() {
        return shopChestBatchUseData;
    }

    public static int getShopNextRefreshTime(ShopInfo shopInfo) {
        return switch (shopInfo.getShopRefreshType()) {
            case SHOP_REFRESH_DAILY -> Utils.getNextTimestampOfThisHour(REFRESH_HOUR, TIME_ZONE, shopInfo.getShopRefreshParam());
            case SHOP_REFRESH_WEEKLY ->  Utils.getNextTimestampOfThisHourInNextWeek(REFRESH_HOUR, TIME_ZONE, shopInfo.getShopRefreshParam());
            case SHOP_REFRESH_MONTHLY -> Utils.getNextTimestampOfThisHourInNextMonth(REFRESH_HOUR, TIME_ZONE, shopInfo.getShopRefreshParam());
            default -> 0;
        };
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
        getShopChestData().clear();
        try {
            List<ShopChestTable> shopChestTableList = DataLoader.loadList("ShopChest.json", ShopChestTable.class);
            if (shopChestTableList.size() > 0) {
                getShopChestData().addAll(shopChestTableList);
                Grasscutter.getLogger().debug("ShopChest data successfully loaded.");
            } else {
                Grasscutter.getLogger().error("Unable to load ShopChest data. ShopChest data size is 0.");
            }
        } catch (Exception e) {
            Grasscutter.getLogger().error("Unable to load ShopChest data.", e);
        }
    }

    private void loadShopChestBatchUse() {
        getShopChestBatchUseData().clear();
        try {
            List<ShopChestBatchUseTable> shopChestBatchUseTableList = DataLoader.loadList("ShopChestBatchUse.json", ShopChestBatchUseTable.class);
            if (shopChestBatchUseTableList.size() > 0) {
                getShopChestBatchUseData().addAll(shopChestBatchUseTableList);
                Grasscutter.getLogger().debug("ShopChestBatchUse data successfully loaded.");
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
