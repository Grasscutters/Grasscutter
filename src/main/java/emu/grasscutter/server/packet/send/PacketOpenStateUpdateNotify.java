package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.PlayerOpenStateManager;
import emu.grasscutter.game.props.OpenState;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.OpenStateUpdateNotifyOuterClass.OpenStateUpdateNotify;

/*
    Must be sent on login for openStates to work
    Tells the client to update its openStateMap for the keys sent. value is irrelevant
 */
public class PacketOpenStateUpdateNotify extends BasePacket {

    public PacketOpenStateUpdateNotify(PlayerOpenStateManager manager) {
        super(PacketOpcodes.OpenStateUpdateNotify);

        OpenStateUpdateNotify.Builder proto = OpenStateUpdateNotify.newBuilder();

        for (OpenState state : OpenState.values()) {
            // If the player has an open state stored in their map, then it would always override any default value
            if (manager.getOpenStateMap().containsKey(state.getValue())) {
                proto.putOpenStateMap(state.getValue(), manager.getOpenState(state));
            } else if (PlayerOpenStateManager.DEV_OPEN_STATES.contains(state)) {
                // Add default value here. TODO properly put default values somewhere
                proto.putOpenStateMap(state.getValue(), 1);
            }
        }

        this.setData(proto);
    }
}
