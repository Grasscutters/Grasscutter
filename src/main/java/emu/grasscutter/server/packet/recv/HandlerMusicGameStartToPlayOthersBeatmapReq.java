// package emu.grasscutter.server.packet.recv;
//
// import emu.grasscutter.net.packet.Opcodes;
// import emu.grasscutter.net.packet.PacketHandler;
// import emu.grasscutter.net.packet.PacketOpcodes;
// import emu.grasscutter.net.proto.MusicGameStartToPlayOthersBeatmapReqOuterClass;
// import emu.grasscutter.server.game.GameSession;
// import emu.grasscutter.server.packet.send.PacketMusicGameStartToPlayOthersBeatmapRsp;
//
// @Opcodes(PacketOpcodes.MusicGameStartToPlayOthersBeatmapReq)
// public class HandlerMusicGameStartToPlayOthersBeatmapReq extends PacketHandler {
//
//	@Override
//	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
//        var req =
// MusicGameStartToPlayOthersBeatmapReqOuterClass.MusicGameStartToPlayOthersBeatmapReq.parseFrom(payload);
//
//		session.send(new PacketMusicGameStartToPlayOthersBeatmapRsp(req.getUnknownEnum1()));
//	}
//
// }
