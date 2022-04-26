package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.Mail;
import emu.grasscutter.server.packet.send.PacketMailChangeNotify;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Command(label = "sendmail", usage = "sendmail <userId|all|help> [templateId]",
        description = "Sends mail to the specified user. The usage of this command changes based on it's composition state.", permission = "server.sendmail")
public class SendMailCommand implements CommandHandler {

    // TODO: You should be able to do /sendmail and then just send subsequent messages until you finish
    //  However, due to the current nature of the command system, I don't think this is possible without rewriting
    //  the command system (again). For now this will do

    // Key = User that is constructing the mail.
    private static HashMap<Integer, MailBuilder> mailBeingConstructed = new HashMap<Integer, MailBuilder>();

    // Yes this is awful and I hate it.
    @Override
    public void execute(GenshinPlayer sender, List<String> args) {
        int senderId;
        if(sender != null) {
            senderId = sender.getUid();
        } else {
            senderId = -1;
        }

        if (!mailBeingConstructed.containsKey(senderId)) {
            switch (args.size()) {
                case 1:
                    MailBuilder mailBuilder;
                    switch (args.get(0).toLowerCase()) {
                        case "help":
                            CommandHandler.sendMessage(sender, this.getClass().getAnnotation(Command.class).description() + "\nUsage: " + this.getClass().getAnnotation(Command.class).usage());
                            return;
                        case "all":
                            mailBuilder = new MailBuilder(true, new Mail());
                            break;
                        default:
                            if (DatabaseHelper.getPlayerById(Integer.parseInt(args.get(0))) != null) {
                                mailBuilder = new MailBuilder(Integer.parseInt(args.get(0)), new Mail());
                                break;
                            } else {
                                CommandHandler.sendMessage(sender, "The user with an id of '" + args.get(0) + "' does not exist");
                                return;
                            }
                    }
                    mailBeingConstructed.put(senderId, mailBuilder);
                    CommandHandler.sendMessage(sender, "Starting composition of message.\nPlease use `/sendmail <title>` to continue.\nYou can use `/sendmail stop` at any time");
                    break;
                case 2:
                    CommandHandler.sendMessage(sender, "Mail templates coming soon implemented...");
                    return;
                default:
                    CommandHandler.sendMessage(sender, "Invalid arguments.\nUsage `/sendmail <userId|all|help> [templateId]`");
                    return;
            }
        } else {
            MailBuilder mailBuilder = mailBeingConstructed.get(senderId);

            if (args.size() >= 1) {
                switch (args.get(0).toLowerCase()) {
                    case "stop":
                        mailBeingConstructed.remove(senderId);
                        CommandHandler.sendMessage(sender, "Message sending cancelled");
                        return;
                    case "finish":
                        if (mailBuilder.constructionStage == 3) {
                            if(mailBuilder.sendToAll == false) {
                                Grasscutter.getGameServer().getPlayerByUid(mailBuilder.recipient, true).sendMail(mailBuilder.mail);
                                CommandHandler.sendMessage(sender, "Message sent to user " + mailBuilder.recipient + "!");
                            } else {
                                for (GenshinPlayer player : DatabaseHelper.getAllPlayers()) {
                                    Grasscutter.getGameServer().getPlayerByUid(player.getUid(), true).sendMail(mailBuilder.mail);
                                }
                                CommandHandler.sendMessage(sender, "Message sent to all users!");
                            }
                            mailBeingConstructed.remove(senderId);
                        } else {
                            CommandHandler.sendMessage(sender, "Message composition not at final stage.\nPlease use `/sendmail " + getConstructionArgs(mailBuilder.constructionStage) + "` or `/sendmail stop` to cancel");
                        }
                        return;
                    case "help":
                        CommandHandler.sendMessage(sender, "Please use `/sendmail " + getConstructionArgs(mailBuilder.constructionStage) + "`");
                        return;
                    default:
                        switch (mailBuilder.constructionStage) {
                            case 0:
                                String title = String.join(" ", args.subList(0, args.size()));
                                mailBuilder.mail.mailContent.title = title;
                                CommandHandler.sendMessage(sender, "Message title set as '" + title + "'.\nUse '/sendmail <content>' to continue.");
                                mailBuilder.constructionStage++;
                                break;
                            case 1:
                                String contents = String.join(" ", args.subList(0, args.size()));
                                mailBuilder.mail.mailContent.content = contents;
                                CommandHandler.sendMessage(sender, "Message contents set as '" + contents + "'.\nUse '/sendmail <sender>' to continue.");
                                mailBuilder.constructionStage++;
                                break;
                            case 2:
                                String msgSender = String.join(" ", args.subList(0, args.size()));
                                mailBuilder.mail.mailContent.sender = msgSender;
                                CommandHandler.sendMessage(sender, "Message sender set as '" + msgSender + "'.\nUse '/sendmail <itemId|itemName|finish> [amount] [level]' to continue.");
                                mailBuilder.constructionStage++;
                                break;
                            case 3:
                                // Literally just copy-pasted from the give command lol.
                                int item, lvl, amount = 1;
                                switch (args.size()) {
                                    default: // *No args*
                                        CommandHandler.sendMessage(sender, "Usage: give [player] <itemId|itemName> [amount]");
                                        return;
                                    case 1: // <itemId|itemName>
                                        try {
                                            item = Integer.parseInt(args.get(0));
                                            lvl = 1;
                                        } catch (NumberFormatException ignored) {
                                            // TODO: Parse from item name using GM Handbook.
                                            CommandHandler.sendMessage(sender, "Invalid item id.");
                                            return;
                                        }
                                        break;
                                    case 2: // <itemId|itemName> [amount]
                                        lvl = 1;
                                        item = Integer.parseInt(args.get(0));
                                        amount = Integer.parseInt(args.get(1));
                                        break;
                                    case 3: // <itemId|itemName> [amount] [level]
                                        try {
                                            item = Integer.parseInt(args.get(0));
                                            amount = Integer.parseInt(args.get(1));
                                            lvl = Integer.parseInt(args.get(2));

                                        } catch (NumberFormatException ignored) {
                                            // TODO: Parse from item name using GM Handbook.
                                            CommandHandler.sendMessage(sender, "Invalid item or player ID.");
                                            return;
                                        }
                                        break;
                                }
                                mailBuilder.mail.itemList.add(new Mail.MailItem(item, amount, lvl));
                                CommandHandler.sendMessage(sender, String.format("Attached %s of %s (level %s) to the message.\nContinue adding more items or use `/sendmail finish` to send the message.", amount, item, lvl));
                        }
                        break;
                }
            } else {
                CommandHandler.sendMessage(sender, "Invalid arguments \n Please use `/sendmail " + getConstructionArgs(mailBuilder.constructionStage));
            }
        }
    }

    public String getConstructionArgs(int stage) {
        switch (stage) {
            case 0:
                return "<title>";
            case 1:
                return "<message>";
            case 2:
                return "<sender>";
            case 3:
                return "<itemId|itemName|finish> [amount] [level]";
            default:
                Thread.dumpStack();
                return "ERROR: invalid construction stage " + stage + ". Check console for stacktrace.";
        }
    }

    public static class MailBuilder {
        public int recipient;
        public boolean sendToAll;
        public int constructionStage;
        public Mail mail;

        public MailBuilder(int recipient, Mail mail) {
            this.recipient = recipient;
            this.sendToAll = false;
            this.constructionStage = 0;
            this.mail = mail;
        }

        public MailBuilder(boolean sendToAll, Mail mail) {
            if (sendToAll) {
                this.recipient = 0;
                this.sendToAll = true;
                this.constructionStage = 0;
                this.mail = mail;
            } else {
                Grasscutter.getLogger().error("Please use MailBuilder(int, mail) when not sending to all");
                Thread.dumpStack();
            }
        }
    }
}
