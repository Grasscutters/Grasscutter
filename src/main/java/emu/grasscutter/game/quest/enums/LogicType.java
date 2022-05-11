package emu.grasscutter.game.quest.enums;

public enum LogicType {
	LOGIC_NONE (0),
	LOGIC_AND (1),
	LOGIC_OR (2),
	LOGIC_NOT (3),
	LOGIC_A_AND_ETCOR (4),
	LOGIC_A_AND_B_AND_ETCOR (5),
	LOGIC_A_OR_ETCAND (6),
	LOGIC_A_OR_B_OR_ETCAND (7),
	LOGIC_A_AND_B_OR_ETCAND (8);
	
	private final int value;
	
	LogicType(int id) {
		this.value = id;
	}

	public int getValue() {
		return value;
	}
}
