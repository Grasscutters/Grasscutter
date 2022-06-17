package emu.grasscutter.game.managers.mapmark;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.MapMarkPointTypeOuterClass.MapMarkPointType;
import emu.grasscutter.net.proto.MarkMapReqOuterClass.MarkMapReq;
import emu.grasscutter.net.proto.MarkMapReqOuterClass.MarkMapReq.Operation;
import emu.grasscutter.server.packet.send.PacketMarkMapRsp;
import emu.grasscutter.server.packet.send.PacketSceneEntityAppearNotify;
import emu.grasscutter.utils.Position;

import java.util.HashMap;

public class MapMarksManager {
    public static final int mapMarkMaxCount = 150;
    private HashMap<String, MapMark> mapMarks;
    private final Player player;

    public MapMarksManager(Player player) {
        this.player = player;
        this.mapMarks = player.getMapMarks();
        if (this.mapMarks == null) { this.mapMarks = new HashMap<>(); }
    }

    public void handleMapMarkReq(MarkMapReq req) {
        Operation op = req.getOp();
        switch (op) {
            case OPERATION_ADD -> {
                MapMark createMark = new MapMark(req.getMark());
                // keep teleporting functionality on fishhook mark.
                if (createMark.getMapMarkPointType() == MapMarkPointType.MAP_MARK_POINT_TYPE_FISH_POOL) {
                    teleport(player, createMark);
                    return;
                }
                addMapMark(createMark);
            }
            case OPERATION_MOD -> {
                MapMark oldMark = new MapMark(req.getOld());
                removeMapMark(oldMark.getPosition());
                MapMark newMark = new MapMark(req.getMark());
                addMapMark(newMark);
            }
            case OPERATION_DEL -> {
                MapMark deleteMark = new MapMark(req.getMark());
                removeMapMark(deleteMark.getPosition());
            }
        }
        if (op != Operation.OPERATION_GET) {
            saveMapMarks();
        }
        player.getSession().send(new PacketMarkMapRsp(getMapMarks()));
    }

    public HashMap<String, MapMark> getMapMarks() {
        return mapMarks;
    }

    public String getMapMarkKey(Position position) {
        return "x" + (int)position.getX()+ "z" + (int)position.getZ();
    }

    public void removeMapMark(Position position) {
        mapMarks.remove(getMapMarkKey(position));
    }

    public void addMapMark(MapMark mapMark) {
        if (mapMarks.size() < mapMarkMaxCount) {
            mapMarks.put(getMapMarkKey(mapMark.getPosition()), mapMark);
        }
    }

    private void saveMapMarks() {
        player.setMapMarks(mapMarks);
        player.save();
    }

    private void teleport(Player player, MapMark mapMark) {
        float y;
        try {
            y = (float)Integer.parseInt(mapMark.getName());
        } catch (Exception e) {
            y = 300;
        }
        Position pos = mapMark.getPosition();
        player.getPos().set(pos.getX(), y, pos.getZ());
        if (mapMark.getSceneId() != player.getSceneId()) {
            player.getWorld().transferPlayerToScene(player, mapMark.getSceneId(), player.getPos());
        }
        player.getScene().broadcastPacket(new PacketSceneEntityAppearNotify(player));
    }
}
