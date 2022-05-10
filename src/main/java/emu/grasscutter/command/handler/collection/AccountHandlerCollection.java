package emu.grasscutter.command.handler.collection;

import emu.grasscutter.command.handler.HandlerContext;
import emu.grasscutter.command.handler.annotation.Handler;
import emu.grasscutter.command.handler.annotation.HandlerCollection;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.Account;
import lombok.SneakyThrows;

import static emu.grasscutter.utils.Language.translate;

@HandlerCollection
public final class AccountHandlerCollection {
    @Handler(ACCOUNT_CREATE)
    @SneakyThrows
    public void createAccount(HandlerContext context) {
        String username = context.getRequired(Fields.ACCOUNT_USERNAME, String.class);
        int uid = context.getOptional(Fields.ACCOUNT_UID, 0);
        Account account = DatabaseHelper.createAccountWithId(username, uid);

        if (account == null) {
            context.notify(translate("commands.account.exists"));
            return;
        }
        account.addPermission("*");
        account.save();
        context.notify(translate("commands.account.create", Integer.toString(account.getPlayerUid())));
    }

    @Handler(ACCOUNT_DELETE)
    @SneakyThrows
    public void deleteAccount(HandlerContext context) {
        String username = context.getRequired(Fields.ACCOUNT_USERNAME, String.class);
        Account accountToDelete = DatabaseHelper.getAccountByName(username);
        if (accountToDelete != null) {
            DatabaseHelper.deleteAccount(accountToDelete);
            context.notify(translate("commands.account.delete"));
        } else {
            context.notify(translate("commands.account.no_account"));
        }
    }

    public static final String ACCOUNT_CREATE = "account.create";
    public static final String ACCOUNT_DELETE = "account.delete";

    public static final class Fields {
        public static final String ACCOUNT_USERNAME = "account.username";
        public static final String ACCOUNT_UID = "account.uid";
    }
}
