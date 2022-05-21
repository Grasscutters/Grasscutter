package emu.grasscutter.auth;

import emu.grasscutter.game.Account;
import emu.grasscutter.server.http.objects.*;
import express.http.Request;
import express.http.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.annotation.Nullable;

/**
 * Defines an authenticator for the server.
 * Can be changed by plugins.
 */
public interface AuthenticationSystem {
    
    /**
     * Called when a user requests to make an account.
     * @param username The provided username.
     * @param password The provided password. (SHA-256'ed)
     */
    void createAccount(String username, String password);

    /**
     * Called when a user requests to reset their password.
     * @param username The username of the account to reset.
     */
    void resetPassword(String username);

    /**
     * Called by plugins to internally verify a user's identity.
     * @param details A unique identifier to identify the user. (For example: a JWT token)
     * @return The user's account if the verification was successful, null if the user was unable to be verified.
     */
    Account verifyUser(String details);

    /**
     * This is the authenticator used for password authentication.
     * @return An authenticator.
     */
    Authenticator<LoginResultJson> getPasswordAuthenticator();

    /**
     * This is the authenticator used for token authentication.
     * @return An authenticator.
     */
    Authenticator<LoginResultJson> getTokenAuthenticator();

    /**
     * This is the authenticator used for session authentication.
     * @return An authenticator.
     */
    Authenticator<ComboTokenResJson> getSessionKeyAuthenticator();

    /**
     * This is the authenticator used for handling external authentication requests.
     * @return An authenticator.
     */
    ExternalAuthenticator getExternalAuthenticator();

    /**
     * This is the authenticator used for handling OAuth authentication requests.
     * @return An authenticator.
     */
    OAuthAuthenticator getOAuthAuthenticator();

    /**
     * A data container that holds relevant data for authenticating a client.
     */
    @Builder @AllArgsConstructor @Getter
    class AuthenticationRequest {
        private final Request request;
        @Nullable private final Response response;
        
        @Nullable private final LoginAccountRequestJson passwordRequest;
        @Nullable private final LoginTokenRequestJson tokenRequest;
        @Nullable private final ComboTokenReqJson sessionKeyRequest;
        @Nullable private final ComboTokenReqJson.LoginTokenData sessionKeyData;
    }

    /**
     * Generates an authentication request from a {@link LoginAccountRequestJson} object.
     * @param request The Express request.
     * @param jsonData The JSON data.
     * @return An authentication request.
     */
    static AuthenticationRequest fromPasswordRequest(Request request, LoginAccountRequestJson jsonData) {
        return AuthenticationRequest.builder()
                .request(request)
                .passwordRequest(jsonData)
                .build();
    }

    /**
     * Generates an authentication request from a {@link LoginTokenRequestJson} object.
     * @param request The Express request.
     * @param jsonData The JSON data.
     * @return An authentication request.
     */
    static AuthenticationRequest fromTokenRequest(Request request, LoginTokenRequestJson jsonData) {
        return AuthenticationRequest.builder()
                .request(request)
                .tokenRequest(jsonData)
                .build();
    }

    /**
     * Generates an authentication request from a {@link ComboTokenReqJson} object.
     * @param request The Express request.
     * @param jsonData The JSON data.
     * @return An authentication request.
     */
    static AuthenticationRequest fromComboTokenRequest(Request request, ComboTokenReqJson jsonData, 
                                                       ComboTokenReqJson.LoginTokenData tokenData) {
        return AuthenticationRequest.builder()
                .request(request)
                .sessionKeyRequest(jsonData)
                .sessionKeyData(tokenData)
                .build();
    }

    /**
     * Generates an authentication request from a {@link Response} object.
     * @param request The Express request.
     * @param response the Express response.
     * @return An authentication request.
     */
    static AuthenticationRequest fromExternalRequest(Request request, Response response) {
        return AuthenticationRequest.builder().request(request)
                .response(response).build();
    }


    /**
     * Generates an authentication request from a {@link Response} object.
     * @param request The Express request.
     * @param jsonData The JSON data.
     * @return An authentication request.
     */
    static AuthenticationRequest fromOAuthRequest(Request request, Response response) {
        return AuthenticationRequest.builder().request(request)
                .response(response).build();
    }
}
