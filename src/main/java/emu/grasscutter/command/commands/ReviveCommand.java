package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.TeamManager;
//import emu.grasscutter.game.avatar.GenshinAvatar;
//import emu.grasscutter.game.TeamInfo;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.server.packet.send.PacketAvatarFightPropUpdateNotify;
import emu.grasscutter.server.packet.send.PacketAvatarLifeStateChangeNotify;

import java.util.List;

@Command(label = "revive", aliases = {"rev"},
            usage = "revive|rev", description = "Revive character(s) that died)")
public class ReviveCommand implements CommandHandler {       
    //private Object teamId;

    @Override
    public void execute(GenshinPlayer player, List<String> args) {       
        for (EntityAvatar entity2 : player.getTeamManager().getActiveTeam()) {
			entity2.setFightProperty(
					FightProperty.FIGHT_PROP_CUR_HP, 
					entity2.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP) * .4f
			);
			player.sendPacket(new PacketAvatarFightPropUpdateNotify(entity2.getAvatar(), FightProperty.FIGHT_PROP_CUR_HP));
			player.sendPacket(new PacketAvatarLifeStateChangeNotify(entity2.getAvatar()));
		}
        player.dropMessage("Character revived.");
    }
}
