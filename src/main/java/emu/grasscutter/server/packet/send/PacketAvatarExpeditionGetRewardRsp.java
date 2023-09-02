package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.expedition.ExpeditionInfo;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.AvatarExpeditionGetRewardRspOuterClass.AvatarExpeditionGetRewardRsp;
import java.util.*;

public class PacketAvatarExpeditionGetRewardRsp extends BasePacket {
    public PacketAvatarExpeditionGetRewardRsp(
            Map<Long, ExpeditionInfo> expeditionInfo, Collection<GameItem> items) {
        super(PacketOpcodes.AvatarExpeditionGetRewardRsp);

        AvatarExpeditionGetRewardRsp.Builder proto = AvatarExpeditionGetRewardRsp.newBuilder();
        expeditionInfo.forEach((key, e) -> proto.putExpeditionInfoMap(key, e.toProto()));
        items.forEach(item -> proto.addItemList(item.toItemParam()));

        this.setData(proto.build());
    }
}
