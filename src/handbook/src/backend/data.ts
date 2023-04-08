import commands from "@data/commands.json";
import avatars from "@data/avatars.csv";
import items from "@data/items.csv";

import { Quality, ItemType, ItemCategory } from "@backend/types";
import type { Command, Avatar, Item } from "@backend/types";

import { inRange } from "@app/utils";

type AvatarDump = { [key: number]: Avatar };
type CommandDump = { [key: string]: Command };
type TaggedItems = { [key: number]: Item[] }

/*
 * Notes on artifacts:
 * TODO: Figure out what suffix is for which artifact type.
 */

export const sortedItems: TaggedItems = {
    [ItemCategory.Constellation]: [], // Range: 1102 - 11xx
    [ItemCategory.Weapon]: [],
    [ItemCategory.Artifact]: [],
    [ItemCategory.Furniture]: [],
    [ItemCategory.Material]: [],
    [ItemCategory.Miscellaneous]: []
};

/**
 * Setup function for this file.
 * Sorts all items into their respective categories.
 */
export function setup(): void {
    getItems().forEach(item => {
        switch (item.type) {
            case ItemType.Weapon: sortedItems[ItemCategory.Weapon].push(item); break;
            case ItemType.Material: sortedItems[ItemCategory.Material].push(item); break;
            case ItemType.Furniture: sortedItems[ItemCategory.Furniture].push(item); break;
            case ItemType.Reliquary: sortedItems[ItemCategory.Artifact].push(item); break;
        }

        // Sort constellations.
        if (inRange(item.id, 1102, 1199)) {
            sortedItems[ItemCategory.Constellation].push(item);
        }
    });
}

/**
 * Fetches and casts all commands in the file.
 */
export function getCommands(): CommandDump {
    return commands as CommandDump;
}

/**
 * Fetches and lists all the commands in the file.
 */
export function listCommands(): Command[] {
    return Object.values(getCommands());
}

/**
 * Fetches and casts all avatars in the file.
 */
export function getAvatars(): AvatarDump {
    const map: AvatarDump = {};
    avatars.forEach((avatar) => {
        const values = Object.values(avatar) as [string, string, string];
        const id = parseInt(values[0]);
        map[id] = {
            id,
            name: values[1],
            quality: values[2] as Quality
        };
    });

    return map;
}

/**
 * Fetches and lists all the avatars in the file.
 */
export function listAvatars(): Avatar[] {
    return Object.values(getAvatars());
}

/**
 * Fetches and casts all items in the file.
 */
export function getItems(): Item[] {
    return items.map((item) => {
        const values = Object.values(item) as [string, string, string, string];
        const id = parseInt(values[0]);
        return {
            id,
            name: values[1],
            type: values[2] as ItemType,
            quality: values[3] as Quality
        };
    });
}
