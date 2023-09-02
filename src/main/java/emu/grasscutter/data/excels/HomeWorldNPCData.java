package emu.grasscutter.data.excels;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.data.*;
import emu.grasscutter.game.inventory.ItemQuality;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;

@ResourceType(name = "HomeWorldNPCExcelConfigData.json")
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HomeWorldNPCData extends GameResource {
    int furnitureID;
    int avatarID;

    @SerializedName(
            value = "npcId",
            alternate = {"HDLJMOGHICL"})
    int npcId;

    @SerializedName(
            value = "talkIdList",
            alternate = {"CKMCLCNIBLD"})
    List<Integer> talkIdList;

    @SerializedName(
            value = "isTalkRandomly",
            alternate = {"HPJMMEBNMAI"})
    boolean isTalkRandomly;

    @SerializedName(
            value = "npcQuality",
            alternate = {"BHJOIKFHIBD"})
    ItemQuality npcQuality;

    @SerializedName(
            value = "titleTextMapHash",
            alternate = {"GNMAIEGCFPO"})
    long titleTextMapHash;

    long descTextMapHash;

    @Override
    public int getId() {
        return this.avatarID;
    }
}
