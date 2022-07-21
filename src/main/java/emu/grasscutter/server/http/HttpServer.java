package emu.grasscutter.server.http;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.Grasscutter.ServerDebugMode;
import emu.grasscutter.utils.FileUtils;
import express.Express;
import express.http.MediaType;
import io.javalin.Javalin;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import java.io.File;
import java.io.UnsupportedEncodingException;

import static emu.grasscutter.config.Configuration.*;
import static emu.grasscutter.utils.Language.translate;

/**
 * Manages all HTTP-related classes.
 * (including dispatch, announcements, gacha, etc.)
 */
public final class HttpServer {
    private final Express express;

    /**
     * Configures the Express application.
     */
    public HttpServer() {
        this.express = new Express(config -> {
            // Set the Express HTTP server.
            config.server(HttpServer::createServer);

            // Configure encryption/HTTPS/SSL.
            config.enforceSsl = HTTP_ENCRYPTION.useEncryption;

            // Configure HTTP policies.
            if (HTTP_POLICIES.cors.enabled) {
                var allowedOrigins = HTTP_POLICIES.cors.allowedOrigins;
                if (allowedOrigins.length > 0)
                    config.enableCorsForOrigin(allowedOrigins);
                else config.enableCorsForAllOrigins();
            }

            // Configure debug logging.
            if (DISPATCH_INFO.logRequests == ServerDebugMode.ALL)
                config.enableDevLogging();

            // Disable compression on static files.
            config.precompressStaticFiles = false;
        });
    }

    /**
     * Creates an HTTP(S) server.
     * @return A server instance.
     */
    @SuppressWarnings("resource")
    private static Server createServer() {
        Server server = new Server();
        ServerConnector serverConnector
                = new ServerConnector(server);

        if (HTTP_ENCRYPTION.useEncryption) {
            var sslContextFactory = new SslContextFactory.Server();
            var keystoreFile = new File(HTTP_ENCRYPTION.keystore);

            if (!keystoreFile.exists()) {
                HTTP_ENCRYPTION.useEncryption = false;
                HTTP_ENCRYPTION.useInRouting = false;

                Grasscutter.getLogger().warn(translate("messages.dispatch.keystore.no_keystore_error"));
            } else try {
                sslContextFactory.setKeyStorePath(keystoreFile.getPath());
                sslContextFactory.setKeyStorePassword(HTTP_ENCRYPTION.keystorePassword);
            } catch (Exception ignored) {
                Grasscutter.getLogger().warn(translate("messages.dispatch.keystore.password_error"));

                try {
                    sslContextFactory.setKeyStorePath(keystoreFile.getPath());
                    sslContextFactory.setKeyStorePassword("123456");

                    Grasscutter.getLogger().warn(translate("messages.dispatch.keystore.default_password"));
                } catch (Exception exception) {
                    Grasscutter.getLogger().warn(translate("messages.dispatch.keystore.general_error"), exception);
                }
            } finally {
                serverConnector = new ServerConnector(server, sslContextFactory);
            }
        }

        serverConnector.setPort(HTTP_INFO.bindPort);
        server.setConnectors(new ServerConnector[]{serverConnector});

        return server;
    }

    /**
     * Returns the handle for the Express application.
     * @return A Javalin instance.
     */
    public Javalin getHandle() {
        return this.express.raw();
    }

    /**
     * Initializes the provided class.
     * @param router The router class.
     * @return Method chaining.
     */
    @SuppressWarnings("UnusedReturnValue")
    public HttpServer addRouter(Class<? extends Router> router, Object... args) {
        // Get all constructor parameters.
        Class<?>[] types = new Class<?>[args.length];
        for (var argument : args)
            types[args.length - 1] = argument.getClass();

        try { // Create a router instance & apply routes.
            var constructor = router.getDeclaredConstructor(types); // Get the constructor.
            var routerInstance = constructor.newInstance(args); // Create instance.
            routerInstance.applyRoutes(this.express, this.getHandle()); // Apply routes.
        } catch (Exception exception) {
            Grasscutter.getLogger().warn(translate("messages.dispatch.router_error"), exception);
        } return this;
    }

    /**
     * Starts listening on the HTTP server.
     * @throws UnsupportedEncodingException
     */
    public void start() throws UnsupportedEncodingException {
        // Attempt to start the HTTP server.
        if (HTTP_INFO.bindAddress.equals("")) {
            this.express.listen(HTTP_INFO.bindPort);
        }else {
            this.express.listen(HTTP_INFO.bindAddress, HTTP_INFO.bindPort);
        }

        // Log bind information.
        Grasscutter.getLogger().info(translate("messages.dispatch.port_bind", Integer.toString(this.express.raw().port())));
    }

    /**
     * Handles the '/' (index) endpoint on the Express application.
     */
    public static class DefaultRequestRouter implements Router {
        @Override public void applyRoutes(Express express, Javalin handle) {
            express.get("/", (request, response) -> {
                File file = new File(HTTP_STATIC_FILES.indexFile);
                if (!file.exists())
                    response.send("""
                            <!DOCTYPE html>
                            <html>
                                <head>
                                    <meta charset="utf8">
                                </head>
                                <body>%s</body>
                            </html>
                            """.formatted(translate("messages.status.welcome")));
                else {
                    final var filePath = file.getPath();
                    final MediaType fromExtension = MediaType.getByExtension(filePath.substring(filePath.lastIndexOf(".") + 1));
                    response.type((fromExtension != null) ? fromExtension.getMIME() : "text/plain")
                            .send(FileUtils.read(filePath));
                }
            });
        }
    }

    /**
     * Handles unhandled endpoints on the Express application.
     */
    public static class UnhandledRequestRouter implements Router {
        @Override public void applyRoutes(Express express, Javalin handle) {
            handle.error(404, context -> {
                if (DISPATCH_INFO.logRequests == ServerDebugMode.MISSING)
                    Grasscutter.getLogger().info(translate("messages.dispatch.unhandled_request_error", context.method(), context.url()));
                context.contentType("text/html");

                File file = new File(HTTP_STATIC_FILES.errorFile);
                if (!file.exists())
                    context.result("""
                        <!DOCTYPE html>
                        <html>
                            <head>
                                <meta charset="utf8">
                            </head>

                            <body>
                                <img src="https://http.cat/404" />
                            </body>
                        </html>
                        """);
                else {
                    final var filePath = file.getPath();
                    final MediaType fromExtension = MediaType.getByExtension(filePath.substring(filePath.lastIndexOf(".") + 1));
                    context.contentType((fromExtension != null) ? fromExtension.getMIME() : "text/plain")
                            .result(FileUtils.read(filePath));
                }
            });
        }
    }
}
