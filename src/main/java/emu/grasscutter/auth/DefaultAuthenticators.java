package emu.grasscutter.auth;

import at.favre.lib.crypto.bcrypt.BCrypt;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.auth.AuthenticationSystem.AuthenticationRequest;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.Account;
import emu.grasscutter.server.http.objects.*;
import emu.grasscutter.utils.FileUtils;
import emu.grasscutter.utils.Utils;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import static emu.grasscutter.config.Configuration.*;
import static emu.grasscutter.utils.Language.translate;

/**
 * A class containing default authenticators.
 */
public final class DefaultAuthenticators {

    /**
     * Handles the authentication request from the username and password form.
     */
    public static class PasswordAuthenticator implements Authenticator<LoginResultJson> {
        @Override
        public LoginResultJson authenticate(AuthenticationRequest request) {
            var response = new LoginResultJson();

            var requestData = request.getPasswordRequest();
            assert requestData != null; // This should never be null.
            int playerCount = Grasscutter.getGameServer().getPlayers().size();

            boolean successfulLogin = false;
            String address = request.getRequest().ip();
            String responseMessage = translate("messages.dispatch.account.username_error");
            String loggerMessage = "";

            // Get account from database.
            Account account = DatabaseHelper.getAccountByName(requestData.account);
            if (ACCOUNT.maxPlayer <= -1 || playerCount < ACCOUNT.maxPlayer) {
                // Check if account exists.
                if (account == null && ACCOUNT.autoCreate) {
                    // This account has been created AUTOMATICALLY. There will be no permissions added.
                    account = DatabaseHelper.createAccountWithUid(requestData.account, 0);

                    // Check if the account was created successfully.
                    if (account == null) {
                        responseMessage = translate("messages.dispatch.account.username_create_error");
                        Grasscutter.getLogger().info(translate("messages.dispatch.account.account_login_create_error", address));
                    } else {
                        // Continue with login.
                        successfulLogin = true;

                        // Log the creation.
                        Grasscutter.getLogger().info(translate("messages.dispatch.account.account_login_create_success", address, response.data.account.uid));
                    }
                } else if (account != null)
                    successfulLogin = true;
                else
                    loggerMessage = translate("messages.dispatch.account.account_login_exist_error", address);

            } else {
                responseMessage = translate("messages.dispatch.account.server_max_player_limit");
                loggerMessage = translate("messages.dispatch.account.login_max_player_limit", address);
            }


            // Set response data.
            if (successfulLogin) {
                response.message = "OK";
                response.data.account.uid = account.getId();
                response.data.account.token = account.generateSessionKey();
                response.data.account.email = account.getEmail();

                loggerMessage = translate("messages.dispatch.account.login_success", address, account.getId());
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
            int playerCount = Grasscutter.getGameServer().getPlayers().size();

            boolean successfulLogin = false;
            String address = request.getRequest().ip();
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

                decryptedPassword = new String(cipher.doFinal(Utils.base64Decode(request.getPasswordRequest().password)), StandardCharsets.UTF_8);
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
            if (ACCOUNT.maxPlayer <= -1 || playerCount < ACCOUNT.maxPlayer) {
                // Check if account exists.
                if (account == null && ACCOUNT.autoCreate) {
                    // This account has been created AUTOMATICALLY. There will be no permissions added.
                    if (decryptedPassword.length() >= 8) {
                        account = DatabaseHelper.createAccountWithUid(requestData.account, 0);
                        account.setPassword(BCrypt.withDefaults().hashToString(12, decryptedPassword.toCharArray()));
                        account.save();

                        // Check if the account was created successfully.
                        if (account == null) {
                            responseMessage = translate("messages.dispatch.account.username_create_error");
                            loggerMessage = translate("messages.dispatch.account.account_login_create_error", address);
                        } else {
                            // Continue with login.
                            successfulLogin = true;

                            // Log the creation.
                            Grasscutter.getLogger().info(translate("messages.dispatch.account.account_login_create_success", address, response.data.account.uid));
                        }
                    } else {
                        successfulLogin = false;
                        loggerMessage = translate("messages.dispatch.account.login_password_error", address);
                        responseMessage = translate("messages.dispatch.account.password_length_error");
                    }
                } else if (account != null) {
                    if (account.getPassword() != null && !account.getPassword().isEmpty()) {
                        if (BCrypt.verifyer().verify(decryptedPassword.toCharArray(), account.getPassword()).verified) {
                            successfulLogin = true;
                        } else {
                            successfulLogin = false;
                            loggerMessage = translate("messages.dispatch.account.login_password_error", address);
                            responseMessage = translate("messages.dispatch.account.password_error");
                        }
                    } else {
                        successfulLogin = false;
                        loggerMessage = translate("messages.dispatch.account.login_password_storage_error", address);
                        responseMessage = translate("messages.dispatch.account.password_storage_error");
                    }
                } else {
                    loggerMessage = translate("messages.dispatch.account.account_login_exist_error", address);
                }
            } else {
                responseMessage = translate("messages.dispatch.account.server_max_player_limit");
                loggerMessage = translate("messages.dispatch.account.login_max_player_limit", address);
            }


            // Set response data.
            if (successfulLogin) {
                response.message = "OK";
                response.data.account.uid = account.getId();
                response.data.account.token = account.generateSessionKey();
                response.data.account.email = account.getEmail();

                loggerMessage = translate("messages.dispatch.account.login_success", address, account.getId());
            } else {
                response.retcode = -201;
                response.message = responseMessage;

            }
            Grasscutter.getLogger().info(loggerMessage);

            return response;
        }
    }

    /**
     * Handles the authentication request from the game when using a registry token.
     */
    public static class TokenAuthenticator implements Authenticator<LoginResultJson> {
        @Override
        public LoginResultJson authenticate(AuthenticationRequest request) {
            var response = new LoginResultJson();

            var requestData = request.getTokenRequest();
            assert requestData != null;

            boolean successfulLogin;
            String address = request.getRequest().ip();
            String loggerMessage;
            int playerCount = Grasscutter.getGameServer().getPlayers().size();

            // Log the attempt.
            Grasscutter.getLogger().info(translate("messages.dispatch.account.login_token_attempt", address));

            if (ACCOUNT.maxPlayer <= -1 || playerCount < ACCOUNT.maxPlayer) {

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
                    loggerMessage = translate("messages.dispatch.account.login_token_success", address, requestData.uid);
                } else {
                    response.retcode = -201;
                    response.message = translate("messages.dispatch.account.account_cache_error");

                    // Log the failure.
                    loggerMessage = translate("messages.dispatch.account.login_token_error", address);
                }

            } else {
                response.retcode = -201;
                response.message = translate("messages.dispatch.account.server_max_player_limit");

                loggerMessage = translate("messages.dispatch.account.login_max_player_limit", address);
            }

            Grasscutter.getLogger().info(loggerMessage);
            return response;
        }
    }

    /**
     * Handles the authentication request from the game when using a combo token/session key.
     */
    public static class SessionKeyAuthenticator implements Authenticator<ComboTokenResJson> {
        @Override
        public ComboTokenResJson authenticate(AuthenticationRequest request) {
            var response = new ComboTokenResJson();

            var requestData = request.getSessionKeyRequest();
            var loginData = request.getSessionKeyData();
            assert requestData != null;
            assert loginData != null;

            boolean successfulLogin;
            String address = request.getRequest().ip();
            String loggerMessage;
            int playerCount = Grasscutter.getGameServer().getPlayers().size();

            if (ACCOUNT.maxPlayer <= -1 || playerCount < ACCOUNT.maxPlayer) {
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
            } else {
                response.retcode = -201;
                response.message = translate("messages.dispatch.account.server_max_player_limit");

                loggerMessage = translate("messages.dispatch.account.login_max_player_limit", address);
            }

            Grasscutter.getLogger().info(loggerMessage);
            return response;
        }
    }

    /**
     * Handles authentication requests from external sources.
     */
    public static class ExternalAuthentication implements ExternalAuthenticator {
        @Override
        public void handleLogin(AuthenticationRequest request) {
            assert request.getResponse() != null;
            request.getResponse().send("Authentication is not available with the default authentication method.");
        }

        @Override
        public void handleAccountCreation(AuthenticationRequest request) {
            assert request.getResponse() != null;
            request.getResponse().send("Authentication is not available with the default authentication method.");
        }

        @Override
        public void handlePasswordReset(AuthenticationRequest request) {
            assert request.getResponse() != null;
            request.getResponse().send("Authentication is not available with the default authentication method.");
        }
    }

    /**
     * Handles authentication requests from OAuth sources.
     */
    public static class OAuthAuthentication implements OAuthAuthenticator {
        @Override
        public void handleLogin(AuthenticationRequest request) {
            assert request.getResponse() != null;
            request.getResponse().send("Authentication is not available with the default authentication method.");
        }

        @Override
        public void handleRedirection(AuthenticationRequest request, ClientType type) {
            assert request.getResponse() != null;
            request.getResponse().send("Authentication is not available with the default authentication method.");
        }

        @Override
        public void handleTokenProcess(AuthenticationRequest request) {
            assert request.getResponse() != null;
            request.getResponse().send("Authentication is not available with the default authentication method.");
        }
    }
}
