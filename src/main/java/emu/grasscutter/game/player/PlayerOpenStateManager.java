package emu.grasscutter.game.player;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Transient;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.OpenState;
import emu.grasscutter.server.packet.send.PacketOpenStateChangeNotify;
import emu.grasscutter.server.packet.send.PacketOpenStateUpdateNotify;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static emu.grasscutter.game.props.OpenState.*;

@Entity
public class PlayerOpenStateManager {
    @Transient private Player player;
    
    // Map of all open states that this player has. Do not put default values here.
    private Map<Integer, Integer> map;
    
     /*
    //DO NOT MODIFY. Based on conversation of official server and client, game version 2.7
    private static Set<OpenState> newPlayerOpenStates = Set.of(OPEN_STATE_DERIVATIVE_MALL,OPEN_STATE_PHOTOGRAPH,OPEN_STATE_BATTLE_PASS,OPEN_STATE_SHOP_TYPE_GENESISCRYSTAL,OPEN_STATE_SHOP_TYPE_RECOMMANDED,
        OPEN_STATE_SHOP_TYPE_GIFTPACKAGE,OPEN_STATE_GUIDE_RELIC_PROM,OPEN_STATE_GUIDE_TALENT,OPEN_STATE_SHOP_TYPE_BLACKSMITH,OPEN_STATE_SHOP_TYPE_PAIMON,OPEN_STATE_WEAPON_AWAKEN,
        OPEN_STATE_WEAPON_PROMOTE,OPEN_STATE_AVATAR_PROMOTE,OPEN_STATE_AVATAR_TALENT,OPEN_STATE_WEAPON_UPGRADE,OPEN_STATE_RESIN,OPEN_STATE_RELIQUARY_UPGRADE,
        OPEN_STATE_SHOP_TYPE_VIRTUAL_SHOP,OPEN_STATE_RELIQUARY_PROMOTE);
    */
    
    // For development. Remove entry when properly implemented
    // TODO - Set as boolean in OpenState
    public static final Set<OpenState> DEV_OPEN_STATES = Stream.of(OpenState.values())
        .filter(s -> s != OpenState.OPEN_STATE_NONE && s.getUnlockLevel() <= 1)
        .collect(Collectors.toSet());

    public PlayerOpenStateManager(Player player) {
        this.player = player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

	public Map<Integer, Integer> getOpenStateMap() {
		if (this.map == null) this.map = new HashMap<>();
		return this.map;
	}

    public int getOpenState(OpenState openState) {
        return this.map.getOrDefault(openState.getValue(), 0);
    }
    
    public void setOpenState(OpenState openState, Integer value) {
        Integer previousValue = this.map.getOrDefault(openState.getValue(),0);
        if(value != previousValue) {
            this.map.put(openState.getValue(), value);
            player.getSession().send(new PacketOpenStateChangeNotify(openState.getValue(),value));
        }
    }

    public void setOpenStates(Map<OpenState,Integer> openStatesChanged) {
        for(Map.Entry<OpenState, Integer> entry : openStatesChanged.entrySet()) {
            setOpenState(entry.getKey(), entry.getValue());
        }
    }

    public void onPlayerLogin() {
        player.getSession().send(new PacketOpenStateUpdateNotify(this));
    }

    public void unlockLevelDependentStates() {
        Stream.of(OpenState.values())
            .filter(s -> s.getUnlockLevel() > 1 && s.getUnlockLevel() <= this.player.getLevel())
            .forEach(s -> this.setOpenState(s, 1));
    }
}