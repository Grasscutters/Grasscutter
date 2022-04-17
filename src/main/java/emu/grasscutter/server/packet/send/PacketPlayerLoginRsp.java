package emu.grasscutter.server.packet.send;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PlayerLoginRspOuterClass.PlayerLoginRsp;
import emu.grasscutter.net.proto.RegionInfoOuterClass.RegionInfo;
import emu.grasscutter.server.game.GameSession;

public class PacketPlayerLoginRsp extends GenshinPacket {

	public PacketPlayerLoginRsp(GameSession session) {
		super(PacketOpcodes.PlayerLoginRsp, 1);
		
		this.setUseDispatchKey(true);
		
		RegionInfo info = Grasscutter.getDispatchServer().getCurrRegion().getRegionInfo();
		
		PlayerLoginRsp p = PlayerLoginRsp.newBuilder()
				.setIsUseAbilityHash(true) // true
				.setAbilityHashCode(1844674) // 1844674
				.setGameBiz("hk4e_global")
				.setClientDataVersion(info.getClientDataVersion())
				.setClientSilenceDataVersion(info.getClientSilenceDataVersion())
				.setClientMd5(info.getClientDataMd5())
				.setClientSilenceMd5(info.getClientSilenceDataMd5())
				.setResVersionConfig(info.getConfig())
				.setClientVersionSuffix(info.getClientVersionSuffix())
				.setClientSilenceVersionSuffix(info.getClientSilenceVersionSuffix())
				.setIsScOpen(false)
				//.setScInfo(ByteString.copyFrom(new byte[] {}))
				.setRegisterCps("mihoyo")
				.setCountryCode("US")
				.build();
		
		this.setData(p.toByteArray());
	}
}
