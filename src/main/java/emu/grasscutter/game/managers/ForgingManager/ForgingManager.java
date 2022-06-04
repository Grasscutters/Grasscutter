package emu.grasscutter.game.managers.ForgingManager;

import java.util.HashMap;
import java.util.Map;

import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.ForgeStartReqOuterClass;
import emu.grasscutter.net.proto.ForgeQueueDataOuterClass.ForgeQueueData;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;
import emu.grasscutter.server.packet.send.PacketForgeDataNotify;
import emu.grasscutter.server.packet.send.PacketForgeFormulaDataNotify;
import emu.grasscutter.server.packet.send.PacketForgeGetQueueDataRsp;
import emu.grasscutter.server.packet.send.PacketForgeStartRsp;

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

	private Map<Integer, ForgeQueueData> determineCurrentForgeQueueData() {
		// Dummy for now.
		ForgeQueueData data = ForgeQueueData.newBuilder()
			.setQueueId(1)
			.setForgeId(11001)
			.setFinishCount(2)
			.setUnfinishCount(3)
			.setNextFinishTimestamp(0)
			.setNextFinishTimestamp(0)
			.setAvatarId(0)
			.build();

		Map<Integer, ForgeQueueData> res = new HashMap<>();
		res.put(1, data);

		return res;
	}

	public void sendForgeDataNotify() {
		// Determine the number of queues and unlocked items.
		int numQueues = this.determineNumberOfQueues();
		var unlockedItems = this.player.getUnlockedForgingBlueprints();
		var queueData = this.determineCurrentForgeQueueData();

		// Send notification.
		this.player.sendPacket(new PacketForgeDataNotify(unlockedItems, numQueues, queueData));
	}
	
	public void handleForgeGetQueueDataReq() {
		// Determine the number of queues.
		int numQueues = this.determineNumberOfQueues();
		var queueData = this.determineCurrentForgeQueueData();

		// Reply.
		this.player.sendPacket(new PacketForgeGetQueueDataRsp(Retcode.RET_SUCC, numQueues, queueData));
	}

	/**********
		Initiate forging process.
	**********/
	public void startForging(ForgeStartReqOuterClass.ForgeStartReq req) {
		// Dummy for now.
		this.player.sendPacket(new PacketForgeStartRsp(Retcode.RET_SUCC));
	}

}
