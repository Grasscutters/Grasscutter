package emu.grasscutter.server.http.documentation;

import emu.grasscutter.tools.Tools;
import emu.grasscutter.utils.Language;
import io.javalin.http.ContentType;
import io.javalin.http.Context;

import static emu.grasscutter.config.Configuration.DOCUMENT_LANGUAGE;

import java.util.List;

final class GachaMappingRequestHandler implements DocumentationHandler {
    private List<String> gachaJsons;

    GachaMappingRequestHandler() {
        this.gachaJsons = Tools.createGachaMappingJsons();
    }

    @Override
    public void handle(Context ctx) {
        final int langIdx = Language.TextStrings.MAP_LANGUAGES.getOrDefault(DOCUMENT_LANGUAGE, 0);  // TODO: This should really be based off the client language somehow
        ctx.contentType(ContentType.APPLICATION_JSON).result(gachaJsons.get(langIdx));
    }
}
