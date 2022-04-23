package emu.grasscutter.tools;

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

import emu.grasscutter.GenshinConstants;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GenshinData;
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
			   
			writer.println("// Genshin Impact " + GenshinConstants.VERSION + " GM Handbook");
			writer.println("// Created " + dtf.format(now) + System.lineSeparator() + System.lineSeparator());
			
			list = new ArrayList<>(GenshinData.getAvatarDataMap().keySet());
			Collections.sort(list); 
			 
			writer.println("// Avatars");
			for (Integer id : list) {
				AvatarData data = GenshinData.getAvatarDataMap().get(id);
				writer.println(data.getId() + " : " + map.get(data.getNameTextMapHash()));
			}
			
			writer.println();
			
			list = new ArrayList<>(GenshinData.getItemDataMap().keySet());
			Collections.sort(list); 
			
			writer.println("// Items");
			for (Integer id : list) {
				ItemData data = GenshinData.getItemDataMap().get(id);
				writer.println(data.getId() + " : " + map.get(data.getNameTextMapHash()));
			}
			
			writer.println();
			
			writer.println("// Scenes");
			list = new ArrayList<>(GenshinData.getSceneDataMap().keySet());
			Collections.sort(list); 
			
			for (Integer id : list) {
				SceneData data = GenshinData.getSceneDataMap().get(id);
				writer.println(data.getId() + " : " + data.getScriptData());
			}
			
			writer.println();
			
			writer.println("// Monsters");
			list = new ArrayList<>(GenshinData.getMonsterDataMap().keySet());
			Collections.sort(list); 
			
			for (Integer id : list) {
				MonsterData data = GenshinData.getMonsterDataMap().get(id);
				writer.println(data.getId() + " : " + map.get(data.getNameTextMapHash()));
			}
		}
		
		Grasscutter.getLogger().info("GM Handbook generated!");
	}
}
