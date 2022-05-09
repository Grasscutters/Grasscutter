package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.utils.Utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "handbook", usage = "handbook <search term>", description = "commands.handbook.description", aliases = {"hb"}, permission = "server.handbook")
public final class HandbookCommand implements CommandHandler {
    HashMap<String, HashMap<String, Integer>> handbook;
    HashMap<Integer, String> realNames;
    List<String> categories = Arrays.asList("// Avatars", "// Items", "// Scenes", "// Monsters");
    Pattern entryRegex = Pattern.compile("(\\d+) : (.*)");
    Pattern replaceRegex = Pattern.compile("\\W");

    private void parseHandbook() {
		String fileName = "./GM Handbook.txt";
        handbook = new HashMap<String, HashMap<String, Integer>>();
        realNames = new HashMap<Integer, String>();
        
        try {
            FileInputStream stream = new FileInputStream(Utils.toFilePath(fileName));
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
			String line;
            HashMap<String, Integer> category = null;
            while ((line = reader.readLine()) != null) {
				if (line.startsWith("//")) {
                    if (categories.contains(line)) {
                        category = new HashMap<String, Integer>();
                        handbook.put(line, category);
                    }
                } else if (category != null) {
                    Matcher match = entryRegex.matcher(line);
                    if (match.find()) {
                        int id = Integer.parseInt(match.group(1));
                        String name = match.group(2);
                        realNames.put(id, name);  // Our keys will not be nice to read so we want to be able to refer back to the real names
                        if (name.length() > 0) {
                            name = replaceRegex.matcher(name).replaceAll("");  // Strip all non-letters
                            name = name.toLowerCase();
                            category.put(name, id);
                        }
                    }
                } else {
                    // CommandHandler.sendMessage(null, line);
                }
			}
            stream.close();
		} catch (IOException e) {
			Grasscutter.getLogger().warn("Failed to read handbook.");
		} catch (NullPointerException ignored) {
			Grasscutter.getLogger().warn("Failed to read handbook.");
			return;
		} catch (Exception e) {
			Grasscutter.getLogger().warn("Failed to read handbook.");
            return;
        }
    }

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (handbook == null) {
            parseHandbook();
        }
        if (args.size() < 1) {
            CommandHandler.sendMessage(sender, String.format("Usage: handbook. Entries: %d", handbook.size()));
            return;
        }

        String query = args.get(0).toLowerCase();
        ArrayList<Integer> matches = new ArrayList<Integer>();
        ArrayList<String> matchesStrs = new ArrayList<String>();
        for (String category : categories) {
            boolean printedCategory = false;
            HashMap<String, Integer> c = handbook.get(category);
            // CommandHandler.sendMessage(sender, String.format("Checking category \"%s\" of size %d", category, c.size()));
            for (String key : c.keySet()) {
                if (key.contains(query)) {
                    int id = c.get(key);
                    matches.add(id);
                    if (!printedCategory) {
                        matchesStrs.add(category);
                        printedCategory = true;
                    }
                    matchesStrs.add(String.format("%d : %s", id, realNames.get(id)));
                }
            }
        }
        CommandHandler.sendMessage(sender, String.join("\n", matchesStrs));
    }
}
