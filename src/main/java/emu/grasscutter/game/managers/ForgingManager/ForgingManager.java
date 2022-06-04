package emu.grasscutter.game.managers.ForgingManager;

import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;
import emu.grasscutter.server.packet.send.PacketForgeDataNotify;
import emu.grasscutter.server.packet.send.PacketForgeFormulaDataNotify;
import emu.grasscutter.server.packet.send.PacketForgeGetQueueDataRsp;

public class ForgingManager {
	private final Player player;

	public ForgingManager(Player player) {
		this.player = player;
	}

	/**********
		Blueprint unlocking.
	**********/
	public boolean unlockForgingBlueprint(GameItem blueprintItem) {
		// Make sure this is actually a forging blueprint.
		if (!blueprintItem.getItemData().getItemUse().get(0).getUseOp().equals("ITEM_USE_UNLOCK_FORGE")) {
			return false;
		}

		// Determine the forging item we should unlock.
		int forgeId = Integer.parseInt(blueprintItem.getItemData().getItemUse().get(0).getUseParam().get(0));

		// Tell the client that this blueprint is now unlocked and add the unlocked item to the player.
		this.player.sendPacket(new PacketForgeFormulaDataNotify(forgeId));
		this.player.getUnlockedForgingBlueprints().add(forgeId);

		// Done.
		return true;
	}

	/**********
		Communicate forging information to the client.
	**********/
	private int determineNumberOfQueues() {
		int adventureRank = player.getLevel();
		return 
			(adventureRank >= 15) ? 4 
			: (adventureRank >= 10) ? 3 
			: (adventureRank >= 5) ? 2 
			: 1;	
	}

	public void sendForgeDataNotify() {
		// Determine the number of queues and unlocked items.
		int numQueues = this.determineNumberOfQueues();
		var unlockedItems = this.player.getUnlockedForgingBlueprints();

		// Send notification.
		this.player.sendPacket(new PacketForgeDataNotify(unlockedItems, numQueues));
	}
	
	public void handleForgeGetQueueDataReq() {
		// Determine the number of queues.
		int numQueues = this.determineNumberOfQueues();

		// Reply.
		this.player.sendPacket(new PacketForgeGetQueueDataRsp(Retcode.RET_SUCC, numQueues));
	}
}
