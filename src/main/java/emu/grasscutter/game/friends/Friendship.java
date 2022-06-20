package emu.grasscutter.game.friends;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Indexed;
import dev.morphia.annotations.Transient;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.FriendBriefOuterClass.FriendBrief;
import emu.grasscutter.net.proto.FriendOnlineStateOuterClass.FriendOnlineState;
import emu.grasscutter.net.proto.PlatformTypeOuterClass;
import emu.grasscutter.net.proto.ProfilePictureOuterClass.ProfilePicture;
import org.bson.types.ObjectId;

@Entity(value = "friendships", useDiscriminator = false)
public class Friendship {
    @Id
    private ObjectId id;

    @Transient
    private Player owner;

    @Indexed
    private int ownerId;
    @Indexed
    private int friendId;
    private boolean isFriend;
    private int askerId;

    private PlayerProfile profile;

    @Deprecated // Morphia use only
    public Friendship() {
    }

    public Friendship(Player owner, Player friend, Player asker) {
        this.setOwner(owner);
        this.ownerId = owner.getUid();
        this.friendId = friend.getUid();
        this.profile = friend.getProfile();
        this.askerId = asker.getUid();
    }

    public Player getOwner() {
        return this.owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public boolean isFriend() {
        return this.isFriend;
    }

    public void setIsFriend(boolean b) {
        this.isFriend = b;
    }

    public int getOwnerId() {
        return this.ownerId;
    }

    public int getFriendId() {
        return this.friendId;
    }

    public int getAskerId() {
        return this.askerId;
    }

    public void setAskerId(int askerId) {
        this.askerId = askerId;
    }

    public PlayerProfile getFriendProfile() {
        return this.profile;
    }

    public void setFriendProfile(Player character) {
        if (character == null || this.friendId != character.getUid()) return;
        this.profile = character.getProfile();
    }

    public boolean isOnline() {
        return this.getFriendProfile().getPlayer() != null;
    }

    public void save() {
        DatabaseHelper.saveFriendship(this);
    }

    public void delete() {
        DatabaseHelper.deleteFriendship(this);
    }

    public FriendBrief toProto() {
        FriendBrief proto = FriendBrief.newBuilder()
            .setUid(this.getFriendProfile().getUid())
            .setNickname(this.getFriendProfile().getName())
            .setLevel(this.getFriendProfile().getPlayerLevel())
            .setProfilePicture(ProfilePicture.newBuilder().setAvatarId(this.getFriendProfile().getAvatarId()))
            .setWorldLevel(this.getFriendProfile().getWorldLevel())
            .setSignature(this.getFriendProfile().getSignature())
            .setOnlineState(this.getFriendProfile().isOnline() ? FriendOnlineState.FRIEND_ONLINE_STATE_ONLINE : FriendOnlineState.FRIEND_ONLINE_STATE_FREIEND_DISCONNECT)
            .setIsMpModeAvailable(true)
            .setLastActiveTime(this.getFriendProfile().getLastActiveTime())
            .setNameCardId(this.getFriendProfile().getNameCard())
            .setParam(this.getFriendProfile().getDaysSinceLogin())
            .setIsGameSource(true)
            .setPlatformType(PlatformTypeOuterClass.PlatformType.PLATFORM_TYPE_PC)
            .build();

        return proto;
    }
}
