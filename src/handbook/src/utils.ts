import { ItemType, Quality } from "@backend/types";
import type { Item } from "@backend/types";

/**
 * Fetches the name of the CSS variable for the quality.
 *
 * @param quality The quality of the item.
 */
export function colorFor(quality: Quality): string {
    switch (quality) {
        default:
            return "--legendary-color";
        case "EPIC":
            return "--epic-color";
        case "RARE":
            return "--rare-color";
        case "UNCOMMON":
            return "--uncommon-color";
        case "COMMON":
            return "--common-color";
        case "UNKNOWN":
            return "--unknown-color";
    }
}

/**
 * Checks if a value is between two numbers.
 *
 * @param value The value to check.
 * @param min The minimum value.
 * @param max The maximum value.
 */
export function inRange(value: number, min: number, max: number): boolean {
    return value >= min && value <= max;
}

/**
 * Gets the path to the icon for an item.
 * Uses the Project Amber API to get the icon.
 *
 * @param item The item to get the icon for.
 */
export function itemIcon(item: Item): string {
    // Check if the item matches a special case.
    if (inRange(item.id, 1001, 1099)) {
        return `https://paimon.moe/images/characters/${formatAvatarName(item.name, item.id)}.png`;
    }

    switch (item.type) {
        default:
            return `https://api.ambr.top/assets/UI/UI_${item.icon}.png`;
        case ItemType.Furniture:
            return `https://api.ambr.top/assets/UI/furniture/UI_${item.icon}.png`;
        case ItemType.Reliquary:
            return `https://api.ambr.top/assets/UI/reliquary/UI_${item.icon}.png`;
    }
}

/**
 * Formats a character's name to fit with the reference name.
 * Example: Hu Tao -> hu_tao
 *
 * @param name The character's name.
 * @param id The character's ID.
 */
export function formatAvatarName(name: string, id: number): string {
    // Check if a different name is used for the character.
    if (refSwitch[id]) name = refSwitch[id];
    return name.toLowerCase().replace(" ", "_");
}

const refSwitch: { [key: number]: string } = {
    10000005: "traveler_anemo",
    10000007: "traveler_geo"
};
