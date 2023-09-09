package io.grasscutter.tests;

import emu.grasscutter.GameConstants;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.*;
import emu.grasscutter.server.game.session.GameSessionManager;
import io.grasscutter.*;
import kcp.highway.*;
import lombok.Getter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

@TestMethodOrder(OrderAnnotation.class)
public final class LoginTest {
    @Getter private static final Ukcp KCP = new Ukcp(
        null, null, null,
        new ChannelConfig(), null);

    @Test
    @Order(1)
    @DisplayName("Connect to server")
    public void connectToServer() {
        var session = GrasscutterTest.getGameSession();

        // Register the session.
        GameSessionManager.getSessions().put(KCP, session);

        // Try connecting to the server.
        session.exchangeToken();
        Assertions.assertTrue(session.waitForPacket(
                PacketOpcodes.GetPlayerTokenRsp, 5));
    }

    @Test
    @Order(2)
    @DisplayName("Login to server")
    public void loginToServer() {
        var account = GrasscutterTest.getAccount();
        var session = GrasscutterTest.getGameSession();

        // Wait for the login response.
        TestUtils.waitFor(session::useSecretKey);

        // Send the login packet.
        session.receive(
                PacketOpcodes.PlayerLoginReq,
                PlayerLoginReqOuterClass.PlayerLoginReq.newBuilder()
                        .setToken(account.getToken())
                        .build()
        );
        // Wait for the login response.
        Assertions.assertTrue(session.waitForPacket(
                PacketOpcodes.PlayerLoginRsp, 5));

        // Send the born data request.
        session.receive(
                PacketOpcodes.SetPlayerBornDataReq,
                SetPlayerBornDataReqOuterClass.SetPlayerBornDataReq.newBuilder()
                        .setAvatarId(GameConstants.MAIN_CHARACTER_FEMALE)
                        .setNickName("Virtual Player")
                        .build()
        );
        // Wait for the born data response.
        Assertions.assertTrue(session.waitForPacket(
                PacketOpcodes.SetPlayerBornDataRsp, 5));
    }
}
