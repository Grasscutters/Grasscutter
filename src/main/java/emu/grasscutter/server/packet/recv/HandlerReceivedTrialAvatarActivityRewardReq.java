package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.activity.trialavatar.TrialAvatarActivityHandler;
import emu.grasscutter.game.props.ActivityType;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ReceivedTrialAvatarActivityRewardReqOuterClass.ReceivedTrialAvatarActivityRewardReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketReceivedTrialAvatarActivityRewardRsp;
import lombok.val;

@Opcodes(PacketOpcodes.ReceivedTrialAvatarActivityRewardReq)
public class HandlerReceivedTrialAvatarActivityRewardReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        val req = ReceivedTrialAvatarActivityRewardReq.parseFrom(payload);
        val player = session.getPlayer();
        val handler =
                player
                        .getActivityManager()
                        .getActivityHandlerAs(
                                ActivityType.NEW_ACTIVITY_TRIAL_AVATAR, TrialAvatarActivityHandler.class);

        boolean result =
                handler.isPresent() && handler.get().getReward(player, req.getTrialAvatarIndexId());

        session
                .getPlayer()
                .sendPacket(
                        new PacketReceivedTrialAvatarActivityRewardRsp(
                                5002, // trial activity id
                                req.getTrialAvatarIndexId(),
                                result));
    }
}
