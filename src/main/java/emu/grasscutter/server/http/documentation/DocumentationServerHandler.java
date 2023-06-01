package emu.grasscutter.server.http.documentation;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.server.http.Router;
import io.javalin.Javalin;

public final class DocumentationServerHandler implements Router {

    @Override
    public void applyRoutes(Javalin javalin) {
        final var root = new RootRequestHandler();
        final var gachaMapping = new GachaMappingRequestHandler();

        // TODO: Removal
        // TODO: Forward /documentation requests to https://grasscutter.io/wiki
        if (Grasscutter.getRunMode() != Grasscutter.ServerRunMode.DISPATCH_ONLY) {
            final var handbook = new HandbookRequestHandler();
            javalin.get("/documentation/handbook", handbook::handle);
        }

        javalin.get("/documentation/gachamapping", gachaMapping::handle);
        javalin.get("/documentation", root::handle);
    }
}
