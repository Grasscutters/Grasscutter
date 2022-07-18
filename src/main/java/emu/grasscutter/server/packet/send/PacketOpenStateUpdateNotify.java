package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.player.PlayerOpenStateManager;
import emu.grasscutter.game.props.OpenState;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.OpenStateUpdateNotifyOuterClass.OpenStateUpdateNotify;

import java.util.Map;
/*
    Must be sent on login for openStates to work
    Tells the client to update its openStateMap for the keys sent. value is irrelevant
 */
public class PacketOpenStateUpdateNotify extends BasePacket {

    public PacketOpenStateUpdateNotify(Player player) {
		super(PacketOpcodes.OpenStateUpdateNotify);

		OpenStateUpdateNotify.Builder proto = OpenStateUpdateNotify.newBuilder();

        proto.putAllOpenStateMap(player.getOpenStateManager().getOpenStateMap()).build();

		this.setData(proto);
	}
}
