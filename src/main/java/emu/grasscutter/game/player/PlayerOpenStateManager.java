package emu.grasscutter.game.player;

import dev.morphia.annotations.Entity;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.OpenStateData;
import emu.grasscutter.data.excels.OpenStateData.OpenStateCondType;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;
import emu.grasscutter.server.packet.send.PacketOpenStateChangeNotify;
import emu.grasscutter.server.packet.send.PacketOpenStateUpdateNotify;
import emu.grasscutter.server.packet.send.PacketSetOpenStateRsp;

import java.util.*;
import java.util.stream.Collectors;

@Entity
public class PlayerOpenStateManager extends BasePlayerDataManager {
    // Set of open states that are never unlocked, whether they fulfill the conditions or not.
    public static final Set<Integer> BLACKLIST_OPEN_STATES = Set.of(
    48      // blacklist OPEN_STATE_LIMIT_REGION_GLOBAL to make Meledy happy. =D Remove this as soon as quest unlocks are fully implemented.
    );

    // Set of open states that are set per default for all accounts. Can be overwritten by an entry in `map`.
    public static final Set<Integer> DEFAULT_OPEN_STATES = GameData.getOpenStateList().stream()
        .filter(s -> 
            s.isDefaultState()      // Actual default-opened states.
            || (s.getCond().stream().filter(c -> c.getCondType() == OpenStateCondType.OPEN_STATE_COND_PLAYER_LEVEL).count() == 0) // All states whose unlock we don't handle correctly yet.
            || s.getId() == 1 // Always unlock OPEN_STATE_PAIMON, otherwise the player will not have a working chat.
        )
        .filter(s -> !BLACKLIST_OPEN_STATES.contains(s.getId()))    // Filter out states in the blacklist.
        .map(s -> s.getId())
        .collect(Collectors.toSet());

    // Map of all open states that this player has.
    private Map<Integer, Integer> map;

    public PlayerOpenStateManager(Player player) {
        super(player);
    }

    public synchronized Map<Integer, Integer> getOpenStateMap() {
        // If no map currently exists, we create one.
        if (this.map == null) {
            this.map = new HashMap<>();
        }
        
        return this.map;
    }

    /**********
        Direct getters and setters for open states.
    **********/
    public int getOpenState(int openState) {
        return getOpenStateMap().getOrDefault(openState, 0);
    }

    private void setOpenState(int openState, int value, boolean sendNotify) {
        int previousValue = this.getOpenStateMap().getOrDefault(openState, 0);

        if (value != previousValue) {
            this.getOpenStateMap().put(openState, value);

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
        Handler for player login.
    **********/
    public void onPlayerLogin() {
        // Try unlocking open states on player login. This handles accounts where unlock conditions were
        // already met before certain open state unlocks were implemented.
        this.tryUnlockOpenStates(false);

        // Send notify to the client.
        player.getSession().send(new PacketOpenStateUpdateNotify(this));
    }

    /**********
        Triggered unlocking of open states (unlock states whose conditions have been met.)
    **********/
    public void tryUnlockOpenStates(boolean sendNotify) {
        // Get list of open states that are not yet unlocked.
        var lockedStates = GameData.getOpenStateList().stream().filter(s -> this.getOpenStateMap().getOrDefault(s, 0) == 0).toList();

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
}
