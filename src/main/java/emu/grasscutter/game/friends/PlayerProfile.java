package emu.grasscutter.game.friends;

import dev.morphia.annotations.*;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.utils.Utils;

public class PlayerProfile {
	@Transient private GenshinPlayer player;
	
	@AlsoLoad("id") private int uid;
	private int nameCard;
	private int avatarId;
	private String name;
	private String signature;
	private int achievements;
	
	private int playerLevel;
	private int worldLevel;
	private int lastActiveTime;

	@Deprecated // Morphia only
	public PlayerProfile() { }
	
	public PlayerProfile(GenshinPlayer player) {
		this.uid = player.getUid();
		this.syncWithCharacter(player);
	}
	
	public int getUid() {
		return uid;
	}

	public GenshinPlayer getPlayer() {
		return player;
	}
	
	public synchronized void setPlayer(GenshinPlayer player) {
		this.player = player;
	}
	
	public String getName() {
		return name;
	}

	public int getNameCard() {
		return nameCard;
	}

	public int getAvatarId() {
		return avatarId;
	}

	public String getSignature() {
		return signature;
	}

	public int getAchievements() {
		return achievements;
	}

	public int getPlayerLevel() {
		return playerLevel;
	}

	public int getWorldLevel() {
		return worldLevel;
	}

	public int getLastActiveTime() {
		return lastActiveTime;
	}
	
	public void updateLastActiveTime() {
		this.lastActiveTime = Utils.getCurrentSeconds();
	}
	
	public int getDaysSinceLogin() {
		return (int) Math.floor((Utils.getCurrentSeconds() - getLastActiveTime()) / 86400.0);
	}

	public boolean isOnline() {
		return this.getPlayer() != null;
	}

	public void syncWithCharacter(GenshinPlayer player) {
		if (player == null) {
			return;
		}
		
		this.uid = player.getUid();
		this.name = player.getNickname();
		this.avatarId = player.getHeadImage();
		this.signature = player.getSignature();
		this.nameCard = player.getNameCardId();
		this.playerLevel = player.getLevel();
		this.worldLevel = player.getWorldLevel();
		//this.achievements = 0;
		this.updateLastActiveTime();
	}
}
