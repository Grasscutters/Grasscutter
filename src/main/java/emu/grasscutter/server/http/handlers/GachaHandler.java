package emu.grasscutter.server.http.handlers;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.database.DatabaseHelper;
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

import static emu.grasscutter.Configuration.DATA;

/**
 * Handles all gacha-related HTTP requests.
 */
public final class GachaHandler implements Router {
    private final String gachaMappings;
    
    private static String frontendTemplate = "{{REPLACE_RECORD}}";
    
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
        if(templateFile.exists())
            frontendTemplate = new String(FileUtils.read(templateFile));
    }
    
    @Override public void applyRoutes(Express express, Javalin handle) {
        express.get("/gacha", GachaHandler::gachaRecords);
        
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
            
            response.send(frontendTemplate
                    .replace("{{REPLACE_RECORD}}", records)
                    .replace("{{REPLACE_MAXPAGE}}", String.valueOf(maxPage)));
        }
    }
}
