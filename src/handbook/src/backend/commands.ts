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

export const give = {
    basic: basicGive
};
