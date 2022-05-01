package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.AvatarData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.player.Player;

import java.util.List;

@Command(label = "givechar", usage = "givechar <playerId> <avatarId> [level]",
        description = "Gives the player a specified character", aliases = {"givec"}, permission = "player.givechar")
public final class GiveCharCommand implements CommandHandler {

    @Override
    public void execute(Player sender, List<String> args) {
        int target, avatarId, level = 1, ascension;

        if (sender == null && args.size() < 2) {
            CommandHandler.sendMessage(null, "Usage: givechar <player> <itemId|itemName> [amount]");
            return;
        }

        switch (args.size()) {
            default:
                CommandHandler.sendMessage(sender, "Usage: givechar <player> <avatarId> [level]");
                return;
            case 2:
                try {
                    target = Integer.parseInt(args.get(0));
                    if (Grasscutter.getGameServer().getPlayerByUid(target) == null && sender != null) {
                        target = sender.getUid();
                        level = Integer.parseInt(args.get(1));
                        avatarId = Integer.parseInt(args.get(0));
                    } else {
                        avatarId = Integer.parseInt(args.get(1));
                    }
                } catch (NumberFormatException ignored) {
                    // TODO: Parse from avatar name using GM Handbook.
                    CommandHandler.sendMessage(sender, "Invalid avatar or player ID.");
                    return;
                }
                break;
            case 3:
                try {
                    target = Integer.parseInt(args.get(0));
                    if (Grasscutter.getGameServer().getPlayerByUid(target) == null) {
                        CommandHandler.sendMessage(sender, "Invalid player ID.");
                        return;
                    }

                    avatarId = Integer.parseInt(args.get(1));
                    level = Integer.parseInt(args.get(2));
                } catch (NumberFormatException ignored) {
                    // TODO: Parse from avatar name using GM Handbook.
                    CommandHandler.sendMessage(sender, "Invalid avatar or player ID.");
                    return;
                }
                break;
        }

        Player targetPlayer = Grasscutter.getGameServer().getPlayerByUid(target);
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, "Player not found.");
            return;
        }

        AvatarData avatarData = GameData.getAvatarDataMap().get(avatarId);
        if (avatarData == null) {
            CommandHandler.sendMessage(sender, "Invalid avatar id.");
            return;
        }

        // Check level.
        if (level > 90) {
            CommandHandler.sendMessage(sender, "Invalid avatar level.");
            return;
        }

        // Calculate ascension level.
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
        CommandHandler.sendMessage(sender, String.format("Given %s with level %s to %s.", avatarId, level, target));
    }
}
