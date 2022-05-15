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

@Command(label = "team", usage = "team [add/remove] [AvatarID]",
        description = "commands.join.description", permission = "player.join"+"player.remove")
public class TeamCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        int branch = 0;
        switch(args.get(0)){
            case "add":
                branch=1;
            break;
            case "remove":
                branch=2;
            break;
            default:
                return;
        }
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.execution.need_target"));
            return;
        }
        int avatarId=0;
        try{
            avatarId = Integer.parseInt(args.get(1));
        }catch (Exception ignored) {
            ignored.printStackTrace();
            if(branch==1){
                CommandHandler.sendMessage(sender, translate("commands.generic.invalid.avatarId"));
            }else if(branch==2){
                CommandHandler.sendMessage(sender, translate("commands.remove.invalid_index"));
            }
            
            return;
        }

        switch(branch){
            case 1:
                Avatar avatar = targetPlayer.getAvatars().getAvatarById(avatarId);
                if (avatar == null) {
                    CommandHandler.sendMessage(sender, translate("commands.generic.invalid.avatarId"));
                    return;
                }
                if (targetPlayer.getTeamManager().getCurrentTeamInfo().contains(avatar)){
                }
                targetPlayer.getTeamManager().getCurrentTeamInfo().addAvatar(avatar);
            break;
            case 2:
                if (avatarId > targetPlayer.getTeamManager().getCurrentTeamInfo().getAvatars().size() || avatarId <= 0) {
                    CommandHandler.sendMessage(targetPlayer, translate("commands.remove.invalid_index"));
                    return;
                }
                targetPlayer.getTeamManager().getCurrentTeamInfo().removeAvatar(avatarId - 1);
            break;
        }

        // Packet
        targetPlayer.getTeamManager().updateTeamEntities(new PacketChangeMpTeamAvatarRsp(targetPlayer, targetPlayer.getTeamManager().getCurrentTeamInfo()));
    }
}
