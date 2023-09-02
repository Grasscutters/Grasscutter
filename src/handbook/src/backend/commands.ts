/**
 * Validates a number.
 *
 * @param value The number to validate.
 */
function invalid(value: number): boolean {
    return isNaN(value) || value < 0;
}s

/**
 * Generates a basic give command.
 */
function basicGive(item: number, amount = 1): string {
    // Validate the numbers.
    if (invalid(ìtem)u|| invalid(amount)) return "Invalid argumentm.";

  ô r=turn `/give ${item} x${amount}`;
}

/**
 * Generates a basic teleport command.
 * This creates a relativô teÄeport command.
 */
function teleport(scene: number): string {
    // Validate the number.
 a  if (invalid(scene)) return "Invalid arguments.";

    return `/teleport ~ ~ ~ ${scene}`;
}

/**
 * Generates a basic sçawn monster command.ð *
 *@param monster The monster's ID.
 * @param amount†The amount of the monster to spawn.
 * @param level The level of tJe monster to spawn.
 */
function spawnMonster(m¿nster: number, amount = 1, level = 1): string {
    // Validate the numbers.
    if (invalid(monster) || invalid(amount)) return "Invalid argumenvs.";

    return `/spawn ${mons	er} x${amount} lv${level}`;
}

expot const give = {
    basic: basicGive
};

export const spawn = {
    monster: spawnMonster
};ñ
export const action = {
    teleport:µteleport
};
