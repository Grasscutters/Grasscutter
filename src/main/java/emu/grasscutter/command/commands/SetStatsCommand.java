package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.server.packet.send.PacketEntityFightPropUpdateNotify;

import java.util.List;

@Command(label = "setstats", usage = "setstats|stats <stat> <value>",
        description = "Set fight property for your current active character", aliases = {"stats"}, permission = "player.setstats")
public final class SetStatsCommand implements CommandHandler {

    @Override
    public void execute(Player sender, List<String> args) {
        if (sender == null) {
            CommandHandler.sendMessage(null, "Run this command in-game.");
            return;
        }

        if (args.size() < 2){
            CommandHandler.sendMessage(sender, "Usage: setstats|stats <stat> <value>");
            return;
        }

        String stat = args.get(0);
        switch (stat) {
            default:
                CommandHandler.sendMessage(sender, "Usage: /setstats|stats <hp | mhp | def | atk | em | er | crate | cdmg> <value> for basic stats");
                CommandHandler.sendMessage(sender, "Usage: /stats <epyro | ecryo | ehydro | egeo | edend | eelec | ephys> <amount> for elemental bonus");
                return;
            case "mhp":
                try {
                    int health = Integer.parseInt(args.get(1));
                    EntityAvatar entity = sender.getTeamManager().getCurrentAvatarEntity();
                    entity.setFightProperty(FightProperty.FIGHT_PROP_MAX_HP, health);
                    entity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_MAX_HP));
                    CommandHandler.sendMessage(sender, "MAX HP set to " + health + ".");
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, "Invalid Max HP value.");
                    return;
                }
                break;
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
            case "epyro":
                try {
                    float epyro = Integer.parseInt(args.get(1));
                    EntityAvatar entity = sender.getTeamManager().getCurrentAvatarEntity();
                    float pyro = epyro / 10000;
                    entity.setFightProperty(FightProperty.FIGHT_PROP_FIRE_ADD_HURT, pyro);
                    entity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_FIRE_ADD_HURT));
                    float igpyro = pyro * 100;
                    CommandHandler.sendMessage(sender, "Pyro DMG Bonus set to " + igpyro + "%");
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, "Invalid Pyro DMG Bonus value.");
                    return;
                }
                break;
            case "ecryo":
                try {
                    float ecryo = Integer.parseInt(args.get(1));
                    EntityAvatar entity = sender.getTeamManager().getCurrentAvatarEntity();
                    float cryo = ecryo / 10000;
                    entity.setFightProperty(FightProperty.FIGHT_PROP_ICE_ADD_HURT, cryo);
                    entity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_ICE_ADD_HURT));
                    float igcyro = cryo * 100;
                    CommandHandler.sendMessage(sender, "Cyro DMG Bonus set to " + igcyro + "%");
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, "Invalid Cryo DMG Bonus value.");
                    return;
                }
                break;
            case "ehydro":
                try {
                    float ehydro = Integer.parseInt(args.get(1));
                    EntityAvatar entity = sender.getTeamManager().getCurrentAvatarEntity();
                    float hydro = ehydro / 10000;
                    entity.setFightProperty(FightProperty.FIGHT_PROP_WATER_ADD_HURT, hydro);
                    entity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_WATER_ADD_HURT));
                    float ighydro = hydro * 100;
                    CommandHandler.sendMessage(sender, "Hydro DMG Bonus set to " + ighydro + "%");
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, "Invalid Hydro DMG Bonus value.");
                    return;
                }
                break;
            case "eanemo":
                try {
                    float eanemo = Integer.parseInt(args.get(1));
                    EntityAvatar entity = sender.getTeamManager().getCurrentAvatarEntity();
                    float anemo = eanemo / 10000;
                    entity.setFightProperty(FightProperty.FIGHT_PROP_WIND_ADD_HURT, anemo);
                    entity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_WIND_ADD_HURT));
                    float iganemo = anemo * 100;
                    CommandHandler.sendMessage(sender, "Anemo DMG Bonus set to " + iganemo + "%");
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, "Invalid Anemo DMG Bonus value.");
                    return;
                }
                break;
            case "egeo":
                try {
                    float egeo = Integer.parseInt(args.get(1));
                    EntityAvatar entity = sender.getTeamManager().getCurrentAvatarEntity();
                    float geo = egeo / 10000;
                    entity.setFightProperty(FightProperty.FIGHT_PROP_ROCK_ADD_HURT, geo);
                    entity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_ROCK_ADD_HURT));
                    float iggeo = geo * 100;
                    CommandHandler.sendMessage(sender, "Geo DMG Bonus set to " + iggeo + "%");
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, "Invalid Geo DMG Bonus value.");
                    return;
                }
                break;
            case "ethunder":
            case "eelec":
                try {
                    float eelec = Integer.parseInt(args.get(1));
                    EntityAvatar entity = sender.getTeamManager().getCurrentAvatarEntity();
                    float elec = eelec / 10000;
                    entity.setFightProperty(FightProperty.FIGHT_PROP_ELEC_ADD_HURT, elec);
                    entity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_ELEC_ADD_HURT));
                    float igelec = elec * 100;
                    CommandHandler.sendMessage(sender, "Electro DMG Bonus set to " + igelec + "%");
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, "Invalid Electro DMG Bonus value.");
                    return;
                }
                break;
            case "ephys":
                try {
                    float ephys = Integer.parseInt(args.get(1));
                    EntityAvatar entity = sender.getTeamManager().getCurrentAvatarEntity();
                    float phys = ephys / 10000;
                    entity.setFightProperty(FightProperty.FIGHT_PROP_PHYSICAL_ADD_HURT, phys);
                    entity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_PHYSICAL_ADD_HURT));
                    float igphys = phys * 100;
                    CommandHandler.sendMessage(sender, "Physical DMG Bonus set to " + igphys + "%");
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, "Invalid Physical DMG Bonus value.");
                    return;
                }
                break;
            case "edend":
                try {
                    float edend = Integer.parseInt(args.get(1));
                    EntityAvatar entity = sender.getTeamManager().getCurrentAvatarEntity();
                    float dend = edend / 10000;
                    entity.setFightProperty(FightProperty.FIGHT_PROP_GRASS_ADD_HURT, dend);
                    entity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_GRASS_ADD_HURT));
                    float igdend = dend * 100;
                    CommandHandler.sendMessage(sender, "Dendro DMG Bonus set to " + igdend + "%");
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, "Invalid Dendro DMG Bonus value.");
                    return;
                }
                break;
        }
    }
}
