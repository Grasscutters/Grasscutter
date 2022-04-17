package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.avatar.GenshinAvatar;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.props.LifeState;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarLifeStateChangeNotifyOuterClass.AvatarLifeStateChangeNotify;

public class PacketAvatarLifeStateChangeNotify extends GenshinPacket {
	
	public PacketAvatarLifeStateChangeNotify(GenshinAvatar avatar) {
		super(PacketOpcodes.AvatarLifeStateChangeNotify);

		AvatarLifeStateChangeNotify proto = AvatarLifeStateChangeNotify.newBuilder()
				.setAvatarGuid(avatar.getGuid())
				.setLifeState(avatar.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP) > 0 ? LifeState.LIFE_ALIVE.getValue() : LifeState.LIFE_DEAD.getValue())
				.build();
		
		this.setData(proto);
	}
}
