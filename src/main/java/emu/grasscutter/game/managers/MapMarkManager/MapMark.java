package emu.grasscutter.game.managers.MapMarkManager;

import dev.morphia.annotations.Entity;
import emu.grasscutter.net.proto.MapMarkFromTypeOuterClass;
import emu.grasscutter.net.proto.MapMarkPointOuterClass;
import emu.grasscutter.net.proto.MapMarkPointTypeOuterClass;
import emu.grasscutter.utils.Position;

@Entity
public class MapMark {
    private int sceneId;
    private String name;
    private Position position;
    private MapMarkPointTypeOuterClass.MapMarkPointType pointType;
    private int monsterId = 0;
    private  MapMarkFromTypeOuterClass.MapMarkFromType fromType;
    private int questId = 7;

    public MapMark(Position position, MapMarkPointTypeOuterClass.MapMarkPointType type) {
        this.position = position;
    }

    public MapMark(MapMarkPointOuterClass.MapMarkPoint mapMarkPoint) {
        this.sceneId = mapMarkPoint.getSceneId();
        this.name = mapMarkPoint.getName();
        this.position = new Position(mapMarkPoint.getPos().getX(), mapMarkPoint.getPos().getY(), mapMarkPoint.getPos().getZ());
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

    public MapMarkPointTypeOuterClass.MapMarkPointType getMapMarkPointType() {
        return this.pointType;
    }

    public void setMapMarkPointType(MapMarkPointTypeOuterClass.MapMarkPointType pointType) {
        this.pointType = pointType;
    }

    public int getMonsterId() {
        return this.monsterId;
    }

    public void setMonsterId(int monsterId) {
        this.monsterId = monsterId;
    }

    public MapMarkFromTypeOuterClass.MapMarkFromType getMapMarkFromType() {
        return this.fromType;
    }

    public int getQuestId() {
        return this.questId;
    }

    public void setQuestId(int questId) {
        this.questId = questId;
    }

}
