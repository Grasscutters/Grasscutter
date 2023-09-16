package emu.grasscutter.game.dungeons;

import emu.grasscutter.*;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.ScenePointEntry;
import emu.grasscutter.data.excels.dungeon.*;
import emu.grasscutter.game.dungeons.handlers.DungeonBaseHandler;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.SceneType;
import emu.grasscutter.game.world.*;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.server.game.*;
import emu.grasscutter.server.packet.send.PacketDungeonEntryInfoRsp;
import it.unimi.dsi.fastutil.ints.*;
import java.util.List;
import lombok.val;

public final class DungeonSystem extends BaseGameSystem {
    private static final BasicDungeonSettleListener basicDungeonSettleObserver =
            new BasicDungeonSettleListener();
    private final Int2ObjectMap<DungeonBaseHandler> passCondHandlers;

    public DungeonSystem(GameServer server) {
        super(server);

        this.passCondHandlers = new Int2ObjectOpenHashMap<>();
        this.registerHandlers();
    }

    public void registerHandlers() {
        this.registerHandlers(this.passCondHandlers, DungeonBaseHandler.class);
    }

    public <T> void registerHandlers(Int2ObjectMap<T> map, Class<T> clazz) {
        var handlerClasses = Grasscutter.reflector.getSubTypesOf(clazz);
        for (var obj : handlerClasses) {
            this.registerHandler(map, obj);
        }
    }

    public <T> void registerHandler(Int2ObjectMap<T> map, Class<? extends T> handlerClass) {
        try {
            DungeonValue opcode = handlerClass.getAnnotation(DungeonValue.class);

            if (opcode == null || opcode.value() == null) {
                return;
            }

            map.put(opcode.value().ordinal(), handlerClass.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends the entry info for the given dungeon point to the player.
     *
     * @param player The player to send the entry info to.
     * @param pointId The dungeon point ID.
     */
    public void sendEntryInfoFor(Player player, int pointId, int sceneId) {
        var entry = GameData.getScenePointEntryById(sceneId, pointId);
        if (entry == null) {
            // An invalid point ID was sent.
            player.sendPacket(new PacketDungeonEntryInfoRsp());
            return;
        }

        // Check if the player has quests with dungeon IDs.
        var questDungeons = player.getQuestManager().questsForDungeon(entry);
        if (questDungeons.size() > 0) {
            player.sendPacket(new PacketDungeonEntryInfoRsp(entry.getPointData(), questDungeons));
        } else {
            player.sendPacket(new PacketDungeonEntryInfoRsp(entry.getPointData()));
        }
    }

    public boolean triggerCondition(
            DungeonPassConfigData.DungeonPassCondition condition, int... params) {
        var handler = passCondHandlers.get(condition.getCondType().ordinal());

        if (handler == null) {
            Grasscutter.getLogger()
                    .debug("Could not trigger condition {} at {}", condition.getCondType(), params);
            return false;
        }

        return handler.execute(condition, params);
    }

    public boolean enterDungeon(Player player, int pointId, int dungeonId, boolean savePrevious) {
        DungeonData data = GameData.getDungeonDataMap().get(dungeonId);

        if (data == null) {
            return false;
        }
        Grasscutter.getLogger()
                .debug(
                        "{} ({}) is trying to enter dungeon {}.",
                        player.getNickname(),
                        player.getUid(),
                        dungeonId);

        var sceneId = data.getSceneId();
        var scene = player.getScene();
        if (savePrevious) scene.setPrevScene(scene.getId());

        if (player.getWorld().transferPlayerToScene(player, sceneId, data)) {
            scene = player.getScene();
            scene.setDungeonManager(new DungeonManager(scene, data));
            scene.addDungeonSettleObserver(basicDungeonSettleObserver);
        }

        if (savePrevious) scene.setPrevScenePoint(pointId);
        return true;
    }

    /** used in tower dungeons handoff */
    public boolean handoffDungeon(
            Player player, int dungeonId, List<DungeonSettleListener> dungeonSettleListeners) {
        DungeonData data = GameData.getDungeonDataMap().get(dungeonId);

        if (data == null) {
            return false;
        }
        Grasscutter.getLogger()
                .info(
                        "{}({}) is trying to enter tower dungeon {}",
                        player.getNickname(),
                        player.getUid(),
                        dungeonId);

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
        val dungeonManager = scene.getDungeonManager();
        DungeonData dungeonData = dungeonManager != null ? dungeonManager.getDungeonData() : null;
        Position prevPos = new Position(GameConstants.START_POSITION);

        if (dungeonData != null) {
            ScenePointEntry entry = GameData.getScenePointEntryById(prevScene, scene.getPrevScenePoint());

            if (entry != null) {
                prevPos.set(entry.getPointData().getTranPos());
            }
            if (!dungeonManager.isFinishedSuccessfully()) {
                dungeonManager.quitDungeon();
            }

            dungeonManager.unsetTrialTeam(player);
        }
        // clean temp team if it has
        player.getTeamManager().cleanTemporaryTeam();
        player.getTowerManager().clearEntry();

        // Transfer player back to world
        player.getWorld().transferPlayerToScene(player, prevScene, prevPos);
        player.sendPacket(new BasePacket(PacketOpcodes.PlayerQuitDungeonRsp));
    }
}
