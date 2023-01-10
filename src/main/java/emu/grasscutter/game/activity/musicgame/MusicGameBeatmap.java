package emu.grasscutter.game.activity.musicgame;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.net.proto.UgcMusicRecordOuterClass;
import emu.grasscutter.net.proto.UgcMusicNoteOuterClass;
import emu.grasscutter.net.proto.UgcMusicTrackOuterClass;
import emu.grasscutter.net.proto.UgcMusicBriefInfoOuterClass;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Random;

@Entity("music_game_beatmaps")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(builderMethodName = "of")
public class MusicGameBeatmap {

    @Id
    long musicShareId;
    int authorUid;
    int musicId;
    int musicNoteCount;
    int savePosition;
    int maxScore;
    int createTime;

    List<List<BeatmapNote>> beatmap;

    public static MusicGameBeatmap getByShareId(long musicShareId) {
        return DatabaseHelper.getMusicGameBeatmap(musicShareId);
    }

    public void save() {
        if (musicShareId == 0) {
            musicShareId = new Random().nextLong(100000000000000L,999999999999999L);
        }
        DatabaseHelper.saveMusicGameBeatmap(this);
    }

    public static List<List<BeatmapNote>> parse(List<UgcMusicTrackOuterClass.UgcMusicTrack> beatmapItemListList) {
        return beatmapItemListList.stream()
            .map(item -> item.getMusicNoteListList().stream()
                .map(BeatmapNote::parse)
                .toList())
            .toList();
    }

    public UgcMusicRecordOuterClass.UgcMusicRecord toProto() {
        return UgcMusicRecordOuterClass.UgcMusicRecord.newBuilder()
            .setMusicId(musicId)
            .addAllMusicTrackList(beatmap.stream()
                .map(this::musicBeatmapListToProto)
                .toList())
            .build();
    }

    public UgcMusicBriefInfoOuterClass.UgcMusicBriefInfo.Builder toBriefProto() {
        var player = DatabaseHelper.getPlayerByUid(authorUid);

        return UgcMusicBriefInfoOuterClass.UgcMusicBriefInfo.newBuilder()
            .setMusicId(musicId)
//            .setMusicNoteCount(musicNoteCount)
            .setUgcGuid(musicShareId)
            .setMaxScore(maxScore)
//            .setShareTime(createTime)
            .setCreatorNickname(player.getNickname())
            .setVersion(1);
    }

    private UgcMusicTrackOuterClass.UgcMusicTrack musicBeatmapListToProto(List<BeatmapNote> beatmapNoteList) {
        return UgcMusicTrackOuterClass.UgcMusicTrack.newBuilder()
            .addAllMusicNoteList(beatmapNoteList.stream()
                .map(BeatmapNote::toProto)
                .toList())
            .build();
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Builder(builderMethodName = "of")
    @Entity
    public static class BeatmapNote{
        int startTime;
        int endTime;

        public static BeatmapNote parse(UgcMusicNoteOuterClass.UgcMusicNote note) {
            return BeatmapNote.of()
                .startTime(note.getStartTime())
                .endTime(note.getEndTime())
                .build();
        }

        public UgcMusicNoteOuterClass.UgcMusicNote toProto() {
            return UgcMusicNoteOuterClass.UgcMusicNote.newBuilder()
                .setStartTime(startTime)
                .setEndTime(endTime)
                .build();
        }
    }
}
