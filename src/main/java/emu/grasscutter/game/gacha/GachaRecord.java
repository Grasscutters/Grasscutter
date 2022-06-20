package emu.grasscutter.game.gacha;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Indexed;
import org.bson.types.ObjectId;

import java.util.Date;

@Entity(value = "gachas", useDiscriminator = false)
public class GachaRecord {
    @Id
    private ObjectId id;

    @Indexed
    private int ownerId;

    private Date transactionDate;
    private int itemID;
    @Indexed
    private int gachaType;

    public GachaRecord() {
    }

    public GachaRecord(int itemId, int ownerId, int gachaType) {
        this.transactionDate = new Date();
        this.itemID = itemId;
        this.ownerId = ownerId;
        this.gachaType = gachaType;
    }

    public int getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getGachaType() {
        return this.gachaType;
    }

    public void setGachaType(int type) {
        this.gachaType = type;
    }

    public Date getTransactionDate() {
        return this.transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public int getItemID() {
        return this.itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public ObjectId getId() {
        return this.id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String toString() {
        return this.toJsonString();
    }

    public String toJsonString() {
        return "{\"time\": " + this.transactionDate.getTime() + ",\"item\":" + this.itemID + "}";
    }

}
