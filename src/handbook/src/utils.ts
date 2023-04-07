import { Quality } from "@backend/types";

/**
 * Fetches the name of the CSS variable for the quality.
 *
 * @param quality The quality of the item.
 */
export function colorFor(quality: Quality): string {
    switch (quality) {
        default: return "--legendary-color";
        case "EPIC": return "--epic-color";
        case "RARE": return "--rare-color";
        case "UNCOMMON": return "--uncommon-color";
        case "COMMON": return "--common-color";
        case "UNKNOWN": return "--unknown-color";
    }
}
