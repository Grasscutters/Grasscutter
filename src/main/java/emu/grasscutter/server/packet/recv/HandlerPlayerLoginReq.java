package emu.grasscutter.server.packet.recv;

import static emu.grasscutter.config.Configuration.ACCOUNT;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PlayerLoginReqOuterClass.PlayerLoginReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.game.GameSession.SessionState;
import emu.grasscutter.server.packet.send.PacketPlayerLoginRsp;
import emu.grasscutter.server.packet.send.PacketTakeAchievementRewardReq;

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
        session.send(new PacketTakeAchievementRewardReq(session));
    }

}
