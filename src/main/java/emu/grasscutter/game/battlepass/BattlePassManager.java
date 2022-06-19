package emu.grasscutter.game.battlepass;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.packet.send.PacketBattlePassCurScheduleUpdateNotify;

public class BattlePassManager {

    private final Player player;
    private int point;
    private int awardTakenLevel;

    public BattlePassManager(Player player){
        this.player = player;
        point = player.getAccount().getPoint();
        awardTakenLevel = player.getAccount().getAwardTakenLevel();
    }

    public void addPoint(int point){
        this.point += point;
        player.getAccount().setPoint(point);
        player.getSession().send(new PacketBattlePassCurScheduleUpdateNotify(player.getSession().getPlayer()));
        //save the point data
        player.getAccount().save();
    }

    public void updateAwardTakenLevel(int level){
        this.awardTakenLevel = level;
        player.getAccount().setAwardTakenLevel(awardTakenLevel);
        player.getSession().send(new PacketBattlePassCurScheduleUpdateNotify(player.getSession().getPlayer()));
        player.getAccount().save();
    }

    public int getPoint() {
        return point;
    }

    public int getAwardTakenLevel() {
        return awardTakenLevel;
    }
}
