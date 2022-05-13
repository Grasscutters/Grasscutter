package emu.grasscutter.auth;

import emu.grasscutter.auth.DefaultAuthenticators.*;
import emu.grasscutter.server.http.objects.ComboTokenResJson;
import emu.grasscutter.server.http.objects.LoginResultJson;

/**
 * The default Grasscutter authentication implementation.
 * Allows all users to access any account.
 */
public final class DefaultAuthentication implements AuthenticationSystem {
    private final Authenticator<LoginResultJson> passwordAuthenticator = new PasswordAuthenticator();
    private final Authenticator<LoginResultJson> tokenAuthenticator = new TokenAuthenticator();
    private final Authenticator<ComboTokenResJson> sessionKeyAuthenticator = new SessionKeyAuthenticator();
    
    @Override
    public void createAccount(String username, String password) {
        // Unhandled. The default authenticator doesn't store passwords.
    }

    @Override
    public void resetPassword(String username) {
        // Unhandled. The default authenticator doesn't store passwords.
    }

    @Override
    public Authenticator<LoginResultJson> getPasswordAuthenticator() {
        return this.passwordAuthenticator;
    }

    @Override
    public Authenticator<LoginResultJson> getTokenAuthenticator() {
        return this.tokenAuthenticator;
    }

    @Override
    public Authenticator<ComboTokenResJson> getSessionKeyAuthenticator() {
        return this.sessionKeyAuthenticator;
    }
}
