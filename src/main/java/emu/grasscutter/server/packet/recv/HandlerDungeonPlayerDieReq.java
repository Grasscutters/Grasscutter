package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.DungeonPlayerDieReqOuterClass.DungeonPlayerDieReq;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketDungeonPlayerDieRsp;

@Opcodes(PacketOpcodes.DungeonPlayerDieReq)
public class HandlerDungeonPlayerDieReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        DungeonPlayerDieReq req = DungeonPlayerDieReq.parseFrom(payload);

        Player player = session.getPlayer();

        boolean result = player.getScene().respawnPlayer(player);

        player.sendPacket(new PacketDungeonPlayerDieRsp(result ? Retcode.RET_SUCC : Retcode.RET_FAIL));
    }
}
