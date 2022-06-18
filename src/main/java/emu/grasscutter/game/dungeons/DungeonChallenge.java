package emu.grasscutter.game.dungeons;

import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.data.excels.DungeonData;
import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.inventory.ItemType;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.proto.GadgetInteractReqOuterClass.GadgetInteractReq;
import emu.grasscutter.scripts.constants.EventType;
import emu.grasscutter.scripts.data.SceneGroup;
import emu.grasscutter.scripts.data.ScriptArgs;
import emu.grasscutter.server.packet.send.PacketChallengeDataNotify;
import emu.grasscutter.server.packet.send.PacketDungeonChallengeBeginNotify;
import emu.grasscutter.server.packet.send.PacketDungeonChallengeFinishNotify;
import emu.grasscutter.server.packet.send.PacketGadgetAutoPickDropInfoNotify;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;

import java.util.ArrayList;
import java.util.List;

public class DungeonChallenge {
    private final Scene scene;
    private final SceneGroup group;

    private int challengeIndex;
    private int challengeId;
    private boolean success;
    private boolean progress;
    /**
     * has more challenge
     */
    private boolean stage;
    private int score;
    private int objective = 0;
    private IntSet rewardedPlayers;

    public DungeonChallenge(Scene scene, SceneGroup group, int challengeId, int challengeIndex, int objective) {
        this.scene = scene;
        this.group = group;
        this.challengeId = challengeId;
        this.challengeIndex = challengeIndex;
        this.objective = objective;
        this.setRewardedPlayers(new IntOpenHashSet());
    }

    public Scene getScene() {
        return this.scene;
    }

    public SceneGroup getGroup() {
        return this.group;
    }

    public int getChallengeIndex() {
        return this.challengeIndex;
    }

    public void setChallengeIndex(int challengeIndex) {
        this.challengeIndex = challengeIndex;
    }

    public int getChallengeId() {
        return this.challengeId;
    }

    public void setChallengeId(int challengeId) {
        this.challengeId = challengeId;
    }

    public int getObjective() {
        return this.objective;
    }

    public void setObjective(int objective) {
        this.objective = objective;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean isSuccess) {
        this.success = isSuccess;
    }

    public boolean inProgress() {
        return this.progress;
    }

    public int getScore() {
        return this.score;
    }

    public boolean isStage() {
        return this.stage;
    }

    public void setStage(boolean stage) {
        this.stage = stage;
    }

    public int getTimeLimit() {
        return 600;
    }

    public IntSet getRewardedPlayers() {
        return this.rewardedPlayers;
    }

    public void setRewardedPlayers(IntSet rewardedPlayers) {
        this.rewardedPlayers = rewardedPlayers;
    }

    public void start() {
        this.progress = true;
        this.getScene().broadcastPacket(new PacketDungeonChallengeBeginNotify(this));
    }

    public void finish() {
        this.progress = false;

        this.getScene().broadcastPacket(new PacketDungeonChallengeFinishNotify(this));

        if (this.isSuccess()) {
            // Call success script event
            this.getScene().getScriptManager().callEvent(EventType.EVENT_CHALLENGE_SUCCESS, null);

            // Settle
            this.settle();
        } else {
            this.getScene().getScriptManager().callEvent(EventType.EVENT_CHALLENGE_FAIL, null);
        }
    }

    private void settle() {
        this.getScene().getDungeonSettleObservers().forEach(o -> o.onDungeonSettle(this.getScene()));

        if (!this.stage) {
            this.getScene().getScriptManager().callEvent(EventType.EVENT_DUNGEON_SETTLE, new ScriptArgs(this.isSuccess() ? 1 : 0));
        }
    }

    public void onMonsterDie(EntityMonster entity) {
        this.score = this.getScore() + 1;

        this.getScene().broadcastPacket(new PacketChallengeDataNotify(this, 1, this.getScore()));

        if (this.getScore() >= this.getObjective() && this.progress) {
            this.setSuccess(true);
            this.finish();
        }
    }

    private List<GameItem> rollRewards() {
        List<GameItem> rewards = new ArrayList<>();

        for (ItemParamData param : this.getScene().getDungeonData().getRewardPreview().getPreviewItems()) {
            rewards.add(new GameItem(param.getId(), Math.max(param.getCount(), 1)));
        }

        return rewards;
    }

    public void getStatueDrops(Player player, GadgetInteractReq request) {
        DungeonData dungeonData = this.getScene().getDungeonData();
        int resinCost = dungeonData.getStatueCostCount() != 0 ? dungeonData.getStatueCostCount() : 20;

        if (!this.isSuccess() || dungeonData == null || dungeonData.getRewardPreview() == null || dungeonData.getRewardPreview().getPreviewItems().length == 0) {
            return;
        }

        // Already rewarded
        if (this.getRewardedPlayers().contains(player.getUid())) {
            return;
        }

        // Get rewards.
        List<GameItem> rewards = new ArrayList<>();

        if (request.getIsUseCondenseResin()) {
            // Check if condensed resin is usable here.
            // For this, we use the following logic for now:
            // The normal resin cost of the dungeon has to be 20.
            if (resinCost != 20) {
                return;
            }

            // Make sure the player has condensed resin.
            GameItem condensedResin = player.getInventory().getInventoryTab(ItemType.ITEM_MATERIAL).getItemById(220007);
            if (condensedResin == null || condensedResin.getCount() <= 0) {
                return;
            }

            // Deduct.
            player.getInventory().removeItem(condensedResin, 1);

            // Roll rewards, twice (because condensed).
            rewards.addAll(this.rollRewards());
            rewards.addAll(this.rollRewards());
        } else {
            // If the player used regular resin, try to deduct.
            // Stop if insufficient resin.
            boolean success = player.getResinManager().useResin(resinCost);
            if (!success) {
                return;
            }

            // Roll rewards.
            rewards.addAll(this.rollRewards());
        }

        // Add rewards to player and send notification.
        player.getInventory().addItems(rewards, ActionReason.DungeonStatueDrop);
        player.sendPacket(new PacketGadgetAutoPickDropInfoNotify(rewards));

        this.getRewardedPlayers().add(player.getUid());
    }
}
