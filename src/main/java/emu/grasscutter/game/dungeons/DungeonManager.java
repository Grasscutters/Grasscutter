package emu.grasscutter.game.dungeons;

import emu.grasscutter.GameConstants;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.ScenePointEntry;
import emu.grasscutter.data.excels.DungeonData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.SceneType;
import emu.grasscutter.game.quest.enums.QuestTrigger;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.server.game.GameServer;
import emu.grasscutter.server.packet.send.PacketDungeonEntryInfoRsp;
import emu.grasscutter.server.packet.send.PacketPlayerEnterDungeonRsp;
import emu.grasscutter.utils.Position;

import java.util.List;

public class DungeonManager {
    private final GameServer server;
    private static final BasicDungeonSettleListener basicDungeonSettleObserver = new BasicDungeonSettleListener();

    public DungeonManager(GameServer server) {
        this.server = server;
    }

    public GameServer getServer() {
        return this.server;
    }

    public void getEntryInfo(Player player, int pointId) {
        ScenePointEntry entry = GameData.getScenePointEntryById(player.getScene().getId(), pointId);

        if (entry == null) {
            // Error
            player.sendPacket(new PacketDungeonEntryInfoRsp());
            return;
        }

        player.sendPacket(new PacketDungeonEntryInfoRsp(player, entry.getPointData()));
    }

    public boolean enterDungeon(Player player, int pointId, int dungeonId) {
        DungeonData data = GameData.getDungeonDataMap().get(dungeonId);

        if (data == null) {
            return false;
        }
        Grasscutter.getLogger().info("{}({}) is trying to enter dungeon {}", player.getNickname(), player.getUid(), dungeonId);

        int sceneId = data.getSceneId();
        player.getScene().setPrevScene(sceneId);

        if (player.getWorld().transferPlayerToScene(player, sceneId, data)) {
            player.getScene().addDungeonSettleObserver(basicDungeonSettleObserver);
            player.getQuestManager().triggerEvent(QuestTrigger.QUEST_CONTENT_ENTER_DUNGEON, data.getId());
        }

        player.getScene().setPrevScenePoint(pointId);
        player.sendPacket(new PacketPlayerEnterDungeonRsp(pointId, dungeonId));
        return true;
    }

    /**
     * used in tower dungeons handoff
     */
    public boolean handoffDungeon(Player player, int dungeonId, List<DungeonSettleListener> dungeonSettleListeners) {
        DungeonData data = GameData.getDungeonDataMap().get(dungeonId);

        if (data == null) {
            return false;
        }
        Grasscutter.getLogger().info("{}({}) is trying to enter tower dungeon {}", player.getNickname(), player.getUid(), dungeonId);

        if (player.getWorld().transferPlayerToScene(player, data.getSceneId(), data)) {
            dungeonSettleListeners.forEach(player.getScene()::addDungeonSettleObserver);
        }
        return true;
    }

    public void exitDungeon(Player player) {
        Scene scene = player.getScene();

        if (scene == null || scene.getSceneType() != SceneType.SCENE_DUNGEON) {
            return;
        }

        // Get previous scene
        int prevScene = scene.getPrevScene() > 0 ? scene.getPrevScene() : 3;

        // Get previous position
        DungeonData dungeonData = scene.getDungeonData();
        Position prevPos = new Position(GameConstants.START_POSITION);

        if (dungeonData != null) {
            ScenePointEntry entry = GameData.getScenePointEntryById(prevScene, scene.getPrevScenePoint());

            if (entry != null) {
                prevPos.set(entry.getPointData().getTranPos());
            }
        }
        // clean temp team if it has
        player.getTeamManager().cleanTemporaryTeam();
        player.getTowerManager().clearEntry();

        // Transfer player back to world
        player.getWorld().transferPlayerToScene(player, prevScene, prevPos);
        player.sendPacket(new BasePacket(PacketOpcodes.PlayerQuitDungeonRsp));
    }

    public void updateDailyDungeons() {
        for (ScenePointEntry entry : GameData.getScenePointEntries().values()) {
            entry.getPointData().updateDailyDungeon();
        }
    }
}
