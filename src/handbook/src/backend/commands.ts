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

export const give = {
    basic: basicGive
};

export const action = {
    teleport: teleport
};
