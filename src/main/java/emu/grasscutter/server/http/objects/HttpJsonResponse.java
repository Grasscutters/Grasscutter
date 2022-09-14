package emu.grasscutter.server.http.objects;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.Grasscutter.ServerDebugMode;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import static emu.grasscutter.config.Configuration.*;
import static emu.grasscutter.utils.Language.translate;

public final class HttpJsonResponse implements Handler {
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
    public void handle(@NotNull Context ctx) throws Exception {
        // Checking for ALL here isn't required as when ALL is enabled enableDevLogging() gets enabled
        if (DISPATCH_INFO.logRequests == ServerDebugMode.MISSING && Arrays.stream(missingRoutes).anyMatch(x -> Objects.equals(x, ctx.endpointHandlerPath()))) {
            Grasscutter.getLogger().info(translate("messages.dispatch.request", ctx.ip(), ctx.method(), ctx.endpointHandlerPath()) + (DISPATCH_INFO.logRequests == ServerDebugMode.MISSING ? "(MISSING)" : ""));
        }
        ctx.result(response);
    }
}
