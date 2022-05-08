package emu.grasscutter.server.dispatch;

import emu.grasscutter.Grasscutter;
import express.http.HttpContextHandler;
import express.http.Request;
import express.http.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

public final class AnnouncementHandler implements HttpContextHandler {
    @Override
    public void handle(Request request, Response response) throws IOException {//event
        if (Objects.equals(request.baseUrl(), "/common/hk4e_global/announcement/api/getAnnContent")) {
            response.send("{\"retcode\":0,\"message\":\"OK\",\"data\":" + readToString(new File(Grasscutter.getConfig().DATA_FOLDER + "GameAnnouncement.json")) +"}");
        } else if (Objects.equals(request.baseUrl(), "/common/hk4e_global/announcement/api/getAnnList")) {
            String data = readToString(new File(Grasscutter.getConfig().DATA_FOLDER + "GameAnnouncementList.json")).replace("System.currentTimeMillis()",String.valueOf(System.currentTimeMillis()));
            response.send("{\"retcode\":0,\"message\":\"OK\",\"data\": "+data +"}");
        } else if (Objects.equals(request.baseUrl(), "/hk4e/announcement/:page")) {
            File file = new File(Grasscutter.getConfig().ANNOUNCEMENT_FOLDER);
            String[] strFilesName = file.list();
            String page = request.params("page");
            assert strFilesName != null;
            if (Arrays.asList(strFilesName).contains(page)) {
                String data = readToString(new File(Grasscutter.getConfig().ANNOUNCEMENT_FOLDER + page)).replace("System.currentTimeMillis()",String.valueOf(System.currentTimeMillis()));
                String[] name = page.split("\\.");
                String type = name[name.length - 1 ];
                response.sendFile(Paths.get( Grasscutter.getConfig().ANNOUNCEMENT_FOLDER + page));
            } else {
                response.set("Content-Type", "text/html; charset=utf-8");
                response.send("<!doctype html><html lang=\"en\"><body><img src=\"https://http.cat/404\" /></body></html>");
            }
        }
    }
    private static String readToString(File file) {
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return new String(filecontent);
    }
}