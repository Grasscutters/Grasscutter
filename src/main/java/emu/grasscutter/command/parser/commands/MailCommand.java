package emu.grasscutter.command.parser.commands;

import emu.grasscutter.command.handler.ContextFields;
import emu.grasscutter.command.handler.HandlerContext;
import emu.grasscutter.command.handler.collection.MailHandlerCollection;
import emu.grasscutter.command.parser.CommandParser;
import emu.grasscutter.command.parser.annotation.*;
import emu.grasscutter.command.parser.argument.TargetUid;
import emu.grasscutter.command.source.BaseCommandSource;
import emu.grasscutter.game.mail.Mail;


@Command(literal = "mail", aliases = {"m"})
public class MailCommand {

    @DefaultHandler
    @Description("commands.mail.description")
    @Permission("mail.send")
    public void sendToSpecificUser(
            BaseCommandSource source,
            @OptionalArgument TargetUid targetUid) {
        if (targetUid != null) {
            source.onMessage("Mailing to specified %s.".formatted(targetUid.toString()));
            source.put(ContextFields.TARGET_UID, targetUid.getUid());
        }
        mailInput(source);
    }

    @SubCommandHandler("all")
    @Permission("mail.sendAll")
    public void sendToAll(BaseCommandSource source) {
        source.put(ContextFields.TARGET_UID, -1);
        mailInput(source);
    }

    public void mailInput(BaseCommandSource source) {
        source.onMessage("Please enter the mail title.");
        source.pushPrompt("title");
        source.registerCommandConsumer(this::readMailTitle);
    }

    public void readMailTitle(BaseCommandSource source, String title) {
        source.put("title", title);
        source.onMessage("Enter the mail content.");
        source.onMessage("Press enter if you want a new line.");
        source.onMessage("type \"EOF\" to end.");
        source.popPrompt();
        source.pushPrompt("content");
        source.put("content", new StringBuilder());
        source.registerCommandConsumer(this::readMailContent);
    }

    public void readMailContent(BaseCommandSource source, String contentLine) {
        if (contentLine.equals("EOF")) {
            source.onMessage("Enter sender name:");
            source.popPrompt();
            source.pushPrompt("sender");
            StringBuilder sb = source.getRequired("content", StringBuilder.class);
            source.put("content", sb.toString().stripTrailing());
            source.registerCommandConsumer(this::readMailSender);
        } else {
            StringBuilder sb = source.getRequired("content", StringBuilder.class);
            sb.append(contentLine);
            sb.append("\n");
            source.registerCommandConsumer(this::readMailContent);
        }
    }

    public void readMailSender(BaseCommandSource source, String sender) {
        source.put("sender", sender);
        source.onMessage("Enter attachments. Currently what you typed is ignored.");
        source.popPrompt();
        source.pushPrompt("attachments");
        source.registerCommandConsumer(this::readAttachments);
    }

    public void readAttachments(BaseCommandSource source, String attachment) {
        // todo: implement this method
        doDispatch(source);
    }

    public void doDispatch(BaseCommandSource source) {
        Mail mail = new Mail();
        mail.mailContent.content = source.getRequired("content", String.class);
        mail.mailContent.title = source.getRequired("title", String.class);
        mail.mailContent.sender = source.getRequired("sender", String.class);
        // todo: add attachments here
        source.popPrompt();

        CommandParser.dispatch(
                source,
                MailHandlerCollection.MAIL_SEND,
                HandlerContext.builder()
                        .content(MailHandlerCollection.Fields.MAIL, mail)
                        .content(ContextFields.TARGET_UID, source.getOrNull(ContextFields.TARGET_UID, int.class))
        );
    }
}
