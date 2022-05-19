package emu.grasscutter.game.gacha;

import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.google.gson.reflect.TypeToken;

import com.sun.nio.file.SensitivityWatchEventModifier;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.DataLoader;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.data.def.ItemData;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.inventory.Inventory;
import emu.grasscutter.game.inventory.ItemType;
import emu.grasscutter.game.inventory.MaterialType;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.GachaItemOuterClass.GachaItem;
import emu.grasscutter.net.proto.GachaTransferItemOuterClass.GachaTransferItem;
import emu.grasscutter.net.proto.GetGachaInfoRspOuterClass.GetGachaInfoRsp;
import emu.grasscutter.net.proto.ItemParamOuterClass.ItemParam;
import emu.grasscutter.server.game.GameServer;
import emu.grasscutter.server.game.GameServerTickEvent;
import emu.grasscutter.server.packet.send.PacketDoGachaRsp;
import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import org.greenrobot.eventbus.Subscribe;

import static emu.grasscutter.Configuration.*;

public class GachaManager {
	private final GameServer server;
	private final Int2ObjectMap<GachaBanner> gachaBanners;
	private GetGachaInfoRsp cachedProto;
	WatchService watchService;
	
	private static final int starglitterId = 221;
	private static final int stardustId = 222;
	private int[] fallbackItems4Pool2Default = {11401, 11402, 11403, 11405, 12401, 12402, 12403, 12405, 13401, 13407, 14401, 14402, 14403, 14409, 15401, 15402, 15403, 15405};
	private int[] fallbackItems5Pool2Default = {11501, 11502, 12501, 12502, 13502, 13505, 14501, 14502, 15501, 15502};

	public GachaManager(GameServer server) {
		this.server = server;
		this.gachaBanners = new Int2ObjectOpenHashMap<>();
		this.load();
		this.startWatcher(server);
	}

	public GameServer getServer() {
		return server;
	}

	public Int2ObjectMap<GachaBanner> getGachaBanners() {
		return gachaBanners;
	}
	
	public int randomRange(int min, int max) {  // Both are inclusive
		return ThreadLocalRandom.current().nextInt(max - min + 1) + min;
	}
	
	public int getRandom(int[] array) {
		return array[randomRange(0, array.length - 1)];
	}
	
	public synchronized void load() {
		try (Reader fileReader = new InputStreamReader(DataLoader.load("Banners.json"))) {
			getGachaBanners().clear();
			List<GachaBanner> banners = Grasscutter.getGsonFactory().fromJson(fileReader, TypeToken.getParameterized(Collection.class, GachaBanner.class).getType());
			if(banners.size() > 0) {
				for (GachaBanner banner : banners) {
					getGachaBanners().put(banner.getGachaType(), banner);
				}
				Grasscutter.getLogger().info("Banners successfully loaded.");


				this.cachedProto = createProto();
			} else {
				Grasscutter.getLogger().error("Unable to load banners. Banners size is 0.");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class BannerPools {
		public int[] rateUpItems4;
		public int[] rateUpItems5;
		public int[] fallbackItems4Pool1;
		public int[] fallbackItems4Pool2;
		public int[] fallbackItems5Pool1;
		public int[] fallbackItems5Pool2;

		public BannerPools(GachaBanner banner) {
			rateUpItems4 = banner.getRateUpItems4();
			rateUpItems5 = banner.getRateUpItems5();
			fallbackItems4Pool1 = banner.getFallbackItems4Pool1();
			fallbackItems4Pool2 = banner.getFallbackItems4Pool2();
			fallbackItems5Pool1 = banner.getFallbackItems5Pool1();
			fallbackItems5Pool2 = banner.getFallbackItems5Pool2();

			if (banner.getAutoStripRateUpFromFallback()) {
				fallbackItems4Pool1 = Utils.setSubtract(fallbackItems4Pool1, rateUpItems4);
				fallbackItems4Pool2 = Utils.setSubtract(fallbackItems4Pool2, rateUpItems4);
				fallbackItems5Pool1 = Utils.setSubtract(fallbackItems5Pool1, rateUpItems5);
				fallbackItems5Pool2 = Utils.setSubtract(fallbackItems5Pool2, rateUpItems5);
			}
		}

		public void removeFromAllPools(int[] itemIds) {
			rateUpItems4 = Utils.setSubtract(rateUpItems4, itemIds);
			rateUpItems5 = Utils.setSubtract(rateUpItems5, itemIds);
			fallbackItems4Pool1 = Utils.setSubtract(fallbackItems4Pool1, itemIds);
			fallbackItems4Pool2 = Utils.setSubtract(fallbackItems4Pool2, itemIds);
			fallbackItems5Pool1 = Utils.setSubtract(fallbackItems5Pool1, itemIds);
			fallbackItems5Pool2 = Utils.setSubtract(fallbackItems5Pool2, itemIds);
		}
	}

	private synchronized int checkPlayerAvatarConstellationLevel(Player player, int itemId) {  // Maybe this would be useful in the Player class?
		ItemData itemData = GameData.getItemDataMap().get(itemId);
		if ((itemData == null) || (itemData.getMaterialType() != MaterialType.MATERIAL_AVATAR)){
			return -2;  // Not an Avatar
		}
		Avatar avatar = player.getAvatars().getAvatarById((itemId % 1000) + 10000000);
		if (avatar == null) {
			return -1;  // Doesn't have
		}
		// Constellation
		int constLevel = avatar.getCoreProudSkillLevel();
		GameItem constItem = player.getInventory().getInventoryTab(ItemType.ITEM_MATERIAL).getItemById(itemId + 100);
		constLevel += (constItem == null)? 0 : constItem.getCount();
		return constLevel;
	}

	private synchronized int[] removeC6FromPool(int[] itemPool, Player player) {
		IntList temp = new IntArrayList();
		for (int itemId : itemPool) {
			if (checkPlayerAvatarConstellationLevel(player, itemId) < 6) {
				temp.add(itemId);
			}
		}
		return temp.toIntArray();
	}

	private synchronized int drawRoulette(int[] weights, int cutoff) {
		// This follows the logic laid out in issue #183
		// Simple weighted selection with an upper bound for the roll that cuts off trailing entries
		// All weights must be >= 0
		int total = 0;
		for (int weight : weights) {
			if (weight < 0) {
				throw new IllegalArgumentException("Weights must be non-negative!");
			}
			total += weight;
		}
		int roll = ThreadLocalRandom.current().nextInt((total < cutoff)? total : cutoff);
		int subTotal = 0;
		for (int i=0; i<weights.length; i++) {
			subTotal += weights[i];
			if (roll < subTotal) {
				return i;
			}
		}
		// throw new IllegalStateException();
		return 0;  // This should only be reachable if total==0
	}

	private synchronized int doRarePull(int[] featured, int[] fallback1, int[] fallback2, int rarity, GachaBanner banner, PlayerGachaBannerInfo gachaInfo) {
		int itemId = 0;
		boolean pullFeatured = (gachaInfo.getFailedFeaturedItemPulls(rarity) >= 1)  // Lost previous coinflip
							|| (this.randomRange(1, 100) <= banner.getEventChance(rarity));  // Won this coinflip
		if (pullFeatured && (featured.length > 0)) {
			itemId = getRandom(featured);
			gachaInfo.setFailedFeaturedItemPulls(rarity, 0);
		} else {
			gachaInfo.addFailedFeaturedItemPulls(rarity, 1);
			if (fallback1.length < 1) {
				if (fallback2.length < 1) {
					itemId = getRandom((rarity==5)? fallbackItems5Pool2Default : fallbackItems4Pool2Default);
				} else {
					itemId = getRandom(fallback2);
				}
			} else if (fallback2.length < 1) {
				itemId = getRandom(fallback1);
			} else {  // Both pools are possible, use the pool balancer
				int pityPool1 = banner.getPoolBalanceWeight(rarity, gachaInfo.getPityPool(rarity, 1));
				int pityPool2 = banner.getPoolBalanceWeight(rarity, gachaInfo.getPityPool(rarity, 2));
				int chosenPool = switch ((pityPool1 >= pityPool2)? 1 : 0) {  // Larger weight must come first for the hard cutoff to function correctly
					case 1 -> 1 + drawRoulette(new int[] {pityPool1, pityPool2}, 10000);
					default -> 2 - drawRoulette(new int[] {pityPool2, pityPool1}, 10000);
				};
				itemId = switch (chosenPool) {
					case 1:
						gachaInfo.setPityPool(rarity, 1, 0);
						yield getRandom(fallback1);
					default:
						gachaInfo.setPityPool(rarity, 2, 0);
						yield getRandom(fallback2);
				};
			}
		}
		return itemId;
	}

	private synchronized int doPull(GachaBanner banner, PlayerGachaBannerInfo gachaInfo, BannerPools pools) {
		// Pre-increment all pity pools (yes this makes all calculations assume 1-indexed pity)
		gachaInfo.incPityAll();

		int[] weights = {banner.getWeight(5, gachaInfo.getPity5()), banner.getWeight(4, gachaInfo.getPity4()), 10000};
		int levelWon = 5 - drawRoulette(weights, 10000);

		return switch (levelWon) {
			case 5:
				gachaInfo.setPity5(0);
				yield doRarePull(pools.rateUpItems5, pools.fallbackItems5Pool1, pools.fallbackItems5Pool2, 5, banner, gachaInfo);
			case 4:
				gachaInfo.setPity4(0);
				yield doRarePull(pools.rateUpItems4, pools.fallbackItems4Pool1, pools.fallbackItems4Pool2, 4, banner, gachaInfo);
			default:
				yield getRandom(banner.getFallbackItems3());
		};
	}
	
	public synchronized void doPulls(Player player, int gachaType, int times) {
		// Sanity check
		if (times != 10 && times != 1) {
			return;
		} 
		Inventory inventory = player.getInventory();
		if (inventory.getInventoryTab(ItemType.ITEM_WEAPON).getSize() + times > inventory.getInventoryTab(ItemType.ITEM_WEAPON).getMaxCapacity()) {
			player.sendPacket(new PacketDoGachaRsp());
			return;
		}
		
		// Get banner
		GachaBanner banner = this.getGachaBanners().get(gachaType);
		if (banner == null) {
			player.sendPacket(new PacketDoGachaRsp());
			return;
		}

		// Spend currency
		ItemParamData cost = banner.getCost(times);
		if (cost.getCount() > 0 && !inventory.payItem(cost)) {
			player.sendPacket(new PacketDoGachaRsp());
			return;
		}
		
		// Add to character
		PlayerGachaBannerInfo gachaInfo = player.getGachaInfo().getBannerInfo(banner);
		BannerPools pools = new BannerPools(banner);
		List<GachaItem> list = new ArrayList<>();
		int stardust = 0, starglitter = 0;

		if (banner.getRemoveC6FromPool()) {  // The ultimate form of pity (non-vanilla)
			pools.rateUpItems4 = removeC6FromPool(pools.rateUpItems4, player);
			pools.rateUpItems5 = removeC6FromPool(pools.rateUpItems5, player);
			pools.fallbackItems4Pool1 = removeC6FromPool(pools.fallbackItems4Pool1, player);
			pools.fallbackItems4Pool2 = removeC6FromPool(pools.fallbackItems4Pool2, player);
			pools.fallbackItems5Pool1 = removeC6FromPool(pools.fallbackItems5Pool1, player);
			pools.fallbackItems5Pool2 = removeC6FromPool(pools.fallbackItems5Pool2, player);
		}
		
		for (int i = 0; i < times; i++) {
			// Roll
			int itemId = doPull(banner, gachaInfo, pools);
			ItemData itemData = GameData.getItemDataMap().get(itemId);
			if (itemData == null) {
				continue;  // Maybe we should bail out if an item fails instead of rolling the rest?
			}

			// Write gacha record
			GachaRecord gachaRecord = new GachaRecord(itemId, player.getUid(), gachaType);
			DatabaseHelper.saveGachaRecord(gachaRecord);
			
			// Create gacha item
			GachaItem.Builder gachaItem = GachaItem.newBuilder();
			int addStardust = 0, addStarglitter = 0;
			boolean isTransferItem = false;
			
			// Const check
			int constellation = checkPlayerAvatarConstellationLevel(player, itemId);
			switch (constellation) {
				case -2:  // Is weapon
					switch (itemData.getRankLevel()) {
						case 5 -> addStarglitter = 10;
						case 4 -> addStarglitter = 2;
						default -> addStardust = 15;
					}
					break;
				case -1:  // New character
					gachaItem.setIsGachaItemNew(true);
					break;
				default:
					if (constellation >= 6) {  // C6, give consolation starglitter
						addStarglitter = (itemData.getRankLevel()==5)? 25 : 5;
					} else {  // C0-C5, give constellation item
						if (banner.getRemoveC6FromPool() && constellation == 5) {  // New C6, remove it from the pools so we don't get C7 in a 10pull
							pools.removeFromAllPools(new int[] {itemId});
						}
						addStarglitter = (itemData.getRankLevel()==5)? 10 : 2;
						int constItemId = itemId + 100;
						GameItem constItem = inventory.getInventoryTab(ItemType.ITEM_MATERIAL).getItemById(constItemId);
						gachaItem.addTransferItems(GachaTransferItem.newBuilder().setItem(ItemParam.newBuilder().setItemId(constItemId).setCount(1)).setIsTransferItemNew(constItem == null));
						inventory.addItem(constItemId, 1);
					}
					isTransferItem = true;
					break;
			}

			// Create item
			GameItem item = new GameItem(itemData);
			gachaItem.setGachaItem(item.toItemParam());
			inventory.addItem(item);
			
			stardust += addStardust;
			starglitter += addStarglitter;
			
			if (addStardust > 0) {
				gachaItem.addTokenItemList(ItemParam.newBuilder().setItemId(stardustId).setCount(addStardust));
			}
			if (addStarglitter > 0) {
				ItemParam starglitterParam = ItemParam.newBuilder().setItemId(starglitterId).setCount(addStarglitter).build();
				if (isTransferItem) {
					gachaItem.addTransferItems(GachaTransferItem.newBuilder().setItem(starglitterParam));
				}
				gachaItem.addTokenItemList(starglitterParam);
			}
			
			list.add(gachaItem.build());
		}
		
		// Add stardust/starglitter
		if (stardust > 0) {
			inventory.addItem(stardustId, stardust);
		}
		if (starglitter > 0) {
			inventory.addItem(starglitterId, starglitter);
		}
		
		// Packets
		player.sendPacket(new PacketDoGachaRsp(banner, list));
	}

	private synchronized void startWatcher(GameServer server) {
		if(this.watchService == null) {
			try {
				this.watchService = FileSystems.getDefault().newWatchService();
				Path path = new File(DATA()).toPath();
				path.register(watchService, new WatchEvent.Kind[]{StandardWatchEventKinds.ENTRY_MODIFY}, SensitivityWatchEventModifier.HIGH);
			} catch (Exception e) {
				Grasscutter.getLogger().error("Unable to load the Gacha Manager Watch Service. If ServerOptions.watchGacha is true it will not auto-reload");
				e.printStackTrace();
			}
		} else {
			Grasscutter.getLogger().error("Cannot reinitialise watcher ");
		}
	}

	@Subscribe
	public synchronized void watchBannerJson(GameServerTickEvent tickEvent) {
		if(GAME_OPTIONS.watchGachaConfig) {
			try {
				WatchKey watchKey = watchService.take();

				for (WatchEvent<?> event : watchKey.pollEvents()) {
					final Path changed = (Path) event.context();
					if (changed.endsWith("Banners.json")) {
						Grasscutter.getLogger().info("Change detected with banners.json. Reloading gacha config");
						this.load();
					}
				}

				boolean valid = watchKey.reset();
				if (!valid) {
					Grasscutter.getLogger().error("Unable to reset Gacha Manager Watch Key. Auto-reload of banners.json will no longer work.");
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Deprecated
	private synchronized GetGachaInfoRsp createProto() {
		GetGachaInfoRsp.Builder proto = GetGachaInfoRsp.newBuilder().setGachaRandom(12345);
		
		for (GachaBanner banner : getGachaBanners().values()) {
			proto.addGachaInfoList(banner.toProto());
		}
				
		return proto.build();
	}

	private synchronized GetGachaInfoRsp createProto(String sessionKey) {
		GetGachaInfoRsp.Builder proto = GetGachaInfoRsp.newBuilder().setGachaRandom(12345);
		
		for (GachaBanner banner : getGachaBanners().values()) {
			proto.addGachaInfoList(banner.toProto(sessionKey));
		}
				
		return proto.build();
	}
	
	@Deprecated
	public GetGachaInfoRsp toProto() {
		if (this.cachedProto == null) {
			this.cachedProto = createProto();
		}
		return this.cachedProto;
	}

	public GetGachaInfoRsp toProto(String sessionKey) {
		return createProto(sessionKey);
	}
}
