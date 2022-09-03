package emu.grasscutter.game.activity.musicgame;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.net.proto.MusicBeatmapListOuterClass;
import emu.grasscutter.net.proto.MusicBeatmapNoteOuterClass;
import emu.grasscutter.net.proto.MusicBeatmapOuterClass;
import emu.grasscutter.net.proto.MusicBriefInfoOuterClass;
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

    public static MusicGameBeatmap getByShareId(long musicShareId){
        return DatabaseHelper.getMusicGameBeatmap(musicShareId);
    }

    public void save(){
        if(musicShareId == 0){
            musicShareId = new Random().nextLong(100000000000000L,999999999999999L);
        }
        DatabaseHelper.saveMusicGameBeatmap(this);
    }

    public static List<List<BeatmapNote>> parse(List<MusicBeatmapListOuterClass.MusicBeatmapList> beatmapItemListList) {
        return beatmapItemListList.stream()
            .map(item -> item.getBeatmapNoteListList().stream()
                .map(BeatmapNote::parse)
                .toList())
            .toList();
    }

    public MusicBeatmapOuterClass.MusicBeatmap toProto(){
        return MusicBeatmapOuterClass.MusicBeatmap.newBuilder()
            .setMusicId(musicId)
            .addAllBeatmapItemList(beatmap.stream()
                .map(this::musicBeatmapListToProto)
                .toList())
            .build();
    }

    public MusicBriefInfoOuterClass.MusicBriefInfo.Builder toBriefProto(){
        var player = DatabaseHelper.getPlayerByUid(authorUid);

        return MusicBriefInfoOuterClass.MusicBriefInfo.newBuilder()
            .setMusicId(musicId)
            .setMusicNoteCount(musicNoteCount)
            .setMusicShareId(musicShareId)
            .setMaxScore(maxScore)
            .setShareTime(createTime)
            .setAuthorNickname(player.getNickname())
            .setVersion(1)
            ;
    }

    private MusicBeatmapListOuterClass.MusicBeatmapList musicBeatmapListToProto(List<BeatmapNote> beatmapNoteList){
        return MusicBeatmapListOuterClass.MusicBeatmapList.newBuilder()
            .addAllBeatmapNoteList(beatmapNoteList.stream()
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

        public static BeatmapNote parse(MusicBeatmapNoteOuterClass.MusicBeatmapNote note){
            return BeatmapNote.of()
                .startTime(note.getStartTime())
                .endTime(note.getEndTime())
                .build();
        }

        public MusicBeatmapNoteOuterClass.MusicBeatmapNote toProto(){
            return MusicBeatmapNoteOuterClass.MusicBeatmapNote.newBuilder()
                .setStartTime(startTime)
                .setEndTime(endTime)
                .build();
        }
    }
}
