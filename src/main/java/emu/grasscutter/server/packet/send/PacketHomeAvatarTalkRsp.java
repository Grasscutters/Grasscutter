package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass;
import emu.grasscutter.net.proto.HomeAvatarTalkRspOuterClass;
import java.util.Set;

public class PacketHomeAvatarTalkRsp extends BasePacket {
    public PacketHomeAvatarTalkRsp(int avatarId, Set<Integer> talkIdSet) {
        super(PacketOpcodes.HomeAvatarTalkRsp);

        this.setData(
                HomeAvatarTalkRspOuterClass.HomeAvatarTalkRsp.newBuilder()
                        .setAvatarTalkInfo(
                                HomeAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFinishInfo.newBuilder()
                                        .setAvatarId(avatarId)
                                        .addAllFinishTalkIdList(talkIdSet)
                                        .build()));
    }
}
