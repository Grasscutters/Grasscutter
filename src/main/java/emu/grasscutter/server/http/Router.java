package emu.grasscutter.server.http;

import io.javalin.Javalin;
import io.javalin.http.Handler;

/** Defines routes for an {@link Javalin} instance. */
public interface Router {

    /**
     * Called when the router is initialized by Express.
     *
     * @param javalin A Javalin instance.
     */
    void applyRoutes(Javalin javalin);

    /**
     * Applies this handler to all endpoint types
     *
     * @param javalin A Javalin instance.
     * @param path
     * @param ctx
     * @return The Javalin instance.
     */
    default Javalin allRoutes(Javalin javalin, String path, Handler ctx) {
        javalin.get(path, ctx);
        javalin.post(path, ctx);
        javalin.put(path, ctx);
        javalin.patch(path, ctx);
        javalin.delete(path, ctx);

        return javalin;
    }
}
