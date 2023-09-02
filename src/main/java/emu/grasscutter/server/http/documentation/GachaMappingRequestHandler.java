package emu.grasscutter.server.http.documentation;

import static emu.grasscutter.config.Configuration.DOCUMENT_LANGUAGE;

import emu.grasscutter.tools.Tools;
import emu.grasscutter.utils.lang.Language;
import io.javalin.http.*;
import java.util.List;

final class GachaMappingRequestHandler implements DocumentationHandler {
    private final List<String> gachaJsons;

    GachaMappingRequestHandler() {
        this.gachaJsons = Tools.createGachaMappingJsons();
    }

    @Override
    public void handle(Context ctx) {
        final int langIdx =
                Language.TextStrings.MAP_LANGUAGES.getOrDefault(
                        DOCUMENT_LANGUAGE,
                        0); // TODO: This should really be based off the client language somehow
        ctx.contentType(ContentType.APPLICATION_JSON).result(gachaJsons.get(langIdx));
    }
}
