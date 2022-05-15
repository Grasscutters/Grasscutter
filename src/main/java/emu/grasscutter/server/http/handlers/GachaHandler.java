package emu.grasscutter.server.http.handlers;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.gacha.GachaBanner;
import emu.grasscutter.game.gacha.GachaManager;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.http.Router;
import emu.grasscutter.tools.Tools;
import emu.grasscutter.utils.FileUtils;
import emu.grasscutter.utils.Utils;
import express.Express;
import express.http.Request;
import express.http.Response;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import static emu.grasscutter.Configuration.DATA;
import static emu.grasscutter.utils.Language.translate;

/**
 * Handles all gacha-related HTTP requests.
 */
public final class GachaHandler implements Router {
    public static final String gachaMappings = DATA(Utils.toFilePath("gacha/mappings.js"));
    
    @Override public void applyRoutes(Express express, Javalin handle) {
        express.get("/gacha", GachaHandler::gachaRecords);
        express.get("/gacha/details", GachaHandler::gachaDetails);
        
        express.useStaticFallback("/gacha/mappings", this.gachaMappings, Location.EXTERNAL);
    }
    
    private static void gachaRecords(Request request, Response response) {
        File recordsTemplate = new File(Utils.toFilePath(DATA("gacha/records.html")));
        if (!recordsTemplate.exists()) {
            Grasscutter.getLogger().warn("File does not exist: " + recordsTemplate);
            response.status(500);
            return;
        }

        String sessionKey = request.query("s");
        Account account = DatabaseHelper.getAccountBySessionKey(sessionKey);
        if(account == null) {
            response.status(403).send("Requested account was not found");
            return;
        }
        Player player = Grasscutter.getGameServer().getPlayerByUid(account.getPlayerUid());
        if (player == null) {
            response.status(403).send("No player associated with requested account");
            return;
        }

        int page = 0, gachaType = 0;
        if(request.query("p") != null)
            page = Integer.parseInt(request.query("p"));
        if(request.query("gachaType") != null)
            gachaType = Integer.parseInt(request.query("gachaType"));

        String records = DatabaseHelper.getGachaRecords(player.getUid(), page, gachaType).toString();
        long maxPage = DatabaseHelper.getGachaRecordsMaxPage(player.getUid(), page, gachaType);

        String template = new String(FileUtils.read(recordsTemplate), StandardCharsets.UTF_8)
            .replace("{{REPLACE_RECORDS}}", records)
            .replace("{{REPLACE_MAXPAGE}}", String.valueOf(maxPage))
            .replace("{{LANGUAGE}}", Utils.getLanguageCode(account.getLocale()));
        response.send(template);
    }
    
    private static void gachaDetails(Request request, Response response) {
        File detailsTemplate = new File(Utils.toFilePath(DATA("gacha/details.html")));
        if (!detailsTemplate.exists()) {
            Grasscutter.getLogger().warn("File does not exist: " + detailsTemplate);
            response.status(500);
            return;
        }

        String sessionKey = request.query("s");
        Account account = DatabaseHelper.getAccountBySessionKey(sessionKey);
        if(account == null) {
            response.status(403).send("Requested account was not found");
            return;
        }
        Player player = Grasscutter.getGameServer().getPlayerByUid(account.getPlayerUid());
        if (player == null) {
            response.status(403).send("No player associated with requested account");
            return;
        }

        String template = new String(FileUtils.read(detailsTemplate), StandardCharsets.UTF_8);

        // Add translated title etc. to the page.
        template = template.replace("{{TITLE}}", translate(player, "gacha.details.title"))
                .replace("{{AVAILABLE_FIVE_STARS}}", translate(player, "gacha.details.available_five_stars"))
                .replace("{{AVAILABLE_FOUR_STARS}}", translate(player, "gacha.details.available_four_stars"))
                .replace("{{AVAILABLE_THREE_STARS}}", translate(player, "gacha.details.available_three_stars"))
                .replace("{{LANGUAGE}}", Utils.getLanguageCode(account.getLocale()));

        // Get the banner info for the banner we want.
        int gachaType = Integer.parseInt(request.query("gachaType"));
        GachaManager manager = Grasscutter.getGameServer().getGachaManager();
        GachaBanner banner = manager.getGachaBanners().get(gachaType);

        // Add 5-star items.
        Set<String> fiveStarItems = new LinkedHashSet<>();

        Arrays.stream(banner.getRateUpItems5()).forEach(i -> fiveStarItems.add(Integer.toString(i)));
        Arrays.stream(banner.getFallbackItems5Pool1()).forEach(i -> fiveStarItems.add(Integer.toString(i)));
        Arrays.stream(banner.getFallbackItems5Pool2()).forEach(i -> fiveStarItems.add(Integer.toString(i)));

        template = template.replace("{{FIVE_STARS}}", "[" + String.join(",", fiveStarItems) + "]");

        // Add 4-star items.
        Set<String> fourStarItems = new LinkedHashSet<>();

        Arrays.stream(banner.getRateUpItems4()).forEach(i -> fourStarItems.add(Integer.toString(i)));
        Arrays.stream(banner.getFallbackItems4Pool1()).forEach(i -> fourStarItems.add(Integer.toString(i)));
        Arrays.stream(banner.getFallbackItems4Pool2()).forEach(i -> fourStarItems.add(Integer.toString(i)));

        template = template.replace("{{FOUR_STARS}}", "[" + String.join(",", fourStarItems) + "]");

        // Add 3-star items.
        Set<String> threeStarItems = new LinkedHashSet<>();
        Arrays.stream(banner.getFallbackItems3()).forEach(i -> threeStarItems.add(Integer.toString(i)));
        template = template.replace("{{THREE_STARS}}", "[" + String.join(",", threeStarItems) + "]");

        // Done.
        response.send(template);
    }
}
