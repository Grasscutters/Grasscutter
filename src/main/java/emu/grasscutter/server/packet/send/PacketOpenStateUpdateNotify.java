package emu.grasscutter.server.packet.send;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.OpenStateData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.player.PlayerProgressManager;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.OpenStateUpdateNotifyOuterClass.OpenStateUpdateNotify;

/*
    Must be sent on login for openStates to work
    Tells the client to update its openStateMap for the keys sent. value is irrelevant
 */
public class PacketOpenStateUpdateNotify extends BasePacket {

    public PacketOpenStateUpdateNotify(Player player) {
        super(PacketOpcodes.OpenStateUpdateNotify);

        OpenStateUpdateNotify.Builder proto = OpenStateUpdateNotify.newBuilder();

        for (OpenStateData state : GameData.getOpenStateList()) {
            // If the player has an open state stored in their map, then it would always override any default value
            if (player.getOpenStates().containsKey(state.getId())) {
                proto.putOpenStateMap(state.getId(), player.getProgressManager().getOpenState(state.getId()));
            }
            // Otherwise, add the state if it is contained in the set of default open states. 
            else if (PlayerProgressManager.DEFAULT_OPEN_STATES.contains(state.getId())) {
                proto.putOpenStateMap(state.getId(), 1);
            }
        }
        
        this.setData(proto);
    }
}
