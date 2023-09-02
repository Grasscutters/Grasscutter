package emu.grasscutter.command.commands;

import static emu.grasscutter.utils.lang.Language.translate;

import emu.grasscutter.command.*;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.server.packet.send.*;
import java.util.List;

@Command(
        label = "heal",
        aliases = {"h"},
        permission = "player.heal",
        permissionTargeted = "player.heal.others")
public final class HealCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        targetPlayer
                .getTeamManager()
                .getActiveTeam()
                .forEach(
                        entity -> {
                            boolean isAlive = entity.isAlive();
                            entity.setFightProperty(
                                    FightProperty.FIGHT_PROP_CUR_HP,
                                    entity.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP));
                            entity
                                    .getWorld()
                                    .broadcastPacket(
                                            new PacketAvatarFightPropUpdateNotify(
                                                    entity.getAvatar(), FightProperty.FIGHT_PROP_CUR_HP));
                            if (!isAlive) {
                                entity
                                        .getWorld()
                                        .broadcastPacket(new PacketAvatarLifeStateChangeNotify(entity.getAvatar()));
                            }
                        });
        CommandHandler.sendMessage(sender, translate(sender, "commands.heal.success"));
    }
}
