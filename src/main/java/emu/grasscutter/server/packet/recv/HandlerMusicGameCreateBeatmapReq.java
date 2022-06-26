package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.activity.musicgame.MusicGameActivityHandler;
import emu.grasscutter.game.activity.musicgame.MusicGameBeatmap;
import emu.grasscutter.game.props.ActivityType;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.MusicGameCreateBeatmapReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketActivityInfoNotify;
import emu.grasscutter.server.packet.send.PacketMusicGameCreateBeatmapRsp;
import emu.grasscutter.utils.Utils;

@Opcodes(PacketOpcodes.MusicGameCreateBeatmapReq)
public class HandlerMusicGameCreateBeatmapReq extends PacketHandler {

	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		var req = MusicGameCreateBeatmapReqOuterClass.MusicGameCreateBeatmapReq.parseFrom(payload);

        var musicGameBeatmap = MusicGameBeatmap.of()
            .musicId(req.getMusicBriefInfo().getMusicId())
            .musicNoteCount(req.getMusicBriefInfo().getMusicNoteCount())
            .savePosition(req.getMusicBriefInfo().getSavePosition())
            .maxScore(req.getMusicBriefInfo().getMaxScore())
            .authorUid(session.getPlayer().getUid())
            .beatmap(MusicGameBeatmap.parse(req.getMusicRecord().getBeatmapItemListList()))
            .createTime(Utils.getCurrentSeconds())
            .build();

        // TODO avoid player save too much to make server down
        musicGameBeatmap.save();

        var playerData = session.getPlayer().getActivityManager().getPlayerActivityDataByActivityType(ActivityType.NEW_ACTIVITY_MUSIC_GAME);
        if(playerData.isEmpty()){
            return;
        }

        var handler = (MusicGameActivityHandler) playerData.get().getActivityHandler();
        handler.addPersonalBeatmap(playerData.get(), musicGameBeatmap);

        session.send(new PacketActivityInfoNotify(handler.toProto(playerData.get())));
        session.send(new PacketMusicGameCreateBeatmapRsp(musicGameBeatmap.getMusicShareId(), req.getUnknownEnum1()));
	}

}
