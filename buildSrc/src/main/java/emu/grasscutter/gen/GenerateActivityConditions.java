package emu.grasscutter.gen;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.options.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.System.lineSeparator;
import static java.nio.file.Files.readAllLines;
import static java.nio.file.Files.writeString;

/**
 * Task that can be used for generating/updating activity conditions enum. These
 * activities come from Resources/ExcelBinOutput/NewActivityCondExcelConfigData.json
 * resource file. Format file with formatter after this job is executed
 * <br />
 * Usage example: <i>./gradlew generateActivityConditions --conf-file=/Users/xxx/IdeaProjects/Grasscutter_Resources/Resources/ExcelBinOutput/NewActivityCondExcelConfigData.json</i>
 */
public class GenerateActivityConditions extends DefaultTask {

    private static final Logger log = LoggerFactory.getLogger(GenerateActivityConditions.class);
    private static final String ACTIVITY_CONDITIONS_SRC = "/src/main/java/emu/grasscutter/game/activity/condition/ActivityConditions.java";

    private static final String activityClassStart = """
        package emu.grasscutter.game.activity;

        public enum ActivityConditions {
                    """;
    @Option(option = "conf-file", description = "Path to NewActivityCondExcelConfigData.json")
    String confFile;

    @SuppressWarnings("unused") //Used by Gradle
    public void setConfFile(String confFile) {
        this.confFile = confFile;
    }

    @TaskAction
    void run() {
        List<String> configFileContent = getFileContent(confFile);

        Set<String> configEnums = configFileContent.stream()
            .filter(s -> s.contains("\"type\":"))
            .map(s -> s.split("\"")[3])
            .map(s -> "    " + s)
            .collect(Collectors.toSet());

        String finalActivityClass =
            activityClassStart +
                String.join("," + lineSeparator(), configEnums) + lineSeparator() + "}";

        writeFile(finalActivityClass, Path.of(getProject().getProjectDir() + ACTIVITY_CONDITIONS_SRC));

        log.info("Successfully added {} enums to {}", configEnums.size(), ACTIVITY_CONDITIONS_SRC);
    }

    private List<String> getFileContent(String path) {
        try {
            return readAllLines(Path.of(confFile));
        } catch (IOException e) {
            log.error("Cannot read file: {}", path);
            throw new RuntimeException(e);
        }
    }

    private void writeFile(String content, Path path) {
        try {
            writeString(path, content, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Cannot read file: {}", path);
            throw new RuntimeException(e);
        }
    }
}
