package emu.grasscutter.server.http.documentation;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.utils.FileUtils;
import emu.grasscutter.utils.Utils;
import express.http.Request;
import express.http.Response;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static emu.grasscutter.Configuration.DATA;
import static emu.grasscutter.utils.Language.translate;

final class RootRequestHandler implements DocumentationHandler {

    private final String template;

    public RootRequestHandler() {
        final File templateFile = new File(Utils.toFilePath(DATA("documentation/index.html")));
        if (templateFile.exists()) {
            this.template = new String(FileUtils.read(templateFile), StandardCharsets.UTF_8);
        } else {
            Grasscutter.getLogger().warn("File does not exist: " + templateFile);
            this.template = null;
        }
    }

    @Override
    public void handle(Request request, Response response) {
        if (this.template == null) {
            response.status(500);
            return;
        }

        String content = this.template.replace("{{TITLE}}", translate("documentation.index.title"))
            .replace("{{ITEM_HANDBOOK}}", translate("documentation.index.handbook"))
            .replace("{{ITEM_GACHA_MAPPING}}", translate("documentation.index.gacha_mapping"));
        response.send(content);
    }
}
