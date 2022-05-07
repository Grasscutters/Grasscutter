package emu.grasscutter.command.parser.commands;

import emu.grasscutter.command.handler.HandlerCollectionCode;
import emu.grasscutter.command.handler.HandlerContext;
import emu.grasscutter.command.handler.HandlerDispatcher;
import emu.grasscutter.command.handler.collections.MailHandlerCollectionCollection;
import emu.grasscutter.command.parser.annotation.Command;
import emu.grasscutter.command.parser.annotation.DefaultHandler;
import emu.grasscutter.command.parser.annotation.Permission;
import emu.grasscutter.command.parser.annotation.SubCommandHandler;
import emu.grasscutter.command.source.BaseCommandSource;
import emu.grasscutter.game.mail.Mail;


import static emu.grasscutter.command.handler.ContextNaming.TargetUid;


@Command(literal = "mail", aliases = {"m", "m2"}, description = "send mail to users")
public class MailCommand {

    @DefaultHandler(description = "send mail to one user")
    @Permission("mail.send")
    public void sendToSpecificUser(
            BaseCommandSource source,
            Integer targetUid) {
        source.put("target", targetUid);
        mailInput(source);
    }

    @SubCommandHandler(literal = "all", description = "send mail to all users")
    @Permission("mail.sendAll")
    public void sendToAll(BaseCommandSource source) {
        source.put("target", -1);
        mailInput(source);
    }

    public void mailInput(BaseCommandSource source) {
        source.info("Please enter the mail title.");
        source.changePrompt("title");
        source.setRegisteredCommandConsumer(this::readMailTitle);
    }

    public void readMailTitle(BaseCommandSource source, String title) {
        source.put("title", title);
        source.info("Enter the mail content.");
        source.info("Press enter if you want a new line.");
        source.info("type \\\"EOF\\\" to end.");
        source.changePrompt("content");
        source.put("content", new StringBuilder());
        source.setRegisteredCommandConsumer(this::readMailContent);
    }

    public void readMailContent(BaseCommandSource source, String contentLine) {
        if (contentLine.equals("EOF")) {
            source.info("Enter sender name:");
            source.changePrompt("sender");
            StringBuilder sb = (StringBuilder) source.get("content");
            source.put("content", sb.toString().stripTrailing());
            source.setRegisteredCommandConsumer(this::readMailSender);
        } else {
            StringBuilder sb = (StringBuilder) source.get("content");
            sb.append(contentLine);
            sb.append("\n");
            source.setRegisteredCommandConsumer(this::readMailContent);
        }
    }

    public void readMailSender(BaseCommandSource source, String sender) {
        source.put("sender", sender);
        source.info("Enter attachments. Currently what you typed is ignored.");
        source.changePrompt("attachments");
        source.setRegisteredCommandConsumer(this::readAttachments);
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
                .content(TargetUid, source.get("target"));
        source.changePrompt(null);
        source.beforeHandlerDispatch(builder);
        HandlerDispatcher.dispatch(
                HandlerCollectionCode.Mail,
                MailHandlerCollectionCollection.Send,
                builder.build()
        );
    }
}
