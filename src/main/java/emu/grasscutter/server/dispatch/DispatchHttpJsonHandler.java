package emu.grasscutter.server.dispatch;

import java.io.IOException;
import java.util.Arrays;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.Grasscutter.ServerDebugMode;
import express.http.HttpContextHandler;
import express.http.Request;
import express.http.Response;

public final class DispatchHttpJsonHandler implements HttpContextHandler {
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
	
	public DispatchHttpJsonHandler(String response) {
		this.response = response;
	}

	@Override
	public void handle(Request req, Response res) throws IOException {
		// Checking for ALL here isn't required as when ALL is enabled enableDevLogging() gets enabled
		if(Grasscutter.getConfig().DebugMode == ServerDebugMode.MISSING && Arrays.stream(missingRoutes).anyMatch(x -> x == req.baseUrl())) {
			Grasscutter.getLogger().info(Grasscutter.getLanguage().Client_request.replace("{ip}", req.ip()).replace("{method}", req.method()).replace("{url}", req.baseUrl()) + (Grasscutter.getConfig().DebugMode == ServerDebugMode.MISSING ? "(MISSING)" : ""));
		}
		res.send(response);
	}
}
