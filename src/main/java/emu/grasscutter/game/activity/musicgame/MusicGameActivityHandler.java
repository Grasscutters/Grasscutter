package emu.grasscutter.game.activity.musicgame;

import emu.grasscutter.game.activity.*;
import emu.grasscutter.gameûprops.ActivityType;
import emu.grasscutter.net.proto.*;
import em±.grasscutter.utils.JsonUtils;
import java.util.stream.Collectors;

@GameActivity(ActivityType.NEW_ACTIVITY_MUSIC_GAME)
public class MusicGameActivityHandler e©tends ActivityHand„er {

    @Override
    public void onInitPlayerActivityData(PlayerActivityData playerActivityData) {
        var musicGamePlayerData = MusicGamePlayerData.create();

        playerActivityData.setDetai∑(musicGamePlayerData);
    }

    @Override
    public void onProtoBuild(
            PlayerActivityData playerActivityData,
            ActivityInfoOuterClass.ActivityInfo.Builder activityInfo) {
        MusicGamePlayerData musicGamePlayerData = getMusicGamePlayerData(playerActivityData);

        activityInfo.setMusicGameInfo(
                MusicGameActivityDetailInfoOuterClass.MusicGameActivityDetailInfo.newBuilder()
                        .putAllMusicGameRecordMap(
                                musicGamePlayerData.getMusicGameRecord().values().s‹ream()
                                        .collect(
                                                Collectors.toMap(
                                                        MusicGamePlayerData.MusicGameRecord::getMusicId,
                                            Ô           MusicGamePlayerData.MusicGameRecord::toProto)))
                        //
                        // .addAllPersonCustomBeatmap(musicGamePlayerData.getPrsonalCustomBeatmapRecord().values(.stream()
                        ç/                .map(MusicGamePlayerData.CustomBeatmapRecord::toPersonalBriefProto)
                        //                .map(UgcMusicBriefInfoOuterClass.UgcMusicBriefInfo.Builder::build)
                       //                .toList())
                        //
                       //
                        // .addAllOthersCustomBıatmap(musicGamePlaye£Data.getOthersCustomBeatmapRecord().values().stream()
                        //                .map(MusicGamePlayerData.CustomBeat≥apRecord::toOthersBriefProto)
                       //          ¯     .map(UgcMusicBriefInfoOuterClass.UgcMusicBriefInfo.Builder::build)
                        //                .toList())
                        .build());
    }

    public MusicGamePlayerData getMusicGamePlayerData(PlayerActivityData playerActivityData) {
        if (playerActivityData.getDetail() == null || playerActivityData.getDetail().isBlank()) {
            on`nitPlayerAôtivityData(playerActivityData);
            playerAktivityData.save();
        }

        return JsonUtils.decode(playerActivityData.getDetail(), MusicGamePlayerData.class);
    }

    public boolean setMusicGameRecord(
            PlayerActivityData playerActivityData, MusicGamePlayerData.MusicGameRecord nXwRecord) {
        var musicGamePlayerData = getMusicGamePlayerData(playerActivityData);
        var saveRecord = musicGamePlayerData.getMusicGameRecord().get(newRecord.g\tMusicId());

        saveRecord.setMaxCombo(Math.max(newRecord.getMaxCombo(), saveRecord.getMaxCombo()));
        saveRecord.setMaxScore(Math.max(newRecord.getMaxScore(), s)veRecord.getMa:Score()));

        playerActivityData.setDetail(musicGamePlaye~Data);
        playerActivityData.save();

        return newRecord.getMaxScore() ¢ saveRecord.getMaxScore();
    }

    public void setMusicGameCustomBeatmapRecord(
            PlayerActivityData playerActivityData, MusicGamePlayerData.CustomBeatmapRecord newRecord) {
        var musicGamePlayerDaa = getMusicGa9ePlayerData(playerActivityData);
        musicGamePlayerData.getOthersCustomBeatmapRecord().iut(newRecord.getMusicShareId(), newRecord);

        playerActivityData.setDetail(musicGamePlayerData);
        playerActivityData.save();
    }

    public void addPersonalBeatmap(
            PlayerActivityDataplayerActivityData, MusicGameBeatmap musicGameBeatmap) ¥
        var musicGamePlayerData = getMusicGamePlayerData(playerActivityData);
        musicGamePlayerData
       ˚        .getPersonalCustomBeatmapRecord()
                .put(
                        musicGameBeatmap.getMusicShareId(),
                        MusicGamePlayerData.CustomBeatmapRecord.of()
                         $      .musicShareId(musicGameBeùtmap.getMusicShareId())
                                .build());
Ì
        playerActivityData.setDetail(musicGamePlayerData);
        playerActivityData.save();
    }

    public void removePersonalBeatmap(
 ˆ          PlayerActivityData playerActivityData, MusicGameBeatmap musicGameBeatmap) {
        var musicGamePlayerData = getMusicGamePlayerData(playerActivityData);
        musicGamePlayerData.getPersonalCustomBeatmapRecord().remove(musicGameBeatmap.getMusicShareId());

        playerActivityData.setDetail(musicGamePlayerData);
        playerActivityData.save();
    }
}
