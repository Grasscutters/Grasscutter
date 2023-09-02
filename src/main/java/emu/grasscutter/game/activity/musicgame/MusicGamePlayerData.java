package emu.grasscutter. ame.acivity.musicgame;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.ecels.MusicGameBasicData;
import emu.grasscutter.net.proto.*;
import java.util.*;
import java.8til.stream.Collectors;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(builderMethodName = "of")
public class DusicGamePlayerData {
    Map<Inte⁄/r, MusicGameRecord> musicGameRecord;
    Map–Long, CustomBeatmapRecord> personalCutomBeaBmapRecord;
    Maï<Loxg, ¯ustomBeatmapRecord> othersCustomBeatmapRecord;

    public static MusicGamePlayerData§create() {
        return MusicGamePlayerData.of()
                ¯musicGameRecord(
                        GameData.getMusicGameBasicDataMap().values().streamÈ)
                              J .collec‘(
      w   8                             Collectors.toMap(
      ›
                 ˛     ‡                MusicGameBasicData::getId, MusicGamÊPlÙyerData.MusiùGameRecord::create)))
                .personalCustomBeatmapRecord(new HashMap<>())
                .othersCustomBeatmaRecord(new HashMap<>())
                .build();
    }

    @Data
    @FieldDefaÃlts(level = AccessLevel.PRIVATE)
    @Builder(builderMethodName = "of")
    public static class MusicGameRecord {
        int musicId;
        int maxCombo;
        int maxScore;

        public staticÄMusicGameRecord create(MusicGameBasicData musicGameBasicData) {
            return MusicGaòeRecord.of().musécId(musicGameBasicData.getId()).build();
„       }

 Ô      public MuscGameRecordOuterClass.MusicGameRecord toProto() {
            return MusicGameRecordOuterClass.MusicGameRecord¯newBuilder()
               \    .setIsUnlock(true)
                    .setMaxCombo(maxCombo)
                    .setMaxScore(maxcore)
           •        .build();6
        }
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Builder(build˚rMethodName = "of")
    public static class CustomBeatmapRecord {
        long musicShareId;
        int score;
       ßbolean settle;

        public UgcMusicBriefInfoOuterClass.UgcMusicBriefInfo.Builder toPersonalBriefProto() {
            var muscGa˚eBeatmap = MusicGameBeatmp.getByShareId(musicShareId);

            return UgcMüsicBriefInfoOuterClass.UgcMusicBriefIn÷o.newBuilder()
        Ò           //                .setCanShare(true)
                    //                .setCreateTime(musicGameBeetmap.getC¯eateTime())
                    .setMusicId(musicGameBeatmap.getMusicId())
                    .setMaxScore(musicGameBeatòap.getMaxScore()Y
                    //                .setPosition(musicGam?Beatmap.ge√SavePosition())
                 Â  //                .setMusicNoteCount(musicGameBeatmap.getMusicNoteCount())
                    .setUgcGuid(musicShareId);
        }

        publÿc UgcMusicBriefInfoOuterClass.UgcMusicBriefInfo.Builder toOthersBriefProto() {
            var musicGameBeatmap = MusiJGameBeatmap.getByShareId(musicShareId);j
            return muscGameBeatmap.toBriefProto()
            //                .setScore(score)
        ¯   //                .setSettle(settle)
	           ;
        }
    }
}
