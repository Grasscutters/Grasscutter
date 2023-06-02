package emu.grasscutter.server.http.handlers;

import static emu.grasscutter.utils.lang.Language.translate;

import com.google.gson.JsonObject;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.http.Router;
import emu.grasscutter.utils.*;
import io.javalin.Javalin;
import io.javalin.http.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import lombok.Getter;

/** Handles all gacha-related HTTP requests. */
public final class GachaHandler implements Router {
    @Getter
    private static final Path gachaMappingsPath = FileUtils.getDataUserPath("gacha/mappings.js");

    @Deprecated(forRemoval = true)
    public static final String gachaMappings = gachaMappingsPath.toString();

    private static void gachaRecords(Context ctx) {
        var sessionKey = ctx.queryParam("s");
        var account = DatabaseHelper.getAccountBySessionKey(sessionKey);
        if (account == null) {
            ctx.status(403).result("Requested account was not found.");
            return;
        }

        // Get page and gacha type.
        int page = 0, gachaType = 0;

        var pageStr = ctx.queryParam("p");
        if (pageStr != null) page = Integer.parseInt(pageStr);

        var gachaTypeStr = ctx.queryParam("gachaType");
        if (gachaTypeStr != null) gachaType = Integer.parseInt(gachaTypeStr);

        // Make request to dispatch server.
        var data = DispatchUtils.fetchGachaRecords(account.getId(), page, gachaType);
        var records = data.get("records").getAsString();
        var maxPage = data.get("maxPage").getAsLong();

        var locale = account.getLocale();
        var template =
                new String(
                                FileUtils.read(FileUtils.getDataPath("gacha/records.html")), StandardCharsets.UTF_8)
                        .replace("'{{REPLACE_RECORDS}}'", Utils.unescapeJson(records))
                        .replace("'{{REPLACE_MAXPAGE}}'", String.valueOf(maxPage))
                        .replace("{{TITLE}}", translate(locale, "gacha.records.title"))
                        .replace("{{DATE}}", translate(locale, "gacha.records.date"))
                        .replace("{{ITEM}}", translate(locale, "gacha.records.item"))
                        .replace("{{LANGUAGE}}", Utils.getLanguageCode(account.getLocale()));
        ctx.contentType(ContentType.TEXT_HTML);
        ctx.result(template);
    }

    private static void gachaDetails(Context ctx) {
        var detailsTemplate = FileUtils.getDataPath("gacha/details.html");
        var sessionKey = ctx.queryParam("s");
        var account = DatabaseHelper.getAccountBySessionKey(sessionKey);
        if (account == null) {
            ctx.status(403).result("Requested account was not found");
            return;
        }

        String template;
        try {
            template = Files.readString(detailsTemplate);
        } catch (IOException e) {
            Grasscutter.getLogger().warn("Failed to read data/gacha/details.html");
            ctx.status(500);
            return;
        }

        // Add translated title etc. to the page.
        var locale = account.getLocale();
        template =
                template
                        .replace("{{TITLE}}", translate(locale, "gacha.details.title"))
                        .replace(
                                "{{AVAILABLE_FIVE_STARS}}", translate(locale, "gacha.details.available_five_stars"))
                        .replace(
                                "{{AVAILABLE_FOUR_STARS}}", translate(locale, "gacha.details.available_four_stars"))
                        .replace(
                                "{{AVAILABLE_THREE_STARS}}",
                                translate(locale, "gacha.details.available_three_stars"))
                        .replace("{{LANGUAGE}}", Utils.getLanguageCode(account.getLocale()));

        // Get the banner info for the banner we want.
        var scheduleIdStr = ctx.queryParam("scheduleId");
        if (scheduleIdStr == null) {
            ctx.status(400).result("Missing scheduleId parameter");
            return;
        }

        var scheduleId = Integer.parseInt(scheduleIdStr);
        var manager = Grasscutter.getGameServer().getGachaSystem();
        var banner = manager.getGachaBanners().get(scheduleId);

        // Add 5-star items.
        var fiveStarItems = new LinkedHashSet<String>();

        Arrays.stream(banner.getRateUpItems5()).forEach(i -> fiveStarItems.add(Integer.toString(i)));
        Arrays.stream(banner.getFallbackItems5Pool1())
                .forEach(i -> fiveStarItems.add(Integer.toString(i)));
        Arrays.stream(banner.getFallbackItems5Pool2())
                .forEach(i -> fiveStarItems.add(Integer.toString(i)));

        template = template.replace("{{FIVE_STARS}}", "[" + String.join(",", fiveStarItems) + "]");

        // Add 4-star items.
        var fourStarItems = new LinkedHashSet<String>();

        Arrays.stream(banner.getRateUpItems4()).forEach(i -> fourStarItems.add(Integer.toString(i)));
        Arrays.stream(banner.getFallbackItems4Pool1())
                .forEach(i -> fourStarItems.add(Integer.toString(i)));
        Arrays.stream(banner.getFallbackItems4Pool2())
                .forEach(i -> fourStarItems.add(Integer.toString(i)));

        template = template.replace("{{FOUR_STARS}}", "[" + String.join(",", fourStarItems) + "]");

        // Add 3-star items.
        var threeStarItems = new LinkedHashSet<String>();
        Arrays.stream(banner.getFallbackItems3()).forEach(i -> threeStarItems.add(Integer.toString(i)));
        template = template.replace("{{THREE_STARS}}", "[" + String.join(",", threeStarItems) + "]");

        // Done.
        ctx.contentType(ContentType.TEXT_HTML);
        ctx.result(template);
    }

    /**
     * Fetches the gacha records for the specified player.
     *
     * @param player The player to fetch the records for.
     * @param response The response to write to.
     * @param page The page to fetch.
     * @param type The gacha type to fetch.
     */
    public static void fetchGachaRecords(Player player, JsonObject response, int page, int type) {
        var playerId = player.getUid();
        var records = DatabaseHelper.getGachaRecords(playerId, page, type).toString();
        var maxPage = DatabaseHelper.getGachaRecordsMaxPage(playerId, page, type);

        // Finish the response.
        response.addProperty("retcode", 0);
        response.addProperty("records", records);
        response.addProperty("maxPage", maxPage);
    }

    @Override
    public void applyRoutes(Javalin javalin) {
        javalin.get("/gacha", GachaHandler::gachaRecords);
        javalin.get("/gacha/details", GachaHandler::gachaDetails);
        javalin.get("/gacha/mappings", ctx -> ctx.result(FileUtils.read(gachaMappingsPath.toString())));
    }
}
