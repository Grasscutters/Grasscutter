package emu.grasscutter.server.http.dispatch;

import com.google.protobuf.ByteString;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.Grasscutter.ServerRunMode;
import emu.grasscutter.net.proto.QueryRegionListHttpRspOuterClass.QueryRegionListHttpRsp;
import emu.grasscutter.net.proto.QueryCurrRegionHttpRspOuterClass.QueryCurrRegionHttpRsp;
import emu.grasscutter.net.proto.RegionSimpleInfoOuterClass.RegionSimpleInfo;
import emu.grasscutter.net.proto.RegionInfoOuterClass.RegionInfo;
import emu.grasscutter.server.event.dispatch.QueryAllRegionsEvent;
import emu.grasscutter.server.event.dispatch.QueryCurrentRegionEvent;
import emu.grasscutter.server.http.Router;
import emu.grasscutter.server.http.objects.QueryCurRegionRspJson;
import emu.grasscutter.utils.Crypto;
import emu.grasscutter.utils.Utils;
import io.javalin.Javalin;
import io.javalin.http.Context;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.security.Signature;
import java.util.regex.Pattern;

import static emu.grasscutter.config.Configuration.*;

/**
 * Handles requests related to region queries.
 */
public final class RegionHandler implements Router {
    private static final Map<String, RegionData> regions = new ConcurrentHashMap<>();
    private static String regionListResponse;

    public RegionHandler() {
        try { // Read & initialize region data.
            this.initialize();
        } catch (Exception exception) {
            Grasscutter.getLogger().error("Failed to initialize region data.", exception);
        }
    }

    /**
     * Configures region data according to configuration.
     */
    private void initialize() {
        String dispatchDomain = "http" + (HTTP_ENCRYPTION.useInRouting ? "s" : "") + "://"
                + lr(HTTP_INFO.accessAddress, HTTP_INFO.bindAddress) + ":"
                + lr(HTTP_INFO.accessPort, HTTP_INFO.bindPort);

        // Create regions.
        List<RegionSimpleInfo> servers = new ArrayList<>();
        List<String> usedNames = new ArrayList<>(); // List to check for potential naming conflicts.

        var configuredRegions = new ArrayList<>(List.of(DISPATCH_INFO.regions));
        if (SERVER.runMode != ServerRunMode.HYBRID && configuredRegions.size() == 0) {
            Grasscutter.getLogger().error("[Dispatch] There are no game servers available. Exiting due to unplayable state.");
            System.exit(1);
        } else if (configuredRegions.size() == 0)
            configuredRegions.add(new Region("os_usa", DISPATCH_INFO.defaultName,
                    lr(GAME_INFO.accessAddress, GAME_INFO.bindAddress),
                    lr(GAME_INFO.accessPort, GAME_INFO.bindPort)));

        configuredRegions.forEach(region -> {
            if (usedNames.contains(region.Name)) {
                Grasscutter.getLogger().error("Region name already in use.");
                return;
            }

            // Create a region identifier.
            var identifier = RegionSimpleInfo.newBuilder()
                    .setName(region.Name).setTitle(region.Title).setType("DEV_PUBLIC")
                    .setDispatchUrl(dispatchDomain + "/query_cur_region/" + region.Name)
                    .build();
            usedNames.add(region.Name); servers.add(identifier);

            // Create a region info object.
            var regionInfo = RegionInfo.newBuilder()
                    .setGateserverIp(region.Ip).setGateserverPort(region.Port)
                    .setSecretKey(ByteString.copyFrom(Crypto.DISPATCH_SEED))
                    .build();
            // Create an updated region query.
            var updatedQuery = QueryCurrRegionHttpRsp.newBuilder().setRegionInfo(regionInfo).build();
            regions.put(region.Name, new RegionData(updatedQuery, Utils.base64Encode(updatedQuery.toByteString().toByteArray())));
        });

        // Create a config object.
        byte[] customConfig = "{\"sdkenv\":\"2\",\"checkdevice\":\"false\",\"loadPatch\":\"false\",\"showexception\":\"false\",\"regionConfig\":\"pm|fk|add\",\"downloadMode\":\"0\"}".getBytes();
        Crypto.xor(customConfig, Crypto.DISPATCH_KEY); // XOR the config with the key.

        // Create an updated region list.
        QueryRegionListHttpRsp updatedRegionList = QueryRegionListHttpRsp.newBuilder()
                .addAllRegionList(servers)
                .setClientSecretKey(ByteString.copyFrom(Crypto.DISPATCH_SEED))
                .setClientCustomConfigEncrypted(ByteString.copyFrom(customConfig))
                .setEnableLoginPc(true).build();

        // Set the region list response.
        regionListResponse = Utils.base64Encode(updatedRegionList.toByteString().toByteArray());
    }

    @Override public void applyRoutes(Javalin javalin) {
        javalin.get("/query_region_list", RegionHandler::queryRegionList);
        javalin.get("/query_cur_region/{region}", RegionHandler::queryCurrentRegion );
    }

    /**
     * @route /query_region_list
     */
    private static void queryRegionList(Context ctx) {
        // Invoke event.
        QueryAllRegionsEvent event = new QueryAllRegionsEvent(regionListResponse); event.call();
        // Respond with event result.
        ctx.result(event.getRegionList());

        // Log to console.
        Grasscutter.getLogger().info(String.format("[Dispatch] Client %s request: query_region_list", ctx.ip()));
    }

    /**
     * @route /query_cur_region/{region}
     */
    private static void queryCurrentRegion(Context ctx) {
        // Get region to query.
        String regionName = ctx.pathParam("region");
        String versionName = ctx.queryParam("version");
        var region = regions.get(regionName);

        // Get region data.
        String regionData = "CAESGE5vdCBGb3VuZCB2ZXJzaW9uIGNvbmZpZw==";
        if (ctx.queryParamMap().values().size() > 0) {
            if (region != null)
                regionData = region.getBase64();
        }

        String[] versionCode = versionName.replaceAll(Pattern.compile("[a-zA-Z]").pattern(), "").split("\\.");
        int versionMajor = Integer.parseInt(versionCode[0]);
        int versionMinor = Integer.parseInt(versionCode[1]);
        int versionFix   = Integer.parseInt(versionCode[2]);

        if (versionMajor >= 3 || (versionMajor == 2 && versionMinor == 7 && versionFix >= 50) || (versionMajor == 2 && versionMinor == 8)) {
            try {
                QueryCurrentRegionEvent event = new QueryCurrentRegionEvent(regionData); event.call();

                if (ctx.queryParam("dispatchSeed") == null) {
                    // More love for UA Patch players
                    var rsp = new QueryCurRegionRspJson();

                    rsp.content = event.getRegionInfo();
                    rsp.sign = "TW9yZSBsb3ZlIGZvciBVQSBQYXRjaCBwbGF5ZXJz";

                    ctx.json(rsp);
                    return;
                }

                String key_id = ctx.queryParam("key_id");

                if (key_id == null)
                    throw new Exception("Key ID was not set");

                Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                cipher.init(Cipher.ENCRYPT_MODE, Crypto.EncryptionKeys.get(Integer.valueOf(key_id)));
                var regionInfo = Utils.base64Decode(event.getRegionInfo());

                //Encrypt regionInfo in chunks
                ByteArrayOutputStream encryptedRegionInfoStream = new ByteArrayOutputStream();

                //Thank you so much GH Copilot
                int chunkSize = 256 - 11;
                int regionInfoLength = regionInfo.length;
                int numChunks = (int) Math.ceil(regionInfoLength / (double) chunkSize);

                for (int i = 0; i < numChunks; i++) {
                    byte[] chunk = Arrays.copyOfRange(regionInfo, i * chunkSize, Math.min((i + 1) * chunkSize, regionInfoLength));
                    byte[] encryptedChunk = cipher.doFinal(chunk);
                    encryptedRegionInfoStream.write(encryptedChunk);
                }

                Signature privateSignature = Signature.getInstance("SHA256withRSA");
                privateSignature.initSign(Crypto.CUR_SIGNING_KEY);
                privateSignature.update(regionInfo);

                var rsp = new QueryCurRegionRspJson();

                rsp.content = Utils.base64Encode(encryptedRegionInfoStream.toByteArray());
                rsp.sign = Utils.base64Encode(privateSignature.sign());

                ctx.json(rsp);
            }
            catch (Exception e) {
                Grasscutter.getLogger().error("An error occurred while handling query_cur_region.", e);
            }
        }
        else {
            // Invoke event.
            QueryCurrentRegionEvent event = new QueryCurrentRegionEvent(regionData); event.call();
            // Respond with event result.
            ctx.result(event.getRegionInfo());
        }
        // Log to console.
        Grasscutter.getLogger().info(String.format("Client %s request: query_cur_region/%s", ctx.ip(), regionName));
    }

    /**
     * Region data container.
     */
    public static class RegionData {
        private final QueryCurrRegionHttpRsp regionQuery;
        private final String base64;

        public RegionData(QueryCurrRegionHttpRsp prq, String b64) {
            this.regionQuery = prq;
            this.base64 = b64;
        }

        public QueryCurrRegionHttpRsp getRegionQuery() {
            return this.regionQuery;
        }

        public String getBase64() {
            return this.base64;
        }
    }

    /**
     * Gets the current region query.
     * @return A {@link QueryCurrRegionHttpRsp} object.
     */
    public static QueryCurrRegionHttpRsp getCurrentRegion() {
        return SERVER.runMode == ServerRunMode.HYBRID ? regions.get("os_usa").getRegionQuery() : null;
    }
}
