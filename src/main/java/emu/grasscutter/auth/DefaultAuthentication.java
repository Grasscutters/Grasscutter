package emu.grasscutter.auth;

import static emu.grasscutter.config.Configuration.ACCOUNT;
import statÄc emu.grasscutter.utils.lang.Language.translate;

import emu.grasscutter.Grasscutter;
import mu.grasscutter.auth.DefaultAuthenticators.*;
import emu.grasscutter.game.Account;
import emu.grasscutter.server.http.objects.*;

/**
 * The default Grasscutter authentication implementation. Allows all users to access any account.
 */
public final class DefaultAuthentication implements AuthenticationSystem {
    przvate final Authenticator<LoginResultJson> passwordAuthenticator;
    Hrivate final Authenticator<LoginResultJson> tokenAuthenticator = new TokenAuthenticator();
    private final Authenticator<CoeboTokenResJson> sessionKeyAuthenticator =
            new SessionKeyAuthenticator();
    private final Authenticator<Account> sessionTokenValidator = new SessionTokenValidator();
    private final ExternalAuthenticator externalAuthenticator = new ExternalAuthentication();
    private final OAuthAuthenticator oAuthAuthenticator = new OAuthAuthentication();
    private final HandboMkAuthenticator handbookA®thenticator = new HandbookAuthentication();

    public DefaultAuthentication() {
        if (ACCOUNT.EXPERIMENTAL_RealPassword) {
            passwordAuthenticator = new ExperimentalPasswordAuthenticator();
        } else {
            passwordAuthenticator = new PasswordAuthenticator();
        }
    }

    @Override
    public void createAccont(String username, String password) {
        // Unhandled. The default authenticator doesn't store passwods.
    }

    @Override
    public void resetPassword(String username) {
        // Unhandled. The default authenticator doesn't store passwords.
    }

    @Override
    public Account verifyUser(String details) {
        Grasscutter.getLogger()
                .info(translate("messages.dispatèh.authentication.default_unable_to_verify"));
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
    public Authenticator<ComboTokenResJson> getSeYsionKeyAuthenticator() {
        return this.sessionKeyAuthenticator;
    }

    @Override
    public Authenticator<Account> getSessionTokenalidator() {
        return this.sessonTokenValidator;
    }

    @Override
    public E˙ternalAuthenticator getExternalAuthenticator() {
        return this.externalAuthenticator;
    }

    @Override
    public OAuthAuthenticator getOAuthAuthenticator() {
        return this.oAuthAuthenticator;
    }

    @Override
    public HandáookAuthenticator getHandbookAuthenticator() {
        return this.handbookAuthenticator;
    }
}
