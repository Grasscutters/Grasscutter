package emu.grasscutter.server.http.handlers;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.server.http.objects.HttpJsonResponse;
import emu.grasscutter.server.http.Router;
import emu.grasscutter.utils.FileUtils;
import emu.grasscutter.utils.Utils;
import express.Express;
import express.http.MediaType;
import express.http.Request;
import express.http.Response;
import io.javalin.Javalin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static emu.grasscutter.Configuration.*;

/**
 * Handles requests related to the announcements page.
 */
public final class AnnouncementsHandler implements Router {
    @Override public void applyRoutes(Express express, Javalin handle) {
        // hk4e-api-os.hoyoverse.com
        express.all("/common/hk4e_global/announcement/api/getAlertPic", new HttpJsonResponse("{\"retcode\":0,\"message\":\"OK\",\"data\":{\"total\":0,\"list\":[]}}"));
        // hk4e-api-os.hoyoverse.com
        express.all("/common/hk4e_global/announcement/api/getAlertAnn", new HttpJsonResponse("{\"retcode\":0,\"message\":\"OK\",\"data\":{\"alert\":false,\"alert_id\":0,\"remind\":true}}"));
        // hk4e-api-os.hoyoverse.com
        express.all("/common/hk4e_global/announcement/api/getAnnList", AnnouncementsHandler::getAnnouncement);
        // hk4e-api-os-static.hoyoverse.com
        express.all("/common/hk4e_global/announcement/api/getAnnContent", AnnouncementsHandler::getAnnouncement);
        // hk4e-sdk-os.hoyoverse.com
        express.all("/hk4e_global/mdk/shopwindow/shopwindow/listPriceTier", new HttpJsonResponse("{\"retcode\":0,\"message\":\"OK\",\"data\":{\"suggest_currency\":\"USD\",\"tiers\":[]}}"));

        express.get("/hk4e/announcement/*", AnnouncementsHandler::getPageResources);
    }
    
    private static void getAnnouncement(Request request, Response response) {
        String data = "";
        if (Objects.equals(request.baseUrl(), "/common/hk4e_global/announcement/api/getAnnContent")) {
            data = readToString(new File(Utils.toFilePath(DATA("GameAnnouncement.json"))));
        } else if (Objects.equals(request.baseUrl(), "/common/hk4e_global/announcement/api/getAnnList")) {
            data = readToString(new File(Utils.toFilePath(DATA("GameAnnouncementList.json"))));
        } else {
            response.send("{\"retcode\":404,\"message\":\"Unknown request path\"}");
        }

        if (data.isEmpty()) {
            response.send("{\"retcode\":500,\"message\":\"Unable to fetch requsted content\"}");
            return;
        }

        String dispatchDomain = "http" + (HTTP_ENCRYPTION.useInRouting ? "s" : "") + "://"
                + lr(HTTP_INFO.accessAddress, HTTP_INFO.bindAddress) + ":"
                + lr(HTTP_INFO.accessPort, HTTP_INFO.bindPort);

        data = data
            .replace("{{DISPATCH_PUBLIC}}", dispatchDomain)
            .replace("{{SYSTEM_TIME}}", String.valueOf(System.currentTimeMillis()));
        response.send("{\"retcode\":0,\"message\":\"OK\",\"data\": " + data + "}");
    }
    
    private static void getPageResources(Request request, Response response) {
        String filename = Utils.toFilePath(DATA(request.path()));
        File file = new File(filename);
        if (file.exists() && file.isFile()) {
            MediaType fromExtension = MediaType.getByExtension(filename.substring(filename.lastIndexOf(".") + 1));
            response.type((fromExtension != null) ? fromExtension.getMIME() : "application/octet-stream");
            response.send(FileUtils.read(file));
        } else {
            Grasscutter.getLogger().warn("File does not exist: " + file);
            response.status(404);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static String readToString(File file) {
        byte[] content = new byte[(int) file.length()];
        
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(content); in.close();
        } catch (IOException ignored) {
            Grasscutter.getLogger().warn("File does not exist: " + file);
        }

        return new String(content, StandardCharsets.UTF_8);
    }
}
