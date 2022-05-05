package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.packet.send.PacketChangeMpTeamAvatarRsp;

import java.util.ArrayList;
import java.util.List;

@Command(label = "join", usage = "join <avatar id> <avatar id> <avatar id>...",
        description = "force join avatar into your team", permission = "player.join")
public class JoinCommand implements CommandHandler {

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


        for (int i = 0; i < args.size(); i++) {
            Avatar avatar = sender.getAvatars().getAvatarById(avatarIds.get(i));
            if (avatar == null || sender.getTeamManager().getCurrentTeamInfo().contains(avatar)) {
                // Should never happen
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Invalid_avatar_id);
                return;
            }
            sender.getTeamManager().getCurrentTeamInfo().addAvatar(avatar);
        }

        // Packet
        sender.getTeamManager().updateTeamEntities(new PacketChangeMpTeamAvatarRsp(sender, sender.getTeamManager().getCurrentTeamInfo()));
    }
}
