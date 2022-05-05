package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.props.LifeState;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarLifeStateChangeNotifyOuterClass.AvatarLifeStateChangeNotify;
import emu.grasscutter.net.proto.PlayerDieTypeOuterClass.PlayerDieType;

public class PacketAvatarLifeStateChangeNotify extends BasePacket {
	
	public PacketAvatarLifeStateChangeNotify(Avatar avatar) {
		super(PacketOpcodes.AvatarLifeStateChangeNotify);

		AvatarLifeStateChangeNotify proto = AvatarLifeStateChangeNotify.newBuilder()
				.setAvatarGuid(avatar.getGuid())
				.setLifeState(avatar.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP) > 0 ? LifeState.LIFE_ALIVE.getValue() : LifeState.LIFE_DEAD.getValue())
				.build();
		
		this.setData(proto);
	}
	public PacketAvatarLifeStateChangeNotify(Avatar avatar,int attackerId,LifeState lifeState) {
		super(PacketOpcodes.AvatarLifeStateChangeNotify);

		AvatarLifeStateChangeNotify proto = AvatarLifeStateChangeNotify.newBuilder()
				.setAvatarGuid(avatar.getGuid())
				.setLifeState(lifeState.getValue())
				.setMoveReliableSeq(attackerId)
				.build();

		this.setData(proto);
	}
}
