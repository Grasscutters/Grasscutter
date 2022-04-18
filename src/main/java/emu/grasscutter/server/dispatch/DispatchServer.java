package emu.grasscutter.server.dispatch;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.security.KeyStore;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.protobuf.ByteString;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.Account;
import emu.grasscutter.net.proto.QueryCurrRegionHttpRspOuterClass.QueryCurrRegionHttpRsp;
import emu.grasscutter.net.proto.QueryRegionListHttpRspOuterClass.QueryRegionListHttpRsp;
import emu.grasscutter.net.proto.RegionInfoOuterClass.RegionInfo;
import emu.grasscutter.net.proto.RegionSimpleInfoOuterClass.RegionSimpleInfo;
import emu.grasscutter.server.dispatch.json.ComboTokenReqJson;
import emu.grasscutter.server.dispatch.json.ComboTokenResJson;
import emu.grasscutter.server.dispatch.json.LoginAccountRequestJson;
import emu.grasscutter.server.dispatch.json.LoginResultJson;
import emu.grasscutter.server.dispatch.json.LoginTokenRequestJson;
import emu.grasscutter.server.dispatch.json.ComboTokenReqJson.LoginTokenData;
import emu.grasscutter.utils.FileUtils;
import emu.grasscutter.utils.Utils;

import com.sun.net.httpserver.HttpServer;

public final class DispatchServer {
	private final InetSocketAddress address;
	private final Gson gson;
	private QueryCurrRegionHttpRsp currRegion;
	
	public String regionListBase64;
	public String regionCurrentBase64;
	
	public static String query_region_list = "";
	public static String query_cur_region = "";
	
	public DispatchServer() {
		this.address = new InetSocketAddress(Grasscutter.getConfig().DispatchServerIp, Grasscutter.getConfig().DispatchServerPort);
		this.gson = new GsonBuilder().create();
		
		this.loadQueries();
		this.initRegion();
	}
	
	public InetSocketAddress getAddress() {
		return address;
	}
	
	public Gson getGsonFactory() {
		return gson;
	}

	public QueryCurrRegionHttpRsp getCurrRegion() {
		return currRegion;
	}
	
	public void loadQueries() {
		File file;
		
		file = new File(Grasscutter.getConfig().DATA_FOLDER + "query_region_list.txt");
		if (file.exists()) {
			query_region_list = new String(FileUtils.read(file));
		} else {
			Grasscutter.getLogger().warn("query_region_list not found! Using default region list.");
		}
	
		file = new File(Grasscutter.getConfig().DATA_FOLDER + "query_cur_region.txt");
		if (file.exists()) {
			query_cur_region = new String(FileUtils.read(file));
		} else {
			Grasscutter.getLogger().warn("query_cur_region not found! Using default current region.");
		}
	}

	private void initRegion() {
		try {
			byte[] decoded = Base64.getDecoder().decode(query_region_list);
			QueryRegionListHttpRsp rl = QueryRegionListHttpRsp.parseFrom(decoded);
			
			byte[] decoded2 = Base64.getDecoder().decode(query_cur_region);
			QueryCurrRegionHttpRsp regionQuery = QueryCurrRegionHttpRsp.parseFrom(decoded2);
			
			RegionSimpleInfo server = RegionSimpleInfo.newBuilder()
					.setName("os_usa")
					.setTitle(Grasscutter.getConfig().GameServerName)
					.setType("DEV_PUBLIC")
					.setDispatchUrl("https://" + (Grasscutter.getConfig().DispatchServerPublicIp.isEmpty() ? Grasscutter.getConfig().DispatchServerIp : Grasscutter.getConfig().DispatchServerPublicIp) + ":" + getAddress().getPort() + "/query_cur_region")
					.build();
			
			RegionSimpleInfo serverTest2 = RegionSimpleInfo.newBuilder()
					.setName("os_euro")
					.setTitle("Grasscutter")
					.setType("DEV_PUBLIC")
					.setDispatchUrl("https://" + (Grasscutter.getConfig().DispatchServerPublicIp.isEmpty() ? Grasscutter.getConfig().DispatchServerIp : Grasscutter.getConfig().DispatchServerPublicIp) + ":" + getAddress().getPort() + "/query_cur_region")
					.build();
				
			QueryRegionListHttpRsp regionList = QueryRegionListHttpRsp.newBuilder()
				.addServers(server)
				.addServers(serverTest2)
				.setClientSecretKey(rl.getClientSecretKey())
			    .setClientCustomConfigEncrypted(rl.getClientCustomConfigEncrypted())
			    .setEnableLoginPc(true)
				.build();
						
			RegionInfo currentRegion = regionQuery.getRegionInfo().toBuilder()
					.setIp((Grasscutter.getConfig().GameServerPublicIp.isEmpty() ? Grasscutter.getConfig().GameServerIp : Grasscutter.getConfig().GameServerPublicIp))
					.setPort(Grasscutter.getConfig().GameServerPort)
					.setSecretKey(ByteString.copyFrom(FileUtils.read(Grasscutter.getConfig().KEY_FOLDER + "dispatchSeed.bin")))
					.build();
			
			QueryCurrRegionHttpRsp parsedRegionQuery = regionQuery.toBuilder().setRegionInfo(currentRegion).build();

			this.regionListBase64 = Base64.getEncoder().encodeToString(regionList.toByteString().toByteArray());
			this.regionCurrentBase64 = Base64.getEncoder().encodeToString(parsedRegionQuery.toByteString().toByteArray());
			this.currRegion = parsedRegionQuery;
		} catch (Exception e) {
			Grasscutter.getLogger().error("Error while initializing region info!", e);
		}
	}

	public void start() throws Exception {
		HttpsServer server = HttpsServer.create(getAddress(), 0);
		SSLContext sslContext = SSLContext.getInstance("TLS");
		
		try (FileInputStream fis = new FileInputStream(Grasscutter.getConfig().DispatchServerKeystorePath)) {
			char[] keystorePassword = Grasscutter.getConfig().DispatchServerKeystorePassword.toCharArray();
			KeyStore ks = KeyStore.getInstance("PKCS12");
			ks.load(fis, keystorePassword);
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(ks, keystorePassword);
			
			sslContext.init(kmf.getKeyManagers(), null, null);
			
			server.setHttpsConfigurator(new HttpsConfigurator(sslContext));
		} catch (Exception e) {
			Grasscutter.getLogger().error("No SSL cert found!");
			return;
		}
		
		server.createContext("/", t -> {
			//Create a response form the request query parameters
			String response = "Hello";
			//Set the response header status and length
			t.getResponseHeaders().put("Content-Type", Collections.singletonList("text/html; charset=UTF-8"));
			t.sendResponseHeaders(200, response.getBytes().length);
			//Write the response string
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		});
		
		// Dispatch
		server.createContext("/query_region_list", t -> {
			// Log
			Grasscutter.getLogger().info("Client request: query_region_list");
			// Create a response form the request query parameters
			String response = regionListBase64;
			// Set the response header status and length
			t.getResponseHeaders().put("Content-Type", Collections.singletonList("text/html; charset=UTF-8"));
			t.sendResponseHeaders(200, response.getBytes().length);
			// Write the response string
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		});
		server.createContext("/query_cur_region", t -> {
			// Log
			Grasscutter.getLogger().info("Client request: query_cur_region");
			// Create a response form the request query parameters
			URI uri = t.getRequestURI();
			String response = "CAESGE5vdCBGb3VuZCB2ZXJzaW9uIGNvbmZpZw==";
			if (uri.getQuery() != null && uri.getQuery().length() > 0) {
				response = regionCurrentBase64;
			}
			// Set the response header status and length
			t.getResponseHeaders().put("Content-Type", Collections.singletonList("text/html; charset=UTF-8"));
			t.sendResponseHeaders(200, response.getBytes().length);
			// Write the response string
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		});
		// Login via account
		server.createContext("/hk4e_global/mdk/shield/api/login", t -> {
			// Get post data
			LoginAccountRequestJson requestData = null;
			try {
				String body = Utils.toString(t.getRequestBody());
				requestData = getGsonFactory().fromJson(body, LoginAccountRequestJson.class);
			} catch (Exception e) {
				
			}
			// Create response json
			if (requestData == null) {
				return;
			}
			LoginResultJson responseData = new LoginResultJson();
			
			// Login
			Account account = DatabaseHelper.getAccountByName(requestData.account);
			
			// Test
			if (account == null) {
				responseData.retcode = -201;
				responseData.message = "Username not found.";
			} else {
				responseData.message = "OK";
				responseData.data.account.uid = account.getId();
				responseData.data.account.token = account.generateSessionKey();
				responseData.data.account.email = account.getEmail();
			}
			
			// Create a response
			String response = getGsonFactory().toJson(responseData);
			// Set the response header status and length
			t.getResponseHeaders().put("Content-Type", Collections.singletonList("application/json"));
			t.sendResponseHeaders(200, response.getBytes().length);
			// Write the response string
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		});
		// Login via token
		server.createContext("/hk4e_global/mdk/shield/api/verify", t -> {
			// Get post data
			LoginTokenRequestJson requestData = null;
			try {
				String body = Utils.toString(t.getRequestBody());
				requestData = getGsonFactory().fromJson(body, LoginTokenRequestJson.class);
			} catch (Exception e) {
				
			}
			// Create response json
			if (requestData == null) {
				return;
			}
			LoginResultJson responseData = new LoginResultJson();

			// Login
			Account account = DatabaseHelper.getAccountById(requestData.uid);
			
			// Test
			if (account == null || !account.getSessionKey().equals(requestData.token)) {
				responseData.retcode = -111;
				responseData.message = "Game account cache information error";
			} else {
				responseData.message = "OK";
				responseData.data.account.uid = requestData.uid;
				responseData.data.account.token = requestData.token;
				responseData.data.account.email = account.getEmail();
			}
			
			// Create a response
			String response = getGsonFactory().toJson(responseData);
			// Set the response header status and length
			t.getResponseHeaders().put("Content-Type", Collections.singletonList("application/json"));
			t.sendResponseHeaders(200, response.getBytes().length);
			// Write the response string
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		});
		// Exchange for combo token
		server.createContext("/hk4e_global/combo/granter/login/v2/login", t -> {
			// Get post data
			ComboTokenReqJson requestData = null;
			try {
				String body = Utils.toString(t.getRequestBody());
				requestData = getGsonFactory().fromJson(body, ComboTokenReqJson.class);
			} catch (Exception e) {
				
			}
			// Create response json
			if (requestData == null || requestData.data == null) {
				return;
			}
			LoginTokenData loginData = getGsonFactory().fromJson(requestData.data, LoginTokenData.class); // Get login data
			ComboTokenResJson responseData = new ComboTokenResJson();

			// Login
			Account account = DatabaseHelper.getAccountById(loginData.uid);
			
			// Test
			if (account == null || !account.getSessionKey().equals(loginData.token)) {
				responseData.retcode = -201;
				responseData.message = "Wrong session key.";
			} else {
				responseData.message = "OK";
				responseData.data.open_id = loginData.uid;
				responseData.data.combo_id = "157795300";
				responseData.data.combo_token = account.generateLoginToken();
			}
			
			// Create a response
			String response = getGsonFactory().toJson(responseData);
			// Set the response header status and length
			t.getResponseHeaders().put("Content-Type", Collections.singletonList("application/json"));
			t.sendResponseHeaders(200, response.getBytes().length);
			// Write the response string
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		});
		// Agreement and Protocol
		server.createContext( // hk4e-sdk-os.hoyoverse.com
				"/hk4e_global/mdk/agreement/api/getAgreementInfos", 
				new DispatchHttpJsonHandler("{\"retcode\":0,\"message\":\"OK\",\"data\":{\"marketing_agreements\":[]}}")
		);
		server.createContext( // hk4e-sdk-os.hoyoverse.com
				"/hk4e_global/combo/granter/api/compareProtocolVersion", 
				new DispatchHttpJsonHandler("{\"retcode\":0,\"message\":\"OK\",\"data\":{\"modified\":true,\"protocol\":{\"id\":0,\"app_id\":4,\"language\":\"en\",\"user_proto\":\"\",\"priv_proto\":\"\",\"major\":7,\"minimum\":0,\"create_time\":\"0\",\"teenager_proto\":\"\",\"third_proto\":\"\"}}}")
		);
		// Game data
		server.createContext( // hk4e-api-os.hoyoverse.com
				"/common/hk4e_global/announcement/api/getAlertPic", 
				new DispatchHttpJsonHandler("{\"retcode\":0,\"message\":\"OK\",\"data\":{\"total\":0,\"list\":[]}}")
		);
		server.createContext( // hk4e-api-os.hoyoverse.com
				"/common/hk4e_global/announcement/api/getAlertAnn",
				new DispatchHttpJsonHandler("{\"retcode\":0,\"message\":\"OK\",\"data\":{\"alert\":false,\"alert_id\":0,\"remind\":true}}")
		);
		server.createContext( // hk4e-api-os.hoyoverse.com
				"/common/hk4e_global/announcement/api/getAnnList", 
				new DispatchHttpJsonHandler("{\"retcode\":0,\"message\":\"OK\",\"data\":{\"list\":[],\"total\":0,\"type_list\":[],\"alert\":false,\"alert_id\":0,\"timezone\":0,\"t\":\"" + System.currentTimeMillis() + "\"}}")
		);
		server.createContext( // hk4e-api-os-static.hoyoverse.com
				"/common/hk4e_global/announcement/api/getAnnContent", 
				new DispatchHttpJsonHandler("{\"retcode\":0,\"message\":\"OK\",\"data\":{\"list\":[],\"total\":0}}")
		);
		server.createContext( // hk4e-sdk-os.hoyoverse.com
				"/hk4e_global/mdk/shopwindow/shopwindow/listPriceTier", 
				new DispatchHttpJsonHandler("{\"retcode\":0,\"message\":\"OK\",\"data\":{\"suggest_currency\":\"USD\",\"tiers\":[]}}")
		);
		// Captcha
		server.createContext( // api-account-os.hoyoverse.com
				"/account/risky/api/check", 
				new DispatchHttpJsonHandler("{\"retcode\":0,\"message\":\"OK\",\"data\":{\"id\":\"c8820f246a5241ab9973f71df3ddd791\",\"action\":\"\",\"geetest\":{\"challenge\":\"\",\"gt\":\"\",\"new_captcha\":0,\"success\":1}}}")
		);
		// Config	
		server.createContext( // sdk-os-static.hoyoverse.com
				"/combo/box/api/config/sdk/combo", 
				new DispatchHttpJsonHandler("{\"retcode\":0,\"message\":\"OK\",\"data\":{\"vals\":{\"disable_email_bind_skip\":\"false\",\"email_bind_remind_interval\":\"7\",\"email_bind_remind\":\"true\"}}}")
		);
		server.createContext( // hk4e-sdk-os-static.hoyoverse.com
				"/hk4e_global/combo/granter/api/getConfig", 
				new DispatchHttpJsonHandler("{\"retcode\":0,\"message\":\"OK\",\"data\":{\"protocol\":true,\"qr_enabled\":false,\"log_level\":\"INFO\",\"announce_url\":\"https://webstatic-sea.hoyoverse.com/hk4e/announcement/index.html?sdk_presentation_style=fullscreen\\u0026sdk_screen_transparent=true\\u0026game_biz=hk4e_global\\u0026auth_appid=announcement\\u0026game=hk4e#/\",\"push_alias_type\":2,\"disable_ysdk_guard\":false,\"enable_announce_pic_popup\":true}}")
		);
		server.createContext( // hk4e-sdk-os-static.hoyoverse.com
				"/hk4e_global/mdk/shield/api/loadConfig", 
				new DispatchHttpJsonHandler("{\"retcode\":0,\"message\":\"OK\",\"data\":{\"id\":6,\"game_key\":\"hk4e_global\",\"client\":\"PC\",\"identity\":\"I_IDENTITY\",\"guest\":false,\"ignore_versions\":\"\",\"scene\":\"S_NORMAL\",\"name\":\"原神海外\",\"disable_regist\":false,\"enable_email_captcha\":false,\"thirdparty\":[\"fb\",\"tw\"],\"disable_mmt\":false,\"server_guest\":false,\"thirdparty_ignore\":{\"tw\":\"\",\"fb\":\"\"},\"enable_ps_bind_account\":false,\"thirdparty_login_configs\":{\"tw\":{\"token_type\":\"TK_GAME_TOKEN\",\"game_token_expires_in\":604800},\"fb\":{\"token_type\":\"TK_GAME_TOKEN\",\"game_token_expires_in\":604800}}}}")
		);
		// Test api?
		server.createContext( // abtest-api-data-sg.hoyoverse.com
				"/data_abtest_api/config/experiment/list", 
				new DispatchHttpJsonHandler("{\"retcode\":0,\"success\":true,\"message\":\"\",\"data\":[{\"code\":1000,\"type\":2,\"config_id\":\"14\",\"period_id\":\"6036_99\",\"version\":\"1\",\"configs\":{\"cardType\":\"old\"}}]}")
		);
		// Log Server 
		server.createContext( // log-upload-os.mihoyo.com
				"/log/sdk/upload", 
				new DispatchHttpJsonHandler("{\"code\":0}")
		);
		server.createContext( // log-upload-os.mihoyo.com
				"/sdk/upload", 
				new DispatchHttpJsonHandler("{\"code\":0}")
		);
		// Start server
		server.start();
		Grasscutter.getLogger().info("Dispatch server started on port " + getAddress().getPort());
		
		// Logging servers
		HttpServer overseaLogServer = HttpServer.create(new InetSocketAddress(Grasscutter.getConfig().DispatchServerIp, 8888), 0);
		overseaLogServer.createContext( // overseauspider.yuanshen.com
				"/log", 
				new DispatchHttpJsonHandler("{\"code\":0}")
		);
		overseaLogServer.start();
		Grasscutter.getLogger().info("Log server (overseauspider) started on port " + 8888);
		
		HttpServer uploadLogServer = HttpServer.create(new InetSocketAddress(Grasscutter.getConfig().DispatchServerIp, 80), 0);
		uploadLogServer.createContext( // log-upload-os.mihoyo.com
				"/crash/dataUpload", 
				new DispatchHttpJsonHandler("{\"code\":0}")
		);
		uploadLogServer.createContext("/gacha", t -> {
			//Create a response form the request query parameters
			String response = "<!doctype html><html lang=\"en\"><head><title>Gacha</title></head><body></body></html>";
			//Set the response header status and length
			t.getResponseHeaders().put("Content-Type", Collections.singletonList("text/html; charset=UTF-8"));
			t.sendResponseHeaders(200, response.getBytes().length);
			//Write the response string
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		});
		uploadLogServer.start();
		Grasscutter.getLogger().info("Log server (log-upload-os) started on port " + 80);
	}
	
	private Map<String, String> parseQueryString(String qs) {
	    Map<String, String> result = new HashMap<>();
	    if (qs == null)
	        return result;

	    int last = 0, next, l = qs.length();
	    while (last < l) {
	        next = qs.indexOf('&', last);
	        if (next == -1)
	            next = l;

	        if (next > last) {
	            int eqPos = qs.indexOf('=', last);
	            try {
	                if (eqPos < 0 || eqPos > next)
	                    result.put(URLDecoder.decode(qs.substring(last, next), "utf-8"), "");
	                else
	                    result.put(URLDecoder.decode(qs.substring(last, eqPos), "utf-8"), URLDecoder.decode(qs.substring(eqPos + 1, next), "utf-8"));
	            } catch (UnsupportedEncodingException e) {
	                throw new RuntimeException(e); // will never happen, utf-8 support is mandatory for java
	            }
	        }
	        last = next + 1;
	    }
	    return result;
	}
}
