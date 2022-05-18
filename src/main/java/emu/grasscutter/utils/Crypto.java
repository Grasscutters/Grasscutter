package emu.grasscutter.utils;

import java.security.SecureRandom;
import java.util.Base64;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.net.proto.GetPlayerTokenRspOuterClass.GetPlayerTokenRsp;
import emu.grasscutter.net.proto.QueryCurrRegionHttpRspOuterClass.QueryCurrRegionHttpRsp;

import static emu.grasscutter.Configuration.*;

public final class Crypto {
	private static final SecureRandom secureRandom = new SecureRandom();

	public static byte[] DISPATCH_KEY;
	public static byte[] DISPATCH_SEED;

	public static byte[] ENCRYPT_KEY;
	public static long ENCRYPT_SEED = Long.parseUnsignedLong("11468049314633205968");
	public static byte[] ENCRYPT_SEED_BUFFER = new byte[0];
	
	public static void loadKeys() {
		DISPATCH_KEY = FileUtils.readResource("/keys/dispatchKey.bin");
		DISPATCH_SEED = FileUtils.readResource("/keys/dispatchSeed.bin");

		ENCRYPT_KEY = FileUtils.readResource("/keys/secretKey.bin");
		ENCRYPT_SEED_BUFFER = FileUtils.readResource("/keys/secretKeyBuffer.bin");
	}
	
	public static void xor(byte[] packet, byte[] key) {
		try {
			for (int i = 0; i < packet.length; i++) {
				packet[i] ^= key[i % key.length];
			}
		} catch (Exception e) {
			Grasscutter.getLogger().error("Crypto error.", e);
		}
	}
	
	public static byte[] createSessionKey(int length) {
		byte[] bytes = new byte[length];
		secureRandom.nextBytes(bytes);
		return bytes;
	}
}
