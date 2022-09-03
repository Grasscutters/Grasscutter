package emu.grasscutter.server.packet.send;

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

    public PacketGetPlayerTokenRsp(GameSession session, int retcode, String msg, int blackEndTime) {
        super(PacketOpcodes.GetPlayerTokenRsp, true);

        this.setUseDispatchKey(true);

        GetPlayerTokenRsp p = GetPlayerTokenRsp.newBuilder()
            .setUid(session.getPlayer().getUid())
            .setIsProficientPlayer(session.getPlayer().getAvatars().getAvatarCount() > 0)
            .setRetcode(retcode)
            .setMsg(msg)
            .setBlackUidEndTime(blackEndTime)
            .setRegPlatform(3)
            .setCountryCode("US")
            .setClientIpStr(session.getAddress().getAddress().getHostAddress())
            .build();

        this.setData(p.toByteArray());
    }

    public PacketGetPlayerTokenRsp(GameSession session, String encryptedSeed, String encryptedSeedSign) {
        super(PacketOpcodes.GetPlayerTokenRsp, true);

        this.setUseDispatchKey(true);

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
            .setEncryptedSeed(encryptedSeed)
            .setSeedSignature(encryptedSeedSign)
            .build();

        this.setData(p.toByteArray());
    }
}
