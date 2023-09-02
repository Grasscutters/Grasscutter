package emu.grasscutter.utils;

import static emu.grasscutter.config.Configuration.DISPATCH_INFO;

import com.google.gson.JsonObject;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.auth.AuthenticationSystem.AuthenticationRequest;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.*;
import emu.grasscutter.server.dispatch.*;
import emu.grasscutter.server.http.handlers.GachaHandler;
import emu.grasscutter.server.http.objects.LoginTokenRequestJson;
import emu.grasscutter.utils.objects.*;
import emu.grasscutter.utils.objects.HandbookBody.*;
import java.util.concurrent.*;
import javax.annotation.Nullable;

public interface DispatchUtils {
    /**
     * @return The dispatch URL.
     */
    static String getDispatchUrl() {
        return DISPATCH_INFO.dispatchUrl;
    }

    /**
     * Validates an authentication request.
     *
     * @param accountId The account ID.
     * @param token The token.
     * @return {@code true} if the authentication request is valid, otherwise {@code false}.
     */
    @Nullable static Account authenticate(String accountId, String token) {
        return switch (Grasscutter.getRunMode()) {
            case GAME_ONLY ->
            // Use the authentication system to validate the token.
            Grasscutter.getAuthenticationSystem()
                    .getSessionTokenValidator()
                    .authenticate(
                            AuthenticationRequest.builder()
                                    .tokenRequest(LoginTokenRequestJson.builder().uid(accountId).token(token).build())
                                    .build());
            case HYBRID, DISPATCH_ONLY -> {
                // Fetch the account from the database.
                var account = DatabaseHelper.getAccountById(accountId);
                if (account == null) yield null;

                // Check if the token is valid.
                yield account.getToken().equals(token) ? account : null;
            }
        };
    }

    /**
     * Fetches the session key for the specified player ID.
     *
     * @param playerId The player ID.
     * @return The session key.
     */
    @Nullable static String fetchSessionKey(int playerId) {
        return switch (Grasscutter.getRunMode()) {
            case GAME_ONLY -> {
                // Fetch the player from the game server.
                var player = DatabaseHelper.getPlayerByUid(playerId);
                if (player == null) yield null;

                // Fetch the account from the dispatch server.
                var accountId = player.getAccountId();
                var account = DispatchUtils.getAccountById(accountId);

                // Return the session key.
                yield account == null ? null : account.getSessionKey();
            }
            case DISPATCH_ONLY -> {
                // Fetch the player's account ID from the game server.
                var playerFields = DispatchUtils.getPlayerFields(playerId, "accountId");
                if (playerFields == null) yield null;

                // Get the account ID.
                var accountId = playerFields.get("accountId").getAsString();
                if (accountId == null) yield null;

                // Fetch the account from the dispatch server.
                var account = DatabaseHelper.getAccountById(accountId);
                // Return the session key.
                yield account == null ? null : account.getSessionKey();
            }
            case HYBRID -> {
                // Fetch the player from the game server.
                var player = DatabaseHelper.getPlayerByUid(playerId);
                if (player == null) yield null;

                // Fetch the account from the database.
                var account = player.getAccount();
                // Return the session key.
                yield account == null ? null : account.getSessionKey();
            }
        };
    }

    /**
     * Fetches an account by its ID.
     *
     * @param accountId The account ID.
     * @return The account.
     */
    @Nullable static Account getAccountById(String accountId) {
        return switch (Grasscutter.getRunMode()) {
            case GAME_ONLY -> {
                // Create a request for account information.
                var request = new JsonObject();
                request.addProperty("accountId", accountId);

                // Wait for the request to complete.
                yield Grasscutter.getGameServer()
                        .getDispatchClient()
                        .await(
                                request,
                                PacketIds.GetAccountReq,
                                PacketIds.GetAccountRsp,
                                packet -> IDispatcher.decode(packet, Account.class));
            }
            case HYBRID, DISPATCH_ONLY -> DatabaseHelper.getAccountById(accountId);
        };
    }

    /**
     * Fetches the values of fields for a player.
     *
     * @param playerId The player's ID.
     * @param fields The fields to fetch.
     * @return An object holding the field values.
     */
    @Nullable static JsonObject getPlayerFields(int playerId, String... fields) {
        return switch (Grasscutter.getRunMode()) {
            case DISPATCH_ONLY -> {
                // Create a request for player fields.
                var request = new JsonObject();
                request.addProperty("playerId", playerId);
                request.add("fields", IDispatcher.JSON.toJsonTree(fields));

                // Wait for the request to complete.
                yield Grasscutter.getDispatchServer()
                        .await(
                                request,
                                PacketIds.GetPlayerFieldsReq,
                                PacketIds.GetPlayerFieldsRsp,
                                IDispatcher.DEFAULT_PARSER);
            }
            case HYBRID, GAME_ONLY -> {
                // Get the player by ID.
                var player = Grasscutter.getGameServer().getPlayerByUid(playerId, true);
                if (player == null) yield null;

                // Return the values.
                yield player.fetchFields(fields);
            }
        };
    }

    /**
     * Fetches the values of fields for a player. Uses an account to find the player. Similar to
     * {@link DispatchUtils#getPlayerFields(int, String...)}
     *
     * @param accountId The account ID.
     * @param fields The fields to fetch.
     * @return An object holding the field values.
     */
    @Nullable static JsonObject getPlayerByAccount(String accountId, String... fields) {
        return switch (Grasscutter.getRunMode()) {
            case DISPATCH_ONLY -> {
                // Create a request for player fields.
                var request = JObject.c().add("accountId", accountId).add("fields", fields);

                // Wait for the request to complete.
                yield Grasscutter.getDispatchServer()
                        .await(
                                request.gson(),
                                PacketIds.GetPlayerByAccountReq,
                                PacketIds.GetPlayerByAccountRsp,
                                IDispatcher.DEFAULT_PARSER);
            }
            case HYBRID, GAME_ONLY -> {
                // Get the player by the account.
                var player = Grasscutter.getGameServer().getPlayerByAccountId(accountId);
                if (player == null) yield null;

                // Return the values.
                yield player.fetchFields(fields);
            }
        };
    }

    /**
     * Fetches the gacha history for the specified account.
     *
     * @param accountId The account ID.
     * @param page The page.
     * @param gachaType The gacha type.
     * @return The gacha history.
     */
    static JsonObject fetchGachaRecords(String accountId, int page, int gachaType) {
        return switch (Grasscutter.getRunMode()) {
            case DISPATCH_ONLY -> {
                // Create a request for gacha records.
                var request = new JsonObject();
                request.addProperty("accountId", accountId);
                request.addProperty("page", page);
                request.addProperty("gachaType", gachaType);

                // Create a future for the response.
                var future = new CompletableFuture<JsonObject>();
                // Listen for the response.
                var server = Grasscutter.getDispatchServer();
                server.registerCallback(
                        PacketIds.GachaHistoryRsp,
                        packet -> future.complete(IDispatcher.decode(packet, JsonObject.class)));

                // Broadcast the request.
                server.sendMessage(PacketIds.GachaHistoryReq, request);

                try {
                    // Wait for the response.
                    yield future.get(5L, TimeUnit.SECONDS);
                } catch (Exception ignored) {
                    yield null;
                }
            }
            case HYBRID, GAME_ONLY -> {
                // Create a response object.
                var response = new JsonObject();

                // Get the player's ID from the account.
                var player = Grasscutter.getGameServer().getPlayerByAccountId(accountId);
                if (player == null) {
                    response.addProperty("retcode", 1);
                    yield response;
                }

                // Fetch the gacha records.
                GachaHandler.fetchGachaRecords(player, response, page, gachaType);

                yield response;
            }
        };
    }

    /**
     * Performs a handbook action.
     *
     * @param action The action.
     * @param data The data.
     * @return The response.
     */
    static Response performHandbookAction(HandbookBody.Action action, Object data) {
        return switch (Grasscutter.getRunMode()) {
            case DISPATCH_ONLY -> {
                // Create a request for the 'GM Talk' action.
                var request = new JsonObject();
                request.addProperty("action", action.name());
                request.add("data", JsonUtils.toJson(data));

                // Create a future for the response.
                var future = new CompletableFuture<Response>();
                // Listen for the response.
                var server = Grasscutter.getDispatchServer();
                server.registerCallback(
                        PacketIds.GmTalkRsp,
                        packet -> future.complete(IDispatcher.decode(packet, Response.class)));

                // Broadcast the request.
                server.sendMessage(PacketIds.GmTalkReq, request);

                try {
                    // Wait for the response.
                    yield future.get(5L, TimeUnit.SECONDS);
                } catch (Exception ignored) {
                    yield Response.builder()
                            .status(400)
                            .message("No response received from any server.")
                            .build();
                }
            }
            case HYBRID, GAME_ONLY -> switch (action) {
                case GRANT_AVATAR -> HandbookActions.grantAvatar((GrantAvatar) data);
                case GIVE_ITEM -> HandbookActions.giveItem((GiveItem) data);
                case TELEPORT_TO -> HandbookActions.teleportTo((TeleportTo) data);
                case SPAWN_ENTITY -> HandbookActions.spawnEntity((SpawnEntity) data);
            };
        };
    }
}
