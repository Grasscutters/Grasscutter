package emu.grasscutter.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.reflect.TypeToken;

import emu.grasscutter.GameConstants;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandMap;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.ResourceLoader;
import emu.grasscutter.data.def.AvatarData;
import emu.grasscutter.data.def.ItemData;
import emu.grasscutter.data.def.MonsterData;
import emu.grasscutter.data.def.SceneData;
import emu.grasscutter.utils.Utils;

public final class Tools {
	public static void createGmHandbook() throws Exception {
		ToolsWithLanguageOption.createGmHandbook(getLanguageOption());
	}

	public static void createGachaMapping(String location) throws Exception {
		ToolsWithLanguageOption.createGachaMapping(location, getLanguageOption());
	}

	public static List<String> getAvailableLanguage() throws Exception {
		File textMapFolder = new File(Grasscutter.getConfig().RESOURCE_FOLDER + "TextMap");
		List<String> availableLangList = new ArrayList<String>();
		for (String textMapFileName : textMapFolder.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if (name.startsWith("TextMap") && name.endsWith(".json")){
					return true;
				}
				return false;
			}
		})) {
			availableLangList.add(textMapFileName.replace("TextMap","").replace(".json","").toLowerCase());
		}
		return availableLangList;
	}

	public static String getLanguageOption() throws Exception {
		List<String> availableLangList = getAvailableLanguage();
	
		// Use system out for better format
		if (availableLangList.size() == 1) {
			return availableLangList.get(0).toUpperCase();
		}
		String stagedMessage = "";
		stagedMessage += "The following languages mappings are available, please select one: [default: EN]\n";
		String groupedLangList = ">\t";
		int groupedLangCount = 0;
		String input = "";
		for (String availableLanguage: availableLangList){
			groupedLangCount++;
			groupedLangList = groupedLangList + "" + availableLanguage + "\t";
			if (groupedLangCount == 6) {
				stagedMessage += groupedLangList + "\n";
				groupedLangCount = 0;
				groupedLangList = ">\t";
			}
		}
		if (groupedLangCount > 0) {
			stagedMessage += groupedLangList + "\n";
		}
		stagedMessage += "\nYour choice:[EN] ";
		
		input = Grasscutter.getConsole().readLine(stagedMessage);
		if (availableLangList.contains(input.toLowerCase())) {
			return input.toUpperCase();
		}
		Grasscutter.getLogger().info("Invalid option. Will use EN(English) as fallback");

		return "EN";
	}
}

final class ToolsWithLanguageOption {
	@SuppressWarnings("deprecation")
	public static void createGmHandbook(String language) throws Exception {
		ResourceLoader.loadResources();
		
		Map<Long, String> map;
		try (InputStreamReader fileReader = new InputStreamReader(new FileInputStream(Utils.toFilePath(Grasscutter.getConfig().RESOURCE_FOLDER + "TextMap/TextMap"+language+".json")), StandardCharsets.UTF_8)) {
			map = Grasscutter.getGsonFactory().fromJson(fileReader, new TypeToken<Map<Long, String>>() {}.getType());
		}
		
		List<Integer> list;
		String fileName = "./GM Handbook.txt";
		try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8), false)) {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now();
			   
			writer.println("// Grasscutter " + GameConstants.VERSION + " GM Handbook");
			writer.println("// Created " + dtf.format(now) + System.lineSeparator() + System.lineSeparator());

			CommandMap cmdMap = new CommandMap(true);
			List<Command> cmdList = new ArrayList<>(cmdMap.getAnnotationsAsList());

			writer.println("// Commands");
			for (Command cmd : cmdList) {
				String cmdName = cmd.label();
				while (cmdName.length() <= 15) {
					cmdName = " " + cmdName;
				}
				writer.println(cmdName + " : " + cmd.description());
			}

			writer.println();

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
	public static void createGachaMapping(String location, String language) throws Exception {
		ResourceLoader.loadResources();
		
		Map<Long, String> map;
		try (InputStreamReader fileReader = new InputStreamReader(new FileInputStream(Utils.toFilePath(Grasscutter.getConfig().RESOURCE_FOLDER + "TextMap/TextMap"+language+".json")), StandardCharsets.UTF_8)) {
			map = Grasscutter.getGsonFactory().fromJson(fileReader, new TypeToken<Map<Long, String>>() {}.getType());
		}
		
		List<Integer> list;

		String fileName = location;

		try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8), false)) {
			
			list = new ArrayList<>(GameData.getAvatarDataMap().keySet());
			Collections.sort(list); 
			 
			// if the user made choices for language, I assume it's okay to assign his/her selected language to "en-us"
			// since it's the fallback language and there will be no difference in the gacha record page. 
			// The enduser can still modify the `gacha_mappings.js` directly to enable multilingual for the gacha record system.
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
				// Got the magic number 4233146695 from manually search in the json file
				writer.println(
					"\"" + (avatarID % 1000 + 1000) + "\" : [\"" 
					+ map.get(data.getNameTextMapHash()) + "(" +  map.get(4233146695L)+ ")\", \"" 
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
				
				// Got the magic number 4231343903 from manually search in the json file

				writer.println(",\"" + data.getId() +
						 "\" : [\"" + map.get(data.getNameTextMapHash()).replaceAll("\"", "")
						 + "("+ map.get(4231343903L)+")\",\""+ color + "\"]");
			}
			writer.println(",\"200\": \""+map.get(332935371L)+"\", \"301\": \""+ map.get(2272170627L) + "\", \"302\": \""+map.get(2864268523L)+"\"");
			writer.println("}\n}");
		}
		
		Grasscutter.getLogger().info("Mappings generated to " + location + " !");
	}
}
