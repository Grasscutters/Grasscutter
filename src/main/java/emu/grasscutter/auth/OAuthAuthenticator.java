package emu.grasscutter.auth;

import emu.grasscutter.auth.AuthenticationSystem.AuthenticationRequest;

/**
 * Handles authentication via OAuth routes.
 */
public interface OAuthAuthenticator {

    /**
     * Called when an OAuth login request is made.
     * @param request The authentication request.
     */
    void handleLogin(AuthenticationRequest request);

    /**
     * Called when an client requests to redirect to login page.
     * @param request The authentication request.
     */
    void handleDesktopRedirection(AuthenticationRequest request);
    void handleMobileRedirection(AuthenticationRequest request);

    /**
     * Called when an OAuth login requests callback.
     * @param request The authentication request.
     */
    void handleTokenProcess(AuthenticationRequest request);
}
