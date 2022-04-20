package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.server.packet.send.PacketEntityFightPropUpdateNotify;

import java.util.List;

@Command(label = "sethealth", usage = "sethealth <hp>",
        description = "Sets your health to the specified value", aliases = {"sethp"}, permission = "player.sethealth")
public class SetHealth implements CommandHandler {

    @Override
    public void onCommand(GenshinPlayer sender, List<String> args) {
        if (sender == null) {
            CommandHandler.sendMessage(null, "Run this command in-game.");
            return; // TODO: set player's health from console or other players
        }

        if (args.size() < 1) {
            CommandHandler.sendMessage(null, "Usage: sethealth <hp>");
            return;
        }

        try {
            int health = Integer.parseInt(args.get(0));
            EntityAvatar entity = sender.getTeamManager().getCurrentAvatarEntity();
            if (entity == null) {
                return;
            }

            entity.setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, health);
            entity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_CUR_HP));
            sender.dropMessage("Health set to " + health + ".");
        } catch (NumberFormatException ignored) {
            CommandHandler.sendMessage(null, "Invalid health value.");
        }
    }
}
