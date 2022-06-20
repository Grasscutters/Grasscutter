package emu.grasscutter.server.http.handlers;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.DataLoader;
import emu.grasscutter.server.http.Router;
import emu.grasscutter.server.http.objects.HttpJsonResponse;
import emu.grasscutter.utils.FileUtils;
import emu.grasscutter.utils.Utils;
import express.Express;
import express.http.MediaType;
import express.http.Request;
import express.http.Response;
import io.javalin.Javalin;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static emu.grasscutter.Configuration.*;

/**
 * Handles requests related to the announcements page.
 */
public final class AnnouncementsHandler implements Router {
    @Override
    public void applyRoutes(Express express, Javalin handle) {
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
            try {
                data = FileUtils.readToString(DataLoader.load("GameAnnouncement.json"));
            } catch (Exception e) {
                if (e.getClass() == IOException.class) {
                    Grasscutter.getLogger().info("Unable to read file 'GameAnnouncementList.json'. \n" + e);
                }
            }
        } else if (Objects.equals(request.baseUrl(), "/common/hk4e_global/announcement/api/getAnnList")) {
            try {
                data = FileUtils.readToString(DataLoader.load("GameAnnouncementList.json"));
            } catch (Exception e) {
                if (e.getClass() == IOException.class) {
                    Grasscutter.getLogger().info("Unable to read file 'GameAnnouncementList.json'. \n" + e);
                }
            }
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
        try (InputStream filestream = DataLoader.load(request.path())) {
            String possibleFilename = Utils.toFilePath(DATA(request.path()));

            MediaType fromExtension = MediaType.getByExtension(possibleFilename.substring(possibleFilename.lastIndexOf(".") + 1));
            response.type((fromExtension != null) ? fromExtension.getMIME() : "application/octet-stream");
            response.send(filestream.readAllBytes());
        } catch (Exception e) {
            Grasscutter.getLogger().warn("File does not exist: " + request.path());
            response.status(404);
        }
    }
}
