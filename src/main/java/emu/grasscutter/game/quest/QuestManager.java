package emu.grasscutter.game.quest;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.MainQuestData;
import emu.grasscutter.data.excels.QuestData;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.player.BasePlayerManager;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.enums.ParentQuestState;
import emu.grasscutter.game.quest.enums.QuestState;
import emu.grasscutter.game.quest.enums.QuestTrigger;
import emu.grasscutter.server.packet.send.PacketFinishedParentQuestUpdateNotify;
import emu.grasscutter.server.packet.send.PacketQuestListUpdateNotify;
import emu.grasscutter.utils.Position;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.Getter;

import java.util.*;
import java.util.function.Consumer;

public class QuestManager extends BasePlayerManager {
    @Getter private final Player player;
    @Getter private final Int2ObjectMap<GameMainQuest> mainQuests;
    @Getter private List<GameQuest> addToQuestListUpdateNotify;
    public static long getQuestKey(int mainQuestId) {
        QuestEncryptionKey questEncryptionKey = GameData.getMainQuestEncryptionMap().get(mainQuestId);
        return questEncryptionKey != null ? questEncryptionKey.getEncryptionKey() : 0L;
    }

    public QuestManager(Player player) {
        super(player);

        this.player = player;
        this.mainQuests = new Int2ObjectOpenHashMap<>();
        this.addToQuestListUpdateNotify = new ArrayList<>();
    }

    public void onLogin() {
        List<GameMainQuest> activeQuests = getActiveMainQuests();
        for (GameMainQuest quest : activeQuests) {
            List<Position> rewindPos = quest.rewind(); // <pos, rotation>
            if (rewindPos != null) {
                getPlayer().getPosition().set(rewindPos.get(0));
                getPlayer().getRotation().set(rewindPos.get(1));
            }
        }
    }

    /*
     * Looking through mainQuests 72201-72208 and 72174, we can infer that a questGlobalVar's default value is 0
     */
    public Integer getQuestGlobalVarValue(Integer variable) {
        return getPlayer().getQuestGlobalVariables().getOrDefault(variable,0);
    }

    public void setQuestGlobalVarValue(Integer variable, Integer value) {
        Integer previousValue = getPlayer().getQuestGlobalVariables().put(variable,value);
        Grasscutter.getLogger().debug("Changed questGlobalVar {} value from {} to {}", variable, previousValue==null ? 0: previousValue, value);
    }

    public void incQuestGlobalVarValue(Integer variable, Integer inc) {
        //
        Integer previousValue = getPlayer().getQuestGlobalVariables().getOrDefault(variable,0);
        getPlayer().getQuestGlobalVariables().put(variable,previousValue + inc);
        Grasscutter.getLogger().debug("Incremented questGlobalVar {} value from {} to {}", variable, previousValue, previousValue + inc);
    }

    // In MainQuest 998, dec is passed as a positive integer
    public void decQuestGlobalVarValue(Integer variable, Integer dec) {
        //
        Integer previousValue = getPlayer().getQuestGlobalVariables().getOrDefault(variable,0);
        getPlayer().getQuestGlobalVariables().put(variable,previousValue - dec);
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

        // Forcefully start
        quest.start();

        // Save main quest
        mainQuest.save();

        // Send packet
        getPlayer().sendPacket(new PacketQuestListUpdateNotify(mainQuest.getChildQuests().values().stream()
            .filter(p -> p.getState() != QuestState.QUEST_STATE_UNSTARTED)
            .toList()));

        return quest;
    }
    public void startMainQuest(int mainQuestId) {
        var mainQuestData = GameData.getMainQuestDataMap().get(mainQuestId);

        if (mainQuestData == null) {
            return;
        }

        Arrays.stream(mainQuestData.getSubQuests())
            .min(Comparator.comparingInt(MainQuestData.SubQuestData::getOrder))
            .map(MainQuestData.SubQuestData::getSubId)
            .ifPresent(this::addQuest);
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
        switch (condType) {
            //accept Conds
            case QUEST_COND_STATE_EQUAL, QUEST_COND_STATE_NOT_EQUAL, QUEST_COND_COMPLETE_TALK, QUEST_COND_LUA_NOTIFY, QUEST_COND_QUEST_VAR_EQUAL, QUEST_COND_QUEST_VAR_GREATER, QUEST_COND_QUEST_VAR_LESS, QUEST_COND_PLAYER_LEVEL_EQUAL_GREATER, QUEST_COND_QUEST_GLOBAL_VAR_EQUAL, QUEST_COND_QUEST_GLOBAL_VAR_GREATER, QUEST_COND_QUEST_GLOBAL_VAR_LESS -> {
                for (GameMainQuest mainquest : checkMainQuests) {
                    mainquest.tryAcceptSubQuests(condType, paramStr, params);
                }
            }

            //fail Conds
            case QUEST_CONTENT_NOT_FINISH_PLOT -> {
                for (GameMainQuest mainquest : checkMainQuests) {
                    mainquest.tryFailSubQuests(condType, paramStr, params);
                }
            }
            //finish Conds
            case QUEST_CONTENT_COMPLETE_TALK, QUEST_CONTENT_FINISH_PLOT, QUEST_CONTENT_COMPLETE_ANY_TALK, QUEST_CONTENT_LUA_NOTIFY, QUEST_CONTENT_QUEST_VAR_EQUAL, QUEST_CONTENT_QUEST_VAR_GREATER, QUEST_CONTENT_QUEST_VAR_LESS, QUEST_CONTENT_ENTER_DUNGEON, QUEST_CONTENT_ENTER_ROOM, QUEST_CONTENT_INTERACT_GADGET, QUEST_CONTENT_TRIGGER_FIRE, QUEST_CONTENT_UNLOCK_TRANS_POINT, QUEST_CONTENT_SKILL -> {
                for (GameMainQuest mainQuest : checkMainQuests) {
                    mainQuest.tryFinishSubQuests(condType, paramStr, params);
                }
            }

            //finish Or Fail Conds
            case QUEST_CONTENT_GAME_TIME_TICK, QUEST_CONTENT_QUEST_STATE_EQUAL, QUEST_CONTENT_ADD_QUEST_PROGRESS, QUEST_CONTENT_LEAVE_SCENE -> {
                for (GameMainQuest mainQuest : checkMainQuests) {
                    mainQuest.tryFailSubQuests(condType, paramStr, params);
                    mainQuest.tryFinishSubQuests(condType, paramStr, params);
                }
            }
            //QUEST_EXEC are handled directly by each subQuest

            //Unused
            default -> Grasscutter.getLogger().error("Unhandled QuestTrigger {}", condType);
        }
        if (this.addToQuestListUpdateNotify.size() != 0) {
            this.getPlayer().getSession().send(new PacketQuestListUpdateNotify(this.addToQuestListUpdateNotify));
            this.addToQuestListUpdateNotify.clear();
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
            boolean cancelAdd = false;
            mainQuest.setOwner(this.getPlayer());

            for (GameQuest quest : mainQuest.getChildQuests().values()) {
                QuestData questConfig = GameData.getQuestDataMap().get(quest.getSubQuestId());

                if (questConfig == null) {
                    mainQuest.delete();
                    cancelAdd = true;
                    break;
                }

                quest.setMainQuest(mainQuest);
                quest.setConfig(questConfig);
            }

            if (!cancelAdd) {
                this.getMainQuests().put(mainQuest.getParentQuestId(), mainQuest);
            }
        }
    }

    public List<GameMainQuest> getActiveMainQuests() {
        return getMainQuests().values().stream().filter(p -> !p.isFinished()).toList();
    }
}
