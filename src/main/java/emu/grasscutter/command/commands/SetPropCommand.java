package emu.grasscutter.command.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.game.tower.TowerLevelRecord;

@Command(label = "setprop", usage = "setprop|prop <prop> <value>", aliases = {"prop"}, permission = "player.setprop", permissionTargeted = "player.setprop.others", description = "commands.setProp.description")
public final class SetPropCommand implements CommandHandler {
    static enum PseudoProp {
        NONE,
        WORLD_LEVEL,
        TOWER_LEVEL,
        BP_LEVEL,
        GOD_MODE,
        NO_STAMINA,
        UNLIMITED_ENERGY
    }

    static class Prop {
        String name;
        PlayerProperty prop;
        PseudoProp pseudoProp;

        public Prop(PlayerProperty prop) {
            this(prop.toString(), prop, PseudoProp.NONE);
        }

        public Prop(String name) {
            this(name, PlayerProperty.PROP_NONE, PseudoProp.NONE);
        }

        public Prop(String name, PseudoProp pseudoProp) {
            this(name, PlayerProperty.PROP_NONE, pseudoProp);
        }

        public Prop(String name, PlayerProperty prop) {
            this(name, prop, PseudoProp.NONE);
        }

        public Prop(String name, PlayerProperty prop, PseudoProp pseudoProp) {
            this.name = name;
            this.prop = prop;
            this.pseudoProp = pseudoProp;
        }
    }
    
    Map<String, Prop> props;
    
    public SetPropCommand() {
        this.props = new HashMap<>();
        // Full PlayerProperty enum that won't be advertised but can be used by devs
        for (PlayerProperty prop : PlayerProperty.values()) {
            String name = prop.toString().substring(5);  // PROP_EXP -> EXP
            String key = name.toLowerCase();  // EXP -> exp
            this.props.put(key, new Prop(name, prop));
        }
        // Add special props
        Prop worldlevel = new Prop("World Level", PlayerProperty.PROP_PLAYER_WORLD_LEVEL, PseudoProp.WORLD_LEVEL);
        this.props.put("worldlevel", worldlevel);
        this.props.put("wl", worldlevel);

        Prop abyss = new Prop("Tower Level", PseudoProp.TOWER_LEVEL);
        this.props.put("abyss", abyss);
        this.props.put("abyssfloor", abyss);
        this.props.put("ut", abyss);
        this.props.put("tower", abyss);
        this.props.put("towerlevel", abyss);
        this.props.put("unlocktower", abyss);

        Prop bplevel = new Prop("BP Level", PseudoProp.BP_LEVEL);
        this.props.put("bplevel", bplevel);
        this.props.put("bp", bplevel);
        this.props.put("battlepass", bplevel);

        Prop godmode = new Prop("godmode", PseudoProp.GOD_MODE);
        this.props.put("godmode", godmode);
        this.props.put("god", godmode);

        Prop nostamina = new Prop("nostamina", PseudoProp.NO_STAMINA);
        this.props.put("nostamina", nostamina);
        this.props.put("nostam", nostamina);
        this.props.put("ns", nostamina);

        Prop unlimitedenergy = new Prop("unlimitedenergy", PseudoProp.UNLIMITED_ENERGY);
        this.props.put("unlimitedenergy", unlimitedenergy);
        this.props.put("ue", unlimitedenergy);
    }

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size() != 2) {
            CommandHandler.sendTranslatedMessage(sender, "commands.setProp.usage");
            return;
        }
        String propStr = args.get(0).toLowerCase();
        String valueStr = args.get(1).toLowerCase();
        int value;
        
        if (!props.containsKey(propStr)) {
            CommandHandler.sendTranslatedMessage(sender, "commands.setProp.usage");
            return;
        }
        try {
            value = switch(valueStr.toLowerCase()) {
                case "on", "true" -> 1;
                case "off", "false" -> 0;
                case "toggle" -> -1;
                default -> Integer.parseInt(valueStr);
            };
        } catch (NumberFormatException ignored) {
            CommandHandler.sendTranslatedMessage(sender, "commands.execution.argument_error");
            return;
        }

        boolean success = false;
        Prop prop = props.get(propStr);

        success = switch (prop.pseudoProp) {
            case WORLD_LEVEL -> targetPlayer.setWorldLevel(value);
            case BP_LEVEL -> targetPlayer.getBattlePassManager().setLevel(value);
            case TOWER_LEVEL -> this.setTowerLevel(sender, targetPlayer, value);
            case GOD_MODE, NO_STAMINA, UNLIMITED_ENERGY -> this.setBool(sender, targetPlayer, prop.pseudoProp, value);
            default -> targetPlayer.setProperty(prop.prop, value);
        };

        if (success) {
            if (targetPlayer == sender) {
                CommandHandler.sendTranslatedMessage(sender, "commands.generic.set_to", prop.name, valueStr);
            } else {
                String uidStr = targetPlayer.getAccount().getId();
                CommandHandler.sendTranslatedMessage(sender, "commands.generic.set_for_to", prop.name, uidStr, valueStr);
            }
        } else {
            if (prop.prop != PlayerProperty.PROP_NONE) {  // PseudoProps need to do their own error messages
                String min = Integer.toString(targetPlayer.getPropertyMin(prop.prop));
                String max = Integer.toString(targetPlayer.getPropertyMax(prop.prop));
                CommandHandler.sendTranslatedMessage(sender, "commands.generic.invalid.value_between", prop.name, min, max);
            }
        }
    }

    private boolean setTowerLevel(Player sender, Player targetPlayer, int topFloor) {
        List<Integer> floorIds = targetPlayer.getServer().getTowerScheduleManager().getAllFloors();
        if (topFloor < 0 || topFloor > floorIds.size()) {
            String min = Integer.toString(0);
            String max = Integer.toString(floorIds.size());
            CommandHandler.sendTranslatedMessage(sender, "commands.generic.invalid.value_between", "Tower Level", min, max);
            return false;
        }

        Map<Integer, TowerLevelRecord> recordMap = targetPlayer.getTowerManager().getRecordMap();
        // Add records for each unlocked floor
        for (int floor : floorIds.subList(0, topFloor)) {
            if (!recordMap.containsKey(floor)) {
                recordMap.put(floor, new TowerLevelRecord(floor));
            }
        }
        // Remove records for each floor past our target
        for (int floor : floorIds.subList(topFloor, floorIds.size())) {
            if (recordMap.containsKey(floor)) {
                recordMap.remove(floor);
            }
        }
        // Six stars required on Floor 8 to unlock Floor 9+
        if (topFloor > 8) {
            recordMap.get(floorIds.get(7)).setLevelStars(0, 6);  // levelIds seem to start at 1 for Floor 1 Chamber 1, so this doesn't get shown at all
        }
        return true;
    }

    private boolean setBool(Player sender, Player targetPlayer, PseudoProp pseudoProp, int value) {
        boolean enabled = switch (pseudoProp) {
            case GOD_MODE -> targetPlayer.inGodmode();
            case NO_STAMINA -> targetPlayer.getUnlimitedStamina();
            case UNLIMITED_ENERGY -> !targetPlayer.getEnergyManager().getEnergyUsage();
            default -> false;
        };
        enabled = switch (value) {
            case -1 -> !enabled;
            case 0 -> false;
            default -> true;
        };

        switch (pseudoProp) {
            case GOD_MODE:
                targetPlayer.setGodmode(enabled);
                break;
            case NO_STAMINA:
                targetPlayer.setUnlimitedStamina(enabled);
                break;
            case UNLIMITED_ENERGY:
                targetPlayer.getEnergyManager().setEnergyUsage(!enabled);
                break;
            default:
                return false;
        }
        return true;
    }
}
