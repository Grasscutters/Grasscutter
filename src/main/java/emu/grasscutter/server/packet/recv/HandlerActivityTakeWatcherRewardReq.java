package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ActivityTakeWatcherRewardReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketActivityTakeWatcherRewardRsp;
import java.util.Optional;

@Opcodes(PacketOpcodes.ActivityTakeWatcherRewardReq)
public class HandlerActivityTakeWatcherRewardReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req =
                ActivityTakeWatcherRewardReqOuterClass.ActivityTakeWatcherRewardReq.parseFrom(payload);

        Optional.ofNullable(
                        session
                                .getPlayer()
                                .getActivityManager()
                                .getPlayerActivityDataMap()
                                .get(req.getActivityId()))
                .ifPresent(x -> x.takeWatcherReward(req.getWatcherId()));

        session.send(new PacketActivityTakeWatcherRewardRsp(req.getActivityId(), req.getWatcherId()));
    }
}
