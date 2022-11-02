package emu.grasscutter.server.http.handlers;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.gacha.GachaBanner;
import emu.grasscutter.game.gacha.GachaSystem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.http.Router;
import emu.grasscutter.utils.FileUtils;
import emu.grasscutter.utils.Utils;
import io.javalin.Javalin;
import io.javalin.http.ContentType;
import io.javalin.http.Context;
import io.javalin.http.staticfiles.Location;
import lombok.Getter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import static emu.grasscutter.utils.Language.translate;

/**
 * Handles all gacha-related HTTP requests.
 */
public final class GachaHandler implements Router {
    @Getter private static final Path gachaMappingsPath = FileUtils.getDataUserPath("gacha/mappings.js");
    @Deprecated(forRemoval = true)
    public static final String gachaMappings = gachaMappingsPath.toString();

    @Override public void applyRoutes(Javalin javalin) {
        javalin.get("/gacha", GachaHandler::gachaRecords);
        javalin.get("/gacha/details", GachaHandler::gachaDetails);

        javalin._conf.addSinglePageRoot("/gacha/mappings", gachaMappingsPath.toString(), Location.EXTERNAL);  // TODO: This ***must*** be changed to take the Path not a String. This might involve upgrading Javalin.
    }

    private static void gachaRecords(Context ctx) {
        String sessionKey = ctx.queryParam("s");
        Account account = DatabaseHelper.getAccountBySessionKey(sessionKey);
        if (account == null) {
            ctx.status(403).result("Requested account was not found");
            return;
        }
        Player player = Grasscutter.getGameServer().getPlayerByAccountId(account.getId());
        if (player == null) {
            ctx.status(403).result("No player associated with requested account");
            return;
        }

        int page = 0, gachaType = 0;
        if (ctx.queryParam("p") != null)
            page = Integer.parseInt(ctx.queryParam("p"));
        if (ctx.queryParam("gachaType") != null)
            gachaType = Integer.parseInt(ctx.queryParam("gachaType"));

        String records = DatabaseHelper.getGachaRecords(player.getUid(), page, gachaType).toString();
        long maxPage = DatabaseHelper.getGachaRecordsMaxPage(player.getUid(), page, gachaType);

        String template = new String(FileUtils.read(FileUtils.getDataPath("gacha/records.html")), StandardCharsets.UTF_8)
            .replace("{{REPLACE_RECORDS}}", records)
            .replace("{{REPLACE_MAXPAGE}}", String.valueOf(maxPage))
            .replace("{{TITLE}}", translate(player, "gacha.records.title"))
            .replace("{{DATE}}", translate(player, "gacha.records.date"))
            .replace("{{ITEM}}", translate(player, "gacha.records.item"))
            .replace("{{LANGUAGE}}", Utils.getLanguageCode(account.getLocale()));
        ctx.contentType(ContentType.TEXT_HTML);
        ctx.result(template);
    }

    private static void gachaDetails(Context ctx) {
        Path detailsTemplate = FileUtils.getDataPath("gacha/details.html");
        String sessionKey = ctx.queryParam("s");
        Account account = DatabaseHelper.getAccountBySessionKey(sessionKey);
        if (account == null) {
            ctx.status(403).result("Requested account was not found");
            return;
        }
        Player player = Grasscutter.getGameServer().getPlayerByAccountId(account.getId());
        if (player == null) {
            ctx.status(403).result("No player associated with requested account");
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
        template = template.replace("{{TITLE}}", translate(player, "gacha.details.title"))
                .replace("{{AVAILABLE_FIVE_STARS}}", translate(player, "gacha.details.available_five_stars"))
                .replace("{{AVAILABLE_FOUR_STARS}}", translate(player, "gacha.details.available_four_stars"))
                .replace("{{AVAILABLE_THREE_STARS}}", translate(player, "gacha.details.available_three_stars"))
                .replace("{{LANGUAGE}}", Utils.getLanguageCode(account.getLocale()));

        // Get the banner info for the banner we want.
        int scheduleId = Integer.parseInt(ctx.queryParam("scheduleId"));
        GachaSystem manager = Grasscutter.getGameServer().getGachaSystem();
        GachaBanner banner = manager.getGachaBanners().get(scheduleId);

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
        ctx.contentType(ContentType.TEXT_HTML);
        ctx.result(template);
    }
}
