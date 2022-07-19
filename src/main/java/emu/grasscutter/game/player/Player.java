package emu.grasscutter.game.player;

import dev.morphia.annotations.*;
import emu.grasscutter.GameConstants;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.PersonalLineData;
import emu.grasscutter.data.excels.PlayerLevelData;
import emu.grasscutter.data.excels.WeatherData;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.CoopRequest;
import emu.grasscutter.game.ability.AbilityManager;
import emu.grasscutter.game.activity.ActivityManager;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.avatar.AvatarProfileData;
import emu.grasscutter.game.avatar.AvatarStorage;
import emu.grasscutter.game.battlepass.BattlePassManager;
import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.game.entity.EntityVehicle;
import emu.grasscutter.game.home.GameHome;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.entity.EntityItem;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.expedition.ExpeditionInfo;
import emu.grasscutter.game.friends.FriendsList;
import emu.grasscutter.game.friends.PlayerProfile;
import emu.grasscutter.game.gacha.PlayerGachaInfo;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.inventory.Inventory;
import emu.grasscutter.game.mail.Mail;
import emu.grasscutter.game.mail.MailHandler;
import emu.grasscutter.game.managers.CookingManager;
import emu.grasscutter.game.managers.FurnitureManager;
import emu.grasscutter.game.managers.InsectCaptureManager;
import emu.grasscutter.game.managers.ResinManager;
import emu.grasscutter.game.managers.collection.CollectionManager;
import emu.grasscutter.game.managers.collection.CollectionRecordStore;
import emu.grasscutter.game.managers.deforestation.DeforestationManager;
import emu.grasscutter.game.managers.energy.EnergyManager;
import emu.grasscutter.game.managers.forging.ActiveForgeData;
import emu.grasscutter.game.managers.forging.ForgingManager;
import emu.grasscutter.game.managers.mapmark.*;
import emu.grasscutter.game.managers.stamina.StaminaManager;
import emu.grasscutter.game.managers.SotSManager;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.game.props.ClimateType;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.game.props.WatcherTriggerType;
import emu.grasscutter.game.quest.QuestManager;
import emu.grasscutter.game.shop.ShopLimit;
import emu.grasscutter.game.tower.TowerData;
import emu.grasscutter.game.tower.TowerManager;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.game.world.World;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.proto.*;
import emu.grasscutter.net.proto.AbilityInvokeEntryOuterClass.AbilityInvokeEntry;
import emu.grasscutter.net.proto.AttackResultOuterClass.AttackResult;
import emu.grasscutter.net.proto.CombatInvokeEntryOuterClass.CombatInvokeEntry;
import emu.grasscutter.net.proto.GadgetInteractReqOuterClass.GadgetInteractReq;
import emu.grasscutter.net.proto.InteractTypeOuterClass.InteractType;
import emu.grasscutter.net.proto.MpSettingTypeOuterClass.MpSettingType;
import emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.OnlinePlayerInfo;
import emu.grasscutter.net.proto.PlayerLocationInfoOuterClass.PlayerLocationInfo;
import emu.grasscutter.net.proto.ProfilePictureOuterClass.ProfilePicture;
import emu.grasscutter.net.proto.SocialDetailOuterClass.SocialDetail;
import emu.grasscutter.net.proto.VisionTypeOuterClass.VisionType;
import emu.grasscutter.server.event.player.PlayerJoinEvent;
import emu.grasscutter.server.event.player.PlayerQuitEvent;
import emu.grasscutter.server.game.GameServer;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.game.GameSession.SessionState;
import emu.grasscutter.server.packet.send.*;
import emu.grasscutter.utils.DateHelper;
import emu.grasscutter.utils.Position;
import emu.grasscutter.utils.MessageHandler;
import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

import static emu.grasscutter.Configuration.*;

@Entity(value = "players", useDiscriminator = false)
public class Player {

	@Id private int id;
	@Indexed(options = @IndexOptions(unique = true)) private String accountId;

	@Transient private Account account;
	private String nickname;
	private String signature;
	private int headImage;
	private int nameCardId = 210001;
	private Position pos;
	private Position rotation;
	private PlayerBirthday birthday;
	private PlayerCodex codex;
    @Getter private PlayerOpenStateManager openStateManager;
	private Map<Integer, Integer> properties;
	private Set<Integer> nameCardList;
	private Set<Integer> flyCloakList;
	private Set<Integer> costumeList;
	private Set<Integer> unlockedForgingBlueprints;
	private Set<Integer> unlockedCombines;
	private Set<Integer> unlockedFurniture;
	private Set<Integer> unlockedFurnitureSuite;
	private List<ActiveForgeData> activeForges;
	private Map<Integer, Integer> unlockedRecipies;

	private Integer widgetId;

	private Set<Integer> realmList;
	private Integer currentRealmId;

	@Transient private long nextGuid = 0;
	@Transient private int peerId;
	@Transient private World world;
	@Transient private Scene scene;
	@Transient @Getter private int weatherId = 0;
	@Transient @Getter private ClimateType climate = ClimateType.CLIMATE_SUNNY;
	@Transient private GameSession session;
	@Transient private AvatarStorage avatars;
	@Transient private Inventory inventory;
	@Transient private FriendsList friendsList;
	@Transient private MailHandler mailHandler;
	@Transient private MessageHandler messageHandler;
	@Transient private AbilityManager abilityManager;
	@Transient private QuestManager questManager;

	@Transient private SotSManager sotsManager;
	@Transient private InsectCaptureManager insectCaptureManager;

	private TeamManager teamManager;

	@Transient private TowerManager towerManager;
	private TowerData towerData;
	private PlayerGachaInfo gachaInfo;
	private PlayerProfile playerProfile;
	private boolean showAvatar;
	private ArrayList<AvatarProfileData> shownAvatars;
	private Set<Integer> rewardedLevels;
	private ArrayList<ShopLimit> shopLimit;
	private Map<Long, ExpeditionInfo> expeditionInfo;

	private int sceneId;
	private int regionId;
	private int mainCharacterId;
	private boolean godmode;
	private boolean stamina;

	private boolean moonCard;
	private Date moonCardStartTime;
	private int moonCardDuration;
	private Set<Date> moonCardGetTimes;

	private List<Integer> showAvatarList;
	private boolean showAvatars;

	@Transient private boolean paused;
	@Transient private int enterSceneToken;
	@Transient private SceneLoadState sceneState;
	@Transient private boolean hasSentAvatarDataNotify;
	@Transient private long nextSendPlayerLocTime = 0;

	@Transient private final Int2ObjectMap<CoopRequest> coopRequests;
	@Transient private final Queue<AttackResult> attackResults;
	@Transient private final InvokeHandler<CombatInvokeEntry> combatInvokeHandler;
	@Transient private final InvokeHandler<AbilityInvokeEntry> abilityInvokeHandler;
	@Transient private final InvokeHandler<AbilityInvokeEntry> clientAbilityInitFinishHandler;

	@Transient private MapMarksManager mapMarksManager;
	@Transient private StaminaManager staminaManager;
	@Transient private EnergyManager energyManager;
	@Transient private ResinManager resinManager;
	@Transient private ForgingManager forgingManager;
	@Transient private DeforestationManager deforestationManager;
	@Transient private GameHome home;
	@Transient private FurnitureManager furnitureManager;
	@Transient private BattlePassManager battlePassManager;
	@Transient private CookingManager cookingManager;
	// @Transient private
	@Getter @Transient private ActivityManager activityManager;

	@Transient private CollectionManager collectionManager;
	private CollectionRecordStore collectionRecordStore;

	private long springLastUsed;
	private HashMap<String, MapMark> mapMarks;
	private int nextResinRefresh;
	private int lastDailyReset;

	@Deprecated
	@SuppressWarnings({"rawtypes", "unchecked"}) // Morphia only!
	public Player() {
		this.inventory = new Inventory(this);
		this.avatars = new AvatarStorage(this);
		this.friendsList = new FriendsList(this);
		this.mailHandler = new MailHandler(this);
		this.towerManager = new TowerManager(this);
		this.abilityManager = new AbilityManager(this);
		this.deforestationManager = new DeforestationManager(this);
		this.insectCaptureManager = new InsectCaptureManager(this);

		this.setQuestManager(new QuestManager(this));
		this.pos = new Position();
		this.rotation = new Position();
		this.properties = new HashMap<>();
		for (PlayerProperty prop : PlayerProperty.values()) {
			if (prop.getId() < 10000) {
				continue;
			}
			this.properties.put(prop.getId(), 0);
		}

		this.gachaInfo = new PlayerGachaInfo();
		this.nameCardList = new HashSet<>();
		this.flyCloakList = new HashSet<>();
		this.costumeList = new HashSet<>();
		this.towerData = new TowerData();
		this.collectionRecordStore = new CollectionRecordStore();
		this.unlockedForgingBlueprints = new HashSet<>();
		this.unlockedCombines = new HashSet<>();
		this.unlockedFurniture = new HashSet<>();
		this.unlockedFurnitureSuite = new HashSet<>();
		this.activeForges = new ArrayList<>();
		this.unlockedRecipies = new HashMap<>();

		this.setSceneId(3);
		this.setRegionId(1);
		this.sceneState = SceneLoadState.NONE;

		this.attackResults = new LinkedBlockingQueue<>();
		this.coopRequests = new Int2ObjectOpenHashMap<>();
		this.combatInvokeHandler = new InvokeHandler(PacketCombatInvocationsNotify.class);
		this.abilityInvokeHandler = new InvokeHandler(PacketAbilityInvocationsNotify.class);
		this.clientAbilityInitFinishHandler = new InvokeHandler(PacketClientAbilityInitFinishNotify.class);

		this.birthday = new PlayerBirthday();
		this.rewardedLevels = new HashSet<>();
		this.moonCardGetTimes = new HashSet<>();
		this.codex = new PlayerCodex(this);
        this.openStateManager = new PlayerOpenStateManager(this);
		this.shopLimit = new ArrayList<>();
		this.expeditionInfo = new HashMap<>();
		this.messageHandler = null;
		this.mapMarksManager = new MapMarksManager(this);
		this.staminaManager = new StaminaManager(this);
		this.sotsManager = new SotSManager(this);
		this.energyManager = new EnergyManager(this);
		this.resinManager = new ResinManager(this);
		this.forgingManager = new ForgingManager(this);
		this.furnitureManager = new FurnitureManager(this);
		this.cookingManager = new CookingManager(this);
	}

	// On player creation
	public Player(GameSession session) {
		this();
		this.account = session.getAccount();
		this.accountId = this.getAccount().getId();
		this.session = session;
		this.nickname = "Traveler";
		this.signature = "";
		this.teamManager = new TeamManager(this);
		this.birthday = new PlayerBirthday();
		this.codex = new PlayerCodex(this);
		this.setProperty(PlayerProperty.PROP_PLAYER_LEVEL, 1, false);
		this.setProperty(PlayerProperty.PROP_IS_SPRING_AUTO_USE, 1, false);
		this.setProperty(PlayerProperty.PROP_SPRING_AUTO_USE_PERCENT, 50, false);
		this.setProperty(PlayerProperty.PROP_IS_FLYABLE, 1, false);
		this.setProperty(PlayerProperty.PROP_IS_TRANSFERABLE, 1, false);
		this.setProperty(PlayerProperty.PROP_MAX_STAMINA, 24000, false);
		this.setProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA, 24000, false);
		this.setProperty(PlayerProperty.PROP_PLAYER_RESIN, 160, false);
		this.getFlyCloakList().add(140001);
		this.getNameCardList().add(210001);
		this.getPos().set(GameConstants.START_POSITION);
		this.getRotation().set(0, 307, 0);
		this.messageHandler = null;
		this.mapMarksManager = new MapMarksManager(this);
		this.staminaManager = new StaminaManager(this);
		this.sotsManager = new SotSManager(this);
		this.energyManager = new EnergyManager(this);
		this.resinManager = new ResinManager(this);
		this.deforestationManager = new DeforestationManager(this);
		this.forgingManager = new ForgingManager(this);
		this.furnitureManager = new FurnitureManager(this);
		this.cookingManager = new CookingManager(this);
	}

	public int getUid() {
		return id;
	}

	public void setUid(int id) {
		this.id = id;
	}

	public long getNextGameGuid() {
		long nextId = ++this.nextGuid;
		return ((long) this.getUid() << 32) + nextId;
	}

	public Account getAccount() {
		if (this.account == null)
			this.account = DatabaseHelper.getAccountById(Integer.toString(this.id));
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public GameSession getSession() {
		return session;
	}

	public void setSession(GameSession session) {
		this.session = session;
	}

	public boolean isOnline() {
		return this.getSession() != null && this.getSession().isActive();
	}

	public GameServer getServer() {
		return this.getSession().getServer();
	}

	public synchronized World getWorld() {
		return this.world;
	}

	public synchronized void setWorld(World world) {
		this.world = world;
	}

	public synchronized Scene getScene() {
		return scene;
	}

	public synchronized void setScene(Scene scene) {
		this.scene = scene;
	}

	synchronized public void setClimate(ClimateType climate) {
		this.climate = climate;
		this.session.send(new PacketSceneAreaWeatherNotify(this));
	}

	synchronized public void setWeather(int weather) {
		this.setWeather(weather, ClimateType.CLIMATE_NONE);
	}

	synchronized public void setWeather(int weatherId, ClimateType climate) {
		// Lookup default climate for this weather
		if (climate == ClimateType.CLIMATE_NONE) {
			WeatherData w = GameData.getWeatherDataMap().get(weatherId);
			if (w != null) {
				climate = w.getDefaultClimate();
			}
		}
		this.weatherId = weatherId;
		this.climate = climate;
		this.session.send(new PacketSceneAreaWeatherNotify(this));
	}

	public int getGmLevel() {
		return 1;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickName) {
		this.nickname = nickName;
		this.updateProfile();
	}

	public int getHeadImage() {
		return headImage;
	}

	public void setHeadImage(int picture) {
		this.headImage = picture;
		this.updateProfile();
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
		this.updateProfile();
	}

	public Integer getWidgetId() {
		return widgetId;
	}

	public void setWidgetId(Integer widgetId) {
		this.widgetId = widgetId;
	}

	public Set<Integer> getRealmList() {
		return realmList;
	}

	public void setRealmList(Set<Integer> realmList) {
		this.realmList = realmList;
	}

	public void addRealmList(int realmId) {
		if (this.realmList == null) {
			this.realmList = new HashSet<>();
		} else if (this.realmList.contains(realmId)) {
			return;
		}
		this.realmList.add(realmId);
	}

	public Integer getCurrentRealmId() {
		return currentRealmId;
	}

	public void setCurrentRealmId(Integer currentRealmId) {
		this.currentRealmId = currentRealmId;
	}
	public GameHome getHome(){
		return home;
	}
	public Position getPos() {
		return pos;
	}

	public Position getRotation() {
		return rotation;
	}

	public int getLevel() {
		return this.getProperty(PlayerProperty.PROP_PLAYER_LEVEL);
	}

	public boolean setLevel(int level) {
		if (this.getLevel() == level) {
			return true;
		}

		if (this.setProperty(PlayerProperty.PROP_PLAYER_LEVEL, level)) {
			// Update world level and profile.
			this.updateWorldLevel();
			this.updateProfile();

			// Handle OpenState unlocks from level-up.
			this.getOpenStateManager().unlockLevelDependentStates();

			return true;
		}
		return false;
	}

	public int getExp() {
		return this.getProperty(PlayerProperty.PROP_PLAYER_EXP);
	}

	public int getWorldLevel() {
		return this.getProperty(PlayerProperty.PROP_PLAYER_WORLD_LEVEL);
	}

	public boolean setWorldLevel(int level) {
		if (this.setProperty(PlayerProperty.PROP_PLAYER_WORLD_LEVEL, level)) {
			if (this.world.getHost() == this)  // Don't update World's WL if we are in someone else's world
            	this.world.setWorldLevel(level);
			this.updateProfile();
			return true;
		}
		return false;
	}

	public int getForgePoints() {
		return this.getProperty(PlayerProperty.PROP_PLAYER_FORGE_POINT);
	}

	public boolean setForgePoints(int value) {
		if (value == this.getForgePoints()) {
			return true;
		}

		return this.setProperty(PlayerProperty.PROP_PLAYER_FORGE_POINT, value);
	}

	public int getPrimogems() {
		return this.getProperty(PlayerProperty.PROP_PLAYER_HCOIN);
	}

	public boolean setPrimogems(int primogem) {
		return this.setProperty(PlayerProperty.PROP_PLAYER_HCOIN, primogem);
	}

	public int getMora() {
		return this.getProperty(PlayerProperty.PROP_PLAYER_SCOIN);
	}

	public boolean setMora(int mora) {
		return this.setProperty(PlayerProperty.PROP_PLAYER_SCOIN, mora);
	}

	public int getCrystals() {
		return this.getProperty(PlayerProperty.PROP_PLAYER_MCOIN);
	}

	public boolean setCrystals(int crystals) {
		return this.setProperty(PlayerProperty.PROP_PLAYER_MCOIN, crystals);
	}

	public int getHomeCoin() {
		return this.getProperty(PlayerProperty.PROP_PLAYER_HOME_COIN);
	}

	public boolean setHomeCoin(int coin) {
		return this.setProperty(PlayerProperty.PROP_PLAYER_HOME_COIN, coin);
	}
	private int getExpRequired(int level) {
		PlayerLevelData levelData = GameData.getPlayerLevelDataMap().get(level);
		return levelData != null ? levelData.getExp() : 0;
	}

	private float getExpModifier() {
		return GAME_OPTIONS.rates.adventureExp;
	}

	// Affected by exp rate
	public void earnExp(int exp) {
		addExpDirectly((int) (exp * getExpModifier()));
	}

	// Directly give player exp
	public void addExpDirectly(int gain) {
		int level = getLevel();
		int exp = getExp();
		int reqExp = getExpRequired(level);

		exp += gain;

		while (exp >= reqExp && reqExp > 0) {
			exp -= reqExp;
			level += 1;
			reqExp = getExpRequired(level);

			// Set level each time to allow level-up specific logic to run.
			this.setLevel(level);
		}

		// Set exp
		this.setProperty(PlayerProperty.PROP_PLAYER_EXP, exp);
	}

	private void updateWorldLevel() {
		int currentWorldLevel = this.getWorldLevel();
		int currentLevel = this.getLevel();

		int newWorldLevel =
			(currentLevel >= 55) ? 8 :
			(currentLevel >= 50) ? 7 :
			(currentLevel >= 45) ? 6 :
			(currentLevel >= 40) ? 5 :
			(currentLevel >= 35) ? 4 :
			(currentLevel >= 30) ? 3 :
			(currentLevel >= 25) ? 2 :
			(currentLevel >= 20) ? 1 :
			0;

		if (newWorldLevel != currentWorldLevel) {
			this.setWorldLevel(newWorldLevel);
		}
	}

	private void updateProfile() {
		getProfile().syncWithCharacter(this);
	}

	public boolean isFirstLoginEnterScene() {
		return !this.hasSentAvatarDataNotify;
	}

	public TeamManager getTeamManager() {
		return this.teamManager;
	}

	public TowerManager getTowerManager() {
		return towerManager;
	}

	public TowerData getTowerData() {
		if (towerData == null) {
			// because of mistake, null may be saved as storage at some machine, this if can be removed in future
			towerData = new TowerData();
		}
		return towerData;
	}

	public QuestManager getQuestManager() {
		return questManager;
	}

	public void setQuestManager(QuestManager questManager) {
		this.questManager = questManager;
	}

	public PlayerGachaInfo getGachaInfo() {
		return gachaInfo;
	}

	public PlayerProfile getProfile() {
		if (this.playerProfile == null) {
			this.playerProfile = new PlayerProfile(this);
		}
		return playerProfile;
	}

	// TODO: Based on the proto, property value could be int or float.
	//  Although there's no float value at this moment, our code should be prepared for float values.
	public Map<Integer, Integer> getProperties() {
		return properties;
	}

	public boolean setProperty(PlayerProperty prop, int value) {
		return setPropertyWithSanityCheck(prop, value, true);
	}

	public boolean setProperty(PlayerProperty prop, int value, boolean sendPacket) {
		return setPropertyWithSanityCheck(prop, value, sendPacket);
	}

	public int getProperty(PlayerProperty prop) {
		return getProperties().get(prop.getId());
	}

	public Set<Integer> getFlyCloakList() {
		return flyCloakList;
	}

	public Set<Integer> getCostumeList() {
		return costumeList;
	}

	public Set<Integer> getNameCardList() {
		return this.nameCardList;
	}

	public Set<Integer> getUnlockedForgingBlueprints() {
		return this.unlockedForgingBlueprints;
	}

	public Set<Integer> getUnlockedCombines() {
		return this.unlockedCombines;
	}

	public Set<Integer> getUnlockedFurniture() {
		return unlockedFurniture;
	}

	public Set<Integer> getUnlockedFurnitureSuite() {
		return unlockedFurnitureSuite;
	}

	public List<ActiveForgeData> getActiveForges() {
		return this.activeForges;
	}

	public Map<Integer, Integer> getUnlockedRecipies() {
		return this.unlockedRecipies;
	}

	public MpSettingType getMpSetting() {
		return MpSettingType.MP_SETTING_TYPE_ENTER_AFTER_APPLY; // TEMP
	}

	public Queue<AttackResult> getAttackResults() {
		return this.attackResults;
	}

	public synchronized Int2ObjectMap<CoopRequest> getCoopRequests() {
		return coopRequests;
	}

	public InvokeHandler<CombatInvokeEntry> getCombatInvokeHandler() {
		return this.combatInvokeHandler;
	}

	public InvokeHandler<AbilityInvokeEntry> getAbilityInvokeHandler() {
		return this.abilityInvokeHandler;
	}

	public InvokeHandler<AbilityInvokeEntry> getClientAbilityInitFinishHandler() {
		return clientAbilityInitFinishHandler;
	}

	public AvatarStorage getAvatars() {
		return avatars;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public FriendsList getFriendsList() {
		return this.friendsList;
	}

	public MailHandler getMailHandler() {
		return mailHandler;
	}

	public int getEnterSceneToken() {
		return enterSceneToken;
	}

	public void setEnterSceneToken(int enterSceneToken) {
		this.enterSceneToken = enterSceneToken;
	}

	public int getNameCardId() {
		return nameCardId;
	}

	public void setNameCardId(int nameCardId) {
		this.nameCardId = nameCardId;
		this.updateProfile();
	}

	public int getMainCharacterId() {
		return mainCharacterId;
	}

	public void setMainCharacterId(int mainCharacterId) {
		this.mainCharacterId = mainCharacterId;
	}

	public int getPeerId() {
		return peerId;
	}

	public void setPeerId(int peerId) {
		this.peerId = peerId;
	}

	public int getClientTime() {
		return session.getClientTime();
	}

	public long getLastPingTime() {
		return session.getLastPingTime();
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean newPauseState) {
		boolean oldPauseState = this.paused;
		this.paused = newPauseState;

		if (newPauseState && !oldPauseState) {
			this.onPause();
		} else if (oldPauseState && !newPauseState) {
			this.onUnpause();
		}
	}

	public long getSpringLastUsed() {
		return springLastUsed;
	}

	public void setSpringLastUsed(long val) {
		springLastUsed = val;
	}

	public int getNextResinRefresh() {
		return nextResinRefresh;
	}

	public void setNextResinRefresh(int value) {
		this.nextResinRefresh = value;
	}

	public SceneLoadState getSceneLoadState() {
		return sceneState;
	}

	public void setSceneLoadState(SceneLoadState sceneState) {
		this.sceneState = sceneState;
	}

	public boolean isInMultiplayer() {
		return this.getWorld() != null && this.getWorld().isMultiplayer();
	}

	public int getSceneId() {
		return sceneId;
	}

	public void setSceneId(int sceneId) {
		this.sceneId = sceneId;
	}

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public void setShowAvatars(boolean showAvatars) {
		this.showAvatars = showAvatars;
	}

	public boolean isShowAvatars() {
		return showAvatars;
	}

	public void setShowAvatarList(List<Integer> showAvatarList) {
		this.showAvatarList = showAvatarList;
	}

	public List<Integer> getShowAvatarList() {
		return showAvatarList;
	}

	public int getLastDailyReset() {
		return this.lastDailyReset;
	}

	public void setLastDailyReset(int value) {
		this.lastDailyReset = value;
	}

	public boolean inMoonCard() {
		return moonCard;
	}

	public void setMoonCard(boolean moonCard) {
		this.moonCard = moonCard;
	}

	public void addMoonCardDays(int days) {
		this.moonCardDuration += days;
	}

	public int getMoonCardDuration() {
		return moonCardDuration;
	}

	public void setMoonCardDuration(int moonCardDuration) {
		this.moonCardDuration = moonCardDuration;
	}

	public Date getMoonCardStartTime() {
		return moonCardStartTime;
	}

	public void setMoonCardStartTime(Date moonCardStartTime) {
		this.moonCardStartTime = moonCardStartTime;
	}

	public Set<Date> getMoonCardGetTimes() {
		return moonCardGetTimes;
	}

	public void setMoonCardGetTimes(Set<Date> moonCardGetTimes) {
		this.moonCardGetTimes = moonCardGetTimes;
	}

	public int getMoonCardRemainDays() {
		Calendar remainCalendar = Calendar.getInstance();
		remainCalendar.setTime(moonCardStartTime);
		remainCalendar.add(Calendar.DATE, moonCardDuration);
		Date theLastDay = remainCalendar.getTime();
		Date now = DateHelper.onlyYearMonthDay(new Date());
		return (int) ((theLastDay.getTime() - now.getTime()) / (24 * 60 * 60 * 1000)); // By copilot
	}

	public void rechargeMoonCard() {
		inventory.addItem(new GameItem(203, 300));
		if (!moonCard) {
			moonCard = true;
			Date now = new Date();
			moonCardStartTime = DateHelper.onlyYearMonthDay(now);
			moonCardDuration = 30;
		} else {
			moonCardDuration += 30;
		}
		if (!moonCardGetTimes.contains(moonCardStartTime)) {
			moonCardGetTimes.add(moonCardStartTime);
		}
	}

	public void getTodayMoonCard() {
		if (!moonCard) {
			return;
		}
		Date now = DateHelper.onlyYearMonthDay(new Date());
		if (moonCardGetTimes.contains(now)) {
			return;
		}
		Date stopTime = new Date();
		Calendar stopCalendar = Calendar.getInstance();
		stopCalendar.setTime(stopTime);
		stopCalendar.add(Calendar.DATE, moonCardDuration);
		stopTime = stopCalendar.getTime();
		if (now.after(stopTime)) {
			moonCard = false;
			return;
		}
		moonCardGetTimes.add(now);
		addMoonCardDays(1);
		GameItem item = new GameItem(201, 90);
		getInventory().addItem(item, ActionReason.BlessingRedeemReward);
		session.send(new PacketCardProductRewardNotify(getMoonCardRemainDays()));
	}

	public Map<Long, ExpeditionInfo> getExpeditionInfo() {
		return expeditionInfo;
	}

	public void addExpeditionInfo(long avaterGuid, int expId, int hourTime, int startTime){
		ExpeditionInfo exp = new ExpeditionInfo();
		exp.setExpId(expId);
		exp.setHourTime(hourTime);
		exp.setState(1);
		exp.setStartTime(startTime);
		expeditionInfo.put(avaterGuid, exp);
	}

	public void removeExpeditionInfo(long avaterGuid){
		expeditionInfo.remove(avaterGuid);
	}

	public ExpeditionInfo getExpeditionInfo(long avaterGuid){
		return expeditionInfo.get(avaterGuid);
	}

	public List<ShopLimit> getShopLimit() {
		return shopLimit;
	}

	public ShopLimit getGoodsLimit(int goodsId) {
		Optional<ShopLimit> shopLimit = this.shopLimit.stream().filter(x -> x.getShopGoodId() == goodsId).findFirst();
		if (shopLimit.isEmpty())
			return null;
		return shopLimit.get();
	}

	public void addShopLimit(int goodsId, int boughtCount, int nextRefreshTime) {
		ShopLimit target = getGoodsLimit(goodsId);
		if (target != null) {
			target.setHasBought(target.getHasBought() + boughtCount);
			target.setHasBoughtInPeriod(target.getHasBoughtInPeriod() + boughtCount);
			target.setNextRefreshTime(nextRefreshTime);
		} else {
			ShopLimit sl = new ShopLimit();
			sl.setShopGoodId(goodsId);
			sl.setHasBought(boughtCount);
			sl.setHasBoughtInPeriod(boughtCount);
			sl.setNextRefreshTime(nextRefreshTime);
			getShopLimit().add(sl);
		}
		this.save();
	}
	public boolean getUnlimitedStamina() {
		return stamina;
	}
	public void setUnlimitedStamina(boolean stamina) {
		this.stamina = stamina;
	}
	public boolean inGodmode() {
		return godmode;
	}

	public void setGodmode(boolean godmode) {
		this.godmode = godmode;
	}

	public boolean hasSentAvatarDataNotify() {
		return hasSentAvatarDataNotify;
	}

	public void setHasSentAvatarDataNotify(boolean hasSentAvatarDataNotify) {
		this.hasSentAvatarDataNotify = hasSentAvatarDataNotify;
	}

	public void addAvatar(Avatar avatar, boolean addToCurrentTeam) {
		boolean result = getAvatars().addAvatar(avatar);

		if (result) {
			// Add starting weapon
			getAvatars().addStartingWeapon(avatar);

			// Done
			if (hasSentAvatarDataNotify()) {
				// Recalc stats
				avatar.recalcStats();
				// Packet, show notice on left if the avatar will be added to the team
				sendPacket(new PacketAvatarAddNotify(avatar, addToCurrentTeam && this.getTeamManager().canAddAvatarToCurrentTeam()));
				if (addToCurrentTeam) {
					// If space in team, add
					this.getTeamManager().addAvatarToCurrentTeam(avatar);
				}
			}
		} else {
			// Failed adding avatar
		}
	}

	public void addAvatar(Avatar avatar) {
		addAvatar(avatar, true);
	}

	public void addFlycloak(int flycloakId) {
		this.getFlyCloakList().add(flycloakId);
		this.sendPacket(new PacketAvatarGainFlycloakNotify(flycloakId));
	}

	public void addCostume(int costumeId) {
		this.getCostumeList().add(costumeId);
		this.sendPacket(new PacketAvatarGainCostumeNotify(costumeId));
	}

	public void addNameCard(int nameCardId) {
		this.getNameCardList().add(nameCardId);
		this.sendPacket(new PacketUnlockNameCardNotify(nameCardId));
	}

	public void setNameCard(int nameCardId) {
		if (!this.getNameCardList().contains(nameCardId)) {
			return;
		}

		this.setNameCardId(nameCardId);

		this.sendPacket(new PacketSetNameCardRsp(nameCardId));
	}

	public void dropMessage(Object message) {
		if (this.messageHandler != null) {
			this.messageHandler.append(message.toString());
			return;
		}

		this.getServer().getChatManager().sendPrivateMessageFromServer(getUid(), message.toString());
		// this.sendPacket(new PacketPrivateChatNotify(GameConstants.SERVER_CONSOLE_UID, getUid(), message.toString()));
	}

	/**
	 * Sends a message to another player.
	 *
	 * @param sender  The sender of the message.
	 * @param message The message to send.
	 */
	public void sendMessage(Player sender, Object message) {
		// this.sendPacket(new PacketPrivateChatNotify(sender.getUid(), this.getUid(), message.toString()));
		this.getServer().getChatManager().sendPrivateMessage(sender, this.getUid(), message.toString());
	}

	// ---------------------MAIL------------------------

	public List<Mail> getAllMail() { return this.getMailHandler().getMail(); }

	public void sendMail(Mail message) {
		this.getMailHandler().sendMail(message);
	}

	public boolean deleteMail(int mailId) {
		return this.getMailHandler().deleteMail(mailId);
	}

	public Mail getMail(int index) { return this.getMailHandler().getMailById(index); }

	public int getMailId(Mail message) {
		return this.getMailHandler().getMailIndex(message);
	}

	public boolean replaceMailByIndex(int index, Mail message) {
		return this.getMailHandler().replaceMailByIndex(index, message);
	}


	public void interactWith(int gadgetEntityId, GadgetInteractReq opType) {
		GameEntity entity = getScene().getEntityById(gadgetEntityId);
		if (entity == null) {
			return;
		}

		// Handle
		if (entity instanceof EntityItem drop) {
			// Pick item
			if (!drop.isShare()) // check drop owner to avoid someone picked up item in others' world
			{
				int dropOwner = (int)(drop.getGuid() >> 32);
				if (dropOwner != getUid()) {
					return;
				}
			}
			entity.getScene().removeEntity(entity);
			GameItem item = new GameItem(drop.getItemData(), drop.getCount());
			// Add to inventory
			boolean success = getInventory().addItem(item, ActionReason.SubfieldDrop);
			if (success) {
				if (!drop.isShare()) { // not shared drop
					this.sendPacket(new PacketGadgetInteractRsp(drop, InteractType.INTERACT_TYPE_PICK_ITEM));
				}else{
					this.getScene().broadcastPacket(new PacketGadgetInteractRsp(drop, InteractType.INTERACT_TYPE_PICK_ITEM));
				}
			}
		} else if (entity instanceof EntityGadget gadget) {
			if (gadget.getContent() == null) {
				return;
			}

			boolean shouldDelete = gadget.getContent().onInteract(this, opType);

			if (shouldDelete) {
				entity.getScene().removeEntity(entity, VisionType.VISION_TYPE_REMOVE);
			}
		} else if (entity instanceof EntityMonster monster) {
			insectCaptureManager.arrestSmallCreature(monster);
		} else if (entity instanceof EntityVehicle vehicle) {// try to arrest it, example: glowworm
			insectCaptureManager.arrestSmallCreature(vehicle);
		} else {
			// Delete directly
			entity.getScene().removeEntity(entity);
		}
	}

	public void onPause() {
		getStaminaManager().stopSustainedStaminaHandler();
	}

	public void onUnpause() {
		getStaminaManager().startSustainedStaminaHandler();
	}

	public void sendPacket(BasePacket packet) {
		this.getSession().send(packet);
	}

	public OnlinePlayerInfo getOnlinePlayerInfo() {
		OnlinePlayerInfo.Builder onlineInfo = OnlinePlayerInfo.newBuilder()
				.setUid(this.getUid())
				.setNickname(this.getNickname())
				.setPlayerLevel(this.getLevel())
				.setMpSettingType(this.getMpSetting())
				.setNameCardId(this.getNameCardId())
				.setSignature(this.getSignature())
				.setProfilePicture(ProfilePicture.newBuilder().setAvatarId(this.getHeadImage()));

		if (this.getWorld() != null) {
			onlineInfo.setCurPlayerNumInWorld(getWorld().getPlayerCount());
		} else {
			onlineInfo.setCurPlayerNumInWorld(1);
		}

		return onlineInfo.build();
	}

	public PlayerBirthday getBirthday() {
		return this.birthday;
	}

	public void setBirthday(int d, int m) {
		this.birthday = new PlayerBirthday(d, m);
		this.updateProfile();
	}

	public boolean hasBirthday() {
		return this.birthday.getDay() > 0;
	}

	public PlayerCodex getCodex(){ return this.codex; }

	public Set<Integer> getRewardedLevels() {
		return rewardedLevels;
	}

	public void setRewardedLevels(Set<Integer> rewardedLevels) {
		this.rewardedLevels = rewardedLevels;
	}

	public SocialDetail.Builder getSocialDetail() {
		List<SocialShowAvatarInfoOuterClass.SocialShowAvatarInfo> socialShowAvatarInfoList = new ArrayList<>();
		if (this.isOnline()) {
			if (this.getShowAvatarList() != null) {
				for (int avatarId : this.getShowAvatarList()) {
					socialShowAvatarInfoList.add(
							socialShowAvatarInfoList.size(),
							SocialShowAvatarInfoOuterClass.SocialShowAvatarInfo.newBuilder()
									.setAvatarId(avatarId)
									.setLevel(getAvatars().getAvatarById(avatarId).getLevel())
									.setCostumeId(getAvatars().getAvatarById(avatarId).getCostume())
									.build()
					);
				}
			}
		} else {
			List<Integer> showAvatarList = DatabaseHelper.getPlayerByUid(id).getShowAvatarList();
			AvatarStorage avatars = DatabaseHelper.getPlayerByUid(id).getAvatars();
			avatars.loadFromDatabase();
			if (showAvatarList != null) {
				for (int avatarId : showAvatarList) {
					socialShowAvatarInfoList.add(
							socialShowAvatarInfoList.size(),
							SocialShowAvatarInfoOuterClass.SocialShowAvatarInfo.newBuilder()
									.setAvatarId(avatarId)
									.setLevel(avatars.getAvatarById(avatarId).getLevel())
									.setCostumeId(avatars.getAvatarById(avatarId).getCostume())
									.build()
					);
				}
			}
		}

		SocialDetail.Builder social = SocialDetail.newBuilder()
				.setUid(this.getUid())
				.setProfilePicture(ProfilePicture.newBuilder().setAvatarId(this.getHeadImage()))
				.setNickname(this.getNickname())
				.setSignature(this.getSignature())
				.setLevel(this.getLevel())
				.setBirthday(this.getBirthday().getFilledProtoWhenNotEmpty())
				.setWorldLevel(this.getWorldLevel())
				.setNameCardId(this.getNameCardId())
				.setIsShowAvatar(this.isShowAvatars())
				.addAllShowAvatarInfoList(socialShowAvatarInfoList)
				.setFinishAchievementNum(0);
		return social;
	}

	public List<ShowAvatarInfoOuterClass.ShowAvatarInfo> getShowAvatarInfoList() {
		List<ShowAvatarInfoOuterClass.ShowAvatarInfo> showAvatarInfoList = new ArrayList<>();

		Player player;
		boolean shouldRecalc;
		if (this.isOnline()) {
			player = this;
			shouldRecalc = false;
		} else {
			player = DatabaseHelper.getPlayerByUid(id);
			player.getAvatars().loadFromDatabase();
			player.getInventory().loadFromDatabase();
			shouldRecalc = true;
		}

		List<Integer> showAvatarList = player.getShowAvatarList();
		AvatarStorage avatars = player.getAvatars();
		if (showAvatarList != null) {
			for (int avatarId : showAvatarList) {
				Avatar avatar = avatars.getAvatarById(avatarId);
				if (shouldRecalc) {
					avatar.recalcStats();
				}
				showAvatarInfoList.add(avatar.toShowAvatarInfoProto());
			}
		}
		return showAvatarInfoList;
	}

	public PlayerWorldLocationInfoOuterClass.PlayerWorldLocationInfo getWorldPlayerLocationInfo() {
		return PlayerWorldLocationInfoOuterClass.PlayerWorldLocationInfo.newBuilder()
				.setSceneId(this.getSceneId())
				.setPlayerLoc(this.getPlayerLocationInfo())
				.build();
	}

	public PlayerLocationInfo getPlayerLocationInfo() {
		return PlayerLocationInfo.newBuilder()
				.setUid(this.getUid())
				.setPos(this.getPos().toProto())
				.setRot(this.getRotation().toProto())
				.build();
	}

	public MapMarksManager getMapMarksManager() {
		return mapMarksManager;
	}

	public StaminaManager getStaminaManager() { return staminaManager; }

	public SotSManager getSotSManager() { return sotsManager; }

	public EnergyManager getEnergyManager() {
		return this.energyManager;
	}

	public ResinManager getResinManager() {
		return this.resinManager;
	}

	public ForgingManager getForgingManager() {
		return this.forgingManager;
	}

	public FurnitureManager getFurnitureManager() {
		return furnitureManager;
	}

	public BattlePassManager getBattlePassManager(){
		return battlePassManager;
	}

	public CookingManager getCookingManager() {
		return cookingManager;
	}

	public void loadBattlePassManager() {
		if (this.battlePassManager != null) return;
		this.battlePassManager = DatabaseHelper.loadBattlePass(this);
		this.battlePassManager.getMissions().values().removeIf(mission -> mission.getData() == null);
	}

	public AbilityManager getAbilityManager() {
		return abilityManager;
	}

	public DeforestationManager getDeforestationManager() {
		return deforestationManager;
	}

	public CollectionManager getCollectionManager() {
		if(this.collectionManager==null){
			this.collectionManager = new CollectionManager();
		}
		return this.collectionManager;
	}

	public CollectionRecordStore getCollectionRecordStore() {
		if(this.collectionRecordStore==null){
			this.collectionRecordStore = new CollectionRecordStore();
		}
		return collectionRecordStore;
	}

	public HashMap<String, MapMark> getMapMarks() { return mapMarks; }

	public void setMapMarks(HashMap<String, MapMark> newMarks) { mapMarks = newMarks; }

	public synchronized void onTick() {
		// Check ping
		if (this.getLastPingTime() > System.currentTimeMillis() + 60000) {
			this.getSession().close();
			return;
		}
		// Check co-op requests
		Iterator<CoopRequest> it = this.getCoopRequests().values().iterator();
		while (it.hasNext()) {
			CoopRequest req = it.next();
			if (req.isExpired()) {
				req.getRequester().sendPacket(new PacketPlayerApplyEnterMpResultNotify(
						this,
						false,
						PlayerApplyEnterMpResultNotifyOuterClass.PlayerApplyEnterMpResultNotify.Reason.REASON_SYSTEM_JUDGE));
				it.remove();
			}
		}
		// Ping
		if (this.getWorld() != null) {
			// RTT notify - very important to send this often
			this.sendPacket(new PacketWorldPlayerRTTNotify(this.getWorld()));

			// Update player locations if in multiplayer every 5 seconds
			long time = System.currentTimeMillis();
			if (this.getWorld().isMultiplayer() && this.getScene() != null && time > nextSendPlayerLocTime) {
				this.sendPacket(new PacketWorldPlayerLocationNotify(this.getWorld()));
				this.sendPacket(new PacketScenePlayerLocationNotify(this.getScene()));
				this.resetSendPlayerLocTime();
			}
		}

		// Handle daily reset.
		this.doDailyReset();

		// Expedition
		var timeNow = Utils.getCurrentSeconds();
		var needNotify = false;
		for (Long key : expeditionInfo.keySet()) {
			ExpeditionInfo e = expeditionInfo.get(key);
			if(e.getState() == 1){
				if(timeNow - e.getStartTime() >= e.getHourTime() * 60 * 60){
					e.setState(2);
					needNotify = true;
				}
			}
		}
		if(needNotify){
			this.save();
			this.sendPacket(new PacketAvatarExpeditionDataNotify(this));
		}

		// Send updated forge queue data, if necessary.
		this.getForgingManager().sendPlayerForgingUpdate();

		// Recharge resin.
		this.getResinManager().rechargeResin();
	}

	private synchronized void doDailyReset() {
		// Check if we should execute a daily reset on this tick.
		int currentTime = Utils.getCurrentSeconds();

		var currentDate = LocalDate.ofInstant(Instant.ofEpochSecond(currentTime), ZoneId.systemDefault());
		var lastResetDate = LocalDate.ofInstant(Instant.ofEpochSecond(this.getLastDailyReset()), ZoneId.systemDefault());

		if (!currentDate.isAfter(lastResetDate)) {
			return;
		}

		// We should - now execute all the resetting logic we need.
		// Reset forge points.
		this.setForgePoints(300_000);

		// Reset daily BP missions.
		this.getBattlePassManager().resetDailyMissions();

		// Trigger login BP mission, so players who are online during the reset
		// don't have to relog to clear the mission.
		this.getBattlePassManager().triggerMission(WatcherTriggerType.TRIGGER_LOGIN);

		// Reset weekly BP missions.
		if (currentDate.getDayOfWeek() == DayOfWeek.MONDAY) {
			this.getBattlePassManager().resetWeeklyMissions();
		}

		// Done. Update last reset time.
		this.setLastDailyReset(currentTime);
	}

	public void resetSendPlayerLocTime() {
		this.nextSendPlayerLocTime = System.currentTimeMillis() + 5000;
	}

	@PostLoad
	private void onLoad() {
		this.getCodex().setPlayer(this);
        this.getOpenStateManager().setPlayer(this);
		this.getTeamManager().setPlayer(this);
		this.getTowerManager().setPlayer(this);
	}

	public void save() {
		DatabaseHelper.savePlayer(this);
	}

	// Called from tokenrsp
	public void loadFromDatabase() {
		// Make sure these exist
		if (this.getTowerManager() == null) {
			this.towerManager = new TowerManager(this);
		}
		if (this.getTeamManager() == null) {
			this.teamManager = new TeamManager(this);
		}
		if (this.getCodex() == null) {
			this.codex = new PlayerCodex(this);
		}
        if (this.getOpenStateManager() == null) {
            this.openStateManager = new PlayerOpenStateManager(this);
        }
		if (this.getProfile().getUid() == 0) {
			this.getProfile().syncWithCharacter(this);
		}
		//Make sure towerManager's player is online player
		this.getTowerManager().setPlayer(this);
		this.getCollectionManager().setPlayer(this);

		// Load from db
		this.getAvatars().loadFromDatabase();
		this.getInventory().loadFromDatabase();
		this.getAvatars().postLoad(); // Needs to be called after inventory is handled

		this.getFriendsList().loadFromDatabase();
		this.getMailHandler().loadFromDatabase();
		this.getQuestManager().loadFromDatabase();

		this.loadBattlePassManager();
	}

	public void onLogin() {
		// Quest - Commented out because a problem is caused if you log out while this quest is active
		/*
		if (getQuestManager().getMainQuestById(351) == null) {
			GameQuest quest = getQuestManager().addQuest(35104);
			if (quest != null) {
				quest.finish();
			}
			getQuestManager().addQuest(35101);

			this.setSceneId(3);
			this.getPos().set(GameConstants.START_POSITION);
		}
		*/

		// Create world
		World world = new World(this);
		world.addPlayer(this);

		// Multiplayer setting
		this.setProperty(PlayerProperty.PROP_PLAYER_MP_SETTING_TYPE, this.getMpSetting().getNumber(), false);
		this.setProperty(PlayerProperty.PROP_IS_MP_MODE_AVAILABLE, 1, false);

		// Execute daily reset logic if this is a new day.
		this.doDailyReset();

		// Packets
		session.send(new PacketPlayerDataNotify(this)); // Player data
		session.send(new PacketStoreWeightLimitNotify());
		session.send(new PacketPlayerStoreNotify(this));
		session.send(new PacketAvatarDataNotify(this));
		session.send(new PacketFinishedParentQuestNotify(this));
		session.send(new PacketBattlePassAllDataNotify(this));
		session.send(new PacketQuestListNotify(this));
		session.send(new PacketCodexDataFullNotify(this));
		session.send(new PacketAllWidgetDataNotify(this));
		session.send(new PacketWidgetGadgetAllDataNotify());
		session.send(new PacketCombineDataNotify(this.unlockedCombines));
		this.forgingManager.sendForgeDataNotify();
		this.resinManager.onPlayerLogin();
		this.cookingManager.sendCookDataNofity();

		// Unlock in case this is an existing user that reached a level before we implemented unlocking.
		this.openStateManager.unlockLevelDependentStates();
        this.openStateManager.onPlayerLogin();

		getTodayMoonCard(); // The timer works at 0:0, some users log in after that, use this method to check if they have received a reward today or not. If not, send the reward.

		// Battle Pass trigger
		this.getBattlePassManager().triggerMission(WatcherTriggerType.TRIGGER_LOGIN);

		this.furnitureManager.onLogin();
		// Home
		home = GameHome.getByUid(getUid());
		home.onOwnerLogin(this);
        // Activity
        activityManager = new ActivityManager(this);

		session.send(new PacketPlayerEnterSceneNotify(this)); // Enter game world
		session.send(new PacketPlayerLevelRewardUpdateNotify(rewardedLevels));


		// First notify packets sent
		this.setHasSentAvatarDataNotify(true);

		// Send server welcome chat.
		this.getServer().getChatManager().sendServerWelcomeMessages(this);
		
		// Set session state
		session.setState(SessionState.ACTIVE);

		// Call join event.
		PlayerJoinEvent event = new PlayerJoinEvent(this); event.call();
		if(event.isCanceled()){ // If event is not cancelled, continue.
			session.close();
			return;
		}

		// register
		getServer().registerPlayer(this);
		getProfile().setPlayer(this); // Set online
	}

	public void onLogout() {
		try{
			// Clear chat history.
			this.getServer().getChatManager().clearHistoryOnLogout(this);

			// stop stamina calculation
			getStaminaManager().stopSustainedStaminaHandler();

			// force to leave the dungeon (inside has a "if")
			this.getServer().getDungeonManager().exitDungeon(this);

			// Leave world
			if (this.getWorld() != null) {
				this.getWorld().removePlayer(this);
			}

			// Status stuff
			this.getProfile().syncWithCharacter(this);
			this.getProfile().setPlayer(null); // Set offline

			this.getCoopRequests().clear();

			// Save to db
			this.save();
			this.getTeamManager().saveAvatars();
			this.getFriendsList().save();

			// Call quit event.
			PlayerQuitEvent event = new PlayerQuitEvent(this); event.call();
		}catch (Throwable e){
			e.printStackTrace();
			Grasscutter.getLogger().warn("Player (UID {}) save failure", getUid());
		}finally {
			removeFromServer();
		}
	}

	public void removeFromServer() {
		// Remove from server.
		//Note: DON'T DELETE BY UID,BECAUSE THERE ARE MULTIPLE SAME UID PLAYERS WHEN DUPLICATED LOGIN!
		//so I decide to delete by object rather than uid
		getServer().getPlayers().values().removeIf(player1 -> player1 == this);
	}

    public int getLegendaryKey() {
        return this.getProperty(PlayerProperty.PROP_PLAYER_LEGENDARY_KEY);
    }
    public synchronized void addLegendaryKey(int count) {
        this.setProperty(PlayerProperty.PROP_PLAYER_LEGENDARY_KEY, getLegendaryKey() + count);
    }
    public synchronized void useLegendaryKey(int count) {
        this.setProperty(PlayerProperty.PROP_PLAYER_LEGENDARY_KEY, getLegendaryKey() - count);
    }

    public enum SceneLoadState {
		NONE(0), LOADING(1), INIT(2), LOADED(3);

		private final int value;

		private SceneLoadState(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}
	}

	public void setMessageHandler(MessageHandler messageHandler) {
		this.messageHandler = messageHandler;
	}

	public int getPropertyMin(PlayerProperty prop) {
		if (prop.getDynamicRange()) {
			return switch (prop) {
				default -> 0;
			};
		} else {
			return prop.getMin();
		}
	}

	public int getPropertyMax(PlayerProperty prop) {
		if (prop.getDynamicRange()) {
			return switch (prop) {
				case PROP_CUR_SPRING_VOLUME -> getProperty(PlayerProperty.PROP_MAX_SPRING_VOLUME);
				case PROP_CUR_PERSIST_STAMINA -> getProperty(PlayerProperty.PROP_MAX_STAMINA);
				default -> 0;
			};
		} else {
			return prop.getMax();
		}
	}

	private boolean setPropertyWithSanityCheck(PlayerProperty prop, int value, boolean sendPacket) {
		int min = this.getPropertyMin(prop);
		int max = this.getPropertyMax(prop);
		if (min <= value && value <= max) {
			this.properties.put(prop.getId(), value);
			if (sendPacket) {
				// Update player with packet
				this.sendPacket(new PacketPlayerPropNotify(this, prop));
			}
			return true;
		} else {
		return false;
	}
	}

}
