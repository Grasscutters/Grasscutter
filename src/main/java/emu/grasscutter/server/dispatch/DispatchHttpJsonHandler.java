package emu.grasscutter.server.dispatch;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public final class DispatchHttpJsonHandler implements HttpHandler {
	private final String response;
	
	public DispatchHttpJsonHandler(String response) {
		this.response = response;
	}

	@Override
	public void handle(HttpExchange t) throws IOException {
        // Set the response header status and length
        t.getResponseHeaders().put("Content-Type", Collections.singletonList("application/json"));
        t.sendResponseHeaders(200, response.getBytes().length);
        // Write the response string
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
	}
}
