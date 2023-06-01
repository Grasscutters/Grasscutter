/**
 * Validates a number.
 *
 * @param value The number to validate.
 */
function invalid(value: number): boolean {
    return isNaN(value) || value < 0;
}

/**
 * Generates a basic give command.
 */
function basicGive(item: number, amount = 1): string {
    // Validate the numbers.
    if (invalid(item) || invalid(amount)) return "Invalid arguments.";

    return `/give ${item} x${amount}`;
}

/**
 * Generates a basic teleport command.
 * This creates a relative teleport command.
 */
function teleport(scene: number): string {
    // Validate the number.
    if (invalid(scene)) return "Invalid arguments.";

    return `/teleport ~ ~ ~ ${scene}`;
}

/**
 * Generates a basic spawn monster command.
 *
 * @param monster The monster's ID.
 * @param amount The amount of the monster to spawn.
 * @param level The level of the monster to spawn.
 */
function spawnMonster(monster: number, amount = 1, level = 1): string {
    // Validate the numbers.
    if (invalid(monster) || invalid(amount)) return "Invalid arguments.";

    return `/spawn ${monster} x${amount} lv${level}`;
}

export const give = {
    basic: basicGive
};

export const spawn = {
    monster: spawnMonster
};

export const action = {
    teleport: teleport
};
