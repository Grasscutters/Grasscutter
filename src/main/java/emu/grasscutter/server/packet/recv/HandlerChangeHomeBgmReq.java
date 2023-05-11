// package emu.grasscutter.server.packet.recv;
//
// import emu.grasscutter.net.packet.Opcodes;
// import emu.grasscutter.net.packet.PacketHandler;
// import emu.grasscutter.net.packet.PacketOpcodes;
// import emu.grasscutter.net.proto.Unk2700BEDLIGJANCJClientReq;
// import emu.grasscutter.server.game.GameSession;
// import emu.grasscutter.server.packet.send.PacketChangeHomeBgmNotify;
// import emu.grasscutter.server.packet.send.PacketChangeHomeBgmRsp;
//
// @Opcodes(PacketOpcodes.Unk2700_BEDLIGJANCJ_ClientReq)
// public class HandlerChangeHomeBgmReq extends PacketHandler {
//    @Override
//    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
//        var req = Unk2700BEDLIGJANCJClientReq.Unk2700_BEDLIGJANCJ_ClientReq.parseFrom(payload);
//
//        int homeBgmId = req.getUnk2700BJHAMKKECEI();
//        var home = session.getPlayer().getHome();
//
//        home.getHomeSceneItem(session.getPlayer().getSceneId()).setHomeBgmId(homeBgmId);
//        home.save();
//
//        session.send(new PacketChangeHomeBgmNotify(homeBgmId));
//        session.send(new PacketChangeHomeBgmRsp());
//    }
// }
