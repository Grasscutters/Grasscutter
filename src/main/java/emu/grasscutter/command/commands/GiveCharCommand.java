package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.AvatarData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.player.Player;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "givechar", usage = "givechar <avatarId> [level]",
        description = "Gives the player a specified character", aliases = {"givec"}, permission = "player.givechar")
public final class GiveCharCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, translate("commands.execution.need_target"));
            return;
        }

        int avatarId;
        int level = 1;

        switch (args.size()) {
            case 2:
                try {
                    level = Integer.parseInt(args.get(1));
                } catch (NumberFormatException ignored) {
                    // TODO: Parse from avatar name using GM Handbook.
                    CommandHandler.sendMessage(sender, translate("commands.execution.invalid.avatarLevel"));
                    return;
                }  // Cheeky fall-through to parse first argument too
            case 1:
                try {
                    avatarId = Integer.parseInt(args.get(0));
                } catch (NumberFormatException ignored) {
                    // TODO: Parse from avatar name using GM Handbook.
                    CommandHandler.sendMessage(sender, translate("commands.execution.invalid.avatarId"));
                    return;
                }
                break;
            default:
                CommandHandler.sendMessage(sender, translate("commands.giveChar.usage"));
                return;
        }

        AvatarData avatarData = GameData.getAvatarDataMap().get(avatarId);
        if (avatarData == null) {
            CommandHandler.sendMessage(sender, translate("commands.execution.invalid.avatarId"));
            return;
        }

        // Check level.
        if (level > 90) {
            CommandHandler.sendMessage(sender, translate("commands.execution.invalid.avatarLevel"));
            return;
        }

        // Calculate ascension level.
        int ascension;
        if (level <= 40) {
            ascension = (int) Math.ceil(level / 20f);
        } else {
            ascension = (int) Math.ceil(level / 10f) - 3;
        }

        Avatar avatar = new Avatar(avatarId);
        avatar.setLevel(level);
        avatar.setPromoteLevel(ascension);

        // This will handle stats and talents
        avatar.recalcStats();

        targetPlayer.addAvatar(avatar);
        CommandHandler.sendMessage(sender, translate("commands.giveChar.given", Integer.toString(avatarId), Integer.toString(level), Integer.toString(targetPlayer.getUid())));
    }
}
