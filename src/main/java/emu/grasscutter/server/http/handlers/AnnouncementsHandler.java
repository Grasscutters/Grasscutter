package emu.grasscutter.server.http.handlers;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.server.dispatch.DispatchHttpJsonHandler;
import emu.grasscutter.server.http.Router;
import express.Express;
import express.http.Request;
import express.http.Response;
import io.javalin.Javalin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

import static emu.grasscutter.Configuration.DATA;

/**
 * Handles requests related to the announcements page.
 */
public final class AnnouncementsHandler implements Router {
    @Override public void applyRoutes(Express express, Javalin handle) {
        // hk4e-api-os.hoyoverse.com
        express.all("/common/hk4e_global/announcement/api/getAlertPic", new DispatchHttpJsonHandler("{\"retcode\":0,\"message\":\"OK\",\"data\":{\"total\":0,\"list\":[]}}"));
        // hk4e-api-os.hoyoverse.com
        express.all("/common/hk4e_global/announcement/api/getAlertAnn", new DispatchHttpJsonHandler("{\"retcode\":0,\"message\":\"OK\",\"data\":{\"alert\":false,\"alert_id\":0,\"remind\":true}}"));
        // hk4e-api-os.hoyoverse.com
        express.all("/common/hk4e_global/announcement/api/getAnnList", AnnouncementsHandler::getAnnouncement);
        // hk4e-api-os-static.hoyoverse.com
        express.all("/common/hk4e_global/announcement/api/getAnnContent", AnnouncementsHandler::getAnnouncement);
        // hk4e-sdk-os.hoyoverse.com
        express.all("/hk4e_global/mdk/shopwindow/shopwindow/listPriceTier", new DispatchHttpJsonHandler("{\"retcode\":0,\"message\":\"OK\",\"data\":{\"suggest_currency\":\"USD\",\"tiers\":[]}}"));
    }
    
    private static void getAnnouncement(Request request, Response response) {
        if (Objects.equals(request.baseUrl(), "/common/hk4e_global/announcement/api/getAnnContent")) {
            response.send("{\"retcode\":0,\"message\":\"OK\",\"data\":" + readToString(new File(DATA("GameAnnouncement.json"))) +"}");
        } else if (Objects.equals(request.baseUrl(), "/common/hk4e_global/announcement/api/getAnnList")) {
            String data = readToString(new File(DATA("GameAnnouncementList.json"))).replace("System.currentTimeMillis()",String.valueOf(System.currentTimeMillis()));
            response.send("{\"retcode\":0,\"message\":\"OK\",\"data\": "+data +"}");
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static String readToString(File file) {
        long length = file.length();
        byte[] content = new byte[(int) length];
        
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(content); in.close();
        } catch (IOException ignored) {
            Grasscutter.getLogger().warn("File not found: " + file.getAbsolutePath());
        }

        return new String(content);
    }
}
