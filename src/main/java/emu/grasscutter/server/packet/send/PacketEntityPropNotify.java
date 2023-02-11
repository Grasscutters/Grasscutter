package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.EntityPropNotifyOuterClass.EntityPropNotify;
import emu.grasscutter.utils.ProtoHelper;

public class PacketEntityPropNotify extends BasePacket {
	public PacketEntityPropNotify(Avatar avatar) {
		super(PacketOpcodes.EntityPropNotify);

		EntityPropNotify proto = EntityPropNotify.newBuilder()
				.setEntityId(avatar.getAvatarId())
				.putPropMap(PlayerProperty.PROP_LEVEL.getId(),
						ProtoHelper.newPropValue(PlayerProperty.PROP_LEVEL, avatar.getLevel()))
				.putPropMap(PlayerProperty.PROP_EXP.getId(),
						ProtoHelper.newPropValue(PlayerProperty.PROP_EXP, avatar.getExp()))
				.putPropMap(PlayerProperty.PROP_BREAK_LEVEL.getId(),
						ProtoHelper.newPropValue(PlayerProperty.PROP_BREAK_LEVEL, avatar.getPromoteLevel()))
				.putPropMap(PlayerProperty.PROP_SATIATION_VAL.getId(),
						ProtoHelper.newPropValue(PlayerProperty.PROP_SATIATION_VAL, (avatar.getSatiation()*100)))
				.putPropMap(PlayerProperty.PROP_SATIATION_PENALTY_TIME.getId(),
						ProtoHelper.newPropValue(PlayerProperty.PROP_SATIATION_PENALTY_TIME, avatar.getSatiationPenalty()))
				.build();

		this.setData(proto);
	}

	public PacketEntityPropNotify(Avatar avatar, PlayerProperty prop, int value) {
		super(PacketOpcodes.EntityPropNotify);

		EntityPropNotify proto = EntityPropNotify.newBuilder()
				.setEntityId(value)
				.putPropMap(prop.getId(), ProtoHelper.newPropValue(prop, value))
				.build();

		this.setData(proto);
	}

	// For food use
	public PacketEntityPropNotify(Avatar avatar, PlayerProperty prop, PlayerProperty prop2, int value) {
		super(PacketOpcodes.EntityPropNotify);

		EntityPropNotify proto = EntityPropNotify.newBuilder()
				.setEntityId(value)
				.putPropMap(prop.getId(), ProtoHelper.newPropValue(prop, value))
				.putPropMap(prop2.getId(), ProtoHelper.newPropValue(prop2, value))
				.build();

		this.setData(proto);
	}
}
