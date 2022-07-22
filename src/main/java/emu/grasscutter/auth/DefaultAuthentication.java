package emu.grasscutter.auth;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.auth.DefaultAuthenticators.*;
import emu.grasscutter.game.Account;
import emu.grasscutter.server.http.objects.ComboTokenResJson;
import emu.grasscutter.server.http.objects.LoginResultJson;

import static emu.grasscutter.config.Configuration.ACCOUNT;
import static emu.grasscutter.utils.Language.translate;

/**
 * The default Grasscutter authentication implementation.
 * Allows all users to access any account.
 */
public final class DefaultAuthentication implements AuthenticationSystem {
    private Authenticator<LoginResultJson> passwordAuthenticator;
    private Authenticator<LoginResultJson> tokenAuthenticator = new TokenAuthenticator();
    private Authenticator<ComboTokenResJson> sessionKeyAuthenticator = new SessionKeyAuthenticator();
    private ExternalAuthenticator externalAuthenticator = new ExternalAuthentication();
    private OAuthAuthenticator oAuthAuthenticator = new OAuthAuthentication();

    public DefaultAuthentication() {
        if (ACCOUNT.EXPERIMENTAL_RealPassword) {
            passwordAuthenticator = new ExperimentalPasswordAuthenticator();
        } else {
            passwordAuthenticator = new PasswordAuthenticator();
        }
    }

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
        Grasscutter.getLogger().info(translate("messages.dispatch.authentication.default_unable_to_verify"));
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
