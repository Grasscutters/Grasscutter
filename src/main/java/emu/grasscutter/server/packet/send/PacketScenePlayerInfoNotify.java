package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.World;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ScenePlayerInfoNotifyOuterClass.ScenePlayerInfoNotify;
import emu.grasscutter.net.proto.ScenePlayerInfoOuterClass.ScenePlayerInfo;

public class PacketScenePlayerInfoNotify extends GenshinPacket {
	
	public PacketScenePlayerInfoNotify(World world) {
		super(PacketOpcodes.ScenePlayerInfoNotify);

		ScenePlayerInfoNotify.Builder proto = ScenePlayerInfoNotify.newBuilder();
		
		for (int i = 0; i < world.getPlayers().size(); i++) {
			GenshinPlayer p = world.getPlayers().get(i);

			ScenePlayerInfo pInfo = ScenePlayerInfo.newBuilder()
					.setUid(p.getUid())
					.setPeerId(p.getPeerId())
					.setName(p.getNickname())
					.setSceneId(p.getSceneId())
					.setOnlinePlayerInfo(p.getOnlinePlayerInfo())
					.build();
			
			proto.addPlayerInfoList(pInfo);
		}
		
		this.setData(proto.build());
	}
}
