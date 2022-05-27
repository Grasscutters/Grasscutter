package emu.grasscutter.server.http.documentation;

import emu.grasscutter.server.http.Router;
import express.Express;
import io.javalin.Javalin;

public final class DocumentationServerHandler implements Router {

    @Override
    public void applyRoutes(Express express, Javalin handle) {
        final RootRequestHandler root = new RootRequestHandler();
        final HandbookRequestHandler handbook = new HandbookRequestHandler();
        final GachaMappingRequestHandler gachaMapping = new GachaMappingRequestHandler();

        express.get("/documentation/handbook", handbook::handle);
        express.get("/documentation/gachamapping", gachaMapping::handle);
        express.get("/documentation", root::handle);
    }
}
