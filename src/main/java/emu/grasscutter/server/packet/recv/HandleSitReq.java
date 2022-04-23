package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SitReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketSitRsp;
import emu.grasscutter.utils.Position;

@Opcodes(PacketOpcodes.SitReq)
public class HandleSitReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        SitReqOuterClass.SitReq req = SitReqOuterClass.SitReq.parseFrom(payload);

        float x = req.getPosition().getX();
        float y = req.getPosition().getY();
        float z = req.getPosition().getZ();

        session.send(new PacketSitRsp(req.getChairId(), new Position(x, y, z), session.getPlayer().getTeamManager().getCurrentAvatarEntity().getId()));
    }

}