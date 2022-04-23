package emu.grasscutter.game.avatar;

import dev.morphia.annotations.Entity;

@Entity
public class AvatarProfileData {
	private int avatarId;
	private int level;
	
	public AvatarProfileData(GenshinAvatar avatar) {
		this.update(avatar);
	}

	public int getAvatarId() {
		return avatarId;
	}

	public int getLevel() {
		return level;
	}

	public void update(GenshinAvatar avatar) {
		this.avatarId = avatar.getAvatarId();
		this.level = avatar.getLevel();
	}
}
