package emu.grasscutter.game.dungeons;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.DataLoader;
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
import emu.grasscutter.utils.Utils;
import io.netty.util.internal.ThreadLocalRandom;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.gson.reflect.TypeToken;

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

	private final static Int2ObjectMap<List<DungeonDropEntry>> dungeonDropData = new Int2ObjectOpenHashMap<>();

	public static void initialize() {
		// Read the data we need for dungeon rewards drops.
		try (Reader fileReader = new InputStreamReader(DataLoader.load("DungeonDrop.json"))) {
			List<DungeonDrop> dungeonDropList = Grasscutter.getGsonFactory().fromJson(fileReader, TypeToken.getParameterized(Collection.class, DungeonDrop.class).getType());

			for (DungeonDrop entry : dungeonDropList) {
				dungeonDropData.put(entry.getDungeonId(), entry.getDrops());
			}

			Grasscutter.getLogger().info("Dungeon drop data successfully loaded.");
		}
		catch (Exception ex) {
			Grasscutter.getLogger().error("Unable to load dungeon drop data.", ex);
		}
	}

	public DungeonChallenge(Scene scene, SceneGroup group, int challengeId, int challengeIndex, int objective) {
		this.scene = scene;
		this.group = group;
		this.challengeId = challengeId;
		this.challengeIndex = challengeIndex;
		this.objective = objective;
		this.setRewardedPlayers(new IntOpenHashSet());
	}

	public Scene getScene() {
		return scene;
	}

	public SceneGroup getGroup() {
		return group;
	}
	
	public int getChallengeIndex() {
		return challengeIndex;
	}

	public void setChallengeIndex(int challengeIndex) {
		this.challengeIndex = challengeIndex;
	}

	public int getChallengeId() {
		return challengeId;
	}

	public void setChallengeId(int challengeId) {
		this.challengeId = challengeId;
	}

	public int getObjective() {
		return objective;
	}

	public void setObjective(int objective) {
		this.objective = objective;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean isSuccess) {
		this.success = isSuccess;
	}
	
	public boolean inProgress() {
		return progress;
	}

	public int getScore() {
		return score;
	}

	public boolean isStage() {
		return stage;
	}

	public void setStage(boolean stage) {
		this.stage = stage;
	}

	public int getTimeLimit() {
		return 600;
	}

	public IntSet getRewardedPlayers() {
		return rewardedPlayers;
	}

	public void setRewardedPlayers(IntSet rewardedPlayers) {
		this.rewardedPlayers = rewardedPlayers;
	}

	public void start() {
		this.progress = true;
		getScene().broadcastPacket(new PacketDungeonChallengeBeginNotify(this));
	}
	
	public void finish() {
		this.progress = false;
		
		getScene().broadcastPacket(new PacketDungeonChallengeFinishNotify(this));
		
		if (this.isSuccess()) {
			// Call success script event
			this.getScene().getScriptManager().callEvent(EventType.EVENT_CHALLENGE_SUCCESS, null);

			// Settle
			settle();
		} else {
			this.getScene().getScriptManager().callEvent(EventType.EVENT_CHALLENGE_FAIL, null);
		}
	}
	
	private void settle() {
		getScene().getDungeonSettleObservers().forEach(o -> o.onDungeonSettle(getScene()));

		if(!stage){
			getScene().getScriptManager().callEvent(EventType.EVENT_DUNGEON_SETTLE, new ScriptArgs(this.isSuccess() ? 1 : 0));
		}
	}

	public void onMonsterDie(EntityMonster entity) {
		score = getScore() + 1;
		
		getScene().broadcastPacket(new PacketChallengeDataNotify(this, 1, getScore()));
		
		if (getScore() >= getObjective() && this.progress) {
			this.setSuccess(true);
			finish();
		}
	}
	
	private List<GameItem> rollRewards(boolean useCondensed) {
		List<GameItem> rewards = new ArrayList<>();
		int dungeonId = this.getScene().getDungeonData().getId();
		Grasscutter.getLogger().info("Rolling rewards for scene {}, dungeon {}.", this.getScene().getId(), this.getScene().getDungeonData().getId());

		// If we have specific drop data for this dungeon, we use it.
		if (dungeonDropData.containsKey(dungeonId)) {
			List<DungeonDropEntry> dropEntries = dungeonDropData.get(dungeonId);

			// Roll for each drop group.
			for (var entry : dropEntries) {
				// Determine the number of drops we get for this entry.
				int start = entry.getCounts().get(0);
				int end = entry.getCounts().get(entry.getCounts().size() - 1);
				var candidateAmounts = IntStream.range(start, end + 1).boxed().collect(Collectors.toList());

				int amount = Utils.drawRandomListElement(candidateAmounts, entry.getProbabilities());

				if (useCondensed) {
					amount += Utils.drawRandomListElement(candidateAmounts, entry.getProbabilities());
				}

				// Roll items for this group.
				// Here, we have to handle stacking, or the client will not display results correctly.
				// For now, we use the following logic: If the possible drop item are a list of multiple items,
				// we roll them separately. If not, we stack them. This should work out in practice, at least
				// for the currently existing set of dungeons.
				if (entry.getItems().size() == 1) {
					rewards.add(new GameItem(entry.getItems().get(0), amount));
				}
				else {
					for (int i = 0; i < amount; i++) {
						int itemIndex = ThreadLocalRandom.current().nextInt(0, entry.getItems().size());
						int itemId = entry.getItems().get(itemIndex);

						rewards.add(new GameItem(itemId, 1));
					}
				}
			}
		}
		// Otherwise, we fall back to the preview data.
		else {
			for (ItemParamData param : getScene().getDungeonData().getRewardPreview().getPreviewItems()) {
				rewards.add(new GameItem(param.getId(), Math.max(param.getCount(), 1)));
			}
		}	

		return rewards;
	}

	public void getStatueDrops(Player player, GadgetInteractReq request) {
		DungeonData dungeonData = getScene().getDungeonData();
		int resinCost = dungeonData.getStatueCostCount() != 0 ? dungeonData.getStatueCostCount() : 20;

		if (!isSuccess() || dungeonData == null || dungeonData.getRewardPreview() == null || dungeonData.getRewardPreview().getPreviewItems().length == 0) {
			return;
		}
		
		// Already rewarded
		if (getRewardedPlayers().contains(player.getUid())) {
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

			// Roll rewards.
			rewards.addAll(this.rollRewards(true));
		}
		else {
			// If the player used regular resin, try to deduct.
			// Stop if insufficient resin.
			boolean success = player.getResinManager().useResin(resinCost);
			if (!success) {
				return;
			}

			// Roll rewards.
			rewards.addAll(this.rollRewards(false));
		}
		
		// Add rewards to player and send notification.
		player.getInventory().addItems(rewards, ActionReason.DungeonStatueDrop);
		player.sendPacket(new PacketGadgetAutoPickDropInfoNotify(rewards));
		
		getRewardedPlayers().add(player.getUid());
	}
}
