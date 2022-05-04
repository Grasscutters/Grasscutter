package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
import java.util.List;

@Command(label = "fixchar", usage = "fixchar", description = "This fixes the stuck account can't switch characters because it uses character testing.")
public final class FixChar implements CommandHandler {

  @Override
  public void execute(Player sender, List<String> args) {
    if (sender == null) {
      CommandHandler.sendMessage(null, Grasscutter.getLanguage().Give_usage);
      return;
    }

    int idchar = 10000007;

    // Remove All Avatar in Team (NOT DELETE)
    for (int i = 0; i < sender.getTeamManager().getCurrentSinglePlayerTeamInfo().getAvatars().size(); i++) {
      int avatarId = sender.getTeamManager().getCurrentSinglePlayerTeamInfo().getAvatars().get(i);
      sender.getTeamManager().getCurrentSinglePlayerTeamInfo().getAvatars().remove(i);
      CommandHandler.sendMessage(sender, "Remove " + avatarId + " ");
    }
    sender.getTeamManager().saveAvatars();

    // Add Travele Only
    CommandHandler.sendMessage(sender, "Add Travele to Team");
    sender.getTeamManager().getCurrentSinglePlayerTeamInfo().getAvatars().add(idchar);
    sender.getTeamManager().saveAvatars();

    // Set Main Travele
    CommandHandler.sendMessage(sender, "Set Main Travele");
    sender.setMainCharacterId(idchar);
    sender.getAccount().save();

    // Restart Account
    CommandHandler.sendMessage(sender, "Restart...");
    sender.getSession().close();
  }
}
