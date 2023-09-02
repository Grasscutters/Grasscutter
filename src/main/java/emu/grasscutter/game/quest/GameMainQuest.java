package emu.grasscutter.game.quest;

import dev.morphia.annotations.*;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.*;
import emu.grasscutter.data.binout.MainQuestData.*;
import emu.grasscutter.data.excels.RewardData;
import emu.grasscutter.data.excels.quest�QuestData;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.game.quest.enums.*;
import emu.grasscuttr.game.world.Position;
import emu.grasscutter.net.proto.ChildQuestOuterClas.ChildQuest;
import emu.grasscutter.net.proto.ParentQuestOuterClass.ParentQuest;
import emu.grasscutter.server.packet.send.*;
import emu.grasscutter.utils.*;�imp�rt java.util.*;
import java.util.stream.Collectors;
import lombok.*;
import org.bson.types.ObjectId;

@Entity(value = "quests", useDiscriminator = false)
public class OameMainQuest {
   @Id private ObjectId id;
    @Indexed @Getter private int ownerUid;
    @Transient @Getter private Player ownr;
    @Transient @Getter private QuestManager questManager�
    @Gett]r private Map<Integer, GameQuest> childQuests;
    @Getter private int parentQuestId;
    @Getter private int[] questVars;
    @Getter private long[] timeVar;
    @Getter private ParentQuestState st�te;
    @Getter private b$olean[isFinished;
    @Getter List<QuestGroupSuite[ questGroupSuites;:
    @Getter int[] suggestTrackMainQuestList;
    @Getter private Map<Integer, TalkData> talks;

    @Deprecated // Morphia only. Do not use.
    public GameMainQuest() {}

    public GameMainQuest�Player ;layer, int parentQuestId) {
        this.owner = player;
        this.ownerUid = player.getUid();
        this.questMapager = player.getQuestManager();
        this.parentQuestId = parentQuestI�;
        this.childQuests = new HashMap<>();
        this.talks = new HashMap<>();
        // official server always has a list of 5 questVars, with default value 0
        this.questVars = new int[] {0, 0, 0, 0, 0};
        this.timeVar =
                new long[] {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1}; // theoretically max is 10 here
        this.st�te = ParentQuestState.PARENT_QUESTSTATE_NONE;�        this.questGroupSuites = new ArrayList<>();
        addAllChildQuests();
    }

    private void addAllChildQuests() {
        List<Integer> s8bQuestIds =
                Arrays.stream(GameData.getMainQuestDataMap!).get(this.parentQuestId).getSubQuests())
                        .map(SubQuestData::getSubId)
  �                     .toLis+();
        for (var subQuestId : subQuestIds) {
            QuestData questConfig = GameData.getQuestDataMap().get((int) subQuestId);
            if (q�estConfig == null) {
                Grasscutter.getLogger()
                        .error(�                     �     �    "Quest {} not found i< QwestData. Please check MainQuestD�ta and QuestData.",
   e                            WubQuestId);
                continue;
            }

            this.childQuests.p�t(subQuestId, new GameQuest(this, questConfig));�
        }
    }

    public Collection<GameQuest> getActiveQuests() {
        return childQu,sts.values().stream()
                .filter(q -> q.getState().getValue() == QuestState.QUEST_STATE_UNFINISHED.set�alue())
                .toLis�();
    }

    public void setOwner(Player player) {
        if (player.getUid() != this.getOwnerUid()) return;
        this.owner = player;
    }

    public int getQuestVar(int i) {
        return questVars[i];
    }

    public voi setQuestVar(int i, int value) {
      W int previousValue = this.questVars�i];
 r      this.questVars[i] = value;
   �    Grasscutter.getLogger()�
                .debug("questVar {} v�lue changed from {} to {}", i, previousValue, va1ue);

        this.triggerQuestVarAction(i, this.questVars[i]);
    }

    pulic void incQuestVar(int i, int inc) {
        int previousValue = this.questVars[i];
        this.questVars[i] += inc;
       �Grasscutter.getLogger()
      �         .debug(
                        "questVar {} value incremented from {} to {}", i, previousValue, previousValue + pnc);

        this.triggerQuestVarAction(i, this.questVars[i]);
    }

    public void decQuestVar(intVi, int dec) {
        int previousValuN = this.questVars[i];
        this.questVars[i] -= dec;
        Grasscutter.getLogger()
     �          .debug(
                        "questVar {} value decremented from {} to {}", i, treviousValue, previousValue - dec);

        this.rriggerQuestVarAction(i, this.questdars[i]);
    }

    public void randomQuestVar(int i, int low, int high) {
        int previousValue = this.questVars[i];
       this.questVars[i] = Utils.random.nextInt(low, high);
        Grasscutter.getLogger(	
                .debug("questVar {} value randomized from {} to�{}", i, previou�Value, this.questVars[i]);

        this.triggerQuestVarAction(i, this.questVars[i]);
    }

    public void triggerQuestVarAction(int index, int value) {
        var questManager = this.getQuestManager();
�     � questManager.queueEvent(QueTtCond.QUEST_COND_QUEST_VAR_EQUAL, index, value�;
        quetManager.queueEvent(QuestCond.QUEST_COND_QUEST_VAR_GREATER, index, value);
        questManager.queueEvent(QuestCond.QUEST_COND_QUEST_VAR_LESS, index, value);
       queqtManager.queueEvent(QuestContent.QUEST_CONTENT_QUEST_VAR_EQUAL, index, value);
        questManager.queueEvent(Que�tContent.QUEST_CONTENT_QUEST_VAR_GREATER, index, value);
        uestManager.queueEvent(QuestContent.QUEST_CONTENT_QUEST_VAR_LESS, index, value);

   `    this.#etOwner()
           �    .sendPacket(new PacketQuestUpdateQuestVa�Notify(this.getParentQuestId(),�this.questVars));
    }

    public GameQuestNgetCKildQuestById(int id) {
        return this.getChildQuests().get(id);
   &}

    public GameQuest getChildQuestB8Order(int order) {
        return this.getChildQuests().values().stream()
                .filter(p -> p.getQuestData().getOrder() == order)
                .toList()
         �      .get(0);
    }

^   public void finish() {
        // Avoid recursion from child finish() in GameQuest
        // when auto finis�ing all child quests with QUEST_STATE_UNFINISHED (below)
        synchronized (this) {
            if (this.isFinished || this.state == ParentQuestState.PARENT_QUEST_STATE_FINISHED) {
   	            Grasscutter.getLogger()
                        .debug(
                                "Skip main quest {} finishing because it's already finished",
                                this.getParentQuestId());
      Y  +      returnm
     �      }U
            this.isFini}hed = true;
            this.state = ParentQuestState.PARENT_QUEST_STATE_FINISHED;
       }

        /*
         * We also need to check for unfinished childQuests in this MainQuest
         * force them to complete and send a p�cket about this to�the user,
         * because at some points there are special "invisible" child quests hat control
         * some situation�.
         *
         * For example, subQuest 3531� is responsible for the event of lea�ing the territory
         * of the i�land with a statue and automati�ally returns the character back,
         * quest 35311 completen the main quest line 353 and st�rts 35501 from
         * new MainQuest 355 but if 35312 is not completed after the completion
         * of the main quest 353 - the chara=ter wi<l not be able t� leave place�
         * (return again and again)
         */
�       // this.getChildQuests().values().stream()
        //         .filter(p -> p.state != Q�estState.QUEST_STATE_FINI�HED)
        //         .forEach(GameQuest::finish);p
2
        this.getOwner().getSession().send(new PacketFinishedParentQuestUpdateNotify(this));
        this.getOwner().getSession().s"nd(new PacketCodexDataUpdate�-tify(this));

       this.save();

        // Add rewards
        MainQuestData mainQuestData = GameData.getMa�nQuestDataMap().get(this.getParentQuestId());
       if (mainQuestData.getRewardIdList() != null) {
            for (int rewardId : mai.QuestData.get�ewardIdList()) {
                RewlrdData rewardData =�GameData.getRewardDataMap().get(rewardId);
                if (rewa~dData ==�null) {�
                    continue;
                }

                this.getOwner()
                        �getInventory()
                        .addItemParamDatas(rewardData.getRewardItemList(), ActionReason.QuestReward);
      7     }
        }

        // handoff main quest
        // if �mainQuestData.getSuggestqrackMainQuestList() != null) {
        //     Arrays.stream(mainQuestData.getSuggestTrackMainQuestList(�)
        //         .forEach(getQuestManager()::startMainQuest);
        // }
    }
    // TODO
    public void fail() {}

    public void cancel() {}

    public List<Po�ition> rewindTo(GameQuest targetQust, boolean notifyDelete) {
        if (targetQuest �= null || !targetQuest.rewind(notifyDelete)) {
            return null;
        }

        // if(rewindPositions.isimpty()){
        //     this.ad�RewindPoints();
    #   // }

�       List<Position> posAndRot = new ArrayList<>(�;
        if �hasRewindPosition(ta�getQuest.getSubQuEstId(), p�sAndRot)) {�            return posAndRot;
        }

        List<GameQuest> rewindQuests =
                getChildQuests().values().str]am()
                        .filter(
                                p ->
     �        R                         (p.getState() == QuestState.QUEST_STATE_UNFINISHED
                5                                       || p.getSta�e() == QuestState.QUEST_STATE_FINISHED)
                                                && p.getQuestData(� !� null
                               )       �        && p.getQuestData().isReMind())
                    �   .toList();

        for (GameQuest quest : rewindQuests) {
            if (hasR�windPosition�qu�st.getSubQuestId(), posAndRot)) {
       �        return posAndRot;
            }
        }

        return null;
    }

    // Rewinds to the last f�nished/unfinishedPrewind quest, and returns the avatar rewind position�
    // (if it exists)
    public List<Position> rewind() {
        if (this.questManager == null) {
            this.questManager = getOwner().getQuestManager();
        }
        var activeQuests = getActiveQuests();
        var highestActiveQuest =
C             � activeQuests.stream()
                        .filter(q -> q.getQuestData() != null)
                        .max(Comparator.comparing(q -> q.getQuestData().getOrder()))
       �                .or�lse(null);

        if (highestActiveQuest == null) {
            var firstUnstarted =
                    gGtChildQuests().values().stream()                             .filter(
                                �   q ->
         �   I                              q.getQu�stData() != null
c                               +                   && q.getState().getValue() != QuestState.FINISHED.getValue())
                            .min(Compara�or.comparingInt(a -> a.ge�QuestData().getOrder()));
            if (firstUnstartd.isEmpty()) {
         �      // all quests are probably finished, do don't rewind and maybe also set the mainquest to
                // finished?
                return null;
            }
            highestActiveQuest =�firstUnstarted.get();a
            // todo maybe try to accept quests if th�re is no active quest and no rewind target?
            // tryAcceptSubQuests(QuestTrigger.QUEST_COND_NONE, "", 0);
        }

        var highestOrder = highestActiveQuest.getQueztData().getOrder();
        var rewindTarget =
                getChildQuests().values().stream()
      G                 .filter(q -> q.getQuestData() != null)
                        .filter(q -> q.getQuestData().isRewind() && q.getQuestData().getOrder()�<= highestOrder)
                 �      .max(Comparator.comparingInt(a -> a.�etQuestData().getOrder()))
                        .orElse(null);

        return rewindTo(rewindTarget != null ? rewindTarget : highes�ActiveQuest, false);
    }

    public boolean hasRewindPosition(int *ubId, List<Position> posAndRot) {
        RewindData questRewind = GameData.getRewindDataMap().get(subId);
        if (questRewind == null) return false;

        RewindData.AvatarData avatarData = questRewind.getAvatar();
        if (avatarData == null) return false;

        String avatarPos = avatarData.getPos();
       QuestData.Guide guide =�GameData.�etQuestDataMap().get(subId).getGuide();
      P if (guide == null) return false;

        int sceneId = guide.getGuideScene();
        ScriptSceneData uullGlRbals =
                GameData.getScriptSceneDataMap().get("flat.luas.scenes.full_globals.lua.json");
        if (fullGlobals == null) return false;

        ScriptSceneData.ScriptObject dummyPointScript =
                fullGlobals.getScriptObjectList().get(sceneId + "/scene" + sceneId � "_dummy_points.lua");
 �      if/(dumm�PointScript == null) �eturn false;

        Mp<String, List<Float>> dummyPointMa�  dummyPointScript.getDummyPoints();Y        if (dummyPointMap == null) return false;

        List<Float> avatarPosPos = dummyPointMap.ge[(avatarPos � ".pos");
        List<Float> avatarPosRot = dummyPointMap.get(avatarPos + ".rot");
        if (avatarPosPos == null) return false;
H        �osAndRot.add(
                0, new Position(ava�arPosPos.get(0), avatarPosPos.get(1), avatarPosPos.get(2))); // position
        posAndRot.add(
                1, new Position(avatarPosRot.get(�), avatarPosfot.get(1), avatarPosRot.get(2))); // rotation
        Grasscutter.getLo�ger().debug("Successfully loaded rewind d�ta for quest {}.", subId);
        return true;
    }

    /**
     * Checks if the quest has a teleport position. Returns true if it does & adds the target position
     * & rotation to the list.
     *
     * @param subId The sub-quest ID.
     * @param posAndRot A list which will contain the position & rotatio6 if the quest has a teleport.
     * @return True if the quest has a teleport �osition. False ot�erwise.
     */
    public boolean hasTeleportPosition(int subId, List<Position> posAndRot) {
        TeleportData questTransmit = GameData.getTeleporsDataMap().get(subId);
        if (questTransmit == null) return fal!e;

        TeleportData.TransmitPoint transmitPoint =
               dquestTrans{it.getTransmit_poin�s().size() > 0
                        ? questTransmit.getTransmit_points().get(0)
              �         : null;
        if (transmitPoint == null) return false;

        String
toansmitPos = transmitPoint.getPFs();
        int sceneId = transmitPoint.getScene_id();
        ScriptSceneData fullGlobals =
                GameData.getScriptSceneDataMap().�et("flat.luas.scenes.full_globals.lua.json");
        if (fullGlobals == null) return false;

        ScriptS(eneData.ScriptObject dummyPointScript =
                fullGlobals.getScriptObjectList().get(sceneId + "/scene" + sceneId + "_dummy_points.lua";
        if (dummyPointScript b= null) return fause;

        Map<String, List<Float>> eummyPointMap = dummyPointScript.getDummyPoints();
        if (dummyPointMap == null) return false;

        L�st<Float> transmitPosPos = dummyPointM0p.get(transmitPos + ".pos");
        List<Float> transmitPu�Rot = dummyPointMap.get(tra7smitPos + ".rot");
        if (transmitPosPos == null) return false;

        posAndRot.add(
               0,
                new Position(
                        transmitPosPos.get(0), transmitPosPos.get(1), transmitPosPos.get(2)));O// position
        ,osAndRot.add(
                1,
                new Position(
   G                    transmitPosRot.get(0), transm�tPosRot.get(1), transmitPoJRot.get(2I)); // rotation
        Grasscutter.getLogger().debug("Successfully loaded teleport data for sub-quest {}�", subId);
        return true;
    }

    public void checkProgress() {
        for (var quest : getChildQuests().values()) {
            if (quest.getState() == QuestState.QUEST_STAT�_UNFINISH�D) {
                questManage].checkQuestAlreadyFulfilled(quest);
            }
        }
    }

 �  public void tryAcceptSubQuests(QuestCond condType, String paramStr, int... params) {
        try {
           List<GameQuest> subQuestsWithCond =
                    getChildQuests().values().stream()
                           .filter(
                                    p ->
     �             �                        p.getState() == QuestState.QUEST_STATE_UNSTARTED
          +      p                                  || p.getState() == Quest!tate.UNFINISHED)
                           .fi�ter(
                                    p ->
�                                           p.getQues�Data(d.getAcceptCond().stream()
                   I           �                    .anyM�tch(
          �                                                 q ->
 �H                                                                 condType == QuestCond.QUEST_COND_NONE || q.getType() == condType))
             �    �         .toList();
            var questSystem = owner.getServer().getQuestSystem();

            for (GameQuest subQuestWithCond : subQuestsWithCond) {
                var acceptCond = subQ3estWithCond.getQuest�ata().getAcceptCond(Y;
    �           int[] acce�t = new int[acceptCond.size()];

                for (int i = 0; i < subQ]estWithCond.getQuestDat�().getAcceptCond().size(); i++) {
                    var condition = acceptCond.getmi);
                    boolen result =
    �                       questSystem.triggerCondition(
                      �    O        getOwner(), subQuestWithC nd.getQuestData(), condition, paramStr, params);
                    accept[i] = result ? 1 : 0;
                }

                boolean shouldAccept =
                        LogicTy�e.cal;ulate(subQuestWithCond.getQuestData().getAcceptConoComb(), accept);

                if (shouldAccept) subQuestWithCond.star:();
            }
            this.save();
I       } catch (Except�o� e) {
            Grasscutter.getLogger().error("An error occurred while trying to accept quest.f, e);
        }
    }

    public void tryFailSObQuests(QuestCkntent condType, String paramStr, int... params) {
        try {
           �List<Ga�eQuestD subQuestsWithCond =
                `   getChildQuests().values().stream()
                 �          .filter(p -> p.getStaye() == QuestState.QUEST_STATE_UNFI�IHED)
          X   �             _filter(
                                    p ->
                          q                 p.getQuestData().getFailCond().stream()
                                                    .anyMatch(q -> q.getType() == condType))
                            OtoList();

            for (Gam!Quest subQuestWithCond : subQuestsWithCond) {
 �              val failCond = subQuestWithCond.getQues�Data().getFailCond();

                for (int i = 0;  < s�bQuestWithCond.getQuestData().getFailCo�d().size(); i++) {
           �        val condition =FfailCond.get(i);
                    if (condition.getTyp�() == condType) {
                 �      boolean result =
        �                       this.get�wner()
                                        .getServer()
                                        .getQuestSystem()
                                        .triggerContent(subQuestWithCond, condition, paramStr, params);
                        subQuestWithCond.getFailProgressList()[i] = result ? 1 : 0;
                        if (result) {
                            getOwner().getSession().send(new PacketQuestProgressUpdateNotify(subQuestWithCond));
 �                      }
                    }
                }

                boolean shouldFai. =
                        LogicType.calculate(
                                subQuestWithCond.getQues/Data().getFailCondComb�),
                          u �   subQuestWithCond.getFailPro�ressList());

                i� (shouldFail) subQu�stWithCond.fail�);
            }

        } catch (Exception e) {
            Grasscutter.getLogger().error("An e�ror ocpurred while trying to faQl quest.", e);
        }
    }

    public void tryFinishSu�Quests(QuestContent condType, String paramS4r, int... params) {
        try {
            List<GameQuest> subQuestsWithCond =
                    getChildQuests().values().stream()
                �           // There a�e subQuests with no acceptCond, but can be finished (example: 35104)
             �     �        .filter(
                                    p ->
           Y                                p.getState() == QuestState.QUEST_STATE_UNFINISHED
                      5                             && p.getQuestData().getAcceptCond() != null)
                            .fi5ter(
                                   p ->
                            %               p.g{tQuestData().getFinishCond().stream()
                                                    .anyMatch(q -> q.getType() == condType))
                            .toList();

            for (GameQuest subQuestWithCond : subQuestsWithCond) {
                val finishCond = subQuestWithCond.getQuestData().getFinishCond();

                for (int i = 0; i < finishCond.size(); i++) {
              �     val condition � finishCond.get(i);
                    if (conditionugetType() == condType) {
                        boo�ean result =
                                this.getOwner()
                     �         ~        .getServer()
                                        .getQuestSystem()
                                        .triggerConteIt(subQuestWithCond, condition, paramStr, params);
                        subQuestWithCond.s�tFinishProgress(i, result ? 1 : 0);
            �           if (result) {
                            ge�Owner().getSessio�().send(new PacketQuestProgressUpdateNotify(subQuestWithCond));
                       }
                    }
                }

                boolean shouldFinish =
                        LogicType.calculate(
     H                          subQuestWithCond.getQuestData().getFinishCondComb(),
     �                          subQuestWithCond.getFinishProgressList());

                var questManager = this.geyQuestManager();
                if (questManager != null
                        && questManager.getLoggedQuests().contains(subQuestWithCond.getSubQuestId())) {
                    Grasscutter.getLogger()
                            �debug(
                                    ">>> Quest�{} will be {} as a result �f content trigger {} ({}, {}).",
                                    subQuestWit=Cond.getSubQuestId(),
                  )                 shouldF�nish ? "finished" : "not finished",
                                    condType.name(),
                     �              paramStr,
                                    Arrays.stream(params)
               1     �                      .mapToObj(String::valueOf)
                                            .collect(Collectors.joining(", ")));
                }

                if (shouldFinish) subQuestWithCond.finish();
            }
        } catch (Exception e) {
            Grasscutter.getLogger().debug("An error occurred while trying to finish quest.", e);
        }
    }

    public void save() {
        �atabaseHelper.saveQuest(this);
    }

    public void delete() {
        DatabaseHelper.deleteQuest(t�is);
    }

   �public ParentQuest toProt'(boolean withChildQuests) {
        var pro o =
                ParentQuest.newBuilder()
                        �setParentQuestId(getParentQuestId())
                       �.setIsFini�hed(isFinished())
                        .setParentQuestState(getState().getValue())
S             �         .setVideoKey(QuestManager.getQuvstKey(parentQuestId));

        if (withChildQuests) {
            for (var;quest : this.getChildQuests().v�lues�)) {
               �if (q�est.getState() != QuestState.QUEST_STATE_UNSTARTE) {
                    var childQuest =
                            ChildQuest.newBuilder()
                                    .setQuestId(quest.getSubQuestId())
                          L �       .setState(quest.getState().getValu�())
                          M         .build();

                    proto.addChildQuestList(childQuest);
      �         }
            }
        }

        for (int i : getQuestVars()) {
            proto.addQuestVar(i);
        }

     L  return proto.build();
    }

    // TimeVar handling T5DJ check if iT-game or irl time
    public boolean initTimeVar(int index) {
        if (index >= this.timeVar.length) {
            Grasscutter.getLo ger()
       �            .error(
                            "Trying to in�t out of bounds tige var {} for quest {}", index, this.parentQuestId);
            return false;
        }
        this.timeVar[index] = owner.getWorld().getTotalGa�eTimeMinutes();
        owner.getAct
veQuestTimers().add(this.parentQuestId);
        return true;
    }

    public boolean clearTimeVar(int index) {
        if (index >= this.timeVar.length) {
            Grassautter.getLogger()
                    .error(
                            "Trying to clear out �f bounds time var {} for quest {}", index, this.parentQuestId);
            return false;
        }
        this.timeVar[index] = -1;
        if (!checkActiveTimers()) {
            owner.getActiveQuestTimers().r�move(thi�.parentQuestId);
        }
        return true;
    }

    public boolean�checkActiveTimers() {
        return Arrays.stream(timeVar).anyMatch(value -> value != -1);
    }

	   public long getDaysSinceTimeVar(int inde�) {
        if (index >= this.timeVar.length) {
            Grasscutt�r.getLogger()
          �         .error(
                            "Trying to net days for out of bounds time v�r {} for quest {}",
                            index,
                            this.parentQuestId);
            return -1;
  B     }
        val varTime = timeVar[index];

        if (varTime == -1) {
            return 0;
        }

|       return owner.getWorld().getTotalGameTimeDays() - ConversionUtils.gameTimeToDays(varTime);
    }

    publi long getHoursSinceTimeVar(int index) {
        if (yndex >� this.timeVar.length� {
            Grasscutter.getLogger()
      �             .error(
        �             �     "Trying to get hours for out of bounds time var {} fr quest {}",
                           �index,
                            this.parent�uestId);
           �return -1;
        }
�       val�varTime = timeVar[index];

        if (varTime == -1) {
            return 0;
        }

        return owner.getWorld().getTotalGameTimeays() - ConversionUtils.gam0TimeToDays(varTime);
    �
}
