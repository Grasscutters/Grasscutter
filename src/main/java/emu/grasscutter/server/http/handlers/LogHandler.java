package emu.grasscutter.server.http.handlers;

import emu.grasscutter.server.dispatch.ClientLogHandler;
import emu.grasscutter.server.http.Router;
import express.Express;
import io.javalin.Javalin;

/**
 * Handles logging requests made to the server.
 */
public final class LogHandler implements Router {
    @Override public void applyRoutes(Express express, Javalin handle) {
        // overseauspider.yuanshen.com
        express.post("/log", new ClientLogHandler());
        // log-upload-os.mihoyo.com
        express.post("/crash/dataUpload", new ClientLogHandler());
    }
}
