package emu.grasscutter.server.http.handlers;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.server.http.objects.HttpJsonResponse;
import emu.grasscutter.server.http.Router;
import emu.grasscutter.utils.FileUtils;
import emu.grasscutter.utils.Utils;
import express.Express;
import express.http.Request;
import express.http.Response;
import io.javalin.Javalin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

import static emu.grasscutter.Configuration.DATA;

/**
 * Handles requests related to the announcements page.
 */
public final class AnnouncementsHandler implements Router {
    private static String template, swjs, vue;
    
    public AnnouncementsHandler() {
        var templateFile = new File(Utils.toFilePath(DATA("/hk4e/announcement/index.html")));
        var swjsFile = new File(Utils.toFilePath(DATA("/hk4e/announcement/sw.js")));
        var vueFile = new File(Utils.toFilePath(DATA("/hk4e/announcement/vue.min.js")));
        
        template = templateFile.exists() ? new String(FileUtils.read(template)) : null;
        swjs = swjsFile.exists() ? new String(FileUtils.read(swjs)) : null;
        vue = vueFile.exists() ? new String(FileUtils.read(vueFile)) : null;
    }
    
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
        express.get("/sw.js", AnnouncementsHandler::getPageResources);
        express.get("/dora/lib/vue/2.6.11/vue.min.js", AnnouncementsHandler::getPageResources);
    }
    
    private static void getAnnouncement(Request request, Response response) {
        if (Objects.equals(request.baseUrl(), "/common/hk4e_global/announcement/api/getAnnContent")) {
            String data = readToString(Paths.get(DATA("GameAnnouncement.json")).toFile());
            response.send("{\"retcode\":0,\"message\":\"OK\",\"data\":" + data + "}");
        } else if (Objects.equals(request.baseUrl(), "/common/hk4e_global/announcement/api/getAnnList")) {
            String data = readToString(Paths.get(DATA("GameAnnouncementList.json")).toFile())
                    .replace("System.currentTimeMillis()", String.valueOf(System.currentTimeMillis()));
            response.send("{\"retcode\":0,\"message\":\"OK\",\"data\": " + data + "}");
        }
    }
    
    private static void getPageResources(Request request, Response response) {
        var path = request.path();
        switch(path) {
            case "/sw.js" -> response.send(swjs);
            case "/hk4e/announcement/index.html" -> response.send(template);
            case "/dora/lib/vue/2.6.11/vue.min.js" -> response.send(vue);
            
            default -> {
                File renderFile = new File(Utils.toFilePath(DATA(path)));
                if(!renderFile.exists()) {
                    Grasscutter.getLogger().info("File not exist: " + path);
                    return;
                }

                String ext = path.substring(path.lastIndexOf(".") + 1);
                if ("css".equals(ext)) {
                    response.type("text/css");
                    response.send(FileUtils.read(renderFile));
                } else {
                    response.send(FileUtils.read(renderFile));
                }
            }
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
