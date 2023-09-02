package emu.grasscutter.auth;

import emu.grasscutter.game.Account;
import emu.grasscutter.server.http.objects.*;
import emu.grasscutter.utils.DispatchUtils;
import io.javalin.http.Context;
import javax.annotation.Nullable;
import lombok.*;

/** Defines an authenticator for the server. Can be changed by plugins. */
public interface AuthenticationSystem {

    /**
     * Generates an authentication request from a {@link LoginAccountRequestJson} object.
     *
     * @param ctx The Javalin context.
     * @param jsonData The JSON data.
     * @return An authentication request.
     */
    static AuthenticationRequest fromPasswordRequest(Context ctx, LoginAccountRequestJson jsonData) {
        return AuthenticationRequest.builder().context(ctx).passwordRequest(jsonData).build();
    }

    /**
     * Generates an authentication request from a {@link LoginTokenRequestJson} object.
     *
     * @param ctx The Javalin context.
     * @param jsonData The JSON data.
     * @return An authentication request.
     */
    static AuthenticationRequest fromTokenRequest(Context ctx, LoginTokenRequestJson jsonData) {
        return AuthenticationRequest.builder().context(ctx).tokenRequest(jsonData).build();
    }

    /**
     * Generates an authentication request from a {@link ComboTokenReqJson} object.
     *
     * @param ctx The Javalin context.
     * @param jsonData The JSON data.
     * @return An authentication request.
     */
    static AuthenticationRequest fromComboTokenRequest(
            Context ctx, ComboTokenReqJson jsonData, ComboTokenReqJson.LoginTokenData tokenData) {
        return AuthenticationRequest.builder()
                .context(ctx)
                .sessionKeyRequest(jsonData)
                .sessionKeyData(tokenData)
                .build();
    }

    /**
     * Generates an authentication request from a {@link Context} object.
     *
     * @param ctx The Javalin context.
     * @return An authentication request.
     */
    static AuthenticationRequest fromExternalRequest(Context ctx) {
        return AuthenticationRequest.builder().context(ctx).build();
    }

    /**
     * Called when a user requests to make an account.
     *
     * @param username The provided username.
     * @param password The provided password. (SHA-256'ed)
     */
    void createAccount(String username, String password);

    /**
     * Called when a user requests to reset their password.
     *
     * @param username The username of the account to reset.
     */
    void resetPassword(String username);

    /**
     * Called by plugins to internally verify a user's identity.
     *
     * @param details A unique identifier to identify the user. (For example: a JWT token)
     * @return The user's account if the verification was successful, null if the user was unable to
     *     be verified.
     */
    Account verifyUser(String details);

    /**
     * This is the authenticator used for password authentication.
     *
     * @return An authenticator.
     */
    Authenticator<LoginResultJson> getPasswordAuthenticator();

    /**
     * This is the authenticator used for token authentication.
     *
     * @return An authenticator.
     */
    Authenticator<LoginResultJson> getTokenAuthenticator();

    /**
     * This is the authenticator used for session authentication.
     *
     * @return An authenticator.
     */
    Authenticator<ComboTokenResJson> getSessionKeyAuthenticator();

    /**
     * This is the authenticator used for validating session tokens. This is a part of the logic in
     * {@link DispatchUtils#authenticate(String, String)}.
     *
     * <p>Plugins can override this authenticator to add support for alternate session authentication
     * methods.
     *
     * @return {@code true} if the session token is valid, {@code false} otherwise.
     */
    Authenticator<Account> getSessionTokenValidator();

    /**
     * This is the authenticator used for handling external authentication requests.
     *
     * @return An authenticator.
     */
    ExternalAuthenticator getExternalAuthenticator();

    /**
     * This is the authenticator used for handling OAuth authentication requests.
     *
     * @return An authenticator.
     */
    OAuthAuthenticator getOAuthAuthenticator();

    /**
     * This is the authenticator used for handling handbook authentication requests.
     *
     * @return An authenticator.
     */
    HandbookAuthenticator getHandbookAuthenticator();

    /** A data container that holds relevant data for authenticating a client. */
    @Builder
    @AllArgsConstructor
    @Getter
    class AuthenticationRequest {
        @Nullable private final Context context;

        @Nullable private final LoginAccountRequestJson passwordRequest;
        @Nullable private final LoginTokenRequestJson tokenRequest;
        @Nullable private final ComboTokenReqJson sessionKeyRequest;
        @Nullable private final ComboTokenReqJson.LoginTokenData sessionKeyData;
    }
}
