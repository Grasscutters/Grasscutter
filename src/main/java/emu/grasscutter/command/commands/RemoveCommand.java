package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.packet.send.PacketChangeMpTeamAvatarRsp;

import java.util.ArrayList;
import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "remove", usage = "remove [indexOfYourTeams] index start from 1",
        description = "commands.remove.description", permission = "player.remove")
public class RemoveCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        List<Integer> avatarIds = new ArrayList<>();
        for (String arg : args) {
            try {
                int avatarId = Integer.parseInt(arg);
                avatarIds.add(avatarId);
            } catch (Exception ignored) {
                ignored.printStackTrace();
                CommandHandler.sendMessage(sender, translate("commands.remove.invalid_index"));
                return;
            }
        }

        for (int i = 0; i < avatarIds.size(); i++) {
            if (avatarIds.get(i) > sender.getTeamManager().getCurrentTeamInfo().getAvatars().size() || avatarIds.get(i) <= 0) {
                CommandHandler.sendMessage(sender, translate("commands.remove.invalid_index"));
                return;
            }
            sender.getTeamManager().getCurrentTeamInfo().removeAvatar(avatarIds.get(i) - 1);
        }

        // Packet
        sender.getTeamManager().updateTeamEntities(new PacketChangeMpTeamAvatarRsp(sender, sender.getTeamManager().getCurrentTeamInfo()));
    }
}
