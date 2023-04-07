import commands from "@data/commands.json";
import avatars from "@data/avatars.csv";
import items from "@data/items.csv";

import { Quality, ItemType } from "@backend/types";
import type { Command, Avatar, Item } from "@backend/types";

type AvatarDump = { [key: number]: Avatar };
type CommandDump = { [key: string]: Command };

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
    const map: AvatarDump = {}; avatars.forEach(avatar => {
        const values = Object.values(avatar) as
            [string, string, string];
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
    return items.map(item => {
        const values = Object.values(item) as
            [string, string, string, string];
        const id = parseInt(values[0]);
        return {
            id,
            name: values[1],
            type: values[2] as ItemType,
            quality: values[3] as Quality
        }
    });
}
