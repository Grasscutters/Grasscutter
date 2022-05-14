package emu.grasscutter.server.dispatch;

import emu.grasscutter.Grasscutter;
import express.http.HttpContextHandler;
import express.http.Request;
import express.http.Response;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static emu.grasscutter.Configuration.*;

public final class AnnouncementHandler implements HttpContextHandler {
    @Override
    public void handle(Request request, Response response) throws IOException {
        if (Objects.equals(request.baseUrl(), "/common/hk4e_global/announcement/api/getAnnContent")) {
            String data = readToString(Paths.get(DATA("GameAnnouncement.json")));

            response.send("{\"retcode\":0,\"message\":\"OK\",\"data\":" + data + "}");
        } else if (Objects.equals(request.baseUrl(), "/common/hk4e_global/announcement/api/getAnnList")) {
            String data = readToString(Paths.get(DATA("GameAnnouncementList.json")))
                .replace("System.currentTimeMillis()", String.valueOf(System.currentTimeMillis()));

            response.send("{\"retcode\":0,\"message\":\"OK\",\"data\": " + data + "}");
        }
    }

    private static String readToString(Path path) {
        String content = "";

        try {
            content = Files.readString(path, StandardCharsets.UTF_8);
        } catch (IOException ignored) {
            Grasscutter.getLogger().warn("Unable to open file " + path.toAbsolutePath());
        }
        
        return content;
    }
}