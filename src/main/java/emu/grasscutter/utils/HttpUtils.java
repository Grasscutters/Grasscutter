package emu.grasscutter.utils;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public final class HttpUtils {

    public static Javalin allRoutes(Javalin javalin, String path, Handler ctx) {
        javalin.get(path, ctx);
        javalin.post(path, ctx);
        javalin.put(path, ctx);
        javalin.patch(path, ctx);
        javalin.delete(path, ctx);

        return javalin;
    }

}
