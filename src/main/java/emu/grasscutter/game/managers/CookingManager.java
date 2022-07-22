package emu.grasscutter.game.managers;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.BasePlayerManager;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.game.props.ItemUseOp;
import emu.grasscutter.net.proto.CookRecipeDataOuterClass;
import emu.grasscutter.net.proto.PlayerCookArgsReqOuterClass.PlayerCookArgsReq;
import emu.grasscutter.net.proto.PlayerCookReqOuterClass.PlayerCookReq;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;
import emu.grasscutter.server.packet.send.PacketCookDataNotify;
import emu.grasscutter.server.packet.send.PacketCookRecipeDataNotify;
import emu.grasscutter.server.packet.send.PacketPlayerCookArgsRsp;
import emu.grasscutter.server.packet.send.PacketPlayerCookRsp;
import io.netty.util.internal.ThreadLocalRandom;

public class CookingManager extends BasePlayerManager {
    private static final int MANUAL_PERFECT_COOK_QUALITY = 3;
    private static Set<Integer> defaultUnlockedRecipies;

    public CookingManager(Player player) {
        super(player);
    }

    public static void initialize() {
        // Initialize the set of recipies that are unlocked by default.
        defaultUnlockedRecipies = new HashSet<>();

        for (var recipe : GameData.getCookRecipeDataMap().values()) {
            if (recipe.isDefaultUnlocked()) {
                defaultUnlockedRecipies.add(recipe.getId());
            }
        }
    }

    /********************
     * Unlocking for recipies.
     ********************/
    public synchronized boolean unlockRecipe(GameItem recipeItem) {
        // Make sure this is actually a cooking recipe.
        if (recipeItem.getItemData().getItemUse().get(0).getUseOp() != ItemUseOp.ITEM_USE_UNLOCK_COOK_RECIPE) {
            return false;
        }

        // Determine the recipe we should unlock.
        int recipeId = Integer.parseInt(recipeItem.getItemData().getItemUse().get(0).getUseParam()[0]);

        // Remove the item from the player's inventory.
        // We need to do this here, before sending CookRecipeDataNotify, or the the UI won't correctly update.
        player.getInventory().removeItem(recipeItem, 1);

        // Tell the client that this blueprint is now unlocked and add the unlocked item to the player.
        this.player.getUnlockedRecipies().put(recipeId, 0);
        this.player.sendPacket(new PacketCookRecipeDataNotify(recipeId));

        return true;
    }

    /********************
     * Perform cooking.
     ********************/
    private double getSpecialtyChance(ItemData cookedItem) {
        // Chances taken from the Wiki.
        return switch (cookedItem.getRankLevel()) {
            case 1 -> 0.25;
            case 2 -> 0.2;
            case 3 -> 0.15;
            default -> 0;
        };
    }

    public void handlePlayerCookReq(PlayerCookReq req) {
        // Get info from the request.
        int recipeId = req.getRecipeId();
        int quality = req.getQteQuality();
        int count = req.getCookCount();
        int avatar = req.getAssistAvatar();

        // Get recipe data.
        var recipeData = GameData.getCookRecipeDataMap().get(recipeId);
        if (recipeData == null) {
            this.player.sendPacket(new PacketPlayerCookRsp(Retcode.RET_FAIL));
            return;
        }

        // Get proficiency for player.
        int proficiency = this.player.getUnlockedRecipies().getOrDefault(recipeId, 0);

        // Try consuming materials.
        boolean success = player.getInventory().payItems(recipeData.getInputVec().toArray(new ItemParamData[0]), count, ActionReason.Cook);
        if (!success) {
            this.player.sendPacket(new PacketPlayerCookRsp(Retcode.RET_FAIL));
        }

        // Get result item information.
        int qualityIndex =
            quality == 0
            ? 2
            : quality - 1;

        ItemParamData resultParam = recipeData.getQualityOutputVec().get(qualityIndex);
        ItemData resultItemData = GameData.getItemDataMap().get(resultParam.getItemId());

        // Handle character's specialties.
        int specialtyCount = 0;
        double specialtyChance = this.getSpecialtyChance(resultItemData);

        var bonusData = GameData.getCookBonusDataMap().get(avatar);
        if (bonusData != null && recipeId == bonusData.getRecipeId()) {
            // Roll for specialy replacements.
            for (int i = 0; i < count; i++) {
                if (ThreadLocalRandom.current().nextDouble() <= specialtyChance) {
                    specialtyCount++;
                }
            }
        }

        // Obtain results.
        List<GameItem> cookResults = new ArrayList<>();

        int normalCount = count - specialtyCount;
        GameItem cookResultNormal = new GameItem(resultItemData, resultParam.getCount() * normalCount);
        cookResults.add(cookResultNormal);
        this.player.getInventory().addItem(cookResultNormal);

        if (specialtyCount > 0) {
            ItemData specialtyItemData = GameData.getItemDataMap().get(bonusData.getReplacementItemId());
            GameItem cookResultSpecialty = new GameItem(specialtyItemData, resultParam.getCount() * specialtyCount);
            cookResults.add(cookResultSpecialty);
            this.player.getInventory().addItem(cookResultSpecialty);
        }

        // Increase player proficiency, if this was a manual perfect cook.
        if (quality == MANUAL_PERFECT_COOK_QUALITY) {
            proficiency = Math.min(proficiency + 1, recipeData.getMaxProficiency());
            this.player.getUnlockedRecipies().put(recipeId, proficiency);
        }

        // Send response.
        this.player.sendPacket(new PacketPlayerCookRsp(cookResults, quality, count, recipeId, proficiency));
    }

    /********************
     * Cooking arguments.
     ********************/
    public void handleCookArgsReq(PlayerCookArgsReq req) {
        this.player.sendPacket(new PacketPlayerCookArgsRsp());
    }

     /********************
     * Notify unlocked recipies.
     ********************/
    private void addDefaultUnlocked() {
        // Get recipies that are already unlocked.
        var unlockedRecipies = this.player.getUnlockedRecipies();

        // Get recipies that should be unlocked by default but aren't.
        var additionalRecipies = new HashSet<>(defaultUnlockedRecipies);
        additionalRecipies.removeAll(unlockedRecipies.keySet());

        // Add them to the player.
        for (int id : additionalRecipies) {
            unlockedRecipies.put(id, 0);
        }
    }

    public void sendCookDataNofity() {
        // Default unlocked recipies to player if they don't have them yet.
        this.addDefaultUnlocked();

        // Get unlocked recipies.
        var unlockedRecipies = this.player.getUnlockedRecipies();

        // Construct CookRecipeData protos.
        List<CookRecipeDataOuterClass.CookRecipeData> data = new ArrayList<>();
        for (var recipe : unlockedRecipies.entrySet()) {
            int recipeId = recipe.getKey();
            int proficiency = recipe.getValue();

            CookRecipeDataOuterClass.CookRecipeData proto = CookRecipeDataOuterClass.CookRecipeData.newBuilder()
                .setRecipeId(recipeId)
                .setProficiency(proficiency)
                .build();
            data.add(proto);
        }

        // Send packet.
        this.player.sendPacket(new PacketCookDataNotify(data));
    }
}
