package emu.grasscutter.server.http.handlers;

import emu.grasscutter.server.http.Router;
import io.javalin.Javalin;
import io.javalin.http.Context;

/** Handles logging requests made to the server. */
public final class LogHandler implements Router {
    private static void log(Context ctx) {
        // TODO: Figure out how to dump request body and log to file.
        ctx.result("{\"code\":0}");
    }

    @Override
    public void applyRoutes(Javalin javalin) {
        // overseauspider.yuanshen.com
        javalin.post("/log", LogHandler::log);
        // log-upload-os.mihoyo.com
        javalin.post("/crash/dataUpload", LogHandler::log);
    }
}
