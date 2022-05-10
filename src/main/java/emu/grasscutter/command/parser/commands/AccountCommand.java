package emu.grasscutter.command.parser.commands;

import emu.grasscutter.command.handler.HandlerContext;
import emu.grasscutter.command.handler.collection.AccountHandlerCollection;
import emu.grasscutter.command.parser.CommandParser;
import emu.grasscutter.command.parser.annotation.*;
import emu.grasscutter.command.source.BaseCommandSource;

@Command(literal = "account")
public class AccountCommand {
    @SubCommandHandler("create")
    @AllowedOrigin(Origin.SERVER)
    @Description("commands.account.description.create")
    public void createAccount(BaseCommandSource source, String username, @OptionalArgument Integer uid) {
        CommandParser.dispatch(
                source,
                AccountHandlerCollection.ACCOUNT_CREATE,
                HandlerContext.builder()
                        .content(AccountHandlerCollection.Fields.ACCOUNT_USERNAME, username)
                        .content(AccountHandlerCollection.Fields.ACCOUNT_UID, uid)
        );
    }

    @SubCommandHandler("delete")
    @AllowedOrigin(Origin.SERVER)
    @Description("commands.account.description.delete")
    public void deleteAccount(BaseCommandSource source, String username) {
        CommandParser.dispatch(
                source,
                AccountHandlerCollection.ACCOUNT_DELETE,
                HandlerContext.builder()
                        .content(AccountHandlerCollection.Fields.ACCOUNT_USERNAME, username)
        );
    }
}
