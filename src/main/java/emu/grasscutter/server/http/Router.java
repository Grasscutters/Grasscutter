package emu.grasscutter.server.http;

import io.javalin.Javalin;

/**
 * Defines routes for an {@link Javalin} instance.
 */
public interface Router {

    /**
     * Called when the router is initialized by Express.
     * @param javalin A Javalin instance.
     */
    void applyRoutes(Javalin javalin);
}
