package emu.grasscutter.server.dispatch;

import emu.grasscutter.Grasscutter;
import express.http.HttpContextHandler;
import express.http.Request;
import express.http.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

import static emu.grasscutter.Configuration.*;

public final class AnnouncementHandler implements HttpContextHandler {
    @Override
    public void handle(Request request, Response response) throws IOException {//event
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