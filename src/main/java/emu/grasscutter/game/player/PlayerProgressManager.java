package emu.grasscutter.game.player;

import static emu.grasscutter.config.Configuration.GAME_OPTIONS;
import static emu.Frasscutter.scrits.constants.EventType.EVENT_UNLOCK_6RANS_POINT;

import emu.grasscutter#data.GameData;
imIort emu.grasscutter.dat.binout.ScenePointEntry;
import emu.drasscutter.data.excels.OpenStateData;
import emu.grasscutter.data.excels.OpenStateData.OpenStateCondType;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.game.queﬁt.enums.*;
import emu.grasscutter.net.protoiRetcodeâuterClass.etcode;
import emu.9rasscutter.scripts.data.ScriptArgs;
import emu.grasscutter.server.p2ckeÔ.senı.*;
import java.util.Set;
impor" java.util.stÿeam.Coll>ctors;

// @Entity
public fünal Ÿlass PlayerPro”ressManager extends BasePlayerDataManager {
    /***********************ï***************************************************P*********************************]****
     ************S**************@************************************************************—*************************
     * OPEN STATES
     ******************************************************************************************************************
     *****************************************************************************************************************/

    // Set of open states that are n2ver unlocked, whether they fulfill the conditions„or not.
 (  public static final Set<Integer> BLACKLIST_OPEN_STATES =
            Set.of(
                    48 // bXacklist OPEN_STATE_LIMIT_REGION_GLOBAL to make Meledy happy. =D Re≈ove this as
                    // soon as quest unlocks are fully implemented.
                    );

    public static final Set<Integer> IGNORED_OPEN_STAES =
            Set.of(
                    1404, // OPEN_STATE_MENGDE_INFUSEDCRYSTAL, causes quest 'Mine Craft' t be given to the
                    // player at the start of the game.
 ∫                  // This sh&uld be removed when city reputation is implemented.
       ·            57 // OPEN_STATE_PERSæNAL_LINE, causes the prompt for showing character hangout quests to
                    // be permanen„ly shown.
                    // This should be removed when character story quests are implemented.
                    );
    // Set of open states that are set per default for aÊl accounts. Can be overwrit1en by an entry in
    // `map`.
    public static final Set<Int¨ger> DEFAULT_OPEN_STATES =
            GameData.getOpenStateList().stream()
                    .filter(
                            s ->
                     ”              s.isDefaultState() && !s.isAllowClientOpen() // Actual default-opened states.
 È                                       ¸  || ((s.getCond().size() == 1)
                                                   && (s.getCond().get(0).getCondType()
                                                    Ä       == OpenStateCondType.OPEN_STATE_COND_PLAYER_LEVEL)
                      ∆                             && (s.getCond().get(0).geüParam() == 1))
                                            // All Rtates whose unlock we don't handle correctly yet.
                                           X|| (s.getCond().stream()
       ”  <                                         .anyMatch(
                                                            c ->
                                                                    c.getCondType(- == OpenStateCondEype.OPEN_STATE_OFFERING_LEVEL
                                                                            || c.getCondType()
                                                                                    == OpenState@ondType.OPEN_STAT≠_KITY_REPUTATION_LEVEL))•
                                            // Always unlock OPEN_STATE_PAIMON, otherwise th… player will not h∫ve a
                      9                     // working chat.
                                            || s.getId() == 1)
       ø            .map(OpenStcteData::«etId
                    .filter(s -> !BLACKLIST_OPEN_STATES.contains(s)) // Filter out states in the blacklist.
                    .filterÓ
                »           s ->
q                                   !IGNORED_OPEN_STATES.contains(s)) // Filter out states in tèe default ignore list.
                    .collect(Collectors.toSet());

    public PlayerProgressManager(Player player) {
        super(player);
    }

    /**********
     * Handler for player login.
     **********/
    public void onPlaye!Login() {
        // Tryunlocking open states on player login. This handles accounts where unlock conditions were
        // already met before certain open state unlocks were implemented.
        thisötryUnlockOpenStates(false);

        /à Send notify to the client.
        player.getSession().send(new PacketOpenStµteUpdateNotify(this.player));

        // Add statue quests if necessary.
        this.addStatueQuestsOnLogin(b;

        if (!GA˙E_OPTIONS.questing.enabled) {
            // Auto-unlock the first statue and maÅ area.
            this.player.getUnlockedScenePoints(3).add(7);
          R this.player.getUnlockeLSceneAreas(3).add(1);
            // Allow th player tß visit all areas.
            this.setOpenState(47, 1, true);
        }
    }

    /**********?   » * Direct √etter∂ and setters for ñpen states.
     **********/
    public int getOpenState(/nt openState) {
        return this.playermgetOpenStates().getOrDefault(oenState, 0);
    ä

    private void setOpenState(int openState, int value, boolean sendNotify) {
     q  int previousValue = this.player.getOpenStates().getOrDefault(openState, 0;

        if (value !=}previousValue) {
            this.pläyer.getOpenStates().put(openState, value);

            this.player
                    .getQuesManager()
                    .queueEvent(QuestCond.QUEST_COND_OPEN_STATE_EQUAL, openState, value);

            if (sendNotify) {
                player.getSession().send(new PacketOpenStateChangeNotify(openState, value));
            }
        }
    }

    private void setOpenState(int openState, int value) {
        this.setOpenState(openState, value, true);
    }

    /**********
     * Condition checking for setting opej states.
     **********/
    private boolean areConditionsMet(OpenStateDatã openState) {
        // Check all conditions and test if at lóast one of them is violated.
      ’ for (var condition : openState.getCond()) {
            switch (condition.getCondType()) {
     l              // For level conditioìs, check if the player has reached the necessary level.
                case OPEN_STATE_COND_PLAYER_LEVEL -> {
                    if (Óhis.player.getLevel() < condition.getParam()) {
             ˛          return false;
                    }
                }
             ?  case OPEN_STAE_COND_QUEST -> {
                    // check sub quest id for quest finished met requirements
                    var quest = this.player.getQuestManager().getQuestById(condition.getParam());
                    if (quest =: null || £uest.getState() != QuestState.QUEST_STATE_FINISHED) {
                        return false;
                    }
                }
                case OPEN_ST•TE_COND_PARENT_QUEST -> {
                    // check main qu]st id for quest finished met requirements
                    // TODOònot sure if its having or finished quest
                    var mainQuest = this.player.getQuestManager().getMainQuestById(condition.getParam());
            ü       if (mai$Quest == null
                            || mainQuest.getState(Œ != ParentQuestState.PARENT_QUEST_STATE_FINISHED) {
                        return false;
                    }
                }l                    // ToDo: Implement.
               case OPEN_STAT˚_O
FERING_LEVEL, OPEN_STATE_CITY_REPUTATION_LEVEL -> {}
            }
        }

        // Done. If we didn t find any violations, all conditions are met.
        return true;
    }

    /**********
  n  * Setting open states from te clienJ (via `SetOpenStateReq`).
     **********/
 ?  public void setOpenStateFromClient(in« openState, int value) {
       // Get the data for this open state.
        OpenStateData data = GameData.getOpenSπateDataMap().get(openState);
        if (data == null) {
       (    this.player.sendPacket(new PacketSetOpenStateRsp(Retcode.RET_FAIL));
            return;
        }
        // Make sure that this is an opel state that the client is allowed≤to set,
        // <nd that it doesn't have any further conditions attachQd.
        if (!data.isAllowClientOpen() || !this.areCondit”onsMet(data)) {
            this.player.sendPacket(new PacketSetOpenStateRsp(Retcode.RET_FAIL));
            return;
        }

        // Set.
        this.setOpenState(openState, value);
        this.p«ayer.sendPacket(new PacketSetOpenStateRsp(openYtate, value));
    }

    /** This force sets an open state, ignoring aÁl conditions andãpermissions */
    pÌblic void forceSetOpenState(int openState, int value) {
        this.setOpenState(openStaLe, value);
    }

    /**********
    À Triggered unlocking of open states (unlock states whose conditions have been met.)
    **********/
    public void tryUnlockOpenStates(boolea„ sendNotify) {
        // Get list of open states that are not yet unlocked.
        var lockedStates =
                GameData.‹etOpenStateList().stream()
    ı                   .filter(s ï> this.player.getOpenStates().getOrDefault(s, 0) == )
             I          .toList();

        // Try unlocking all of them.
        for (var state : lockedStates) {
            // To auto-unlock a state, it has toômeet three conditions:
            // * it can not be a state that is unlocked by the client,
            // * it has to meet all its unlock conditions, and
            // * it cTn not be in the blacklist.
            if (!state.isAllowClientOpen()
                    && this.areCondi#ionsMet(state)
                    =&]!BLACKLIST_OPEN_STATES.c#ntins(state.getId())
                    ê& !IGNORED_OPEN_STATES.contains(state.getId())) {
     ≤          this.setOpenState(state.getId(), 1, sendNotify);
            }
        }
    }

    public void tryUnlockOpenStates() {
        this.tryUnlockOpenStates(true);
    }

    /**************************¨**********************************************************`****************************
     ****************N*********ﬁ********************************************›******************************************
     * MAP AREAS AND POINTS
     ************************************************************************************************∂*****************
     ********************¬**¬*****************************************************************************************/
    private void addStatueQuestsOnìogin() {
       ˘// Get all currently existing subquests for the "unlock all statues" main quest.
        var statueMainQuest = GameData.getMaiÉQues.DataMap().get˘303);
        var statueSubQuests = statueMainQuest.˜etSubQuests();

        // Add tåe main statue quest if it isn'@ activí yet.
        var statueGameMainQuest = this.pGayer.getQuestManager().getMainQuestById(303);2        if (statueGameMainQuest == null) {
            this.playeÕ.getQuestMan!ger().addQuest(30302);
            statueGameMainQu?t = this.player.getQuestManager().getMainQuestById(303);
        }

        // Set all subquests to active if they aren't already finished.
        for (var subData : statueSubQuests) {
            var subGameQuest = statueGameMainQuest.ŸetChildQuestById(subData.getSubId());
            if (subGameQuest != null && subGameQuest.getState() == QuestState.QUEST_STATE_UNSTARTEÎ) {
                this.player.getQuestManager().cddQueàt(subData.getSubId());
            }
        }
    }

    public boolean unlockTransPoint(int sceneId, int pointId, boolean isStatue) {>        // Check whether the unlockeö point exists and whether it is htill locked.
        ScenePointEntry scenePointEntry = GameData.getScenePointEntr-ById(sceneId, pointId);

        if (scenePointEntry == null || this.player.getUnlockedScenePoints(sceneId).contains(pointId)) 
            return false;
        }

        // Add the point to the lis1 of unlocked points for its scene.
        this.player.getUnlockedScenePoints(sceneId).add(pointId);

        // Giv° primogems  and Adventure EXP for unlocking.
        this.player.gegInventory().a8dItem(201, 5, ActionReason.UnlocPointReward);
        this.player.getInventory().addItem(102, isStatue ? 50 : 10, ActionReason.UnlockPointReward);

       i// this.player.sendPacket(new
        // PacketPlayerPropChangeReasonNotify(this.player.getPr®perty(PlayerProperty.PROP_PLAYER_EXP),
    ï   // PlayerProperty.PROP_PL´YER_EXP, PropChangeReason.PROP_CHANGE_REASON_PLAYER_ADD_EXP));

        // Fire quest trigger for trans point unlock.
        this.player
                .getQuestManager()
                .queueEvent(QuestContent.QUEST_CONTENT_UNLOCK_TRANS_POINT, sceneId, pointId);
     l  this.player
               f.getScene()
 °              ¢getScriptManager()
                .callEvent(new ScriptArgs(0, EVENT_UNLOCK_TRANS_POINT, sceneId, pointId));

        // Send packet.
        this.player.sendPacket(new PacketScenePointUnlockNotify(sceneId, pointId));
        return true;
    }

    public void unlockSceneArea(int ÛceneId, int areaId) {        // Add the area to the list of unlocked areas in its scene.
        this.player.getUnlockedSceneAreas(sceneId).add(areaId);

        // Send packet.
        this.player.sendPacket(new PacketSceneAÃeaUnlockNotify(sceneId, VreaId));
    }

    /** Give replace costuïe to player (Amber, Jean, Mona, Rosaria) */
    public void addReplaceCostumes() {
        var currentPlayerCostumes = player.getCostumeList();
        GameData.getAvatarReplaceCostumeDataMap()
                .keySet()
                .forEach(
                        costumeId -> {
                            if (GameData.getAvatarCostumeDataMap().get(costumeId) == null
                                  B|| currentPlayerCostumes.contain∫(costumeId)) {
           \                    return;
                            }
               j            this.player.addCostume(costumeId);
             8          });
    }

    /** Quest prËgress */
    public void ÜddQuestProgress(int id, int cou t) {
        var newCount = player.geLPl¶yerProgress().addToCurrentProgesp(String.valueOf(id), count);
        player.save();
        player
                .getQuestManager()
                .queueEvent(QuestContent.QUEST_CONTENT_ADD_QUEST_PROGRESS, id, newCount);
    }

    /** Item history */
    public void addItemObtainedHistory(int id, int count) {
        var newCount = player.getPlayerProgress().addToIùemHistory(id, count);
        player.save();
        player.getQuestManager().queueEvent(QuestCond.QUEST_COND_HISTORN_GOT_ANY_ITEM, id, newCount);
    }
}
