// package emu.grasscutter.server.packet.recv;
//
// import emu.grasscutter.game.activity.musicgame.MusicGameBeatmap;
// import emu.grasscutter.net.packet.Opcodes;
// import emu.grasscutter.net.packet.PacketHandler;
// import emu.grasscutter.net.packet.PacketOpcodes;
// import emu.grasscutter.net.proto.MusicGameSearchBeatmapReqOuterClass;
// import emu.grasscutter.server.game.GameSession;
// import emu.grasscutter.server.packet.send.PacketMusicGameSearchBeatmapRsp;
//
// @Opcodes(PacketOpcodes.MusicGameSearchBeatmapReq)
// public class HandlerMusicGameSearchBeatmapReq extends PacketHandler {
//
//	@Override
//	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
//		var req = MusicGameSearchBeatmapReqOuterClass.MusicGameSearchBeatmapReq.parseFrom(payload);
//
//		var musicGameBeatmap = MusicGameBeatmap.getByShareId(req.getMusicShareId());
//
//		if(musicGameBeatmap == null){
//			session.send(new PacketMusicGameSearchBeatmapRsp(11153, req.getUnknownEnum1()));
//			return;
//		}
//
//        session.send(new PacketMusicGameSearchBeatmapRsp(musicGameBeatmap.toBriefProto().build(),
// req.getUnknownEnum1()));
//	}
//
// }
