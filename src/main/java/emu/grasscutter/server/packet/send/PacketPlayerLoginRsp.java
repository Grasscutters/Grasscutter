package emu.grasscutter.server.packet.send;

import com.google.protobuf.ByteString;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.Grasscutter.ServerDebugMode;
import emu.grasscutter.Grasscutter.ServerRunMode;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PlayerLoginRspOuterClass.PlayerLoginRsp;
import emu.grasscutter.net.proto.QueryCurrRegionHttpRspOuterClass;
import emu.grasscutter.net.proto.RegionInfoOuterClass.RegionInfo;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.utils.FileUtils;

import java.io.File;
import java.net.URL;
import java.util.Base64;

public class PacketPlayerLoginRsp extends BasePacket {

	private static QueryCurrRegionHttpRspOuterClass.QueryCurrRegionHttpRsp regionCache;

	public PacketPlayerLoginRsp(GameSession session) {
		super(PacketOpcodes.PlayerLoginRsp, 1);
		
		this.setUseDispatchKey(true);

		RegionInfo info;

		if (Grasscutter.getConfig().RunMode == ServerRunMode.GAME_ONLY) {
			if (regionCache == null) {
				try {
					File file = new File(Grasscutter.getConfig().DATA_FOLDER + "query_cur_region.txt");
					String query_cur_region = "";
					if (file.exists()) {
						query_cur_region = new String(FileUtils.read(file));
					} else {
						Grasscutter.getLogger().warn("query_cur_region not found! Using default current region.");
					}

					byte[] decodedCurRegion = Base64.getDecoder().decode(query_cur_region);
					QueryCurrRegionHttpRspOuterClass.QueryCurrRegionHttpRsp regionQuery = QueryCurrRegionHttpRspOuterClass.QueryCurrRegionHttpRsp.parseFrom(decodedCurRegion);

					RegionInfo serverRegion = regionQuery.getRegionInfo().toBuilder()
							.setGateserverIp((Grasscutter.getConfig().getGameServerOptions().PublicIp.isEmpty() ? Grasscutter.getConfig().getGameServerOptions().Ip : Grasscutter.getConfig().getGameServerOptions().PublicIp))
							.setGateserverPort(Grasscutter.getConfig().getGameServerOptions().PublicPort != 0 ? Grasscutter.getConfig().getGameServerOptions().PublicPort : Grasscutter.getConfig().getGameServerOptions().Port)
							.setSecretKey(ByteString.copyFrom(FileUtils.read(Grasscutter.getConfig().KEY_FOLDER + "dispatchSeed.bin")))
							.build();

					regionCache = regionQuery.toBuilder().setRegionInfo(serverRegion).build();
				} catch (Exception e) {
					Grasscutter.getLogger().error("Error while initializing region cache!", e);
				}
			}

			info = regionCache.getRegionInfo();
		} else {
			info = Grasscutter.getDispatchServer().getCurrRegion().getRegionInfo();
		}

		PlayerLoginRsp p = PlayerLoginRsp.newBuilder()
				.setIsUseAbilityHash(true) // true
				.setAbilityHashCode(1844674) // 1844674
				.setGameBiz("hk4e_global")
				.setClientDataVersion(info.getClientDataVersion())
				.setClientSilenceDataVersion(info.getClientSilenceDataVersion())
				.setClientMd5(info.getClientDataMd5())
				.setClientSilenceMd5(info.getClientSilenceDataMd5())
				.setResVersionConfig(info.getResVersionConfig())
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
