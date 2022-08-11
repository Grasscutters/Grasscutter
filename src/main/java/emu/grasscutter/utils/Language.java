package emu.grasscutter.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.ResourceLoader;
import emu.grasscutter.game.player.Player;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import lombok.EqualsAndHashCode;

import javax.annotation.Nullable;

import static emu.grasscutter.config.Configuration.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Language {
    private static final Map<String, Language> cachedLanguages = new ConcurrentHashMap<>();

    private final JsonObject languageData;
    private final String languageCode;
    private final Map<String, String> cachedTranslations = new ConcurrentHashMap<>();

    /**
     * Creates a language instance from a code.
     * @param langCode The language code.
     * @return A language instance.
     */
    public static Language getLanguage(String langCode) {
        if (cachedLanguages.containsKey(langCode)) {
            return cachedLanguages.get(langCode);
        }

        var fallbackLanguageCode = Utils.getLanguageCode(FALLBACK_LANGUAGE);
        var description = getLanguageFileDescription(langCode, fallbackLanguageCode);
        var actualLanguageCode = description.getLanguageCode();

        Language languageInst;
        if (description.getLanguageFile() != null) {
            languageInst = new Language(description);
            cachedLanguages.put(actualLanguageCode, languageInst);
        } else {
            languageInst = cachedLanguages.get(actualLanguageCode);
            cachedLanguages.put(langCode, languageInst);
        }

        return languageInst;
    }

    /**
     * Returns the translated value from the key while substituting arguments.
     * @param key The key of the translated value to return.
     * @param args The arguments to substitute.
     * @return A translated value with arguments substituted.
     */
    public static String translate(String key, Object... args) {
        String translated = Grasscutter.getLanguage().get(key);

        try {
            return translated.formatted(args);
        } catch (Exception exception) {
            Grasscutter.getLogger().error("Failed to format string: " + key, exception);
            return translated;
        }
    }

    /**
     * Returns the translated value from the key while substituting arguments.
     * @param player Target player
     * @param key The key of the translated value to return.
     * @param args The arguments to substitute.
     * @return A translated value with arguments substituted.
     */
    public static String translate(Player player, String key, Object... args) {
        if (player == null) {
            return translate(key, args);
        }

        var langCode = Utils.getLanguageCode(player.getAccount().getLocale());
        String translated = getLanguage(langCode).get(key);

        try {
            return translated.formatted(args);
        } catch (Exception exception) {
            Grasscutter.getLogger().error("Failed to format string: " + key, exception);
            return translated;
        }
    }

    /**
     * get language code
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Reads a file and creates a language instance.
     */
    private Language(LanguageStreamDescription description) {
        @Nullable JsonObject languageData = null;
        languageCode = description.getLanguageCode();

        try {
            languageData = JsonUtils.decode(Utils.readFromInputStream(description.getLanguageFile()), JsonObject.class);
        } catch (Exception exception) {
            Grasscutter.getLogger().warn("Failed to load language file: " + description.getLanguageCode(), exception);
        }

        this.languageData = languageData;
    }

    /**
     * create a LanguageStreamDescription
     * @param languageCode The name of the language code.
     * @param fallbackLanguageCode The name of the fallback language code.
     */
    private static LanguageStreamDescription getLanguageFileDescription(String languageCode, String fallbackLanguageCode) {
        var fileName = languageCode + ".json";
        var fallback = fallbackLanguageCode + ".json";

        String actualLanguageCode = languageCode;
        InputStream file = Grasscutter.class.getResourceAsStream("/languages/" + fileName);

        if (file == null) { // Provided fallback language.
            Grasscutter.getLogger().warn("Failed to load language file: " + fileName + ", falling back to: " + fallback);
            actualLanguageCode = fallbackLanguageCode;
            if (cachedLanguages.containsKey(actualLanguageCode)) {
                return new LanguageStreamDescription(actualLanguageCode, null);
            }

            file = Grasscutter.class.getResourceAsStream("/languages/" + fallback);
        }

        if (file == null) { // Fallback the fallback language.
            Grasscutter.getLogger().warn("Failed to load language file: " + fallback + ", falling back to: en-US.json");
            actualLanguageCode = "en-US";
            if (cachedLanguages.containsKey(actualLanguageCode)) {
                return new LanguageStreamDescription(actualLanguageCode, null);
            }

            file = Grasscutter.class.getResourceAsStream("/languages/en-US.json");
        }

        if (file == null)
            throw new RuntimeException("Unable to load the primary, fallback, and 'en-US' language files.");

        return new LanguageStreamDescription(actualLanguageCode, file);
    }

    /**
     * Returns the value (as a string) from a nested key.
     * @param key The key to look for.
     * @return The value (as a string) from a nested key.
     */
    public String get(String key) {
        if (this.cachedTranslations.containsKey(key)) {
            return this.cachedTranslations.get(key);
        }

        String[] keys = key.split("\\.");
        JsonObject object = this.languageData;

        int index = 0;
        String valueNotFoundPattern = "This value does not exist. Please report this to the Discord: ";
        String result = valueNotFoundPattern + key;
        boolean isValueFound = false;

        while (true) {
            if (index == keys.length) break;

            String currentKey = keys[index++];
            if (object.has(currentKey)) {
                JsonElement element = object.get(currentKey);
                if (element.isJsonObject())
                    object = element.getAsJsonObject();
                else {
                    isValueFound = true;
                    result = element.getAsString(); break;
                }
            } else break;
        }

        if (!isValueFound && !languageCode.equals("en-US")) {
            var englishValue = getLanguage("en-US").get(key);
            if (!englishValue.contains(valueNotFoundPattern)) {
                result += "\nhere is english version:\n" + englishValue;
            }
        }

        this.cachedTranslations.put(key, result); return result;
    }

    private static class LanguageStreamDescription {
        private final String languageCode;
        private final InputStream languageFile;

        public LanguageStreamDescription(String languageCode, InputStream languageFile) {
            this.languageCode = languageCode;
            this.languageFile = languageFile;
        }

        public String getLanguageCode() {
            return languageCode;
        }

        public InputStream getLanguageFile() {
            return languageFile;
        }
    }

    private static final int TEXTMAP_CACHE_VERSION = 0x9CCACE02;
    @EqualsAndHashCode public static class TextStrings implements Serializable {
        public static final String[] ARR_LANGUAGES = {"EN", "CHS", "CHT", "JP", "KR", "DE", "ES", "FR", "ID", "PT", "RU", "TH", "VI"};
        public static final String[] ARR_GC_LANGUAGES = {"en-US", "zh-CN", "zh-TW", "en-US", "ko-KR", "en-US", "es-ES", "fr-FR", "en-US", "en-US", "ru-RU", "en-US", "en-US"};  // TODO: Update the placeholder en-US entries if we ever add GC translations for the missing client languages
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

        public TextStrings(List<String> strings, int key) {
            // Some hashes don't have strings for some languages :(
            String nullReplacement = "[N/A] %d".formatted((long) key & 0xFFFFFFFFL);
            for (int i = 0; i < NUM_LANGUAGES; i++) {  // Find first non-null if there is any
                String s = strings.get(i);
                if (s != null) {
                    nullReplacement = "[%s] - %s".formatted(ARR_LANGUAGES[i], s);
                    break;
                }
            }
            for (int i = 0; i < NUM_LANGUAGES; i++) {
                String s = strings.get(i);
                if (s != null)
                    this.strings[i] = s;
                else
                    this.strings[i] = nullReplacement;
            }
        }

        public static List<Language> getLanguages() {
            return Arrays.stream(ARR_GC_LANGUAGES).map(Language::getLanguage).toList();
        }

        public String get(int languageIndex) {
            return strings[languageIndex];
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

    private static Int2ObjectMap<String> loadTextMapFile(String language, IntSet nameHashes) {
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
                        m -> m.group(2).replace("\\\"", "\""))));
        } catch (Exception e) {
            Grasscutter.getLogger().error("Error loading textmap: " + language);
            Grasscutter.getLogger().error(e.toString());
        }
        return output;
    }

    private static Int2ObjectMap<TextStrings> loadTextMapFiles(IntSet nameHashes) {
        Map<Integer, Int2ObjectMap<String>> mapLanguageMaps =  // Separate step to process the textmaps in parallel
            TextStrings.LIST_LANGUAGES.parallelStream().collect(
            Collectors.toConcurrentMap(s -> TextStrings.MAP_LANGUAGES.getInt(s), s -> loadTextMapFile(s, nameHashes)));
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
                    .mapToObj(i -> languageMaps.get(i).get((int) key))
                    .collect(Collectors.toList()), (int) key);
                return canonicalTextStrings.computeIfAbsent(t, x -> t);
                }))
            );
    }

    private static Int2ObjectMap<TextStrings> loadTextMapsCache() throws Exception {
        try (ObjectInputStream file = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(TEXTMAP_CACHE_PATH), 0x100000))) {
            final int fileVersion = file.readInt();
            if (fileVersion != TEXTMAP_CACHE_VERSION)
                throw new Exception("Invalid cache version");
            return (Int2ObjectMap<TextStrings>) file.readObject();
        }
    }

    private static void saveTextMapsCache(Int2ObjectMap<TextStrings> input) throws IOException {
        try {
            Files.createDirectory(Path.of("cache"));
        } catch (FileAlreadyExistsException ignored) {};
        try (ObjectOutputStream file = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(TEXTMAP_CACHE_PATH, StandardOpenOption.CREATE), 0x100000))) {
            file.writeInt(TEXTMAP_CACHE_VERSION);
            file.writeObject(input);
        }
    }

    private static Int2ObjectMap<TextStrings> textMapStrings;
    private static final Path TEXTMAP_CACHE_PATH = Path.of(Utils.toFilePath("cache/TextMapCache.bin"));

    public static Int2ObjectMap<TextStrings> getTextMapStrings() {
        if (textMapStrings == null)
            loadTextMaps();
        return textMapStrings;
    }

    public static TextStrings getTextMapKey(long hash) {
        if (textMapStrings == null)
            loadTextMaps();
        return textMapStrings.get((int) hash);
    }

    public static void loadTextMaps() {
        // Check system timestamps on cache and resources
        try {
            long cacheModified = Files.getLastModifiedTime(TEXTMAP_CACHE_PATH).toMillis();

            long textmapsModified = Files.list(Path.of(RESOURCE("TextMap")))
                .filter(path -> path.toString().endsWith(".json"))
                .map(path -> {
                    try {
                        return Files.getLastModifiedTime(path).toMillis();
                    } catch (Exception ignored) {
                        Grasscutter.getLogger().debug("Exception while checking modified time: ", path);
                        return Long.MAX_VALUE;  // Don't use cache, something has gone wrong
                    }
                })
                .max(Long::compare)
                .get();

                Grasscutter.getLogger().debug("Cache modified %d, textmap modified %d".formatted(cacheModified, textmapsModified));
            if (textmapsModified < cacheModified) {
                // Try loading from cache
                Grasscutter.getLogger().info("Loading cached TextMaps");
                textMapStrings = loadTextMapsCache();
                return;
            }
        } catch (Exception e) {
            Grasscutter.getLogger().debug("Exception while checking cache: ", e);
        };

        // Regenerate cache
        Grasscutter.getLogger().info("Generating TextMaps cache");
        ResourceLoader.loadAll();
        IntSet usedHashes = new IntOpenHashSet();
        GameData.getAvatarDataMap().forEach((k, v) -> usedHashes.add((int) v.getNameTextMapHash()));
        GameData.getItemDataMap().forEach((k, v) -> usedHashes.add((int) v.getNameTextMapHash()));
        GameData.getMonsterDataMap().forEach((k, v) -> usedHashes.add((int) v.getNameTextMapHash()));
        GameData.getMainQuestDataMap().forEach((k, v) -> usedHashes.add((int) v.getTitleTextMapHash()));
        GameData.getQuestDataMap().forEach((k, v) -> usedHashes.add((int) v.getDescTextMapHash()));
        // Incidental strings
        usedHashes.add((int) 4233146695L);  // Character
        usedHashes.add((int) 4231343903L);  // Weapon
        usedHashes.add((int)  332935371L);  // Standard Wish
        usedHashes.add((int) 2272170627L);  // Character Event Wish
        usedHashes.add((int) 3352513147L);  // Character Event Wish-2
        usedHashes.add((int) 2864268523L);  // Weapon Event Wish

        textMapStrings = loadTextMapFiles(usedHashes);
        try {
            saveTextMapsCache(textMapStrings);
        } catch (IOException e) {
            Grasscutter.getLogger().error("Failed to save TextMap cache: ", e);
        };
    }
}
