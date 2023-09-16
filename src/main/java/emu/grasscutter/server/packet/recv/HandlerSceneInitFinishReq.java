package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.player.Player.SceneLoadState;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.*;

@Opcodes(PacketOpcodes.SceneInitFinishReq)
public class HandlerSceneInitFinishReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var player = session.getPlayer();
        var world = player.getWorld();

        // Info packets
        session.send(new PacketServerTimeNotify());
        session.send(new PacketWorldPlayerInfoNotify(world));
        session.send(new PacketWorldDataNotify(world));
        session.send(new PacketPlayerWorldSceneInfoListNotify(player));
        session.send(new BasePacket(PacketOpcodes.SceneForceUnlockNotify));
        session.send(new PacketHostPlayerNotify(world));

        session.send(new PacketSceneTimeNotify(player));
        session.send(new PacketPlayerGameTimeNotify(player));
        session.send(new PacketPlayerEnterSceneInfoNotify(player));
        session.send(new PacketSceneAreaWeatherNotify(player));
        session.send(new PacketScenePlayerInfoNotify(world));
        session.send(new PacketSceneTeamUpdateNotify(player));

        session.send(new PacketSyncTeamEntityNotify(player));
        session.send(new PacketSyncScenePlayTeamEntityNotify(player));

        // Done Packet
        session.send(new PacketSceneInitFinishRsp(player));

        // Set scene load state.
        player.setSceneLoadState(SceneLoadState.INIT);
        // Run scene initialization.
        player.getScene().playerSceneInitialized(player);
    }
}
