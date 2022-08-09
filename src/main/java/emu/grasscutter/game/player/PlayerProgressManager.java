package emu.grasscutter.game.player;

import dev.morphia.annotations.Entity;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.ScenePointEntry;
import emu.grasscutter.data.excels.OpenStateData;
import emu.grasscutter.data.excels.OpenStateData.OpenStateCondType;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.game.quest.enums.QuestState;
import emu.grasscutter.game.quest.enums.QuestTrigger;
import emu.grasscutter.net.proto.PropChangeReasonOuterClass.PropChangeReason;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;
import emu.grasscutter.server.packet.send.PacketOpenStateChangeNotify;
import emu.grasscutter.server.packet.send.PacketOpenStateUpdateNotify;
import emu.grasscutter.server.packet.send.PacketSceneAreaUnlockNotify;
import emu.grasscutter.server.packet.send.PacketScenePointUnlockNotify;
import emu.grasscutter.server.packet.send.PacketSetOpenStateRsp;
import emu.grasscutter.server.packet.send.PacketUnlockTransPointRsp;

import java.util.*;
import java.util.stream.Collectors;

// @Entity
public class PlayerProgressManager extends BasePlayerDataManager {
    public PlayerProgressManager(Player player) {
        super(player);
    }

    /**********
        Handler for player login.
    **********/
    public void onPlayerLogin() {
        // Try unlocking open states on player login. This handles accounts where unlock conditions were
        // already met before certain open state unlocks were implemented.
        this.tryUnlockOpenStates(false);

        // Send notify to the client.
        player.getSession().send(new PacketOpenStateUpdateNotify(this.player));

        // Add statue quests if necessary.
        this.addStatueQuestsOnLogin();

        // Auto-unlock the first statue and map area, until we figure out how to make
        // that particular statue interactable.
        if (!this.player.getUnlockedScenePoints().containsKey(3)) {
            this.player.getUnlockedScenePoints().put(3, new ArrayList<>());
        }
        if (!this.player.getUnlockedScenePoints().get(3).contains(7)) {
            this.player.getUnlockedScenePoints().get(3).add(7);
        }

        if (!this.player.getUnlockedSceneAreas().containsKey(3)) {
            this.player.getUnlockedSceneAreas().put(3, new ArrayList<>());
        }
        if (!this.player.getUnlockedSceneAreas().get(3).contains(1)) {
            this.player.getUnlockedSceneAreas().get(3).add(1);
        }
    }

    /******************************************************************************************************************
     ******************************************************************************************************************
     * OPEN STATES
     ******************************************************************************************************************
     *****************************************************************************************************************/

    // Set of open states that are never unlocked, whether they fulfill the conditions or not.
    public static final Set<Integer> BLACKLIST_OPEN_STATES = Set.of(
    48      // blacklist OPEN_STATE_LIMIT_REGION_GLOBAL to make Meledy happy. =D Remove this as soon as quest unlocks are fully implemented.
    );

    // Set of open states that are set per default for all accounts. Can be overwritten by an entry in `map`.
    public static final Set<Integer> DEFAULT_OPEN_STATES = GameData.getOpenStateList().stream()
        .filter(s -> 
            s.isDefaultState()      // Actual default-opened states.
            // All states whose unlock we don't handle correctly yet.
            || (s.getCond().stream().filter(c -> c.getCondType() == OpenStateCondType.OPEN_STATE_COND_PLAYER_LEVEL).count() == 0)
            // Always unlock OPEN_STATE_PAIMON, otherwise the player will not have a working chat.
            || s.getId() == 1 
        )
        .filter(s -> !BLACKLIST_OPEN_STATES.contains(s.getId()))    // Filter out states in the blacklist.
        .map(s -> s.getId())
        .collect(Collectors.toSet());

    /**********
        Direct getters and setters for open states.
    **********/
    public int getOpenState(int openState) {
        return this.player.getOpenStates().getOrDefault(openState, 0);
    }

    private void setOpenState(int openState, int value, boolean sendNotify) {
        int previousValue = this.player.getOpenStates().getOrDefault(openState, 0);

        if (value != previousValue) {
            this.player.getOpenStates().put(openState, value);

            if (sendNotify) {
                player.getSession().send(new PacketOpenStateChangeNotify(openState, value));
            }
        }
    }
    private void setOpenState(int openState, int value) {
        this.setOpenState(openState, value, true);
    }

    /**********
        Condition checking for setting open states.
    **********/
    private boolean areConditionsMet(OpenStateData openState) {
        // Check all conditions and test if at least one of them is violated.
        for (var condition : openState.getCond()) {
            // For level conditions, check if the player has reached the necessary level.
            if (condition.getCondType() == OpenStateCondType.OPEN_STATE_COND_PLAYER_LEVEL) {
                if (this.player.getLevel() < condition.getParam()) {
                    return false;
                }
            }
            else if (condition.getCondType() == OpenStateCondType.OPEN_STATE_COND_QUEST) {
                // ToDo: Implement.
            }
            else if (condition.getCondType() == OpenStateCondType.OPEN_STATE_COND_PARENT_QUEST) {
                // ToDo: Implement.
            }
            else if (condition.getCondType() == OpenStateCondType.OPEN_STATE_OFFERING_LEVEL) {
                // ToDo: Implement.
            }
            else if (condition.getCondType() == OpenStateCondType.OPEN_STATE_CITY_REPUTATION_LEVEL) {
                // ToDo: Implement.
            }
        }
        
        // Done. If we didn't find any violations, all conditions are met.
        return true;
    }

    /**********
        Setting open states from the client (via `SetOpenStateReq`).
    **********/
    public void setOpenStateFromClient(int openState, int value) {
        // Get the data for this open state.
        OpenStateData data = GameData.getOpenStateDataMap().get(openState);
        if (data == null) {
            this.player.sendPacket(new PacketSetOpenStateRsp(Retcode.RET_FAIL));
            return;
        }

        // Make sure that this is an open state that the client is allowed to set,
        // and that it doesn't have any further conditions attached.
        if (!data.isAllowClientOpen() || !this.areConditionsMet(data)) {
            this.player.sendPacket(new PacketSetOpenStateRsp(Retcode.RET_FAIL));
            return;
        }

        // Set.
        this.setOpenState(openState, value);
        this.player.sendPacket(new PacketSetOpenStateRsp(openState, value));
    }

    /**********
        Triggered unlocking of open states (unlock states whose conditions have been met.)
    **********/
    public void tryUnlockOpenStates(boolean sendNotify) {
        // Get list of open states that are not yet unlocked.
        var lockedStates = GameData.getOpenStateList().stream().filter(s -> this.player.getOpenStates().getOrDefault(s, 0) == 0).toList();

        // Try unlocking all of them.
        for (var state : lockedStates) {
            // To auto-unlock a state, it has to meet three conditions:
            // * it can not be a state that is unlocked by the client,
            // * it has to meet all its unlock conditions, and
            // * it can not be in the blacklist.
            if (!state.isAllowClientOpen() && this.areConditionsMet(state) && !BLACKLIST_OPEN_STATES.contains(state.getId())) {
                this.setOpenState(state.getId(), 1, sendNotify);
            }
        }
    }
    public void tryUnlockOpenStates() {
        this.tryUnlockOpenStates(true);
    }

    /******************************************************************************************************************
     ******************************************************************************************************************
     * MAP AREAS AND POINTS
     ******************************************************************************************************************
     *****************************************************************************************************************/
    private void addStatueQuestsOnLogin() {
        // Get all currently existing subquests for the "unlock all statues" main quest.
        var statueMainQuest = GameData.getMainQuestDataMap().get(303);
        var statueSubQuests = statueMainQuest.getSubQuests();

        // Add the main statue quest if it isn't active yet.
        var statueGameMainQuest = this.player.getQuestManager().getMainQuestById(303);
        if (statueGameMainQuest == null) {
            this.player.getQuestManager().addQuest(30302);
            statueGameMainQuest = this.player.getQuestManager().getMainQuestById(303);
        }

        // Set all subquests to active if they aren't already finished.
        for (var subData : statueSubQuests) {
            var subGameQuest = statueGameMainQuest.getChildQuestById(subData.getSubId());
            if (subGameQuest != null && subGameQuest.getState() == QuestState.QUEST_STATE_UNSTARTED) {
                this.player.getQuestManager().addQuest(subData.getSubId());
            }
        }
    }

    public void unlockTransPoint(int sceneId, int pointId, boolean isStatue) {
        // Check whether the unlocked point exists and whether it is still locked.
        String key = sceneId + "_" + pointId;
		ScenePointEntry scenePointEntry = GameData.getScenePointEntries().get(key);
		
		if (scenePointEntry == null || this.player.getUnlockedScenePoints().getOrDefault(sceneId, List.of()).contains(pointId)) {
            this.player.sendPacket(new PacketUnlockTransPointRsp(Retcode.RET_FAIL));
            return;
        }

        // Add the point to the list of unlocked points for its scene.
        if (!this.player.getUnlockedScenePoints().containsKey(sceneId)) {
            this.player.getUnlockedScenePoints().put(sceneId, new ArrayList<>());
        }
        this.player.getUnlockedScenePoints().get(sceneId).add(pointId);

        // Give primogems  and Adventure EXP for unlocking.
        var primos = new GameItem(GameData.getItemDataMap().get(201), 5);
        this.player.getInventory().addItem(primos, ActionReason.UnlockPointReward);

        var exp = new GameItem(GameData.getItemDataMap().get(102), isStatue ? 50 : 10);
        this.player.getInventory().addItem(exp, ActionReason.UnlockPointReward);

        // this.player.sendPacket(new PacketPlayerPropChangeReasonNotify(this.player.getProperty(PlayerProperty.PROP_PLAYER_EXP), PlayerProperty.PROP_PLAYER_EXP, PropChangeReason.PROP_CHANGE_REASON_PLAYER_ADD_EXP));

        // Fire quest trigger for trans point unlock.
        this.player.getQuestManager().triggerEvent(QuestTrigger.QUEST_CONTENT_UNLOCK_TRANS_POINT, sceneId, pointId);

        // Send packet.
        this.player.sendPacket(new PacketScenePointUnlockNotify(sceneId, pointId));
        this.player.sendPacket(new PacketUnlockTransPointRsp(Retcode.RET_SUCC));
    }

    public void unlockSceneArea(int sceneId, int areaId) {
        // Check whether this area is already unlocked.
        if (this.player.getUnlockedSceneAreas().getOrDefault(sceneId, List.of()).contains(areaId)) {
            return;
        }

        // Add the area to the list of unlocked areas in its scene.
        if (!this.player.getUnlockedSceneAreas().containsKey(sceneId)) {
            this.player.getUnlockedSceneAreas().put(sceneId, new ArrayList<>());
        }
        this.player.getUnlockedSceneAreas().get(sceneId).add(areaId);

        // Send packet.
        this.player.sendPacket(new PacketSceneAreaUnlockNotify(sceneId, areaId));
    }
}
