package emu.grasscutter.server.packet.send;

import com.google.protobuf.ByteString;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetPlayerTokenRspOuterClass.GetPlayerTokenRsp;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.utils.Crypto;

public class PacketGetPlayerTokenRsp extends GenshinPacket {

	public PacketGetPlayerTokenRsp(GameSession session, boolean doesPlayerExist) {
		super(PacketOpcodes.GetPlayerTokenRsp, true);
		
		this.setUseDispatchKey(true);
		
		GetPlayerTokenRsp p = GetPlayerTokenRsp.newBuilder()
				.setPlayerUid(session.getAccount().getPlayerUid())
				.setAccountToken(session.getAccount().getToken())
				.setAccountType(1)
				.setIsProficientPlayer(doesPlayerExist) // Not sure where this goes
				.setSecretKey(Crypto.ENCRYPT_SEED)
				.setSecretKeyBuffer(ByteString.copyFrom(Crypto.ENCRYPT_SEED_BUFFER))
				.setPlatformType(3)
				.setChannelId(1)
				.setCountryCode("US")
				.setUnk1("c25-314dd05b0b5f")
				.setUnk3(3)
				.setClientIp(session.getAddress().getAddress().getHostAddress())
				.build();
	
		this.setData(p.toByteArray());
	}
}
