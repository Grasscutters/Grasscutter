package io.grasscutter.tests;

import com.mchange.util.AssertException;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.config.Configuration;
import io.grasscutter.GrasscutterTest;
import io.grasscutter.virtual.*;
import lombok.*;
import okhttp3.*;
import org.junit.jupiter.api.*;

import java.io.IOException;

/** Testing entrypoint for {@link Grasscutter}. */
public final class BaseServerTest {
    @Getter private static int httpPort = -1;
    @Getter private static int gamePort = -1;

    /**
     * Creates an HTTP URL.
     *
     * @param route The route to use.
     * @return The URL.
     */
    public static String http(String route) {
        return "http://127.0.0.1:" + BaseServerTest.httpPort + "/" + route;
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
        BaseServerTest.httpPort = Configuration.SERVER.http.bindPort;
        BaseServerTest.gamePort = Configuration.SERVER.game.bindPort;

        // Create virtual instances.
        GrasscutterTest.account = new VirtualAccount();
        GrasscutterTest.gameSession = new VirtualGameSession();
    }

    @Test
    @DisplayName("HTTP server check")
    public void checkHttpServer() {
        // Create a request.
        var request = new Request.Builder().url(BaseServerTest.http("")).build();

        // Perform the request.
        try (var response = GrasscutterTest.httpClient.newCall(request).execute()) {
            // Check the response.
            Assertions.assertTrue(response.isSuccessful());
        } catch (IOException exception) {
            throw new AssertionError(exception);
        }
    }
}
