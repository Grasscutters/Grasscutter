package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.activity.musicgame.MusicGameBeatmap;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.MusicGameGetBeatmapReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketMusicGameGetBeatmapRsp;

@Opcodes(PacketOpcodes.MusicGameGetBeatmapReq)
public class HandlerMusicGameGetBeatmapReq extends PacketHandler {

	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		var req = MusicGameGetBeatmapReqOuterClass.MusicGameGetBeatmapReq.parseFrom(payload);

		var musicGameBeatmap = MusicGameBeatmap.getByShareId(req.getMusicShareId());

		if(musicGameBeatmap == null){
			return;
		}

        session.send(new PacketMusicGameGetBeatmapRsp(
            musicGameBeatmap.toBriefProto().build(),
            musicGameBeatmap.toProto(),
            req
        ));
	}

}
