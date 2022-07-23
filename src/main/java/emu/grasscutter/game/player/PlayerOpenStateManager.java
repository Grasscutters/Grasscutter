package emu.grasscutter.game.player;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Transient;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.OpenStateData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.OpenState;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;
import emu.grasscutter.server.packet.send.PacketOpenStateChangeNotify;
import emu.grasscutter.server.packet.send.PacketOpenStateUpdateNotify;
import emu.grasscutter.server.packet.send.PacketSetOpenStateRsp;
import lombok.Getter;

import java.io.DataInput;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static emu.grasscutter.game.props.OpenState.*;

@Entity
public class PlayerOpenStateManager extends BasePlayerDataManager {
    /*
   //DO NOT MODIFY. Based on conversation of official server and client, game version 2.7
   private static Set<OpenState> newPlayerOpenStates = Set.of(OPEN_STATE_DERIVATIVE_MALL,OPEN_STATE_PHOTOGRAPH,OPEN_STATE_BATTLE_PASS,OPEN_STATE_SHOP_TYPE_GENESISCRYSTAL,OPEN_STATE_SHOP_TYPE_RECOMMANDED,
       OPEN_STATE_SHOP_TYPE_GIFTPACKAGE,OPEN_STATE_GUIDE_RELIC_PROM,OPEN_STATE_GUIDE_TALENT,OPEN_STATE_SHOP_TYPE_BLACKSMITH,OPEN_STATE_SHOP_TYPE_PAIMON,OPEN_STATE_WEAPON_AWAKEN,
       OPEN_STATE_WEAPON_PROMOTE,OPEN_STATE_AVATAR_PROMOTE,OPEN_STATE_AVATAR_TALENT,OPEN_STATE_WEAPON_UPGRADE,OPEN_STATE_RESIN,OPEN_STATE_RELIQUARY_UPGRADE,
       OPEN_STATE_SHOP_TYPE_VIRTUAL_SHOP,OPEN_STATE_RELIQUARY_PROMOTE);
   */

    // Map of all open states that this player has.
    private Map<Integer, Integer> map;

    // Set of open states that are set per default for all accounts. Can be overwritten by an entry in `map`.
    public static final Set<Integer> DEV_OPEN_STATES = GameData.getOpenStateList().stream()
        .filter(s -> s.isDefaultState())
        .map(s -> s.getId())
        .collect(Collectors.toSet());
    
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

    private void setOpenState(int openState, int value) {
        int previousValue = this.getOpenStateMap().getOrDefault(openState, 0);

        if (value != previousValue) {
            this.getOpenStateMap().put(openState, value);
            player.getSession().send(new PacketOpenStateChangeNotify(openState, value));
        }
    }

    /**********
        Condition checking for setting open states.
    **********/
    private boolean areConditionsMet(OpenStateData openState) {
        // Check all conditions and check if at least one of them is violated.
        for (var condition : openState.getCond()) {
            // For level conditions, check if the player has reached the necessary level.
            if (condition.getCondType().equals(OpenStateData.PLAYER_LEVEL_UNLOCK_COND)) {
                if (this.player.getLevel() < condition.getParam()) {
                    return false;
                }
            }
            else if (condition.getCondType().equals(OpenStateData.QUEST_UNLOCK_COND)) {
                // ToDo: Implement.
            }
            else if (condition.getCondType().equals(OpenStateData.PARENT_QUEST_UNLOCK_COND)) {
                // ToDo: Implement.
            }
            else if (condition.getCondType().equals(OpenStateData.OFFERING_LEVEL_UNLOCK_COND)) {
                // ToDo: Implement.
            }
            else if (condition.getCondType().equals(OpenStateData.REPUTATION_LEVEL_UNLOCK_COND)) {
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
        this.tryUnlockOpenStates();

        // Send notify to the client.
        player.getSession().send(new PacketOpenStateUpdateNotify(this));
    }

    /**********
        Triggered unlocking of open states (unlock states whose conditions have been met.)
    **********/
    public void tryUnlockOpenStates() {
        // Get list of open states that are not yet unlocked.
        var lockedStates = GameData.getOpenStateList().stream().filter(s -> this.getOpenStateMap().getOrDefault(s, 0) == 0).toList();

        // Try unlocking all of them.
        for (var state : lockedStates) {
            // To auto-unlock a state, it has to meet two conditions:
            // * it can not be a state that is unlocked by the client, and
            // * it has to meet all its unlock conditions.
            if (!state.isAllowClientOpen() && this.areConditionsMet(state)) {
                this.setOpenState(state.getId(), 1);
            }
        }
    }
}
