package emu.grasscutter.game.drop;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.*;
import emu.grasscutter.data.common.DropItemData;
import emu.grasscutter.data.excels.*;
import emu.grasscutter.game.entity.*;
import emu.grasscutter.game.inventory.*;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.scripts.data.SceneMonster;
import emu.grasscutter.server.game.*;
import emu.grasscutter.server.packet.send.*;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.*;

public final class DropSystem extends BaseGameSystem {
    private final Int2ObjectMap<DropTableData> dropTable;
    private final Map<String, List<BaseDropData>> chestReward;
    private final Map<String, List<BaseDropData>> monsterDrop;
    private final Random rand;

    // TODO: don't know how to determine boss level.Have to hard-code the data from wiki.
    private final int[] bossLevel = {36, 37, 41, 50, 62, 72, 83, 91, 93};

    public DropSystem(GameServer server) {
        super(server);

        this.rand = new Random();
        this.dropTable = GameData.getDropTableDataMap();
        this.chestReward = new HashMap<>();
        this.monsterDrop = new HashMap<>();

        try {
            var dataList = DataLoader.loadList("ChestDrop.json", ChestDropData.class);
            for (var i : dataList) {
                if (!chestReward.containsKey(i.getIndex())) {
                    chestReward.put(i.getIndex(), new ArrayList<>());
                }
                chestReward.get(i.getIndex()).add(i);
            }
        } catch (Exception ignored) {
            Grasscutter.getLogger()
                    .error("Unable to load chest drop data. Please place ChestDrop.json in data folder.");
        }

        try {
            var dataList = DataLoader.loadList("MonsterDrop.json", BaseDropData.class);
            for (var i : dataList) {
                if (!monsterDrop.containsKey(i.getIndex())) {
                    monsterDrop.put(i.getIndex(), new ArrayList<>());
                }
                monsterDrop.get(i.getIndex()).add(i);
            }
        } catch (Exception ignored) {
            Grasscutter.getLogger()
                    .error("Unable to load monster drop data. Please place MonsterDrop.json in data folder.");
        }
    }

    private int queryDropData(String dropTag, int level, Map<String, List<BaseDropData>> rewards) {
        if (!rewards.containsKey(dropTag)) return 0;

        var rewardList = rewards.get(dropTag);
        BaseDropData dropData = null;
        int minLevel = 0;
        for (var i : rewardList) {
            if (level >= i.getMinLevel() && i.getMinLevel() > minLevel) {
                minLevel = i.getMinLevel();
                dropData = i;
            }
        }
        if (dropData == null) return 0;
        return dropData.getDropId();
    }

    public List<GameItem> handleDungeonRewardDrop(int dropId, boolean doubleReward) {
        if (!dropTable.containsKey(dropId)) return List.of();
        var dropData = dropTable.get(dropId);
        List<GameItem> items = new ArrayList<>();
        processDrop(dropData, doubleReward ? 2 : 1, items);
        return items;
    }

    public boolean handleMonsterDrop(EntityMonster monster) {
        int dropId;
        int level = monster.getLevel();
        SceneMonster sceneMonster = monster.getMetaMonster();
        if (sceneMonster != null) {
            if (sceneMonster.drop_tag != null) {
                dropId = queryDropData(sceneMonster.drop_tag, level, monsterDrop);
            } else {
                dropId = sceneMonster.drop_id;
            }
        } else {
            dropId = monster.getMonsterData().getKillDropId();
        }
        if (!dropTable.containsKey(dropId)) return false;
        var dropData = dropTable.get(dropId);
        List<GameItem> items = new ArrayList<>();
        processDrop(dropData, 1, items);
        if (dropData.isFallToGround()) {
            dropItems(
                    items, ActionReason.MonsterDie, monster, monster.getScene().getPlayers().get(0), true);
        } else {
            for (Player p : monster.getScene().getPlayers()) {
                p.getInventory().addItems(items, ActionReason.MonsterDie);
            }
        }
        return true;
    }

    public boolean handleChestDrop(int chestDropId, int dropCount, GameEntity bornFrom) {
        if (!dropTable.containsKey(chestDropId)) return false;
        var dropData = dropTable.get(chestDropId);
        List<GameItem> items = new ArrayList<>();
        processDrop(dropData, dropCount, items);
        if (dropData.isFallToGround()) {
            dropItems(items, ActionReason.OpenChest, bornFrom, bornFrom.getWorld().getHost(), false);
        } else {
            bornFrom.getWorld().getHost().getInventory().addItems(items, ActionReason.OpenChest);
        }
        return true;
    }

    public boolean handleChestDrop(String dropTag, int level, GameEntity bornFrom) {
        int dropId = queryDropData(dropTag, level, chestReward);
        if (dropId == 0) return false;
        return handleChestDrop(dropId, 1, bornFrom);
    }

    public boolean handleBossChestDrop(String dropTag, Player player) {
        int dropId = queryDropData(dropTag, bossLevel[player.getWorldLevel()], chestReward);
        if (!dropTable.containsKey(dropId)) return false;
        var dropData = dropTable.get(dropId);
        List<GameItem> items = new ArrayList<>();
        processDrop(dropData, 1, items);
        player.getInventory().addItems(items, ActionReason.OpenWorldBossChest);
        player.sendPacket(new PacketGadgetAutoPickDropInfoNotify(items));
        return true;
    }

    private void processDrop(DropTableData dropData, int count, List<GameItem> items) {
        // TODO:Not clear on the meaning of some fields,like "dropLevel".Will ignore them.
        // TODO:solve drop limits,like everydayLimit.
        if (count > 1) {
            for (int i = 0; i < count; i++) processDrop(dropData, 1, items);
            return;
        }
        if (dropData.getRandomType() == 0) {
            int weightSum = 0;
            for (var i : dropData.getDropVec()) {
                int id = i.getId();
                if (id == 0) continue;
                weightSum += i.getWeight();
            }
            if (weightSum == 0) return;
            int weight = rand.nextInt(weightSum);
            int sum = 0;
            for (var i : dropData.getDropVec()) {
                int id = i.getId();
                if (id == 0) continue;
                sum += i.getWeight();
                if (weight < sum) {
                    // win the item
                    int amount = calculateDropAmount(i) * count;
                    if (amount <= 0) break;
                    if (dropTable.containsKey(id)) {
                        processDrop(dropTable.get(id), amount, items);
                    } else {
                        boolean flag = true;
                        for (var j : items) {
                            if (j.getItemId() == id) {
                                j.setCount(j.getCount() + amount);
                                flag = false;
                                break;
                            }
                        }
                        if (flag) items.add(new GameItem(id, amount));
                    }
                    break;
                }
            }
        } else if (dropData.getRandomType() == 1) {
            for (var i : dropData.getDropVec()) {
                int id = i.getId();
                if (id == 0) continue;
                if (rand.nextInt(10000) < i.getWeight()) {
                    int amount = calculateDropAmount(i) * count;
                    if (amount <= 0) continue;
                    if (dropTable.containsKey(id)) {
                        processDrop(dropTable.get(id), amount, items);
                    } else {
                        boolean flag = true;
                        for (var j : items) {
                            if (j.getItemId() == id) {
                                j.setCount(j.getCount() + amount);
                                flag = false;
                                break;
                            }
                        }
                        if (flag) items.add(new GameItem(id, amount));
                    }
                }
            }
        }
    }

    private int calculateDropAmount(DropItemData i) {
        int amount;
        if (i.getCountRange().contains(";")) {
            String[] ranges = i.getCountRange().split(";");
            amount = rand.nextInt(Integer.parseInt(ranges[0]), Integer.parseInt(ranges[1]) + 1);
        } else if (i.getCountRange().contains(".")) {
            double expectAmount = Double.parseDouble(i.getCountRange());
            amount = (int) expectAmount;
            if (rand.nextDouble() < expectAmount - amount) amount++;
        } else {
            amount = Integer.parseInt(i.getCountRange());
        }
        return amount;
    }

    /**
     * @param share Whether other players in the scene could see the drop items.
     */
    private void dropItem(
            GameItem item, ActionReason reason, Player player, GameEntity bornFrom, boolean share) {
        DropMaterialData drop = GameData.getDropMaterialDataMap().get(item.getItemId());
        if ((drop != null && drop.isAutoPick())
                || (item.getItemData().getItemType() == ItemType.ITEM_VIRTUAL
                        && item.getItemData().getGadgetId() == 0)) {
            giveItem(item, reason, player, share);
        } else {
            // TODO:solve share problem
            player.getScene().addDropEntity(item, bornFrom, player, share);
        }
    }

    private void dropItems(
            List<GameItem> items,
            ActionReason reason,
            GameEntity bornFrom,
            Player player,
            boolean share) {
        for (var i : items) {
            dropItem(i, reason, player, bornFrom, share);
        }
    }

    private void giveItem(GameItem item, ActionReason reason, Player player, boolean share) {
        if (share) {
            for (var p : player.getScene().getPlayers()) {
                p.getInventory().addItem(item, reason);
                p.sendPacket(new PacketDropHintNotify(item.getItemId(), player.getPosition().toProto()));
            }
        } else {
            player.getInventory().addItem(item, reason);
            player.sendPacket(new PacketDropHintNotify(item.getItemId(), player.getPosition().toProto()));
        }
    }

    private void giveItems(List<GameItem> items, ActionReason reason, Player player, boolean share) {
        // don't know whether we need PacketDropHintNotify.
        if (share) {
            for (var p : player.getScene().getPlayers()) {
                p.getInventory().addItems(items, reason);
                p.sendPacket(new PacketDropHintNotify(items, player.getPosition().toProto()));
            }
        } else {
            player.getInventory().addItems(items, reason);
            player.sendPacket(new PacketDropHintNotify(items, player.getPosition().toProto()));
        }
    }
}
