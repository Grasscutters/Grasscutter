package emu.grasscutter.game;

import java.util.*;

import dev.morphia.annotations.*;
import emu.grasscutter.GenshinConstants;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GenshinData;
import emu.grasscutter.data.def.PlayerLevelData;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.avatar.AvatarProfileData;
import emu.grasscutter.game.avatar.AvatarStorage;
import emu.grasscutter.game.avatar.GenshinAvatar;
import emu.grasscutter.game.entity.EntityItem;
import emu.grasscutter.game.entity.GenshinEntity;
import emu.grasscutter.game.friends.FriendsList;
import emu.grasscutter.game.friends.PlayerProfile;
import emu.grasscutter.game.gacha.PlayerGachaInfo;
import emu.grasscutter.game.inventory.GenshinItem;
import emu.grasscutter.game.inventory.Inventory;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.proto.AbilityInvokeEntryOuterClass.AbilityInvokeEntry;
import emu.grasscutter.net.proto.BirthdayOuterClass.Birthday;
import emu.grasscutter.net.proto.CombatInvokeEntryOuterClass.CombatInvokeEntry;
import emu.grasscutter.net.proto.HeadImageOuterClass.HeadImage;
import emu.grasscutter.net.proto.InteractTypeOuterClass.InteractType;
import emu.grasscutter.net.proto.MpSettingTypeOuterClass.MpSettingType;
import emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.OnlinePlayerInfo;
import emu.grasscutter.net.proto.PlayerApplyEnterMpReasonOuterClass.PlayerApplyEnterMpReason;
import emu.grasscutter.net.proto.PlayerLocationInfoOuterClass.PlayerLocationInfo;
import emu.grasscutter.net.proto.SocialDetailOuterClass.SocialDetail;
import emu.grasscutter.net.proto.WorldPlayerLocationInfoOuterClass.WorldPlayerLocationInfo;
import emu.grasscutter.server.game.GameServer;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketAbilityInvocationsNotify;
import emu.grasscutter.server.packet.send.PacketAvatarAddNotify;
import emu.grasscutter.server.packet.send.PacketAvatarDataNotify;
import emu.grasscutter.server.packet.send.PacketAvatarGainCostumeNotify;
import emu.grasscutter.server.packet.send.PacketAvatarGainFlycloakNotify;
import emu.grasscutter.server.packet.send.PacketClientAbilityInitFinishNotify;
import emu.grasscutter.server.packet.send.PacketCombatInvocationsNotify;
import emu.grasscutter.server.packet.send.PacketGadgetInteractRsp;
import emu.grasscutter.server.packet.send.PacketItemAddHintNotify;
import emu.grasscutter.server.packet.send.PacketOpenStateUpdateNotify;
import emu.grasscutter.server.packet.send.PacketPlayerApplyEnterMpResultNotify;
import emu.grasscutter.server.packet.send.PacketPlayerDataNotify;
import emu.grasscutter.server.packet.send.PacketPlayerEnterSceneNotify;
import emu.grasscutter.server.packet.send.PacketPlayerPropNotify;
import emu.grasscutter.server.packet.send.PacketPlayerStoreNotify;
import emu.grasscutter.server.packet.send.PacketPrivateChatNotify;
import emu.grasscutter.server.packet.send.PacketScenePlayerLocationNotify;
import emu.grasscutter.server.packet.send.PacketSetNameCardRsp;
import emu.grasscutter.server.packet.send.PacketStoreWeightLimitNotify;
import emu.grasscutter.server.packet.send.PacketUnlockNameCardNotify;
import emu.grasscutter.server.packet.send.PacketWorldPlayerLocationNotify;
import emu.grasscutter.server.packet.send.PacketWorldPlayerRTTNotify;
import emu.grasscutter.utils.Position;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

@Entity(value = "players", noClassnameStored = true)
public class GenshinPlayer {
	@Id private int id;
	@Indexed(options = @IndexOptions(unique = true)) private String accountId;
	
	@Transient private Account account;
	private String nickname;
	private String signature;
	private int headImage;
	private int nameCardId = 210001;
	private Position pos;
	private Position rotation;
	
	private Map<Integer, Integer> properties;
	private Set<Integer> nameCardList;
	private Set<Integer> flyCloakList;
	private Set<Integer> costumeList;
	
	@Transient private long nextGuid = 0;
	@Transient private int peerId;
	@Transient private World world;
	@Transient private GenshinScene scene;
	@Transient private GameSession session;
	@Transient private AvatarStorage avatars;
	@Transient private Inventory inventory;
	@Transient private FriendsList friendsList;
	
	private TeamManager teamManager;
	private PlayerGachaInfo gachaInfo;
	private PlayerProfile playerProfile;
	private MpSettingType mpSetting = MpSettingType.MpSettingEnterAfterApply;
	private boolean showAvatar;
	private ArrayList<AvatarProfileData> shownAvatars;
	
	private int sceneId;
	private int regionId;
	private int mainCharacterId;
	private boolean godmode;
	
	@Transient private boolean paused;
	@Transient private int enterSceneToken;
	@Transient private SceneLoadState sceneState;
	@Transient private boolean hasSentAvatarDataNotify;
	@Transient private long nextSendPlayerLocTime = 0;
	
	@Transient private final Int2ObjectMap<CoopRequest> coopRequests;
	@Transient private final InvokeHandler<CombatInvokeEntry> combatInvokeHandler;
	@Transient private final InvokeHandler<AbilityInvokeEntry> abilityInvokeHandler;
	@Transient private final InvokeHandler<AbilityInvokeEntry> clientAbilityInitFinishHandler;
	
	@Deprecated @SuppressWarnings({ "rawtypes", "unchecked" }) // Morphia only!
	public GenshinPlayer() { 
		this.inventory = new Inventory(this);
		this.avatars = new AvatarStorage(this);
		this.friendsList = new FriendsList(this);
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
		
		this.setSceneId(3);
		this.setRegionId(1);
		this.sceneState = SceneLoadState.NONE;
		
		this.coopRequests = new Int2ObjectOpenHashMap<>();
		this.combatInvokeHandler = new InvokeHandler(PacketCombatInvocationsNotify.class);
		this.abilityInvokeHandler = new InvokeHandler(PacketAbilityInvocationsNotify.class);
		this.clientAbilityInitFinishHandler = new InvokeHandler(PacketClientAbilityInitFinishNotify.class);
	}
	
	// On player creation
	public GenshinPlayer(GameSession session) {
		this();
		this.account = session.getAccount();
		this.accountId = this.getAccount().getId();
		this.session = session;
		this.nickname = "Traveler";
		this.signature = "";
		this.teamManager = new TeamManager(this);
		this.setProperty(PlayerProperty.PROP_PLAYER_LEVEL, 1);
		this.setProperty(PlayerProperty.PROP_IS_SPRING_AUTO_USE, 1);
		this.setProperty(PlayerProperty.PROP_SPRING_AUTO_USE_PERCENT, 50);
		this.setProperty(PlayerProperty.PROP_IS_FLYABLE, 1);
		this.setProperty(PlayerProperty.PROP_IS_TRANSFERABLE, 1);
		this.setProperty(PlayerProperty.PROP_MAX_STAMINA, 24000);
		this.setProperty(PlayerProperty.PROP_CUR_PERSIST_STAMINA, 24000);
		this.setProperty(PlayerProperty.PROP_PLAYER_RESIN, 160);
		this.getFlyCloakList().add(140001);
		this.getNameCardList().add(210001);
		this.getPos().set(GenshinConstants.START_POSITION);
		this.getRotation().set(0, 307, 0);
	}

	public int getUid() {
		return id;
	}

	public void setUid(int id) {
		this.id = id;
	}
	
	public long getNextGenshinGuid() {
		long nextId = ++this.nextGuid;
		return ((long) this.getUid() << 32) + nextId;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
		this.account.setPlayerId(getUid());
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
	
	public GenshinScene getScene() {
		return scene;
	}

	public void setScene(GenshinScene scene) {
		this.scene = scene;
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

	public Position getPos() {
		return pos;
	}
	
	public Position getRotation() {
		return rotation;
	}
	
	public int getLevel() {
		return this.getProperty(PlayerProperty.PROP_PLAYER_LEVEL);
	}
	
	public int getExp() {
		return this.getProperty(PlayerProperty.PROP_PLAYER_EXP);
	}
	
	public int getWorldLevel() {
		return this.getProperty(PlayerProperty.PROP_PLAYER_WORLD_LEVEL);
	}
	
	public int getPrimogems() {
		return this.getProperty(PlayerProperty.PROP_PLAYER_HCOIN);
	}
	
	public void setPrimogems(int primogem) {
		this.setProperty(PlayerProperty.PROP_PLAYER_HCOIN, primogem);
		this.sendPacket(new PacketPlayerPropNotify(this, PlayerProperty.PROP_PLAYER_HCOIN));
	}
	
	public int getMora() {
		return this.getProperty(PlayerProperty.PROP_PLAYER_SCOIN);
	}
	
	public void setMora(int mora) {
		this.setProperty(PlayerProperty.PROP_PLAYER_SCOIN, mora);
		this.sendPacket(new PacketPlayerPropNotify(this, PlayerProperty.PROP_PLAYER_SCOIN));
	}
	
	private int getExpRequired(int level) {
		PlayerLevelData levelData = GenshinData.getPlayerLevelDataMap().get(level);
		return levelData != null ? levelData.getExp() : 0; 
	}
	
	private float getExpModifier() {
		return Grasscutter.getConfig().getGameServerOptions().getGameRates().ADVENTURE_EXP_RATE;
	}
	
	// Affected by exp rate
	public void earnExp(int exp) {
		addExpDirectly((int) (exp * getExpModifier()));
	}
	
	// Directly give player exp
	public void addExpDirectly(int gain) {
		boolean hasLeveledUp = false;
		int level = getLevel();
		int exp = getExp();
		int reqExp = getExpRequired(level);
		
		exp += gain;
		
		while (exp >= reqExp && reqExp > 0) {
			exp -= reqExp;
			level += 1;
			reqExp = getExpRequired(level);
			hasLeveledUp = true;
		}
		
		if (hasLeveledUp) {
			// Set level property
			this.setProperty(PlayerProperty.PROP_PLAYER_LEVEL, level);
			// Update social status
			this.updateProfile();
			// Update player with packet
			this.sendPacket(new PacketPlayerPropNotify(this, PlayerProperty.PROP_PLAYER_LEVEL));
		}
		
		// Set exp
		this.setProperty(PlayerProperty.PROP_PLAYER_EXP, exp);
		
		// Update player with packet
		this.sendPacket(new PacketPlayerPropNotify(this, PlayerProperty.PROP_PLAYER_EXP));
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

	public PlayerGachaInfo getGachaInfo() {
		return gachaInfo;
	}

	public PlayerProfile getProfile() {
		if (this.playerProfile == null) {
			this.playerProfile = new PlayerProfile(this);
		}
		return playerProfile;
	}

	public Map<Integer, Integer> getProperties() {
		return properties;
	}
	
	public void setProperty(PlayerProperty prop, int value) {
		getProperties().put(prop.getId(), value);
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

	public MpSettingType getMpSetting() {
		return mpSetting;
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

	public void setMpSetting(MpSettingType mpSetting) {
		this.mpSetting = mpSetting;
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

	public void addAvatar(GenshinAvatar avatar) {
		boolean result = getAvatars().addAvatar(avatar);
		
		if (result) {
			// Add starting weapon
			getAvatars().addStartingWeapon(avatar);
			
			// Try adding to team if possible
			//List<EntityAvatar> currentTeam = this.getTeamManager().getCurrentTeam();
			boolean addedToTeam = false;
			
			/*
			if (currentTeam.size() <= GenshinConstants.MAX_AVATARS_IN_TEAM) {
				addedToTeam = currentTeam
			}
			*/
			
			// Done
			if (hasSentAvatarDataNotify()) {
				// Recalc stats
				avatar.recalcStats();
				// Packet
				sendPacket(new PacketAvatarAddNotify(avatar, addedToTeam));
			}
		} else {
			// Failed adding avatar
		}
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
		this.sendPacket(new PacketPrivateChatNotify(GenshinConstants.SERVER_CONSOLE_UID, getUid(), message.toString()));
	}

	/**
	 * Sends a message to another player.
	 * @param sender The sender of the message.
	 * @param message The message to send.
	 */
	public void sendMessage(GenshinPlayer sender, Object message) {
		this.sendPacket(new PacketPrivateChatNotify(sender.getUid(), this.getUid(), message.toString()));
	}
	
	public void interactWith(int gadgetEntityId) {
		GenshinEntity entity = getScene().getEntityById(gadgetEntityId);
		
		if (entity == null) {
			return;
		}
		
		// Delete
		entity.getScene().removeEntity(entity);
		
		// Handle
		if (entity instanceof EntityItem) {
			// Pick item
			EntityItem drop = (EntityItem) entity;
			GenshinItem item = new GenshinItem(drop.getItemData(), drop.getCount());
			// Add to inventory
			boolean success = getInventory().addItem(item);
			if (success) {
				this.sendPacket(new PacketGadgetInteractRsp(drop, InteractType.InteractPickItem));
				this.sendPacket(new PacketItemAddHintNotify(item, ActionReason.SubfieldDrop));
			}
		}
		
		return;
	}
	
	public void onPause() {
		
	}
	
	public void onUnpause() {
		
	}
	
	public void sendPacket(GenshinPacket packet) {
		if (this.hasSentAvatarDataNotify) {
			this.getSession().send(packet);
		}
	}
	
	public OnlinePlayerInfo getOnlinePlayerInfo() {
		OnlinePlayerInfo.Builder onlineInfo = OnlinePlayerInfo.newBuilder()
				.setUid(this.getUid())
				.setNickname(this.getNickname())
				.setPlayerLevel(this.getLevel())
				.setMpSettingType(this.getMpSetting())
				.setNameCardId(this.getNameCardId())
				.setSignature(this.getSignature())
				.setAvatar(HeadImage.newBuilder().setAvatarId(this.getHeadImage()));
		
		if (this.getWorld() != null) {
			onlineInfo.setCurPlayerNumInWorld(this.getWorld().getPlayers().indexOf(this) + 1);
		} else {
			onlineInfo.setCurPlayerNumInWorld(1);
		}
		
		return onlineInfo.build();
	}

	public SocialDetail.Builder getSocialDetail() {
		SocialDetail.Builder social = SocialDetail.newBuilder()
				.setUid(this.getUid())
				.setAvatar(HeadImage.newBuilder().setAvatarId(this.getHeadImage()))
				.setNickname(this.getNickname())
				.setSignature(this.getSignature())
				.setLevel(this.getLevel())
				.setBirthday(Birthday.newBuilder())
				.setWorldLevel(this.getWorldLevel())
				.setUnk1(1)
				.setUnk3(1)
				.setNameCardId(this.getNameCardId())
				.setFinishAchievementNum(0);
		return social;
	}
	
	public WorldPlayerLocationInfo getWorldPlayerLocationInfo() {
		return WorldPlayerLocationInfo.newBuilder()
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
				req.getRequester().sendPacket(new PacketPlayerApplyEnterMpResultNotify(this, false, PlayerApplyEnterMpReason.SystemJudge));
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
	}
	
	public void resetSendPlayerLocTime() {
		this.nextSendPlayerLocTime = System.currentTimeMillis() + 5000;
	}

	@PostLoad
	private void onLoad() {
		this.getTeamManager().setPlayer(this);
	}
	
	public void save() {
		DatabaseHelper.savePlayer(this);
	}

	public void onLogin() {
		// Make sure these exist
		if (this.getTeamManager() == null) {
			this.teamManager = new TeamManager(this);
		} if (this.getProfile().getUid() == 0) {
			this.getProfile().syncWithCharacter(this);
		}
		
		// Check if player object exists in server
		// TODO - optimize
		GenshinPlayer exists = this.getServer().getPlayerByUid(getUid());
		if (exists != null) {
			exists.getSession().close();
		}
		
		// Load from db
		this.getAvatars().loadFromDatabase();
		this.getInventory().loadFromDatabase();
		this.getAvatars().postLoad();
		
		this.getFriendsList().loadFromDatabase();
		
		// Create world
		World world = new World(this);
		world.addPlayer(this);
		
		// Add to gameserver
		if (getSession().isActive()) {
			getServer().registerPlayer(this);
			getProfile().setPlayer(this); // Set online
		}
		
		// Multiplayer setting 
		this.setProperty(PlayerProperty.PROP_PLAYER_MP_SETTING_TYPE, this.getMpSetting().getNumber());
		this.setProperty(PlayerProperty.PROP_IS_MP_MODE_AVAILABLE, 1);
		
		// Packets
		session.send(new PacketPlayerDataNotify(this)); // Player data
		session.send(new PacketStoreWeightLimitNotify());
		session.send(new PacketPlayerStoreNotify(this));
		session.send(new PacketAvatarDataNotify(this));
		
		session.send(new PacketPlayerEnterSceneNotify(this)); // Enter game world
		session.send(new PacketOpenStateUpdateNotify());

		// First notify packets sent
		this.setHasSentAvatarDataNotify(true);
	}
	
	public void onLogout() {
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
	}
	
	public enum SceneLoadState {
		NONE (0), LOADING (1), INIT (2), LOADED (3);
		
		private final int value;
		
		private SceneLoadState(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return this.value;
		}
	}
}
