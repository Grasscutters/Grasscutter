package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.props.LifeState;
import emu.grasscutter.server.packet.send.PacketEntityFightPropUpdateNotify;
import emu.grasscutter.server.packet.send.PacketLifeStateChangeNotify;

import java.util.List;

@Command(label = "killcharacter", usage = "killcharacter [playerId]", aliases = {"suicide", "kill"},
        description = "Kills the players current character", permission = "player.killcharacter")
public final class KillCharacterCommand implements CommandHandler {

    @Override
    public void execute(Player sender, List<String> args) {
        int target;
        if (sender == null) {
            // from console
            if (args.size() == 1) {
                try {
                    target = Integer.parseInt(args.get(0));
                } catch (NumberFormatException e) {
                    CommandHandler.sendMessage(null, Grasscutter.getLanguage().Invalid_playerId);
                    return;
                }
            } else {
                CommandHandler.sendMessage(null, Grasscutter.getLanguage().KillCharacter_usage);
                return;
            }
        } else {
            if (args.size() == 1) {
                try {
                    target = Integer.parseInt(args.get(0));
                    if (Grasscutter.getGameServer().getPlayerByUid(target) == null) {
                        target = sender.getUid();
                    }
                } catch (NumberFormatException e) {
                    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Invalid_playerId);
                    return;
                }
            } else {
                target = sender.getUid();
            }
        }

        Player targetPlayer = Grasscutter.getGameServer().getPlayerByUid(target);
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Player_not_found_or_offline);
            return;
        }

        EntityAvatar entity = targetPlayer.getTeamManager().getCurrentAvatarEntity();
        entity.setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, 0f);
        // Packets
        entity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_CUR_HP));
        entity.getWorld().broadcastPacket(new PacketLifeStateChangeNotify(0, entity, LifeState.LIFE_DEAD));
        // remove
        targetPlayer.getScene().removeEntity(entity);
        entity.onDeath(0);

        CommandHandler.sendMessage(sender, Grasscutter.getLanguage().KillCharacter_kill_current_character.replace("{name}", targetPlayer.getNickname()));
    }
}
