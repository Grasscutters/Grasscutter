package emu.grasscutter.game;

import java.util.ArrayList;
import java.util.List;

import emu.grasscutter.GenshinConstants;
import emu.grasscutter.game.avatar.GenshinAvatar;

public class TeamInfo {
	private String name;
	private List<Integer> avatars;
	
	public TeamInfo() {
		this.name = "";
		this.avatars = new ArrayList<>(GenshinConstants.MAX_AVATARS_IN_TEAM);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Integer> getAvatars() {
		return avatars;
	}
	
	public int size() {
		return avatars.size();
	}
	
	public boolean contains(GenshinAvatar avatar) {
		return getAvatars().contains(avatar.getAvatarId());
	}

	public boolean addAvatar(GenshinAvatar avatar) {
		if (size() >= GenshinConstants.MAX_AVATARS_IN_TEAM || contains(avatar)) {
			return false;
		}
		
		getAvatars().add(avatar.getAvatarId());
		
		return true;
	}
	
	public boolean removeAvatar(int slot) {
		if (size() <= 1) {
			return false;
		}
		
		getAvatars().remove(slot);
		
		return true;
	}
	
	public void copyFrom(TeamInfo team) {
		copyFrom(team, GenshinConstants.MAX_AVATARS_IN_TEAM);
	}
	
	public void copyFrom(TeamInfo team, int maxTeamSize) {
		// Clear
		this.getAvatars().clear();
		
		// Copy from team
		int len = Math.min(team.getAvatars().size(), maxTeamSize);
		for (int i = 0; i < len; i++) {
			int id = team.getAvatars().get(i);
			this.getAvatars().add(id);
		}
	}
}
