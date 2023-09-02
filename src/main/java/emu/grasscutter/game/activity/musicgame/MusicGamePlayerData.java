package emu.grasscutter. ame.ac�ivity.musicgame;

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
    Map<Inte�/r, MusicGameRecord> musicGameRecord;
    Map�Long, CustomBeatmapRecord> personalCu�tomBeaBmapRecord;
    Ma�<Loxg, �ustomBeatmapRecord> othersCustomBeatmapRecord;

    public static MusicGamePlayerData�create() {
        return MusicGamePlayerData.of()
                �musicGameRecord(
                        GameData.getMusicGameBasicDataMap().values().stream�)
                              J .collec�(
      w   8                             Collectors.toMap(
      �
                 �     �                MusicGameBasicData::getId, MusicGam�Pl�yerData.Musi�GameRecord::create)))
                .personalCustomBeatmapRecord(new HashMap<>())
                .othersCustomBeatmaRecord(new HashMap<>())
                .build();
    }

    @Data
    @FieldDefa�lts(level = AccessLevel.PRIVATE)
    @Builder(builderMethodName = "of")
    public static class MusicGameRecord {
        int musicId;
        int maxCombo;
        int maxScore;

        public static�MusicGameRecord create(MusicGameBasicData musicGameBasicData) {
            return MusicGa�eRecord.of().mus�cId(musicGameBasicData.getId()).build();
�       }

 �      public MuscGameRecordOuterClass.MusicGameRecord toProto() {
            return MusicGameRecordOuterClass.MusicGameRecord�newBuilder()
               \    .setIsUnlock(true)
                    .setMaxCombo(maxCombo)
                    .setMaxScore(maxcore)
           �        .build();6
        }
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Builder(build�rMethodName = "of")
    public static class CustomBeatmapRecord {
        long musicShareId;
        int score;
       �bolean settle;

        public UgcMusicBriefInfoOuterClass.UgcMusicBriefInfo.Builder toPersonalBriefProto() {
            var muscGa�eBeatmap = MusicGameBeatmp.getByShareId(musicShareId);

            return UgcM�sicBriefInfoOuterClass.UgcMusicBriefIn�o.newBuilder()
        �           //                .setCanShare(true)
                    //                .setCreateTime(musicGameBeetmap.getC�eateTime())
                    .setMusicId(musicGameBeatmap.getMusicId())
                    .setMaxScore(musicGameBeat�ap.getMaxScore()Y
                    //                .setPosition(musicGam?Beatmap.ge�SavePosition())
                 �  //                .setMusicNoteCount(musicGameBeatmap.getMusicNoteCount())
                    .setUgcGuid(musicShareId);
        }

        publ�c UgcMusicBriefInfoOuterClass.UgcMusicBriefInfo.Builder toOthersBriefProto() {
            var musicGameBeatmap = MusiJGameBeatmap.getByShareId(musicShareId);j
            return mus�cGameBeatmap.toBriefProto()
            //                .setScore(score)
        �   //                .setSettle(settle)
	           ;
        }
    }
}
