package emu.grasscutter.server.http.objects;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.utils.FileUtils;
import io.javalin.http.ContentType;
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
            ContentType fromExtension = ContentType.getContentTypeByExtension(path.substring(path.lastIndexOf(".") + 1));
            ctx.contentType(fromExtension != null ? fromExtension : ContentType.APPLICATION_OCTET_STREAM);
            ctx.result(filestream.readAllBytes());
        } catch (Exception e) {
            if (DISPATCH_INFO.logRequests == Grasscutter.ServerDebugMode.MISSING) {
                Grasscutter.getLogger().warn("Webstatic File Missing: " + path);
            }
            ctx.status(404);
        }
    }
}
