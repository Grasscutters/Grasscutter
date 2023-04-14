package emu.grasscutter.game.quest;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.MainQuestData;
import emu.grasscutter.data.binout.ScenePointEntry;
import emu.grasscutter.data.excels.QuestData;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.player.BasePlayerManager;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.enums.*;
import emu.grasscutter.server.packet.send.PacketFinishedParentQuestUpdateNotify;
import emu.grasscutter.utils.Position;
import io.netty.util.concurrent.FastThreadLocalThread;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;
import lombok.Getter;
import lombok.val;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class QuestManager extends BasePlayerManager {

    @Getter private final Player player;
    @Getter private final Int2ObjectMap<GameMainQuest> mainQuests;

    private long lastHourCheck = 0;
    private long lastDayCheck = 0;

    public static final ExecutorService eventExecutor;
    static {
        eventExecutor = new ThreadPoolExecutor(4, 4,
            60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1000),
            FastThreadLocalThread::new, new ThreadPoolExecutor.AbortPolicy());
    }
    /*
        On SetPlayerBornDataReq, the server sends FinishedParentQuestNotify, with this exact
        parentQuestList. Captured on Game version 2.7
        Note: quest 40063 is already set to finished, with childQuest 4006406's state set to 3
    */

    private static Set<Integer> newPlayerMainQuests = Set.of(303,318,348,349,350,351,416,500,
        501,502,503,504,505,506,507,508,509,20000,20507,20509,21004,21005,21010,21011,21016,21017,
        21020,21021,21025,40063,70121,70124,70511,71010,71012,71013,71015,71016,71017,71555);

    /*
        On SetPlayerBornDataReq, the server sends ServerCondMeetQuestListUpdateNotify, with this exact
        addQuestIdList. Captured on Game version 2.7
        Total of 161...
     */
    /*
    private static Set<Integer> newPlayerServerCondMeetQuestListUpdateNotify = Set.of(3100101, 7104405, 2201601,
        7100801, 1907002, 7293301, 7193801, 7293401, 7193901, 7091001, 7190501, 7090901, 7190401, 7090801, 7190301,
        7195301, 7294801, 7195201, 7293001, 7094001, 7193501, 7293501, 7194001, 7293701, 7194201, 7194301, 7293801,
        7194901, 7194101, 7195001, 7294501, 7294101, 7194601, 7294301, 7194801, 7091301, 7290301, 2102401, 7216801,
        7190201, 7090701, 7093801, 7193301, 7292801, 7227828, 7093901, 7193401, 7292901, 7093701, 7193201, 7292701,
        7082402, 7093601, 7292601, 7193101, 2102301, 7093501, 7292501, 7193001, 7093401, 7292401, 7192901, 7093301,
        7292301, 7192801, 7294201, 7194701, 2100301, 7093201, 7212402, 7292201, 7192701, 7280001, 7293901, 7194401,
        7093101, 7212302, 7292101, 7192601, 7093001, 7292001, 7192501, 7216001, 7195101, 7294601, 2100900, 7092901,
        7291901, 7192401, 7092801, 7291801, 7192301, 2101501, 7092701, 7291701, 7192201, 7106401, 2100716, 7091801,
        7290801, 7191301, 7293201, 7193701, 7094201, 7294001, 7194501, 2102290, 7227829, 7193601, 7094101, 7091401,
        7290401, 7190901, 7106605, 7291601, 7192101, 7092601, 7291501, 7192001, 7092501, 7291401, 7191901, 7092401,
        7291301, 7191801, 7092301, 7211402, 7291201, 7191701, 7092201, 7291101, 7191601, 7092101, 7291001, 7191501,
        7092001, 7290901, 7191401, 7091901, 7290701, 7191201, 7091701, 7290601, 7191101, 7091601, 7290501, 7191001,
        7091501, 7290201, 7190701, 7091201, 7190601, 7091101, 7190101, 7090601, 7090501, 7090401, 7010701, 7090301,
        7090201, 7010103, 7090101
        );

    */

    public static long getQuestKey(int mainQuestId) {
        QuestEncryptionKey questEncryptionKey = GameData.getMainQuestEncryptionMap().get(mainQuestId);
        return questEncryptionKey != null ? questEncryptionKey.getEncryptionKey() : 0L;
    }
    public QuestManager(Player player) {

        super(player);
        this.player = player;
        this.mainQuests = new Int2ObjectOpenHashMap<>();
    }

    // TODO store user value set on enable
    public boolean isQuestingEnabled() {
        return Grasscutter.getConfig().server.game.gameOptions.questing;
    }

    public void onPlayerBorn() {
        // TODO scan the quest and start the quest with acceptCond fulfilled
        // The off send 3 request in that order: 1. FinishedParentQuestNotify, 2. QuestListNotify, 3. ServerCondMeetQuestListUpdateNotify

        if (this.isQuestingEnabled()) {
            // this.enableQuests();
            this.addQuest(35104);
        }

        //getPlayer().sendPacket(new PacketFinishedParentQuestUpdateNotify(newQuests));
        //getPlayer().sendPacket(new PacketQuestListNotify(subQuests));
        //getPlayer().sendPacket(new PacketServerCondMeetQuestListUpdateNotify(newPlayerServerCondMeetQuestListUpdateNotify));
    }

    public void onLogin() {

        List<GameMainQuest> activeQuests = getActiveMainQuests();
        List<GameQuest> activeSubs = new ArrayList<>(activeQuests.size());
        for (GameMainQuest quest : activeQuests) {
            List<Position> rewindPos = quest.rewind(); // <pos, rotation>
            var activeQuest = quest.getActiveQuests();
            if (rewindPos != null) {
                getPlayer().getPosition().set(rewindPos.get(0));
                getPlayer().getRotation().set(rewindPos.get(1));
            }
            if(activeQuest!=null && rewindPos!=null){
                //activeSubs.add(activeQuest);
                //player.sendPacket(new PacketQuestProgressUpdateNotify(activeQuest));
            }
            quest.checkProgress();
        }

        if (this.player.getActivityManager() != null)
            this.player.getActivityManager().triggerActivityConditions();
    }

    public void onTick(){
        checkTimeVars();

        // trigger game time tick for quests
        queueEvent(QuestContent.QUEST_CONTENT_GAME_TIME_TICK,
            player.getWorld().getGameTimeHours() , // hours
            0);
    }

    private void checkTimeVars() {
        val currentDays = player.getWorld().getTotalGameTimeDays();
        val currentHours = player.getWorld().getTotalGameTimeHours();
        boolean checkDays =  currentDays != lastDayCheck;
        boolean checkHours = currentHours != lastHourCheck;

        if(!checkDays && !checkHours){
            return;
        }

        this.lastDayCheck = currentDays;
        this.lastHourCheck = currentHours;

        player.getActiveQuestTimers().forEach(mainQuestId -> {
            if(checkHours) {
                queueEvent(QuestCond.QUEST_COND_TIME_VAR_GT_EQ, mainQuestId);
                queueEvent(QuestContent.QUEST_CONTENT_TIME_VAR_GT_EQ, mainQuestId);
            }
            if(checkDays) {
                queueEvent(QuestCond.QUEST_COND_TIME_VAR_PASS_DAY, mainQuestId);
                queueEvent(QuestContent.QUEST_CONTENT_TIME_VAR_PASS_DAY, mainQuestId);
            }
        });
    }

    private List<GameMainQuest> addMultMainQuests(Set<Integer> mainQuestIds) {
        List<GameMainQuest> newQuests = new ArrayList<>();
        for (Integer id : mainQuestIds) {
            getMainQuests().put(id.intValue(),new GameMainQuest(this.player, id));
            getMainQuestById(id).save();
            newQuests.add(getMainQuestById(id));
        }
        return newQuests;
    }

    public void enableQuests() {
        this.triggerEvent(QuestCond.QUEST_COND_NONE, null, 0);
        this.triggerEvent(QuestCond.QUEST_COND_PLAYER_LEVEL_EQUAL_GREATER, null, 1);
    }

    /*
        Looking through mainQuests 72201-72208 and 72174, we can infer that a questGlobalVar's default value is 0
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
    //In MainQuest 998, dec is passed as a positive integer
    public void decQuestGlobalVarValue(Integer variable, Integer dec) {
        //
        Integer previousValue = getPlayer().getQuestGlobalVariables().getOrDefault(variable,0);
        getPlayer().getQuestGlobalVariables().put(variable,previousValue - dec);
        Grasscutter.getLogger().debug("Decremented questGlobalVar {} value from {} to {}", variable, previousValue, previousValue - dec);
    }

    public GameMainQuest getMainQuestById(int mainQuestId) {
        return getMainQuests().get(mainQuestId);
    }

    public GameMainQuest getMainQuestByTalkId(int talkId) {
        int mainQuestId = GameData.getQuestTalkMap().getOrDefault(talkId, talkId / 100);
        return getMainQuestById(mainQuestId);
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

       return addQuest(questConfig);
    }

    public GameQuest addQuest(@Nonnull QuestData questConfig) {

        // Main quest
        GameMainQuest mainQuest = this.getMainQuestById(questConfig.getMainId());

        // Create main quest if it doesnt exist
        if (mainQuest == null) {
            mainQuest = addMainQuest(questConfig);
        }

        // Sub quest
        GameQuest quest = mainQuest.getChildQuestById(questConfig.getSubId());

        // Forcefully start
        quest.start();
        checkQuestAlreadyFullfilled(quest);

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
        //TODO find a better way then hardcoding to detect needed required quests
        // if (mainQuestId == 355){
        //     startMainQuest(361);
        //     startMainQuest(418);
        //     startMainQuest(423);
        //     startMainQuest(20509);
        // }
    }
    public void queueEvent(QuestCond condType, int... params) {
        queueEvent(condType, "", params);
    }
    public void queueEvent(QuestContent condType, int... params) {
        queueEvent(condType, "", params);
    }

    public void queueEvent(QuestContent condType, String paramStr, int... params) {
        eventExecutor.submit(() -> triggerEvent(condType, paramStr, params));
    }
    public void queueEvent(QuestCond condType, String paramStr, int... params) {
        eventExecutor.submit(() -> triggerEvent(condType, paramStr, params));
    }

    //QUEST_EXEC are handled directly by each subQuest

    public void triggerEvent(QuestCond condType, String paramStr, int... params) {
        Grasscutter.getLogger().debug("Trigger Event {}, {}, {}", condType, paramStr, params);
        var potentialQuests = GameData.getQuestDataByConditions(condType, params[0], paramStr);
        if(potentialQuests == null){
            return;
        }

        var questSystem = getPlayer().getServer().getQuestSystem();
        var owner = getPlayer();

        potentialQuests.forEach(questData -> {
            if(this.wasSubQuestStarted(questData)){
                return;
            }
            val acceptCond = questData.getAcceptCond();
            int[] accept = new int[acceptCond.size()];
            for (int i = 0; i < acceptCond.size(); i++) {
                val condition = acceptCond.get(i);
                boolean result = questSystem.triggerCondition(owner, questData, condition, paramStr, params);
                accept[i] = result ? 1 : 0;
            }

            boolean shouldAccept = LogicType.calculate(questData.getAcceptCondComb(), accept);

            if (shouldAccept){
                GameQuest quest = owner.getQuestManager().addQuest(questData);
                Grasscutter.getLogger().debug("Added quest {} result {}", questData.getSubId(), quest !=null);
            }
        });
    }

    public boolean wasSubQuestStarted(QuestData questData) {
        var quest = getQuestById(questData.getId());
        if(quest == null) return false;

        return quest.state != QuestState.QUEST_STATE_UNSTARTED;
    }

    public void triggerEvent(QuestContent condType, String paramStr, int... params) {
        if (condType != QuestContent.QUEST_CONTENT_GAME_TIME_TICK)
            Grasscutter.getLogger().debug("Trigger Event {}, {}, {}", condType, paramStr, params);
        else Grasscutter.getLogger().trace("Trigger Event {}, {}, {}", condType, paramStr, params);

        List<GameMainQuest> checkMainQuests = this.getMainQuests().values().stream()
            .filter(i -> i.getState() != ParentQuestState.PARENT_QUEST_STATE_FINISHED)
            .toList();
        for (GameMainQuest mainQuest : checkMainQuests) {
            mainQuest.tryFailSubQuests(condType, paramStr, params);
            mainQuest.tryFinishSubQuests(condType, paramStr, params);
        }
    }

    /**
     * TODO maybe trigger them delayed to allow basic communication finish first
     * TODO move content checks to use static informations where possible to allow direct already fulfilled checking
     * @param quest
     */
    public void checkQuestAlreadyFullfilled(GameQuest quest){
        Grasscutter.getGameServer().getScheduler().scheduleDelayedTask(() -> {
            for(var condition : quest.getQuestData().getFinishCond()){
                switch (condition.getType()) {
                    case QUEST_CONTENT_OBTAIN_ITEM, QUEST_CONTENT_ITEM_LESS_THAN -> {
                        //check if we already own enough of the item
                        var item = getPlayer().getInventory().getItemByGuid(condition.getParam()[0]);
                        queueEvent(condition.getType(), condition.getParam()[0], item != null ? item.getCount() : 0);
                    }
                    case QUEST_CONTENT_UNLOCK_TRANS_POINT -> {
                        var scenePoints = getPlayer().getUnlockedScenePoints().get(condition.getParam()[0]);
                        if (scenePoints != null && scenePoints.contains(condition.getParam()[1])) {
                            queueEvent(condition.getType(), condition.getParam()[0], condition.getParam()[1]);
                        }
                    }
                    case QUEST_CONTENT_UNLOCK_AREA -> {
                        var sceneAreas = getPlayer().getUnlockedSceneAreas().get(condition.getParam()[0]);
                        if (sceneAreas != null && sceneAreas.contains(condition.getParam()[1])) {
                            queueEvent(condition.getType(), condition.getParam()[0], condition.getParam()[1]);
                        }
                    }
                    case QUEST_CONTENT_PLAYER_LEVEL_UP -> queueEvent(condition.getType(), player.getLevel());
                }
            }
        }, 1);
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

    /**
     * Fetches dungeon IDs for quests which have a dungeon.
     *
     * @param point The associated scene point of the dungeon.
     * @return A list of dungeon IDs, or an empty list if none are found.
     */
    public List<Integer> questsForDungeon(ScenePointEntry point) {
        var pointId = point.getPointData().getId();
        // Get the active quests.
        return this.getActiveMainQuests().stream()
            // Get the sub-quests of the main quest.
            .map(GameMainQuest::getChildQuests)
            // Get the values of the sub-quests map.
            .map(Map::values)
            .map(quests -> quests.stream()
                // Get the dungeon IDs of each quest.
                .map(GameQuest::getDungeonIds)
                .map(ids -> ids.stream()
                    // Find entry points which match this dungeon.
                    .filter(id -> id.rightInt() == pointId)
                    .toList())
                .map(ids -> ids.stream()
                    // Of the remaining dungeons, find the ID of the quest dungeon.
                    .map(IntIntImmutablePair::leftInt)
                    .toList())
                .flatMap(Collection::stream)
                .toList())
            .flatMap(Collection::stream)
            .toList();
    }
}
