package emu.grasscutter.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.player.Player;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static emu.grasscutter.Configuration.FALLBACK_LANGUAGE;

public final class Language {
    private static final Map<String, Language> cachedLanguages = new ConcurrentHashMap<>();

    private final JsonObject languageData;
    private final String languageCode;
    private final Map<String, String> cachedTranslations = new ConcurrentHashMap<>();

    /**
     * Creates a language instance from a code.
     *
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
     *
     * @param key  The key of the translated value to return.
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
     *
     * @param player Target player
     * @param key    The key of the translated value to return.
     * @param args   The arguments to substitute.
     * @return A translated value with arguments substituted.
     */
    public static String translate(Player player, String key, Object... args) {
        if (player == null) {
            return translate(key, args);
        }

        var langCode = Utils.getLanguageCode(player.getAccount().getLocale());
        String translated = Grasscutter.getLanguage(langCode).get(key);

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
        return this.languageCode;
    }

    /**
     * Reads a file and creates a language instance.
     */
    private Language(LanguageStreamDescription description) {
        @Nullable JsonObject languageData = null;
        this.languageCode = description.getLanguageCode();

        try {
            languageData = Grasscutter.getGsonFactory().fromJson(Utils.readFromInputStream(description.getLanguageFile()), JsonObject.class);
        } catch (Exception exception) {
            Grasscutter.getLogger().warn("Failed to load language file: " + description.getLanguageCode(), exception);
        }

        this.languageData = languageData;
    }

    /**
     * create a LanguageStreamDescription
     *
     * @param languageCode         The name of the language code.
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
     *
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
                    result = element.getAsString();
                    break;
                }
            } else break;
        }

        if (!isValueFound && !this.languageCode.equals("en-US")) {
            var englishValue = Grasscutter.getLanguage("en-US").get(key);
            if (!englishValue.contains(valueNotFoundPattern)) {
                result += "\nhere is english version:\n" + englishValue;
            }
        }

        this.cachedTranslations.put(key, result);
        return result;
    }

    private static class LanguageStreamDescription {
        private final String languageCode;
        private final InputStream languageFile;

        public LanguageStreamDescription(String languageCode, InputStream languageFile) {
            this.languageCode = languageCode;
            this.languageFile = languageFile;
        }

        public String getLanguageCode() {
            return this.languageCode;
        }

        public InputStream getLanguageFile() {
            return this.languageFile;
        }
    }
}
