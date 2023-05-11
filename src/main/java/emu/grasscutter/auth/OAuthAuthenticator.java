package emu.grasscutter.auth;

import emu.grasscutter.auth.AuthenticationSystem.AuthenticationRequest;

/** Handles authentication via OAuth routes. */
public interface OAuthAuthenticator {

    /**
     * Called when an OAuth login request is made.
     *
     * @param request The authentication request.
     */
    void handleLogin(AuthenticationRequest request);

    /**
     * Called when a client requests to redirect to login page.
     *
     * @param request The authentication request.
     */
    void handleRedirection(AuthenticationRequest request, ClientType clientType);

    /**
     * Called when an OAuth login requests callback.
     *
     * @param request The authentication request.
     */
    void handleTokenProcess(AuthenticationRequest request);

    /** The type of the client. Used for handling redirection. */
    enum ClientType {
        DESKTOP,
        MOBILE
    }
}
