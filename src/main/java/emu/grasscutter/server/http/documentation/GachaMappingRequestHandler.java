package emu.grasscutter.server.http.documentation;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.AvatarData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.utils.Language;
import express.http.Request;
import express.http.Response;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

import static emu.grasscutter.config.Configuration.DOCUMENT_LANGUAGE;

import java.util.ArrayList;
import java.util.List;

final class GachaMappingRequestHandler implements DocumentationHandler {
    private List<String> gachaJsons;

    GachaMappingRequestHandler() {
        this.gachaJsons = createGachaMappingJsons();
    }

    @Override
    public void handle(Request request, Response response) {
        final int langIdx = Language.TextStrings.MAP_LANGUAGES.getOrDefault(DOCUMENT_LANGUAGE, 0);  // TODO: This should really be based off the client language somehow
        response.set("Content-Type", "application/json")
                .ctx()
                .result(gachaJsons.get(langIdx));
    }

    private List<String> createGachaMappingJsons() {
        final int NUM_LANGUAGES = Language.TextStrings.NUM_LANGUAGES;
        final Language.TextStrings CHARACTER = Language.getTextMapKey(4233146695L);  // "Character" in EN
        final Language.TextStrings WEAPON = Language.getTextMapKey(4231343903L);  // "Weapon" in EN
        final Language.TextStrings STANDARD_WISH = Language.getTextMapKey(332935371L);  // "Standard Wish" in EN
        final Language.TextStrings CHARACTER_EVENT_WISH = Language.getTextMapKey(2272170627L);  // "Character Event Wish" in EN
        final Language.TextStrings CHARACTER_EVENT_WISH_2 = Language.getTextMapKey(3352513147L);  // "Character Event Wish-2" in EN
        final Language.TextStrings WEAPON_EVENT_WISH = Language.getTextMapKey(2864268523L);  // "Weapon Event Wish" in EN
        final List<StringBuilder> sbs = new ArrayList<>(NUM_LANGUAGES);
        for (int langIdx = 0; langIdx < NUM_LANGUAGES; langIdx++)
            sbs.add(new StringBuilder("{\n"));  // Web requests should never need Windows line endings

        // Avatars
        IntList list = new IntArrayList(GameData.getAvatarDataMap().keySet().intStream().sorted().toArray());
        for (int id : list) {
            AvatarData data = GameData.getAvatarDataMap().get(id);
            int avatarID = data.getId();
            if (avatarID >= 11000000) { // skip test avatar
                continue;
            }
            String color = switch (data.getQualityType()) {
                case "QUALITY_PURPLE" -> "purple";
                case "QUALITY_ORANGE" -> "yellow";
                case "QUALITY_BLUE" -> "blue";
                default -> "";
            };
            Language.TextStrings avatarName = Language.getTextMapKey(data.getNameTextMapHash());
            for (int langIdx = 0; langIdx < NUM_LANGUAGES; langIdx++) {
                sbs.get(langIdx)
                    .append("\"")
                    .append(avatarID % 1000 + 1000)
                    .append("\" : [\"")
                    .append(avatarName.get(langIdx))
                    .append("(")
                    .append(CHARACTER.get(langIdx))
                    .append(")\", \"")
                    .append(color)
                    .append("\"],\n");
            }
        }

        list = new IntArrayList(GameData.getItemDataMap().keySet().intStream().sorted().toArray());

        // Weapons
        for (int id : list) {
            ItemData data = GameData.getItemDataMap().get(id);
            if (data.getId() <= 11101 || data.getId() >= 20000) {
                continue; //skip non weapon items
            }
            String color = switch (data.getRankLevel()) {
                case 3 -> "blue";
                case 4 -> "purple";
                case 5 -> "yellow";
                default -> null;
            };
            if (color == null) continue;  // skip unnecessary entries
            Language.TextStrings weaponName = Language.getTextMapKey(data.getNameTextMapHash());
            for (int langIdx = 0; langIdx < NUM_LANGUAGES; langIdx++) {
                sbs.get(langIdx)
                    .append("\"")
                    .append(data.getId())
                    .append("\" : [\"")
                    .append(weaponName.get(langIdx).replaceAll("\"", "\\\\\""))
                    .append("(")
                    .append(WEAPON.get(langIdx))
                    .append(")\",\"")
                    .append(color)
                    .append("\"],\n");
            }
        }

        for (int langIdx = 0; langIdx < NUM_LANGUAGES; langIdx++) {
            sbs.get(langIdx)
                .append("\"200\": \"")
                .append(STANDARD_WISH.get(langIdx))
                .append("\", \"301\": \"")
                .append(CHARACTER_EVENT_WISH.get(langIdx))
                .append("\", \"400\": \"")
                .append(CHARACTER_EVENT_WISH_2.get(langIdx))
                .append("\", \"302\": \"")
                .append(WEAPON_EVENT_WISH.get(langIdx))
                .append("\"\n}\n");
        }
        return sbs.stream().map(StringBuilder::toString).toList();
    }
}
