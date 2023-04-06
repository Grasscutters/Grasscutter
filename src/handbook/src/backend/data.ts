import avatars from "@data/avatars.json";
import commands from "@data/commands.json";
import items from "@data/items.csv";

import type { Command, Avatar, Item } from "@backend/types";

/**
 * Fetches and casts all commands in the file.
 */
export function getCommands(): { [key: string]: Command } {
    return commands as { [key: string]: Command };
}

/**
 * Fetches and casts all avatars in the file.
 */
export function getAvatars(): { [key: number]: Avatar } {
    return avatars as { [key: number] : Avatar };
}

/**
 * Fetches and casts all items in the file.
 */
export function getItems(): Item[] {
    return items.map(item => {
        return {
            id: item[0],
            name: item[1],
            quality: item[2],
            type: item[3]
        };
    });
}
