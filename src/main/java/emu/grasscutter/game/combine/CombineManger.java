package emu.grasscutter.game.combine;

import emu.grasscutter.GameConstants;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.*;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.data.excels.CombineData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.net.proto.RetcodeOuterClass;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;
import emu.grasscutter.server.game.*;
import emu.grasscutter.server.packet.send.*;
import emu.grasscutter.utils.Utils;
import io.netty.util.internal.ThreadLocalRandom;
import it.unimi.dsi.fastutil.ints.*;
import java.util.*;

public class CombineManger extends BaseGameSystem {
    private static final Int2ObjectMap<List<Integer>> reliquaryDecomposeData =
            new Int2ObjectOpenHashMap<>();
    private final Int2ObjectMap<CombineBonusData> combineBonusData = new Int2ObjectOpenHashMap<>();

    public CombineManger(GameServer server) {
        super(server);

        // load combine bonus data
        try {
            DataLoader.loadList("CombineBonus.json", CombineBonusData.class)
                    .forEach(entry -> combineBonusData.put(entry.getAvatarId(), entry));
        } catch (Exception ignored) {
            Grasscutter.getLogger()
                    .error("Unable to load combine bonus data. Please place CombineBonus.json in the data folder.");
        }
    }

    public static void initialize() {
        // Read the data we need for strongbox.
        try {
            DataLoader.loadList("ReliquaryDecompose.json", ReliquaryDecomposeEntry.class)
                    .forEach(
                            entry -> {
                                reliquaryDecomposeData.put(entry.getConfigId(), entry.getItems());
                            });
            Grasscutter.getLogger()
                    .debug("Loaded {} reliquary decompose entries.", reliquaryDecomposeData.size());
        } catch (Exception ex) {
            Grasscutter.getLogger().error("Unable to load reliquary decompose data.", ex);
        }
    }

    public boolean unlockCombineDiagram(Player player, int combineId) {
        if (!player.getUnlockedCombines().add(combineId)) {
            return false; // Already unlocked
        }
        // Tell the client that this diagram is now unlocked and add the unlocked item to the player.
        player.sendPacket(new PacketCombineFormulaDataNotify(combineId));
        return true;
    }

    public CombineResult combineItem(Player player, int cid, int count, long avatarGuid) {
        // check config exist
        if (!GameData.getCombineDataMap().containsKey(cid)) {
            player.getWorld().getHost().sendPacket(new PacketCombineRsp());
            return null;
        }

        CombineData combineData = GameData.getCombineDataMap().get(cid);

        if (combineData.getPlayerLevel() > player.getLevel()) {
            return null;
        }

        // consume items
        List<ItemParamData> material = new ArrayList<>(combineData.getMaterialItems());
        material.add(new ItemParamData(202, combineData.getScoinCost()));

        boolean success = player.getInventory().payItems(material, count, ActionReason.Combine);

        // abort if not enough material
        if (!success) {
            player.sendPacket(
                    new PacketCombineRsp(RetcodeOuterClass.Retcode.RET_ITEM_COMBINE_COUNT_NOT_ENOUGH_VALUE));
        }

        // make the result
        player
                .getInventory()
                .addItem(combineData.getResultItemId(), combineData.getResultItemCount() * count);

        CombineResult result = new CombineResult();
        result.setMaterial(List.of());
        result.setResult(
                List.of(
                        new ItemParamData(combineData.getResultItemId(), combineData.getResultItemCount())));
        // lucky characters
        int luckyCount = 0;
        Avatar avatar = player.getAvatars().getAvatarByGuid(avatarGuid);
        CombineBonusData combineBonusAvatar = combineBonusData.get(avatar.getAvatarId());
        if (combineBonusAvatar != null
                && combineData.getCombineType() == combineBonusAvatar.getCombineType()) {
            double luckyChange = combineBonusAvatar.getParamVec().get(0);
            for (int i = 0; i < count; i++) {
                if (ThreadLocalRandom.current().nextDouble() <= luckyChange) {
                    luckyCount++;
                }
            }
        }

        result.setExtra(new ArrayList<ItemParamData>());
        result.setBack(new ArrayList<ItemParamData>());
        result.setRandom(new ArrayList<ItemParamData>());

        // add lucky items
        if (luckyCount > 0) {
            switch (combineBonusAvatar.getBonusType()) {
                case COMBINE_BONUS_DOUBLE -> {
                    var combineExtra = new ItemParamData(combineData.getResultItemId(), luckyCount);
                    player.getInventory().addItem(combineExtra);
                    result.getExtra().add(combineExtra);
                }
                case COMBINE_BONUS_REFUND -> {
                    if (combineData.getMaterialItems().size() == 1) {
                        var combineBack = new ItemParamData(combineData.getMaterialItems().get(0).getItemId(),
                                luckyCount);
                        player.getInventory().addItem(combineBack);
                        result.getBack().add(combineBack);
                    } else {
                        Map<Integer, Integer> mapIdCount = new HashMap<>();
                        for (int i = 0; i < luckyCount; i++) {
                            var randomId = combineData
                                    .getMaterialItems()
                                    .get(
                                            ThreadLocalRandom.current()
                                                    .nextInt(combineData.getMaterialItems().size()))
                                    .getItemId();
                            mapIdCount.put(randomId, mapIdCount.getOrDefault(randomId, 0) + 1);
                        }

                        for (var entry : mapIdCount.entrySet()) {
                            var combineBack = new ItemParamData(entry.getKey(), entry.getValue());
                            player.getInventory().addItem(combineBack);
                            result.getBack().add(combineBack);
                        }
                    }
                }
                case COMBINE_BONUS_REFUND_RANDOM -> {
                    // for yae miko, "Has a 25% chance to get 1 regional Character Talent Material
                    // (base
                    // material excluded) when crafting. The rarity is that of the base material."
                    // from wiki
                    // map of material id to region id
                    Map<Integer, Integer> itemToRegion = GameConstants.YAE_MIKO_ITEM_TO_REGION_COMBINE_BONUS;

                    // get list of material id with every region
                    Map<Integer, List<Integer>> regionToId = GameConstants.YAE_MIKO_REGION_TO_ITEM_COMBINE_BONUS;

                    // check material id in itemToRegion
                    int itemId = combineData.getMaterialItems().get(0).getItemId();
                    int rank = 0; // rank of material
                    if (itemToRegion.get(itemId) != null)
                        rank = 1;
                    if (itemToRegion.get(itemId - 1) != null) {
                        rank = 2;
                        itemId -= 1;
                    }

                    if (rank >= 1) { // if material is regional
                        // get list of material id with same region
                        List<Integer> listIdRandom = regionToId.get(itemToRegion.get(itemId));
                        // remove material id from array
                        listIdRandom.remove(Integer.valueOf(itemId));

                        HashMap<Integer, Integer> mapIdCount = new HashMap<>();
                        // pick random material from list with luckyCount
                        for (int i = 0; i < luckyCount; i++) {
                            int randomId = listIdRandom.get(ThreadLocalRandom.current().nextInt(listIdRandom.size()));
                            mapIdCount.put(randomId, mapIdCount.getOrDefault(randomId, 0) + 1);
                        }

                        // add to random list
                        for (var entry : mapIdCount.entrySet()) {
                            // if rank 2, add 1 to material id
                            var combineRandom = new ItemParamData(
                                    (rank == 2) ? entry.getKey() + 1 : entry.getKey(), entry.getValue());
                            player.getInventory().addItem(combineRandom);
                            result.getRandom().add(combineRandom);
                        }
                    }
                }
            }
        }

        return result;
    }

    public synchronized void decomposeReliquaries(
            Player player, int configId, int count, List<Long> input) {
        // Check if the configId is legal.
        List<Integer> possibleDrops = reliquaryDecomposeData.get(configId);
        if (possibleDrops == null) {
            player.sendPacket(
                    new PacketReliquaryDecomposeRsp(Retcode.RET_RELIQUARY_DECOMPOSE_PARAM_ERROR));
            return;
        }

        // Check if the number of input items matches the output count.
        if (input.size() != count * 3) {
            player.sendPacket(
                    new PacketReliquaryDecomposeRsp(Retcode.RET_RELIQUARY_DECOMPOSE_PARAM_ERROR));
            return;
        }

        // Check if all the input reliquaries actually are in the player's inventory.
        for (long guid : input) {
            if (player.getInventory().getItemByGuid(guid) == null) {
                player.sendPacket(
                        new PacketReliquaryDecomposeRsp(Retcode.RET_RELIQUARY_DECOMPOSE_PARAM_ERROR));
                return;
            }
        }

        // Delete the input reliquaries.
        for (long guid : input) {
            player.getInventory().removeItem(guid);
        }

        // Generate outoput reliquaries.
        List<Long> resultItems = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int itemId = Utils.drawRandomListElement(possibleDrops);
            GameItem newReliquary = new GameItem(itemId, 1);

            player.getInventory().addItem(newReliquary);
            resultItems.add(newReliquary.getGuid());
        }

        // Send packet.
        player.sendPacket(new PacketReliquaryDecomposeRsp(resultItems));
    }
}
