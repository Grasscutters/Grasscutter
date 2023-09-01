package emu.grasscutter.server.http.documentation;

import emu.grasscutter.server.http.Router;
import io.javalin.Javalin;

public final class DocumentationServerHandler implements Router {

    @Override
    public void applyRoutes(Javalin javalin) {
        final var root = new RootRequestHandler();
        final var gachaMapping = new GachaMappingRequestHandler();

        javalin.get("/documentation/handbook", ctx -> ctx.redirect("https://grasscutter.io/handbook"));
        javalin.get("/documentation/gachamapping", gachaMapping::handle);
        javalin.get("/documentation", root::handle);
    }
}
