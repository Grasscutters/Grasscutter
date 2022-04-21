package emu.grasscutter.game.friends;

import org.bson.types.ObjectId;

import dev.morphia.annotations.*;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.net.proto.FriendBriefOuterClass.FriendBrief;
import emu.grasscutter.net.proto.FriendOnlineStateOuterClass.FriendOnlineState;
import emu.grasscutter.net.proto.HeadImageOuterClass.HeadImage;

@Entity(value = "friendships", noClassnameStored = true)
public class Friendship {
	@Id private ObjectId id;
	
	@Transient private GenshinPlayer owner;
	
	@Indexed private int ownerId;
	@Indexed private int friendId;
	private boolean isFriend;
	private int askerId;
	
	private PlayerProfile profile;
	
	@Deprecated // Morphia use only
	public Friendship() { }
	
	public Friendship(GenshinPlayer owner, GenshinPlayer friend, GenshinPlayer asker) {
		this.setOwner(owner);
		this.ownerId = owner.getUid();
		this.friendId = friend.getUid();
		this.profile = friend.getProfile();
		this.askerId = asker.getUid();
	}

	public GenshinPlayer getOwner() {
		return owner;
	}

	public void setOwner(GenshinPlayer owner) {
		this.owner = owner;
	}

	public boolean isFriend() {
		return isFriend;
	}

	public void setIsFriend(boolean b) {
		this.isFriend = b;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public int getFriendId() {
		return friendId;
	}

	public int getAskerId() {
		return askerId;
	}

	public void setAskerId(int askerId) {
		this.askerId = askerId;
	}

	public PlayerProfile getFriendProfile() {
		return profile;
	}
	
	public void setFriendProfile(GenshinPlayer character) {
		if (character == null || this.friendId != character.getUid()) return;
		this.profile = character.getProfile();
	}
	
	public boolean isOnline() {
		return getFriendProfile().getPlayer() != null;
	}

	public void save() {
		DatabaseHelper.saveFriendship(this);
	}

	public void delete() {
		DatabaseHelper.deleteFriendship(this);
	}
	
	public FriendBrief toProto() {
		FriendBrief proto = FriendBrief.newBuilder()
				.setUid(getFriendProfile().getUid())
				.setNickname(getFriendProfile().getName())
				.setLevel(getFriendProfile().getPlayerLevel())
				.setAvatar(HeadImage.newBuilder().setAvatarId(getFriendProfile().getAvatarId()))
				.setWorldLevel(getFriendProfile().getWorldLevel())
				.setSignature(getFriendProfile().getSignature())
				.setOnlineState(getFriendProfile().isOnline() ? FriendOnlineState.FRIEND_ONLINE : FriendOnlineState.FRIEND_DISCONNECT)
				.setIsMpModeAvailable(true)
				.setLastActiveTime(getFriendProfile().getLastActiveTime())
				.setNameCardId(getFriendProfile().getNameCard())
				.setParam(getFriendProfile().getDaysSinceLogin())
				.setUnk1(1)
				.setUnk2(3)
				.build();

		return proto;
	}
}
