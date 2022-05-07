package emu.grasscutter.languages;


import cn.hutool.core.bean.BeanUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.utils.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Locale;
import java.util.function.Supplier;

public enum SupportLanguageEnum {
    /**
     * You can add supported languages here
     * and the program will switch to the appropriate language
     * based on your native locale, if supported
     */

    en_US(Locale.US, Language.class),
    zh_CN(Locale.SIMPLIFIED_CHINESE, CNLanguage.class),

    ;

    private Locale locale;
    private Class<? extends LanguageService> language;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    SupportLanguageEnum(Locale locale, Class<? extends LanguageService> language) {
        this.locale = locale;
        this.language = language;
    }

    public Locale getLocale() {
        return locale;
    }

    public Class<? extends LanguageService> getLanguage() {
        return language;
    }

    /**
     * Load the JSON language configuration file data
     *
     * @param fileReader A json language file reader object
     * @param locale     locale language
     * @return A concrete language instance object
     * @throws Exception The language is not supported
     */
    public static Language getSupportLanguageInstance(FileReader fileReader, Locale locale) throws Exception {
        for (SupportLanguageEnum languageEnum : values()) {
            if (languageEnum.getLocale().toString().equals(locale.toString())) {
                LanguageService languageService = gson.fromJson(fileReader, languageEnum.getLanguage());
                // Use a common class template: language.class
                // to better invoke the properties of this LanguageService instance
                return BeanUtil.toBean(languageService, Language.class);
            }
        }

        throw new Exception("The language is not supported");
    }

    /**
     * Load the JSON language configuration file data
     *
     * @param filePath A json language file path
     * @param locale   locale language
     * @return A concrete language instance object
     */
    public static Language getSupportLanguageInstance(String filePath, Locale locale, Supplier<Language> notSupportLanguageCallback) {
        try {
            FileReader file = new FileReader(filePath);
            for (SupportLanguageEnum languageEnum : values()) {
                if (languageEnum.getLocale().toString().equals(locale.toString())) {
                    LanguageService languageService = gson.fromJson(file, languageEnum.getLanguage());
                    // Use a common class template: language.class
                    // to better invoke the properties of this LanguageService instance
                    return BeanUtil.toBean(languageService, Language.class);
                }
            }
        } catch (FileNotFoundException e) {
            return notSupportLanguageCallback.get();
        }

        return notSupportLanguageCallback.get();
    }

    public static void writeAllSupportLanguageJsonFileToLanguagesDir(String languagesDir) {
        try {
            File folder = new File(languagesDir);
            if (!folder.exists() && !folder.isDirectory()) {
                //noinspection ResultOfMethodCallIgnored
                folder.mkdirs();
            }
        } catch (Exception ee) {
            Grasscutter.getLogger().error("Unable to create language folder.");
            return;
        }
        for (SupportLanguageEnum languageEnum : values()) {
            try {
                FileUtils.write(languagesDir + languageEnum.getLocale() + ".json", gson.toJson(languageEnum.getLanguage().newInstance()).getBytes());
            } catch (Exception ee) {
                if ("zh_CN".equals(languageEnum.getLocale().toString())) {
                    Grasscutter.getLogger().error("无法创建简体中文语言文件。");
                    return;
                }
                Grasscutter.getLogger().error("Unable to create language file.");
            }
        }
    }

}
