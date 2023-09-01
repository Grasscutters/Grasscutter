package emu.grasscutter.server.packet.recv;

import emu.grasscutter.*;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.GetPlayerTokenReqOuterClass.GetPlayerTokenReq;
import emu.grasscutter.server.event.game.PlayerCreationEvent;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.game.GameSession.SessionState;
import emu.grasscutter.server.packet.send.PacketGetPlayerTokenRsp;
import emu.grasscutter.utils.*;
import emu.grasscutter.utils.helpers.ByteHelper;

import javax.crypto.Cipher;
import java.nio.ByteBuffer;
import java.security.Signature;

import static emu.grasscutter.config.Configuration.ACCOUNT;

@Opcodes(PacketOpcodes.GetPlayerTokenReq)
public class HandlerGetPlayerTokenReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = GetPlayerTokenReq.parseFrom(payload);

        // Fetch the account from the ID and token.
        var accountId = req.getAccountUid();
        var account = DispatchUtils.authenticate(accountId, req.getAccountToken());

        // Check the account.
        if (account == null && !DebugConstants.ACCEPT_CLIENT_TOKEN) {
            session.close();
            return;
        } else if (account == null && DebugConstants.ACCEPT_CLIENT_TOKEN) {
            account = DispatchUtils.getAccountById(accountId);
            if (account == null) {
                session.close();
                return;
            }
        }

        // Set account
        session.setAccount(account);

        // Check if player object exists in server
        // NOTE: CHECKING MUST SITUATED HERE (BEFORE getPlayerByUid)! because to save firstly ,to load
        // secondly !!!
        // TODO - optimize
        boolean kicked = false;
        var exists = Grasscutter.getGameServer().getPlayerByAccountId(accountId);
        if (exists != null) {
            var existsSession = exists.getSession();
            if (existsSession != session) { // No self-kicking
                exists.onLogout(); // must save immediately , or the below will load old data
                existsSession.close();
                Grasscutter.getLogger()
                        .warn("Player {} was kicked due to duplicated login", account.getUsername());
                kicked = true;
            }
        }

        // NOTE: If there are 5 online players, max count of player is 5,
        // a new client want to login by kicking one of them ,
        // I think it should be allowed
        if (!kicked) {
            // Max players limit
            if (ACCOUNT.maxPlayer > -1
                    && Grasscutter.getGameServer().getPlayers().size() >= ACCOUNT.maxPlayer) {
                session.close();
                return;
            }
        }

        // Call creation event.
        var event = new PlayerCreationEvent(session, Player.class);
        event.call();

        // Get player.
        var player = DatabaseHelper.getPlayerByAccount(account, event.getPlayerClass());

        if (player == null) {
            var nextPlayerUid =
                    DatabaseHelper.getNextPlayerId(session.getAccount().getReservedPlayerUid());

            // Create player instance from event.
            player =
                    event.getPlayerClass().getDeclaredConstructor(GameSession.class).newInstance(session);

            // Save to db
            DatabaseHelper.generatePlayerUid(player, nextPlayerUid);
        }

        // Set player object for session
        session.setPlayer(player);

        // Checks if the player is banned
        if (session.getAccount().isBanned()) {
            session.setState(SessionState.ACCOUNT_BANNED);
            session.send(
                    new PacketGetPlayerTokenRsp(
                            session, 21, "FORBID_CHEATING_PLUGINS", session.getAccount().getBanEndTime()));
            return;
        }

        // Load player from database
        player.loadFromDatabase();

        // Set session state
        session.setUseSecretKey(true);
        session.setState(SessionState.WAITING_FOR_LOGIN);

        // Only >= 2.7.50 has this
        if (req.getKeyId() > 0) {
            try {
                var cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                cipher.init(Cipher.DECRYPT_MODE, Crypto.CUR_SIGNING_KEY);

                var client_seed_encrypted = Utils.base64Decode(req.getClientRandKey());
                var client_seed = ByteBuffer.wrap(cipher.doFinal(client_seed_encrypted)).getLong();

                var seed_bytes =
                        ByteBuffer.wrap(new byte[8]).putLong(Crypto.ENCRYPT_SEED ^ client_seed).array();

                cipher.init(Cipher.ENCRYPT_MODE, Crypto.EncryptionKeys.get(req.getKeyId()));
                var seed_encrypted = cipher.doFinal(seed_bytes);

                var privateSignature = Signature.getInstance("SHA256withRSA");
                privateSignature.initSign(Crypto.CUR_SIGNING_KEY);
                privateSignature.update(seed_bytes);

                session.send(
                        new PacketGetPlayerTokenRsp(
                                session,
                                Utils.base64Encode(seed_encrypted),
                                Utils.base64Encode(privateSignature.sign())));
            } catch (Exception ignored) {
                // Only UA Patch users will have exception
                var clientBytes = Utils.base64Decode(req.getClientRandKey());
                var seed = ByteHelper.longToBytes(Crypto.ENCRYPT_SEED);
                Crypto.xor(clientBytes, seed);

                var base64str = Utils.base64Encode(clientBytes);
                session.send(new PacketGetPlayerTokenRsp(session, base64str, "bm90aGluZyBoZXJl"));
            }
        } else {
            // Send packet
            session.send(new PacketGetPlayerTokenRsp(session));
        }
    }
}
