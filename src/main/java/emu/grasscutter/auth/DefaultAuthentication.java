package emu.grasscutter.auth;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.auth.DefaultAuthenticators.*;
import emu.grasscutter.game.Account;
import emu.grasscutter.server.http.objects.ComboTokenResJson;
import emu.grasscutter.server.http.objects.LoginResultJson;

import static emu.grasscutter.utils.Language.translate;

/**
 * The default Grasscutter authentication implementation.
 * Allows all users to access any account.
 */
public final class DefaultAuthentication implements AuthenticationSystem {
    private final Authenticator<LoginResultJson> passwordAuthenticator = new PasswordAuthenticator();
    private final Authenticator<LoginResultJson> tokenAuthenticator = new TokenAuthenticator();
    private final Authenticator<ComboTokenResJson> sessionKeyAuthenticator = new SessionKeyAuthenticator();
    private final ExternalAuthenticator externalAuthenticator = new ExternalAuthentication();
    private final OAuthAuthenticator oAuthAuthenticator = new OAuthAuthentication();

    @Override
    public void createAccount(String username, String password) {
        // Unhandled. The default authenticator doesn't store passwords.
    }

    @Override
    public void resetPassword(String username) {
        // Unhandled. The default authenticator doesn't store passwords.
    }

    @Override
    public Account verifyUser(String details) {
        Grasscutter.getLogger().info(translate("dispatch.authentication.default_unable_to_verify"));
        return null;
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

    @Override
    public ExternalAuthenticator getExternalAuthenticator() {
        return this.externalAuthenticator;
    }

    @Override
    public OAuthAuthenticator getOAuthAuthenticator() {
        return this.oAuthAuthenticator;
    }
}
