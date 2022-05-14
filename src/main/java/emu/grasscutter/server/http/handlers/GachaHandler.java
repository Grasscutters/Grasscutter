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
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import static emu.grasscutter.Configuration.DATA;
import static emu.grasscutter.utils.Language.translate;

/**
 * Handles all gacha-related HTTP requests.
 */
public final class GachaHandler implements Router {
    private final String gachaMappings;
    
    private static String recordsTemplate = "";
    private static String detailsTemplate = "";
    
    public GachaHandler() {
        this.gachaMappings = Utils.toFilePath(DATA("/gacha_mappings.js"));
        if(!(new File(this.gachaMappings).exists())) {
            try {
                Tools.createGachaMapping(this.gachaMappings);
            } catch (Exception exception) {
                Grasscutter.getLogger().warn("Failed to create gacha mappings.", exception);
            }
        }
        
        var templateFile = new File(DATA("/gacha_records.html"));
        recordsTemplate = templateFile.exists() ? new String(FileUtils.read(templateFile)) : "{{REPLACE_RECORD}}";

        templateFile = new File(Utils.toFilePath(DATA("/gacha_details.html")));
        detailsTemplate = templateFile.exists() ? new String(FileUtils.read(templateFile)) : null;
    }
    
    @Override public void applyRoutes(Express express, Javalin handle) {
        express.get("/gacha", GachaHandler::gachaRecords);
        express.get("/gacha/details", GachaHandler::gachaDetails);
        
        express.useStaticFallback("/gacha/mappings", this.gachaMappings, Location.EXTERNAL);
    }
    
    private static void gachaRecords(Request request, Response response) {
        var sessionKey = request.query("s");
        
        int page = 0, gachaType = 0;
        if(request.query("p") != null)
            page = Integer.parseInt(request.query("p"));
        if(request.query("gachaType") != null)
            gachaType = Integer.parseInt(request.query("gachaType"));
        
        // Get account from session key.
        var account = DatabaseHelper.getAccountBySessionKey(sessionKey);
        
        if(account == null) // Send response.
            response.status(404).send("Unable to find account.");
        else {
            String records = DatabaseHelper.getGachaRecords(account.getPlayerUid(), gachaType, page).toString();
            long maxPage = DatabaseHelper.getGachaRecordsMaxPage(account.getPlayerUid(), page, gachaType);
            
            response.send(recordsTemplate
                    .replace("{{REPLACE_RECORD}}", records)
                    .replace("{{REPLACE_MAXPAGE}}", String.valueOf(maxPage)));
        }
    }
    
    private static void gachaDetails(Request request, Response response) {
        String template = detailsTemplate;

        // Get player info (for langauge).
        String sessionKey = request.query("s");
        Account account = DatabaseHelper.getAccountBySessionKey(sessionKey);
        Player player = Grasscutter.getGameServer().getPlayerByUid(account.getPlayerUid());

        // If the template was not loaded, return an error.
        if (detailsTemplate == null) {
            response.send(translate(player, "gacha.details.template_missing"));
            return;
        }

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
