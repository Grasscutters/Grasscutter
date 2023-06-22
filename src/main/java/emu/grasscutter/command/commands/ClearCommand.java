package emu.grasscutter.command.commands;

import static emu.grasscutter.command.CommandHelpers.*;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.inventory.Inventory;
import emu.grasscutter.game.inventory.ItemType;
import emu.grasscutter.game.player.Player;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import lombok.Setter;

@Command(
        label = "clear",
        usage = {"(all|wp|art|mat|<itemId>) [x<amount>] [lv<max level>] [r<max refinement>] [<max rarity>*]"},
        permission = "player.clearinv",
        permissionTargeted = "player.clearinv.others")
public final class ClearCommand implements CommandHandler {

    private static final Map<Pattern, BiConsumer<ClearItemParameters, Integer>> intCommandHandlers =
            Map.ofEntries(
                    Map.entry(amountRegex, ClearItemParameters::setAmount),
                    Map.entry(lvlRegex, ClearItemParameters::setLvl),
                    Map.entry(refineRegex, ClearItemParameters::setRefinement),
                    Map.entry(rankRegex, ClearItemParameters::setRank));

    private Stream<GameItem> getOther(
            ItemType type, Inventory playerInventory, ClearItemParameters param) {
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

    private Stream<GameItem> getId(int id, Inventory playerInventory, ClearItemParameters param) {
        return playerInventory.getItems().values().stream()
                .filter(item -> item.getItemId() == id)
                .filter(item -> item.getItemData().getRankLevel() <= param.rank)
                .filter(item -> !item.isLocked() && !item.isEquipped());
    }

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        Inventory playerInventory = targetPlayer.getInventory();
        ClearItemParameters param = new ClearItemParameters();

        // Extract any tagged int arguments (e.g. "lv90", "x100", "r5")
        parseIntParameters(args, param, intCommandHandlers);

        if (args.size() < 1) {
            sendUsageMessage(sender);
            return;
        }

        String playerString = targetPlayer.getNickname(); // Should probably be UID instead but whatever

        String id = args.remove(0);

        try {
            switch (id) {
                case "wp" -> {
                    if (param.amount != -1) {
                        playerInventory.removeItems(getWeapons(playerInventory, param).toList().stream().limit(param.amount).collect(Collectors.toList()));
                        CommandHandler.sendTranslatedMessage(sender, "commands.clear.weapons", playerString, param.amount);
                    } else {
                        playerInventory.removeItems(getWeapons(playerInventory, param).toList());
                        CommandHandler.sendTranslatedMessage(sender, "commands.clear.weapons_all", playerString);
                    }

                }
                case "art" -> {
                    if (param.amount != -1) {
                        playerInventory.removeItems(getRelics(playerInventory, param).toList().stream().limit(param.amount).collect(Collectors.toList()));
                        CommandHandler.sendTranslatedMessage(sender, "commands.clear.artifacts", playerString, param.amount);
                    } else {
                        playerInventory.removeItems(getRelics(playerInventory, param).toList());
                        CommandHandler.sendTranslatedMessage(sender, "commands.clear.artifacts_all", playerString);
                    }
                }
                case "mat" -> {
                    if (param.amount != -1) {
                        playerInventory.removeItems(getOther(ItemType.ITEM_MATERIAL, playerInventory, param).toList().stream().limit(param.amount).collect(Collectors.toList()));
                        CommandHandler.sendTranslatedMessage(sender, "commands.clear.materials", playerString, param.amount);
                    } else {
                        playerInventory.removeItems(getOther(ItemType.ITEM_MATERIAL, playerInventory, param).toList());
                        CommandHandler.sendTranslatedMessage(sender, "commands.clear.materials_all", playerString);
                    }
                }
                case "all" -> {
                    playerInventory.removeItems(getRelics(playerInventory, param).toList());
                    CommandHandler.sendTranslatedMessage(sender, "commands.clear.artifacts_all", playerString);
                    playerInventory.removeItems(getWeapons(playerInventory, param).toList());
                    CommandHandler.sendTranslatedMessage(sender, "commands.clear.weapons_all", playerString);
                    playerInventory.removeItems(
                        getOther(ItemType.ITEM_MATERIAL, playerInventory, param).toList());
                    CommandHandler.sendTranslatedMessage(sender, "commands.clear.materials_all", playerString);
                    playerInventory.removeItems(
                        getOther(ItemType.ITEM_FURNITURE, playerInventory, param).toList());
                    CommandHandler.sendTranslatedMessage(sender, "commands.clear.furniture", playerString);
                    playerInventory.removeItems(
                        getOther(ItemType.ITEM_DISPLAY, playerInventory, param).toList());
                    CommandHandler.sendTranslatedMessage(sender, "commands.clear.displays", playerString);
                    playerInventory.removeItems(
                        getOther(ItemType.ITEM_VIRTUAL, playerInventory, param).toList());
                    CommandHandler.sendTranslatedMessage(sender, "commands.clear.virtuals", playerString);
                    CommandHandler.sendTranslatedMessage(sender, "commands.clear.everything", playerString);
                }
                default -> {
                    try {
                        param.id = Integer.parseInt(id);
                    } catch (NumberFormatException e) {
                        // TODO: Parse from item name using GM Handbook.
                        CommandHandler.sendTranslatedMessage(sender, "commands.generic.invalid.itemId");
                        return;
                    }
                    if (param.amount != -1) {
                        playerInventory.removeItems(getId(param.id, playerInventory, param).toList().stream().limit(param.amount).collect(Collectors.toList()));
                        CommandHandler.sendTranslatedMessage(sender, "commands.clear.id", playerString, param.amount, param.id);
                    } else {
                        playerInventory.removeItems(getId(param.id, playerInventory, param).toList());
                        CommandHandler.sendTranslatedMessage(sender, "commands.clear.id_all", playerString, param.id);
                    }
                }
            }
        }
        catch(Exception e) {
            CommandHandler.sendTranslatedMessage(sender, "commands.execution.argument_error");
        }
    }

    private static class ClearItemParameters {
        public int id;
        @Setter public int amount = -1;
        @Setter public int lvl = 1;
        @Setter public int refinement = 1;
        @Setter public int rank = 4;
    }
}
