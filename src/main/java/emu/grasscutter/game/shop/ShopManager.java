package emu.grasscutter.game.shop;

import com.google.gson.reflect.TypeToken;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.data.def.ShopGoodsData;
import emu.grasscutter.net.proto.ItemParamOuterClass;
import emu.grasscutter.net.proto.ShopGoodsOuterClass;
import emu.grasscutter.server.game.GameServer;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ShopManager {
	private final GameServer server;

	public Int2ObjectMap<List<ShopInfo>> getShopData() {
		return shopData;
	}

	private final Int2ObjectMap<List<ShopInfo>> shopData;

	public ShopManager(GameServer server) {
		this.server = server;
		this.shopData = new Int2ObjectOpenHashMap<>();
		this.load();
	}

	public synchronized void load() {
		try (FileReader fileReader = new FileReader(Grasscutter.getConfig().DATA_FOLDER + "Shop.json")) {
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

			if (Grasscutter.getConfig().getGameServerOptions().EnableOfficialShop) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public GameServer getServer() {
		return server;
	}
}
