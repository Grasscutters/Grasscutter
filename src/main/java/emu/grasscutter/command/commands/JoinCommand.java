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
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.execution.need_target"));
            return;
        }

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
            Avatar avatar = targetPlayer.getAvatars().getAvatarById(avatarIds.get(i));
            if (avatar == null) {
                CommandHandler.sendMessage(sender, translate("commands.generic.invalid.avatarId"));
                return;
            }
            if (targetPlayer.getTeamManager().getCurrentTeamInfo().contains(avatar)){
                continue;
            }
            targetPlayer.getTeamManager().getCurrentTeamInfo().addAvatar(avatar);
        }

        // Packet
        targetPlayer.getTeamManager().updateTeamEntities(new PacketChangeMpTeamAvatarRsp(targetPlayer, targetPlayer.getTeamManager().getCurrentTeamInfo()));
    }
}
