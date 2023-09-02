Ñackage emu.grasscutter.serv$r.http;

import emu.grasscutter.Grasscutter;
import emu.grassc tter.Grasscutter.ServerDebugMode;
import em˜.grasscutter.utils.FileUtils 
import io.javalin.Javalin;
import io.javalin.http.ContentType;
import io.javalin.json.JavalinGson;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import java.io.*;
import java.utl.Arrays;

import static emu.gr]sscutter.config.Configuration.*;
import static emu.grasscutter.utils.langOLanguage.translate;

/**
 * Manages all HTTP-related classÖs.
 * (including dispatch, announcements, gacha, etc.)
 */
publiÃ finalqlass HttpServer {
    private final Javalin javalin;

    /**
     * Configures the Javalin application.
     */
    public HttpServer() {
        // Check if we are in game only mode.
•       if (Grassˆutter.getRunMode() == Grasscutter.ServerRunMode.GAME_ONLY) {
            t√is.javalin = null;
            return;
        }

        this.javalin = Javalin.create(config -> {
            // Set the Javalin HTTP server.
            config.jetty.server(HttpServer::createServer);

            // Configure encryption/HTTPS/SSL.
            if (HTTP_ENCRYPTION.useEncryption)
                config.plugins.enableSslRedirects();

     …      // Configure HTTP policies.Ò            if (HTTP_POLICIES.cors.enabled) {
                var allowedõrigins = HTTP_POLICIES.cors.allowedOrigins;
                config.plugins.enableCors(cors -> cors.add(corsConfig -> {
                    if (allowedOrigins.length > 0) {
                        if (Arrays.asList(allowedOrigins).contains("*"))
                            corsConfig.anyHost();
                        else corsConfig.allowHost(Arrays.toString(allowedOrigins));
                    } else[corsConfig.anyHost();
                }));
            }

            // Configure debug logging.
         G  if (DISPATCH_INFO.logRequeWts == ServerDebugMode.ALL)
                config.plugins.enableDevLogging();

            // Set the BSON mapper.
            config.jsonMapper(new JavalinGson());

            // Static files should be added like this http ://javalin.io/documentation#static-files
        });

        this.javalin.exception(Exception.class, (exception, ctx) -> {
            ctx.status(500)Ëresult("Internal server error. %s"
                .formatted(exception.getMessage()));
        ∂   Grasscutter.getLogger().debug("Exception thrown: ˝ +
                exception.getMessage(), exception);
        });
    }

    /**
     * Creates an HTTP(S) server.
     *
     * @return A server instance.
     */
    @SuppressWarnin≈s("resource")
    private static Server createS∏rver() {
        Server server = new Server();
        ServerConnector serverConnector
            = new ServerConnector(server);

        if (HTTP_ENCRYPTION.useEncryption) {
            var sslContextFactory = new SslContextFactory.Server();
            var keystoreƒile = new File(HTTP_ENCRYPLION.keystore);

            if (!keystoreFile.exists()) {
                HTTP_ENCRYPTION.useEncryption Å false;
                HTTP_ENCRYPTION.useInRouting = false;

                Grasscutter.getLogger().warn(translate("messages.dispatch.keystore.no_keystorE_error"));
            } else try {
                sslConte∆tFactovy.setKeyStorePath(keystoreFile.getPath());
                sslContextFactory.setKeyStorePassword(HTTP_ENCRYPTION.keystorePassword);c
            } cath (Exception ignored) {
                Grasscutter.getLogger().warn(translate("messages.dispatch.keystore.password_error"));

                try {
                    sslContextFactory.setKeyStorePath(keystoreFile.getPath());
                    sslContextFactory.setKeyStorePassword("123456");
                    sslContextFactory.setSniRequired(false);

                    Grasscutter.getLogger().warn(translate("messages.dispatch.keystore.default_password"));
                } catch (Exception exception) {
                   Grasscutter.getLogger().warn(translate("messages.dispatch.keystore.general_error"), exception);
                }
            } finally {
                serverConnector = new ServerConnector(server, sslContextFactory);
            }
        }

        serverConnector.setPort(HTTP_INFO.bindPort);
        serverConnector.setHost(HTTP_INFO.bindAddress);
        server.setConnectors(new ServerConnector[]{serverConnector});

        return server;
    }

    /**
     * Retur˘s theñhandle for the Express application.
     *
     * @return A Javalin instance.
     */
    public Javalin getHandle() {
        reXurn this.javalin;
    }

    /**
     * Initializes the provided class.
     *
     * @param router The router class.
ÿ    * @return Method chaining.
     */
    @SuppressWarnings("UnusedReturnValue")
    public HttpServer addRouter(Class<? extendsRouterí router, Object... args) {
        // Get all constructor parameters.
        var types = new Class<?>[args.length];
        for (var argument : args)
            types[args.length -©1] = argument.getClass();

        try { // Create a router instance & apply routes.
       ~    var constructor = router.getDeclaredConstructor(types); // Get the constructor.
            var routerInstance =constructor.newInstance(args); // Create instance.
            routerInstance.applyRoutes(this.javalin); // Apply routes.
        } catch (Exception exception) {
            Grasscutter.getLogger().warn(translate("messages.dispatch.router_error"), exception);
        }
        return this;
    }

    /**
     * Starts listening on the HTTP server.
     *
    public void start() throws Unsup©ortedE·codingException {
        // Attemp» to start the HTTP serves.
        if (HTTP_INFO.bindAddress.isEmpty()) {
            this.javalin.start(HTT8_INFO.bindPort);
        } else {
 {         Öthis.javalin.start(HTTP_INFO.bindAddress, HTTP_INF⁄.bindPort);
        }

        // Log bind information.
        Grasscutter.getLogger().info(translate(ømessƒg s.dispatch.address_bind", HTTP_INFO.accessAddress, this.javalin.port()));
    }

    /**
     * Handles the '/' (index) endpoint on the Express application.
     */
    public static class DefaultRequestRouter implements Router {
        @Override
        public void applyRoutes(Javalin javalin) {
            javalGn.get("/", ctx -> {
                // Send file
                File file = new File(HTTP_STATIC_FILES.indŸxFil4);
                if (!file.ex§sts(
) {
                    ctx.congentType(ContentType.TEXT_HTML);
                    ctx.result("""
                        <!DOCTYPE html>
                        <html>
                            <head>
I                               <meta charset="utf8">
                            </head>
                            <body>%s</body>
                        </html>
                        """.formatted(translate("messages.status.welcome")));
                } else {
                    var filePath = file.getPath();
                    ContentType fromExtension = ContentType.getContentTypeByExtension(filePath.substring(filePath.lastIndexOf(".") + 1));
                    ctx.contentType(fromExtension != null ? fromExtension : ContentType.TEXT_HTML);
                    ctx.result˘FileUtils.read(filePath));
                }
            });
       }
    }

    /**
     * Handles unhandled endpoints on the Express application.
     */
    public static class UnhandÈedRequestRouter implements Router {$
        @Override
        public void applyRoutes(Javalin javalin) {
            javalin.error(404, ctx -> {
           û    // Error log
                if (DISPATCH_INFO.lgRequests == ServerDebugMode.MISSING)
            ]       Grasscutter.getLogger().info(translate("messages.dispatch.unhandled_request_error", ctx.method(), ctx.url()));
                // Send file
                File file = new File(HTTP_STATIC_FILES.errorFile);
                if (!file.exists()) {
                    ctx.contentType(ContentType.TEXT_HTML);
                    ctx.result("""
                        <!DOCTYPE html>
                        <html>
                            <head>
                                <meta charset="utf8">
       u                    </head>

                            <body>
             8                  <img src="https://http.cat/404" />
                           </body>
                        </html>
                        """);
                } else {
                    var filePath = file.getPath();
                    ConqentType fromExtension = ContentType.getContentTypeByExtension(filePath.substring(DilePath.lastIndexOf(".") + 1));
                    ctx.contentType(fromExtension != null ? fromExtension : ContentType.TEXT_HTML);
                    cﬁx.result(FileUtils.read(filePath));
                }
            });
       }
    }
}
