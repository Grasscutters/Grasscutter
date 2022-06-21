package emu.grasscutter.game.battlepass;

import org.bson.types.ObjectId;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Indexed;
import dev.morphia.annotations.Transient;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.packet.send.PacketBattlePassCurScheduleUpdateNotify;

@Entity(value = "battlepass", useDiscriminator = false)
public class BattlePassManager {
	@Id private ObjectId id;
	@Transient private Player player;
	
	@Indexed private int ownerUid;
    private int point;
    private int awardTakenLevel;
    
    @Deprecated // Morphia only
    public BattlePassManager() {}

    public BattlePassManager(Player player) {
        this.setPlayer(player);
    }
    
    public ObjectId getId() {
		return id;
	}

	public Player getPlayer() {
    	return this.player;
    }
    
    public void setPlayer(Player player) {
    	this.player = player;
    	this.ownerUid = player.getUid();
    }
    
    public int getPoint() {
        return point;
    }

    public int getAwardTakenLevel() {
        return awardTakenLevel;
    }

    public void addPoint(int point){
        this.point += point;
        player.getSession().send(new PacketBattlePassCurScheduleUpdateNotify(player.getSession().getPlayer()));
        this.save();
    }

    public void updateAwardTakenLevel(int level){
        this.awardTakenLevel = level;
        player.getSession().send(new PacketBattlePassCurScheduleUpdateNotify(player.getSession().getPlayer()));
        this.save();
    }
    
    public void save() {
    	DatabaseHelper.saveBattlePass(this);
    }
}
