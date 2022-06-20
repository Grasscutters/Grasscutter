package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.GameData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.packet.send.PacketAvatarFetterDataNotify;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "setfetterlevel", usage = "setfetterlevel <level>",
    aliases = {"setfetterlvl", "setfriendship"}, permission = "player.setfetterlevel", permissionTargeted = "player.setfetterlevel.others", description = "commands.setFetterLevel.description")
public final class SetFetterLevelCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size() != 1) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.setFetterLevel.usage"));
            return;
        }

        try {
            int fetterLevel = Integer.parseInt(args.get(0));
            if (fetterLevel < 0 || fetterLevel > 10) {
                CommandHandler.sendMessage(sender, translate(sender, "commands.setFetterLevel.range_error"));
                return;
            }
            Avatar avatar = targetPlayer.getTeamManager().getCurrentAvatarEntity().getAvatar();

            avatar.setFetterLevel(fetterLevel);
            if (fetterLevel != 10) {
                avatar.setFetterExp(GameData.getAvatarFetterLevelDataMap().get(fetterLevel).getExp());
            }
            avatar.save();

            targetPlayer.sendPacket(new PacketAvatarFetterDataNotify(avatar));
            CommandHandler.sendMessage(sender, translate(sender, "commands.setFetterLevel.success", fetterLevel));
        } catch (NumberFormatException ignored) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.setFetterLevel.level_error"));
        }
    }

}
