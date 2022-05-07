package emu.grasscutter.server.packet.send;

import emu.grasscutter.GameConstants;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.friends.Friendship;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.FriendBriefOuterClass.FriendBrief;
import emu.grasscutter.net.proto.FriendOnlineStateOuterClass.FriendOnlineState;
import emu.grasscutter.net.proto.GetPlayerFriendListRspOuterClass.GetPlayerFriendListRsp;
import emu.grasscutter.net.proto.ProfilePictureOuterClass.ProfilePicture;
import emu.grasscutter.net.proto.PlatformTypeOuterClass;

public class PacketGetPlayerFriendListRsp extends BasePacket {
	
	public PacketGetPlayerFriendListRsp(Player player) {
		super(PacketOpcodes.GetPlayerFriendListRsp);
		
		FriendBrief serverFriend = FriendBrief.newBuilder()
				.setUid(GameConstants.SERVER_CONSOLE_UID)
				.setNickname(Grasscutter.getConfig().getGameServerOptions().ServerNickname)
				.setLevel(Grasscutter.getConfig().getGameServerOptions().ServerLevel)
				.setProfilePicture(ProfilePicture.newBuilder().setAvatarId(Grasscutter.getConfig().getGameServerOptions().ServerAvatarId))
				.setWorldLevel(Grasscutter.getConfig().getGameServerOptions().ServerWorldLevel)
				.setSignature(Grasscutter.getConfig().getGameServerOptions().ServerSignature)
				.setLastActiveTime((int) (System.currentTimeMillis() / 1000f))
				.setNameCardId(Grasscutter.getConfig().getGameServerOptions().ServerNameCardId)
				.setOnlineState(FriendOnlineState.FRIEND_ONLINE)
				.setParam(1)
				.setIsGameSource(true)
				.setPlatformType(PlatformTypeOuterClass.PlatformType.PC)
				.build();
		
		GetPlayerFriendListRsp.Builder proto = GetPlayerFriendListRsp.newBuilder().addFriendList(serverFriend);
		
		for (Friendship friendship : player.getFriendsList().getFriends().values()) {
			proto.addFriendList(friendship.toProto());
		}
		for (Friendship friendship : player.getFriendsList().getPendingFriends().values()) {
			if (friendship.getAskerId() == player.getUid()) {
				continue;
			}
			proto.addAskFriendList(friendship.toProto());
		}
		
		this.setData(proto);
	}
}
