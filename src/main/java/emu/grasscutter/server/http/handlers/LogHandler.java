package emu.grasscutter.server.http.handlers;

import emu.grasscutter.server.http.Router;
import express.Express;
import express.http.Request;
import express.http.Response;
import io.javalin.Javalin;

/**
 * Handles logging requests made to the server.
 */
public final class LogHandler implements Router {
    @Override
    public void applyRoutes(Express express, Javalin handle) {
        // overseauspider.yuanshen.com
        express.post("/log", LogHandler::log);
        // log-upload-os.mihoyo.com
        express.post("/crash/dataUpload", LogHandler::log);
    }

    private static void log(Request request, Response response) {
        // TODO: Figure out how to dump request body and log to file.
        response.send("{\"code\":0}");
    }
}
