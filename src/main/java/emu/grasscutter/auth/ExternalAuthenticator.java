package emu.grasscutter.auth;

import emu.grasscutter.auth.AuthenticationSystem.AuthenticationRequest;

/** Handles authentication via external routes. */
public interface ExternalAuthenticator {

    /**
     * Called when an external login request is made.
     *
     * @param request The authentication request.
     */
    void handleLogin(AuthenticationRequest request);

    /**
     * Called when an external account creation request is made.
     *
     * @param request The authentication request.
     *     <p>For developers: Use AuthenticationRequest#getRequest() to get the request body. Use
     *     AuthenticationRequest#getResponse() to get the response body.
     */
    void handleAccountCreation(AuthenticationRequest request);

    /**
     * Called when an external password reset request is made.
     *
     * @param request The authentication request.
     *     <p>For developers: Use AuthenticationRequest#getRequest() to get the request body. Use
     *     AuthenticationRequest#getResponse() to get the response body.
     */
    void handlePasswordReset(AuthenticationRequest request);
}
