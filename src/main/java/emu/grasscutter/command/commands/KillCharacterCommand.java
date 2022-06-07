package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.props.LifeState;
import emu.grasscutter.server.packet.send.PacketEntityFightPropUpdateNotify;
import emu.grasscutter.server.packet.send.PacketLifeStateChangeNotify;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "killcharacter", usage = "killcharacter", aliases = {"suicide", "kill"}, permission = "player.killcharacter", permissionTargeted = "player.killcharacter.others", description = "commands.killCharacter.description")
public final class KillCharacterCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.isEmpty()) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.killCharacter.usage"));
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

        CommandHandler.sendMessage(sender, translate(sender, "commands.killCharacter.success", targetPlayer.getNickname()));
    }
}
