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
    int totalProgress;
    boolean finished;
    boolean viewed;
    boolean hidden;
    int finishRewardId;
    int finishedDate;
    String[] paramList;

    @Deprecated // Morphia only. Do not use.
    public PlayerAchievementInfo() {}

    public PlayerAchievementInfo(int id){
        this.achievementId = id;
        this.currentProgress = 0;
        this.totalProgress = 1;
        this.finished = false;
        this.viewed = false;
        this.hidden = false;
        this.finishRewardId = 0;
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

    public boolean getHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden){
        this.hidden = hidden;
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
    }

    public int getTotalProgress() {
        return totalProgress;
    }

    public void setTotalProgress(int totalProgress) {
        this.totalProgress = totalProgress;
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

    public int getFinishRewardId() {
        return finishRewardId;
    }

    public void setFinishRewardId(int finishRewardId){
        this.finishRewardId = finishRewardId;
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

    public boolean getStatusHidden(){
        if(getHidden() && !getFinished()){
            return true;
        }
        return false;
    }

    public void save() {
        DatabaseHelper.saveAchievement(this);
    }
}