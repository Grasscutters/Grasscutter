package emu.grasscutter.server.dispatch.authentication;

import emu.grasscutter.server.dispatch.json.LoginAccountRequestJson;
import emu.grasscutter.server.dispatch.json.LoginResultJson;
import express.http.Request;
import express.http.Response;

public interface AuthenticationHandler {

    // This is in case plugins also want some sort of authentication
    void handleLogin(Request req, Response res);
    void handleRegister(Request req, Response res);
    void handleChangePassword(Request req, Response res);

    /**
     * Other plugins may need to verify a user's identity using details from handleLogin()
     * @param details The user's unique one-time token that needs to be verified
     * @return If the verification was successful
     */
    boolean verifyUser(String details);

    LoginResultJson handleGameLogin(Request req, LoginAccountRequestJson requestData);
}
