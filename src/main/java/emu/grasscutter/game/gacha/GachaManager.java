package emu.grasscutter.game.gacha;

import java.io.File;
import java.io.FileReader;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.google.gson.reflect.TypeToken;

import com.sun.nio.file.SensitivityWatchEventModifier;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.ItemData;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.gacha.GachaBanner.BannerType;
import emu.grasscutter.game.inventory.GameItem;
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
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import org.greenrobot.eventbus.Subscribe;

public class GachaManager {
	private final GameServer server;
	private final Int2ObjectMap<GachaBanner> gachaBanners;
	private GetGachaInfoRsp cachedProto;
	WatchService watchService;

	private int[] yellowAvatars = new int[] {1003, 1016, 1042, 1035, 1041};
	private int[] yellowWeapons = new int[] {11501, 11502, 12501, 12502, 13502, 13505, 14501, 14502, 15501, 15502};
	private int[] purpleAvatars = new int[] {1006, 1014, 1015, 1020, 1021, 1023, 1024, 1025, 1027, 1031, 1032, 1034, 1036, 1039, 1043, 1044, 1045, 1048, 1050, 1053, 1055, 1056, 1064};
	private int[] purpleWeapons = new int[] {11401, 11402, 11403, 11405, 12401, 12402, 12403, 12405, 13401, 13407, 14401, 14402, 14403, 14409, 15401, 15402, 15403, 15405};
	private int[] blueWeapons = new int[] {11301, 11302, 11306, 12301, 12302, 12305, 13303, 14301, 14302, 14304, 15301, 15302, 15304};
	
	private static int starglitterId = 221;
	private static int stardustId = 222;

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
	
	public int randomRange(int min, int max) {
		// [min, max)
		return ThreadLocalRandom.current().nextInt(max - min) + min;
	}
	
	public int getRandom(int[] array) {
		return array[randomRange(0, array.length)];
	}
	
	public synchronized void load() {
		try (FileReader fileReader = new FileReader(Grasscutter.getConfig().DATA_FOLDER + "Banners.json")) {
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

	private static int calculateChance(int base, int softPity,int hardPity,int t){
		if ( t < softPity) {
			return base;
		}
		float a = t - softPity + 1;
		float b = hardPity - softPity + 1;
		return base + 10000 - (int)(base*a/b);
	}
	
	public synchronized void doPulls(Player player, int gachaType, int times) {
		// Sanity check
		if (times != 10 && times != 1) {
			return;
		} 
		if (player.getInventory().getInventoryTab(ItemType.ITEM_WEAPON).getSize() + times > player.getInventory().getInventoryTab(ItemType.ITEM_WEAPON).getMaxCapacity()) {
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
		if (banner.getCostItem() > 0) {
			GameItem costItem = player.getInventory().getInventoryTab(ItemType.ITEM_MATERIAL).getItemById(banner.getCostItem());
			if (costItem == null || costItem.getCount() < times) {
				return;
			}
			
			player.getInventory().removeItem(costItem, times);
		}
		
		// Roll
		PlayerGachaBannerInfo gachaInfo = player.getGachaInfo().getBannerInfo(banner);
		IntList wonItems = new IntArrayList(times);
		
		for (int i = 0; i < times; i++) {
			int random = this.randomRange(0, 10000);
			int itemId = 0;
			gachaInfo.addPity5(1);
			gachaInfo.addPity4(1);
			int yellowChance = calculateChance(banner.getBaseYellowWeight(), banner.getSoftPity(), banner.getHardPity(), gachaInfo.getPity5());
			int purpleChance = calculateChance(banner.getBasePurpleWeight(), banner.getPrupleSoftPity(), banner.getPrupleHardPity(), gachaInfo.getPity4());
		
			if (random < yellowChance) {
				if (banner.getRateUpItems1().length > 0) {
					int eventChance = this.randomRange(0, 10000);
					
					if (eventChance < banner.getEventChance() || gachaInfo.getFailedFeaturedItemPulls() > 0) {
						itemId = getRandom(banner.getRateUpItems1());
						gachaInfo.setFailedFeaturedItemPulls(0);
					} else {
						// Lost the 50/50... rip
						gachaInfo.addFailedFeaturedItemPulls(1);
					}
				}
				
				if (itemId == 0) {
					int typeChance = this.randomRange(banner.getBannerType() == BannerType.WEAPON ? 1 : 0, banner.getBannerType() == BannerType.EVENT ? 1 : 2);
					if (typeChance == 0) {
						itemId = getRandom(this.yellowAvatars);
					} else {
						itemId = getRandom(this.yellowWeapons);
					}
				}
				// Pity
				gachaInfo.setPity5(0);
			} else if (random < purpleChance) {
				if (banner.getRateUpItems2().length > 0) {
					int eventChance = this.randomRange(0, 10000);
					if (eventChance < banner.getEventChance() || gachaInfo.getfailedFeaturedPrupleItemPulls() > 0) {
						itemId = getRandom(banner.getRateUpItems2());
						gachaInfo.setfailedFeaturedPrupleItemPulls(0);
					} else {
						gachaInfo.addfailedFeaturedPrupleItemPulls(1);
					}
				}
				
				if (itemId == 0) {
					int typeChance = this.randomRange(0, 2);
					if (typeChance == 0) {
						itemId = getRandom(this.purpleAvatars);
					} else {
						itemId = getRandom(this.purpleWeapons);
					}
				}
				
				// The pity5 pushes the pity4 back.
				int pity4 = Math.max(0,gachaInfo.getPity4() - banner.getPrupleHardPity());
				gachaInfo.setPity4(pity4);
			} else {
				itemId = getRandom(this.blueWeapons);
			}
			
			// Add winning item
			wonItems.add(itemId);
		}
		
		// Add to character
		List<GachaItem> list = new ArrayList<>();
		int stardust = 0, starglitter = 0;
		
		for (int itemId : wonItems) {
			ItemData itemData = GameData.getItemDataMap().get(itemId);
			if (itemData == null) {
				continue;
			}

			// Write gacha record
			GachaRecord gachaRecord = new GachaRecord(itemId, player.getUid(), gachaType);
			DatabaseHelper.saveGachaRecord(gachaRecord);
			
			// Create gacha item
			GachaItem.Builder gachaItem = GachaItem.newBuilder();
			int addStardust = 0, addStarglitter = 0;
			boolean isTransferItem = false;
			
			// Const check
			if (itemData.getMaterialType() == MaterialType.MATERIAL_AVATAR) {
				int avatarId = (itemData.getId() % 1000) + 10000000;
				Avatar avatar = player.getAvatars().getAvatarById(avatarId);
				if (avatar != null) {
					int constLevel = avatar.getCoreProudSkillLevel();
					int constItemId = itemData.getId() + 100;
					GameItem constItem = player.getInventory().getInventoryTab(ItemType.ITEM_MATERIAL).getItemById(constItemId);
					if (constItem != null) {
						constLevel += constItem.getCount();
					}
					
					if (constLevel < 6) {
						// Not max const
						addStarglitter = 2;
						// Add 1 const
						gachaItem.addTransferItems(GachaTransferItem.newBuilder().setItem(ItemParam.newBuilder().setItemId(constItemId).setCount(1)).setIsTransferItemNew(constItem == null));
						player.getInventory().addItem(constItemId, 1);
					} else {
						// Is max const
						addStarglitter = 5;
					}
					
					if (itemData.getRankLevel() == 5) {
						addStarglitter *= 5;
					}
					
					isTransferItem = true;
				} else {
					// New
					gachaItem.setIsGachaItemNew(true);
				}
			} else {
				// Is weapon
				switch (itemData.getRankLevel()) {
					case 5:
						addStarglitter = 10;
						break;
					case 4:
						addStarglitter = 2;
						break;
					case 3:
						addStardust = 15;
						break;
				}
			}

			// Create item
			GameItem item = new GameItem(itemData);
			gachaItem.setGachaItem(item.toItemParam());
			player.getInventory().addItem(item);
			
			stardust += addStardust;
			starglitter += addStarglitter;
			
			if (addStardust > 0) {
				gachaItem.addTokenItemList(ItemParam.newBuilder().setItemId(stardustId).setCount(addStardust));
			} if (addStarglitter > 0) {
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
			player.getInventory().addItem(stardustId, stardust);
		} if (starglitter > 0) {
			player.getInventory().addItem(starglitterId, starglitter);
		}
		
		// Packets
		player.sendPacket(new PacketDoGachaRsp(banner, list));
	}

	private synchronized void startWatcher(GameServer server) {
		if(this.watchService == null) {
			try {
				this.watchService = FileSystems.getDefault().newWatchService();
				Path path = new File(Grasscutter.getConfig().DATA_FOLDER).toPath();
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
		if(Grasscutter.getConfig().getGameServerOptions().WatchGacha) {
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
