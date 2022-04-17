package emu.grasscutter.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {
	
	public static void write(String dest, byte[] bytes) {
		Path path = Paths.get(dest);
		
		try {
			Files.write(path, bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static byte[] read(String dest) {
		return read(Paths.get(dest));
	}

	public static byte[] read(Path path) {
		try {
			return Files.readAllBytes(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new byte[0];
	}
	
	public static byte[] read(File file) {
		return read(file.getPath());
	}
	
	public static String getFilenameWithoutPath(String fileName) {
		if (fileName.indexOf(".") > 0) {
		   return fileName.substring(0, fileName.lastIndexOf("."));
		} else {
		   return fileName;
		}
	}
}
