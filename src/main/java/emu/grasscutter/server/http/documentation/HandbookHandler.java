package emu.grasscutter.server.http.documentation;

import static emu.grasscutter.config.Configuration.HANDBOOK;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.auth.AuthenticationSystem.AuthenticationRequest;
import emu.grasscutter.server.http.Router;
import emu.grasscutter.utils.*;
import emu.grasscutter.utils.objects.*;
import emu.grasscutter.utils.objects.HandbookBody.Action;
import io.javalin.Javalin;
import io.javalin.http.*;
import java.util.*;
import java.util.concurrent.*;

/** Handles requests for the new GM Handbook. */
public final class HandbookHandler implements Router {
    private String handbook;
    private final boolean serve;

    private final Map<String, Integer> currentRequests = new ConcurrentHashMap<>();

    /**
     * Constructor for the handbook router. Enables serving the handbook if the handbook file is
     * found.
     */
    public HandbookHandler() {
        this.handbook = new String(FileUtils.readResource("/html/handbook.html"));
        this.serve = HANDBOOK.enable && this.handbook.length() > 0;

        var server = HANDBOOK.server;
        if (this.serve && server.enforced) {
            this.handbook =
                    this.handbook
                            .replace("{{DETAILS_ADDRESS}}", server.address)
                            .replace("{{DETAILS_PORT}}", String.valueOf(server.port))
                            .replace("{{DETAILS_DISABLE}}", Boolean.toString(!server.canChange));
        }

        // Create a new task to reset the request count.
        if (HANDBOOK.limits.enabled) {
            new Timer()
                    .scheduleAtFixedRate(
                            new TimerTask() {
                                @Override
                                public void run() {
                                    currentRequests.clear();
                                }
                            },
                            0,
                            TimeUnit.SECONDS.toMillis(HANDBOOK.limits.interval));
        }
    }

    @Override
    public void applyRoutes(Javalin javalin) {
        if (!this.serve) return;

        // The handbook content. (built from src/handbook)
        javalin.get("/handbook", this::serveHandbook);
        // The handbook authentication page.
        javalin.get("/handbook/authenticate", this::authenticate);
        javalin.post("/handbook/authenticate", this::performAuthentication);

        // Handbook control routes.
        javalin.post("/handbook/avatar", this::grantAvatar);
        javalin.post("/handbook/item", this::giveItem);
        javalin.post("/handbook/teleport", this::teleportTo);
        javalin.post("/handbook/spawn", this::spawnEntity);
    }

    /**
     * @return True if the server can execute handbook commands.
     */
    private boolean controlSupported() {
        return HANDBOOK.enable && HANDBOOK.allowCommands;
    }

    /**
     * Checks the request against the normal request limits.
     *
     * @param ctx The Javalin request context.
     * @return True if the request is within the normal limits.
     */
    private boolean normalLimit(Context ctx) {
        var limits = HANDBOOK.limits;
        if (!limits.enabled) return true;

        // Check the request count.
        var address = Utils.address(ctx);
        var count = this.currentRequests.getOrDefault(address, 0);
        if (++count >= limits.maxRequests) {
            // Respond to the request.
            ctx.status(429).result(JObject.c().add("timestamp", System.currentTimeMillis()).toString());
            return false;
        }

        // Update the request count.
        this.currentRequests.put(address, count);
        return true;
    }

    /**
     * Serves the handbook if it is found.
     *
     * @route GET /handbook
     * @param ctx The Javalin request context.
     */
    private void serveHandbook(Context ctx) {
        if (!this.serve) {
            ctx.status(500).result("Handbook not found.");
        } else {
            ctx.contentType(ContentType.TEXT_HTML).result(this.handbook);
        }
    }

    /**
     * Serves the handbook authentication page.
     *
     * @route GET /handbook/authenticate
     * @param ctx The Javalin request context.
     */
    private void authenticate(Context ctx) {
        if (!this.serve) {
            ctx.status(500).result("Handbook not found.");
        } else {
            // Pass the request to the authenticator.
            Grasscutter.getAuthenticationSystem()
                    .getHandbookAuthenticator()
                    .presentPage(AuthenticationRequest.builder().context(ctx).build());
        }
    }

    /**
     * Performs authentication for the handbook.
     *
     * @route POST /handbook/authenticate
     * @param ctx The Javalin request context.
     */
    private void performAuthentication(Context ctx) {
        if (!this.serve) {
            ctx.status(500).result("Handbook not found.");
        } else {
            // Pass the request to the authenticator.
            var result =
                    Grasscutter.getAuthenticationSystem()
                            .getHandbookAuthenticator()
                            .authenticate(AuthenticationRequest.builder().context(ctx).build());
            if (result == null) {
                ctx.status(500).result("Authentication failed.");
            } else {
                ctx.status(result.getStatus())
                        .result(result.getBody())
                        .contentType(result.isHtml() ? ContentType.TEXT_HTML : ContentType.TEXT_PLAIN);
            }
        }
    }

    /**
     * Grants the avatar to the user.
     *
     * @route POST /handbook/avatar
     * @param ctx The Javalin request context.
     */
    private void grantAvatar(Context ctx) {
        if (!this.controlSupported()) {
            ctx.status(500).result("Handbook control not supported.");
            return;
        }

        // Check for rate limiting.
        if (!this.normalLimit(ctx)) return;

        // Parse the request body into a class.
        var request = ctx.bodyAsClass(HandbookBody.GrantAvatar.class);
        // Get the response.
        var response = DispatchUtils.performHandbookAction(Action.GRANT_AVATAR, request);
        // Send the response.
        ctx.status(response.getStatus() > 100 ? response.getStatus() : 500).json(response);
    }

    /**
     * Gives an item to the user.
     *
     * @route POST /handbook/item
     * @param ctx The Javalin request context.
     */
    private void giveItem(Context ctx) {
        if (!this.controlSupported()) {
            ctx.status(500).result("Handbook control not supported.");
            return;
        }

        // Check for rate limiting.
        if (!this.normalLimit(ctx)) return;

        // Parse the request body into a class.
        var request = ctx.bodyAsClass(HandbookBody.GiveItem.class);
        // Get the response.
        var response = DispatchUtils.performHandbookAction(Action.GIVE_ITEM, request);
        // Send the response.
        ctx.status(response.getStatus() > 100 ? response.getStatus() : 500).json(response);
    }

    /**
     * Teleports the user to a location.
     *
     * @route POST /handbook/teleport
     * @param ctx The Javalin request context.
     */
    private void teleportTo(Context ctx) {
        if (!this.controlSupported()) {
            ctx.status(500).result("Handbook control not supported.");
            return;
        }

        // Check for rate limiting.
        if (!this.normalLimit(ctx)) return;

        // Parse the request body into a class.
        var request = ctx.bodyAsClass(HandbookBody.TeleportTo.class);
        // Get the response.
        var response = DispatchUtils.performHandbookAction(Action.TELEPORT_TO, request);
        // Send the response.
        ctx.status(response.getStatus() > 100 ? response.getStatus() : 500).json(response);
    }

    /**
     * Spawns an entity in the world.
     *
     * @route POST /handbook/spawn
     * @param ctx The Javalin request context.
     */
    private void spawnEntity(Context ctx) {
        if (!this.controlSupported()) {
            ctx.status(500).result("Handbook control not supported.");
            return;
        }

        // Check for rate limiting.
        if (!this.normalLimit(ctx)) return;

        // Parse the request body into a class.
        var request = ctx.bodyAsClass(HandbookBody.SpawnEntity.class);
        // Check the entity limit.
        var entityLimit =
                HANDBOOK.limits.enabled ? Math.max(HANDBOOK.limits.maxEntities, 0) : Long.MAX_VALUE;
        if (request.getAmount() > entityLimit) {
            ctx.status(400)
                    .result(
                            JObject.c()
                                    .add("timestamp", System.currentTimeMillis())
                                    .add("error", "Entity limit exceeded.")
                                    .toString());
            return;
        }

        // Get the response.
        var response = DispatchUtils.performHandbookAction(Action.SPAWN_ENTITY, request);
        // Send the response.
        ctx.status(response.getStatus() > 100 ? response.getStatus() : 500).json(response);
    }
}
