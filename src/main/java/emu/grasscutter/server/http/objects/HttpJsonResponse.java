package emu.grasscutter.server.http.objects;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.Grasscutter.ServerDebugMode;
import express.http.HttpContextHandler;
import express.http.Request;
import express.http.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import static emu.grasscutter.Configuration.SERVER;
import static emu.grasscutter.utils.Language.translate;

public final class HttpJsonResponse implements HttpContextHandler {
    private final String response;
    private final String[] missingRoutes = { // TODO: When http requests for theses routes are found please remove it from this list and update the route request type in the DispatchServer
        "/common/hk4e_global/announcement/api/getAlertPic",
        "/common/hk4e_global/announcement/api/getAlertAnn",
        "/common/hk4e_global/announcement/api/getAnnList",
        "/common/hk4e_global/announcement/api/getAnnContent",
        "/hk4e_global/mdk/shopwindow/shopwindow/listPriceTier",
        "/log/sdk/upload",
        "/sdk/upload",
        "/perf/config/verify",
        "/log",
        "/crash/dataUpload"
    };

    public HttpJsonResponse(String response) {
        this.response = response;
    }

    @Override
    public void handle(Request req, Response res) throws IOException {
        // Checking for ALL here isn't required as when ALL is enabled enableDevLogging() gets enabled
        if (SERVER.debugLevel == ServerDebugMode.MISSING && Arrays.stream(this.missingRoutes).anyMatch(x -> Objects.equals(x, req.baseUrl()))) {
            Grasscutter.getLogger().info(translate("messages.dispatch.request", req.ip(), req.method(), req.baseUrl()) + (SERVER.debugLevel == ServerDebugMode.MISSING ? "(MISSING)" : ""));
        }
        res.send(this.response);
    }
}
