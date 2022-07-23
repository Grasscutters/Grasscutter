package emu.grasscutter.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.gson.reflect.TypeToken;

import emu.grasscutter.GameConstants;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.command.CommandMap;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.ResourceLoader;
import emu.grasscutter.data.binout.MainQuestData;
import emu.grasscutter.data.excels.AvatarData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.data.excels.MonsterData;
import emu.grasscutter.data.excels.QuestData;
import emu.grasscutter.data.excels.SceneData;
import emu.grasscutter.utils.Language;
import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import lombok.EqualsAndHashCode;

import static emu.grasscutter.config.Configuration.*;
import static emu.grasscutter.utils.Language.translate;

public final class Tools {
    @EqualsAndHashCode public static class TextStrings {
        public static final String[] ARR_LANGUAGES = {"EN", "CHS", "CHT", "JP", "KR", "DE", "ES", "FR", "ID", "PT", "RU", "TH", "VI"};
        public static final String[] ARR_GC_LANGUAGES = {"en-US", "zh-CN", "zh-TW", "JP", "KR", "DE", "es-ES", "fr-FR", "ID", "PT", "ru-RU", "TH", "VI"};
        public static final int NUM_LANGUAGES = ARR_LANGUAGES.length;
        public static final List<String> LIST_LANGUAGES = Arrays.asList(ARR_LANGUAGES);
        public static final Object2IntMap<String> MAP_LANGUAGES =  // Map "EN": 0, "CHS": 1, ..., "VI": 12
            new Object2IntOpenHashMap<>(
                IntStream.range(0, ARR_LANGUAGES.length)
                .boxed()
                .collect(Collectors.toMap(i -> ARR_LANGUAGES[i], i -> i)));
        public String[] strings = new String[ARR_LANGUAGES.length];

        public TextStrings() {};

        public TextStrings(String init) {
            for (int i = 0; i < NUM_LANGUAGES; i++)
                this.strings[i] = init;
        };

        public TextStrings(Collection<String> strings) {
            this.strings = strings.toArray(new String[0]);
        }

        public String get(String languageCode) {
            return strings[MAP_LANGUAGES.getOrDefault(languageCode, 0)];
        }

        public boolean set(String languageCode, String string) {
            int index = MAP_LANGUAGES.getOrDefault(languageCode, -1);
            if (index < 0) return false;
            strings[index] = string;
            return true;
        }
    }

    private static final Pattern textMapKeyValueRegex = Pattern.compile("\"(\\d+)\": \"(.+)\"");

    private static Int2ObjectMap<String> loadTextMap(String language, IntSet nameHashes) {
        Int2ObjectMap<String> output = new Int2ObjectOpenHashMap<>();
        try (BufferedReader file = new BufferedReader(new FileReader(Utils.toFilePath(RESOURCE("TextMap/TextMap"+language+".json")), StandardCharsets.UTF_8))) {
            Matcher matcher = textMapKeyValueRegex.matcher("");
            return new Int2ObjectOpenHashMap<>(
                file.lines()
                    .sequential()
                    .map(matcher::reset)  // Side effects, but it's faster than making a new one
                    .filter(Matcher::find)
                    .filter(m -> nameHashes.contains((int) Long.parseLong(m.group(1))))  // TODO: Cache this parse somehow
                    .collect(Collectors.toMap(
                        m -> (int) Long.parseLong(m.group(1)),
                        m -> m.group(2))));
        } catch (Exception e) {
            Grasscutter.getLogger().error("Error loading textmap: " + language);
            Grasscutter.getLogger().error(e.toString());
        }
        return output;
    }

    public static Int2ObjectMap<TextStrings> loadTextMaps(IntSet nameHashes) {
        Map<Integer, Int2ObjectMap<String>> mapLanguageMaps =  // Separate step to process the textmaps in parallel
            TextStrings.LIST_LANGUAGES.parallelStream().collect(
            Collectors.toConcurrentMap(s -> TextStrings.MAP_LANGUAGES.getInt(s), s -> loadTextMap(s, nameHashes)));
        List<Int2ObjectMap<String>> languageMaps = 
            IntStream.range(0, TextStrings.NUM_LANGUAGES)
            .mapToObj(i -> mapLanguageMaps.get(i))
            .collect(Collectors.toList());

        Map<TextStrings, TextStrings> canonicalTextStrings = new HashMap<>();
        return new Int2ObjectOpenHashMap<TextStrings>(
            nameHashes
            .intStream()
            .boxed()
            .collect(Collectors.toMap(key -> key, key -> {
                TextStrings t = new TextStrings(
                    IntStream.range(0, TextStrings.NUM_LANGUAGES)
                    .mapToObj(i -> languageMaps.get(i).getOrDefault((int) key, "[N/A] - hash key %d".formatted(key)))
                    .collect(Collectors.toList()));
                return canonicalTextStrings.computeIfAbsent(t, x -> t);
                }))
            );
    }

    public static void createGmHandbooks() throws Exception {
        ResourceLoader.loadAll();
        Int2IntMap avatarNames = new Int2IntOpenHashMap(GameData.getAvatarDataMap().int2ObjectEntrySet().stream().collect(Collectors.toMap(e -> (int) e.getIntKey(), e -> (int) e.getValue().getNameTextMapHash())));
        Int2IntMap itemNames = new Int2IntOpenHashMap(GameData.getItemDataMap().int2ObjectEntrySet().stream().collect(Collectors.toMap(e -> (int) e.getIntKey(), e -> (int) e.getValue().getNameTextMapHash())));
        Int2IntMap monsterNames = new Int2IntOpenHashMap(GameData.getMonsterDataMap().int2ObjectEntrySet().stream().collect(Collectors.toMap(e -> (int) e.getIntKey(), e -> (int) e.getValue().getNameTextMapHash())));
        Int2IntMap mainQuestTitles = new Int2IntOpenHashMap(GameData.getMainQuestDataMap().int2ObjectEntrySet().stream().collect(Collectors.toMap(e -> (int) e.getIntKey(), e -> (int) e.getValue().getTitleTextMapHash())));
        Int2IntMap questDescs = new Int2IntOpenHashMap(GameData.getQuestDataMap().int2ObjectEntrySet().stream().collect(Collectors.toMap(e -> (int) e.getIntKey(), e -> (int) e.getValue().getDescTextMapHash())));
        
        IntSet usedHashes = new IntOpenHashSet();
        usedHashes.addAll(avatarNames.values());
        usedHashes.addAll(itemNames.values());
        usedHashes.addAll(monsterNames.values());
        usedHashes.addAll(mainQuestTitles.values());
        usedHashes.addAll(questDescs.values());
        
        Int2ObjectMap<TextStrings> textMaps = loadTextMaps(usedHashes);

        Language savedLanguage = Grasscutter.getLanguage();

        // Preamble
        StringBuilder[] handbookBuilders = new StringBuilder[TextStrings.NUM_LANGUAGES];
        String now = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
        for (int i = 0; i < TextStrings.NUM_LANGUAGES; i++) {
            handbookBuilders[i] = new StringBuilder()
                .append("// Grasscutter " + GameConstants.VERSION + " GM Handbook\n")
                .append("// Created " + now + "\n\n")
                .append("// Commands\n");
        }
        // Commands
        List<CommandHandler> cmdList = new CommandMap(true).getHandlersAsList();
        for (CommandHandler cmd : cmdList) {
            StringBuilder cmdName = new StringBuilder(cmd.getLabel());
            while (cmdName.length() <= 15) {
                cmdName.insert(0, " ");
            }
            for (int i = 0; i < TextStrings.NUM_LANGUAGES; i++) {
                Grasscutter.setLanguage(Language.getLanguage(TextStrings.ARR_GC_LANGUAGES[i]));  // A bit hacky but eh whatever
                handbookBuilders[i]
                    .append(cmdName + " : ")
                    .append(cmd.getDescriptionString(null).replace("\n", "\n\t\t\t\t").replace("\t", "    "))
                    .append("\n");
            }
        }
        // Avatars
        for (int i = 0; i < TextStrings.NUM_LANGUAGES; i++) {
            handbookBuilders[i].append("\n\n// Avatars\n");
        }
        avatarNames.keySet().intStream().sorted().forEach(id -> {
            TextStrings t = textMaps.get(avatarNames.get(id));
            for (int i = 0; i < TextStrings.NUM_LANGUAGES; i++) {
                handbookBuilders[i]
                    .append("%d : ".formatted(id))
                    .append(t.strings[i])
                    .append("\n");
            }
        });
        // Items
        for (int i = 0; i < TextStrings.NUM_LANGUAGES; i++) {
            handbookBuilders[i].append("\n\n// Items\n");
        }
        itemNames.keySet().intStream().sorted().forEach(id -> {
            TextStrings t = textMaps.get(itemNames.get(id));
            for (int i = 0; i < TextStrings.NUM_LANGUAGES; i++) {
                handbookBuilders[i]
                    .append("%d : ".formatted(id))
                    .append(t.strings[i])
                    .append("\n");
            }
        });
        // Monsters
        for (int i = 0; i < TextStrings.NUM_LANGUAGES; i++) {
            handbookBuilders[i].append("\n\n// Monsters\n");
        }
        monsterNames.keySet().intStream().sorted().forEach(id -> {
            TextStrings t = textMaps.get(monsterNames.get(id));
            for (int i = 0; i < TextStrings.NUM_LANGUAGES; i++) {
                handbookBuilders[i]
                    .append("%d : ".formatted(id))
                    .append(t.strings[i])
                    .append("\n");
            }
        });
        // Scenes - no translations
        for (int i = 0; i < TextStrings.NUM_LANGUAGES; i++) {
            handbookBuilders[i].append("\n\n// Scenes\n");
        }
        var sceneDataMap = GameData.getSceneDataMap();
        sceneDataMap.keySet().intStream().sorted().forEach(id -> {
            String data = sceneDataMap.get(id).getScriptData();
            for (int i = 0; i < TextStrings.NUM_LANGUAGES; i++) {
                handbookBuilders[i]
                    .append("%d : ".formatted(id))
                    .append(data)
                    .append("\n");
            }
        });
        // Quests
        for (int i = 0; i < TextStrings.NUM_LANGUAGES; i++) {
            handbookBuilders[i].append("\n\n// Quests\n");
        }
        var questDataMap = GameData.getQuestDataMap();
        questDataMap.keySet().intStream().sorted().forEach(id -> {
            QuestData data = questDataMap.get(id);
            int titleKey = (int) mainQuestTitles.get(data.getMainId());
            int descKey = (int) data.getDescTextMapHash();
            TextStrings title = textMaps.get(titleKey);
            TextStrings desc = textMaps.get(descKey);
            for (int i = 0; i < TextStrings.NUM_LANGUAGES; i++) {
                handbookBuilders[i]
                    .append(id)
                    .append(" : ")
                    .append(title.strings[i])
                    .append(" - ")
                    .append(desc.strings[i])
                    .append("\n");
            }
        });
        Grasscutter.setLanguage(savedLanguage);

        // Write txt files
        for (int i = 0; i < TextStrings.NUM_LANGUAGES; i++) {
            String fileName = "./GM Handbook - %s.txt".formatted(TextStrings.ARR_LANGUAGES[i]);
            try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8), false)) {
                writer.write(handbookBuilders[i].toString());
            }
        }
        Grasscutter.getLogger().info("GM Handbooks generated!");
    }

    public static void createGmHandbook() throws Exception {
        ToolsWithLanguageOption.createGmHandbook(getLanguageOption());
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
    public static void createGmHandbook(String language) throws Exception {
        ResourceLoader.loadAll();

        Map<Long, String> map;
        try (InputStreamReader fileReader = new InputStreamReader(new FileInputStream(Utils.toFilePath(RESOURCE("TextMap/TextMap"+language+".json"))), StandardCharsets.UTF_8)) {
            map = Grasscutter.getGsonFactory().fromJson(fileReader, new TypeToken<Map<Long, String>>() {}.getType());
        }

        List<Integer> list;
        String fileName = "./GM Handbook.txt";
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8), false)) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            writer.println("// Grasscutter " + GameConstants.VERSION + " GM Handbook");
            writer.println("// Created " + dtf.format(now) + System.lineSeparator() + System.lineSeparator());

			List<CommandHandler> cmdList = new CommandMap(true).getHandlersAsList();

            writer.println("// Commands");
			for (CommandHandler cmd : cmdList) {
				StringBuilder cmdName = new StringBuilder(cmd.getLabel());
                while (cmdName.length() <= 15) {
                    cmdName.insert(0, " ");
                }
				writer.println(cmdName + " : " + cmd.getDescriptionString(null));
            }
            writer.println();

            list = new ArrayList<>(GameData.getAvatarDataMap().keySet());
            Collections.sort(list);

            writer.println("// Avatars");
            for (Integer id : list) {
                AvatarData data = GameData.getAvatarDataMap().get(id);
                writer.println(data.getId() + " : " + map.get(data.getNameTextMapHash()));
            }

            writer.println();

            list = new ArrayList<>(GameData.getItemDataMap().keySet());
            Collections.sort(list);

            writer.println("// Items");
            for (Integer id : list) {
                ItemData data = GameData.getItemDataMap().get(id);
                writer.println(data.getId() + " : " + map.get(data.getNameTextMapHash()));
            }

            writer.println();

            writer.println("// Scenes");
            list = new ArrayList<>(GameData.getSceneDataMap().keySet());
            Collections.sort(list);

            for (Integer id : list) {
                SceneData data = GameData.getSceneDataMap().get(id);
                writer.println(data.getId() + " : " + data.getScriptData());
            }

            writer.println();

            writer.println("// Quests");
            list = new ArrayList<>(GameData.getQuestDataMap().keySet());
            Collections.sort(list);

            for (Integer id : list) {
                QuestData data = GameData.getQuestDataMap().get(id);
                MainQuestData mainQuest = GameData.getMainQuestDataMap().get(data.getMainId());
                if (mainQuest != null) {
                    writer.println(data.getId() + " : " + map.get(mainQuest.getTitleTextMapHash()) + " - " + map.get(data.getDescTextMapHash()));
                }
            }

            writer.println();

            writer.println("// Monsters");
            list = new ArrayList<>(GameData.getMonsterDataMap().keySet());
            Collections.sort(list);

            for (Integer id : list) {
                MonsterData data = GameData.getMonsterDataMap().get(id);
                writer.println(data.getId() + " : " + map.get(data.getNameTextMapHash()));
            }
        }

        Grasscutter.getLogger().info("GM Handbook generated!");
    }

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
