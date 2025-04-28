package emu.grasscutter.game.gacha;

import dev.morphia.annotations.*;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Setter
@Getter
@Entity(value = "gachas", useDiscriminator = false)
public class GachaRecord {
    @Id private ObjectId id;

    @Indexed private int ownerId;

    private Date transactionDate;
    private int itemID;
    @Indexed private int gachaType;

    public GachaRecord() {}

    public GachaRecord(int itemId, int ownerId, int gachaType) {
        this.transactionDate = new Date();
        this.itemID = itemId;
        this.ownerId = ownerId;
        this.gachaType = gachaType;
    }

    public String toString() {
        return toJsonString();
    }

    public String toJsonString() {
        return "{\"time\": " + this.transactionDate.getTime() + ",\"item\":" + this.itemID + "}";
    }
}
