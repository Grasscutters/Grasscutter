package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.game.props.EntityType;

@ResourceType(name = "GadgetExcelConfigData.json")
public class GadgetData extends GameResource {
	private int id;

	private EntityType type;
	private String jsonName;
	private boolean isInteractive;
	private String[] tags;
	private String itemJsonName;
	private long nameTextMapHash;
	private int campID;

	@Override
	public int getId() {
		return this.id;
	}

	public EntityType getType() {
		return type;
	}

	public String getJsonName() {
		return jsonName;
	}

	public boolean isInteractive() {
		return isInteractive;
	}

	public String[] getTags() {
		return tags;
	}

	public String getItemJsonName() {
		return itemJsonName;
	}

	public long getNameTextMapHash() {
		return nameTextMapHash;
	}

	public int getCampID() {
		return campID;
	}

	@Override
	public void onLoad() {

	}
}
