package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.expedition.ExpeditionInfo;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarExpeditionDataNotifyOuterClass.AvatarExpeditionDataNotify;
import emu.grasscutter.net.proto.AvatarExpeditionInfoOuterClass.AvatarExpeditionInfo;

import java.util.HashMap;
import java.util.Map;

public class PacketAvatarExpeditionDataNotify extends BasePacket {
    public PacketAvatarExpeditionDataNotify(Player player) {
        super(PacketOpcodes.AvatarExpeditionDataNotify);

        Map<Long, AvatarExpeditionInfo> avatarExpeditionInfoList = new HashMap<Long, AvatarExpeditionInfo>();

        var expeditionInfo = player.getExpeditionInfo();
        for (Long key : player.getExpeditionInfo().keySet()) {
            ExpeditionInfo e = expeditionInfo.get(key);
            avatarExpeditionInfoList.put(key, AvatarExpeditionInfo.newBuilder().setStateValue(e.getState()).setExpId(e.getExpId()).setHourTime(e.getHourTime()).setStartTime(e.getStartTime()).build());
        }

        AvatarExpeditionDataNotify.Builder proto = AvatarExpeditionDataNotify.newBuilder()
            .putAllExpeditionInfoMap(avatarExpeditionInfoList);

        this.setData(proto.build());
    }
}
