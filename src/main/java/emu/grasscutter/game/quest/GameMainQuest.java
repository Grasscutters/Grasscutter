package emu.grasscutter.game.quest;

import java.util.*;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.server.packet.send.PacketCodexDataUpdateNotify;
import lombok.Getter;
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

	@Indexed @Getter private int ownerUid;
	@Transient @Getter private Player owner;

	@Getter private Map<Integer, GameQuest> childQuests;

	@Getter private int parentQuestId;
	@Getter private int[] questVars;
	@Getter private ParentQuestState state;
	@Getter private boolean isFinished;
    @Getter List<QuestGroupSuite> questGroupSuites;

	@Deprecated // Morphia only. Do not use.
	public GameMainQuest() {}

	public GameMainQuest(Player player, int parentQuestId) {
		this.owner = player;
		this.ownerUid = player.getUid();
		this.parentQuestId = parentQuestId;
		this.childQuests = new HashMap<>();
        //official server always has a list of 5 questVars, with default value 0
		this.questVars = new int[] {0,0,0,0,0};
		this.state = ParentQuestState.PARENT_QUEST_STATE_NONE;
        this.questGroupSuites = new ArrayList<>();
	}

	public void setOwner(Player player) {
		if (player.getUid() != this.getOwnerUid()) return;
		this.owner = player;
	}

    public void setQuestVar(int i, int value) {
        int previousValue = this.questVars[i];
        this.questVars[i] = value;
        Grasscutter.getLogger().debug("questVar {} value changed from {} to {}", i, previousValue, value);
    }

    public void incQuestVar(int i, int inc) {
        int previousValue = this.questVars[i];
        this.questVars[i] += inc;
        Grasscutter.getLogger().debug("questVar {} value incremented from {} to {}", i, previousValue, previousValue + inc);
    }

    public void decQuestVar(int i, int dec) {
        int previousValue = this.questVars[i];
        this.questVars[i] -= dec;
        Grasscutter.getLogger().debug("questVar {} value decremented from {} to {}", i, previousValue, previousValue - dec);
    }

	public GameQuest getChildQuestById(int id) {
		return this.getChildQuests().get(id);
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

        for (int i : getQuestVars()) {
            proto.addQuestVar(i);
        }

		return proto.build();
	}
}
