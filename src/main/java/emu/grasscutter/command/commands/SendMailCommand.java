package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.mail.Mail;
import emu.grasscutter.game.player.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static emu.grasscutter.utils.Language.translate;

@SuppressWarnings("ConstantConditions")
@Command(
    label = "sendMail",
    usage = {"(<userId>|all) [<templateId>]", "help"},
    permission = "server.sendmail",
    targetRequirement = Command.TargetRequirement.NONE)
public final class SendMailCommand implements CommandHandler {

    // TODO: You should be able to do /sendmail and then just send subsequent messages until you finish
    //  However, due to the current nature of the command system, I don't think this is possible without rewriting
    //  the command system (again). For now this will do

    // Key = User that is constructing the mail.
    private static final HashMap<Integer, MailBuilder> mailBeingConstructed = new HashMap<Integer, MailBuilder>();

    // Yes this is awful and I hate it.
    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        int senderId;
        if (sender != null) {
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
                            sendUsageMessage(sender);
                            return;
                        }
                        case "all" -> mailBuilder = new MailBuilder(true, new Mail());
                        default -> {
                            if (DatabaseHelper.getPlayerByUid(Integer.parseInt(args.get(0))) != null) {
                                mailBuilder = new MailBuilder(Integer.parseInt(args.get(0)), new Mail());
                            } else {
                                CommandHandler.sendMessage(sender, translate(sender, "commands.sendMail.user_not_exist", args.get(0)));
                                return;
                            }
                        }
                    }
                    mailBeingConstructed.put(senderId, mailBuilder);
                    CommandHandler.sendMessage(sender, translate(sender, "commands.sendMail.start_composition"));
                }
                case 2 -> CommandHandler.sendMessage(sender, translate(sender, "commands.sendMail.templates"));
                default -> CommandHandler.sendMessage(sender, translate(sender, "commands.sendMail.invalid_arguments"));
            }
        } else {
            MailBuilder mailBuilder = mailBeingConstructed.get(senderId);

            if (args.size() >= 1) {
                switch (args.get(0).toLowerCase()) {
                    case "stop" -> {
                        mailBeingConstructed.remove(senderId);
                        CommandHandler.sendMessage(sender, translate(sender, "commands.sendMail.send_cancel"));
                        return;
                    }
                    case "finish" -> {
                        if (mailBuilder.constructionStage == 3) {
                            if (!mailBuilder.sendToAll) {
                                Grasscutter.getGameServer().getPlayerByUid(mailBuilder.recipient, true).sendMail(mailBuilder.mail);
                                CommandHandler.sendMessage(sender, translate(sender, "commands.sendMail.send_done", mailBuilder.recipient));
                            } else {
                                DatabaseHelper.getByGameClass(Player.class).forEach(player -> {
                                    var onlinePlayer = Grasscutter.getGameServer().getPlayerByUid(player.getUid(), false);
                                    Objects.requireNonNullElse(onlinePlayer, player).sendMail(mailBuilder.mail);
                                });
                                CommandHandler.sendMessage(sender, translate(sender, "commands.sendMail.send_all_done"));
                            }
                            mailBeingConstructed.remove(senderId);
                        } else {
                            CommandHandler.sendMessage(sender, translate(sender, "commands.sendMail.not_composition_end", getConstructionArgs(mailBuilder.constructionStage, sender)));
                        }
                        return;
                    }
                    case "help" -> {
                        CommandHandler.sendMessage(sender, translate(sender, "commands.sendMail.please_use", getConstructionArgs(mailBuilder.constructionStage, sender)));
                        return;
                    }
                    default -> {
                        switch (mailBuilder.constructionStage) {
                            case 0 -> {
                                String title = String.join(" ", args.subList(0, args.size()));
                                mailBuilder.mail.mailContent.title = title;
                                CommandHandler.sendMessage(sender, translate(sender, "commands.sendMail.set_title", title));
                                mailBuilder.constructionStage++;
                            }
                            case 1 -> {
                                String contents = String.join(" ", args.subList(0, args.size()));
                                mailBuilder.mail.mailContent.content = contents;
                                CommandHandler.sendMessage(sender, translate(sender, "commands.sendMail.set_contents", contents));
                                mailBuilder.constructionStage++;
                            }
                            case 2 -> {
                                String msgSender = String.join(" ", args.subList(0, args.size()));
                                mailBuilder.mail.mailContent.sender = msgSender;
                                CommandHandler.sendMessage(sender, translate(sender, "commands.sendMail.set_message_sender", msgSender));
                                mailBuilder.constructionStage++;
                            }
                            case 3 -> {
                                int item;
                                int lvl = 1;
                                int amount = 1;
                                int refinement = 0;
                                switch (args.size()) {
                                    case 4: // <itemId|itemName> [amount] [level] [refinement] // TODO: this requires Mail support but there's no harm leaving it here for now
                                        try {
                                            refinement = Integer.parseInt(args.get(3));
                                        } catch (NumberFormatException ignored) {
                                            CommandHandler.sendMessage(sender, translate(sender, "commands.generic.invalid.itemRefinement"));
                                            return;
                                        }  // Fallthrough
                                    case 3: // <itemId|itemName> [amount] [level]
                                        try {
                                            lvl = Integer.parseInt(args.get(2));
                                        } catch (NumberFormatException ignored) {
                                            CommandHandler.sendMessage(sender, translate(sender, "commands.generic.invalid.itemLevel"));
                                            return;
                                        }  // Fallthrough
                                    case 2: // <itemId|itemName> [amount]
                                        try {
                                            amount = Integer.parseInt(args.get(1));
                                        } catch (NumberFormatException ignored) {
                                            CommandHandler.sendMessage(sender, translate(sender, "commands.generic.invalid.amount"));
                                            return;
                                        }  // Fallthrough
                                    case 1: // <itemId|itemName>
                                        try {
                                            item = Integer.parseInt(args.get(0));
                                        } catch (NumberFormatException ignored) {
                                            // TODO: Parse from item name using GM Handbook.
                                            CommandHandler.sendMessage(sender, translate(sender, "commands.generic.invalid.itemId"));
                                            return;
                                        }
                                        break;
                                    default: // *No args*
                                        CommandHandler.sendTranslatedMessage(sender, "commands.sendMail.give_usage");
                                        return;
                                }
                                mailBuilder.mail.itemList.add(new Mail.MailItem(item, amount, lvl));
                                CommandHandler.sendMessage(sender, translate(sender, "commands.sendMail.send", amount, item, lvl));
                            }
                        }
                    }
                }
            } else {
                CommandHandler.sendMessage(sender, translate(sender, "commands.sendMail.invalid_arguments_please_use", getConstructionArgs(mailBuilder.constructionStage, sender)));
            }
        }
    }

    private String getConstructionArgs(int stage, Player sender) {
        return switch (stage) {
            case 0 -> translate(sender, "commands.sendMail.title");
            case 1 -> translate(sender, "commands.sendMail.message");
            case 2 -> translate(sender, "commands.sendMail.sender");
            case 3 -> translate(sender, "commands.sendMail.arguments");
            default -> translate(sender, "commands.sendMail.error", stage);
        };
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
