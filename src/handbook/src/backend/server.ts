import type { CommandResponse } from "@backend/types";
import emitter from "@backend/events";

let targetPlayer = 0; // The UID of the target player.
export let connected = false; // Whether the server is connected.

/**
 * Sets the target player.
 *
 * @param player The UID of the target player.
 */
export function setTargetPlayer(player: number): void {
    targetPlayer = player;
    connected = !isNaN(player) && player > 0;

    // Emit the connected event.
    emitter.emit("connected", connected);
}

/**
 * Validates a number.
 *
 * @param value The number to validate.
 */
function invalid(value: number): boolean {
    return isNaN(value) || value < 0;
}

/**
 * Grants an avatar to a player.
 *
 * @param avatar The avatar's ID.
 * @param level The avatar's level.
 * @param constellations The avatar's unlocked constellations.
 * @param talents The level for the avatar's talents.
 */
export async function grantAvatar(
    avatar: number,
    level = 90,
    constellations = 6,
    talents = 10
): Promise<CommandResponse> {
    // Validate the numbers.
    if (invalid(avatar) || invalid(level) || invalid(constellations) || invalid(talents))
        return { status: -1, message: "Invalid arguments." };

    return await fetch(`https://localhost:443/handbook/avatar`, {
        method: "POST",
        body: JSON.stringify({
            player: targetPlayer.toString(),
            avatar: avatar.toString(),
            level,
            constellations,
            talentLevels: talents
        })
    }).then((res) => res.json());
}

/**
 * Gives an item to the player.
 * This does not support weapons.
 * This does not support relics.
 *
 * @param item The item's ID.
 * @param amount The amount of the item to give.
 */
export async function giveItem(item: number, amount = 1): Promise<CommandResponse> {
    // Validate the number.
    if (isNaN(amount) || amount < 1) return { status: -1, message: "Invalid amount." };

    return await fetch(`https://localhost:443/handbook/item`, {
        method: "POST",
        body: JSON.stringify({
            player: targetPlayer.toString(),
            item: item.toString(),
            amount
        })
    }).then((res) => res.json());
}

/**
 * Teleports the player to a new scene.
 *
 * @param scene The scene's ID.
 */
export async function teleportTo(scene: number): Promise<CommandResponse> {
    // Validate the number.
    if (isNaN(scene) || scene < 1) return { status: -1, message: "Invalid scene." };

    return await fetch(`https://localhost:443/handbook/teleport`, {
        method: "POST",
        body: JSON.stringify({
            player: targetPlayer.toString(),
            scene: scene.toString()
        })
    }).then((res) => res.json());
}

/**
 * Spawns an entity.
 *
 * @param entity The entity's ID.
 * @param amount The amount of the entity to spawn.
 * @param level The level of the entity to spawn.
 */
export async function spawnEntity(entity: number, amount = 1, level = 1): Promise<CommandResponse> {
    // Validate the numbers.
    if (isNaN(entity) || isNaN(amount) || isNaN(level) || amount < 1 || level < 1 || level > 200)
        return { status: -1, message: "Invalid arguments." };

    return await fetch(`https://localhost:443/handbook/spawn`, {
        method: "POST",
        body: JSON.stringify({
            player: targetPlayer.toString(),
            entity: entity.toString(),
            amount,
            level
        })
    }).then((res) => res.json());
}
