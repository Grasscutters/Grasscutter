package emu.grasscutter.game.dungeons;

import java.util.ArrayList;
import java.util.List;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.MonsterData;
import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.proto.VisionTypeOuterClass.VisionType;
import emu.grasscutter.scripts.constants.EventType;
import emu.grasscutter.scripts.data.SceneGroup;
import emu.grasscutter.scripts.data.SceneMonster;
import emu.grasscutter.server.packet.send.PacketChallengeDataNotify;
import emu.grasscutter.server.packet.send.PacketDungeonChallengeBeginNotify;
import emu.grasscutter.server.packet.send.PacketDungeonChallengeFinishNotify;
import emu.grasscutter.server.packet.send.PacketSceneEntityAppearNotify;

public class DungeonChallenge {
	private final Scene scene;
	private final SceneGroup group;
	
	private int challengeIndex;
	private int challengeId;
	private boolean success;
	private boolean progress;
	
	private int score;
	private int objective = 0;
	
	public DungeonChallenge(Scene scene, SceneGroup group) {
		this.scene = scene;
		this.group = group;
		
		objective += group.monsters.size();
	}

	public Scene getScene() {
		return scene;
	}

	public SceneGroup getGroup() {
		return group;
	}
	
	public int getChallengeIndex() {
		return challengeIndex;
	}

	public void setChallengeIndex(int challengeIndex) {
		this.challengeIndex = challengeIndex;
	}

	public int getChallengeId() {
		return challengeId;
	}

	public void setChallengeId(int challengeId) {
		this.challengeId = challengeId;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean isSuccess) {
		this.success = isSuccess;
	}
	
	public boolean inProgress() {
		return progress;
	}

	public int getScore() {
		return score;
	}

	public void start() {
		this.progress = true;
		getScene().broadcastPacket(new PacketDungeonChallengeBeginNotify(this));
	}
	
	public void finish() {
		this.progress = false;
		getScene().broadcastPacket(new PacketDungeonChallengeFinishNotify(this));
		
		if (this.isSuccess()) {
			this.getScene().getScriptManager().callEvent(EventType.EVENT_CHALLENGE_SUCCESS, null);
		} else {
			this.getScene().getScriptManager().callEvent(EventType.EVENT_CHALLENGE_FAIL, null);
		}
	}

	public void onMonsterDie(EntityMonster entity) {
		score = getScore() + 1;
		
		getScene().broadcastPacket(new PacketChallengeDataNotify(this, 1, getScore()));
		
		if (getScore() >= objective) {
			this.setSuccess(true);
			finish();
		}
	}
}
