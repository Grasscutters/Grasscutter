package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeAvatarRewardEventGetRspOuterClass;
import java.util.List;

public class PacketHomeAvatarRewardEventGetRsp extends BasePacket {
    public PacketHomeAvatarRewardEventGetRsp(int eventId, List<GameItem> rewards) {
        super(PacketOpcodes.HomeAvatarRewardEventGetRsp);

        this.setData(
                HomeAvatarRewardEventGetRspOuterClass.HomeAvatarRewardEventGetRsp.newBuilder()
                        .setEventId(eventId)
                        .addAllItemList(rewards.stream().map(GameItem::toItemParam).toList()));
    }

    public PacketHomeAvatarRewardEventGetRsp(int eventId, int retcode) {
        super(PacketOpcodes.HomeAvatarRewardEventGetRsp);

        this.setData(
                HomeAvatarRewardEventGetRspOuterClass.HomeAvatarRewardEventGetRsp.newBuilder()
                        .setEventId(eventId)
                        .setRetcode(retcode));
    }
}
