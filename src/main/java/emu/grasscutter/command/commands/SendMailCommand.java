package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.mail.Mail;
import emu.grasscutter.game.player.Player;

import java.util.HashMap;
import java.util.List;

@Command(label = "sendmail", usage = "sendmail <userId|all|help> [templateId]",
        description = "Sends mail to the specified user. The usage of this command changes based on it's composition state.", permission = "server.sendmail")
public final class SendMailCommand implements CommandHandler {

    // TODO: You should be able to do /sendmail and then just send subsequent messages until you finish
    //  However, due to the current nature of the command system, I don't think this is possible without rewriting
    //  the command system (again). For now this will do

    // Key = User that is constructing the mail.
    private static final HashMap<Integer, MailBuilder> mailBeingConstructed = new HashMap<Integer, MailBuilder>();

    // Yes this is awful and I hate it.
    @Override
    public void execute(Player sender, List<String> args) {
        int senderId;
        if(sender != null) {
            senderId = sender.getUid();
        } else {
            senderId = -1;
        }

        if (!mailBeingConstructed.containsKey(senderId)) {
            switch (args.size()) {
                case 1 -> {
                    MailBuilder mailBuilder;
                    switch (args.get(0).toLowerCase()) {
                        case "help" -> {
                            CommandHandler.sendMessage(sender, this.getClass().getAnnotation(Command.class).description() + "\nUsage: " + this.getClass().getAnnotation(Command.class).usage());
                            return;
                        }
                        case "all" -> mailBuilder = new MailBuilder(true, new Mail());
                        default -> {
                            if (DatabaseHelper.getPlayerById(Integer.parseInt(args.get(0))) != null) {
                                mailBuilder = new MailBuilder(Integer.parseInt(args.get(0)), new Mail());
                            } else {
                                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SendMail_user_not_exist.replace("{id}", args.get(0)));
                                return;
                            }
                        }
                    }
                    mailBeingConstructed.put(senderId, mailBuilder);
                    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SendMail_start_composition);
                }
                case 2 -> CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SendMail_templates);
                default -> CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SendMail_invalid_arguments);
            }
        } else {
            MailBuilder mailBuilder = mailBeingConstructed.get(senderId);

            if (args.size() >= 1) {
                switch (args.get(0).toLowerCase()) {
                    case "stop" -> {
                        mailBeingConstructed.remove(senderId);
                        CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SendMail_send_cancel);
                        return;
                    }
                    case "finish" -> {
                        if (mailBuilder.constructionStage == 3) {
                            if (!mailBuilder.sendToAll) {
                                Grasscutter.getGameServer().getPlayerByUid(mailBuilder.recipient, true).sendMail(mailBuilder.mail);
                                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SendMail_send_done.replace("{name}", Integer.toString(mailBuilder.recipient)));
                            } else {
                                for (Player player : DatabaseHelper.getAllPlayers()) {
                                    Grasscutter.getGameServer().getPlayerByUid(player.getUid(), true).sendMail(mailBuilder.mail);
                                }
                                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SendMail_send_all_done);
                            }
                            mailBeingConstructed.remove(senderId);
                        } else {
                            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SendMail_not_composition_end.replace("{args}", getConstructionArgs(mailBuilder.constructionStage)));
                        }
                        return;
                    }
                    case "help" -> {
                        CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SendMail_please_use.replace("{args}", getConstructionArgs(mailBuilder.constructionStage)));
                        return;
                    }
                    default -> {
                        switch (mailBuilder.constructionStage) {
                            case 0 -> {
                                String title = String.join(" ", args.subList(0, args.size()));
                                mailBuilder.mail.mailContent.title = title;
                                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SendMail_set_title.replace("{title}", title));
                                mailBuilder.constructionStage++;
                            }
                            case 1 -> {
                                String contents = String.join(" ", args.subList(0, args.size()));
                                mailBuilder.mail.mailContent.content = contents;
                                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SendMail_set_contents.replace("{contents}", contents));
                                mailBuilder.constructionStage++;
                            }
                            case 2 -> {
                                String msgSender = String.join(" ", args.subList(0, args.size()));
                                mailBuilder.mail.mailContent.sender = msgSender;
                                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SendMail_set_message_sender.replace("{send}", msgSender));
                                mailBuilder.constructionStage++;
                            }
                            case 3 -> {
                                // Literally just copy-pasted from the give command lol.
                                int item, lvl, amount = 1;
                                switch (args.size()) {
                                    default -> { // *No args*
                                        CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SendMail_usage);
                                        return;
                                    }
                                    case 1 -> { // <itemId|itemName>
                                        try {
                                            item = Integer.parseInt(args.get(0));
                                            lvl = 1;
                                        } catch (NumberFormatException ignored) {
                                            // TODO: Parse from item name using GM Handbook.
                                            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Invalid_item_id);
                                            return;
                                        }
                                    }
                                    case 2 -> { // <itemId|itemName> [amount]
                                        lvl = 1;
                                        item = Integer.parseInt(args.get(0));
                                        amount = Integer.parseInt(args.get(1));
                                    }
                                    case 3 -> { // <itemId|itemName> [amount] [level]
                                        try {
                                            item = Integer.parseInt(args.get(0));
                                            amount = Integer.parseInt(args.get(1));
                                            lvl = Integer.parseInt(args.get(2));

                                        } catch (NumberFormatException ignored) {
                                            // TODO: Parse from item name using GM Handbook.
                                            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Invalid_item_or_player_id);
                                            return;
                                        }
                                    }
                                }
                                mailBuilder.mail.itemList.add(new Mail.MailItem(item, amount, lvl));
                                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SendMail_send.replace("{amount}", Integer.toString(amount)).replace("{item}", Integer.toString(item)).replace("{lvl}", Integer.toString(lvl)));
                            }
                        }
                    }
                }
            } else {
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SendMail_invalid_arguments_please_use.replace("{args}", getConstructionArgs(mailBuilder.constructionStage)));
            }
        }
    }

    private String getConstructionArgs(int stage) {
        switch (stage) {
            case 0 -> {
                return Grasscutter.getLanguage().SendMail_title;
            }
            case 1 -> {
                return Grasscutter.getLanguage().SendMail_message;
            }
            case 2 -> {
                return Grasscutter.getLanguage().SendMail_sender;

            }
            case 3 -> {
                return Grasscutter.getLanguage().SendMail_arguments;
            }
            default -> {
                Thread.dumpStack();
                return Grasscutter.getLanguage().SendMail_error.replace("{stage}", Integer.toString(stage));
            }
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
