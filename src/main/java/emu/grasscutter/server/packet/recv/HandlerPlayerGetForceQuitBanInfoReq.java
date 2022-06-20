package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.RetcodeOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketPlayerGetForceQuitBanInfoRsp;

@Opcodes(PacketOpcodes.PlayerGetForceQuitBanInfoReq)
public class HandlerPlayerGetForceQuitBanInfoReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {

        if (session.getServer().getMultiplayerManager().leaveCoop(session.getPlayer())) {
            // Success
            session.send(new PacketPlayerGetForceQuitBanInfoRsp(RetcodeOuterClass.Retcode.RET_SUCC_VALUE));
        } else {
            // Fail
            session.send(new PacketPlayerGetForceQuitBanInfoRsp(RetcodeOuterClass.Retcode.RET_SVR_ERROR_VALUE));
        }
    }

}
