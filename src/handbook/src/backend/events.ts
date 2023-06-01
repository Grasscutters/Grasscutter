import { EventEmitter } from "events";

import type { Page } from "@backend/types";
import { isPage } from "@backend/types";

const emitter = new EventEmitter();
const navigation = new EventEmitter();

let navStack: Page[] = [];
let currentPage: number | null = -1;

/**
 * Sets up the event system.
 */
export function setup(): void {
    window.onpopstate = (event) => {
        navigate(event.state, false);
    };

    setTimeout(() => {
        // Check if the window's href is a page.
        const page = window.location.href.split("/").pop();
        if (page == undefined || page == "") return;

        // Convert the page to a Page type.
        const pageName = page.charAt(0).toUpperCase() + page.slice(1);
        const pageType = pageName as Page;

        // Navigate to the page.
        isPage(page) && navigate(pageType, false);
    }, 3e2);
}

/**
 * Adds a navigation listener.
 *
 * @param listener The listener to add.
 */
export function addNavListener(listener: (page: Page) => void) {
    navigation.on("navigate", listener);
}

/**
 * Removes a navigation listener.
 *
 * @param listener The listener to remove.
 */
export function removeNavListener(listener: (page: Page) => void) {
    navigation.off("navigate", listener);
}

/**
 * Navigates to a page.
 * Returns the last page.
 *
 * @param page The page to navigate to.
 * @param update Whether to update the state or not.
 */
export function navigate(page: Page, update: boolean = true): Page | null {
    // Check the page.
    if (page == undefined) page = "Home";

    // Navigate to the new page.
    const lastPage = currentPage;
    navigation.emit("navigate", page);

    if (update) {
        // Set the current page.
        navStack.push(page);
        currentPage = navStack.length - 1;
        // Add the page to the window history.
        window.history.pushState(page, page, "/" + page.toLowerCase());
    }

    return lastPage ? navStack[lastPage] : null;
}

/**
 * Goes back or forward in the navigation stack.
 *
 * @param forward Whether to go forward or not.
 */
export function go(forward: boolean): void {
    if (currentPage == undefined) return;

    // Get the new page.
    const newPage = forward ? currentPage + 1 : currentPage - 1;
    if (newPage < 0 || newPage >= navStack.length) return;

    // Navigate to the new page.
    currentPage = newPage;
    navigation.emit("navigate", navStack[newPage]);

    // Update the window history.
    window.history.pushState(navStack[newPage], navStack[newPage], "/" + navStack[newPage].toLowerCase());
}

// This is the global event system.
export default emitter;
// @ts-ignore
window["emitter"] = emitter;
// @ts-ignore
window["navigate"] = navigate;
