package emu.grasscutter.utils;

import static emu.grasscutter.config.Configuration.DISPATCH_INFO;

import com.google.gson.JsonObject;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.auth.AuthenticationSystem.AuthenticationRequest;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.HandbookActions;
import emu.grasscutter.server.dispatch.IDispatcher;
import emu.grasscutter.server.dispatch.PacketIds;
import emu.grasscutter.server.http.handlers.GachaHandler;
import emu.grasscutter.server.http.objects.LoginTokenRequestJson;
import emu.grasscutter.utils.objects.HandbookBody;
import emu.grasscutter.utils.objects.HandbookBody.*;
import java.net.http.HttpClient;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

public interface DispatchUtils {
    /** HTTP client used for dispatch queries. */
    HttpClient HTTP_CLIENT =
            HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .followRedirects(HttpClient.Redirect.ALWAYS)
                    .build();

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
