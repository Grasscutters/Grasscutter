package emu.grasscutter.command.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.server.packet.send.PacketEntityFightPropUpdateNotify;
import emu.grasscutter.utils.Language;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "setstats", usage = "setstats|stats <stat> <value>", aliases = {"stats"}, permission = "player.setstats", permissionTargeted = "player.setstats.others", description = "commands.setStats.description")
public final class SetStatsCommand implements CommandHandler {
    static class Stat {
        String name;
        FightProperty prop;
        boolean percent;

        public Stat(String name, FightProperty prop, boolean percent) {
            this.name = name;
            this.prop = prop;
            this.percent = percent;
        }
    }
    
    Map<String, Stat> stats = new HashMap<>();
    
    public SetStatsCommand() {
        // Default stats
        stats.put("maxhp", new Stat(FightProperty.FIGHT_PROP_MAX_HP.toString(), FightProperty.FIGHT_PROP_MAX_HP, false));
        stats.put("hp", new Stat(FightProperty.FIGHT_PROP_CUR_HP.toString(), FightProperty.FIGHT_PROP_CUR_HP, false));
        stats.put("atk", new Stat(FightProperty.FIGHT_PROP_CUR_ATTACK.toString(), FightProperty.FIGHT_PROP_CUR_ATTACK, false));
        stats.put("atkb", new Stat(FightProperty.FIGHT_PROP_BASE_ATTACK.toString(), FightProperty.FIGHT_PROP_BASE_ATTACK, false));  // This doesn't seem to get used to recalculate ATK, so it's only useful for stuff like Bennett's buff.
        stats.put("def", new Stat(FightProperty.FIGHT_PROP_DEFENSE.toString(), FightProperty.FIGHT_PROP_DEFENSE, false));
        stats.put("em", new Stat(FightProperty.FIGHT_PROP_ELEMENT_MASTERY.toString(), FightProperty.FIGHT_PROP_ELEMENT_MASTERY, false));
        stats.put("er", new Stat(FightProperty.FIGHT_PROP_CHARGE_EFFICIENCY.toString(), FightProperty.FIGHT_PROP_CHARGE_EFFICIENCY, true));
        stats.put("crate", new Stat(FightProperty.FIGHT_PROP_CRITICAL.toString(), FightProperty.FIGHT_PROP_CRITICAL, true));
        stats.put("cdmg", new Stat(FightProperty.FIGHT_PROP_CRITICAL_HURT.toString(), FightProperty.FIGHT_PROP_CRITICAL_HURT, true));
        stats.put("dmg", new Stat(FightProperty.FIGHT_PROP_ADD_HURT.toString(), FightProperty.FIGHT_PROP_ADD_HURT, true));  // This seems to get reset after attacks
        stats.put("eanemo", new Stat(FightProperty.FIGHT_PROP_WIND_ADD_HURT.toString(), FightProperty.FIGHT_PROP_WIND_ADD_HURT, true));
        stats.put("ecryo", new Stat(FightProperty.FIGHT_PROP_ICE_ADD_HURT.toString(), FightProperty.FIGHT_PROP_ICE_ADD_HURT, true));
        stats.put("edendro", new Stat(FightProperty.FIGHT_PROP_GRASS_ADD_HURT.toString(), FightProperty.FIGHT_PROP_GRASS_ADD_HURT, true));
        stats.put("eelectro", new Stat(FightProperty.FIGHT_PROP_ELEC_ADD_HURT.toString(), FightProperty.FIGHT_PROP_ELEC_ADD_HURT, true));
        stats.put("egeo", new Stat(FightProperty.FIGHT_PROP_ROCK_ADD_HURT.toString(), FightProperty.FIGHT_PROP_ROCK_ADD_HURT, true));
        stats.put("ehydro", new Stat(FightProperty.FIGHT_PROP_WATER_ADD_HURT.toString(), FightProperty.FIGHT_PROP_WATER_ADD_HURT, true));
        stats.put("epyro", new Stat(FightProperty.FIGHT_PROP_FIRE_ADD_HURT.toString(), FightProperty.FIGHT_PROP_FIRE_ADD_HURT, true));
        stats.put("ephys", new Stat(FightProperty.FIGHT_PROP_PHYSICAL_ADD_HURT.toString(), FightProperty.FIGHT_PROP_PHYSICAL_ADD_HURT, true));
        stats.put("resall", new Stat(FightProperty.FIGHT_PROP_SUB_HURT.toString(), FightProperty.FIGHT_PROP_SUB_HURT, true));  // This seems to get reset after attacks
        stats.put("resanemo", new Stat(FightProperty.FIGHT_PROP_WIND_SUB_HURT.toString(), FightProperty.FIGHT_PROP_WIND_SUB_HURT, true));
        stats.put("rescryo", new Stat(FightProperty.FIGHT_PROP_ICE_SUB_HURT.toString(), FightProperty.FIGHT_PROP_ICE_SUB_HURT, true));
        stats.put("resdendro", new Stat(FightProperty.FIGHT_PROP_GRASS_SUB_HURT.toString(), FightProperty.FIGHT_PROP_GRASS_SUB_HURT, true));
        stats.put("reselectro", new Stat(FightProperty.FIGHT_PROP_ELEC_SUB_HURT.toString(), FightProperty.FIGHT_PROP_ELEC_SUB_HURT, true));
        stats.put("resgeo", new Stat(FightProperty.FIGHT_PROP_ROCK_SUB_HURT.toString(), FightProperty.FIGHT_PROP_ROCK_SUB_HURT, true));
        stats.put("reshydro", new Stat(FightProperty.FIGHT_PROP_WATER_SUB_HURT.toString(), FightProperty.FIGHT_PROP_WATER_SUB_HURT, true));
        stats.put("respyro", new Stat(FightProperty.FIGHT_PROP_FIRE_SUB_HURT.toString(), FightProperty.FIGHT_PROP_FIRE_SUB_HURT, true));
        stats.put("resphys", new Stat(FightProperty.FIGHT_PROP_PHYSICAL_SUB_HURT.toString(), FightProperty.FIGHT_PROP_PHYSICAL_SUB_HURT, true));
        stats.put("cdr", new Stat(FightProperty.FIGHT_PROP_SKILL_CD_MINUS_RATIO.toString(), FightProperty.FIGHT_PROP_SKILL_CD_MINUS_RATIO, true));
        stats.put("heal", new Stat(FightProperty.FIGHT_PROP_HEAL_ADD.toString(), FightProperty.FIGHT_PROP_HEAL_ADD, true));
        stats.put("heali", new Stat(FightProperty.FIGHT_PROP_HEALED_ADD.toString(), FightProperty.FIGHT_PROP_HEALED_ADD, true));
        stats.put("shield", new Stat(FightProperty.FIGHT_PROP_SHIELD_COST_MINUS_RATIO.toString(), FightProperty.FIGHT_PROP_SHIELD_COST_MINUS_RATIO, true));
        stats.put("defi", new Stat(FightProperty.FIGHT_PROP_DEFENCE_IGNORE_RATIO.toString(), FightProperty.FIGHT_PROP_DEFENCE_IGNORE_RATIO, true));
        // Compatibility aliases
        stats.put("mhp", stats.get("maxhp"));
        stats.put("cr", stats.get("crate"));
        stats.put("cd", stats.get("cdmg"));
        stats.put("edend", stats.get("edendro"));
        stats.put("eelec", stats.get("eelectro"));
        stats.put("ethunder", stats.get("eelectro"));

        // Full FightProperty enum that won't be advertised but can be used by devs
        // They have a prefix to avoid the "hp" clash
        stats.put("_none", new Stat("NONE", FightProperty.FIGHT_PROP_NONE, true));
        stats.put("_base_hp", new Stat("BASE_HP", FightProperty.FIGHT_PROP_BASE_HP, false));
        stats.put("_hp", new Stat("HP", FightProperty.FIGHT_PROP_HP, false));
        stats.put("_hp_percent", new Stat("HP_PERCENT", FightProperty.FIGHT_PROP_HP_PERCENT, true));
        stats.put("_base_attack", new Stat("BASE_ATTACK", FightProperty.FIGHT_PROP_BASE_ATTACK, false));
        stats.put("_attack", new Stat("ATTACK", FightProperty.FIGHT_PROP_ATTACK, false));
        stats.put("_attack_percent", new Stat("ATTACK_PERCENT", FightProperty.FIGHT_PROP_ATTACK_PERCENT, true));
        stats.put("_base_defense", new Stat("BASE_DEFENSE", FightProperty.FIGHT_PROP_BASE_DEFENSE, false));
        stats.put("_defense", new Stat("DEFENSE", FightProperty.FIGHT_PROP_DEFENSE, false));
        stats.put("_defense_percent", new Stat("DEFENSE_PERCENT", FightProperty.FIGHT_PROP_DEFENSE_PERCENT, true));
        stats.put("_base_speed", new Stat("BASE_SPEED", FightProperty.FIGHT_PROP_BASE_SPEED, true));
        stats.put("_speed_percent", new Stat("SPEED_PERCENT", FightProperty.FIGHT_PROP_SPEED_PERCENT, true));
        stats.put("_hp_mp_percent", new Stat("HP_MP_PERCENT", FightProperty.FIGHT_PROP_HP_MP_PERCENT, true));
        stats.put("_attack_mp_percent", new Stat("ATTACK_MP_PERCENT", FightProperty.FIGHT_PROP_ATTACK_MP_PERCENT, true));
        stats.put("_critical", new Stat("CRITICAL", FightProperty.FIGHT_PROP_CRITICAL, true));
        stats.put("_anti_critical", new Stat("ANTI_CRITICAL", FightProperty.FIGHT_PROP_ANTI_CRITICAL, true));
        stats.put("_critical_hurt", new Stat("CRITICAL_HURT", FightProperty.FIGHT_PROP_CRITICAL_HURT, true));
        stats.put("_charge_efficiency", new Stat("CHARGE_EFFICIENCY", FightProperty.FIGHT_PROP_CHARGE_EFFICIENCY, true));
        stats.put("_add_hurt", new Stat("ADD_HURT", FightProperty.FIGHT_PROP_ADD_HURT, true));
        stats.put("_sub_hurt", new Stat("SUB_HURT", FightProperty.FIGHT_PROP_SUB_HURT, true));
        stats.put("_heal_add", new Stat("HEAL_ADD", FightProperty.FIGHT_PROP_HEAL_ADD, true));
        stats.put("_healed_add", new Stat("HEALED_ADD", FightProperty.FIGHT_PROP_HEALED_ADD, false));
        stats.put("_element_mastery", new Stat("ELEMENT_MASTERY", FightProperty.FIGHT_PROP_ELEMENT_MASTERY, true));
        stats.put("_physical_sub_hurt", new Stat("PHYSICAL_SUB_HURT", FightProperty.FIGHT_PROP_PHYSICAL_SUB_HURT, true));
        stats.put("_physical_add_hurt", new Stat("PHYSICAL_ADD_HURT", FightProperty.FIGHT_PROP_PHYSICAL_ADD_HURT, true));
        stats.put("_defence_ignore_ratio", new Stat("DEFENCE_IGNORE_RATIO", FightProperty.FIGHT_PROP_DEFENCE_IGNORE_RATIO, true));
        stats.put("_defence_ignore_delta", new Stat("DEFENCE_IGNORE_DELTA", FightProperty.FIGHT_PROP_DEFENCE_IGNORE_DELTA, true));
        stats.put("_fire_add_hurt", new Stat("FIRE_ADD_HURT", FightProperty.FIGHT_PROP_FIRE_ADD_HURT, true));
        stats.put("_elec_add_hurt", new Stat("ELEC_ADD_HURT", FightProperty.FIGHT_PROP_ELEC_ADD_HURT, true));
        stats.put("_water_add_hurt", new Stat("WATER_ADD_HURT", FightProperty.FIGHT_PROP_WATER_ADD_HURT, true));
        stats.put("_grass_add_hurt", new Stat("GRASS_ADD_HURT", FightProperty.FIGHT_PROP_GRASS_ADD_HURT, true));
        stats.put("_wind_add_hurt", new Stat("WIND_ADD_HURT", FightProperty.FIGHT_PROP_WIND_ADD_HURT, true));
        stats.put("_rock_add_hurt", new Stat("ROCK_ADD_HURT", FightProperty.FIGHT_PROP_ROCK_ADD_HURT, true));
        stats.put("_ice_add_hurt", new Stat("ICE_ADD_HURT", FightProperty.FIGHT_PROP_ICE_ADD_HURT, true));
        stats.put("_hit_head_add_hurt", new Stat("HIT_HEAD_ADD_HURT", FightProperty.FIGHT_PROP_HIT_HEAD_ADD_HURT, true));
        stats.put("_fire_sub_hurt", new Stat("FIRE_SUB_HURT", FightProperty.FIGHT_PROP_FIRE_SUB_HURT, true));
        stats.put("_elec_sub_hurt", new Stat("ELEC_SUB_HURT", FightProperty.FIGHT_PROP_ELEC_SUB_HURT, true));
        stats.put("_water_sub_hurt", new Stat("WATER_SUB_HURT", FightProperty.FIGHT_PROP_WATER_SUB_HURT, true));
        stats.put("_grass_sub_hurt", new Stat("GRASS_SUB_HURT", FightProperty.FIGHT_PROP_GRASS_SUB_HURT, true));
        stats.put("_wind_sub_hurt", new Stat("WIND_SUB_HURT", FightProperty.FIGHT_PROP_WIND_SUB_HURT, true));
        stats.put("_rock_sub_hurt", new Stat("ROCK_SUB_HURT", FightProperty.FIGHT_PROP_ROCK_SUB_HURT, true));
        stats.put("_ice_sub_hurt", new Stat("ICE_SUB_HURT", FightProperty.FIGHT_PROP_ICE_SUB_HURT, true));
        stats.put("_effect_hit", new Stat("EFFECT_HIT", FightProperty.FIGHT_PROP_EFFECT_HIT, true));
        stats.put("_effect_resist", new Stat("EFFECT_RESIST", FightProperty.FIGHT_PROP_EFFECT_RESIST, true));
        stats.put("_freeze_resist", new Stat("FREEZE_RESIST", FightProperty.FIGHT_PROP_FREEZE_RESIST, true));
        stats.put("_torpor_resist", new Stat("TORPOR_RESIST", FightProperty.FIGHT_PROP_TORPOR_RESIST, true));
        stats.put("_dizzy_resist", new Stat("DIZZY_RESIST", FightProperty.FIGHT_PROP_DIZZY_RESIST, true));
        stats.put("_freeze_shorten", new Stat("FREEZE_SHORTEN", FightProperty.FIGHT_PROP_FREEZE_SHORTEN, true));
        stats.put("_torpor_shorten", new Stat("TORPOR_SHORTEN", FightProperty.FIGHT_PROP_TORPOR_SHORTEN, true));
        stats.put("_dizzy_shorten", new Stat("DIZZY_SHORTEN", FightProperty.FIGHT_PROP_DIZZY_SHORTEN, true));
        stats.put("_max_fire_energy", new Stat("MAX_FIRE_ENERGY", FightProperty.FIGHT_PROP_MAX_FIRE_ENERGY, true));
        stats.put("_max_elec_energy", new Stat("MAX_ELEC_ENERGY", FightProperty.FIGHT_PROP_MAX_ELEC_ENERGY, true));
        stats.put("_max_water_energy", new Stat("MAX_WATER_ENERGY", FightProperty.FIGHT_PROP_MAX_WATER_ENERGY, true));
        stats.put("_max_grass_energy", new Stat("MAX_GRASS_ENERGY", FightProperty.FIGHT_PROP_MAX_GRASS_ENERGY, true));
        stats.put("_max_wind_energy", new Stat("MAX_WIND_ENERGY", FightProperty.FIGHT_PROP_MAX_WIND_ENERGY, true));
        stats.put("_max_ice_energy", new Stat("MAX_ICE_ENERGY", FightProperty.FIGHT_PROP_MAX_ICE_ENERGY, true));
        stats.put("_max_rock_energy", new Stat("MAX_ROCK_ENERGY", FightProperty.FIGHT_PROP_MAX_ROCK_ENERGY, true));
        stats.put("_skill_cd_minus_ratio", new Stat("SKILL_CD_MINUS_RATIO", FightProperty.FIGHT_PROP_SKILL_CD_MINUS_RATIO, true));
        stats.put("_shield_cost_minus_ratio", new Stat("SHIELD_COST_MINUS_RATIO", FightProperty.FIGHT_PROP_SHIELD_COST_MINUS_RATIO, true));
        stats.put("_cur_fire_energy", new Stat("CUR_FIRE_ENERGY", FightProperty.FIGHT_PROP_CUR_FIRE_ENERGY, false));
        stats.put("_cur_elec_energy", new Stat("CUR_ELEC_ENERGY", FightProperty.FIGHT_PROP_CUR_ELEC_ENERGY, false));
        stats.put("_cur_water_energy", new Stat("CUR_WATER_ENERGY", FightProperty.FIGHT_PROP_CUR_WATER_ENERGY, false));
        stats.put("_cur_grass_energy", new Stat("CUR_GRASS_ENERGY", FightProperty.FIGHT_PROP_CUR_GRASS_ENERGY, false));
        stats.put("_cur_wind_energy", new Stat("CUR_WIND_ENERGY", FightProperty.FIGHT_PROP_CUR_WIND_ENERGY, false));
        stats.put("_cur_ice_energy", new Stat("CUR_ICE_ENERGY", FightProperty.FIGHT_PROP_CUR_ICE_ENERGY, false));
        stats.put("_cur_rock_energy", new Stat("CUR_ROCK_ENERGY", FightProperty.FIGHT_PROP_CUR_ROCK_ENERGY, false));
        stats.put("_cur_hp", new Stat("CUR_HP", FightProperty.FIGHT_PROP_CUR_HP, false));
        stats.put("_max_hp", new Stat("MAX_HP", FightProperty.FIGHT_PROP_MAX_HP, false));
        stats.put("_cur_attack", new Stat("CUR_ATTACK", FightProperty.FIGHT_PROP_CUR_ATTACK, false));
        stats.put("_cur_defense", new Stat("CUR_DEFENSE", FightProperty.FIGHT_PROP_CUR_DEFENSE, false));
        stats.put("_cur_speed", new Stat("CUR_SPEED", FightProperty.FIGHT_PROP_CUR_SPEED, true));
        stats.put("_nonextra_attack", new Stat("NONEXTRA_ATTACK", FightProperty.FIGHT_PROP_NONEXTRA_ATTACK, true));
        stats.put("_nonextra_defense", new Stat("NONEXTRA_DEFENSE", FightProperty.FIGHT_PROP_NONEXTRA_DEFENSE, true));
        stats.put("_nonextra_critical", new Stat("NONEXTRA_CRITICAL", FightProperty.FIGHT_PROP_NONEXTRA_CRITICAL, true));
        stats.put("_nonextra_anti_critical", new Stat("NONEXTRA_ANTI_CRITICAL", FightProperty.FIGHT_PROP_NONEXTRA_ANTI_CRITICAL, true));
        stats.put("_nonextra_critical_hurt", new Stat("NONEXTRA_CRITICAL_HURT", FightProperty.FIGHT_PROP_NONEXTRA_CRITICAL_HURT, true));
        stats.put("_nonextra_charge_efficiency", new Stat("NONEXTRA_CHARGE_EFFICIENCY", FightProperty.FIGHT_PROP_NONEXTRA_CHARGE_EFFICIENCY, true));
        stats.put("_nonextra_element_mastery", new Stat("NONEXTRA_ELEMENT_MASTERY", FightProperty.FIGHT_PROP_NONEXTRA_ELEMENT_MASTERY, true));
        stats.put("_nonextra_physical_sub_hurt", new Stat("NONEXTRA_PHYSICAL_SUB_HURT", FightProperty.FIGHT_PROP_NONEXTRA_PHYSICAL_SUB_HURT, true));
        stats.put("_nonextra_fire_add_hurt", new Stat("NONEXTRA_FIRE_ADD_HURT", FightProperty.FIGHT_PROP_NONEXTRA_FIRE_ADD_HURT, true));
        stats.put("_nonextra_elec_add_hurt", new Stat("NONEXTRA_ELEC_ADD_HURT", FightProperty.FIGHT_PROP_NONEXTRA_ELEC_ADD_HURT, true));
        stats.put("_nonextra_water_add_hurt", new Stat("NONEXTRA_WATER_ADD_HURT", FightProperty.FIGHT_PROP_NONEXTRA_WATER_ADD_HURT, true));
        stats.put("_nonextra_grass_add_hurt", new Stat("NONEXTRA_GRASS_ADD_HURT", FightProperty.FIGHT_PROP_NONEXTRA_GRASS_ADD_HURT, true));
        stats.put("_nonextra_wind_add_hurt", new Stat("NONEXTRA_WIND_ADD_HURT", FightProperty.FIGHT_PROP_NONEXTRA_WIND_ADD_HURT, true));
        stats.put("_nonextra_rock_add_hurt", new Stat("NONEXTRA_ROCK_ADD_HURT", FightProperty.FIGHT_PROP_NONEXTRA_ROCK_ADD_HURT, true));
        stats.put("_nonextra_ice_add_hurt", new Stat("NONEXTRA_ICE_ADD_HURT", FightProperty.FIGHT_PROP_NONEXTRA_ICE_ADD_HURT, true));
        stats.put("_nonextra_fire_sub_hurt", new Stat("NONEXTRA_FIRE_SUB_HURT", FightProperty.FIGHT_PROP_NONEXTRA_FIRE_SUB_HURT, true));
        stats.put("_nonextra_elec_sub_hurt", new Stat("NONEXTRA_ELEC_SUB_HURT", FightProperty.FIGHT_PROP_NONEXTRA_ELEC_SUB_HURT, true));
        stats.put("_nonextra_water_sub_hurt", new Stat("NONEXTRA_WATER_SUB_HURT", FightProperty.FIGHT_PROP_NONEXTRA_WATER_SUB_HURT, true));
        stats.put("_nonextra_grass_sub_hurt", new Stat("NONEXTRA_GRASS_SUB_HURT", FightProperty.FIGHT_PROP_NONEXTRA_GRASS_SUB_HURT, true));
        stats.put("_nonextra_wind_sub_hurt", new Stat("NONEXTRA_WIND_SUB_HURT", FightProperty.FIGHT_PROP_NONEXTRA_WIND_SUB_HURT, true));
        stats.put("_nonextra_rock_sub_hurt", new Stat("NONEXTRA_ROCK_SUB_HURT", FightProperty.FIGHT_PROP_NONEXTRA_ROCK_SUB_HURT, true));
        stats.put("_nonextra_ice_sub_hurt", new Stat("NONEXTRA_ICE_SUB_HURT", FightProperty.FIGHT_PROP_NONEXTRA_ICE_SUB_HURT, true));
        stats.put("_nonextra_skill_cd_minus_ratio", new Stat("NONEXTRA_SKILL_CD_MINUS_RATIO", FightProperty.FIGHT_PROP_NONEXTRA_SKILL_CD_MINUS_RATIO, true));
        stats.put("_nonextra_shield_cost_minus_ratio", new Stat("NONEXTRA_SHIELD_COST_MINUS_RATIO", FightProperty.FIGHT_PROP_NONEXTRA_SHIELD_COST_MINUS_RATIO, true));
        stats.put("_nonextra_physical_add_hurt", new Stat("NONEXTRA_PHYSICAL_ADD_HURT", FightProperty.FIGHT_PROP_NONEXTRA_PHYSICAL_ADD_HURT, true));
    }

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        String syntax = sender == null ? translate(sender, "commands.setStats.usage_console") : translate(sender, "commands.setStats.usage_ingame");
        String usage = syntax + translate(sender, "commands.setStats.help_message");
        String statStr;
        String valueStr;

        if (args.size() == 2) {
            statStr = args.get(0).toLowerCase();
            valueStr = args.get(1);
        } else {
            CommandHandler.sendMessage(sender, usage);
            return;
        }

        EntityAvatar entity = targetPlayer.getTeamManager().getCurrentAvatarEntity();

        float value;
        try {
            if (valueStr.endsWith("%")) {
                value = Float.parseFloat(valueStr.substring(0, valueStr.length()-1))/100f;
            } else {
                value = Float.parseFloat(valueStr);
            }
        } catch (NumberFormatException ignored) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.setStats.value_error"));
            return;
        }

        if (stats.containsKey(statStr)) {
            Stat stat = stats.get(statStr);
            entity.setFightProperty(stat.prop, value);
            entity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, stat.prop));
            if (stat.percent) {
                valueStr = String.format("%.1f%%", value*100f);
            } else {
                valueStr = String.format("%.0f", value);
            }
            if (targetPlayer == sender) {
                CommandHandler.sendMessage(sender, translate(sender, "commands.setStats.set_self", stat.name, valueStr));
            } else {
                String uidStr = targetPlayer.getAccount().getId();
                CommandHandler.sendMessage(sender, translate(sender, "commands.setStats.set_self", stat.name, uidStr, valueStr));
            }
        } else {
            CommandHandler.sendMessage(sender, usage);
        }
        return;
    }
}
