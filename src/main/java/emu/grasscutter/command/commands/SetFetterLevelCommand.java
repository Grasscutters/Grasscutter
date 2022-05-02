package emu.grasscutter.command.commands;

import java.util.List;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.GameData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.packet.send.PacketAvatarFetterDataNotify;

@Command(label = "setfetterlevel", usage = "setfetterlevel <level>",
        description = "Sets your fetter level for your current active character",
        aliases = {"setfetterlvl", "setfriendship"}, permission = "player.setfetterlevel")
public final class SetFetterLevelCommand implements CommandHandler {

    @Override
    public void execute(Player sender, List<String> args) {
        if (sender == null) {
            CommandHandler.sendMessage(null, "Run this command in-game.");
            return;
        }

        if (args.size() < 1) {
            CommandHandler.sendMessage(sender, "Usage: setfetterlevel <level>");
            return;
        }

        try {
            int fetterLevel = Integer.parseInt(args.get(0));
            if (fetterLevel < 0 || fetterLevel > 10) {
                CommandHandler.sendMessage(sender, "Fetter level must be between 0 and 10.");
                return;
            }
            Avatar avatar = sender.getTeamManager().getCurrentAvatarEntity().getAvatar();

            avatar.setFetterLevel(fetterLevel);
            if (fetterLevel != 10) {
                avatar.setFetterExp(GameData.getAvatarFetterLevelDataMap().get(fetterLevel).getExp());
            }
		    avatar.save();
		
		    sender.sendPacket(new PacketAvatarFetterDataNotify(avatar));
            CommandHandler.sendMessage(sender, "Fetter level set to " + fetterLevel);
        } catch (NumberFormatException ignored) {
            CommandHandler.sendMessage(sender, "Invalid fetter level.");
        }
    }
    
}
