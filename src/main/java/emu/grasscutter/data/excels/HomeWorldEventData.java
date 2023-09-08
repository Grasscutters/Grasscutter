package emu.grasscutter.data.excels;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.game.home.suite.event.SuiteEventType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@ResourceType(name = "HomeWorldEventExcelConfigData.json")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class HomeWorldEventData extends GameResource {
    @SerializedName(
            value = "id",
            alternate = {"BBEIIPEFDPE"})
    int id;

    @SerializedName(
            value = "eventType",
            alternate = {"JOCKIMECHDP"})
    SuiteEventType eventType;

    int avatarID;

    @SerializedName(
            value = "talkId",
            alternate = {"IGNJAICDFPD"})
    int talkId;

    int rewardID;

    @SerializedName(
            value = "suiteId",
            alternate = {"FEHOKMJPOED"})
    int suiteId;
}
