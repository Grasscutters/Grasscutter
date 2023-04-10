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
 * Grants an avatar to a player.
 *
 * @param avatar The avatar's ID.
 * @param level The avatar's level.
 * @param constellations The avatar's unlocked constellations.
 * @param talents The level for the avatar's talents.
 */
export async function grantAvatar(
    avatar: number, level = 90,
    constellations = 6, talents = 6
): Promise<CommandResponse> {
    return await fetch(`https://localhost:443/handbook/avatar`, {
        method: "POST", body: JSON.stringify({
            player: targetPlayer.toString(),
            avatar: avatar.toString(),
            level, constellations, talentLevels: talents
        })
    }).then(res => res.json());
}
