package emu.grasscutter.tools;

import emu.grasscutter.game.props.OpenState;
import emu.grasscutter.net.proto.GetGachaInfoRspOuterClass.GetGachaInfoRsp;
import emu.grasscutter.net.proto.GetShopRspOuterClass.GetShopRsp;
import emu.grasscutter.net.proto.OpenStateUpdateNotifyOuterClass.OpenStateUpdateNotify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Dumpers {
    public static void extractBanner(byte[] data) throws Exception {
        GetGachaInfoRsp proto = GetGachaInfoRsp.parseFrom(data);
        System.out.println(proto);
    }

    public static void extractShop(byte[] data) throws Exception {
        GetShopRsp proto = GetShopRsp.parseFrom(data);
        System.out.println(proto);
    }

    public static void dumpOpenStates(byte[] data) throws Exception {
        OpenStateUpdateNotify proto = OpenStateUpdateNotify.parseFrom(data);

        List<Integer> list = new ArrayList<>(proto.getOpenStateMap().keySet());
        Collections.sort(list);

        for (int key : list) {
            System.out.println(OpenState.getTypeByValue(key) + " : " + key);
        }
    }
}
