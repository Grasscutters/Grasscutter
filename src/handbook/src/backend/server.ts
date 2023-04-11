import type { CommandResponse } from "@backend/types";

let targetPlayer = 0; // The UID of the target player.

/**
 * Sets the target player.
 *
 * @param player The UID of the target player.
 */
export function setTargetPlayer(player: number): void {
    targetPlayer = player;
    console.log(`Target Player is now: ${targetPlayer}`);
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
    talents = 6
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
