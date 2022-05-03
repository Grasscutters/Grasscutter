package emu.grasscutter.server.http.gcstatic;

import java.io.File;
import java.io.IOException;

import emu.grasscutter.Grasscutter;
import express.http.HttpContextHandler;
import express.http.Request;
import express.http.Response;

public final class StaticFileHandler implements HttpContextHandler {
	String static_folder;
	public StaticFileHandler() {
        static_folder = Grasscutter.getConfig().RESOURCE_FOLDER + "/gcstatic";
	}

	@Override
	public void handle(Request req, Response res) throws IOException {
        // Grasscutter.getLogger().info( req.path());

        String reqFilename = req.path().replace("/gcstatic", ""); // remove the leading path
        reqFilename = reqFilename.replace("/../", "/./"); // security guard to prevent arbitrary read
        File resFile = new File(static_folder + reqFilename);
        if (resFile.exists()) {
            res.sendFile(resFile.toPath());
        } else {
            res.status(404);
            res.send("404");
        }
    }
}
