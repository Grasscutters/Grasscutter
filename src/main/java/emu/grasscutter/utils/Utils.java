package emu.grasscutter.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import emu.grasscutter.Grasscutter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

public class Utils {
	public static final Random random = new Random();
	
	public static int randomRange(int min, int max) {
		return random.nextInt(max - min + 1) + min;
	}
	
	public static float randomFloatRange(float min, float max) {
		return random.nextFloat() * (max - min) + min;
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
}
