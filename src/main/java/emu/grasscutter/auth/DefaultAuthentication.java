package emu.grasscutter.auth;

import static emu.grasscutter.config.Configuration.ACCOUNT;
import static emu.grasscutter.utils.lang.Language.translate;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.auth.DefaultAuthenticators.*;
import emu.grasscutter.game.Account;
import emu.grasscutter.server.http.objects.*;

/**
 * The default Grasscutter authentication implementation. Allows all users to access any account.
 */
public final class DefaultAuthentication implements AuthenticationSystem {
    private final Authenticator<LoginResultJson> passwordAuthenticator;
    private final Authenticator<LoginResultJson> tokenAuthenticator = new TokenAuthenticator();
    private final Authenticator<ComboTokenResJson> sessionKeyAuthenticator =
            new SessionKeyAuthenticator();
    private final Authenticator<Account> sessionTokenValidator = new SessionTokenValidator();
    private final ExternalAuthenticator externalAuthenticator = new ExternalAuthentication();
    private final OAuthAuthenticator oAuthAuthenticator = new OAuthAuthentication();
    private final HandbookAuthenticator handbookAuthenticator = new HandbookAuthentication();

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
        Grasscutter.getLogger()
                .info(translate("messages.dispatch.authentication.default_unable_to_verify"));
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
    public Authenticator<Account> getSessionTokenValidator() {
        return this.sessionTokenValidator;
    }

    @Override
    public ExternalAuthenticator getExternalAuthenticator() {
        return this.externalAuthenticator;
    }

    @Override
    public OAuthAuthenticator getOAuthAuthenticator() {
        return this.oAuthAuthenticator;
    }

    @Override
    public HandbookAuthenticator getHandbookAuthenticator() {
        return this.handbookAuthenticator;
    }
}
