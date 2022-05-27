package emu.grasscutter.game.managers.MapMarkManager;

import dev.morphia.annotations.Entity;
import emu.grasscutter.net.proto.MapMarkFromTypeOuterClass.MapMarkFromType;
import emu.grasscutter.net.proto.MapMarkPointOuterClass.MapMarkPoint;
import emu.grasscutter.net.proto.MapMarkPointTypeOuterClass.MapMarkPointType;
import emu.grasscutter.utils.Position;

@Entity
public class MapMark {
    private final int sceneId;
    private final String name;
    private final Position position;
    private final MapMarkPointType pointType;
    private final int monsterId;
    private final MapMarkFromType fromType;
    private final int questId;

    public MapMark(MapMarkPoint mapMarkPoint) {
        this.sceneId = mapMarkPoint.getSceneId();
        this.name = mapMarkPoint.getName();
        this.position = new Position(
                mapMarkPoint.getPos().getX(),
                mapMarkPoint.getPos().getY(),
                mapMarkPoint.getPos().getZ()
        );
        this.pointType = mapMarkPoint.getPointType();
        this.monsterId = mapMarkPoint.getMonsterId();
        this.fromType = mapMarkPoint.getFromType();
        this.questId = mapMarkPoint.getQuestId();
    }

    public int getSceneId() {
        return this.sceneId;
    }
    public String getName() {
        return this.name;
    }
    public Position getPosition() {
        return this.position;
    }
    public MapMarkPointType getMapMarkPointType() {
        return this.pointType;
    }
    public int getMonsterId() {
        return this.monsterId;
    }
    public MapMarkFromType getMapMarkFromType() {
        return this.fromType;
    }
    public int getQuestId() {
        return this.questId;
    }
}