package emu.grasscutter.server.http.documentation;

import static emu.grasscutter.config.Configuration.DATA;
import static emu.grasscutter.utils.Language.translate;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.utils.FileUtils;
import emu.grasscutter.utils.Utils;
import io.javalin.http.ContentType;
import io.javalin.http.Context;

import java.io.File;
import java.nio.charset.StandardCharsets;

final class RootRequestHandler implements DocumentationHandler {

    private final String template;

    public RootRequestHandler() {
        final File templateFile = new File(Utils.toFilePath(DATA("documentation/index.html")));
        if (templateFile.exists()) {
            template = new String(FileUtils.read(templateFile), StandardCharsets.UTF_8);
        } else {
            Grasscutter.getLogger().warn("File does not exist: " + templateFile);
            template = null;
        }
    }

    @Override
    public void handle(Context ctx) {
        if (template == null) {
            ctx.status(500);
            return;
        }

        String content = template.replace("{{TITLE}}", translate("documentation.index.title"))
                .replace("{{ITEM_HANDBOOK}}", translate("documentation.index.handbook"))
                .replace("{{ITEM_GACHA_MAPPING}}", translate("documentation.index.gacha_mapping"));
        ctx.contentType(ContentType.TEXT_HTML);
        ctx.result(content);
    }
}
