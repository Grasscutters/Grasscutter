package emu.grasscutter.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import emu.grasscutter.GameConstants;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.command.CommandMap;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.ResourceLoader;
import emu.grasscutter.data.excels.AvatarData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.data.excels.QuestData;
import emu.grasscutter.utils.Language;
import emu.grasscutter.utils.Language.TextStrings;
import it.unimi.dsi.fastutil.ints.Int2IntSortedMap;
import it.unimi.dsi.fastutil.ints.Int2IntRBTreeMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

import static emu.grasscutter.config.Configuration.*;
import static emu.grasscutter.utils.FileUtils.getResourcePath;

public final class Tools {
    public static void createGmHandbooks() throws Exception {
        final List<Language> languages = Language.TextStrings.getLanguages();
        final Int2ObjectMap<TextStrings> textMaps = Language.getTextMapStrings();

        ResourceLoader.loadAll();
        final Int2IntSortedMap avatarNames = new Int2IntRBTreeMap(GameData.getAvatarDataMap().int2ObjectEntrySet().stream().collect(Collectors.toMap(e -> (int) e.getIntKey(), e -> (int) e.getValue().getNameTextMapHash())));
        final Int2IntSortedMap itemNames = new Int2IntRBTreeMap(GameData.getItemDataMap().int2ObjectEntrySet().stream().collect(Collectors.toMap(e -> (int) e.getIntKey(), e -> (int) e.getValue().getNameTextMapHash())));
        final Int2IntSortedMap monsterNames = new Int2IntRBTreeMap(GameData.getMonsterDataMap().int2ObjectEntrySet().stream().collect(Collectors.toMap(e -> (int) e.getIntKey(), e -> (int) e.getValue().getNameTextMapHash())));
        final Int2IntSortedMap mainQuestTitles = new Int2IntRBTreeMap(GameData.getMainQuestDataMap().int2ObjectEntrySet().stream().collect(Collectors.toMap(e -> (int) e.getIntKey(), e -> (int) e.getValue().getTitleTextMapHash())));
        // Int2IntSortedMap questDescs = new Int2IntRBTreeMap(GameData.getQuestDataMap().int2ObjectEntrySet().stream().collect(Collectors.toMap(e -> (int) e.getIntKey(), e -> (int) e.getValue().getDescTextMapHash())));

        // Preamble
        final List<StringBuilder> handbookBuilders = new ArrayList<>(TextStrings.NUM_LANGUAGES);
        final String now = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
        for (int i = 0; i < TextStrings.NUM_LANGUAGES; i++)
            handbookBuilders.add(new StringBuilder()
                .append("// Grasscutter " + GameConstants.VERSION + " GM Handbook\n")
                .append("// Created " + now + "\n\n")
                .append("// Commands\n"));
        // Commands
        final List<CommandHandler> cmdList = CommandMap.getInstance().getHandlersAsList();
        final String padCmdLabel = "%" + cmdList.stream().map(CommandHandler::getLabel).map(String::length).max(Integer::compare).get().toString() + "s : ";
        for (CommandHandler cmd : cmdList) {
            final String label = padCmdLabel.formatted(cmd.getLabel());
            final String descKey = cmd.getDescriptionKey();
            for (int i = 0; i < TextStrings.NUM_LANGUAGES; i++) {
                String desc = languages.get(i).get(descKey).replace("\n", "\n\t\t\t\t").replace("\t", "    ");
                handbookBuilders.get(i).append(label + desc + "\n");
            }
        }
        // Avatars, Items, Monsters
        final String[] handbookSections = {"Avatars", "Items", "Monsters"};
        final Int2IntSortedMap[] handbookNames = {avatarNames, itemNames, monsterNames};
        for (int section = 0; section < handbookSections.length; section++) {
            final var h = handbookNames[section];
            final String s = "\n\n// " + handbookSections[section] + "\n";
            handbookBuilders.forEach(b -> b.append(s));
            final String padId = "%" + Integer.toString(h.keySet().lastInt()).length() + "s : ";
            h.forEach((id, hash) -> {
                final String sId = padId.formatted(id);
                final TextStrings t = textMaps.get((int) hash);
                for (int i = 0; i < TextStrings.NUM_LANGUAGES; i++)
                    handbookBuilders.get(i).append(sId + t.strings[i] + "\n");
            });
        }
        // Scenes - no translations
        handbookBuilders.forEach(b -> b.append("\n\n// Scenes\n"));
        final var sceneDataMap = GameData.getSceneDataMap();
        final String padSceneId = "%" + Integer.toString(sceneDataMap.keySet().intStream().max().getAsInt()).length() + "d : ";
        sceneDataMap.keySet().intStream().sorted().forEach(id -> {
            final String sId = padSceneId.formatted(id);
            final String data = sceneDataMap.get(id).getScriptData();
            handbookBuilders.forEach(b -> b.append(sId + data + "\n"));
        });
        // Quests
        handbookBuilders.forEach(b -> b.append("\n\n// Quests\n"));
        final var questDataMap = GameData.getQuestDataMap();
        final String padQuestId = "%" + Integer.toString(questDataMap.keySet().intStream().max().getAsInt()).length() + "d : ";
        questDataMap.keySet().intStream().sorted().forEach(id -> {
            final String sId = padQuestId.formatted(id);
            final QuestData data = questDataMap.get(id);
            final TextStrings title = textMaps.get((int) mainQuestTitles.get(data.getMainId()));
            final TextStrings desc = textMaps.get((int) data.getDescTextMapHash());
            for (int i = 0; i < TextStrings.NUM_LANGUAGES; i++)
                handbookBuilders.get(i).append(sId + title.strings[i] + " - " + desc.strings[i] + "\n");
        });

        // Write txt files
        for (int i = 0; i < TextStrings.NUM_LANGUAGES; i++) {
            File GMHandbookOutputpath=new File("./GM Handbook");
            GMHandbookOutputpath.mkdir();
            final String fileName = "./GM Handbook/GM Handbook - %s.txt".formatted(TextStrings.ARR_LANGUAGES[i]);
            try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8), false)) {
                writer.write(handbookBuilders.get(i).toString());
            }
        }
        Grasscutter.getLogger().info("GM Handbooks generated!");
    }

    public static List<String> createGachaMappingJsons() {
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
        GameData.getAvatarDataMap().keySet().intStream().sorted().forEach(id -> {
            AvatarData data = GameData.getAvatarDataMap().get(id);
            int avatarID = data.getId();
            if (avatarID >= 11000000) { // skip test avatar
                return;
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
                    .append("\t\"")
                    .append(avatarID % 1000 + 1000)
                    .append("\": [\"")
                    .append(avatarName.get(langIdx))
                    .append(" (")
                    .append(CHARACTER.get(langIdx))
                    .append(")\", \"")
                    .append(color)
                    .append("\"],\n");
            }
        });

        // Weapons
        GameData.getItemDataMap().keySet().intStream().sorted().forEach(id -> {
            ItemData data = GameData.getItemDataMap().get(id);
            if (data.getId() <= 11101 || data.getId() >= 20000) {
                return; //skip non weapon items
            }
            String color = switch (data.getRankLevel()) {
                case 3 -> "blue";
                case 4 -> "purple";
                case 5 -> "yellow";
                default -> null;
            };
            if (color == null) return;  // skip unnecessary entries
            Language.TextStrings weaponName = Language.getTextMapKey(data.getNameTextMapHash());
            for (int langIdx = 0; langIdx < NUM_LANGUAGES; langIdx++) {
                sbs.get(langIdx)
                    .append("\t\"")
                    .append(data.getId())
                    .append("\": [\"")
                    .append(weaponName.get(langIdx).replaceAll("\"", "\\\\\""))
                    .append(" (")
                    .append(WEAPON.get(langIdx))
                    .append(")\", \"")
                    .append(color)
                    .append("\"],\n");
            }
        });

        for (int langIdx = 0; langIdx < NUM_LANGUAGES; langIdx++) {
            sbs.get(langIdx)
                .append("\t\"200\": \"")
                .append(STANDARD_WISH.get(langIdx))
                .append("\",\n\t\"301\": \"")
                .append(CHARACTER_EVENT_WISH.get(langIdx))
                .append("\",\n\t\"400\": \"")
                .append(CHARACTER_EVENT_WISH_2.get(langIdx))
                .append("\",\n\t\"302\": \"")
                .append(WEAPON_EVENT_WISH.get(langIdx))
                .append("\"\n}");
        }
        return sbs.stream().map(StringBuilder::toString).toList();
    }

    public static void createGachaMappings(Path location) throws IOException {
        ResourceLoader.loadResources();
        List<String> jsons = createGachaMappingJsons();
        StringBuilder sb = new StringBuilder("mappings = {\n");
        for (int i = 0; i < Language.TextStrings.NUM_LANGUAGES; i++) {
            sb.append("\t\"%s\": ".formatted(Language.TextStrings.ARR_GC_LANGUAGES[i].toLowerCase()));  // TODO: change the templates to not use lowercased locale codes
            sb.append(jsons.get(i).replace("\n", "\n\t") + ",\n");
        }
        sb.setLength(sb.length() - 2);  // Delete trailing ",\n"
        sb.append("\n}");

        Files.createDirectories(location.getParent());
        Files.writeString(location, sb);
        Grasscutter.getLogger().info("Mappings generated to " + location);
    }

    public static List<String> getAvailableLanguage() {
        List<String> availableLangList = new ArrayList<>();
        try {
            Files.newDirectoryStream(getResourcePath("TextMap"), "TextMap*.json").forEach(path -> {
                availableLangList.add(path.getFileName().toString().replace("TextMap", "").replace(".json", "").toLowerCase());
            });
        } catch (IOException e) {
            Grasscutter.getLogger().error("Failed to get available languages:", e);
        }
        return availableLangList;
    }

    @Deprecated(forRemoval = true, since = "1.2.3")
    public static String getLanguageOption() {
        List<String> availableLangList = getAvailableLanguage();

        // Use system out for better format
        if (availableLangList.size() == 1) {
            return availableLangList.get(0).toUpperCase();
        }
        StringBuilder stagedMessage = new StringBuilder();
        stagedMessage.append("The following languages mappings are available, please select one: [default: EN] \n");

        StringBuilder groupedLangList = new StringBuilder(">\t"); String input;
        int groupedLangCount = 0;

        for (String availableLanguage: availableLangList) {
            groupedLangCount++;
            groupedLangList.append(availableLanguage).append("\t");

            if (groupedLangCount == 6) {
                stagedMessage.append(groupedLangList).append("\n");
                groupedLangCount = 0;
                groupedLangList = new StringBuilder(">\t");
            }
        }

        if (groupedLangCount > 0) {
            stagedMessage.append(groupedLangList).append("\n");
        }

        stagedMessage.append("\nYour choice: [EN] ");

        input = Grasscutter.getConsole().readLine(stagedMessage.toString());
        if (availableLangList.contains(input.toLowerCase())) {
            return input.toUpperCase();
        }

        Grasscutter.getLogger().info("Invalid option. Will use EN (English) as fallback."); return "EN";
    }
}
