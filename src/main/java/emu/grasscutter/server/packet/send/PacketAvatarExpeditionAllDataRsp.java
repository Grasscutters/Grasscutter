package emu.grasscutter.server.packet.send;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.expedition.ExpeditionInfo;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarExpeditionAllDataRspOuterClass.AvatarExpeditionAllDataRsp;
import emu.grasscutter.net.proto.AvatarExpeditionInfoOuterClass.AvatarExpeditionInfo;

import java.util.*;

public class PacketAvatarExpeditionAllDataRsp extends BasePacket {
    public PacketAvatarExpeditionAllDataRsp(Player player) {
        super(PacketOpcodes.AvatarExpeditionAllDataRsp);

        List<Integer> openExpeditionList  = new ArrayList<>(List.of(306,305,304,303,302,301,206,105,204,104,203,103,202,101,102,201,106,205));
        Map<Long, AvatarExpeditionInfo> avatarExpeditionInfoList = new HashMap<Long, AvatarExpeditionInfo>();

        var expeditionInfo = player.getExpeditionInfo();
        for (Long key : player.getExpeditionInfo().keySet()) {
            ExpeditionInfo e = expeditionInfo.get(key);
            avatarExpeditionInfoList.put(key, AvatarExpeditionInfo.newBuilder().setStateValue(e.getState()).setExpId(e.getExpId()).setHourTime(e.getHourTime()).setStartTime(e.getStartTime()).build());
        };

        AvatarExpeditionAllDataRsp.Builder proto = AvatarExpeditionAllDataRsp.newBuilder()
                .addAllOpenExpeditionList(openExpeditionList)
                .setExpeditionCountLimit(5)
                .putAllExpeditionInfoMap(avatarExpeditionInfoList);

        this.setData(proto.build());
    }
}
