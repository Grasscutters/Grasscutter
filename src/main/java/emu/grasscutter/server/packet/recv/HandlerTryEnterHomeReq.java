package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.home.GameHome;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.FriendEnterHomeOptionOuterClass;
import emu.grasscutter.net.proto.RetcodeOuterClass;
import emu.grasscutter.net.proto.TryEnterHomeReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketTryEnterHomeRsp;

@Opcodes(PacketOpcodes.TryEnterHomeReq)
public class HandlerTryEnterHomeReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = TryEnterHomeReqOuterClass.TryEnterHomeReq.parseFrom(payload);
        var targetPlayer = session.getServer().getPlayerByUid(req.getTargetUid(), true);

        if (targetPlayer == null || !GameHome.doesHomeExist(targetPlayer.getUid())) {
            session.send(new PacketTryEnterHomeRsp());
            return;
        }

        var targetHome = session.getServer().getHomeWorldOrCreate(targetPlayer).getHome();

        if (req.getTargetUid() != session.getPlayer().getUid()) {
            // I hope that tomorrow there will be a hero who can support multiplayer mode and write code
            // like a poem
            // A person who rote this comment, I DID IT!!!!!! by hamusuke.
            switch (targetHome.getEnterHomeOption()) {
                case FriendEnterHomeOptionOuterClass.FriendEnterHomeOption
                        .FRIEND_ENTER_HOME_OPTION_NEED_CONFIRM_VALUE -> {
                    if (!targetPlayer.isOnline()) {
                        session.send(
                                new PacketTryEnterHomeRsp(
                                        RetcodeOuterClass.Retcode.RET_HOME_OWNER_OFFLINE_VALUE, req.getTargetUid()));
                    } else {
                        session
                                .getServer()
                                .getHomeWorldMPSystem()
                                .sendEnterHomeRequest(session.getPlayer(), req.getTargetUid());
                    }
                }
                case FriendEnterHomeOptionOuterClass.FriendEnterHomeOption
                        .FRIEND_ENTER_HOME_OPTION_REFUSE_VALUE -> {
                    session.send(
                            new PacketTryEnterHomeRsp(
                                    RetcodeOuterClass.Retcode.RET_HOME_HOME_REFUSE_GUEST_ENTER_VALUE,
                                    req.getTargetUid()));
                }

                case FriendEnterHomeOptionOuterClass.FriendEnterHomeOption
                        .FRIEND_ENTER_HOME_OPTION_DIRECT_VALUE -> {
                    session
                            .getServer()
                            .getHomeWorldMPSystem()
                            .enterHome(
                                    session.getPlayer(),
                                    targetPlayer,
                                    req.getTargetPoint(),
                                    req.getIsTransferToSafePoint());
                }
            }

            return;
        }

        session
                .getServer()
                .getHomeWorldMPSystem()
                .enterHome(
                        session.getPlayer(),
                        targetPlayer,
                        req.getTargetPoint(),
                        req.getIsTransferToSafePoint());
    }
}
