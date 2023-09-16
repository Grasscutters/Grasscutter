package emu.grasscutter.utils;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.server.http.objects.QueryCurRegionRspJson;
import emu.grasscutter.utils.algorithms.MersenneTwister64;
import java.io.ByteArrayOutputStream;
import java.nio.file.Path;
import java.security.*;
import java.security.spec.*;
import java.util.*;
import java.util.regex.Pattern;
import javax.crypto.Cipher;

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
            CUR_SIGNING_KEY =
                    KeyFactory.getInstance("RSA")
                            .generatePrivate(
                                    new PKCS8EncodedKeySpec(FileUtils.readResource("/keys/SigningKey.der")));

            Pattern pattern = Pattern.compile("([0-9]*)_Pub\\.der");
            for (Path path : FileUtils.getPathsFromResource("/keys/game_keys")) {
                if (path.toString().endsWith("_Pub.der")) {

                    var m = pattern.matcher(path.getFileName().toString());

                    if (m.matches()) {
                        var key =
                                KeyFactory.getInstance("RSA")
                                        .generatePublic(new X509EncodedKeySpec(FileUtils.read(path)));

                        EncryptionKeys.put(Integer.valueOf(m.group(1)), key);
                    }
                }
            }
        } catch (Exception e) {
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

    public static long generateEncryptKeyAndSeed(byte[] encryptKey) {
        var encryptSeed = secureRandom.nextLong();
        var mt = new MersenneTwister64();
        mt.setSeed(encryptSeed);
        mt.setSeed(mt.nextLong());
        mt.nextLong();
        for (int i = 0; i < 4096 >> 3; i++) {
            var rand = mt.nextLong();
            encryptKey[i << 3] = (byte) (rand >> 56);
            encryptKey[(i << 3) + 1] = (byte) (rand >> 48);
            encryptKey[(i << 3) + 2] = (byte) (rand >> 40);
            encryptKey[(i << 3) + 3] = (byte) (rand >> 32);
            encryptKey[(i << 3) + 4] = (byte) (rand >> 24);
            encryptKey[(i << 3) + 5] = (byte) (rand >> 16);
            encryptKey[(i << 3) + 6] = (byte) (rand >> 8);
            encryptKey[(i << 3) + 7] = (byte) rand;
        }
        return encryptSeed;
    }

    public static QueryCurRegionRspJson encryptAndSignRegionData(byte[] regionInfo, String key_id)
            throws Exception {
        if (key_id == null) {
            throw new Exception("Key ID was not set");
        }

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, EncryptionKeys.get(Integer.valueOf(key_id)));

        // Encrypt regionInfo in chunks
        ByteArrayOutputStream encryptedRegionInfoStream = new ByteArrayOutputStream();

        // Thank you so much GH Copilot
        int chunkSize = 256 - 11;
        int regionInfoLength = regionInfo.length;
        int numChunks = (int) Math.ceil(regionInfoLength / (double) chunkSize);

        for (int i = 0; i < numChunks; i++) {
            byte[] chunk =
                    Arrays.copyOfRange(
                            regionInfo, i * chunkSize, Math.min((i + 1) * chunkSize, regionInfoLength));
            byte[] encryptedChunk = cipher.doFinal(chunk);
            encryptedRegionInfoStream.write(encryptedChunk);
        }

        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(CUR_SIGNING_KEY);
        privateSignature.update(regionInfo);

        var rsp = new QueryCurRegionRspJson();

        rsp.content = Utils.base64Encode(encryptedRegionInfoStream.toByteArray());
        rsp.sign = Utils.base64Encode(privateSignature.sign());
        return rsp;
    }
}
