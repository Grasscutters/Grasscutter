package emu.grasscutter.command.commands;

import static emu.grasscutter.utils.lang.Language.translate;

import emu.grasscutter.command.*;
import emu.grasscutter.data.GameData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.packet.send.PacketAvatarFetterDataNotify;
import java.util.List;

@Command(
        label = "setFetterLevel",
        usage = {"<level>"},
        aliases = {"setfetterlvl", "setfriendship"},
        permission = "player.setfetterlevel",
        permissionTargeted = "player.setfetterlevel.others")
public final class SetFetterLevelCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size() != 1) {
            sendUsageMessage(sender);
            return;
        }

        try {
            int fetterLevel = Integer.parseInt(args.get(0));
            if (fetterLevel < 0 || fetterLevel > 10) {
                CommandHandler.sendMessage(
                        sender, translate(sender, "commands.setFetterLevel.range_error"));
                return;
            }
            Avatar avatar = targetPlayer.getTeamManager().getCurrentAvatarEntity().getAvatar();

            avatar.setFetterLevel(fetterLevel);
            if (fetterLevel != 10) {
                avatar.setFetterExp(GameData.getAvatarFetterLevelDataMap().get(fetterLevel).getExp());
            }
            avatar.save();

            targetPlayer.sendPacket(new PacketAvatarFetterDataNotify(avatar));
            CommandHandler.sendMessage(
                    sender, translate(sender, "commands.setFetterLevel.success", fetterLevel));
        } catch (NumberFormatException ignored) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.setFetterLevel.level_error"));
        }
    }
}
