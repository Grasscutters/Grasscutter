package emu.grasscutter.game.quest;

import java.util.*;

import emu.grasscutter.server.packet.send.PacketCodexDataUpdateNotify;
import org.bson.types.ObjectId;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Indexed;
import dev.morphia.annotations.Transient;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.MainQuestData;
import emu.grasscutter.data.excels.RewardData;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.game.quest.enums.ParentQuestState;
import emu.grasscutter.game.quest.enums.QuestState;
import emu.grasscutter.net.proto.ChildQuestOuterClass.ChildQuest;
import emu.grasscutter.net.proto.ParentQuestOuterClass.ParentQuest;
import emu.grasscutter.net.proto.QuestOuterClass.Quest;
import emu.grasscutter.server.packet.send.PacketFinishedParentQuestUpdateNotify;
import emu.grasscutter.server.packet.send.PacketQuestListUpdateNotify;
import emu.grasscutter.server.packet.send.PacketQuestProgressUpdateNotify;
import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

@Entity(value = "quests", useDiscriminator = false)
public class GameMainQuest {
	@Id private ObjectId id;

	@Indexed private int ownerUid;
	@Transient private Player owner;

	private Map<Integer, GameQuest> childQuests;

	private int parentQuestId;
	private int[] questVars;
	private ParentQuestState state;
	private boolean isFinished;
    List<QuestGroupSuite> questGroupSuites;

	@Deprecated // Morphia only. Do not use.
	public GameMainQuest() {}

	public GameMainQuest(Player player, int parentQuestId) {
		this.owner = player;
		this.ownerUid = player.getUid();
		this.parentQuestId = parentQuestId;
		this.childQuests = new HashMap<>();
		this.questVars = new int[5];
		this.state = ParentQuestState.PARENT_QUEST_STATE_NONE;
        this.questGroupSuites = new ArrayList<>();
	}

	public int getParentQuestId() {
		return parentQuestId;
	}

	public int getOwnerUid() {
		return ownerUid;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player player) {
		if (player.getUid() != this.getOwnerUid()) return;
		this.owner = player;
	}

	public Map<Integer, GameQuest> getChildQuests() {
		return childQuests;
	}

	public GameQuest getChildQuestById(int id) {
		return this.getChildQuests().get(id);
	}

	public int[] getQuestVars() {
		return questVars;
	}

	public ParentQuestState getState() {
		return state;
	}

	public boolean isFinished() {
		return isFinished;
	}

    public List<QuestGroupSuite> getQuestGroupSuites() {
        return questGroupSuites;
    }

    public void finish() {
		this.isFinished = true;
		this.state = ParentQuestState.PARENT_QUEST_STATE_FINISHED;

		this.getOwner().getSession().send(new PacketFinishedParentQuestUpdateNotify(this));
		this.getOwner().getSession().send(new PacketCodexDataUpdateNotify(this));

		this.save();

		// Add rewards
		MainQuestData mainQuestData = GameData.getMainQuestDataMap().get(this.getParentQuestId());
		for (int rewardId : mainQuestData.getRewardIdList()) {
			RewardData rewardData = GameData.getRewardDataMap().get(rewardId);

			if (rewardData == null) {
				continue;
			}

			getOwner().getInventory().addItemParamDatas(rewardData.getRewardItemList(), ActionReason.QuestReward);
		}

        // handoff main quest
        if(mainQuestData.getSuggestTrackMainQuestList() != null){
            Arrays.stream(mainQuestData.getSuggestTrackMainQuestList())
                .forEach(getOwner().getQuestManager()::startMainQuest);
        }
	}

	public void save() {
		DatabaseHelper.saveQuest(this);
	}

	public ParentQuest toProto() {
		ParentQuest.Builder proto = ParentQuest.newBuilder()
				.setParentQuestId(getParentQuestId())
				.setIsFinished(isFinished())
				.setParentQuestState(getState().getValue());

		for (GameQuest quest : this.getChildQuests().values()) {
			ChildQuest childQuest = ChildQuest.newBuilder()
					.setQuestId(quest.getQuestId())
					.setState(quest.getState().getValue())
					.build();

			proto.addChildQuestList(childQuest);
		}

		if (getQuestVars() != null) {
			for (int i : getQuestVars()) {
				proto.addQuestVar(i);
			}
		}

		return proto.build();
	}
}
