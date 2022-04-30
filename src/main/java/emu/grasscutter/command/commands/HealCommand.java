package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.LanguageResourceDict;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.server.packet.send.PacketAvatarFightPropUpdateNotify;
import emu.grasscutter.server.packet.send.PacketAvatarLifeStateChangeNotify;

import java.util.List;

@Command(label = "heal", usage = "heal|h", aliases = {"h"}, description = LanguageResourceDict.HEAL_DESC, permission = "player.heal")
public final class HealCommand implements CommandHandler {
    @Override
    public void execute(Player sender, List<String> args) {
        if (sender == null) {
            CommandHandler.sendMessage(null, LanguageResourceDict.getText(LanguageResourceDict.RUN_IN_GAME));
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
        CommandHandler.sendMessage(sender, "All characters have been healed.");
    }
}
