export type Page = "Home" | "Commands" | "Avatars" | "Items" | "Entities" | "Scenes" | "Quests" | "Achievements";
export type Overlays = "None" | "ServerSettings";
export type Days = "Sunday" | "Monday" | "Tuesday" | "Wednesday" | "Thursday" | "Friday" | "Saturday";

export type MainQuest = {
    id: number;
    title: string;
};

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

export type Scene = {
    identifier: string;
    type: SceneType;
    id: number;
};

export type Item = {
    id: number;
    name: string;
    quality: Quality;
    type: ItemType;
    icon: string;
};

export type Entity = {
    id: number;
    name: string;
    internal: string;
};

export type Quest = {
    id: number;
    description: string;
    mainId: number;
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

// Exported from Project Amber.
export type EntityInfo = {
    response: number | 200 | 404;
    data: {
        id: number;
        name: string;
        type: string;
        icon: string;
        route: string;
        title: string;
        specialName: string;
        description: string;
        entries: any[];
        tips: null;
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

export enum SceneType {
    None = "SCENE_NONE",
    World = "SCENE_WORLD",
    Dungeon = "SCENE_DUNGEON",
    Room = "SCENE_ROOM",
    HomeWorld = "SCENE_HOME_WORLD",
    HomeRoom = "SCENE_HOME_ROOM",
    Activity = "SCENE_ACTIVITY"
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

export type CommandResponse = {
    status: number | 200 | 500;
    message: string;
};

export type WindowDetails = {
    address: string;
    port: number;
    disable: boolean;
};

/**
 * Checks if a string is a page.
 *
 * @param page The string to check.
 */
export function isPage(page: string): page is Page {
    return ["Home", "Commands", "Avatars", "Items", "Entities", "Scenes"].includes(page);
}

/**
 * Converts an item type to a string.
 *
 * @param type The item type to convert.
 */
export function itemTypeToString(type: ItemType): string {
    switch (type) {
        default:
            return "Unknown";
        case ItemType.None:
            return "None";
        case ItemType.Virtual:
            return "Virtual";
        case ItemType.Material:
            return "Material";
        case ItemType.Reliquary:
            return "Reliquary";
        case ItemType.Weapon:
            return "Weapon";
        case ItemType.Display:
            return "Display";
        case ItemType.Furniture:
            return "Furniture";
    }
}
