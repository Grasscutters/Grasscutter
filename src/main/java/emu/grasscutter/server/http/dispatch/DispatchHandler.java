package emu.grasscutter.server.http.dispatch;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.auth.AuthenticationSystem;
import emu.grasscutter.auth.OAuthAuthenticator;
import emu.grasscutter.auth.OAuthAuthenticator.ClientType;
import emu.grasscutter.server.http.Router;
import emu.grasscutter.server.http.objects.*;
import emu.grasscutter.server.http.objects.ComboTokenReqJson.LoginTokenData;
import emu.grasscutter.utils.JsonUtils;
import express.Express;
import express.http.Request;
import express.http.Response;
import io.javalin.Javalin;

import static emu.grasscutter.utils.Language.translate;

/**
 * Handles requests related to authentication. (aka dispatch)
 */
public final class DispatchHandler implements Router {
    @Override public void applyRoutes(Express express, Javalin handle) {
        // Username & Password login (from client).
        express.post("/hk4e_global/mdk/shield/api/login", DispatchHandler::clientLogin);
        // Cached token login (from registry).
        express.post("/hk4e_global/mdk/shield/api/verify", DispatchHandler::tokenLogin);
        // Combo token login (from session key).
        express.post("/hk4e_global/combo/granter/login/v2/login", DispatchHandler::sessionKeyLogin);
        
        // External login (from other clients).
        express.get("/authentication/type", (request, response) -> response.send(Grasscutter.getAuthenticationSystem().getClass().getSimpleName()));
        express.post("/authentication/login", (request, response) -> Grasscutter.getAuthenticationSystem().getExternalAuthenticator()
                .handleLogin(AuthenticationSystem.fromExternalRequest(request, response)));
        express.post("/authentication/register", (request, response) -> Grasscutter.getAuthenticationSystem().getExternalAuthenticator()
                .handleAccountCreation(AuthenticationSystem.fromExternalRequest(request, response)));
        express.post("/authentication/change_password", (request, response) -> Grasscutter.getAuthenticationSystem().getExternalAuthenticator()
                .handlePasswordReset(AuthenticationSystem.fromExternalRequest(request, response)));

        // External login (from OAuth2).
        express.post("/hk4e_global/mdk/shield/api/loginByThirdparty", (request, response) -> Grasscutter.getAuthenticationSystem().getOAuthAuthenticator()
                .handleLogin(AuthenticationSystem.fromExternalRequest(request, response)));
        express.get("/authentication/openid/redirect", (request, response) -> Grasscutter.getAuthenticationSystem().getOAuthAuthenticator()
                .handleTokenProcess(AuthenticationSystem.fromExternalRequest(request, response)));
        express.get("/Api/twitter_login", (request, response) -> Grasscutter.getAuthenticationSystem().getOAuthAuthenticator()
                .handleRedirection(AuthenticationSystem.fromExternalRequest(request, response), ClientType.DESKTOP));
        express.get("/sdkTwitterLogin.html", (request, response) -> Grasscutter.getAuthenticationSystem().getOAuthAuthenticator()
                .handleRedirection(AuthenticationSystem.fromExternalRequest(request, response), ClientType.MOBILE));
    }

    /**
     * @route /hk4e_global/mdk/shield/api/login
     */
    private static void clientLogin(Request request, Response response) {
        // Parse body data.
        String rawBodyData = request.ctx().body();
        var bodyData = JsonUtils.decode(rawBodyData, LoginAccountRequestJson.class);
        
        // Validate body data.
        if(bodyData == null)
            return;
        
        // Pass data to authentication handler.
        var responseData = Grasscutter.getAuthenticationSystem()
                .getPasswordAuthenticator()
                .authenticate(AuthenticationSystem.fromPasswordRequest(request, bodyData));
        // Send response.
        response.send(responseData);
        
        // Log to console.
        Grasscutter.getLogger().info(translate("messages.dispatch.account.login_attempt", request.ip()));
    }

    /**
     * @route /hk4e_global/mdk/shield/api/verify
     */
    private static void tokenLogin(Request request, Response response) {
        // Parse body data.
        String rawBodyData = request.ctx().body();
        var bodyData = JsonUtils.decode(rawBodyData, LoginTokenRequestJson.class);

        // Validate body data.
        if(bodyData == null)
            return;

        // Pass data to authentication handler.
        var responseData = Grasscutter.getAuthenticationSystem()
                .getTokenAuthenticator()
                .authenticate(AuthenticationSystem.fromTokenRequest(request, bodyData));
        // Send response.
        response.send(responseData);

        // Log to console.
        Grasscutter.getLogger().info(translate("messages.dispatch.account.login_attempt", request.ip()));
    }

    /**
     * @route /hk4e_global/combo/granter/login/v2/login
     */
    private static void sessionKeyLogin(Request request, Response response) {
        // Parse body data.
        String rawBodyData = request.ctx().body();
        var bodyData = JsonUtils.decode(rawBodyData, ComboTokenReqJson.class);

        // Validate body data.
        if(bodyData == null || bodyData.data == null)
            return;
        
        // Decode additional body data.
        var tokenData = JsonUtils.decode(bodyData.data, LoginTokenData.class);

        // Pass data to authentication handler.
        var responseData = Grasscutter.getAuthenticationSystem()
                .getSessionKeyAuthenticator()
                .authenticate(AuthenticationSystem.fromComboTokenRequest(request, bodyData, tokenData));
        // Send response.
        response.send(responseData);

        // Log to console.
        Grasscutter.getLogger().info(translate("messages.dispatch.account.login_attempt", request.ip()));
    }
}
