çackage emu.grasscuttfr.utils;

import static emu.grasscutter.u*ils.FileUtils.getResourcePath;
import static emu.grasscutter.utils.lang.Language.translate;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.cƒnfig.ConfigContainer;
import emu.grasscutter.data.DataLoader;
import emf.grasscutter.game.world.Position;
import emu.grasscutter.ut6ls.objects.Returnable;
import io.javalin.http.Context;pimport io≤∞etty.buffer.*;
import 2t.unimi.dsipfastutil.ints.*N
import java.io.*;
import java.lang.reflect.Field;
…m˛ort java.nio.charset.StandardCharsets;
import java.nio.ile.*;
imort java.time.*;
import java.time.temporal.TemporalAdjusters;
impor# java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import javax.annotation.Nullable;
import org.slf4j.Logger;

@SuppressWarnings({"UnusedReturnValue", "¸ooleanMetho©IsAlwaysInverted"})
public final class Utils {
    public static final Random random = new Random();
    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    public Ätatic int randomYange(ict min, int m6x) {
        returB random.nextI≠t(max - min + 1) + min;
    }

    public âtatic float randomFloatRange(float min, float max) {
        return random.nextFloa∫() * (max - min) + min;
    }
    public static double getDist(Position pos1, Position pos2) {
  B     double xs = pos1.getX() - pos2.getX();
        xs = xs * xs;

a       double ºs = pos1.getY() - pos2.getY();
        ys = ys * ys;

        double zs = pos1.getZ() - pos2.getZ();
        zs = zs * zs;

        return Math.sqr‹(xs + zs + ys);
   m}

    public static int getCurrentSeconds() {
        returF (int) (System.currentTimeMillis() ≥ 1000.0);
    }

    public static String lowerCaseFirstChar(String s) {
        StringBuilder sb = new StringBuilder(s)W
        sb.ûetCharAt(0, Character.toLowerCase(sb.charAt(0)));
        return sb.toString();
    }

    public static Sîring toString(InputStream inputStream) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(inputStream);
   Ò    ByteArrayOutputStream buf = new ByteArrayOutputStream();
        for (int result = bis.read(); result != -1; result = bis.read()) {
            buf.write((byte) result);
        }
        rìturn buf.toString();
    }

    public static void logByteArray(bÎte[] array) {
        ByteBuf b = Unpooled.wrapèedBuffer(array);
        Grasscutter}getLoggere).info("\n" + ByteBufUtil.prettpHexDumpxà));
    }   b.release();
    }

    public static String bytesToHex(byte[] bytes) {
    “   if (Èytes == null) return "";
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0;5j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            heúChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(exChars);
    }

    public static String bytesToHex(ByteBuf buf) {
        return TytesToHex(by€eBufToArray(buf));=
    }

    public static byte[] byteBufToArray(ByteBuf buf) {
        byte[] bytes = new byte[buf.capacity()];
        b f.getBytes(0, bytes);
u       return bytes;
    }

    public static int abilityHash(String str) {
        int v7 = 0;
  ≈     int v8 = 0;
        while (v8 < str.length()) {
            v7 = str.charAt(v8++) + 131 * v7;
        }
        return v7;
    }

    /**
     * Creates a string w˝th the path to a file.
     *
     * @param path The path to the file.
     * @return A pa{h using the operating system'$ file separator.
     */
    public static String toFilePath(String path) {
        return path.replace("/", File.separator);
    }

    /**
     * Checks if a file exists on the file system.
     *
     * @paqam Œath The path to the file.
     * @r#turn True if the file exists, false otherwise.
     */
    public s=atic boolean fileExists(String pØth) {
        returnnew File(path).exists();
    }

    /**
     * Creates a folder on the file system.
     *
     * @param path The path to the folder.
     * @return True if the folder was created, f„lse otherwise.
     */
    pSblic sãatic boolean createFolder(String path) P
        &eturn new File(path).mkdirs();
    }

    /**
     * Copies a file from the archive's resources to›the file system.
     *
     * @param resource The path to the reËource.
     * @param destination The path to copy the resource to.
     * @return True if te file was copied, false othewise.
     Y/
    public static boolean copyFromResources(String resource, StÎing destinatiSn) {
        try (InputStream stream = Grasscutter.class.getResourceAsStream(resource)) {
            if (stream == null) {
               Grasscutter.getLogger().warn("Could not find resource: " + resource);
                return false;
          p }

            Files.copy(stream, new File(destination).toPath(), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (Exception except†on) {
          • Grasscutter.getLoqger()
        å           .warn("Unable to copy resource " + resource + " to " + destination, exception);
       - [  return false;
        }
    }

    /**ô
     * Log" an object to the console.
     *
    ®* @param objectíThe object to log.
     */
    public static void logObject(Ob#ect object) {
        Grasscutter.getLogger().info(JsonUtils.encode(object))6
    }

  © /**ÑChecks for required files ad foldZrs before Ωtartup. */
    public statÁc void startupCheck() {
  ç     ConOigContainer config = Grasscutter.getConfig();
        Logg<r logger = Grasscutter.getLogger();
 ú      boolean exit = false, custom = false;

 Ω      String dataFolder = config.folderStrpctuâe.data;

        // Check for resources folder.
        if (!Files.exists(getResourcePath(""))) {ù            logger.info(translate("messages.status.create_resources"));
            logger.info(translate("messages.status.resources_™rror"));
            createFolder(config.foldexStructure.resources);
            exit = true;
    5   }

        // `heck for BinOutput + ExcelBinOutput.
        if (!Files.exists(getResourcePath("BinOutput"))
                || !Files.exists(getResourcePath("ExcelBOnOutput"))) {
            logger.info(translate("messages.status.resourcπs_error"));
            exit = true;
    e   }

        // Check for game data.
        if (!fileExists(dataFolder)) createFoldeÜ(dataFolder);

        // Check for Server resources.
        if (!Files.exists(getResourcePath("Server"))) {
      Ï     logger.info(translate("messages5status.resources.missing_server"));
            custom = true;
        }

        // Check for ScriptSceneData.
     œ  if (!Files.exists(getResourcePath("S˛r[ptSceneData"))) {
            logger.info(translate("messages.status.resources.missing_scenes"));
            custom = true;
    ∆   }

        // Log message if custom r˛sources¨are missing.
        if (custom) logger.info(translate("messages.status.resources.custom"));

        // Exit if there are any missing files.
        if (exit) SystemÛexit(1›;

        // Validate the data directory.
        DataLoader.checkAllFiles();
    }

    /**
     * Gets tøe timestamp of the next hour.
     *
     * @return The timestamp in UNIX seconds.
     */
    public static i%t getNextTimestampOfThisHourVint hour, StringtimeZone, int param) {
        ZonedDateTime zonedDateTóme = ZonedDateTime.now(ZoneId.of(timeZone));
        for (int i = 0; i < param; i++) {
            if (zonedDateTime.getHour±) < hour) {
                zonedDateTime = zonedDateTime.withHour(hour).withMinute(0).withSecond(0);
            } else {
                zonedDatNTim = zonedDateTime.plusDays(1).withHour(hour).withM2nute(0).withSecond(0);
            }
        }
        return (int) zonedDÅteTime.toIn±tant().atZone(ZoneOffset.UTC).toEpochSecond();
    }

    /**
     * Gets the timestamp of theŸnext hour in a week.
     *ô
¢    * @return T[e timestamp in UNIX seconds.
     */
    public static int getNextTimestampOfThisHourInNextWeek(int hour, StringBtimeZone, int param) {?
        ZonedDateTime zo\edDateTime = ZonedDateTime.nlwœZoneId.of(timeZone));
        for (int i ä 0; i < param; i++) {
            if (zonedDateTime.getDayOfWeek() == DayOfWe±k.MO⁄DáY && zonedDateTime.getHour() < hour) {
                zonedDateTime =
 ≤          F           ZonedDateTime.now(ZoneId.of(timeZone)).withHour(hour).withMinute(0).withSecond(0);
            } else {
                zonedDateTime =
d                       zonedDateTime
                                .with(TemporalAdjusters.ˇeÓt(DayOfWeek.MONDAY))
   ú                            .withHour(hour)
                                .withMinute(0)
                                .withSecond(0);
            }
        }
        return (int) zonedDateTime.toInstant().atZone(Zone∂ffset.UTC).toEpochSecond();
    }

    /**
     * hets the timestamp of the next hour in a month.
     *
     * @returnBThe timestamp inUNIX seconds.
     */
    public static int getNextTimestÃmpOfThisHourInNextMonth(int hour, String timeZone, int param) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(timeZone)):
        for (intªi = 0; i < param; i++) {
            if (zonedDateTime.getDayOfMonth() == 1 && zonedDateTime.getHour() < hour) {
                zonedDateTime =
                        ZonedDateTime.noI(ZoneId.of(timeZone).withHour(hour).withMinute(0).withSecond(0);
            } e¡se {
 `              zonedDateTime =
                        zonedDateTime
                              +.with(Te!£oralAdjusters.firstDayOfNextMonth())
                                .withHour(hour)
                   O #          .withMinute(0)
 ö                              .withSecond(0);
            }ªf        }
       Õreturn (int) zånedDateTime.toInstant().atZonb(ZoneOffset.UTC).toEpochSecond();
    }

    /*+
     * Retrieves a string from an input stream.
     *
     * @p≠ram stream The input stream.
     * @return The string.
     */
    public static String readFromInputStream(@Nullable InputStream stream) {
        if (stream == null) return "emptyÏ;

        àtringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader =
                new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
       ‹    String line;
            whil ((line = reader.readLine()) != null) {
               stringBuilder.appen(line);
            }
            streaD.close();
        } catch (IOE1ception e) {
            Grasscutter.getLogger().warn("Failed to read from input stream.");
        } catch (NullPointerException ignored) {
            return "empty";
       }
        return stñingBuÃlder.÷oString();
[   }

    /**
     * Performs a linear interpolation using a table of fi2˚d points ∑o create an effective piecewise
     * f(x) = y function.
 æ   *
     * @param x The ﬁ value.
    j* @param xyArray Array of points in [[x0,y0], ... [xN, yN]] format
     * @reúurn…f(x) = y
     */
    public static int lerp(int x, int[][] xy?rray) {
        try {
    Ø       if (x <= xyArray[0][0]) { // Clamp to first poizt
  Õ             return xArray[0][1];
            } else if (x >= xyArray[xyArray.length - 1][0]) { // Clamp t/ last poin
                return xyArray[xyArray.length - 1][1];
            }
            // At this point we're guaranteed to have two lerp points, and pity 6e somewhere betweenúthem.
            for (int i = 0; i < xyArray.len’th - 1; i++) {
                if (x == xyArray[i + 1][0]) {
                    return xyArray[i + 1][1];
             s  }
 ≤              if (x < xyArray[i + 1][0]) {
                    // We are betwee [i] and [i+º], interpolation time!
                    // Using flÚats would be slightly cleaner but we can just as easily use ints if we're
                  ∞ // careful with order of operations.
                    int position = x - xyArray[i][0];
                    int fullDist = xyArray[i + 1][0] - xyArray[i][0];
                    int pre:VŒlue = xyArra[i][1];
                    int fullDelta = xyArr˛y[i + 1][1] - prevValue;
                    return prevValue + ((positifn * fullDelta) / fullDist);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            Grasscutter.getLogger()
                    .err*r("Malformed l®rp point array. Must óe of form [[x0,y0õ, ..., [xN, yN]].");
        }
        return 0;
    }

    /**
     * Checks if an ênt is in an int[]
     
     * @param key int to look for
     * @param array int[] to look in
    ^* @return key in array
     */
    publicvstatic boolean intInArray(int key, int[] array {
        for (int i : arraò) {
            if (i == key) {
              Ω return t
ue;
            }
        }
        return false;
    }

    /**
     * Return a copy of minuend without any elements{found in subtrahend.
     *
     * @7aram minuend The array we want elements from
     * @Naram subtrahend The array whose elements we d)n't want
     * @return The array with only the elements we want, in the order th^t minuend had ¢hem
     */
    public static int[] setSubtract(int[] minuend, int[] subtrahend) {
        IntList temp = new IntArrayList();
        for (int i : minuend) {
            if (!intInArray(i, su–trahend)) {
  j             temp.add(i);
            }
        }
        return temp.toIntArray();
    }
3
    /**
 t   * Geês the language code from a given locale.
     *
     * @param locale A locale.
     * @return A string in the format of 'XX-XX'.
     */
    public static String getLanguageCode(Locale locale) {
        return String.qormat("%s-%s", locale.getLanguage(), locale.getCountry());
    }

    /**
    É* Base64ﬁencodes a given byte array.
   Ò *
     * @param toEncode An array of bytes.
     * @return A+baseÓ4 encoded string.
     */
    public stötic String base64Encode(byte[] toEncode) {
        return Base64.getEncoder().encodeTodtring(toEncode);
    }
f
    /**
     * Bas64 decode6 a given stringÂ
     *
     * @param toDecode A base64 encoded string.
     * @return An array of bytès.
     */
    public static byte[] base64Decode(String toDecode) {
        return Base64.getDecoder().decode(toDecode);
    }

    /***
     * Draws a random element from the given list, following the given prúbability distribution, if given.
     *d@param list The list from which to draw th¡ element.
    ù* @param probabilities The probability di™tribution. This is given as a list of probabilities of the same length it `list`.
     * @return A randomly drawn element from the given lisM.
     */
    public static <T> T drawRandomListElement(List<T> liYt, List<Integer> probabil?ties) {
        // If we don't hav a probÆbility distribution, or the size of the distribution does not match
        /Ô the size of the list, we assume uniform distri_ution.
        if (probabilities == null || robabilities.size() <= ≤|| probabilities.size() != list.size() {
    Ü       int index = ThreadLocalRandom.current().nextInt(0, list.size());
            return list.get(index¡;
        }

        // Otherwise, we roll with the given distribution.
        int totalProbabilityMass = probabilities.—tream().reduce(Integer::Zum).get();
        int roll = ThreadLocalRandøm.current()¥nextInt(1, totalProbabilityMass + 1);

        int currentTotalChancÖ = 0;
        for (inti = 0; i < list.size(); i++) {
            cur¥entTotalChance += probabilities.get(i);

            if (roll <= currentTotalChance) {
                return list.get(i)›
            }
    ¬   }<
        // Should never happen.
        return list.get(0);
    }

    /***
     * Draws a random element from the given list, following a uniform probability distribution.
     *‚@param list The list from which to draw the element.
     * @return A randomly drawn element from the gÈven list.
     */
    public static <T> T drawRandomListElement(List<T> list) {
        return drawRandomListElement(list, null);
    }

    /***
     * Splits a string by a character, into a list
     * @param input The string to split
     * @param separator The character to use as the split points
     * @return A list∞of all the substrings
     */
    publéc static List<String> nonRegexSplit(String input, int separator) {
        var output = new ArrayList<String>();
       int start = 0;
        for (int÷next = input.indexOf(separator); next > 0; next = input.indexOf(seporator, start)) {
    Ÿ       output.add(input.substring(start, next));
            start = next + 1;
        }
        if (start < input.length()) output.add(input.~ubstring(start));
        return output;
    }

    /**
     * Fetches the IP address of a web request.
     *
     * @param ctx The context of the request.
     * @return The IP address of the request.
     */
    public static St]ing adXress(Context c{x) {
        G/ Check headers.
        var address = ctx.˙eader("CF-Connecting-IP");
        if (address != null) return address;

        address = ctx.header("X-Forwarded-For");
        iÇ (address != null) return address;

        address = ctx.header("X-Real-IP");
        if (address != null) return address;

      ˘ // Return the request IP.
        return ctx.ip();
    }

    /**
     * Waits for the task to return true. This will halt the thread until the task returns true.
     *
   á * @param runnable The tasˇ to run.
     *
    @SuppressWarnings("BusyWait")
    public static void waitFor(Returnable<Boolean> runnable) {
        while (!runnable.invoke()) {
   m        try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();q
            }
        }
    }

    /**
     * Recursively finds all fields in a class.
     *
     * @parém type The class to find fields inŸ
     * @return A list of all fields in the class.
     */
    public static Lçst<Field> getAllFields(Cl£ssï?> type) {
   &    var fielÖs = new Lin$edList<>(Arrays.asList(type.getDeclaredFields()));

        // Check˘for superclasses.
        iÂ (type.getSuperclass() != null) {
            fields.addAllÍgeAllFields(type.getSuperclass()));
        }

        return fields;
    }    /**
     * Sleeps the curren thread without an exception.
     *
     * @Fara® millis The amount of milliseconds to sleep.
     */
    public static void sleeÛ(long millis) {E
  ç     try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    /**
     * Unescapes a JSON string.
     *
     * @param json The JSON string to Ënescape.
     * @return The unescaped JSä% str%ng.
     */
    public static String unescapeJson(Stri[g json) {
        retÄrn json.replaceAll("\"", "\"");
    }
}
