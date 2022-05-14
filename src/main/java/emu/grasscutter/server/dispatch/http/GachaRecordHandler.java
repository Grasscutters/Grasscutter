package emu.grasscutter.server.dispatch.http;

import java.io.File;
import java.io.IOException;

import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.Account;
import emu.grasscutter.utils.FileUtils;
import emu.grasscutter.utils.Utils;
import express.http.HttpContextHandler;
import express.http.Request;
import express.http.Response;

import static emu.grasscutter.Configuration.*;

public final class GachaRecordHandler implements HttpContextHandler {
	String render_template;
	public GachaRecordHandler() {
		File template = new File(Utils.toFilePath(DATA("/gacha_records.html")));
		if (template.exists()) {
			// Load from cache
			render_template = new String(FileUtils.read(template));
		} else {
			render_template = "{{REPLACE_RECORD}}";
		}
	}

	@Override
	public void handle(Request req, Response res) throws IOException {
		// Grasscutter.getLogger().info( req.query().toString() );
		String sessionKey = req.query("s");
		int page = 0;
		int gachaType = 0;
		if (req.query("p") != null) {
			page = Integer.parseInt(req.query("p"));
		}

		if (req.query("gachaType") != null) {
			gachaType = Integer.parseInt(req.query("gachaType"));
		}

		Account account = DatabaseHelper.getAccountBySessionKey(sessionKey);
		if (account != null) {
			String records = DatabaseHelper.getGachaRecords(account.getPlayerUid(), page, gachaType).toString();
			// Grasscutter.getLogger().info(records);
			String response = render_template.replace("{{REPLACE_RECORD}}", records)
							.replace("{{REPLACE_MAXPAGE}}", String.valueOf(DatabaseHelper.getGachaRecordsMaxPage(account.getPlayerUid(), page, gachaType)));

			res.send(response);
		} else {
			res.send("No account found.");
		}
	}
}
