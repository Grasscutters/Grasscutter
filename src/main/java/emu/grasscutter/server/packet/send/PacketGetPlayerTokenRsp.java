package emu.grasscutter.server.packet.send;

import com.google.protobuf.ByteString;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetPlayerTokenRspOuterClass.GetPlayerTokenRsp;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.utils.Crypto;

public class PacketGetPlayerTokenRsp extends BasePacket {

	public PacketGetPlayerTokenRsp(GameSession session, boolean doesPlayerExist) {
		super(PacketOpcodes.GetPlayerTokenRsp, true);
		
		this.setUseDispatchKey(true);
		
		GetPlayerTokenRsp p = GetPlayerTokenRsp.newBuilder()
				.setUid(session.getAccount().getPlayerUid())
				.setToken(session.getAccount().getToken())
				.setAccountType(1)
				.setIsProficientPlayer(doesPlayerExist) // Not sure where this goes
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
