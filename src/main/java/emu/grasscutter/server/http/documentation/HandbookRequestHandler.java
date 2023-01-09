package emu.grasscutter.server.http.documentation;

import static emu.grasscutter.config.Configuration.*;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.CommandMap;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.AvatarData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.data.excels.MonsterData;
import emu.grasscutter.data.excels.SceneData;
import emu.grasscutter.utils.FileUtils;
import emu.grasscutter.utils.Language;
import io.javalin.http.ContentType;
import io.javalin.http.Context;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class HandbookRequestHandler implements DocumentationHandler {
    private List<String> handbookHtmls;

    public HandbookRequestHandler() {
        var templatePath = FileUtils.getDataPath("documentation/handbook.html");
        try {
            this.handbookHtmls = generateHandbookHtmls(Files.readString(templatePath));
        } catch (IOException ignored) {
            Grasscutter.getLogger().warn("File does not exist: " + templatePath);
        }
    }

    @Override
    public void handle(Context ctx) {
        int langIdx = 0;
        String acceptLanguage = ctx.header("Accept-Language");
        if (acceptLanguage != null) {
            Pattern localePattern = Pattern.compile("[a-z]+-[A-Z]+");
            Matcher matcher = localePattern.matcher(acceptLanguage);
            if (matcher.find()) {
                String lang = matcher.group(0);
                langIdx = Language.TextStrings.MAP_GC_LANGUAGES.getOrDefault(lang,0);
            }
        }

        if (this.handbookHtmls == null) {
            ctx.status(500);
        } else {
            if (langIdx <= this.handbookHtmls.size() - 1) {
                ctx.contentType(ContentType.TEXT_HTML);
                ctx.result(this.handbookHtmls.get(langIdx));
            }
        }
    }

    private List<String> generateHandbookHtmls(String template) {
        final int NUM_LANGUAGES = Language.TextStrings.NUM_LANGUAGES;
        final List<String> output = new ArrayList<>(NUM_LANGUAGES);
        final List<Language> languages = Language.TextStrings.getLanguages();
        final List<StringBuilder> sbs = new ArrayList<>(NUM_LANGUAGES);
        for (int langIdx = 0; langIdx < NUM_LANGUAGES; langIdx++)
            sbs.add(new StringBuilder(""));

        // Commands table
        CommandMap.getInstance().getHandlersAsList().forEach(cmd -> {
            String label = cmd.getLabel();
            String descKey = cmd.getDescriptionKey();
            for (int langIdx = 0; langIdx < NUM_LANGUAGES; langIdx++)
                sbs.get(langIdx).append("<tr><td><code>" + label + "</code></td><td>" + languages.get(langIdx).get(descKey) + "</td></tr>\n");
        });
        sbs.forEach(sb -> sb.setLength(sb.length()-1));  // Remove trailing \n
        final List<String> cmdsTable = sbs.stream().map(StringBuilder::toString).toList();

        // Avatars table
        final Int2ObjectMap<AvatarData> avatarMap = GameData.getAvatarDataMap();
        sbs.forEach(sb -> sb.setLength(0));
        avatarMap.keySet().intStream().sorted().mapToObj(avatarMap::get).forEach(data -> {
            int id = data.getId();
            Language.TextStrings name = Language.getTextMapKey(data.getNameTextMapHash());
            for (int langIdx = 0; langIdx < NUM_LANGUAGES; langIdx++)
                sbs.get(langIdx).append("<tr><td><code>" + id + "</code></td><td>" + name.get(langIdx) + "</td></tr>\n");
        });
        sbs.forEach(sb -> sb.setLength(sb.length()-1));  // Remove trailing \n
        final List<String> avatarsTable = sbs.stream().map(StringBuilder::toString).toList();

        // Items table
        final Int2ObjectMap<ItemData> itemMap = GameData.getItemDataMap();
        sbs.forEach(sb -> sb.setLength(0));
        itemMap.keySet().intStream().sorted().mapToObj(itemMap::get).forEach(data -> {
            int id = data.getId();
            Language.TextStrings name = Language.getTextMapKey(data.getNameTextMapHash());
            for (int langIdx = 0; langIdx < NUM_LANGUAGES; langIdx++)
                sbs.get(langIdx).append("<tr><td><code>" + id + "</code></td><td>" + name.get(langIdx) + "</td></tr>\n");
        });
        sbs.forEach(sb -> sb.setLength(sb.length()-1));  // Remove trailing \n
        final List<String> itemsTable = sbs.stream().map(StringBuilder::toString).toList();

        // Scenes table
        final Int2ObjectMap<SceneData> sceneMap = GameData.getSceneDataMap();
        sceneMap.keySet().intStream().sorted().mapToObj(sceneMap::get).forEach(data -> {
            int id = data.getId();
            for (int langIdx = 0; langIdx < NUM_LANGUAGES; langIdx++)
                sbs.get(langIdx).append("<tr><td><code>" + id + "</code></td><td>" + data.getScriptData() + "</td></tr>\n");
        });
        sbs.forEach(sb -> sb.setLength(sb.length()-1));  // Remove trailing \n
        final List<String> scenesTable = sbs.stream().map(StringBuilder::toString).toList();

        // Monsters table
        final Int2ObjectMap<MonsterData> monsterMap = GameData.getMonsterDataMap();
        monsterMap.keySet().intStream().sorted().mapToObj(monsterMap::get).forEach(data -> {
            int id = data.getId();
            Language.TextStrings name = Language.getTextMapKey(data.getNameTextMapHash());
            for (int langIdx = 0; langIdx < NUM_LANGUAGES; langIdx++)
                sbs.get(langIdx).append("<tr><td><code>" + id + "</code></td><td>" + name.get(langIdx) + "</td></tr>\n");
        });
        sbs.forEach(sb -> sb.setLength(sb.length()-1));  // Remove trailing \n
        final List<String> monstersTable = sbs.stream().map(StringBuilder::toString).toList();

        // Add translated title etc. to the page.
        for (int langIdx = 0; langIdx < NUM_LANGUAGES; langIdx++) {
            Language lang = languages.get(langIdx);
            output.add(template
                .replace("{{TITLE}}", lang.get("documentation.handbook.title"))
                .replace("{{TITLE_COMMANDS}}", lang.get("documentation.handbook.title_commands"))
                .replace("{{TITLE_AVATARS}}", lang.get("documentation.handbook.title_avatars"))
                .replace("{{TITLE_ITEMS}}", lang.get("documentation.handbook.title_items"))
                .replace("{{TITLE_SCENES}}", lang.get("documentation.handbook.title_scenes"))
                .replace("{{TITLE_MONSTERS}}", lang.get("documentation.handbook.title_monsters"))
                .replace("{{HEADER_ID}}", lang.get("documentation.handbook.header_id"))
                .replace("{{HEADER_COMMAND}}", lang.get("documentation.handbook.header_command"))
                .replace("{{HEADER_DESCRIPTION}}", lang.get("documentation.handbook.header_description"))
                .replace("{{HEADER_AVATAR}}", lang.get("documentation.handbook.header_avatar"))
                .replace("{{HEADER_ITEM}}", lang.get("documentation.handbook.header_item"))
                .replace("{{HEADER_SCENE}}", lang.get("documentation.handbook.header_scene"))
                .replace("{{HEADER_MONSTER}}", lang.get("documentation.handbook.header_monster"))
                // Commands table
                .replace("{{COMMANDS_TABLE}}", cmdsTable.get(langIdx))
                .replace("{{AVATARS_TABLE}}", avatarsTable.get(langIdx))
                .replace("{{ITEMS_TABLE}}", itemsTable.get(langIdx))
                .replace("{{SCENES_TABLE}}", scenesTable.get(langIdx))
                .replace("{{MONSTERS_TABLE}}", monstersTable.get(langIdx))
            );
        }
        return output;
    }
}
