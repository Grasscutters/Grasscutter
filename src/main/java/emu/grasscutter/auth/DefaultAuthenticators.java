package emu.grasscutter.auth;

import static emu.grasscutter.config.Configuration.ACCOUNT;
import static emu.grasscutter.utils.lang.Language.translate;

import at.favre.lib.crypto.bcrypt.BCrypt;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.Grasscutter.ServerRunMode;
import emu.grasscutter.auth.AuthenticationSystem.AuthenticationRequest;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.Account;
import emu.grasscutter.server.dispatch.*;
import emu.grasscutter.server.http.objects.*;
import emu.grasscutter.utils.*;
import io.javalin.http.ContentType;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.concurrent.*;
import javax.crypto.Cipher;

/** A class containing default authenticators. */
public final class DefaultAuthenticators {

    /** Handles the authentication request from the username and password form. */
    public static class PasswordAuthenticator implements Authenticator<LoginResultJson> {
        @Override
        public LoginResultJson authenticate(AuthenticationRequest request) {
            var response = new LoginResultJson();

            var requestData = request.getPasswordRequest();
            assert requestData != null; // This should never be null.

            boolean successfulLogin = false;
            String address = Utils.address(request.getContext());
            String responseMessage = translate("messages.dispatch.account.username_error");
            String loggerMessage = "";

            // Get account from database.
            Account account = DatabaseHelper.getAccountByName(requestData.account);
            // Check if account exists.
            if (account == null && ACCOUNT.autoCreate) {
                // This account has been created AUTOMATICALLY. There will be no permissions added.
                account = DatabaseHelper.createAccountWithUid(requestData.account, 0);

                // Check if the account was created successfully.
                if (account == null) {
                    responseMessage = translate("messages.dispatch.account.username_create_error");
                    Grasscutter.getLogger()
                            .info(translate("messages.dispatch.account.account_login_create_error", address));
                } else {
                    // Continue with login.
                    successfulLogin = true;

                    // Log the creation.
                    Grasscutter.getLogger()
                            .info(
                                    translate(
                                            "messages.dispatch.account.account_login_create_success",
                                            address,
                                            response.data.account.uid));
                }
            } else if (account != null) successfulLogin = true;
            else
                loggerMessage = translate("messages.dispatch.account.account_login_exist_error", address);

            // Set response data.
            if (successfulLogin) {
                response.message = "OK";
                response.data.account.uid = account.getId();
                response.data.account.token = account.generateSessionKey();
                response.data.account.email = account.getEmail();

                loggerMessage =
                        translate("messages.dispatch.account.login_success", address, account.getId());
            } else {
                response.retcode = -201;
                response.message = responseMessage;
            }
            Grasscutter.getLogger().info(loggerMessage);

            return response;
        }
    }

    public static class ExperimentalPasswordAuthenticator implements Authenticator<LoginResultJson> {
        @Override
        public LoginResultJson authenticate(AuthenticationRequest request) {
            var response = new LoginResultJson();

            var requestData = request.getPasswordRequest();
            assert requestData != null; // This should never be null.

            boolean successfulLogin = false;
            String address = Utils.address(request.getContext());
            String responseMessage = translate("messages.dispatch.account.username_error");
            String loggerMessage = "";
            String decryptedPassword = "";
            try {
                byte[] key = FileUtils.readResource("/keys/auth_private-key.der");
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                RSAPrivateKey private_key = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);

                Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

                cipher.init(Cipher.DECRYPT_MODE, private_key);

                decryptedPassword =
                        new String(
                                cipher.doFinal(Utils.base64Decode(request.getPasswordRequest().password)),
                                StandardCharsets.UTF_8);
            } catch (Exception ignored) {
                decryptedPassword = request.getPasswordRequest().password;
            }

            if (decryptedPassword == null) {
                successfulLogin = false;
                loggerMessage = translate("messages.dispatch.account.login_password_error", address);
                responseMessage = translate("messages.dispatch.account.password_error");
            }

            // Get account from database.
            Account account = DatabaseHelper.getAccountByName(requestData.account);
            // Check if account exists.
            if (account == null && ACCOUNT.autoCreate) {
                // This account has been created AUTOMATICALLY. There will be no permissions added.
                if (decryptedPassword.length() >= 8) {
                    account = DatabaseHelper.createAccountWithUid(requestData.account, 0);
                    account.setPassword(
                            BCrypt.withDefaults().hashToString(12, decryptedPassword.toCharArray()));
                    account.save();

                    // Check if the account was created successfully.
                    if (account == null) {
                        responseMessage = translate("messages.dispatch.account.username_create_error");
                        loggerMessage =
                                translate("messages.dispatch.account.account_login_create_error", address);
                    } else {
                        // Continue with login.
                        successfulLogin = true;

                        // Log the creation.
                        Grasscutter.getLogger()
                                .info(
                                        translate(
                                                "messages.dispatch.account.account_login_create_success",
                                                address,
                                                response.data.account.uid));
                    }
                } else {
                    successfulLogin = false;
                    loggerMessage = translate("messages.dispatch.account.login_password_error", address);
                    responseMessage = translate("messages.dispatch.account.password_length_error");
                }
            } else if (account != null) {
                if (account.getPassword() != null && !account.getPassword().isEmpty()) {
                    if (BCrypt.verifyer()
                            .verify(decryptedPassword.toCharArray(), account.getPassword())
                            .verified) {
                        successfulLogin = true;
                    } else {
                        successfulLogin = false;
                        loggerMessage = translate("messages.dispatch.account.login_password_error", address);
                        responseMessage = translate("messages.dispatch.account.password_error");
                    }
                } else {
                    successfulLogin = false;
                    loggerMessage =
                            translate("messages.dispatch.account.login_password_storage_error", address);
                    responseMessage = translate("messages.dispatch.account.password_storage_error");
                }
            } else {
                loggerMessage = translate("messages.dispatch.account.account_login_exist_error", address);
            }

            // Set response data.
            if (successfulLogin) {
                response.message = "OK";
                response.data.account.uid = account.getId();
                response.data.account.token = account.generateSessionKey();
                response.data.account.email = account.getEmail();

                loggerMessage =
                        translate("messages.dispatch.account.login_success", address, account.getId());
            } else {
                response.retcode = -201;
                response.message = responseMessage;
            }
            Grasscutter.getLogger().info(loggerMessage);

            return response;
        }
    }

    /** Handles the authentication request from the game when using a registry token. */
    public static class TokenAuthenticator implements Authenticator<LoginResultJson> {
        @Override
        public LoginResultJson authenticate(AuthenticationRequest request) {
            var response = new LoginResultJson();

            var requestData = request.getTokenRequest();
            assert requestData != null;

            boolean successfulLogin;
            String address = Utils.address(request.getContext());
            String loggerMessage;

            // Log the attempt.
            Grasscutter.getLogger()
                    .info(translate("messages.dispatch.account.login_token_attempt", address));

            // Get account from database.
            Account account = DatabaseHelper.getAccountById(requestData.uid);

            // Check if account exists/token is valid.
            successfulLogin = account != null && account.getSessionKey().equals(requestData.token);

            // Set response data.
            if (successfulLogin) {
                response.message = "OK";
                response.data.account.uid = account.getId();
                response.data.account.token = account.getSessionKey();
                response.data.account.email = account.getEmail();

                // Log the login.
                loggerMessage =
                        translate("messages.dispatch.account.login_token_success", address, requestData.uid);
            } else {
                response.retcode = -201;
                response.message = translate("messages.dispatch.account.account_cache_error");

                // Log the failure.
                loggerMessage = translate("messages.dispatch.account.login_token_error", address);
            }

            Grasscutter.getLogger().info(loggerMessage);
            return response;
        }
    }

    /** Handles the authentication request from the game when using a combo token/session key. */
    public static class SessionKeyAuthenticator implements Authenticator<ComboTokenResJson> {
        @Override
        public ComboTokenResJson authenticate(AuthenticationRequest request) {
            var response = new ComboTokenResJson();

            var requestData = request.getSessionKeyRequest();
            var loginData = request.getSessionKeyData();
            assert requestData != null;
            assert loginData != null;

            boolean successfulLogin;
            String address = Utils.address(request.getContext());
            String loggerMessage;

            // Get account from database.
            Account account = DatabaseHelper.getAccountById(loginData.uid);

            // Check if account exists/token is valid.
            successfulLogin = account != null && account.getSessionKey().equals(loginData.token);

            // Set response data.
            if (successfulLogin) {
                response.message = "OK";
                response.data.open_id = account.getId();
                response.data.combo_id = "157795300";
                response.data.combo_token = account.generateLoginToken();

                // Log the login.
                loggerMessage = translate("messages.dispatch.account.combo_token_success", address);

            } else {
                response.retcode = -201;
                response.message = translate("messages.dispatch.account.session_key_error");

                // Log the failure.
                loggerMessage = translate("messages.dispatch.account.combo_token_error", address);
            }

            Grasscutter.getLogger().info(loggerMessage);
            return response;
        }
    }

    /** Handles authentication requests from external sources. */
    public static class ExternalAuthentication implements ExternalAuthenticator {
        @Override
        public void handleLogin(AuthenticationRequest request) {
            request
                    .getContext()
                    .result("Authentication is not available with the default authentication method.");
        }

        @Override
        public void handleAccountCreation(AuthenticationRequest request) {
            request
                    .getContext()
                    .result("Authentication is not available with the default authentication method.");
        }

        @Override
        public void handlePasswordReset(AuthenticationRequest request) {
            request
                    .getContext()
                    .result("Authentication is not available with the default authentication method.");
        }
    }

    /** Handles authentication requests from OAuth sources.Zenlith */
    public static class OAuthAuthentication implements OAuthAuthenticator {
        @Override
        public void handleLogin(AuthenticationRequest request) {
            request
                    .getContext()
                    .result("Authentication is not available with the default authentication method.");
        }

        @Override
        public void handleRedirection(AuthenticationRequest request, ClientType type) {
            request
                    .getContext()
                    .result("Authentication is not available with the default authentication method.");
        }

        @Override
        public void handleTokenProcess(AuthenticationRequest request) {
            request
                    .getContext()
                    .result("Authentication is not available with the default authentication method.");
        }
    }

    /** Validates a session token during game login. */
    public static class SessionTokenValidator implements Authenticator<Account> {
        @Override
        public Account authenticate(AuthenticationRequest request) {
            var tokenRequest = request.getTokenRequest();
            if (tokenRequest == null) {
                Grasscutter.getLogger().warn("Invalid session token validator request.");
                return null;
            }

            // Prepare the request.
            var client = Grasscutter.getGameServer().getDispatchClient();
            var future = new CompletableFuture<Account>();

            client.registerCallback(
                    PacketIds.TokenValidateRsp,
                    packet -> {
                        var data = IDispatcher.decode(packet);

                        // Check if the token is valid.
                        var valid = data.get("valid").getAsBoolean();
                        if (!valid) {
                            future.complete(null);
                            return;
                        }

                        // Return the account data.
                        future.complete(IDispatcher.decode(data.get("account"), Account.class));
                    });
            client.sendMessage(PacketIds.TokenValidateReq, tokenRequest);

            try {
                return future.get(5, TimeUnit.SECONDS);
            } catch (Exception ignored) {
                return null;
            }
        }
    }

    /** Handles authentication for the web GM Handbook. */
    public static class HandbookAuthentication implements HandbookAuthenticator {
        private final String authPage;

        public HandbookAuthentication() {
            try {
                this.authPage = new String(FileUtils.readResource("/html/handbook_auth.html"));
            } catch (Exception ignored) {
                throw new RuntimeException("Failed to load handbook auth page.");
            }
        }

        @Override
        public void presentPage(AuthenticationRequest request) {
            var ctx = request.getContext();
            if (ctx == null) return;

            // Check to see if an IP authentication can be performed.
            if (Grasscutter.getRunMode() == ServerRunMode.HYBRID) {
                var player = Grasscutter.getGameServer().getPlayerByIpAddress(Utils.address(ctx));
                if (player != null) {
                    // Get the player's session token.
                    var sessionKey = player.getAccount().getSessionKey();
                    // Respond with the handbook auth page.
                    ctx.status(200)
                            .result(
                                    this.authPage
                                            .replace("{{VALUE}}", "true")
                                            .replace("{{SESSION_TOKEN}}", sessionKey)
                                            .replace("{{PLAYER_ID}}", String.valueOf(player.getUid())));
                    return;
                }
            }

            // Respond with the handbook auth page.
            ctx.contentType(ContentType.TEXT_HTML).result(this.authPage);
        }

        @Override
        public Response authenticate(AuthenticationRequest request) {
            var ctx = request.getContext();
            if (ctx == null) return null;

            // Get the body data.
            var playerId = ctx.formParam("playerid");
            if (playerId == null) {
                return Response.builder().status(400).body("Invalid player ID.").build();
            }

            try {
                // Get the player's session token.
                var sessionKey = DispatchUtils.fetchSessionKey(Integer.parseInt(playerId));
                if (sessionKey == null) {
                    return Response.builder().status(400).body("Invalid player ID.").build();
                }

                // Check if the account is banned.
                return Response.builder()
                        .status(200)
                        .body(
                                this.authPage
                                        .replace("{{VALUE}}", "true")
                                        .replace("{{SESSION_TOKEN}}", sessionKey)
                                        .replace("{{PLAYER_ID}}", playerId))
                        .build();
            } catch (NumberFormatException ignored) {
                return Response.builder().status(500).body("Invalid player ID.").build();
            }
        }
    }
}
