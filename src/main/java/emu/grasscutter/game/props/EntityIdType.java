package emu.grasscutter.game.props;

public enum EntityIdType {
	AVATAR	(0x01),
	MONSTER	(0x02),
	NPC		(0x03),
	GADGET	(0x04),
	WEAPON	(0x06),
	TEAM 	(0x09),
	MPLEVEL	(0x0b);
	
	private final int id;

	private EntityIdType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
