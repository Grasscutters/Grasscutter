package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.server.packet.send.PacketEntityFightPropUpdateNotify;

import java.util.List;

@Command(label = "setstats", usage = "setstats <hp|def|atk|em|er|crate|cdmg> <value>",
        aliases = {"stats"})
public final class SetStatsCommand implements CommandHandler {

    @Override
    public void execute(GenshinPlayer sender, List<String> args) {
        if (sender == null) {
            CommandHandler.sendMessage(null, "Run this command in-game.");
            return;
        }

        String stat = args.get(0);
        switch (stat) {
            default:
                CommandHandler.sendMessage(sender, "Usage: setstats|stats <hp|def|atk|em|er|crate|cdmg> <value>");
                return;
            case "hp":
                try {
                    int health = Integer.parseInt(args.get(1));
                    EntityAvatar entity = sender.getTeamManager().getCurrentAvatarEntity();
                    entity.setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, health);
                    entity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_CUR_HP));
                    CommandHandler.sendMessage(sender, "HP set to " + health + ".");
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, "Invalid HP value.");
                    return;
                }
                break;
            case "def":
                try {
                    int def = Integer.parseInt(args.get(1));
                    EntityAvatar entity = sender.getTeamManager().getCurrentAvatarEntity();
                    entity.setFightProperty(FightProperty.FIGHT_PROP_CUR_DEFENSE, def);
                    entity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_CUR_DEFENSE));
                    CommandHandler.sendMessage(sender, "DEF set to " + def + ".");
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, "Invalid DEF value.");
                    return;
                }
                break;
            case "atk":
                try {
                    int atk = Integer.parseInt(args.get(1));
                    EntityAvatar entity = sender.getTeamManager().getCurrentAvatarEntity();
                    entity.setFightProperty(FightProperty.FIGHT_PROP_CUR_ATTACK, atk);
                    entity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_CUR_ATTACK));
                    CommandHandler.sendMessage(sender, "ATK set to " + atk + ".");
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, "Invalid ATK value.");
                    return;
                }
                break;
            case "em":
                try {
                    int em = Integer.parseInt(args.get(1));
                    EntityAvatar entity = sender.getTeamManager().getCurrentAvatarEntity();
                    entity.setFightProperty(FightProperty.FIGHT_PROP_ELEMENT_MASTERY, em);
                    entity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_ELEMENT_MASTERY));
                    CommandHandler.sendMessage(sender, "Elemental Mastery set to " + em + ".");
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, "Invalid EM value.");
                    return;
                }
                break;
            case "er":
                try {
                    float er = Integer.parseInt(args.get(1));
                    EntityAvatar entity = sender.getTeamManager().getCurrentAvatarEntity();
                    float erecharge = er / 10000;
                    entity.setFightProperty(FightProperty.FIGHT_PROP_CHARGE_EFFICIENCY, erecharge);
                    entity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_CHARGE_EFFICIENCY));
                    float iger = erecharge * 100;
                    CommandHandler.sendMessage(sender, "Energy recharge set to " + iger + "%.");
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, "Invalid ER value.");
                    return;
                }
                break;
            case "crate":
                try {
                    float cr = Integer.parseInt(args.get(1));
                    EntityAvatar entity = sender.getTeamManager().getCurrentAvatarEntity();
                    float crate = cr / 10000;
                    entity.setFightProperty(FightProperty.FIGHT_PROP_CRITICAL, crate);
                    entity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_CRITICAL));
                    float igcrate = crate * 100;
                    CommandHandler.sendMessage(sender, "Crit Rate set to " + igcrate + "%.");
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, "Invalid Crit Rate value.");
                    return;
                }
                break;
            case "cdmg":
                try {
                    float cdmg = Integer.parseInt(args.get(1));
                    EntityAvatar entity = sender.getTeamManager().getCurrentAvatarEntity();
                    float cdamage = cdmg / 10000;
                    entity.setFightProperty(FightProperty.FIGHT_PROP_CRITICAL_HURT, cdamage);
                    entity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_CRITICAL_HURT));
                    float igcdmg = cdamage * 100;
                    CommandHandler.sendMessage(sender, "Crit DMG set to " + igcdmg + "%");
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, "Invalid Crit DMG value.");
                    return;
                }
                break;
        }
    }
}
