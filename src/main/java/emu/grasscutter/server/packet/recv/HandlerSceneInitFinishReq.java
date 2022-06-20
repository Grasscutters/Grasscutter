package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.player.Player.SceneLoadState;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.*;

@Opcodes(PacketOpcodes.SceneInitFinishReq)
public class HandlerSceneInitFinishReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        // Info packets
        session.send(new PacketServerTimeNotify());
        session.send(new PacketWorldPlayerInfoNotify(session.getPlayer().getWorld()));
        session.send(new PacketWorldDataNotify(session.getPlayer().getWorld()));
        session.send(new PacketPlayerWorldSceneInfoListNotify());
        session.send(new BasePacket(PacketOpcodes.SceneForceUnlockNotify));
        session.send(new PacketHostPlayerNotify(session.getPlayer().getWorld()));

        session.send(new PacketSceneTimeNotify(session.getPlayer()));
        session.send(new PacketPlayerGameTimeNotify(session.getPlayer()));
        session.send(new PacketPlayerEnterSceneInfoNotify(session.getPlayer()));
        session.send(new PacketSceneAreaWeatherNotify(session.getPlayer()));
        session.send(new PacketScenePlayerInfoNotify(session.getPlayer().getWorld()));
        session.send(new PacketSceneTeamUpdateNotify(session.getPlayer()));

        session.send(new PacketSyncTeamEntityNotify(session.getPlayer()));
        session.send(new PacketSyncScenePlayTeamEntityNotify(session.getPlayer()));

        // Done Packet
        session.send(new PacketSceneInitFinishRsp(session.getPlayer()));

        // Set state
        session.getPlayer().setSceneLoadState(SceneLoadState.INIT);
    }
}
