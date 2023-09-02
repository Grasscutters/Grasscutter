package emu.grasscutter.game.friends;

import dev.morphia.annotations.*;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.FriendBriefOuterClass.FriendBrief;
import emu.grasscutter.net.proto.FriendOnlineStateOuterClass.FriendOnlineState;
import emu.grasscutter.net.proto.PlatformTypeOuterClass;
import emu.grasscutter.net.proto.ProfilePictureOuterClass.ProfilePicture;
import org.bson.types.ObjectId;

@Entity(value = "friendships", useDiscriminator = false)
public class Friendship {
    @Id private ObjectId id;

    @Transient private Player owner;

    @Indexed private int ownerId;
    @Indexed private int friendId;
    private boolean isFriend;
    private int askerId;

    private PlayerProfile profile;

    @Deprecated // Morphia use only
    public Friendship() {}

    public Friendship(Player owner, Player friend, Player asker) {
        this.setOwner(owner);
        this.ownerId = owner.getUid();
        this.friendId = friend.getUid();
        this.profile = friend.getProfile();
        this.askerId = asker.getUid();
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
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

    public void setFriendProfile(Player character) {
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
        var player = this.getFriendProfile().getPlayer(); // get latest player and sync.

        return FriendBrief.newBuilder()
                .setUid(getFriendProfile().getUid())
                .setNickname(getFriendProfile().getName())
                .setLevel(getFriendProfile().getPlayerLevel())
                .setProfilePicture(
                        ProfilePicture.newBuilder().setAvatarId(getFriendProfile().getAvatarId()))
                .setWorldLevel(getFriendProfile().getWorldLevel())
                .setSignature(getFriendProfile().getSignature())
                .setOnlineState(
                        player != null && player.isOnline()
                                ? FriendOnlineState.FRIEND_ONLINE_STATE_ONLINE
                                : FriendOnlineState.FRIEND_ONLINE_STATE_DISCONNECT)
                .setIsMpModeAvailable(true)
                .setLastActiveTime(getFriendProfile().getLastActiveTime())
                .setNameCardId(getFriendProfile().getNameCard())
                .setParam(getFriendProfile().getDaysSinceLogin())
                .setIsGameSource(true)
                .setPlatformType(PlatformTypeOuterClass.PlatformType.PLATFORM_TYPE_PC)
                .setIsInDuel(getFriendProfile().isInDuel())
                .setIsDuelObservable(getFriendProfile().isDuelObservable())
                .build();
    }
}
