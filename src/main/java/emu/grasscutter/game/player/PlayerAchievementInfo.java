package emu.grasscutter.game.player;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Indexed;
import emu.grasscutter.database.DatabaseHelper;
import org.bson.types.ObjectId;

@Entity(value = "achievements", useDiscriminator = false)
public class PlayerAchievementInfo{
    @Id private ObjectId Id;
    private int achievementId;
    @Indexed private int ownerUid;
    int currentProgress;
    boolean finished;
    boolean viewed;
    int finishedDate;
    String[] paramList;

    @Deprecated // Morphia only. Do not use.
    public PlayerAchievementInfo() {}

    public PlayerAchievementInfo(int id){
        this.achievementId = id;
        this.currentProgress = 0;
        this.finished = false;
        this.viewed = false;
        this.paramList = new String[]{"", "", "", ""};
        this.finishedDate = 0;
    }

    public void setOwnerUid(int ownerUid) {
        this.ownerUid = ownerUid;
    }

    public int getOwnerUid(){
        return ownerUid;
    }

    public int getAchievementId() {
        return achievementId;
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
    }

    public boolean getFinished(){
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean getViewed(){
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public String[] getParamList() {
        return paramList;
    }

    public int getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(int finishedDate) {
        this.finishedDate = finishedDate;
    }

    public void setParamList(String[] paramList) {
        this.paramList = paramList;
    }

    public Integer getStatusValue(){
        if(!getFinished()){
            return 1;
        }else if(getFinished() && !getViewed()){
            return 2;
        }else if(getFinished() && getViewed()) {
            return 3;
        }
        return 0;
    }

    public boolean getStatusHidden(boolean isHidden){
        if(isHidden && !getFinished()){
            return true;
        }
        return false;
    }

    public void save() {
        DatabaseHelper.saveAchievement(this);
    }
}