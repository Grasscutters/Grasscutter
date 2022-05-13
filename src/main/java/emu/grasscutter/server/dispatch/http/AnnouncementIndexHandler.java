package emu.grasscutter.server.dispatch.http;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.Account;
import emu.grasscutter.utils.FileUtils;
import emu.grasscutter.utils.Utils;
import express.http.HttpContextHandler;
import express.http.Request;
import express.http.Response;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static emu.grasscutter.Configuration.DATA;

public class AnnouncementIndexHandler implements HttpContextHandler {
    private final String render_template;
    private final String render_swjs;
    private final String render_vueminjs;

    public AnnouncementIndexHandler() {
        File template = new File(Utils.toFilePath(DATA("/hk4e/announcement/index.html")));
        File swjs = new File(Utils.toFilePath(DATA("/hk4e/announcement/sw.js")));
        File vueminjs = new File(Utils.toFilePath(DATA("/hk4e/announcement/vue.min.js")));
        this.render_template = template.exists() ? new String(FileUtils.read(template)) : null;
        this.render_swjs = swjs.exists() ? new String(FileUtils.read(swjs)) : null;
        this.render_vueminjs = vueminjs.exists() ? new String(FileUtils.read(vueminjs)) : null;
    }

    @Override
    public void handle(Request req, Response res) throws IOException {
        if (Objects.equals(req.path(), "/sw.js")) {
            res.send(render_swjs);
        }else if(Objects.equals(req.path(), "/hk4e/announcement/index.html")) {
            res.send(render_template);
        }else if(Objects.equals(req.path(), "/dora/lib/vue/2.6.11/vue.min.js")){
            res.send(render_vueminjs);
        }else{
            File renderFile = new File(Utils.toFilePath(DATA(req.path())));
            if(renderFile.exists()){
                String ext = req.path().substring(req.path().lastIndexOf(".") + 1);
                switch(ext){
                    case "css":
                        res.type("text/css");
                        res.send(FileUtils.read(renderFile));
                        break;
                    case "js":
                    default:
                        res.send(FileUtils.read(renderFile));
                        break;
                }
            }else{
                Grasscutter.getLogger().info( "File not exist: " + req.path());
            }
        }


    }
}
