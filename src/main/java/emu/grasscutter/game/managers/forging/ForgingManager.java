package emu.grasscutter.game.managers.forging;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.data.excels.*;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.*;
import emu.grasscutter.game.props.*;
import emu.grasscutter.net.proto.ForgeQueueDataOuterClass.ForgeQueueData;
import emu.grasscutter.net.proto.ForgeQueueManipulateReqOuterClass.ForgeQueueManipulateReq;
import emu.grasscutter.net.proto.ForgeQueueManipulateTypeOuterClass.ForgeQueueManipulateType;
import emu.grasscutter.net.proto.ForgeStartReqOuterClass.ForgeStartReq;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;
import emu.grasscutter.server.event.player.PlayerForgeItemEvent;
import emu.grasscutter.server.packet.send.*;
import emu.grasscutter.utils.Utils;
import java.util.*;

public final class ForgingManager extends BasePlayerManager {

    public ForgingManager(Player player) {
        super(player);
    }

    /**********
     * Blueprint unlocking.
     **********/
    public boolean unlockForgingBlueprint(int id) {
        // Tell the client that this blueprint is now unlocked and add the unlocked item to the player.
        if (!this.player.getUnlockedForgingBlueprints().add(id)) {
            return false; // Already unlocked
        }
        this.player.sendPacket(new PacketForgeFormulaDataNotify(id));
        return true;
    }

    /**********
     * Communicate forging information to the client.
     **********/
    private synchronized int determineNumberOfQueues() {
        int adventureRank = player.getLevel();
        return (adventureRank >= 15) ? 4 : (adventureRank >= 10) ? 3 : (adventureRank >= 5) ? 2 : 1;
    }

    private synchronized Map<Integer, ForgeQueueData> determineCurrentForgeQueueData() {
        Map<Integer, ForgeQueueData> res = new HashMap<>();
        int currentTime = Utils.getCurrentSeconds();

        // Create queue information for all active forges.
        for (int i = 0; i < this.player.getActiveForges().size(); i++) {
            ActiveForgeData activeForge = this.player.getActiveForges().get(i);

            ForgeQueueData data =
                    ForgeQueueData.newBuilder()
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

    public synchronized void sendForgeDataNotify() {
        // Determine the number of queues and unlocked items.
        int numQueues = this.determineNumberOfQueues();
        var unlockedItems = this.player.getUnlockedForgingBlueprints();
        var queueData = this.determineCurrentForgeQueueData();

        // Send notification.
        this.player.sendPacket(new PacketForgeDataNotify(unlockedItems, numQueues, queueData));
    }

    public synchronized void handleForgeGetQueueDataReq() {
        // Determine the number of queues.
        int numQueues = this.determineNumberOfQueues();
        var queueData = this.determineCurrentForgeQueueData();

        // Reply.
        this.player.sendPacket(new PacketForgeGetQueueDataRsp(Retcode.RET_SUCC, numQueues, queueData));
    }

    /**********
     * Initiate forging process.
     **********/
    private synchronized void sendForgeQueueDataNotify() {
        var queueData = this.determineCurrentForgeQueueData();
        this.player.sendPacket(new PacketForgeQueueDataNotify(queueData, List.of()));
    }

    private synchronized void sendForgeQueueDataNotify(boolean hasRemoved) {
        var queueData = this.determineCurrentForgeQueueData();

        if (hasRemoved) {
            this.player.sendPacket(new PacketForgeQueueDataNotify(Map.of(), List.of(1, 2, 3, 4)));
        }

        this.player.sendPacket(new PacketForgeQueueDataNotify(queueData, List.of()));
    }

    public synchronized void handleForgeStartReq(ForgeStartReq req) {
        // Refuse if all queues are already full.
        if (this.player.getActiveForges().size() >= this.determineNumberOfQueues()) {
            this.player.sendPacket(new PacketForgeStartRsp(Retcode.RET_FORGE_QUEUE_FULL));
            return;
        }

        // Get the required forging information for the target item.
        if (!GameData.getForgeDataMap().containsKey(req.getForgeId())) {
            this.player.sendPacket(
                    new PacketForgeStartRsp(Retcode.RET_FAIL)); // ToDo: Probably the wrong return code.
            return;
        }

        ForgeData forgeData = GameData.getForgeDataMap().get(req.getForgeId());

        // Check if the player has sufficient forge points.
        int requiredPoints = forgeData.getForgePoint() * req.getForgeCount();
        if (requiredPoints > this.player.getForgePoints()) {
            this.player.sendPacket(new PacketForgeStartRsp(Retcode.RET_FORGE_POINT_NOT_ENOUGH));
            return;
        }

        // Check if we have enough of each material and consume.
        List<ItemParamData> material = new ArrayList<>(forgeData.getMaterialItems());
        material.add(new ItemParamData(202, forgeData.getScoinCost()));

        boolean success =
                player.getInventory().payItems(material, req.getForgeCount(), ActionReason.ForgeCost);

        if (!success) {
            // TODO:I'm not sure this one is correct.
            this.player.sendPacket(
                    new PacketForgeStartRsp(
                            Retcode.RET_ITEM_COUNT_NOT_ENOUGH)); // ToDo: Probably the wrong return code.
        }

        // Consume forge points.
        this.player.setForgePoints(this.player.getForgePoints() - requiredPoints);

        // Create and add active forge.
        ActiveForgeData activeForge = new ActiveForgeData();
        activeForge.setForgeId(req.getForgeId());
        activeForge.setAvatarId(req.getAvatarId());
        activeForge.setCount(req.getForgeCount());
        activeForge.setStartTime(Utils.getCurrentSeconds());
        activeForge.setForgeTime(forgeData.getForgeTime());

        this.player.getActiveForges().add(activeForge);

        // Done.
        this.sendForgeQueueDataNotify();
        this.player.sendPacket(new PacketForgeStartRsp(Retcode.RET_SUCC));
    }

    /**********
     * Forge queue manipulation (obtaining results and cancelling forges).
     **********/
    private synchronized void obtainItems(int queueId) {
        // Determine how many items are finished.
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

        int resultId = data.getResultItemId() > 0 ? data.getResultItemId() : data.getShowItemId();
        ItemData resultItemData = GameData.getItemDataMap().get(resultId);
        GameItem addItem = new GameItem(resultItemData, data.getResultItemCount() * finished);

        // Call the PlayerForgeItemEvent.
        var event = new PlayerForgeItemEvent(this.player, addItem);
        if (!event.call()) return;

        addItem = event.getItemForged();
        this.player.getInventory().addItem(addItem);

        // Battle pass trigger handler
        this.player
                .getBattlePassManager()
                .triggerMission(WatcherTriggerType.TRIGGER_DO_FORGE, 0, finished);

        // Replace active forge with a new one for the unfinished items, if there are any.
        if (unfinished > 0) {
            ActiveForgeData remainingForge = new ActiveForgeData();

            remainingForge.setForgeId(forge.getForgeId());
            remainingForge.setAvatarId(forge.getAvatarId());
            remainingForge.setCount(unfinished);
            remainingForge.setForgeTime(forge.getForgeTime());
            remainingForge.setStartTime(forge.getStartTime() + finished * forge.getForgeTime());

            this.player.getActiveForges().set(queueId - 1, remainingForge);
            this.sendForgeQueueDataNotify();
        }
        // Otherwise, completely remove it.
        else {
            this.player.getActiveForges().remove(queueId - 1);
            // this.sendForgeQueueDataNotify(queueId);
            this.sendForgeQueueDataNotify(true);
        }

        // Send response.
        this.player.sendPacket(
                new PacketForgeQueueManipulateRsp(
                        Retcode.RET_SUCC,
                        ForgeQueueManipulateType.FORGE_QUEUE_MANIPULATE_TYPE_RECEIVE_OUTPUT,
                        List.of(addItem),
                        List.of(),
                        List.of()));
    }

    private synchronized void cancelForge(int queueId) {
        // Make sure there are no unfinished items.
        int currentTime = Utils.getCurrentSeconds();
        ActiveForgeData forge = this.player.getActiveForges().get(queueId - 1);

        if (forge.getFinishedCount(currentTime) > 0) {
            return;
        }

        // Return material items to the player.
        ForgeData data = GameData.getForgeDataMap().get(forge.getForgeId());

        var returnItems = new ArrayList<GameItem>();
        for (var material : data.getMaterialItems()) {
            if (material.getItemId() == 0) {
                continue;
            }

            ItemData resultItemData = GameData.getItemDataMap().get(material.getItemId());
            GameItem returnItem =
                    new GameItem(resultItemData, material.getItemCount() * forge.getCount());

            this.player.getInventory().addItem(returnItem);
            returnItems.add(returnItem);
        }

        // Return Mora to the player.
        this.player.setMora(this.player.getMora() + data.getScoinCost() * forge.getCount());

        ItemData moraItem = GameData.getItemDataMap().get(202);
        GameItem returnMora = new GameItem(moraItem, data.getScoinCost() * forge.getCount());
        returnItems.add(returnMora);

        // Return forge points to the player.
        int requiredPoints = data.getForgePoint() * forge.getCount();
        int newPoints = Math.min(this.player.getForgePoints() + requiredPoints, 300_000);

        this.player.setForgePoints(newPoints);

        // Remove the forge queue.
        this.player.getActiveForges().remove(queueId - 1);
        this.sendForgeQueueDataNotify(true);

        // Send response.
        this.player.sendPacket(
                new PacketForgeQueueManipulateRsp(
                        Retcode.RET_SUCC,
                        ForgeQueueManipulateType.FORGE_QUEUE_MANIPULATE_TYPE_STOP_FORGE,
                        List.of(),
                        returnItems,
                        List.of()));
    }

    public synchronized void handleForgeQueueManipulateReq(ForgeQueueManipulateReq req) {
        // Get info from the request.
        int queueId = req.getForgeQueueId();
        var manipulateType = req.getManipulateType();

        // Handle according to the manipulation type.
        switch (manipulateType) {
            case FORGE_QUEUE_MANIPULATE_TYPE_RECEIVE_OUTPUT -> this.obtainItems(queueId);
            case FORGE_QUEUE_MANIPULATE_TYPE_STOP_FORGE -> this.cancelForge(queueId);
            default -> {} // Should never happen.
        }
    }

    /**********
     * Periodic forging updates.
     **********/
    public synchronized void sendPlayerForgingUpdate() {
        int currentTime = Utils.getCurrentSeconds();

        // Determine if sending an update is necessary.
        // We only send an update if there are forges in the forge queue
        // that have changed since the last notification.
        if (this.player.getActiveForges().size() <= 0) {
            return;
        }

        boolean hasChanges =
                this.player.getActiveForges().stream().anyMatch(forge -> forge.updateChanged(currentTime));

        if (!hasChanges) {
            return;
        }

        // Send notification.
        this.sendForgeQueueDataNotify();

        // Reset changed flags.
        this.player.getActiveForges().stream().forEach(forge -> forge.setChanged(false));
    }
}
