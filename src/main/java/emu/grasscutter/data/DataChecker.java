package emu.grasscutter.data;

import emu.grasscutter.Configuration;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.server.http.handlers.GachaHandler;
import emu.grasscutter.tools.Tools;
import emu.grasscutter.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import static emu.grasscutter.Configuration.DATA;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class DataChecker {

    public static void CheckAllFiles() {
        CheckAndCopyData("Banners.json");
        CheckAndCopyData("Drop.json");
        CheckAndCopyData("ExpeditionReward.json");
        CheckAndCopyData("GameAnnouncement.json");
        CheckAndCopyData("GameAnnouncementList.json");
        CheckAndCopyData("Shop.json");
        CheckAndCopyData("ShopChest.json");
        CheckAndCopyData("ShopChestBatchUse.json");
        CheckAndCopyData("Spawns.json");
        CheckAndCopyData("TowerSchedule.json");
        CheckAndCopyData("gacha/details.html");
        CheckAndCopyData("gacha/records.html");

        CheckAndCopyKey("dispatchKey.bin");
        CheckAndCopyKey("dispatchSeed.bin");
        CheckAndCopyKey("secretKey.bin");
        CheckAndCopyKey("secretKeyBuffer.bin");

        GenerateGachaMappings();
    }

    private static void CheckAndCopyKey(String name) {
        String filePath = Utils.toFilePath(Grasscutter.getConfig().folderStructure.keys + "/" + name);

        if(!(new File(filePath).exists())) {
            Grasscutter.getLogger().info("Creating default '" + name + "' key");
            CopyFileFromResources("/defaults/keys/" + name, filePath);
        }
    }

    private static void CheckAndCopyData(String name) {
        String filePath = Utils.toFilePath(DATA(name));

        if(name.indexOf("/") != -1) {
            String[] path = name.split("/");

            if(path.length <= 2) {
                Utils.createFolder(Utils.toFilePath(DATA(path[0])));
            } else {
                Grasscutter.getLogger().error("Unable to create directories more then one directory deep inside the 'data' folder");
            }
        }

        if(!(new File(filePath).exists())) {
            Grasscutter.getLogger().info("Creating default '" + name + "' data");
            CopyFileFromResources("/defaults/data/" + name, filePath);
        }
    }

    private static void GenerateGachaMappings() {
        if(!(new File(GachaHandler.gachaMappings).exists())) {
            try {
                Grasscutter.getLogger().info("Creating default '" + GachaHandler.gachaMappings + "' data");
                Tools.createGachaMapping(GachaHandler.gachaMappings);
            } catch (Exception exception) {
                Grasscutter.getLogger().warn("Failed to create gacha mappings.", exception);
            }
        }
    }

    public static void CopyFileFromResources(String resourcePath, String destination) {
        try (InputStream is = Grasscutter.class.getResourceAsStream(resourcePath)) {
            Files.copy(is, Paths.get(new File(destination).toURI()), REPLACE_EXISTING);
        } catch (Exception exception) {
            // An error occurred copying the resource
            Grasscutter.getLogger().error(String.format("An error occurred while trying to copy a resource %s to %s", resourcePath, destination));
            exception.printStackTrace();
        }
    }
}
