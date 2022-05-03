package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
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
            CommandHandler.sendMessage(null, Grasscutter.getLanguage().Run_this_command_in_game);
            return;
        }

        if (args.size() < 2){
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SetStats_usage);
            return;
        }

        String stat = args.get(0);
        switch (stat) {
            default:
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SetStats_setstats_help_message);
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SetStats_stats_help_message);
                return;
            case "mhp":
                try {
                    int health = Integer.parseInt(args.get(1));
                    EntityAvatar entity = sender.getTeamManager().getCurrentAvatarEntity();
                    entity.setFightProperty(FightProperty.FIGHT_PROP_MAX_HP, health);
                    entity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_MAX_HP));
                    CommandHandler.sendMessage(sender, String.format(Grasscutter.getLanguage().SetStats_set_max_hp, health));
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SetStats_set_max_hp_error);
                    return;
                }
                break;
            case "hp":
                try {
                    int health = Integer.parseInt(args.get(1));
                    EntityAvatar entity = sender.getTeamManager().getCurrentAvatarEntity();
                    entity.setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, health);
                    entity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_CUR_HP));
                    CommandHandler.sendMessage(sender, String.format(Grasscutter.getLanguage().SetStats_set_hp, health));
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SetStats_set_hp_error);
                    return;
                }
                break;
            case "def":
                try {
                    int def = Integer.parseInt(args.get(1));
                    EntityAvatar entity = sender.getTeamManager().getCurrentAvatarEntity();
                    entity.setFightProperty(FightProperty.FIGHT_PROP_CUR_DEFENSE, def);
                    entity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_CUR_DEFENSE));
                    CommandHandler.sendMessage(sender, String.format(Grasscutter.getLanguage().SetStats_set_def, def));
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SetStats_set_def_error);
                    return;
                }
                break;
            case "atk":
                try {
                    int atk = Integer.parseInt(args.get(1));
                    EntityAvatar entity = sender.getTeamManager().getCurrentAvatarEntity();
                    entity.setFightProperty(FightProperty.FIGHT_PROP_CUR_ATTACK, atk);
                    entity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_CUR_ATTACK));
                    CommandHandler.sendMessage(sender, String.format(Grasscutter.getLanguage().SetStats_set_atk, atk));
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SetStats_set_atk_error);
                    return;
                }
                break;
            case "em":
                try {
                    int em = Integer.parseInt(args.get(1));
                    EntityAvatar entity = sender.getTeamManager().getCurrentAvatarEntity();
                    entity.setFightProperty(FightProperty.FIGHT_PROP_ELEMENT_MASTERY, em);
                    entity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_ELEMENT_MASTERY));
                    CommandHandler.sendMessage(sender, String.format(Grasscutter.getLanguage().SetStats_set_em, em));
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SetStats_set_em_error);
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
                    CommandHandler.sendMessage(sender, String.format(Grasscutter.getLanguage().SetStats_set_er, iger));
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SetStats_set_er_error);
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
                    CommandHandler.sendMessage(sender, String.format(Grasscutter.getLanguage().SetStats_set_cr, igcrate));
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SetStats_set_cr_error);
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
                    CommandHandler.sendMessage(sender, String.format(Grasscutter.getLanguage().SetStats_set_cd, igcdmg));
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SetStats_set_cd_error);
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
                    CommandHandler.sendMessage(sender, String.format(Grasscutter.getLanguage().SetStats_set_pdb, igpyro));
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SetStats_set_pdb_error);
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
                    CommandHandler.sendMessage(sender, String.format(Grasscutter.getLanguage().SetStats_set_cdb, igcyro));
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SetStats_set_cdb_error);
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
                    CommandHandler.sendMessage(sender, String.format(Grasscutter.getLanguage().SetStats_set_hdb, ighydro));
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SetStats_set_hdb_error);
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
                    CommandHandler.sendMessage(sender, String.format(Grasscutter.getLanguage().SetStats_set_adb, iganemo));
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SetStats_set_adb_error);
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
                    CommandHandler.sendMessage(sender, String.format(Grasscutter.getLanguage().SetStats_set_gdb, iggeo));
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SetStats_set_gdb_error);
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
                    CommandHandler.sendMessage(sender, String.format(Grasscutter.getLanguage().SetStats_set_edb, igelec));
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SetStats_set_edb_error);
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
                    CommandHandler.sendMessage(sender, String.format(Grasscutter.getLanguage().SetStats_set_physdb, igphys));
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SetStats_set_physdb_error);
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
                    CommandHandler.sendMessage(sender, String.format(Grasscutter.getLanguage().SetStats_set_ddb, igdend));
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SetStats_set_ddb_error);
                    return;
                }
                break;
        }
    }
}
