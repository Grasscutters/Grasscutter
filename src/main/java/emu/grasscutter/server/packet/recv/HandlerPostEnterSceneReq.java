package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.props.SceneType;
import emu.grasscutter.game.quest.enums.QuestContent;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketPostEnterSceneRsp;

@Opcodes(PacketOpcodes.PostEnterSceneReq)
public class HandlerPostEnterSceneReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var player = session.getPlayer();
        var scene = player.getScene();
        var questManager = player.getQuestManager();

        switch (session.getPlayer().getScene().getSceneType()){
            case SCENE_ROOM -> questManager.queueEvent(QuestContent.QUEST_CONTENT_ENTER_ROOM, scene.getId(), 0);
            case SCENE_WORLD -> {
                questManager.queueEvent(QuestContent.QUEST_CONTENT_ENTER_MY_WORLD, scene.getId());
                questManager.queueEvent(QuestContent.QUEST_CONTENT_ENTER_MY_WORLD_SCENE, scene.getId());
            }
            case SCENE_DUNGEON -> {
                var dungeonManager = scene.getDungeonManager();
                if (dungeonManager != null) dungeonManager.startDungeon();
            }
        }

        session.send(new PacketPostEnterSceneRsp(session.getPlayer()));
    }
}
