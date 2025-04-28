package emu.grasscutter.game.managers.mapmark;

import dev.morphia.annotations.Entity;
import emu.grasscutter.game.world.Position;
import emu.grasscutter.net.proto.MapMarkFromTypeOuterClass.MapMarkFromType;
import emu.grasscutter.net.proto.MapMarkPointOuterClass.MapMarkPoint;
import emu.grasscutter.net.proto.MapMarkPointTypeOuterClass.MapMarkPointType;
import lombok.Getter;

@Getter
@Entity
public class MapMark {
    private int sceneId;
    private String name;
    private Position position;
    private final MapMarkPointType mapMarkPointType;
    private int monsterId;
    private final MapMarkFromType mapMarkFromType;
    private int questId;

    @Deprecated // Morhpia
    public MapMark() {
        this.mapMarkPointType = MapMarkPointType.MAP_MARK_POINT_TYPE_MONSTER;
        this.mapMarkFromType = MapMarkFromType.MAP_MARK_FROM_TYPE_MONSTER;
    }

    public MapMark(MapMarkPoint mapMarkPoint) {
        this.sceneId = mapMarkPoint.getSceneId();
        this.name = mapMarkPoint.getName();
        this.position = new Position(mapMarkPoint.getPos());
        this.mapMarkPointType = mapMarkPoint.getPointType();
        this.monsterId = mapMarkPoint.getMonsterId();
        this.mapMarkFromType = mapMarkPoint.getFromType();
        this.questId = mapMarkPoint.getQuestId();
    }
}
