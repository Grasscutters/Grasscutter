package emu.grasscutter.game.combine;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.data.excels.CombineData;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.inventory.ItemType;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.RetcodeOuterClass;
import emu.grasscutter.server.game.GameServer;
import emu.grasscutter.server.packet.send.PacketCombineFormulaDataNotify;
import emu.grasscutter.server.packet.send.PacketCombineRsp;
import it.unimi.dsi.fastutil.Pair;

import java.util.List;

public class CombineManger {
    private final GameServer gameServer;

    public GameServer getGameServer() {
        return gameServer;
    }

    public CombineManger(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    public boolean unlockCombineDiagram(Player player, GameItem diagramItem) {
		// Make sure this is actually a diagram.
		if (!diagramItem.getItemData().getItemUse().get(0).getUseOp().equals("ITEM_USE_UNLOCK_COMBINE")) {
			return false;
		}

		// Determine the combine item we should unlock.
		int combineId = Integer.parseInt(diagramItem.getItemData().getItemUse().get(0).getUseParam().get(0));

		// Remove the diagram from the player's inventory.
		// We need to do this here, before sending CombineFormulaDataNotify, or the the combine UI won't correctly
		// update when unlocking the diagram.
		player.getInventory().removeItem(diagramItem, 1);

		// Tell the client that this diagram is now unlocked and add the unlocked item to the player.
		player.getUnlockedCombines().add(combineId);
		player.sendPacket(new PacketCombineFormulaDataNotify(combineId));

		return true;
	}

    public CombineResult combineItem(Player player, int cid, int count){
        // check config exist
        if(!GameData.getCombineDataMap().containsKey(cid)){
            player.getWorld().getHost().sendPacket(new PacketCombineRsp());
            return null;
        }

        CombineData combineData = GameData.getCombineDataMap().get(cid);

        if(combineData.getPlayerLevel() > player.getLevel()){
            return null;
        }
        // check enough
        var enough = combineData.getMaterialItems().stream()
                .filter(item -> player.getInventory()
                        .getInventoryTab(ItemType.ITEM_MATERIAL)
                        .getItemById(item.getId())
                        .getCount() < item.getCount() * count
                )
                .findAny()
                .isEmpty();

        // if not enough
        if(!enough){
            player.getWorld().getHost().sendPacket(
                    new PacketCombineRsp(RetcodeOuterClass.Retcode.RET_ITEM_COMBINE_COUNT_NOT_ENOUGH_VALUE)
            );
            return null;
        }
        if (player.getMora() >= combineData.getScoinCost()) {
            player.setMora(player.getMora() - combineData.getScoinCost() * count);
        } else {
            return null;
        }
        // try to remove materials
        combineData.getMaterialItems().stream()
                .map(item -> Pair.of(player.getInventory()
                                .getInventoryTab(ItemType.ITEM_MATERIAL)
                                .getItemById(item.getId())
                        ,item.getCount() * count)
                )
                .forEach(item -> player.getInventory().removeItem(item.first(), item.second()));

        // make the result
        player.getInventory().addItem(combineData.getResultItemId(),
                combineData.getResultItemCount() * count);

        CombineResult result = new CombineResult();
        result.setMaterial(List.of());
        result.setResult(List.of(new ItemParamData(combineData.getResultItemId(),
                combineData.getResultItemCount() * count)));
        // TODO lucky characters
        result.setExtra(List.of());
        result.setBack(List.of());

        return result;
    }

}
