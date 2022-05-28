package emu.grasscutter.scripts.data;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Indexed;
import org.bson.types.ObjectId;
@Entity(value = "suites", useDiscriminator = false)
public class SuiteIndex {
    @Id
    private ObjectId id;


    public int getOwnerUid() {
        return ownerUid;
    }

    public void setOwnerUid(int ownerUid) {
        this.ownerUid = ownerUid;
    }

    @Indexed
    private int ownerUid;
    int sceneId;
    int groupId;

    public SuiteIndex(int sceneId, int groupId, int suiteIndex,int ownerUid) {
        this.sceneId = sceneId;
        this.groupId = groupId;
        this.suiteIndex = suiteIndex;
        this.ownerUid=ownerUid;
    }

    public int getSceneId() {
        return sceneId;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getSuiteIndex() {
        return suiteIndex;
    }

    public void setSuiteIndex(int suiteIndex) {
        this.suiteIndex = suiteIndex;
    }

    int suiteIndex;
}
