package emu.grasscutter;

import java.util.Arrays;

import emu.grasscutter.utils.Position;
import emu.grasscutter.utils.Utils;

public final class GameConstants {
	public static String VERSION = "2.6.0";
	
	public static final int MAX_TEAMS = 4;
	public static final int MAIN_CHARACTER_MALE = 10000005;
	public static final int MAIN_CHARACTER_FEMALE = 10000007;
	public static final Position START_POSITION = new Position(2747, 194, -1719);
	
	public static final int MAX_FRIENDS = 45;
	public static final int MAX_FRIEND_REQUESTS = 50;
	
	public static final int SERVER_CONSOLE_UID = 99; // The UID of the server console's "player".
	
	// Default entity ability hashes.
	public static final String[] DEFAULT_ABILITY_STRINGS = {
		"Avatar_DefaultAbility_VisionReplaceDieInvincible", "Avatar_DefaultAbility_AvartarInShaderChange", "Avatar_SprintBS_Invincible", 
		"Avatar_Freeze_Duration_Reducer", "Avatar_Attack_ReviveEnergy", "Avatar_Component_Initializer", "Avatar_FallAnthem_Achievement_Listener"
	};
	public static final int[] DEFAULT_ABILITY_HASHES = Arrays.stream(DEFAULT_ABILITY_STRINGS).mapToInt(Utils::abilityHash).toArray();
	public static final int DEFAULT_ABILITY_NAME = Utils.abilityHash("Default");
}
