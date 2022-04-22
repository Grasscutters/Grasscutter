package emu.grasscutter.command.commands;

import java.util.List;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.entity.EntityAvatar;

@Command(label = "setfetterlevel", usage = "setfetterlevel <level>",
        description = "Sets your fetter level for your current active character",
        aliases = {"setfetterlvl"}, permission = "player.setfetterlevel")
public final class SetFetterLevelCommand implements CommandHandler {

    @Override
    public void execute(GenshinPlayer sender, List<String> args) {
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
            EntityAvatar avatar = sender.getTeamManager().getCurrentAvatarEntity();
            avatar.getAvatar().setFetterLevel(fetterLevel);
            CommandHandler.sendMessage(sender, "Fetter level set to " + fetterLevel);
        } catch (NumberFormatException ignored) {
            CommandHandler.sendMessage(null, "Invalid fetter level.");
        }
    }
    
}
