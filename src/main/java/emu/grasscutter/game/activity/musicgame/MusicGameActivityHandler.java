package emu.grasscutter.game.activity.musicgame;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.activity.ActivityHandler;
import emu.grasscutter.game.activity.GameActivity;
import emu.grasscutter.game.activity.PlayerActivityData;
import emu.grasscutter.game.props.ActivityType;
import emu.grasscutter.net.proto.ActivityInfoOuterClass;
import emu.grasscutter.net.proto.MusicBriefInfoOuterClass;
import emu.grasscutter.net.proto.MusicGameActivityDetailInfoOuterClass;

import java.util.stream.Collectors;

@GameActivity(ActivityType.NEW_ACTIVITY_MUSIC_GAME)
public class MusicGameActivityHandler extends ActivityHandler {

    @Override
    public void onInitPlayerActivityData(PlayerActivityData playerActivityData) {
        var musicGamePlayerData = MusicGamePlayerData.create();

        playerActivityData.setDetail(musicGamePlayerData);
    }

    @Override
    public void onProtoBuild(PlayerActivityData playerActivityData, ActivityInfoOuterClass.ActivityInfo.Builder activityInfo) {
        MusicGamePlayerData musicGamePlayerData = getMusicGamePlayerData(playerActivityData);

        activityInfo.setMusicGameInfo(MusicGameActivityDetailInfoOuterClass.MusicGameActivityDetailInfo.newBuilder()
            .putAllMusicGameRecordMap(
                musicGamePlayerData.getMusicGameRecord().values().stream()
                    .collect(Collectors.toMap(MusicGamePlayerData.MusicGameRecord::getMusicId, MusicGamePlayerData.MusicGameRecord::toProto)))
            .addAllPersonCustomBeatmap(musicGamePlayerData.getPersonalCustomBeatmapRecord().values().stream()
                .map(MusicGamePlayerData.CustomBeatmapRecord::toPersonalBriefProto)
                .map(MusicBriefInfoOuterClass.MusicBriefInfo.Builder::build)
                .toList())

            .addAllOthersCustomBeatmap(musicGamePlayerData.getOthersCustomBeatmapRecord().values().stream()
                .map(MusicGamePlayerData.CustomBeatmapRecord::toOthersBriefProto)
                .map(MusicBriefInfoOuterClass.MusicBriefInfo.Builder::build)
                .toList())
            .build());
    }

    public MusicGamePlayerData getMusicGamePlayerData(PlayerActivityData playerActivityData) {
        if (playerActivityData.getDetail() == null || playerActivityData.getDetail().isBlank()) {
            onInitPlayerActivityData(playerActivityData);
            playerActivityData.save();
        }

        return Grasscutter.getGsonFactory().fromJson(playerActivityData.getDetail(),
            MusicGamePlayerData.class);
    }

    public boolean setMusicGameRecord(PlayerActivityData playerActivityData, MusicGamePlayerData.MusicGameRecord newRecord) {
        var musicGamePlayerData = getMusicGamePlayerData(playerActivityData);
        var saveRecord = musicGamePlayerData.getMusicGameRecord().get(newRecord.getMusicId());

        saveRecord.setMaxCombo(Math.max(newRecord.getMaxCombo(), saveRecord.getMaxCombo()));
        saveRecord.setMaxScore(Math.max(newRecord.getMaxScore(), saveRecord.getMaxScore()));

        playerActivityData.setDetail(musicGamePlayerData);
        playerActivityData.save();

        return newRecord.getMaxScore() > saveRecord.getMaxScore();
    }
    public void setMusicGameCustomBeatmapRecord(PlayerActivityData playerActivityData, MusicGamePlayerData.CustomBeatmapRecord newRecord) {
        var musicGamePlayerData = getMusicGamePlayerData(playerActivityData);
        musicGamePlayerData.getOthersCustomBeatmapRecord().put(newRecord.getMusicShareId(), newRecord);

        playerActivityData.setDetail(musicGamePlayerData);
        playerActivityData.save();
    }

    public void addPersonalBeatmap(PlayerActivityData playerActivityData, MusicGameBeatmap musicGameBeatmap) {
        var musicGamePlayerData = getMusicGamePlayerData(playerActivityData);
        musicGamePlayerData.getPersonalCustomBeatmapRecord().put(musicGameBeatmap.getMusicShareId(),
            MusicGamePlayerData.CustomBeatmapRecord.of()
                .musicShareId(musicGameBeatmap.getMusicShareId())
                .build());

        playerActivityData.setDetail(musicGamePlayerData);
        playerActivityData.save();
    }

    public void removePersonalBeatmap(PlayerActivityData playerActivityData, MusicGameBeatmap musicGameBeatmap) {
        var musicGamePlayerData = getMusicGamePlayerData(playerActivityData);
        musicGamePlayerData.getPersonalCustomBeatmapRecord().remove(musicGameBeatmap.getMusicShareId());

        playerActivityData.setDetail(musicGamePlayerData);
        playerActivityData.save();
    }
}
