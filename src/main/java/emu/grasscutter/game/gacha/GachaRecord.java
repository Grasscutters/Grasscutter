package emu.grasscutter.game.gacha;

import java.util.Date;

import org.bson.types.ObjectId;

import dev.morphia.annotations.*;

@Entity(value = "gachas", useDiscriminator = false)
public class GachaRecord {
    @Id private ObjectId id;
    
    @Indexed private int ownerId;

    private Date transactionDate; 
    private int itemID;
    @Indexed private int gachaType;

    public GachaRecord() {}

    public GachaRecord(int itemId ,int ownerId, int gachaType){
        this.transactionDate = new Date();
        this.itemID = itemId;
        this.ownerId = ownerId;
        this.gachaType = gachaType;
    }
    
    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getGachaType() {
        return gachaType;
    }

    public void setGachaType(int type) {
        this.gachaType = type;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }
    
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public ObjectId getId(){
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String toString() {
        return toJsonString();
    }
    public String toJsonString() {
        return "{\"time\": " + this.transactionDate.getTime() + ",\"item\":" + this.itemID + "}";
    }
    
}
