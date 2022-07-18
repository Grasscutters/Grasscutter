package emu.grasscutter.game.quest;

import java.util.*;

import dev.morphia.query.Query;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.excels.QuestData;
import emu.grasscutter.database.DatabaseManager;
import emu.grasscutter.game.quest.enums.LogicType;
import emu.grasscutter.game.quest.enums.QuestTrigger;
import emu.grasscutter.server.packet.send.PacketCodexDataUpdateNotify;
import emu.grasscutter.utils.ConfigContainer;
import jdk.jshell.spi.ExecutionControl;
import lombok.Getter;
import org.bson.types.ObjectId;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Indexed;
import dev.morphia.annotations.Transient;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.MainQuestData;
import emu.grasscutter.data.binout.MainQuestData.*;
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

@Entity(value = "quests", useDiscriminator = false)
public class GameMainQuest {
	@Id private ObjectId id;
	@Indexed @Getter private int ownerUid;
	@Transient @Getter private Player owner;
    @Transient @Getter private QuestManager questManager;
    @Transient @Getter private ServerQuestHandler serverQuestHandler;
	@Getter private Map<Integer, GameQuest> childQuests;
	@Getter private int parentQuestId;
	@Getter private int[] questVars;
    //QuestUpdateQuestVarReq is sent in two stages...
    @Getter private List<Integer> questVarsUpdate;
	@Getter private ParentQuestState state;
	@Getter private boolean isFinished;
    @Getter List<QuestGroupSuite> questGroupSuites;

    @Getter int[] suggestTrackMainQuestList;
    @Getter private Map<Integer,TalkData> talks;


	@Deprecated // Morphia only. Do not use.
	public GameMainQuest() {}

	public GameMainQuest(Player player, int parentQuestId) {
		this.owner = player;
		this.ownerUid = player.getUid();
        this.questManager = player.getQuestManager();
        this.serverQuestHandler = player.getServer().getQuestHandler();
		this.parentQuestId = parentQuestId;
		this.childQuests = new HashMap<>();
        this.talks = new HashMap<>();
        //official server always has a list of 5 questVars, with default value 0
		this.questVars = new int[] {0,0,0,0,0};
		this.state = ParentQuestState.PARENT_QUEST_STATE_NONE;
        this.questGroupSuites = new ArrayList<>();
        addAllChildQuests();
	}

    private void addAllChildQuests() {
        List<Integer> subQuestIds = Arrays.stream(GameData.getMainQuestDataMap().get(this.parentQuestId).getSubQuests()).map(SubQuestData::getSubId).toList();
        for (Integer subQuestId : subQuestIds) {
            QuestData questConfig = GameData.getQuestDataMap().get(subQuestId);
            this.childQuests.put(subQuestId, new GameQuest(this, questConfig));
        }
    }

	public void setOwner(Player player) {
		if (player.getUid() != this.getOwnerUid()) return;
		this.owner = player;
	}

    public int getQuestVar(int i) {
        return questVars[i];
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
                .forEach(getQuestManager()::startMainQuest);
        }
	}
    //TODO
    public void fail() {}
    public void cancel() {}

    public void tryAcceptSubQuests(QuestTrigger condType, String paramStr, int... params) {
        try {
            List<GameQuest> subQuestsWithCond = getChildQuests().values().stream()
                .filter(p -> p.getState() == QuestState.QUEST_STATE_UNSTARTED)
                .filter(p -> p.getQuestData().getAcceptCond().stream().anyMatch(q -> q.getType() == condType))
                .toList();

            for (GameQuest subQuestWithCond : subQuestsWithCond) {
                List<QuestData.QuestCondition> acceptCond = subQuestWithCond.getQuestData().getAcceptCond();
                int[] accept = new int[acceptCond.size()];

                for (int i = 0; i < subQuestWithCond.getQuestData().getAcceptCond().size(); i++) {
                    QuestData.QuestCondition condition = acceptCond.get(i);
                    boolean result = getServerQuestHandler().triggerCondition(subQuestWithCond, condition, paramStr, params);
                    accept[i] = result ? 1 : 0;
                }

                boolean shouldAccept = LogicType.calculate(subQuestWithCond.getQuestData().getAcceptCondComb(), accept);

                if (shouldAccept) {
                    subQuestWithCond.start();
                    getQuestManager().getAddToQuestListUpdateNotify().add(subQuestWithCond);
                }

            }
            this.save();
        } catch (Exception e) {
            Grasscutter.getLogger().error("An error occurred while trying to accept quest.", e);
        }

    }

    public void tryFailSubQuests(QuestTrigger condType, String paramStr, int... params) {
        try {
            List<GameQuest> subQuestsWithCond = getChildQuests().values().stream()
                .filter(p -> p.getState() == QuestState.QUEST_STATE_UNFINISHED)
                .filter(p -> p.getQuestData().getFailCond().stream().anyMatch(q -> q.getType() == condType))
                .toList();

            for (GameQuest subQuestWithCond : subQuestsWithCond) {
                List<QuestData.QuestCondition> failCond = subQuestWithCond.getQuestData().getFailCond();
                int[] fail = new int[failCond.size()];

                for (int i = 0; i < subQuestWithCond.getQuestData().getFailCond().size(); i++) {
                    QuestData.QuestCondition condition = failCond.get(i);
                    boolean result = getServerQuestHandler().triggerContent(subQuestWithCond, condition, paramStr, params);
                    fail[i] = result ? 1 : 0;
                }

                boolean shouldFail = LogicType.calculate(subQuestWithCond.getQuestData().getFailCondComb(), fail);

                if (shouldFail) {
                    subQuestWithCond.fail();
                    getQuestManager().getAddToQuestListUpdateNotify().add(subQuestWithCond);
                }
            }

        } catch (Exception e) {
            Grasscutter.getLogger().error("An error occurred while trying to fail quest.", e);
        }
    }

    public void tryFinishSubQuests(QuestTrigger condType, String paramStr, int... params) {
        try {
            List<GameQuest> subQuestsWithCond = getChildQuests().values().stream()
                //There are subQuests with no acceptCond, but can be finished (example: 35104)
                .filter(p -> p.getState() == QuestState.QUEST_STATE_UNFINISHED && p.getQuestData().getAcceptCond() != null)
                .filter(p -> p.getQuestData().getFinishCond().stream().anyMatch(q -> q.getType() == condType))
                .toList();

            for (GameQuest subQuestWithCond : subQuestsWithCond) {
                List<QuestData.QuestCondition> finishCond = subQuestWithCond.getQuestData().getFinishCond();
                int[] finish = new int[finishCond.size()];

                for (int i = 0; i < subQuestWithCond.getQuestData().getFinishCond().size(); i++) {
                    QuestData.QuestCondition condition = finishCond.get(i);
                    boolean result = getServerQuestHandler().triggerContent(subQuestWithCond, condition, paramStr, params);
                    finish[i] = result ? 1 : 0;
                }

                boolean shouldFinish = LogicType.calculate(subQuestWithCond.getQuestData().getFinishCondComb(), finish);

                if (shouldFinish) {
                    subQuestWithCond.finish();
                    getQuestManager().getAddToQuestListUpdateNotify().add(subQuestWithCond);
                }
            }
        } catch (Exception e) {
            Grasscutter.getLogger().debug("An error occurred while trying to finish quest.", e);
        }
    }

	public void save() {
		DatabaseHelper.saveQuest(this);
	}

	public ParentQuest toProto() {
		ParentQuest.Builder proto = ParentQuest.newBuilder()
				.setParentQuestId(getParentQuestId())
				.setIsFinished(isFinished());
		/**
		    if ParentQuestState is NONE, official server does not send ParentQuestState nor childQuestList!!!
		    might need more sniffing...
            sending childQuestList without ParentQuestState set causes the game to hang on login
        */
        if (getState() != ParentQuestState.PARENT_QUEST_STATE_NONE) {
            proto.setParentQuestState(getState().getValue());
            for (GameQuest quest : this.getChildQuests().values()) {
                if (quest.getState() != QuestState.QUEST_STATE_UNSTARTED) {
                    ChildQuest childQuest = ChildQuest.newBuilder()
                        .setQuestId(quest.getSubQuestId())
                        .setState(quest.getState().getValue())
                        .build();

                    proto.addChildQuestList(childQuest);
                }
            }
        }
        for (int i : getQuestVars()) {
            proto.addQuestVar(i);
        }

		return proto.build();
	}

}
