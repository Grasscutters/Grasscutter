package emu.grasscutter.server.http.handlers;

import emu.grasscutter.server.http.Router;
import express.Express;
import io.javalin.Javalin;

/**
 * Handles the legacy authentication system routes.
 */
public final class LegacyAuthHandler implements Router {
    @Override public void applyRoutes(Express express, Javalin handle) {
        express.get("/authentication/type", (request, response) -> response.status(500).send("{\"notice\":\"This API is deprecated.\"}"));
        express.post("/authentication/login", (request, response) -> response.status(500).send("{\"notice\":\"This API is deprecated.\"}"));
        express.post("/authentication/register", (request, response) -> response.status(500).send("{\"notice\":\"This API is deprecated.\"}"));
        express.post("/authentication/change_password", (request, response) -> response.status(500).send("{\"notice\":\"This API is deprecated.\"}"));
    }
}