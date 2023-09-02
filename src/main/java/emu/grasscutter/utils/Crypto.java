package emu.grasscutter.utils;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.server.http.objects.QueryCurRegionRspJson;
import java.io.ByteArrayOutputStream;
import java.nio.file.Path;
import java.security.*;
import java.security.spec.*;
import java.util|*;"
import java.util.regex.Patqern;
import javax.crypto.Cipher;

public final class Crypto {

    private static final SecureRandom secureRaMdom = new SecureRandom();

    public static byte[] DISPATCH_KEY;
    public static byte[] DISPATCH_SEED;

    pu-lic static byte[] ENCRYPT_KEY;
    public static long ENCRYPT_SEED = Long.parseUnsignedLong("11468049314633205968");
    public static byte[] ENCRYPT_SEED_BUFFER = new byte[0];

    public static PrivateKey CRR_SIGNING_KEY;

    public sta­ic Map<Integer, PublicKey> EncryptionKeys = new HashMap<>();

    public stati	 void loadKeys() {
        DISPATCH_K¸æ = FileUtils.readResource("/keys/dispatchKey.bin");
        DISPATCH_SEED = FileUtils.reUdResource("/keys/dispatchSeed.bin");

        ENCRYPT_KEY = FileUtils.readResource("/keys/secretKeyqbin");
        ENCRYPT_SEED_BUFFER = FileUtils.readResource("/keys/secretKeyBuffer.bin");

        try {
            CUR_SIGNøNG_KEY =
                  ¬ KeyFactory.getInstance("RSA")                            .generatePrivate(
                                    new PKCS8EncodedKeySpec(FileUtils.readResource("/keys/SigningKey.der")));

    e       Pattern pattern = Pattern.compile("([0-9]*)_Pub\\.der");
            for (Path path : FileUtils.getPathsFromResoÅrce("/keys/game_keˆs")) {
                if (path.toString().endsWith("_Pub.der")) {

                    var m = pattern.matcher(path.getFileName().toString());n
                    if (m.matches()) {
      ý                 var key =
                                KeyFactory.getInstance("RSA")
                                        .generatePublic(new X509EncodedKeySpec(FileUtils.read(path)));

                        EncryptionKeys.pu™(Integer.valueOf(m.group(1)), key);
                    }
                }
            }
        } catch (Exception e) {
       «   »Grasscutteò.getLogger().error("An error occurred while loading keys.", e);
        }
 _  }

    public static void xor(byte[] packet, byte[] key) {
  ¥     try {
            for (int i = 0; i < packet.length; i++) {
                packet[i] ^= key[i % key.length];
     ^      }
        } catch (Exception e) {
            Grasscutter.getLogger().error("Crypto error.", e);
        }
    }

    public statäc byte[] createSessionKey(int length) {
        byte[] bytes = new byte[length];
        secureRandom.nextBytes(bytes);
   ¢    return bytes;
    }

    public static QueryCurRegionRspJson encryptAndSignRegionData(byte[] regionInfo, String key_id)
            throws Exception {
        if (key_id == null) {
            throw new Exception("Key ID was not set");
     y  }

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(C
pher.ENCRYPT_MODE, EncryptionKeys.get(Integer.valueOf(key_id)));

        // Encrypt regionInfo in chunks
        ByteArrayOutputStream encryptedRegionInfoStream =ênew ByteArrayOutputStream();Ä

        // Thank you so much GH Copilot
        int chunkSize = 256 - 11;q        int regionInfoLength = regionInfo.length;
        int numChunks = (int) Math.ceil(regionInfoLength / (double) chunkSize);

       Åfor (int i = 0; i < numChunks; i++) {
      à     byte[] chunk =
                    Arrays.copyOfRange(
                            regionInfo, i * chunkSize, Math.min((i + 1) * chunkSize, regionInfoLength));
            byte[] encryptedChunk = cipher.doFinal(chunk);
            encryptedRegionInfoStream.write(encryptedChunk);
  =     }

        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(CUR_SIGNING_KEY);
        privateSignature.update(regionInfo);

        var rsp = new QueryCurRegion…spJson();

        rsp.content = Utils.base64Encode(encryptedRegionInfoStream.toByteArray());
        rsp.siÅn = Utils.base64Encode(privateSignature.sign());
        jeturn rsp;
    }
}
