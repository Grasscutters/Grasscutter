package emu.grasscutter.auth;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.auth.AuthenticationSystem.AuthenticationRequest;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.Account;
import emu.grasscutter.server.http.objects.*;

import static emu.grasscutter.Configuration.*;
import static emu.grasscutter.utils.Language.translate;

/**
 * A class containing default authenticators.
 */
public final class DefaultAuthenticators {
    
    /**
     * Handles the authentication request from the username and password form.
     */
    public static class PasswordAuthenticator implements Authenticator<LoginResultJson> {
        @Override public LoginResultJson authenticate(AuthenticationRequest request) {
            var response = new LoginResultJson();
            
            var requestData = request.getPasswordRequest();
            assert requestData != null; // This should never be null.
            
            boolean successfulLogin = false; 
            String address = request.getRequest().ip();
            String responseMessage = translate("messages.dispatch.account.username_error");
            
            // Get account from database.
            Account account = DatabaseHelper.getAccountByName(requestData.account);
            
            // Check if account exists.
            if(account == null && ACCOUNT.autoCreate) {
                // This account has been created AUTOMATICALLY. There will be no permissions added.
                account = DatabaseHelper.createAccountWithId(requestData.account, 0);
                
                // Check if the account was created successfully.
                if(account == null) {
                    responseMessage = translate("messages.dispatch.account.username_create_error");
                    Grasscutter.getLogger().info(translate("messages.dispatch.account.account_login_create_error", address));
                } else {
                    // Continue with login.
                    successfulLogin = true;

                    // Log the creation.
                    Grasscutter.getLogger().info(translate("messages.dispatch.account.account_login_create_success", address, response.data.account.uid));
                }
            } else if(account != null)
                successfulLogin = true;
            
            // Set response data.
            if(successfulLogin) {
                response.message = "OK";
                response.data.account.uid = account.getId();
                response.data.account.token = account.generateSessionKey();
                response.data.account.email = account.getEmail();
                
                // Log the login.
                Grasscutter.getLogger().info(translate("messages.dispatch.account.login_success", address, account.getId()));
            } else {
                response.retcode = -201;
                response.message = responseMessage;
                
                // Log the failure.
                Grasscutter.getLogger().info(translate("messages.dispatch.account.account_login_exist_error", address));
            }
            
            return response;
        }
    }

    /**
     * Handles the authentication request from the game when using a registry token.
     */
    public static class TokenAuthenticator implements Authenticator<LoginResultJson> {
        @Override public LoginResultJson authenticate(AuthenticationRequest request) {
            var response = new LoginResultJson();
            
            var requestData = request.getTokenRequest();
            assert requestData != null;
            
            boolean successfulLogin;
            String address = request.getRequest().ip();
            
            // Log the attempt.
            Grasscutter.getLogger().info(translate("messages.dispatch.account.login_token_attempt", address));
            
            // Get account from database.
            Account account = DatabaseHelper.getAccountById(requestData.uid);
            
            // Check if account exists/token is valid.
            successfulLogin = account != null && account.getSessionKey().equals(requestData.token);
            
            // Set response data.
            if(successfulLogin) {
                response.message = "OK";
                response.data.account.uid = account.getId();
                response.data.account.token = account.getSessionKey();
                response.data.account.email = account.getEmail();
                
                // Log the login.
                Grasscutter.getLogger().info(translate("messages.dispatch.account.login_token_success", address, requestData.uid));
            } else {
                response.retcode = -201;
                response.message = translate("messages.dispatch.account.account_cache_error");
                
                // Log the failure.
                Grasscutter.getLogger().info(translate("messages.dispatch.account.login_token_error", address));
            }
            
            return response;
        }
    }

    /**
     * Handles the authentication request from the game when using a combo token/session key.
     */
    public static class SessionKeyAuthenticator implements Authenticator<ComboTokenResJson> {
        @Override public ComboTokenResJson authenticate(AuthenticationRequest request) {
            var response  = new ComboTokenResJson();
            
            var requestData = request.getSessionKeyRequest();
            var loginData = request.getSessionKeyData();
            assert requestData != null; assert loginData != null;
            
            boolean successfulLogin;
            String address = request.getRequest().ip();
            
            // Get account from database.
            Account account = DatabaseHelper.getAccountById(loginData.uid);
            
            // Check if account exists/token is valid.
            successfulLogin = account != null && account.getSessionKey().equals(loginData.token);
            
            // Set response data.
            if(successfulLogin) {
                response.message = "OK";
                response.data.open_id = account.getId();
                response.data.combo_id = "157795300";
                response.data.combo_token = account.generateLoginToken();
                
                // Log the login.
                Grasscutter.getLogger().info(translate("messages.dispatch.account.combo_token_success", address));
            } else {
                response.retcode = -201;
                response.message = translate("messages.dispatch.account.session_key_error");
                
                // Log the failure.
                Grasscutter.getLogger().info(translate("messages.dispatch.account.combo_token_error", address));
            }
            
            return response;
        }
    }

    /**
     * Handles authentication requests from external sources.
     */
    public static class ExternalAuthentication implements ExternalAuthenticator {
        @Override public void handleLogin(AuthenticationRequest request) {
            assert request.getResponse() != null;
            request.getResponse().send("Authentication is not available with the default authentication method.");
        }

        @Override public void handleAccountCreation(AuthenticationRequest request) {
            assert request.getResponse() != null;
            request.getResponse().send("Authentication is not available with the default authentication method.");
        }

        @Override public void handlePasswordReset(AuthenticationRequest request) {
            assert request.getResponse() != null;
            request.getResponse().send("Authentication is not available with the default authentication method.");
        }
    }

    /**
     * Handles authentication requests from OAuth sources.
     */
    public static class OAuthAuthentication implements OAuthAuthenticator {
        @Override public void handleLogin(AuthenticationRequest request) {
            assert request.getResponse() != null;
            request.getResponse().send("Authentication is not available with the default authentication method.");
        }

        @Override public void handleDesktopRedirection(AuthenticationRequest request) {
            assert request.getResponse() != null;
            request.getResponse().send("Authentication is not available with the default authentication method.");
        }

        @Override public void handleMobileRedirection(AuthenticationRequest request) {
            assert request.getResponse() != null;
            request.getResponse().send("Authentication is not available with the default authentication method.");
        }

        @Override public void handleTokenProcess(AuthenticationRequest request) {
            assert request.getResponse() != null;
            request.getResponse().send("Authentication is not available with the default authentication method.");
        }
    }
}
