package io.grasscutter;

import com.mchange.util.AssertException;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.config.Configuration;
import java.io.IOException;
import lombok.Getter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Testing entrypoint for {@link Grasscutter}. */
public final class GrasscutterTest {
    @Getter private static final OkHttpClient httpClient = new OkHttpClient();

    @Getter private static int httpPort = -1;
    @Getter private static int gamePort = -1;

    /**
     * Creates an HTTP URL.
     *
     * @param route The route to use.
     * @return The URL.
     */
    public static String http(String route) {
        return "http://127.0.0.1:" + GrasscutterTest.httpPort + "/" + route;
    }

    @BeforeAll
    public static void entry() {
        try {
            // Start Grasscutter.
            Grasscutter.main(new String[] {"-test"});
        } catch (Exception ignored) {
            throw new AssertException("Grasscutter failed to start.");
        }

        // Set the ports.
        GrasscutterTest.httpPort = Configuration.SERVER.http.bindPort;
        GrasscutterTest.gamePort = Configuration.SERVER.game.bindPort;
    }

    @Test
    @DisplayName("HTTP server check")
    public void checkHttpServer() {
        // Create a request.
        var request = new Request.Builder().url(GrasscutterTest.http("")).build();

        // Perform the request.
        try (var response = GrasscutterTest.httpClient.newCall(request).execute()) {
            // Check the response.
            Assertions.assertTrue(response.isSuccessful());
        } catch (IOException exception) {
            throw new AssertionError(exception);
        }
    }
}
