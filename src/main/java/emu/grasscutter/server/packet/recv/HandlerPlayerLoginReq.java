package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.PlayerLoginReqOuterClass.PlayerLoginReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.game.GameSession.SessionState;
import emu.grasscutter.server.packet.send.PacketPlayerLoginRsp;

@Opcodes(PacketOpcodes.PlayerLoginReq) // Sends initial data packets
public class HandlerPlayerLoginReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        // Check
        if (session.getAccount() == null) {
            session.close();
            return;
        }

        // Parse request
        PlayerLoginReq req = PlayerLoginReq.parseFrom(payload);

        // Authenticate session
        if (!req.getToken().equals(session.getAccount().getToken())) {
            session.close();
            return;
        }

        // Load character from db
        Player player = session.getPlayer();

        // Show opening cutscene if player has no avatars
        if (player.getAvatars().getAvatarCount() == 0) {
            // Pick character
            session.setState(SessionState.PICKING_CHARACTER);
            session.send(new BasePacket(PacketOpcodes.DoSetPlayerBornDataNotify));
        } else {
            // Login done
            session.getPlayer().onLogin();
        }

        // Final packet to tell client logging in is done
        session.send(new PacketPlayerLoginRsp(session));
    }
}
