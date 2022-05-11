package emu.grasscutter.server.dispatch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.protobuf.ByteString;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.Grasscutter.ServerDebugMode;
import emu.grasscutter.Grasscutter.ServerRunMode;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.Account;
import emu.grasscutter.net.proto.QueryCurrRegionHttpRspOuterClass.QueryCurrRegionHttpRsp;
import emu.grasscutter.net.proto.QueryRegionListHttpRspOuterClass.QueryRegionListHttpRsp;
import emu.grasscutter.net.proto.RegionInfoOuterClass.RegionInfo;
import emu.grasscutter.net.proto.RegionSimpleInfoOuterClass.RegionSimpleInfo;
import emu.grasscutter.server.dispatch.authentication.AuthenticationHandler;
import emu.grasscutter.server.dispatch.authentication.DefaultAuthenticationHandler;
import emu.grasscutter.server.dispatch.http.GachaRecordHandler;
import emu.grasscutter.server.dispatch.json.*;
import emu.grasscutter.server.dispatch.json.ComboTokenReqJson.LoginTokenData;
import emu.grasscutter.server.event.dispatch.QueryAllRegionsEvent;
import emu.grasscutter.server.event.dispatch.QueryCurrentRegionEvent;
import emu.grasscutter.tools.Tools;
import emu.grasscutter.utils.FileUtils;
import emu.grasscutter.utils.Utils;
import express.Express;
import io.javalin.http.staticfiles.Location;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static emu.grasscutter.utils.Language.translate;
import static emu.grasscutter.Configuration.*;

public final class DispatchServer {
	public static String query_region_list = "";
	public static String query_cur_region = "";

	private final Gson gson;
	private final String defaultServerName = "os_usa";

	public String regionListBase64;
	public Map<String, RegionData> regions;
	private AuthenticationHandler authHandler;
	private Express httpServer;

	public DispatchServer() {
		this.regions = new HashMap<>();
		this.gson = new GsonBuilder().create();

		this.loadQueries();
		this.initRegion();
	}

	public Express getServer() {
		return httpServer;
	}

	public void setHttpServer(Express httpServer) {
		this.httpServer.stop();
		this.httpServer = httpServer;
		this.httpServer.listen(DISPATCH_INFO.bindPort);
	}

	public Gson getGsonFactory() {
		return gson;
	}

	public QueryCurrRegionHttpRsp getCurrRegion() {
		// Needs to be fixed by having the game servers connect to the dispatch server.
		if (SERVER.runMode == ServerRunMode.HYBRID) {
			return regions.get(defaultServerName).parsedRegionQuery;
		}

		Grasscutter.getLogger().warn("[Dispatch] Unsupported run mode for getCurrRegion()");
		return null;
	}

	public void loadQueries() {
		File file;

		file = new File(DATA("query_region_list.txt"));
		if (file.exists()) {
			query_region_list = new String(FileUtils.read(file));
		} else {
			Grasscutter.getLogger().warn("[Dispatch] query_region_list not found! Using default region list.");
		}

		file = new File(DATA("query_cur_region.txt"));
		if (file.exists()) {
			query_cur_region = new String(FileUtils.read(file));
		} else {
			Grasscutter.getLogger().warn("[Dispatch] query_cur_region not found! Using default current region.");
		}
	}

	private void initRegion() {
		try {
			byte[] decoded = Base64.getDecoder().decode(query_region_list);
			QueryRegionListHttpRsp rl = QueryRegionListHttpRsp.parseFrom(decoded);

			byte[] decoded2 = Base64.getDecoder().decode(query_cur_region);
			QueryCurrRegionHttpRsp regionQuery = QueryCurrRegionHttpRsp.parseFrom(decoded2);

			List<RegionSimpleInfo> servers = new ArrayList<>();
			List<String> usedNames = new ArrayList<>(); // List to check for potential naming conflicts.
			if (SERVER.runMode == ServerRunMode.HYBRID) { // Automatically add the game server if in hybrid mode.
				RegionSimpleInfo server = RegionSimpleInfo.newBuilder()
						.setName("os_usa")
						.setTitle(DISPATCH_INFO.defaultName)
						.setType("DEV_PUBLIC")
						.setDispatchUrl(
								"http" + (DISPATCH_ENCRYPTION.useEncryption ? "s" : "") + "://"
										+ lr(DISPATCH_INFO.accessAddress, DISPATCH_INFO.bindAddress) + ":"
										+ lr(DISPATCH_INFO.accessPort, DISPATCH_INFO.bindPort)
										+ "/query_cur_region/" + defaultServerName)
						.build();
				usedNames.add(defaultServerName);
				servers.add(server);

				RegionInfo serverRegion = regionQuery.getRegionInfo().toBuilder()
						.setGateserverIp(lr(GAME_INFO.accessAddress, GAME_INFO.bindAddress))
						.setGateserverPort(lr(GAME_INFO.accessPort, GAME_INFO.bindPort))
						.setSecretKey(ByteString.copyFrom(FileUtils.read(KEYS_FOLDER + "/dispatchSeed.bin")))
						.build();

				QueryCurrRegionHttpRsp parsedRegionQuery = regionQuery.toBuilder().setRegionInfo(serverRegion).build();
				regions.put(defaultServerName, new RegionData(parsedRegionQuery,
						Base64.getEncoder().encodeToString(parsedRegionQuery.toByteString().toByteArray())));

			} else if (DISPATCH_INFO.regions.length == 0) {
				Grasscutter.getLogger().error("[Dispatch] There are no game servers available. Exiting due to unplayable state.");
				System.exit(1);
			}

			for (var regionInfo : DISPATCH_INFO.regions) {
				if (usedNames.contains(regionInfo.Name)) {
					Grasscutter.getLogger().error("Region name already in use.");
					continue;
				}
				RegionSimpleInfo server = RegionSimpleInfo.newBuilder()
						.setName(regionInfo.Name)
						.setTitle(regionInfo.Title)
						.setType("DEV_PUBLIC")
						.setDispatchUrl(
								"http" + (DISPATCH_ENCRYPTION.useEncryption ? "s" : "") + "://"
										+ lr(DISPATCH_INFO.accessAddress, DISPATCH_INFO.bindAddress) + ":" 
										+ lr(DISPATCH_INFO.accessPort, DISPATCH_INFO.bindPort) 
										+ "/query_cur_region/" + regionInfo.Name)
						.build();
				usedNames.add(regionInfo.Name);
				servers.add(server);

				RegionInfo serverRegion = regionQuery.getRegionInfo().toBuilder()
						.setGateserverIp(regionInfo.Ip)
						.setGateserverPort(regionInfo.Port)
						.setSecretKey(ByteString
								.copyFrom(FileUtils.read(KEYS_FOLDER + "/dispatchSeed.bin")))
						.build();

				QueryCurrRegionHttpRsp parsedRegionQuery = regionQuery.toBuilder().setRegionInfo(serverRegion).build();
				regions.put(regionInfo.Name, new RegionData(parsedRegionQuery,
						Base64.getEncoder().encodeToString(parsedRegionQuery.toByteString().toByteArray())));
			}

			QueryRegionListHttpRsp regionList = QueryRegionListHttpRsp.newBuilder()
					.addAllRegionList(servers)
					.setClientSecretKey(rl.getClientSecretKey())
					.setClientCustomConfigEncrypted(rl.getClientCustomConfigEncrypted())
					.setEnableLoginPc(true)
					.build();

			this.regionListBase64 = Base64.getEncoder().encodeToString(regionList.toByteString().toByteArray());
		} catch (Exception exception) {
			Grasscutter.getLogger().error("[Dispatch] Error while initializing region info!", exception);
		}
	}

	public void start() throws Exception {
		httpServer = new Express(config -> {
			config.server(() -> {
				Server server = new Server();
				ServerConnector serverConnector;

				if(DISPATCH_ENCRYPTION.useEncryption) {
					SslContextFactory.Server sslContextFactory = new SslContextFactory.Server();
					File keystoreFile = new File(DISPATCH_ENCRYPTION.keystore);

					if(keystoreFile.exists()) {
						try {
							sslContextFactory.setKeyStorePath(keystoreFile.getPath());
							sslContextFactory.setKeyStorePassword(DISPATCH_ENCRYPTION.keystorePassword);
						} catch (Exception e) {
							e.printStackTrace();
							Grasscutter.getLogger().warn(translate("messages.dispatch.keystore.password_error"));

							try {
								sslContextFactory.setKeyStorePath(keystoreFile.getPath());
								sslContextFactory.setKeyStorePassword("123456");
								Grasscutter.getLogger().warn(translate("messages.dispatch.keystore.default_password"));
							} catch (Exception e2) {
								Grasscutter.getLogger().warn(translate("messages.dispatch.keystore.general_error"));
								e2.printStackTrace();
							}
						}

						serverConnector = new ServerConnector(server, sslContextFactory);
					} else {
						Grasscutter.getLogger().warn(translate("messages.dispatch.keystore.no_keystore_error"));
						DISPATCH_ENCRYPTION.useEncryption = false;

						serverConnector = new ServerConnector(server);
					}
				} else {
					serverConnector = new ServerConnector(server);
				}

				serverConnector.setPort(DISPATCH_INFO.bindPort);
				server.setConnectors(new Connector[]{serverConnector});
				return server;
			});

			config.enforceSsl = DISPATCH_ENCRYPTION.useEncryption;
			if(SERVER.debugLevel == ServerDebugMode.ALL) {
				config.enableDevLogging();
			}
			
			if (DISPATCH_POLICIES.cors.enabled) {
				var corsPolicy = DISPATCH_POLICIES.cors;
				if (corsPolicy.allowedOrigins.length > 0) 
					config.enableCorsForOrigin(corsPolicy.allowedOrigins);
				else config.enableCorsForAllOrigins();
			}
		});
		httpServer.get("/", (req, res) -> res.send("<!doctype html><html><head><meta charset=\"utf8\"></head><body>" + translate("messages.status.welcome") + "</body></html>"));

		httpServer.raw().error(404, ctx -> {
			if(SERVER.debugLevel == ServerDebugMode.MISSING) {
				Grasscutter.getLogger().info(translate("messages.dispatch.unhandled_request_error", ctx.method(), ctx.url()));
			}
			ctx.contentType("text/html");
			ctx.result("<!doctype html><html><head><meta charset=\"utf8\"></head><body><img src=\"https://http.cat/404\" /></body></html>"); // I'm like 70% sure this won't break anything.
		});

		// Authentication Handler
		// These routes are so that authentication routes are always the same no matter what auth system is used.
		httpServer.get("/authentication/type", (req, res) -> {
			res.send(this.getAuthHandler().getClass().getName());
		});

		httpServer.post("/authentication/login", (req, res) -> this.getAuthHandler().handleLogin(req, res));
		httpServer.post("/authentication/register", (req, res) -> this.getAuthHandler().handleRegister(req, res));
		httpServer.post("/authentication/change_password", (req, res) -> this.getAuthHandler().handleChangePassword(req, res));

		// Dispatch
		httpServer.get("/query_region_list", (req, res) -> {
			// Log
			Grasscutter.getLogger().info(String.format("[Dispatch] Client %s request: query_region_list", req.ip()));

			// Invoke event.
			QueryAllRegionsEvent event = new QueryAllRegionsEvent(regionListBase64); event.call();
			// Respond with event result.
			res.send(event.getRegionList());
		});

		httpServer.get("/query_cur_region/:id", (req, res) -> {
			String regionName = req.params("id");
			// Log
			Grasscutter.getLogger().info(
					String.format("Client %s request: query_cur_region/%s", req.ip(), regionName));
			// Create a response form the request query parameters
			String response = "CAESGE5vdCBGb3VuZCB2ZXJzaW9uIGNvbmZpZw==";
			if (req.query().values().size() > 0) {
				response = regions.get(regionName).Base64;
			}

			// Invoke event.
			QueryCurrentRegionEvent event = new QueryCurrentRegionEvent(response); event.call();
			// Respond with event result.
			res.send(event.getRegionInfo());
		});

		// Login

		httpServer.post("/hk4e_global/mdk/shield/api/login", (req, res) -> {
			// Get post data
			LoginAccountRequestJson requestData = null;
			try {
				String body = req.ctx().body();
				requestData = getGsonFactory().fromJson(body, LoginAccountRequestJson.class);
			} catch (Exception ignored) { }

			// Create response json
			if (requestData == null) {
				return;
			}
			Grasscutter.getLogger().info(translate("messages.dispatch.account.login_attempt", req.ip()));

			res.send(this.getAuthHandler().handleGameLogin(req, requestData));
		});

		// Login via token
		httpServer.post("/hk4e_global/mdk/shield/api/verify", (req, res) -> {
			// Get post data
			LoginTokenRequestJson requestData = null;
			try {
				String body = req.ctx().body();
				requestData = getGsonFactory().fromJson(body, LoginTokenRequestJson.class);
			} catch (Exception ignored) {
			}

			// Create response json
			if (requestData == null) {
				return;
			}
			LoginResultJson responseData = new LoginResultJson();
			Grasscutter.getLogger().info(translate("messages.dispatch.account.login_token_attempt", req.ip()));

			// Login
			Account account = DatabaseHelper.getAccountById(requestData.uid);

			// Test
			if (account == null || !account.getSessionKey().equals(requestData.token)) {
				responseData.retcode = -111;
				responseData.message = translate("messages.dispatch.account.account_cache_error");

				Grasscutter.getLogger().info(translate("messages.dispatch.account.login_token_error", req.ip()));
			} else {
				responseData.message = "OK";
				responseData.data.account.uid = requestData.uid;
				responseData.data.account.token = requestData.token;
				responseData.data.account.email = account.getEmail();

				Grasscutter.getLogger().info(translate("messages.dispatch.account.login_token_success", req.ip(), requestData.uid));
			}

			res.send(responseData);
		});

		// Exchange for combo token
		httpServer.post("/hk4e_global/combo/granter/login/v2/login", (req, res) -> {
			// Get post data
			ComboTokenReqJson requestData = null;
			try {
				String body = req.ctx().body();
				requestData = getGsonFactory().fromJson(body, ComboTokenReqJson.class);
			} catch (Exception ignored) {
			}

			// Create response json
			if (requestData == null || requestData.data == null) {
				return;
			}
			LoginTokenData loginData = getGsonFactory().fromJson(requestData.data, LoginTokenData.class); // Get login
			// data
			ComboTokenResJson responseData = new ComboTokenResJson();

			// Login
			Account account = DatabaseHelper.getAccountById(loginData.uid);

			// Test
			if (account == null || !account.getSessionKey().equals(loginData.token)) {
				responseData.retcode = -201;
				responseData.message = translate("messages.dispatch.account.session_key_error");

				Grasscutter.getLogger().info(translate("messages.dispatch.account.combo_token_error", req.ip()));
			} else {
				responseData.message = "OK";
				responseData.data.open_id = loginData.uid;
				responseData.data.combo_id = "157795300";
				responseData.data.combo_token = account.generateLoginToken();

				Grasscutter.getLogger().info(translate("messages.dispatch.account.combo_token_success", req.ip()));
			}

			res.send(responseData);
		});

		// TODO: There are some missing route request types here (You can tell if they are missing if they are .all and not anything else)
		//  When http requests for theses routes are found please remove it from the list in DispatchHttpJsonHandler and update the route request types here

		// Agreement and Protocol
		// hk4e-sdk-os.hoyoverse.com
		httpServer.get("/hk4e_global/mdk/agreement/api/getAgreementInfos", new DispatchHttpJsonHandler("{\"retcode\":0,\"message\":\"OK\",\"data\":{\"marketing_agreements\":[]}}"));
		// hk4e-sdk-os.hoyoverse.com
		// this could be either GET or POST based on the observation of different clients
		httpServer.all("/hk4e_global/combo/granter/api/compareProtocolVersion", new DispatchHttpJsonHandler("{\"retcode\":0,\"message\":\"OK\",\"data\":{\"modified\":true,\"protocol\":{\"id\":0,\"app_id\":4,\"language\":\"en\",\"user_proto\":\"\",\"priv_proto\":\"\",\"major\":7,\"minimum\":0,\"create_time\":\"0\",\"teenager_proto\":\"\",\"third_proto\":\"\"}}}"));

		// Game data
		// hk4e-api-os.hoyoverse.com
		httpServer.all("/common/hk4e_global/announcement/api/getAlertPic", new DispatchHttpJsonHandler("{\"retcode\":0,\"message\":\"OK\",\"data\":{\"total\":0,\"list\":[]}}"));
		// hk4e-api-os.hoyoverse.com
		httpServer.all("/common/hk4e_global/announcement/api/getAlertAnn", new DispatchHttpJsonHandler("{\"retcode\":0,\"message\":\"OK\",\"data\":{\"alert\":false,\"alert_id\":0,\"remind\":true}}"));
		// hk4e-api-os.hoyoverse.com
		httpServer.all("/common/hk4e_global/announcement/api/getAnnList", new AnnouncementHandler());
		// hk4e-api-os-static.hoyoverse.com
		httpServer.all("/common/hk4e_global/announcement/api/getAnnContent", new AnnouncementHandler());
		// hk4e-sdk-os.hoyoverse.com
		httpServer.all("/hk4e_global/mdk/shopwindow/shopwindow/listPriceTier", new DispatchHttpJsonHandler("{\"retcode\":0,\"message\":\"OK\",\"data\":{\"suggest_currency\":\"USD\",\"tiers\":[]}}"));

		// Captcha
		// api-account-os.hoyoverse.com
		httpServer.post("/account/risky/api/check", new DispatchHttpJsonHandler("{\"retcode\":0,\"message\":\"OK\",\"data\":{\"id\":\"none\",\"action\":\"ACTION_NONE\",\"geetest\":null}}"));

		// Config
		// sdk-os-static.hoyoverse.com
		httpServer.get("/combo/box/api/config/sdk/combo", new DispatchHttpJsonHandler("{\"retcode\":0,\"message\":\"OK\",\"data\":{\"vals\":{\"disable_email_bind_skip\":\"false\",\"email_bind_remind_interval\":\"7\",\"email_bind_remind\":\"true\"}}}"));
		// hk4e-sdk-os-static.hoyoverse.com
		httpServer.get("/hk4e_global/combo/granter/api/getConfig", new DispatchHttpJsonHandler("{\"retcode\":0,\"message\":\"OK\",\"data\":{\"protocol\":true,\"qr_enabled\":false,\"log_level\":\"INFO\",\"announce_url\":\"https://webstatic-sea.hoyoverse.com/hk4e/announcement/index.html?sdk_presentation_style=fullscreen\\u0026sdk_screen_transparent=true\\u0026game_biz=hk4e_global\\u0026auth_appid=announcement\\u0026game=hk4e#/\",\"push_alias_type\":2,\"disable_ysdk_guard\":false,\"enable_announce_pic_popup\":true}}"));
		// hk4e-sdk-os-static.hoyoverse.com
		httpServer.get("/hk4e_global/mdk/shield/api/loadConfig", new DispatchHttpJsonHandler("{\"retcode\":0,\"message\":\"OK\",\"data\":{\"id\":6,\"game_key\":\"hk4e_global\",\"client\":\"PC\",\"identity\":\"I_IDENTITY\",\"guest\":false,\"ignore_versions\":\"\",\"scene\":\"S_NORMAL\",\"name\":\"原神海外\",\"disable_regist\":false,\"enable_email_captcha\":false,\"thirdparty\":[\"fb\",\"tw\"],\"disable_mmt\":false,\"server_guest\":false,\"thirdparty_ignore\":{\"tw\":\"\",\"fb\":\"\"},\"enable_ps_bind_account\":false,\"thirdparty_login_configs\":{\"tw\":{\"token_type\":\"TK_GAME_TOKEN\",\"game_token_expires_in\":604800},\"fb\":{\"token_type\":\"TK_GAME_TOKEN\",\"game_token_expires_in\":604800}}}}"));
		// Test api?
		// abtest-api-data-sg.hoyoverse.com
		httpServer.post("/data_abtest_api/config/experiment/list", new DispatchHttpJsonHandler("{\"retcode\":0,\"success\":true,\"message\":\"\",\"data\":[{\"code\":1000,\"type\":2,\"config_id\":\"14\",\"period_id\":\"6036_99\",\"version\":\"1\",\"configs\":{\"cardType\":\"old\"}}]}"));

		// log-upload-os.mihoyo.com
		httpServer.all("/log/sdk/upload", new DispatchHttpJsonHandler("{\"code\":0}"));
		httpServer.all("/sdk/upload", new DispatchHttpJsonHandler("{\"code\":0}"));
		httpServer.post("/sdk/dataUpload", new DispatchHttpJsonHandler("{\"code\":0}"));
		// /perf/config/verify?device_id=xxx&platform=x&name=xxx
		httpServer.all("/perf/config/verify", new DispatchHttpJsonHandler("{\"code\":0}"));

		// Logging servers
		// overseauspider.yuanshen.com
		httpServer.all("/log", new ClientLogHandler());
		// log-upload-os.mihoyo.com
		httpServer.all("/crash/dataUpload", new ClientLogHandler());

		// webstatic-sea.hoyoverse.com
		httpServer.get("/admin/mi18n/plat_oversea/m202003048/m202003048-version.json", new DispatchHttpJsonHandler("{\"version\":51}"));

		// gacha record.
		String gachaMappingsPath = Utils.toFilePath(DATA("/gacha_mappings.js"));
		// TODO: Only serve the html page and have a subsequent request to fetch the gacha data.
		httpServer.get("/gacha", new GachaRecordHandler());
		if(!(new File(gachaMappingsPath).exists())) {
			Tools.createGachaMapping(gachaMappingsPath);
		}

		httpServer.raw().config.addSinglePageRoot("/gacha/mappings", gachaMappingsPath, Location.EXTERNAL);

		// static file support for plugins
		httpServer.raw().config.precompressStaticFiles = false; // If this isn't set to false, files such as images may appear corrupted when serving static files

		httpServer.listen(DISPATCH_INFO.bindPort);
		Grasscutter.getLogger().info(translate("messages.dispatch.port_bind", Integer.toString(httpServer.raw().port())));
	}

	private Map<String, String> parseQueryString(String qs) {
		Map<String, String> result = new HashMap<>();
		if (qs == null) {
			return result;
		}

		int last = 0, next, l = qs.length();
		while (last < l) {
			next = qs.indexOf('&', last);
			if (next == -1) {
				next = l;
			}

			if (next > last) {
				int eqPos = qs.indexOf('=', last);
				if (eqPos < 0 || eqPos > next) {
					result.put(URLDecoder.decode(qs.substring(last, next), StandardCharsets.UTF_8), "");
				} else {
					result.put(URLDecoder.decode(qs.substring(last, eqPos), StandardCharsets.UTF_8),
							URLDecoder.decode(qs.substring(eqPos + 1, next), StandardCharsets.UTF_8));
				}
			}
			last = next + 1;
		}
		return result;
	}

	public AuthenticationHandler getAuthHandler() {
		if(authHandler == null) {
			return new DefaultAuthenticationHandler();
		}
		return authHandler;
	}

	public boolean registerAuthHandler(AuthenticationHandler authHandler) {
		if(this.authHandler != null) {
			Grasscutter.getLogger().error(String.format("[Dispatch] Unable to register '%s' authentication handler. \n" +
					"The '%s' authentication handler has already been registered", authHandler.getClass().getName(), this.authHandler.getClass().getName()));
			return false;
		}
		this.authHandler = authHandler;
		return true;
	}

	public void resetAuthHandler() {
		this.authHandler = null;
	}

	public static class RegionData {
		QueryCurrRegionHttpRsp parsedRegionQuery;
		String Base64;

		public RegionData(QueryCurrRegionHttpRsp prq, String b64) {
			this.parsedRegionQuery = prq;
			this.Base64 = b64;
		}

		public QueryCurrRegionHttpRsp getParsedRegionQuery() {
			return parsedRegionQuery;
		}

		public String getBase64() {
			return Base64;
		}
	}
}
