package emu.grasscutter.server.http.documentation;

import static emu.grasscutter.utils.lang.Language.translate;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.utils.FileUtils;
import io.javalin.http.*;
import java.io.IOException;
import java.nio.file.Files;

final class RootRequestHandler implements DocumentationHandler {

    private final String template;

    public RootRequestHandler() {
        var templatePath = FileUtils.getDataPath("documentation/index.html");
        String t = null;
        try {
            t = Files.readString(templatePath);
        } catch (IOException ignored) {
            Grasscutter.getLogger().warn("File does not exist: " + templatePath);
        }
        this.template = t;
    }

    @Override
    public void handle(Context ctx) {
        if (template == null) {
            ctx.status(500);
            return;
        }

        String content =
                template
                        .replace("{{TITLE}}", translate("documentation.index.title"))
                        .replace("{{ITEM_HANDBOOK}}", translate("documentation.index.handbook"))
                        .replace("{{ITEM_GACHA_MAPPING}}", translate("documentation.index.gacha_mapping"));
        ctx.contentType(ContentType.TEXT_HTML);
        ctx.result(content);
    }
}
