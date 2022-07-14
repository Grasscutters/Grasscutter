package emu.grasscutter.game.quest;

import java.util.*;

import dev.morphia.query.Query;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.excels.QuestData;
import emu.grasscutter.database.DatabaseManager;
import emu.grasscutter.game.quest.enums.LogicType;
import emu.grasscutter.game.quest.enums.QuestTrigger;
import emu.grasscutter.server.packet.send.PacketCodexDataUpdateNotify;
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
	}

    private void addChildQuestById(int questId) {
        QuestData questConfig = GameData.getQuestDataMap().get(questId);
        this.childQuests.put(Integer.valueOf(questId),new GameQuest(this, questConfig));
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
                .forEach(getQuestManager()::startMainQuest);
        }
	}
    //TODO
    public void fail() {}
    public void cancel() {}

    public void tryAcceptSubQuests(QuestTrigger condType, String paramStr, int... params) {
        try {
            SubQuestData[] subQuestsData = GameData.getMainQuestDataMap().get(this.parentQuestId).getSubQuests();
            ArrayList<QuestData> subQuests = new ArrayList<>();
            for(SubQuestData subQuestData: subQuestsData) {
                subQuests.add(GameData.getQuestDataMap().get(subQuestData.getSubId()));
            }
            /*
                Make sure we check the subQuests with the exact condition AND parameters
                Note: because we currently don't pass all parameters (example: QUEST_CONTENT_ENTER_DUNGEON normally has 2 parameters),
                I'll only check for params[0], which is a worse filter
            */
            List<QuestData> subQuestsWithCond = subQuests.stream()
                .filter(p -> p.getAcceptCond().stream().anyMatch(q -> (q.getType() == condType) && q.getParam()[0] == params[0]))
                .toList();
            if (subQuestsWithCond.size() == 0) {return;}

            for (QuestData subQuestWithCond : subQuestsWithCond) {
                GameQuest quest = this.getChildQuestById(subQuestWithCond.getSubId());

                if (quest == null) {

                    int[] accept = new int[subQuestWithCond.getAcceptCond().size()];

                    for (int i = 0; i < subQuestWithCond.getAcceptCond().size(); i++) {
                        QuestData.QuestCondition condition = subQuestWithCond.getAcceptCond().get(i);
                        boolean result = evaluateAcceptCond(condition, subQuestWithCond.getSubId());
                        accept[i] = result ? 1 : 0;
                    }

                    boolean shouldAccept = LogicType.calculate(subQuestWithCond.getAcceptCondComb(), accept);

                    if (shouldAccept) {
                        new GameQuest(this,subQuestWithCond);
                        getQuestManager().getToAddToQuestList().add(getChildQuests().get(subQuestWithCond.getSubId()));
                    }
                }

            }
            this.save();
        } catch (Exception e) {
            Grasscutter.getLogger().error("An error occurred while trying to accept quest.", e);
        }

    }

    private boolean evaluateAcceptCond(QuestData.QuestCondition condition, int questId) throws ExecutionControl.NotImplementedException {
        switch (condition.getType()) {

            case QUEST_COND_STATE_EQUAL:
            case QUEST_COND_STATE_NOT_EQUAL:
                //checkQuest might not even be in the same MainQuest. Example: 99902 appears everywhere
                GameQuest checkQuest = getQuestManager().getQuestById(condition.getParam()[0]);
                if (checkQuest == null) {
                    Grasscutter.getLogger().debug("Warning: quest {} hasn't been started yet!", condition.getParam()[0]);
                    return false;
                }
                return getServerQuestHandler().triggerCondition(checkQuest, condition,
                    condition.getParamStr(),
                    condition.getParam());

            case QUEST_COND_COMPLETE_TALK:
                GameMainQuest checkMainQuest = getQuestManager().getMainQuestById(condition.getParam()[0]/100);
                if (checkMainQuest == null || GameData.getMainQuestDataMap().get(checkMainQuest.getParentQuestId()).getTalks() == null) {
                    Grasscutter.getLogger().debug("Warning: mainQuest {} hasn't been started yet, or has no talks", condition.getParam()[0]/100);
                    return false;
                }
                return getServerQuestHandler().triggerCondition(checkMainQuest, condition,
                    condition.getParamStr(),
                    condition.getParam());

            case QUEST_COND_QUEST_VAR_EQUAL:
            case QUEST_COND_QUEST_VAR_GREATER:
            case QUEST_COND_QUEST_VAR_LESS:
                return getServerQuestHandler().triggerCondition(this, condition,
                    condition.getParamStr(),
                    condition.getParam());

            case QUEST_COND_PLAYER_LEVEL_EQUAL_GREATER:
            case QUEST_COND_QUEST_GLOBAL_VAR_EQUAL:
            case QUEST_COND_QUEST_GLOBAL_VAR_GREATER:
            case QUEST_COND_QUEST_GLOBAL_VAR_LESS:
                return getServerQuestHandler().triggerCondition(getOwner(), condition,
                    condition.getParamStr(),
                    condition.getParam());

            case QUEST_COND_LUA_NOTIFY: //Not sure where to put
            default:
                return getServerQuestHandler().triggerCondition(getChildQuestById(questId), condition,
                    condition.getParamStr(),
                    condition.getParam());
        }
    }

    public void tryFailSubQuests(QuestTrigger condType, String paramStr, int... params) {
        try {
            SubQuestData[] subQuestsData = GameData.getMainQuestDataMap().get(this.parentQuestId).getSubQuests();
            ArrayList<QuestData> subQuests = new ArrayList<>();
            for(SubQuestData subQuestData: subQuestsData) {
                subQuests.add(GameData.getQuestDataMap().get(subQuestData.getSubId()));
            }
            /*
                Make sure we check the subQuests with the exact condition AND parameters
                Note: because we currently don't pass all parameters (example: QUEST_CONTENT_ENTER_DUNGEON normally has 2 parameters),
                I'll only check for params[0], which is a worse filter
            */
            List<QuestData> subQuestsWithCond = subQuests.stream()
                .filter(p -> p.getFailCond().stream().anyMatch(q -> (q.getType() == condType) && q.getParam()[0] == params[0]))
                .toList();
            if (subQuestsWithCond.size() == 0) {return;}

            for (QuestData subQuestWithCond : subQuestsWithCond) {
                GameQuest quest = this.getChildQuestById(subQuestWithCond.getSubId());

                if (quest != null) {

                    int[] fail = new int[subQuestWithCond.getFailCond().size()];

                    for (int i = 0; i < subQuestWithCond.getFailCond().size(); i++) {
                        QuestData.QuestCondition condition = subQuestWithCond.getFailCond().get(i);
                        boolean result = evaluateFailCond(condition, subQuestWithCond.getSubId());
                        fail[i] = result ? 1 : 0;
                    }

                    boolean shouldFail = LogicType.calculate(subQuestWithCond.getFailCondComb(), fail);

                    if (shouldFail) {
                        this.getChildQuestById(subQuestWithCond.getSubId()).fail();
                    }
                }
            }
        } catch (Exception e) {
            Grasscutter.getLogger().error("An error occurred while trying to fail quest.", e);
        }
    }

    private boolean evaluateFailCond(QuestData.QuestCondition condition, int questId) throws ExecutionControl.NotImplementedException {
        switch(condition.getType()) {
            case QUEST_CONTENT_NOT_FINISH_PLOT:
            case QUEST_CONTENT_GAME_TIME_TICK:
                return getServerQuestHandler().triggerContent(this, condition,
                    condition.getParamStr(),
                    condition.getParam());

            case QUEST_CONTENT_QUEST_STATE_EQUAL:
                //checkQuest might not even be in the same MainQuest. Example: 99902 appears everywhere
                GameQuest checkQuest = getQuestManager().getQuestById(condition.getParam()[0]);
                if (checkQuest == null) {
                    //Grasscutter.getLogger().debug("Warning: quest {} hasn't been started yet!", condition.getParam()[0]);
                    return false;
                }
                return getServerQuestHandler().triggerContent(checkQuest, condition,
                    condition.getParamStr(),
                    condition.getParam());
            default:
                return false;
        }
    }

    public void tryFinishSubQuests(QuestTrigger condType, String paramStr, int... params) {
        try {
            SubQuestData[] subQuestsData = GameData.getMainQuestDataMap().get(this.parentQuestId).getSubQuests();
            ArrayList<QuestData> subQuests = new ArrayList<>();
            for(SubQuestData subQuestData: subQuestsData) {
                subQuests.add(GameData.getQuestDataMap().get(subQuestData.getSubId()));
            }
            /*
                Make sure we check the subQuests with the exact condition AND parameters
                Note: because we currently don't pass all params and paramStr correctly (example: QUEST_CONTENT_ENTER_DUNGEON normally has 2 parameters, we pass 1),
                I'll only check for params[0], which is a worse filter
            */
            List<QuestData> subQuestsWithCond = subQuests.stream()
                .filter(p -> p.getFinishCond().stream().anyMatch(q -> (q.getType() == condType) && q.getParam()[0] == params[0]))
                .toList();
            if (subQuestsWithCond.size() == 0) {return;}

            for (QuestData subQuestWithCond : subQuestsWithCond) {
                GameQuest quest = this.getChildQuestById(subQuestWithCond.getSubId());

                if (quest != null) {

                    int[] finish = new int[subQuestWithCond.getFinishCond().size()];

                    for (int i = 0; i < subQuestWithCond.getFinishCond().size(); i++) {
                        QuestData.QuestCondition condition = subQuestWithCond.getFinishCond().get(i);
                        boolean result = evaluateFinishCond(condition, subQuestWithCond.getSubId());
                        finish[i] = result ? 1 : 0;
                    }

                    boolean shouldFinish= LogicType.calculate(subQuestWithCond.getFinishCondComb(), finish);

                    if (shouldFinish) {
                        this.getChildQuestById(subQuestWithCond.getSubId()).finish();
                    }
                }
            }
        } catch (Exception e) {
            Grasscutter.getLogger().debug("An error occurred while trying to finish quest.", e);
        }
    }

    private boolean evaluateFinishCond(QuestData.QuestCondition condition, int questId) throws ExecutionControl.NotImplementedException {
        switch(condition.getType()){
            case QUEST_CONTENT_COMPLETE_TALK:
            case QUEST_CONTENT_FINISH_PLOT:
            case QUEST_CONTENT_COMPLETE_ANY_TALK:
            case QUEST_CONTENT_QUEST_VAR_EQUAL:
            case QUEST_CONTENT_QUEST_VAR_GREATER:
            case QUEST_CONTENT_QUEST_VAR_LESS:
            case QUEST_CONTENT_ENTER_DUNGEON:
            case QUEST_CONTENT_ENTER_ROOM:
            case QUEST_CONTENT_INTERACT_GADGET:
            case QUEST_CONTENT_LUA_NOTIFY:
                return getServerQuestHandler().triggerContent(this, condition,
                    condition.getParamStr(),
                    condition.getParam());
            default:
                return false;
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
					.setQuestId(quest.getSubQuestId())
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
