package emu.grasscutter.server.packet.send;

import static emu.grasscutter.config.Configuration.GAME_INFO;

import emu.grasscutter.GameConstants;
import emu.grasscutter.game.friends.Friendship;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.*;
import emu.grasscutter.net.proto.FriendBriefOuterClass.FriendBrief;
import emu.grasscutter.net.proto.FriendOnlineStateOuterClass.FriendOnlineState;
import emu.grasscutter.net.proto.GetPlayerFriendListRspOuterClass.GetPlayerFriendListRsp;
import emu.grasscutter.net.proto.ProfilePictureOuterClass.ProfilePicture;

public class PacketGetPlayerFriendListRsp extends BasePacket {

    public PacketGetPlayerFriendListRsp(Player player) {
        super(PacketOpcodes.GetPlayerFriendListRsp);

        var serverAccount = GAME_INFO.serverAccount;
        FriendBrief serverFriend =
                FriendBrief.newBuilder()
                        .setUid(GameConstants.SERVER_CONSOLE_UID)
                        .setNickname(serverAccount.nickName)
                        .setLevel(serverAccount.adventureRank)
                        .setProfilePicture(ProfilePicture.newBuilder().setAvatarId(serverAccount.avatarId))
                        .setWorldLevel(serverAccount.worldLevel)
                        .setSignature(serverAccount.signature)
                        .setLastActiveTime((int) (System.currentTimeMillis() / 1000f))
                        .setNameCardId(serverAccount.nameCardId)
                        .setOnlineState(FriendOnlineState.FRIEND_ONLINE_STATE_ONLINE)
                        .setParam(1)
                        .setIsGameSource(true)
                        .setPlatformType(PlatformTypeOuterClass.PlatformType.PLATFORM_TYPE_PC)
                        .setFriendEnterHomeOptionValue(
                                FriendEnterHomeOptionOuterClass.FriendEnterHomeOption
                                        .FRIEND_ENTER_HOME_OPTION_REFUSE_VALUE)
                        .build();

        GetPlayerFriendListRsp.Builder proto =
                GetPlayerFriendListRsp.newBuilder().addFriendList(serverFriend);

        for (Friendship friendship : player.getFriendsList().getFriends().values()) {
            proto.addFriendList(friendship.toProto());
        }

        this.setData(proto);
    }
}
