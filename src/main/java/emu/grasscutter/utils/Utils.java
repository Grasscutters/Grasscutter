package emu.grasscutter.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Random;

import emu.grasscutter.Config;
import emu.grasscutter.Grasscutter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

import org.slf4j.Logger;

@SuppressWarnings({"UnusedReturnValue", "BooleanMethodIsAlwaysInverted"})
public final class Utils {
	public static final Random random = new Random();
	
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
	
	private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();
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
	 * @param path The path to the file.
	 * @return A path using the operating system's file separator.
	 */
	public static String toFilePath(String path) {
		return path.replace("/", File.separator);
	}

	/**
	 * Checks if a file exists on the file system.
	 * @param path The path to the file.
	 * @return True if the file exists, false otherwise.
	 */
	public static boolean fileExists(String path) {
		return new File(path).exists();
	}

	/**
	 * Creates a folder on the file system.
	 * @param path The path to the folder.
	 * @return True if the folder was created, false otherwise.
	 */
	public static boolean createFolder(String path) {
		return new File(path).mkdirs();
	}

	/**
	 * Copies a file from the archive's resources to the file system.
	 * @param resource The path to the resource.
	 * @param destination The path to copy the resource to.
	 * @return True if the file was copied, false otherwise.
	 */
	public static boolean copyFromResources(String resource, String destination) {
		try (InputStream stream = Grasscutter.class.getResourceAsStream(resource)) {
			if(stream == null) {
				Grasscutter.getLogger().warn("Could not find resource: " + resource);
				return false;
			}

			Files.copy(stream, new File(destination).toPath(), StandardCopyOption.REPLACE_EXISTING);
			return true;
		} catch (Exception e) {
			Grasscutter.getLogger().warn("Unable to copy resource " + resource + " to " + destination, e);
			return false;
		}
	}

	/**
	 * Get object with null fallback.
	 * @param nonNull The object to return if not null.
	 * @param fallback The object to return if null.
	 * @return One of the two provided objects.
	 */
	public static <T> T requireNonNullElseGet(T nonNull, T fallback) {
		return nonNull != null ? nonNull : fallback;
	}

	/**
	 * Logs an object to the console.
	 * @param object The object to log.
	 */
	public static void logObject(Object object) {
		String asJson = Grasscutter.getGsonFactory().toJson(object);
		Grasscutter.getLogger().info(asJson);
	}
	
	/**
	 * Checks for required files and folders before startup.
	 */
	public static void startupCheck() {
		Config config = Grasscutter.getConfig();
		Logger logger = Grasscutter.getLogger();
		boolean exit = false;

		String resourcesFolder = config.RESOURCE_FOLDER;
		String dataFolder = config.DATA_FOLDER;

		// Check for resources folder.
		if(!fileExists(resourcesFolder)) {
			logger.info(Grasscutter.getLanguage().Create_resources_folder);
			logger.info(Grasscutter.getLanguage().Place_copy);
			createFolder(resourcesFolder); exit = true;
		}

		// Check for BinOutput + ExcelBinOuput.
		if(!fileExists(resourcesFolder + "BinOutput") ||
				!fileExists(resourcesFolder + "ExcelBinOutput")) {
			logger.info(Grasscutter.getLanguage().Place_copy);
			exit = true;
		}

		// Check for game data.
		if(!fileExists(dataFolder))
			createFolder(dataFolder);

		if(exit) System.exit(1);
	}

	public static int GetNextTimestampOfThisHour(int hour, String timeZone, int param) {
		ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(timeZone));
		for (int i = 0; i < param; i ++){
			if (zonedDateTime.getHour() < hour) {
				zonedDateTime = zonedDateTime.withHour(hour).withMinute(0).withSecond(0);
			} else {
				zonedDateTime = zonedDateTime.plusDays(1).withHour(hour).withMinute(0).withSecond(0);
			}
		}
		return (int)zonedDateTime.toInstant().atZone(ZoneOffset.UTC).toEpochSecond();
	}

	public static int GetNextTimestampOfThisHourInNextWeek(int hour, String timeZone, int param) {
		ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(timeZone));
		for (int i = 0; i < param; i++) {
			if (zonedDateTime.getDayOfWeek() == DayOfWeek.MONDAY && zonedDateTime.getHour() < hour) {
				zonedDateTime = ZonedDateTime.now(ZoneId.of(timeZone)).withHour(hour).withMinute(0).withSecond(0);
			} else {
				zonedDateTime = zonedDateTime.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).withHour(hour).withMinute(0).withSecond(0);
			}
		}
		return (int)zonedDateTime.toInstant().atZone(ZoneOffset.UTC).toEpochSecond();
	}

	public static int GetNextTimestampOfThisHourInNextMonth(int hour, String timeZone, int param) {
		ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(timeZone));
		for (int i = 0; i < param; i++) {
			if (zonedDateTime.getDayOfMonth() == 1 && zonedDateTime.getHour() < hour) {
				zonedDateTime = ZonedDateTime.now(ZoneId.of(timeZone)).withHour(hour).withMinute(0).withSecond(0);
			} else {
				zonedDateTime = zonedDateTime.with(TemporalAdjusters.firstDayOfNextMonth()).withHour(hour).withMinute(0).withSecond(0);
			}
		}
		return (int)zonedDateTime.toInstant().atZone(ZoneOffset.UTC).toEpochSecond();
	}
}
