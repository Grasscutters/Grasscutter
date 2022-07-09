package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.packet.send.PacketServerAnnounceNotify;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "announce",
    usage = "announce|a <\"tpl\" templateId|\"refresh\"|\"revoke\" templateId|content>",
    permission = "server.announce",
    aliases = {"a"},
    description = "commands.announce.description",
    targetRequirement = Command.TargetRequirement.NONE)
public final class AnnounceCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        var manager = Grasscutter.getGameServer().getAnnouncementManager();
        if (args.size() < 1) {
            CommandHandler.sendTranslatedMessage(sender, "commands.announce.command_usage");
            return;
        }

        switch (args.get(0)){
            case "tpl":
                if (args.size() < 2) {
                    CommandHandler.sendTranslatedMessage(sender, "commands.announce.command_usage");
                    return;
                }

                var templateId = Integer.parseInt(args.get(1));
                var tpl = manager.getAnnounceConfigItemMap().get(templateId);
                if(tpl == null){
                    CommandHandler.sendMessage(sender, translate(sender, "commands.announce.not_found", templateId));
                    return;
                }

                manager.broadcast(Collections.singletonList(tpl));
                CommandHandler.sendMessage(sender, translate(sender, "commands.announce.send_success", tpl.getTemplateId()));
                break;

            case "refresh":
                var num = manager.refresh();
                CommandHandler.sendMessage(sender, translate(sender, "commands.announce.refresh_success", num));
                break;

            case "revoke":
                if (args.size() < 2) {
                    CommandHandler.sendTranslatedMessage(sender, "commands.announce.command_usage");
                    return;
                }

                var templateId1 = Integer.parseInt(args.get(1));
                manager.revoke(templateId1);
                CommandHandler.sendMessage(sender, translate(sender, "commands.announce.revoke_done", templateId1));
                break;

            default:
                var id = new Random().nextInt(10000, 99999);
                var text = String.join(" ", args);
                manager.getOnlinePlayers().forEach(i -> i.sendPacket(new PacketServerAnnounceNotify(text, id)));

                CommandHandler.sendMessage(sender, translate(sender, "commands.announce.send_success", id));
        }

    }
}
