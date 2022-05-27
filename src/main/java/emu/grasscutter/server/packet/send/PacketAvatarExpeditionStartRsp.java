package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.expedition.ExpeditionInfo;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarExpeditionInfoOuterClass.AvatarExpeditionInfo;
import emu.grasscutter.net.proto.AvatarExpeditionStartRspOuterClass.AvatarExpeditionStartRsp;

public class PacketAvatarExpeditionStartRsp extends BasePacket {
    public PacketAvatarExpeditionStartRsp(Player player) {
        super(PacketOpcodes.AvatarExpeditionStartRsp);

        AvatarExpeditionStartRsp.Builder proto = AvatarExpeditionStartRsp.newBuilder();
        var expeditionInfo = player.getExpeditionInfo();
        for (Long key : player.getExpeditionInfo().keySet()) {
            ExpeditionInfo e = expeditionInfo.get(key);
            proto.putExpeditionInfoMap(key, AvatarExpeditionInfo.newBuilder().setStateValue(e.getState()).setExpId(e.getExpId()).setHourTime(e.getHourTime()).setStartTime(e.getStartTime()).build());
        };

        this.setData(proto.build());
    }
}
