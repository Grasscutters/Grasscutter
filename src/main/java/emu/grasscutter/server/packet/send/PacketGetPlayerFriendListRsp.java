package emu.grasscutter.server.packet.send;

import emu.grasscutter.GenshinConstants;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.friends.Friendship;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.FriendBriefOuterClass.FriendBrief;
import emu.grasscutter.net.proto.FriendOnlineStateOuterClass.FriendOnlineState;
import emu.grasscutter.net.proto.GetPlayerFriendListRspOuterClass.GetPlayerFriendListRsp;
import emu.grasscutter.net.proto.HeadImageOuterClass.HeadImage;
import emu.grasscutter.net.proto.PlatformTypeOuterClass;

public class PacketGetPlayerFriendListRsp extends GenshinPacket {
	
	public PacketGetPlayerFriendListRsp(GenshinPlayer player) {
		super(PacketOpcodes.GetPlayerFriendListRsp);
		
		FriendBrief serverFriend = FriendBrief.newBuilder()
				.setUid(GenshinConstants.SERVER_CONSOLE_UID)
				.setNickname("Server")
				.setLevel(1)
				.setAvatarId(HeadImage.newBuilder().setAvatarId(GenshinConstants.MAIN_CHARACTER_FEMALE).getAvatarId())
				.setWorldLevel(0)
				.setSignature("")
				.setLastActiveTime((int) (System.currentTimeMillis() / 1000f))
				.setNameCardId(210001)
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
