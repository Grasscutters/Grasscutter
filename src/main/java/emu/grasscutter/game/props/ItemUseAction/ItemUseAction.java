package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.data.common.ItemUseData;
import emu.grasscutter.game.props.ItemUseOp;

public class ItemUseAction {
    public static ItemUseAction fromItemUseData(ItemUseData data) {
        var useParam = data.getUseParam();
        return switch (data.getUseOp()) {
            case ITEM_USE_NONE -> null;
                // Uprade materials - no direct usage
            case ITEM_USE_ADD_EXP -> new ItemUseAddExp(useParam);
            case ITEM_USE_ADD_RELIQUARY_EXP -> new ItemUseAddReliquaryExp(useParam);
            case ITEM_USE_ADD_WEAPON_EXP -> new ItemUseAddWeaponExp(useParam);
                // Energy pickups
            case ITEM_USE_ADD_ALL_ENERGY -> new ItemUseAddAllEnergy(useParam);
            case ITEM_USE_ADD_ELEM_ENERGY -> new ItemUseAddElemEnergy(useParam);
                // Give items
            case ITEM_USE_ADD_ITEM -> new ItemUseAddItem(useParam);
            case ITEM_USE_GAIN_AVATAR -> new ItemUseGainAvatar(useParam);
            case ITEM_USE_GAIN_COSTUME -> new ItemUseGainCostume(useParam); // TODO - real success/fail
            case ITEM_USE_GAIN_FLYCLOAK -> new ItemUseGainFlycloak(useParam); // TODO - real success/fail
            case ITEM_USE_GAIN_NAME_CARD -> new ItemUseGainNameCard(useParam);
            case ITEM_USE_CHEST_SELECT_ITEM -> new ItemUseChestSelectItem(useParam);
            case ITEM_USE_ADD_SELECT_ITEM -> new ItemUseAddSelectItem(useParam);
            case ITEM_USE_GRANT_SELECT_REWARD -> new ItemUseGrantSelectReward(useParam);
            case ITEM_USE_COMBINE_ITEM -> new ItemUseCombineItem(useParam);
            case ITEM_USE_OPEN_RANDOM_CHEST -> new ItemUseOpenRandomChest(useParam);
                // Food effects
            case ITEM_USE_RELIVE_AVATAR -> new ItemUseReliveAvatar(
                    useParam); // First action for revival food. Should we worry about race conditions in
                // parallel streams?
            case ITEM_USE_ADD_CUR_HP -> new ItemUseAddCurHp(useParam);
            case ITEM_USE_ADD_CUR_STAMINA -> new ItemUseAddCurStamina(useParam);
            case ITEM_USE_ADD_SERVER_BUFF -> new ItemUseAddServerBuff(useParam);
            case ITEM_USE_MAKE_GADGET -> new ItemUseMakeGadget(useParam);
                // Unlock recipes - TODO: allow scheduling packets for after recipe is removed
            case ITEM_USE_UNLOCK_COMBINE -> new ItemUseUnlockCombine(useParam);
            case ITEM_USE_UNLOCK_CODEX -> new ItemUseUnlockCodex(
                    useParam); // TODO: No backend for this yet
            case ITEM_USE_UNLOCK_COOK_RECIPE -> new ItemUseUnlockCookRecipe(useParam);
            case ITEM_USE_UNLOCK_FORGE -> new ItemUseUnlockForge(useParam);
            case ITEM_USE_UNLOCK_FURNITURE_FORMULA -> new ItemUseUnlockFurnitureFormula(useParam);
            case ITEM_USE_UNLOCK_FURNITURE_SUITE -> new ItemUseUnlockFurnitureSuite(useParam);
            case ITEM_USE_UNLOCK_HOME_MODULE -> new ItemUseUnlockHomeModule(
                    useParam); // No backend for this yet
            case ITEM_USE_UNLOCK_HOME_BGM -> new ItemUseUnlockHomeBgm(useParam);
                // Account things
            case ITEM_USE_ACCEPT_QUEST -> new ItemUseAcceptQuest(useParam);
            case ITEM_USE_GAIN_CARD_PRODUCT -> new ItemUseGainCardProduct(useParam);
            case ITEM_USE_UNLOCK_PAID_BATTLE_PASS_NORMAL -> new ItemUseUnlockPaidBattlePassNormal(
                    useParam); // TODO: add paid BP

                // Unused in current resources
            case ITEM_USE_DEL_SERVER_BUFF -> null;
            case ITEM_USE_ADD_BIG_TALENT_POINT -> null;
            case ITEM_USE_GAIN_RESIN_CARD_PRODUCT -> null;
            case ITEM_USE_TRIGGER_ABILITY -> null;
            case ITEM_USE_ADD_TREASURE_MAP_BONUS_REGION_FRAGMENT -> null;
                // Used in current resources but no point yet
            case ITEM_USE_ADD_PERSIST_STAMINA -> null; // [int amount] one Test item
            case ITEM_USE_ADD_TEMPORARY_STAMINA -> null; // [int amount] one Test item
            case ITEM_USE_ADD_DUNGEON_COND_TIME -> null; // [int 1, int 15 or 20] - minigame shards
            case ITEM_USE_ADD_CHANNELLER_SLAB_BUFF -> null; // [int] minigame buffs
            case ITEM_USE_ADD_REGIONAL_PLAY_VAR -> null; // [String, int] - coral butterfly effect
        };
    }

    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_NONE;
    }

    public boolean useItem(UseItemParams params) {
        // An item must return true on at least one of its actions to count as successfully used.
        // If all of the actions return false, the item will not be consumed from inventory.
        return false;
    }

    public boolean postUseItem(UseItemParams params) {
        // This is run after the item has been consumed from inventory.
        return false;
    }
}
