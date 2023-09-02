package emu.grasscutter.utils;

import static emu.grasscutter.utils.FileUtils.getResourcePath;
import static emu.grasscutter.utils.lang.Language.translate;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.config.ConfigContainer;
import emu.grasscutter.data.DataLoader;
import emu.grasscutter.game.world.Position;
import emu.grasscutter.utils.objects.Returnable;
import io.javalin.http.Context;
import io.netty.buffer.*;
import it.unimi.dsi.fastutil.ints.*;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import javax.annotation.Nullable;
import org.slf4j.Logger;

@SuppressWarnings({"UnusedReturnValue", "BooleanMethodIsAlwaysInverted"})
public final class Utils {
    public static final Random random = new Random();
    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    public static int randomRange(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static float randomFloatRange(float min, float max) {
        return random.nextFloat() * (max - min) + min;
    }

    public static double getDist(Position pos1, Position pos2) {
        double xs = pos1.getX() - pos2.getX();
        xs = xs * xs;

        double ys = pos1.getY() - pos2.getY();
        ys = ys * ys;

        double zs = pos1.getZ() - pos2.getZ();
        zs = zs * zs;

        return Math.sqrt(xs + zs + ys);
    }

    public static int getCurrentSeconds() {
        return (int) (System.currentTimeMillis() / 1000.0);
    }

    public static String lowerCaseFirstChar(String s) {
        StringBuilder sb = new StringBuilder(s);
        sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
        return sb.toString();
    }

    public static String toString(InputStream inputStream) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        for (int result = bis.read(); result != -1; result = bis.read()) {
            buf.write((byte) result);
        }
        return buf.toString();
    }

    public static void logByteArray(byte[] array) {
        ByteBuf b = Unpooled.wrappedBuffer(array);
        Grasscutter.getLogger().info("\n" + ByteBufUtil.prettyHexDump(b));
        b.release();
    }

    public static String bytesToHex(byte[] bytes) {
        if (bytes == null) return "";
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String bytesToHex(ByteBuf buf) {
        return bytesToHex(byteBufToArray(buf));
    }

    public static byte[] byteBufToArray(ByteBuf buf) {
        byte[] bytes = new byte[buf.capacity()];
        buf.getBytes(0, bytes);
        return bytes;
    }

    public static int abilityHash(String str) {
        int v7 = 0;
        int v8 = 0;
        while (v8 < str.length()) {
            v7 = str.charAt(v8++) + 131 * v7;
        }
        return v7;
    }

    /**
     * Creates a string with the path to a file.
     *
     * @param path The path to the file.
     * @return A path using the operating system's file separator.
     */
    public static String toFilePath(String path) {
        return path.replace("/", File.separator);
    }

    /**
     * Checks if a file exists on the file system.
     *
     * @param path The path to the file.
     * @return True if the file exists, false otherwise.
     */
    public static boolean fileExists(String path) {
        return new File(path).exists();
    }

    /**
     * Creates a folder on the file system.
     *
     * @param path The path to the folder.
     * @return True if the folder was created, false otherwise.
     */
    public static boolean createFolder(String path) {
        return new File(path).mkdirs();
    }

    /**
     * Copies a file from the archive's resources to the file system.
     *
     * @param resource The path to the resource.
     * @param destination The path to copy the resource to.
     * @return True if the file was copied, false otherwise.
     */
    public static boolean copyFromResources(String resource, String destination) {
        try (InputStream stream = Grasscutter.class.getResourceAsStream(resource)) {
            if (stream == null) {
                Grasscutter.getLogger().warn("Could not find resource: " + resource);
                return false;
            }

            Files.copy(stream, new File(destination).toPath(), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (Exception exception) {
            Grasscutter.getLogger()
                    .warn("Unable to copy resource " + resource + " to " + destination, exception);
            return false;
        }
    }

    /**
     * Logs an object to the console.
     *
     * @param object The object to log.
     */
    public static void logObject(Object object) {
        Grasscutter.getLogger().info(JsonUtils.encode(object));
    }

    /** Checks for required files and folders before startup. */
    public static void startupCheck() {
        ConfigContainer config = Grasscutter.getConfig();
        Logger logger = Grasscutter.getLogger();
        boolean exit = false, custom = false;

        String dataFolder = config.folderStructure.data;

        // Check for resources folder.
        if (!Files.exists(getResourcePath(""))) {
            logger.info(translate("messages.status.create_resources"));
            logger.info(translate("messages.status.resources_error"));
            createFolder(config.folderStructure.resources);
            exit = true;
        }

        // Check for BinOutput + ExcelBinOutput.
        if (!Files.exists(getResourcePath("BinOutput"))
                || !Files.exists(getResourcePath("ExcelBinOutput"))) {
            logger.info(translate("messages.status.resources_error"));
            exit = true;
        }

        // Check for game data.
        if (!fileExists(dataFolder)) createFolder(dataFolder);

        // Check for Server resources.
        if (!Files.exists(getResourcePath("Server"))) {
            logger.info(translate("messages.status.resources.missing_server"));
            custom = true;
        }

        // Check for ScriptSceneData.
        if (!Files.exists(getResourcePath("ScriptSceneData"))) {
            logger.info(translate("messages.status.resources.missing_scenes"));
            custom = true;
        }

        // Log message if custom resources are missing.
        if (custom) logger.info(translate("messages.status.resources.custom"));

        // Exit if there are any missing files.
        if (exit) System.exit(1);

        // Validate the data directory.
        DataLoader.checkAllFiles();
    }

    /**
     * Gets the timestamp of the next hour.
     *
     * @return The timestamp in UNIX seconds.
     */
    public static int getNextTimestampOfThisHour(int hour, String timeZone, int param) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(timeZone));
        for (int i = 0; i < param; i++) {
            if (zonedDateTime.getHour() < hour) {
                zonedDateTime = zonedDateTime.withHour(hour).withMinute(0).withSecond(0);
            } else {
                zonedDateTime = zonedDateTime.plusDays(1).withHour(hour).withMinute(0).withSecond(0);
            }
        }
        return (int) zonedDateTime.toInstant().atZone(ZoneOffset.UTC).toEpochSecond();
    }

    /**
     * Gets the timestamp of the next hour in a week.
     *
     * @return The timestamp in UNIX seconds.
     */
    public static int getNextTimestampOfThisHourInNextWeek(int hour, String timeZone, int param) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(timeZone));
        for (int i = 0; i < param; i++) {
            if (zonedDateTime.getDayOfWeek() == DayOfWeek.MONDAY && zonedDateTime.getHour() < hour) {
                zonedDateTime =
                        ZonedDateTime.now(ZoneId.of(timeZone)).withHour(hour).withMinute(0).withSecond(0);
            } else {
                zonedDateTime =
                        zonedDateTime
                                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                                .withHour(hour)
                                .withMinute(0)
                                .withSecond(0);
            }
        }
        return (int) zonedDateTime.toInstant().atZone(ZoneOffset.UTC).toEpochSecond();
    }

    /**
     * Gets the timestamp of the next hour in a month.
     *
     * @return The timestamp in UNIX seconds.
     */
    public static int getNextTimestampOfThisHourInNextMonth(int hour, String timeZone, int param) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(timeZone));
        for (int i = 0; i < param; i++) {
            if (zonedDateTime.getDayOfMonth() == 1 && zonedDateTime.getHour() < hour) {
                zonedDateTime =
                        ZonedDateTime.now(ZoneId.of(timeZone)).withHour(hour).withMinute(0).withSecond(0);
            } else {
                zonedDateTime =
                        zonedDateTime
                                .with(TemporalAdjusters.firstDayOfNextMonth())
                                .withHour(hour)
                                .withMinute(0)
                                .withSecond(0);
            }
        }
        return (int) zonedDateTime.toInstant().atZone(ZoneOffset.UTC).toEpochSecond();
    }

    /**
     * Retrieves a string from an input stream.
     *
     * @param stream The input stream.
     * @return The string.
     */
    public static String readFromInputStream(@Nullable InputStream stream) {
        if (stream == null) return "empty";

        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader =
                new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            stream.close();
        } catch (IOException e) {
            Grasscutter.getLogger().warn("Failed to read from input stream.");
        } catch (NullPointerException ignored) {
            return "empty";
        }
        return stringBuilder.toString();
    }

    /**
     * Performs a linear interpolation using a table of fixed points to create an effective piecewise
     * f(x) = y function.
     *
     * @param x The x value.
     * @param xyArray Array of points in [[x0,y0], ... [xN, yN]] format
     * @return f(x) = y
     */
    public static int lerp(int x, int[][] xyArray) {
        try {
            if (x <= xyArray[0][0]) { // Clamp to first point
                return xyArray[0][1];
            } else if (x >= xyArray[xyArray.length - 1][0]) { // Clamp to last point
                return xyArray[xyArray.length - 1][1];
            }
            // At this point we're guaranteed to have two lerp points, and pity be somewhere between them.
            for (int i = 0; i < xyArray.length - 1; i++) {
                if (x == xyArray[i + 1][0]) {
                    return xyArray[i + 1][1];
                }
                if (x < xyArray[i + 1][0]) {
                    // We are between [i] and [i+1], interpolation time!
                    // Using floats would be slightly cleaner but we can just as easily use ints if we're
                    // careful with order of operations.
                    int position = x - xyArray[i][0];
                    int fullDist = xyArray[i + 1][0] - xyArray[i][0];
                    int prevValue = xyArray[i][1];
                    int fullDelta = xyArray[i + 1][1] - prevValue;
                    return prevValue + ((position * fullDelta) / fullDist);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            Grasscutter.getLogger()
                    .error("Malformed lerp point array. Must be of form [[x0, y0], ..., [xN, yN]].");
        }
        return 0;
    }

    /**
     * Checks if an int is in an int[]
     *
     * @param key int to look for
     * @param array int[] to look in
     * @return key in array
     */
    public static boolean intInArray(int key, int[] array) {
        for (int i : array) {
            if (i == key) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return a copy of minuend without any elements found in subtrahend.
     *
     * @param minuend The array we want elements from
     * @param subtrahend The array whose elements we don't want
     * @return The array with only the elements we want, in the order that minuend had them
     */
    public static int[] setSubtract(int[] minuend, int[] subtrahend) {
        IntList temp = new IntArrayList();
        for (int i : minuend) {
            if (!intInArray(i, subtrahend)) {
                temp.add(i);
            }
        }
        return temp.toIntArray();
    }

    /**
     * Gets the language code from a given locale.
     *
     * @param locale A locale.
     * @return A string in the format of 'XX-XX'.
     */
    public static String getLanguageCode(Locale locale) {
        return String.format("%s-%s", locale.getLanguage(), locale.getCountry());
    }

    /**
     * Base64 encodes a given byte array.
     *
     * @param toEncode An array of bytes.
     * @return A base64 encoded string.
     */
    public static String base64Encode(byte[] toEncode) {
        return Base64.getEncoder().encodeToString(toEncode);
    }

    /**
     * Base64 decodes a given string.
     *
     * @param toDecode A base64 encoded string.
     * @return An array of bytes.
     */
    public static byte[] base64Decode(String toDecode) {
        return Base64.getDecoder().decode(toDecode);
    }

    /***
     * Draws a random element from the given list, following the given probability distribution, if given.
     * @param list The list from which to draw the element.
     * @param probabilities The probability distribution. This is given as a list of probabilities of the same length it `list`.
     * @return A randomly drawn element from the given list.
     */
    public static <T> T drawRandomListElement(List<T> list, List<Integer> probabilities) {
        // If we don't have a probability distribution, or the size of the distribution does not match
        // the size of the list, we assume uniform distribution.
        if (probabilities == null || probabilities.size() <= 1 || probabilities.size() != list.size()) {
            int index = ThreadLocalRandom.current().nextInt(0, list.size());
            return list.get(index);
        }

        // Otherwise, we roll with the given distribution.
        int totalProbabilityMass = probabilities.stream().reduce(Integer::sum).get();
        int roll = ThreadLocalRandom.current().nextInt(1, totalProbabilityMass + 1);

        int currentTotalChance = 0;
        for (int i = 0; i < list.size(); i++) {
            currentTotalChance += probabilities.get(i);

            if (roll <= currentTotalChance) {
                return list.get(i);
            }
        }

        // Should never happen.
        return list.get(0);
    }

    /***
     * Draws a random element from the given list, following a uniform probability distribution.
     * @param list The list from which to draw the element.
     * @return A randomly drawn element from the given list.
     */
    public static <T> T drawRandomListElement(List<T> list) {
        return drawRandomListElement(list, null);
    }

    /***
     * Splits a string by a character, into a list
     * @param input The string to split
     * @param separator The character to use as the split points
     * @return A list of all the substrings
     */
    public static List<String> nonRegexSplit(String input, int separator) {
        var output = new ArrayList<String>();
        int start = 0;
        for (int next = input.indexOf(separator); next > 0; next = input.indexOf(separator, start)) {
            output.add(input.substring(start, next));
            start = next + 1;
        }
        if (start < input.length()) output.add(input.substring(start));
        return output;
    }

    /**
     * Fetches the IP address of a web request.
     *
     * @param ctx The context of the request.
     * @return The IP address of the request.
     */
    public static String address(Context ctx) {
        // Check headers.
        var address = ctx.header("CF-Connecting-IP");
        if (address != null) return address;

        address = ctx.header("X-Forwarded-For");
        if (address != null) return address;

        address = ctx.header("X-Real-IP");
        if (address != null) return address;

        // Return the request IP.
        return ctx.ip();
    }

    /**
     * Waits for the task to return true. This will halt the thread until the task returns true.
     *
     * @param runnable The task to run.
     */
    @SuppressWarnings("BusyWait")
    public static void waitFor(Returnable<Boolean> runnable) {
        while (!runnable.invoke()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Recursively finds all fields in a class.
     *
     * @param type The class to find fields in.
     * @return A list of all fields in the class.
     */
    public static List<Field> getAllFields(Class<?> type) {
        var fields = new LinkedList<>(Arrays.asList(type.getDeclaredFields()));

        // Check for superclasses.
        if (type.getSuperclass() != null) {
            fields.addAll(getAllFields(type.getSuperclass()));
        }

        return fields;
    }

    /**
     * Sleeps the current thread without an exception.
     *
     * @param millis The amount of milliseconds to sleep.
     */
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    /**
     * Unescapes a JSON string.
     *
     * @param json The JSON string to unescape.
     * @return The unescaped JSON string.
     */
    public static String unescapeJson(String json) {
        return json.replaceAll("\"", "\"");
    }
}
