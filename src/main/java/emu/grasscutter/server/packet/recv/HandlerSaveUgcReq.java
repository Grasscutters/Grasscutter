// package emu.grasscutter.server.packet.recv;
//
// import emu.grasscutter.database.DatabaseHelper;
// import emu.grasscutter.game.activity.musicgame.MusicGameActivityHandler;
// import emu.grasscutter.game.activity.musicgame.MusicGameBeatmap;
// import emu.grasscutter.game.activity.musicgame.MusicGamePlayerData;
// import emu.grasscutter.game.props.ActivityType;
// import emu.grasscutter.net.packet.Opcodes;
// import emu.grasscutter.net.packet.PacketHandler;
// import emu.grasscutter.net.packet.PacketOpcodes;
// import emu.grasscutter.net.proto.RetcodeOuterClass;
// import emu.grasscutter.net.proto.SaveUgcReqOuterClass;
// import emu.grasscutter.net.proto.UgcTypeOuterClass;
// import emu.grasscutter.server.game.GameSession;
// import emu.grasscutter.server.packet.send.PacketActivityInfoNotify;
// import emu.grasscutter.server.packet.send.PacketMusicGameCreateBeatmapRsp;
// import emu.grasscutter.utils.Utils;
// import java.util.Objects;
// import lombok.val;
//
// @Opcodes(PacketOpcodes.SaveUgcReq)
// public class HandlerSaveUgcReq extends PacketHandler {
//
//    @Override
//    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
//        val req = SaveUgcReqOuterClass.SaveUgcReq.parseFrom(payload);
//
//        // We only support music game user generated content
//        if (req.getUgcType() != UgcTypeOuterClass.UgcType.UGC_TYPE_MUSIC_GAME) {
//            session.send(
//                    new PacketMusicGameCreateBeatmapRsp(
//                            RetcodeOuterClass.Retcode.RET_UGC_DISABLED, req.getUgcType()));
//            return;
//        }
//        val briefInfo = req.getMusicBriefInfo();
//
//        val musicGameBeatmap =
//                MusicGameBeatmap.of()
//                        .musicId(briefInfo.getMusicId())
//                        .musicNoteCount(briefInfo.getNoteCount())
//                        .savePosition(briefInfo.getSaveIdx())
//                        .savePageType(briefInfo.getSavePageType())
//                        .version(briefInfo.getVersion())
//                        .afterNoteList(briefInfo.getAfterNoteListList())
//                        .beforeNoteList(briefInfo.getBeforeNoteListList())
//                        .timeLineEditTime(briefInfo.getTimeLineEditTime())
//                        .publishTime(briefInfo.getPublishTime())
//                        .realTimeEditTime(briefInfo.getRealTimeEditTime())
//                        .maxScore(briefInfo.getMaxScore())
//                        .authorUid(session.getPlayer().getUid())
//
// .beatmap(MusicGameBeatmap.parse(req.getMusicRecord().getMusicTrackListList()))
//                        .createTime(Utils.getCurrentSeconds())
//                        .build();
//
//        musicGameBeatmap.save();
//
//        val playerData =
//                session
//                        .getPlayer()
//                        .getActivityManager()
//
// .getPlayerActivityDataByActivityType(ActivityType.NEW_ACTIVITY_MUSIC_GAME);
//        if (playerData.isEmpty()) {
//            session.send(
//                    new PacketMusicGameCreateBeatmapRsp(
//                            RetcodeOuterClass.Retcode.RET_UGC_DATA_NOT_FOUND, req.getUgcType()));
//            return;
//        }
//
//        val handler = (MusicGameActivityHandler) playerData.get().getActivityHandler();
//        val musicGamePlayerData = handler.getMusicGamePlayerData(playerData.get());
//
//        val oldBeatmap =
//                musicGamePlayerData.getPersonalCustomBeatmapRecord().values().stream()
//                        .map(MusicGamePlayerData.CustomBeatmapRecord::getMusicShareId)
//                        .map(DatabaseHelper::getMusicGameBeatmap)
//                        .filter(Objects::nonNull)
//                        .filter(item -> item.getAuthorUid() == session.getPlayer().getUid())
//                        .filter(item -> item.getMusicId() == req.getMusicBriefInfo().getMusicId())
//                        .filter(item -> item.getSavePosition() ==
// req.getMusicBriefInfo().getSaveIdx())
//                        .findFirst();
//
//        // delete old beatmap for player
//        // the old beatmap is still in database so that others can still play.
//        oldBeatmap.ifPresent(i -> handler.removePersonalBeatmap(playerData.get(), i));
//
//        // link this beatmap to player's personal data
//        handler.addPersonalBeatmap(playerData.get(), musicGameBeatmap);
//
//        session.send(
//                new PacketActivityInfoNotify(
//                        handler.toProto(
//                                playerData.get(),
//
// session.getPlayer().getActivityManager().getConditionExecutor())));
//        session.send(
//                new PacketMusicGameCreateBeatmapRsp(musicGameBeatmap.getMusicShareId(),
// req.getUgcType()));
//    }
// }
