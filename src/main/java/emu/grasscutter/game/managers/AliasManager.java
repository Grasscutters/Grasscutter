package emu.grasscutter.game.managers;

import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.BasePlayerManager;
import emu.grasscutter.game.player.Player;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AliasManager extends BasePlayerManager {
    /*
    v aliasManager: Object
        v aliases: Object
            v somealias1: Array
                0: prop um 1
                1: prop ue 1
                2: give avatars
            v somealias2: Array
                0: setstats cdr 1
                1: setstats cr 1
                2: setstats cd 5
            > somealias3: Array
            > somealias4: Array
            > somealias5: Array
     */
    public AliasManager(Player player) {
        super(player);
    }
    public static boolean setAlias(Player player, String alias, String commandInputString) {
        String filtered = commandInputString.replaceAll("[^a-zA-Z0-9; ]", "");
        List<String> commandInput = Stream.of(filtered.split(";")).map(String::trim).collect(Collectors.toList());
        player.getCommandAliases().put(alias, commandInput);
        player.save();
        return true;
    }

    public static boolean getAliasDescription(Player player, String alias) {
        if (!player.getCommandAliases().containsKey(alias)) return false;
        CommandHandler.sendMessage(player, alias + " = " + player.getCommandAliases().get(alias));
        return true;
    }

    public static boolean clearAlias(Player player, String alias) {
        if (!player.getCommandAliases().containsKey(alias)) return false;
        player.getCommandAliases().keySet().remove(alias);
        player.save();
        return true;
    }

    public static boolean listAlias(Player player) {
        if (player.getCommandAliases() == null || player.getCommandAliases().isEmpty()) return false;
        for (String i : player.getCommandAliases().keySet()) CommandHandler.sendMessage(player,  i + " = " + player.getCommandAliases().get(i));
        return true;
    }

    public static void clearAllAlias(Player player) {
        player.getCommandAliases().clear();
        player.save();
    }
}
