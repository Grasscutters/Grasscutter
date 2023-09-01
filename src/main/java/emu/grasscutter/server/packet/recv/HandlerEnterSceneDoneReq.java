package emu.grasscutter.server.packet.recv;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.player.Player.SceneLoadState;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.*;

@Opcodes(PacketOpcodes.EnterSceneDoneReq)
public class HandlerEnterSceneDoneReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var player = session.getPlayer();

        // Finished loading
        player.setSceneLoadState(SceneLoadState.LOADED);

        // Done

        session.send(new PacketPlayerTimeNotify(player)); // Probably not the right place

        // Spawn player in world
        player.getScene().spawnPlayer(player);

        // Spawn other entites already in world
        player.getScene().showOtherEntities(player);

        // Locations
        session.send(new PacketWorldPlayerLocationNotify(player.getWorld()));
        session.send(new PacketScenePlayerLocationNotify(player.getScene()));
        session.send(new PacketWorldPlayerRTTNotify(player.getWorld()));

        // spawn NPC
        player.getScene().loadNpcForPlayerEnter(player);

        // notify client to load the npc for quest
        var questGroupSuites = player.getQuestManager().getSceneGroupSuite(player.getSceneId());

        player.getScene().loadGroupForQuest(questGroupSuites);
        Grasscutter.getLogger()
                .trace("Loaded Scene {} Quest(s) Groupsuite(s): {}", player.getSceneId(), questGroupSuites);
        session.send(new PacketGroupSuiteNotify(questGroupSuites));

        // Reset timer for sending player locations
        player.resetSendPlayerLocTime();

        // Rsp
        session.send(new PacketEnterSceneDoneRsp(player));
    }
}
