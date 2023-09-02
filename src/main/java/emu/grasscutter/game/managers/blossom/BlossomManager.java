package emu.grasscutter.game.managers.blossom;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.*;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.data.excels.RewardPreviewData;
import emu.grasscutter.game.entity.*;
import emu.grasscutter.game.entity.gadget.GadgetWorktop;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.*;
import emu.grasscutter.game.world.SpawnDataEntry.SpawnGroupEntry;
import emu.grasscutter.net.proto.*;
import emu.grasscutter.server.packet.send.PacketBlossomBriefInfoNotify;
import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.*;
import java.util.*;

public class BlossomManager {
    private final Scene scene;
    private final List<BlossomActivity> blossomActivities = new ArrayList<>();
    private final List<BlossomActivity> activeChests = new ArrayList<>();
    private final List<EntityGadget> createdEntity = new ArrayList<>();

    private final List<SpawnDataEntry> blossomConsumed = new ArrayList<>();

    public BlossomManager(Scene scene) {
        this.scene = scene;
    }

    public void onTick() {
        synchronized (blossomActivities) {
            var it = blossomActivities.iterator();
            while (it.hasNext()) {
                var active = it.next();
                active.onTick();
                if (active.getPass()) {
                    EntityGadget chest = active.getChest();
                    scene.addEntity(chest);
                    scene.setChallenge(null);
                    activeChests.add(active);
                    it.remove();
                }
            }
        }
    }

    public void recycleGadgetEntity(List<GameEntity> entities) {
        for (var entity : entities) {
            if (entity instanceof EntityGadget gadget) {
                createdEntity.remove(gadget);
            }
        }
        notifyIcon();
    }

    public void initBlossom(EntityGadget gadget) {
        if (createdEntity.contains(gadget)) {
            return;
        }
        if (blossomConsumed.contains(gadget.getSpawnEntry())) {
            return;
        }
        var id = gadget.getGadgetId();
        if (BlossomType.valueOf(id) == null) {
            return;
        }
        gadget.buildContent();
        gadget.setState(204);
        int worldLevel = getWorldLevel();
        GadgetWorktop gadgetWorktop = ((GadgetWorktop) gadget.getContent());
        gadgetWorktop.addWorktopOptions(new int[] {187});
        gadgetWorktop.setOnSelectWorktopOptionEvent(
                (GadgetWorktop context, int option) -> {
                    BlossomActivity activity;
                    EntityGadget entityGadget = context.getGadget();
                    synchronized (blossomActivities) {
                        for (BlossomActivity i : this.blossomActivities) {
                            if (i.getGadget() == entityGadget) {
                                return false;
                            }
                        }

                        int volume = 0;
                        IntList monsters = new IntArrayList();
                        while (true) {
                            var remain = GameDepot.getBlossomConfig().getMonsterFightingVolume() - volume;
                            if (remain <= 0) {
                                break;
                            }
                            var rand = Utils.randomRange(1, 100);
                            if (rand > 85 && remain >= 50) { // 15% ,generate strong monster
                                monsters.addAll(getRandomMonstersID(2, 1));
                                volume += 50;
                            } else if (rand > 50 && remain >= 20) { // 35% ,generate normal monster
                                monsters.addAll(getRandomMonstersID(1, 1));
                                volume += 20;
                            } else { // 50% ,generate weak monster
                                monsters.addAll(getRandomMonstersID(0, 1));
                                volume += 10;
                            }
                        }

                        Grasscutter.getLogger().info("Blossom Monsters:" + monsters);

                        activity = new BlossomActivity(entityGadget, monsters, -1, worldLevel);
                        blossomActivities.add(activity);
                    }
                    entityGadget.updateState(201);
                    scene.setChallenge(activity.getChallenge());
                    scene.removeEntity(entityGadget, VisionTypeOuterClass.VisionType.VISION_TYPE_REMOVE);
                    activity.start();
                    return true;
                });
        createdEntity.add(gadget);
        notifyIcon();
    }

    public void notifyIcon() {
        final int wl = getWorldLevel();
        final int worldLevel = (wl < 0) ? 0 : ((wl > 8) ? 8 : wl);
        final var worldLevelData = GameData.getWorldLevelDataMap().get(worldLevel);
        final int monsterLevel = (worldLevelData != null) ? worldLevelData.getMonsterLevel() : 1;
        List<BlossomBriefInfoOuterClass.BlossomBriefInfo> blossoms = new ArrayList<>();
        GameDepot.getSpawnLists()
                .forEach(
                        (gridBlockId, spawnDataEntryList) -> {
                            int sceneId = gridBlockId.getSceneId();
                            spawnDataEntryList.stream()
                                    .map(SpawnDataEntry::getGroup)
                                    .map(SpawnGroupEntry::getSpawns)
                                    .flatMap(List::stream)
                                    .filter(spawn -> !blossomConsumed.contains(spawn))
                                    .filter(spawn -> BlossomType.valueOf(spawn.getGadgetId()) != null)
                                    .forEach(
                                            spawn -> {
                                                var type = BlossomType.valueOf(spawn.getGadgetId());
                                                int previewReward = getPreviewReward(type, worldLevel);
                                                blossoms.add(
                                                        BlossomBriefInfoOuterClass.BlossomBriefInfo.newBuilder()
                                                                .setSceneId(sceneId)
                                                                .setPos(spawn.getPos().toProto())
                                                                .setResin(20)
                                                                .setMonsterLevel(monsterLevel)
                                                                .setRewardId(previewReward)
                                                                .setCircleCampId(type.getCircleCampId())
                                                                .setRefreshId(
                                                                        type.getBlossomChestId()) // TODO: replace when using actual
                                                                // leylines
                                                                .build());
                                            });
                        });
        scene.broadcastPacket(new PacketBlossomBriefInfoNotify(blossoms));
    }

    public int getWorldLevel() {
        return scene.getWorld().getWorldLevel();
    }

    private static Integer getPreviewReward(BlossomType type, int worldLevel) {
        // TODO: blossoms should be based on their city
        if (type == null) {
            Grasscutter.getLogger().error("Illegal blossom type {}", type);
            return null;
        }

        int blossomChestId = type.getBlossomChestId();
        var dataMap = GameData.getBlossomRefreshExcelConfigDataMap();
        for (var data : dataMap.values()) {
            if (blossomChestId == data.getBlossomChestId()) {
                var dropVecList = data.getDropVec();
                if (worldLevel > dropVecList.length) {
                    Grasscutter.getLogger().error("Illegal world level {}", worldLevel);
                    return null;
                }
                return dropVecList[worldLevel].getPreviewReward();
            }
        }
        Grasscutter.getLogger().error("Cannot find blossom type {}", type);
        return null;
    }

    private static RewardPreviewData getRewardList(BlossomType type, int worldLevel) {
        Integer previewReward = getPreviewReward(type, worldLevel);
        if (previewReward == null) return null;
        return GameData.getRewardPreviewDataMap().get((int) previewReward);
    }

    public List<GameItem> onReward(Player player, EntityGadget chest, boolean useCondensedResin) {
        var resinManager = player.getResinManager();
        synchronized (activeChests) {
            var it = activeChests.iterator();
            while (it.hasNext()) {
                var activeChest = it.next();
                if (activeChest.getChest() == chest) {
                    boolean pay =
                            useCondensedResin ? resinManager.useCondensedResin(1) : resinManager.useResin(20);
                    if (pay) {
                        int worldLevel = getWorldLevel();
                        List<GameItem> items = new ArrayList<>();
                        var gadget = activeChest.getGadget();
                        var type = BlossomType.valueOf(gadget.getGadgetId());
                        RewardPreviewData blossomRewards = getRewardList(type, worldLevel);
                        if (blossomRewards == null) {
                            Grasscutter.getLogger()
                                    .error("Blossom could not support world level : " + worldLevel);
                            return null;
                        }
                        var rewards = blossomRewards.getPreviewItems();
                        for (ItemParamData blossomReward : rewards) {
                            int rewardCount = blossomReward.getCount();
                            if (useCondensedResin) {
                                rewardCount += blossomReward.getCount(); // Double!
                            }
                            items.add(new GameItem(blossomReward.getItemId(), rewardCount));
                        }
                        it.remove();
                        recycleGadgetEntity(List.of(gadget));
                        blossomConsumed.add(gadget.getSpawnEntry());
                        return items;
                    }
                    return null;
                }
            }
        }
        return null;
    }

    public static IntList getRandomMonstersID(int difficulty, int count) {
        IntList result = new IntArrayList();
        List<Integer> monsters =
                GameDepot.getBlossomConfig().getMonsterIdsPerDifficulty().get(difficulty);
        for (int i = 0; i < count; i++) {
            result.add((int) monsters.get(Utils.randomRange(0, monsters.size() - 1)));
        }
        return result;
    }
}
