package emu.grasscutter.utils;

import emu.grasscutter.server.http.objects.QueryCurRegionRspJson;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Pattern;

import emu.grasscutter.Grasscutter;
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
            CUR_SIGNING_KEY = KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(FileUtils.readResource("/keys/SigningKey.der")));

            Pattern pattern = Pattern.compile("([0-9]*)_Pub\\.der");
            for (Path path : FileUtils.getPathsFromResource("/keys/game_keys")) {
                if (path.toString().endsWith("_Pub.der")) {

                    var m = pattern.matcher(path.getFileName().toString());

                    if (m.matches()) {
                        var key = KeyFactory.getInstance("RSA")
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

    public static QueryCurRegionRspJson encryptAndSignRegionData(byte[] regionInfo, String key_id) throws Exception {
        if (key_id == null) {
            throw new Exception("Key ID was not set");
        }

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, EncryptionKeys.get(Integer.valueOf(key_id)));

        //Encrypt regionInfo in chunks
        ByteArrayOutputStream encryptedRegionInfoStream = new ByteArrayOutputStream();

        //Thank you so much GH Copilot
        int chunkSize = 256 - 11;
        int regionInfoLength = regionInfo.length;
        int numChunks = (int) Math.ceil(regionInfoLength / (double) chunkSize);

        for (int i = 0; i < numChunks; i++) {
            byte[] chunk = Arrays.copyOfRange(regionInfo, i * chunkSize,
                Math.min((i + 1) * chunkSize, regionInfoLength));
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
