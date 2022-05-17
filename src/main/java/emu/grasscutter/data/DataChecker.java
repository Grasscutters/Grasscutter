package emu.grasscutter.data;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.server.http.handlers.GachaHandler;
import emu.grasscutter.tools.Tools;
import emu.grasscutter.utils.FileUtils;
import emu.grasscutter.utils.Utils;

import java.io.*;
import java.nio.file.Path;
import java.util.List;

import static emu.grasscutter.Configuration.DATA;

public class DataChecker {

    public static void CheckAllFiles() {

        try {
            List<Path> filenames = FileUtils.getPathsFromResource("/defaults/data/");

            for (Path file : filenames) {
                String relativePath = String.valueOf(file).split("/defaults/data/")[1];

                CheckAndCopyData(relativePath);
            }
        } catch (Exception e) {
            Grasscutter.getLogger().error("An error occurred while trying to check the data folder. \n" + e);
            e.printStackTrace();
        }

        GenerateGachaMappings();
    }

    private static void CheckAndCopyData(String name) {
        String filePath = Utils.toFilePath(DATA(name));

        if (!Utils.fileExists(filePath)) {
            // Check if file is in subdirectory
            if (name.indexOf("/") != -1) {
                String[] path = name.split("/");

                String folder = "";
                for(int i = 0; i < (path.length - 1); i++) {
                    folder += path[i] + "/";

                    // Make sure the current folder exists
                    String folderToCreate = Utils.toFilePath(DATA(folder));
                    if(!Utils.fileExists(folderToCreate)) {
                        Grasscutter.getLogger().info("Creating data folder '" + folder + "'");
                        Utils.createFolder(folderToCreate);
                    }
                }
            }

            Grasscutter.getLogger().info("Creating default '" + name + "' data");
            FileUtils.copyResource("/defaults/data/" + name, filePath);
        }
    }

    private static void GenerateGachaMappings() {
        if (!Utils.fileExists(GachaHandler.gachaMappings)) {
            try {
                Grasscutter.getLogger().info("Creating default '" + GachaHandler.gachaMappings + "' data");
                Tools.createGachaMapping(GachaHandler.gachaMappings);
            } catch (Exception exception) {
                Grasscutter.getLogger().warn("Failed to create gacha mappings. \n" + exception);
            }
        }
    }
}
