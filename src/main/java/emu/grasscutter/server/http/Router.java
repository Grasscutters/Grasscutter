package emu.grasscutter.server.http;

import express.Express;
import io.javalin.Javalin;

/**
 * Defines routes for an {@link Express} instance.
 */
public interface Router {

    /**
     * Called when the router is initialized by Express.
     *
     * @param express An Express instance.
     */
    void applyRoutes(Express express, Javalin handle);
}
