package emu.grasscutter.server.http.documentation;

import emu.grasscutter.tools.Tools;
import emu.grasscutter.utils.Language;
import express.http.Request;
import express.http.Response;

import static emu.grasscutter.config.Configuration.DOCUMENT_LANGUAGE;

import java.util.List;

final class GachaMappingRequestHandler implements DocumentationHandler {
    private List<String> gachaJsons;

    GachaMappingRequestHandler() {
        this.gachaJsons = Tools.createGachaMappingJsons();
    }

    @Override
    public void handle(Request request, Response response) {
        final int langIdx = Language.TextStrings.MAP_LANGUAGES.getOrDefault(DOCUMENT_LANGUAGE, 0);  // TODO: This should really be based off the client language somehow
        response.set("Content-Type", "application/json")
                .ctx()
                .result(gachaJsons.get(langIdx));
    }
}
