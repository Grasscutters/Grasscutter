package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.expedition.ExpeditionInfo;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarExpeditionGetRewardRspOuterClass.AvatarExpeditionGetRewardRsp;
import emu.grasscutter.net.proto.AvatarExpeditionInfoOuterClass.AvatarExpeditionInfo;

import java.util.Collection;

public class PacketAvatarExpeditionGetRewardRsp extends BasePacket {
    public PacketAvatarExpeditionGetRewardRsp(Player player, Collection<GameItem> items) {
        super(PacketOpcodes.AvatarExpeditionGetRewardRsp);

        AvatarExpeditionGetRewardRsp.Builder proto = AvatarExpeditionGetRewardRsp.newBuilder();
        var expeditionInfo = player.getExpeditionInfo();
        for (Long key : player.getExpeditionInfo().keySet()) {
            ExpeditionInfo e = expeditionInfo.get(key);
            proto.putExpeditionInfoMap(key, AvatarExpeditionInfo.newBuilder().setStateValue(e.getState()).setExpId(e.getExpId()).setHourTime(e.getHourTime()).setStartTime(e.getStartTime()).build());
        };

        for (GameItem item : items) {
            proto.addItemList(item.toItemParam());
        }

        this.setData(proto.build());
    }
}
