package emu.grasscutter.command.commands;

import emu.grasscutter.command.*;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.server.packet.send.PacketEntityFightPropUpdateNotify;
import java.util.*;

@Command(
        label = "setStats",
        aliases = {"stats", "stat"},
        usage = {
            "[set] <stat> <value>",
            "(lock|freeze) <stat> [<value>]", // Can lock to current value
            "(unlock|unfreeze) <stat>"
        },
        permission = "player.setstats",
        permissionTargeted = "player.setstats.others")
public final class SetStatsCommand implements CommandHandler {
    private final Map<String, Stat> stats;

    public SetStatsCommand() {
        this.stats = new HashMap<>();
        for (String key : FightProperty.getShortNames()) {
            this.stats.put(key, new Stat(FightProperty.getPropByShortName(key)));
        }
        // Full FightProperty enum that won't be advertised but can be used by devs
        // They have a prefix to avoid the "hp" clash
        for (FightProperty prop : FightProperty.values()) {
            String name = prop.toString().substring(10); // FIGHT_PROP_BASE_HP -> _BASE_HP
            String key = name.toLowerCase(); // _BASE_HP -> _base_hp
            name = name.substring(1); // _BASE_HP -> BASE_HP
            this.stats.put(key, new Stat(name, prop));
        }

        // Compatibility aliases
        this.stats.put("mhp", this.stats.get("maxhp"));
        this.stats.put("hp", this.stats.get("_cur_hp")); // Overrides FIGHT_PROP_HP
        this.stats.put("atk", this.stats.get("_cur_attack")); // Overrides FIGHT_PROP_ATTACK
        this.stats.put("def", this.stats.get("_cur_defense")); // Overrides FIGHT_PROP_DEFENSE
        this.stats.put(
                "atkb",
                this.stats.get(
                        "_base_attack")); // This doesn't seem to get used to recalculate ATK, so it's only
        // useful for stuff like Bennett's buff.
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

    public static float parsePercent(String input) throws NumberFormatException {
        if (input.endsWith("%")) {
            return Float.parseFloat(input.substring(0, input.length() - 1)) / 100f;
        } else {
            return Float.parseFloat(input);
        }
    }

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        String statStr = null;
        String valueStr;
        float value = 0f;

        if (args.size() < 2) {
            sendUsageMessage(sender);
            return;
        }

        // Get the action and stat
        String arg0 = args.remove(0).toLowerCase();
        Action action =
                switch (arg0) {
                    default -> {
                        statStr = arg0;
                        yield Action.ACTION_SET;
                    } // Implicit set command
                    case "set" -> Action.ACTION_SET; // Explicit set command
                    case "lock", "freeze" -> Action.ACTION_LOCK;
                    case "unlock", "unfreeze" -> Action.ACTION_UNLOCK;
                };
        if (statStr == null) {
            statStr = args.remove(0).toLowerCase();
        }
        if (!stats.containsKey(statStr)) {
            sendUsageMessage(sender); // Invalid stat or action
            return;
        }
        Stat stat = stats.get(statStr);
        EntityAvatar entity = targetPlayer.getTeamManager().getCurrentAvatarEntity();
        Avatar avatar = entity.getAvatar();

        // Get the value if the action requires it
        try {
            switch (action) {
                case ACTION_LOCK:
                    if (args.isEmpty()) { // Lock to current value
                        value = avatar.getFightProperty(stat.prop);
                        break;
                    } // Else fall-through and lock to supplied value
                case ACTION_SET:
                    value = parsePercent(args.remove(0));
                    break;
                case ACTION_UNLOCK:
                    break;
            }
        } catch (NumberFormatException ignored) {
            CommandHandler.sendTranslatedMessage(sender, "commands.generic.invalid.statValue");
            return;
        } catch (IndexOutOfBoundsException ignored) {
            sendUsageMessage(sender);
            return;
        }

        if (!args.isEmpty()) { // Leftover arguments!
            sendUsageMessage(sender);
            return;
        }

        switch (action) {
            case ACTION_SET:
                entity.setFightProperty(stat.prop, value);
                entity.getWorld().broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, stat.prop));
                break;
            case ACTION_LOCK:
                avatar.getFightPropOverrides().put(stat.prop.getId(), value);
                avatar.recalcStats();
                break;
            case ACTION_UNLOCK:
                avatar.getFightPropOverrides().remove(stat.prop.getId());
                avatar.recalcStats();
                break;
        }

        // Report action
        if (FightProperty.isPercentage(stat.prop)) {
            valueStr = String.format("%.1f%%", value * 100f);
        } else {
            valueStr = String.format("%.0f", value);
        }
        if (targetPlayer == sender) {
            CommandHandler.sendTranslatedMessage(sender, action.messageKeySelf, stat.name, valueStr);
        } else {
            String uidStr = targetPlayer.getAccount().getId();
            CommandHandler.sendTranslatedMessage(
                    sender, action.messageKeyOther, stat.name, uidStr, valueStr);
        }
    }

    private enum Action {
        ACTION_SET("commands.generic.set_to", "commands.generic.set_for_to"),
        ACTION_LOCK("commands.setStats.locked_to", "commands.setStats.locked_for_to"),
        ACTION_UNLOCK("commands.setStats.unlocked", "commands.setStats.unlocked_for");
        public final String messageKeySelf;
        public final String messageKeyOther;

        Action(String messageKeySelf, String messageKeyOther) {
            this.messageKeySelf = messageKeySelf;
            this.messageKeyOther = messageKeyOther;
        }
    }

    private static class Stat {
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
}
