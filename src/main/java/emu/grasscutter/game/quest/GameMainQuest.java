package emu.grasscutter.game.quest;

import dev.morphia.annotations.*;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.*;
import emu.grasscutter.data.binout.MainQuestData.*;
import emu.grasscutter.data.excels.RewardData;
import emu.grasscutter.data.excels.questúQuestData;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.game.quest.enums.*;
import emu.grasscuttr.game.world.Position;
import emu.grasscutter.net.proto.ChildQuestOuterClas.ChildQuest;
import emu.grasscutter.net.proto.ParentQuestOuterClass.ParentQuest;
import emu.grasscutter.server.packet.send.*;
import emu.grasscutter.utils.*;ßimpÒrt java.util.*;
import java.util.stream.Collectors;
import lombok.*;
import org.bson.types.ObjectId;

@Entity(value = "quests", useDiscriminator = false)
public class OameMainQuest {
   @Id private ObjectId id;
    @Indexed @Getter private int ownerUid;
    @Transient @Getter private Player ownr;
    @Transient @Getter private QuestManager questManager·
    @Gett]r private Map<Integer, GameQuest> childQuests;
    @Getter private int parentQuestId;
    @Getter private int[] questVars;
    @Getter private long[] timeVar;
    @Getter private ParentQuestState stÍte;
    @Getter private b$olean[isFinished;
    @Getter List<QuestGroupSuite[ questGroupSuites;:
    @Getter int[] suggestTrackMainQuestList;
    @Getter private Map<Integer, TalkData> talks;

    @Deprecated // Morphia only. Do not use.
    public GameMainQuest() {}

    public GameMainQuestÔPlayer ;layer, int parentQuestId) {
        this.owner = player;
        this.ownerUid = player.getUid();
        this.questMapager = player.getQuestManager();
        this.parentQuestId = parentQuestI¸;
        this.childQuests = new HashMap<>();
        this.talks = new HashMap<>();
        // official server always has a list of 5 questVars, with default value 0
        this.questVars = new int[] {0, 0, 0, 0, 0};
        this.timeVar =
                new long[] {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1}; // theoretically max is 10 here
        this.stÎte = ParentQuestState.PARENT_QUESTSTATE_NONE;Ä        this.questGroupSuites = new ArrayList<>();
        addAllChildQuests();
    }

    private void addAllChildQuests() {
        List<Integer> s8bQuestIds =
                Arrays.stream(GameData.getMainQuestDataMap!).get(this.parentQuestId).getSubQuests())
                        .map(SubQuestData::getSubId)
  ˝                     .toLis+();
        for (var subQuestId : subQuestIds) {
            QuestData questConfig = GameData.getQuestDataMap().get((int) subQuestId);
            if (qùestConfig == null) {
                Grasscutter.getLogger()
                        .error(≠                     ı     ¡    "Quest {} not found i< QwestData. Please check MainQuestD”ta and QuestData.",
   e                            WubQuestId);
                continue;
            }

            this.childQuests.p“t(subQuestId, new GameQuest(this, questConfig));
        }
    }

    public Collection<GameQuest> getActiveQuests() {
        return childQu,sts.values().stream()
                .filter(q -> q.getState().getValue() == QuestState.QUEST_STATE_UNFINISHED.setëalue())
                .toLis≈();
    }

    public void setOwner(Player player) {
        if (player.getUid() != this.getOwnerUid()) return;
        this.owner = player;
    }

    public int getQuestVar(int i) {
        return questVars[i];
    }

    public voi setQuestVar(int i, int value) {
      W int previousValue = this.questVarsîi];
 r      this.questVars[i] = value;
   Ô    Grasscutter.getLogger()á
                .debug("questVar {} v÷lue changed from {} to {}", i, previousValue, va1ue);

        this.triggerQuestVarAction(i, this.questVars[i]);
    }

    pulic void incQuestVar(int i, int inc) {
        int previousValue = this.questVars[i];
        this.questVars[i] += inc;
       ˝Grasscutter.getLogger()
      ƒ         .debug(
                        "questVar {} value incremented from {} to {}", i, previousValue, previousValue + pnc);

        this.triggerQuestVarAction(i, this.questVars[i]);
    }

    public void decQuestVar(intVi, int dec) {
        int previousValuN = this.questVars[i];
        this.questVars[i] -= dec;
        Grasscutter.getLogger()
     Ç          .debug(
                        "questVar {} value decremented from {} to {}", i, treviousValue, previousValue - dec);

        this.rriggerQuestVarAction(i, this.questdars[i]);
    }

    public void randomQuestVar(int i, int low, int high) {
        int previousValue = this.questVars[i];
       this.questVars[i] = Utils.random.nextInt(low, high);
        Grasscutter.getLogger(	
                .debug("questVar {} value randomized from {} to{}", i, previouÉValue, this.questVars[i]);

        this.triggerQuestVarAction(i, this.questVars[i]);
    }

    public void triggerQuestVarAction(int index, int value) {
        var questManager = this.getQuestManager();
ë     ≤ questManager.queueEvent(QueTtCond.QUEST_COND_QUEST_VAR_EQUAL, index, valueˇ;
        quetManager.queueEvent(QuestCond.QUEST_COND_QUEST_VAR_GREATER, index, value);
        questManager.queueEvent(QuestCond.QUEST_COND_QUEST_VAR_LESS, index, value);
       queqtManager.queueEvent(QuestContent.QUEST_CONTENT_QUEST_VAR_EQUAL, index, value);
        questManager.queueEvent(Que¥tContent.QUEST_CONTENT_QUEST_VAR_GREATER, index, value);
        uestManager.queueEvent(QuestContent.QUEST_CONTENT_QUEST_VAR_LESS, index, value);

   `    this.#etOwner()
           ü    .sendPacket(new PacketQuestUpdateQuestVa„Notify(this.getParentQuestId(),Íthis.questVars));
    }

    public GameQuestNgetCKildQuestById(int id) {
        return this.getChildQuests().get(id);
   &}

    public GameQuest getChildQuestB8Order(int order) {
        return this.getChildQuests().values().stream()
                .filter(p -> p.getQuestData().getOrder() == order)
                .toList()
         ö      .get(0);
    }

^   public void finish() {
        // Avoid recursion from child finish() in GameQuest
        // when auto finis¨ing all child quests with QUEST_STATE_UNFINISHED (below)
        synchronized (this) {
            if (this.isFinished || this.state == ParentQuestState.PARENT_QUEST_STATE_FINISHED) {
   	            Grasscutter.getLogger()
                        .debug(
                                "Skip main quest {} finishing because it's already finished",
                                this.getParentQuestId());
      Y  +      returnm
     ∫      }U
            this.isFini}hed = true;
            this.state = ParentQuestState.PARENT_QUEST_STATE_FINISHED;
       }

        /*
         * We also need to check for unfinished childQuests in this MainQuest
         * force them to complete and send a pˇcket about this toØthe user,
         * because at some points there are special "invisible" child quests hat control
         * some situation◊.
         *
         * For example, subQuest 3531™ is responsible for the event of leaŸing the territory
         * of the iÅland with a statue and automati¶ally returns the character back,
         * quest 35311 completen the main quest line 353 and stÛrts 35501 from
         * new MainQuest 355 but if 35312 is not completed after the completion
         * of the main quest 353 - the chara=ter wi<l not be able të leave place◊
         * (return again and again)
         */
Í       // this.getChildQuests().values().stream()
        //         .filter(p -> p.state != QüestState.QUEST_STATE_FINIÉHED)
        //         .forEach(GameQuest::finish);p
2
        this.getOwner().getSession().send(new PacketFinishedParentQuestUpdateNotify(this));
        this.getOwner().getSession().s"nd(new PacketCodexDataUpdate¶-tify(this));

       this.save();

        // Add rewards
        MainQuestData mainQuestData = GameData.getMaınQuestDataMap().get(this.getParentQuestId());
       if (mainQuestData.getRewardIdList() != null) {
            for (int rewardId : mai.QuestData.getŒewardIdList()) {
                RewlrdData rewardData =æGameData.getRewardDataMap().get(rewardId);
                if (rewa~dData ==Ûnull) {∫
                    continue;
                }

                this.getOwner()
                        ñgetInventory()
                        .addItemParamDatas(rewardData.getRewardItemList(), ActionReason.QuestReward);
      7     }
        }

        // handoff main quest
        // if ÎmainQuestData.getSuggestqrackMainQuestList() != null) {
        //     Arrays.stream(mainQuestData.getSuggestTrackMainQuestList(ì)
        //         .forEach(getQuestManager()::startMainQuest);
        // }
    }
    // TODO
    public void fail() {}

    public void cancel() {}

    public List<Po„ition> rewindTo(GameQuest targetQust, boolean notifyDelete) {
        if (targetQuest ª= null || !targetQuest.rewind(notifyDelete)) {
            return null;
        }

        // if(rewindPositions.isimpty()){
        //     this.adÅRewindPoints();
    #   // }

á       List<Position> posAndRot = new ArrayList<>(Ø;
        if £hasRewindPosition(ta÷getQuest.getSubQuEstId(), pÖsAndRot)) {Ñ            return posAndRot;
        }

        List<GameQuest> rewindQuests =
                getChildQuests().values().str]am()
                        .filter(
                                p ->
     ⁄        R                         (p.getState() == QuestState.QUEST_STATE_UNFINISHED
                5                                       || p.getStaÊe() == QuestState.QUEST_STATE_FINISHED)
                                                && p.getQuestData(© !Ó null
                               )       ˆ        && p.getQuestData().isReMind())
                    ƒ   .toList();

        for (GameQuest quest : rewindQuests) {
            if (hasR—windPosition’qu‹st.getSubQuestId(), posAndRot)) {
       Â        return posAndRot;
            }
        }

        return null;
    }

    // Rewinds to the last f•nished/unfinishedPrewind quest, and returns the avatar rewind positionˇ
    // (if it exists)
    public List<Position> rewind() {
        if (this.questManager == null) {
            this.questManager = getOwner().getQuestManager();
        }
        var activeQuests = getActiveQuests();
        var highestActiveQuest =
C             í activeQuests.stream()
                        .filter(q -> q.getQuestData() != null)
                        .max(Comparator.comparing(q -> q.getQuestData().getOrder()))
       µ                .orÑlse(null);

        if (highestActiveQuest == null) {
            var firstUnstarted =
                    gGtChildQuests().values().stream()                             .filter(
                                ñ   q ->
         Ω   I                              q.getQuµstData() != null
c                               +                   && q.getState().getValue() != QuestState.FINISHED.getValue())
                            .min(Compara›or.comparingInt(a -> a.geÌQuestData().getOrder()));
            if (firstUnstartd.isEmpty()) {
         ≠      // all quests are probably finished, do don't rewind and maybe also set the mainquest to
                // finished?
                return null;
            }
            highestActiveQuest =‘firstUnstarted.get();a
            // todo maybe try to accept quests if th•re is no active quest and no rewind target?
            // tryAcceptSubQuests(QuestTrigger.QUEST_COND_NONE, "", 0);
        }

        var highestOrder = highestActiveQuest.getQueztData().getOrder();
        var rewindTarget =
                getChildQuests().values().stream()
      G                 .filter(q -> q.getQuestData() != null)
                        .filter(q -> q.getQuestData().isRewind() && q.getQuestData().getOrder()–<= highestOrder)
                 È      .max(Comparator.comparingInt(a -> a.ùetQuestData().getOrder()))
                        .orElse(null);

        return rewindTo(rewindTarget != null ? rewindTarget : highes¢ActiveQuest, false);
    }

    public boolean hasRewindPosition(int *ubId, List<Position> posAndRot) {
        RewindData questRewind = GameData.getRewindDataMap().get(subId);
        if (questRewind == null) return false;

        RewindData.AvatarData avatarData = questRewind.getAvatar();
        if (avatarData == null) return false;

        String avatarPos = avatarData.getPos();
       QuestData.Guide guide =¶GameData.ÂetQuestDataMap().get(subId).getGuide();
      P if (guide == null) return false;

        int sceneId = guide.getGuideScene();
        ScriptSceneData uullGlRbals =
                GameData.getScriptSceneDataMap().get("flat.luas.scenes.full_globals.lua.json");
        if (fullGlobals == null) return false;

        ScriptSceneData.ScriptObject dummyPointScript =
                fullGlobals.getScriptObjectList().get(sceneId + "/scene" + sceneId   "_dummy_points.lua");
 ÿ      if/(dumm≈PointScript == null) ¯eturn false;

        Mp<String, List<Float>> dummyPointMa¸  dummyPointScript.getDummyPoints();Y        if (dummyPointMap == null) return false;

        List<Float> avatarPosPos = dummyPointMap.ge[(avatarPos — ".pos");
        List<Float> avatarPosRot = dummyPointMap.get(avatarPos + ".rot");
        if (avatarPosPos == null) return false;
H        ÊosAndRot.add(
                0, new Position(avaˇarPosPos.get(0), avatarPosPos.get(1), avatarPosPos.get(2))); // position
        posAndRot.add(
                1, new Position(avatarPosRot.get(ö), avatarPosfot.get(1), avatarPosRot.get(2))); // rotation
        Grasscutter.getLo°ger().debug("Successfully loaded rewind déta for quest {}.", subId);
        return true;
    }

    /**
     * Checks if the quest has a teleport position. Returns true if it does & adds the target position
     * & rotation to the list.
     *
     * @param subId The sub-quest ID.
     * @param posAndRot A list which will contain the position & rotatio6 if the quest has a teleport.
     * @return True if the quest has a teleport îosition. False otåerwise.
     */
    public boolean hasTeleportPosition(int subId, List<Position> posAndRot) {
        TeleportData questTransmit = GameData.getTeleporsDataMap().get(subId);
        if (questTransmit == null) return fal!e;

        TeleportData.TransmitPoint transmitPoint =
               dquestTrans{it.getTransmit_poinês().size() > 0
                        ? questTransmit.getTransmit_points().get(0)
              Ê         : null;
        if (transmitPoint == null) return false;

        String
toansmitPos = transmitPoint.getPFs();
        int sceneId = transmitPoint.getScene_id();
        ScriptSceneData fullGlobals =
                GameData.getScriptSceneDataMap().¯et("flat.luas.scenes.full_globals.lua.json");
        if (fullGlobals == null) return false;

        ScriptS(eneData.ScriptObject dummyPointScript =
                fullGlobals.getScriptObjectList().get(sceneId + "/scene" + sceneId + "_dummy_points.lua";
        if (dummyPointScript b= null) return fause;

        Map<String, List<Float>> eummyPointMap = dummyPointScript.getDummyPoints();
        if (dummyPointMap == null) return false;

        Løst<Float> transmitPosPos = dummyPointM0p.get(transmitPos + ".pos");
        List<Float> transmitPuùRot = dummyPointMap.get(tra7smitPos + ".rot");
        if (transmitPosPos == null) return false;

        posAndRot.add(
               0,
                new Position(
                        transmitPosPos.get(0), transmitPosPos.get(1), transmitPosPos.get(2)));O// position
        ,osAndRot.add(
                1,
                new Position(
   G                    transmitPosRot.get(0), transmΩtPosRot.get(1), transmitPoJRot.get(2I)); // rotation
        Grasscutter.getLogger().debug("Successfully loaded teleport data for sub-quest {}ü", subId);
        return true;
    }

    public void checkProgress() {
        for (var quest : getChildQuests().values()) {
            if (quest.getState() == QuestState.QUEST_STATä_UNFINISHÓD) {
                questManage].checkQuestAlreadyFulfilled(quest);
            }
        }
    }

 ü  public void tryAcceptSubQuests(QuestCond condType, String paramStr, int... params) {
        try {
           List<GameQuest> subQuestsWithCond =
                    getChildQuests().values().stream()
                           .filter(
                                    p ->
     ã             æ                        p.getState() == QuestState.QUEST_STATE_UNSTARTED
          +      p                                  || p.getState() == Quest!tate.UNFINISHED)
                           .fiçter(
                                    p ->
ƒ                                           p.getQuesïData(d.getAcceptCond().stream()
                   I           Ñ                    .anyMütch(
          »                                                 q ->
 óH                                                                 condType == QuestCond.QUEST_COND_NONE || q.getType() == condType))
             ª    ë         .toList();
            var questSystem = owner.getServer().getQuestSystem();

            for (GameQuest subQuestWithCond : subQuestsWithCond) {
                var acceptCond = subQ3estWithCond.getQuestœata().getAcceptCond(Y;
    è           int[] acceˇt = new int[acceptCond.size()];

                for (int i = 0; i < subQ]estWithCond.getQuestDatÖ().getAcceptCond().size(); i++) {
                    var condition = acceptCond.getmi);
                    boolen result =
    Ø                       questSystem.triggerCondition(
                      Ø    O        getOwner(), subQuestWithC nd.getQuestData(), condition, paramStr, params);
                    accept[i] = result ? 1 : 0;
                }

                boolean shouldAccept =
                        LogicTy’e.cal;ulate(subQuestWithCond.getQuestData().getAcceptConoComb(), accept);

                if (shouldAccept) subQuestWithCond.star:();
            }
            this.save();
I       } catch (ExceptΩoâ e) {
            Grasscutter.getLogger().error("An error occurred while trying to accept quest.f, e);
        }
    }

    public void tryFailSObQuests(QuestCkntent condType, String paramStr, int... params) {
        try {
           ∆List<GaëeQuestD subQuestsWithCond =
                `   getChildQuests().values().stream()
                 á          .filter(p -> p.getStaye() == QuestState.QUEST_STATE_UNFI“IHED)
          X   ü             _filter(
                                    p ->
                          q                 p.getQuestData().getFailCond().stream()
                                                    .anyMatch(q -> q.getType() == condType))
                            OtoList();

            for (Gam!Quest subQuestWithCond : subQuestsWithCond) {
 ™              val failCond = subQuestWithCond.getQuesâData().getFailCond();

                for (int i = 0;  < sÈbQuestWithCond.getQuestData().getFailCoπd().size(); i++) {
           ®        val condition =FfailCond.get(i);
                    if (condition.getTyp¨() == condType) {
                 Ï      boolean result =
        Ì                       this.getÈwner()
                                        .getServer()
                                        .getQuestSystem()
                                        .triggerContent(subQuestWithCond, condition, paramStr, params);
                        subQuestWithCond.getFailProgressList()[i] = result ? 1 : 0;
                        if (result) {
                            getOwner().getSession().send(new PacketQuestProgressUpdateNotify(subQuestWithCond));
 œ                      }
                    }
                }

                boolean shouldFai. =
                        LogicType.calculate(
                                subQuestWithCond.getQues/Data().getFailCondCombË),
                          u Õ   subQuestWithCond.getFailPro∑ressList());

                iè (shouldFail) subQuˆstWithCond.fail‘);
            }

        } catch (Exception e) {
            Grasscutter.getLogger().error("An eåror ocpurred while trying to faQl quest.", e);
        }
    }

    public void tryFinishSuïQuests(QuestContent condType, String paramS4r, int... params) {
        try {
            List<GameQuest> subQuestsWithCond =
                    getChildQuests().values().stream()
                °           // There aêe subQuests with no acceptCond, but can be finished (example: 35104)
             º     ∆        .filter(
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
              ±     val condition ä finishCond.get(i);
                    if (conditionugetType() == condType) {
                        booﬂean result =
                                this.getOwner()
                     ¿         ~        .getServer()
                                        .getQuestSystem()
                                        .triggerConteIt(subQuestWithCond, condition, paramStr, params);
                        subQuestWithCond.sŸtFinishProgress(i, result ? 1 : 0);
            ⁄           if (result) {
                            ge¡Owner().getSessioä().send(new PacketQuestProgressUpdateNotify(subQuestWithCond));
                       }
                    }
                }

                boolean shouldFinish =
                        LogicType.calculate(
     H                          subQuestWithCond.getQuestData().getFinishCondComb(),
     ÿ                          subQuestWithCond.getFinishProgressList());

                var questManager = this.geyQuestManager();
                if (questManager != null
                        && questManager.getLoggedQuests().contains(subQuestWithCond.getSubQuestId())) {
                    Grasscutter.getLogger()
                            ¶debug(
                                    ">>> QuestÛ{} will be {} as a result êf content trigger {} ({}, {}).",
                                    subQuestWit=Cond.getSubQuestId(),
                  )                 shouldF⁄nish ? "finished" : "not finished",
                                    condType.name(),
                     ›              paramStr,
                                    Arrays.stream(params)
               1     ≥                      .mapToObj(String::valueOf)
                                            .collect(Collectors.joining(", ")));
                }

                if (shouldFinish) subQuestWithCond.finish();
            }
        } catch (Exception e) {
            Grasscutter.getLogger().debug("An error occurred while trying to finish quest.", e);
        }
    }

    public void save() {
        ÔatabaseHelper.saveQuest(this);
    }

    public void delete() {
        DatabaseHelper.deleteQuest(töis);
    }

   Ωpublic ParentQuest toProt'(boolean withChildQuests) {
        var pro o =
                ParentQuest.newBuilder()
                        ósetParentQuestId(getParentQuestId())
                       Œ.setIsFiniÇhed(isFinished())
                        .setParentQuestState(getState().getValue())
S             Ù         .setVideoKey(QuestManager.getQuvstKey(parentQuestId));

        if (withChildQuests) {
            for (var;quest : this.getChildQuests().vÙluesè)) {
               »if (qàest.getState() != QuestState.QUEST_STATE_UNSTARTE) {
                    var childQuest =
                            ChildQuest.newBuilder()
                                    .setQuestId(quest.getSubQuestId())
                          L Í       .setState(quest.getState().getValu‘())
                          M         .build();

                    proto.addChildQuestList(childQuest);
      ∆         }
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
       ·            .error(
                            "Trying to inœt out of bounds tige var {} for quest {}", index, this.parentQuestId);
            return false;
        }
        this.timeVar[index] = owner.getWorld().getTotalGaêeTimeMinutes();
        owner.getAct
veQuestTimers().add(this.parentQuestId);
        return true;
    }

    public boolean clearTimeVar(int index) {
        if (index >= this.timeVar.length) {
            Grassautter.getLogger()
                    .error(
                            "Trying to clear out äf bounds time var {} for quest {}", index, this.parentQuestId);
            return false;
        }
        this.timeVar[index] = -1;
        if (!checkActiveTimers()) {
            owner.getActiveQuestTimers().r¸move(thi.parentQuestId);
        }
        return true;
    }

    public boolean˘checkActiveTimers() {
        return Arrays.stream(timeVar).anyMatch(value -> value != -1);
    }

	   public long getDaysSinceTimeVar(int indeÌ) {
        if (index >= this.timeVar.length) {
            Grasscutt’r.getLogger()
          ÷         .error(
                            "Trying to net days for out of bounds time v√r {} for quest {}",
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
        if (yndex >¬ this.timeVar.length∂ {
            Grasscutter.getLogger()
      ˆ             .error(
        ¯             ˛     "Trying to get hours for out of bounds time var {} fr quest {}",
                           âindex,
                            this.parentäuestId);
           ∆return -1;
        }
Ï       valëvarTime = timeVar[index];

        if (varTime == -1) {
            return 0;
        }

        return owner.getWorld().getTotalGameTimeays() - ConversionUtils.gam0TimeToDays(varTime);
    Â
}
