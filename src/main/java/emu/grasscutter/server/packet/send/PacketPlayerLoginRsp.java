package emu.grasscutter.server.packet.send;

import static emu.grasscutter.config.Configuration.*;

import com.google.protobuf.ByteString;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.Grasscutter.ServerRunMode;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.PlayerLoginRspOuterClass.PlayerLoginRsp;
import emu.grasscutter.net.proto.QueryCurrRegionHttpRspOuterClass;
import emu.grasscutter.net.proto.RegionInfoOuterClass.RegionInfo;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.http.dispatch.RegionHandler;
import emu.grasscutter.utils.Crypto;
import java.util.Objects;

public class PacketPlayerLoginRsp extends BasePacket {

    private static QueryCurrRegionHttpRspOuterClass.QueryCurrRegionHttpRsp regionCache;

    public PacketPlayerLoginRsp(GameSession session) {
        super(PacketOpcodes.PlayerLoginRsp, 1);

        this.setUseDispatchKey(true);

        RegionInfo info;

        if (Grasscutter.getRunMode() == ServerRunMode.GAME_ONLY) {
            if (regionCache == null) {
                try {
                    // todo: we might want to push custom config to client
                    RegionInfo serverRegion =
                            RegionInfo.newBuilder()
                                    .setGateserverIp(lr(GAME_INFO.accessAddress, GAME_INFO.bindAddress))
                                    .setGateserverPort(lr(GAME_INFO.accessPort, GAME_INFO.bindPort))
                                    .build();

                    regionCache =
                            QueryCurrRegionHttpRspOuterClass.QueryCurrRegionHttpRsp.newBuilder()
                                    .setRegionInfo(serverRegion)
                                    .setClientSecretKey(ByteString.copyFrom(Crypto.DISPATCH_SEED))
                                    .build();
                } catch (Exception e) {
                    Grasscutter.getLogger().error("Error while initializing region cache!", e);
                }
            }

            info = regionCache.getRegionInfo();
        } else {
            info = Objects.requireNonNull(RegionHandler.getCurrentRegion()).getRegionInfo();
        }

        PlayerLoginRsp p =
                PlayerLoginRsp.newBuilder()
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
                        // .setIsScOpen(false)
                        // .setScInfo(ByteString.copyFrom(new byte[] {}))
                        // .setRegisterCps("mihoyo")
                        .setCountryCode("US")
                        .build();

        this.setData(p.toByteArray());
    }
}
