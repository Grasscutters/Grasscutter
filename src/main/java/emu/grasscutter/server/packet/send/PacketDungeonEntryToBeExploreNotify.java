package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.DungeonEntryToBeExploreNotifyOuterClass.DungeonEntryToBeExploreNotify;

public class PacketDungeonEntryToBeExploreNotify extends BasePacket {

    /**
     * Marks the dungeon as pending exploration. This creates the "Unknown" text bubble above the
     * dungeon entry in the world map.
     */
    public PacketDungeonEntryToBeExploreNotify(
            int dungeonEntryScenePointId, int sceneId, int dungeonEntryConfigId) {
        super(PacketOpcodes.DungeonEntryToBeExploreNotify);
        this.setData(
                DungeonEntryToBeExploreNotify.newBuilder()
                        .setDungeonEntryScenePointId(dungeonEntryScenePointId)
                        .setSceneId(sceneId)
                        .setDungeonEntryConfigId(dungeonEntryConfigId));
    }
}
