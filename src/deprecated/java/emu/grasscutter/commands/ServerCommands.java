package emu.grasscutter.commands;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GenshinData;
import emu.grasscutter.data.def.ItemData;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.inventory.GenshinItem;
import emu.grasscutter.utils.Crypto;
import emu.grasscutter.utils.Utils;

public class ServerCommands {
	private static HashMap<String, ServerCommand> list = new HashMap<>();
	
	static {
		try {
			// Look for classes
			for (Class<?> cls : ServerCommands.class.getDeclaredClasses()) {
				// Get non abstract classes
			    if (!Modifier.isAbstract(cls.getModifiers())) {
			    	String commandName = cls.getSimpleName().toLowerCase();
			    	list.put(commandName, (ServerCommand) cls.newInstance());
			    }
		
			}
		} catch (Exception e) {
			
		}
	}

	public static void handle(String msg) {
		String[] split = msg.split(" ");
		
		// End if invalid
		if (split.length == 0) {
			return;
		}
		
		//
		String first = split[0].toLowerCase();
		ServerCommand c = ServerCommands.list.get(first);
		
		if (c != null) {
			// Execute
			int len = Math.min(first.length() + 1, msg.length());
			c.execute(msg.substring(len));
		}
	}
	
	public static abstract class ServerCommand {
		public abstract void execute(String raw);
	}
	
	// ================ Commands ================

	public static class Reload extends ServerCommand {
		@Override
		public void execute(String raw) {
			Grasscutter.getLogger().info("Reloading config.");
			Grasscutter.loadConfig();
			Grasscutter.getDispatchServer().loadQueries();
			Grasscutter.getLogger().info("Reload complete.");
		}
	}

	public static class sendMsg extends ServerCommand {
		@Override
		public void execute(String raw) {
			List<String> split = Arrays.asList(raw.split(" "));

			if (split.size() < 2) {
				Grasscutter.getLogger().error("Invalid amount of args");
				return;
			}

			String playerID = split.get(0);
			String message = split.stream().skip(1).collect(Collectors.joining(" "));


			emu.grasscutter.game.Account account = DatabaseHelper.getAccountByPlayerId(Integer.parseInt(playerID));
			if (account != null) {
				GenshinPlayer player = Grasscutter.getGameServer().getPlayerById(Integer.parseInt(playerID));
				if(player != null) {
					player.dropMessage(message);
					Grasscutter.getLogger().info(String.format("Successfully sent message to %s: %s", playerID, message));
				} else {
					Grasscutter.getLogger().error("Player not online");
				}
			} else {
				Grasscutter.getLogger().error(String.format("Player %s does not exist", playerID));
			}
		}
	}
	
	public static class Account extends ServerCommand {
		@Override
		public void execute(String raw) {
			String[] split = raw.split(" ");
			
			if (split.length < 2) {
				Grasscutter.getLogger().error("Invalid amount of args");
				return;
			}		
			
			String command = split[0].toLowerCase();
			String username = split[1];

			switch (command) {
				case "create":
					if (split.length < 2) {
						Grasscutter.getLogger().error("Invalid amount of args");
						return;
					}		
					
					int reservedId = 0;		
					try {
						reservedId = Integer.parseInt(split[2]);
					} catch (Exception e) {
						reservedId = 0;
					}
					
					emu.grasscutter.game.Account account = DatabaseHelper.createAccountWithId(username, reservedId);
					if (account != null) {
						Grasscutter.getLogger().info("Account created" + (reservedId > 0 ? " with an id of " + reservedId : ""));
					} else {
						Grasscutter.getLogger().error("Account already exists");
					}
					break;
				case "delete":
					boolean success = DatabaseHelper.deleteAccount(username);
					
					if (success) {
						Grasscutter.getLogger().info("Account deleted");
					}
					break;
				/*
				case "setpw":
				case "setpass":
				case "setpassword":
					if (split.length < 3) {
						Grasscutter.getLogger().error("Invalid amount of args");
						return;
					}		
					
					account = DatabaseHelper.getAccountByName(username);
					
					if (account == null) {
						Grasscutter.getLogger().error("No account found!");
						return;
					}
					
					token = split[2];
					token = PasswordHelper.hashPassword(token);
					
					account.setPassword(token);
					DatabaseHelper.saveAccount(account);
					
					Grasscutter.getLogger().info("Password set");
					break;
				*/
			}
		}
	}
}
