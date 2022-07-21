package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.inventory.Inventory;
import emu.grasscutter.game.inventory.ItemType;
import emu.grasscutter.game.player.Player;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Command(
    label = "clear",
    usage = {"(all|wp|art|mat) [lv<max level>] [r<max refinement>] [<max rarity>*]"},
    permission = "player.clearinv",
    permissionTargeted = "player.clearinv.others")
public final class ClearCommand implements CommandHandler {
    private static Pattern lvlRegex = Pattern.compile("l(?:vl?)?(\\d+)");  // Java doesn't have raw string literals :(
    private static Pattern refineRegex = Pattern.compile("r(\\d+)");
    private static Pattern rankRegex = Pattern.compile("(\\d+)\\*");

    private static int matchIntOrNeg(Pattern pattern, String arg) {
        Matcher match = pattern.matcher(arg);
        if (match.find()) {
            return Integer.parseInt(match.group(1));  // This should be exception-safe as only \d+ can be passed to it (i.e. non-empty string of pure digits)
        }
        return -1;
    }

    private static class ClearItemParameters {
        public int lvl = 1;
        public int refinement = 1;
        public int rank = 4;
    };

    private Stream<GameItem> getOther(ItemType type, Inventory playerInventory, ClearItemParameters param) {
        return playerInventory.getItems().values().stream()
                .filter(item -> item.getItemType() == type)
                .filter(item -> item.getItemData().getRankLevel() <= param.rank)
                .filter(item -> !item.isLocked() && !item.isEquipped());
    }

    private Stream<GameItem> getWeapons(Inventory playerInventory, ClearItemParameters param) {
        return getOther(ItemType.ITEM_WEAPON, playerInventory, param)
                .filter(item -> item.getLevel() <= param.lvl)
                .filter(item -> item.getRefinement() < param.refinement);
    }

    private Stream<GameItem> getRelics(Inventory playerInventory, ClearItemParameters param) {
        return getOther(ItemType.ITEM_RELIQUARY, playerInventory, param)
                .filter(item -> item.getLevel() <= param.lvl + 1);
    }

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        Inventory playerInventory = targetPlayer.getInventory();
        ClearItemParameters param = new ClearItemParameters();

        // Extract any tagged arguments (e.g. "lv90", "x100", "r5")
        for (int i = args.size() - 1; i >= 0; i--) {  // Reverse iteration as we are deleting elements
            String arg = args.get(i).toLowerCase();
            boolean deleteArg = false;
            int argNum;
            // Note that a single argument can actually match all of these, e.g. "lv90r5*"
            if ((argNum = matchIntOrNeg(lvlRegex, arg)) != -1) {
                param.lvl = argNum;
                deleteArg = true;
            }
            if ((argNum = matchIntOrNeg(refineRegex, arg)) != -1) {
                param.refinement = argNum;
                deleteArg = true;
            }
            if ((argNum = matchIntOrNeg(rankRegex, arg)) != -1) {
                param.rank = argNum;
                deleteArg = true;
            }
            if (deleteArg) {
                args.remove(i);
            }
        }

        if (args.size() < 1) {
            sendUsageMessage(sender);
            return;
        }

        String playerString = targetPlayer.getNickname();  // Should probably be UID instead but whatever
        switch (args.get(0)) {
            case "wp" -> {
                playerInventory.removeItems(getWeapons(playerInventory, param).toList());
                CommandHandler.sendTranslatedMessage(sender, "commands.clear.weapons", playerString);
            }
            case "art" -> {
                playerInventory.removeItems(getRelics(playerInventory, param).toList());
                CommandHandler.sendTranslatedMessage(sender, "commands.clear.artifacts", playerString);
            }
            case "mat" -> {
                playerInventory.removeItems(getOther(ItemType.ITEM_MATERIAL, playerInventory, param).toList());
                CommandHandler.sendTranslatedMessage(sender, "commands.clear.materials", playerString);
            }
            case "all" -> {
                playerInventory.removeItems(getRelics(playerInventory, param).toList());
                CommandHandler.sendTranslatedMessage(sender, "commands.clear.artifacts", playerString);
                playerInventory.removeItems(getWeapons(playerInventory, param).toList());
                CommandHandler.sendTranslatedMessage(sender, "commands.clear.weapons", playerString);
                playerInventory.removeItems(getOther(ItemType.ITEM_MATERIAL, playerInventory, param).toList());
                CommandHandler.sendTranslatedMessage(sender, "commands.clear.materials", playerString);
                playerInventory.removeItems(getOther(ItemType.ITEM_FURNITURE, playerInventory, param).toList());
                CommandHandler.sendTranslatedMessage(sender, "commands.clear.furniture", playerString);
                playerInventory.removeItems(getOther(ItemType.ITEM_DISPLAY, playerInventory, param).toList());
                CommandHandler.sendTranslatedMessage(sender, "commands.clear.displays", playerString);
                playerInventory.removeItems(getOther(ItemType.ITEM_VIRTUAL, playerInventory, param).toList());
                CommandHandler.sendTranslatedMessage(sender, "commands.clear.virtuals", playerString);
                CommandHandler.sendTranslatedMessage(sender, "commands.clear.everything", playerString);
            }
        }
    }
}
