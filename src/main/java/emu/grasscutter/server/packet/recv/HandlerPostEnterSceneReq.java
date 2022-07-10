package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.props.SceneType;
import emu.grasscutter.game.quest.enums.QuestTrigger;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketPostEnterSceneRsp;

@Opcodes(PacketOpcodes.PostEnterSceneReq)
public class HandlerPostEnterSceneReq extends PacketHandler {

	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        if(session.getPlayer().getScene().getSceneType() == SceneType.SCENE_ROOM){
            session.getPlayer().getQuestManager().triggerEvent(QuestTrigger.QUEST_CONTENT_ENTER_ROOM, session.getPlayer().getSceneId());
        }

		session.send(new PacketPostEnterSceneRsp(session.getPlayer()));
	}

}
