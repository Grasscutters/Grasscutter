package emu.grasscutter.command.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.server.packet.send.PacketEntityFightPropUpdateNotify;

@Command(label = "setStats", aliases = {"stats", "stat"}, usage = {"<stat> <value>"}, permission = "player.setstats", permissionTargeted = "player.setstats.others")
public final class SetStatsCommand implements CommandHandler {
    static class Stat {
        String name;
        FightProperty prop;

        public Stat(FightProperty prop) {
            this.name = prop.toString();
            this.prop = prop;
        }

        public Stat(String name, FightProperty prop) {
            this.name = name;
            this.prop = prop;
        }
    }
    
    Map<String, Stat> stats;
    
    public SetStatsCommand() {
        this.stats = new HashMap<>();
        for (String key : FightProperty.getShortNames()) {
            this.stats.put(key, new Stat(FightProperty.getPropByShortName(key)));
        }
        // Full FightProperty enum that won't be advertised but can be used by devs
        // They have a prefix to avoid the "hp" clash
        for (FightProperty prop : FightProperty.values()) {
            String name = prop.toString().substring(10);  // FIGHT_PROP_BASE_HP -> _BASE_HP
            String key = name.toLowerCase();  // _BASE_HP -> _base_hp
            name = name.substring(1);  // _BASE_HP -> BASE_HP
            this.stats.put(key, new Stat(name, prop));
        }

        // Compatibility aliases
        this.stats.put("mhp", this.stats.get("maxhp"));
        this.stats.put("hp", new Stat(FightProperty.FIGHT_PROP_CUR_HP));  // Overrides FIGHT_PROP_HP
        this.stats.put("atk", new Stat(FightProperty.FIGHT_PROP_CUR_ATTACK));  // Overrides FIGHT_PROP_ATTACK
        this.stats.put("atkb", new Stat(FightProperty.FIGHT_PROP_BASE_ATTACK));  // This doesn't seem to get used to recalculate ATK, so it's only useful for stuff like Bennett's buff.
        this.stats.put("eanemo", this.stats.get("anemo%"));
        this.stats.put("ecryo", this.stats.get("cryo%"));
        this.stats.put("edendro", this.stats.get("dendro%"));
        this.stats.put("edend", this.stats.get("dendro%"));
        this.stats.put("eelectro", this.stats.get("electro%"));
        this.stats.put("eelec", this.stats.get("electro%"));
        this.stats.put("ethunder", this.stats.get("electro%"));
        this.stats.put("egeo", this.stats.get("geo%"));
        this.stats.put("ehydro", this.stats.get("hydro%"));
        this.stats.put("epyro", this.stats.get("pyro%"));
        this.stats.put("ephys", this.stats.get("phys%"));
    }

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        String statStr;
        String valueStr;

        if (args.size() == 2) {
            statStr = args.get(0).toLowerCase();
            valueStr = args.get(1);
        } else {
            sendUsageMessage(sender);
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
            CommandHandler.sendTranslatedMessage(sender, "commands.generic.invalid.statValue");
            return;
        }

        if (stats.containsKey(statStr)) {
            Stat stat = stats.get(statStr);
            entity.setFightProperty(stat.prop, value);
            entity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, stat.prop));
            if (FightProperty.isPercentage(stat.prop)) {
                valueStr = String.format("%.1f%%", value * 100f);
            } else {
                valueStr = String.format("%.0f", value);
            }
            if (targetPlayer == sender) {
                CommandHandler.sendTranslatedMessage(sender, "commands.generic.set_to", stat.name, valueStr);
            } else {
                String uidStr = targetPlayer.getAccount().getId();
                CommandHandler.sendTranslatedMessage(sender, "commands.generic.set_for_to", stat.name, uidStr, valueStr);
            }
        } else {
            sendUsageMessage(sender);
        }
        return;
    }
}
