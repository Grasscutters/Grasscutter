package emu.grasscutter.server.http.documentation;

import com.google.gson.reflect.TypeToken;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.AvatarData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.utils.Utils;
import express.http.Request;
import express.http.Response;

import static emu.grasscutter.config.Configuration.DOCUMENT_LANGUAGE;
import static emu.grasscutter.config.Configuration.RESOURCE;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class GachaMappingRequestHandler implements DocumentationHandler {

    private Map<Long, String> map;

    GachaMappingRequestHandler() {
        final String textMapFile = "TextMap/TextMap" + DOCUMENT_LANGUAGE + ".json";
        try (InputStreamReader fileReader = new InputStreamReader(new FileInputStream(
                Utils.toFilePath(RESOURCE(textMapFile))), StandardCharsets.UTF_8)) {
            map = Grasscutter.getGsonFactory().fromJson(fileReader,
                    new TypeToken<Map<Long, String>>() {
                    }.getType());
        } catch (IOException e) {
            Grasscutter.getLogger().warn("Resource does not exist: " + textMapFile);
            map = new HashMap<>();
        }
    }

    @Override
    public void handle(Request request, Response response) {
        if (map.isEmpty()) {
            response.status(500);
        } else {
            response.set("Content-Type", "application/json")
                    .ctx()
                    .result(createGachaMappingJson());
        }
    }

    private String createGachaMappingJson() {
        List<Integer> list;

        final StringBuilder sb = new StringBuilder();
        list = new ArrayList<>(GameData.getAvatarDataMap().keySet());
        Collections.sort(list);

        final String newLine = System.lineSeparator();

        // if the user made choices for language, I assume it's okay to assign his/her selected language to "en-us"
        // since it's the fallback language and there will be no difference in the gacha record page.
        // The enduser can still modify the `gacha_mappings.js` directly to enable multilingual for the gacha record system.
        sb.append("{").append(newLine);

        // Avatars
        boolean first = true;
        for (Integer id : list) {
            AvatarData data = GameData.getAvatarDataMap().get(id);
            int avatarID = data.getId();
            if (avatarID >= 11000000) { // skip test avatar
                continue;
            }
            if (first) { // skip adding comma for the first element
                first = false;
            } else {
                sb.append(",");
            }
            String color;
            switch (data.getQualityType()) {
                case "QUALITY_PURPLE":
                    color = "purple";
                    break;
                case "QUALITY_ORANGE":
                    color = "yellow";
                    break;
                case "QUALITY_BLUE":
                default:
                    color = "blue";
            }
            // Got the magic number 4233146695 from manually search in the json file
            sb.append("\"")
                    .append(avatarID % 1000 + 1000)
                    .append("\" : [\"")
                    .append(map.get(data.getNameTextMapHash()))
                    .append("(")
                    .append(map.get(4233146695L))
                    .append(")\", \"")
                    .append(color)
                    .append("\"]")
                    .append(newLine);
        }

        list = new ArrayList<>(GameData.getItemDataMap().keySet());
        Collections.sort(list);

        // Weapons
        for (Integer id : list) {
            ItemData data = GameData.getItemDataMap().get(id);
            if (data.getId() <= 11101 || data.getId() >= 20000) {
                continue; //skip non weapon items
            }
            String color;

            switch (data.getRankLevel()) {
                case 3:
                    color = "blue";
                    break;
                case 4:
                    color = "purple";
                    break;
                case 5:
                    color = "yellow";
                    break;
                default:
                    continue; // skip unnecessary entries
            }

            // Got the magic number 4231343903 from manually search in the json file

            sb.append(",\"")
                    .append(data.getId())
                    .append("\" : [\"")
                    .append(map.get(data.getNameTextMapHash()).replaceAll("\"", ""))
                    .append("(")
                    .append(map.get(4231343903L))
                    .append(")\",\"")
                    .append(color)
                    .append("\"]")
                    .append(newLine);
        }
        sb.append(",\"200\": \"")
                .append(map.get(332935371L))
                .append("\", \"301\": \"")
                .append(map.get(2272170627L))
                .append("\", \"302\": \"")
                .append(map.get(2864268523L))
                .append("\"")
                .append("}\n}")
                .append(newLine);
        return sb.toString();
    }
}
