package emu.grasscutter.commands;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import emu.grasscutter.data.GenshinData;
import emu.grasscutter.data.def.ItemData;
import emu.grasscutter.data.def.MonsterData;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.avatar.GenshinAvatar;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.entity.EntityItem;
import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.game.entity.GenshinEntity;
import emu.grasscutter.game.inventory.GenshinItem;
import emu.grasscutter.game.inventory.ItemType;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.server.packet.send.PacketEntityFightPropUpdateNotify;
import emu.grasscutter.server.packet.send.PacketItemAddHintNotify;
import emu.grasscutter.utils.Position;

public class PlayerCommands {
	private static HashMap<String, PlayerCommand> commandList = new HashMap<String, PlayerCommand>();
	private static HashMap<String, PlayerCommand> commandAliasList = new HashMap<String, PlayerCommand>();

	
	static {
		try {
			// Look for classes
			for (Class<?> cls : PlayerCommands.class.getDeclaredClasses()) {
				// Get non abstract classes
			    if (!Modifier.isAbstract(cls.getModifiers())) {
			    	Command commandAnnotation = cls.getAnnotation(Command.class);
			    	PlayerCommand command = (PlayerCommand) cls.newInstance();
			    	
			    	if (commandAnnotation != null) {
			    		command.setLevel(commandAnnotation.gmLevel());
						command.setHelpText(commandAnnotation.helpText());
						for (String alias : commandAnnotation.aliases()) {
			    			if (alias.length() == 0) {
			    				continue;
			    			}

							String commandName = alias;
							commandAliasList.put(commandName, command);
			    		}
			    	}

			    	String commandName = cls.getSimpleName().toLowerCase();
			    	commandList.put(commandName, command);
			    }
		
			}
		} catch (Exception e) {
			
		}
	}

	public static void handle(GenshinPlayer player, String msg) {
		String[] split = msg.split(" ");
		
		// End if invalid
		if (split.length == 0) {
			return;
		}

		String first = split[0].toLowerCase().substring(1);
		PlayerCommand c = PlayerCommands.commandList.get(first);
		PlayerCommand a = PlayerCommands.commandAliasList.get(first);
		
		if (c != null || a != null) {
			PlayerCommand cmd = c != null ? c : a;
			// Level check
			if (player.getGmLevel() < cmd.getLevel()) {
				return;
			}
			// Execute
			int len = Math.min(first.length() + 1, msg.length());
			cmd.execute(player, msg.substring(len));
		}
	}
	
	public static abstract class PlayerCommand {
		// GM level required to use this command
		private int level;
		private String helpText;

		protected int getLevel() { return this.level; }
		protected void setLevel(int minLevel) { this.level = minLevel; }

		protected String getHelpText() { return this.helpText; }
		protected void setHelpText(String helpText) { this.helpText = helpText; }

		// Main
		public abstract void execute(GenshinPlayer player, String raw);
	}
	
	// ================ Commands ================

	@Command(aliases = {"h"}, helpText = "Shows this command")
	public static class Help extends PlayerCommand {

		@Override
		public void execute(GenshinPlayer player, String raw) {
			String helpMessage = "Grasscutter Commands: ";
			for (Map.Entry<String, PlayerCommand> cmd : commandList.entrySet()) {

				helpMessage += "\n" + cmd.getKey() + " - " + cmd.getValue().helpText;
			}

			player.dropMessage(helpMessage);
		}
	}
	
	@Command(aliases = {"g", "item", "additem"}, helpText = "/give [item id] [count] - Gives {count} amount of {item id}")
	public static class Give extends PlayerCommand {
		@Override
		public void execute(GenshinPlayer player, String raw) {
			String[] split = raw.split(" ");
			int itemId = 0, count = 1;
			
			try {
				itemId = Integer.parseInt(split[0]);
			} catch (Exception e) {
				itemId = 0;
			}
			
			try {
				count = Math.max(Math.min(Integer.parseInt(split[1]), Integer.MAX_VALUE), 1);
			} catch (Exception e) {
				count = 1;
			}
			
			// Give
			ItemData itemData = GenshinData.getItemDataMap().get(itemId);
			GenshinItem item;
			
			if (itemData == null) {
				player.dropMessage("Error: Item data not found");
				return;
			}
			
			if (itemData.isEquip()) {
				List<GenshinItem> items = new LinkedList<>();
				for (int i = 0; i < count; i++) {
					item = new GenshinItem(itemData);
					items.add(item);
				}
				player.getInventory().addItems(items);
				player.sendPacket(new PacketItemAddHintNotify(items, ActionReason.SubfieldDrop));
			} else {
				item = new GenshinItem(itemData, count);
				player.getInventory().addItem(item);
				player.sendPacket(new PacketItemAddHintNotify(item, ActionReason.SubfieldDrop));
			}
		}
	}
	
	@Command(aliases = {"d"}, helpText = "/drop [item id] [count] - Drops {count} amount of {item id}")
	public static class Drop extends PlayerCommand {
		@Override
		public void execute(GenshinPlayer player, String raw) {
			String[] split = raw.split(" ");
			int itemId = 0, count = 1;
			
			try {
				itemId = Integer.parseInt(split[0]);
			} catch (Exception e) {
				itemId = 0;
			}
			
			try {
				count = Math.max(Math.min(Integer.parseInt(split[1]), Integer.MAX_VALUE), 1);
			} catch (Exception e) {
				count = 1;
			}
			
			// Give
			ItemData itemData = GenshinData.getItemDataMap().get(itemId);
			
			if (itemData == null) {
				player.dropMessage("Error: Item data not found");
				return;
			}
			
			if (itemData.isEquip()) {
				float range = (5f + (.1f * count));
				for (int i = 0; i < count; i++) {
					Position pos = player.getPos().clone().addX((float) (Math.random() * range) - (range / 2)).addY(3f).addZ((float) (Math.random() * range) - (range / 2));
					EntityItem entity = new EntityItem(player.getScene(), player, itemData, pos, 1);
					player.getScene().addEntity(entity);
				}
			} else {
				EntityItem entity = new EntityItem(player.getScene(), player, itemData, player.getPos().clone().addY(3f), count);
				player.getScene().addEntity(entity);
			}
		}
	}
	
	@Command(helpText = "/spawn [monster id] [count] - Creates {count} amount of {item id}")
	public static class Spawn extends PlayerCommand {
		@Override
		public void execute(GenshinPlayer player, String raw) {
			String[] split = raw.split(" ");
			int monsterId = 0, count = 1, level = 1;
			
			try {
				monsterId = Integer.parseInt(split[0]);
			} catch (Exception e) {
				monsterId = 0;
			}
			
			try {
				level = Math.max(Math.min(Integer.parseInt(split[1]), 200), 1);
			} catch (Exception e) {
				level = 1;
			}
			
			try {
				count = Math.max(Math.min(Integer.parseInt(split[2]), 1000), 1);
			} catch (Exception e) {
				count = 1;
			}
			
			// Give
			MonsterData monsterData = GenshinData.getMonsterDataMap().get(monsterId);
			
			if (monsterData == null) {
				player.dropMessage("Error: Monster data not found");
				return;
			}
			
			float range = (5f + (.1f * count));
			for (int i = 0; i < count; i++) {
				Position pos = player.getPos().clone().addX((float) (Math.random() * range) - (range / 2)).addY(3f).addZ((float) (Math.random() * range) - (range / 2));
				EntityMonster entity = new EntityMonster(player.getScene(), monsterData, pos, level);
				player.getScene().addEntity(entity);
			}
		}
	}
	
	@Command(helpText = "/killall")
	public static class KillAll extends PlayerCommand {
		@Override
		public void execute(GenshinPlayer player, String raw) {
			List<GenshinEntity> toRemove = new LinkedList<>();
			for (GenshinEntity entity : player.getScene().getEntities().values()) {
				if (entity instanceof EntityMonster) {
					toRemove.add(entity);
				}
			}
			toRemove.forEach(e -> player.getScene().killEntity(e, 0));
		}
	}
	
	@Command(helpText = "/resetconst - Resets all constellations for the currently active character")
	public static class ResetConst extends PlayerCommand {
		@Override
		public void execute(GenshinPlayer player, String raw) {
			EntityAvatar entity = player.getTeamManager().getCurrentAvatarEntity();
			
			if (entity == null) {
				return;
			}
			
			GenshinAvatar avatar = entity.getAvatar();
			
			avatar.getTalentIdList().clear();
			avatar.setCoreProudSkillLevel(0);
			avatar.recalcStats();
			avatar.save();
			
			player.dropMessage("Constellations for " + entity.getAvatar().getAvatarData().getName() + " have been reset. Please relogin to see changes.");
		}
	}
	
	@Command(helpText = "/godmode - Prevents you from taking damage")
	public static class Godmode extends PlayerCommand {
		@Override
		public void execute(GenshinPlayer player, String raw) {
			player.setGodmode(!player.hasGodmode());
			player.dropMessage("Godmode is now " + (player.hasGodmode() ? "ON" : "OFF"));
		}
	}
	
	@Command(helpText = "/sethp [hp]")
	public static class Sethp extends PlayerCommand {
		@Override
		public void execute(GenshinPlayer player, String raw) {
			String[] split = raw.split(" ");
			int hp = 0;
			
			try {
				hp = Math.max(Integer.parseInt(split[0]), 1);
			} catch (Exception e) {
				hp = 1;
			}
			
			EntityAvatar entity = player.getTeamManager().getCurrentAvatarEntity();
			
			if (entity == null) {
				return;
			}
			
			entity.setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, hp);
			entity.getScene().broadcastPacket(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_CUR_HP));
		}
	}
	
	@Command(aliases = {"clearart"}, helpText = "/clearartifacts")
	public static class ClearArtifacts extends PlayerCommand {
		@Override
		public void execute(GenshinPlayer player, String raw) {
			List<GenshinItem> toRemove = new LinkedList<>();
			for (GenshinItem item : player.getInventory().getItems().values()) {
				if (item.getItemType() == ItemType.ITEM_RELIQUARY && item.getLevel() == 1 && item.getExp() == 0 && !item.isLocked() && !item.isEquipped()) {
					toRemove.add(item);
				}
			}
			
			player.getInventory().removeItems(toRemove);
		}
	}
	
	@Command(aliases = {"scene"}, helpText = "/Changescene [Scene id]")
	public static class ChangeScene extends PlayerCommand {
		@Override
		public void execute(GenshinPlayer player, String raw) {
			int sceneId = 0;
		
			try {
				sceneId = Integer.parseInt(raw);
			} catch (Exception e) {
				return;
			}
			
			player.getWorld().transferPlayerToScene(player, sceneId, player.getPos());
		}
	}
}
