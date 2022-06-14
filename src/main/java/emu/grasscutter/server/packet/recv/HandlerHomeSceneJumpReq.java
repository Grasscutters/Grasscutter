package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeSceneJumpReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketHomeSceneJumpRsp;

@Opcodes(PacketOpcodes.HomeSceneJumpReq)
public class HandlerHomeSceneJumpReq extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		var req = HomeSceneJumpReqOuterClass.HomeSceneJumpReq.parseFrom(payload);

		int realmId = 2000 + session.getPlayer().getCurrentRealmId();

		var home = session.getPlayer().getHome();
		var homeScene = home.getHomeSceneItem(realmId);
		home.save();

		if(req.getIsEnterRoomScene()){
			var roomScene = home.getHomeSceneItem(homeScene.getRoomSceneId());

			session.getPlayer().getWorld().transferPlayerToScene(
					session.getPlayer(),
					homeScene.getRoomSceneId(),
					roomScene.getBornPos()
			);
		}else{
			session.getPlayer().getWorld().transferPlayerToScene(
					session.getPlayer(),
					realmId,
					homeScene.getBornPos()
			);
		}

		session.send(new PacketHomeSceneJumpRsp(req.getIsEnterRoomScene()));
	}

}
