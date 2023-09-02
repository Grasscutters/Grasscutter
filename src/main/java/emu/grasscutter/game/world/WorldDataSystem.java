package emu.grasscutter.game.world;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.*;
import emu.grasscutter.data.excels.*;
import emu.grasscutter.data.excels.world.WorldLevelData;
import emu.grasscutter.game.entity.gadget.chest.*;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.InvestigationMonsterOuterClass;
import emu.grasscutter.scripts.data.*;
import emu.grasscutter.server.game.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.luaj.vm2.LuaError;

public class WorldDataSystem extends BaseGameSystem {
    private final Map<String, ChestInteractHandler> chestInteractHandlerMap; // chestType-Handler
    private final Map<String, SceneGroup> sceneInvestigationGroupMap; // <sceneId_groupId, Group>

    public WorldDataSystem(GameServer server) {
        super(server);
        this.chestInteractHandlerMap = new HashMap<>();
        this.sceneInvestigationGroupMap = new ConcurrentHashMap<>();

        loadChestConfig();
    }

    public synchronized void loadChestConfig() {
        // set the special chest first
        chestInteractHandlerMap.put("SceneObj_Chest_Flora", new BossChestInteractHandler());

        try {
            DataLoader.loadList("ChestReward.json", ChestReward.class)
                    .forEach(
                            reward ->
                                    reward
                                            .getObjNames()
                                            .forEach(
                                                    name ->
                                                            chestInteractHandlerMap.computeIfAbsent(
                                                                    name, x -> new NormalChestInteractHandler(reward))));
        } catch (Exception e) {
            Grasscutter.getLogger().error("Unable to load chest reward config.", e);
        }
    }

    public Map<String, ChestInteractHandler> getChestInteractHandlerMap() {
        return chestInteractHandlerMap;
    }

    public RewardPreviewData getRewardByBossId(int monsterId) {
        var investigationMonsterData =
                GameData.getInvestigationMonsterDataMap().values().parallelStream()
                        .filter(imd -> imd.getMonsterIdList() != null && !imd.getMonsterIdList().isEmpty())
                        .filter(imd -> imd.getMonsterIdList().get(0) == monsterId)
                        .findFirst();

        return investigationMonsterData
                .map(
                        monsterData -> GameData.getRewardPreviewDataMap().get(monsterData.getRewardPreviewId()))
                .orElse(null);
    }

    private SceneGroup getInvestigationGroup(int sceneId, int groupId) {
        var key = sceneId + "_" + groupId;
        if (!sceneInvestigationGroupMap.containsKey(key)) {
            try {
                var group = SceneGroup.of(groupId).load(sceneId);
                sceneInvestigationGroupMap.putIfAbsent(key, group);
                return group;
            } catch (LuaError luaError) {
                Grasscutter.getLogger()
                        .error("failed to get investigationGroup {} in scene{}:", groupId, sceneId, luaError);
            }
        }
        return sceneInvestigationGroupMap.get(key);
    }

    public int getMonsterLevel(SceneMonster monster, World world) {
        // Calculate level
        int level = monster.level;
        WorldLevelData worldLevelData = GameData.getWorldLevelDataMap().get(world.getWorldLevel());

        if (worldLevelData != null) {
            level = Math.max(level, worldLevelData.getMonsterLevel());
        }
        return level;
    }

    private InvestigationMonsterOuterClass.InvestigationMonster getInvestigationMonster(
            Player player, InvestigationMonsterData imd) {
        if (imd.getGroupIdList().isEmpty() || imd.getMonsterIdList().isEmpty()) {
            return null;
        }

        var groupId = imd.getGroupIdList().get(0);
        var monsterId = imd.getMonsterIdList().get(0);
        var sceneId = imd.getCityData().getSceneId();
        var group = getInvestigationGroup(sceneId, groupId);

        if (group == null || group.monsters == null) {
            return null;
        }

        var monster =
                group.monsters.values().stream().filter(x -> x.monster_id == monsterId).findFirst();
        if (monster.isEmpty()) {
            return null;
        }

        var builder = InvestigationMonsterOuterClass.InvestigationMonster.newBuilder();

        builder
                .setId(imd.getId())
                .setCityId(imd.getCityId())
                .setSceneId(imd.getCityData().getSceneId())
                .setGroupId(groupId)
                .setMonsterId(monsterId)
                .setLevel(getMonsterLevel(monster.get(), player.getWorld()))
                .setIsAlive(true)
                .setNextRefreshTime(Integer.MAX_VALUE)
                .setRefreshInterval(Integer.MAX_VALUE)
                .setPos(monster.get().pos.toProto());

        if ("Boss".equals(imd.getMonsterCategory())) {
            var bossChest = group.searchBossChestInGroup();
            if (bossChest.isPresent()) {
                builder.setResin(bossChest.get().resin);
                builder.setMaxBossChestNum(bossChest.get().take_num);
            }
        }
        return builder.build();
    }

    public List<InvestigationMonsterOuterClass.InvestigationMonster> getInvestigationMonstersByCityId(
            Player player, int cityId) {
        var cityData = GameData.getCityDataMap().get(cityId);
        if (cityData == null) {
            Grasscutter.getLogger().warn("City not exist {}", cityId);
            return List.of();
        }

        return GameData.getInvestigationMonsterDataMap().values().parallelStream()
                .filter(imd -> imd.getCityId() == cityId)
                .map(imd -> this.getInvestigationMonster(player, imd))
                .filter(Objects::nonNull)
                .toList();
    }
}
