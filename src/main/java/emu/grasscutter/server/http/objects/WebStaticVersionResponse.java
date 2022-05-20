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

import java.io.IOException;
import java.io.InputStream;

import static emu.grasscutter.Configuration.DATA;

public class WebStaticVersionResponse implements HttpContextHandler {

    @Override
    public void handle(Request request, Response response) throws IOException {
        if(request.path().contains("version")) {
            getPageResources("/webstatic/version.json", response);
            return;
        } else { // TODO other versions
            getPageResources("/webstatic/en.json", response);
        }
    }

    private static void getPageResources(String path, Response response) {
        try(InputStream filestream = FileUtils.readResourceAsStream(path)) {

            MediaType fromExtension = MediaType.getByExtension(path.substring(path.lastIndexOf(".") + 1));
            response.type((fromExtension != null) ? fromExtension.getMIME() : "application/octet-stream");
            response.send(filestream.readAllBytes());
        } catch (Exception e) {
            Grasscutter.getLogger().warn("File does not exist: " + path);
            response.status(404);
        }
    }
}
