export type Page = "Home" | "Commands" | "Avatars" | "Items";

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
