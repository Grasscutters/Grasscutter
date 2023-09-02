package emu.grasscutter.server.http.objects;

import static emu.grasscutter.config.Configuration.DISPATCH_INFO;
import static emu.grasscutter.utils.lang.Language.translate;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.Grasscutter.ServerDebugMode;
import emu.grasscutter.utils.Utils;
import io.javalin.http.*;
import java.util.*;
import org.jetbrains.annotations.NotNull;

public final class HttpJsonResponse implements Handler {
    private final String response;
    private final String[]
            missingRoutes = { // TODO: When http requests for theses routes are found please remove it
        // from this list and update the route request type in the DispatchServer
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
        if (DISPATCH_INFO.logRequests == ServerDebugMode.MISSING
                && Arrays.stream(missingRoutes)
                        .anyMatch(x -> Objects.equals(x, ctx.endpointHandlerPath()))) {
            Grasscutter.getLogger()
                    .info(
                            translate(
                                            "messages.dispatch.request",
                                            Utils.address(ctx),
                                            ctx.method(),
                                            ctx.endpointHandlerPath())
                                    + (DISPATCH_INFO.logRequests == ServerDebugMode.MISSING ? "(MISSING)" : ""));
        }
        ctx.result(response);
    }
}
