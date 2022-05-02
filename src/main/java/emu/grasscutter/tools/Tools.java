package emu.grasscutter.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.reflect.TypeToken;

import emu.grasscutter.GameConstants;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.ResourceLoader;
import emu.grasscutter.data.def.AvatarData;
import emu.grasscutter.data.def.ItemData;
import emu.grasscutter.data.def.MonsterData;
import emu.grasscutter.data.def.SceneData;
import emu.grasscutter.utils.Utils;

public final class Tools {
	
	@SuppressWarnings("deprecation")
	public static void createGmHandbook() throws Exception {
		ResourceLoader.loadResources();
		
		Map<Long, String> map;
		try (InputStreamReader fileReader = new InputStreamReader(new FileInputStream(Utils.toFilePath(Grasscutter.getConfig().RESOURCE_FOLDER + "TextMap/TextMapEN.json")), StandardCharsets.UTF_8)) {
			map = Grasscutter.getGsonFactory().fromJson(fileReader, new TypeToken<Map<Long, String>>() {}.getType());
		}
		
		List<Integer> list;
		String fileName = "./GM Handbook.txt";
		try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8), false)) {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now();
			   
			writer.println("// Grasscutter " + GameConstants.VERSION + " GM Handbook");
			writer.println("// Created " + dtf.format(now) + System.lineSeparator() + System.lineSeparator());
			
			list = new ArrayList<>(GameData.getAvatarDataMap().keySet());
			Collections.sort(list); 
			 
			writer.println("// Avatars");
			for (Integer id : list) {
				AvatarData data = GameData.getAvatarDataMap().get(id);
				writer.println(data.getId() + " : " + map.get(data.getNameTextMapHash()));
			}
			
			writer.println();
			
			list = new ArrayList<>(GameData.getItemDataMap().keySet());
			Collections.sort(list); 
			
			writer.println("// Items");
			for (Integer id : list) {
				ItemData data = GameData.getItemDataMap().get(id);
				writer.println(data.getId() + " : " + map.get(data.getNameTextMapHash()));
			}
			
			writer.println();
			
			writer.println("// Scenes");
			list = new ArrayList<>(GameData.getSceneDataMap().keySet());
			Collections.sort(list); 
			
			for (Integer id : list) {
				SceneData data = GameData.getSceneDataMap().get(id);
				writer.println(data.getId() + " : " + data.getScriptData());
			}
			
			writer.println();
			
			writer.println("// Monsters");
			list = new ArrayList<>(GameData.getMonsterDataMap().keySet());
			Collections.sort(list); 
			
			for (Integer id : list) {
				MonsterData data = GameData.getMonsterDataMap().get(id);
				writer.println(data.getId() + " : " + map.get(data.getNameTextMapHash()));
			}
		}
		
		Grasscutter.getLogger().info("GM Handbook generated!");
	}

	@SuppressWarnings("deprecation")
	public static void createGachaMapping() throws Exception {
		ResourceLoader.loadResources();
		
		Map<Long, String> map;
		try (InputStreamReader fileReader = new InputStreamReader(new FileInputStream(Utils.toFilePath(Grasscutter.getConfig().RESOURCE_FOLDER + "TextMap/TextMapEN.json")), StandardCharsets.UTF_8)) {
			map = Grasscutter.getGsonFactory().fromJson(fileReader, new TypeToken<Map<Long, String>>() {}.getType());
		}
		
		List<Integer> list;


		String fileName = Grasscutter.getConfig().RESOURCE_FOLDER + "/gcstatic";
		File folder = new File(fileName);
		if (!folder.exists()) { folder.mkdirs(); } // create folder if it doesn't exist
		fileName = fileName + "/mappings.js";

		try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8), false)) {
			
			list = new ArrayList<>(GameData.getAvatarDataMap().keySet());
			Collections.sort(list); 
			 
			writer.println("mappings = {\"en-us\": {"); 

			// Avatars
			boolean first = true;
			for (Integer id : list) {
				AvatarData data = GameData.getAvatarDataMap().get(id);
				int avatarID = data.getId();
				if (avatarID >= 11000000) { // skip test avatar
					continue;
				}
				if (first) { // skip adding comma for the first element
					first = false;
				} else {
					writer.print(",");
				}
				String color;
				switch (data.getQualityType()){
					case "QUALITY_PURPLE":
						color = "purple";
						break;
					case "QUALITY_ORANGE":
						color = "yellow";
						break;
					case "QUALITY_BLUE":
					default:
						color = "blue";
				}
				
				writer.println(
					"\"" + (avatarID % 1000 + 1000) + "\" : [\"" 
					+ map.get(data.getNameTextMapHash()) + "(Avatar)\", \"" 
					+ color + "\"]");
			}
			
			writer.println();
			
			list = new ArrayList<>(GameData.getItemDataMap().keySet());
			Collections.sort(list); 
			
			// Weapons
			for (Integer id : list) {
				ItemData data = GameData.getItemDataMap().get(id);
				if (data.getId() <= 11101 || data.getId() >= 20000) {
					continue; //skip non weapon items
				}
				String color;

				switch (data.getRankLevel()){
					case 3: 
						color = "blue";
						break;
					case 4:
						color = "purple";
						break;
					case 5:
						color = "yellow";
						break;
					default:
						continue; // skip unnecessary entries
				}
				writer.println(",\"" + data.getId() +
						 "\" : [\"" + map.get(data.getNameTextMapHash()).replaceAll("\"", "")
						 + "(Weapon)\",\""+ color + "\"]");
			}
			writer.println(",\"200\": \"Standard\", \"301\": \"Avatar Event\", \"302\": \"Weapon event\"");
			writer.println("}\n}");
		}
		Grasscutter.getLogger().info("Mappings generated!");
	}
}
