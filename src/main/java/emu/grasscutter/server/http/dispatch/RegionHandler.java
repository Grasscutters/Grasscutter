package emu.grasscutter.server.http.dispatch;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.Grasscutter.ServerRunMode;
import emu.grasscutter.net.proto.QueryCurrRegionHttpRspOuterClass.*;
import emu.grasscutter.net.proto.RegionSimpleInfoOuterClass.RegionSimpleInfo;
import emu.grasscutter.server.event.dispatch.QueryAllRegionsEvent;
import emu.grasscutter.server.event.dispatch.QueryCurrentRegionEvent;
import emu.grasscutter.server.http.Router;
import emu.grasscutter.utils.FileUtils;
import emu.grasscutter.utils.Utils;
import express.Express;
import express.http.Request;
import express.http.Response;
import io.javalin.Javalin;

import java.io.File;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static emu.grasscutter.Configuration.*;
import static emu.grasscutter.net.proto.QueryRegionListHttpRspOuterClass.*;

/**
 * Handles requests related to region queries.
 */
public final class RegionHandler implements Router {
    private String regionQuery = "";
    private String regionList = "";
    
    private static final Map<String, RegionData> regions = new ConcurrentHashMap<>();
    private static String regionListResponse;
    
    public RegionHandler() {
        try { // Read & initialize region data.
            this.readRegionData();
            this.initialize();
        } catch (Exception exception) {
            Grasscutter.getLogger().error("Failed to initialize region data.", exception);
        }
    }

    /**
     * Loads initial region data.
     */
    private void readRegionData() {
        File file;

        file = new File(DATA("query_region_list.txt"));
        if (file.exists()) 
            this.regionList = new String(FileUtils.read(file));
        else Grasscutter.getLogger().error("[Dispatch] 'query_region_list' not found!");

        file = new File(DATA("query_cur_region.txt"));
        if (file.exists()) 
            regionQuery = new String(FileUtils.read(file));
        else Grasscutter.getLogger().warn("[Dispatch] 'query_cur_region' not found!");
    }

    /**
     * Configures region data according to configuration.
     */
    private void initialize() throws InvalidProtocolBufferException {
        // Decode the initial region query.
        byte[] queryBase64 = Base64.getDecoder().decode(this.regionQuery);
        QueryCurrRegionHttpRsp regionQuery = QueryCurrRegionHttpRsp.parseFrom(queryBase64);
        
        // Create regions.
        List<RegionSimpleInfo> servers = new ArrayList<>();
        List<String> usedNames = new ArrayList<>(); // List to check for potential naming conflicts.
        
        var configuredRegions = new ArrayList<>(List.of(DISPATCH_INFO.regions));
        if(SERVER.runMode != ServerRunMode.HYBRID && configuredRegions.size() == 0) {
            Grasscutter.getLogger().error("[Dispatch] There are no game servers available. Exiting due to unplayable state.");
            System.exit(1);
        } else configuredRegions.add(new Region("os_usa", DISPATCH_INFO.defaultName,
                lr(GAME_INFO.accessAddress, GAME_INFO.bindAddress), 
                lr(GAME_INFO.accessPort, GAME_INFO.bindPort)));
        
        configuredRegions.forEach(region -> {
            if (usedNames.contains(region.Name)) {
                Grasscutter.getLogger().error("Region name already in use.");
                return;
            }
            
            // Create a region identifier.
            var identifier = RegionSimpleInfo.newBuilder()
                    .setName(region.Name).setTitle(region.Title)
                    .setType("DEV_PUBLIC").setDispatchUrl(
                            "http" + (HTTP_ENCRYPTION.useInRouting ? "s" : "") + "://"
                                    + lr(HTTP_INFO.accessAddress, HTTP_INFO.bindAddress) + ":"
                                    + lr(HTTP_INFO.accessPort, HTTP_INFO.bindPort)
                                    + "/query_cur_region/" + region.Name)
                    .build();
            usedNames.add(region.Name); servers.add(identifier);
            
            // Create a region info object.
            var regionInfo = regionQuery.getRegionInfo().toBuilder()
                    .setGateserverIp(region.Ip).setGateserverPort(region.Port)
                    .setSecretKey(ByteString.copyFrom(FileUtils.read(KEYS_FOLDER + "/dispatchSeed.bin")))
                    .build();
            // Create an updated region query.
            var updatedQuery = regionQuery.toBuilder().setRegionInfo(regionInfo).build();
            regions.put(region.Name, new RegionData(updatedQuery, Utils.base64Encode(updatedQuery.toByteString().toByteArray())));
        });
        
        // Decode the initial region list.
        byte[] listBase64 = Base64.getDecoder().decode(this.regionList);
        QueryRegionListHttpRsp regionList = QueryRegionListHttpRsp.parseFrom(listBase64);
        
        // Create an updated region list.
        QueryRegionListHttpRsp updatedRegionList = QueryRegionListHttpRsp.newBuilder()
                .addAllRegionList(servers)
                .setClientSecretKey(regionList.getClientSecretKey())
                .setClientCustomConfigEncrypted(regionList.getClientCustomConfigEncrypted())
                .setEnableLoginPc(true).build();
        
        // Set the region list response.
        regionListResponse = Utils.base64Encode(updatedRegionList.toByteString().toByteArray());
    }
    
    @Override public void applyRoutes(Express express, Javalin handle) {
        express.get("/query_region_list", RegionHandler::queryRegionList);
        express.get("/query_cur_region/:region", RegionHandler::queryCurrentRegion );
    }

    /**
     * @route /query_region_list
     */
    private static void queryRegionList(Request request, Response response) {
        // Invoke event.
        QueryAllRegionsEvent event = new QueryAllRegionsEvent(regionListResponse); event.call();
        // Respond with event result.
        response.send(event.getRegionList());
        
        // Log to console.
        Grasscutter.getLogger().info(String.format("[Dispatch] Client %s request: query_region_list", request.ip()));
    }

    /**
     * @route /query_cur_region/:region
     */
    private static void queryCurrentRegion(Request request, Response response) {
        // Get region to query.
        String regionName = request.params("region");
        
        // Get region data.
        String regionData = "CAESGE5vdCBGb3VuZCB2ZXJzaW9uIGNvbmZpZw==";
        if (request.query().values().size() > 0) {
            var region = regions.get(regionName);
            if(region != null) regionData = region.getBase64();
        }
        
        // Invoke event.
        QueryCurrentRegionEvent event = new QueryCurrentRegionEvent(regionData); event.call();
        // Respond with event result.
        response.send(event.getRegionInfo());

        // Log to console.
        Grasscutter.getLogger().info(String.format("Client %s request: query_cur_region/%s", request.ip(), regionName));
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
