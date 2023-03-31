package emu.grasscutter.tools;

import emu.grasscutter.net.proto.GetGachaInfoRspOuterClass.GetGachaInfoRsp;
import emu.grasscutter.net.proto.GetShopRspOuterClass.GetShopRsp;

public final class Dumpers {
    public static void extractBanner(byte[] data) throws Exception {
        GetGachaInfoRsp proto = GetGachaInfoRsp.parseFrom(data);
        System.out.println(proto);
    }

    public static void extractShop(byte[] data) throws Exception {
        GetShopRsp proto = GetShopRsp.parseFrom(data);
        System.out.println(proto);
    }
}
