package emu.grasscutter.server.http.objects;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.DataLoader;
import emu.grasscutter.utils.FileUtils;
import emu.grasscutter.utils.Utils;
import express.http.HttpContextHandler;
import express.http.MediaType;
import express.http.Request;
import express.http.Response;
import io.javalin.core.util.FileUtil;

import static emu.grasscutter.config.Configuration.DATA;
import static emu.grasscutter.config.Configuration.DISPATCH_INFO;

import java.io.IOException;
import java.io.InputStream;

public class WebStaticVersionResponse implements HttpContextHandler {

    @Override
    public void handle(Request request, Response response) throws IOException {
        String requestFor = request.path().substring(request.path().lastIndexOf("-") + 1);

        getPageResources("/webstatic/" + requestFor, response);
        return;
    }

    private static void getPageResources(String path, Response response) {
        try (InputStream filestream = FileUtils.readResourceAsStream(path)) {

            MediaType fromExtension = MediaType.getByExtension(path.substring(path.lastIndexOf(".") + 1));
            response.type((fromExtension != null) ? fromExtension.getMIME() : "application/octet-stream");
            response.send(filestream.readAllBytes());
        } catch (Exception e) {
            if (DISPATCH_INFO.logRequests == Grasscutter.ServerDebugMode.MISSING) {
                Grasscutter.getLogger().warn("Webstatic File Missing: " + path);
            }
            response.status(404);
        }
    }
}
