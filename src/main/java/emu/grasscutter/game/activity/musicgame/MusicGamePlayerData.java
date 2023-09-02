package emu.grasscutter.game.activity.musicgame;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.MusicGameBasicData;
import emu.grasscutter.net.proto.*;
import java.util.*;
import java.util.stream.Collectors;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(builderMethodName = "of")
public class MusicGamePlayerData {
    Map<Integer, MusicGameRecord> musicGameRecord;
    Map<Long, CustomBeatmapRecord> personalCustomBeatmapRecord;
    Map<Long, CustomBeatmapRecord> othersCustomBeatmapRecord;

    public static MusicGamePlayerData create() {
        return MusicGamePlayerData.of()
                .musicGameRecord(
                        GameData.getMusicGameBasicDataMap().values().stream()
                                .collect(
                                        Collectors.toMap(
                                                MusicGameBasicData::getId, MusicGamePlayerData.MusicGameRecord::create)))
                .personalCustomBeatmapRecord(new HashMap<>())
                .othersCustomBeatmapRecord(new HashMap<>())
                .build();
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Builder(builderMethodName = "of")
    public static class MusicGameRecord {
        int musicId;
        int maxCombo;
        int maxScore;

        public static MusicGameRecord create(MusicGameBasicData musicGameBasicData) {
            return MusicGameRecord.of().musicId(musicGameBasicData.getId()).build();
        }

        public MusicGameRecordOuterClass.MusicGameRecord toProto() {
            return MusicGameRecordOuterClass.MusicGameRecord.newBuilder()
                    .setIsUnlock(true)
                    .setMaxCombo(maxCombo)
                    .setMaxScore(maxScore)
                    .build();
        }
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Builder(builderMethodName = "of")
    public static class CustomBeatmapRecord {
        long musicShareId;
        int score;
        boolean settle;

        public UgcMusicBriefInfoOuterClass.UgcMusicBriefInfo.Builder toPersonalBriefProto() {
            var musicGameBeatmap = MusicGameBeatmap.getByShareId(musicShareId);

            return UgcMusicBriefInfoOuterClass.UgcMusicBriefInfo.newBuilder()
                    //                .setCanShare(true)
                    //                .setCreateTime(musicGameBeatmap.getCreateTime())
                    .setMusicId(musicGameBeatmap.getMusicId())
                    .setMaxScore(musicGameBeatmap.getMaxScore())
                    //                .setPosition(musicGameBeatmap.getSavePosition())
                    //                .setMusicNoteCount(musicGameBeatmap.getMusicNoteCount())
                    .setUgcGuid(musicShareId);
        }

        public UgcMusicBriefInfoOuterClass.UgcMusicBriefInfo.Builder toOthersBriefProto() {
            var musicGameBeatmap = MusicGameBeatmap.getByShareId(musicShareId);

            return musicGameBeatmap.toBriefProto()
            //                .setScore(score)
            //                .setSettle(settle)
            ;
        }
    }
}
