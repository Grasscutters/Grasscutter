package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeBasicInfoNotifyOuterClass;
import emu.grasscutter.net.proto.HomeBasicInfoOuterClass;

public class PacketHomeBasicInfoNotify extends BasePacket {

    public PacketHomeBasicInfoNotify(Player player, boolean editMode) {
        super(PacketOpcodes.HomeBasicInfoNotify);

        if (player.getCurrentRealmId() == null) {
            return;
        }

        var proto = HomeBasicInfoNotifyOuterClass.HomeBasicInfoNotify.newBuilder();

        var sceneId = player.getCurrentRealmId() + 2000;
        var homeScene = player.getHome().getHomeSceneItem(sceneId);

        proto.setBasicInfo(HomeBasicInfoOuterClass.HomeBasicInfo.newBuilder()
            .setCurModuleId(player.getCurrentRealmId())
            .setCurRoomSceneId(homeScene.getRoomSceneId())
            .setIsInEditMode(editMode)
            .setHomeOwnerUid(player.getUid())
            .setLevel(player.getHome().getLevel())
            .setOwnerNickName(player.getNickname())
            // TODO limit shop
            .build());

        this.setData(proto);
    }
}
