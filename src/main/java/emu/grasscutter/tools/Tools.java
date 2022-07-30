package emu.grasscutter.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.reflect.TypeToken;

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
import emu.grasscutter.utils.Utils;
import emu.grasscutter.utils.Language.TextStrings;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

import static emu.grasscutter.config.Configuration.*;

public final class Tools {
    public static void createGmHandbooks() throws Exception {
        Language savedLanguage = Grasscutter.getLanguage();
        Int2ObjectMap<TextStrings> textMaps = Language.getTextMapStrings();

        ResourceLoader.loadAll();
        Int2IntMap avatarNames = new Int2IntOpenHashMap(GameData.getAvatarDataMap().int2ObjectEntrySet().stream().collect(Collectors.toMap(e -> (int) e.getIntKey(), e -> (int) e.getValue().getNameTextMapHash())));
        Int2IntMap itemNames = new Int2IntOpenHashMap(GameData.getItemDataMap().int2ObjectEntrySet().stream().collect(Collectors.toMap(e -> (int) e.getIntKey(), e -> (int) e.getValue().getNameTextMapHash())));
        Int2IntMap monsterNames = new Int2IntOpenHashMap(GameData.getMonsterDataMap().int2ObjectEntrySet().stream().collect(Collectors.toMap(e -> (int) e.getIntKey(), e -> (int) e.getValue().getNameTextMapHash())));
        Int2IntMap mainQuestTitles = new Int2IntOpenHashMap(GameData.getMainQuestDataMap().int2ObjectEntrySet().stream().collect(Collectors.toMap(e -> (int) e.getIntKey(), e -> (int) e.getValue().getTitleTextMapHash())));
        // Int2IntMap questDescs = new Int2IntOpenHashMap(GameData.getQuestDataMap().int2ObjectEntrySet().stream().collect(Collectors.toMap(e -> (int) e.getIntKey(), e -> (int) e.getValue().getDescTextMapHash())));

        // Preamble
        List<StringBuilder> handbookBuilders = new ArrayList<>(TextStrings.NUM_LANGUAGES);
        String now = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
        for (int i = 0; i < TextStrings.NUM_LANGUAGES; i++)
            handbookBuilders.add(new StringBuilder()
                .append("// Grasscutter " + GameConstants.VERSION + " GM Handbook\n")
                .append("// Created " + now + "\n\n")
                .append("// Commands\n"));
        // Commands
        List<CommandHandler> cmdList = new CommandMap(true).getHandlersAsList();
        final String padCmdLabel = "%" + cmdList.stream().map(CommandHandler::getLabel).map(String::length).max(Integer::compare).get().toString() + "s : ";
        for (CommandHandler cmd : cmdList) {
            String label = padCmdLabel.formatted(cmd.getLabel());
            for (int i = 0; i < TextStrings.NUM_LANGUAGES; i++) {
                Grasscutter.setLanguage(Language.getLanguage(TextStrings.ARR_GC_LANGUAGES[i]));  // A bit hacky but eh whatever
                String desc = cmd.getDescriptionString(null).replace("\n", "\n\t\t\t\t").replace("\t", "    ");
                handbookBuilders.get(i).append(label + desc + "\n");
            }
        }
        // Avatars, Items, Monsters
        String[] handbookSections = {"Avatars", "Items", "Monsters"};
        Int2IntMap[] handbookNames = {avatarNames, itemNames, monsterNames};
        for (int section = 0; section < handbookSections.length; section++) {
            final Int2IntMap h = handbookNames[section];
            final String s = "\n\n// " + handbookSections[section] + "\n";
            handbookBuilders.forEach(b -> b.append(s));
            final String padId = "%" + Integer.toString(Integer.toString(h.keySet().intStream().max().getAsInt()).length()) + "s : ";
            h.keySet().intStream().sorted().forEach(id -> {
                String sId = padId.formatted(id);
                TextStrings t = textMaps.get(h.get(id));
                for (int i = 0; i < TextStrings.NUM_LANGUAGES; i++)
                    handbookBuilders.get(i).append(sId + t.strings[i] + "\n");
            });
        }
        // Scenes - no translations
        handbookBuilders.forEach(b -> b.append("\n\n// Scenes\n"));
        var sceneDataMap = GameData.getSceneDataMap();
        final String padSceneId = "%" + Integer.toString(Integer.toString(sceneDataMap.keySet().intStream().max().getAsInt()).length()) + "d : ";
        sceneDataMap.keySet().intStream().sorted().forEach(id -> {
            String sId = padSceneId.formatted(id);
            String data = sceneDataMap.get(id).getScriptData();
            handbookBuilders.forEach(b -> b.append(sId + data + "\n"));
        });
        // Quests
        handbookBuilders.forEach(b -> b.append("\n\n// Quests\n"));
        var questDataMap = GameData.getQuestDataMap();
        final String padQuestId = "%" + Integer.toString(Integer.toString(questDataMap.keySet().intStream().max().getAsInt()).length()) + "d : ";
        questDataMap.keySet().intStream().sorted().forEach(id -> {
            String sId = padQuestId.formatted(id);
            QuestData data = questDataMap.get(id);
            TextStrings title = textMaps.get((int) mainQuestTitles.get(data.getMainId()));
            TextStrings desc = textMaps.get((int) data.getDescTextMapHash());
            for (int i = 0; i < TextStrings.NUM_LANGUAGES; i++)
                handbookBuilders.get(i).append(sId + title.strings[i] + " - " + desc.strings[i] + "\n");
        });
        Grasscutter.setLanguage(savedLanguage);

        // Write txt files
        for (int i = 0; i < TextStrings.NUM_LANGUAGES; i++) {
            String fileName = "./GM Handbook - %s.txt".formatted(TextStrings.ARR_LANGUAGES[i]);
            try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8), false)) {
                writer.write(handbookBuilders.get(i).toString());
            }
        }
        Grasscutter.getLogger().info("GM Handbooks generated!");
    }

    public static void createGachaMapping(String location) throws Exception {
        ToolsWithLanguageOption.createGachaMapping(location, getLanguageOption());
    }

    public static List<String> getAvailableLanguage() {
        File textMapFolder = new File(RESOURCE("TextMap"));
        List<String> availableLangList = new ArrayList<>();
        for (String textMapFileName : Objects.requireNonNull(textMapFolder.list((dir, name) -> name.startsWith("TextMap") && name.endsWith(".json")))) {
            availableLangList.add(textMapFileName.replace("TextMap", "").replace(".json", "").toLowerCase());
        } return availableLangList;
    }

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

        stagedMessage.append("\nYour choice:[EN] ");

        input = Grasscutter.getConsole().readLine(stagedMessage.toString());
        if (availableLangList.contains(input.toLowerCase())) {
            return input.toUpperCase();
        }
        Grasscutter.getLogger().info("Invalid option. Will use EN(English) as fallback");

        return "EN";
    }
}

final class ToolsWithLanguageOption {
    @SuppressWarnings("deprecation")
    public static void createGachaMapping(String location, String language) throws Exception {
        ResourceLoader.loadResources();

        Map<Long, String> map;
        try (InputStreamReader fileReader = new InputStreamReader(new FileInputStream(Utils.toFilePath(RESOURCE("TextMap/TextMap" + language + ".json"))), StandardCharsets.UTF_8)) {
            map = Grasscutter.getGsonFactory().fromJson(fileReader, new TypeToken<Map<Long, String>>() {}.getType());
        }

        List<Integer> list;

        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(location), StandardCharsets.UTF_8), false)) {
            list = new ArrayList<>(GameData.getAvatarDataMap().keySet());
            Collections.sort(list);

            // if the user made choices for language, I assume it's okay to assign his/her selected language to "en-us"
            // since it's the fallback language and there will be no difference in the gacha record page.
            // The enduser can still modify the `gacha_mappings.js` directly to enable multilingual for the gacha record system.
            writer.println("mappings = {\"en-us\": {");

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
                    writer.print(",");
                }
                String color = switch (data.getQualityType()) {
                    case "QUALITY_PURPLE" -> "purple";
                    case "QUALITY_ORANGE" -> "yellow";
                    default -> "blue";
                };
                // Got the magic number 4233146695 from manually search in the json file
                writer.println(
                    "\"" + (avatarID % 1000 + 1000) + "\" : [\""
                    + map.get(data.getNameTextMapHash()) + "(" +  map.get(4233146695L)+ ")\", \""
                    + color + "\"]");
            }

            writer.println();

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

                writer.println(",\"" + data.getId() +
                         "\" : [\"" + map.get(data.getNameTextMapHash()).replaceAll("\"", "")
                         + "("+ map.get(4231343903L)+")\",\""+ color + "\"]");
            }
            writer.println(",\"200\": \""+map.get(332935371L)+"\", \"301\": \""+ map.get(2272170627L) + "\", \"302\": \""+map.get(2864268523L)+"\"");
            writer.println("}\n}");
        }

        Grasscutter.getLogger().info("Mappings generated to " + location + " !");
    }
}
