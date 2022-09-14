package emu.grasscutter.server.http.documentation;

import emu.grasscutter.server.http.Router;
import io.javalin.Javalin;
import io.javalin.http.Context;

public final class DocumentationServerHandler implements Router {

    @Override
    public void applyRoutes(Javalin javalin) {
        final RootRequestHandler root = new RootRequestHandler();
        final HandbookRequestHandler handbook = new HandbookRequestHandler();
        final GachaMappingRequestHandler gachaMapping = new GachaMappingRequestHandler();

        // TODO: Removal
        // TODO: Forward /documentation requests to https://grasscutter.io/wiki
        javalin.get("/documentation/handbook", handbook::handle);
        javalin.get("/documentation/gachamapping", gachaMapping::handle);
        javalin.get("/documentation", root::handle);
    }
}
