package emu.grasscutter.data;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.utils.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import lombok.val;

public class DataLoader {

    /**
     * Load a data file by its name. If the file isn't found within the /data directory then it will
     * fallback to the default within the jar resources
     *
     * @param resourcePath The path to the data file to be loaded.
     * @return InputStream of the data file.
     * @throws FileNotFoundException
     * @see #load(String, boolean)
     */
    public static InputStream load(String resourcePath) throws FileNotFoundException {
        return load(resourcePath, true);
    }

    /**
     * Creates an input stream reader for a data file. If the file isn't found within the /data
     * directory then it will fallback to the default within the jar resources
     *
     * @param resourcePath The path to the data file to be loaded.
     * @return InputStreamReader of the data file.
     * @throws IOException
     * @throws FileNotFoundException
     * @see #load(String, boolean)
     */
    public static InputStreamReader loadReader(String resourcePath)
            throws IOException, FileNotFoundException {
        try {
            InputStream is = load(resourcePath, true);
            return new InputStreamReader(is);
        } catch (FileNotFoundException exception) {
            throw exception;
        }
    }

    /**
     * Load a data file by its name.
     *
     * @param resourcePath The path to the data file to be loaded.
     * @param useFallback If the file does not exist in the /data directory, should it use the default
     *     file in the jar?
     * @return InputStream of the data file.
     * @throws FileNotFoundException
     */
    public static InputStream load(String resourcePath, boolean useFallback)
            throws FileNotFoundException {
        Path path =
                useFallback ? FileUtils.getDataPath(resourcePath) : FileUtils.getDataUserPath(resourcePath);
        if (Files.exists(path)) {
            // Data is in the resource directory
            try {
                return Files.newInputStream(path);
            } catch (IOException e) {
                throw new FileNotFoundException(
                        e.getMessage()); // This is evil but so is changing the function signature at this point
            }
        }
        return null;
    }

    public static <T> T loadClass(String resourcePath, Class<T> classType) throws IOException {
        try (InputStreamReader reader = loadReader(resourcePath)) {
            return JsonUtils.loadToClass(reader, classType);
        }
    }

    public static <T> List<T> loadList(String resourcePath, Class<T> classType) throws IOException {
        try (var reader = loadReader(resourcePath)) {
            return JsonUtils.loadToList(reader, classType);
        }
    }

    public static <T1, T2> Map<T1, T2> loadMap(
            String resourcePath, Class<T1> keyType, Class<T2> valueType) throws IOException {
        try (InputStreamReader reader = loadReader(resourcePath)) {
            return JsonUtils.loadToMap(reader, keyType, valueType);
        }
    }

    public static <T> List<T> loadTableToList(String resourcePath, Class<T> classType)
            throws IOException {
        val path = FileUtils.getDataPathTsjJsonTsv(resourcePath);
        Grasscutter.getLogger().trace("Loading data table from: " + path);
        return switch (FileUtils.getFileExtension(path)) {
            case "json" -> JsonUtils.loadToList(path, classType);
            case "tsj" -> TsvUtils.loadTsjToListSetField(path, classType);
            case "tsv" -> TsvUtils.loadTsvToListSetField(path, classType);
            default -> null;
        };
    }

    public static void checkAllFiles() {
        try {
            List<Path> filenames = FileUtils.getPathsFromResource("/defaults/data/");

            if (filenames == null) {
                Grasscutter.getLogger().error("We were unable to locate your default data files.");
            } // else for (Path file : filenames) {
            //     String relativePath = String.valueOf(file).split("defaults[\\\\\\/]data[\\\\\\/]")[1];

            //     checkAndCopyData(relativePath);
            // }
        } catch (Exception e) {
            Grasscutter.getLogger().error("An error occurred while trying to check the data folder.", e);
        }
    }

    private static void checkAndCopyData(String name) {
        // TODO: Revisit this if default dumping is ever reintroduced
        Path filePath = FileUtils.getDataPath(name);

        if (!Files.exists(filePath)) {
            var root = filePath.getParent();
            if (root.toFile().mkdirs())
                Grasscutter.getLogger().info("Created data folder '" + root + "'");

            Grasscutter.getLogger().debug("Creating default '" + name + "' data");
            FileUtils.copyResource("/defaults/data/" + name, filePath.toString());
        }
    }
}
