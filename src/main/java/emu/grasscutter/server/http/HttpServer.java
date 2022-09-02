package emu.grasscutter.server.http;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.Grasscutter.ServerDebugMode;
import emu.grasscutter.utils.FileUtils;
import io.javalin.Javalin;
import io.javalin.core.util.JavalinLogger;
import io.javalin.http.ContentType;

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
    private final Javalin javalin;

    /**
     * Configures the Javalin application.
     */
    public HttpServer() {
        this.javalin = Javalin.create(config -> {
            // Set the Javalin HTTP server.
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

            // Static files should be added like this https://javalin.io/documentation#static-files
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
        return this.javalin;
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
            routerInstance.applyRoutes(this.javalin); // Apply routes.
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
            this.javalin.start(HTTP_INFO.bindPort);
        }else {
            this.javalin.start(HTTP_INFO.bindAddress, HTTP_INFO.bindPort);
        }

        // Log bind information.
        Grasscutter.getLogger().info(translate("messages.dispatch.address_bind", HTTP_INFO.accessAddress, this.javalin.port()));
    }

    /**
     * Handles the '/' (index) endpoint on the Express application.
     */
    public static class DefaultRequestRouter implements Router {
        @Override public void applyRoutes(Javalin javalin) {
            javalin.get("/", ctx -> {
                // Send file
                File file = new File(HTTP_STATIC_FILES.indexFile);
                if (!file.exists()) {
                    ctx.contentType(ContentType.TEXT_HTML);
                    ctx.result("""
                            <!DOCTYPE html>
                            <html>
                                <head>
                                    <meta charset="utf8">
                                </head>
                                <body>%s</body>
                            </html>
                            """.formatted(translate("messages.status.welcome")));
                } else {
                    var filePath = file.getPath();
                    ContentType fromExtension = ContentType.getContentTypeByExtension(filePath.substring(filePath.lastIndexOf(".") + 1));
                    ctx.contentType(fromExtension != null ? fromExtension : ContentType.TEXT_HTML);
                    ctx.result(FileUtils.read(filePath));
                }
            });
        }
    }

    /**
     * Handles unhandled endpoints on the Express application.
     */
    public static class UnhandledRequestRouter implements Router {
        @Override public void applyRoutes(Javalin javalin) {
            javalin.error(404, ctx -> {
                // Error log
                if (DISPATCH_INFO.logRequests == ServerDebugMode.MISSING)
                    Grasscutter.getLogger().info(translate("messages.dispatch.unhandled_request_error", ctx.method(), ctx.url()));
                // Send file
                File file = new File(HTTP_STATIC_FILES.errorFile);
                if (!file.exists()) {
                    ctx.contentType(ContentType.TEXT_HTML);
                    ctx.result("""
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
                } else {
                    var filePath = file.getPath();
                    ContentType fromExtension = ContentType.getContentTypeByExtension(filePath.substring(filePath.lastIndexOf(".") + 1));
                    ctx.contentType(fromExtension != null ? fromExtension : ContentType.TEXT_HTML);
                    ctx.result(FileUtils.read(filePath));
                }
            });
        }
    }
}
