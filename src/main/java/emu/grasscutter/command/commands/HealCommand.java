package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.server.packet.send.PacketAvatarFightPropUpdateNotify;
import emu.grasscutter.server.packet.send.PacketAvatarLifeStateChangeNotify;
import emu.grasscutter.server.packet.send.PacketEntityFightPropUpdateNotify;
import emu.grasscutter.server.packet.send.PacketLifeStateChangeNotify;

import java.util.List;

@Command(label = "heal", usage = "heal|h",
        description = "Heal all characters in your current team.", aliases = {"h"}, permission = "player.heal")
public class HealCommand implements CommandHandler {
    @Override
    public void execute(GenshinPlayer sender, List<String> args) {
        if (sender == null) {
            CommandHandler.sendMessage(null, "Run this command in-game.");
            return;
        }
        sender.getTeamManager().getActiveTeam().forEach(entity -> {
            boolean isAlive = entity.isAlive();
            entity.setFightProperty(
                    FightProperty.FIGHT_PROP_CUR_HP,
                    entity.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP)
            );
            entity.getWorld().broadcastPacket(new PacketAvatarFightPropUpdateNotify(entity.getAvatar(), FightProperty.FIGHT_PROP_CUR_HP));
            if (!isAlive) {
                entity.getWorld().broadcastPacket(new PacketAvatarLifeStateChangeNotify(entity.getAvatar()));
            }
        });
        CommandHandler.sendMessage(sender, "All characters are healed.");
    }
}
