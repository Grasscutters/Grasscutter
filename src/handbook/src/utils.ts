import type { Entity, Item, EntityInfo, ItemInfo, WindowDetails } from "@backend/types";
import { ItemType, Quality } from "@backend/types";

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
 * Gets the path to the icon for an entity.
 * Uses the Project Amber API to get the icon.
 *
 * @param entity The entity to get the icon for. Project Amber data required.
 */
export function entityIcon(entity: Entity): string {
    return `https://api.ambr.top/assets/UI/monster/UI_MonsterIcon_${entity.internal}.png`;
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

/**
 * Gets the route for an item type.
 *
 * @param type The type of the item.
 */
export function typeToRoute(type: ItemType): string {
    switch (type) {
        default:
            return "material";
        case ItemType.Furniture:
            return "furniture";
        case ItemType.Reliquary:
            return "reliquary";
        case ItemType.Weapon:
            return "weapon";
    }
}

/**
 * Fetches the data for an item.
 * Uses the Project Amber API to get the data.
 *
 * @route GET https://api.ambr.top/v2/EN/{type}/{id}
 * @param item The item to fetch the data for.
 */
export async function fetchItemData(item: Item): Promise<ItemInfo> {
    let url = `https://api.ambr.top/v2/EN/(type)/(id)`;

    // Replace the type and ID in the URL.
    url = url.replace("(type)", typeToRoute(item.type));
    url = url.replace("(id)", item.id.toString());

    // Fetch the data.
    return fetch(url)
        .then((res) => res.json())
        .catch(() => {});
}

/**
 * Fetches the data for an entity.
 * Uses the Project Amber API to get the data.
 *
 * @route GET https://api.ambr.top/v2/en/monster/{id}
 * @param entity The entity to fetch the data for.
 */
export async function fetchEntityData(entity: Entity): Promise<EntityInfo> {
    return fetch(`https://api.ambr.top/v2/en/monster/${entity.id}`)
        .then((res) => res.json())
        .catch(() => {});
}

/**
 * Attempts to copy text to the clipboard.
 * Uses the Clipboard API.
 *
 * @param text The text to copy.
 */
export async function copyToClipboard(text: string): Promise<void> {
    await navigator.clipboard.writeText(text);
}

/**
 * Opens a URL in a new tab.
 * Uses the window.open() method.
 *
 * @param url The URL to open.
 */
export function openUrl(url: string): void {
    window.open(url, "_blank");
}

/**
 * Checks if a value is NaN.
 * Returns an empty string if it is.
 *
 * @param value The value to check.
 */
export function notNaN(value: number | string): string {
    const number = parseInt(value.toString());
    return isNaN(number) ? "" : number.toString();
}

/**
 * Extracts the server details out of the window.
 */
export function getWindowDetails(): WindowDetails {
    const details = (window as any).details;
    const { address, port, disable } = details;

    return {
        address: (address as string).includes("DETAILS_ADDRESS") ? "127.0.0.1" : address,
        port: (port as string).includes("DETAILS_PORT") ? 443 : parseInt(port),
        disable: (disable as string).includes("DETAILS_DISABLE") ? false : disable == "true"
    };
}
