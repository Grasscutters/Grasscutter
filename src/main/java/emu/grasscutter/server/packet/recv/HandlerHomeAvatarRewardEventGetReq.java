package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeAvatarRewardEventGetReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketHomeAvatarAllFinishRewardNotify;
import emu.grasscutter.server.packet.send.PacketHomeAvatarRewardEventGetRsp;
import emu.grasscutter.server.packet.send.PacketHomeAvatarRewardEventNotify;

@Opcodes(PacketOpcodes.HomeAvatarRewardEventGetReq)
public class HandlerHomeAvatarRewardEventGetReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = HomeAvatarRewardEventGetReqOuterClass.HomeAvatarRewardEventGetReq.parseFrom(payload);

        var player = session.getPlayer();
        var rewardsOrError =
                player.getCurHomeWorld().getModuleManager().claimAvatarRewards(req.getEventId());
        session.send(new PacketHomeAvatarRewardEventNotify(player));
        session.send(new PacketHomeAvatarAllFinishRewardNotify(player));

        session.send(
                rewardsOrError.map(
                        gameItems -> new PacketHomeAvatarRewardEventGetRsp(req.getEventId(), gameItems),
                        integer -> new PacketHomeAvatarRewardEventGetRsp(req.getEventId(), integer)));
    }
}
