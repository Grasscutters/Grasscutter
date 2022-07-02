package emu.grasscutter.game.managers;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import emu.grasscutter.data.GameData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.CookRecipeDataOuterClass;
import emu.grasscutter.server.packet.send.PacketCookDataNotify;

public class CookingManager {
    private static Set<Integer> defaultUnlockedRecipies;
    private final Player player;

    public CookingManager(Player player) {
        this.player = player;
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
     * Perform cooking.
     ********************/
    

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
