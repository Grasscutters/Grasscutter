package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify;

public class PacketSyncScenePlayTeamEntityNotify extends GenshinPacket {
	
	public PacketSyncScenePlayTeamEntityNotify(GenshinPlayer player) {
		super(PacketOpcodes.SyncScenePlayTeamEntityNotify);

		SyncScenePlayTeamEntityNotify proto = SyncScenePlayTeamEntityNotify.newBuilder()
				.setSceneId(player.getSceneId())
				.build(); 
		
		this.setData(proto);
	}
}
