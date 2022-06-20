package emu.grasscutter.server.http.documentation;

import com.google.gson.reflect.TypeToken;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.CommandMap;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.AvatarData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.data.excels.MonsterData;
import emu.grasscutter.data.excels.SceneData;
import emu.grasscutter.utils.FileUtils;
import emu.grasscutter.utils.Utils;
import express.http.Request;
import express.http.Response;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static emu.grasscutter.Configuration.*;
import static emu.grasscutter.utils.Language.translate;

final class HandbookRequestHandler implements DocumentationHandler {

    private final String template;
    private Map<Long, String> map;


    public HandbookRequestHandler() {
        final File templateFile = new File(Utils.toFilePath(DATA("documentation/handbook.html")));
        if (templateFile.exists()) {
            this.template = new String(FileUtils.read(templateFile), StandardCharsets.UTF_8);
        } else {
            Grasscutter.getLogger().warn("File does not exist: " + templateFile);
            this.template = null;
        }

        final String textMapFile = "TextMap/TextMap" + DOCUMENT_LANGUAGE + ".json";
        try (InputStreamReader fileReader = new InputStreamReader(new FileInputStream(
            Utils.toFilePath(RESOURCE(textMapFile))), StandardCharsets.UTF_8)) {
            this.map = Grasscutter.getGsonFactory()
                .fromJson(fileReader, new TypeToken<Map<Long, String>>() {
                }.getType());
        } catch (IOException e) {
            Grasscutter.getLogger().warn("Resource does not exist: " + textMapFile);
            this.map = new HashMap<>();
        }
    }

    @Override
    public void handle(Request request, Response response) {
        if (this.template == null) {
            response.status(500);
            return;
        }

        final CommandMap cmdMap = new CommandMap(true);
        final Int2ObjectMap<AvatarData> avatarMap = GameData.getAvatarDataMap();
        final Int2ObjectMap<ItemData> itemMap = GameData.getItemDataMap();
        final Int2ObjectMap<SceneData> sceneMap = GameData.getSceneDataMap();
        final Int2ObjectMap<MonsterData> monsterMap = GameData.getMonsterDataMap();

        // Add translated title etc. to the page.
        String content = this.template.replace("{{TITLE}}", translate("documentation.handbook.title"))
            .replace("{{TITLE_COMMANDS}}", translate("documentation.handbook.title_commands"))
            .replace("{{TITLE_AVATARS}}", translate("documentation.handbook.title_avatars"))
            .replace("{{TITLE_ITEMS}}", translate("documentation.handbook.title_items"))
            .replace("{{TITLE_SCENES}}", translate("documentation.handbook.title_scenes"))
            .replace("{{TITLE_MONSTERS}}", translate("documentation.handbook.title_monsters"))
            .replace("{{HEADER_ID}}", translate("documentation.handbook.header_id"))
            .replace("{{HEADER_COMMAND}}", translate("documentation.handbook.header_command"))
            .replace("{{HEADER_DESCRIPTION}}",
                translate("documentation.handbook.header_description"))
            .replace("{{HEADER_AVATAR}}", translate("documentation.handbook.header_avatar"))
            .replace("{{HEADER_ITEM}}", translate("documentation.handbook.header_item"))
            .replace("{{HEADER_SCENE}}", translate("documentation.handbook.header_scene"))
            .replace("{{HEADER_MONSTER}}", translate("documentation.handbook.header_monster"))
            // Commands table
            .replace("{{COMMANDS_TABLE}}", cmdMap.getAnnotationsAsList()
                .stream()
                .map(cmd -> "<tr><td><code>" + cmd.label() + "</code></td><td>" +
                    cmd.description() + "</td></tr>")
                .collect(Collectors.joining("\n")))
            // Avatars table
            .replace("{{AVATARS_TABLE}}", GameData.getAvatarDataMap().keySet()
                .intStream()
                .sorted()
                .mapToObj(avatarMap::get)
                .map(data -> "<tr><td><code>" + data.getId() + "</code></td><td>" +
                    this.map.get(data.getNameTextMapHash()) + "</td></tr>")
                .collect(Collectors.joining("\n")))
            // Items table
            .replace("{{ITEMS_TABLE}}", GameData.getItemDataMap().keySet()
                .intStream()
                .sorted()
                .mapToObj(itemMap::get)
                .map(data -> "<tr><td><code>" + data.getId() + "</code></td><td>" +
                    this.map.get(data.getNameTextMapHash()) + "</td></tr>")
                .collect(Collectors.joining("\n")))
            // Scenes table
            .replace("{{SCENES_TABLE}}", GameData.getSceneDataMap().keySet()
                .intStream()
                .sorted()
                .mapToObj(sceneMap::get)
                .map(data -> "<tr><td><code>" + data.getId() + "</code></td><td>" +
                    data.getScriptData() + "</td></tr>")
                .collect(Collectors.joining("\n")))
            .replace("{{MONSTERS_TABLE}}", GameData.getMonsterDataMap().keySet()
                .intStream()
                .sorted()
                .mapToObj(monsterMap::get)
                .map(data -> "<tr><td><code>" + data.getId() + "</code></td><td>" +
                    this.map.get(data.getNameTextMapHash()) + "</td></tr>")
                .collect(Collectors.joining("\n")));

        response.send(content);
    }
}
