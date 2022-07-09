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
    @Getter private Map<Integer,Integer> openStateMap;

    public PlayerOpenStateManager(Player player) {
        this.openStateMap = new HashMap<>();
        this.player = player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Integer getOpenState(OpenState openState) {
        if(!openStateMap.containsKey(openState)){
            //default to 0
            openStateMap.putIfAbsent(openState.getValue(),0);
        }
        return this.openStateMap.get(openState);
    }
    public void setOpenState(OpenState openState, Integer value) {
        if(!openStateMap.containsKey(openState.getValue())){
            openStateMap.putIfAbsent(openState.getValue(),value);
        } else {
            this.openStateMap.put(openState.getValue(), value);
        }
        player.getSession().send(new PacketOpenStateChangeNotify(openState.getValue(),value));
    }

    public void setOpenState(int openState, Integer value) {
        if(!openStateMap.containsKey(openState)) {
            openStateMap.putIfAbsent(openState,value);
        } else {
            this.openStateMap.put(openState, value);
        }
        player.getSession().send(new PacketOpenStateChangeNotify(openState,value));
    }

    public void setOpenStatesByKey(Map<OpenState,Integer> openStatesChanged) {
        for(Map.Entry<OpenState, Integer> entry : openStatesChanged.entrySet()) {
            setOpenState(entry.getKey(), entry.getValue());
        }
    }

    public void setOpenStatesByKeyValue(Map<Integer,Integer> openStatesChanged) {
        for(Map.Entry<Integer, Integer> entry : openStatesChanged.entrySet()) {
            setOpenState(entry.getKey(), entry.getValue());
        }
    }

    public void onNewPlayerCreate() {
       Set<OpenState> newPlayerOpenStates = Set.of(OPEN_STATE_DERIVATIVE_MALL,OPEN_STATE_PHOTOGRAPH,OPEN_STATE_BATTLE_PASS,OPEN_STATE_SHOP_TYPE_GENESISCRYSTAL,OPEN_STATE_SHOP_TYPE_RECOMMANDED,
           OPEN_STATE_SHOP_TYPE_GIFTPACKAGE,OPEN_STATE_GUIDE_RELIC_PROM,OPEN_STATE_GUIDE_TALENT,OPEN_STATE_SHOP_TYPE_BLACKSMITH,OPEN_STATE_SHOP_TYPE_PAIMON,OPEN_STATE_WEAPON_AWAKEN,
           OPEN_STATE_WEAPON_PROMOTE,OPEN_STATE_AVATAR_PROMOTE,OPEN_STATE_AVATAR_TALENT,OPEN_STATE_WEAPON_UPGRADE,OPEN_STATE_RESIN,OPEN_STATE_RELIQUARY_UPGRADE,
           OPEN_STATE_SHOP_TYPE_VIRTUAL_SHOP,OPEN_STATE_RELIQUARY_PROMOTE);

        Map<OpenState,Integer> openStatesChanged = new HashMap<>();
        Iterator<OpenState> iterator = newPlayerOpenStates.iterator();
        while (iterator.hasNext()) {
            openStatesChanged.put(iterator.next(), 1);
        }
        setOpenStatesByKey(openStatesChanged);
    }
    public void onPlayerLogin() {
        //little hack to give all openStates on second login
        if(openStateMap.containsKey(16)) {
            setAllOpenStates();
        }
        player.getSession().send(new PacketOpenStateUpdateNotify(player));
    }

    public void setAllOpenStates() {
        Map<Integer,Integer> openStatesChanged = new HashMap<>();
        List<Integer> allOpenStates = Stream.of(OpenState.values())
            .map(OpenState::getValue).collect(Collectors.toList());
        for(int i=0; i<allOpenStates.size(); i++) {
            openStatesChanged.put(allOpenStates.get(i), 1);
        }
        setOpenStatesByKeyValue(openStatesChanged);
    }

}