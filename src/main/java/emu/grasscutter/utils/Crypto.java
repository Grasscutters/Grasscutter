package emu.grasscutter.utils;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import emu.grasscutter.Grasscutter;

public final class Crypto {
    private static final SecureRandom secureRandom = new SecureRandom();

    public static byte[] DISPATCH_KEY;
    public static byte[] DISPATCH_SEED;

    public static byte[] ENCRYPT_KEY;
    public static long ENCRYPT_SEED = Long.parseUnsignedLong("11468049314633205968");
    public static byte[] ENCRYPT_SEED_BUFFER = new byte[0];

    public static PublicKey CUR_OS_ENCRYPT_KEY;
    public static PublicKey CUR_CN_ENCRYPT_KEY;
    public static PrivateKey CUR_SIGNING_KEY;

    public static void loadKeys() {
        DISPATCH_KEY = FileUtils.readResource("/keys/dispatchKey.bin");
        DISPATCH_SEED = FileUtils.readResource("/keys/dispatchSeed.bin");

        ENCRYPT_KEY = FileUtils.readResource("/keys/secretKey.bin");
        ENCRYPT_SEED_BUFFER = FileUtils.readResource("/keys/secretKeyBuffer.bin");

        try {
            //These should be loaded from ChannelConfig_whatever.json
            CUR_SIGNING_KEY = KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(FileUtils.readResource("/keys/SigningKey.der")));

            CUR_OS_ENCRYPT_KEY = KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(FileUtils.readResource("/keys/OSCB_Pub.der")));

            CUR_CN_ENCRYPT_KEY = KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(FileUtils.readResource("/keys/OSCN_Pub.der")));
        }
        catch (Exception e) {
            Grasscutter.getLogger().error("An error occurred while loading keys.", e);
        }
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
