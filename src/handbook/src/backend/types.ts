export type Page = "Home" | "Commands";

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
}

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
    None = 0,
    Virtual = 1,
    Material = 2,
    Reliquary = 3,
    Weapon = 4,
    Display = 5,
    Furniture = 6
}

/**
 * Checks if a string is a page.
 *
 * @param page The string to check.
 */
export function isPage(page: string): page is Page {
    return ["Home", "Commands"].includes(page);
}
