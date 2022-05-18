package emu.grasscutter.utils;

import emu.grasscutter.Grasscutter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ShowInfosUtil {
    private static final Map<String, Map<String, Map<Integer, String>>> showInfosMap = new ConcurrentHashMap<>();
    private static String fallbackLanguageCode = null;

    public enum InfoCategory {
        AVATARS,
        ARTIFACTS,
        TALENTS,
        WEAPONS
    }

    private ShowInfosUtil() {

    }

    public static void initShowInfoMap(String langCode, String fallbackLangCode) {
        if (showInfosMap.containsKey(langCode)) {
            return;
        }

        if (fallbackLangCode == null) {
            fallbackLanguageCode = langCode;
        } else {
            fallbackLanguageCode = fallbackLangCode;
        }

        String avatarsPath = "/showinfos/%s/Avatars.txt".formatted(langCode);
        String artifactsPath = "/showinfos/%s/Artifacts.txt".formatted(langCode);
        String talentsPath = "/showinfos/%s/Talents.txt".formatted(langCode);
        String weaponsPath = "/showinfos/%s/Weapons.txt".formatted(langCode);

        String avatarsFallbackPath = "/showinfos/%s/Avatars.txt".formatted(fallbackLangCode);
        String artifactsFallbackPath = "/showinfos/%s/Artifacts.txt".formatted(fallbackLangCode);
        String talentsFallbackPath = "/showinfos/%s/Talents.txt".formatted(fallbackLangCode);
        String weaponsFallbackPath = "/showinfos/%s/Weapons.txt".formatted(fallbackLangCode);

        InputStream avatarsFile = Grasscutter.class.getResourceAsStream(avatarsPath);
        InputStream artifactsFile = Grasscutter.class.getResourceAsStream(artifactsPath);
        InputStream talentsFile = Grasscutter.class.getResourceAsStream(talentsPath);
        InputStream weaponsFile = Grasscutter.class.getResourceAsStream(weaponsPath);
        if (avatarsFile == null) { // Provided fallback language.
            Grasscutter.getLogger().warn("Failed to load Avatars file: [" + avatarsPath + "], falling back to: [" + avatarsFallbackPath + "]");
            avatarsFile = Grasscutter.class.getResourceAsStream(avatarsFallbackPath);
        }
        if (artifactsFile == null) { // Provided fallback language.
            Grasscutter.getLogger().warn("Failed to load Artifacts file: [" + artifactsPath + "], falling back to: [" + artifactsFallbackPath + "]");
            artifactsFile = Grasscutter.class.getResourceAsStream(artifactsFallbackPath);
        }
        if (talentsFile == null) { // Provided fallback language.
            Grasscutter.getLogger().warn("Failed to load Talents file: [" + talentsPath + "], falling back to: [" + talentsFallbackPath + "]");
            talentsFile = Grasscutter.class.getResourceAsStream(talentsFallbackPath);
        }
        if (weaponsFile == null) { // Provided fallback language.
            Grasscutter.getLogger().warn("Failed to load Weapons file: [" + weaponsFile + "], falling back to: [" + weaponsFallbackPath + "]");
            weaponsFile = Grasscutter.class.getResourceAsStream(weaponsFallbackPath);
        }
        Map<String, Map<Integer, String>> langCodeShowInfos = new HashMap<>();
        Map<Integer, String> avatarInfoMap = parseFileContent(avatarsFile);
        Map<Integer, String> artifactMap = parseFileContent(artifactsFile);
        Map<Integer, String> talentInfoMap = parseFileContent(talentsFile);
        Map<Integer, String> weaponInfoMap = parseFileContent(weaponsFile);
        langCodeShowInfos.put(InfoCategory.AVATARS.name(), avatarInfoMap);
        langCodeShowInfos.put(InfoCategory.ARTIFACTS.name(), artifactMap);
        langCodeShowInfos.put(InfoCategory.TALENTS.name(), talentInfoMap);
        langCodeShowInfos.put(InfoCategory.WEAPONS.name(), weaponInfoMap);
        showInfosMap.put(langCode, langCodeShowInfos);
    }

    private static Map<Integer, String> parseFileContent(InputStream file) {
        if (file == null)
            throw new RuntimeException("Unable to load the primary, fallback, and 'en-US' showinfos files.");
        Map<Integer, String> infoMap = new LinkedHashMap<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(file, StandardCharsets.UTF_8), 1024);
            while (br.ready()) {
                String line = br.readLine();
                String[] idName = line.split(":");
                if (idName.length == 2) {
                    int avatarId = 0;
                    try {
                        avatarId = Integer.parseInt(idName[0].strip());
                    } catch (NumberFormatException e) {
                        continue;
                    }
                    if (!infoMap.containsKey(avatarId)) {
                        infoMap.put(avatarId, idName[1].strip());
                    }
                }
            }
            br.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return infoMap;
    }

    public static Map<Integer, String> getShowInfoMap(String langCode, InfoCategory category) {
        if (showInfosMap.containsKey(langCode)) {
            return showInfosMap.get(langCode).get(category.name());
        } else if (showInfosMap.containsKey(fallbackLanguageCode)){
            return showInfosMap.get(fallbackLanguageCode).get(category.name());
        } else {
            initShowInfoMap(Utils.getLanguageCode(Grasscutter.getConfig().language.language), null);
        }
        return showInfosMap.get(fallbackLanguageCode).get(category.name());
    }

}
