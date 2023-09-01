package emu.grasscutter.auth;

import emu.grasscutter.server.http.objects.*;

/**
 * Handles username/password authentication from the client.
 *
 * @param <T> The response object type. Should be {@link LoginResultJson} or {@link
 *     ComboTokenResJson}
 */
public interface Authenticator<T> {

    /**
     * Attempt to authenticate the client with the provided credentials.
     *
     * @param request The authentication request wrapped in a {@link
     *     AuthenticationSystem.AuthenticationRequest} object.
     * @return The result of the login in an object.
     */
    T authenticate(AuthenticationSystem.AuthenticationRequest request);
}
