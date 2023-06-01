// package emu.grasscutter.server.packet.recv;
//
// import emu.grasscutter.net.packet.Opcodes;
// import emu.grasscutter.net.packet.PacketHandler;
// import emu.grasscutter.net.packet.PacketOpcodes;
// import emu.grasscutter.net.proto.RemoveCustomTeamReqOuterClass.RemoveCustomTeamReq;
// import emu.grasscutter.server.game.GameSession;
//
// @Opcodes(PacketOpcodes.RemoveCustomTeamReq)
// public class HandlerRemoveCustomTeamReq extends PacketHandler {
//    @Override
//    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
//        RemoveCustomTeamReq req = RemoveCustomTeamReq.parseFrom(payload);
//        session.getPlayer().getTeamManager().removeCustomTeam(req.getId());
//    }
// }
