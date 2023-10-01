package emu.grasscutter.game.player;

import dev.morphia.annotations.*;
import emu.grasscutter.*;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.PlayerLevelData;
import emu.grasscutter.data.excels.world.WeatherData;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.*;
import emu.grasscutter.game.ability.AbilityManager;
import emu.grasscutter.game.achievement.Achievements;
import emu.grasscutter.game.activity.ActivityManager;
import emu.grasscutter.game.avatar.*;
import emu.grasscutter.game.battlepass.BattlePassManager;
import emu.grasscutter.game.city.CityInfoData;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.expedition.ExpeditionInfo;
import emu.grasscutter.game.friends.*;
import emu.grasscutter.game.gacha.PlayerGachaInfo;
import emu.grasscutter.game.home.*;
import emu.grasscutter.game.inventory.*;
import emu.grasscutter.game.mail.*;
import emu.grasscutter.game.managers.*;
import emu.grasscutter.game.managers.cooking.*;
import emu.grasscutter.game.managers.deforestation.DeforestationManager;
import emu.grasscutter.game.managers.energy.EnergyManager;
import emu.grasscutter.game.managers.forging.*;
import emu.grasscutter.game.managers.mapmark.*;
import emu.grasscutter.game.managers.stamina.StaminaManager;
import emu.grasscutter.game.props.*;
import emu.grasscutter.game.quest.QuestManager;
import emu.grasscutter.game.quest.enums.*;
import emu.grasscutter.game.shop.ShopLimit;
import emu.grasscutter.game.talk.TalkManager;
import emu.grasscutter.game.tower.*;
import emu.grasscutter.game.world.*;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.proto.AbilityInvokeEntryOuterClass.AbilityInvokeEntry;
import emu.grasscutter.net.proto.AttackResultOuterClass.AttackResult;
import emu.grasscutter.net.proto.CombatInvokeEntryOuterClass.CombatInvokeEntry;
import emu.grasscutter.net.proto.GadgetInteractReqOuterClass.GadgetInteractReq;
import emu.grasscutter.net.proto.MpSettingTypeOuterClass.MpSettingType;
import emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.OnlinePlayerInfo;
import emu.grasscutter.net.proto.*;
import emu.grasscutter.net.proto.PlayerLocationInfoOuterClass.PlayerLocationInfo;
import emu.grasscutter.net.proto.ProfilePictureOuterClass.ProfilePicture;
import emu.grasscutter.net.proto.PropChangeReasonOuterClass.PropChangeReason;
import emu.grasscutter.net.proto.SocialDetailOuterClass.SocialDetail;
import emu.grasscutter.plugin.api.PlayerHook;
import emu.grasscutter.scripts.ScriptLoader;
import emu.grasscutter.scripts.data.SceneRegion;
import emu.grasscutter.server.event.player.*;
import emu.grasscutter.server.game.*;
import emu.grasscutter.server.game.GameSession.SessionState;
import emu.grasscutter.server.packet.send.*;
import emu.grasscutter.utils.*;
import emu.grasscutter.utils.helpers.DateHelper;
import emu.grasscutter.utils.objects.FieldFetch;
import it.unimi.dsi.fastutil.ints.*;
import lombok.*;

import java.time.*;
import java.util.*;
import java.util.concurrent.*;

import static emu.grasscutter.config.Configuration.GAME_OPTIONS;

@Entity(value = "players", useDiscriminator = false)
public class Player implements PlayerHook, FieldFetch {
    @Id private int id;
    @Indexed(options = @IndexOptions(unique = true))
    @Getter private String accountId;
    @Setter private transient Account account;
    @Getter @Setter private transient GameSession session;
    @Transient private String sessionKey;

    @Getter private String nickname;
    @Getter private String signature;
    @Getter private int headImage;
    @Getter private int nameCardId = 210001;
    @Getter private Position position;
    @Getter @Setter private Position prevPos;
    @Getter @Setter private Position prevPosForHome;
    @Getter @Setter private int prevScene;
    @Getter private Position rotation;
    @Getter private PlayerBirthday birthday;
    @Getter private PlayerCodex codex;
    @Getter @Setter private boolean showAvatars;
    @Getter @Setter private List<Integer> showAvatarList;
    @Getter @Setter private List<Integer> showNameCardList;
    @Getter private Map<Integer, Integer> properties;
    @Getter @Setter private int currentRealmId;
    @Getter @Setter private transient boolean isInEditMode;
    @Getter @Setter private int widgetId;
    @Getter @Setter private int sceneId;
    @Getter @Setter private int regionId;
    @Getter private int mainCharacterId;
    @Getter @Setter private boolean inGodMode;
    @Getter @Setter private boolean unlimitedStamina;

    @Getter private Set<Integer> nameCardList;
    @Getter private Set<Integer> flyCloakList;
    @Getter private Set<Integer> costumeList;
    @Getter private Set<Integer> personalLineList;
    @Getter @Setter private Set<Integer> rewardedLevels;
    @Getter @Setter private Set<Integer> homeRewardedLevels;
    @Getter @Setter private Set<Integer> realmList;
    @Getter @Setter private Set<Integer> seenRealmList;
    @Getter private Set<Integer> unlockedForgingBlueprints;
    @Getter private Set<Integer> unlockedCombines;
    @Getter private Set<Integer> unlockedFurniture;
    @Getter private Set<Integer> unlockedFurnitureSuite;
    @Getter private Map<Long, ExpeditionInfo> expeditionInfo;
    @Getter private Map<Integer, Integer> unlockedRecipies;
    @Getter private List<ActiveForgeData> activeForges;
    @Getter private Map<Integer, ActiveCookCompoundData> activeCookCompounds;
    @Getter private Map<Integer, Integer> questGlobalVariables;
    @Getter private Map<Integer, Integer> openStates;
    @Getter private Map<Integer, Set<Integer>> sceneTags;
    @Getter @Setter private Map<Integer, Set<Integer>> unlockedSceneAreas;
    @Getter @Setter private Map<Integer, Set<Integer>> unlockedScenePoints;
    @Getter @Setter private List<Integer> chatEmojiIdList;

    @Transient private long nextGuid = 0;
    @Transient @Getter @Setter private int peerId;
    @Transient private World world;  // Synchronized getter and setter
    @Transient @Getter @Setter private HomeWorld curHomeWorld;
    @Transient @Getter @Setter private boolean hasSentInitPacketInHome;
    @Transient private Scene scene;  // Synchronized getter and setter
    @Transient @Getter private int weatherId = 0;
    @Transient @Getter private ClimateType climate = ClimateType.CLIMATE_SUNNY;
    @Transient @Getter private int areaId = 0;
    @Transient @Getter private int areaType = 0;

    // Player managers go here
    @Getter private transient AvatarStorage avatars;
    @Getter private transient Inventory inventory;
    @Getter private transient FriendsList friendsList;
    @Getter private transient MailHandler mailHandler;
    @Getter private transient AbilityManager abilityManager;
    @Getter private transient QuestManager questManager;
    @Getter private transient TowerManager towerManager;
    @Getter private transient SotSManager sotsManager;
    @Getter private transient MapMarksManager mapMarksManager;
    @Getter private transient StaminaManager staminaManager;
    @Getter private transient EnergyManager energyManager;
    @Getter private transient ResinManager resinManager;
    @Getter private transient ForgingManager forgingManager;
    @Getter private transient DeforestationManager deforestationManager;
    @Getter private transient FurnitureManager furnitureManager;
    @Getter private transient BattlePassManager battlePassManager;
    @Getter private transient CookingManager cookingManager;
    @Getter private transient CookingCompoundManager cookingCompoundManager;
    @Getter private transient ActivityManager activityManager;
    @Getter private transient PlayerBuffManager buffManager;
    @Getter private transient PlayerProgressManager progressManager;
    @Getter private transient SatiationManager satiationManager;
    @Getter private transient TalkManager talkManager;

    @Getter @Setter private transient Position lastCheckedPosition = null;

    // Manager data (Save-able to the database)
    @Getter private transient Achievements achievements;
    private PlayerProfile playerProfile;  // Getter has null-check
    @Getter private TeamManager teamManager;
    private TowerData towerData;  // Getter has null-check
    @Getter private PlayerGachaInfo gachaInfo;
    private PlayerCollectionRecords collectionRecordStore;  // Getter has null-check
    @Getter private ArrayList<ShopLimit> shopLimit;

    @Getter private transient GameHome home;

    @Setter private boolean moonCard;  // Getter is inMoonCard
    @Getter @Setter private Date moonCardStartTime;
    @Getter @Setter private int moonCardDuration;
    @Getter @Setter private Set<Date> moonCardGetTimes;

    @Transient @Getter private boolean paused;
    @Transient @Getter @Setter private int enterSceneToken;
    @Transient @Getter @Setter private SceneLoadState sceneLoadState = SceneLoadState.NONE;
    @Transient private boolean hasSentLoginPackets;
    @Transient private long nextSendPlayerLocTime = 0;
    @Getter private transient final Int2ObjectMap<EnterHomeRequest> enterHomeRequests;

    private transient final Int2ObjectMap<CoopRequest> coopRequests;  // Synchronized getter
    @Getter private transient final Queue<AttackResult> attackResults;
    @Getter private transient final InvokeHandler<CombatInvokeEntry> combatInvokeHandler;
    @Getter private transient final InvokeHandler<AbilityInvokeEntry> abilityInvokeHandler;
    @Getter private transient final InvokeHandler<AbilityInvokeEntry> clientAbilityInitFinishHandler;

    @Getter @Setter private long springLastUsed;
    private HashMap<String, MapMark> mapMarks;  // Getter makes an empty hashmap - maybe do this elsewhere?
    @Getter @Setter private int nextResinRefresh;
    @Getter @Setter private int resinBuyCount;
    @Getter @Setter private int lastDailyReset;
    @Getter private transient MpSettingType mpSetting = MpSettingType.MP_SETTING_TYPE_ENTER_AFTER_APPLY;
    @Getter private long playerGameTime = 540000; // 9 in-game hours. Present at the start of the game.

    @Getter private PlayerProgress playerProgress;
    @Getter private Set<Integer> activeQuestTimers;

    @Getter @Setter private ElementType mainCharacterElement = ElementType.None;

    @Getter @Setter private Map<Integer, CityInfoData> cityInfoData; // cityId -> CityData

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
        this.questManager = new QuestManager(this);
        this.buffManager = new PlayerBuffManager(this);
        this.position = new Position(GameConstants.START_POSITION);
        this.prevPos = new Position();
        this.prevPosForHome = Position.ZERO;
        this.rotation = new Position(0, 307, 0);
        this.sceneId = 3;
        this.regionId = 1;
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
        this.personalLineList = new HashSet<>();
        this.towerData = new TowerData();
        this.collectionRecordStore = new PlayerCollectionRecords();
        this.unlockedForgingBlueprints = new HashSet<>();
        this.unlockedCombines = new HashSet<>();
        this.unlockedFurniture = new HashSet<>();
        this.unlockedFurnitureSuite = new HashSet<>();
        this.activeCookCompounds = new HashMap<>();
        this.activeForges = new ArrayList<>();
        this.unlockedRecipies = new HashMap<>();
        this.questGlobalVariables = new HashMap<>();
        this.openStates = new HashMap<>();
        this.sceneTags = new HashMap<>();
        this.unlockedSceneAreas = new HashMap<>();
        this.unlockedScenePoints = new HashMap<>();
        this.chatEmojiIdList = new ArrayList<>();
        this.playerProgress = new PlayerProgress();
        this.activeQuestTimers = new HashSet<>();
        this.cityInfoData = new HashMap<>();

        this.attackResults = new LinkedBlockingQueue<>();
        this.coopRequests = new Int2ObjectOpenHashMap<>();
        this.enterHomeRequests = new Int2ObjectOpenHashMap<>();
        this.combatInvokeHandler = new InvokeHandler(PacketCombatInvocationsNotify.class);
        this.abilityInvokeHandler = new InvokeHandler(PacketAbilityInvocationsNotify.class);
        this.clientAbilityInitFinishHandler = new InvokeHandler(PacketClientAbilityInitFinishNotify.class);

        this.birthday = new PlayerBirthday();
        this.rewardedLevels = new HashSet<>();
        this.homeRewardedLevels = new HashSet<>();
        this.seenRealmList = new HashSet<>();
        this.moonCardGetTimes = new HashSet<>();
        this.codex = new PlayerCodex(this);
        this.progressManager = new PlayerProgressManager(this);
        this.shopLimit = new ArrayList<>();
        this.expeditionInfo = new HashMap<>();
        this.mapMarksManager = new MapMarksManager(this);
        this.staminaManager = new StaminaManager(this);
        this.sotsManager = new SotSManager(this);
        this.energyManager = new EnergyManager(this);
        this.resinManager = new ResinManager(this);
        this.forgingManager = new ForgingManager(this);
        this.progressManager = new PlayerProgressManager(this);
        this.furnitureManager = new FurnitureManager(this);
        this.cookingManager = new CookingManager(this);
        this.cookingCompoundManager = new CookingCompoundManager(this);
        this.satiationManager = new SatiationManager(this);
        this.talkManager = new TalkManager(this);
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

        this.applyProperties();
        this.applyStartingSceneTags();
        this.getFlyCloakList().add(140001);
        this.getNameCardList().add(210001);

        this.mapMarksManager = new MapMarksManager(this);
        this.staminaManager = new StaminaManager(this);
        this.sotsManager = new SotSManager(this);
        this.energyManager = new EnergyManager(this);
        this.resinManager = new ResinManager(this);
        this.deforestationManager = new DeforestationManager(this);
        this.forgingManager = new ForgingManager(this);
        this.progressManager = new PlayerProgressManager(this);
        this.furnitureManager = new FurnitureManager(this);
        this.cookingManager = new CookingManager(this);
        this.cookingCompoundManager = new CookingCompoundManager(this);
        this.satiationManager = new SatiationManager(this);
    }

    @Override
    public Player getPlayer() {
        return this;
    }

    /**
     * Updates the player's game time if it has changed.
     *
     * @param gameTime The new game time.
     */
    public void updatePlayerGameTime(long gameTime) {
        if (this.playerGameTime == gameTime) return;

        // Update the game time.
        this.playerGameTime = gameTime;

        // If the player is the host of the world, update the game time as well.
        var world = this.getWorld();
        if (world != null && world.getHost() == this) {
            world.changeTime(gameTime);
        }

        this.save();
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
            this.account = DatabaseHelper.getAccountById(this.accountId);
        return this.account;
    }

    /**
     * @return The player's session key.
     */
    public String getSessionKey() {
        if (this.sessionKey == null) {
            // Check if the account is null.
            if (this.account == null) {
                this.account = DispatchUtils.getAccountById(this.getAccountId());
            }
            if (this.account == null) return "";

            // Get the session key.
            this.sessionKey = this.getAccount().getSessionKey();
        }

        return this.sessionKey;
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

    public synchronized void setWeather(int weather) {
        this.setWeather(weather, ClimateType.CLIMATE_NONE);
    }

    public synchronized void setWeather(int weatherId, ClimateType climate) {
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

    /**
     * Sets the player's weather and climate.
     *
     * @param areaId The area ID.
     * @param areaType The area type.
     */
    public void setArea(int areaId, int areaType) {
        this.areaId = areaId;
        this.areaType = areaType;

        // Call the event.
        var event = new PlayerEnterAreaEvent(this);
        event.call();
    }

    public void setNickname(String nickName) {
        this.nickname = nickName;
        this.updateProfile();
    }

    public void setHeadImage(int picture) {
        this.headImage = picture;
        this.updateProfile();
    }

    public void setSignature(String signature) {
        this.signature = signature;
        this.updateProfile();
    }

    public void addRealmList(int realmId) {
        if (this.realmList == null) {
            this.realmList = new HashSet<>();
        } else if (this.realmList.contains(realmId)) {
            return;
        }
        this.realmList.add(realmId);

        // Tell the client the realm is unlocked
        if (realmId > 3) { // Realms 3 and below are default 'unlocked'
            this.sendPacket(new PacketHomeModuleUnlockNotify(realmId));
            this.getHome().onClaimReward(this);
        }
    }

    public void addSeenRealmList(int seenId) {
        if (this.seenRealmList == null) {
            this.seenRealmList = new HashSet<>();
        } else if (this.seenRealmList.contains(seenId)) {
            return;
        }
        this.seenRealmList.add(seenId);
    }

    public Optional<GameHome> tryGetHome() {
        if (this.isOnline()) {
            return Optional.ofNullable(this.home);
        }

        if (GameHome.doesHomeExist(this.getUid())) {
            this.home = GameHome.getByUid(this.getUid());
        }

        return Optional.ofNullable(this.home);
    }

    public int getExpeditionLimit() {
        final int CONST_VALUE_EXPEDITION_INIT_LIMIT = 2;  // TODO: pull from ConstValueExcelConfigData.json
        int expeditionLimit = CONST_VALUE_EXPEDITION_INIT_LIMIT;
        var levelMap = GameData.getPlayerLevelDataMap();
        for (int i = 1; i <= this.getLevel(); i++) {  // 1-indexed
            var data = levelMap.get(i);
            if (data != null)
                expeditionLimit += data.getExpeditionLimitAdd();
        }
        return expeditionLimit;
    }

    public Set<Integer> getUnlockedSceneAreas(int sceneId) {
        return this.unlockedSceneAreas.computeIfAbsent(sceneId, i -> new CopyOnWriteArraySet<>());
    }

    public Set<Integer> getUnlockedScenePoints(int sceneId) {
        return this.unlockedScenePoints.computeIfAbsent(sceneId, i -> new CopyOnWriteArraySet<>());
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

            // Handle open state unlocks from level-up.
            this.getProgressManager().tryUnlockOpenStates();
            this.getQuestManager().queueEvent(QuestContent.QUEST_CONTENT_PLAYER_LEVEL_UP, level);
            this.getQuestManager().queueEvent(QuestCond.QUEST_COND_PLAYER_LEVEL_EQUAL_GREATER, level);

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

    /**
     * Applies the properties to the player.
     */
    private void applyProperties() {
        var withQuesting = GAME_OPTIONS.questing.enabled;

        this.setOrFetch(PlayerProperty.PROP_PLAYER_LEVEL, 1);
        this.setOrFetch(PlayerProperty.PROP_IS_SPRING_AUTO_USE, 1);
        this.setOrFetch(PlayerProperty.PROP_SPRING_AUTO_USE_PERCENT, 50);
        this.setOrFetch(PlayerProperty.PROP_IS_FLYABLE,
            withQuesting ? 0 : 1);
        this.setOrFetch(PlayerProperty.PROP_PLAYER_CAN_DIVE,
                withQuesting ? 0 : 1);
        this.setOrFetch(PlayerProperty.PROP_IS_TRANSFERABLE, 1);
        this.setOrFetch(PlayerProperty.PROP_MAX_STAMINA,
            withQuesting ? 10000 : 24000);
        this.setOrFetch(PlayerProperty.PROP_DIVE_MAX_STAMINA,
                withQuesting ? 10000 : 0);
        this.setOrFetch(PlayerProperty.PROP_PLAYER_RESIN, 160);

        // The player's current stamina is always their max stamina.
        this.setProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA,
            this.getProperty(PlayerProperty.PROP_MAX_STAMINA));
        this.setProperty(PlayerProperty.PROP_DIVE_CUR_STAMINA,
                this.getProperty(PlayerProperty.PROP_DIVE_MAX_STAMINA));
    }

    /**
     * Applies all default scenetags to the player.
     */
    private void applyStartingSceneTags() {
        GameData.getSceneTagDataMap().values().stream()
                .filter(sceneTag -> sceneTag.isDefaultValid())
                .forEach(sceneTag -> {
                    if (this.getSceneTags().get(sceneTag.getSceneId()) == null) {
                        this.getSceneTags().put(sceneTag.getSceneId(), new HashSet<>());
                    }
                    this.getSceneTags().get(sceneTag.getSceneId()).add(sceneTag.getId());
                });
    }

    /**
     * Applies a property to the player if it doesn't exist in the database.
     *
     * @param property The property to apply.
     * @param defaultValue The value to apply if the property doesn't exist.
     */
    private void setOrFetch(PlayerProperty property, int defaultValue) {
        var exists = this.properties.containsKey(property.getId());
        if (exists) exists = this.getProperty(property) != 0;
        this.setProperty(property, exists ? this.getProperty(property)
            : defaultValue, false);
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
        return !this.hasSentLoginPackets;
    }

    public TowerData getTowerData() {
        if (towerData == null) {
            // because of mistake, null may be saved as storage at some machine, this if can be removed in future
            towerData = new TowerData();
        }
        return towerData;
    }

    public void onEnterRegion(SceneRegion region) {
        var enterRegionName = "ENTER_REGION_" + region.config_id;
        this.getQuestManager().forEachActiveQuest(quest -> {
            if (quest.getTriggerData() != null &&
                quest.getTriggers().containsKey(enterRegionName) &&
                region.getGroupId() == quest.getTriggerData().get(enterRegionName).getGroupId()) {
                // If trigger hasn't been fired yet
                if (!Boolean.TRUE.equals(quest.getTriggers().put(enterRegionName, true))) {
                    this.getSession().send(new PacketServerCondMeetQuestListUpdateNotify());
                    this.getQuestManager().queueEvent(QuestContent.QUEST_CONTENT_TRIGGER_FIRE,
                        quest.getTriggerData().get(enterRegionName).getId(), 0);
                }
            }
        });

    }

    public void onLeaveRegion(SceneRegion region) {
        var leaveRegionName = "LEAVE_REGION_" + region.config_id;
        this.getQuestManager().forEachActiveQuest(quest -> {
            if (quest.getTriggers().containsKey(leaveRegionName) &&
                region.getGroupId() == quest.getTriggerData().get(leaveRegionName).getGroupId()) {
                // If trigger hasn't been fired yet
                if (!Boolean.TRUE.equals(quest.getTriggers().put(leaveRegionName, true))) {
                    this.getSession().send(new PacketServerCondMeetQuestListUpdateNotify());
                    this.getQuestManager().queueEvent(QuestContent.QUEST_CONTENT_TRIGGER_FIRE,
                        quest.getTriggerData().get(leaveRegionName).getId(), 0);
                }
            }
        });
    }

    public PlayerProfile getProfile() {
        if (this.playerProfile == null) {
            this.playerProfile = new PlayerProfile(this);
        }
        return playerProfile;
    }

    /**
     * Sets a player's property.
     *
     * @param prop The property.
     * @param value The value as a boolean.
     * @return True if the property was set.
     */
    public boolean setProperty(PlayerProperty prop, boolean value) {
        return setPropertyWithSanityCheck(prop, value ? 1 : 0, true);
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

    public synchronized Int2ObjectMap<CoopRequest> getCoopRequests() {
        return coopRequests;
    }

    public void setNameCardId(int nameCardId) {
        this.nameCardId = nameCardId;
        this.updateProfile();
    }

    public void setMainCharacterId(int mainCharacterId) {
        if (this.mainCharacterId != 0) {
            return;
        }
        this.mainCharacterId = mainCharacterId;
    }

    public int getClientTime() {
        return session.getClientTime();
    }

    public long getLastPingTime() {
        return session.getLastPingTime();
    }

    public void setPaused(boolean newPauseState) {
        var oldPauseState = this.paused;
        this.paused = newPauseState;

        if (newPauseState && !oldPauseState) {
            this.onPause();
        } else if (oldPauseState && !newPauseState) {
            this.onUnpause();
        }
    }

    public boolean isInMultiplayer() {
        return this.getWorld() != null && this.getWorld().isMultiplayer();
    }

    public boolean inMoonCard() {
        return moonCard;
    }

    public void addMoonCardDays(int days) {
        this.moonCardDuration += days;
    }

    public int getMoonCardRemainDays() {
        Calendar remainCalendar = Calendar.getInstance();
        remainCalendar.setTime(moonCardStartTime);
        remainCalendar.add(Calendar.DATE, moonCardDuration);
        Date theLastDay = remainCalendar.getTime();
        Date now = DateHelper.onlyYearMonthDay(new Date());
        return (int) ((theLastDay.getTime() - now.getTime()) / (24 * 60 * 60 * 1000)); // By copilot
    }

    public boolean rechargeMoonCard() {
        if (this.moonCardDuration > 150) return false;  // Can only stack up to 180 days
        inventory.addItem(new GameItem(203, 300));
        if (!moonCard) {
            moonCard = true;
            Date now = new Date();
            moonCardStartTime = DateHelper.onlyYearMonthDay(now);
            moonCardDuration = 30;
        } else {
            moonCardDuration += 30;
        }
        moonCardGetTimes.add(moonCardStartTime);
        return true;
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

    public void addExpeditionInfo(long avatarGuid, int expId, int hourTime, int startTime) {
        ExpeditionInfo exp = new ExpeditionInfo();
        exp.setExpId(expId);
        exp.setHourTime(hourTime);
        exp.setState(1);
        exp.setStartTime(startTime);
        expeditionInfo.put(avatarGuid, exp);
    }

    public void removeExpeditionInfo(long avatarGuid) {
        expeditionInfo.remove(avatarGuid);
    }

    public ExpeditionInfo getExpeditionInfo(long avatarGuid) {
        return expeditionInfo.get(avatarGuid);
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

    public boolean hasSentLoginPackets() {
        return hasSentLoginPackets;
    }

    public void addAvatar(Avatar avatar, boolean addToCurrentTeam) {
        boolean result = getAvatars().addAvatar(avatar);

        if (result) {
            // Add starting weapon
            getAvatars().addStartingWeapon(avatar);

            // Done
            if (hasSentLoginPackets()) {
                // Recalc stats
                avatar.recalcStats();
                // Packet, show notice on left if the avatar will be added to the team
                sendPacket(new PacketAvatarAddNotify(avatar, addToCurrentTeam && this.getTeamManager().canAddAvatarToCurrentTeam()));
                if (addToCurrentTeam) {
                    // If space in team, add
                    this.getTeamManager().addAvatarToCurrentTeam(avatar);
                }
            }

            // Call PlayerObtainAvatarEvent.
            new PlayerObtainAvatarEvent(this.getPlayer(), avatar).call();
        } else {
            // Failed adding avatar
        }
    }

    public void addAvatar(Avatar avatar) {
        addAvatar(avatar, true);
    }

    public void addAvatar(int avatarId) {
        // I dont see why we cant do this lolz
        addAvatar(new Avatar(avatarId), true);
    }

    public void addFlycloak(int flycloakId) {
        this.getFlyCloakList().add(flycloakId);
        this.sendPacket(new PacketAvatarGainFlycloakNotify(flycloakId));
    }

    public void addCostume(int costumeId) {
        this.getCostumeList().add(costumeId);
        this.sendPacket(new PacketAvatarGainCostumeNotify(costumeId));
    }

    public int getCostumeFrom(int avatarId) {
        var avatars = this.getAvatars();
        avatars.loadFromDatabase();
        var avatar = avatars.getAvatarById(avatarId);
        return avatar == null ? 0 : avatar.getCostume();
    }

    public void addPersonalLine(int personalLineId) {
        this.getPersonalLineList().add(personalLineId);
        session.getPlayer().getQuestManager().queueEvent(QuestCond.QUEST_COND_PERSONAL_LINE_UNLOCK, personalLineId);
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

    /**
     * Sends a message to this player.
     *
     * @param message The message to send.
     */
    public void dropMessage(Object message) {
        this.getServer().getChatSystem().sendPrivateMessageFromServer(getUid(), message.toString());
    }

    /**
     * Sends a message to another player.
     *
     * @param sender  The sender of the message.
     * @param message The message to send.
     */
    public void sendMessage(Player sender, Object message) {
        this.getServer().getChatSystem().sendPrivateMessage(sender, this.getUid(), message.toString());
    }

    // ---------------------MAIL------------------------

    public List<Mail> getAllMail() {
        return this.getMailHandler().getMail();
    }

    public void sendMail(Mail message) {
        this.getMailHandler().sendMail(message);
    }

    public boolean deleteMail(int mailId) {
        return this.getMailHandler().deleteMail(mailId);
    }

    public Mail getMail(int index) {
        return this.getMailHandler().getMailById(index);
    }

    public int getMailId(Mail message) {
        return this.getMailHandler().getMailIndex(message);
    }

    public boolean replaceMailByIndex(int index, Mail message) {
        return this.getMailHandler().replaceMailByIndex(index, message);
    }

    public void interactWith(int gadgetEntityId, GadgetInteractReq interactReq) {
        GameEntity target = getScene().getEntityById(gadgetEntityId);

        if (target == null) {
            return;
        }

        target.onInteract(this, interactReq);
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

    public void setBirthday(int d, int m) {
        this.birthday = new PlayerBirthday(d, m);
        this.updateProfile();
    }

    public boolean hasBirthday() {
        return this.birthday.getDay() > 0;
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

        return SocialDetail.newBuilder()
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
            .addAllShowNameCardIdList(this.getShowNameCardInfoList())
            .setFinishAchievementNum(this.getFinishedAchievementNum())
            .setFriendEnterHomeOptionValue(this.getHome() == null ? 0 : this.getHome().getEnterHomeOption());
    }

    public int getFinishedAchievementNum() {
        return Achievements.getByPlayer(this).getFinishedAchievementNum();
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

    public List<Integer> getShowNameCardInfoList() {
        List<Integer> info = this.getShowNameCardList();
        return info == null ? new ArrayList<>() : info;
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
            .setPos(this.getPosition().toProto())
            .setRot(this.getRotation().toProto())
            .build();
    }

    public void loadBattlePassManager() {
        if (this.battlePassManager != null) return;
        this.battlePassManager = DatabaseHelper.loadBattlePass(this);
        this.battlePassManager.getMissions().values().removeIf(mission -> mission.getData() == null);
    }

    public PlayerCollectionRecords getCollectionRecordStore() {
        if (this.collectionRecordStore == null) {
            this.collectionRecordStore = new PlayerCollectionRecords();
        }
        return collectionRecordStore;
    }

    public Map<String, MapMark> getMapMarks() {
        if (this.mapMarks == null) {
            this.mapMarks = new HashMap<String, MapMark>();
        }
        return mapMarks;
    }

    private boolean expireCoopRequest(CoopRequest req) {
        if (!req.isExpired()) return false;
        req.getRequester().sendPacket(new PacketPlayerApplyEnterMpResultNotify(
            this,
            false,
            PlayerApplyEnterMpResultNotifyOuterClass.PlayerApplyEnterMpResultNotify.Reason.REASON_SYSTEM_JUDGE));
        return true;
    }

    private boolean expireEnterHomeRequest(EnterHomeRequest req) {
        return this.expireEnterHomeRequest(req, false);
    }

    private boolean expireEnterHomeRequest(EnterHomeRequest req, boolean force) {
        if (!req.isExpired() && !force) return false;
        req.getRequester().sendPacket(new PacketPlayerApplyEnterHomeResultNotify(
            this.getUid(),
            this.getNickname(),
            false,
            PlayerApplyEnterHomeResultNotifyOuterClass.PlayerApplyEnterHomeResultNotify.Reason.SYSTEM_JUDGE));
        req.getRequester().sendPacket(new PacketTryEnterHomeRsp());
        return true;
    }

    public synchronized void onTick() {
        // Check ping
        if (this.getLastPingTime() > System.currentTimeMillis() + 60000) {
            this.getSession().close();
            return;
        }
        // Check co-op requests
        this.getCoopRequests().values().removeIf(this::expireCoopRequest);
        // Check enter-home requests
        this.getEnterHomeRequests().values().removeIf(this::expireEnterHomeRequest);
        // Handle buff
        this.getBuffManager().onTick();
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
        for (ExpeditionInfo e : expeditionInfo.values()) {
            if (e.getState() == 1) {
                if (timeNow - e.getStartTime() >= e.getHourTime() * 60 * 60) {
                    e.setState(2);
                    needNotify = true;
                }
            }
        }
        if (needNotify) {
            this.save();
            this.sendPacket(new PacketAvatarExpeditionDataNotify(this.getExpeditionInfo()));
        }

        // Send updated forge queue data, if necessary.
        this.getForgingManager().sendPlayerForgingUpdate();

        // Recharge resin.
        this.getResinManager().rechargeResin();

        // Satiation
        this.getSatiationManager().reduceSatiation();

        // Home resources
        this.getHome().updateHourlyResources(this);

        this.getQuestManager().onTick();
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

        // Reset resin-buying count.
        this.setResinBuyCount(0);

        // Done. Update last reset time.
        this.setLastDailyReset(currentTime);
    }

    public void resetSendPlayerLocTime() {
        this.nextSendPlayerLocTime = System.currentTimeMillis() + 5000;
    }

    @PostLoad
    private void onLoad() {
        this.getCodex().setPlayer(this);
        this.getProgressManager().setPlayer(this);
        this.getTeamManager().setPlayer(this);
    }

    public void save() {
        DatabaseHelper.savePlayer(this);
    }

    // Called from tokenrsp
    public void loadFromDatabase() {
        // Make sure these exist
        if (this.getTeamManager() == null) {
            this.teamManager = new TeamManager(this);
        }
        if (this.getCodex() == null) {
            this.codex = new PlayerCodex(this);
        }
        if (this.getProfile().getUid() == 0) {
            this.getProfile().syncWithCharacter(this);
        }

        // Load from db
        var runner = Grasscutter.getThreadPool();
        runner.submit(() -> this.achievements = Achievements.getByPlayer(this));

        runner.submit(this.getAvatars()::loadFromDatabase);
        runner.submit(this.getInventory()::loadFromDatabase);

        runner.submit(this.getFriendsList()::loadFromDatabase);
        runner.submit(this.getMailHandler()::loadFromDatabase);
        runner.submit(this.getQuestManager()::loadFromDatabase);

        runner.submit(this::loadBattlePassManager);

        // Wait for all tasks to finish.
        Utils.waitFor(() ->
            this.getAvatars().isLoaded() &&
                this.getInventory().isLoaded());

        this.getPlayerProgress().setPlayer(this); // Add reference to the player.
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

        // Ensure the player has valid scenetags, allows old accounts to work
        if (this.getSceneTags().isEmpty() || this.getSceneTags() == null) {
            this.applyStartingSceneTags();
        }

        if (GameHome.HOME_SCENE_IDS.contains(this.getSceneId())) {
            this.setSceneId(this.prevScene <= 0 ? 3 : this.prevScene); // if the player in home, make the player go back.
            var pos = this.getPrevPosForHome();
            if (pos.equals(Position.ZERO)) {
                pos = ScriptLoader.getSceneMeta(this.getSceneId()).config.born_pos;
            }
            this.position.set(pos);
        }

        // Create world
        World world = new World(this);
        world.addPlayer(this);

        // Multiplayer setting
        this.setProperty(PlayerProperty.PROP_PLAYER_MP_SETTING_TYPE, this.getMpSetting().getNumber(), false);
        this.setProperty(PlayerProperty.PROP_IS_MP_MODE_AVAILABLE, 1, false);

        // Execute daily reset logic if this is a new day.
        this.doDailyReset();

        // Rewind active quests, and put the player to a rewind position it finds (if any) of an active quest
        getQuestManager().onLogin();

        // Packets
        session.send(new PacketPlayerDataNotify(this)); // Player data
        session.send(new PacketStoreWeightLimitNotify());
        session.send(new PacketPlayerStoreNotify(this));
        session.send(new PacketAvatarDataNotify(this));

        this.getProgressManager().onPlayerLogin();

        session.send(new PacketFinishedParentQuestNotify(this));
        session.send(new PacketBattlePassAllDataNotify(this));
        session.send(new PacketQuestListNotify(this));
        session.send(new PacketQuestGlobalVarNotify(this));
        session.send(new PacketCodexDataFullNotify(this));
        session.send(new PacketAllWidgetDataNotify(this));

        //Achievements
        this.achievements.onLogin(this);

        session.send(new PacketWidgetGadgetAllDataNotify());
        session.send(new PacketCombineDataNotify(this.unlockedCombines));
        session.send(new PacketGetChatEmojiCollectionRsp(this.getChatEmojiIdList()));
        this.forgingManager.sendForgeDataNotify();
        this.resinManager.onPlayerLogin();
        this.cookingManager.sendCookDataNotify();
        this.cookingCompoundManager.onPlayerLogin();
        this.teamManager.onPlayerLogin();

        getTodayMoonCard(); // The timer works at 0:0, some users log in after that, use this method to check if they have received a reward today or not. If not, send the reward.

        // Battle Pass trigger
        this.getBattlePassManager().triggerMission(WatcherTriggerType.TRIGGER_LOGIN);

        this.furnitureManager.onLogin();
        // Home
        var homeWorld = this.getServer().getHomeWorldOrCreate(this);
        homeWorld.setHost(this); // synchronize player object if homeWorld already exists in the server.
        this.home = homeWorld.getHome();
        this.setCurHomeWorld(homeWorld);
        home.onOwnerLogin(this);
        // Activity
        this.activityManager = new ActivityManager(this);

        session.send(new PacketPlayerEnterSceneNotify(this)); // Enter game world
        session.send(new PacketPlayerLevelRewardUpdateNotify(rewardedLevels));

        // First notify packets sent
        this.hasSentLoginPackets = true;

        // Set session state
        session.setState(SessionState.ACTIVE);

        // Call join event.
        PlayerJoinEvent event = new PlayerJoinEvent(this);
        event.call();
        if (event.isCanceled()) { // If event is not cancelled, continue.
            session.close();
            return;
        }

        // register
        getServer().registerPlayer(this);
    }

    public void onLogout() {
        try {
            // Clear chat history.
            this.getServer().getChatSystem().clearHistoryOnLogout(this);

            // stop stamina calculation
            getStaminaManager().stopSustainedStaminaHandler();

            // force to leave the dungeon (inside has a "if")
            this.getServer().getDungeonSystem().exitDungeon(this);

            // Leave world
            if (this.getWorld() != null) {
                this.getWorld().removePlayer(this);
            }

            // Status stuff
            this.getProfile().syncWithCharacter(this);

            this.getCoopRequests().clear();
            this.getEnterHomeRequests().values().forEach(req -> this.expireEnterHomeRequest(req, true));
            this.getEnterHomeRequests().clear();

            // Save to db
            this.save();
            this.getTeamManager().saveAvatars();
            this.getFriendsList().save();

            // Call quit event.
            PlayerQuitEvent event = new PlayerQuitEvent(this);
            event.call();
        } catch (Throwable e) {
            e.printStackTrace();
            Grasscutter.getLogger().warn("Player (UID {}) save failure", getUid());
        } finally {
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

    public int getPropertyMin(PlayerProperty prop) {
        if (prop.isDynamicRange()) {
            return 0;
        } else {
            return prop.getMin();
        }
    }

    public int getPropertyMax(PlayerProperty prop) {
        if (prop.isDynamicRange()) {
            return switch (prop) {
                case PROP_CUR_SPRING_VOLUME -> getProperty(PlayerProperty.PROP_MAX_SPRING_VOLUME);
                case PROP_CUR_PERSIST_STAMINA -> getProperty(PlayerProperty.PROP_MAX_STAMINA);
                default -> 0;
            };
        } else {
            return prop.getMax();
        }
    }

    /**
     * Applies a property change to this player.
     * Checks the value against the property's min and max values for sanity.
     *
     * @param prop The property to change.
     * @param value The new value.
     * @param sendPacket Whether to send a packet to the client.
     * @return Whether the property was changed.
     */
    private boolean setPropertyWithSanityCheck(PlayerProperty prop, int value, boolean sendPacket) {
        var currentValue = this.properties.getOrDefault(prop.getId(), 0);

        // Call PlayerPropertyChangeEvent.
        var event = new PlayerPropertyChangeEvent(this, prop, currentValue, value);
        if (!event.call()) return false;

        prop = event.getProperty();
        value = event.getNewValue();

        var min = this.getPropertyMin(prop);
        var max = this.getPropertyMax(prop);
        if (min <= value && value <= max) {
            this.properties.put(prop.getId(), value);
            if (sendPacket) {
                // Send property change reasons if needed.
                switch (prop) {
                    case PROP_PLAYER_EXP -> this.sendPacket(new PacketPlayerPropChangeReasonNotify(this, prop, currentValue, value,
                        PropChangeReason.PROP_CHANGE_REASON_PLAYER_ADD_EXP));
                    case PROP_PLAYER_LEVEL -> this.sendPacket(new PacketPlayerPropChangeReasonNotify(this, prop, currentValue, value,
                        PropChangeReason.PROP_CHANGE_REASON_LEVELUP));
                    case PROP_MAX_STAMINA -> this.sendPacket(new PacketPlayerPropChangeReasonNotify(this, prop, currentValue, value,
                        PropChangeReason.PROP_CHANGE_REASON_CITY_LEVELUP));

                    // TODO: Handle world level changing.
                    // case PROP_PLAYER_WORLD_LEVEL -> this.sendPacket(new PacketPlayerPropChangeReasonNotify(this, prop, currentValue, value,
                    //     PropChangeReason.PROP_CHANGE_REASON_MANUAL_ADJUST_WORLD_LEVEL));
                }

                // Update player with packet.
                this.sendPacket(new PacketPlayerPropNotify(this, prop));
                this.sendPacket(new PacketPlayerPropChangeNotify(this, prop, value - currentValue));
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Player otherPlayer &&
            this.id == otherPlayer.getUid();
    }

    @Override
    public String toString() {
        return "Player UID: %s; Nickname: %s; Account: %s"
                .formatted(this.id, this.nickname, this.account);
    }

    public enum SceneLoadState {
        NONE(0), LOADING(1), INIT(2), LOADED(3);

        @Getter
        private final int value;

        SceneLoadState(int value) {
            this.value = value;
        }
    }
}
