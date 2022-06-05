package emu.grasscutter.game.managers.ForgingManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.data.excels.ForgeData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.inventory.ItemType;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.ForgeStartReqOuterClass;
import emu.grasscutter.net.proto.ForgeQueueDataOuterClass.ForgeQueueData;
import emu.grasscutter.net.proto.ForgeQueueManipulateReqOuterClass.ForgeQueueManipulateReq;
import emu.grasscutter.net.proto.ForgeQueueManipulateTypeOuterClass.ForgeQueueManipulateType;
import emu.grasscutter.net.proto.ForgeStartReqOuterClass.ForgeStartReq;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;
import emu.grasscutter.server.packet.send.PacketForgeDataNotify;
import emu.grasscutter.server.packet.send.PacketForgeFormulaDataNotify;
import emu.grasscutter.server.packet.send.PacketForgeGetQueueDataRsp;
import emu.grasscutter.server.packet.send.PacketForgeQueueManipulateRsp;
import emu.grasscutter.server.packet.send.PacketForgeStartRsp;
import emu.grasscutter.utils.Utils;

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
		Map<Integer, ForgeQueueData> res = new HashMap<>();
		int currentTime = Utils.getCurrentSeconds();

		// Create queue information for all active forges.
		for (int i = 0; i < this.player.getActiveForges().size(); i++) {
			ActiveForgeData activeForge = this.player.getActiveForges().get(i);

			ForgeQueueData data = ForgeQueueData.newBuilder()
				.setQueueId(i + 1)
				.setForgeId(activeForge.getForgeId())
				.setFinishCount(activeForge.getFinishedCount(currentTime))
				.setUnfinishCount(activeForge.getUnfinishedCount(currentTime))
				.setTotalFinishTimestamp(activeForge.getTotalFinishTimestamp())
				.setNextFinishTimestamp(activeForge.getNextFinishTimestamp(currentTime))
				.setAvatarId(activeForge.getAvatarId())
				.build();

			res.put(i + 1, data);
		}

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
	public void handleForgeStartReq(ForgeStartReq req) {
		// Refuse if all queues are already full.
		if (this.player.getActiveForges().size() >= this.determineNumberOfQueues()) {
			this.player.sendPacket(new PacketForgeStartRsp(Retcode.RET_FORGE_QUEUE_FULL));
			return;
		}

		// Get the required forging information for the target item.
		if (!GameData.getForgeDataMap().containsKey(req.getForgeId())) {
			this.player.sendPacket(new PacketForgeStartRsp(Retcode.RET_FAIL)); //ToDo: Probably the wrong return code.
			return;
		}

		ForgeData forgeData = GameData.getForgeDataMap().get(req.getForgeId());
		
		// Check if we have enough of each material.
		for (var material : forgeData.getMaterialItems()) {
			if (material.getItemId() == 0) {
				continue;
			}

			int currentCount = this.player.getInventory().getInventoryTab(ItemType.ITEM_MATERIAL).getItemById(material.getItemId()).getCount();

			if (currentCount < material.getCount() * req.getForgeCount()) {
				this.player.sendPacket(new PacketForgeStartRsp(Retcode.RET_FORGE_POINT_NOT_ENOUGH)); //ToDo: Probably the wrong return code.
				return;
			}
		}

		// Check if we have enough Mora.
		if (this.player.getMora() < forgeData.getScoinCost() * req.getForgeCount()) {
			this.player.sendPacket(new PacketForgeStartRsp(Retcode.RET_FORGE_POINT_NOT_ENOUGH)); //ToDo: Probably the wrong return code.
			return;
		}

		// Consume material and Mora.
		for (var material : forgeData.getMaterialItems()) {
			if (material.getItemId() == 0) {
				continue;
			}

			GameItem item = this.player.getInventory().getInventoryTab(ItemType.ITEM_MATERIAL).getItemById(material.getItemId());
			this.player.getInventory().removeItem(item, material.getCount() * req.getForgeCount());
		}

		this.player.setMora(this.player.getMora() - forgeData.getScoinCost() * req.getForgeCount());

		// Create and add active forge.
		ActiveForgeData activeForge = new ActiveForgeData();
		activeForge.setForgeId(req.getForgeId());
		activeForge.setAvatarId(req.getAvatarId());
		activeForge.setCount(req.getForgeCount());
		activeForge.setStartTime(Utils.getCurrentSeconds());
		activeForge.setForgeTime(forgeData.getForgeTime());

		this.player.getActiveForges().add(activeForge);

		// Done.
		this.player.sendPacket(new PacketForgeStartRsp(Retcode.RET_SUCC));
		this.sendForgeDataNotify();
	}

	/**********
		Forge queue manipulation (obtaining results and cancelling forges).
	**********/
	private void obtainItems(int queueId) {
		// Determin how many items are finished.
		int currentTime = Utils.getCurrentSeconds();
		ActiveForgeData forge = this.player.getActiveForges().get(queueId - 1);

		int finished = forge.getFinishedCount(currentTime);
		int unfinished = forge.getUnfinishedCount(currentTime);

		// Sanity check: Are any items finished?
		if (finished <= 0) {
			return;
		}

		// Give finished items to the player.
		ForgeData data = GameData.getForgeDataMap().get(forge.getForgeId());
		ItemData resultItemData = GameData.getItemDataMap().get(data.getResultItemId());

		GameItem addItem = new GameItem(resultItemData, data.getResultItemCount() * finished);
		this.player.getInventory().addItem(addItem);

		// Replace active forge with a new one for the unfinished items, if there are any.
		if (unfinished > 0) {
			ActiveForgeData remainingForge = new ActiveForgeData();

			remainingForge.setForgeId(forge.getForgeId());
			remainingForge.setAvatarId(forge.getAvatarId());
			remainingForge.setCount(unfinished);
			remainingForge.setForgeTime(forge.getForgeTime());

			// We simple restart the forge. This will increase the time, but is easier for now.
			// ToDo: Make this more accurate.
			remainingForge.setStartTime(currentTime);

			this.player.getActiveForges().set(queueId - 1, remainingForge);
		}
		// Otherwise, completely remove it.
		else {
			this.player.getActiveForges().remove(queueId - 1);
		}

		// Send response.
		this.player.sendPacket(new PacketForgeQueueManipulateRsp(Retcode.RET_SUCC, ForgeQueueManipulateType.FORGE_QUEUE_MANIPULATE_TYPE_RECEIVE_OUTPUT, List.of(addItem), List.of(), List.of()));
		this.sendForgeDataNotify();
	}

	private void cancelForge(int queueId) {
		
	}

	public void handleForgeQueueManipulateReq(ForgeQueueManipulateReq req) {
		// Get info from the request.
		int queueId = req.getForgeQueueId();
		var manipulateType = req.getManipulateType();
		
		// Handle according to the manipulation type.
		switch (manipulateType) {
			case FORGE_QUEUE_MANIPULATE_TYPE_RECEIVE_OUTPUT:
				this.obtainItems(queueId);
				break;
			case FORGE_QUEUE_MANIPULATE_TYPE_STOP_FORGE:
				this.cancelForge(queueId);
				break;
			default:
				break; //Should never happen.
		}
	}
}
