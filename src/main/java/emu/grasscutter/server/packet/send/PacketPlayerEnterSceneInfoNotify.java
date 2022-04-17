package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.inventory.GenshinItem;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AbilitySyncStateInfoOuterClass.AbilitySyncStateInfo;
import emu.grasscutter.net.proto.AvatarEnterSceneInfoOuterClass.AvatarEnterSceneInfo;
import emu.grasscutter.net.proto.MPLevelEntityInfoOuterClass.MPLevelEntityInfo;
import emu.grasscutter.net.proto.PlayerEnterSceneInfoNotifyOuterClass.PlayerEnterSceneInfoNotify;
import emu.grasscutter.net.proto.TeamEnterSceneInfoOuterClass.TeamEnterSceneInfo;

public class PacketPlayerEnterSceneInfoNotify extends GenshinPacket {
	
	public PacketPlayerEnterSceneInfoNotify(GenshinPlayer player) {
		super(PacketOpcodes.PlayerEnterSceneInfoNotify);
		
		AbilitySyncStateInfo empty = AbilitySyncStateInfo.newBuilder().build();

		PlayerEnterSceneInfoNotify.Builder proto = PlayerEnterSceneInfoNotify.newBuilder()
				.setCurAvatarEntityId(player.getTeamManager().getCurrentAvatarEntity().getId())
				.setEnterSceneToken(player.getEnterSceneToken());
		
		proto.setTeamEnterInfo(
				TeamEnterSceneInfo.newBuilder()
					.setTeamEntityId(player.getTeamManager().getEntityId()) // 150995833
					.setTeamAbilityInfo(empty)
					.setUnk(empty)
		);
		proto.setMpLevelEntityInfo(
				MPLevelEntityInfo.newBuilder()
					.setEntityId(player.getWorld().getLevelEntityId()) // 184550274
					.setAuthorityPeerId(player.getWorld().getHostPeerId())
					.setAbilityInfo(empty)
		);
		
		for (EntityAvatar avatarEntity : player.getTeamManager().getActiveTeam()) {
			GenshinItem weapon = avatarEntity.getAvatar().getWeapon();
			long weaponGuid = weapon != null ? weapon.getGuid() : 0;
			
			AvatarEnterSceneInfo avatarInfo = AvatarEnterSceneInfo.newBuilder()
					.setAvatarGuid(avatarEntity.getAvatar().getGuid())
					.setAvatarEntityId(avatarEntity.getId())
					.setWeaponGuid(weaponGuid)
					.setWeaponEntityId(avatarEntity.getWeaponEntityId())
					.setAvatarAbilityInfo(empty)
					.setWeaponAbilityInfo(empty)
					.build();
			
			proto.addAvatarEnterInfo(avatarInfo);
		}
		
		this.setData(proto.build());
	}
}
