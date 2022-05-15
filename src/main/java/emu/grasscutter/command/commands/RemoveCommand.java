package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.packet.send.PacketChangeMpTeamAvatarRsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "remove", usage = "remove [indexOfYourTeams] index start from 1",
        description = "commands.remove.description", permission = "player.remove")
public class RemoveCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        List<Integer> avatarIndexList = new ArrayList<>();
        for (String arg : args) {
            try {
                int avatarIndex = Integer.parseInt(arg);
                avatarIndexList.add(avatarIndex);
            } catch (Exception ignored) {
                ignored.printStackTrace();
                CommandHandler.sendMessage(sender, translate("commands.remove.invalid_index"));
                return;
            }
        }

        Collections.reverse(avatarIndexList);

        for (int i = 0; i < avatarIndexList.size(); i++) {
            if (avatarIndexList.get(i) > sender.getTeamManager().getCurrentTeamInfo().getAvatars().size() || avatarIndexList.get(i) <= 0) {
                CommandHandler.sendMessage(sender, translate("commands.remove.invalid_index"));
                return;
            }
            sender.getTeamManager().getCurrentTeamInfo().removeAvatar(avatarIndexList.get(i) - 1);
        }

        // Packet
        sender.getTeamManager().updateTeamEntities(new PacketChangeMpTeamAvatarRsp(sender, sender.getTeamManager().getCurrentTeamInfo()));
    }
}
