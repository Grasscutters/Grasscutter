export type Page = "Home" | "Commands" | "Avatars" | "Items";
export type Days = "Sunday" | "Monday" | "Tuesday"
    | "Wednesday" | "Thursday" | "Friday" | "Saturday";

export type Command = {
    name: string[];
    description: string;
    usage: string[];
    permission: string[];
    target: Target;
};

export type Avatar = {
    name: string;
    quality: Quality;
    id: number;
};

export type Item = {
    id: number;
    name: string;
    quality: Quality;
    type: ItemType;
    icon: string;
};

// Exported from Project Amber.
export type ItemInfo = {
    response: number | 200 | 404;
    data: {
        name: string;
        description: string;
        type: string;
        recipe: boolean;
        mapMark: boolean;
        source: {
            name: string;
            type: string | "domain";
            days: Days;
        }[];
        icon: string;
        rank: 1 | 2 | 3 | 4 | 5;
        route: string;
    };
};

export enum Target {
    None = "NONE",
    Offline = "OFFLINE",
    Player = "PLAYER",
    Online = "ONLINE"
}

export enum Quality {
    Legendary = "LEGENDARY",
    Epic = "EPIC",
    Rare = "RARE",
    Uncommon = "UNCOMMON",
    Common = "COMMON",
    Unknown = "UNKNOWN"
}

export enum ItemType {
    None = "ITEM_NONE",
    Virtual = "ITEM_VIRTUAL",
    Material = "ITEM_MATERIAL",
    Reliquary = "ITEM_RELIQUARY",
    Weapon = "ITEM_WEAPON",
    Display = "ITEM_DISPLAY",
    Furniture = "ITEM_FURNITURE"
}

export enum ItemCategory {
    Constellation,
    Avatar,
    Weapon,
    Artifact,
    Furniture,
    Material,
    Miscellaneous
}

/**
 * Checks if a string is a page.
 *
 * @param page The string to check.
 */
export function isPage(page: string): page is Page {
    return ["Home", "Commands"].includes(page);
}

/**
 * Converts an item type to a string.
 *
 * @param type The item type to convert.
 */
export function itemTypeToString(type: ItemType): string {
    switch (type) {
        default: return "Unknown";
        case ItemType.None: return "None";
        case ItemType.Virtual: return "Virtual";
        case ItemType.Material: return "Material";
        case ItemType.Reliquary: return "Reliquary";
        case ItemType.Weapon: return "Weapon";
        case ItemType.Display: return "Display";
        case ItemType.Furniture: return "Furniture";
    }
}
