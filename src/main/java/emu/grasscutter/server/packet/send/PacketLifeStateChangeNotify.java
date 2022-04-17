package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.entity.GenshinEntity;
import emu.grasscutter.game.props.LifeState;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.LifeStateChangeNotifyOuterClass.LifeStateChangeNotify;

public class PacketLifeStateChangeNotify extends GenshinPacket {
	public PacketLifeStateChangeNotify(GenshinEntity attacker, GenshinEntity target, LifeState lifeState) {
		super(PacketOpcodes.LifeStateChangeNotify);

		LifeStateChangeNotify proto = LifeStateChangeNotify.newBuilder()
				.setEntityId(target.getId())
				.setLifeState(lifeState.getValue())
				.setSourceEntityId(attacker.getId())
				.build();
		
		this.setData(proto);
	}
	public PacketLifeStateChangeNotify(int attackerId, GenshinEntity target, LifeState lifeState) {
		super(PacketOpcodes.LifeStateChangeNotify);

		LifeStateChangeNotify proto = LifeStateChangeNotify.newBuilder()
				.setEntityId(target.getId())
				.setLifeState(lifeState.getValue())
				.setSourceEntityId(attackerId)
				.build();
		
		this.setData(proto);
	}
}
