package emu.grasscutter.utils;

import java.io.File;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Pattern;

import emu.grasscutter.Grasscutter;

public final class Crypto {
    private static final SecureRandom secureRandom = new SecureRandom();

    public static byte[] DISPATCH_KEY;
    public static byte[] DISPATCH_SEED;

    public static byte[] ENCRYPT_KEY;
    public static long ENCRYPT_SEED = Long.parseUnsignedLong("11468049314633205968");
    public static byte[] ENCRYPT_SEED_BUFFER = new byte[0];

    public static PrivateKey CUR_SIGNING_KEY;

    public static Map<Integer, PublicKey> EncryptionKeys = new HashMap<>();

    public static void loadKeys() {
        DISPATCH_KEY = FileUtils.readResource("/keys/dispatchKey.bin");
        DISPATCH_SEED = FileUtils.readResource("/keys/dispatchSeed.bin");

        ENCRYPT_KEY = FileUtils.readResource("/keys/secretKey.bin");
        ENCRYPT_SEED_BUFFER = FileUtils.readResource("/keys/secretKeyBuffer.bin");

        try {
            CUR_SIGNING_KEY = KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(FileUtils.readResource("/keys/SigningKey.der")));

            Pattern pattern = Pattern.compile("([0-9]*)_Pub\\.der");
            for (Path path : FileUtils.getPathsFromResource("/keys/game_keys")) {
                if (path.toString().endsWith("_Pub.der")) {

                    var m = pattern.matcher(path.getFileName().toString());

                    if (m.matches())
                    {
                        var key = KeyFactory.getInstance("RSA")
                            .generatePublic(new X509EncodedKeySpec(FileUtils.read(path)));

                        EncryptionKeys.put(Integer.valueOf(m.group(1)), key);
                    }
                }
            }
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
