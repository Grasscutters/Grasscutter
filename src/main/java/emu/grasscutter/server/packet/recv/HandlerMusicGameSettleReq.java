package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.activity.musicgame.MusicGameActivityHandler;
import emu.grasscutter.game.activity.musicgame.MusicGamePlayerData;
import emu.grasscutter.game.props.ActivityType;
import emu.grasscutter.game.props.WatcherTriggerType;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.MusicGameSettleReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketActivityInfoNotify;
import emu.grasscutter.server.packet.send.PacketMusicGameSettleRsp;

@Opcodes(PacketOpcodes.MusicGameSettleReq)
public class HandlerMusicGameSettleReq extends PacketHandler {

	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		var req = MusicGameSettleReqOuterClass.MusicGameSettleReq.parseFrom(payload);

        var playerData = session.getPlayer().getActivityManager().getPlayerActivityDataByActivityType(ActivityType.NEW_ACTIVITY_MUSIC_GAME);
        if(playerData.isEmpty()){
            return;
        }
        var handler = (MusicGameActivityHandler) playerData.get().getActivityHandler();
        boolean isNewRecord = false;
        // check if custom beatmap
        if(req.getMusicShareId() == 0){
            session.getPlayer().getActivityManager().triggerWatcher(
                WatcherTriggerType.TRIGGER_FLEUR_FAIR_MUSIC_GAME_REACH_SCORE,
                String.valueOf(req.getMusicBasicId()),
                String.valueOf(req.getScore())
            );

            isNewRecord = handler.setMusicGameRecord(playerData.get(),
                MusicGamePlayerData.MusicGameRecord.of()
                    .musicId(req.getMusicBasicId())
                    .maxCombo(req.getMaxCombo())
                    .maxScore(req.getScore())
                    .build());

            // update activity info
            session.send(new PacketActivityInfoNotify(handler.toProto(playerData.get())));
        }else{
            handler.setMusicGameCustomBeatmapRecord(playerData.get(),
                MusicGamePlayerData.CustomBeatmapRecord.of()
                    .musicShareId(req.getMusicShareId())
                    .score(req.getMaxCombo())
                    .settle(req.getSuccess())
                    .build());
        }


		session.send(new PacketMusicGameSettleRsp(req.getMusicBasicId(), req.getMusicShareId(), isNewRecord));
	}

}
