export type Page = "Home" | "Commands";

/**
 * Checks if a string is a page.
 *
 * @param page The string to check.
 */
export function isPage(page: string): page is Page {
    return ["Home", "Commands"].includes(page);
}
