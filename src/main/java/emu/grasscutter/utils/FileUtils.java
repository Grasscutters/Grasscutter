package emu.grasscutter.utils;

import emu.grasscutter.Grasscutter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class FileUtils {
    private static final Path DATA_DEFAULT_PATH;
    private static final Path DATA_USER_PATH = Path.of(Grasscutter.config.folderStructure.data);
    private static final Path PACKETS_PATH = Path.of(Grasscutter.config.folderStructure.packets);
    private static final Path PLUGINS_PATH = Path.of(Grasscutter.config.folderStructure.plugins);
    private static final Path RESOURCES_PATH;
    private static final Path SCRIPTS_PATH;
    static {
        FileSystem fs = null;
        Path path = null;
        // Setup access to jar resources
        try {
            var uri = Grasscutter.class.getResource("/defaults/data").toURI();
            switch (uri.getScheme()) {
                case "jar":  // When running normally, as a jar
                case "zip":  // Honestly I have no idea what setup would result in this, but this should work regardless
                    fs = FileSystems.newFileSystem(uri, Map.of());  // Have to mount zip filesystem. This leaks, but we want to keep it forever anyway.
                    // Fall-through
                case "file":  // When running in an IDE
                    path = Path.of(uri);  // Can access directly
                    break;
                default:
                Grasscutter.getLogger().error("Invalid URI scheme for class resources: "+uri.getScheme());
                    break;
            }
        } catch (URISyntaxException | IOException e) {
            // Failed to load this jar. How?
            Grasscutter.getLogger().error("Failed to load jar?!");
        } finally {
            DATA_DEFAULT_PATH = path;
            Grasscutter.getLogger().debug("Setting path for default data: "+path.toAbsolutePath());
        }

        // Setup Resources path
        final String resources = Grasscutter.config.folderStructure.resources;
        fs = null;
        path = Path.of(resources);
        if (resources.endsWith(".zip")) {  // Would be nice to support .tar.gz too at some point, but it doesn't come for free in Java
            try {
                fs = FileSystems.newFileSystem(path);
            } catch (IOException e) {
                Grasscutter.getLogger().error("Failed to load resources zip \"" + resources + "\"");
            }
        }

        if (fs != null) {
            var root = fs.getPath("");
            try (Stream<Path> pathStream = Files.find(root, 3, (p, a) -> {
                        var filename = p.getFileName();
                        if (filename == null) return false;
                        return filename.toString().equals("ExcelBinOutput");
            })) {
                var excelBinOutput = pathStream.findFirst();
                if (excelBinOutput.isPresent()) {
                    path = excelBinOutput.get().getParent();
                    if (path == null)
                        path = root;
                    Grasscutter.getLogger().debug("Resources will be loaded from \"" + resources + "/" + path.toString() + "\"");
                } else {
                    Grasscutter.getLogger().error("Failed to find ExcelBinOutput in resources zip \"" + resources + "\"");
                }
            } catch (IOException e) {
                Grasscutter.getLogger().error("Failed to scan resources zip \"" + resources + "\"");
            }
        }
        RESOURCES_PATH = path;

        // Setup Scripts path
        final String scripts = Grasscutter.config.folderStructure.scripts;
        SCRIPTS_PATH = (scripts.startsWith("resources:"))
            ? RESOURCES_PATH.resolve(scripts.substring("resources:".length()))
            : Path.of(scripts);
    };

    public static Path getDataPath(String path) {
        Path userPath = DATA_USER_PATH.resolve(path);
        if (Files.exists(userPath)) return userPath;
        Path defaultPath = DATA_DEFAULT_PATH.resolve(path);
        if (Files.exists(defaultPath)) return defaultPath;
        return userPath;  // Maybe they want to write to a new file
    }

    public static Path getDataUserPath(String path) {
        return DATA_USER_PATH.resolve(path);
    }

    public static Path getPacketPath(String path) {
        return PACKETS_PATH.resolve(path);
    }

    public static Path getPluginPath(String path) {
        return PLUGINS_PATH.resolve(path);
    }

    public static Path getResourcePath(String path) {
        return RESOURCES_PATH.resolve(path);
    }

    public static Path getScriptPath(String path) {
        return SCRIPTS_PATH.resolve(path);
    }

    public static void write(String dest, byte[] bytes) {
        Path path = Path.of(dest);

        try {
            Files.write(path, bytes);
        } catch (IOException e) {
            Grasscutter.getLogger().warn("Failed to write file: " + dest);
        }
    }

    public static byte[] read(String dest) {
        return read(Path.of(dest));
    }

    public static byte[] read(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            Grasscutter.getLogger().warn("Failed to read file: " + path);
        }

        return new byte[0];
    }

    public static InputStream readResourceAsStream(String resourcePath) {
        return Grasscutter.class.getResourceAsStream(resourcePath);
    }

    public static byte[] readResource(String resourcePath) {
        try (InputStream is = Grasscutter.class.getResourceAsStream(resourcePath)) {
            return is.readAllBytes();
        } catch (Exception exception) {
            Grasscutter.getLogger().warn("Failed to read resource: " + resourcePath);
            exception.printStackTrace();
        }

        return new byte[0];
    }

    public static byte[] read(File file) {
        return read(file.getPath());
    }

    public static void copyResource(String resourcePath, String destination) {
        try {
            byte[] resource = FileUtils.readResource(resourcePath);
            FileUtils.write(destination, resource);
        } catch (Exception exception) {
            Grasscutter.getLogger().warn("Failed to copy resource: " + resourcePath + "\n" + exception);
        }
    }

    @Deprecated  // No current uses of this anyway
    public static String getFilenameWithoutPath(String fileName) {
        int i = fileName.lastIndexOf(".");
        if (i > 0) {
           return fileName.substring(0, i);
        } else {
           return fileName;
        }
    }

    public static List<Path> getPathsFromResource(String folder) throws URISyntaxException {
        try {
            // file walks JAR
            return Files.walk(Path.of(Grasscutter.class.getResource(folder).toURI()))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            // Eclipse puts resources in its bin folder
            try {
                return Files.walk(Path.of(System.getProperty("user.dir"), folder))
                        .filter(Files::isRegularFile)
                        .collect(Collectors.toList());
            } catch (IOException ignored) {
                return null;
            }
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static String readToString(InputStream file) throws IOException {
        byte[] content = file.readAllBytes();

        return new String(content, StandardCharsets.UTF_8);
    }
}
