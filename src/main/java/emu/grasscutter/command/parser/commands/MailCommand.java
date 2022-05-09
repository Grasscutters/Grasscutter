package emu.grasscutter.command.parser.commands;

import emu.grasscutter.command.handler.HandlerContext;
import emu.grasscutter.command.handler.HandlerDispatcher;
import emu.grasscutter.command.handler.collection.MailHandlerCollection;
import emu.grasscutter.command.parser.CommandParser;
import emu.grasscutter.command.parser.annotation.*;
import emu.grasscutter.command.source.BaseCommandSource;
import emu.grasscutter.game.mail.Mail;


import static emu.grasscutter.command.handler.ContextNaming.TargetUid;

@Command(literal = "mail", aliases = {"m"})
public class MailCommand {

    @DefaultHandler
    @Description("commands.mail.description")
    @Permission("mail.send")
    public void sendToSpecificUser(
            BaseCommandSource source,
            Integer targetUid) {
        source.put(TargetUid, targetUid);
        mailInput(source);
    }

    @SubCommandHandler("all")
    @Permission("mail.sendAll")
    public void sendToAll(BaseCommandSource source) {
        source.put(TargetUid, -1);
        mailInput(source);
    }

    public void mailInput(BaseCommandSource source) {
        source.info("Please enter the mail title.");
        source.pushPrompt("title");
        source.registerCommandConsumer(this::readMailTitle);
    }

    public void readMailTitle(BaseCommandSource source, String title) {
        source.put("title", title);
        source.info("Enter the mail content.");
        source.info("Press enter if you want a new line.");
        source.info("type \"EOF\" to end.");
        source.popPrompt();
        source.pushPrompt("content");
        source.put("content", new StringBuilder());
        source.registerCommandConsumer(this::readMailContent);
    }

    public void readMailContent(BaseCommandSource source, String contentLine) {
        if (contentLine.equals("EOF")) {
            source.info("Enter sender name:");
            source.popPrompt();
            source.pushPrompt("sender");
            StringBuilder sb = (StringBuilder) source.get("content");
            source.put("content", sb.toString().stripTrailing());
            source.registerCommandConsumer(this::readMailSender);
        } else {
            StringBuilder sb = (StringBuilder) source.get("content");
            sb.append(contentLine);
            sb.append("\n");
            source.registerCommandConsumer(this::readMailContent);
        }
    }

    public void readMailSender(BaseCommandSource source, String sender) {
        source.put("sender", sender);
        source.info("Enter attachments. Currently what you typed is ignored.");
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
        mail.mailContent.content = (String) source.get("content");
        mail.mailContent.title = (String) source.get("title");
        mail.mailContent.sender = (String) source.get("sender");
        // todo: add attachments here
        HandlerContext.HandlerContextBuilder builder = HandlerContext.builder()
                .content("mail", mail)
                .content(TargetUid, source.get(TargetUid));
        source.popPrompt();
        CommandParser.dispatch(source, MailHandlerCollection.SEND_MAIL, builder);
    }
}
