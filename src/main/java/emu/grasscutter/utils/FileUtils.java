package emu.grasscutter.utils;

import emu.grasscutter.Grasscutter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class FileUtils {
	public static void write(String dest, byte[] bytes) {
		Path path = Paths.get(dest);
		
		try {
			Files.write(path, bytes);
		} catch (IOException e) {
			Grasscutter.getLogger().warn("Failed to write file: " + dest);
		}
	}
	
	public static byte[] read(String dest) {
		return read(Paths.get(dest));
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
	
	public static String getFilenameWithoutPath(String fileName) {
		if (fileName.indexOf(".") > 0) {
		   return fileName.substring(0, fileName.lastIndexOf("."));
		} else {
		   return fileName;
		}
	}

	// From https://mkyong.com/java/java-read-a-file-from-resources-folder/
	public static List<Path> getPathsFromResource(String folder) throws URISyntaxException, IOException {
		List<Path> result;

		// get path of the current running JAR
		String jarPath = Grasscutter.class.getProtectionDomain()
				.getCodeSource()
				.getLocation()
				.toURI()
				.getPath();

		// file walks JAR
		URI uri = URI.create("jar:file:" + jarPath);
		try (FileSystem fs = FileSystems.newFileSystem(uri, Collections.emptyMap())) {
			result = Files.walk(fs.getPath(folder))
					.filter(Files::isRegularFile)
					.collect(Collectors.toList());
		}

		return result;
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	public static String readToString(InputStream file) throws IOException {
		byte[] content = file.readAllBytes();

		return new String(content, StandardCharsets.UTF_8);
	}
}
