package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.packet.send.PacketChangeMpTeamAvatarRsp;

import java.util.ArrayList;
import java.util.List;

@Command(label = "remove", usage = "remove <index> <index> <index>...",
        description = "force remove avatar into your team", permission = "player.remove")
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
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Invalid_avatar_id);
                return;
            }
        }

        for (int i = 0; i < avatarIds.size(); i++) {
            if (avatarIds.get(i) > sender.getTeamManager().getCurrentTeamInfo().getAvatars().size() || avatarIds.get(i) <= 0) {
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Invalid_avatar_index);
                return;
            }
            sender.getTeamManager().getCurrentTeamInfo().removeAvatar(avatarIds.get(i) - 1);
        }

        // Packet
        sender.getTeamManager().updateTeamEntities(new PacketChangeMpTeamAvatarRsp(sender, sender.getTeamManager().getCurrentTeamInfo()));
    }
}
