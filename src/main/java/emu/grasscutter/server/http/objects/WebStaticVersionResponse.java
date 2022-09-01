package emu.grasscutter.server.http.objects;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.utils.FileUtils;
import emu.grasscutter.utils.HttpUtils;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import static emu.grasscutter.config.Configuration.DISPATCH_INFO;

import java.io.IOException;
import java.io.InputStream;

public class WebStaticVersionResponse implements Handler {

    @Override
    public void handle(Context ctx) throws IOException {
        String requestFor = ctx.path().substring(ctx.path().lastIndexOf("-") + 1);

        getPageResources("/webstatic/" + requestFor, ctx);
        return;
    }

    private static void getPageResources(String path, Context ctx) {
        try (InputStream filestream = FileUtils.readResourceAsStream(path)) {

            HttpUtils.MediaType fromExtension = HttpUtils.MediaType.getByExtension(path.substring(path.lastIndexOf(".") + 1));
            ctx.contentType((fromExtension != null) ? fromExtension.getMIME() : "application/octet-stream");
            ctx.result(filestream.readAllBytes());
        } catch (Exception e) {
            if (DISPATCH_INFO.logRequests == Grasscutter.ServerDebugMode.MISSING) {
                Grasscutter.getLogger().warn("Webstatic File Missing: " + path);
            }
            ctx.status(404);
        }
    }
}
