package emu.grasscutter.game.quest;

import java.beans.Transient;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.MainQuestData;
import emu.grasscutter.data.excels.QuestData;
import emu.grasscutter.data.excels.QuestData.QuestCondition;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.enums.ParentQuestState;
import emu.grasscutter.game.quest.enums.QuestTrigger;
import emu.grasscutter.game.quest.enums.LogicType;
import emu.grasscutter.game.quest.enums.QuestState;
import emu.grasscutter.server.packet.send.*;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import jdk.jshell.spi.ExecutionControl;
import lombok.Getter;

public class QuestManager {
	@Getter private final Player player;
    @Getter private Map<Integer,Integer> questGlobalVariables;

    @Getter private final Int2ObjectMap<GameMainQuest> mainQuests;
    @Getter private List<GameQuest> toAddToQuestList;

	public QuestManager(Player player) {
		this.player = player;
        //TODO make QuestManagers objects in the database, and use update instead of save
        this.questGlobalVariables = player.getQuestGlobalVariables();
		this.mainQuests = new Int2ObjectOpenHashMap<>();
        this.toAddToQuestList = new ArrayList<>();
	}
    /*
        Looking through mainQuests 72201-72208 and 72174, we can infer that a questGlobalVar's default value is 0
    */
    public Integer getQuestGlobalVarValue(Integer variable) {
        return this.questGlobalVariables.getOrDefault(variable,0);
    }

    public void setQuestGlobalVarValue(Integer variable, Integer value) {
        Integer previousValue = this.questGlobalVariables.put(variable,value);
        Grasscutter.getLogger().debug("Changed questGlobalVar {} value from {} to {}", variable, previousValue==null ? 0: previousValue, value);
    }
    public void incQuestGlobalVarValue(Integer variable, Integer inc) {
        //
        Integer previousValue = this.questGlobalVariables.getOrDefault(variable,0);
        this.questGlobalVariables.put(variable,previousValue + inc);
        Grasscutter.getLogger().debug("Incremented questGlobalVar {} value from {} to {}", variable, previousValue, previousValue + inc);
    }
    //In MainQuest 998, dec is passed as a positive integer
    public void decQuestGlobalVarValue(Integer variable, Integer dec) {
        //
        Integer previousValue = this.questGlobalVariables.getOrDefault(variable,0);
        this.questGlobalVariables.put(variable,previousValue - dec);
        Grasscutter.getLogger().debug("Decremented questGlobalVar {} value from {} to {}", variable, previousValue, previousValue - dec);
    }

	public GameMainQuest getMainQuestById(int mainQuestId) {
		return getMainQuests().get(mainQuestId);
	}

	public GameQuest getQuestById(int questId) {
		QuestData questConfig = GameData.getQuestDataMap().get(questId);
		if (questConfig == null) {
			return null;
		}

		GameMainQuest mainQuest = getMainQuests().get(questConfig.getMainId());

		if (mainQuest == null) {
			return null;
		}

		return mainQuest.getChildQuests().get(questId);
	}

	public void forEachQuest(Consumer<GameQuest> callback) {
		for (GameMainQuest mainQuest : getMainQuests().values()) {
			for (GameQuest quest : mainQuest.getChildQuests().values()) {
				callback.accept(quest);
			}
		}
	}

	public void forEachMainQuest(Consumer<GameMainQuest> callback) {
		for (GameMainQuest mainQuest : getMainQuests().values()) {
			callback.accept(mainQuest);
		}
	}

	// TODO
	public void forEachActiveQuest(Consumer<GameQuest> callback) {
		for (GameMainQuest mainQuest : getMainQuests().values()) {
			for (GameQuest quest : mainQuest.getChildQuests().values()) {
				if (quest.getState() != QuestState.QUEST_STATE_FINISHED) {
					callback.accept(quest);
				}
			}
		}
	}

	public GameMainQuest addMainQuest(QuestData questConfig) {
		GameMainQuest mainQuest = new GameMainQuest(getPlayer(), questConfig.getMainId());
		getMainQuests().put(mainQuest.getParentQuestId(), mainQuest);

		getPlayer().sendPacket(new PacketFinishedParentQuestUpdateNotify(mainQuest));

		return mainQuest;
	}
	public GameQuest addQuest(int questId) {
		QuestData questConfig = GameData.getQuestDataMap().get(questId);
		if (questConfig == null) {
			return null;
		}

		// Main quest
		GameMainQuest mainQuest = this.getMainQuestById(questConfig.getMainId());

		// Create main quest if it doesnt exist
		if (mainQuest == null) {
			mainQuest = addMainQuest(questConfig);
		}

		// Sub quest
		GameQuest quest = mainQuest.getChildQuestById(questId);

		if (quest != null) {
			return null;
		}

		// Create
		quest = new GameQuest(mainQuest, questConfig);

		// Save main quest
		mainQuest.save();

		// Send packet
		getPlayer().sendPacket(new PacketQuestListUpdateNotify(mainQuest.getChildQuests().values().stream().toList()));

		return quest;
	}
    public void startMainQuest(int mainQuestId){
        var mainQuestData = GameData.getMainQuestDataMap().get(mainQuestId);

        if (mainQuestData == null){
            return;
        }

        Arrays.stream(mainQuestData.getSubQuests())
            .min(Comparator.comparingInt(MainQuestData.SubQuestData::getOrder))
            .map(MainQuestData.SubQuestData::getSubId)
            .ifPresent(this::addQuest);
    }

    private void tryFinishActiveQuests(QuestTrigger condType, String paramStr, int... params) {
        Set<GameQuest> changedQuests = new HashSet<>();

        //try finish active quests
        this.forEachActiveQuest(quest -> {
            QuestData data = quest.getQuestData();

            for (int i = 0; i < data.getFinishCond().size(); i++) {
                if (quest.getFinishProgressList() == null
                    || quest.getFinishProgressList().length == 0
                    || quest.getFinishProgressList()[i] == 1) {
                    continue;
                }

                QuestCondition condition = data.getFinishCond().get(i);

                if (condition.getType() != condType) {
                    continue;
                }

                boolean result = false;
                try {
                    result = getPlayer().getServer().getQuestHandler().triggerContent(quest, condition, paramStr, params);
                } catch (ExecutionControl.NotImplementedException e) {
                    throw new RuntimeException(e);
                }

                if (result) {
                    quest.getFinishProgressList()[i] = 1;

                    changedQuests.add(quest);
                }
            }
        });

        for (GameQuest quest : changedQuests) {
            LogicType logicType = quest.getQuestData().getFailCondComb();
            int[] progress = quest.getFinishProgressList();

            // Handle logical comb
            boolean finish = LogicType.calculate(logicType, progress);

            // Finish
            if (finish) {
                quest.finish();
            } else {
                getPlayer().sendPacket(new PacketQuestProgressUpdateNotify(quest));
                quest.save();
            }
        }
    }
    public void triggerEvent(QuestTrigger condType, int... params) {
        triggerEvent(condType, "", params);
    }

    //TODO
	public void triggerEvent(QuestTrigger condType, String paramStr, int... params) {
        Grasscutter.getLogger().debug("Trigger Event {}, {}, {}", condType, paramStr, params);
        List<GameMainQuest> checkMainQuests = this.getMainQuests().values().stream()
            .filter(i -> i.getState() != ParentQuestState.PARENT_QUEST_STATE_FINISHED)
            .toList();
        switch(condType){
            //accept Conds
            case QUEST_COND_STATE_EQUAL:
            case QUEST_COND_STATE_NOT_EQUAL:
            case QUEST_COND_COMPLETE_TALK:
            case QUEST_COND_LUA_NOTIFY:
            case QUEST_COND_QUEST_VAR_EQUAL:
            case QUEST_COND_QUEST_VAR_GREATER:
            case QUEST_COND_QUEST_VAR_LESS:
            case QUEST_COND_PLAYER_LEVEL_EQUAL_GREATER:
            case QUEST_COND_QUEST_GLOBAL_VAR_EQUAL:
            case QUEST_COND_QUEST_GLOBAL_VAR_GREATER:
            case QUEST_COND_QUEST_GLOBAL_VAR_LESS:
                for (GameMainQuest mainquest : checkMainQuests) {
                    mainquest.tryAcceptSubQuests(condType, paramStr, params);
                }
                if(toAddToQuestList.size() != 0){
                    this.getPlayer().getSession().send(new PacketQuestListUpdateNotify(toAddToQuestList));
                    toAddToQuestList.clear();
                }
                break;

            //fail Conds
            case QUEST_CONTENT_NOT_FINISH_PLOT:
                for (GameMainQuest mainquest : checkMainQuests) {
                    mainquest.tryFailSubQuests(condType, paramStr, params);
                }
                break;
            //finish Conds
            case QUEST_CONTENT_COMPLETE_TALK:
            case QUEST_CONTENT_FINISH_PLOT:
            case QUEST_CONTENT_COMPLETE_ANY_TALK:
            case QUEST_CONTENT_LUA_NOTIFY:
            case QUEST_CONTENT_QUEST_VAR_EQUAL:
            case QUEST_CONTENT_QUEST_VAR_GREATER:
            case QUEST_CONTENT_QUEST_VAR_LESS:
            case QUEST_CONTENT_ENTER_DUNGEON:
            case QUEST_CONTENT_ENTER_ROOM:
            case QUEST_CONTENT_INTERACT_GADGET:
                for (GameMainQuest mainQuest : checkMainQuests) {
                    mainQuest.tryFinishSubQuests(condType, paramStr, params);
                }
                break;

            //finish Or Fail Conds
            case QUEST_CONTENT_GAME_TIME_TICK:
            case QUEST_CONTENT_QUEST_STATE_EQUAL:
            case QUEST_CONTENT_ADD_QUEST_PROGRESS:
                for (GameMainQuest mainQuest : checkMainQuests) {
                    mainQuest.tryFailSubQuests(condType, paramStr, params);
                    mainQuest.tryFinishSubQuests(condType, paramStr, params);
                }
                break;
            //QUEST_EXEC are handled directly by each subQuest

            //Unused
            case QUEST_CONTENT_QUEST_STATE_NOT_EQUAL:
            case QUEST_COND_PLAYER_CHOOSE_MALE:
            default:
                Grasscutter.getLogger().error("Unhandled QuestTrigger {}", condType);
        }

	}

    public List<QuestGroupSuite> getSceneGroupSuite(int sceneId) {
        return getMainQuests().values().stream()
            .filter(i -> i.getState() != ParentQuestState.PARENT_QUEST_STATE_FINISHED)
            .map(GameMainQuest::getQuestGroupSuites)
            .filter(Objects::nonNull)
            .flatMap(Collection::stream)
            .filter(i -> i.getScene() == sceneId)
            .toList();
    }
	public void loadFromDatabase() {
		List<GameMainQuest> quests = DatabaseHelper.getAllQuests(getPlayer());

		for (GameMainQuest mainQuest : quests) {
			mainQuest.setOwner(this.getPlayer());

			for (GameQuest quest : mainQuest.getChildQuests().values()) {
				quest.setMainQuest(mainQuest);
				quest.setConfig(GameData.getQuestDataMap().get(quest.getSubQuestId()));
			}

			this.getMainQuests().put(mainQuest.getParentQuestId(), mainQuest);
		}
	}
}
