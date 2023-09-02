package emu.grasscutter.game.combine;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.*;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.data.excels.CombineData;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.net.proto.RetcodeOuterClass;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;
import emu.grasscutter.server.game.*;
import emu.grasscutter.server.packet.send.*;
import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.*;
import java.util.*;

public class CombineManger extends BaseGameSystem {
    private static final Int2ObjectMap<List<Integer>> reliquaryDecomposeData =
            new Int2ObjectOpenHashMap<>();

    public CombineManger(GameServer server) {
        super(server);
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

    public CombineResult combineItem(Player player, int cid, int count) {
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
                        new ItemParamData(
                                combineData.getResultItemId(), combineData.getResultItemCount() * count)));
        // TODO lucky characters
        result.setExtra(List.of());
        result.setBack(List.of());

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
