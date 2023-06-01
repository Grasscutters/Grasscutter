import type { CommandResponse } from "@backend/types";
import emitter from "@backend/events";

import { getWindowDetails } from "@app/utils";

let playerToken: string | null = null; // The session token for the player.
export let targetPlayer = 0; // The UID of the target player.

// The server's address and port.
export let address: string = getWindowDetails().address,
    port: string = getWindowDetails().port.toString();
export let encrypted: boolean = true;

export let lockedPlayer = false; // Whether the UID field is locked.
export let connected = false; // Whether the server is connected.

/**
 * Loads the server details from local storage.
 */
export function setup(): void {
    // Check if the server is disabled.
    if (getWindowDetails().disable) return;

    // Load the server details from local storage.
    const storedAddress = localStorage.getItem("address");
    const storedPort = localStorage.getItem("port");

    // Set the server details.
    if (storedAddress) address = storedAddress;
    if (storedPort) port = storedPort;
}

/**
 * Returns the formed URL.
 * This assumes that the server upgrades to HTTPS.
 */
export function url(): string {
    // noinspection HttpUrlsUsage
    return `http${window.isSecureContext || encrypted ? "s" : ""}://${address}:${port}`;
}

/**
 * Sets the target player.
 *
 * @param player The UID of the target player.
 * @param token The session token for the player.
 */
export function setTargetPlayer(player: number, token: string | null = null): void {
    playerToken = token;
    targetPlayer = player;

    // Determine connected status.
    connected = !isNaN(player) && player > 0;
    // Determine locked status.
    lockedPlayer = connected && token != null;

    // Emit the connected event.
    emitter.emit("connected", connected);
}

/**
 * Sets the server details.
 *
 * @param newAddress The server's address.
 * @param newPort The server's port.
 */
export async function setServerDetails(newAddress: string | null, newPort: string | number | null): Promise<void> {
    if (!getWindowDetails().disable) {
        if (typeof newPort == "number") newPort = newPort.toString();

        // Apply the new details.
        if (newAddress != null) {
            address = newAddress;
            localStorage.setItem("address", newAddress);
        }
        if (newPort != null) {
            port = newPort;
            localStorage.setItem("port", newPort);
        }
    }

    // Check if the server is encrypted.
    return new Promise((resolve) => {
        encrypted = true;
        fetch(`${url()}`)
            .catch(() => {
                encrypted = false;
                resolve();
            })
            .then(() => resolve());
    });
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

    return await fetch(`${url()}/handbook/avatar`, {
        method: "POST",
        body: JSON.stringify({
            playerToken,
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

    return await fetch(`${url()}/handbook/item`, {
        method: "POST",
        body: JSON.stringify({
            playerToken,
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

    return await fetch(`${url()}/handbook/teleport`, {
        method: "POST",
        body: JSON.stringify({
            playerToken,
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

    return await fetch(`${url()}/handbook/spawn`, {
        method: "POST",
        body: JSON.stringify({
            playerToken,
            player: targetPlayer.toString(),
            entity: entity.toString(),
            amount,
            level
        })
    }).then((res) => res.json());
}
