package emu.grasscutter.server.dispatch.authentication;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.Account;
import emu.grasscutter.server.dispatch.json.LoginAccountRequestJson;
import emu.grasscutter.server.dispatch.json.LoginResultJson;
import express.http.Request;
import express.http.Response;

import static emu.grasscutter.utils.Language.translate;

public class DefaultAuthenticationHandler implements AuthenticationHandler {

    @Override
    public void handleLogin(Request req, Response res) {
        res.send("Authentication is not available with the default authentication method");
    }

    @Override
    public void handleRegister(Request req, Response res) {
        res.send("Authentication is not available with the default authentication method");
    }

    @Override
    public void handleChangePassword(Request req, Response res) {
        res.send("Authentication is not available with the default authentication method");
    }

    @Override
    public LoginResultJson handleGameLogin(Request req, LoginAccountRequestJson requestData) {
        LoginResultJson responseData = new LoginResultJson();

        // Login
        Account account = DatabaseHelper.getAccountByName(requestData.account);

        // Check if account exists, else create a new one.
        if (account == null) {
            // Account doesn't exist, so we can either auto create it if the config value is set.
            if (Grasscutter.getConfig().getDispatchOptions().AutomaticallyCreateAccounts) {
                // This account has been created AUTOMATICALLY. There will be no permissions added.
                account = DatabaseHelper.createAccountWithId(requestData.account, 0);

                for (String permission : Grasscutter.getConfig().getDispatchOptions().defaultPermissions) {
                    account.addPermission(permission);
                }

                if (account != null) {
                    responseData.message = "OK";
                    responseData.data.account.uid = account.getId();
                    responseData.data.account.token = account.generateSessionKey();
                    responseData.data.account.email = account.getEmail();

                    Grasscutter.getLogger().info(translate("messages.dispatch.account.account_login_create_success", req.ip(), responseData.data.account.uid));
                } else {
                    responseData.retcode = -201;
                    responseData.message = translate("messages.dispatch.account.username_create_error");

                    Grasscutter.getLogger().info(translate("messages.dispatch.account.account_login_create_error", req.ip()));
                }
            } else {
                responseData.retcode = -201;
                responseData.message = translate("messages.dispatch.account.username_error");

                Grasscutter.getLogger().info(translate("messages.dispatch.account.account_login_exist_error", req.ip()));
            }
        } else {
            // Account was found, log the player in
            responseData.message = "OK";
            responseData.data.account.uid = account.getId();
            responseData.data.account.token = account.generateSessionKey();
            responseData.data.account.email = account.getEmail();

            Grasscutter.getLogger().info(translate("messages.dispatch.account.login_success", req.ip(), responseData.data.account.uid));
        }

        return responseData;
    }
}
