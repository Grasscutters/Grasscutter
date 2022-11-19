package emu.grasscutter.game.drop;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.DataLoader;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.common.DropItemData;
import emu.grasscutter.data.excels.DropMaterialData;
import emu.grasscutter.data.excels.DropTableData;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.scripts.data.SceneBossChest;
import emu.grasscutter.server.game.BaseGameSystem;
import emu.grasscutter.server.game.GameServer;
import emu.grasscutter.server.packet.send.PacketDropHintNotify;
import emu.grasscutter.server.packet.send.PacketGadgetAutoPickDropInfoNotify;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

import java.util.*;

public class DropSystem extends BaseGameSystem {
    private final Int2ObjectMap<DropTableData> dropTable;
    private final Map<String, List<ChestDropData>> chestReward;
    private final Random rand;

    public DropSystem(GameServer server) {
        super(server);
        rand = new Random();
        dropTable = GameData.getDropTableDataMap();
        chestReward = new HashMap<>();
        try {
            List<ChestDropData> dataList = DataLoader.loadList("ChestDrop.json", ChestDropData.class);
            for (var i : dataList) {
                if (!chestReward.containsKey(i.getIndex())) {
                    chestReward.put(i.getIndex(), new ArrayList<>());
                }
                chestReward.get(i.getIndex()).add(i);
            }
        } catch (Exception e) {
            Grasscutter.getLogger().error("Unable to load chest drop data.Please place ChestDrop.json in data folder.");
        }

    }

    private int queryDropData(String dropTag, int level) {
        if (!chestReward.containsKey(dropTag)) return 0;
        var rewardList = chestReward.get(dropTag);
        ChestDropData dropData = null;
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

    public boolean handleChestDrop(int chestDropId, int dropCount, GameEntity bornFrom) {
        Grasscutter.getLogger().info("ChestDrop:chest_drop_id={},drop_count={}", chestDropId, dropCount);
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
        Grasscutter.getLogger().info("ChestDrop:drop_tag={},level={}", dropTag, level);
        int dropId = queryDropData(dropTag, level);
        if (dropId == 0) return false;
        return handleChestDrop(dropId, 1, bornFrom);
    }

    public boolean handleBossChestDrop(String dropTag, int level, SceneBossChest bossChest, Player player) {
        int dropId = queryDropData(dropTag, level);
        if (!dropTable.containsKey(dropId)) return false;
        var dropData = dropTable.get(dropId);
        List<GameItem> items = new ArrayList<>();
        processDrop(dropData, 1, items);
        //TODO:test if there still need some packets.
        player.getInventory().addItems(items, ActionReason.OpenWorldBossChest);
        player.sendPacket(new PacketGadgetAutoPickDropInfoNotify(items));
        return true;
    }

    private void processDrop(DropTableData dropData, int count, List<GameItem> items) {
        //TODO:Not clear on the meaning of some fields,like "dropLevel".Will ignore them.
        //TODO:solve drop limits,like everydayLimit.

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
                    //win the item
                    int amount = calculateDropAmount(i) * count;
                    if (amount > 0) {
                        if (dropTable.containsKey(id)) {
                            processDrop(dropTable.get(id), amount, items);
                        } else {
                            items.add(new GameItem(id, amount));
                        }
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
                    if (amount > 0) {
                        if (dropTable.containsKey(id)) {
                            processDrop(dropTable.get(id), amount, items);
                        } else {
                            items.add(new GameItem(id, amount));
                        }
                    }
                }
            }
        }
    }

    private int calculateDropAmount(DropItemData i) {
        int amount = 0;
        if (i.getCountRange().contains(";")) {
            String[] ranges = i.getCountRange().split(";");
            amount = rand.nextInt(Integer.parseInt(ranges[0]), Integer.parseInt(ranges[1]) + 1);
        } else if (i.getCountRange().contains(".")) {
            double expectAmount = Double.parseDouble(i.getCountRange());
            int chance = (int) expectAmount + 1;
            for (int k = 0; k < chance; k++) {
                if (rand.nextDouble() < expectAmount / chance) amount++;
            }
        } else {
            amount = Integer.parseInt(i.getCountRange());
        }
        return amount;
    }

    /**
     * @param share Whether other players in the scene could see the drop items.
     */
    private void dropItem(GameItem item, ActionReason reason, Player player, GameEntity bornFrom, boolean share) {
        DropMaterialData drop = GameData.getDropMaterialDataMap().get(item.getItemId());
        if ((drop != null && drop.isAutoPick()) || item.getItemId() == 102) {
            giveItem(item, reason, player, share);
        } else {
            //TODO:solve share problem
            player.getScene().addDropEntity(item, bornFrom, player, share);
        }
    }

    private void dropItems(List<GameItem> items, ActionReason reason, GameEntity bornFrom, Player player, boolean share) {
        for (var i : items) {
            dropItem(i,reason,player,bornFrom,share);
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
        //don't know whether we need PacketDropHintNotify.
        if (share) {
            for (var p : player.getScene().getPlayers()) {
                p.getInventory().addItems(items, reason);
            }
        } else {
            player.getInventory().addItems(items, reason);
        }
    }
}
