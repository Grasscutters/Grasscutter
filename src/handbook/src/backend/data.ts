import mainQuests from "@data/mainquests.csv";
import commands from "@data/commands.json";
import entities from "@data/entities.csv";
import avatars from "@data/avatars.csv";
import scenes from "@data/scenes.csv";
import quests from "@data/quests.csv";
import items from "@data/items.csv";

import type { RawNodeDatum } from "react-d3-tree";

import { Quality, ItemType, ItemCategory, SceneType } from "@backend/types";
import type { MainQuest, Command, Avatar, Item, Scene, Entity, Quest } from "@backend/types";

import { inRange } from "@app/utils";

type AvatarDump = { [key: number]: Avatar };
type CommandDump = { [key: string]: Command };
type TaggedItems = { [key: number]: Item[] };
type QuestDump = { [key: number]: Quest };
type MainQuestDump = { [key: number]: MainQuest };

/**
 * @see {@file src/handbook/data/README.md}
 */

export const sortedItems: TaggedItems = {
    [ItemCategory.Constellation]: [], // Range: 1102 - 11xx
    [ItemCategory.Avatar]: [], // Range: 1002 - 10xx
    [ItemCategory.Weapon]: [],
    [ItemCategory.Artifact]: [],
    [ItemCategory.Furniture]: [],
    [ItemCategory.Material]: [],
    [ItemCategory.Miscellaneous]: []
};

export let allMainQuests: MainQuestDump = {};

/**
 * Setup function for this file.
 * Sorts all items into their respective categories.
 */
export function setup(): void {
    getItems().forEach((item) => {
        switch (item.type) {
            case ItemType.Weapon:
                sortedItems[ItemCategory.Weapon].push(item);
                break;
            case ItemType.Material:
                sortedItems[ItemCategory.Material].push(item);
                break;
            case ItemType.Furniture:
                sortedItems[ItemCategory.Furniture].push(item);
                break;
            case ItemType.Reliquary:
                sortedItems[ItemCategory.Artifact].push(item);
                break;
        }

        // Sort constellations.
        if (inRange(item.id, 1102, 1199)) {
            sortedItems[ItemCategory.Constellation].push(item);
        }
        // Sort avatars.
        if (inRange(item.id, 1002, 1099)) {
            sortedItems[ItemCategory.Avatar].push(item);
        }
    });

    allMainQuests = getMainQuests();
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
    return Object.values(getCommands()).sort((a, b) => a.name[0].localeCompare(b.name[0]));
}

/**
 * Fetches and casts all entities in the file.
 */
export function getEntities(): Entity[] {
    return entities
        .map((entry) => {
            const values = Object.values(entry) as string[];
            const id = parseInt(values[0]);
            return {
                id,
                name: values[1],
                internal: values[2]
            };
        })
        .filter((entity) => !entity.name.includes("Mechanicus"));
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
    return Object.values(getAvatars()).sort((a, b) => a.name.localeCompare(b.name));
}

/**
 * Fetches and casts all scenes in the file.
 */
export function getScenes(): Scene[] {
    return scenes
        .map((entry) => {
            const values = Object.values(entry) as string[];
            const id = parseInt(values[0]);
            return {
                id,
                identifier: values[1],
                type: values[2] as SceneType
            };
        })
        .sort((a, b) => a.id - b.id);
}

/**
 * Fetches and casts all items in the file.
 */
export function getItems(): Item[] {
    return items.map((entry) => {
        const values = Object.values(entry) as string[];
        const id = parseInt(values[0]);
        return {
            id,
            name: values[1],
            type: values[3] as ItemType,
            quality: values[2] as Quality,
            icon: values[4]
        };
    });
}

/**
 * Fetches and casts all quests in the file.
 */
export function getQuests(): QuestDump {
    const map: QuestDump = {};
    quests.forEach((quest: Quest) => {
        quest.description = quest.description.replaceAll("\\", ",");
        map[quest.id] = quest;
    });

    return map;
}

/**
 * Fetches and lists all the quests in the file.
 */
export function listQuests(): Quest[] {
    return Object.values(getQuests()).sort((a, b) => a.id - b.id);
}

/**
 * Fetches and casts all quests in the file.
 */
export function getMainQuests(): MainQuestDump {
    const map: MainQuestDump = {};
    mainQuests.forEach((quest: MainQuest) => {
        quest.title = quest.title.replaceAll("\\", ",");
        map[quest.id] = quest;
    });

    return map;
}

/**
 * Fetches and lists all the quests in the file.
 */
export function listMainQuests(): MainQuestDump[] {
    return Object.values(allMainQuests).sort((a, b) => a.id - b.id);
}

/**
 * Fetches a quest by its ID.
 *
 * @param quest The quest ID.
 */
export function getMainQuestFor(quest: Quest): MainQuest {
    return allMainQuests[quest.mainId];
}

/**
 * Fetches all quests for a main quest.
 *
 * @param mainQuest The main quest to fetch quests for.
 */
export function listSubQuestsFor(mainQuest: MainQuest): Quest[] {
    return listQuests().filter((quest) => quest.mainId == mainQuest.id);
}

/*
 * Tree conversion methods.
 */

/**
 * Converts a quest to a tree.
 *
 * @param mainQuest The main quest to convert.
 */
export function questToTree(mainQuest: MainQuest): RawNodeDatum {
    return {
        name: mainQuest.title,
        attributes: {
            id: mainQuest.id
        },
        children: listSubQuestsFor(mainQuest).map((quest) => {
            return {
                name: quest.id.toString(),
                attributes: {
                    description: quest.description
                },
                children: []
            } as RawNodeDatum;
        })
    };
}
