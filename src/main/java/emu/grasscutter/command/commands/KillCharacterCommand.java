package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.props.LifeState;
import emu.grasscutter.server.packet.send.PacketEntityFightPropUpdateNotify;
import emu.grasscutter.server.packet.send.PacketLifeStateChangeNotify;

import java.util.List;

@Command(label = "killcharacter", usage = "killcharacter [playerId]", aliases = {"suicide"},
        description = "Directly kill a player's current active character", permission = "player.killcharacter")
public final class KillCharacterCommand implements CommandHandler {

    @Override
    public void execute(GenshinPlayer sender, List<String> args) {
        if (sender == null) {
            CommandHandler.sendMessage(null, "Run this command in-game.");
            return; // TODO: kill a player's current active character from console
        }

        int target;
        if (args.size() == 1) {
            try {
                target = Integer.parseInt(args.get(0));
                if (Grasscutter.getGameServer().getPlayerByUid(target) == null) {
                    target = sender.getUid();
                }
            } catch (NumberFormatException e) {
                CommandHandler.sendMessage(sender, "Invalid player id.");
                return;
            }
        } else {
            target = sender.getUid();
        }
        GenshinPlayer targetPlayer = Grasscutter.getGameServer().getPlayerByUid(target);
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, "Player not found or offline.");
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

        sender.dropMessage("Killed " + targetPlayer.getNickname() + "'s current active character.");
    }
}
