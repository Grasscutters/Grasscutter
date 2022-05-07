package emu.grasscutter.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import emu.grasscutter.Grasscutter;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public final class Language {
    private final JsonObject languageData;
    private final Map<String, String> cachedTranslations = new HashMap<>();

    /**
     * Creates a language instance from a code.
     * @param langCode The language code.
     * @return A language instance.
     */
    public static Language getLanguage(String langCode) {
        return new Language(langCode + ".json", Grasscutter.getConfig().DefaultLanguage.toLanguageTag());
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
     * Reads a file and creates a language instance.
     * @param fileName The name of the language file.
     */
    private Language(String fileName, String fallback) {
        @Nullable JsonObject languageData = null;

        try {
            InputStream file = Grasscutter.class.getResourceAsStream("/languages/" + fileName);
            if(file == null) {
                file = Grasscutter.class.getResourceAsStream("/languages/" + fallback);
            }
            
            languageData = Grasscutter.getGsonFactory().fromJson(Utils.readFromInputStream(file), JsonObject.class);
        } catch (Exception exception) {
            Grasscutter.getLogger().warn("Failed to load language file: " + fileName, exception);
        }
        
        this.languageData = languageData;
    }

    /**
     * Returns the value (as a string) from a nested key.
     * @param key The key to look for.
     * @return The value (as a string) from a nested key.
     */
    public String get(String key) {
        if(this.cachedTranslations.containsKey(key)) {
            return this.cachedTranslations.get(key);
        }
        
        String[] keys = key.split("\\.");
        JsonObject object = this.languageData;

        int index = 0;
        String result = "This value does not exist. Please report this to the Discord: " + key;

        while (true) {
            if(index == keys.length) break;
            
            String currentKey = keys[index++];
            if(object.has(currentKey)) {
                JsonElement element = object.get(currentKey);
                if(element.isJsonObject())
                    object = element.getAsJsonObject();
                else {
                    result = element.getAsString(); break;
                }
            } else break;
        }
        
        this.cachedTranslations.put(key, result); return result;
    }
}
