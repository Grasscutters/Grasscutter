package emu.grasscutter.command.commands;

import static emu.grasscutter.utils.lang.Language.translate;

import emu.grasscutter.command.*;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.*;
import emu.grasscutter.server.packet.send.*;
import java.util.List;

@Command(
        label = "killCharacter",
        aliases = {"suicide", "kill"},
        permission = "player.killcharacter",
        permissionTargeted = "player.killcharacter.others")
public final class KillCharacterCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        EntityAvatar entity = targetPlayer.getTeamManager().getCurrentAvatarEntity();
        entity.setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, 0f);
        // Packets
        entity
                .getWorld()
                .broadcastPacket(
                        new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_CUR_HP));
        entity
                .getWorld()
                .broadcastPacket(new PacketLifeStateChangeNotify(0, entity, LifeState.LIFE_DEAD));
        // remove
        targetPlayer.getScene().removeEntity(entity);
        entity.onDeath(0);

        CommandHandler.sendMessage(
                sender, translate(sender, "commands.killCharacter.success", targetPlayer.getNickname()));
    }
}
