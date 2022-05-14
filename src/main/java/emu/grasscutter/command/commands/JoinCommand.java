package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.packet.send.PacketChangeMpTeamAvatarRsp;

import java.util.ArrayList;
import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "join", usage = "join [AvatarIDs] such as\"join 10000038 10000039\"",
        description = "commands.join.description", permission = "player.join")
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
                CommandHandler.sendMessage(sender, translate("commands.generic.invalid.avatarId"));
                return;
            }
        }


        for (int i = 0; i < args.size(); i++) {
            Avatar avatar = sender.getAvatars().getAvatarById(avatarIds.get(i));
            if (avatar == null || sender.getTeamManager().getCurrentTeamInfo().contains(avatar)) {
                CommandHandler.sendMessage(sender, translate("commands.generic.invalid.avatarId"));
                return;
            }
            sender.getTeamManager().getCurrentTeamInfo().addAvatar(avatar);
        }

        // Packet
        sender.getTeamManager().updateTeamEntities(new PacketChangeMpTeamAvatarRsp(sender, sender.getTeamManager().getCurrentTeamInfo()));
    }
}
