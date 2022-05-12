package emu.grasscutter.server.dispatch.http;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.gacha.GachaBanner;
import emu.grasscutter.game.gacha.GachaManager;
import emu.grasscutter.game.gacha.GachaBanner.BannerType;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.utils.FileUtils;
import emu.grasscutter.utils.Utils;
import express.http.HttpContextHandler;
import express.http.Request;
import express.http.Response;

import static emu.grasscutter.utils.Language.translate;

import static emu.grasscutter.Configuration.*;

public final class GachaDetailsHandler implements HttpContextHandler {
	private final String render_template;

	public GachaDetailsHandler() {
		File template = new File(Utils.toFilePath(DATA("/gacha_details.html")));
		this.render_template = template.exists() ? new String(FileUtils.read(template)) : null;
	}

	@Override
	public void handle(Request req, Response res) throws IOException {
		String response = this.render_template;

		// Get player info (for langauge).
		String sessionKey = req.query("s");
		Account account = DatabaseHelper.getAccountBySessionKey(sessionKey);
		Player player = Grasscutter.getGameServer().getPlayerByUid(account.getPlayerUid());

		// If the template was not loaded, return an error.
		if (this.render_template == null) {
			res.send(translate(player, "gacha.details.template_missing"));
			return;
		}

		// Add translated title etc. to the page.
		response = response.replace("{{TITLE}}", translate(player, "gacha.details.title"));
		response = response.replace("{{AVAILABLE_FIVE_STARS}}", translate(player, "gacha.details.available_five_stars"));
		response = response.replace("{{AVAILABLE_FOUR_STARS}}", translate(player, "gacha.details.available_four_stars"));
		response = response.replace("{{AVAILABLE_THREE_STARS}}", translate(player, "gacha.details.available_three_stars"));

		response = response.replace("{{LANGUAGE}}", Utils.getLanguageCode(account.getLocale()));
		
		// Get the banner info for the banner we want.
		int gachaType = Integer.parseInt(req.query("gachaType"));
		GachaManager manager = Grasscutter.getGameServer().getGachaManager();
	 	GachaBanner banner = manager.getGachaBanners().get(gachaType);

		// Add 5-star items.
		Set<String> fiveStarItems = new LinkedHashSet<>();

		Arrays.stream(banner.getRateUpItems5()).forEach(i -> fiveStarItems.add(Integer.toString(i)));
		Arrays.stream(banner.getFallbackItems5Pool1()).forEach(i -> fiveStarItems.add(Integer.toString(i)));
		Arrays.stream(banner.getFallbackItems5Pool2()).forEach(i -> fiveStarItems.add(Integer.toString(i)));

		response = response.replace("{{FIVE_STARS}}", "[" + String.join(",", fiveStarItems) + "]");
		
		// Add 4-star items.
		Set<String> fourStarItems = new LinkedHashSet<>();

		Arrays.stream(banner.getRateUpItems4()).forEach(i -> fourStarItems.add(Integer.toString(i)));
		Arrays.stream(banner.getFallbackItems4Pool1()).forEach(i -> fourStarItems.add(Integer.toString(i)));
		Arrays.stream(banner.getFallbackItems4Pool2()).forEach(i -> fourStarItems.add(Integer.toString(i)));

		response = response.replace("{{FOUR_STARS}}", "[" + String.join(",", fourStarItems) + "]");

		// Add 3-star items.
		Set<String> threeStarItems = new LinkedHashSet<>();
		Arrays.stream(banner.getFallbackItems3()).forEach(i -> threeStarItems.add(Integer.toString(i)));
		response = response.replace("{{THREE_STARS}}", "[" + String.join(",", threeStarItems) + "]");

		// Done.
		res.send(response);
	}
}
