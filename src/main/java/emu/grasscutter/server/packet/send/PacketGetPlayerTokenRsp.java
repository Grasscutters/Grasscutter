package emu.grasscutter.server.packet.send;

import java.util.Base64;

import com.google.protobuf.ByteString;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetPlayerTokenRspOuterClass.GetPlayerTokenRsp;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.utils.Crypto;

public class PacketGetPlayerTokenRsp extends BasePacket {

	public PacketGetPlayerTokenRsp(GameSession session) {
		super(PacketOpcodes.GetPlayerTokenRsp, true);
		
		this.setUseDispatchKey(true);

		if (session.getPlayer().getAccount().isBanned()) {
			byte[] bin = Base64.getDecoder().decode("5LiN54ixIEdDIOWNgeW5tOS6hu+8jOWNgeW5tOmHjOeUqOi/h+eahOavj+S4gOS4qiBQUyDpg73lg48gR0PjgII=");

			GetPlayerTokenRsp p = GetPlayerTokenRsp.newBuilder()
				.setUid(session.getPlayer().getUid())
				.setIsProficientPlayer(session.getPlayer().getAvatars().getAvatarCount() > 0)
				.setRetcode(21)
				.setExtraBinData(ByteString.copyFrom(bin))
				.setMsg("FORBID_CHEATING_PLUGINS")
				.setBlackUidEndTime(session.getPlayer().getAccount().getBanEndTime())
				.setRegPlatform(3)
				.setCountryCode("US")
				.setClientIpStr(session.getAddress().getAddress().getHostAddress())
				.build();
			
			this.setData(p.toByteArray());
		} else {
			GetPlayerTokenRsp p = GetPlayerTokenRsp.newBuilder()
				.setUid(session.getPlayer().getUid())
				.setToken(session.getAccount().getToken())
				.setAccountType(1)
				.setIsProficientPlayer(session.getPlayer().getAvatars().getAvatarCount() > 0) // Not sure where this goes
				.setSecretKeySeed(Crypto.ENCRYPT_SEED)
				.setSecurityCmdBuffer(ByteString.copyFrom(Crypto.ENCRYPT_SEED_BUFFER))
				.setPlatformType(3)
				.setChannelId(1)
				.setCountryCode("US")
				.setClientVersionRandomKey("c25-314dd05b0b5f")
				.setRegPlatform(3)
				.setClientIpStr(session.getAddress().getAddress().getHostAddress())
				.build();
			
			this.setData(p.toByteArray());
		}
	}
}
